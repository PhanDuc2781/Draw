package com.example.draw.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.draw.R
import com.example.draw.adapter.ImageAdapter
import com.example.draw.databinding.FragmentHomeBinding
import com.example.draw.entities.Image
import com.example.draw.viewmodel.ImageViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private val imageViewModel: ImageViewModel by viewModels()
    private lateinit var adapterImage: ImageAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater , container , false)
        binding?.btnDraw?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_drawFragment)
        }

        adapterImage = ImageAdapter {image->
            val bundle = Bundle().apply {
                putSerializable("image" , image)
            }
            findNavController().navigate(R.id.action_homeFragment_to_showDitailImageFragment , bundle)
        }

        binding?.recImage?.apply {
            layoutManager = GridLayoutManager(requireContext() , 2)
            setHasFixedSize(true)
            adapter = adapterImage
        }

        //observe data change to fill in recycleView
        imageViewModel.listImage.observe(viewLifecycleOwner , Observer {
            adapterImage.setUpList(it)
        })

        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}