package com.nobbysoft.first.common.entities.equipment;

import java.io.Serializable;

import com.nobbysoft.first.common.entities.DataDTOInterface;

@SuppressWarnings("serial")
public class MagicRing implements  MagicalArmourBonus,EquipmentI, Comparable<MagicRing>, Serializable, DataDTOInterface<String> {

	
	private String code;
	private String name; 
	private int encumberanceGP;
	private int magicalBonus;
	private String extendedBonus;
	
	public MagicRing() {
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
		return new Object[] {this,code,name,magicalBonus,extendedBonus};
	}

	@Override
	public String[] getRowHeadings() {
		return new String[] {"Code","Name","Magical bonus","Other magic"};
	}

	@Override
	public Integer[] getColumnWidths() {
		return new Integer[] {120,150,100,200};
	}

	@Override
	public String[] getColumnCodedListTypes() {
		return new String[] {null,null,null,null};
	}

	@Override
	public int compareTo(MagicRing o) {
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
		return EquipmentType.MAGIC_RING;
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

	public int getMagicalBonus() {
		return magicalBonus;
	}

	public void setMagicalBonus(int magicalBonus) {
		this.magicalBonus = magicalBonus;
	}

	public String getExtendedBonus() {
		return extendedBonus;
	}

	public void setExtendedBonus(String extendedBonus) {
		this.extendedBonus = extendedBonus;
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
		result = prime * result + ((extendedBonus == null) ? 0 : extendedBonus.hashCode());
		result = prime * result + magicalBonus;
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
		MagicRing other = (MagicRing) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (encumberanceGP != other.encumberanceGP)
			return false;
		if (extendedBonus == null) {
			if (other.extendedBonus != null)
				return false;
		} else if (!extendedBonus.equals(other.extendedBonus))
			return false;
		if (magicalBonus != other.magicalBonus)
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
		return "MagicRing [code=" + code + ", name=" + name + ", encumberanceGP=" + encumberanceGP + ", magicalBonus="
				+ magicalBonus + ", extendedBonus=" + extendedBonus + "]";
	}

 

	
}
