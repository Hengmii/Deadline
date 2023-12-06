package com.example.deadline.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.deadline.ProjectDeadlineApplication
import com.example.deadline.data.DeadlineState
import com.example.deadline.data.database.Deadline
import com.example.deadline.databinding.FullDetailFragmentBinding
import com.example.deadline.viewmodels.ProjectDeadlineViewModel
import com.example.deadline.viewmodels.ProjectDeadlineViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class DetailDeadlineFragment : Fragment() {
    private var _binding: FullDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var deadline: Deadline
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FullDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val viewModel: ProjectDeadlineViewModel by viewModels {
        ProjectDeadlineViewModelFactory(
            (activity?.application as ProjectDeadlineApplication).database
                .deadlineDao()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deadline = DetailDeadlineFragmentArgs.fromBundle(requireArguments()).deadline
        updateComposeView()
    }

    fun updateComposeView() {
        binding.composeView.setContent {
            DeadlineDetailView(deadline = deadline, {
                val executor = Executors.newSingleThreadExecutor()
                executor.execute {
                    viewModel.updateDeadlineState(deadline.id, DeadlineState.DONE.toString())
                    Handler(Looper.getMainLooper()).post {
                        findNavController().popBackStack()
                    }
                }
            }, {
                findNavController().popBackStack()
            })
        }

    }
}