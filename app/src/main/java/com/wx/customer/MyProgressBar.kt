package com.wx.customer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
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
    private var mPaint: Paint   //圆环画笔
    private var paint: Paint // 圆弧画笔
    private var textPaint: Paint // 文字画笔
    private var screenHeight = 0
    private var screenWidth = 0


    companion object {
        var progress = 0f
    }
    init {
        //圆环
        mPaint = Paint()
        mPaint.strokeWidth = RING_WIDTH
        mPaint.isAntiAlias = true
        mPaint.color = Color.parseColor("#e3eaef")
        mPaint.style = Paint.Style.STROKE

        //圆弧
        paint = Paint()
        paint.color = Color.parseColor("#2725f0")
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL

        //画文字
        textPaint = Paint()
        textPaint.isAntiAlias =true
        textPaint.color = Color.WHITE
        textPaint.textSize = RADUS/2
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenHeight = h
        screenWidth = w
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

//        画圆环

        canvas?.drawCircle(screenWidth/2f, screenHeight/2f, RADUS, mPaint)
//        画中心圆

//        画圆弧
        canvas?.drawArc(screenWidth/2f-RADUS-RING_WIDTH/2, screenHeight/2f-RADUS-RING_WIDTH/2, screenWidth/2f+RADUS+RING_WIDTH/2, screenHeight/2f+RADUS+RING_WIDTH/2,
                -90f, progress * 3.6f, true, paint)

//        测了字体宽度
        val textWidth = textPaint.measureText(""+progress.toInt() + "%")
        canvas?.drawText(""+ progress.toInt() + "%", screenWidth/2f-textWidth/2, screenHeight/2f+RADUS/4-RING_WIDTH/2, textPaint)

        canvas?.save()

        postInvalidate()
    }
}