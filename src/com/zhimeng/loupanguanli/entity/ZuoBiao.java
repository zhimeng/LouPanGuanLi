package com.zhimeng.loupanguanli.entity;

import java.io.Serializable;

public class ZuoBiao implements Serializable {
	private static final long serialVersionUID = 4003871646645991533L;

	private Integer id;
	private Integer x;
	private Integer y;
	private Integer loupanId;
	private Integer loudongId;

	public ZuoBiao() {
	}

	public ZuoBiao(Integer x, Integer y, Integer loupanId, Integer loudongId) {
		this.x = x;
		this.y = y;
		this.loupanId = loupanId;
		this.loudongId = loudongId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getLoupanId() {
		return loupanId;
	}

	public void setLoupanId(Integer loupanId) {
		this.loupanId = loupanId;
	}

	public Integer getLoudongId() {
		return loudongId;
	}

	public void setLoudongId(Integer loudongId) {
		this.loudongId = loudongId;
	}

}
