package com.mystudy.po;

import java.io.Serializable;
import java.util.List;

public class Car implements Serializable {
	private static final long serialVersionUID = -8479041048668017784L;

	private String name;
	private String flag;
	private Double length;
	private Double width;
	private Double height;
	private List<Tyre> tyrelist;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public Double getLength() {
		return length;
	}
	public void setLength(Double length) {
		this.length = length;
	}
	public Double getWidth() {
		return width;
	}
	public void setWidth(Double width) {
		this.width = width;
	}
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	public List<Tyre> getTyrelist() {
		return tyrelist;
	}
	public void setTyrelist(List<Tyre> tyrelist) {
		this.tyrelist = tyrelist;
	}
	@Override
	public String toString() {
		return "Car [name=" + name + ", flag=" + flag + ", length=" + length + ", width=" + width + ", height=" + height
				+ ", tyrelist=" + tyrelist + "]";
	}
	
}
