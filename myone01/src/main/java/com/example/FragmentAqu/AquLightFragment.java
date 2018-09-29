package com.example.FragmentAqu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.dbabcqu.AquData;
import com.example.myone01.CommonMethodFragment;
import com.example.myone01.R;

import org.litepal.LitePal;
import org.litepal.LitePalDB;
import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;
import org.litepal.exceptions.LitePalSupportException;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;


public class AquLightFragment extends CommonMethodFragment {
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    private LineChartView lineChart;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_light, container, false);
        /***************************************静态折线图********************************************************************************/
        lineChart = view.findViewById(R.id.line_chart_light_AquFra);
            readLightDataAqu();

        return view;
    }
    /***************************************静态折线图方法********************************************************************************/

    //读取光照强度数据
    private void readLightDataAqu() {
        Log.d("AquLightFragment","开始读取光照强度数据");
        List<AquData> lightDatasAqu = LitePal
                .select("id", "time", "lightdata")
                .order("id desc")    //以id为准降序排列
                .find(AquData.class);
        int i=0;
        boolean isFrist_lightFragmnet = true;
        int start_id=1,end_id = 1;
        String data_del = "";
        for (AquData aquData : lightDatasAqu) {
            String timeX = aquData.getTime().substring(aquData.getTime().indexOf("日") + 1
                    , aquData.getTime().length());
            float lightDataAqu = aquData.getLightdata(); // 从数据库获取光照强度
            mAxisXValues.add(new AxisValue(i).setLabel(timeX));
            mPointValues.add(new PointValue(i ,lightDataAqu));
            Log.d("timeX", "Data is " + lightDataAqu + "when time is " + timeX);
            i++;
            initLineChart(2000,0,"光照强度(xl)");//初始化光照强度
            if(isFrist_lightFragmnet){
                start_id = aquData.getId();
                isFrist_lightFragmnet = false;
            }else{
                end_id = aquData.getId();
                data_del =String.valueOf(start_id-720) ;
            }
            if (start_id-end_id>720){
                LitePal.deleteAll(AquData.class,"id=?",data_del);
            }
        }
    }

    private void initLineChart(int maxY,int minY,String nameY){
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //折线的颜色（橙色）
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
        line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        line.setStrokeWidth(1);//设置线的宽度
        line.setPointRadius(3);// 设置节点半径

        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);
        data.setValueLabelBackgroundColor(0x00ffffff);// 设置数据背景颜色
        data.setValueLabelTextSize(5); //设置数据文字大小
        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.GRAY);  //设置字体颜色
        //axisX.setName("date");  //表格名称
        axisX.setTextSize(8);//设置字体大小
        axisX.setMaxLabelChars(6); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数5<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线

        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName(nameY);//y轴标注
        axisY.setTextSize(8);//设置字体大小
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边
//        Viewport port = initViewPort(0,10);//初始化X轴10个间隔坐标
        //设置行为属性，支持缩放、滑动以及平移
//        lineChart.setCurrentViewportWithAnimation(port);
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);  //平移
        lineChart.setMaxZoom((float) 20);//最大方法比例
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
        /**注：下面的5，0只是代表一个数字去类比而已
         * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         */
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.bottom = minY;
        v.top = maxY;
        //固定Y轴的范围,如果没有这个,Y轴的范围会根据数据的最大值和最小值决定,这不是我想要的
        lineChart.setMaximumViewport(v);// 这个方法前设置固定刻度，后设置滑动刻度
        //这2个属性的设置一定要在lineChart.setMaximumViewport(v);这个方法之后,不然显示的坐标数据是不能左右滑动查看更多数据的
        v.left = 0;
        v.right = 7;
        lineChart.setCurrentViewport(v);
    }
}
