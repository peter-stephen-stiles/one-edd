package com.nobbysoft.first.common.entities.staticdto;

public class SavingThrowKey implements Comparable<SavingThrowKey>{

	private String classId;
	private int fromLevel;
	private int toLevel;
	private String savingThrowTypeString;
	
	public SavingThrowKey() {
		 
	}

	@Override
	public int compareTo(SavingThrowKey o) {
		int ret = classId.compareTo(o.getClassId());
		if(ret==0) {
			ret = (int)Math.signum(fromLevel - o.getFromLevel());
			if(ret==0) {
				ret = (int)Math.signum(toLevel - o.getToLevel());
				if(ret==0) {
					ret = savingThrowTypeString.compareTo(o.getSavingThrowTypeString());
				}
			}
		}
		return ret;
	}

	public SavingThrowKey(String classId, int fromLevel, int toLevel, String savingThrowTypeString) {
		super();
		this.classId = classId;
		this.fromLevel = fromLevel;
		this.toLevel = toLevel;
		this.savingThrowTypeString = savingThrowTypeString;
	}

	public String getClassId() {
		return classId;
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

	public String getSavingThrowTypeString() {
		return savingThrowTypeString;
	}

	public void setSavingThrowTypeString(String savingThrowType) {
		this.savingThrowTypeString = savingThrowType;
	}

	public SavingThrowType getSavingThrowType() {
		return SavingThrowType.valueOf(savingThrowTypeString);
	}
	public void setSavingThrowType(SavingThrowType type) {
		if(type==null) {
			savingThrowTypeString = null;
		} else {
			savingThrowTypeString = type.toString();
		}
	}
	
}
