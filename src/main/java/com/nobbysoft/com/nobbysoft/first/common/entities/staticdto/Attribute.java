package com.nobbysoft.com.nobbysoft.first.common.entities.staticdto;

public enum Attribute {
	STRENGTH("STR","Strength",1),
	INTELLIGENCE("INT","Intelligence",2),
	WISDOM("WIS","Wisdom",3),
	DEXTERITY("DEX","Dexterity",4),
	CONSTITUTION("CON","Constritution",5),
	CHARISMA("CHR","Charisma",6);
	
	public String getAbbr() {
		return abbr;
	}
	public String getDescription() {
		return description;
	}
	private final String abbr;
	private final String description;
	private int index;
	private Attribute(String abbr,String description,int index){
		this.abbr=abbr;
		this.description = description;
		this.index=index;
	}
	public int getIndex() {
		return index;
	}
	
	public static Attribute fromIndex(int index) {
		if(1==index) {
			return STRENGTH;
		} else if (2==index) {
			return INTELLIGENCE;
		}else if (3==index) {
			return WISDOM;
		}else if (4==index) {
			return DEXTERITY;
		}else if (5==index) {
			return CONSTITUTION;
		}else if (6==index) {
			return CHARISMA;
		} else {
			return null;
		}
	}
}
