package com.ahao.camera.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

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
                sb.append('\n');
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
        int vertexId = compileShaderCode(GLES20.GL_VERTEX_SHADER, readShader(shaderVertexResource, context));
        int fragmentId = compileShaderCode(GLES20.GL_FRAGMENT_SHADER, readShader(shaderFragmentResource, context));

        int programId = GLES20.glCreateProgram();

        if (programId == 0) {
            throw new RuntimeException("crate program failed : " + shaderVertexResource + " \n:\n " + shaderFragmentResource);
        }

        GLES20.glAttachShader(programId, vertexId);
        GLES20.glAttachShader(programId, fragmentId);

        GLES20.glLinkProgram(programId);

        return programId;
    }

    public static int loadTexture(Context context, int resourceId) {
        final int[] textureId = new int[1];
        GLES20.glGenTextures(1, textureId, 0);
        if (textureId[0] == 0) {
            throw new RuntimeException("create texture failed");
        }

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        if (bitmap == null) {
            GLES20.glDeleteTextures(1, textureId, 0);
            throw new RuntimeException("create bitmap failed");
        }

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[0]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

        return textureId[0];
    }

}
