package com.nobbysoft.first.common.entities.staticdto;

public class CharacterClassToHitKey implements Comparable<CharacterClassToHitKey>{

	private String classId;
	private int fromLevel;
	private int toLevel; 
	
	public CharacterClassToHitKey() { 
	}

	public String getClassId() {
		return classId;
	}

	public CharacterClassToHitKey(String classId, int fromLevel, int toLevel) {
		super();
		this.classId = classId;
		this.fromLevel = fromLevel;
		this.toLevel = toLevel;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public int getFromLevel() {
		return fromLevel;
	}

	public void setFromLevel(int fromLevel) {
		this.fromLevel = fromLevel;
	}

	public int getToLevel() {
		return toLevel;
	}

 

	public void setToLevel(int toLevel) {
		this.toLevel = toLevel;
	}

 

	@Override
	public int compareTo(CharacterClassToHitKey o) {
		int ret=0;
		ret = classId.compareTo(o.getClassId());
		if(ret==0) {			
			ret = (int) Math.signum(o.getFromLevel() - fromLevel);
			if(ret==0) {
				ret = (int) Math.signum(o.getToLevel() - toLevel);
			}
		}
		return ret;
	}

	@Override
	public String toString() {
		return "CharacterClassToHitKey [classId=" + classId + ", fromLevel=" + fromLevel + ", toLevel=" + toLevel
				+ "]";
	}

 
 

}
