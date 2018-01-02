package com.wx.qrcode

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import com.wx.base.BaseActivity
import com.wx.debug.R

import kotlinx.android.synthetic.main.activity_qrcode.*
import java.io.File
import com.wx.utils.toast

/**
 * Created by wx on 2018/1/2.
 *
 * 多码合一支付
 */
class QRCodeActivity : BaseActivity {
    constructor() : super()

    override fun initViews(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_qrcode)

        create.setOnClickListener{
            createQR()
        }

        read.setOnClickListener({
            readQR()
        })
    }

    /**
     * 生成二维码
     * */
    private fun createQR(){
        var filePath = getFileRoot(this@QRCodeActivity)+ File.separator + "qr_" + System.currentTimeMillis() + ".jpg"

        Thread(Runnable {
            var success = QRCreate.createQRImage("HTTPS://QR.ALIPAY.COM/FKX00116NUF4V3KRJ9RDEC", 800, 800, BitmapFactory.decodeResource(resources, R.mipmap.debug), filePath)

            if (success){
                runOnUiThread {
                    qrView.setImageBitmap(BitmapFactory.decodeFile(filePath))
                }
            }else{
                toast("二维码生成失败")
            }
        }).start()
    }

    /**
     * 扫描二维码
     * */
    private fun readQR(){

    }

    private fun getFileRoot(context: Context) : String{
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            var external = context.getExternalFilesDir(null)
            if (external != null){
                return external.absolutePath
            }
        }
        return context.filesDir.absolutePath
    }
}