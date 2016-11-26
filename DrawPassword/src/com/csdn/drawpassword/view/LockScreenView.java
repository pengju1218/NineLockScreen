/**   
 * Copyright © 2014 All rights reserved.
 * 
 * @Title: LockScreenView.java 
 * @Prject: LockScreen
 * @Package: com.example.lockscreen 
 * @Description: TODO
 * @author: raot raotao.bj@cabletech.com.cn 
 * @date: 2014年12月12日 上午10:00:57 
 * @version: V1.0   
 */
package com.csdn.drawpassword.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: LockScreenView
 * @Description: TODO
 * @author: raot raotao.bj@cabletech.com.cn
 * @date: 2014年12月12日 上午10:00:57
 */
@SuppressLint("DrawAllocation")
public class LockScreenView extends View {

	private int bitmapWidth, lineWidth;
	private Point[] points = new Point[9];
	private Paint paint = new Paint();
	private List<Integer> selectedList = new ArrayList<Integer>();
	private boolean isWrong = false;
	private MotionEvent moveEvent;
	private List<Integer> lockedList = new ArrayList<Integer>();
	private OnUnLockScreenListener onUnLockScreenListener = new OnUnLockScreenListener() {

		@Override
		public void onSuccess() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onFinish(List<Integer> list) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onFail() {
			// TODO Auto-generated method stub

		}
	};
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (selectedList.size() > 0) {
				onUnLockScreenListener.onFinish(selectedList);
			}
			switch (msg.what) {
			case 0:
				onUnLockScreenListener.onFail();
				break;
			case 1:
				onUnLockScreenListener.onSuccess();
				break;
			default:
				break;
			}
			selectedList = new ArrayList<Integer>();
			isWrong = false;
			invalidate();
		};
	};

	public LockScreenView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public LockScreenView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public LockScreenView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param onUnLockScreenListener
	 *            the onUnLockScreenListener to set
	 */
	public void setOnUnLockScreenListener(
			OnUnLockScreenListener onUnLockScreenListener) {
		this.onUnLockScreenListener = onUnLockScreenListener;
	}

	/**
	 * 设置锁屏密码
	 * 
	 * @Title: setLockedList
	 * @Description: TODO
	 * @param list
	 *            1~9不重复的数字集合
	 * @return void
	 */
	public void setLockedList(List<Integer> list) {
		lockedList = list;
	}

	/**
	 * 得到锁屏密码
	 * 
	 * @Title: getLockedList
	 * @Description: TODO
	 * @return
	 * @return List<Integer> 1~9不重复的数字集合
	 */
	public List<Integer> getLockedList() {
		return lockedList;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(2);
		for (int i = 0; i < points.length; i++) {
			if (selectedList.contains(i)) {
				if (isWrong) {
					paint.setColor(Color.RED);
				} else {
					paint.setColor(Color.BLUE);
				}
				paint.setAlpha(255);
				paint.setStyle(Paint.Style.STROKE);
				canvas.drawCircle(points[i].x, points[i].y, bitmapWidth, paint);
			} else {
				paint.setColor(Color.LTGRAY);
				paint.setAlpha(255);
				paint.setStyle(Paint.Style.FILL);
				canvas.drawCircle(points[i].x, points[i].y, bitmapWidth, paint);
				paint.setColor(Color.GRAY);
				paint.setAlpha(255);
			}
			paint.setStyle(Paint.Style.FILL);
			canvas.drawCircle(points[i].x, points[i].y, lineWidth, paint);
		}
		paint.setStyle(Paint.Style.FILL);
		paint.setStrokeWidth(lineWidth * 2);
		if (isWrong) {
			paint.setColor(Color.RED);
		} else {
			paint.setColor(Color.BLUE);
		}
		paint.setAlpha(100);
		for (int i = 0; i < selectedList.size(); i++) {
			if (i > 0) {
				canvas.drawLine(points[selectedList.get(i - 1)].x,
						points[selectedList.get(i - 1)].y,
						points[selectedList.get(i)].x,
						points[selectedList.get(i)].y, paint);
			}
		}
		if (selectedList.size() > 0 && moveEvent != null) {
			canvas.drawCircle(moveEvent.getX(), moveEvent.getY(), lineWidth,
					paint);
			canvas.drawLine(
					points[selectedList.get(selectedList.size() - 1)].x,
					points[selectedList.get(selectedList.size() - 1)].y,
					moveEvent.getX(), moveEvent.getY(), paint);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = getWidth();
		int height = getHeight();
		if (width > height) {
			bitmapWidth = height / 9;
		} else {
			bitmapWidth = width / 9;
		}
		lineWidth = bitmapWidth / 3;
		for (int i = 0; i < points.length; i++) {
			if (i < 3) {
				points[i] = new Point((1 + 2 * i) * width / 6, height / 6);
			} else if (i < 6 && i >= 3) {
				points[i] = new Point((1 + 2 * (i - 3)) * width / 6,
						3 * height / 6);
			} else {
				points[i] = new Point((1 + 2 * (i - 6)) * width / 6,
						5 * height / 6);
			}
		}
	}

	private int type;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (isWrong) {
			return super.onTouchEvent(event);
		}
		moveEvent = MotionEvent.obtain(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isTouchPoint(event);
			break;
		case MotionEvent.ACTION_UP:
			moveEvent = null;
			type = -1;
			if (lockedList == null || lockedList.size() == 0) {
				isWrong = false;
				type = 2;
			} else {
				boolean isSuccess = true;
				if (lockedList.size() == selectedList.size()) {
					for (int i = 0; i < lockedList.size(); i++) {
						if (lockedList.get(i) != selectedList.get(i)) {
							isSuccess = false;
							break;
						}
					}
				} else {
					isSuccess = false;
				}
				if (!isSuccess) {
					isWrong = true;
					type = 0;
				} else {
					isWrong = false;
					type = 1;
				}
			}
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					handler.sendEmptyMessage(type);
				}
			}).start();
			break;
		case MotionEvent.ACTION_MOVE:
			isTouchPoint(event);
			break;
		default:
			return super.onTouchEvent(event);
		}
		invalidate();
		return true;
	}

	private void isTouchPoint(MotionEvent event) {
		for (int i = 0; i < points.length; i++) {
			Point point = points[i];
			if (event.getX() >= point.x - bitmapWidth
					&& event.getX() <= point.x + bitmapWidth
					&& event.getY() >= point.y - bitmapWidth
					&& event.getY() <= point.y + bitmapWidth) {
				if (!selectedList.contains(i)) {
					if (selectedList.size() > 0) {
						int j = selectedList.get(selectedList.size() - 1);
						switch (i) {
						case 0:
							if (j == 2) {
								if (!selectedList.contains(1)) {
									selectedList.add(1);
								}
							} else if (j == 6) {
								if (!selectedList.contains(3)) {
									selectedList.add(3);
								}
							} else if (j == 8) {
								if (!selectedList.contains(4)) {
									selectedList.add(4);
								}
							}
							break;
						case 1:
							if (j == 7) {
								if (!selectedList.contains(4)) {
									selectedList.add(4);
								}
							}
							break;
						case 2:
							if (j == 0) {
								if (!selectedList.contains(1)) {
									selectedList.add(1);
								}
							} else if (j == 6) {
								if (!selectedList.contains(4)) {
									selectedList.add(4);
								}
							} else if (j == 8) {
								if (!selectedList.contains(5)) {
									selectedList.add(5);
								}
							}
							break;
						case 3:
							if (j == 5) {
								if (!selectedList.contains(4)) {
									selectedList.add(4);
								}
							}
							break;
						case 5:
							if (j == 3) {
								if (!selectedList.contains(4)) {
									selectedList.add(4);
								}
							}
							break;
						case 6:
							if (j == 0) {
								if (!selectedList.contains(3)) {
									selectedList.add(3);
								}
							} else if (j == 2) {
								if (!selectedList.contains(4)) {
									selectedList.add(4);
								}
							} else if (j == 8) {
								if (!selectedList.contains(7)) {
									selectedList.add(7);
								}
							}
							break;
						case 7:
							if (j == 1) {
								if (!selectedList.contains(4)) {
									selectedList.add(4);
								}
							}
							break;
						case 8:
							if (j == 0) {
								if (!selectedList.contains(4)) {
									selectedList.add(4);
								}
							} else if (j == 2) {
								if (!selectedList.contains(5)) {
									selectedList.add(5);
								}
							} else if (j == 6) {
								if (!selectedList.contains(7)) {
									selectedList.add(7);
								}
							}
							break;
						}
					}
					selectedList.add(i);
				}
				return;
			}
		}
	}

	public interface OnUnLockScreenListener {
		/**
		 * 解锁失败
		 * 
		 * @Title: onFail
		 * @Description: TODO
		 * @return void
		 */
		public void onFail();

		/**
		 * 解锁成功
		 * 
		 * @Title: onSuccess
		 * @Description: TODO
		 * @return void
		 */
		public void onSuccess();

		/**
		 * @Title: onFinish
		 * @Description: TODO
		 * @param list
		 *            触摸结束后，被选中点的下标
		 * @return void
		 */
		public void onFinish(List<Integer> list);
	}

}
