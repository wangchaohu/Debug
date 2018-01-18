package com.wx.customer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.View
import com.wx.utils.log
import java.sql.Time
import java.util.*

/**
 * Created by wx on 2018/1/12.
 */
class HoverBallView : View {
    constructor(context: Context?) : super(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    private var centerX = 0f
    private var centerY = 0f
    private val RADUS = 300f

    private var mPaint: Paint
    private var wavePaint: Paint
    private var mPath: Path
    private var progress = 0
    private var isStart = false

    init {
        mPaint = Paint()
        mPaint.color = Color.BLUE
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true
        mPaint.textSize = RADUS / 2

        wavePaint = Paint()
        wavePaint.color = Color.GREEN
        wavePaint.style = Paint.Style.FILL
        wavePaint.isAntiAlias = true
        wavePaint.strokeWidth = 2f
        mPath = Path()

        isStart = false
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        centerX = w / 2f
        centerY = h / 2f

    }

    override fun onDraw(canvas: Canvas?) {



        super.onDraw(canvas)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)


        mPath.reset()
        mPath.addCircle(centerX, centerY, RADUS, Path.Direction.CCW)

        canvas?.clipPath(mPath)
//        canvas?.drawCircle(centerX, centerY, RADUS, mPaint)
        mPath.reset()
        mPath.moveTo(centerX - RADUS * 3 + progress, centerY + RADUS - progress)
        mPath.rQuadTo(RADUS / 2, -RADUS / 4, RADUS, 0f)
        mPath.rQuadTo(RADUS / 2, RADUS / 4, RADUS, 0f)
        mPath.rQuadTo(RADUS / 2, -RADUS / 4, RADUS, 0f)
        mPath.rQuadTo(RADUS / 2, RADUS / 4, RADUS, 0f)

        mPath.lineTo(centerX + RADUS, centerY + RADUS)
        mPath.lineTo(centerX - RADUS, centerY + RADUS)
        mPath.close()
        canvas?.clipPath(mPath)
        canvas?.drawPath(mPath, wavePaint)

        val textWidth = mPaint.measureText("" + (100 / RADUS * progress).toInt() + "%")
        canvas?.drawText("" + (100 / (RADUS * 2) * progress).toInt() + "%", centerX - textWidth / 2, centerY + RADUS / 4, mPaint)


        Timer().schedule(object : TimerTask() {
            override fun run() {
                if (progress > RADUS * 2) {
                    progress = 0
                }
                progress += 10
                postInvalidate()

            }
        }, 100)
    }
}