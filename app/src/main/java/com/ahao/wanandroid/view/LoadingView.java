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
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;

import com.ahao.wanandroid.R;
import com.ahao.wanandroid.util.CommonUtilKt;

import static com.ahao.wanandroid.util.DensityUtilKt.dp2px;

public class LoadingView extends View {
    private Paint p;
    private int itemGap = dp2px(4);
    private int itemRadius = dp2px(4);

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
        p = new Paint();
        init();
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

    private void init() {
        colors = new int[]{CommonUtilKt.getColorWithApp(R.color.shallow_blue), CommonUtilKt.getColorWithApp(R.color.deep_blue), CommonUtilKt.getColorWithApp(R.color.shallow_blue)};

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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        leftX = centerX = rightX = (getMeasuredWidth() - itemRadius * 2) / 2;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        p.setStyle(Paint.Style.FILL);
        p.setAntiAlias(true);

        int top = getMeasuredHeight() / 2;
        p.setColor(colors[0]);
        canvas.drawCircle(leftX, top, itemRadius, p);
        p.setColor(colors[1]);
        canvas.drawCircle(centerX, top, itemRadius, p);
        p.setColor(colors[2]);
        canvas.drawCircle(rightX, top, itemRadius, p);
    }

    public void setLeftX(int leftX) {
        this.leftX = leftX;
        invalidate();
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
        invalidate();
    }

    public void setRightX(int rightX) {
        this.rightX = rightX;
        invalidate();
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
