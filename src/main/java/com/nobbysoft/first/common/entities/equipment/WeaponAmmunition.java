package com.nobbysoft.first.common.entities.equipment;

import java.io.Serializable;

import com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.first.common.utils.DICE;
import com.nobbysoft.first.common.views.DicePanelData;

@SuppressWarnings("serial")
public class WeaponAmmunition implements EquipmentI ,WeaponMagicI,WeaponDamageI,
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

	private int damageLMod;
	private int damageSMMod;
	
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

	private String damage(int mult,DICE dice, int mod) {
		StringBuilder sb = new StringBuilder();
		sb.append(mult);
		sb.append(dice.getDesc());
		if (mod>0) {
			sb.append("+");
		}
		if(mod!=0) {
			sb.append(mod);
		}
		return sb.toString();
	}

	public String getLDamage() {
		return damage(damageLDiceCount,damageLDICE,damageLMod);
	}
	
	
	public String getSMDamage() {
		return damage(damageSMDiceCount,damageSMDICE,damageSMMod);
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

		int ret= name.compareTo(o.name);
		if(ret==0) {
			ret= code.compareTo(o.code);
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


	public int getDamageLMod() {
		return damageLMod;
	}


	public void setDamageLMod(int damageLMod) {
		this.damageLMod = damageLMod;
	}


	public int getDamageSMMod() {
		return damageSMMod;
	}


	public void setDamageSMMod(int damageSMMod) {
		this.damageSMMod = damageSMMod;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + capacityGP;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((damageLDICE == null) ? 0 : damageLDICE.hashCode());
		result = prime * result + damageLDiceCount;
		result = prime * result + damageLMod;
		result = prime * result + ((damageSMDICE == null) ? 0 : damageSMDICE.hashCode());
		result = prime * result + damageSMDiceCount;
		result = prime * result + damageSMMod;
		result = prime * result + encumberanceGP;
		result = prime * result + extraMagicBonus;
		result = prime * result + ((extraMagicCondition == null) ? 0 : extraMagicCondition.hashCode());
		result = prime * result + magicBonus;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
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
		WeaponAmmunition other = (WeaponAmmunition) obj;
		if (capacityGP != other.capacityGP)
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (damageLDICE != other.damageLDICE)
			return false;
		if (damageLDiceCount != other.damageLDiceCount)
			return false;
		if (damageLMod != other.damageLMod)
			return false;
		if (damageSMDICE != other.damageSMDICE)
			return false;
		if (damageSMDiceCount != other.damageSMDiceCount)
			return false;
		if (damageSMMod != other.damageSMMod)
			return false;
		if (encumberanceGP != other.encumberanceGP)
			return false;
		if (extraMagicBonus != other.extraMagicBonus)
			return false;
		if (extraMagicCondition == null) {
			if (other.extraMagicCondition != null)
				return false;
		} else if (!extraMagicCondition.equals(other.extraMagicCondition))
			return false;
		if (magicBonus != other.magicBonus)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (requiresHands != other.requiresHands)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "WeaponAmmunition [code=" + code + ", name=" + name + ", requiresHands=" + requiresHands
				+ ", capacityGP=" + capacityGP + ", encumberanceGP=" + encumberanceGP + ", damageSMDiceCount="
				+ damageSMDiceCount + ", damageSMDICE=" + damageSMDICE + ", damageLDiceCount=" + damageLDiceCount
				+ ", damageLDICE=" + damageLDICE + ", notes=" + notes + ", magicBonus=" + magicBonus
				+ ", extraMagicBonus=" + extraMagicBonus + ", extraMagicCondition=" + extraMagicCondition
				+ ", damageLMod=" + damageLMod + ", damageSMMod=" + damageSMMod + "]";
	}

	@Override
	public DicePanelData getDamageL() {
		return new DicePanelData(damageLDiceCount,damageLDICE,damageLMod);
	}


	@Override
	public void setDamageL(DicePanelData data) {
		setDamageLDiceCount(data.getMultiplier());
		setDamageLDICE(data.getDice());
		setDamageLMod(data.getModifier());
		
	}


	@Override
	public DicePanelData getDamageSM() {
		return new DicePanelData(damageSMDiceCount,damageSMDICE,damageSMMod);
	}


	@Override
	public void setDamageSM(DicePanelData data) {
		setDamageSMDiceCount(data.getMultiplier());
		setDamageSMDICE(data.getDice());
		setDamageSMMod(data.getModifier());
		
		
	}
 
 

}
