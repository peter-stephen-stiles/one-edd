package com.nobbysoft.first.common.entities.equipment;

import java.io.Serializable;
import java.math.BigDecimal;

import com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.first.common.utils.DICE;
import com.nobbysoft.first.common.views.DicePanelData;

@SuppressWarnings("serial")
public class WeaponRanged  implements EquipmentI ,WeaponACAdjustmentsI,WeaponMagicI,WeaponDamageI,WeaponRangedI,
Comparable<WeaponRanged>, Serializable, DataDTOInterface<String>{

	public WeaponRanged() { 
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
	
	private boolean twiceDamageToLargeWhenGroundedAgainstCharge;
	private boolean twiceDamageWhenChargingOnMount;
	private boolean twiceDamageWhenGroundedAgainstCharge;
	 
	private int ACAdjustment02;
	private int ACAdjustment03;
	private int ACAdjustment04;
	private int ACAdjustment05;
	private int ACAdjustment06;
	private int ACAdjustment07;
	private int ACAdjustment08;
	private int ACAdjustment09;
	private int ACAdjustment10;
	
	private int magicBonus;
	private int extraMagicBonus;
	private String extraMagicCondition;

	private int damageLMod;
	private int damageSMMod;
	
	private BigDecimal fireRate;
	private BigDecimal rangeS;
	private BigDecimal rangeM;
	private BigDecimal rangeL;
	
	
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
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public boolean isTwiceDamageToLargeWhenGroundedAgainstCharge() {
		return twiceDamageToLargeWhenGroundedAgainstCharge;
	}
	public void setTwiceDamageToLargeWhenGroundedAgainstCharge(boolean twiceDamageToLargeWhenGroundedAgainstCharge) {
		this.twiceDamageToLargeWhenGroundedAgainstCharge = twiceDamageToLargeWhenGroundedAgainstCharge;
	}
	public boolean isTwiceDamageWhenChargingOnMount() {
		return twiceDamageWhenChargingOnMount;
	}
	public void setTwiceDamageWhenChargingOnMount(boolean twiceDamageWhenChargingOnMount) {
		this.twiceDamageWhenChargingOnMount = twiceDamageWhenChargingOnMount;
	}
	public boolean isTwiceDamageWhenGroundedAgainstCharge() {
		return twiceDamageWhenGroundedAgainstCharge;
	}
	public void setTwiceDamageWhenGroundedAgainstCharge(boolean twiceDamageWhenGroundedAgainstCharge) {
		this.twiceDamageWhenGroundedAgainstCharge = twiceDamageWhenGroundedAgainstCharge;
	}
	public int getACAdjustment02() {
		return ACAdjustment02;
	}
	public void setACAdjustment02(int aCAdjustment02) {
		ACAdjustment02 = aCAdjustment02;
	}
	public int getACAdjustment03() {
		return ACAdjustment03;
	}
	public void setACAdjustment03(int aCAdjustment03) {
		ACAdjustment03 = aCAdjustment03;
	}
	public int getACAdjustment04() {
		return ACAdjustment04;
	}
	public void setACAdjustment04(int aCAdjustment04) {
		ACAdjustment04 = aCAdjustment04;
	}
	public int getACAdjustment05() {
		return ACAdjustment05;
	}
	public void setACAdjustment05(int aCAdjustment05) {
		ACAdjustment05 = aCAdjustment05;
	}
	public int getACAdjustment06() {
		return ACAdjustment06;
	}
	public void setACAdjustment06(int aCAdjustment06) {
		ACAdjustment06 = aCAdjustment06;
	}
	public int getACAdjustment07() {
		return ACAdjustment07;
	}
	public void setACAdjustment07(int aCAdjustment07) {
		ACAdjustment07 = aCAdjustment07;
	}
	public int getACAdjustment08() {
		return ACAdjustment08;
	}
	public void setACAdjustment08(int aCAdjustment08) {
		ACAdjustment08 = aCAdjustment08;
	}
	public int getACAdjustment09() {
		return ACAdjustment09;
	}
	public void setACAdjustment09(int aCAdjustment09) {
		ACAdjustment09 = aCAdjustment09;
	}
	public int getACAdjustment10() {
		return ACAdjustment10;
	}
	public void setACAdjustment10(int aCAdjustment10) {
		ACAdjustment10 = aCAdjustment10;
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
	public BigDecimal getFireRate() {
		return fireRate;
	}
	public void setFireRate(BigDecimal fireRate) {
		this.fireRate = fireRate;
	}
	public BigDecimal getRangeS() {
		return rangeS;
	}
	public void setRangeS(BigDecimal rangeS) {
		this.rangeS = rangeS;
	}
	public BigDecimal getRangeM() {
		return rangeM;
	}
	public void setRangeM(BigDecimal rangeM) {
		this.rangeM = rangeM;
	}
	public BigDecimal getRangeL() {
		return rangeL;
	}
	public void setRangeL(BigDecimal rangeL) {
		this.rangeL = rangeL;
	}
	
	@Override
	public int compareTo(WeaponRanged o) {
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
		return EquipmentType.WEAPON_RANGED;
	}
	@Override
	public String toString() {
		return "WeaponRangedThrown [code=" + code + ", name=" + name + ", requiresHands=" + requiresHands
				+ ", capacityGP=" + capacityGP + ", encumberanceGP=" + encumberanceGP + ", damageSMDiceCount="
				+ damageSMDiceCount + ", damageSMDICE=" + damageSMDICE + ", damageLDiceCount=" + damageLDiceCount
				+ ", damageLDICE=" + damageLDICE + ", notes=" + notes + ", twiceDamageToLargeWhenGroundedAgainstCharge="
				+ twiceDamageToLargeWhenGroundedAgainstCharge + ", twiceDamageWhenChargingOnMount="
				+ twiceDamageWhenChargingOnMount + ", twiceDamageWhenGroundedAgainstCharge="
				+ twiceDamageWhenGroundedAgainstCharge + ", ACAdjustment02=" + ACAdjustment02 + ", ACAdjustment03="
				+ ACAdjustment03 + ", ACAdjustment04=" + ACAdjustment04 + ", ACAdjustment05=" + ACAdjustment05
				+ ", ACAdjustment06=" + ACAdjustment06 + ", ACAdjustment07=" + ACAdjustment07 + ", ACAdjustment08="
				+ ACAdjustment08 + ", ACAdjustment09=" + ACAdjustment09 + ", ACAdjustment10=" + ACAdjustment10
				+ ", magicBonus=" + magicBonus + ", extraMagicBonus=" + extraMagicBonus + ", extraMagicCondition="
				+ extraMagicCondition + ", damageLMod=" + damageLMod + ", damageSMMod=" + damageSMMod + ", fireRate="
				+ fireRate + ", rangeS=" + rangeS + ", rangeM=" + rangeM + ", rangeL=" + rangeL + "]";
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

	public String getLDamage() {
		return DicePanelData.damage(damageLDiceCount,damageLDICE,damageLMod);
	}
	
	
	public String getSMDamage() {
		return DicePanelData.damage(damageSMDiceCount,damageSMDICE,damageSMMod);
	}

	@Override
	public Object[] getAsRow() { 
		return new Object[] {this,code,name,fireRate,rangeS,rangeM,rangeL,getSMDamage() ,getLDamage(),notes};
	}


	@Override
	public String[] getRowHeadings() { 
		return new String[] {"Code","Name","RoF","Short","Medium","Long","Dmg (S/M)","Dmg (L)","Notes"};
	}


	@Override
	public Integer[] getColumnWidths() { 
		return new Integer[] {150,200,60,60,60,60,80,80,300};
	}


	@Override
	public String[] getColumnCodedListTypes() {
		return new String[] {null,null,null,null,null,null,null,null,null};
	}
	
	    
 

	
	
}
