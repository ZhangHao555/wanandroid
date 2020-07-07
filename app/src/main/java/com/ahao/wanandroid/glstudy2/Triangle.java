package com.ahao.wanandroid.glstudy2;

import android.opengl.GLES20;

import com.ahao.wanandroid.R;
import com.ahao.wanandroid.glstudy.ShaderHelper;
import com.ahao.wanandroid.glstudy.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Triangle extends RenderElement {

    private final int vertexShaderScript = R.raw.gl_study_simple_triangle_vertex_shader;
    private final int fragmentShaderScript = R.raw.gl_study_simple_triangle_fragment_shader;

    private int program;

    private final int VERTEX_DATA_SIZE = 2;

    private int uColor;
    private int aPosition;
    private int uMatrix;

    private static float[] vertex = {
            -0.5f, -0.5f,
            0.5f, -0.5f,
            0.5f, 0.5f,
            -0.5f,0.5f

    };

    public Triangle() {
        vertexBuffer = ByteBuffer.allocateDirect(vertex.length * BYTES_PRT_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertex);

        String vertexShader = TextResourceReader.readTextFileFromResource(vertexShaderScript);
        String fragmentShader = TextResourceReader.readTextFileFromResource(fragmentShaderScript);

        program = ShaderHelper.buildProgram(vertexShader, fragmentShader);
        aPosition = GLES20.glGetAttribLocation(program, "a_Position");
        uColor = GLES20.glGetUniformLocation(program, "u_Color");
        uMatrix = GLES20.glGetUniformLocation(program,"u_Matrix");

        vertexBuffer.position(0);
        GLES20.glVertexAttribPointer(aPosition, VERTEX_DATA_SIZE, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glEnableVertexAttribArray(aPosition);
    }


    @Override
    public void render() {
        GLES20.glUseProgram(program);
        GLES20.glUniform4f(uColor,0.5f,0,0,1f);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);
    }

    public int getMatrixLocation(){
        return uMatrix;
    }
}
