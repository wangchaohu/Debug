package com.wx.customer

import android.os.Bundle
import com.wx.base.BaseActivity
import com.wx.debug.R

/**
 * Created by wx on 2018/1/9.
 */
class CustomerActivity: BaseActivity {
    constructor() : super()


    override fun initViews(savedInstanceState: Bundle?) {

        setContentView(R.layout.activity_customer)
    }
}