package com.ahao.camera;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ahao.camera.util.VertexArray;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;

public class TriangleActivity extends AppCompatActivity {

    private GLSurfaceView.Renderer renderer;

    private GLSurfaceView glSurfaceView;

    //投影矩阵
    private float[] mProjectionMatrix = new float[16];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glSurfaceView = new GLSurfaceView(this);
        renderer = new TriangleRender();

        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(renderer);


        setContentView(glSurfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

    private class TriangleRender implements GLSurfaceView.Renderer {

        TriangleProgram triangleProgram;

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            triangleProgram = new TriangleProgram(TriangleActivity.this, R.raw.triangle_vertext, R.raw.triangle_shader_fragment);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            float aspect = width > height ?
                    (float) width / height :
                    (float) height / width;

            if (width > height) {
                // 横屏需要设置的就是左右
                Matrix.orthoM(mProjectionMatrix, 0, -aspect, aspect, -1f, 1f, -1f, 1f);
            } else {
                // 竖屏
                Matrix.orthoM(mProjectionMatrix, 0, -1, 1f, -aspect, aspect, -1.f, 1f);
            }
            gl.glViewport(0, 0, width, height);

        }

        @Override
        public void onDrawFrame(GL10 gl) {
            glClear(GL_COLOR_BUFFER_BIT);

            triangleProgram.useProgram();
            triangleProgram.setColor(0.5f, 0f, 0f);
            triangleProgram.setVertex();
            GLES20.glUniformMatrix4fv(triangleProgram.getMatrix(), 1, false, mProjectionMatrix, 0);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);

        }
    }
}