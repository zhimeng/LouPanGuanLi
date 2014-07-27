package com.zhimeng.loupanguanli.activities;

import java.io.File;
import java.util.ArrayList;

import com.zhimeng.loupanguanli.R;
import com.zhimeng.loupanguanli.dao.LouPanDAO;
import com.zhimeng.loupanguanli.database.DBHelper;
import com.zhimeng.loupanguanli.entity.LouPan;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Button btnCreate;
	private GridView gvLoupan;// 楼盘信息gridview
	private LayoutInflater inflater;// 将xml布局文件转化为view对象的服务类
	private ArrayList loupans;// gridview操作的楼盘集合

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();

	}

	/**
	 * 初始化控件对象的方法
	 */
	private void initViews() {
		// 初始化LAYOUT_INFLATER_SERVICE系统服务
		inflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		gvLoupan = (GridView) MainActivity.this.findViewById(R.id.gv_loupan);

		// 读取数据库数据集合
		LouPanDAO lpdao = new LouPanDAO(MainActivity.this);

		// 通过Adapter显示数据
		gvLoupan.setAdapter(new MyAdapter(lpdao.getAll()));

		/*
		 * 
		 * // 编辑楼盘文件信息的按钮 Button btnEditLP_info = (Button) v_menu
		 * .findViewById(R.id.btnEditLP_info);
		 * btnEditLP_info.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub
		 * 
		 * } });
		 * 
		 * // 编辑楼盘图片的按钮 Button btnEditLP_pic = (Button)
		 * v_menu.findViewById(R.id.btnEditLP_pic);
		 * 
		 * // 编辑楼栋文件信息的按钮 Button btnEditLD_info = (Button) v_menu
		 * .findViewById(R.id.btnEditLD_info);
		 * 
		 * // 编辑楼栋坐标分布的按钮 Button btnEditLD_zuobiao = (Button) v_menu
		 * .findViewById(R.id.btnEditLD_zuobiao);
		 * 
		 * // 删除楼盘的按钮 Button btndelete_loupan = (Button) v_menu
		 * .findViewById(R.id.btndelete_loupan);
		 */
		// 设置gridview的长按事件

		gvLoupan.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) { // TODO Auto-generated method stub
				View v_menu = inflater.inflate(R.layout.menu_gridview_loupan,
						null);// 长按击时弹出的菜单对话框
				LouPan thisLP = (LouPan) loupans.get(position);// 触发本事件的楼盘对象

				AlertDialog.Builder builder_Dialog = new AlertDialog.Builder(
						MainActivity.this);
				builder_Dialog.setView(v_menu);
				builder_Dialog.show();

				// Intent intMenu=new Intent(this,v_menu);

				return false;
			}

		});

		// 设置gridview的单击事件
		/*
		 * gvLoupan.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, View view,
		 * int position, long id) { // TODO Auto-generated method stub LouPan
		 * thisLP = (LouPan) loupans.get(position);// 触发本事件的楼盘对象
		 * 
		 * // 点击楼盘后跳转页面到查看楼盘楼栋分布界面 Intent intent = new Intent(MainActivity.this,
		 * PantoDongActivity.class);
		 * 
		 * // intent.putExtra("loupan", thisLP); Bundle bundle = new Bundle();
		 * bundle.putSerializable("loupan", thisLP); intent.putExtras(bundle);
		 * startActivity(intent); }
		 * 
		 * });
		 */

		btnCreate = (Button) MainActivity.this.findViewById(R.id.btn_create);
		// 点击创建
		btnCreate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						CreateLouPanActivity.class);
				startActivity(intent);
			}
		});
	}

	private class MyAdapter extends BaseAdapter {

		public MyAdapter(ArrayList<LouPan> lps) {
			// TODO Auto-generated constructor stub
			loupans = lps;
		}

		@Override
		public int getCount() {
			return loupans.size();
		}

		@Override
		public Object getItem(int position) {
			return loupans.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			// 将item_gridview_loupan.xml文件转化为view对象
			View view = inflater.inflate(R.layout.item_gridview_loupan, null);

			// 获取当前要显示的楼盘记录
			LouPan thisLP = (LouPan) loupans.get(position);

			// 获取显示图片的item
			ImageView item_imgPic = (ImageView) view
					.findViewById(R.id.item_imgPic);

			File file = new File(thisLP.getPicPath());
			if (file.exists()) {
				Bitmap bm = BitmapFactory.decodeFile(thisLP.getPicPath());
				item_imgPic.setImageBitmap(bm);
			} else {
				// 没有楼盘图片就显示默认的图片
				item_imgPic.setImageResource(R.drawable.noup);
			}

			// 获取显示楼盘名的item
			TextView item_tvName = (TextView) view
					.findViewById(R.id.item_tvName);
			item_tvName.setText(thisLP.getName());
			// item_tvName.setText("南昌山水人家");
			// 按比例扩大图片的size居中显示，使得图片长(宽)等于或大于View的长(宽)
			item_imgPic.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			return view;
		}

	}

}
