package com.nobbysoft.first.common.entities.equipment;

import java.io.Serializable;

import com.nobbysoft.first.common.entities.DataDTOInterface;

@SuppressWarnings("serial")
public class Scroll  implements  EquipmentI, Comparable<Scroll>, Serializable, DataDTOInterface<String> {

	
	private String code;
	private boolean spellScroll;
	private String name;  // only populated if NOT "spellScroll"
	private int encumberanceGP;	
	private String spellClass; // only populated if "spellScroll"
	private int countSpells; // only populated if "spellScroll"

	public Scroll() {
		
	}

	@Override
	public String getKey() {
		return code;
	}

	@Override
	public String getDescription() {
		//if(spellScroll) {
//			return spellClass+" " +countSpells;
		//} else {
			return name;
		//}
	}

	@Override
	public Object[] getAsRow() { 
		return new Object[] {this,code,name};
	}

	@Override
	public String[] getRowHeadings() {
		return new String[] {"Code","Description"};
	}

	@Override
	public Integer[] getColumnWidths() {		// 
		return new Integer[] {100,300};
	}

	@Override
	public String[] getColumnCodedListTypes() {
		// 
		return new String[] {null,null};
	}

	@Override
	public int compareTo(Scroll o) {

		int ret= getDescription().compareTo(o.getDescription());
		if(ret==0) {
			ret= code.compareTo(o.code);
			if(ret==0) {
				ret =toString().compareTo(o.toString());
			}
		}
		return ret;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public void setCode(String code) {
		this.code=code;
		
	}

	@Override
	public EquipmentType getType() {
		// TODO Auto-generated method stub
		return EquipmentType.SCROLL;
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
	public void setRequiresHands(EquipmentHands requiresHands) {
		// not allowed
	}

	@Override
	public int getCapacityGP() {
		// no capacity
		return 0;
	}

	@Override
	public void setCapacityGP(int capacityGP) {
		// no capacity		
	}

	@Override
	public int getEncumberanceGP() {
		return encumberanceGP;
	}

	@Override
	public void setEncumberanceGP(int encumberanceGP) {
		this.encumberanceGP=encumberanceGP;		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + countSpells;
		result = prime * result + encumberanceGP;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((spellClass == null) ? 0 : spellClass.hashCode());
		result = prime * result + (spellScroll ? 1231 : 1237);
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
		Scroll other = (Scroll) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (countSpells != other.countSpells)
			return false;
		if (encumberanceGP != other.encumberanceGP)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (spellClass == null) {
			if (other.spellClass != null)
				return false;
		} else if (!spellClass.equals(other.spellClass))
			return false;
		if (spellScroll != other.spellScroll)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Scroll [code=" + code + ", spellScroll=" + spellScroll + ", name=" + name + ", encumberanceGP="
				+ encumberanceGP + ", spellClass=" + spellClass + ", countSpells=" + countSpells + "]";
	}

	public boolean isSpellScroll() {
		return spellScroll;
	}

	public void setSpellScroll(boolean spellScroll) {
		this.spellScroll = spellScroll;
	}

	public String getSpellClass() {
		return spellClass;
	}

	public void setSpellClass(String spellClass) {
		this.spellClass = spellClass;
	}

	public int getCountSpells() {
		return countSpells;
	}

	public void setCountSpells(int countSpells) {
		this.countSpells = countSpells;
	}

}
