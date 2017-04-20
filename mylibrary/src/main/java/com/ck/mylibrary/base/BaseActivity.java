package com.ck.mylibrary.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.ck.mylibrary.R;
import com.ck.mylibrary.view.immersive.ImmersiveUtil;
import com.ck.mylibrary.view.immersive.SystemBarCompact;
import com.ck.mylibrary.view.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.ck.mylibrary.view.recyclerview.LRecyclerView;
import com.ck.mylibrary.view.recyclerview.interfaces.OnItemClickLitener;
import com.ck.mylibrary.view.recyclerview.util.RecyclerViewUtils;
import com.ck.mylibrary.view.slider.SlidingCompatActivity;


/**
 * Created by CK on 2016/9/29.
 */
public class BaseActivity extends SlidingCompatActivity {
    public SystemBarCompact systemBarCompact;
    // 沉浸式UI
    public boolean needStatusTrans = true; // 设置是否需要状态栏透明
    public boolean needImmersive = true; // 设置是否需要设置状态栏颜色
//    private MyReceiver myReceiver;

    @Override
    protected void onResume() {
        super.onResume();
        if (systemBarCompact != null) {
            systemBarCompact.init();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(getIntent());
//        MyApplication.ACTIVITY_ARRAY.add(this);
//        myReceiver = new MyReceiver();
    }

    protected void requestWindowFeature(Intent intent) {
    }

    public void setImmersiveStatus(int colorId) {
        if (needStatusTrans && ImmersiveUtil.isSupporImmersive() == 1) {
            getWindow().addFlags(0x04000000); // SDK19:WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (needImmersive) {
                systemBarCompact = new SystemBarCompact(this, true, colorId);
            }
        }
    }
    public HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    public View footView,header;
    public void iniLrvtAdapter(Context mContext, LRecyclerView mlrv,
                            OnItemClickLitener onItemClickLitener,
                            RecyclerView.Adapter adapter, LRecyclerView.LScrollListener lScrollListener, RecyclerView.LayoutManager layoutManager) {
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mContext, adapter);
        mHeaderAndFooterRecyclerViewAdapter.setOnItemClickLitener(onItemClickLitener);
        footView = LayoutInflater.from(mContext).inflate(R.layout.gridview_footer, null);
        footView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, 200));
        mlrv.setLayoutManager(layoutManager);
        mlrv.setLScrollListener(lScrollListener);
        RecyclerViewUtils.setFooterView(mlrv, footView);
        mlrv.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.out_to_right, R.anim.in_from_left);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof AppCompatEditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getRawX() > left && event.getRawX() < right
                    && event.getRawY() > top && event.getRawY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
