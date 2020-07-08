package com.ahao.particles;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class ParticlesRender implements GLSurfaceView.Renderer {

    private final Context context;

    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];

    private ParticleShaderProgram particleShaderProgram;
    private ParticleSystem particleSystem;
    private ParticleShooter redParticleShooter;
    private ParticleShooter greenParticleShooter;
    private ParticleShooter blueParticleShooter;


    private float angleVarianceInDegrees = 5f;
    private float speedVariance = 1f;

    private long globalStartTime;

    private int texture;

    private SkyBoxShaderProgram skyBoxShaderProgram;
    private SkyBox skyBox;
    private int skyBoxTexture;

    private float xRotation = 0;
    private float yRotation = 0;

    public ParticlesRender(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0, 0, 0, 0);

        particleShaderProgram = new ParticleShaderProgram(context);
        particleSystem = new ParticleSystem(10000);
        globalStartTime = System.nanoTime();

        final Vector particleDirection = new Vector(0f, 0.5f, 0f);

        redParticleShooter = new ParticleShooter(new Point(-1f, 0f, 0f), particleDirection, Color.rgb(255, 50, 5), angleVarianceInDegrees, speedVariance);

        greenParticleShooter = new ParticleShooter(new Point(0f, 0f, 0f), particleDirection, Color.rgb(25, 255, 25), angleVarianceInDegrees, speedVariance);

        blueParticleShooter = new ParticleShooter(new Point(1f, 0f, 0f), particleDirection, Color.rgb(5, 50, 255), angleVarianceInDegrees, speedVariance);

        texture = TextureHelper.loadTexture(R.drawable.particle_texture);

        skyBoxShaderProgram = new SkyBoxShaderProgram(App.context);
        skyBox = new SkyBox();

        skyBoxTexture = TextureHelper.loadCubeMap(App.context,
                new int[]{R.drawable.left, R.drawable.right,
                        R.drawable.bottom, R.drawable.top,
                        R.drawable.front, R.drawable.back});
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        Matrix.perspectiveM(projectionMatrix, 0, 45, (float) width / height, 1f, 10f);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        drawSkyBox();
        drawParticles();
    }

    private void drawSkyBox() {
        Matrix.setIdentityM(viewMatrix, 0);
        Matrix.rotateM(viewMatrix,0,-yRotation,1f,0,0);
        Matrix.rotateM(viewMatrix,0,-xRotation,0,1,0);
        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        skyBoxShaderProgram.useProgram();
        skyBoxShaderProgram.setUniform(viewProjectionMatrix, skyBoxTexture);
        skyBox.bindData(skyBoxShaderProgram);
        skyBox.draw();
    }

    private void drawParticles() {
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE);
        float currentTime = (System.nanoTime() - globalStartTime) / 1000_000_000f;
        redParticleShooter.addParticles(particleSystem, currentTime, 5);
        greenParticleShooter.addParticles(particleSystem, currentTime, 5);
        blueParticleShooter.addParticles(particleSystem, currentTime, 5);

        Matrix.setIdentityM(viewMatrix, 0);
        Matrix.rotateM(viewMatrix,0,-yRotation,1f,0,0);
        Matrix.rotateM(viewMatrix,0,-xRotation,0,1,0);


        Matrix.translateM(viewMatrix, 0, 0, -1.5f, -5f);
        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        particleShaderProgram.useProgram();
        particleShaderProgram.setUniforms(viewProjectionMatrix, currentTime, texture);
        particleSystem.bindData(particleShaderProgram);
        particleSystem.draw();

        GLES20.glDisable(GLES20.GL_BLEND);
    }


    public void handleTouchPress(float normalizedX, float normalizedY) {

    }

    public void handleTouchDrag(float deltaX, float deltaY) {
        xRotation += deltaX / 16f;
        yRotation += deltaY / 16f;

        if(yRotation < -90){
            yRotation = -90;
        }else if(yRotation > 90){
            yRotation = 90;
        }
    }

    private float currentScale = 1.0f;
    public void setScale(float scaleFactor) {

    }
}
