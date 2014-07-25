package com.zhimeng.loupanguanli.dao;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

import com.zhimeng.loupanguanli.database.DBColumns;
import com.zhimeng.loupanguanli.database.DBHelper;
import com.zhimeng.loupanguanli.entity.ZuoBiao;
import com.zhimeng.loupanguanli.entity.ZuoBiao;

/**
 * 操作坐标坐标数据表的类
 * 
 * @author jy
 *
 */
public class ZuoBiaoDAO {
	private DBHelper dbHelper;

	public ZuoBiaoDAO(Context context) {
		// TODO Auto-generated constructor stub
		dbHelper = new DBHelper(context);
	}

	/**
	 * 封装数据记录到一个ZuoBiao对象中
	 * 
	 * @param cs
	 * @return
	 */
	public ZuoBiao PottDataLP(Cursor cs) {
		ZuoBiao ld = new ZuoBiao();
		ld.setId(cs.getInt(cs.getColumnIndex(DBColumns.ZuoBiaoColumns.ID)));
		ld.setX(cs.getInt(cs.getColumnIndex(DBColumns.ZuoBiaoColumns.X)));
		ld.setY(cs.getInt(cs.getColumnIndex(DBColumns.ZuoBiaoColumns.Y)));
		ld.setLoudongId(cs.getInt(cs
				.getColumnIndex(DBColumns.ZuoBiaoColumns.LOUDONG_ID)));
		ld.setLoupanId(cs.getInt(cs
				.getColumnIndex(DBColumns.ZuoBiaoColumns.LOUPAN_ID)));
		return ld;
	}

	/**
	 * 判断指定楼栋的坐标是否存在
	 * 
	 * @param loudongId
	 *            楼栋id
	 * @param loupanId
	 *            楼盘id
	 * @return
	 */
	public boolean isExisted(Integer loudongId, Integer loupanId) {
		boolean result = false;
		Cursor cs = dbHelper.getReadableDatabase().rawQuery(
				"select * from " + DBColumns.ZuoBiaoColumns.TB_NAME + " where "
						+ DBColumns.ZuoBiaoColumns.LOUDONG_ID + " = ? and "
						+ DBColumns.ZuoBiaoColumns.LOUPAN_ID + "=?",
				new String[] { String.valueOf(loudongId),
						String.valueOf(loupanId) });

		if (cs.moveToNext()) {
			result = true;
		}
		return result;

	}

	/**
	 * 获取某楼盘下所有楼栋坐标数据
	 * 
	 * @return
	 */
	public ArrayList<ZuoBiao> getAll(Integer loupanId) {

		ArrayList<ZuoBiao> list = new ArrayList<ZuoBiao>();
		Cursor cs = dbHelper.getReadableDatabase().rawQuery(
				"select * from " + DBColumns.ZuoBiaoColumns.TB_NAME + " where "
						+ DBColumns.ZuoBiaoColumns.LOUPAN_ID + "=?",
				new String[] { String.valueOf(loupanId) });

		while (cs.moveToNext()) {

			ZuoBiao lp = new ZuoBiao();
			lp = PottDataLP(cs);// 将坐标记录封装在一个ZuoBiao对象中
			list.add(lp);// 将坐标对象添加到集合中
		}
		cs.close();
		return list;
	}

	/**
	 * 根据id获取坐标对象
	 * 
	 * @param id
	 * @return
	 */
	public ZuoBiao GetZuoBiaoById(Integer id) {

		Cursor cs = dbHelper.getReadableDatabase().rawQuery(
				"select * from " + DBColumns.ZuoBiaoColumns.TB_NAME + " where "
						+ DBColumns.ZuoBiaoColumns.ID + "=?",
				new String[] { String.valueOf(id) });
		while (cs.moveToNext()) {
			ZuoBiao lp = new ZuoBiao();// 创建坐标对象
			lp = PottDataLP(cs);// 将坐标记录封装在一个ZuoBiao对象中
			cs.close();
			return lp;
		}
		cs.close();
		return null;
	}

	/**
	 * 根据坐标名获取指定楼盘下的坐标对象
	 * 
	 * @param name
	 *            坐标名称
	 * @param loupanId
	 *            楼盘id
	 * @return
	 */
	public ArrayList<ZuoBiao> GetZuoBiaoList(String name, Integer loupanId) {

		ArrayList<ZuoBiao> list = new ArrayList<ZuoBiao>();
		Cursor cs = dbHelper.getReadableDatabase().rawQuery(
				"select * from " + DBColumns.ZuoBiaoColumns.TB_NAME + " where "
						+ DBColumns.ZuoBiaoColumns.LOUPAN_ID,
				new String[] { name });

		while (cs.moveToNext()) {

			ZuoBiao lp = new ZuoBiao();
			lp = PottDataLP(cs);// 将坐标记录封装在一个ZuoBiao对象中
			list.add(lp);// 将坐标对象添加到集合中
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
	public void Insert(ZuoBiao zb) {
		String sql = "insert into " + DBColumns.ZuoBiaoColumns.TB_NAME + "("
				+ DBColumns.ZuoBiaoColumns.X + "," + DBColumns.ZuoBiaoColumns.Y
				+ "," + DBColumns.ZuoBiaoColumns.LOUDONG_ID + ","
				+ DBColumns.ZuoBiaoColumns.LOUPAN_ID + ") values(?,?,?,?)";
		String[] params = new String[] { 
				String.valueOf(zb.getX()),
				String.valueOf(zb.getY()),
				String.valueOf(zb.getLoudongId()),
				String.valueOf(zb.getLoupanId()) };
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
