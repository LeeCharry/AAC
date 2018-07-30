package com.shyouhan.aac.activity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shyouhan.aac.R;
import com.shyouhan.aac.adapter.SiteRvAdapter;
import com.shyouhan.aac.base.BaseActivity;
import com.shyouhan.aac.bean.PlaceBean;
import com.shyouhan.aac.mvp.contract.GetPlaceContract;
import com.shyouhan.aac.mvp.presenter.GetPlacePresenter;
import com.shyouhan.aac.widget.ToastUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 站点查询
 */
public class SiteInquiryActivity extends BaseActivity implements GetPlaceContract.View {
    private RecyclerView rvSites;
    private List<PlaceBean> datas = new ArrayList<>();
    private SiteRvAdapter adapter;
    private GetPlacePresenter presenter;
    public int position;

    @Override
    protected void initView() {
        iniTitlelayout();
        tvTitle.setText(R.string.site_inquiry);
        findId();
        initRv();
        presenter = new GetPlacePresenter(SiteInquiryActivity.this, SiteInquiryActivity.this);
        presenter.getPlace();
        setTextString();
    }

    private void initRv() {
        rvSites.setLayoutManager(new LinearLayoutManager(SiteInquiryActivity.this));
        adapter = new SiteRvAdapter(datas);
        rvSites.setAdapter(adapter);

        //call
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                SiteInquiryActivity.this.position = position;
                showdialog(mContext, datas.get(position).getPhone(), new CallBack() {
                    @Override
                    public void onConfirm() {
                        requestCallPermission();
                    }

                    @Override
                    public void onSelect(View view) {

                    }
                });
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCallPermission() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + datas.get(position).getPhone()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
            return;
        }
        mContext.startActivity(intent);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                    ToastUtils.showShort(R.string.permission_request_failed);
                    return;
                }
            }
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + datas.get(position).getPhone()));
            startActivity(intent);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + datas.get(position).getPhone()));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                return;
            }
            startActivity(intent);
        }
    }

    private void findId() {
        rvSites = findViewById(R.id.rv_sites);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_site_inquiry;
    }

    @Override
    protected void setTextString() {
    }
    @Override
    public void showLoading() {
        loadingDailog.show();
    }

    @Override
    public void hideLoading() {
        loadingDailog.dismiss();
    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (adapter != null) {
//            adapter.notifyDataSetChanged();
//        }
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMySelf() {

    }

    @Override
    public void onGetPlaceSuccess(List<PlaceBean> placeBeanList) {
            datas.clear();
            datas.addAll(placeBeanList);
            adapter.notifyDataSetChanged();
    }

    @Override
    public void onGetPlaceFailed(String error) {
        ToastUtils.showShort(error);
    }
}
