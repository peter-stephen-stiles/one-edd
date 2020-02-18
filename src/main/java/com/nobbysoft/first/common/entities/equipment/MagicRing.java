package com.nobbysoft.first.common.entities.equipment;

import java.io.Serializable;

import com.nobbysoft.first.common.entities.DataDTOInterface;

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
		return 0;
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

 

	
}
