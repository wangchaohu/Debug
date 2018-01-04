package com.wx.main;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.wx.base.BaseActivity;
import com.wx.customlottie.CustomLottieActivity;
import com.wx.datepick.DatePickActivity;
import com.wx.debug.R;
import com.wx.lottie.LottieActivity;
import com.wx.qrcode.QRCodeActivity;
import com.wx.sendmail.SendEmailActivity;
import com.wx.utils.UtilsKt;
import com.wx.view.CustomViewActivity;
import com.wx.viewdrag.ViewDragActivity;
import com.wx.write.WriteActivity;

import java.util.Arrays;
import java.util.List;

import static com.wx.utils.UtilsKt.checkVersion;


/**
 * Created by xwangch on 16/8/2.
 * <p>
 * fun：主界面
 * <p>
 * 可以根据数组长度的大小自动添加按钮，实现跳转
 */
public class RecycleActivity extends BaseActivity implements View.OnClickListener{

    private List<String> chwang_s = Arrays.asList("Lottie简单使用", "Lottie自定义动画", "ViewDrag使用", "读写文件", "发送邮件", "日期时间选择", "自定义view",
            "生成二维码");
    private List chwang_c = Arrays.asList(LottieActivity.class, CustomLottieActivity.class, ViewDragActivity.class, WriteActivity.class,
            SendEmailActivity.class, DatePickActivity.class, CustomViewActivity.class, QRCodeActivity.class);

    private SoundPool soundPool;
    private int soundId;  //创建某个声音对应的音频id

    private RecyclerView recyclerView;
    private ImageButton hoverButton, hoverButton1, hoverButton2 ,hoverButton3, hoverButton4, hoverButton5;
    private int radius = 300;  //半径
    private int count = 5;  //总数

    @Override
    public void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_recycleview_main);
        initSound();
        init();

    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void init(){
        recyclerView = (RecyclerView) findViewById(R.id.recycle);

        hoverButton = (ImageButton) findViewById(R.id.hoverButton);
        hoverButton1 = (ImageButton) findViewById(R.id.hoverButton1);
        hoverButton2 = (ImageButton) findViewById(R.id.hoverButton2);
        hoverButton3 = (ImageButton) findViewById(R.id.hoverButton3);
        hoverButton4 = (ImageButton) findViewById(R.id.hoverButton4);
        hoverButton5 = (ImageButton) findViewById(R.id.hoverButton5);

        hoverButton.setOnClickListener(this);
        hoverButton1.setOnClickListener(this);
        hoverButton2.setOnClickListener(this);
        hoverButton3.setOnClickListener(this);
        hoverButton4.setOnClickListener(this);
        hoverButton5.setOnClickListener(this);
    }

    private void initData(){
        if (null != recyclerView) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new MyRecycleViewAdapter(this));
        }
    }

    class MyRecycleViewAdapter extends RecyclerView.Adapter<MyViewHolder> {
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
                    playSound();
                    startActivityByIntent(RecycleActivity.this, (Class) chwang_c.get(position), false);
                }
            });
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private Button btn_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            btn_item = (Button) itemView.findViewById(R.id.button);
        }

        public Button getBtn() {
            return btn_item;
        }
    }

    /**
     * 初始化音效
     */

    private void initSound() {
        if (checkVersion(Build.VERSION_CODES.LOLLIPOP)){
            soundPool = new SoundPool.Builder().build();
        }else {
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }

        soundId = soundPool.load(this, R.raw.music, 1);
    }

    private void playSound() {
        soundPool.play(soundId,
                0.1f,   //左耳道音量
                0.5f,  //右耳道音量
                0,    //播放优先级，0表示最低优先级
                0,   //循环模式，0表示循环一次，-1表示一直循环，，+1表示当前循环次数
                1   //播放速度，1是正常，范围0-2
        );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.hoverButton:
                openMenuAnimator();
                break;
            case R.id.hoverButton1:
                UtilsKt.toast(this, "点击了hoverButton1", Toast.LENGTH_SHORT);
                break;
            case R.id.hoverButton2:
                UtilsKt.toast(this, "点击了hoverButton2", Toast.LENGTH_SHORT);
                break;
            case R.id.hoverButton3:
                UtilsKt.toast(this, "点击了hoverButton3", Toast.LENGTH_SHORT);
                break;
            case R.id.hoverButton4:
                UtilsKt.toast(this, "点击了hoverButton4", Toast.LENGTH_SHORT);
                break;
            case R.id.hoverButton5:
                closeMenuAnimator();
                break;
        }
    }

    /**
     * 菜单按钮打开
     * */

    private void openMenuAnimator(){

//       按钮1动画
        openAnimator(hoverButton1, 0);
//       按钮2动画
        openAnimator(hoverButton2, 1);
//       按钮3动画
        openAnimator(hoverButton3, 2);
//       按钮4动画
        openAnimator(hoverButton4, 3);
//       按钮5动画
        openAnimator(hoverButton5, 4);

    }
    /**
     * 菜单按钮关闭
     * */

    private void closeMenuAnimator(){
        closeAnimator(hoverButton1, 0);
        closeAnimator(hoverButton2, 1);
        closeAnimator(hoverButton3, 2);
        closeAnimator(hoverButton4, 3);
        closeAnimator(hoverButton5, 4);
    }

    /**
     * 打开动画
     * */
    private void openAnimator(View view, int index){

        if (view.getVisibility() != View.VISIBLE){
            view.setVisibility(View.VISIBLE);
        }

        double angle = Math.toRadians(90)/(count-1) * (index);
        float x = -(float) ( Math.sin(angle) * radius);
        float y = -(float) Math.cos(angle) * radius;


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0, x),
                ObjectAnimator.ofFloat(view, "translationY", 0, y),
                ObjectAnimator.ofFloat(view, "alpha", 0, 1f),
                ObjectAnimator.ofFloat(view, "scaleX", 0, 1f),
                ObjectAnimator.ofFloat(view, "scaleY", 0, 1f)
        );
        animatorSet.setDuration(500).start();
    }

    /**
     * 关闭动画
     * */
    private void closeAnimator(final View view, int index){
        if (view.getVisibility() != View.VISIBLE){
            view.setVisibility(View.VISIBLE);
        }

        double angle = Math.PI/2 /(count-1) * index;
        float x = -(float) ( Math.sin(angle) * radius);
        float y = -(float) Math.cos(angle) * radius;

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", x, 0f),
                ObjectAnimator.ofFloat(view, "translationY", y, 0f),
                ObjectAnimator.ofFloat(view, "alpha", 1f, 0f),
                ObjectAnimator.ofFloat(view, "scaleX", 1, 0.1f),  //当缩小为0时，不会实际改变控件位置
                ObjectAnimator.ofFloat(view, "scaleY", 1, 0.1f)  //改为0.1这样防止关闭之后，原有位置还响应(方法一)
        );

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
//                方案二，或将控件放大
                if (view.getVisibility() != View.GONE){
                    view.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        animatorSet.setDuration(500).start();

    }
}
