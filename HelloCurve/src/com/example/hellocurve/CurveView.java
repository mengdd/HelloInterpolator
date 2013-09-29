package com.example.hellocurve;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 
 * @ClassName: CurveView
 * @Description: This custom View is to show the x-y relation curve
 * @author Meng Dandan
 * @date 2013-9-29
 * 
 */
public class CurveView extends View {

	private float[] xValues = null;
	private float[] yValues = null;
	private Path mPath = null;
	private Paint mPaint = null;
	private Paint mGridPaint = null;

	private int mWidth = 0;
	private int mHeight = 0;

	private int mUnitHeight = 0;
	private int mHeightMargin = 0;

	public CurveView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CurveView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CurveView(Context context) {
		super(context);
		init();
	}

	private void init() {
		mPath = new Path();
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(Color.RED);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(2);

		// 平滑曲线
		PathEffect pathEffect = new CornerPathEffect(30);
		mPaint.setPathEffect(pathEffect);

		mGridPaint = new Paint();
		mGridPaint.setAntiAlias(true);
		mGridPaint.setDither(true);
		mGridPaint.setColor(Color.BLACK);
		mGridPaint.setStyle(Paint.Style.STROKE);
		mGridPaint.setStrokeWidth(1);

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mWidth = w;
		mHeight = h;

		mUnitHeight = Math.round(0.5f * mHeight);

		mHeightMargin = (mHeight - mUnitHeight) / 2;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawVerticalGrids(canvas);
		drawHorizontalGrids(canvas);
		if (null != xValues && null != yValues
				&& xValues.length == yValues.length) {

			mPath.reset();
			// move to start point
			mPath.moveTo(xValues[0] * mWidth, mHeightMargin + (1 - yValues[0])
					* mUnitHeight);
			// line to other points
			for (int i = 1; i < xValues.length; ++i) {
				mPath.lineTo(xValues[i] * mWidth, mHeightMargin
						+ (1 - yValues[i]) * mUnitHeight);
			}

		}

		canvas.drawPath(mPath, mPaint);

	}

	private void drawHorizontalGrids(Canvas canvas) {
		for (int i = 0; i < xValues.length; ++i) {
			canvas.drawLine(0, mHeightMargin + xValues[i] * mUnitHeight,
					mWidth, mHeightMargin + xValues[i] * mUnitHeight,
					mGridPaint);
		}
	}

	private void drawVerticalGrids(Canvas canvas) {

		for (int i = 0; i < xValues.length; ++i) {
			canvas.drawLine(xValues[i] * mWidth, 0, xValues[i] * mWidth,
					mHeight, mGridPaint);
		}
	}

	public float[] getxValues() {
		return xValues;
	}

	public void setxValues(float[] xValues) {
		this.xValues = xValues;
	}

	public float[] getyValues() {
		return yValues;
	}

	public void setyValues(float[] yValues) {
		this.yValues = yValues;
	}

}
