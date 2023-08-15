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

        imageViewModel.listImages.observe(viewLifecycleOwner , Observer {
            adapterImage = ImageAdapter {position->
                val bundel = Bundle().apply {
                    putParcelableArrayList("Images" , ArrayList(it))
                    putInt("Position" , position)
                }
                findNavController().navigate(R.id.action_homeFragment_to_showDitailImageFragment , bundel)
            }
            adapterImage.setUpList(it)

            binding?.recImage?.apply {
                layoutManager = GridLayoutManager(requireContext() , 2)
                setHasFixedSize(true)
                adapter = adapterImage
            }

        })

        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}