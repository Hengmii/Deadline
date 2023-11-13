package com.example.deadline.DeadlineList

import DeadlineAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deadline.ProjectDeadlineApplication
import com.example.deadline.databinding.FragmentDeadlineListRecyclerViewBinding
import com.example.deadline.viewmodels.ProjectDeadlineViewModel
import com.example.deadline.viewmodels.ProjectDeadlineViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DeadlineRecycleViewFragment: Fragment() {

    private var _binding: FragmentDeadlineListRecyclerViewBinding? = null

    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private val viewModel: ProjectDeadlineViewModel by activityViewModels {
        ProjectDeadlineViewModelFactory(
            (activity?.application as ProjectDeadlineApplication).database
                .deadlineDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDeadlineListRecyclerViewBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val deadlineAdapter = DeadlineAdapter({
            val action = DeadlineRecycleViewFragmentDirections.actionDeadlineRecycleViewFragmentToDeadlineDetailFragment(it)
        })

        GlobalScope.launch(Dispatchers.IO) {
            deadlineAdapter.submitList(viewModel.fullDeadline())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}