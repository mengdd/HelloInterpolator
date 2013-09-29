package com.example.hellocurve;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
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
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends Activity {

	private RadioGroup mInterpolatorGroup = null;
	private CurveView mCurveView = null;
	private float[] mXValues = new float[] { 0.0f, 0.1f, 0.2f, 0.3f, 0.4f,
			0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f };
	private float[] mYValues = new float[11];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mInterpolatorGroup = (RadioGroup) findViewById(R.id.interpolatorGroup);
		mCurveView = (CurveView) findViewById(R.id.curve);
		mCurveView.setxValues(mXValues);
		mInterpolatorGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						Interpolator interpolator = getInterpolator();
						for (int i = 0; i < mXValues.length; ++i) {
							mYValues[i] = interpolator
									.getInterpolation(mXValues[i]);
						}
						mCurveView.setyValues(mYValues);
						mCurveView.invalidate();
					}
				});

		mInterpolatorGroup.check(R.id.LinearInterpolator);

	}

	private Interpolator getInterpolator() {

		int checkedId = mInterpolatorGroup.getCheckedRadioButtonId();

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
			break;
		}

		return null;

	}

}
