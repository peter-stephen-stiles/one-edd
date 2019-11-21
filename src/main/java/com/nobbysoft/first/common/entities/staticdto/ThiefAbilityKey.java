package com.nobbysoft.first.common.entities.staticdto;

public class ThiefAbilityKey  implements Comparable<ThiefAbilityKey>{

	
	private int thiefLevel;
	private String thiefAbilityType;// FK to TheifAbilityType
	
	
	public ThiefAbilityKey() {

	}


	public ThiefAbilityKey(int thiefLevel, String thiefAbilityType) {
		super();
		this.thiefLevel = thiefLevel;
		this.thiefAbilityType = thiefAbilityType;
	}


	@Override
	public int compareTo(ThiefAbilityKey o) {
		int ret = (int)Math.signum(thiefLevel - o.getThiefLevel());
		if(ret==0) {
				if(ret==0) {
					ret = thiefAbilityType.compareTo(o.getThiefAbilityType());
				} 
		}
		return ret;
	}


	public int getThiefLevel() {
		return thiefLevel;
	}


	public void setThiefLevel(int thiefLevel) {
		this.thiefLevel = thiefLevel;
	}


	public String getThiefAbilityType() {
		return thiefAbilityType;
	}


	public void setThiefAbilityType(String thiefAbilityType) {
		this.thiefAbilityType = thiefAbilityType;
	}


	@Override
	public String toString() {
		return "ThiefAbilityKey [thiefLevel=" + thiefLevel + ", thiefAbilityType=" + thiefAbilityType + "]";
	}

}
