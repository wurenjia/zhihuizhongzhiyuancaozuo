package com.example.ABCqu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.FragmentAqu.AquAirMstFragment;
import com.example.FragmentAqu.AquAirTempFragment;
import com.example.FragmentAqu.AquLightFragment;
import com.example.FragmentAqu.AquSoilMstFragment;
import com.example.FragmentAqu.AquSoilTempFragment;
import com.example.myone01.CommonMethodActivity;
import com.example.myone01.MainActivity;
import com.example.myone01.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class BquActivity extends CommonMethodActivity {
    LineChartView lineChartView;
    List<PointValue> pointValues = new ArrayList<PointValue>();// 节点数据结合
    ArrayList<PointValue> pointValueList;
    ArrayList<Line> linesList;
    ArrayList<PointValue> points;
    private Axis axisX;
    private Axis axisY;
    LineChartData lineChartData;
    Timer timer;
    private int position = 0;
    private boolean isFinish = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bqu);
        SQLiteStudioService.instance().start(this);

        /*******************************************主界面切换**********************************************/
        //B  to  Main
        Button main_btn_Bqu = findViewById(R.id.main_btn_Bqu);
        main_btn_Bqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUI(BquActivity.this , MainActivity.class);
                finish();
            }
        });
        //B  to A
        Button A_btn_Bqu = findViewById(R.id.A_btn_Bqu);
        A_btn_Bqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUI(BquActivity.this ,AquActivity.class);
                finish();
            }
        });
        //B  to C
        Button C_btn_Bqu = findViewById(R.id.C_btn_Bqu);
        C_btn_Bqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUI(BquActivity.this ,CquActivity.class);
                finish();
            }
        });
    }    /*******************************************设置变换碎片界面点击事件**********************************************/




}
