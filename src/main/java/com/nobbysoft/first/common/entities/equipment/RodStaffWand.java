package com.nobbysoft.first.common.entities.equipment;

import java.io.Serializable;

import com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.first.common.utils.SU;

@SuppressWarnings("serial")
public class RodStaffWand implements  EquipmentI, Comparable<RodStaffWand>, Serializable, DataDTOInterface<String> {

	
	private String code;
	private String name;
	private String rodStaffOrWand;
	private int encumberanceGP;
	private String definition;
	private EquipmentHands requiresHands;
	
	public RodStaffWand() {
	}

	@Override
	public String getKey() {
		return code;
	}

	@Override
	public String getDescription() {
		return name;
	}

	@Override
	public Object[] getAsRow() {
		return new Object[] {this,code,rodStaffOrWand,name};
	}

	@Override
	public String[] getRowHeadings() {
		return new String[] {"Code","Type","Name"};
	}

	@Override
	public Integer[] getColumnWidths() {
		return new Integer[] {120,100,350};
	}

	@Override
	public String[] getColumnCodedListTypes() {
		return new String[] {null,null,null};
	}

	@Override
	public int compareTo(RodStaffWand o) {
		return toString().compareTo(o.toString());
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public EquipmentType getType() {
		return EquipmentType.ROD_STAFF_WAND;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name=name;
	}

	@Override
	public int getCapacityGP() {
		return 0; // never any capacity
	}

	@Override
	public void setCapacityGP(int capacityGP) {
		// nothing; ignore
	}

	@Override
	public int getEncumberanceGP() {
		return encumberanceGP;
	}

	@Override
	public void setEncumberanceGP(int encumberanceGP) {
		this.encumberanceGP = encumberanceGP;
	}




	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}


	public EquipmentHands getRequiresHands() {
		return requiresHands;
	}


	public void setRequiresHands(EquipmentHands requiresHands) {
		this.requiresHands = requiresHands;
	}

	public String getRodStaffOrWand() {
		return rodStaffOrWand;
	}

	private static final String[] rswValues = new String[] {"Rod","Staff","Wand"};
	
	public void setRodStaffOrWand(String rodStaffOrWand) {
		if(rodStaffOrWand!=null) {
			if(!SU.inList(rodStaffOrWand,(Object[])rswValues)){
				throw new IllegalArgumentException("rodStaffOrWand value "+rodStaffOrWand+" is invalid :(");
			}
		}
		this.rodStaffOrWand = rodStaffOrWand;
	}

	public static final String[] getRswValues() {
		return rswValues;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((definition == null) ? 0 : definition.hashCode());
		result = prime * result + encumberanceGP;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((requiresHands == null) ? 0 : requiresHands.hashCode());
		result = prime * result + ((rodStaffOrWand == null) ? 0 : rodStaffOrWand.hashCode());
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
		RodStaffWand other = (RodStaffWand) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (definition == null) {
			if (other.definition != null)
				return false;
		} else if (!definition.equals(other.definition))
			return false;
		if (encumberanceGP != other.encumberanceGP)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (requiresHands != other.requiresHands)
			return false;
		if (rodStaffOrWand == null) {
			if (other.rodStaffOrWand != null)
				return false;
		} else if (!rodStaffOrWand.equals(other.rodStaffOrWand))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RodStaffWand [code=" + code + ", name=" + name + ", rodStaffOrWand=" + rodStaffOrWand
				+ ", encumberanceGP=" + encumberanceGP + ", definition=" + definition + ", requiresHands="
				+ requiresHands + "]";
	} 
}
