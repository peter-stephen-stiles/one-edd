package com.nobbysoft.first.common.entities.staticdto;

public enum AffectsACType {
	X("No effect"),
	P("Adds/Subtracts from AC"),
	S("Sets AC");
	
	private String description;
	public String getDescription() {
		return description;
	}
	 
	private AffectsACType(String description){
		this.description=description;
	}
	
}
