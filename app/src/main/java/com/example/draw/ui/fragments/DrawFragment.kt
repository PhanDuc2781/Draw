package com.example.draw.ui.fragments

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.draw.R
import com.example.draw.adapter.BrushColorAdapter
import com.example.draw.customview.BrushChooseSize
import com.example.draw.customview.setOnBrushItemListener
import com.example.draw.databinding.FragmentDrawBinding
import com.example.draw.entities.Image
import com.example.draw.utils.Util
import com.example.draw.viewmodel.ImageViewModel
import com.raed.rasmview.brushtool.data.Brush
import com.raed.rasmview.brushtool.data.BrushesRepository
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DrawFragment : Fragment() {

    lateinit var binding: FragmentDrawBinding
    private val imageViewModel : ImageViewModel by viewModels()

    private var selectedPen: ImageView? = null
    private val brushSizesMap: MutableMap<ImageView, Float> = mutableMapOf()

    var isPositionSelected = false
    var selectedPosition = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDrawBinding.inflate(inflater , container , false)

        brushPencelAction()
        brushPenAction()
        brushMakerAction()
        brushPenBoldAction()
        eraserAction()
        colorPicker()

        //select option draw
        val optionDraws = arrayOf(
            binding.imagePencel,
            binding.imagePen,
            binding.imageMaker,
            binding.imagePenBold,
            binding.imageEraser,
            binding.imgChooseimage
        )
        optionDraws.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {

                if (isPositionSelected && index == selectedPosition) {
                    return@setOnClickListener
                }

                isPositionSelected = true
                selectedPosition = index

                if (index != 5) {
                    val previousPen = selectedPen
                    selectedPen = imageView

                    previousPen?.apply {
                        scaleY = 1.0f
                    }

                    selectedPen?.apply {
                        scaleY = 1.7f
                    }

                    val selectedPenHeight = selectedPen?.height ?: 0

                    previousPen?.apply {
                        layoutParams.height = selectedPenHeight
                        requestLayout()
                    }
                    val brushesRepository = BrushesRepository(resources)
                    val brush = Brush.values()[index]
                    binding.raswView.rasmContext.brushConfig = brushesRepository.get(brush)

                    val selectedPenSize = brushSizesMap[imageView] ?: 0.1f
                    binding.raswView.rasmContext.brushConfig.size = selectedPenSize
                }

                val pencelVisivility = if (index == 0) View.VISIBLE else View.GONE
                val penVisivility = if (index == 1) View.VISIBLE else View.GONE
                val penMakerVisivility = if (index == 2) View.VISIBLE else View.GONE
                val penBoldVisivility = if (index == 3) View.VISIBLE else View.GONE
                val eraserVisivility = if (index == 4) View.VISIBLE else View.GONE
                val chooseColorVisivility = if (index == 5) View.VISIBLE else View.GONE

                binding.constrainColor.visibility = chooseColorVisivility
                binding.constrainBrushPencel.visibility = pencelVisivility
                binding.constrainBrushPen.visibility = penVisivility
                binding.constrainBrushPenMaker.visibility = penMakerVisivility
                binding.constrainBrushPenBold.visibility = penBoldVisivility
                binding.constrainEraser.visibility = eraserVisivility

                binding.imgChooseimage.borderColor =
                    if (index == 5) requireActivity().getColor(R.color.choose_color) else requireActivity().getColor(R.color.un_choose_color)

                val isPositionSelectedAnimation = BooleanArray(optionDraws.size)

                if (!isPositionSelectedAnimation[index] && index != 5) {
                    if (imageView.scaleX == 1.0f && imageView.scaleY == 1.0f) {
                        imageView.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.move_up))
                    } else {
                        imageView.startAnimation(
                            AnimationUtils.loadAnimation(
                                requireContext(),
                                R.anim.move_down
                            )
                        )
                    }
                }
            }
        }

        optionDraws[0].performClick()

        binding.txtReset.setOnClickListener {
            binding.raswView.rasmContext.clear()
        }

        binding.raswView.rasmContext
            .state
            .addOnStateChangedListener { state ->
                binding.imgRedo.isEnabled = state.canCallRedo()
                binding.imgUndo.isEnabled = state.canCallUndo()
            }

        binding.txtSaveDraw.setOnClickListener {
            returnBitmapDraw()
        }
//
        binding.imgRedo.setOnClickListener {
            binding.raswView.rasmContext.state.redo()
        }

        binding.imgUndo.setOnClickListener {
            binding.raswView.rasmContext.state.undo()
        }

        binding.imageView5.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun returnBitmapDraw() {
        val drawingBitmap = binding.raswView.rasmContext.exportRasm()
        val image = Util.bitmapToString(drawingBitmap)
            ?.let { Image(System.currentTimeMillis() , it) }
        image?.let { imageViewModel.insert(it) }
        findNavController().popBackStack()
    }


    private fun colorPicker() {
        val adapterColor = BrushColorAdapter(requireContext()) { color, i ->
            if (i == 0) {
                ColorPickerDialog.Builder(requireContext())
                    .setTitle("")
                    .setPreferenceName("MyColorPickerDialog")
                    .setPositiveButton(getString(R.string.ok),
                        ColorEnvelopeListener { envelope, fromUser ->
                            val hexColor = "#" + envelope.hexCode.substring(2)
                            setColorBrush(hexColor)
                        })
                    .setNegativeButton(
                        getString(R.string.cancle)
                    ) { dialogInterface, i -> dialogInterface.dismiss() }
                    .attachAlphaSlideBar(true)
                    .attachBrightnessSlideBar(true)
                    .setBottomSpace(12)
                    .show()
            } else {
                setColorBrush(color)
            }
        }

        binding.layoutColor.recColorBrush.apply {
            adapter = adapterColor
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapterColor.setData(listColor)
        }
    }

    private fun setColorBrush(color: String) {
        val colorBrush = Color.parseColor(color)
        binding.raswView.rasmContext.brushColor = colorBrush
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun brushPencelAction() {
        val brushPencelSizes = arrayOf(
            binding.layoutBrushPencel.brushPencel1,
            binding.layoutBrushPencel.brushPencel2,
            binding.layoutBrushPencel.brushPencel3,
            binding.layoutBrushPencel.brushPencel4,
            binding.layoutBrushPencel.brushPencel5
        )

        setSizes(brushPencelSizes, 0.1f)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun brushPenBoldAction() {
        val brushPenBoldSizes = arrayOf(
            binding.layoutPenBold.brushPenBold1,
            binding.layoutPenBold.brushPenBold2,
            binding.layoutPenBold.brushPenBold3,
            binding.layoutPenBold.brushPenBold4,
            binding.layoutPenBold.brushPenBold5,
        )

        setSizes(brushPenBoldSizes, 0.1f)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun brushMakerAction() {
        val brushMakerSizes = arrayOf(
            binding.layoutPenMaker.brushPenMaker1,
            binding.layoutPenMaker.brushPenMaker2,
            binding.layoutPenMaker.brushPenMaker3,
            binding.layoutPenMaker.brushPenMaker4,
            binding.layoutPenMaker.brushPenMaker5,
        )

        setSizes(brushMakerSizes, 0.1f)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun brushPenAction() {
        val brushPenSizes = arrayOf(
            binding.layoutPen.brushPen1,
            binding.layoutPen.brushPen2,
            binding.layoutPen.brushPen3,
            binding.layoutPen.brushPen4,
            binding.layoutPen.brushPen5,
        )

        setSizes(brushPenSizes, 0.1f)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun eraserAction() {
        val eraserSizes = arrayOf(
            binding.layoutEraser.eraser1,
            binding.layoutEraser.eraser2,
            binding.layoutEraser.eraser3,
            binding.layoutEraser.eraser4,
            binding.layoutEraser.eraser5,
        )

        setSizes(eraserSizes, 0.1f)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setSizes(
        sizes: Array<BrushChooseSize>,
        defaultSizeIndex: Float,
    ) {
        val selectedPenSize = brushSizesMap[selectedPen] ?: defaultSizeIndex

        sizes.forEachIndexed { index, customSizeView ->
            val size = (index + 1) / 10f
            customSizeView.isSelectedVisibility = (size == selectedPenSize)

            customSizeView.setOnBrushItemListener {
                sizes.forEach { it.isSelectedVisibility = false }
                customSizeView.isSelectedVisibility = true

                binding.raswView.rasmContext.brushConfig.size = size
                selectedPen?.updateBrushSize(size)
            }
        }

        // save size the selected pen
        selectedPen?.let { brushSizesMap.putIfAbsent(it, selectedPenSize) }
    }

    private fun ImageView.updateBrushSize(size: Float) {
        brushSizesMap[this] = size
    }

    companion object {
        val listColor = listOf(
            "#FFC545",
            "#9D6EFF",
            "#438BFB",
            "#00DFC9",
            "#55D35D",
            "#FF7917",
            "#E96AFF",
            "#FF617D",
            "#90A4AE",
            "#7fffd4",
            "#faebd7",
            "#adff2f"
        )
    }

}