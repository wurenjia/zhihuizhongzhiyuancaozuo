package com.example.myone01;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegistActivity extends CommonMethodActivity {
    EditText username_et_ra , password1_et_ra ,password2_et_ra;
    private TextView tv_back;//返回按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        Button register_btn = findViewById(R.id.register_btn_ra);
        username_et_ra = findViewById(R.id.usename_et_ra);
        password1_et_ra = findViewById(R.id.password1_et_ra);
        password2_et_ra = findViewById(R.id.password2_et_ra);
        tv_back = findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistActivity.this.finish();
            }
        });
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registUser();
            }
        });
    }
    //创建注册方法，在里面调用saveRegisterInfo方法将用户数据保存起来
    public void registUser(){
        if(!isExistUserName(username_et_ra.getText().toString())){
            if(!(username_et_ra.getText().toString().equals("")) &&(!password1_et_ra.getText().toString().equals(""))){
                if(password1_et_ra.getText().toString().equals(password2_et_ra.getText().toString())){
                    saveRegisterInfo(username_et_ra.getText().toString() ,password1_et_ra.getText().toString(),"");
                    Toast.makeText(RegistActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    RegistActivity.this.finish(); //关闭注册界面
                }else{
                    Toast.makeText(RegistActivity.this, "第二次输入密码与第一次输入密码不同，" +
                            "请重新输入", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "账号密码未填写完整，请核实后再注册", Toast.LENGTH_SHORT).show();
            }
        }
    }

//    //创建保存数据方法，保存数据到user文件中去
//    private void saveRegisterInfo(String userName,String psw){
//        String md5Psw = MD5Utils.md5(psw);//把密码用MD5加密
//        //loginInfo表示文件名, mode_private SharedPreferences sp = getSharedPreferences( );
//        SharedPreferences sp=getSharedPreferences("user", MODE_PRIVATE);
//        //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
//        SharedPreferences.Editor editor=sp.edit();
//        //以用户名为key，密码为value保存在SharedPreferences中
//        //key,value,如键值对，editor.putString(用户名，密码）;
//        editor.putString(userName, md5Psw);
//        String phone = readOfFile("phone");
//        editor.putString(userName+"_phone" ,phone);
//        //提交修改 editor.commit();
//        editor.apply();
//    }


    /**
     * 从SharedPreferences中读取输入的用户名，判断SharedPreferences中是否有此用户名
     */
    private boolean isExistUserName(String userName){
        boolean has_userName=false;
        //mode_private SharedPreferences sp = getSharedPreferences( );
        // "loginInfo", MODE_PRIVATE
        SharedPreferences sp=getSharedPreferences("user", MODE_PRIVATE);
        //获取密码
        String spPsw=sp.getString(userName, "");//传入用户名获取密码
        //如果密码不为空则确实保存过这个用户名
        if(!TextUtils.isEmpty(spPsw)) {
            has_userName=true;
        }
        return has_userName;
    }
    //md5 加密算法
    public static class MD5Utils {

        static String md5(String text) {
            MessageDigest digest = null;
            try {
                digest = MessageDigest.getInstance("md5");
                // 数组 byte[] result -> digest.digest( );  文本 text.getBytes();
                byte[] result = digest.digest(text.getBytes());
                //创建StringBuilder对象 然后建议StringBuffer，安全性高
                //StringBuilder sb = new StringBuilder();
                StringBuffer sb = new StringBuffer();
                // result数组，digest.digest ( ); -> text.getBytes();
                // for 循环数组byte[] result;
                for (byte b : result){
                    // 0xff 为16进制
                    int number = b & 0xff;
                    // number值 转换 字符串 Integer.toHexString( );
                    String hex = Integer.toHexString(number);
                    if (hex.length() == 1){
                        sb.append("0"+hex);
                    }else {
                        sb.append(hex);
                    }
                }
                //sb StringBuffer sb = new StringBuffer();对象实例化
                return sb.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                //发送异常return空字符串
                return "";
            }
        }
    }

}
