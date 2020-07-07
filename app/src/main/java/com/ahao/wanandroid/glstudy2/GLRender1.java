package com.ahao.wanandroid.glstudy2;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.ahao.wanandroid.App;
import com.ahao.wanandroid.glstudy.ColorShaderProgram;
import com.ahao.wanandroid.glstudy.data.Mallet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;

public class GLRender1 implements GLSurfaceView.Renderer {

    private SimpleTexture simpleTexture;

    private final float[] viewMatrix = new float[16];
    private float[] viewProjectionMatrix = new float[16];
    private final float[] modelViewProjectionMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];

    private Puck puck;
    private Mallet mallet;

    ColorShaderProgram colorShaderProgram;

    private boolean malletPressed = false;
    private Geometry.Point blueMalletPosition;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        simpleTexture = new SimpleTexture();

        colorShaderProgram = new ColorShaderProgram(App.context);

        mallet = new Mallet(0.08f, 0.15f, 32);
        puck = new Puck(0.06f, 0.02f, 32);

        blueMalletPosition = new Geometry.Point(0f,mallet.height / 2f,0.4f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        Matrix.perspectiveM(projectionMatrix, 0, 45f, (float) width
                / (float) height, 1f, 10f);

        change();
       /* Matrix.perspectiveM(projectionMatrix, 0, 45f, (float) width
                / (float) height, 1f, 5f);

        setIdentityM(modelMatrix, 0);
        translateM(modelMatrix, 0, 0f, 0f, -3f);
        Matrix.rotateM(modelMatrix, 0, -30, 1, 0, 0);

        final float[] temp = new float[16];
        multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
        System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);*/
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        positionTableInScene();

        GLES20.glUseProgram(simpleTexture.getProgram());
        GLES20.glUniformMatrix4fv(simpleTexture.getMatrixLocation(), 1, false, modelViewProjectionMatrix, 0);
        simpleTexture.render();

        positionObjectInScene(0, mallet.height / 2f, -0.4f);
        colorShaderProgram.useProgram();
        colorShaderProgram.setUniforms(modelViewProjectionMatrix, 0f, 0f, 1f);
        mallet.bindData(colorShaderProgram);
        mallet.draw();

        positionObjectInScene(0, mallet.height / 2f, 0.4f);

        colorShaderProgram.setUniforms(modelViewProjectionMatrix, 0.5f, 0f, 0f);
        mallet.bindData(colorShaderProgram);
        mallet.draw();

        positionObjectInScene(0, puck.height / 2f, 0f);
        colorShaderProgram.setUniforms(modelViewProjectionMatrix, 0.8f, 0.8f, 1f);
        puck.bindData(colorShaderProgram);
        puck.draw();


    }

    private void positionObjectInScene(float x, float y, float z) {
        setIdentityM(modelMatrix, 0);
        translateM(modelMatrix, 0, x, y, z);
        Matrix.multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix, 0, modelMatrix, 0);
    }

    private void positionTableInScene() {
        setIdentityM(modelMatrix, 0);
        rotateM(modelMatrix, 0, -90, 1f, 0, 0);
        Matrix.multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix, 0, modelMatrix, 0);
    }

    float curXZRadius = 0;
    float viewRadius = 3f;

    public void change() {

        curXZRadius += 1;
        curXZRadius = curXZRadius % 360;
        float x = (float) Math.sin(curXZRadius / 360 * Math.PI * 2) * viewRadius;
        float z = (float) Math.cos(curXZRadius / 360 * Math.PI * 2) * viewRadius;
        Matrix.setLookAtM(viewMatrix, 0, x, 2f, z, 0f, 0f, 0f, 0f, 1f, 0f);

        multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
    }


    public void handleTouchPress(float normalizedX,float normalizedY){
    }

    public void handleTouchDrag(float normalizedX,float normalizedY){

    }
}
