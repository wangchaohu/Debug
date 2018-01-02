package com.wx.view

import android.os.Bundle
import com.wx.base.BaseActivity
import com.wx.debug.R

/**
 * Created by wx on 2017/12/29.
 */
class CustomViewActivity : BaseActivity{
    constructor() : super()

    override fun initViews(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_customview)
    }
}