package com.ahao.skybox;

import android.content.Context;
import android.opengl.GLES20;

public class SkyBoxShaderProgram extends ShaderProgram {

    private final int uMatrixLocation;
    private final int uTextureUnitLocation;
    private final int aPositionLocation;

    protected SkyBoxShaderProgram(Context context) {
        super(context, R.raw.skybox_vertex_shader, R.raw.skybox_fragment_shader);

        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);

        uTextureUnitLocation = GLES20.glGetUniformLocation(program, A_TEXTURE_UNIT);

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
}
