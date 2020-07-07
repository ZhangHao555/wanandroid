package com.ahao.wanandroid.glstudy2;

import android.annotation.SuppressLint;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ahao.wanandroid.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class GLActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gl_study);

        glSurfaceView = findViewById(R.id.gl_surface_view);
        glSurfaceView.setEGLContextClientVersion(2);
        final GLRender1 glRender1 = new GLRender1();
        glSurfaceView.setRenderer(glRender1);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.interval(20, TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        glRender1.change();
                    }
                });
            }
        });

        glSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event == null) {
                    return false;
                }
                final float nomalizeX = event.getX() / v.getWidth() * 2 - 1;
                final float nomalizeY = -(event.getY() / v.getHeight() * 2 - 1);


                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        glSurfaceView.queueEvent(new Runnable() {
                            @Override
                            public void run() {
                                glRender1.handleTouchPress(nomalizeX, nomalizeY);

                            }
                        });
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        glSurfaceView.queueEvent(new Runnable() {
                            @Override
                            public void run() {
                                glRender1.handleTouchDrag(nomalizeX, nomalizeY);
                            }
                        });
                        return true;
                }

                return false;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }
}
