package com.zhimeng.loupanguanli.dao;

import java.util.ArrayList;

import android.content.Context;

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
	 * 获取所有楼盘数据
	 * 
	 * @return
	 */
	public ArrayList<LouPan> getAll() {

		ArrayList<LouPan> lps = new ArrayList<LouPan>();

		return lps;
	}

	/**
	 * 根据id获取楼盘对象
	 * 
	 * @param id
	 * @return
	 */
	public LouPan GetLouPanById(long id) {

		LouPan lp = new LouPan();

		return lp;
	}

	/**
	 * 根据楼盘名称获取楼盘对象
	 * 
	 * @param name
	 * @return
	 */
	public LouPan GetLouPanByName(String name) {

		LouPan lp = new LouPan();

		return lp;
	}

	/**
	 * 插入数据操作
	 * 
	 * @param sql包含参数占位符
	 *            ?的sql语句
	 * @param params参数数组
	 * @return 影响行数
	 */
	public int Insert(String sql, Object[] params) {
		int result = 0;
		return result;
	}

	/**
	 * 更新数据操作
	 * 
	 * @param sql包含参数占位符
	 *            ?的sql语句
	 * @param params参数数组
	 * @return 影响行数
	 */
	public int Update(String sql, Object[] params) {
		int result = 0;
		return result;
	}

	/**
	 * 删除数据操作
	 * 
	 * @param sql包含参数占位符
	 *            ?的sql语句
	 * @param params参数数组
	 * @return 影响行数
	 */
	public int Delete(String sql, Object[] params) {
		int result = 0;
		return result;
	}

}
