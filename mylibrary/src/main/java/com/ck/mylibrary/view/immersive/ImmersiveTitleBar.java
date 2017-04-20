package com.ck.mylibrary.view.immersive;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.ck.mylibrary.R;


/**
 * Created by skindhu on 15/7/14.
 */
public class ImmersiveTitleBar extends View {
	private int mStatusBarHeight;
	public int mViewHeight;
	// 是否要开启透明状态栏兼容绘制，默认开启
	public static boolean TRANSLUCENT_STATUS_BAR = true;
	public static boolean mNeedDrawStatus = true;
	public ImmersiveTitleBar(Context mContext, AttributeSet attrs) {
		super(mContext, attrs);
        mNeedDrawStatus =  TRANSLUCENT_STATUS_BAR && (ImmersiveUtil.isSupporImmersive() == 1);
        mStatusBarHeight = ImmersiveUtil.getStatusBarHeight(mContext);
        if (mNeedDrawStatus) {
            setCustomHeight(mStatusBarHeight);
        }else{
            setCustomHeight(0);
        }
        //状态栏颜色
        TypedArray a = mContext.obtainStyledAttributes(attrs,
                R.styleable.ImmersiveTitleBarColor);

        setBackgroundColor(a.getColor(R.styleable.ImmersiveTitleBarColor_barColor,getResources().getColor(android.R.color.white)));
	}


	public void setImmersive(int color){
		setBackgroundColor(color);
	}
	public void setCustomHeight(int height){
		mViewHeight = height;
		requestLayout();
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(mViewHeight, MeasureSpec.EXACTLY));
		setMeasuredDimension(getMeasuredWidth(), mViewHeight);

	}
}
