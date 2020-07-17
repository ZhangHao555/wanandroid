package com.ahao.camera;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CameraView extends GLSurfaceView implements GLSurfaceView.Renderer {

    private ICamera mCameraApi;

    private CameraDrawer mCameraDrawer;

    private int width;
    private int height;

    private int mCameraIdDefault = 0;


    public CameraView(Context context) {
        super(context);

        setEGLContextClientVersion(2);
        setRenderer(this);
        //只有刷新之后，才会去重绘
        setRenderMode(RENDERMODE_WHEN_DIRTY);

        mCameraApi = new CameraApi14();
        mCameraDrawer = new CameraDrawer(context);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mCameraDrawer.onSurfaceCreated(gl, config);
        mCameraApi.open(mCameraIdDefault);
        mCameraDrawer.setCameraId(mCameraIdDefault);

        CameraSize previewSize = mCameraApi.getPreviewSize();
        mCameraDrawer.setPreviewSize(previewSize.getWidth(), previewSize.getHeight());

        mCameraApi.setPreviewTexture(mCameraDrawer.getSurfaceTexture());

        mCameraDrawer.getSurfaceTexture().setOnFrameAvailableListener(surfaceTexture -> requestRender());

        mCameraApi.preview();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        this.width = width;
        this.height = height;
        mCameraDrawer.onSurfaceChanged(gl, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        mCameraDrawer.onDrawFrame(gl);
    }
}
