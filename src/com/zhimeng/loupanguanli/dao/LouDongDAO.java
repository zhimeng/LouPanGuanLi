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
		LouDong ld = new LouDong();
		ld.setId(cs.getInt(cs.getColumnIndex(DBColumns.LouDongColumns.ID)));
		ld.setName(cs.getString(cs
				.getColumnIndex(DBColumns.LouDongColumns.NAME)));
		ld.setLayers(cs.getInt(cs
				.getColumnIndex(DBColumns.LouDongColumns.LAYERS)));
		ld.setSets(cs.getInt(cs.getColumnIndex(DBColumns.LouDongColumns.SETS)));
		ld.setRemark(cs.getString(cs
				.getColumnIndex(DBColumns.LouDongColumns.REMARK)));
		ld.setNumber(cs.getInt(cs
				.getColumnIndex(DBColumns.LouDongColumns.NUMBER)));
		ld.setLoupanId(cs.getInt(cs
				.getColumnIndex(DBColumns.LouDongColumns.LOUPAN_ID)));

		return ld;
	}

	/**
	 * 判断指定名称的楼栋是否存在
	 * 
	 * @param name
	 * @return
	 */
	public boolean isNameExisted(String name, Integer loupanId) {
		boolean result = false;
		Cursor cs = dbHelper.getReadableDatabase().rawQuery(
				"select * from " + DBColumns.LouPanColumns.TB_NAME + " where "
						+ DBColumns.LouPanColumns.NAME + " = ? and "
						+ DBColumns.LouDongColumns.LOUPAN_ID + "=?",
				new String[] { name, String.valueOf(loupanId) });

		if (cs.moveToNext()) {
			result = true;
		}
		return result;

	}

	/**
	 * 获取所有楼栋数据
	 * 
	 * @return
	 */
	public ArrayList<LouDong> getAll() {

		ArrayList<LouDong> list = new ArrayList<LouDong>();
		Cursor cs = dbHelper.getReadableDatabase().rawQuery(
				"select * from " + DBColumns.LouDongColumns.TB_NAME + "", null);

		while (cs.moveToNext()) {

			LouDong lp = new LouDong();
			lp = PottDataLP(cs);// 将楼栋记录封装在一个LouDong对象中
			list.add(lp);// 将楼栋对象添加到集合中
		}
		cs.close();
		return list;
	}

	/**
	 * 根据id获取楼栋对象
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
			LouDong lp = new LouDong();// 创建楼栋对象
			lp = PottDataLP(cs);// 将楼栋记录封装在一个LouDong对象中
			cs.close();
			return lp;
		}
		return null;
	}

	/**
	 * 根据楼栋名获取指定楼盘下的楼栋对象
	 * 
	 * @param name
	 *            楼栋名称
	 * @param loupanId
	 *            楼盘id
	 * @return
	 */
	public ArrayList<LouDong> GetLouDongByName(String name, Integer loupanId) {

		ArrayList<LouDong> list = new ArrayList<LouDong>();
		Cursor cs = dbHelper.getReadableDatabase().rawQuery(
				"select * from " + DBColumns.LouDongColumns.TB_NAME + " where "
						+ DBColumns.LouDongColumns.LOUPAN_ID + " = "
						+ String.valueOf(loupanId) + " and "
						+ DBColumns.LouDongColumns.NAME + " like '%?%'",
				new String[] { name });

		while (cs.moveToNext()) {

			LouDong lp = new LouDong();
			lp = PottDataLP(cs);// 将楼栋记录封装在一个LouDong对象中
			list.add(lp);// 将楼栋对象添加到集合中
		}
		cs.close();
		return list;
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
