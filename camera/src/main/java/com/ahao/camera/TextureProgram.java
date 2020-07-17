package com.ahao.camera;

import android.content.Context;
import android.opengl.GLES20;

import com.ahao.camera.util.ShaderUtil;
import com.ahao.camera.util.VertexArray;

import static android.opengl.GLES20.glUniform1i;

public class TextureProgram extends Program {

    private final float[] square = {
            0f, 0f,
            -0.5f, -0.5f,
            0.5f, -0.5f,
            0.5f, 0.5f,
            -0.5f, 0.5f,
            -0.5f, -0.5f
    };

    // 注意 T是反过来的
    private final float[] texture = {
            0.5f, 0.5f,
            0f, 1f,
            1f, 1f,
            1f, 0f,
            0f, 0f,
            0f, 1f
    };

    float[] grayFilterColorData = {0.299f, 0.587f, 0.114f};

    private final int aPosition;
    private final int aTextureCoordinates;

    private final int uMatrix;

    private VertexArray vertexArray;
    private VertexArray textureArray;

    private int textureId;

    private int uTextureUnitLocation;

    private int uChangeColor;

    protected TextureProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        super(context, vertexShaderResourceId, fragmentShaderResourceId);

        aPosition = GLES20.glGetAttribLocation(program, A_POSITION);
        aTextureCoordinates = GLES20.glGetAttribLocation(program, A_TEXTURE_COORDINATES);
        uMatrix = GLES20.glGetUniformLocation(program, U_MATRIX);

        vertexArray = new VertexArray(square);
        textureArray = new VertexArray(texture);

        uTextureUnitLocation = GLES20.glGetUniformLocation(program, "u_TextureUnit");
        textureId = ShaderUtil.loadTexture(context, R.drawable.study_manifest_default_cover);

        uChangeColor = GLES20.glGetUniformLocation(program, U_CHANGE_COLOR);

    }

    public int getPosition() {
        return aPosition;
    }

    public int getTextureCoordinates() {
        return aTextureCoordinates;
    }

    public int getMatrix() {
        return uMatrix;
    }

    public void setVertex() {
        vertexArray.setVertexAttribPointer(0, aPosition, 2, 0);
        GLES20.glEnableVertexAttribArray(aPosition);
        textureArray.setVertexAttribPointer(0, aTextureCoordinates, 2, 0);
        GLES20.glEnableVertexAttribArray(aTextureCoordinates);
    }

    public void draw() {
        setVertex();
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        glUniform1i(uTextureUnitLocation, 0);

        GLES20.glUniform3fv(uChangeColor, 1, grayFilterColorData, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);
    }
}
