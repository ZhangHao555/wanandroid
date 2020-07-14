package com.ahao.camera;

import android.opengl.GLSurfaceView;
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

        private final float[] triangle = {
                -0.5f, -0.5f,
                0.5f, -0.5f,
                0f, 0.5f
        };

        TriangleProgram triangleProgram;
        private VertexArray vertexArray;

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            triangleProgram = new TriangleProgram(TriangleActivity.this, R.raw.triangle_vertext, R.raw.triangle_shader_fragment);
            vertexArray = new VertexArray(triangle);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            gl.glViewport(0, 0, width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            glClear(GL_COLOR_BUFFER_BIT);

            triangleProgram.useProgram();
            triangleProgram.setColor(1f, 0f, 0f);
            triangleProgram.setVertex(vertexArray.getFloatBuffer(), 0, 3);

        }
    }
}
