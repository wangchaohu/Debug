package com.wx.processalive

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import com.wx.debug.R
import com.wx.utils.log

/**
 * Created by wx on 2018/1/10.
 *
 * app保活(开启一个1像素，失败)
 */
class ProcessAliveActivity: Activity{
    constructor() : super()

    companion object {
        fun actionToLiveActivity(context: Context){
            var intent = Intent(context, ProcessAliveActivity.javaClass)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alive)

        val window = window
//        放在左上角
        window.setGravity(Gravity.START and Gravity.TOP)
        var attributes = window.attributes
//        宽高设计为1个像素
        attributes.width = 1
        attributes.height = 1
//        起始坐标
        attributes.x = 0
        attributes.y = 0

        window.attributes = attributes

        ScreenManager.getIntance(this@ProcessAliveActivity).setActivity(this@ProcessAliveActivity)


        Thread(Runnable {
            Thread.sleep(1000)
            log(message = "wang")
        }).start()

        log(message = "wang")
    }
}