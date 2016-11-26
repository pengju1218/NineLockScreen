package com.csdn.drawpassword;

import com.csdn.drawpassword.lan.component.LocusPassWordView;
import com.csdn.drawpassword.lan.component.LocusPassWordView.OnCompleteListener;
import com.csdn.drawpassword.lan.util.Md5Utils;
import com.csdn.drawpassword.lan.util.SharedPreferencesHelper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

    private LocusPassWordView mPwdView;
    private Context mContext;
    int i = 0;
    private String pwd, pass1, pass2;
    private SharedPreferencesHelper sph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw_pwd);
        sph = SharedPreferencesHelper.getInstance(MainActivity.this);
        pwd = sph.getString("password", "");

        if (pwd.length() == 0) {
            Toast.makeText(MainActivity.this,  "请设置手势密码", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(MainActivity.this,  "请验证手势密码", Toast.LENGTH_LONG).show();
        }
        mContext = getApplicationContext();
        mPwdView = (LocusPassWordView) this.findViewById(R.id.mPassWordView);
        mPwdView.setOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(String mPassword) {

                Md5Utils md5 = new Md5Utils();
                boolean passed = false;

                if (pwd.length() == 0) {
                    i++;

                    if (i == 1) {
                        pass1 = md5.toMd5(mPassword, "");
                        Toast.makeText(mContext, i + "第一次手势密码成功", Toast.LENGTH_LONG).show();
                    } else if (i == 2) {
                        pass2 = md5.toMd5(mPassword, "");

                        if (pass1.equals(pass2)) {
                            Toast.makeText(mContext, i + "两次密码一样", Toast.LENGTH_LONG).show();
                            sph.putString("password", pass2);
                        } else {
                            i = 0;
                            Toast.makeText(mContext, i + "两次密码不一样,请重新绘制", Toast.LENGTH_LONG).show();
                        }
                    }

                    mPwdView.clearPassword();
                    //	sph.putString("password", );
                    //Toast.makeText(mContext, mContext.getString(R.string.pwd_setted), Toast.LENGTH_LONG).show();
                    return;
                } else {
                    String encodedPwd = md5.toMd5(mPassword, "");
                    if (encodedPwd.equals(pwd)) {
                        passed = true;
                    } else {
                        mPwdView.markError();
                    }
                }

                if (passed) {
                    Log.d("hcj", "password is correct!");
                    Toast.makeText(mContext, mContext.getString(R.string.pwd_correct), Toast.LENGTH_LONG).show();
//					finish();
                }
                mPwdView.clearPassword();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
