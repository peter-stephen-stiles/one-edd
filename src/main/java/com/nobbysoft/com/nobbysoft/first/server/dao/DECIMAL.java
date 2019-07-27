package com.nobbysoft.com.nobbysoft.first.server.dao;

public class DECIMAL {

	private String name;
	private int length;
	private int decimalPlaces;
	private boolean nullable;
	
	public DECIMAL() { 
	}

	public DECIMAL(String name, int length, int decimalPlaces) {
		super();
		this.name = name;
		this.length = length;
		this.decimalPlaces = decimalPlaces;
		this.nullable = true;
	}
	
	public DECIMAL(String name, int length, int decimalPlaces, boolean nullable) {
		super();
		this.name = name;
		this.length = length;
		this.decimalPlaces = decimalPlaces;
		this.nullable = nullable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getDecimalPlaces() {
		return decimalPlaces;
	}

	public void setDecimalPlaces(int decimalPlaces) {
		this.decimalPlaces = decimalPlaces;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

}
