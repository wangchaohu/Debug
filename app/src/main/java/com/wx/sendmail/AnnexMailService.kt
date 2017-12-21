package com.wx.sendmail

import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart
import android.R.attr.password
import android.R.attr.host
import javax.activation.CommandMap
import javax.activation.CommandMap.setDefaultCommandMap
import javax.activation.CommandMap.getDefaultCommandMap
import javax.activation.MailcapCommandMap


/**
 * Created by wangchaohu on 2017/2/17.
 *
 *
 * fun：发送邮件的主类
 */

class AnnexMailService {
    /**
     * 以文本格式发送邮件
     * @param mailInfo 待发送邮件信息
     */
    fun sendTextMail(mailInfo: MailSenderInfo): Boolean {

        var pro = mailInfo.getProperties()
        var sendMailSession: Session

        if (mailInfo.validate == "true") {
//            如果需要身份认证，则创建一个密码验证器
            var authenticator = MailAuthenticator(mailInfo.userName, mailInfo.password)!!
            sendMailSession = Session.getDefaultInstance(pro, authenticator)
        } else {

//        根据邮件会话属性和密码验证器构造一个发送邮件的session
            sendMailSession = Session.getDefaultInstance(pro)
        }

        try {
//            根据session创建一个邮件消息
            var mailMessage = MimeMessage(sendMailSession)

//            创建邮件发送着地址
            var from = InternetAddress(mailInfo.fromAddress)
//            设置邮件消息的发送者
            mailMessage.setFrom(from)
//            创建邮件的接收者地址
            var tos = arrayOfNulls<Address>(mailInfo.receivers.size)
            var receivers = mailInfo.receivers;
            if (receivers.isNotEmpty()) {
                for ( i:Int in receivers.indices){
                    tos.set(i,InternetAddress(mailInfo.receivers.get(i)))
                }
            }
//
            mailMessage.setRecipients(Message.RecipientType.TO, tos)
//            设置邮件消息的主题
            mailMessage.setSubject(mailInfo.subject)
//            设置邮件的发送时间
            mailMessage.sentDate = Date()
//            MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
            var mainPart = MimeMultipart()
//            创建一个包含html内容的MinmeBodyPart
            var html = MimeBodyPart()
//            设置html内容
            html.setContent(mailInfo.content, "text/html; charset=utf-8")
            mainPart.addBodyPart(html)
//            将MiniMultipart对象设置为邮件内容
            mailMessage.setContent(mainPart)
            //发送邮件
//            Transport.send(mailMessage)
            val mc = CommandMap.getDefaultCommandMap() as MailcapCommandMap
//            mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html")
//            mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml")
            mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain")
//            mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed")
//            mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822")
            CommandMap.setDefaultCommandMap(mc)

            val transport = sendMailSession.getTransport("smtp")
            transport.connect(mailInfo.mailServerHost, mailInfo.fromAddress, mailInfo.password)
            transport.sendMessage(mailMessage, mailMessage.getAllRecipients())
            transport.close()
            return true
        }catch (e: MessagingException){
            e.printStackTrace()
        }
        return false
    }
}