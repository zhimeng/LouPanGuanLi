package com.zhimeng.loupanguanli.entity;

import java.io.Serializable;

public class LouDong implements Serializable{
	private static final long serialVersionUID = 964627924285967120L;
	
	private Integer id;// 楼栋ID
	private Integer number;// 楼栋编号
	private String name;// 楼栋名称
	private Integer layers;// 楼层数
	private Integer sets;// 几套/层
	private String remark;// 楼栋备注
	private Integer loupanId;// 对应楼盘ID
	
	public LouDong(Integer number,String name,)

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLayers() {
		return layers;
	}

	public void setLayers(Integer layers) {
		this.layers = layers;
	}

	public Integer getSets() {
		return sets;
	}

	public void setSets(Integer sets) {
		this.sets = sets;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getLoupanId() {
		return loupanId;
	}

	public void setLoupanId(Integer loupanId) {
		this.loupanId = loupanId;
	}

}
