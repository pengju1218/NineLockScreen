package com.csdn.drawpassword;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.csdn.drawpassword.view.LockScreenView;
import com.csdn.drawpassword.view.UnlockView;

import java.util.List;

public class UnLockActivity extends Activity {

	private UnlockView lockScreenView;
	private boolean isFirst = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.u_lock);
		lockScreenView = (UnlockView) findViewById(R.id.lockscreenview);

		lockScreenView.setUnlockListener(new UnlockView.UnlockListener() {
			@Override
			public void drawOver(int[] pwd) {
				Toast.makeText(UnLockActivity.this, "unlock fail" + pwd.length + "==" + pwd[0], Toast.LENGTH_SHORT).show();
			}

			@Override
			public void isPwdRight(boolean isRight) {
				Toast.makeText(UnLockActivity.this, "unlock fail" + isRight, Toast.LENGTH_SHORT).show();
			}
		});


	}


}
