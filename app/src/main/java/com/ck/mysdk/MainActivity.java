package com.ck.mysdk;

import android.os.Bundle;
import android.view.View;

import com.ck.mylibrary.base.BaseActivity;
import com.ck.mylibrary.utils.ToastUtils;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        needImmersive=false;
        setImmersiveStatus(R.color.color_00a5eb);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main,true);
    }

    public void sdk(View view) {
        ToastUtils.showToast(this,"哈哈哈");
    }
}
