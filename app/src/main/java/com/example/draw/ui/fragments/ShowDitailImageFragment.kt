package com.example.draw.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.draw.R
import com.example.draw.databinding.FragmentShowDitailImageBinding
import com.example.draw.entities.Image
import com.example.draw.utils.Util
import com.example.draw.viewmodel.ImageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Suppress("DEPRECATION")
class ShowDitailImageFragment : Fragment() {
    private lateinit var binding: FragmentShowDitailImageBinding
    private val imageViewModel: ImageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShowDitailImageBinding.inflate(inflater , container , false)

        val image = arguments?.getSerializable("image") as Image

        binding.image.setImageBitmap(Util.stringToBitmap(image.url))

        binding.txtDelete.setOnClickListener {
            imageViewModel.delete(image)
            findNavController().popBackStack()
        }

        binding.imageBack.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}