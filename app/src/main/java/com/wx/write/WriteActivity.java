package com.wx.write;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;

import com.wx.base.BaseActivity;
import com.wx.debug.R;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * Created by lapsen_wang on 2017/2/17/0017.
 *
 *
 * 向android手机sd卡中写入文件，与读取文件
 */

public class WriteActivity extends BaseActivity {
    private EditText et;

    @Override
    public void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_write);
        et = (EditText) findViewById(R.id.edit_text);
        findViewById(R.id.btn_write).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeTxtToFile(et.getText().toString());
            }
        });
    }

    /**
     *
     * 获取默认的文件路径
     *
     */

    // 将字符串写入到文本文件中
    public void writeTxtToFile(String strcontent) {
        String filePath = Environment.getExternalStorageDirectory() + "/myAccount";
        String fileName = "/ceshi.json";
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);

        String strFilePath = filePath+fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
        }
    }
    // 生成文件
    public File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
        }
    }
}

