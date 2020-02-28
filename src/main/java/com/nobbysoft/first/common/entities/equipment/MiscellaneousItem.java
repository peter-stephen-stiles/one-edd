package com.nobbysoft.first.common.entities.equipment;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.first.common.utils.COINAGE;

@SuppressWarnings("serial")
public class MiscellaneousItem implements  EquipmentI, Comparable<MiscellaneousItem>, Serializable, DataDTOInterface<String> {

	
	private String code;
	private String name; 
	private int encumberanceGP;
	private int capacityGP;	
	private String definition;
	private COINAGE valueCoin;
	private int value;
	
	public String getValueCoinToString() {
		if(valueCoin==null) {
			return null;
		} else {
			return valueCoin.name();
		}
	}
	
	public void setValueCoinFromString(String coin) {
		if(coin==null) {
			valueCoin=null;
		} else {
			valueCoin = COINAGE.valueOf(coin);
		}
	}
	
	
	public String getValueString() {
		if(value==0||valueCoin==null) {
			return "";
		}
		Map<COINAGE,Integer> v = new HashMap<>();
		v.put(valueCoin, value);
		return COINAGE.getValueString(v);				
	}
	
	public int getValueInCopper() {
		if(value==0||valueCoin==null) {
			return 0;
		}
		Map<COINAGE,Integer> v = new HashMap<>();
		v.put(valueCoin, value); 
		return COINAGE.getValueInCopper(v);				
	}
	
	
	public MiscellaneousItem() {
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
		return new Object[] {this,code,name,getValueString()};
	}

	@Override
	public String[] getRowHeadings() {
		return new String[] {"Code","Name","Value"};
	}

	@Override
	public Integer[] getColumnWidths() {
		return new Integer[] {120,300,100};
	}

	@Override
	public String[] getColumnCodedListTypes() {
		return new String[] {null,null,null};
	}

	@Override
	public int compareTo(MiscellaneousItem o) {

		int ret= name.compareTo(o.name);
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
		this.code = code;
	}

	@Override
	public EquipmentType getType() {
		return EquipmentType.MISCELLANEOUS_ITEM;			
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

	public COINAGE getValueCoin() {
		return valueCoin;
	}

	public void setValueCoin(COINAGE valueCoin) {
		this.valueCoin = valueCoin;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + capacityGP;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((definition == null) ? 0 : definition.hashCode());
		result = prime * result + encumberanceGP;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + value;
		result = prime * result + ((valueCoin == null) ? 0 : valueCoin.hashCode());
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
		MiscellaneousItem other = (MiscellaneousItem) obj;
		if (capacityGP != other.capacityGP)
			return false;
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
		if (value != other.value)
			return false;
		if (valueCoin != other.valueCoin)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MiscellaneousItem [code=" + code + ", name=" + name + ", encumberanceGP=" + encumberanceGP
				+ ", capacityGP=" + capacityGP + ", definition=" + definition + ", valueCoin=" + valueCoin + ", value="
				+ value + "]";
	}

	public int getValue() {
		return value;
	}

 
 

 

	
}
