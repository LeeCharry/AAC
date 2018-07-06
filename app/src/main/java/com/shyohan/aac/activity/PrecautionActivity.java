package com.shyohan.aac.activity;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shyohan.aac.base.BaseActivity;

import acc.tulip.com.accreputation.R;

/**
 * 注意事项
 */
public class PrecautionActivity extends BaseActivity {
    private TextView tvQuestionAnswer;
    private TextView tvInstructions;
    private TextView tvConsignmentNotes;

    @Override
    protected void initView() {
        iniTitlelayout();
        tvTitle.setText(R.string.precaution);
        findId();
        onBack("");
    }

    private void findId() {
        /**
         * Q & A
         */
        tvQuestionAnswer = findViewById(R.id.tv_question_answer);
        tvInstructions = findViewById(R.id.tv_instructions);
        tvConsignmentNotes = findViewById(R.id.tv_consignment_notes);

        tvQuestionAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        /**
         * APP 操作说明
         */
        tvInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        /**
         * 托运须知
         */
        tvConsignmentNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_precaution;
    }

    @Override
    protected void setTextString() {
        tvTitle.setText(R.string.precaution);
        tvQuestionAnswer.setText(R.string.question_and_answer);
        tvInstructions.setText(R.string.app_instructions);
        tvConsignmentNotes.setText(R.string.consignment_notes);
    }
}
