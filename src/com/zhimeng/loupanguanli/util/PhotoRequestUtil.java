package com.zhimeng.loupanguanli.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

public class PhotoRequestUtil {
	public static final int PHOTO_REQUEST_GALLERY = 1;
	public static final int PHOTO_REQUEST_CAREMA = 2;
	public static final int PHOTO_REQUEST_CUT = 3;

	// 从相册中选取图片
	public static void getPicFromGallery(Activity activity) {
		// 激活系统图库，选择一张图片
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		activity.startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
	}

	// 相机拍照获取图片
	public static void getPicFromCamera(Activity activity, Uri uri) {
		// 激活相机
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		activity.startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
	}

	// 裁切图片
	public static void cropPic(Uri uri, Activity activity) {
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");
		// 裁剪框的比例，1:1
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		// 图片格式
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("return-data", true);
		// 取消人脸识别
		intent.putExtra("noFaceDetection", true);
		activity.startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

}
