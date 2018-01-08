package com.wx.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import com.wx.utils.log

/**
 * Created by wx on 2018/1/5.
 */
class MyView : View {
    constructor(context: Context?) : super(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var mPath = Path()
    private var wavePath = Path()
    private var mPaint = Paint()
    private var wavePaint = Paint()
    private var mPreX = 0f
    private var mPreY = 0f
    private var itemWaveLength = 1000f  //要大于初始波浪的高度
    private var dx = 0f

    private var screenHeitht = 0
    private var screenWidth = 0

    init {
        mPaint.isAntiAlias = true
        mPaint.color = Color.MAGENTA
        mPaint.strokeWidth = 5f
        mPaint.style = Paint.Style.STROKE

        wavePaint.color = Color.BLUE
        wavePaint.style = Paint.Style.FILL_AND_STROKE
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        wave(canvas)

//        手写效果
//        canvas?.drawPath(mPath, mPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenHeitht = h
        screenWidth = w
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                mPath.moveTo(event.x, event.y)
                mPreX = event.x
                mPreY = event.y
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                var endX = (mPreX + event.x) / 2f
                var endY = (mPreY + event.y) / 2f

                mPath.quadTo(mPreX, mPreY, endX, endY)

                mPreX = event.x
                mPreY = event.y
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                mPath.reset()
                invalidate()
            }
        }

        return super.onTouchEvent(event)
    }

    private fun wave(canvas: Canvas?) {

        wavePath.reset()
        var halfWaveLen = itemWaveLength / 2.0f

            wavePath.moveTo(-halfWaveLen + dx, screenHeitht / 2f + dx)
        var i = -itemWaveLength
        while (i <= (itemWaveLength + screenWidth)) {

            wavePath.rQuadTo(halfWaveLen / 2.0f, -100f, halfWaveLen, 0f)
            wavePath.rQuadTo(halfWaveLen / 2.0f, 100f, halfWaveLen, 0f)
            i += itemWaveLength

        }

        wavePath.lineTo(screenWidth.toFloat(), screenHeitht.toFloat())
        wavePath.lineTo(0f, screenHeitht.toFloat())
        wavePath.close()

        canvas?.drawPath(wavePath, wavePaint)
    }

    fun startAnimator() {
        var animator = ValueAnimator.ofFloat(0f, itemWaveLength/2f)

        animator.duration = 2000
        animator.interpolator = LinearInterpolator()
        animator.repeatCount = ValueAnimator.INFINITE

        animator.addUpdateListener(ValueAnimator.AnimatorUpdateListener { valueAnimator: ValueAnimator? ->
            dx = (valueAnimator?.animatedValue).toString().toFloat()
            postInvalidate()
        })

        animator.start()
    }
}