package com.example.draw.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.draw.databinding.ItemImageBinding
import com.example.draw.databinding.ItemImageViewpagerBinding
import com.example.draw.entities.Image
import com.example.draw.utils.Util

class ImageViewPager: RecyclerView.Adapter<ImageViewPager.ViewHolder>() {
    var listImage: MutableList<Image> = mutableListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ImageViewPager.ViewHolder {
        val binding = ItemImageViewpagerBinding.inflate(LayoutInflater.from(viewGroup.context) , viewGroup , false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ImageViewPager.ViewHolder, position: Int) {
        viewHolder.binImage(listImage[position])
    }

    override fun getItemCount(): Int {
        return listImage.size
    }
    inner class ViewHolder(private val binding: ItemImageViewpagerBinding) : RecyclerView.ViewHolder(binding.root){
        fun binImage(image: Image){
            binding.iamge.setImageBitmap(Util.stringToBitmap(image.url))
        }
    }
}