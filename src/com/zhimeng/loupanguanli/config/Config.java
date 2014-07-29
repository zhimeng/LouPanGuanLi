package com.zhimeng.loupanguanli.config;

import com.zhimeng.loupanguanli.util.StorageUtil;

import android.os.Environment;

/*
 * 程序配置类
 */
public class Config {

	// 应用程序名称
	public static final String APP_NAME = "楼盘管理工具";

	// 选择照片方式
	public static final String WAY_GALLERY = "选择已有照片";
	public static final String WAY_CAMERA = "拍照";

	// 楼盘图片存取路径，默认SDCard，SDCard不存在时使用手机存储
	public static final String APP_DIR_PATH = StorageUtil.isSDCardExisted() ? Environment
			.getExternalStorageDirectory().getPath() + "/LouPanGuanLi"
			: Environment.getRootDirectory().getPath() + "/LouPanGuanLi";

}
