package com.wx.sendmail

import java.util.*


/**
 * Created by wx on 2017/12/21.
 *
 * 发件人信息实体类
 * @property mailServerHost  发送邮件的服务器的IP
 * @property mailServerPort   发送邮件的服务器的端口
 * @property fromAddress   邮件发送者地址
 * @property toAddress   邮件接受者地址
 * @property userName  登陆邮件发送服务器的用户名
 * @property password   密码
 * @property validate   是否需要身份验证
 * @property subject   邮件主题
 * @property content   邮件内容
 * @property receivers 群发邮件收件地址列表
 * @property ccs 抄送收件人
 * @property attachFileNames   邮件附件的文件名
 */
class MailSenderInfo {

    var mailServerHost = ""
    var mailServerPort = "25"
    var fromAddress = ""
    var userName = ""
    var password = ""
    var validate = "false"
    var subject = ""
    var content = ""
    var receivers = ""
    var attachFileNames = emptySet<String>()
    var ccs = ""

    /**
     * 获取邮件会话属性
     * */

    fun getProperties(): Properties{
        var props =  Properties()
        props.put("mail.smtp.host", mailServerHost)
        props.put("mail.smtp.port", mailServerPort)
        props.put("mail.smtp.auth", validate)
        props.put("mail.smtp.starttls.enable", "true")
        props.put("mail.smtp.user", fromAddress)
        props.put("mail.smtp.password", password)
        props.put("mail.smtp.auth", "true")
        return props
    }
}