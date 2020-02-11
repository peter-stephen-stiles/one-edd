package com.nobbysoft.first.common.entities.staticdto;

public class CharacterClassSkillKey implements Comparable<CharacterClassSkillKey>{
	
	private String classId;
	private String skillId;
	

	public CharacterClassSkillKey(String classId, String skillId) {
		super();
		this.classId = classId;
		this.skillId = skillId;
	}


	public CharacterClassSkillKey() {
	}


	public String getClassId() {
		return classId;
	}


	public void setClassId(String classId) {
		this.classId = classId;
	}


	public String getSkillId() {
		return skillId;
	}


	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classId == null) ? 0 : classId.hashCode());
		result = prime * result + ((skillId == null) ? 0 : skillId.hashCode());
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
		CharacterClassSkillKey other = (CharacterClassSkillKey) obj;
		if (classId == null) {
			if (other.classId != null)
				return false;
		} else if (!classId.equals(other.classId))
			return false;
		if (skillId == null) {
			if (other.skillId != null)
				return false;
		} else if (!skillId.equals(other.skillId))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "CharacterClassSkillKey [classId=" + classId + ", skillId=" + skillId + "]";
	}


	@Override
	public int compareTo(CharacterClassSkillKey o) {
		if(o==null) {
			return 1;
		}
		return toString().compareTo(o.toString());
	}

}
