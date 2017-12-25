package com.wx.sendmail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wx.base.BaseActivity;
import com.wx.debug.R;

import java.io.File;
import java.util.HashSet;


/**
 * Created by wangchaohu on 2017/2/17.
 * <p>
 * <p>
 * fun：利用java mail 实现android端发送邮件
 */

public class SendEmailActivity extends BaseActivity {

    private EditText editText, editText2;
    private String receEmail, ccEmail, filePath;
    private HashSet<String> files;

    @Override
    public void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sendemail);

        editText = (EditText) findViewById(R.id.getEmail);
        editText2 = (EditText) findViewById(R.id.ccEmail);

        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                receEmail = editText.getText().toString();
                ccEmail = editText2.getText().toString();
                sendMessagge("你好，圣诞", "839461699@qq.com", "546509279@qq.com");
            }
        });

        findViewById(R.id.addAttachment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                files = new HashSet<String>();
                openCurDir();
            }
        });

        findViewById(R.id.attachmentSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendAttachment("附件", "839461699@qq.com", "546509279@qq.com");
            }
        });

    }

    private void sendMessagge(final String msg, final String toAddress, final String ccEmail) {
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
                mailSenderInfo.setCcs(ccEmail);
                mailSenderInfo.setSubject("这是主题");
                mailSenderInfo.setContent(msg);
//                这个类主要来发送邮件f
                AnnexMailService annexMailService = new AnnexMailService();
                boolean isSuccess = annexMailService.sendTextMail(mailSenderInfo);
//                annexMailService.sendHtmlMail(mailSenderInfo);  //发送html格式
            }
        }).start();
    }

    private void sendAttachment(final String msg, final String toAddress, final String ccEmail) {
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
                mailSenderInfo.setCcs(ccEmail);
                mailSenderInfo.setSubject("这是主题");
                mailSenderInfo.setContent(msg);
                mailSenderInfo.setAttachFileNames(files);
//                这个类主要来发送邮件f
                AnnexMailService annexMailService = new AnnexMailService();
                boolean isSuccess = annexMailService.sendTextMail(mailSenderInfo);
//                annexMailService.sendHtmlMail(mailSenderInfo);  //发送html格式
            }
        }).start();
    }

    private void openCurDir() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");  //设置类型
//        intent.setType(“image/*”);
//intent.setType(“audio/*”); //选择音频
//intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
//intent.setType(“video/*;image/*”);//同时选择视频和图片
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {  //是否选择，没选择就不会继续
            Uri uri = data.getData();
            if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
                filePath = uri.getPath();
                return;
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                filePath = getPath(this, uri);
            } else {//4.4以下下系统调用方法
                filePath = getRealPathFromURI(uri);
            }
            files.add(filePath);
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (null != cursor && cursor.moveToFirst()) {
            ;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null) cursor.close();

        }
        return null;

    }

    /**
     * @param uri The Uri to check.
     */

    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */

    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());

    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */

    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());

    }

}
