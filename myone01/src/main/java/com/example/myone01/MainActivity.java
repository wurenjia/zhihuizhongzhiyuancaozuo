package com.example.myone01;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ABCqu.AquActivity;
import com.example.ABCqu.BquActivity;
import com.example.ABCqu.CquActivity;
import com.example.dbabcqu.AquData;

import org.w3c.dom.Text;

public class MainActivity extends CommonMethodActivity {

    private DrawerLayout mDrawerLayout;
    private TextView lgAgain_tv;
    private TextView changepsw_tv;
    String[] resArr_main;   //定义用于截取服务器返回数据
    /**************************************按钮定义**********************************************/

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendAskToTheSever("B5");
        /**************************************重新定义标题栏，加一个菜单导航键**********************************************/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        /*********************************************跳转至设置界面的跳转界面**********************************************/
        TextView setting_tv_main = findViewById(R.id.setting_tv_main);
        setting_tv_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUI(MainActivity.this,TransferActivity.class);
            }
        });
        /*************************************************修改密码*********************************************************/
        changepsw_tv = findViewById(R.id.changepsw_tv_main);
        changepsw_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUI(MainActivity.this,ChangePswActivity.class);
            }
        });
        /*************************************************返回登录*********************************************************/
        lgAgain_tv = findViewById(R.id.loginAgain_tv_main);
        lgAgain_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUI(MainActivity.this,LoginActivity.class);
                finish();
            }
        });
        /*********************************************A、B、C区变换**********************************************/
        //主页至A区
       Button A_tv_main = findViewById(R.id.A_btn_main);
       A_tv_main.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                changeUI(MainActivity.this ,AquActivity.class);
                finish();
           }
       });
       //主页至B区
        Button B_tv_main = findViewById(R.id.B_btn_main);
        B_tv_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUI(MainActivity.this ,BquActivity.class);
                finish();
            }
        });
        //主页至C区
        Button C_tv_main = findViewById(R.id.C_btn_main);
        C_tv_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUI(MainActivity.this ,CquActivity.class);
                finish();
            }
        });

        /*********************************************建立数据请求**********************************************/
        //园外
        final TextView light_outG_ll_main = findViewById(R.id.light_outG_ll_main);
        final TextView airHUmidity_outG_ll_main = findViewById(R.id.airHUmidity_outG_ll_main);
        final TextView airTemp_outG_ll_main = findViewById(R.id.airTemp_outG_ll_main);
        //园内
        final TextView light_inG_ll_main = findViewById(R.id.light_inG_ll_main);
        final TextView airHUmidity_inG_ll_main = findViewById(R.id.airHUmidity_inG_ll_main);
        final TextView airTemp_inG_ll_main = findViewById(R.id.airTemp_inG_ll_main);
        @SuppressLint("HandlerLeak") final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == 0x1){
                //显示数据
                    try{
                        airTemp_inG_ll_main.setText("空气温度: "+resArr_main[3]);                  //空气湿度
                        airTemp_outG_ll_main.setText("空气温度: "+resArr_main[4]);
                        airHUmidity_inG_ll_main.setText("空气湿度: "+resArr_main[5]);          //空气温度
                        airHUmidity_outG_ll_main.setText("空气湿度: "+resArr_main[6]);
                        light_inG_ll_main.setText("光照强度: "+resArr_main[7]);                      //光照强度
                        light_outG_ll_main.setText("光照强度: "+resArr_main[8]);
                        Log.d("MainActivity","线程更新");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i=10 ; i>1 ; i++){
                        //请求数据
                        String reslut_main = sendAskToTheSever("B5");
                        resArr_main = reslut_main.split("/");
                        Log.d("MainActivity","主界面请求");
                        Message message = new Message();
                        message.what = 0x1;
                        handler.sendEmptyMessage(0x1);
                        Thread.sleep(5000);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
    /*********************************************侧滑菜单**********************************************/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

            default:
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
