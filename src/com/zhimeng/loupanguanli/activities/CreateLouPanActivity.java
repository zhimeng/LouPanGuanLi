package com.zhimeng.loupanguanli.activities;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.zhimeng.loupanguanli.R;
import com.zhimeng.loupanguanli.config.Config;
import com.zhimeng.loupanguanli.dao.LouPanDAO;
import com.zhimeng.loupanguanli.entity.LouPan;
import com.zhimeng.loupanguanli.util.PhotoRequestUtil;
import com.zhimeng.loupanguanli.util.StorageUtil;
import com.zhimeng.loupanguanli.util.UUIDUtil;

public class CreateLouPanActivity extends Activity {
	private Button btnSelectPic;
	private Spinner spinnerSelectWay;
	private EditText etName;
	private EditText etAddress;
	private EditText etRemark;
	private Button btnSave;
	private Button btnCancel;

	// 如果用户选择拍照上传图片，则cameraFile指示图片存取路径
	private File cameraFile = null;
	// 剪切的图片名
	private String cutPicName = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_loupan);

		initViews();
	}

	// 初始化控件
	private void initViews() {
		btnSelectPic = (Button) findViewById(R.id.btn_select_pic);
		spinnerSelectWay = (Spinner) findViewById(R.id.spinner_select_way);
		etName = (EditText) findViewById(R.id.et_name);
		etAddress = (EditText) findViewById(R.id.et_address);
		etRemark = (EditText) findViewById(R.id.et_remark);
		btnSave = (Button) findViewById(R.id.btn_save);
		btnCancel = (Button) findViewById(R.id.btn_cancel);

		// 选择图片
		btnSelectPic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String ssw = spinnerSelectWay.getSelectedItem().toString();
				if (ssw.equals(Config.WAY_GALLERY)) {
					// 选择已有图片
					PhotoRequestUtil
							.getPicFromGallery(CreateLouPanActivity.this);
				} else if (ssw.equals(Config.WAY_CAMERA)) {
					// 拍照
					if (StorageUtil.isSDCardExisted()) {
						File file = null;
						// 默认存放在SDCard卡中
						if (!(file = new File(Config.APP_DIR_PATH + "/cache"))
								.exists()) {
							file.mkdirs();
						}
						cameraFile = new File(Config.APP_DIR_PATH + "/cache",
								UUIDUtil.getUStr());
						PhotoRequestUtil.getPicFromCamera(
								CreateLouPanActivity.this,
								Uri.fromFile(cameraFile));
					} else {
						Toast.makeText(CreateLouPanActivity.this, "SDCard不可用",
								Toast.LENGTH_LONG).show();
					}
				}
			}
		});

		// 创建楼盘
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LouPanDAO louPanDao = new LouPanDAO(CreateLouPanActivity.this);
				String nameStr = etName.getText().toString().trim();
				if ("".equals(nameStr) || cutPicName == null) {
					Toast.makeText(CreateLouPanActivity.this, "图片&楼盘名均不能为空",
							Toast.LENGTH_SHORT).show();
				} else if (louPanDao.isNameExisted(nameStr)) {
					Toast.makeText(CreateLouPanActivity.this, "楼盘名已存在",
							Toast.LENGTH_SHORT).show();
				} else {
					// String picPath = UUIDUtil.getUStr();

					String addressStr = etAddress.getText().toString().trim();
					String remarkStr = etRemark.getText().toString().trim();

					if (Config.APP_DIR_PATH != null) {
						File file = null;
						if (!(file = new File(Config.APP_DIR_PATH)).exists()) {
							file.mkdirs();
						}
						// 将Bitmap保存到SDCard中，大图片保存可能存在问题，暂时未测试
						// StorageUtil.saveBitmapToSDCard(bitmap,
						// Config.APP_DIR_PATH + "/" + picPath);
						LouPan louPan = new LouPan(nameStr, addressStr,
								remarkStr, cutPicName);
						louPanDao.insert(louPan);
						louPan = louPanDao.getLouPanByName(nameStr);
						// 跳转到创建楼栋页面
						Intent intent = new Intent(CreateLouPanActivity.this,
								CreateLouDongActivity.class);
						intent.putExtra("loupan", louPan);
						startActivity(intent);
						CreateLouPanActivity.this.finish();
					} else {
						Toast.makeText(CreateLouPanActivity.this,
								"保存失败，SDCard不可用", Toast.LENGTH_SHORT).show();
					}
				}

			}
		});

		// 取消创建
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CreateLouPanActivity.this.finish();
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case PhotoRequestUtil.PHOTO_REQUEST_GALLERY:
			if (data != null) {
				cutPicName = UUIDUtil.getUStr();
				if (Config.APP_DIR_PATH == null) {
					Toast.makeText(CreateLouPanActivity.this, "SDCard不可用",
							Toast.LENGTH_SHORT).show();
				} else {
					File file = null;
					if (!(file = new File(Config.APP_DIR_PATH)).exists()) {
						file.mkdirs();
					}
					Uri desUri = Uri.parse("file:///" + Config.APP_DIR_PATH
							+ "/" + cutPicName);
					PhotoRequestUtil.cropPic(data.getData(), desUri,
							CreateLouPanActivity.this);
				}
			}
			break;
		case PhotoRequestUtil.PHOTO_REQUEST_CAREMA:
			cutPicName = UUIDUtil.getUStr();
			if (Config.APP_DIR_PATH == null) {
				Toast.makeText(CreateLouPanActivity.this, "SDCard不可用",
						Toast.LENGTH_SHORT).show();
			} else {
				File file = null;
				if (!(file = new File(Config.APP_DIR_PATH)).exists()) {
					file.mkdirs();
				}
				Uri desUri = Uri.parse("file:///" + Config.APP_DIR_PATH + "/"
						+ cutPicName);
				PhotoRequestUtil.cropPic(Uri.fromFile(cameraFile), desUri,
						CreateLouPanActivity.this);
			}
			break;
		case PhotoRequestUtil.PHOTO_REQUEST_CUT:
			// if (data != null) {
			// bitmap = data.getParcelableExtra("data");
			// }
			// // 删除缓存文件
			// if (cameraFile != null && cameraFile.exists())
			// cameraFile.delete();
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
