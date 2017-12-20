package com.wx.customlottie;

import android.os.Bundle;

import com.wx.base.BaseActivity;
import com.wx.debug.R;


/**
 * Created by lapsen_wang on 2017/2/9/0009.
 *
 * fun：使用lottie实现了lapsen这几个字母的动画
 *
 */

public class CustomLottieActivity extends BaseActivity {

    private CustomLottieGroup footView;

    @Override
    public void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_customlottie);
        init();
    }

    private void init() {

        footView = (CustomLottieGroup) findViewById(R.id.customLottieGroup);
    }

}
