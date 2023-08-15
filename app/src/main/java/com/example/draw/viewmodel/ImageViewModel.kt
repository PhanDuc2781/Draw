package com.example.draw.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draw.entities.Image
import com.example.draw.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(private val imageRepository: ImageRepository) :
    ViewModel() {

    private val _listImages = imageRepository.getAllImage()
    val listImage: LiveData<out MutableList<Image>> = _listImages

    fun insert(image: Image) {
        viewModelScope.launch {
            imageRepository.insert(image)
        }
    }

    fun delete(image: Image) {
        viewModelScope.launch {
            imageRepository.delete(image)
        }
    }
}