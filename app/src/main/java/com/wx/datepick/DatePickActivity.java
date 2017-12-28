package com.wx.datepick;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.wx.base.BaseActivity;
import com.wx.debug.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by liuwan on 2016/9/28.
 */
public class DatePickActivity extends BaseActivity implements View.OnClickListener {

    private CustomerDatePicker customDatePicker2;

    private TextView currentTime;
    private CheckBox loop, date, gravity;

    @Override
    public void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_datepick);
        currentTime = findViewById(R.id.curTime);
        loop = findViewById(R.id.loop);
        date = findViewById(R.id.date);
        gravity = findViewById(R.id.gravity);
        currentTime.setOnClickListener(this);
        initDatePicker();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.curTime:

                customDatePicker2 = new CustomerDatePicker(this, new CustomerDatePicker.ResultHandler() {
                    @Override
                    public void handle(String time) { // 回调接口，获得选中的时间
                        currentTime.setText(time);
                    }
                }, "2017-11-11 11:11", "2099-01-01 00:00", !gravity.isChecked()); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
                if (!date.isChecked()) {
                    customDatePicker2.showSpecificTime(false); // 显示时和分
                }
                if (loop.isChecked()) {
                    customDatePicker2.setIsLoop(false); // 允许循环滚动
                }
            // 日期格式为yyyy-MM-dd HH:mm
            customDatePicker2.show(currentTime.getText().toString());
            break;
        }
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        Log.e("wang",(new Date()).toString());
        currentTime.setText(now);


    }

}
