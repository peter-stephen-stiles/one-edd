package com.nobbysoft.first.common.views;

import java.io.Serializable;

import com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.first.common.entities.equipment.EquipmentClass;
import com.nobbysoft.first.common.entities.equipment.EquipmentClassKey;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterEquipment;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterEquipmentKey;

@SuppressWarnings("serial")
public class ViewClassEquipment implements Serializable,DataDTOInterface<EquipmentClassKey> {

	private EquipmentClass equipmentClass;
	private String equipmentName;
	private String className;
	private boolean assigned=true; // i know its a strange default :)

	public ViewClassEquipment( EquipmentClass equipmentClass, String equipmentName,String className) {
		this.equipmentClass = equipmentClass;
		this.equipmentName = equipmentName;
		this.className= className;
	}

	public ViewClassEquipment( EquipmentClass equipmentClass, String equipmentName,String className,boolean assigned) {
		this.equipmentClass = equipmentClass;
		this.equipmentName = equipmentName;
		this.className= className;
		this.assigned=assigned;
	}
	
	public ViewClassEquipment() { 
	}

	@Override
	public EquipmentClassKey getKey() {
		return equipmentClass.getKey();
	}

	@Override
	public String getDescription() {
		return equipmentName  +" " + className;
	}

	@Override
	public Object[] getAsRow() {
		return null;
	}

	@Override
	public String[] getRowHeadings() {
		return null;
	}

	@Override
	public Integer[] getColumnWidths() {
		return null;
	}

	@Override
	public String[] getColumnCodedListTypes() {
		return null;
	}

	public EquipmentClass getEquipmentClass() {
		return equipmentClass;
	}

	public void setEquipmentClass(EquipmentClass equipmentClass) {
		this.equipmentClass = equipmentClass;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public boolean isAssigned() {
		return assigned;
	}

	public void setAssigned(boolean assigned) {
		this.assigned = assigned;
	}

	
 
	
	@Override
	public String toString() {
		return "ViewClassEquipment [equipmentClass=" + equipmentClass + ", equipmentName=" + equipmentName
				+ ", className=" + className + ", assigned=" + assigned + "]";
	}

 
 


}
