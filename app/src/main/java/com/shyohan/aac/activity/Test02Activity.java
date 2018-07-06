package com.shyohan.aac.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import acc.tulip.com.accreputation.R;

public class Test02Activity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test02);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        //显示dialog
        findViewById(R.id.btn_show_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog();
            }
        });
         findViewById(R.id.tv_middle).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ToastUtils.showShort("      ");
             }
         });
        LinearLayout llRight = findViewById(R.id.ll_right);
        LogUtils.a(MainActivity.TAG, "透明度:"+llRight.getAlpha()+"    ");
        Button btn_show_dialog = findViewById(R.id.btn_show_dialog);
        LogUtils.a(MainActivity.TAG, "透明度:"+btn_show_dialog.getAlpha()+"    ");
        TextView tv_middle = findViewById(R.id.tv_middle);
        LogUtils.a(MainActivity.TAG, "透明度:"+tv_middle.getAlpha()+"    ");
        findViewById(R.id.ll_right).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ToastUtils.showShort("      ");
             }
         });
    }
    /**
     * 显示自定义对话框
     */
    private void showdialog() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_logout, null);
        final Dialog dialog = new Dialog(this, R.style.style_logout_dialog);
        contentView.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        contentView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(contentView);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}
