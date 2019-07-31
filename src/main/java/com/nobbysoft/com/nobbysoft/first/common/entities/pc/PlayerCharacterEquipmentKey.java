package com.nobbysoft.com.nobbysoft.first.common.entities.pc;

import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.EquipmentType;

public class PlayerCharacterEquipmentKey implements Comparable<PlayerCharacterEquipmentKey> {


	private int pcId; 
	private int equipmentId;
	
	public PlayerCharacterEquipmentKey(int pcId,int equipmentId) { 
		this.pcId=pcId;
		this.equipmentId=equipmentId; 
	}
	
	public PlayerCharacterEquipmentKey() { 
	}

	@Override
	public int compareTo(PlayerCharacterEquipmentKey o) {
		int ret=0;
		if(pcId<o.getPcId()) {
			ret = 1;
		} else if (pcId>o.getPcId()) {
			ret = -1;
		} 
		if(ret==0) {
			if(equipmentId<o.getEquipmentId()) {
				ret = 1;
			} else if (equipmentId>o.getEquipmentId()) {
				ret = -1;
			} 
		}

		return ret;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + equipmentId;
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
		PlayerCharacterEquipmentKey other = (PlayerCharacterEquipmentKey) obj;
		if (equipmentId != other.equipmentId)
			return false;
		if (pcId != other.pcId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PlayerCharacterEquipmentKey [pcId=" + pcId + ", equipmentId=" + equipmentId + "]";
	}
 
	 
}
