package com.wx.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * Created by wx on 2017/12/29.
 */
class PieView : View {
    constructor(context: Context?) : super(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var mColors = intArrayOf(Color.YELLOW, Color.BLUE, Color.GREEN, Color.RED, Color.GRAY)
    private var mAngle = floatArrayOf(50f, 90f, 80f, 20f, 120f)

    private var mPaint: Paint
    private var mWidth = 0
    private var mHight = 0

    init {
        mPaint = Paint()
        mPaint.style = Paint.Style.FILL
        mPaint.isAntiAlias = true

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHight = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.translate(mWidth / 2f, mHight / 2f)  //原点
        drawTen(canvas)
        //半径
        var r = (Math.min(mWidth, mHight) / 2 * 0.9).toFloat()
        var rect = RectF(-r, -r, r, r)
        var currAngle = 0f
        for (i in mColors.indices) {
            mPaint.color = mColors[i]

            canvas?.drawArc(rect, currAngle, mAngle[i],true, mPaint)
            currAngle += mAngle[i]
            Log.d("wang", currAngle.toString()+ "--->"+ mAngle[i].toString())
        }
    }

    private fun drawTen(canvas: Canvas?){
        canvas!!.drawLine(0f, 0f, 0F, -mHight /2f, mPaint)
        canvas!!.drawLine(0f, 0f, 0f, mHight /2f, mPaint)
        canvas!!.drawLine(0f, 0f, -mWidth /2f, 0f, mPaint)
        canvas!!.drawLine(0f, 0f, mWidth /2f, 0f, mPaint)
    }
}