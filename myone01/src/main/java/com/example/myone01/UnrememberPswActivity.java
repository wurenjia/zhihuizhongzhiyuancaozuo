package com.example.myone01;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class UnrememberPswActivity extends CommonMethodActivity {
    EditText usename_et_fp,newPsw_et_fp,newPswAgain_et_fp,phoneNum_et_fp;
    String phone_vd_fromlg;//创建获取自登录界面的验证手机号码
    String phone_in_fp;        //创建输入手机号码
    String usename_in_fp;      //创建输入用户名对象
    String newPsw_in_fp;       //创建输入新密码对象
    String newPswAgain_in_fp;  //创建再次输入新密码对象
    String phoneKey_fp;     //创建存储手机号码的Key值对象
    String phone_vd;        //创建绑定的手机号码对象
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_psw);

        Button vaioldataWithPhone = findViewById(R.id.validata_phone_btn_fp);
        usename_et_fp = findViewById(R.id.usename_et_fp);
        newPsw_et_fp = findViewById(R.id.newPsw_et_fp);
        phoneNum_et_fp = findViewById(R.id.phoneNum_et_fp);
        newPswAgain_et_fp = findViewById(R.id.newPswAgain_et_fp);
        phoneNum_et_fp.setText(phone_vd_fromlg);
        //开启寻回
        vaioldataWithPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vaildataWithphone();  //调用vaildataWithphone方法存储新密码
            }
        });
    }
    public void vaildataWithphone(){
        phone_in_fp = String.valueOf(phoneNum_et_fp.getText()); //获取输入手机号码
        usename_in_fp = String.valueOf(usename_et_fp.getText());//获取输入用户名
        newPsw_in_fp = String.valueOf(newPsw_et_fp.getText());  //获取输入密码
        newPswAgain_in_fp = String.valueOf(newPswAgain_et_fp.getText());  //获取再次输入密码
        phoneKey_fp = usename_in_fp +"_phone";
        phone_vd = readOfFile(phoneKey_fp);                     //获取绑定手机号码
        if(!phone_vd.equals("")){   //确认是否有此用户，通过读取用户绑定的手机
            if(newPsw_in_fp.equals(newPswAgain_in_fp)){
                Toast.makeText(this, "用户存在", Toast.LENGTH_SHORT).show();

                delay();//延时
                if(phone_in_fp.equals(phone_vd)){
                    sendCodeToFindPsw(UnrememberPswActivity.this);  //手机验证
                }
            }else{
                Toast.makeText(this, "第二次输入密码与第一次不同，请重新输入", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "没有此用户，请重新输入", Toast.LENGTH_SHORT).show();
        }
    }
    //创建用户注册_（含手机验证）方法
    private void sendCodeToFindPsw(final Context context) {
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
                    if(phone.equals(phone_in_fp)) {
                        saveRegisterInfo(usename_in_fp, newPsw_in_fp ,phone_in_fp);                 //储存新密码
                        Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(context, "非账户绑定手机号，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                    // TODO 利用国家代码和手机号码进行后续的操作
                } else{
                    // TODO 处理错误的结果
                }

            }
        });
        page.show(context);
    }
}
