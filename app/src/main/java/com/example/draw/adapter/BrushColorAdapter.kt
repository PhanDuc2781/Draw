package com.example.draw.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.draw.R
import com.example.draw.databinding.ItemColorBrushBinding

class BrushColorAdapter(
    private var context: Context,
    private val onItemClick: (String, Int) -> Unit
) : RecyclerView.Adapter<BrushColorAdapter.ViewHolderBrush>() {

    private val colors = mutableListOf<String>()
    private var selected = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolderBrush(
        context,
        ItemColorBrushBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("RecyclerView")
    override fun onBindViewHolder(holder: ViewHolderBrush, position: Int) {
        holder.bindData(colors[position])
        holder.itemView.rootView.setOnClickListener {
            if (position == 0) {
                onItemClick(colors[position], 0)
            } else {
                onItemClick(colors[position], 1)
            }

            selected = position
            notifyDataSetChanged()

        }
    }

    inner class ViewHolderBrush(
        private val context: Context,
        private val binding: ItemColorBrushBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceType")
        @RequiresApi(Build.VERSION_CODES.M)
        fun bindData(item: String) {
            binding.apply {
                val color = Color.parseColor(item)
                val drawable = ColorDrawable(color)
                if (adapterPosition == 0) {
                    itemColorBrush.setImageResource(R.drawable.choose_color)
                } else {
                    itemColorBrush.setImageDrawable(drawable)
                }

                binding.itemColorBrush.borderWidth =
                    if (selected == adapterPosition) context.resources.getDimensionPixelSize(
                        R.dimen.width
                    ) else 0
                binding.itemColorBrush.borderColor =
                    if (selected == adapterPosition) context.getColor(R.color.choose_color) else context.getColor(
                        R.color.un_choose_color
                    )
            }
        }
    }

    override fun getItemCount(): Int {
        return colors.size
    }

    fun setData(data: List<String>) {
        colors.clear()
        colors.addAll(data)
        notifyDataSetChanged()
    }
}