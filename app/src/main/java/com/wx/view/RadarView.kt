package com.wx.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * Created by wx on 2017/12/29.
 */
class RadarView : View {
    constructor(context: Context?) : super(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val count = 6
    private var mWidth = 0f
    private var mHight = 0f
    private var mPaint: Paint
    private val angle = Math.PI * 2 / 6
    private var radius = 0    //网格最大半径


    init {
        mPaint = Paint()
        mPaint.color = Color.GRAY
        mPaint.strokeWidth = 5f
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true
        this@RadarView.setLayerType(View.LAYER_TYPE_SOFTWARE,null)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {

        mWidth = w / 2f
        mHight = h /2f
        radius = (Math.min(w, h)/2 * 0.8f).toInt()
        postInvalidate()

        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        移到屏幕中间
//        canvas?.translate(mWidth, mHight)


        drawPolygon(canvas)
        drawLine(canvas)
    }

    //    绘制正多边形
    private fun drawPolygon(canvas: Canvas?) {
        var path = Path()

        var r = radius / (count - 1)   //网格间距
        var i = 1
        while (i < count) {
            var curR = r * i
            path.reset()
            var n = 0
            while (n < count) {
                if (n == 0) {   //第一个点
                    path.moveTo((mWidth + curR).toFloat(), mHight.toFloat())
                } else {
                    var x = mWidth + curR * Math.cos(angle * n)
                    var y = mHight + curR * Math.sin(angle * n)
                    path.lineTo(x.toFloat(), y.toFloat())
                }
                n++
            }
            path.close()
            canvas!!.drawPath(path, mPaint)
            i++
        }
    }

    private fun drawLine(canvas: Canvas?){
        var path = Path()
        var i = 0
        while (i < count){
            path.reset()
            path.moveTo(mWidth, mHight)

            var x = mWidth + radius*Math.cos(angle * i)
            var y = mHight + radius*Math.sin(angle * i)

            path.lineTo(x.toFloat(),y.toFloat())
            canvas!!.drawPath(path, mPaint)
            i++
        }
    }
}