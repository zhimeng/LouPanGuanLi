package com.zhimeng.loupanguanli.activities;

import com.zhimeng.loupanguanli.R;
import com.zhimeng.loupanguanli.entity.LouPan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditActivity extends Activity {
	private LinearLayout llShow, llEdit;
	private Button btnEdit;
	private TextView tvName, tvAddress, tvRemark;
	private Button btnSave, btnCancel;
	private Button btnGallery, btnCamera;
	private EditText etName, etAddress, etRemark;
	private ImageView imgViewPic;

	private LouPan louPan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		initData();
		initViews();
	}

	private void initData() {
		louPan = (LouPan) getIntent().getSerializableExtra("loupan");
	}

	private void initViews() {
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
		imgViewPic = (ImageView) findViewById(R.id.imgv_pic);

		btnEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
			}
		});
	}
}
