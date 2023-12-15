package com.example.deadline.view.DeadlineList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deadline.ProjectDeadlineApplication
import com.example.deadline.data.DeadlineState
import com.example.deadline.databinding.FragmentDeadlineListRecyclerViewBinding
import com.example.deadline.viewmodels.ProjectDeadlineViewModel
import com.example.deadline.viewmodels.ProjectDeadlineViewModelFactory
import java.util.concurrent.Executors

class DeadlineRecycleViewFragment : Fragment() {

    private var _binding: FragmentDeadlineListRecyclerViewBinding? = null

    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private lateinit var deadlineAdapter: DeadlineAdapter

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
        deadlineAdapter = DeadlineAdapter({
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

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = deadlineAdapter.currentPressedPosition ?: return super.onContextItemSelected(item)
        val deadline = deadlineAdapter.getDeadlineAtPosition(position)
        when (item.itemId) {
            1 -> {
                val action = DeadlineRecycleViewFragmentDirections.actionDeadlineRecycleViewFragmentToDetailDeadlineFragment(deadline)
                findNavController().navigate(action)
                Log.d("DeadlineRecycleViewFragment", "Show in full")
            }
            2 -> {
                val executor = Executors.newSingleThreadExecutor()
                executor.execute {
                    viewModel.updateDeadlineState(deadline.id, DeadlineState.DONE.toString())
                }
            }
            3-> {
                val action =
                    DeadlineRecycleViewFragmentDirections.actionDeadlineRecycleViewFragmentToEditDeadlineFragment(deadline)
                findNavController().navigate(action)
            }
            else -> return super.onContextItemSelected(item)
        }

        return true
    }
}