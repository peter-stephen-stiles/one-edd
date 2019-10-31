package com.nobbysoft.first.common.entities.pc;

public class PlayerCharacterHpKey implements Comparable<PlayerCharacterHpKey> {

	private int pcId;
	private String classId;
	private int level;

	
	public PlayerCharacterHpKey(int pcId,String classId, int level) { 
		this.pcId=pcId;
		this.classId=classId;
		this.level=level;
	}
	
	public PlayerCharacterHpKey() { 
	}

	public int getPcId() {
		return pcId;
	}

	public void setPcId(int pcId) {
		this.pcId = pcId;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classId == null) ? 0 : classId.hashCode());
		result = prime * result + level;
		result = prime * result + pcId;
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
		PlayerCharacterHpKey other = (PlayerCharacterHpKey) obj;
		if (classId == null) {
			if (other.classId != null)
				return false;
		} else if (!classId.equals(other.classId))
			return false;
		if (level != other.level)
			return false;
		if (pcId != other.pcId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PlayerCharacterHpKey [pcId=" + pcId + ", classId=" + classId + ", level=" + level + "]";
	}

	@Override
	public int compareTo(PlayerCharacterHpKey o) {
		int ret=0;
		if(o.equals(this)) {
			return 0;
		}
		if(o.getPcId()>pcId) {
			return 1;
		}
		if(o.getPcId()<pcId) {
			return -1;
		}
		ret = (o.getClassId().compareTo(classId));
		if(ret==0) {
			if(o.getLevel()>level) {
				return 1;
			}
			if(o.getLevel()<level) {
				return -1;
			}
		}
		return ret;
	}

 

 


}
