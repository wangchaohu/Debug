package com.wx.sendmail;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.wx.base.BaseActivity;
import com.wx.debug.R;


/**
 * Created by wangchaohu on 2017/2/17.
 * <p>
 * <p>
 * fun：利用java mail 实现android端发送邮件
 */

public class SendEmailActivity extends BaseActivity {

    private EditText editText;

    @Override
    public void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sendemail);

        editText = (EditText) findViewById(R.id.getEmail);
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendMessagge("这是一个测试","546509279@qq.com","839461699@qq.com");
            }
        });
    }

    private void sendMessagge(final String msg,final String...toAddress){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MailSenderInfo mailSenderInfo = new MailSenderInfo();
                mailSenderInfo.setMailServerHost("smtp.mxhichina.com");
                mailSenderInfo.setMailServerPort("25");
                mailSenderInfo.setValidate("false");
                mailSenderInfo.setUserName("wangch@wangch.top");
                mailSenderInfo.setPassword("186wch.hx7797");
                mailSenderInfo.setFromAddress("wangch@wangch.top");
                mailSenderInfo.setReceivers(toAddress);
                mailSenderInfo.setSubject("这是主题");
                mailSenderInfo.setContent(msg);

//                这个类主要来发送邮件
                AnnexMailService annexMailService = new AnnexMailService();
                boolean isSuccess = annexMailService.sendTextMail(mailSenderInfo);
//                annexMailService.sendHtmlMail(mailSenderInfo);  //发送html格式
            }
        }).start();
    }
}
