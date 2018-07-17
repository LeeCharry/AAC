package com.shyouhan.aac.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shyouhan.aac.R;
import com.shyouhan.aac.base.BaseActivity;
import com.shyouhan.aac.bean.BaseObject;
import com.shyouhan.aac.bean.ExpressBean;
import com.shyouhan.aac.bean.PackStatusBean;
import com.shyouhan.aac.constant.AppConstant;
import com.shyouhan.aac.google.zxing.activity.CaptureActivity;
import com.shyouhan.aac.mvp.contract.SearchContract;
import com.shyouhan.aac.mvp.contract.TransferContract;
import com.shyouhan.aac.mvp.presenter.SearchPresenter;
import com.shyouhan.aac.mvp.presenter.TransferPresenter;
import com.shyouhan.aac.widget.DateUtil;
import com.shyouhan.aac.widget.ToastUtils;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;


import java.util.List;
import java.util.Locale;


/**
 * 国内转运
 */
public class DomesticTransferActivity extends BaseActivity implements View.OnClickListener,TransferContract.View,SearchContract.View{
    private TextView tvWaybillNo;
    private TextView tvDateAddr;
    private TextView tvSelect;
    private AutoRelativeLayout rlSelect;
    private EditText etWaybillNo;
    private ImageView ivScan;
    private Button btnInquiry;
    private static final int REQUEST_CODE_SELECT_EXPRESS = 99;
    private static final int REQUEST_CODE_SCAN_EXPRESS = 100;
    private ExpressBean selectExpressBean;
    private String pickId;
    private TransferPresenter transferPresenter;
    private SearchPresenter searchPresenter;
    private String expressNo;  //选择的国内快递运单编号
    private TextView tvExpressName;

    @Override
    protected void initView() {
        iniTitlelayout();
        transferPresenter = new TransferPresenter(DomesticTransferActivity.this, DomesticTransferActivity.this);
        searchPresenter = new SearchPresenter(DomesticTransferActivity.this, DomesticTransferActivity.this);
        findId();
        String pickIdStr = getIntent().getStringExtra(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN);
        if (null != pickIdStr) {
         pickId = pickIdStr;
         //查询订单状态
         searchPresenter.search(pickIdStr.trim());
        }
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
    private void findId() {
        tvWaybillNo = findViewById(R.id.tv_waybill_no);
        tvDateAddr = findViewById(R.id.tv_date_addr);
        tvExpressName = findViewById(R.id.tv_express_name);
        tvSelect = findViewById(R.id.tv_select);
        rlSelect = findViewById(R.id.rl_select);
        etWaybillNo = findViewById(R.id.et_waybill_no);
        ivScan = findViewById(R.id.iv_scan);
        btnInquiry = findViewById(R.id.btn_inquiry);
        findViewById(R.id.iv_select).setOnClickListener(this);
        btnInquiry.setOnClickListener(this);
        ivScan.setOnClickListener(this);
        rlSelect.setOnClickListener(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_domestic_transfer;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_select:
            case R.id.rl_select:  //选择国内快递
                Intent intent = new Intent(DomesticTransferActivity.this, ExpressChoiceActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT_EXPRESS);
                break;
            case R.id.iv_scan:  //扫一扫
                startActivityForResult(new Intent(DomesticTransferActivity.this, CaptureActivity.class),REQUEST_CODE_SCAN_EXPRESS);
                break;
            case R.id.btn_inquiry:  //确定
                if (checkInfo()){
                    final String string1 = getString(R.string.waybill_no3)+" ";
                    final String string2 = getString(R.string.whether_to_confirm_the_transfer);
                    showdialog(DomesticTransferActivity.this, string1 + pickId +"\r\n"+ string2 + getSelectedName(selectExpressBean) + "？", new CallBack() {
                        @Override
                        public void onConfirm() {
                            transferPresenter.transfer(pickId,selectExpressBean.getId(),expressNo);
                        }
                    });
                }
                break;
        }
    }

    private boolean checkInfo() {
        expressNo = etWaybillNo.getText().toString().trim();
        if (null == selectExpressBean) {
            ToastUtils.showShort(R.string.Please_choose_domestic_express);
             return false;
        }
        if (TextUtils.isEmpty(expressNo)) {
            ToastUtils.showShort(R.string.Waybill_number_cannot_be_empty);
            return false;
        }
        return true;
    }
    /**
     * 获取选中的快递名称
     * @param selectExpressBean
     * @return
     */
    private String getSelectedName(ExpressBean selectExpressBean) {
        if (getResources().getConfiguration().locale == Locale.TRADITIONAL_CHINESE) {
            return selectExpressBean.getTraname();
        }else{
            return selectExpressBean.getName();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_EXPRESS && resultCode == 101) {
             selectExpressBean = (ExpressBean) data.getSerializableExtra(AppConstant.SELECTED_EXPRESSBEAN);
            if (isTranditional()) {
                tvExpressName.setText(selectExpressBean.getTraname());
            }else{
                tvExpressName.setText(selectExpressBean.getName());
            }
        }else  if (resultCode == CaptureActivity.RESULT_CODE_QR_SCAN && requestCode == REQUEST_CODE_SCAN_EXPRESS) {
            Bundle extra = data.getExtras();
            String result = extra.getString(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN);
            etWaybillNo.setText(result);
            etWaybillNo.setSelection(etWaybillNo.getText().toString().length());
            expressNo = result;
        }
    }

    @Override
    protected void setTextString() {
        tvTitle.setText(R.string.domestic_transfer);
        tvWaybillNo.setText(getString(R.string.waybill_no3)+" "+pickId);
        tvSelect.setText(R.string.select_guonei_kuaidi);
        etWaybillNo.setHint(R.string.please_input_waybill_no);
        btnInquiry.setText(R.string.confirm);
    }

    @Override
    public void onTransferSuccess(BaseObject baseObject) {
        if (getLocaLanguage().equals(AppConstant.zh_TW)) {
            ToastUtils.showShort(baseObject.getTramsg());
        }else{
            ToastUtils.showShort(baseObject.getMsg());
        }
        this.finish();
    }

    @Override
    public void onTransferFailed(String error) {
        ToastUtils.showShort(error);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
    public void onSearchSuccess(PackStatusBean packStatusBean) {
        List<PackStatusBean.StatusBean> status =  packStatusBean.getData();
        for (PackStatusBean.StatusBean statusBean:
             status) {
            if (null !=  statusBean.getTime()) {
                Long time = statusBean.getTime();
                tvDateAddr.setText(DateUtil.getDate2(time)+"   "+getStatusDesc(statusBean));
            }
        }
    }

    private String getStatusDesc(PackStatusBean.StatusBean statusBean) {
        if (isTranditional()) {
            return statusBean.getTradesc();
        }else{
            return statusBean.getDesc();
        }
    }

    @Override
    public void onSearchFailed(String error) {

    }
}
