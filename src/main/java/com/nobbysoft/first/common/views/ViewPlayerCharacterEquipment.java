package com.nobbysoft.first.common.views;

import java.io.Serializable;

import com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterEquipment;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterEquipmentKey;

@SuppressWarnings("serial")
public class ViewPlayerCharacterEquipment implements Serializable,DataDTOInterface<PlayerCharacterEquipmentKey> {

	private PlayerCharacterEquipment playerCharacterEquipment;
	private String equipmentDescription;
	private int encumberence;

	public ViewPlayerCharacterEquipment(PlayerCharacterEquipment playerCharacterEquipment, String equipmentDescription,
			int encumberence) {
		this.playerCharacterEquipment=playerCharacterEquipment;
		this.equipmentDescription=equipmentDescription;
		this.encumberence=encumberence;
	}
	
	public ViewPlayerCharacterEquipment() { 
	}

	@Override
	public PlayerCharacterEquipmentKey getKey() {
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

	public int getEncumberence() {
		return encumberence;
	}

	public void setEncumberence(int encumberence) {
		this.encumberence = encumberence;
	}

}
