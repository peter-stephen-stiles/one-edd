package com.nobbysoft.first.common.entities.staticdto;

public class CharacterClassLevelKey implements Comparable<CharacterClassLevelKey>{

	private String classId; 
	private int level;
	
	public CharacterClassLevelKey() { 
	}

	public CharacterClassLevelKey(String classId, int level) {
		super();
		this.classId = classId; 
		this.level = level;
	}

	@Override
	public int compareTo(CharacterClassLevelKey o) {
		// cheat
		if(o==null) {
			return 1;
		}
		return toString().compareTo(o.toString());
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
 

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}


	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classId == null) ? 0 : classId.hashCode());
		result = prime * result + level; 
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CharacterClassLevelKey other = (CharacterClassLevelKey) obj;
		if (classId == null) {
			if (other.classId != null)
				return false;
		} else if (!classId.equals(other.classId))
			return false;
		if (level != other.level)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CharacterClassSpellsKey [classId=" + classId +  ", level=" + level
				+ "]";
	}

}
