package com.example.hellocurve;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
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

    public static final int V_GRID_COUNT = 10;
    public static final int H_GRID_COUNT = 10;

	private float[] mYValues = null;
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

        mPath.reset();
        // move to start point
        mPath.moveTo(0, mHeightMargin + mUnitHeight);
        // line to other points
        for (int i = 0; i < mYValues.length; ++i) {
            if (Float.isNaN(mYValues[i])) {
                break;
            }

            float x = mWidth * ((float) (i + 1) / mYValues.length);
            mPath.lineTo(x, mHeightMargin + mUnitHeight * (1 - mYValues[i]));
        }
		canvas.drawPath(mPath, mPaint);
	}


    private void drawVerticalGrids(Canvas canvas) {
        for (int i = 0; i < V_GRID_COUNT; ++i) {
            float x = mWidth * ((float) i / V_GRID_COUNT);
            canvas.drawLine(x, 0, x, mHeight, mGridPaint);
        }
    }
	private void drawHorizontalGrids(Canvas canvas) {
		for (int i = 0; i < H_GRID_COUNT; ++i) {
            float y = mHeightMargin + mUnitHeight * ((float) i / H_GRID_COUNT);
			canvas.drawLine(0, y, mWidth, y, mGridPaint);
		}
	}

	public void setYValues(float[] yValues) {
        mYValues = yValues;
	}

}
