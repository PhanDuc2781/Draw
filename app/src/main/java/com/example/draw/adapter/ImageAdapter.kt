package com.example.draw.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.draw.databinding.ItemImageBinding
import com.example.draw.entities.Image
import com.example.draw.utils.Util

class ImageAdapter(private val onClick : (Image) ->Unit) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    var listImage: MutableList<Image> = mutableListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(viewGroup.context) , viewGroup , false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binImage(listImage[position])
    }

    override fun getItemCount() = listImage.size

    fun setUpList(list: MutableList<Image>){
        listImage.clear()
        listImage.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root.rootView){
        fun binImage(image: Image) {
           binding.iamge.setImageBitmap(Util.stringToBitmap(image.url))
            binding.iamge.setOnClickListener {
                onClick(image)
            }
        }
    }
}