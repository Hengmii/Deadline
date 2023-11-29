package com.example.deadline.fragment

import DeadlineAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deadline.DeadlineList.DeadlineProgressBar
import com.example.deadline.ProjectDeadlineApplication
import com.example.deadline.databinding.FragmentDeadlineListRecyclerViewBinding
import com.example.deadline.viewmodels.ColorViewModel
import com.example.deadline.viewmodels.ProjectDeadlineViewModel
import com.example.deadline.viewmodels.ProjectDeadlineViewModelFactory

class DeadlineRecycleViewFragment : Fragment() {

    private var _binding: FragmentDeadlineListRecyclerViewBinding? = null

    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private val viewModel: ProjectDeadlineViewModel by viewModels {
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
        binding.greetingText.setContent { Greeting() }
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val deadlineAdapter = DeadlineAdapter({
//            val action = DeadlineRecycleViewFragmentDirections.actionDeadlineRecycleViewFragmentToDeadlineDetailFragment(it)
        })
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = deadlineAdapter
        }

        viewModel.fullDeadlines.observe(viewLifecycleOwner) {
            deadlineAdapter.submitList(it)
        }
        binding.addDeadlineButton.setOnClickListener {
            Log.d("DeadlineRecycleViewFragment", "addDeadlineButton clicked")
            navigateToAddDeadlineFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToAddDeadlineFragment() {
        val action =
            DeadlineRecycleViewFragmentDirections.actionDeadlineRecycleViewFragmentToAddDeadlineFragment()
        findNavController().navigate(action)
    }
}