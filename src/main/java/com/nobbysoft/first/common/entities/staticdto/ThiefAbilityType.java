package com.nobbysoft.first.common.entities.staticdto;

import java.io.Serializable;

import com.nobbysoft.first.common.entities.DataDTOInterface;

@SuppressWarnings("serial")
public class ThiefAbilityType  implements Comparable<ThiefAbilityType>, Serializable, DataDTOInterface<String>{

	public ThiefAbilityType() {
		
	}
	
	private String thiefAbilityType;
	private String thiefAbilityName;
	public String getThiefAbilityType() {
		return thiefAbilityType;
	}
	public void setThiefAbilityType(String thiefAbilityType) {
		this.thiefAbilityType = thiefAbilityType;
	}
	public String getThiefAbilityName() {
		return thiefAbilityName;
	}
	public void setThiefAbilityName(String thiefAbilityName) {
		this.thiefAbilityName = thiefAbilityName;
	}
	@Override
	public String getKey() { 
		return thiefAbilityType;
	}
	@Override
	public String getDescription() { 
		return thiefAbilityName;
	}
	@Override
	public Object[] getAsRow() { 
		return new  Object[] {this,thiefAbilityType,thiefAbilityName};
	}
	@Override
	public String[] getRowHeadings() {
		return new String[] {"Ability","Ability Name"};
	}
	@Override
	public Integer[] getColumnWidths() {
		return new Integer[] {120,-1};
	}
	@Override
	public String[] getColumnCodedListTypes() {
		return new String[] {null,null};
	}
	@Override
	public int compareTo(ThiefAbilityType o) {
		int ret = thiefAbilityType.compareTo(o.getThiefAbilityType());
		if(ret==0) {
			ret = thiefAbilityName.compareTo(o.getThiefAbilityName());	
		}
		return ret;
	}
	

}
