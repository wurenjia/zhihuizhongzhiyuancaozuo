package com.example.myone01;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ViewportChangeListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

public class CommonMethodFragment extends Fragment{


    //动态折线图
    public static final String TAG = "MainActivity";
    public LineChartView chart;

    public boolean hasAxes = true;
    public boolean hasAxesNames = true;
    public Axis axisX;
    public Axis axisY;

    public ProgressDialog progressDialog;
    public boolean isBiss;



    /***************************************************************************************************/
    /**
     * 动态添加点到图表
     * @param count
     */
    private void addDataPoint(int count) {
        Line line = chart.getLineChartData().getLines().get(0);
        List<PointValue> values = line.getValues();
        int startIndex = values.size();

        for (int i = 0; i < count; i++) {
            int newIndex = -startIndex - i;
            Log.i(TAG, "addDataPoint: newIndex=" + newIndex);
            values.add(new PointValue(newIndex, (float) Math.random() * 100f));
        }

        line.setValues(values);
        List<Line> lines = new ArrayList<>();
        lines.add(line);
        LineChartData lineData = new LineChartData(lines);
        lineData.setAxisXBottom(axisX);
        lineData.setAxisYLeft(axisY);
        chart.setLineChartData(lineData);

        //根据点的横坐标实时变换X坐标轴的视图范围
        Viewport port = initViewPort(-startIndex+1,-startIndex+10);
//        chart.setMaximumViewport(port);
        chart.setCurrentViewport(port);

        final float firstXValue = values.get(values.size()-1).getX();

        chart.setViewportChangeListener(new ViewportChangeListener() {
            @Override
            public void onViewportChanged(Viewport viewport) {
                Log.i(TAG, "onViewportChanged: " + viewport.toString());
                if (!isBiss && viewport.left == firstXValue) {
                    isBiss = true;
                    loadData();
                }
            }
        });
    }

    /**
     * 模拟网络请求动态加载数据
     */
    private void loadData() {
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addDataPoint(20);
                            isBiss = false;
                            progressDialog.dismiss();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 设置视图
     * @param left
     * @param right
     * @return
     */
    private Viewport initViewPort(float left, float right) {
        Viewport port = new Viewport();
        port.top = 100;//Y轴上限，固定(不固定上下限的话，Y轴坐标值可自适应变化)
        port.bottom = 0;//Y轴下限，固定
        port.left = left;//X轴左边界，变化
        port.right = right;//X轴右边界，变化
        return port;
    }

    /**
     * 初始化图表
     * @param numberOfPoints 初始数据
     */
    public void generateData(int numberOfPoints) {

        List<Line> lines = new ArrayList<Line>();
        int numberOfLines = 1;
        List<PointValue> values = null;
        for (int i = 0; i < numberOfLines; ++i) {

            values = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; j++) {
                int newIndex = j * -1;
                Log.i(TAG, "generateData: newIndex=" + newIndex);
                values.add(new PointValue(newIndex, (float) Math.random() * 100f));
            }

            Line line = new Line(values);
            line.setColor(ChartUtils.COLORS[i]);
            line.setShape(ValueShape.CIRCLE);
            line.setCubic(false);//曲线是否平滑，即是曲线还是折线
            line.setFilled(false);//是否填充曲线的面积
            line.setHasLabels(false);//曲线的数据坐标是否加上备注
            line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
            line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
            line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
            lines.add(line);
        }

        LineChartData data = new LineChartData(lines);

        if (hasAxes) {
            axisX = new Axis();
            axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("Axis X");
                axisY.setName("Axis Y");
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);

        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        data.setBaseValue(Float.NEGATIVE_INFINITY);
        chart.setLineChartData(data);

        final float firstXValue = values.get(values.size() - 1).getX();
        Viewport v = new Viewport(chart.getMaximumViewport());
        v.left = -9;
        v.right = 0;
        chart.setCurrentViewport(v);
        chart.setViewportChangeListener(new ViewportChangeListener() {
            @Override
            public void onViewportChanged(Viewport viewport) {
                Log.i(TAG, "onViewportChanged: " + viewport.toString());
                if (!isBiss && viewport.left == firstXValue) {
                    isBiss = true;
                    loadData();
                }
            }
        });
    }
    /*******************************************延时方法*****************************************************/
    //001
    public void delay(long Millis){
        new Handler().postDelayed(new Runnable(){
            public void run() {
                //execute the task
            }
        },1000);
    }
    /************************************************获取当前时间**************************************************************/
    public String getNewTime(){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        Log.d("AquLightFragment:","Date获取当前日期时间"+simpleDateFormat.format(date));
        return simpleDateFormat.format(date);
    }
}
