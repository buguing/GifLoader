package com.wellee.gifloader;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import java.io.File;
import java.security.Permission;
import java.security.Permissions;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;
    private String path;
    ImageView imageView;
    private GifHandle gifHandle;
    private Bitmap bitmap;
    private int maxLength;
    private int currentLength;
    private ImageView imageViewGlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageViewGlide = findViewById(R.id.iv0);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "hh.gif";
            this.imageView = findViewById(R.id.iv);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "hh.gif";
                imageView = findViewById(R.id.iv);
            }
        }
    }

    public void load(View view) {
        Glide.with(this)
                .load(path)
                .into(imageViewGlide);
        gifHandle = new GifHandle(path);
        int width = gifHandle.getWidth();
        int height = gifHandle.getHeight();
        maxLength = gifHandle.getLength();
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // 渲染
        long delayTime = gifHandle.renderFrame(bitmap, currentLength);
        imageView.setImageBitmap(bitmap);
        if (handler != null) {
            handler.sendEmptyMessageDelayed(1, delayTime);
        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            currentLength++;
            if (currentLength >= maxLength) {
                currentLength = 0;
            }
            long delayTime = gifHandle.renderFrame(bitmap, currentLength);
            imageView.setImageBitmap(bitmap);
            handler.sendEmptyMessageDelayed(1, delayTime);
            return false;
        }
    });

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }
}
