package com.wx.processalive

import android.annotation.SuppressLint
import android.app.Service
import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.wx.utils.log
import android.app.ActivityManager
import com.wx.main.RecycleActivity


/**
 * Created by wx on 2018/1/10.
 *
 * 使用jobService保活失败
 */


@SuppressLint("newApi")
class JobHandlerService : JobService {
    constructor() : super()

    private var jobid = 0
    private lateinit var mJobScheduler: JobScheduler

    override fun onStopJob(p0: JobParameters?): Boolean {
        log(message = "开启进程")
//        if (!isServiceRunning(this@JobHandlerService, "com.ph.myservice.LocalService") ||
//                !isServiceRunning(this@JobHandlerService, "com.ph.myservice.RemoteService")) {
//            startService(Intent(this@JobHandlerService, LocalService::class.java))
//            startService(Intent(this@JobHandlerService, RemoteService::class.java))
//
//            log(message = "开启进程")
//        }
        startActivity(Intent(this@JobHandlerService, RecycleActivity::class.java))
        return false
    }


    // 服务是否运行
    fun isServiceRunning(context: Context, serviceName: String): Boolean {
        var isRunning = false
        val am = this
                .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val lists = am.runningAppProcesses


        for (info in lists) {// 获取运行服务再启动
            println(info.processName)
            if (info.processName == serviceName) {
                isRunning = true
            }
        }
        return isRunning

    }


    override fun onStartJob(p0: JobParameters?): Boolean {
        log(message = "开始工作")

        return false
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        scheduleJob(getJobInfo())

        return Service.START_STICKY
    }

    private fun scheduleJob(t: JobInfo) {
        mJobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        if (mJobScheduler.schedule(t) <= JobScheduler.RESULT_FAILURE) {
            log(message = "工作失败")
        } else {
            log(message = "工作成功")
        }
    }

    private fun getJobInfo(): JobInfo {
        var builder = JobInfo.Builder(jobid++,
                ComponentName(this@JobHandlerService, JobHandlerService::class.java))

        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
        builder.setPersisted(true)
        builder.setRequiresCharging(true)
        builder.setRequiresDeviceIdle(false)
        //间隔100毫秒
        builder.setPeriodic(1000)
        return builder.build()
    }

}