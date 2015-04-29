package com.example.hellocurve;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity implements CompoundButton.OnCheckedChangeListener {

    public static final int ANIMATION_DURATION = 2000;
    public static final int ANIMATION_FRAME = 20;

    private RadioButton mAccelerateDecelerate;
    private RadioButton mAccelerateInterpolator;
    private RadioButton mAnticipateInterpolator;
    private RadioButton mAnticipateOvershoot;
    private RadioButton mBounceInterpolator;
    private RadioButton mCycleInterpolator;
    private RadioButton mDecelerateInterpolator;
    private RadioButton mLinearInterpolator;
    private RadioButton mOvershootInterpolator;
    private CurveView mCurveView = null;
    private float[] mYValues = new float[ANIMATION_DURATION / ANIMATION_FRAME];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAccelerateDecelerate = (RadioButton) findViewById(R.id.AccelerateDecelerateInterpolator);
        mAccelerateInterpolator = (RadioButton) findViewById(R.id.AccelerateInterpolator);
        mAnticipateInterpolator = (RadioButton) findViewById(R.id.AnticipateInterpolator);
        mAnticipateOvershoot = (RadioButton) findViewById(R.id.AnticipateOvershootInterpolator);
        mBounceInterpolator = (RadioButton) findViewById(R.id.BounceInterpolator);
        mCycleInterpolator = (RadioButton) findViewById(R.id.CycleInterpolator);
        mDecelerateInterpolator = (RadioButton) findViewById(R.id.DecelerateInterpolator);
        mLinearInterpolator = (RadioButton) findViewById(R.id.LinearInterpolator);
        mOvershootInterpolator = (RadioButton) findViewById(R.id.OvershootInterpolator);
        mCurveView = (CurveView) findViewById(R.id.curve);

        //
        mAccelerateDecelerate.setOnCheckedChangeListener(this);
        mAccelerateInterpolator.setOnCheckedChangeListener(this);
        mAnticipateInterpolator.setOnCheckedChangeListener(this);
        mAnticipateOvershoot.setOnCheckedChangeListener(this);
        mBounceInterpolator.setOnCheckedChangeListener(this);
        mCycleInterpolator.setOnCheckedChangeListener(this);
        mDecelerateInterpolator.setOnCheckedChangeListener(this);
        mLinearInterpolator.setOnCheckedChangeListener(this);
        mOvershootInterpolator.setOnCheckedChangeListener(this);

        mCurveView.setYValues(mYValues);

        mLinearInterpolator.setChecked(true);
    }

    private Interpolator getInterpolator(int checkedId) {

        switch (checkedId) {
            case R.id.AccelerateDecelerateInterpolator:
                return new AccelerateDecelerateInterpolator();

            case R.id.AccelerateInterpolator:
                return new AccelerateInterpolator();

            case R.id.AnticipateInterpolator:
                return new AnticipateInterpolator();

            case R.id.AnticipateOvershootInterpolator:
                return new AnticipateOvershootInterpolator();

            case R.id.BounceInterpolator:
                return new BounceInterpolator();

            case R.id.CycleInterpolator:
                return new CycleInterpolator(2);

            case R.id.DecelerateInterpolator:
                return new DecelerateInterpolator();

            case R.id.LinearInterpolator:
                return new LinearInterpolator();

            case R.id.OvershootInterpolator:
                return new OvershootInterpolator();

            default:
                return null;
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked) {
            return;
        }

        // 重置
        Arrays.fill(mYValues, Float.NaN);

        // 动画
        final TimeInterpolator timeInterpolator = getInterpolator(buttonView.getId());
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimator.setInterpolator(timeInterpolator);
        valueAnimator.setDuration(ANIMATION_DURATION);
        valueAnimator.setFrameDelay(ANIMATION_FRAME);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();

                // 计算 0 ~ value 的曲线
                for (int i = 0; i < (int) Math.abs(mYValues.length * value) && i < mYValues.length; i++) {
                    mYValues[i] = timeInterpolator.getInterpolation((float) i / mYValues.length);
                }

                mCurveView.invalidate();

                Log.d("walfud", String.format("%.2f, %.2f", value, mYValues.length * value));
            }
        });
        valueAnimator.start();
    }
}
