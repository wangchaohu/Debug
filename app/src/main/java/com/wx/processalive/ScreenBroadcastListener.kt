package com.wx.processalive

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.wx.utils.log

/**
 * Created by wx on 2018/1/10.
 */
class ScreenBroadcastListener {
    private var context: Context
    private var mScreenReceiver: ScreenBroadcastReceiver
    var listener: ScreenStateListener? = null

    constructor(context: Context){
        this@ScreenBroadcastListener.context = context.applicationContext
        mScreenReceiver = ScreenBroadcastReceiver()
    }


    interface ScreenStateListener{
        fun onScreenOn()
        fun onScreenOff()
    }

    inner class ScreenBroadcastReceiver: BroadcastReceiver{
        constructor() : super()

        private var action:String? = null
        override fun onReceive(p0: Context?, p1: Intent?) {
            action = p1?.action
            if (Intent.ACTION_SCREEN_ON ==(action)){  //开屏
                listener?.onScreenOn()
            }else if (Intent.ACTION_SCREEN_OFF == action){
                listener?.onScreenOff()
            }
        }
    }

    fun registerListener(listener: ScreenStateListener){
        this@ScreenBroadcastListener.listener = listener
        registerListener()
        log(message = "registerListener")
    }

    private fun registerListener(){
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        this@ScreenBroadcastListener.context.registerReceiver(mScreenReceiver, filter)
    }
}