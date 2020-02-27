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
	private int valuePp;
	private int valueGp;
	private int valueEp;
	private int valueSp;
	private int valueCp;
	
	
	public String getValue() {
		if(valuePp+valueGp+valueEp+valueSp+valueCp==0) {
			return "";
		}
		Map<COINAGE,Integer> v = new HashMap<>();
		v.put(COINAGE.PP, valuePp);
		v.put(COINAGE.GP, valueGp);
		v.put(COINAGE.EP, valueEp);
		v.put(COINAGE.SP, valueSp);
		v.put(COINAGE.CP, valueCp);
		return COINAGE.getValueString(v);				
	}
	
	public int getValueInCopper() {
		if(valuePp+valueGp+valueEp+valueSp+valueCp==0) {
			return 0;
		}
		Map<COINAGE,Integer> v = new HashMap<>();
		v.put(COINAGE.PP, valuePp);
		v.put(COINAGE.GP, valueGp);
		v.put(COINAGE.EP, valueEp);
		v.put(COINAGE.SP, valueSp);
		v.put(COINAGE.CP, valueCp);
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
		return new Object[] {this,code,name,getValue()};
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

 
 

 

	
}
