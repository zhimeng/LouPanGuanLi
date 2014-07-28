package com.zhimeng.loupanguanli.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.os.Environment;

public class StorageUtil {

	public static boolean isSDCardExisted() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	// 将Bitmap对象保存到外设中，大图片保存可能存在问题，暂时未测试
	public static void saveBitmapToSDCard(Bitmap bitmap, String path) {
		// 创建文件对象，用来存储新的图像文件
		File file = new File(path);
		try {
			// 创建文件
			file.createNewFile();
		} catch (IOException e) {
			System.out.println("在保存图片时出错：" + e.toString());
		}
		// 定义文件输出流
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// 将bitmap存储为jpg格式的图片
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
		try {
			// 刷新文件流
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			// 关闭文件流
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
