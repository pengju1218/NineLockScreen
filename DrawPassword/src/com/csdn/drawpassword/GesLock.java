package com.csdn.drawpassword;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.csdn.drawpassword.view.GestureLockView;


public class GesLock extends Activity {


    private com.csdn.drawpassword.view.GestureLockView glv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ges_layout);
        this.glv = (GestureLockView) findViewById(R.id.glv);
        glv.setCallback(new GestureLockView.GestureLockCallback() {

            @Override
            public void onFinish(String pwdString, GestureLockView.Result result) {
                boolean resu = "1236".equals(pwdString);
                result.setRight(resu);
                Toast.makeText(GesLock.this, resu ? "密码正确" : "密码错误", Toast.LENGTH_LONG).show();
                if (!resu) {
                    //TODO: 跳转
                }
            }
        });

    }
}
