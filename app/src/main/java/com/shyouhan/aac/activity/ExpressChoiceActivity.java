package com.shyouhan.aac.activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shyouhan.aac.R;
import com.shyouhan.aac.adapter.ExpressChoiceAdapter;
import com.shyouhan.aac.base.BaseActivity;
import com.shyouhan.aac.bean.ExpressBean;
import com.shyouhan.aac.constant.AppConstant;
import com.shyouhan.aac.mvp.contract.ExpressContract;
import com.shyouhan.aac.mvp.presenter.ExpressPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 快递选择
 */
public class ExpressChoiceActivity extends BaseActivity implements ExpressContract.View{
    private RecyclerView recyclerView;
    private Button btnConfirm;
    private ExpressChoiceAdapter adapter;
    private List<ExpressBean> datas = new ArrayList<>();
    private ExpressPresenter expressPresenter;
    private int RESULT_CODE_TRANSFER = 101;
    @Override
    protected void initView() {
        iniTitlelayout();
        findId();
        setTextString();
//        initRv();
         expressPresenter = new ExpressPresenter(ExpressChoiceActivity.this, ExpressChoiceActivity.this);
        expressPresenter.getExpress();
        selectExpress();
    }

    private void selectExpress() {

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(AppConstant.SELECTED_EXPRESSBEAN,getSelectExpressBean());
                setResult(RESULT_CODE_TRANSFER,intent);
                ExpressChoiceActivity.this.finish();

            }
        });
    }

    /**
     * 获取选中的expressBean
     * @return
     */
    private ExpressBean getSelectExpressBean() {
        for (ExpressBean expressBean:
                datas) {
            if (expressBean.getSelected()) {
                return expressBean;
            }
        }
        return null;
    }

    private void initRv() {
//       datas =  datas.subList(0,4);
        adapter = new ExpressChoiceAdapter(datas);
        recyclerView.setLayoutManager(new LinearLayoutManager(ExpressChoiceActivity.this));
        recyclerView.setAdapter(adapter);
        /**
         * 选中快递
         */
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ExpressBean expressBean = datas.get(position);
                if (!expressBean.getSelected()) {
                    expressBean.setSelected(true);
                    for (int i = 0; i < datas.size() ; i++) {
                        if (i != position) {
                            datas.get(i).setSelected(false);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }
    private void findId() {
        recyclerView = findViewById(R.id.rv);
        btnConfirm = findViewById(R.id.btn_confirm);
        btnConfirm.setVisibility(View.INVISIBLE);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_express_choice;
    }

    @Override
    protected void setTextString() {
        tvTitle.setText(R.string.express_choice);
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
    public void onGetExpressSuccess(List<ExpressBean> baseObject) {
        datas.clear();
        datas.addAll(baseObject);
        for (ExpressBean bean:baseObject
             ) {
            if (baseObject.indexOf(bean) == 0){
                bean.setSelected(true);
            }else{
                bean.setSelected(false);
            }
        }
        initRv();
        btnConfirm.setVisibility(View.VISIBLE);
    }
    @Override
    public void onGetExpressFailed(String error) {

    }

}
