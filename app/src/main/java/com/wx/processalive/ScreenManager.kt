package com.wx.processalive

import android.app.Activity
import android.content.Context
import com.wx.utils.log
import java.lang.ref.WeakReference

/**
 * Created by wx on 2018/1/10.
 */
class ScreenManager {
    private lateinit var context:Context
    private lateinit var mActivityWref: WeakReference<Activity>

    companion object {
        var gDefualt: ScreenManager? = null

        fun getIntance(context: Context): ScreenManager{
            if (gDefualt == null){
                synchronized(ScreenManager::class.java){
                    if (gDefualt == null){
                        gDefualt = ScreenManager(context)
                    }
                }
            }
            return gDefualt!!
        }
    }

    private constructor(context: Context){
        this@ScreenManager.context = context
    }

    fun setActivity(activity: Activity){
        mActivityWref = WeakReference<Activity>(activity)
    }

    fun startActivity(){
        ProcessAliveActivity.actionToLiveActivity(this@ScreenManager.context)

log(message = "wang")
    }

    fun finishActivity(){
        val activity = mActivityWref.get()
        if (activity != null){
            activity?.finish()
        }
    }
}