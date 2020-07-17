package com.ahao.camera;

import android.content.Context;
import android.opengl.GLES20;

import com.ahao.camera.util.VertexArray;

public class TriangleProgram extends Program {

    private final int aPosition;
    private final int uColor;
    private final int uMatrix;
    private VertexArray vertexArray;

    private final float[] triangle = {
            -0.5f, -0.5f,
            0.5f, -0.5f,
            0f, 0.5f
    };

    protected TriangleProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        super(context, vertexShaderResourceId, fragmentShaderResourceId);

        aPosition = GLES20.glGetAttribLocation(program, A_POSITION);
        uColor = GLES20.glGetUniformLocation(program, U_COLOR);
        uMatrix = GLES20.glGetUniformLocation(program,U_MATRIX);
        vertexArray = new VertexArray(triangle);
    }


    public int getPositionLocation() {
        return aPosition;
    }

    public int getColorLocation() {
        return uColor;
    }

    public int getMatrix() {
        return uMatrix;
    }

    public void setColor(float r, float g, float b) {
        GLES20.glUniform4f(uColor, r, g, b, 1f);
    }

    public void setVertex() {
        vertexArray.setVertexAttribPointer(0,getPositionLocation(),2,0);
    }
}
