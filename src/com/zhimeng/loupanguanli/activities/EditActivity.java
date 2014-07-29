package com.zhimeng.loupanguanli.activities;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.zhimeng.loupanguanli.R;
import com.zhimeng.loupanguanli.config.Config;
import com.zhimeng.loupanguanli.dao.LouDongDAO;
import com.zhimeng.loupanguanli.dao.LouPanDAO;
import com.zhimeng.loupanguanli.dao.ZuoBiaoDAO;
import com.zhimeng.loupanguanli.entity.LouDong;
import com.zhimeng.loupanguanli.entity.LouPan;
import com.zhimeng.loupanguanli.entity.ZuoBiao;
import com.zhimeng.loupanguanli.util.PhotoRequestUtil;
import com.zhimeng.loupanguanli.util.StorageUtil;
import com.zhimeng.loupanguanli.util.UUIDUtil;

public class EditActivity extends Activity {
	private DrawerLayout dl;
	private LinearLayout llShow, llEdit;
	private Button btnEdit;
	private TextView tvName, tvAddress, tvRemark;
	private Button btnSave, btnCancel;
	private Button btnGallery, btnCamera;
	private EditText etName, etAddress, etRemark;
	private RelativeLayout rlLoc;
	private ImageView imgViewPic;

	// private PopupWindow mPW;// 用于弹出楼栋详细信息

	// 控件从自身底部滑入、滑出动画
	private Animation slideIn, slidOut;

	private LouPan louPan;
	private String newPicPath;
	private File cameraCacheFile;// 拍照临时存储文件
	private LouPanDAO louPanDao = new LouPanDAO(EditActivity.this);
	private LouDongDAO louDongDao = new LouDongDAO(EditActivity.this);
	private ZuoBiaoDAO zuoBiaoDao = new ZuoBiaoDAO(EditActivity.this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		initData();
		initViews();
	}

	private void initData() {
		louPan = (LouPan) getIntent().getSerializableExtra("loupan");
		newPicPath = louPan.getPicPath();
	}

	private void initViews() {
		dl = (DrawerLayout) findViewById(R.id.dl);
		llShow = (LinearLayout) findViewById(R.id.ll_show);
		llEdit = (LinearLayout) findViewById(R.id.ll_edit);
		btnEdit = (Button) findViewById(R.id.btn_edit);
		tvName = (TextView) findViewById(R.id.tv_name);
		tvAddress = (TextView) findViewById(R.id.tv_address);
		tvRemark = (TextView) findViewById(R.id.tv_remark);
		btnSave = (Button) findViewById(R.id.btn_save);
		btnCancel = (Button) findViewById(R.id.btn_cancel);
		btnGallery = (Button) findViewById(R.id.btn_gallery);
		btnCamera = (Button) findViewById(R.id.btn_camera);
		etName = (EditText) findViewById(R.id.et_name);
		etAddress = (EditText) findViewById(R.id.et_address);
		etRemark = (EditText) findViewById(R.id.et_remark);
		rlLoc = (RelativeLayout) findViewById(R.id.rl_loc);
		imgViewPic = (ImageView) findViewById(R.id.imgv_pic);

		slideIn = AnimationUtils.loadAnimation(EditActivity.this,
				R.anim.slide_fromitselftop_in);
		slidOut = AnimationUtils.loadAnimation(EditActivity.this,
				R.anim.slide_fromitselftop_out);

		// 编辑楼盘按钮点击事件
		btnEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 楼盘信息显示版块消失
				llShow.startAnimation(slidOut);
				llShow.setVisibility(View.GONE);
				// 楼盘信息编辑版块滑出
				llEdit.setVisibility(View.VISIBLE);
				llEdit.startAnimation(slideIn);
			}
		});

		// 设置楼盘信息显示版块
		tvName.setText(louPan.getName());
		tvAddress.setText(louPan.getAddress());
		tvRemark.setText(louPan.getRemark());

		// 楼盘编辑保存按钮点击事件
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = etName.getText().toString().trim();
				if (name.equals("")) {
					Toast.makeText(EditActivity.this, "楼盘名不能为空",
							Toast.LENGTH_SHORT).show();
				} else if (!name.equals(louPan.getName())
						&& louPanDao.isNameExisted(name)) {
					Toast.makeText(EditActivity.this, "楼盘名已存在",
							Toast.LENGTH_SHORT).show();
				} else {
					String address = etAddress.getText().toString().trim();
					String remark = etRemark.getText().toString().trim();
					// // 删除原有图片
					// File f = new File(Config.APP_DIR_PATH + "/"
					// + louPan.getPicPath());
					// f.delete();
					// 更新数据库楼盘信息
					louPan.setName(name);
					louPan.setAddress(address);
					louPan.setRemark(remark);
					louPan.setPicPath(newPicPath);
					louPanDao.update(louPan);
					// 楼盘信息编辑版块消失
					llEdit.startAnimation(slidOut);
					llEdit.setVisibility(View.GONE);
					// 楼盘信息显示版块滑出
					llShow.setVisibility(View.VISIBLE);
					tvName.setText(louPan.getName());
					tvAddress.setText(louPan.getAddress());
					tvRemark.setText(louPan.getRemark());
					llShow.startAnimation(slideIn);
					// 楼盘图片重新加载
					imgViewPic.setImageBitmap(BitmapFactory
							.decodeFile(Config.APP_DIR_PATH + "/" + newPicPath));
				}
			}
		});

		// 楼盘编辑版块取消按钮点击事件
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				newPicPath = louPan.getPicPath();
				// 楼盘信息编辑版块消失
				llEdit.startAnimation(slidOut);
				llEdit.setVisibility(View.GONE);
				// 楼盘信息显示版块滑出
				llShow.setVisibility(View.VISIBLE);
				llShow.startAnimation(slideIn);
			}
		});

		// 楼盘编辑版块从图库选择图片按钮点击事件
		btnGallery.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 选择已有图片
				PhotoRequestUtil.getPicFromGallery(EditActivity.this);
			}
		});

		// 楼盘编辑版块拍照选择图片按钮点击事件
		btnCamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 拍照
				if (StorageUtil.isSDCardExisted()) {
					File file = null;
					// 默认存放在SDCard卡中
					if (!(file = new File(Config.APP_DIR_PATH + "/cache"))
							.exists()) {
						file.mkdirs();
					}
					cameraCacheFile = new File(Config.APP_DIR_PATH + "/cache",
							UUIDUtil.getUStr());
					PhotoRequestUtil.getPicFromCamera(EditActivity.this,
							Uri.fromFile(cameraCacheFile));
				} else {
					Toast.makeText(EditActivity.this, "SDCard不可用",
							Toast.LENGTH_LONG).show();
				}
			}
		});

		etName.setText(louPan.getName());
		etAddress.setText(louPan.getAddress());
		etRemark.setText(louPan.getRemark());

		imgViewPic.setImageBitmap(BitmapFactory.decodeFile(Config.APP_DIR_PATH
				+ "/" + louPan.getPicPath()));

		// 楼栋坐标显示
		ArrayList<ZuoBiao> zuoBiaos = zuoBiaoDao.getAll(louPan.getId());
		int total = zuoBiaos.size();
		for (int i = 0; i < total; i++) {
			ZuoBiao zb = zuoBiaos.get(i);
			LouDong ld = louDongDao.GetLouDongById(zb.getLoudongId());
			Button btn = new Button(EditActivity.this);
			btn.setBackgroundResource(R.drawable.bg_btn_indicate);
			btn.setText(ld.getName() + "#");
			btn.setTag(R.id.tag_loudong, ld);// Button按键将楼栋信息随身携带
			btn.setTag(R.id.tag_loupan, zb);// Button将坐标信息随身携带
			rlLoc.addView(btn);
			RelativeLayout.LayoutParams mParams = (LayoutParams) btn
					.getLayoutParams();
			mParams.leftMargin = zb.getX();
			mParams.topMargin = zb.getY();
			btn.setLayoutParams(mParams);
			btn.setOnClickListener(ocl);
			btn.setOnTouchListener(new MOnTouchListener(zb.getLoupanId(), zb
					.getLoudongId()));
		}

		dl.openDrawer(Gravity.RIGHT);
	}

	// 楼栋指示按钮的点击事件
	private OnClickListener ocl = new OnClickListener() {

		@Override
		public void onClick(View v) {
			final Button zbBtn = (Button) v;
			final LouDong ld = (LouDong) v.getTag(R.id.tag_loudong);
			final ZuoBiao zb = (ZuoBiao) v.getTag(R.id.tag_loupan);
			View contentView = LayoutInflater.from(EditActivity.this).inflate(
					R.layout.layout_loudong_edit, null);
			final PopupWindow pw = new PopupWindow(contentView,
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			final EditText etNumber = (EditText) contentView
					.findViewById(R.id.et_number);
			final EditText etName = (EditText) contentView
					.findViewById(R.id.et_name);
			final EditText etLayers = (EditText) contentView
					.findViewById(R.id.et_layers);
			final EditText etSets = (EditText) contentView
					.findViewById(R.id.et_sets);
			final EditText etRemark = (EditText) contentView
					.findViewById(R.id.et_remark);
			etNumber.setText(String.valueOf(ld.getNumber()));
			etName.setText(ld.getName());
			etLayers.setText(String.valueOf(ld.getLayers()));
			etSets.setText(String.valueOf(ld.getSets()));
			etRemark.setText(ld.getRemark());
			// 设置PopupWindow的拖动事件
			contentView.setOnTouchListener(new OnTouchListener() {
				private int lastX, lastY;
				private int mScreenX = 0, mScreenY = 0;
				private int dx, dy;

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					/*
					 * 要使RelativeLayout的拖动事件有效，必须在布局文件中设置以下几个属性
					 * 1、android:clickable="true" 2、android:focusable="true"
					 * 3、android:focusableInTouchMode="true"
					 */
					// 注意onTouch方法的实现
					int action = event.getAction();
					switch (action) {
					case MotionEvent.ACTION_DOWN:
						lastX = (int) event.getRawX();
						lastY = (int) event.getRawY();
						break;
					case MotionEvent.ACTION_UP:
						mScreenX = dx;
						mScreenY = dy;
						break;
					case MotionEvent.ACTION_MOVE:
						dx = ((int) event.getRawX()) - lastX + mScreenX;
						dy = ((int) event.getRawY()) - lastY + mScreenY;
						pw.update(dx, dy, -1, -1);
						break;
					}
					return false;
				}
			});
			((Button) contentView.findViewById(R.id.btn_save))
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							String number = etNumber.getText().toString()
									.trim();
							String name = etName.getText().toString().trim();
							String layers = etLayers.getText().toString()
									.trim();
							String sets = etSets.getText().toString().trim();
							String remark = etRemark.getText().toString()
									.trim();
							if (name.equals("")) {
								Toast.makeText(EditActivity.this, "楼栋名不能为空",
										Toast.LENGTH_SHORT).show();
							} else if (!name.endsWith(ld.getName())
									&& louDongDao.isNameExisted(name,
											ld.getLoupanId())) {
								Toast.makeText(EditActivity.this, "楼栋名已存在",
										Toast.LENGTH_SHORT).show();
							} else {
								ld.setNumber(number.equals("") ? null : Integer
										.valueOf(number));
								ld.setName(name);
								ld.setLayers(layers.equals("") ? null : Integer
										.valueOf(layers));
								ld.setSets(sets.equals("") ? null : Integer
										.valueOf(sets));
								ld.setRemark(remark);
								louDongDao.Update(ld);
								pw.dismiss();
							}
						}
					});
			((Button) contentView.findViewById(R.id.btn_delete))
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							louDongDao.Delete(ld);
							zuoBiaoDao.Delete(zb);
							zbBtn.setVisibility(View.GONE);
							pw.dismiss();
						}
					});

			// 点击PopupWindow外部消失
			pw.update(contentView, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			pw.setFocusable(true);
			pw.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.bg_pop_up));
			pw.setOutsideTouchable(true);
			// 不知为何设置没有效果
			pw.setAnimationStyle(R.style.popwin_anim_style);
			pw.showAtLocation(rlLoc, Gravity.NO_GRAVITY, 2, 2);
			pw.update();
		}
	};

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
			// v.performClick();
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
			return false;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case PhotoRequestUtil.PHOTO_REQUEST_GALLERY:
			if (data != null) {
				newPicPath = UUIDUtil.getUStr();
				if (Config.APP_DIR_PATH == null) {
					Toast.makeText(EditActivity.this, "SDCard不可用",
							Toast.LENGTH_SHORT).show();
				} else {
					File file = null;
					if (!(file = new File(Config.APP_DIR_PATH)).exists()) {
						file.mkdirs();
					}
					Uri desUri = Uri.parse("file:///" + Config.APP_DIR_PATH
							+ "/" + newPicPath);
					PhotoRequestUtil.cropPic(data.getData(), desUri,
							EditActivity.this);
				}
			}
			break;
		case PhotoRequestUtil.PHOTO_REQUEST_CAREMA:
			newPicPath = UUIDUtil.getUStr();
			if (Config.APP_DIR_PATH == null) {
				Toast.makeText(EditActivity.this, "SDCard不可用",
						Toast.LENGTH_SHORT).show();
			} else {
				File file = null;
				if (!(file = new File(Config.APP_DIR_PATH)).exists()) {
					file.mkdirs();
				}
				Uri desUri = Uri.parse("file:///" + Config.APP_DIR_PATH + "/"
						+ newPicPath);
				PhotoRequestUtil.cropPic(Uri.fromFile(cameraCacheFile), desUri,
						EditActivity.this);
			}
			break;
		case PhotoRequestUtil.PHOTO_REQUEST_CUT:
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	// 返回键监听
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 配合MainActivity的singleTask启动模式
			startActivity(new Intent(EditActivity.this, MainActivity.class));
			EditActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
