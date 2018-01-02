package com.wx.utils

import android.content.Context
import android.os.Build
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import android.widget.Toast

/**
 * Created by wx on 2017/12/20.
 *
 * 扩展类
 */

/**
 * 显示Toast
 *
 * @param Context
 * @param message
 * @param duration
 * */
fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, message, duration)
            .show()
}

/**
 * 显示snackbar
 *
 * @param view
 * @param message
 * @param duration
 * */
fun View.showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT){
    Snackbar.make(this, message, duration)
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
fun View.showSnackBarWithAction(view: View, message: String, duration: Int = Snackbar.LENGTH_SHORT,
                                actionMsg: String, listener: View.OnClickListener){
    Snackbar.make(view, message, duration)
            .setAction(actionMsg, listener)
            .show()
}

/**
 * Log
 *
 * */

fun log(tag: String = "wang", message: String){
    Log.e(tag,message)
}

/**
 * 安卓版本判断
 * @param version 版本临界
 * @return 返回是否大于传入版本
 * */

fun checkVersion(version: Int) : Boolean{
    return Build.VERSION.SDK_INT > version
}