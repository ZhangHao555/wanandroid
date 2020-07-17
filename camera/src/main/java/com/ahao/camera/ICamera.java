package com.ahao.camera;

import android.graphics.SurfaceTexture;

public interface ICamera {
    boolean open(int cameraId);

    void setAspectRatio(AspectRatio aspectRatio);

    boolean preview();

    boolean close();

    void setPreviewTexture(SurfaceTexture surfaceTexture);

    CameraSize getPreviewSize();

    CameraSize getPictureSize();

    void takePhoto(TakePhotoCallback callback);

    interface TakePhotoCallback {
        void onTakePhoto(byte[] bytes, int width, int height);
    }
}
