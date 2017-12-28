package com.wx.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wx.base.BaseActivity;
import com.wx.customlottie.CustomLottieActivity;
import com.wx.datepick.DatePickActivity;
import com.wx.debug.R;
import com.wx.lottie.LottieActivity;
import com.wx.sendmail.SendEmailActivity;
import com.wx.viewdrag.ViewDragActivity;
import com.wx.write.WriteActivity;

import java.util.Arrays;
import java.util.List;


/**
 * Created by xwangch on 16/8/2.
 *
 * fun：主界面
 *
 * 可以根据数组长度的大小自动添加按钮，实现跳转
 */
public class RecycleActivity extends BaseActivity {

    private List<String> chwang_s = Arrays.asList("Lottie简单使用","Lottie自定义动画","ViewDrag使用","读写文件","发送邮件","日期时间选择");
    private List chwang_c = Arrays.asList(LottieActivity.class, CustomLottieActivity.class, ViewDragActivity.class, WriteActivity.class,
            SendEmailActivity.class, DatePickActivity.class);

    @Override
    public void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_recycleview_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyRecycleViewAdapter(this));
    }

    class MyRecycleViewAdapter extends RecyclerView.Adapter<MyViewHolder>{
        /**
         * 如果想要添加一个按钮，则在contentData中添加一个按钮名称即可，
         * 以及在skipClass中添加一个要跳转的类名
         */
        private Activity activity;

        public MyRecycleViewAdapter(Activity activity) {
            this.activity = activity;
        }

        @Override
        public int getItemCount() {
            return chwang_s.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(RecycleActivity.this).inflate(R.layout.layout_item_recycleview_main, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {

            holder.getBtn().setText(chwang_s.get(position));
             holder.getBtn().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityByIntent(RecycleActivity.this, (Class) chwang_c.get(position), false);
                }
            });
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private Button btn_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            btn_item = (Button)itemView.findViewById(R.id.button);
        }
        public Button getBtn() {
            return btn_item;
        }
    }
}
