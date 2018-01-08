package com.wx.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.wx.debug.R

/**
 * Created by wx on 2018/1/8.
 *
 * 仿qq红点拖拽效果
 */
class RedPointView : FrameLayout {
    constructor(context: Context?) : super(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var mStartPoint: PointF
    private var mCurPoint: PointF
    private var mRadius = 0f
    private val Default_RADIUS = 50f
    private var mPaint: Paint
    private var tvPaint: Paint
    private var mPath: Path
    private var isTouch = false
    private val tipTv: TextView
    private val tipIv: ImageView
    private var isAnimStart: Boolean = false

    init {
        mStartPoint = PointF(100f, 100f)
        mCurPoint = PointF()

        mPaint = Paint()
        mPaint.color = Color.RED
        mPaint.style = Paint.Style.FILL
        mPaint.isAntiAlias = true

        tvPaint = Paint()
        tvPaint.color = Color.WHITE
        tvPaint.style = Paint.Style.STROKE
        tvPaint.isAntiAlias = true
        tvPaint.textSize = mRadius

        mPath = Path()


        var params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        tipTv = TextView(this@RedPointView.context)

        tipTv.layoutParams = params
        tipTv.text = "99+"
        tipTv.setPadding(10, 10, 10, 10)
        tipTv.setBackgroundResource(R.drawable.redpoint_tv_bg)
        tipTv.setTextColor(Color.WHITE)

//        添加imageView播放动画
        tipIv = ImageView(this@RedPointView.context)
        tipIv.layoutParams = params
        tipIv.setImageResource(R.drawable.redpoint_anim)
        tipIv.visibility = View.GONE

        addView(tipTv)
        addView(tipIv)

    }

    override fun dispatchDraw(canvas: Canvas?) {

        canvas?.saveLayer(RectF(0f, 0f, width.toFloat(), height.toFloat()), mPaint)


        if (!isTouch || isAnimStart) {
            tipTv.x = mStartPoint.x - tipTv.width / 2
            tipTv.y = mStartPoint.y - tipTv.height / 2
        } else {

            calculatePath()
            canvas?.drawCircle(mStartPoint.x, mStartPoint.y, mRadius, mPaint)
            canvas?.drawCircle(mCurPoint.x, mCurPoint.y, mRadius, mPaint)
            canvas?.drawPath(mPath, mPaint)
            tipTv.x = mCurPoint.x - tipTv.width / 2
            tipTv.y = mCurPoint.y - tipTv.height / 2
        }

        canvas?.restore()

        super.dispatchDraw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_UP -> {
                isTouch = false

            }
            MotionEvent.ACTION_DOWN -> {

                if (isInCircle(event)) {
                    isTouch = true
                }
            }

        }
        mCurPoint.x = event!!.x
        mCurPoint.y = event.y
        postInvalidate()

        return true
    }

    /**
     * 计算路径
     * */

    private fun calculatePath() {
        var x = mCurPoint.x
        var y = mCurPoint.y
        var startX = mStartPoint.x
        var startY = mStartPoint.y

//        得出四边形的四个点
        var dx = x - startX
        var dy = y - startY
        var a = Math.atan((dy / dx).toDouble())
        var offsetX = mRadius * Math.sin(a)
        var offsetY = mRadius * Math.cos(a)

        var distance = Math.sqrt(Math.pow((y - startY).toDouble(), 2.00) + Math.pow((x - startX).toDouble(), 2.00))

        mRadius = (Default_RADIUS - distance / 15f).toFloat()

        if (mRadius < 9) {
            isAnimStart = true

            tipIv.x = mCurPoint.x - tipTv.width / 2
            tipIv.y = mCurPoint.y - tipTv.height / 2
            tipIv.visibility = View.VISIBLE
            (tipIv.drawable as AnimationDrawable).start()
            tipTv.visibility = View.GONE
        }

//        根据角度苏阿初四边形的四个点
        var x1 = startX - offsetX
        var y1 = startY + offsetY

        var x2 = x - offsetX
        var y2 = y + offsetY

        var x3 = x + offsetX
        var y3 = y - offsetY

        var x4 = startX + offsetX
        var y4 = startY - offsetY

        var anchorX = (startX + x) / 2
        var anchorY = (startY + y) / 2

        mPath.reset()
        mPath.moveTo(x1.toFloat(), y1.toFloat())
        mPath.quadTo(anchorX, anchorY, x2.toFloat(), y2.toFloat())
        mPath.lineTo(x3.toFloat(), y3.toFloat())
        mPath.quadTo(anchorX, anchorY, x4.toFloat(), y4.toFloat())
        mPath.lineTo(x1.toFloat(), y1.toFloat())
    }

    /**
     * 计算点击点是否在圆内
     *
     * */
    private fun isInCircle(event: MotionEvent?): Boolean {
        val rect = Rect()
        var location = IntArray(2)

        tipTv.getLocationOnScreen(location)
        rect.left = location[0]
        rect.right = location[1]
        rect.top = tipTv.width + location[0]
        rect.bottom = tipTv.height + location[1]

        if (rect.contains(event!!.rawX.toInt(), event!!.rawY.toInt())) {
            return true
        }
        return false
    }
}