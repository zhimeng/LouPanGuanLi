package com.zhimeng.loupanguanli.activities;

import com.zhimeng.loupanguanli.R;
import com.zhimeng.loupanguanli.database.DBHelper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Button btnCreate;
	private GridView gvLoupan;// 楼盘信息gridview
	private LayoutInflater inflater;// 将xml布局文件转化为view对象的服务类

	private DBHelper dbHelper;

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

		dbHelper = new DBHelper(MainActivity.this);
		gvLoupan = (GridView) MainActivity.this.findViewById(R.id.gv_loupan);

		btnCreate = (Button) MainActivity.this.findViewById(R.id.btn_create);
		// 点击创建
		btnCreate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						CreateActivity.class);
				startActivity(intent);
			}
		});
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 30;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = inflater.inflate(R.layout.item_gridview_loupan, null);
			ImageView ItemImage = (ImageView) view
					.findViewById(R.id.imgloupanitem);
			ItemImage.setImageResource(R.drawable.ic_launcher);
			TextView itemtext = (TextView) view.findViewById(R.id.tvloupanItem);
			itemtext.setText("哈哈哈哈哈");
			ItemImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
			return view;
		}

	}

}
