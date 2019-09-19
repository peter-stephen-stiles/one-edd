package com.nobbysoft.first.common.entities.staticdto;

public class CharacterClassSpellKey implements Comparable<CharacterClassSpellKey>{

	private String classId;
	private String spellClassId;
	private int level;
	
	public CharacterClassSpellKey() { 
	}

	public CharacterClassSpellKey(String classId, String spellClassId, int level) {
		super();
		this.classId = classId;
		this.spellClassId = spellClassId;
		this.level = level;
	}

	@Override
	public int compareTo(CharacterClassSpellKey o) {
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

	public String getSpellClassId() {
		return spellClassId;
	}

	public void setSpellClassId(String spellClassId) {
		this.spellClassId = spellClassId;
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
		result = prime * result + ((spellClassId == null) ? 0 : spellClassId.hashCode());
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
		CharacterClassSpellKey other = (CharacterClassSpellKey) obj;
		if (classId == null) {
			if (other.classId != null)
				return false;
		} else if (!classId.equals(other.classId))
			return false;
		if (level != other.level)
			return false;
		if (spellClassId == null) {
			if (other.spellClassId != null)
				return false;
		} else if (!spellClassId.equals(other.spellClassId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CharacterClassSpellsKey [classId=" + classId + ", spellClassId=" + spellClassId + ", level=" + level
				+ "]";
	}

}
