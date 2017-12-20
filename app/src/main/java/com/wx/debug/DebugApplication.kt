package com.wx.debug

import android.app.Activity
import android.app.Application

/**
 * Created by wx on 2017/12/18.
 */

/**
 * activity集合
 * */

class DebugApplication : Application {


    private val activityList = ArrayList<Activity>()

    constructor() : super()

    init {
        app = this
    }

    companion object {

        var app: DebugApplication? = null
        fun getInstance(): DebugApplication {
            return app!!
        }
    }

    override fun onCreate() {
        super.onCreate()
    }


    /**
     * 添加到ArrayList<Activity>
     *
     * @param activity:Activity
     * */
    fun addActivity(activity: Activity) {
        activityList.add(activity)
    }

    /**
     * 移除activity
     * * */
    fun removeActivity(activity: Activity){
        activityList.remove(activity)
    }

    /**
     * 退出所有activity
     * * */
    fun finishAllActivity() {
        for (activity in activityList) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
    }
}