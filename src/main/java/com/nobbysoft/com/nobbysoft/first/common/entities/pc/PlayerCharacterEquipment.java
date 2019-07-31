package com.nobbysoft.com.nobbysoft.first.common.entities.pc;

import java.io.Serializable;

import com.nobbysoft.com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.EquipmentType;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.EquipmentWhere;

@SuppressWarnings("serial")
public class PlayerCharacterEquipment implements Serializable,DataDTOInterface<PlayerCharacterEquipmentKey> {

	private int pcId;
	private int equipmentId;
	private EquipmentType equipmentType;
	private String code;
	private boolean equipped;
	private EquipmentWhere equippedWhere;
	
	
	
	
	public PlayerCharacterEquipment() {

	}

	@Override
	public PlayerCharacterEquipmentKey getKey() { 
		return new PlayerCharacterEquipmentKey(pcId,equipmentId);
	}

	@Override
	public String getDescription() { 
		return pcId+":"+equipmentType+":"+code;
	}

	@Override
	public Object[] getAsRow() { 
		return new Object[] {this,pcId,equipmentType,code,equippedWhere};
	}

	@Override
	public String[] getRowHeadings() { 
		return new String[] {"Player","Type","Equipment","Where"};
	}

	@Override
	public Integer[] getColumnWidths() { 
		return new Integer[] {200,100,200,100};
	}

	@Override
	public String[] getColumnCodedListTypes() { 
		return new String[] {Constants.CLI_PLAYER_CHARACTER,Constants.CLI_EQUIPMENT_TYPE,null,Constants.CLI_EQUIPMENT_WHERE};
	}

	public int getPcId() {
		return pcId;
	}

	public void setPcId(int pcId) {
		this.pcId = pcId;
	}

	public int getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(int equipmentId) {
		this.equipmentId = equipmentId;
	}

	public EquipmentType getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(EquipmentType equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isEquipped() {
		return equipped;
	}

	public void setEquipped(boolean equipped) {
		this.equipped = equipped;
	}

	public EquipmentWhere getEquippedWhere() {
		return equippedWhere;
	}

	public void setEquippedWhere(EquipmentWhere equippedWhere) {
		this.equippedWhere = equippedWhere;
	}

 
 

}
