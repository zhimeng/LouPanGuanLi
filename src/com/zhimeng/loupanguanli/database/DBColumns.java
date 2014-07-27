package com.zhimeng.loupanguanli.database;

/*
 * 数据库各个表的列名
 */
public final class DBColumns {

	public static final class LouPanColumns {
		public static final String TB_NAME = "loupan";

		public static final String ID = "id";
		public static final String NAME = "name";
		public static final String ADDRESS = "address";
		public static final String REMARK = "remark";
		public static final String PIC_PATH = "picpath";
	}

	public static final class LouDongColumns {
		public static final String TB_NAME = "loudong";

		public static final String ID = "id";
		public static final String NUMBER = "number";
		public static final String NAME = "name";
		public static final String LAYERS = "layers";
		public static final String SETS = "sets";
		public static final String REMARK = "remark";
		public static final String LOUPAN_ID = "loupanid";
	}

	public static final class ZuoBiaoColumns {
		public static final String TB_NAME = "zuobiao";

		public static final String ID = "id";
		public static final String X = "x";
		public static final String Y = "y";
		public static final String LOUPAN_ID = "loupanid";
		public static final String LOUDONG_ID = "loudongid";
	}

}
