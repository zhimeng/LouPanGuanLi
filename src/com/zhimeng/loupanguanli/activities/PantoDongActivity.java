package com.zhimeng.loupanguanli.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.zhimeng.loupanguanli.R;
import com.zhimeng.loupanguanli.config.Config;
import com.zhimeng.loupanguanli.dao.LouDongDAO;
import com.zhimeng.loupanguanli.dao.LouPanDAO;
import com.zhimeng.loupanguanli.dao.ZuoBiaoDAO;
import com.zhimeng.loupanguanli.entity.LouDong;
import com.zhimeng.loupanguanli.entity.LouPan;
import com.zhimeng.loupanguanli.entity.ZuoBiao;

public class PantoDongActivity extends Activity {
	private ListView lvLouPans;
	private RelativeLayout rlLoc;
	private ImageView imgViewPic;
	private TextView tvLouPanDetail;

	private LouPan louPan;// 从主页面跳转过来所带的参数
	private ArrayList<LouPan> louPanList;// 所有楼盘信息
	private ArrayList<String> louPanNameList; // 楼盘名称列表

	ArrayList<Button> btns = new ArrayList<Button>();// 坐标指示按钮，主要用于管理按钮的删除

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

	// 初始化数据
	private void initData() {
		louPan = (LouPan) getIntent().getSerializableExtra("loupan");
		louPanList = louPanDao.getAll();
		int total = louPanList.size();
		for (int i = 0; i < total; i++) {
			louPanNameList.add(louPanList.get(i).getName());
		}
	}

	// 初始化视图
	private void initViews() {
		lvLouPans = (ListView) findViewById(R.id.lv_loupan_list);
		rlLoc = (RelativeLayout) findViewById(R.id.rl_loc);
		imgViewPic = (ImageView) findViewById(R.id.imgv_pic);
		tvLouPanDetail = (TextView) findViewById(R.id.tv_loupan_detail);

		// ListView设置适配器
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

		// 从上个页面刚跳转过来的图片、数据、楼栋指示按钮显示
		imgViewPic.setImageBitmap(BitmapFactory.decodeFile(Config.APP_DIR_PATH
				+ "/" + louPan.getPicPath()));
		tvLouPanDetail.setText("楼盘名称：" + louPan.getName() + "；楼盘地址："
				+ louPan.getAddress() + "；备注：" + louPan.getRemark());
		ArrayList<ZuoBiao> zuoBiaos = zuoBiaoDao.getAll(louPan.getId());
		int total = zuoBiaos.size();
		for (int i = 0; i < total; i++) {
			ZuoBiao zb = zuoBiaos.get(i);
			LouDong ld = louDongDao.GetLouDongById(zb.getLoudongId());
			Button btn = new Button(PantoDongActivity.this);
			btn.setText(ld.getName() + "#");
			btn.setTag(ld);// Button按键将楼栋信息随身携带
			rlLoc.addView(btn);
			RelativeLayout.LayoutParams mParams = (LayoutParams) btn
					.getLayoutParams();
			mParams.leftMargin = zb.getX();
			mParams.topMargin = zb.getY();
			btn.setLayoutParams(mParams);
			btn.setOnClickListener(ocl);
			btns.add(btn);
		}

		// 设置ListView的Item点击事件
		lvLouPans.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 选中高亮显示
				int childCount = parent.getChildCount();
				for (int i = 0; i < childCount; i++) {
					parent.getChildAt(i).setBackgroundColor(Color.WHITE);
				}
				parent.getChildAt(position).setBackgroundColor(Color.GRAY);
				// 获取楼盘信息并显示
				LouPan lp = louPanList.get(position);
				imgViewPic.setImageBitmap(BitmapFactory
						.decodeFile(Config.APP_DIR_PATH + "/" + lp.getPicPath()));
				tvLouPanDetail.setText("楼盘名称：" + lp.getName() + "；楼盘地址："
						+ lp.getAddress() + "；备注：" + lp.getRemark());
				// 楼栋指示按钮显示
				int zsCount = btns.size();
				for (int i = 0; i < zsCount; i++) {
					btns.get(i).setVisibility(View.GONE);
				}
				btns.clear();
				ArrayList<ZuoBiao> zbs = zuoBiaoDao.getAll(lp.getId());
				int total = zbs.size();
				for (int i = 0; i < total; i++) {
					ZuoBiao zb = zbs.get(i);
					LouDong ld = louDongDao.GetLouDongById(zb.getLoudongId());
					Button btn = new Button(PantoDongActivity.this);
					btn.setText(ld.getName() + "#");
					btn.setTag(ld);// Button按键将楼栋信息随身携带
					rlLoc.addView(btn);
					RelativeLayout.LayoutParams mParams = (LayoutParams) btn
							.getLayoutParams();
					mParams.leftMargin = zb.getX();
					mParams.topMargin = zb.getY();
					btn.setLayoutParams(mParams);
					btn.setOnClickListener(ocl);
					btns.add(btn);
				}
			}
		});
	}

	// 楼栋指示按钮的点击事件
	private OnClickListener ocl = new OnClickListener() {

		@Override
		public void onClick(View v) {
			LouDong ld = (LouDong) v.getTag();
			View contentView = LayoutInflater.from(PantoDongActivity.this)
					.inflate(R.layout.layout_loudong_detail, null);
			((TextView) contentView.findViewById(R.id.tv_number)).setText(ld
					.getNumber());
			((TextView) contentView.findViewById(R.id.tv_name)).setText(ld
					.getName());
			((TextView) contentView.findViewById(R.id.tv_layers)).setText(ld
					.getLayers());
			((TextView) contentView.findViewById(R.id.tv_sets)).setText(ld
					.getSets());
			((TextView) contentView.findViewById(R.id.tv_remark)).setText(ld
					.getRemark());
			PopupWindow pw = new PopupWindow(contentView,
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			pw.setBackgroundDrawable(new BitmapDrawable());
			pw.setOutsideTouchable(true);
			pw.showAsDropDown(v);
		}
	};
}
