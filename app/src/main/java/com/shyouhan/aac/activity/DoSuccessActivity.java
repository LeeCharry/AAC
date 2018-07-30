package com.shyouhan.aac.activity;


import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shyouhan.aac.ProcessType;
import com.shyouhan.aac.R;
import com.shyouhan.aac.base.BaseActivity;
import com.shyouhan.aac.constant.AppConstant;
import com.shyouhan.aac.widget.DateUtil;


/**
 * 派送
 */
public class DoSuccessActivity extends BaseActivity {

    private TextView tvDate;
    private TextView tvContent;
    private Button btnConfirm;
    private int processType;
    private Long processTime;

    @Override
    protected void initView() {
        iniTitlelayout();
        findId();
         processType = getIntent().getIntExtra(AppConstant.PROCESS_TYPE, -1);
         processTime = getIntent().getLongExtra(AppConstant.PROCESS_TIME, 0);
        setTextString();
    }
    private void findId() {
        tvDate = findViewById(R.id.tv_date);
        tvContent = findViewById(R.id.tv_is_sign);
        btnConfirm = findViewById(R.id.btn_confirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoSuccessActivity.this.finish();
            }
        });

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_do_success;
    }


    @Override
    protected void setTextString() {
        switch (processType) {
            case ProcessType.REQUEST_CODE_REWARD:
                tvTitle.setText(R.string.reward);
                tvContent.setText(R.string.salesman_has_received_the_shipment);
                break;
            case ProcessType.REQUEST_CODE_SENDING:
                tvTitle.setText(R.string.shipping);
                tvContent.setText(R.string.shipment_loaded_and_sent_to_destination);
                break;
            case ProcessType.REQUEST_CODE_ARRIVEPLACE:
                tvTitle.setText(R.string.arrival_station);
                if (null !=  getIntent().getStringExtra(AppConstant.FAKEIDSTR)) {
                    String fakeIsStr = getIntent().getStringExtra(AppConstant.FAKEIDSTR);
                    if (!TextUtils.isEmpty(fakeIsStr)) {
                        tvContent.setText("（"+fakeIsStr+"）\r\n"+getString(R.string.shipments_arrive_at_Station));
                    }else{
                        tvContent.setText(R.string.shipments_arrive_at_Station);
                    }
                }else{
                    tvContent.setText(R.string.shipments_arrive_at_Station);
                }
                break;
            case ProcessType.REQUEST_CODE_DELIVERY:
                tvTitle.setText(R.string.delivery);
                tvContent.setText(R.string.has_delivery);
                break;
            case ProcessType.REQUEST_CODE_SIGNING:
                tvTitle.setText(R.string.signing);
                tvContent.setText(R.string.has_sign);
                break;
        }
//        new SimpleDateFormat("").format(new Date())
        tvDate.setText(DateUtil.getDate2(processTime));
        btnConfirm.setText(R.string.confirm);
    }
}
