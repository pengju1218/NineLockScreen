package com.csdn.drawpassword;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.csdn.drawpassword.nok.view.GestureLockViewGroup;
import com.csdn.drawpassword.nok.view.listener.GestureEventListener;
import com.csdn.drawpassword.nok.view.listener.GesturePasswordSettingListener;
import com.csdn.drawpassword.nok.view.listener.GestureUnmatchedExceedListener;


public class LockAcitity extends Activity {




    private GestureLockViewGroup mGestureLockViewGroup;
    private android.widget.TextView tvtitle;
    private android.widget.TextView tvreset;
    private android.widget.RelativeLayout rlreset;
    private android.widget.RelativeLayout rltop;
    private android.widget.TextView tv_state;
    private boolean isReset = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_acitity);
        this.tv_state = (TextView) findViewById(R.id.tv_state);
        this.rltop = (RelativeLayout) findViewById(R.id.rl_top);
        this.rlreset = (RelativeLayout) findViewById(R.id.rl_reset);
        this.tvreset = (TextView) findViewById(R.id.tv_reset);
        this.tvtitle = (TextView) findViewById(R.id.tv_title);
        this.mGestureLockViewGroup = (GestureLockViewGroup) findViewById(R.id.gesturelock);
        initGesture();
        setGestureWhenNoSet();
    }







    private void initGesture() {
        mGestureLockViewGroup = (GestureLockViewGroup) findViewById(R.id.gesturelock);
        gestureEventListener();
        gesturePasswordSettingListener();
        gestureRetryLimitListener();
    }

    private void gestureEventListener() {
        mGestureLockViewGroup.setGestureEventListener(new GestureEventListener() {
            @Override
            public void onGestureEvent(boolean matched) {

                if (!matched) {
                    tv_state.setTextColor(Color.RED);
                    tv_state.setText("手势密码错误");
                } else {
                    if (isReset) {
                        isReset = false;
                        Toast.makeText(LockAcitity.this, "清除成功!", Toast.LENGTH_SHORT).show();
                        resetGesturePattern();
                    } else {
                        tv_state.setTextColor(Color.WHITE);
                        tv_state.setText("手势密码正确");
                    }
                }
            }
        });
    }

    private void gesturePasswordSettingListener() {
        mGestureLockViewGroup.setGesturePasswordSettingListener(new GesturePasswordSettingListener() {
            @Override
            public boolean onFirstInputComplete(int len) {

                if (len > 3) {
                    tv_state.setTextColor(Color.WHITE);
                    tv_state.setText("再次绘制手势密码");
                    return true;
                } else {
                    tv_state.setTextColor(Color.RED);
                    tv_state.setText("最少连接4个点，请重新输入!");
                    return false;
                }
            }

            @Override
            public void onSuccess() {

                tv_state.setTextColor(Color.WHITE);
                Toast.makeText(LockAcitity.this, "密码设置成功!", Toast.LENGTH_SHORT).show();
                tv_state.setText("请输入手势密码解锁!");
            }

            @Override
            public void onFail() {

                tv_state.setTextColor(Color.RED);
                tv_state.setText("与上一次绘制不一致，请重新绘制");
            }
        });
    }

    private void gestureRetryLimitListener() {
        mGestureLockViewGroup.setGestureUnmatchedExceedListener(3, new GestureUnmatchedExceedListener() {
            @Override
            public void onUnmatchedExceedBoundary() {
                tv_state.setTextColor(Color.RED);
                tv_state.setText("错误次数过多，请稍后再试!");
            }
        });
    }

    private void setGestureWhenNoSet() {
        if (!mGestureLockViewGroup.isSetPassword()){

            tv_state.setTextColor(Color.WHITE);
            tv_state.setText("绘制手势密码");
        }
    }

    private void resetGesturePattern() {
        mGestureLockViewGroup.removePassword();
        setGestureWhenNoSet();
        mGestureLockViewGroup.resetView();
    }







}
