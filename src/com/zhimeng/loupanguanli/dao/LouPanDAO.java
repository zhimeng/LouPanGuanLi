package com.zhimeng.loupanguanli.dao;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

import com.zhimeng.loupanguanli.database.DBColumns;
import com.zhimeng.loupanguanli.database.DBHelper;
import com.zhimeng.loupanguanli.entity.LouPan;

/**
 * 操作楼盘数据表的类
 * 
 * @author jy
 *
 */
public class LouPanDAO {
	private DBHelper dbHelper;

	public LouPanDAO(Context context) {
		dbHelper = new DBHelper(context);
	}

	/**
	 * 封装数据记录到一个LouPan对象中
	 * 
	 * @param cs
	 * @return
	 */
	private LouPan pottData(Cursor cs) {
		LouPan lp = new LouPan();
		lp.setId(cs.getInt(cs.getColumnIndex(DBColumns.LouPanColumns.ID)));
		lp.setName(cs.getString(cs.getColumnIndex(DBColumns.LouPanColumns.NAME)));
		lp.setAddress(cs.getString(cs
				.getColumnIndex(DBColumns.LouPanColumns.ADDRESS)));
		lp.setRemark(cs.getString(cs
				.getColumnIndex(DBColumns.LouPanColumns.REMARK)));
		lp.setPicPath(cs.getString(cs
				.getColumnIndex(DBColumns.LouPanColumns.PIC_PATH)));
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
				"select * from " + DBColumns.LouPanColumns.TB_NAME + "", null);

		while (cs.moveToNext()) {

			LouPan lp = new LouPan();
			lp = pottData(cs);// 将楼盘记录封装在一个LouPan对象中
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
	public LouPan getLouPanById(Integer id) {

		Cursor cs = dbHelper.getReadableDatabase().rawQuery(
				"select * from " + DBColumns.LouPanColumns.TB_NAME + " where "
						+ DBColumns.LouPanColumns.ID + "=?",
				new String[] { String.valueOf(id) });
		while (cs.moveToNext()) {
			LouPan lp = new LouPan();// 创建楼盘对象
			lp = pottData(cs);// 将楼盘记录封装在一个LouPan对象中
			cs.close();
			return lp;
		}
		return null;
	}

	/**
	 * 判断指定名称的楼盘是否存在
	 * 
	 * @param name
	 * @return
	 */
	public boolean isNameExisted(String name) {
		boolean result = false;
		Cursor cs = dbHelper.getReadableDatabase().rawQuery(
				"select * from " + DBColumns.LouPanColumns.TB_NAME + " where "
						+ DBColumns.LouPanColumns.NAME + " = ?",
				new String[] { name });

		if (cs.moveToNext()) {
			result = true;
		}
		return result;

	}

	/**
	 * 根据楼盘名模糊匹配出楼盘对象
	 * 
	 * @param name
	 *            参数楼盘名称
	 * @return
	 */
	public ArrayList<LouPan> getLPlistByName(String name) {

		ArrayList<LouPan> lps = new ArrayList<LouPan>();
		Cursor cs = dbHelper.getReadableDatabase().rawQuery(
				"select * from " + DBColumns.LouPanColumns.TB_NAME + " where "
						+ DBColumns.LouPanColumns.NAME + " like ?",
				new String[] { "%" + name + "%" });

		while (cs.moveToNext()) {

			LouPan lp = new LouPan();
			lp = pottData(cs);// 将楼盘记录封装在一个LouPan对象中
			lps.add(lp);// 将楼盘对象添加到集合中
		}
		cs.close();
		return lps;
	}

	/**
	 * 根据楼盘名查询楼盘对象
	 * 
	 * @param name
	 *            参数楼盘名称
	 * @return
	 */
	public LouPan getLouPanByName(String name) {

		LouPan lp = null;
		Cursor cs = dbHelper.getReadableDatabase().rawQuery(
				"select * from " + DBColumns.LouPanColumns.TB_NAME + " where "
						+ DBColumns.LouPanColumns.NAME + " =? ",
				new String[] { name });

		while (cs.moveToNext()) {

			lp = new LouPan();
			lp = pottData(cs);// 将楼盘记录封装在一个LouPan对象中

		}
		cs.close();
		return lp;
	}

	/**
	 * 插入数据操作
	 * 
	 * @param lp
	 *            LouPan对象
	 */
	public void insert(LouPan lp) {
		String sql = "insert into " + DBColumns.LouPanColumns.TB_NAME + "("
				+ DBColumns.LouPanColumns.NAME + ","
				+ DBColumns.LouPanColumns.PIC_PATH + ","
				+ DBColumns.LouPanColumns.ADDRESS + ","
				+ DBColumns.LouPanColumns.REMARK + ") values(?,?,?,?)";
		String[] params = new String[] { lp.getName(), lp.getPicPath(),
				lp.getAddress(), lp.getRemark()

		};
		dbHelper.getReadableDatabase().execSQL(sql, params);
	}

	/**
	 * 更新数据操作
	 * 
	 * @param LP楼盘对象
	 */
	public void update(LouPan LP) {
		String sql = "update " + DBColumns.LouPanColumns.TB_NAME + " set "
				+ DBColumns.LouPanColumns.ADDRESS + "=? ,"
				+ DBColumns.LouPanColumns.NAME + "=? ,"
				+ DBColumns.LouPanColumns.PIC_PATH + "=? ,"
				+ DBColumns.LouPanColumns.REMARK + "=? " + " where "
				+ DBColumns.LouPanColumns.ID + "=?";
		String[] params = new String[] { LP.getAddress(), LP.getName(),
				LP.getPicPath(), LP.getRemark(), String.valueOf(LP.getId()) };

		dbHelper.getReadableDatabase().execSQL(sql, params);
	}

	/**
	 * 删除数据操作
	 * 
	 * @param LP楼盘对象
	 */
	public void delete(LouPan LP) {
		String sql = "delete from " + DBColumns.LouPanColumns.TB_NAME
				+ " where id=?";
		String[] params = new String[] { String.valueOf(LP.getId()) };

		dbHelper.getReadableDatabase().execSQL(sql, params);
	}
	
	

}
