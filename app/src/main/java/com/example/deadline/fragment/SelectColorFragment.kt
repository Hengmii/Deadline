package com.example.deadline.fragment

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.deadline.databinding.SelectColorFragmentBinding

class SelectColorFragment : Fragment() {
    private var _binding: SelectColorFragmentBinding? = null

    private val binding get() = _binding!!

    private var selectedColor = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = SelectColorFragmentBinding.inflate(inflater, container, false)
        return binding.root
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

        var defaultColorMap = mapOf(
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
    }
}