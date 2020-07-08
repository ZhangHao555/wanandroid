package com.ahao.skybox;

import android.opengl.GLES20;
import android.util.Log;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VALIDATE_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glLinkProgram;

public class ShaderHelper {
    public static String TAG = "ShaderHelper";

    public static int compileVertexShader(String shaderCode) {
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }

    private static int compileShader(int type, String shaderCode) {
        final int shaderObjectId = GLES20.glCreateShader(type);
        if (shaderObjectId == 0) {
            Log.w(TAG, "Could not create new shader.");
            return 0;
        }
        // Pass in the shader source.
        GLES20.glShaderSource(shaderObjectId, shaderCode);
        // Compile the shader.
        GLES20.glCompileShader(shaderObjectId);
        // Get the compilation status.
        final int[] compilationStatus = new int[1];

        GLES20.glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compilationStatus, 0);

        Log.v(TAG, "Results of compiling source:" + "\n" + shaderCode + "\n:"
                + GLES20.glGetShaderInfoLog(shaderObjectId));

        if (compilationStatus[0] == 0) {
            // compile failed
            GLES20.glDeleteShader(shaderObjectId);
            return 0;
        }

        return shaderObjectId;
    }

    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        int programId = GLES20.glCreateProgram();
        if (programId == 0) {
            Log.w(TAG, "linkProgram: could not create new program");
            return 0;
        }

        glAttachShader(programId, vertexShaderId);
        glAttachShader(programId, fragmentShaderId);
        glLinkProgram(programId);

        final int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(programId, GL_LINK_STATUS, linkStatus, 0);

        Log.v(TAG, "Results of linking program:\n" + GLES20.glGetProgramInfoLog(programId));

        if (linkStatus[0] == 0) {
            glDeleteProgram(programId);
            Log.w(TAG, "Linking of program failed.");
            return 0;
        }
        return programId;
    }

    public static boolean validateProgram(int program) {
        GLES20.glValidateProgram(program);

        final int[] validateStatus = new int[1];
        GLES20.glGetProgramiv(program, GL_VALIDATE_STATUS, validateStatus, 0);
        Log.v(TAG, "Results of validating program: " + validateStatus[0]
                + "\nLog:" + GLES20.glGetProgramInfoLog(program));
        return validateStatus[0] != 0;
    }

    public static int buildProgram(String vertexShaderSource,
                                   String fragmentShaderSource) {
        int program;
        // Compile the shaders.
        int vertexShader = compileVertexShader(vertexShaderSource);
        int fragmentShader = compileFragmentShader(fragmentShaderSource);
        // Link them into a shader program.
        program = linkProgram(vertexShader, fragmentShader);
        validateProgram(program);
        return program;
    }
}
