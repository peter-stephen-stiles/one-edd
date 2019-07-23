package com.nobbysoft.com.nobbysoft.first.common.entities.equipment;

import java.io.Serializable;

import com.nobbysoft.com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.com.nobbysoft.first.common.utils.DICE;

@SuppressWarnings("serial")
public class WeaponAmmunition implements EquipmentI ,WeaponMagicI,
Comparable<WeaponAmmunition>, Serializable, DataDTOInterface<String>{

	public WeaponAmmunition() { 
	}

	private String code;
	private String name;
	private EquipmentHands requiresHands;
	private int capacityGP;
	private int encumberanceGP;

	private int damageSMDiceCount;
	private DICE damageSMDICE;
	private int damageLDiceCount;
	private DICE damageLDICE;
	private String notes;
	
 
	
	private int magicBonus;
	private int extraMagicBonus;
	private String extraMagicCondition;
	
	
	@Override
	public EquipmentType getType() { 
		return EquipmentType.AMMUNITION;
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

 
 
 


	public int getDamageSMDiceCount() {
		return damageSMDiceCount;
	}


	public void setDamageSMDiceCount(int damageSMDiceCount) {
		this.damageSMDiceCount = damageSMDiceCount;
	}


	public DICE getDamageSMDICE() {
		return damageSMDICE;
	}


	public void setDamageSMDICE(DICE damageSMDICE) {
		this.damageSMDICE = damageSMDICE;
	}


	public int getDamageLDiceCount() {
		return damageLDiceCount;
	}


	public void setDamageLDiceCount(int damageLDiceCount) {
		this.damageLDiceCount = damageLDiceCount;
	}


	public DICE getDamageLDICE() {
		return damageLDICE;
	}


	public void setDamageLDICE(DICE damageLDICE) {
		this.damageLDICE = damageLDICE;
	}

	public String getLDamage() {
		return ""+damageLDiceCount+""+damageLDICE.getDesc();
	}
	
	
	public String getSMDamage() {
		return ""+damageSMDiceCount+""+damageSMDICE.getDesc();
	}
	

	public String getNotes() {
		return notes;
	}


	public void setNotes(String notes) {
		this.notes = notes;
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
		return new Object[] {this,code,name,getSMDamage(),getLDamage(),notes};
	}


	@Override
	public String[] getRowHeadings() { 
		return new String[] {"Code","Name","Dmg (S/M)","Dmg (L)","Notes"};
	}


	@Override
	public Integer[] getColumnWidths() { 
		return new Integer[] {150,200,80,80,300};
	}


	@Override
	public String[] getColumnCodedListTypes() {
		return new String[] {null,null,null};
	}


	@Override
	public int compareTo(WeaponAmmunition o) {
		int ret= code.compareTo(o.code);
		if(ret==0) {
			ret= name.compareTo(o.name);
			if(ret==0) {
				ret =toString().compareTo(o.toString());
			}
		}
		return ret;
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


	public int getExtraMagicBonus() {
		return extraMagicBonus;
	}


	public void setExtraMagicBonus(int extraMagicBonus) {
		this.extraMagicBonus = extraMagicBonus;
	}


	public String getExtraMagicCondition() {
		return extraMagicCondition;
	}


	public void setExtraMagicCondition(String extraMagicCondition) {
		this.extraMagicCondition = extraMagicCondition;
	}


 
 

}
