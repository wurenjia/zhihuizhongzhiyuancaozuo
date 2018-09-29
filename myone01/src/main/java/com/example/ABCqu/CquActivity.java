package com.example.ABCqu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.app.ProgressDialog;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.ViewportChangeListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import com.example.myone01.CommonMethodActivity;
import com.example.myone01.MainActivity;
import com.example.myone01.R;

public class CquActivity extends CommonMethodActivity {
    private static final String TAG = "CquActivity";
    private LineChartView chart;

    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private Axis axisX;
    private Axis axisY;
    private ProgressDialog progressDialog;
    private boolean isBiss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cqu);
        /*******************************************主界面切换**********************************************/
        //C  to  Main
        Button main_btn_Cqu = findViewById(R.id.main_btn_Cqu);
        main_btn_Cqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUI(CquActivity.this , MainActivity.class);
                finish();
            }
        });
        //C  to A
        Button B_btn_Cqu = findViewById(R.id.B_btn_Cqu);
        B_btn_Cqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUI(CquActivity.this ,AquActivity.class);
                finish();
            }
        });
        //C  to B
        Button Bqu_btn_Cqu = findViewById(R.id.B_btn_Cqu);
        Bqu_btn_Cqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUI(CquActivity.this ,BquActivity.class);
                finish();
            }
        });
    }
}
