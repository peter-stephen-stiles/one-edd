package com.nobbysoft.first.common.entities.pc;

import java.io.Serializable;

import com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.first.common.entities.equipment.EquipmentType;
import com.nobbysoft.first.common.entities.equipment.EquipmentWhere;

@SuppressWarnings("serial")
public class PlayerCharacterEquipment implements Serializable,DataDTOInterface<PlayerCharacterEquipmentKey> {

	private int pcId;
	private int equipmentId;
	private EquipmentType equipmentType;
	private String code;
	private boolean equipped;
	private EquipmentWhere equippedWhere;
	
	private int countOwned = 1;
	
	
	
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
		return new Object[] {this,pcId,equipmentType,code,equippedWhere,countOwned};
	}

	@Override
	public String[] getRowHeadings() { 
		return new String[] {"Player","Type","Equipment","Where","Count"};
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

 

	public int getCountOwned() {
		return countOwned;
	}

	public void setCountOwned(int count) {
		this.countOwned = count;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + countOwned;
		result = prime * result + equipmentId;
		result = prime * result + ((equipmentType == null) ? 0 : equipmentType.hashCode());
		result = prime * result + (equipped ? 1231 : 1237);
		result = prime * result + ((equippedWhere == null) ? 0 : equippedWhere.hashCode());
		result = prime * result + pcId;
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
		PlayerCharacterEquipment other = (PlayerCharacterEquipment) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (countOwned != other.countOwned)
			return false;
		if (equipmentId != other.equipmentId)
			return false;
		if (equipmentType != other.equipmentType)
			return false;
		if (equipped != other.equipped)
			return false;
		if (equippedWhere != other.equippedWhere)
			return false;
		if (pcId != other.pcId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PlayerCharacterEquipment [pcId=" + pcId + ", equipmentId=" + equipmentId + ", equipmentType="
				+ equipmentType + ", code=" + code + ", equipped=" + equipped + ", equippedWhere=" + equippedWhere
				+ ", count=" + countOwned + "]";
	}

 
 

}
