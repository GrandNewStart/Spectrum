package com.spectrum.spectrum.src.customs

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.config.Helpers.dp2px
import kotlin.math.min

class CircleView: View {

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    private var paint: Paint = Paint().apply { isAntiAlias = true }
    private val rect = Rect()
    private var circleColor = context.resources.getColor(R.color.spectrumLightBlue, null)
    private var text1 = ""
    private var text2 = ""
    private var text3 = ""
    private var text1Size = dp2px(14).toFloat()
    private var text2Size = dp2px(14).toFloat()
    private var text3Size = dp2px(14).toFloat()

    fun setCircleColor(color: Int) {
        circleColor = color
        invalidate()
    }

    fun getCircleColor(): Int = circleColor

    fun setText1(text: String, size: Float) {
        text1 = text
        text1Size = size
        invalidate()
    }
    fun setText2(text: String, size: Float) {
        text2 = text
        text2Size = size
        invalidate()
    }

    fun setText3(text: String, size: Float ) {
        text3 = text
        text3Size = size
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // Circle
        val usableWidth = width - paddingLeft - paddingRight
        val usableHeight = height - paddingTop - paddingBottom
        val radius = (min(usableWidth, usableHeight)/2).toFloat()
        val cx = (paddingLeft + usableWidth/2).toFloat()
        val cy = (paddingTop + usableHeight/2).toFloat()
        paint.color = circleColor
        canvas?.apply {
            drawCircle(cx, cy, radius, paint)
        }

        // Text 1
        paint.textSize = text1Size
        paint.textAlign = Paint.Align.CENTER
        paint.getTextBounds(text1, 0, text1.length, rect)
        paint.color = Color.BLACK
        paint.typeface = Typeface.createFromAsset(context.assets, "roboto_bold.ttf")
        canvas?.apply {
            drawText(text1, (width/2).toFloat(), height/2 - text1Size/2, paint)
        }

        // Text 2
        paint.textSize = text2Size
        paint.textAlign = Paint.Align.CENTER
        paint.getTextBounds(text2, 0, text2.length, rect)
        paint.color = Color.BLACK
        paint.typeface = Typeface.createFromAsset(context.assets, "roboto_medium.ttf")
        canvas?.apply {
            drawText(text2, (width/2).toFloat(), height/2 + text2Size/2, paint)
        }

        // Text 3
        paint.textSize = text3Size
        paint.textAlign = Paint.Align.CENTER
        paint.getTextBounds(text3, 0, text3.length, rect)
        paint.color = context.resources.getColor(R.color.spectrumGray2, null)
        paint.typeface = Typeface.createFromAsset(context.assets, "roboto_medium.ttf")
        canvas?.apply {
            drawText(text3, (width/2).toFloat(), height/2 + text2Size + text3Size/2, paint)
        }
    }

}