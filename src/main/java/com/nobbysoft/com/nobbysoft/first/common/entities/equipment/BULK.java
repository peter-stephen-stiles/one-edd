package com.nobbysoft.com.nobbysoft.first.common.entities.equipment;

public enum BULK {
	BULKY("Bulky",2),
	FAIRLY("Fairly",1),
	NON("Non",0),
	;
	
	private String desc;
	private int relative;
	BULK(String desc,int relative){
		this.desc=desc;
		this.relative=relative;
	}
	public String getDesc() {
		return desc;
	}
	public int getRelative() {
		return relative;
	} 
		
	public static BULK fromDescription(String desc) {
		if(desc==null){
			return NON;
		}
		for(BULK b:BULK.values()) {
			if(b.getDesc().equalsIgnoreCase(desc)) {
				return b;
			}
		}
		return BULK.valueOf(desc);// last ditch try
	}
}
