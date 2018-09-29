package com.example.myone01;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.Toast;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class LoginActivity extends CommonMethodActivity {
    EditText username_et,password_et;//定义输入账户、密码的编辑框
    String username_in,password_in;  //定义输入用户名、密码
    CheckBox remember_cb;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username_et = findViewById(R.id.username_et_lg);
        password_et = findViewById(R.id.password_et_lg);
        Button login_btn = findViewById(R.id.login_btn_lg);
        Button unremeber_btn = findViewById(R.id.unremember_btn_lg);
        remember_cb = (CheckBox) findViewById(R.id.remember_cb);
        Button register_btn = findViewById(R.id.register_btn_lg);
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        boolean result = pref.getBoolean("checked" ,false);//建立是否记住密码的值
        //创建忘记密码事件
        unremeber_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent unremember_intent = new Intent(LoginActivity.this ,UnrememberPswActivity.class);
                startActivity(unremember_intent);
            }
        });
        //创建记住密码事件
        if(result){
                loadPswWithSharePreferences();
                remember_cb.setChecked(true);
        }
        //创建登录按钮点击事件
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLogin(password_in);
            }
        });
        //创建注册按钮点击事件
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCode(LoginActivity.this);

            }
        });
}
    //记住密码密码存储
    private void savePswWithSharePreferencs(){
        String usn = username_et.getText().toString();
        String psw = password_et.getText().toString();
        editor.putString("username",usn);
        editor.putString("password",psw);
        editor.putBoolean("checked",true);
        editor.apply();
    }
    //密码读取
    private void loadPswWithSharePreferences(){
        username_et.setText(pref.getString("username" ,""));
        password_et.setText(pref.getString("password" ,""));
    }
    //创建用户注册_（含手机验证）方法
    private void sendCode(Context context) {
        RegisterPage page = new RegisterPage();
        //如果使用我们的ui，没有申请模板编号的情况下需传null
        page.setTempCode(null);
        page.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 处理成功的结果
                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("86"); // 国家代码，如“86”
                    String phone = (String) phoneMap.get("phone"); // 手机号码，如“17873676984”
                    savePhoneRegisterInfo(phone);  //保存手机号码
                    // TODO 利用国家代码和手机号码进行后续的操作
                } else{
                    // TODO 处理错误的结果
                }
                //跳转界面至注册界面
                Intent intent = new Intent(LoginActivity.this ,RegistActivity.class);
                startActivity(intent);
            }
        });
        page.show(context);
    }
    //存储注册手机号码
    private void savePhoneRegisterInfo(String phoneNum){
        //loginInfo表示文件名, mode_private SharedPreferences sp = getSharedPreferences( );
        SharedPreferences sp=getSharedPreferences("user", MODE_PRIVATE);
        //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
        SharedPreferences.Editor editor=sp.edit();
        //以phone为key，号码为value保存在SharedPreferences中
        //key,value,如键值对，editor.putString(手机键值，号码）;
        editor.putString("phone", phoneNum);
        //提交修改 editor.commit();
        editor.apply();
    }
    //建立登录方法
    @SuppressLint("CommitPrefEdits")
    private void toLogin(String password){
        editor = pref.edit();
        //获取输入的用户名以及密码
        username_in = username_et.getText().toString();
        password_in = password_et.getText().toString();
        String password_md5 = readOfFile(username_in);  //获取已注册的加密密码
        String password_in_md5= RegistActivity.MD5Utils.md5(password_in);  //读取以密码编辑框中的密码数据，并对其进行md5加密
        if(password_in_md5.equals(password_md5)){  //判断md5加密后的输入的用户名和密码是否对应
            Toast.makeText(LoginActivity.this, "密码正确", Toast.LENGTH_SHORT).show();
            if(remember_cb.isChecked()){
                savePswWithSharePreferencs();  //保存账号密码
            }else{
                editor.clear();  //清除账号密码
                editor.apply();
            }
            Intent intent = new Intent(LoginActivity.this ,MainActivity.class);
            startActivity(intent);
            finish();  // 关闭登录界面
        }else{
            Toast.makeText(this, "密码输入错误，请再次输入", Toast.LENGTH_SHORT).show();
        }
    }
}
