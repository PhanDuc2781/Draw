package com.example.draw.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.draw.R
import com.example.draw.databinding.FragmentShowDitailImageBinding
import com.example.draw.entities.Image


@Suppress("DEPRECATION")
class ShowDitailImageFragment : Fragment() {
    private var _binding: FragmentShowDitailImageBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentShowDitailImageBinding.inflate(inflater , container , false)

        val images = arguments?.getParcelableArray("Images")
        val position = arguments?.getInt("Position")

        Toast.makeText(requireContext() , images?.size.toString() + position.toString() , Toast.LENGTH_SHORT).show()
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}