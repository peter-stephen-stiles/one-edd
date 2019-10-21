package com.nobbysoft.first.common.entities.staticdto;

import java.io.Serializable;

import com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.first.common.entities.DataDTOInterface;

public class CharacterClassLevel implements Serializable, DataDTOInterface<CharacterClassLevelKey> {
	private String classId; 
	private int level;
	private String levelTitle;
	private int fromXp;
	private int toXp;
	private String notes;
	private boolean nameLevel;
	

 
	

	public Object[] getAsRow(){
		return new Object[]{this,classId,level,levelTitle,fromXp,toXp,notes};
	}
	private static final String[] h =new String[]{"","Id","Lvl", "Title","From","To","Notes"};
	public String[] getRowHeadings(){
		return h;
	}

	public String[] getColumnCodedListTypes() {
		return new String[] {Constants.CLI_CLASS,null,null,null,null,null,null};
	}

	private static final Integer[] w =new Integer[]{0,120,120,120,120,120,-1};
	public Integer[] getColumnWidths(){
		return w;
	}
	
	
 

	@Override
	public CharacterClassLevelKey getKey() { 
		CharacterClassLevelKey key = new CharacterClassLevelKey();
		key.setClassId(classId);
		key.setLevel(level);
		return key;
	}

	@Override
	public String getDescription() { 
		return ""+classId+"-"+level+" "+ levelTitle;
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

	public String getLevelTitle() {
		return levelTitle;
	}

	public void setLevelTitle(String levelTitle) {
		this.levelTitle = levelTitle;
	}

	public int getFromXp() {
		return fromXp;
	}

	public void setFromXp(int fromXp) {
		this.fromXp = fromXp;
	}

	public int getToXp() {
		return toXp;
	}

	public void setToXp(int toXp) {
		this.toXp = toXp;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public static String[] getH() {
		return h;
	}

	public static Integer[] getW() {
		return w;
	}

	public boolean isNameLevel() {
		return nameLevel;
	}

	public void setNameLevel(boolean nameLevel) {
		this.nameLevel = nameLevel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classId == null) ? 0 : classId.hashCode());
		result = prime * result + fromXp;
		result = prime * result + level;
		result = prime * result + ((levelTitle == null) ? 0 : levelTitle.hashCode());
		result = prime * result + (nameLevel ? 1231 : 1237);
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + toXp;
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
		CharacterClassLevel other = (CharacterClassLevel) obj;
		if (classId == null) {
			if (other.classId != null)
				return false;
		} else if (!classId.equals(other.classId))
			return false;
		if (fromXp != other.fromXp)
			return false;
		if (level != other.level)
			return false;
		if (levelTitle == null) {
			if (other.levelTitle != null)
				return false;
		} else if (!levelTitle.equals(other.levelTitle))
			return false;
		if (nameLevel != other.nameLevel)
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (toXp != other.toXp)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CharacterClassLevel [classId=" + classId + ", level=" + level + ", levelTitle=" + levelTitle
				+ ", fromXp=" + fromXp + ", toXp=" + toXp + ", notes=" + notes + ", nameLevel=" + nameLevel + "]";
	}

 
}
