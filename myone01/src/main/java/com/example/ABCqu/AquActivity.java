package com.example.ABCqu;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.FragmentAqu.AquAirMstFragment;
import com.example.FragmentAqu.AquAirTempFragment;
import com.example.FragmentAqu.AquLightFragment;
import com.example.FragmentAqu.AquSoilMstFragment;
import com.example.FragmentAqu.AquSoilTempFragment;
import com.example.dbabcqu.AquData;
import com.example.myone01.CommonMethodActivity;
import com.example.myone01.MainActivity;
import com.example.myone01.R;

import org.litepal.LitePal;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;


public class AquActivity extends CommonMethodActivity implements GestureDetector.OnGestureListener{
/*******************************************创建Button碎片标题栏点击窗口**********************************************/
    Button soilMst_btn_Aqu;
    Button soilTemp_btn_Aqu;
    Button airMst_btn_Aqu;
    Button airTemp_btn_Aqu;
    Button light_btn_Aqu;
/***********************************************创建用于实时更新碎片的对象***************************************************************/
    int dataToUpData = 1;
    boolean isUpFragment = true;//碎片更新
    boolean isCanUpDown = false; //升降台更新
    boolean isFristAskData = true; // 是否为第一次请求B5
    String eqm1_Code_Aqu="0",eqm2_Code_Aqu="0",eqm3_Code_Aqu="0";
    String result = "5/5/5/5/5/5/5/5/5/5";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aqu);
        SQLiteStudioService.instance().start(this);


        soilMst_btn_Aqu = findViewById(R.id.soilMst_title_tv_Aqu);
        soilTemp_btn_Aqu = findViewById(R.id.soilTemp_title_tv_Aqu);
        airMst_btn_Aqu = findViewById(R.id.airMst_title_tv_Aqu);
        airTemp_btn_Aqu = findViewById(R.id.airTemp_title_tv_Aqu);
        light_btn_Aqu = findViewById(R.id.light_title_tv_Aqu);
        //*******************************************建立tv窗口点击事件**********************************************/

        light_btn_Aqu.setOnClickListener(replaceFragment);
        soilMst_btn_Aqu.setOnClickListener(replaceFragment);
        soilTemp_btn_Aqu.setOnClickListener(replaceFragment);
        airMst_btn_Aqu.setOnClickListener(replaceFragment);
        airTemp_btn_Aqu.setOnClickListener(replaceFragment);

        //******************************************创建用于控制A区设备升降的对象***************************************************************/
        //设备一
        Button up_eqm1_btn_Aqu = findViewById(R.id.up_eqm1_btn_Aqu);
        Button stop_eqm1_btn_Aqu = findViewById(R.id.stop_eqm1_btn_Aqu);
        Button down_eqm1_btn_Aqu = findViewById(R.id.down_eqm1_btn_Aqu);
        //设备二
        Button up_eqm2_btn_Aqu = findViewById(R.id.up_eqm2_btn_Aqu);
        Button stop_eqm2_btn_Aqu = findViewById(R.id.stop_eqm2_btn_Aqu);
        Button down_eqm2_btn_Aqu = findViewById(R.id.down_eqm2_btn_Aqu);
        //设备三
        Button up_eqm3_btn_Aqu = findViewById(R.id.up_eqm3_btn_Aqu);
        Button stop_eqm3_btn_Aqu = findViewById(R.id.stop_eqm3_btn_Aqu);
        Button down_eqm3_btn_Aqu = findViewById(R.id.down_eqm3_btn_Aqu);
        //设置升降台控制命令
        try {
            //设备一
            up_eqm1_btn_Aqu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!eqm1_Code_Aqu.equals("2"))
                        eqm1_Code_Aqu="2";
                    //设置升降台控制命令可发送
                    isCanUpDown = true;
                }
            });
            stop_eqm1_btn_Aqu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!eqm1_Code_Aqu.equals("1"))
                        eqm1_Code_Aqu="1";
                    //设置升降台控制命令可发送
                    isCanUpDown = true;
                }
            });
            down_eqm1_btn_Aqu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!eqm1_Code_Aqu.equals("0"))
                        eqm1_Code_Aqu="0";
                    //设置升降台控制命令可发送
                    isCanUpDown = true;
                }
            });
            //设备二
            up_eqm2_btn_Aqu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!eqm2_Code_Aqu.equals("2"))
                        eqm2_Code_Aqu="2";
                    //设置升降台控制命令可发送
                    isCanUpDown = true;
                }
            });
            stop_eqm2_btn_Aqu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!eqm2_Code_Aqu.equals("1"))
                        eqm2_Code_Aqu="1";
                    //设置升降台控制命令可发送
                    isCanUpDown = true;
                }
            });
            down_eqm2_btn_Aqu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!eqm2_Code_Aqu.equals("0"))
                        eqm2_Code_Aqu="0";
                    //设置升降台控制命令可发送
                    isCanUpDown = true;
                }
            });
            //设备三
            up_eqm3_btn_Aqu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!eqm3_Code_Aqu.equals("2"))
                    eqm3_Code_Aqu="2";
                    //设置升降台控制命令可发送
                    isCanUpDown = true;
                }
            });
            stop_eqm3_btn_Aqu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!eqm3_Code_Aqu.equals("1"))
                    eqm3_Code_Aqu="1";
                    //设置升降台控制命令可发送
                    isCanUpDown = true;
                }
            });
            down_eqm3_btn_Aqu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!eqm3_Code_Aqu.equals("0"))
                        eqm3_Code_Aqu="0";
                    //设置升降台控制命令可发送
                    isCanUpDown = true;
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
        //*******************************************设置碎片主界面**********************************************/
        upFragment();
        //*******************************************主界面切换****************************************************/
        //A  to  Main
        final Button main_btn_Aqu = findViewById(R.id.main_btn_Aqu);
        main_btn_Aqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUI(AquActivity.this , MainActivity.class);
                finish();
            }
        });
        //A  to B
        Button B_btn_Aqu = findViewById(R.id.B_btn_Aqu);
        B_btn_Aqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUI(AquActivity.this ,BquActivity.class);
                finish();
            }
        });
        //A  to C
        Button C_btn_Aqu = findViewById(R.id.C_btn_Aqu);
        C_btn_Aqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUI(AquActivity.this ,CquActivity.class);
                finish();
            }
        });

        //*******************************************数据库操作****************************************************************/
        //创建数据库
        LitePal.getDatabase();
        //存储数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        //请求数据
                        result = sendAskToTheSever("B5");//总的数据
                        String[] resArr = result.split("/");
//                        if(Float.parseFloat(resArr[2])<1){
//                            resArr[2]="1";
//                        }
                        //向数据库中添加数据
                        if(Float.parseFloat(resArr[2])>1) {
                            AquData aquData = new AquData();
                            aquData.setTime(getNewTime()); //保存数据点的时间
                            aquData.setSoiltempdata(Float.parseFloat(resArr[1]));//保存土壤温度的数据sendAskToTheSever("BH")
                            aquData.setSoilmstdata(Float.parseFloat(resArr[2]));//保存土壤湿度的数据sendAskToTheSever("BI")
                            aquData.setAirtempdata(Float.parseFloat(resArr[3]));//保存空气温度的数据
                            aquData.setAirmstdata(Float.parseFloat(resArr[5]));//保存空气湿度的数据
                            aquData.setLightdata(Float.parseFloat(resArr[7])); //保存光照强度数据点的数据
                            aquData.save();  //存储数据
                            Log.d("AquActivity", "线程更新");

                            upFragment();  // 更新数据
                        }
                        Thread.sleep(1000);
                        if(isCanUpDown){
                            //发送升降台更新
                            sendAskToTheSever("BF"+eqm1_Code_Aqu+"/"+eqm2_Code_Aqu+"/"+eqm3_Code_Aqu);//
                            isCanUpDown = false;
                        }
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    //删除数据，当数据即将超出1000条时

    //******************************************设置变换碎片界面点击事件**********************************************/

    View.OnClickListener replaceFragment = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Fragment f = null;

            switch (v.getId()){
                case R.id.light_title_tv_Aqu:
                    f= new AquLightFragment();
                    dataToUpData = 1;
                    break;
                case R.id.soilMst_title_tv_Aqu:
                    f= new AquSoilMstFragment();
                     dataToUpData = 2;
                   break;
                case R.id.soilTemp_title_tv_Aqu:
                    f= new AquSoilTempFragment();
                     dataToUpData = 3;
                   break;
                case R.id.airMst_title_tv_Aqu:
                    f= new AquAirMstFragment();
                    dataToUpData = 4;
                    break;
                case R.id.airTemp_title_tv_Aqu:
                    f= new AquAirTempFragment();
                    dataToUpData = 5;
                    break;
            }
            transaction.replace(R.id.fragment_fl_Aqu, f);
            transaction.commitAllowingStateLoss();
        }

    };

    //更新碎片界面
    private void upFragment(){
        Log.d("upFragment","更新以打开");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction tf = fragmentManager.beginTransaction();
        Fragment startF=null;
        switch(dataToUpData){
        case 1:
            startF = new AquLightFragment();
            break;
        case 2:
            startF = new AquSoilMstFragment();
            break;
        case 3:
            startF = new AquSoilTempFragment();
            break;
        case 4:
            startF = new AquAirMstFragment();
            break;
        case 5:
            startF = new AquAirTempFragment();
            break;
        }
        tf.replace(R.id.fragment_fl_Aqu ,startF);
        tf.commitAllowingStateLoss();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        isUpFragment = true;
        Toast.makeText(this, " 已恢复数据动态更新", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(e1.getX() - e2.getX() >50){  //如果从第一个触点到第二个触点的X轴距离超过50dp，就是从右向左滑
            isUpFragment = false;
            Toast.makeText(this, " 已取消数据动态更新，长按屏幕即可恢复数据动态更新", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        SQLiteStudioService.instance().stop();
        super.onDestroy();
    }
}

