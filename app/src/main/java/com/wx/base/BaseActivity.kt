package com.wx.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.wx.debug.DebugApplication
import com.zhy.autolayout.AutoLayoutActivity

/**
 * Created by sks on 2017/1/3.
 */

abstract class BaseActivity : AutoLayoutActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews(savedInstanceState)
        addActivity(this@BaseActivity)
    }

    abstract fun initViews(savedInstanceState: Bundle?)

    /**
     * 将activity   添加到application里
     * * */
    private fun addActivity(activity: Activity){
        Log.e("wang",activity.localClassName)
        DebugApplication.getInstance().addActivity(activity)
    }

    /**
     * 启动activity
     *
     * @param context :Context
     * @param cls :Class 要跳转到activity
     * @param isFinish  是否关闭activity
     */
    fun startActivityByIntent(context: Context, cls: Class<*>, isFinish: Boolean = false) {
        startActivity(Intent(context, cls))
        if (isFinish) {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        DebugApplication.getInstance().removeActivity(this@BaseActivity)
    }
}
