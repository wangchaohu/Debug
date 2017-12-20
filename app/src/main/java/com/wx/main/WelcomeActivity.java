package com.wx.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wx.base.BaseActivity;
import com.wx.debug.R;


/**
 * Created by xwangch on 16/8/13.
 */
public class WelcomeActivity extends BaseActivity {
    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_welcome_main);
        pause();
    }

    private void pause(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    startActivityByIntent(WelcomeActivity.this, RecycleActivity.class, true);
//                    finish();
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();
    }

}
