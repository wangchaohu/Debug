package com.wx.customer

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.LinearInterpolator
import com.wx.utils.log

/**
 * Created by wx on 2018/1/17.
 */
class WheelView: View {
    constructor(context: Context?) : super(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var mPaint = Paint()
    private var centerX = 0f
    private var centerY = 0f
    private val RADUS = 300f
    var dx = 0f

    init {
        mPaint.strokeWidth = 2f
        mPaint.isAntiAlias = true
        mPaint.color = Color.RED
        mPaint.style = Paint.Style.STROKE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = RADUS
        centerY = h / 2f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawCircle(centerX, centerY, RADUS-Math.abs(dx)-10, mPaint)
        mPaint.color = Color.BLUE
        canvas?.drawCircle(centerX, centerY, RADUS -Math.abs(dx)-20, mPaint)

        mPaint.color = Color.GREEN
        canvas?.drawCircle(centerX, centerY, RADUS -Math.abs(dx)-30, mPaint)

        mPaint.color = Color.CYAN
        canvas?.drawCircle(centerX, centerY, RADUS -Math.abs(dx)-40, mPaint)

        mPaint.color = Color.MAGENTA
        canvas?.drawCircle(centerX, centerY, RADUS -Math.abs(dx)-50, mPaint)

        mPaint.color = Color.YELLOW
        canvas?.drawCircle(centerX, centerY, RADUS -Math.abs(dx)-60, mPaint)

        mPaint.color = Color.GRAY
        canvas?.drawCircle(centerX, centerY, RADUS -Math.abs(dx)-70, mPaint)

    }

    fun startAnimation() {
        var animator1 = ValueAnimator.ofInt(-RADUS.toInt(), RADUS.toInt(), 0)

        animator1.duration = 3000
        animator1.interpolator = LinearInterpolator()
        animator1.repeatMode = ValueAnimator.REVERSE
        animator1.repeatCount = -1


        animator1.addUpdateListener(ValueAnimator.AnimatorUpdateListener { valueAnimator: ValueAnimator? ->
            dx = (valueAnimator?.animatedValue).toString().toFloat()

            centerX+=1
            mPaint.color = Color.rgb((Math.abs(dx) * 0.3).toInt(), (Math.abs(dx) * 0.5).toInt(), (Math.abs(dx) * 0.8).toInt())
            postInvalidate()
        })
        animator1.start()

    }
}