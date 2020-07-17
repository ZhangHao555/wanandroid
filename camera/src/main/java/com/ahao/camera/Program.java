package com.ahao.camera;

import android.content.Context;
import android.opengl.GLES20;

import com.ahao.camera.util.ShaderUtil;

public class Program {

    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_COLOR = "u_Color";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";
    protected static final String U_TIME = "u_Time";


    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";
    protected static final String A_COORDINATE = "a_Coordinate";
    protected static final String U_COORDINATEMATRIX = "u_CoordinateMatrix";

    protected static final String A_DIRECTION_VECTOR = "a_DirectionVector";
    protected static final String A_PARTICLE_START_TIME = "a_ParticleStartTime";
    protected static final String U_CHANGE_COLOR = "u_ChangeColor";

    protected int program;

    protected Program(Context context, int vertexShaderResourceId,
                      int fragmentShaderResourceId) {
        // Compile the shaders and link the program.
        program = ShaderUtil.createProgram(vertexShaderResourceId, fragmentShaderResourceId, context);
    }

    public void useProgram(){
        GLES20.glUseProgram(program);
    }
}
