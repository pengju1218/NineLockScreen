package com.csdn.drawpassword;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.csdn.drawpassword.axml.ContentView;
import com.csdn.drawpassword.axml.Drawl;


/**
 * 
 * @author yinqiaoyin
 * 
 */
public class ContentActivity extends Activity {

	private ContentView content;

	private FrameLayout bodylayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.body_layout);
		this.bodylayout = (FrameLayout) findViewById(R.id.body_layout);


		// 初始化一个显示各个点的viewGroup
		content = new ContentView(this, "1236",new Drawl.GestureCallBack() {

			@Override
			public void checkedSuccess() {
				Toast.makeText(ContentActivity.this,"校验成功", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void checkedFail() {
				Toast.makeText(ContentActivity.this,"校验失败", Toast.LENGTH_SHORT).show();
			}
		});

		//设置手势解锁显示到哪个布局里面
		content.setParentView(bodylayout);
	}

}
