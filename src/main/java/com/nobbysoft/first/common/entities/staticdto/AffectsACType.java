package com.nobbysoft.first.common.entities.staticdto;

public enum AffectsACType {
	X("No effect"),
	P("Bonus to AC value"),
	S("Sets AC value");
	
	private String description;
	public String getDescription() {
		return description;
	}
	 
	private AffectsACType(String description){
		this.description=description;
	}
	
}
