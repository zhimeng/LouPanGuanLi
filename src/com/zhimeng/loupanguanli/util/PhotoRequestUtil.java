package com.zhimeng.loupanguanli.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;

public class PhotoRequestUtil {
	public static final int PHOTO_REQUEST_GALLERY = 1;
	public static final int PHOTO_REQUEST_CAREMA = 2;
	public static final int PHOTO_REQUEST_CUT = 3;

	// 从相册中选取图片
	public static void getPicFromGallery(Activity srcActivity) {
		// 激活系统图库，选择一张图片
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		srcActivity.startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
	}

	// 相机拍照获取图片
	public static void getPicFromCamera(Activity srcActivity, Uri uri) {
		// 激活相机
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		srcActivity.startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
	}

	// 裁切图片
	public static void cropPic(Uri srcUri, Uri desUri, Activity srcActivity) {
		// 获取屏幕像素
		DisplayMetrics dm = new DisplayMetrics();
		srcActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int gcd = MathUtil.getGCD(dm.heightPixels, dm.widthPixels);
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(srcUri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");
		// 裁剪框的比例
		intent.putExtra("aspectX", (dm.widthPixels / gcd));
		intent.putExtra("aspectY", (dm.heightPixels / gcd));
		// 裁剪后输出图片的尺寸大小
		// intent.putExtra("outputX", dm.widthPixels);
		// intent.putExtra("outputY", dm.heightPixels);
		intent.putExtra("scale", true);
		// 图片格式
		intent.putExtra("outputFormat", "JPEG");
		// 设置不返回Bitmap对象
		intent.putExtra("return-data", false);
		// 设置剪切后的文件输出路径
		intent.putExtra(MediaStore.EXTRA_OUTPUT, desUri);
		// 取消人脸识别
		intent.putExtra("noFaceDetection", true);
		srcActivity.startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

}
