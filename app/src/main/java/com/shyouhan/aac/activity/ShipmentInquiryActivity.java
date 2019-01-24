package com.shyouhan.aac.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shyouhan.aac.ProcessType;
import com.shyouhan.aac.R;
import com.shyouhan.aac.adapter.SearchHistoryAdapter;
import com.shyouhan.aac.base.BaseActivity;
import com.shyouhan.aac.bean.PackStatusBean;
import com.shyouhan.aac.constant.AppConstant;
import com.shyouhan.aac.db.DBUtils;
import com.shyouhan.aac.google.zxing.activity.CaptureActivity;
import com.shyouhan.aac.mvp.contract.SearchContract;
import com.shyouhan.aac.mvp.presenter.SearchPresenter;
import com.shyouhan.aac.widget.ToastUtils;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 货件查询
 */
public class ShipmentInquiryActivity extends BaseActivity implements View.OnClickListener, SearchContract.View {
    private AutoLinearLayout llSearchBox;
    private ImageView ivScan;
    private Button btnInquiry;
    private EditText etWaybillNo;
    private SearchPresenter presenter;
    private RecyclerView rvHistory;
    private List<String> pickIdList = new ArrayList<>();
    private DBUtils dbUtils2;
    private List<String> searchHistoryList = new ArrayList<>();
    private SearchHistoryAdapter searchHistoryAdapter;
    private boolean isScanFor = false;
    private View headView;

    @Override
    protected void initView() {
        iniTitlelayout();
        findId();
        dbUtils2 = new DBUtils(ShipmentInquiryActivity.this);
        presenter = new SearchPresenter(ShipmentInquiryActivity.this, ShipmentInquiryActivity.this);
        setTextString();
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

    private void initRv() {
        if (null == searchHistoryAdapter) {
            rvHistory.setLayoutManager(new LinearLayoutManager(mContext));
            searchHistoryAdapter = new SearchHistoryAdapter(searchHistoryList);
            rvHistory.setAdapter(searchHistoryAdapter);

            View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout, null);
            searchHistoryAdapter.setEmptyView(emptyView);
            headView = LayoutInflater.from(this).inflate(R.layout.head_layout, null);
            searchHistoryAdapter.setHeaderView(headView);
            isShowHeadview();
            //第一次 设置左滑删除
            swipTodelete();
            //跳转到搜索结果界面
            searchHistoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    presenter.search(searchHistoryList.get(position));
                }
            });
        } else {
            if (null != headView) {
                isShowHeadview();
            }
            searchHistoryAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 是否显示头部
     */
    private void isShowHeadview() {
        if (searchHistoryList.size() > 0) {
            headView.setVisibility(View.VISIBLE);
        }else{
            headView.setVisibility(View.GONE);
        }
    }

    private void swipTodelete() {
        //左滑删除
        ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//              int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlag = ItemTouchHelper.LEFT;
                return makeMovementFlags(0, swipeFlag);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //加了headlayout，所以减一
                final int position = viewHolder.getAdapterPosition() - 1;
                try {
                    //删除数据库记录
                    dbUtils2.deleteOneRecord(searchHistoryList.get(position));
                    searchHistoryAdapter.onSwipe(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(rvHistory);
    }
    private void findId() {
        llSearchBox = findViewById(R.id.ll_search_box);
        etWaybillNo = findViewById(R.id.et_waybill_no);
        ivScan = findViewById(R.id.iv_scan);
        btnInquiry = findViewById(R.id.btn_inquiry);
        rvHistory = findViewById(R.id.rv_history);

        ivScan.setOnClickListener(this);
        btnInquiry.setOnClickListener(this);
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_shipment_inquiry;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_scan:  //扫描
                Intent intent = new Intent(ShipmentInquiryActivity.this, CaptureActivity.class);
                startActivityForResult(intent, ProcessType.REQUEST_CODE_SEARCH);
                break;
            case R.id.btn_inquiry:  //查询
                if (!TextUtils.isEmpty(etWaybillNo.getText().toString())) {
                    presenter.search(etWaybillNo.getText().toString().trim());
                } else {
                    ToastUtils.showShort(R.string.please_input_waybill_no);
                }
                break;
        }
    }
    @Override
    protected void setTextString() {
        tvTitle.setText(R.string.shippment_inquiry);
        etWaybillNo.setHint(R.string.please_input_waybill_no);
        btnInquiry.setText(R.string.inquiry);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CaptureActivity.RESULT_CODE_QR_SCAN && requestCode == ProcessType.REQUEST_CODE_SEARCH) {
            Bundle extra = data.getExtras();
            String result = extra.getString(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN);
            if (!TextUtils.isEmpty(result)) {
                isScanFor = true;
                presenter.search(result);
            }
        }
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
    public void launchActivity(Intent intent) {
    }
    @Override
    public void killMySelf() {
    }
    @Override
    protected void onResume() {
        super.onResume();
        searchHistoryList.clear();
        searchHistoryList.addAll(dbUtils2.getAllSearchList());
        Collections.reverse(searchHistoryList);
        initRv();
    }
    @Override
    public void onSearchSuccess(PackStatusBean packStatusBean) {
        if (isScanFor){
            //扫描获取到的
            etWaybillNo.setText(packStatusBean.getPack());
            etWaybillNo.setSelection(etWaybillNo.getText().toString().length());
            isScanFor = false;
        }
        //向数据库插入一条查询历史记录
        dbUtils2.insert(AppConstant.TABLE_SEARCH_HISTORY, new String[]{AppConstant.COLUMN_PICKID}, new String[]{packStatusBean.getPack().toString()});
        Intent intent = new Intent(ShipmentInquiryActivity.this, SearchResultActivity.class);
        intent.putExtra(AppConstant.STATUSBEAN, packStatusBean);
        startActivity(intent);
    }

    @Override
    public void onSearchFailed(String error) {
        ToastUtils.showShort(error);
    }
}
