package com.nobbysoft.first.server.dao;

public class VC {

	private String name;
	private int length;
	private boolean nullable=true;
	
	public VC(String name, int length) {
		this.name=name;
		this.length=length;
		this.nullable=true;
	}
	
	
	public VC(String name, int length,boolean nullable) {
		this.name=name;
		this.length=length;
		this.nullable=nullable;
	}


	public String getName() {
		return name;
	}


	public int getLength() {
		return length;
	}


	public boolean isNullable() {
		return nullable;
	}
	
	
	
	
}
