package com.nobbysoft.com.nobbysoft.first.common.views;

import java.io.Serializable;

import com.nobbysoft.com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.com.nobbysoft.first.common.entities.pc.PlayerCharacterEquipment;
import com.nobbysoft.com.nobbysoft.first.common.entities.pc.PlayerCharacterEquipmentKey;

@SuppressWarnings("serial")
public class ViewPlayerCharacterEquipment implements Serializable,DataDTOInterface<PlayerCharacterEquipmentKey> {

	private PlayerCharacterEquipment playerCharacterEquipment;
	private String equipmentDescription;

	public ViewPlayerCharacterEquipment(PlayerCharacterEquipment playerCharacterEquipment, String equipmentDescription) {
		this.playerCharacterEquipment=playerCharacterEquipment;
		this.equipmentDescription=equipmentDescription;
	}
	
	public ViewPlayerCharacterEquipment() { 
	}

	@Override
	public PlayerCharacterEquipmentKey getKey() {
		// TODO Auto-generated method stub
		return playerCharacterEquipment.getKey();
	}

	@Override
	public String getDescription() {
		return equipmentDescription;
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

	public PlayerCharacterEquipment getPlayerCharacterEquipment() {
		return playerCharacterEquipment;
	}

	public void setPlayerCharacterEquipment(PlayerCharacterEquipment playerCharacterEquipment) {
		this.playerCharacterEquipment = playerCharacterEquipment;
	}

	public String getEquipmentDescription() {
		return equipmentDescription;
	}

	public void setEquipmentDescription(String equipmentDescription) {
		this.equipmentDescription = equipmentDescription;
	}

}
