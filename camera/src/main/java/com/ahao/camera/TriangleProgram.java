package com.ahao.camera;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.FloatBuffer;

public class TriangleProgram extends Program {

    private final int aPosition;
    private final int uColor;

    protected TriangleProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        super(context, vertexShaderResourceId, fragmentShaderResourceId);

        aPosition = GLES20.glGetAttribLocation(program, A_POSITION);
        uColor = GLES20.glGetUniformLocation(program, U_COLOR);
    }


    public int getPositionLocation() {
        return aPosition;
    }

    public int getColorLocation() {
        return uColor;
    }

    public void setColor(float r, float g, float b) {
        GLES20.glUniform4f(uColor, r, g, b, 1f);
    }

    public void setVertex(FloatBuffer buffer, int stride, int size) {
        GLES20.glVertexAttribPointer(aPosition, size, GLES20.GL_FLOAT, false, stride, buffer);
    }
}
