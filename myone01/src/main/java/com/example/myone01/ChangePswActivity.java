package com.example.myone01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ChangePswActivity extends CommonMethodActivity {
    ImageView back_iv_Change;
    EditText usename_et_Change;
    EditText oldPsw_et_Change;
    EditText newPsw_et_Change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_psw);
        back_iv_Change = findViewById(R.id.back_iv_Change);
/**********************************************返回主界面**************************************************************/
        back_iv_Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUI(ChangePswActivity.this,MainActivity.class);
                finish();
            }
        });
/**********************************************修改密码*************************************************************/
        Button enter_btn_Change = findViewById(R.id.enter_btn_Change);
        usename_et_Change = findViewById(R.id.usename_et_Change);
        oldPsw_et_Change = findViewById(R.id.oldPsw_et_Change);
        newPsw_et_Change = findViewById(R.id.newPsw_et_Change);
        enter_btn_Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePsw(usename_et_Change.getText().toString(),oldPsw_et_Change.getText().toString()
                        ,newPsw_et_Change.getText().toString());
            }
        });
    }
    private void changePsw(String usename_in_Change,String oldPsw_in_Change,String newpsw_in_Change){

        String oldPsw_in_Change_md5= RegistActivity.MD5Utils.md5(oldPsw_in_Change);  //读取以密码编辑框中的密码数据，并对其进行md5加密
        Log.d("ChangePswActivity","usename_in_Change = "+usename_in_Change+"\noldPsw_in_Change = "
                +oldPsw_in_Change+"\nnewpsw_in_Change = "+newpsw_in_Change+"\nreadOfFile(usename_in_Change) = "+
                readOfFile(usename_in_Change)+"\noldPsw_in_Change_md5 = "+oldPsw_in_Change_md5);
        if((!usename_in_Change.equals("")) && (!oldPsw_in_Change.equals("")) && (!newpsw_in_Change.equals(""))){
            if(oldPsw_in_Change_md5.equals(readOfFile(usename_in_Change))){
               String oldPhone = readOfFile(usename_in_Change+"_phone");  //得到用户手机信息
               saveRegisterInfo(usename_in_Change,newpsw_in_Change,oldPhone); //  存储新密码
                Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();
                Log.d("ChangePswActivity","密码修改成功");
                changeUI(ChangePswActivity.this,MainActivity.class);
                finish();
            }else{
                Toast.makeText(this, "旧密码错误，请重新输入", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "账号密码为空，请重新输入", Toast.LENGTH_SHORT).show();
        }
    }
}
