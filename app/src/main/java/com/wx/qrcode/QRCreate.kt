package com.wx.qrcode

import android.graphics.Bitmap
import android.graphics.Canvas
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.io.FileOutputStream
import java.util.*

/**
 * Created by wx on 2018/1/2.
 */
class QRCreate {
    companion object {

        /**
         * 生成二维码
         *
         * @param content  内容
         * @param width  宽度
         * @param height  高度
         * @param logo  二维码中心logo图标
         * @param filePath  存储二维码图片的路径
         *
         * @return 生成二维码及保存文件是否成功
         * */
        fun createQRImage(content: String, width: Int, height :Int, logo: Bitmap?, filePath: String) : Boolean{
            try {
                if (content.isNullOrEmpty() ){
                    return false
                }

//                配置参数
                var hints = HashMap<EncodeHintType, Any>()
                hints.put(EncodeHintType.CHARACTER_SET, "utf-8")
//                容错级别
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H)
//                设置空白边距的宽度
                hints.put(EncodeHintType.MARGIN, 2)   //默认是4
//                 图像数据转换，使用了矩阵转换
                var bitMatrix = QRCodeWriter().encode(content,BarcodeFormat.QR_CODE, width, height, hints)
                var pixels = IntArray(width * height)

//                下面按照二维码的算法，逐个生成二维码图片

                for (y in 0 until height){

                    for (x in 0 until  width){
                        if (bitMatrix.get(x,y)){
                            pixels[y * width + x] = (0xff000000).toInt()
                        }else{
                            pixels[y * width + x] = (0xffffffff) .toInt()
                        }
                    }
                }

//                生成二维码的图片的格式，使用argb——8888
                var bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
                if (logo != null){
                    bitmap = addLogo(bitmap, logo)
                }
//                必须使用compress方法将bitmap保存到文件中再进行读取，直接返回的bitmap是没有任何压缩的，内存消耗极大

                return bitmap != null && bitmap.compress(Bitmap.CompressFormat.JPEG, 100, FileOutputStream(filePath))
            }catch (e: WriterException){
                e.printStackTrace()
            }
            return false
        }


        /**
         * 在二维码中间添加logo
         * */
        fun addLogo(src : Bitmap?, logo: Bitmap?) : Bitmap?{
            if (src == null){
                return null
            }

            if (logo == null){
                return src
            }

//        获取图片的宽高
            var srcWidth = src.width
            var srcHeight = src.height
            var logoWidth = logo.width
            var logoHeight = logo.height

            if ((srcWidth == 0 )or (srcHeight == 0)){
                return null
            }

            if ((logoWidth == 0) or (logoHeight == 0)){
                return null
            }

//        logo大小为二维码整体的五分之一
            var scaleFactor = srcWidth * 1.0f / 5 / logoWidth
            var bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888)

            try {
                var canvas = Canvas(bitmap)
                canvas.drawBitmap(src, 0f, 0f, null)

                canvas.scale(scaleFactor, scaleFactor, srcWidth /2f, srcHeight /2f)

                canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2f, (srcHeight - logoHeight) / 2f, null)

                canvas.save(Canvas.ALL_SAVE_FLAG)
                canvas.restore()
            }catch (e : Exception){
                e.printStackTrace()
            }
            return bitmap
        }
    }




}