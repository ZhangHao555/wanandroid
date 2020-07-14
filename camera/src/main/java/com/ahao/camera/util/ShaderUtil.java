package com.ahao.camera.util;

import android.content.Context;
import android.opengl.GLES20;

import com.ahao.camera.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ShaderUtil {

    public static String readShader(int rawResource, Context context) {
        InputStream inputStream = context.getResources().openRawResource(rawResource);
        InputStreamReader streamReader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(streamReader);

        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception ignored) {

        }

        return sb.toString();

    }

    public static int compileShaderCode(int type, String shaderCode) {
        int shaderObjId = GLES20.glCreateShader(type);

        if (shaderObjId == 0) {
            throw new RuntimeException("create shader failed :" + shaderCode);
        }

        GLES20.glShaderSource(shaderObjId, shaderCode);
        GLES20.glCompileShader(shaderObjId);

        int[] status = new int[1];
        GLES20.glGetShaderiv(shaderObjId, GLES20.GL_COMPILE_STATUS, status, 0);
        if (status[0] == 0) {
            GLES20.glDeleteShader(shaderObjId);
            return 0;
        }

        return shaderObjId;
    }

    public static int createProgram(int shaderVertexResource, int shaderFragmentResource, Context context) {
        int vertexId = compileShaderCode(GLES20.GL_VERTEX_SHADER, readShader(R.raw.triangle_vertext, context));
        int fragmentId = compileShaderCode(GLES20.GL_FRAGMENT_SHADER, readShader(R.raw.triangle_shader_fragment, context));

        int programId = GLES20.glCreateProgram();

        if (programId == 0) {
            throw new RuntimeException("crate program failed : " + shaderVertexResource + " \n:\n " + shaderFragmentResource);
        }

        GLES20.glAttachShader(programId, vertexId);
        GLES20.glAttachShader(programId, fragmentId);

        GLES20.glLinkProgram(programId);

        return programId;
    }
}
