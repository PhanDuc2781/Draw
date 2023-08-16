package com.example.draw.repository

import androidx.lifecycle.LiveData
import com.example.draw.dao.DAO
import com.example.draw.entities.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageRepository @Inject constructor(private val dao: DAO) {

    fun getAllImage(): LiveData<MutableList<Image>> {
        return dao.getAll()
    }

    suspend fun insert(image: Image) = withContext(Dispatchers.IO) {
        dao.insert(image)
    }

    suspend fun delete(image: Image) = withContext(Dispatchers.IO) {
        dao.delete(image)
    }
}