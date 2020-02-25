package com.nobbysoft.first.common.entities.equipment;

import java.io.Serializable;

import com.nobbysoft.first.common.entities.DataDTOInterface;

public class Armour implements EquipmentI, ArmourI,Comparable<Armour>, Serializable, DataDTOInterface<String> {

	
	private String code;
	private String name;
	private EquipmentHands requiresHands;
	private int capacityGP;
	private int encumberanceGP;
	
	private int baseAC;
	private int magicBonus;
	
	private int baseMovement;
	private BULK bulk;
	
	public Armour() { 
	}

	@Override
	public EquipmentType getType() {
		return EquipmentType.ARMOUR;
	}

	@Override
	public String getKey() { 
		return code;
	}

	@Override
	public String getDescription() { 
		return name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EquipmentHands getRequiresHands() {
		return requiresHands;
	}

	public void setRequiresHands(EquipmentHands requiresHands) {
		this.requiresHands = requiresHands;
	}

	public int getCapacityGP() {
		return capacityGP;
	}

	public void setCapacityGP(int capacityGP) {
		this.capacityGP = capacityGP;
	}

	public int getEncumberanceGP() {
		return encumberanceGP;
	}

	public void setEncumberanceGP(int encumberanceGP) {
		this.encumberanceGP = encumberanceGP;
	}

	public int getBaseAC() {
		return baseAC;
	}

	public void setBaseAC(int baseAC) {
		this.baseAC = baseAC;
	}

	public int getMagicBonus() {
		return magicBonus;
	}

	public void setMagicBonus(int magicBonus) {
		this.magicBonus = magicBonus;
	}

	public int getAC() {
		return baseAC - magicBonus;
	}
	
	@Override
	public Object[] getAsRow() { 
		return new Object[] {this,code,name,baseAC,magicBonus,getAC()};
	}

	@Override
	public String[] getRowHeadings() { 
		return new String[] {"Code","Name","Base A/C","Magic","A/C"};
	}

	@Override
	public Integer[] getColumnWidths() { 
		return new Integer[] {120,300,100,100,100};
	}

	@Override
	public String[] getColumnCodedListTypes() { 
		return new String[] {null,null,null,null,null};
	}

	@Override
	public int compareTo(Armour o) { 

		int ret= name.compareTo(o.name);
		if(ret==0) {
			ret= code.compareTo(o.code);
			if(ret==0) {
				ret =toString().compareTo(o.toString());
			}
		}
		return ret;
	}
	public int getBaseMovement() {
		return baseMovement;
	}

	public void setBaseMovement(int baseMovement) {
		this.baseMovement = baseMovement;
	}

	public BULK getBulk() {
		return bulk;
	}

	public void setBulk(BULK bulk) {
		this.bulk = bulk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + baseAC;
		result = prime * result + baseMovement;
		result = prime * result + ((bulk == null) ? 0 : bulk.hashCode());
		result = prime * result + capacityGP;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + encumberanceGP;
		result = prime * result + magicBonus;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((requiresHands == null) ? 0 : requiresHands.hashCode());
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
		Armour other = (Armour) obj;
		if (baseAC != other.baseAC)
			return false;
		if (baseMovement != other.baseMovement)
			return false;
		if (bulk != other.bulk)
			return false;
		if (capacityGP != other.capacityGP)
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (encumberanceGP != other.encumberanceGP)
			return false;
		if (magicBonus != other.magicBonus)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (requiresHands != other.requiresHands)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Armour [code=" + code + ", name=" + name + ", requiresHands=" + requiresHands + ", capacityGP="
				+ capacityGP + ", encumberanceGP=" + encumberanceGP + ", baseAC=" + baseAC + ", magicBonus="
				+ magicBonus + ", baseMovement=" + baseMovement + ", bulk=" + bulk + "]";
	}
 
 
 

}
