package com.example.draw.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.AttrRes
import com.example.draw.R
import com.example.draw.databinding.ItemBrushBinding

class BrushChooseSize :  LinearLayout, OnItemBrushSelectedListener {

    var isSelectedVisibility: Boolean = false
    set(value) {
        binding.cardViewBrushSize.elevation = 0f
        binding.cardViewBrushSize.setCardBackgroundColor(
            if (value) context.resources.getColor(R.color.choose_color) else context.resources.getColor(
                R.color.white
            )
        )

        binding.imageBrushSelected.visibility = if (value) VISIBLE else INVISIBLE
        binding.imageBrush.visibility = if (value) INVISIBLE else VISIBLE
        field = value
    }

    var iamge_BrushSelected: Int? = null
    set(value) {
        if (value != null) {
            binding.imageBrushSelected.setImageResource(value)
        }
        field = value
    }

    var iamge_Brush: Int? = null
    set(value) {
        if (value != null) {
            binding.imageBrush.setImageResource(value)
        }
        field = value
    }


    constructor(context: Context) : super(context) {
        initView(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(attrs, 0)
    }

    constructor(
        context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initView(attrs, defStyleAttr)
    }

    lateinit var binding: ItemBrushBinding
    private fun initView(attrs: AttributeSet?, @AttrRes defStyleAttr: Int) {
        binding = ItemBrushBinding.inflate(LayoutInflater.from(context), this, true)

        val brushCustomView =
            context.obtainStyledAttributes(attrs, R.styleable.BrushView, defStyleAttr, 0)

        try {
            isSelectedVisibility = brushCustomView.getBoolean(R.styleable.BrushView_isSelected, false)
            iamge_Brush = brushCustomView.getResourceId(R.styleable.BrushView_icon, 0)
            iamge_BrushSelected =
                brushCustomView.getResourceId(R.styleable.BrushView_icon_selected, 0)
        } finally {
            brushCustomView.recycle()
        }
    }

    override fun onItemBrushSelected():  View? = binding.root
}

interface OnItemBrushSelectedListener {
    fun onItemBrushSelected(): View?
}

fun OnItemBrushSelectedListener.setOnBrushItemListener(onclick: () -> Unit) {
    onItemBrushSelected()?.setOnClickListener {
        onclick.invoke()
    }
}