package com.ahao.camera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int checkSelfPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(checkSelfPermission == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},32131);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void gotoTriangleActivity(View v){
        startActivity(new Intent(this,TriangleActivity.class));
    }

    public void gotoTextureActivity(View v){
        startActivity(new Intent(this,TextureActivity.class));
    }

    public void gotoCameraActivity(View v){
        startActivity(new Intent(this,CameraActivity.class));
    }
}
