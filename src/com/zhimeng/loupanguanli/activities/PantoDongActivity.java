package com.zhimeng.loupanguanli.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhimeng.loupanguanli.R;
import com.zhimeng.loupanguanli.dao.LouDongDAO;
import com.zhimeng.loupanguanli.dao.LouPanDAO;
import com.zhimeng.loupanguanli.dao.ZuoBiaoDAO;
import com.zhimeng.loupanguanli.entity.LouPan;

public class PantoDongActivity extends Activity {
	private ListView lvLouPans;
	private RelativeLayout rlLoc;
	private ImageView imgViewPic;
	private TextView tvLouPanDetail;

	private LouPan louPan;// 从主页面跳转过来所带的参数
	private ArrayList<LouPan> louPanList;// 所有楼盘信息
	private ArrayList<String> louPanNameList; // 楼盘名称列表

	ArrayList<Button> btns = new ArrayList<Button>();// 坐标指示按钮

	private LouPanDAO louPanDao = new LouPanDAO(PantoDongActivity.this);
	private LouDongDAO louDongDao = new LouDongDAO(PantoDongActivity.this);
	private ZuoBiaoDAO zuoBiaoDao = new ZuoBiaoDAO(PantoDongActivity.this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pan_to_dong);
		initData();
		initViews();
	}

	private void initData() {
		louPan = (LouPan) getIntent().getSerializableExtra("loupan");
		louPanList = louPanDao.getAll();
		for (int i = 0; i < louPanList.size(); i++) {
			louPanNameList.add(louPanList.get(i).getName());
		}
	}

	private void initViews() {
		lvLouPans = (ListView) findViewById(R.id.lv_loupan_list);
		rlLoc = (RelativeLayout) findViewById(R.id.rl_loc);
		imgViewPic = (ImageView) findViewById(R.id.imgv_pic);
		tvLouPanDetail = (TextView) findViewById(R.id.tv_loupan_detail);

		zuoBiaoDao.getAll(louPan.getId());

		lvLouPans.setAdapter(new ArrayAdapter<String>(PantoDongActivity.this,
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, louPanNameList));

		// ListView滚动到指定位置，并指定背景颜色
		int count = louPanNameList.size();
		for (int i = 0; i < count; i++) {
			if (louPan.getName().equals(louPanNameList.get(i))) {
				lvLouPans.getChildAt(i).setBackgroundColor(Color.GRAY);
				lvLouPans.setSelection(i);
				break;
			}
		}

		// 设置

		lvLouPans.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LouPan lp = louPanList.get(position);
			}
		});
	}
}
