package com.zhimeng.loupanguanli.dao;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

import com.zhimeng.loupanguanli.database.DBColumns;
import com.zhimeng.loupanguanli.database.DBHelper;
import com.zhimeng.loupanguanli.entity.LouDong;

/**
 * 操作楼栋数据表的类
 * 
 * @author jy
 *
 */
public class LouDongDAO {
	private DBHelper dbHelper;

	public LouDongDAO(Context context) {
		// TODO Auto-generated constructor stub
		dbHelper = new DBHelper(context);
	}

	/**
	 * 封装数据记录到一个LouDong对象中
	 * 
	 * @param cs
	 * @return
	 */
	public LouDong PottDataLP(Cursor cs) {
		LouDong lp = new LouDong();
		lp.setId(cs.getInt(cs.getColumnIndex(DBColumns.LouDongColumns.ID)));
		lp.setName(cs.getString(cs.getColumnIndex(DBColumns.LouDongColumns.NAME)));
		lp.setAddress(cs.getString(cs
				.getColumnIndex(DBColumns.LouDongColumns.ADDRESS)));
		lp.setRemark(cs.getString(cs
				.getColumnIndex(DBColumns.LouDongColumns.REMARK)));
		lp.setPicPath(cs.getString(cs
				.getColumnIndex(DBColumns.LouDongColumns.PIC_PATH)));
		return lp;
	}

	/**
	 * 获取所有楼盘数据
	 * 
	 * @return
	 */
	public ArrayList<LouDong> getAll() {

		ArrayList<LouDong> lps = new ArrayList<LouDong>();
		Cursor cs = dbHelper.getReadableDatabase().rawQuery(
				"select * from " + DBColumns.LouDongColumns.TB_NAME + "", null);

		while (cs.moveToNext()) {

			LouDong lp = new LouDong();
			lp = PottDataLP(cs);// 将楼盘记录封装在一个LouDong对象中
			lps.add(lp);// 将楼盘对象添加到集合中
		}
		cs.close();
		return lps;
	}

	/**
	 * 根据id获取楼盘对象
	 * 
	 * @param id
	 * @return
	 */
	public LouDong GetLouDongById(Integer id) {

		Cursor cs = dbHelper.getReadableDatabase().rawQuery(
				"select * from " + DBColumns.LouDongColumns.TB_NAME + " where "
						+ DBColumns.LouDongColumns.ID + "=?",
				new String[] { String.valueOf(id) });
		while (cs.moveToNext()) {
			LouDong lp = new LouDong();// 创建楼盘对象
			lp = PottDataLP(cs);// 将楼盘记录封装在一个LouDong对象中
			cs.close();
			return lp;
		}
		return null;
	}

	/**
	 * 根据楼盘名模糊匹配出楼盘对象
	 * 
	 * @param name
	 *            参数楼盘名称
	 * @return
	 */
	public ArrayList<LouDong> GetLouDongByName(String name) {

		ArrayList<LouDong> lps = new ArrayList<LouDong>();
		Cursor cs = dbHelper.getReadableDatabase().rawQuery(
				"select * from " + DBColumns.LouDongColumns.TB_NAME + " where "
						+ DBColumns.LouDongColumns.NAME + " like '%?%'",
				new String[] { name });

		while (cs.moveToNext()) {

			LouDong lp = new LouDong();
			lp = PottDataLP(cs);// 将楼盘记录封装在一个LouDong对象中
			lps.add(lp);// 将楼盘对象添加到集合中
		}
		cs.close();
		return lps;
	}

	/**
	 * 插入数据操作
	 * 
	 * @param sql包含参数占位符
	 *            ?的sql语句
	 * @param params参数数组
	 */
	public void Insert(String sql, Object[] params) {

		dbHelper.getReadableDatabase().execSQL(sql, params);
	}

	/**
	 * 更新数据操作
	 * 
	 * @param sql包含参数占位符
	 *            ?的sql语句
	 * @param params参数数组
	 */
	public void Update(String sql, Object[] params) {
		dbHelper.getReadableDatabase().execSQL(sql, params);
	}

	/**
	 * 删除数据操作
	 * 
	 * @param sql包含参数占位符
	 *            ?的sql语句
	 * @param params参数数组
	 */
	public void Delete(String sql, Object[] params) {
		dbHelper.getReadableDatabase().execSQL(sql, params);
	}
}
