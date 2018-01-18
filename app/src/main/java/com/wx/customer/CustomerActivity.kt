package com.wx.customer

import android.os.Bundle
import android.widget.SeekBar
import com.wx.base.BaseActivity
import com.wx.debug.R
import kotlinx.android.synthetic.main.activity_customer.*

/**
 * Created by wx on 2018/1/9.
 */
class CustomerActivity: BaseActivity,SeekBar.OnSeekBarChangeListener {
    constructor() : super()

    override fun initViews(savedInstanceState: Bundle?) {

        setContentView(R.layout.activity_customer)

        seekBar.setOnSeekBarChangeListener(this@CustomerActivity)
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        MyProgressBar.progress = p1.toFloat()
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }

    override fun onResume() {
        super.onResume()
        wheelView.startAnimation()
    }
}