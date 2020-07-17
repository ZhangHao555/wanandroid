package com.ahao.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.concurrent.atomic.AtomicBoolean;

public class CameraApi14 implements ICamera{

    private int mCameraId;
    private Camera mCamera;

    private Camera.Parameters mCameraParameters;

    private int mDesiredHeight = 1920;
    private int mDesiredWidth = 1080;

    private boolean mAutoFocus;
    public CameraSize mPreviewSize;
    public CameraSize mPicSize;

    private AspectRatio mDesiredAspectRatio;

    private AtomicBoolean isPictureCaptureInProgress = new AtomicBoolean(false);


    private TakePhotoCallback photoCallBack;

    public CameraApi14() {
        mDesiredHeight = 1920;
        mDesiredWidth = 1080;
        //创建默认的比例.因为后置摄像头的比例，默认的情况下，都是旋转了270
        mDesiredAspectRatio = AspectRatio.of(mDesiredWidth, mDesiredHeight).inverse();
    }

    @Override
    public boolean open(int cameraId) {
        final CameraSize.ISizeMap mPreviewSizes = new CameraSize.ISizeMap();
        final CameraSize.ISizeMap mPictureSizes = new CameraSize.ISizeMap();

        if (mCamera != null) {
            releaseCamera();
        }

        mCameraId = cameraId;
        mCamera = Camera.open(mCameraId);
        if(mCamera != null){
            mCameraParameters = mCamera.getParameters();

            List<Camera.Size> supportedPreviewSizes = mCameraParameters.getSupportedPreviewSizes();
            for (Camera.Size size : supportedPreviewSizes) {
                mPreviewSizes.add(new CameraSize(size.width,size.height));
            }

            for (Camera.Size pictureSize : mCameraParameters.getSupportedPictureSizes()) {
                mPictureSizes.add(new CameraSize(pictureSize.width,pictureSize.height));
            }

            //挑选出最需要的参数
            adJustParametersByAspectRatio2(mPreviewSizes, mPictureSizes);
        }
        return false;
    }

    public static String TAG = "CameraApi14";
    private void adJustParametersByAspectRatio2(CameraSize.ISizeMap previewSizes, CameraSize.ISizeMap pictureSizes) {
        //得到当前预期比例的size
        SortedSet<CameraSize> sizes = previewSizes.sizes(mDesiredAspectRatio);
        if (sizes == null) {  //表示不支持.
            // TODO: 2018/9/14 这里应该抛出异常？
            return;
        }
        for (CameraSize next : sizes) {
            if (next.getWidth() >= 720 && next.getHeight() >= 720) {
                mPreviewSize = next;
                break;
            }
        }
        if (mPreviewSize == null) {
            mPreviewSize = sizes.last();
        }

//
        for (CameraSize next : pictureSizes.sizes(mDesiredAspectRatio)) {
            if (next.getWidth() >= 720 && next.getHeight() >= 720) {
                mPicSize = next;
                break;
            }
        }
        if (mPicSize == null) {
            mPicSize = pictureSizes.sizes(mDesiredAspectRatio).last();
        }

        mCameraParameters.setPreviewSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
        mCameraParameters.setPictureSize(mPicSize.getWidth(), mPicSize.getHeight());

        mPreviewSize = mPreviewSize.inverse();
        mPicSize = mPicSize.inverse();

        Log.d(TAG, "preview=" + mPreviewSize);
        Log.d(TAG, "mPicSize=" + mPicSize);
        //设置对角和闪光灯
        setAutoFocusInternal(mAutoFocus);
        //先不设置闪光灯
//        mCameraParameters.setFlashMode("FLASH_MODE_OFF");

        //设置到camera中
//        mCameraParameters.setRotation(90);
        mCamera.setParameters(mCameraParameters);
//        mCamera.setDisplayOrientation(90);
//        setCameraDisplayOrientation();
    }

    private void setAutoFocusInternal(boolean autoFocus) {
        mAutoFocus = autoFocus;
        final List<String> modes = mCameraParameters.getSupportedFocusModes();
        if (autoFocus && modes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            mCameraParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        } else if (modes.contains(Camera.Parameters.FOCUS_MODE_FIXED)) {
            mCameraParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_FIXED);
        } else if (modes.contains(Camera.Parameters.FOCUS_MODE_INFINITY)) {
            mCameraParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_INFINITY);
        } else {
            mCameraParameters.setFocusMode(modes.get(0));
        }
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void setAspectRatio(AspectRatio aspectRatio) {
        this.mDesiredAspectRatio = aspectRatio;
    }

    @Override
    public boolean preview() {
        if (mCamera != null) {
            mCamera.startPreview();
            return true;
        }
        return false;
    }

    @Override
    public boolean close() {
        if (mCamera != null) {
            try {
                //stop preview时，可能爆出异常
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    @Override
    public void setPreviewTexture(SurfaceTexture surfaceTexture) {
        if (mCamera != null) {
            try {
                mCamera.setPreviewTexture(surfaceTexture);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public CameraSize getPreviewSize() {
        return mPreviewSize;
    }

    @Override
    public CameraSize getPictureSize() {
        return mPicSize;
    }

    @Override
    public void takePhoto(TakePhotoCallback callback) {
        this.photoCallBack = callback;
        if (getAutoFocus()) {
            mCamera.cancelAutoFocus();
            mCamera.autoFocus((success, camera) -> takePictureInternal());
        } else {
            takePictureInternal();
        }

    }

    private void takePictureInternal() {
        if (!isPictureCaptureInProgress.getAndSet(true)) {
            final long start = System.currentTimeMillis();
            mCamera.takePicture(null, null, null, new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    long end = System.currentTimeMillis();
                    Log.d(TAG, "mCamera.takePicture cost = " + (end - start));
                    isPictureCaptureInProgress.set(false);
                    if (photoCallBack != null) {
                        photoCallBack.onTakePhoto(data, mPreviewSize.getWidth(), mPreviewSize.getHeight());
                    }
                    camera.cancelAutoFocus();
                    camera.startPreview();
                }
            });
        }
    }

    //判断是否自动对焦
    private boolean getAutoFocus() {
        String focusMode = mCameraParameters.getFocusMode();
        return focusMode != null && focusMode.contains("continuous");
    }
}
