package com.example.deadline.fragment

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.deadline.databinding.SelectColorFragmentBinding
import com.example.deadline.viewmodels.ColorViewModel
import com.example.deadline.viewmodels.SharedViewModel

class SelectColorFragment : Fragment() {
    private var _binding: SelectColorFragmentBinding? = null

    private val binding get() = _binding!!

    private var selectedColor = "#666666"

    private lateinit var colorViewModel: ColorViewModel

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = SelectColorFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        colorViewModel = ViewModelProvider(requireActivity()).get(ColorViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val aquamarineCircle = binding.aquamarineCircle
        val blueCircle = binding.blueCircle
        val greenCircle = binding.greenCircle
        val orangeCircle = binding.orangeCircle
        val pinkCircle = binding.pinkCircle
        val purpleCircle = binding.purpleCircle
        val lightGreenCircle = binding.lightGreenCircle
        val yellowCircle = binding.yellowCircle

        val defaultColorMap = mapOf(
            "#88CCC7" to aquamarineCircle,
            "#719DE5" to blueCircle,
            "#7CB58F" to greenCircle,
            "#E99B71" to orangeCircle,
            "#F092BC" to pinkCircle,
            "#BDACD1" to purpleCircle,
            "#CEDE81" to lightGreenCircle,
            "#E4CC87" to yellowCircle
        )

        val selectedColorView = binding.selectedColor
        val circleDrawable = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setSize(120, 120)
        }
        for (color in defaultColorMap) {
            color.value.setOnClickListener {
                selectedColor = color.key
                circleDrawable.setColor(Color.parseColor(selectedColor))
                selectedColorView.background = circleDrawable
            }
        }

        val saveButton = binding.confirmSelectColorButton
        saveButton.setOnClickListener() {
            colorViewModel.setSelectedColor(selectedColor)
            findNavController().popBackStack()
            sharedViewModel.selectedColor.value = selectedColor
        }
    }
}