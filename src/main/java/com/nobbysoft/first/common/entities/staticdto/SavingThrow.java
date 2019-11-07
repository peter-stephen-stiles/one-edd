package com.nobbysoft.first.common.entities.staticdto;

import java.io.Serializable;

import com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.first.common.entities.DataDTOInterface;

public class SavingThrow  implements Comparable<SavingThrow>, Serializable, DataDTOInterface<SavingThrowKey>{

	private String classId;
	private int fromLevel;
	private int toLevel;
	private String savingThrowTypeString;
	
	private int rollRequired;
	
	public SavingThrow() {
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

	public void setSavingThrowTypeString(String savingThrowTypeString) {
		this.savingThrowTypeString = savingThrowTypeString;
	}

	public int getRollRequired() {
		return rollRequired;
	}

	public void setRollRequired(int rollRequired) {
		this.rollRequired = rollRequired;
	}

	@Override
	public SavingThrowKey getKey() {
		// TODO Auto-generated method stub
		return new SavingThrowKey(classId,fromLevel,toLevel,savingThrowTypeString);
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

	@Override
	public String getDescription() {
		return ""+classId+":"+fromLevel+"-"+toLevel+" "+savingThrowTypeString+"="+rollRequired ;
	}

	@Override
	public Object[] getAsRow() {
		return new Object[] {this,classId,savingThrowTypeString,fromLevel,toLevel,rollRequired};
	}

	@Override
	public String[] getRowHeadings() {
		// TODO Auto-generated method stub
		return new String[] {"","Class","Type","From Level","To Level","Roll Required"};
	}

	@Override
	public Integer[] getColumnWidths() {
		// TODO Auto-generated method stub
		return new Integer[] {0,150,400,100,100,100};
	}

	@Override
	public String[] getColumnCodedListTypes() {
		// TODO Auto-generated method stub
		return new String[] {Constants.CLI_CLASS,Constants.CLI_SAVING_THROW,null,null,null,null};
	}

	@Override
	public int compareTo(SavingThrow o) {
		int ret = classId.compareTo(o.getClassId());
		if(ret==0) {
			ret = (int)Math.signum(fromLevel - o.getFromLevel());
			if(ret==0) {
				ret = (int)Math.signum(toLevel - o.getToLevel());
				if(ret==0) {
					ret = savingThrowTypeString.compareTo(o.getSavingThrowTypeString());
					if(ret==0) {
						ret = (int)Math.signum(rollRequired - o.getRollRequired()); 
					}
				}
			}
		}
		return ret;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classId == null) ? 0 : classId.hashCode());
		result = prime * result + fromLevel;
		result = prime * result + rollRequired;
		result = prime * result + ((savingThrowTypeString == null) ? 0 : savingThrowTypeString.hashCode());
		result = prime * result + toLevel;
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
		SavingThrow other = (SavingThrow) obj;
		if (classId == null) {
			if (other.classId != null)
				return false;
		} else if (!classId.equals(other.classId))
			return false;
		if (fromLevel != other.fromLevel)
			return false;
		if (rollRequired != other.rollRequired)
			return false;
		if (savingThrowTypeString == null) {
			if (other.savingThrowTypeString != null)
				return false;
		} else if (!savingThrowTypeString.equals(other.savingThrowTypeString))
			return false;
		if (toLevel != other.toLevel)
			return false;
		return true;
	}

}
