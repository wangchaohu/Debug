package com.wx.sendmail

import java.net.PasswordAuthentication
import javax.mail.Authenticator

/**
 * Created by wx on 2017/12/21.
 */
class MailAuthenticator: Authenticator {

    var username = ""
    var password = ""

    constructor() : super()

    constructor(username: String, password: String){
        this@MailAuthenticator.username = username
        this@MailAuthenticator.password = password
    }

    fun getPasswordAuthenticator(): PasswordAuthentication{
        return PasswordAuthentication(username,password.toCharArray())
    }
}