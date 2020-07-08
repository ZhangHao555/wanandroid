package com.ahao.particles;

import android.content.Context;
import android.opengl.GLES20;

import static com.ahao.particles.ShaderProgram.U_TEXTURE_UNIT;

public class SkyBoxShaderProgram extends ShaderProgram {

    private final int uMatrixLocation;
    private final int uTextureUnitLocation;
    private final int aPositionLocation;

    protected SkyBoxShaderProgram(Context context) {
        super(context, R.raw.skybox_vertex_shader, R.raw.skybox_fragment_shader);

        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);

        uTextureUnitLocation = GLES20.glGetUniformLocation(program, U_TEXTURE_UNIT);

        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);

    }


    public int getuMatrixLocation() {
        return uMatrixLocation;
    }

    public int getuTextureUnitLocation() {
        return uTextureUnitLocation;
    }

    public int getaPositionLocation() {
        return aPositionLocation;
    }

    public void setUniform(float[] matrix, int textureId) {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_CUBE_MAP, textureId);
        GLES20.glUniform1i(uTextureUnitLocation, 0);

    }
}
