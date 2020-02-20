package com.nobbysoft.first.common.entities.equipment;

import java.io.Serializable;

import com.nobbysoft.first.common.entities.DataDTOInterface;

@SuppressWarnings("serial")
public class MiscellaneousMagicItem implements  EquipmentI, Comparable<MiscellaneousMagicItem>, Serializable, DataDTOInterface<String> {

	
	private String code;
	private String name; 
	private int encumberanceGP;	
	private String definition;
	
	public MiscellaneousMagicItem() {
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
		return new Object[] {this,code,name};
	}

	@Override
	public String[] getRowHeadings() {
		return new String[] {"Code","Name"};
	}

	@Override
	public Integer[] getColumnWidths() {
		return new Integer[] {120,300};
	}

	@Override
	public String[] getColumnCodedListTypes() {
		return new String[] {null,null};
	}

	@Override
	public int compareTo(MiscellaneousMagicItem o) {
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
		return EquipmentType.MISCELLANEOUS_MAGIC;			
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
	public EquipmentHands getRequiresHands() {
		return EquipmentHands.NONE;
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

	@Override
	public void setRequiresHands(EquipmentHands requiresHands) {
		// ignore!		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + encumberanceGP;
		result = prime * result + ((definition == null) ? 0 : definition.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		MiscellaneousMagicItem other = (MiscellaneousMagicItem) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (encumberanceGP != other.encumberanceGP)
			return false;
		if (definition == null) {
			if (other.definition != null)
				return false;
		} else if (!definition.equals(other.definition))
			return false; 
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MagicRing [code=" + code + ", name=" + name + ", encumberanceGP=" + encumberanceGP   + ", definition=" + definition + "]";
	}

 

	
}
