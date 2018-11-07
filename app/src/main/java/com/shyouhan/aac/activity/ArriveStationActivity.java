package com.shyouhan.aac.activity;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shyouhan.aac.ProcessType;
import com.shyouhan.aac.R;
import com.shyouhan.aac.base.BaseActivity;
import com.shyouhan.aac.bean.BaseObject;
import com.shyouhan.aac.constant.AppConstant;
import com.shyouhan.aac.google.zxing.activity.CaptureActivity;
import com.shyouhan.aac.mvp.contract.ArrivePlaceContract;
import com.shyouhan.aac.mvp.presenter.ArrivePlacePresenter;
import com.shyouhan.aac.widget.ToastUtils;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * 抵达站点
 */
public class ArriveStationActivity extends BaseActivity implements ArrivePlaceContract.View{
    List<String> fakePackIds = new ArrayList<>();
    private String realPackNum = "";
    private ArrivePlacePresenter arrivePlacePresenter;  //抵达站点
    private boolean isDuo = false;  //是否是多件扫描
    private String fakeIdStr = "";
    private BottomSheetDialog selectDialog;

    @Override
    protected void initView() {
        iniTitlelayout();
        arrivePlacePresenter = new ArrivePlacePresenter(this,this);
        findId();
        setTextString();
    }
    private void findId() {
        //单件
         findViewById(R.id.ll_danjian).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 showSelectBottomSheet(ProcessType.REQUEST_CODE_ARRIVEPLACE_DAN);
             }
         });
         //多件
        findViewById(R.id.ll_duojian).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectBottomSheet(ProcessType.REQUEST_CODE_ARRIVEPLACE_DUO);
//                intent2CaptureActivity(ProcessType.REQUEST_CODE_ARRIVEPLACE_DUO);  //扫描真单号
            }
        });
    }
    private void intent2CaptureActivity(int code) {
        Intent intent = new Intent(ArriveStationActivity.this, CaptureActivity.class);
        if (code == ProcessType.REQUEST_CODE_FAKE_PACK) {
            intent.putExtra(AppConstant.PROCESS_TYPE, ProcessType.REQUEST_CODE_FAKE_PACK);
        }
        startActivityForResult(intent, code);
    }

    public void showSelectBottomSheet(int scanCode) {
        selectDialog = new BottomSheetDialog(this);
        selectDialog.setCancelable(true);
        selectDialog.setCanceledOnTouchOutside(false);
        selectDialog.setTitle(R.string.please_select_express_no_way);

        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_select, null);
        initContentView(contentView, scanCode);
        selectDialog.setContentView(contentView);

        selectDialog.show();

    }
    public void initContentView(View contentView, final int scanCode) {
        TextView tvManualInput;
        TextView tvScanBarcode;
        tvManualInput = contentView.findViewById(R.id.tv_manual_input);
        tvScanBarcode = contentView.findViewById(R.id.tv_scan_barcode);

        tvManualInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDialog.dismiss();
                showNumberInputDialog(scanCode);
            }
        });
        tvScanBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDialog.dismiss();
                intent2CaptureActivity(scanCode);
            }
        });
        contentView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDialog.dismiss();
            }
        });
    }
    public void showNumberInputDialog(final int scanCode) {
               final EditText editText1 = new EditText(this);
        editText1.setKeyListener(DigitsKeyListener.getInstance("1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM"));
        editText1.setHint(R.string.please_input_express_number);
        editText1.setTextColor(getResources().getColor(R.color.dark_gray));
        editText1.setTextSize(16);
        editText1.setPadding(0,20,0,20);

        final EditText editText2 = new EditText(this);
        editText2.setKeyListener(DigitsKeyListener.getInstance("1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM,"));
        editText2.setHint(R.string.please_input_duo_express_number);
        AlertDialog.Builder builder = new AlertDialog.Builder(ArriveStationActivity.this)
                .setCustomTitle(LayoutInflater.from(this).inflate(R.layout.custom_title, null));
        if (scanCode == ProcessType.REQUEST_CODE_ARRIVEPLACE_DUO){
            AutoLinearLayout autoLinearLayout = new AutoLinearLayout(this);
            autoLinearLayout.setOrientation(AutoLinearLayout.VERTICAL);
            autoLinearLayout.setPadding(10,0,10,0);

            editText2.setTextColor(getResources().getColor(R.color.dark_gray));
            editText2.setTextSize(16);
            editText2.setPadding(0,20,0,20);

            autoLinearLayout.addView(editText1);
            autoLinearLayout.addView(editText2);

            builder .setView(autoLinearLayout);
        }else{
            builder .setView(editText1);
        }

//        builder .setView(editText1)
        builder .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = null;
                        String result1 = editText1.getText().toString().trim();
                        if (scanCode == ProcessType.REQUEST_CODE_ARRIVEPLACE_DUO){
                            //多件
                            String result2 = editText2.getText().toString().trim();
                            if (!TextUtils.isEmpty(result1) && !TextUtils.isEmpty(result2)) {
                                arrivePlacePresenter.arrivePlace(result1,result2);
                            }
                        }else{
                            //单件
                            if (!TextUtils.isEmpty(result1)) {
                                arrivePlacePresenter.arrivePlace(result1,"");
                            }
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CaptureActivity.RESULT_CODE_QR_SCAN) {
            Bundle extra = data.getExtras();
            String result = extra.getString(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN);
            switch (requestCode) {
                case ProcessType.REQUEST_CODE_ARRIVEPLACE_DAN:
                    showDialogDan(result);
                    break;
                case ProcessType.REQUEST_CODE_ARRIVEPLACE_DUO:
                    realPackNum = result;
                    showDialogDuo1(result);
                    break;
                case ProcessType.REQUEST_CODE_FAKE_PACK:
                    if (!fakePackIds.contains(result)){
                        fakePackIds.add(result);
                    }else{
                        ToastUtils.showShort(R.string.please_do_not_scan_again);
                    }
                    showDialogDuo(result);
                    break;
            }
        }
    }
    //确认并继续扫描多件
    protected void showDialogDuo1(String result) {
        String string1 = "";
        String string2 = "";
        string1 = getString(R.string.waybill_no3) + " ";
        string2 = getString(R.string.ok_continue_scan_duojian);
        showdialog(ArriveStationActivity.this, string1 + result + "\r\n" + string2, new CallBack() {
            @Override
            public void onConfirm() {
                intent2CaptureActivity(ProcessType.REQUEST_CODE_FAKE_PACK);  //扫描假件

            }

            @Override
            public void onSelect(View view) {

            }
        });

    }

    private void showDialogDuo(String result) {
        String string1 = "";
        String string2 = "";
        string1 = getString(R.string.waybill_no3) + " ";
        string2 = getString(R.string.is_sure_to_arriveplace);
         fakeIdStr = getFakeIdStr(fakePackIds);
        showDialogDuo(ArriveStationActivity.this,string1 + result + "\r\n" + string2 , new CallBack() {
            @Override
            public void onConfirm() {
            }
            @Override
            public void onSelect(View view) {
                switch (view.getId()) {
                    case R.id.tv_queren:
                        isDuo = true;
                        arrivePlacePresenter.arrivePlace(realPackNum,fakeIdStr);
                        break;
                    case R.id.tv_queren_continue:
                        intent2CaptureActivity(ProcessType.REQUEST_CODE_FAKE_PACK);
                        break;
                    case R.id.tv_cancel:
                        //取消
                        realPackNum = "";
                        fakePackIds.clear();
                        break;
                }
            }
        });
    }
    /**
     * list转化为 字符串
     * @param fakePackIds
     */
    private String getFakeIdStr(List<String> fakePackIds) {
        if ( fakePackIds.size()>0) {
            String fakeIds = "";
            for (String fakeId: fakePackIds) {
                fakeIds = fakeIds.concat(fakeId+",");
            }
            return fakeIds.substring(0,fakeIds.length()-1);
        }else{
            return "";
        }
    }

    private void showDialogDan(final String result) {
        String string1 = "";
        String string2 = "";
        string1 = getString(R.string.waybill_no3) + " ";
        string2 = getString(R.string.is_sure_to_arriveplace);
        showdialog(ArriveStationActivity.this, string1 + result + "\r\n" + string2, new CallBack() {
            @Override
            public void onConfirm() {
                    arrivePlacePresenter.arrivePlace(result,"");
            }

            @Override
            public void onSelect(View view) {

            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_arrive_station;
    }


    @Override
    protected void setTextString() {
        tvTitle.setText(R.string.arrival_station);
    }
    @Override
    public void showLoading() {
        loadingDailog.show();
    }

    @Override
    public void hideLoading() {
        loadingDailog.hide();
    }

    @Override
    public void showMessage(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void launchActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void killMySelf() {

    }

    @Override
    public void onArrivePlaceSuccess(BaseObject baseObject) {
        realPackNum = "";
        fakePackIds.clear();
        Intent intent = new Intent(ArriveStationActivity.this, DoSuccessActivity.class);
        intent.putExtra(AppConstant.PROCESS_TIME, baseObject.getTime());
        intent.putExtra(AppConstant.PROCESS_TYPE, ProcessType.REQUEST_CODE_ARRIVEPLACE);
        if (isDuo){
            isDuo = false;
            intent.putExtra(AppConstant.FAKEIDSTR,fakeIdStr);
        }
        startActivity(intent);
    }

    @Override
    public void onArrivePlaceFailed(String error) {
        realPackNum = "";
        fakePackIds.clear();
        if (isDuo) {
            isDuo = false;
        }
        showMessage(error);

    }
}
