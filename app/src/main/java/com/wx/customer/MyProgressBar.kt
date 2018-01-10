package com.wx.customer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Created by wx on 2018/1/9.
 */
class MyProgressBar:View {
    constructor(context: Context?) : super(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val RING_WIDTH = 50f
    private val RADUS = 300f
    private lateinit var mPaint: Paint

    init {
        mPaint = Paint()
        mPaint.strokeWidth = RING_WIDTH
        mPaint.isAntiAlias = true
        mPaint.color = Color.parseColor("#e3eaef")
        mPaint.style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

//        画圆环

        canvas?.drawCircle(width/2f, height/2f, RADUS, mPaint)
    }
}