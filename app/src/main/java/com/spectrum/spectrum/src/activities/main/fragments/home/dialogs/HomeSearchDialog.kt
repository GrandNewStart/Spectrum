package com.spectrum.spectrum.src.activities.main.fragments.home.dialogs

import android.content.Context
import android.graphics.Rect
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.models.Post
import com.spectrum.spectrum.src.models.Info

class HomeSearchDialog(private val context: Context) {

    private val mDialog: PopupWindow
    private val mView: View
    private var mOnDismissListener: ()->Unit = {}
    private var mKeywords = ArrayList<String>().apply {
        add("휴학생")
        add("인턴")
        add("uiux")
    }
    private var mPosts = ArrayList<Post>().apply {
        val specs = arrayListOf(
            Info(0, "취업준비"),
            Info(1, "26세"),
            Info(2, "여성"),
            Info(3, "IT/인터넷"),
            Info(4, "디자인")
        )
        add(Post(0, "졸업 전 대기업 인턴", "06/25 16:25", 12, 0, specs))
        add(Post(0, "졸업 전 대기업 인턴", "06/25 16:25", 12, 0, specs))
        add(Post(0, "졸업 전 대기업 인턴", "06/25 16:25", 12, 0, specs))
        add(Post(0, "졸업 전 대기업 인턴", "06/25 16:25", 12, 0, specs))
        add(Post(0, "졸업 전 대기업 인턴", "06/25 16:25", 12, 0, specs))
    }

    init {
        val inflater = LayoutInflater.from(context)
        mView = inflater.inflate(R.layout.dialog_search_assist, null)
        initView()
        val offsetFromTop = context.resources.getDimensionPixelSize(R.dimen.app_bar_size)
        val height = Constants.SCREEN_HEIGHT - offsetFromTop
        mDialog = PopupWindow(mView, WindowManager.LayoutParams.MATCH_PARENT, height, false)
        mDialog.inputMethodMode = PopupWindow.INPUT_METHOD_NEEDED
        mDialog.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING
        mDialog.isFocusable = false
        mDialog.overlapAnchor = true
        mDialog.elevation = context.resources.getDimension(R.dimen.default_half_margin)
        mDialog.setOnDismissListener { mOnDismissListener() }
        mDialog.isOutsideTouchable = true
    }

    private fun initView() {
        mView.apply {
            findViewById<ChipGroup>(R.id.recent_keywords_chips).apply {
                mKeywords.forEach {
                    val chip = Chip(context)
                    chip.setChipStrokeColorResource(R.color.spectrumGray1)
                    chip.setChipStrokeWidthResource(R.dimen.default_stroke_width)
                    chip.setChipBackgroundColorResource(R.color.clear)
                    chip.setCloseIconResource(R.drawable.icon_close)
                    chip.isCloseIconVisible = true
                    chip.text = it
                    chip.setOnCloseIconClickListener {
                        removeView(chip)
                    }
                    addView(chip)
                }
            }
            findViewById<RecyclerView>(R.id.recent_recycler_view).apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        val spacing = resources.getDimensionPixelSize(R.dimen.default_double_margin)
                        outRect.left += spacing/2
                        outRect.right += spacing/2
                        outRect.top += spacing
                        outRect.bottom += spacing
                    }
                })
                PagerSnapHelper().attachToRecyclerView(this)
                //adapter = PostAdapter(mPosts, PostAdapter.HORIZONTAL)
            }
        }
    }

    fun show() {
        mDialog.showAtLocation(mView, Gravity.BOTTOM, 0, 0)
    }

    fun hide() {
        mDialog.dismiss()
    }

    fun isShowing(): Boolean = mDialog.isShowing

    fun setOnDismissListener(listener: ()->Unit): HomeSearchDialog {
        mOnDismissListener = listener
        return this
    }

}