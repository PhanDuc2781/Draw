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

    var job: Job? = null
    val listImages: LiveData<out MutableList<Image>>
        get() = imageRepository.getAllImage()

    fun insert(image: Image) {
        job?.cancel()
        job = viewModelScope.launch {
            imageRepository.insert(image)
        }
    }

    fun delete(image: Image) {
        job?.cancel()
        job = viewModelScope.launch {
            imageRepository.delete(image)
        }
    }
}