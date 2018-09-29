package com.example.myone01;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class ControlActivity extends CommonMethodActivity {
    /********************************************定义控制按钮**************************************************************/
    Button spirr_open_btn_Cont; // 喷灌开关
    Button spirr_close_btn_Cont;

    Button nts_open_btn_Cont; //营养液开关
    Button nts_close_btn_Cont;

    Button nosun_open_btn_Cont; //遮阳开关
    Button nosun_close_btn_Cont;

    Button addmst_open_Cont;  //加湿开光
    Button addmst_close_Cont;

    Button coor_open_btn_Cont;  //冷气开关
    Button coor_close_btn_Cont;

    Button heating_open_btn_Cont;//暖气开关
    Button heating_close_btn_Cont;

    Button addlight_open_btn_Cont;// 补光开关
    Button addlight_close_btn_Cont;

    Button airin_open_all_btn_Cont;  //进风口开关
    Button airin_open_half_btn_Cont;
    Button airin_close_btn_Cont;

    Button airout_open_all_btn_Cont; //排风口开关
    Button airout_open_half_btn_Cont;
    Button airout_close_btn_Cont;
    /********************************************定义原料拖动条**************************************************************/
    SeekBar yuanliao1_jd_control; //KH2PO4
    SeekBar yuanliao2_jd_control; //KNO3
    SeekBar yuanliao3_jd_control; //Ca(NO3)2
    SeekBar yuanliao4_jd_control; //水
    /********************************************定义原料比例显示文本框**************************************************************/
    TextView yuanliao1_data_control;
    TextView yuanliao2_data_control;
    TextView yuanliao3_data_control;
    TextView yuanliao4_data_control;
    /********************************************定义开关控制代码默认值**************************************************************/
    //      暖气         冷气    加湿         补光       通风风机     遮阳     *A、B、C区通水喷灌
    String heating="0",coor="0",addmst="0",addlight="0",airfluent="0",nosun="0",a="0",b="0",c="0";
   //A、B、C区通营养液
    String A="0",B="0",C="0";
    /********************************************定义并初始化请求代码**************************************************************/
    static String B2Code="B20/0/0/0/0/0/0/0/0";
    static String B4Code="B40/0/0";
    /********************************************营养液是否为可操作状态**************************************************************/
    static boolean state_nts_Cont = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        /********************************************实例化控制按钮**************************************************************/
        spirr_open_btn_Cont = findViewById(R.id.spirr_open_btn_Cont); // 喷灌开关
        spirr_close_btn_Cont = findViewById(R.id.spirr_close_btn_Cont);

        nts_open_btn_Cont = findViewById(R.id.nts_open_btn_Cont);  //营养液开关
        nts_close_btn_Cont = findViewById(R.id.nts_close_btn_Cont);

        nosun_open_btn_Cont = findViewById(R.id.nosun_open_btn_Cont); //遮阳开关
        nosun_close_btn_Cont = findViewById(R.id.nosun_close_btn_Cont);

        addmst_open_Cont = findViewById(R.id.addmst_open_Cont);  //加湿开光
        addmst_close_Cont = findViewById(R.id.addmst_close_Cont);

        coor_open_btn_Cont = findViewById(R.id.coor_open_btn_Cont);  //冷气开关
        coor_close_btn_Cont = findViewById(R.id.coor_close_btn_Cont);

        heating_open_btn_Cont = findViewById(R.id.heating_open_btn_Cont);//暖气开关
        heating_close_btn_Cont = findViewById(R.id.heating_close_btn_Cont);

        addlight_open_btn_Cont = findViewById(R.id.addlight_open_btn_Cont);// 补光开关
        addlight_close_btn_Cont = findViewById(R.id.addlight_close_btn_Cont);

        airin_open_all_btn_Cont = findViewById(R.id.airin_open_all_btn_Cont);  //进风口开关
        airin_open_half_btn_Cont = findViewById(R.id.airin_open_half_btn_Cont);
        airin_close_btn_Cont = findViewById(R.id.airin_close_btn_Cont);

        airout_open_all_btn_Cont = findViewById(R.id.airout_open_all_btn_Cont); //排风口开关
        airout_open_half_btn_Cont = findViewById(R.id.airout_open_half_btn_Cont);
        airout_close_btn_Cont = findViewById(R.id.airout_close_btn_Cont);

        /********************************************建立按钮点击事件**************************************************************/
        spirr_open_btn_Cont.setOnClickListener(sendAskData);  //喷灌
        spirr_close_btn_Cont.setOnClickListener(sendAskData);

        nts_open_btn_Cont.setOnClickListener(sendAskData);  //营养液
        nts_close_btn_Cont.setOnClickListener(sendAskData);

        nosun_open_btn_Cont.setOnClickListener(sendAskData);     //遮阳
        nosun_close_btn_Cont.setOnClickListener(sendAskData);

        addmst_open_Cont.setOnClickListener(sendAskData);      //加湿
        addmst_close_Cont.setOnClickListener(sendAskData);

        coor_open_btn_Cont.setOnClickListener(sendAskData);     //冷气
        coor_close_btn_Cont.setOnClickListener(sendAskData);

        heating_open_btn_Cont.setOnClickListener(sendAskData);     //暖气
        heating_close_btn_Cont.setOnClickListener(sendAskData);

        addlight_open_btn_Cont.setOnClickListener(sendAskData);     //补光
        addlight_close_btn_Cont.setOnClickListener(sendAskData);

        airin_open_all_btn_Cont.setOnClickListener(sendAskData);     //进风
        airin_open_half_btn_Cont.setOnClickListener(sendAskData);
        airin_close_btn_Cont.setOnClickListener(sendAskData);

        airout_open_all_btn_Cont.setOnClickListener(sendAskData);     //排风
        airout_open_half_btn_Cont.setOnClickListener(sendAskData);
        airout_close_btn_Cont.setOnClickListener(sendAskData);

        /************************************************显示进度*************************************************/
        //定义显示数据
        yuanliao1_data_control = findViewById(R.id.yuanliao1_data_control);
        yuanliao2_data_control = findViewById(R.id.yuanliao2_data_control);
        yuanliao3_data_control = findViewById(R.id.yuanliao3_data_control);
        yuanliao4_data_control = findViewById(R.id.yuanliao4_data_control);
        //定义进度条
        yuanliao1_jd_control = findViewById(R.id.yuanliao1_jd_control);
        yuanliao2_jd_control = findViewById(R.id.yuanliao2_jd_control);
        yuanliao3_jd_control = findViewById(R.id.yuanliao3_jd_control);
        yuanliao4_jd_control = findViewById(R.id.yuanliao4_jd_control);

        //请求原料配比
        try {
            Log.d("ControlActivity","jd_get");
            String yuanliaoAsk_get = sendAskToTheSever("BC"); //获取配比数据
            final String[] yuanliaoAskArr_get = yuanliaoAsk_get.split("/");
            //设置当前进度为后台获取的数据
            yuanliao1_jd_control.setProgress(Integer.parseInt(yuanliaoAskArr_get[1]));
            yuanliao2_jd_control.setProgress(Integer.parseInt(yuanliaoAskArr_get[2]));
            yuanliao3_jd_control.setProgress(Integer.parseInt(yuanliaoAskArr_get[3]));
            yuanliao4_jd_control.setProgress(Integer.parseInt(yuanliaoAskArr_get[4]));
            //设置文本框显示拖动条数据
            yuanliao1_data_control.setText(String.valueOf(yuanliao1_jd_control.getProgress()));
            yuanliao2_data_control.setText(String.valueOf(yuanliao2_jd_control.getProgress()));
            yuanliao3_data_control.setText(String.valueOf(yuanliao3_jd_control.getProgress()));
            yuanliao4_data_control.setText(String.valueOf(yuanliao4_jd_control.getProgress()));
            yuanliao1_jd_control.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    Log.d("ControlActivity","原料一正在拖动");
                    yuanliao1_data_control.setText(progress+"");
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    Log.d("ControlActivity","原料一拖动开始");
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    Log.d("ControlActivity","原料一拖动结束");
                }
            });
            yuanliao2_jd_control.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    Log.d("ControlActivity","原料二正在拖动");
                    yuanliao2_data_control.setText(progress+"");
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    Log.d("ControlActivity","原料二拖动开始");
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    Log.d("ControlActivity","原料二拖动结束");
                }
            });
            yuanliao3_jd_control.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    Log.d("ControlActivity","原料三正在拖动");
                    yuanliao3_data_control.setText(progress+"");
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    Log.d("ControlActivity","原料三拖动开始");
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    Log.d("ControlActivity","原料三拖动结束");
                }
            });
            yuanliao4_jd_control.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    Log.d("ControlActivity","原料四正在拖动");
                    yuanliao4_data_control.setText(progress+"");
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    Log.d("ControlActivity","原料四拖动开始");
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    Log.d("ControlActivity","原料四拖动结束");
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        /******************************************************请求发送按钮**************************************/
        Button sendAsk_btn_Cont = findViewById(R.id.sendAsk_btn_Cont);
        sendAsk_btn_Cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送控件请求码
                B2Code = "B2"+heating+"/"+coor+"/"+addmst+"/"+addlight+"/"+airfluent+"/"+nosun+"/"+a+"/"+b+"/"+c;
                sendAskToTheSever(B2Code);
                Log.d("ControlActivity","发送B2");
                delay();//延时300ms
                // 发送营养液开关请码
                B4Code = "B4"+A+"/"+B+"/"+C;
                sendAskToTheSever(B4Code);
                Log.d("ControlActivity","发送B4");
                Toast.makeText(ControlActivity.this, "发送B4", Toast.LENGTH_SHORT).show();
                delay();//延时300ms

                Toast.makeText(ControlActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
            }
        });
        Button start_yingyangye_btn_Cont = findViewById(R.id.start_yingyangye_btn_Cont);
        start_yingyangye_btn_Cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送营养液原料配比
                String B3Code = "B3"+yuanliao1_jd_control.getProgress()+"/"+yuanliao2_jd_control.getProgress()
                        +"/"+yuanliao3_jd_control.getProgress()+"/"+yuanliao4_jd_control.getProgress();
                sendAskToTheSever(B3Code);
                Log.d("ControlActivity","发送B3");
            }
        });
    }
    //建立控制按钮点击事件
            View.OnClickListener sendAskData = new View.OnClickListener(){
                @Override
                public void onClick(View v) {
            switch (v.getId()){
                //喷灌
                case R.id.spirr_open_btn_Cont:
                    spirr_open_btn_Cont.setBackgroundColor(0x883672fd); //显示开启按钮被点击
                    spirr_close_btn_Cont.setBackgroundColor(0x00ffffff);
                    a="1";
                    break;
                case R.id.spirr_close_btn_Cont:
                    spirr_open_btn_Cont.setBackgroundColor(0x00ffffff); //显示关闭按钮被点击
                    spirr_close_btn_Cont.setBackgroundColor(0x883672fd);
                    a="0";
                    break;
                //营养液
                case R.id.nts_open_btn_Cont:
                    nts_open_btn_Cont.setBackgroundColor(0x883672fd); //显示开启按钮被点击
                    nts_close_btn_Cont.setBackgroundColor(0x00ffffff);
                    A="1";
                    break;
                case R.id.nts_close_btn_Cont:
                    nts_close_btn_Cont.setBackgroundColor(0x883672fd); //显示关闭按钮被点击
                    nts_open_btn_Cont.setBackgroundColor(0x00ffffff);
                    A="0";
                    break;
                //遮阳
                case R.id.nosun_open_btn_Cont:
                    nosun_open_btn_Cont.setBackgroundColor(0x883672fd); //显示开启按钮被点击
                    nosun_close_btn_Cont.setBackgroundColor(0x00ffffff);
                    nosun="1";
                    break;
                case R.id.nosun_close_btn_Cont:
                    nosun_close_btn_Cont.setBackgroundColor(0x883672fd); //显示关闭按钮被点击
                    nosun_open_btn_Cont.setBackgroundColor(0x00ffffff);
                    nosun="0";
                    break;
                //加湿
                case R.id.addmst_open_Cont:
                    addmst_open_Cont.setBackgroundColor(0x883672fd); //显示开启按钮被点击
                    addmst_close_Cont.setBackgroundColor(0x00ffffff);
                    addmst="1";
                    break;
                case R.id.addmst_close_Cont:
                    addmst_close_Cont.setBackgroundColor(0x883672fd); //显示关闭按钮被点击
                    addmst_open_Cont.setBackgroundColor(0x00ffffff);
                    addmst="0";
                    break;
                //冷气
                case R.id.coor_open_btn_Cont:
                    coor_open_btn_Cont.setBackgroundColor(0x883672fd); //显示开启按钮被点击
                    coor_close_btn_Cont.setBackgroundColor(0x00ffffff);
                    coor="1";
                    break;
                case R.id.coor_close_btn_Cont:
                    coor_close_btn_Cont.setBackgroundColor(0x883672fd); //显示关闭按钮被点击
                    coor_open_btn_Cont.setBackgroundColor(0x00ffffff);
                    coor="0";
                    break;
                //暖气
                case R.id.heating_open_btn_Cont:
                    heating_open_btn_Cont.setBackgroundColor(0x883672fd); //显示开启按钮被点击
                    heating_close_btn_Cont.setBackgroundColor(0x00ffffff);
                    heating="1";
                    break;
                case R.id.heating_close_btn_Cont:
                    heating_close_btn_Cont.setBackgroundColor(0x883672fd); //显示关闭按钮被点击
                    heating_open_btn_Cont.setBackgroundColor(0x00ffffff);
                    heating="0";
                    break;
                //补光
                case R.id.addlight_open_btn_Cont:
                    addlight_open_btn_Cont.setBackgroundColor(0x883672fd); //显示开启按钮被点击
                    addlight_close_btn_Cont.setBackgroundColor(0x00ffffff);
                    addlight="1";
                    break;
                case R.id.addlight_close_btn_Cont:
                    addlight_close_btn_Cont.setBackgroundColor(0x883672fd); //显示关闭按钮被点击
                    addlight_open_btn_Cont.setBackgroundColor(0x00ffffff);
                    addlight="0";
                    break;
                //进风
                case R.id.airin_open_all_btn_Cont:
                    airin_open_all_btn_Cont.setBackgroundColor(0x883672fd); //显示全部开启按钮被点击
                    airin_open_half_btn_Cont.setBackgroundColor(0x00ffffff);
                    airin_close_btn_Cont.setBackgroundColor(0x00ffffff);
                    airfluent="2";
                    break;
                case R.id.airin_open_half_btn_Cont:
                    airin_open_half_btn_Cont.setBackgroundColor(0x883672fd); //显示一半开启按钮被点击
                    airin_open_all_btn_Cont.setBackgroundColor(0x00ffffff);
                    airin_close_btn_Cont.setBackgroundColor(0x00ffffff);
                    airfluent="3";
                    break;
                case R.id.airin_close_btn_Cont:
                    airin_close_btn_Cont.setBackgroundColor(0x883672fd); //显示关闭按钮被点击
                    airin_open_all_btn_Cont.setBackgroundColor(0x00ffffff);
                    airin_open_half_btn_Cont.setBackgroundColor(0x00ffffff);
                    airfluent="0";
                    break;
                //排风
                case R.id.airout_open_all_btn_Cont:
                    airout_open_all_btn_Cont.setBackgroundColor(0x883672fd); //显示全部开启按钮被点击
                    airout_open_half_btn_Cont.setBackgroundColor(0x00ffffff);
                    airout_close_btn_Cont.setBackgroundColor(0x00ffffff);
                    airfluent="1";
                    break;
                case R.id.airout_open_half_btn_Cont:
                    airout_open_half_btn_Cont.setBackgroundColor(0x883672fd); //显示一半开启按钮被点击
                    airout_open_all_btn_Cont.setBackgroundColor(0x00ffffff);
                    airout_close_btn_Cont.setBackgroundColor(0x00ffffff);
                    airfluent="4";
                    break;
                case R.id.airout_close_btn_Cont:
                    airout_close_btn_Cont.setBackgroundColor(0x883672fd); //显示关闭按钮被点击
                    airout_open_all_btn_Cont.setBackgroundColor(0x00ffffff);
                    airout_open_half_btn_Cont.setBackgroundColor(0x00ffffff);
                    airfluent="0";
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        changeUI(ControlActivity.this , MainActivity.class);
        finish();
        return true;
    }
}
