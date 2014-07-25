package com.zhimeng.loupanguanli.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "loupan.db";
	private static final int VERSION = 1;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建楼盘表
		db.execSQL("create table if not exists loupan("
				+ "id integer primary key autoincrement," + "name varchar(40),"
				+ "address varchar(100)," + "remark varchar(200),"
				+ "picpath varchar(100))");
		// 创建楼栋表
		db.execSQL("create table if not exists loudong("
				+ "id integer primary key autoincrement," + "number integer,"
				+ "name varchar(40)," + "layers integer," + "sets integer,"
				+ "remark varchar(200)," + "loupanid integer,"
				+ "foreign key(loupanid) references loupan(id))");
		// 创建坐标表
		db.execSQL("create table if not exists zuobiao("
				+ "id integer primary key autoincrement," + "x integer,"
				+ "y integer," + "loupanid integer ," + "loudongid integer,"
				+ "foreign key(loupanid) references loupan(id),"
				+ "foreign key(loudongid) references loudong(id))");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
