package com.ahao.wanandroid.glstudy2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;

import com.ahao.wanandroid.App;
import com.ahao.wanandroid.R;
import com.ahao.wanandroid.glstudy.ShaderHelper;
import com.ahao.wanandroid.glstudy.TextResourceReader;
import com.ahao.wanandroid.glstudy.TextureHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_LINEAR_MIPMAP_LINEAR;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glUniform1i;

public class SimpleTexture extends RenderElement {

    private int vertexScript = R.raw.gl_study_texutre_vertex_shader;
    private int fragmentScript = R.raw.gl_study_texture_fragment_shader;

    private int textureResource = R.drawable.air_hockey_surface;

    private int program;

    private int aPosition;
    private int uMatrixPosition;
    private int aTextureCoordinatesLocation;
    private int uTextureUnitLocation;

    private int textureId;

    private FloatBuffer textureBuffer;

    private static final float[] VERTEX_DATA = {
            0f, 0f,
            -0.5f, -0.8f,
            0.5f, -0.8f,
            0.5f, 0.8f,
            -0.5f, 0.8f,
            -0.5f, -0.8f,

    };

    private static final float[] TEXTURE_DATA = {
            0.5f, 0.5f,
            0f, 0.9f,
            1f, 0.9f,
            1f, 0.1f,
            0f, 0.1f,
            0f, 0.9f
    };

    public SimpleTexture() {
        vertexBuffer = ByteBuffer.allocateDirect(VERTEX_DATA.length * BYTES_PRT_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(VERTEX_DATA);

        textureBuffer = ByteBuffer.allocateDirect(TEXTURE_DATA.length * BYTES_PRT_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(TEXTURE_DATA);

        program = ShaderHelper.buildProgram(TextResourceReader.readTextFileFromResource(vertexScript), TextResourceReader.readTextFileFromResource(fragmentScript));

        aPosition = GLES20.glGetAttribLocation(program, "a_Position");
        aTextureCoordinatesLocation = GLES20.glGetAttribLocation(program, "a_TextureCoordinates");
        uMatrixPosition = GLES20.glGetUniformLocation(program, "u_Matrix");
        uTextureUnitLocation = GLES20.glGetUniformLocation(program, "u_TextureUnit");

        textureId = TextureHelper.loadTexture(textureResource);


        vertexBuffer.position(0);
        GLES20.glVertexAttribPointer(aPosition, 2, GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glEnableVertexAttribArray(aPosition);

        textureBuffer.position(0);
        GLES20.glVertexAttribPointer(aTextureCoordinatesLocation, 2, GL_FLOAT, false, 0, textureBuffer);
        GLES20.glEnableVertexAttribArray(aTextureCoordinatesLocation);

    }

    @Override
    public void render() {
        GLES20.glUseProgram(program);
        // Set the active texture unit to texture unit 0.
        glActiveTexture(GL_TEXTURE0);

        // Bind the texture to this unit.
        glBindTexture(GL_TEXTURE_2D, textureId);

        // Tell the texture uniform sampler to use this texture in the shader by
        // telling it to read from texture unit 0.
        glUniform1i(uTextureUnitLocation, 0);
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
    }

    public int getMatrixLocation() {
        return uMatrixPosition;
    }
}
