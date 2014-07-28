package com.zhimeng.loupanguanli.activities;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhimeng.loupanguanli.R;
import com.zhimeng.loupanguanli.config.Config;
import com.zhimeng.loupanguanli.dao.LouPanDAO;
import com.zhimeng.loupanguanli.database.DBColumns;
import com.zhimeng.loupanguanli.database.DBHelper;
import com.zhimeng.loupanguanli.entity.LouPan;

public class MainActivity extends Activity {
	private Button btnCreate;// 添加新楼盘按钮
	private Button btn_search;// 搜索楼盘按钮
	private EditText et_loupan_keyword;// 搜索文本框
	private GridView gvLoupan;// 楼盘信息gridview
	private LayoutInflater inflater;// 将xml布局文件转化为view对象的服务类
	private ArrayList<LouPan> loupans;// gridview操作的楼盘集合
	private MyAdapter adpter;// 绑定到gridview的Adpter对象
	private LouPanDAO lpdao;// 楼盘数据库操作类

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
		lpdao = new LouPanDAO(MainActivity.this);

		// 通过Adapter显示数据
		loupans = lpdao.getAll();
		adpter = new MyAdapter();
		gvLoupan.setAdapter(adpter);

		// 设置gridview的长按事件
		gvLoupan.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 弹出框对象
				final Dialog dog = new Dialog(MainActivity.this);

				View v_menu = inflater.inflate(R.layout.menu_gridview_loupan,
						null);// 长按击时弹出的菜单对话框
				final LouPan thisLP = (LouPan) loupans.get(position);// 触发本事件的楼盘对象
				final int index = position;
				// 设置menu中按钮的点击事件-----------------------------------------
				// 编辑楼盘的按钮
				Button itemEditLP = (Button) v_menu
						.findViewById(R.id.itemEditLP);
				itemEditLP.setWidth(view.getWidth());
				itemEditLP.setHeight(view.getHeight() / 3);
				itemEditLP.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent0 = new Intent(MainActivity.this,
								EditActivity.class);
						Bundle bl0 = new Bundle();
						bl0.putSerializable("loupan", thisLP);
						intent0.putExtras(bl0);
						startActivity(intent0);// 进入楼盘修改界面
						dog.dismiss();// 关闭弹出框
					}
				});

				// 删除 楼盘的按钮
				Button itemDeleteLP = (Button) v_menu
						.findViewById(R.id.itemDeleteLP);
				itemDeleteLP.setWidth(view.getWidth());
				itemDeleteLP.setHeight(view.getHeight() / 3);
				itemDeleteLP.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method

						AlertDialog.Builder builder = new Builder(
								MainActivity.this);
						builder.setTitle("温馨提示").setMessage("确认删除?");
						builder.setPositiveButton("确定",
								new Dialog.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										lpdao.delete(thisLP);
										// 关闭弹出框，刷新数据显示
										dog.dismiss();
										loupans.remove(index);
										adpter.notifyDataSetChanged();

									}
								});

						builder.setNeutralButton("取消",
								new Dialog.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

									}
								});

						builder.create().show();
					}
				});

				// ----------------------------------------------------

				// // 弹出框对象
				// Dialog dialog = new Dialog(MainActivity.this);

				// 去掉标题，否则会影响高度计算，一定要在setContentView之前调用
				dog.requestWindowFeature(Window.FEATURE_NO_TITLE);

				// 设置弹出框要弹出的视图内容
				dog.setContentView(v_menu);

				// h获取弹出框的窗体对象，可以通过该对象对其参数进行配置，更新弹出窗口的属性(包括作保和宽高)
				Window dialogWindow = dog.getWindow();

				// 获取窗体参数对象
				WindowManager.LayoutParams lp = dialogWindow.getAttributes();

				// 设置窗体的X坐标、Y坐标边界位置，（这里分别为手机屏幕的最左边和最上边）
				dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);

				// 获取标题栏控件
				View rl = MainActivity.this.findViewById(R.id.rl);

				// 设置弹出窗的坐标值与被点击的控件的坐标一致(x,y值将成为与边界的相对坐标值)
				lp.x = (int) view.getX();
				lp.y = (int) (view.getY() + rl.getHeight());

				lp.alpha = 0.7f;// 设置弹出窗的透明度

				// 设置弹出窗的宽高与被点击的控件的坐标一致
				lp.width = view.getWidth();
				lp.height = view.getHeight();

				// 更新弹出窗的窗体属性
				dialogWindow.setAttributes(lp);

				// 显示弹出窗体
				dog.show();
				return false;
			}

		});

		/**
		 * 点击楼盘查看
		 */
		gvLoupan.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent1 = new Intent(MainActivity.this,
						PantoDongActivity.class);
				Bundle bl1 = new Bundle();
				bl1.putSerializable("loupan", loupans.get(position));
				intent1.putExtras(bl1);
				startActivity(intent1);// 进入楼盘查看界面
			}

		});

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

		btn_search = (Button) MainActivity.this.findViewById(R.id.btn_search);
		et_loupan_keyword = (EditText) MainActivity.this
				.findViewById(R.id.et_loupan_keyword);
		// 点击搜索，重新刷新页面
		btn_search.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				loupans = lpdao.getLPlistByName(et_loupan_keyword.getText()
						.toString().trim());
				adpter.notifyDataSetChanged();

			}
		});
	}

	private class MyAdapter extends BaseAdapter {

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

			File file = new File(Config.APP_DIR_PATH + "/"
					+ thisLP.getPicPath());
			if (file.exists()) {
				Bitmap bm = BitmapFactory.decodeFile(Config.APP_DIR_PATH + "/"
						+ thisLP.getPicPath());
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
