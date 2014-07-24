package com.zhimeng.loupanguanli.entity;

public class LouPan {

	private Integer id;// 楼盘ID
	private String name;// 楼盘名称
	private String address;// 楼盘地址
	private String remark;// 楼盘备注
	private String picPath;// 图片路径

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

}
