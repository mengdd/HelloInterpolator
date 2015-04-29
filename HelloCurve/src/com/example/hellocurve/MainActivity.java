package com.example.hellocurve;

import android.app.Activity;
import android.os.Bundle;
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

public class MainActivity extends Activity implements CompoundButton.OnCheckedChangeListener {

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
    private float[] mXValues = new float[]{0.0f, 0.1f, 0.2f, 0.3f, 0.4f,
            0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f};
    private float[] mYValues = new float[11];

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

        mCurveView.setxValues(mXValues);

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

        Interpolator interpolator = getInterpolator(buttonView.getId());
        for (int i = 0; i < mXValues.length; ++i) {
            mYValues[i] = interpolator
                    .getInterpolation(mXValues[i]);
        }
        mCurveView.setyValues(mYValues);
        mCurveView.invalidate();
    }
}
