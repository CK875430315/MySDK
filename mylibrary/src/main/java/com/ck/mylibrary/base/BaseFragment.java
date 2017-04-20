package com.ck.mylibrary.base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ck.mylibrary.R;
import com.ck.mylibrary.utils.Core;
import com.ck.mylibrary.utils.Permission;
import com.ck.mylibrary.utils.SpUtils;
import com.ck.mylibrary.view.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.ck.mylibrary.view.recyclerview.LRecyclerView;
import com.ck.mylibrary.view.recyclerview.interfaces.OnItemClickLitener;
import com.ck.mylibrary.view.recyclerview.util.RecyclerViewUtils;


/**
 * Created by CK on 2016/10/20.
 */
public abstract class BaseFragment extends Fragment {
    public static final String TAG = "sssss";
    private boolean isVisiable;
    private boolean isViewInit;
    private boolean isDataFinish;
//    protected String appid= ConfigUtils.APPID;
    protected String imei="";
//    protected String pt = ConfigUtils.PT;
    protected String ver="";
    public Context mContext;
    private boolean isFirsrt=true;
    protected String uid="0";
    public  Permission.Builder permission;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        permission= new Permission.Builder();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initInfo();
        return inflater.inflate(getLayoutId(), container, false);
    }
    @Override
    public void onResume() {
        super.onResume();
        uid = SpUtils.getSP(mContext,"uid");
    }
    public HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    public View footView;
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void initInfo() {
         uid = SpUtils.getSP(mContext,"uid");
        if(isFirsrt){
            isFirsrt=false;
            Core core = new Core(mContext);
            imei = core.getIMEI();
            try {
                ver = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    protected abstract int getLayoutId();
}
