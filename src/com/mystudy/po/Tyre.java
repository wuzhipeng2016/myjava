package com.mystudy.po;

import java.io.Serializable;

public class Tyre implements Serializable{
	private String name;
	private int index;
	private double weight;
	
	public Tyre(String name,int index,double weight){
		this.name=name;
		this.index=index;
		this.setWeight(weight);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}

	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	@Override
	public String toString() {
		return "Tyre [name=" + name + ", index=" + index + ", weight=" + weight + "]";
	}
	
	
}
