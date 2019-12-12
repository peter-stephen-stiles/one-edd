package com.nobbysoft.first.common.entities.equipment;

import java.io.Serializable;

import com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.first.common.entities.DataDTOInterface;

@SuppressWarnings("serial")
public class EquipmentClass implements Comparable<EquipmentClass>, Serializable, DataDTOInterface<EquipmentClassKey> {


	private String type;
	private String code;
	private String classId;
	
	
	public EquipmentClass() { 
	}


	@Override
	public EquipmentClassKey getKey() {
		return new EquipmentClassKey(type,code,classId);
	}


	@Override
	public String getDescription() {
		return type+":"+code+":"+classId;
	}


	@Override
	public Object[] getAsRow() { 
		return new Object[] {this,type,code,classId};
	}


	@Override
	public String[] getRowHeadings() { 
		return new String[] {"Type","Code","Class"};
	}


	@Override
	public Integer[] getColumnWidths() { 
		return new Integer[] {100,200,200};
	}


	@Override
	public String[] getColumnCodedListTypes() { 
		return new String[] {Constants.CLI_EQUIPMENT_TYPE,"Code",Constants.CLI_CLASS};
	}


	@Override
	public int compareTo(EquipmentClass o) {
		int ret = type.compareTo(o.getType());
		if(ret==0) {
			ret = code.compareTo(o.getCode());
			if(ret==0) {
				ret = classId.compareTo(o.getClassId());
			}
		}
		return ret;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getClassId() {
		return classId;
	}


	public void setClassId(String classId) {
		this.classId = classId;
	}

}
