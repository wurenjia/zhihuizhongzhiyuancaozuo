package com.example.myone01;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class TransferActivity extends CommonMethodActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        Button kaifa_btn_Tran = findViewById(R.id.kaifa_btn_Tran);
        kaifa_btn_Tran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUI(TransferActivity.this,ControlActivity.class);
                finish();
                Log.d("TranferActivity","手动营养配完了");
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i=10 ; i>1 ; i++){
                        //请求数据
                        String stare_nts_Tranfer = sendAskToTheSever("BC");
                        String[] sntArr = stare_nts_Tranfer.split("/");
                        if(sntArr[4].equals("1")){
                            changeUI(TransferActivity.this,ControlActivity.class);
                            finish();
                            Log.d("TranferActivity","营养配完了");
                            break;
                        }
                        Log.d("TranferActivity","营养还在配比");
                        Thread.sleep(3000);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
