package com.csdn.drawpassword;

import java.util.List;

import com.csdn.drawpassword.view.LockScreenView;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class LockScreenActivity extends Activity {

	private LockScreenView lockScreenView;
	private boolean isFirst = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_lock);
		lockScreenView = (LockScreenView) findViewById(R.id.lockscreenview);
		lockScreenView.setOnUnLockScreenListener(new LockScreenView.OnUnLockScreenListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(LockScreenActivity.this, "unlock success", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onFinish(List<Integer> list) {
				// TODO Auto-generated method stub
				if(isFirst){
					lockScreenView.setLockedList(list);
					isFirst = false;
				}
			}
			
			@Override
			public void onFail() {
				// TODO Auto-generated method stub
				Toast.makeText(LockScreenActivity.this, "unlock fail", Toast.LENGTH_SHORT).show();
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
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
