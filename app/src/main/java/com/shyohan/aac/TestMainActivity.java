package com.shyohan.aac;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.blankj.utilcode.util.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import acc.tulip.com.accreputation.R;
import com.shyohan.aac.base.BaseActivity;
import com.shyohan.aac.constant.AppConstant;
import com.shyohan.aac.google.zxing.activity.CaptureActivity;
import com.shyohan.aac.widget.DateUtil;
import com.shyohan.aac.widget.PictureUtil;

public class TestMainActivity extends BaseActivity {
    private static final int TAKE_PHOTO = 101;
    private static  final int CROP_PHOTO = 102;
    private Button btnScan;
    private TextView textView2;
    private Button btnTakephoto;
    private ImageView imageView2;

    //打开扫描界面请求码
    private int REQUEST_CODE = 0x01;
    //扫描成功返回码
    private int RESULT_OK = 0xA1;
    private Uri imageUri;

    @Override
    protected void initView() {
        btnScan = findViewById(R.id.btn_scan);
        textView2 = findViewById(R.id.textView2);
        btnTakephoto = findViewById(R.id.btn_takephoto);
        imageView2 = findViewById(R.id.imageView2);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan();
            }
        });

        //拍照
        btnTakephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
    }

    /**
     * 设置图片本地保存路径
     * @return
     */
    @NonNull
    private File setImgOutPath() {
        String imagePath = AppConstant.PARENTDIR  + "img/" ;
        File file = new File(imagePath);
        if (!file.exists()) {
            file.mkdir();
        }

        imagePath = imagePath + DateUtil.getDateFormat(new Date())+ ".jpg";
        try {
            file = new File(imagePath);
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
    private void takePhoto() {
        File file = setImgOutPath();
        imageUri = Uri.fromFile(file);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    private void scan() {
        startActivityForResult(new Intent(this, CaptureActivity.class), REQUEST_CODE);
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_scan_and_take_photo;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                Bundle extras = data.getExtras();
                if (null != extras.getString("qr_scan_result")) {
                    textView2.setText(extras.getString("qr_scan_result"));
                    ToastUtils.showShort("扫描成功");
                }
            }
        }else if (resultCode == Activity.RESULT_OK){
             if (requestCode == TAKE_PHOTO){
                // 启动裁剪程序
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(imageUri, "image/*");
                intent.putExtra("scale", true);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, CROP_PHOTO);
            }else if (requestCode == CROP_PHOTO){
                try {
                    //图片自动保存在img文件夹中
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    imageView2.setImageBitmap(bitmap);

                    //压缩图片
//                    PictureUtil.compressImage(imageUri.getPath(),AppConstant.PARENTDIR+"img/"+DateUtil.dateFormat(new Date(),"yyyyMMdd")+".jpg",40);
                    PictureUtil.compressImage(imageUri.getPath(),imageUri.getPath(),40);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    protected void setTextString() {

    }
}