package com.zhimeng.loupanguanli.entity.dto;

import java.io.Serializable;

import android.graphics.Bitmap;

public class LouPanDto implements Serializable {
	private static final long serialVersionUID = 6461625063526606984L;

	private Bitmap bitmap;// 楼盘图
	private String name;// 楼盘名称
	private String address;// 楼盘地址
	private String remark;// 楼盘备注

	public LouPanDto() {
	}

	public LouPanDto(Bitmap bitmap, String name, String address, String remark) {
		this.bitmap = bitmap;
		this.name = name;
		this.address = address;
		this.remark = remark;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
