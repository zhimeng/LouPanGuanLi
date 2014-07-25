package com.zhimeng.loupanguanli.dao;

import java.util.ArrayList;

import android.R.integer;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhimeng.loupanguanli.activities.MainActivity;
import com.zhimeng.loupanguanli.database.DBHelper;
import com.zhimeng.loupanguanli.entity.LouPan;

public class LouPanDAO {
	private DBHelper dbHelper;

	public LouPanDAO(Context context) {
		// TODO Auto-generated constructor stub
		dbHelper = new DBHelper(context);
	}

	/**
	 * 封装数据记录到一个LouPan对象中
	 * 
	 * @param cs
	 * @return
	 */
	public LouPan PottDataLP(Cursor cs) {
		LouPan lp = new LouPan();
		lp.setId(cs.getInt(cs.getColumnIndex("id")));
		lp.setName(cs.getString(cs.getColumnIndex("name")));
		lp.setAddress(cs.getString(cs.getColumnIndex("address")));
		lp.setRemark(cs.getString(cs.getColumnIndex("remark")));
		lp.setPicPath(cs.getString(cs.getColumnIndex("picpath")));
		return lp;
	}

	/**
	 * 获取所有楼盘数据
	 * 
	 * @return
	 */
	public ArrayList<LouPan> getAll() {

		ArrayList<LouPan> lps = new ArrayList<LouPan>();
		Cursor cs = dbHelper.getReadableDatabase().rawQuery(
				"select * from loupan", null);

		while (cs.moveToNext()) {

			LouPan lp = new LouPan();
			/*
			 * lp.setId(cs.getInt(cs.getColumnIndex("id")));
			 * lp.setName(cs.getString(cs.getColumnIndex("name")));
			 * lp.setAddress(cs.getString(cs.getColumnIndex("address")));
			 * lp.setRemark(cs.getString(cs.getColumnIndex("remark")));
			 * lp.setPicPath(cs.getString(cs.getColumnIndex("picpath")));
			 */
			lp = PottDataLP(cs);// 将楼盘记录封装在一个LouPan对象中
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
	public LouPan GetLouPanById(Integer id) {

		Cursor cs = dbHelper.getReadableDatabase().rawQuery(
				"select * from loupan where na=?",
				new String[] { String.valueOf(id) });
		while (cs.moveToNext()) {
			LouPan lp = new LouPan();// 创建楼盘对象
			lp = PottDataLP(cs);// 将楼盘记录封装在一个LouPan对象中
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
	public ArrayList<LouPan> GetLouPanByName(String name) {

		ArrayList<LouPan> lps = new ArrayList<LouPan>();
		Cursor cs = dbHelper.getReadableDatabase().rawQuery(
				"select * from loupan where name like '%?%'",
				new String[] { name });

		while (cs.moveToNext()) {

			LouPan lp = new LouPan();
			lp = PottDataLP(cs);// 将楼盘记录封装在一个LouPan对象中
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
