package com.nobbysoft.first.common.entities.equipment;

import java.io.Serializable;

import com.nobbysoft.first.common.entities.DataDTOInterface;

public class Shield implements EquipmentI,  Comparable<Shield>, Serializable, DataDTOInterface<String> {
	
	
	private String code;
	private String name;
	private int capacityGP;
	private int encumberanceGP;	
	private int magicBonus;	
	private int attacksPerRound;
	

	@Override
	public Object[] getAsRow() {
		return new Object[] {this,code,name,encumberanceGP,magicBonus,attacksPerRound};
	}
	@Override
	public String[] getRowHeadings()  {
		return new String[] {"Code","Name","Enc (GP)","Magic Bonus","Against #Att/Round"};
	}
	@Override
	public Integer[] getColumnWidths() {
		return new Integer[] {100,200,100,100,120};
	}
	@Override
	public String[] getColumnCodedListTypes() {
		return new String[0];
	}
	@Override 
	public int compareTo(Shield o) { 
		int ret= code.compareTo(o.code);
		if(ret==0) {
			ret= name.compareTo(o.name);
			if(ret==0) {
				ret =toString().compareTo(o.toString());
			}
		}
		return ret;
	}
	@Override
	public EquipmentType getType() {
		return EquipmentType.SHIELD;
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
		return EquipmentHands.SINGLE_HANDED;
	}
	@Override
	public void setRequiresHands(EquipmentHands requiresHands) {
		throw new IllegalStateException("Shields are alwasy single handed!");
	}
	@Override
	public int getCapacityGP() {
		return capacityGP;
	}
	@Override
	public void setCapacityGP(int capacityGP) {
		this.capacityGP=capacityGP;
		
	}
	@Override
	public int getEncumberanceGP() {
		return encumberanceGP;
	}
	@Override
	public void setEncumberanceGP(int encumberanceGP) {
		this.encumberanceGP=encumberanceGP;		
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
 
	public int getMagicBonus() {
		return magicBonus;
	}
	public void setMagicBonus(int magicBonus) {
		this.magicBonus = magicBonus;
	}
	public int getAttacksPerRound() {
		return attacksPerRound;
	}
	public void setAttacksPerRound(int attacksPerRound) {
		this.attacksPerRound = attacksPerRound;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1; 
		result = prime * result + attacksPerRound;
		result = prime * result + capacityGP;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + encumberanceGP;
		result = prime * result + magicBonus;
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
		Shield other = (Shield) obj; 
		if (attacksPerRound != other.attacksPerRound)
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
		return true;
	}
	@Override
	public String toString() {
		return "Shield [code=" + code + ", name=" + name + ", capacityGP="
				+ capacityGP + ", encumberanceGP=" + encumberanceGP +   ", magicBonus="
				+ magicBonus + ", attacksPerRound=" + attacksPerRound + "]";
	}

	@Override
	public String getKey() {
		return code;
	}
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return name;
	}
	
}
