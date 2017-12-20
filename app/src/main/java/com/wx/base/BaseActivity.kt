package com.wx.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.wx.debug.DebugApplication

/**
 * Created by sks on 2017/1/3.
 */

abstract class BaseActivity : AppCompatActivity() {

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

    /**
     * 显示snackbar
     *
     * @param view
     * @param message
     * @param duration
     * */
    fun showSnackBar(view: View, message: String, duration: Int = Snackbar.LENGTH_SHORT){
        Snackbar.make(view, message, duration)
                .show()
    }

    /**
     * 显示带有action的snackbar
     *
     *@param view
     * @param message
     * @param duration
     * @param actionMsg
     * @param listener
     * */
    fun showSnackBarWithAction(view: View, message: String, duration: Int = Snackbar.LENGTH_SHORT,
                               actionMsg: String, listener: View.OnClickListener){
        Snackbar.make(view, message, duration)
                .setAction(actionMsg, listener)
                .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        DebugApplication.getInstance().removeActivity(this@BaseActivity)
    }
}
