package com.ahao.wanandroid.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import static com.ahao.wanandroid.util.DensityUtilKt.dp2px;

public class LoadingView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder holder;
    private Paint p;
    private Thread t;
    private Canvas mCanvas;
    public volatile boolean flag;

    private int itemGap = dp2px(8);
    private int itemRadius = dp2px(8);

    private static final int NORMAL_DURATION = 300;
    private static final int END_DURATION = 250;
    private static final int START_DELAY = 200;

    private int[] leftXTranslation;
    private int[] centerXTranslation;
    private int[] rightXTranslation;
    private int[] colors;

    ObjectAnimator leftAnimation;
    ObjectAnimator centerAnimation;
    ObjectAnimator rightAnimation;

    private final AnimatorSet animatorSet = new AnimatorSet();

    private final Interpolator overshootInterpolator = new OvershootInterpolator();
    private final Interpolator defaultInterpolator = new AccelerateDecelerateInterpolator();
    private int leftX;
    private int centerX;
    private int rightX;

    private int step;
    public boolean animatorStop = true;

    private boolean firstLayout = true;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder(); // 获得SurfaceHolder对象
        holder.addCallback(this); // 为SurfaceView添加状态监听
        setFocusable(true);
        p = new Paint();

        init(context);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (firstLayout) {
            leftX = centerX = rightX = (getMeasuredWidth() - itemRadius) / 2;
            initAnimators();
            if (!animatorStop) {
                stopAnimation();
                startAnimation();
            }
            firstLayout = false;
        }
    }

    private void init(Context context) {
        colors = new int[]{Color.parseColor("#FF62DFCB"), Color.parseColor("#FFACEBE3"), Color.parseColor("#FF00CEAA")};

        int stepLength = itemGap + itemRadius * 2;
        leftXTranslation = new int[]{-stepLength, stepLength, stepLength, -stepLength};
        centerXTranslation = new int[]{0, stepLength, -2 * stepLength, stepLength};
        rightXTranslation = new int[]{stepLength, -2 * stepLength, stepLength, 0};

    }

    private void initAnimators() {
        leftAnimation = ObjectAnimator.ofInt(this, "leftX", leftX, leftX + leftXTranslation[step]).setDuration(NORMAL_DURATION);
        leftAnimation.setInterpolator(overshootInterpolator);

        centerAnimation = ObjectAnimator.ofInt(this, "centerX", centerX, centerX + centerXTranslation[step]).setDuration(NORMAL_DURATION);
        centerAnimation.setInterpolator(overshootInterpolator);

        rightAnimation = ObjectAnimator.ofInt(this, "rightX", rightX, rightX + rightXTranslation[step]).setDuration(NORMAL_DURATION);
        rightAnimation.setInterpolator(overshootInterpolator);
        animatorSet.playTogether(leftAnimation, centerAnimation, rightAnimation);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!animatorStop) {
                    step = (++step) % 4;
                    leftAnimation.setIntValues(leftX, leftX + leftXTranslation[step]);
                    centerAnimation.setIntValues(centerX, centerX + centerXTranslation[step]);
                    rightAnimation.setIntValues(rightX, rightX + rightXTranslation[step]);

                    leftAnimation.setInterpolator(step == 3 ? defaultInterpolator : overshootInterpolator);
                    centerAnimation.setInterpolator(step == 3 ? defaultInterpolator : overshootInterpolator);
                    rightAnimation.setInterpolator(step == 3 ? defaultInterpolator : overshootInterpolator);

                    leftAnimation.setDuration(step == 3 ? END_DURATION : NORMAL_DURATION);
                    centerAnimation.setDuration(step == 3 ? END_DURATION : NORMAL_DURATION);
                    rightAnimation.setDuration(step == 3 ? END_DURATION : NORMAL_DURATION);

                    animatorSet.setStartDelay(START_DELAY);
                    animatorSet.start();
                } else {
                    step = 0;
                    leftX = centerX = rightX = (getMeasuredWidth() - itemRadius * 2) / 2;
                    leftAnimation.setIntValues(leftX, leftX + leftXTranslation[step]);
                    centerAnimation.setIntValues(centerX, centerX + centerXTranslation[step]);
                    rightAnimation.setIntValues(rightX, rightX + rightXTranslation[step]);

                    leftAnimation.setInterpolator(overshootInterpolator);
                    centerAnimation.setInterpolator(overshootInterpolator);
                    rightAnimation.setInterpolator(overshootInterpolator);
                }
            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.holder = holder;
        t = new Thread(this);
        flag = true;
        t.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;
        holder.removeCallback(this);
    }

    public static String TAG = "LoadingView";

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        leftX = centerX = rightX = (getMeasuredWidth() - itemRadius * 2) / 2;
        Log.e(TAG, "onMeasure: ");
    }

    @Override
    public void run() {
        while (flag) {
            try {
                synchronized (holder) {
                    Thread.sleep(30); // 让线程休息100毫秒
                    myDraw(); // 调用自定义画画方法
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (mCanvas != null) {
                    holder.unlockCanvasAndPost(mCanvas);
                }
            }
        }
    }

    private void myDraw() {
        mCanvas = holder.lockCanvas();
        p.setStyle(Paint.Style.FILL);
        p.setAntiAlias(true);

        p.setColor(Color.WHITE);
        mCanvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), p);

        int top = getMeasuredHeight() / 2 - itemRadius;
        p.setColor(colors[0]);
        mCanvas.drawCircle(leftX, top, itemRadius, p);
        p.setColor(colors[1]);
        mCanvas.drawCircle(centerX, top, itemRadius, p);
        p.setColor(colors[2]);
        mCanvas.drawCircle(rightX, top, itemRadius, p);

    }


    public void setLeftX(int leftX) {
        this.leftX = leftX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public void setRightX(int rightX) {
        this.rightX = rightX;
    }

    public synchronized void startAnimation() {
        if (animatorStop) {
            animatorStop = false;
            animatorSet.start();
        }
    }

    public synchronized void stopAnimation() {
        animatorStop = true;
        animatorSet.cancel();
    }
}
