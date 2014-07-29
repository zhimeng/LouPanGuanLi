package com.zhimeng.loupanguanli.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zhimeng.loupanguanli.R;
import com.zhimeng.loupanguanli.config.Config;
import com.zhimeng.loupanguanli.dao.LouDongDAO;
import com.zhimeng.loupanguanli.dao.ZuoBiaoDAO;
import com.zhimeng.loupanguanli.entity.LouDong;
import com.zhimeng.loupanguanli.entity.LouPan;
import com.zhimeng.loupanguanli.entity.ZuoBiao;

public class CreateLouDongActivity extends Activity {
	private DrawerLayout dl;
	private Button btnCreateLouDong;
	private EditText etLouDongNumber;
	private EditText etLouDongName;
	private EditText etLayers;
	private EditText etSets;
	private EditText etLouDongRemark;
	private RelativeLayout rlLoc;
	private ImageView imgViewPic;

	private LouPan louPan;// 楼盘对象
	private LouDongDAO louDongDao;// 楼栋操作对象
	private ZuoBiaoDAO zuoBiaoDao;// 坐标操作对象

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_loudong);

		initData();
		initViews();

	}

	// 初始化数据
	private void initData() {
		louPan = (LouPan) getIntent().getSerializableExtra("loupan");
		louDongDao = new LouDongDAO(CreateLouDongActivity.this);
		zuoBiaoDao = new ZuoBiaoDAO(CreateLouDongActivity.this);
	}

	// 初始化视图
	private void initViews() {
		dl = (DrawerLayout) findViewById(R.id.dl);
		btnCreateLouDong = (Button) findViewById(R.id.btn_create_loudong);
		etLouDongNumber = (EditText) findViewById(R.id.et_loudong_number);
		etLouDongName = (EditText) findViewById(R.id.et_loudong_name);
		etLayers = (EditText) findViewById(R.id.et_layers);
		etSets = (EditText) findViewById(R.id.et_sets);
		etLouDongRemark = (EditText) findViewById(R.id.et_loudong_remark);
		rlLoc = (RelativeLayout) findViewById(R.id.rl_loc);
		imgViewPic = (ImageView) findViewById(R.id.imgv_pic);

		btnCreateLouDong.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = etLouDongName.getText().toString().trim();
				if (validate(name)) {
					// 楼栋文字相关信息
					String numberStr = etLouDongNumber.getText().toString()
							.trim();
					String layersStr = etLayers.getText().toString().trim();
					String setsStr = etSets.getText().toString().trim();
					String remark = etLouDongRemark.getText().toString().trim();
					Integer number = numberStr.equals("") ? null : Integer
							.valueOf(numberStr);
					Integer layers = layersStr.equals("") ? null : Integer
							.valueOf(layersStr);
					Integer sets = setsStr.equals("") ? null : Integer
							.valueOf(setsStr);
					LouDong louDong = new LouDong(number, name, layers, sets,
							remark, louPan.getId());
					// 楼栋坐标相关信息
					Button btn = new Button(CreateLouDongActivity.this);
					btn.setBackgroundResource(R.drawable.bg_btn_indicate);
					btn.setText(louDong.getName() + "#");
					rlLoc.addView(btn);
					RelativeLayout.LayoutParams mParams = (RelativeLayout.LayoutParams) btn
							.getLayoutParams();
					louDongDao.Insert(louDong);
					louDong = louDongDao.GetLouDongByName(name, louPan.getId());
					zuoBiaoDao.Insert(new ZuoBiao(mParams.leftMargin,
							mParams.topMargin, louPan.getId(), louDong.getId()));
					btn.setOnTouchListener(new MOnTouchListener(louPan.getId(),
							louDong.getId()));
				} else {
					Toast.makeText(CreateLouDongActivity.this, "楼栋名不为空并且应唯一",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		imgViewPic.setImageBitmap(BitmapFactory.decodeFile(Config.APP_DIR_PATH
				+ "/" + louPan.getPicPath()));

		dl.openDrawer(Gravity.RIGHT);

	}

	// 楼栋指示按钮拖动事件
	private class MOnTouchListener implements OnTouchListener {

		private Integer loupanId;
		private Integer loudongId;

		private int _xDelta, _yDelta;// 父控件的坐标

		public MOnTouchListener(Integer loupanId, Integer loudongId) {
			this.loupanId = loupanId;
			this.loudongId = loudongId;
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			final int X = (int) event.getRawX();
			final int Y = (int) event.getRawY();
			// 处理多点触摸
			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) v
						.getLayoutParams();
				_xDelta = X - lParams.leftMargin;
				_yDelta = Y - lParams.topMargin;
				break;
			case MotionEvent.ACTION_UP:
				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v
						.getLayoutParams();
				// 保存坐标
				zuoBiaoDao.saveZuoBiao(new ZuoBiao(params.leftMargin,
						params.topMargin, loupanId, loudongId));
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				break;
			case MotionEvent.ACTION_POINTER_UP:
				break;
			case MotionEvent.ACTION_MOVE:
				RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v
						.getLayoutParams();
				layoutParams.leftMargin = X - _xDelta;
				layoutParams.topMargin = Y - _yDelta;
				// 添加下面两行代码可以保证Button移动到父控件的右边沿的时候不变形
				layoutParams.rightMargin = -250;
				layoutParams.bottomMargin = -250;
				v.setLayoutParams(layoutParams);
				break;
			}
			rlLoc.invalidate();
			return true;
		}
	}

	// 输入合法性判断
	private boolean validate(String name) {
		boolean b = true;
		if (name.equals("") || louDongDao.isNameExisted(name, louPan.getId())) {
			b = false;
		}
		return b;
	}

	// 返回键监听
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startActivity(new Intent(CreateLouDongActivity.this,
					MainActivity.class));
			CreateLouDongActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
