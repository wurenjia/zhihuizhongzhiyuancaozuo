package com.example.myone01;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

@SuppressLint("Registered")
public class CommonMethodActivity extends AppCompatActivity{
    Response response;
    String qustionData = "5/5/5/5/5/5/5/5/5/5";
    //创建用户已注册数据的读取方法
    public String readOfFile(String key){
        SharedPreferences sp = getSharedPreferences("user" ,MODE_PRIVATE );
        return sp.getString(key , "");  //返回与输入用户名对应的加密的密码
    }
    //创建密码存储方法
    public void saveRegisterInfo(String userName,String psw,String inPhone){
        String md5Psw = RegistActivity.MD5Utils.md5(psw);//把密码用MD5加密
        //loginInfo表示文件名, mode_private SharedPreferences sp = getSharedPreferences( );
        SharedPreferences sp=getSharedPreferences("user", MODE_PRIVATE);
        //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
        SharedPreferences.Editor editor=sp.edit();
        //以用户名为key，密码为value保存在SharedPreferences中
        //key,value,如键值对，editor.putString(用户名，密码）;
        String phone_fromF = readOfFile("phone");
        if(!phone_fromF.equals("")) {
            editor.clear();//清除原来的代码
            editor.putString(userName, md5Psw);
            editor.putString(userName + "_phone", phone_fromF);
        }else{
            editor.putString(userName, md5Psw);
            editor.putString(userName + "_phone", inPhone);
        }
        //提交修改 editor.commit();
        editor.apply();
    }
    /*******************************************延时方法*****************************************************/
    public void delay(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    /*******************************************Intent界面切换**********************************************/
    public void changeUI(Context fromActivyty ,Class toActivity){
        Intent intent = new Intent(fromActivyty ,toActivity);
        startActivity(intent);

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
    /************************************************向服务器发送请求**************************************************************/

    public String sendAskToTheSever( final String askCode){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                OkHttpClient okHttpClient = new OkHttpClient();   //定义一个OKHttpClient实例
                RequestBody requestBody = new FormBody.Builder()
                        .add("CoNt",askCode)
                        .build();
                //实例化一个Respon对象，用于发送HTTP请求
                Request request = new Request.Builder()
                        .url("http://192.168.43.26:80/process.php")             //设置目标网址
                        .post(requestBody)
                        .build();
                    response = okHttpClient.newCall(request).execute();  //获取服务器返回的数据
                    if (response.body() != null) {
                        if((askCode.equals("B5")||askCode.equals("BC"))&&!askCode.equals("BF")) {
                            qustionData = response.body().string();//存储服务器返回的数据
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return qustionData;
    }
}
