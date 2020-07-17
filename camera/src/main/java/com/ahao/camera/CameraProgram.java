package com.ahao.camera;

import android.content.Context;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;

import com.ahao.camera.util.VertexArray;

public class CameraProgram extends Program {
    private final int aPosition;
    private final int aCoordinate;
    private final int uMatrix;
    private final int uCoordinateMatrix;

    private final int uTextureUnit;

    private int textureId;

    private VertexArray vertexArray = new VertexArray(new float[]{
            -1.0f, 1.0f,
            -1.0f, -1.0f,
            1.0f, 1.0f,
            1.0f, -1.0f
    });

    private VertexArray coord = new VertexArray(new float[]{
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,
    });

    private float[] modelMatrix = new float[16];
    private float[] dwMatrix = new float[16];

    protected CameraProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        super(context, vertexShaderResourceId, fragmentShaderResourceId);

        aPosition = GLES20.glGetAttribLocation(program, A_POSITION);
        aCoordinate = GLES20.glGetAttribLocation(program, A_COORDINATE);
        uMatrix = GLES20.glGetUniformLocation(program, U_MATRIX);
        uCoordinateMatrix = GLES20.glGetUniformLocation(program, U_COORDINATEMATRIX);
        uTextureUnit = GLES20.glGetUniformLocation(program, "uTexture");

    }

    public int getPosition() {
        return aPosition;
    }

    public int getCoordinate() {
        return aCoordinate;
    }

    public int getMatrix() {
        return uMatrix;
    }

    public int getCoordinateMatrix() {
        return uCoordinateMatrix;
    }


    public int getTextureId() {
        return textureId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }

    public void draw() {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        GLES20.glUseProgram(program);
        
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, getTextureId());
        GLES20.glUniform1i(uTextureUnit, 0);



        GLES20.glUniformMatrix4fv(uMatrix, 1, false, modelMatrix, 0);
        GLES20.glUniformMatrix4fv(uCoordinateMatrix, 1, false, dwMatrix, 0);

        GLES20.glEnableVertexAttribArray(aPosition);
        vertexArray.getFloatBuffer().position(0);
        GLES20.glVertexAttribPointer(
                aPosition,
                2,
                GLES20.GL_FLOAT,
                false,
                0,
                vertexArray.getFloatBuffer());

        GLES20.glEnableVertexAttribArray(aCoordinate);

        coord.getFloatBuffer().position(0);
        GLES20.glVertexAttribPointer(
                aCoordinate,
                2,
                GLES20.GL_FLOAT,
                false,
                0,
                coord.getFloatBuffer());

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

        GLES20.glDisableVertexAttribArray(aPosition);
        GLES20.glDisableVertexAttribArray(aCoordinate);

    }

    public float[] getModelMatrix() {
        return modelMatrix;
    }

    public void setModelMatrix(float[] modelMatrix) {
        this.modelMatrix = modelMatrix;
    }
}
