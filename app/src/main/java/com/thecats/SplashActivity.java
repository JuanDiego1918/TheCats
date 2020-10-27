package com.thecats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.DataBase.VotesBO;
import com.Util.Const;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

public class SplashActivity extends AppCompatActivity {
    public static final String TAG = SplashActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        init();
    }

    private void init() {
        ImageView imageView = (ImageView) findViewById(R.id.splash);
        Glide.with(this).load(R.drawable.splash)
                .into(imageView);
        checkPermission();
    }

    public void cargarSplash(){
        Handler handler = new Handler();
        handler.postDelayed(getRunnableStartApp(), 2 * 1000);
    }

    private void checkPermission() {
        Log.i(TAG, "checkPermission: 1");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //permisos almacenamiento
            checkPermissionStorage();
            Log.i(TAG, "checkPermission: 2");
        }else{
            cargarSplash();
        }
    }

    private void checkPermissionStorage() {
        Log.i(TAG, "checkPermission: 4");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Const.RESP_PERMISOS_STORAGE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Const.RESP_PERMISOS_STORAGE);
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Const.RESP_PERMISOS_STORAGE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Const.RESP_PERMISOS_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.e(TAG, "grantResults.length  -> " + grantResults.length);
        //Log.e(TAG, "grantResults.length  -> " + grantResults[0] );
        switch (requestCode) {
            case Const.RESP_PERMISOS_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "checkPermission: 5");
                    Log.e(TAG, "onRequestPermissionsResult RESP_PERMISOS_STORAGE -> PERMISSION_GRANTED" + 1);
                    cargarSplash();
                } else {
                    Log.e(TAG, "onRequestPermissionsResult RESP_PERMISOS_STORAGE -> PERMISSION_DENIED" + 2);
                }
                return;
            }
        }
    }

    private Runnable getRunnableStartApp() {

        Runnable runnable = new Runnable() {

            public void run() {
                VotesBO.CrearVoteDB();
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        return runnable;
    }
}