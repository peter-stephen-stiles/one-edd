package com.nobbysoft.first.common.entities.pc;

import java.io.Serializable;

import com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.first.common.entities.DataDTOInterface;

@SuppressWarnings("serial")
public class PlayerCharacterHp implements Serializable,DataDTOInterface<PlayerCharacterHpKey> {

	private int pcId;
	private String classId;
	private int level;
	private int hpIncrement=0;
	
	public PlayerCharacterHp() {

	}

	@Override
	public PlayerCharacterHpKey getKey() { 
		return new PlayerCharacterHpKey(pcId,classId,level);
	}

	@Override
	public String getDescription() { 
		return toString();
	}

	@Override
	public Object[] getAsRow() {
		// TODO Auto-generated method stub
		return new Object[] {this,pcId,classId,level,hpIncrement};
	}

	@Override
	public String[] getRowHeadings() {
		// TODO Auto-generated method stub
		return new String[] {"Player","Class","Level","+hp"};
	}

	@Override
	public Integer[] getColumnWidths() {
		// TODO Auto-generated method stub
		return new Integer[] {200,200,100,100};
	}

	@Override
	public String[] getColumnCodedListTypes() {
		// TODO Auto-generated method stub
		return new String[] {Constants.CLI_PLAYER_CHARACTER,Constants.CLI_CLASS,null,null};
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

	public int getHpIncrement() {
		return hpIncrement;
	}

	public void setHpIncrement(int hpIncrement) {
		this.hpIncrement = hpIncrement;
	}

	@Override
	public String toString() {
		return "PlayerCharacterHp [pcId=" + pcId + ", classId=" + classId + ", level=" + level + ", hpIncrement="
				+ hpIncrement + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classId == null) ? 0 : classId.hashCode());
		result = prime * result + hpIncrement;
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
		PlayerCharacterHp other = (PlayerCharacterHp) obj;
		if (classId == null) {
			if (other.classId != null)
				return false;
		} else if (!classId.equals(other.classId))
			return false;
		if (hpIncrement != other.hpIncrement)
			return false;
		if (level != other.level)
			return false;
		if (pcId != other.pcId)
			return false;
		return true;
	}
 
}
