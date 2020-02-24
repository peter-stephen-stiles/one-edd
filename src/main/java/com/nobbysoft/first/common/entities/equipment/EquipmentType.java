package com.nobbysoft.first.common.entities.equipment;

import com.nobbysoft.first.common.utils.SU;

public enum EquipmentType {
	MELEE_WEAPON(EquipmentWhere.HAND_L,EquipmentWhere.HAND_R,EquipmentWhere.PACK,EquipmentWhere.OTHER),
	AMMUNITION(EquipmentWhere.PACK,EquipmentWhere.OTHER),
	WEAPON_RANGED(EquipmentWhere.HAND_L,EquipmentWhere.HAND_R,EquipmentWhere.PACK,EquipmentWhere.OTHER),
	SHIELD(EquipmentWhere.HAND_L,EquipmentWhere.HAND_R,EquipmentWhere.PACK,EquipmentWhere.OTHER),
	ARMOUR(EquipmentWhere.TORSO,EquipmentWhere.PACK,EquipmentWhere.OTHER),
	// NO use MM instead  !MAGIC_RING(EquipmentWhere.FINGERS_R,EquipmentWhere.FINGERS_L,EquipmentWhere.PACK,EquipmentWhere.OTHER),
	ARMOUR_BONUS_GIVING(),  // don't specify so can go anywhere
	MISCELLANEOUS_MAGIC(),  // don't specify so can go anywhere
	ROD_STAFF_WAND(EquipmentWhere.HAND_L,EquipmentWhere.HAND_R,EquipmentWhere.PACK,EquipmentWhere.OTHER),
	OTHER() // don't specify so can go anywhere
	;

	private String description;
	public String getDescription() {
		return description;
	}
	
	public String getDesc() {
		return name();
	}
	private EquipmentWhere[] validWheres;
	public boolean isValidWhere(EquipmentWhere where) {
		if(validWheres.length==0) {
			return true;
		}
		for(EquipmentWhere vw: validWheres) {
			if(vw.equals(where)){
				return true;
			}
		}
		return false;
	}
	public EquipmentWhere[] getValidWheres() {
		return validWheres;
	}
	
	EquipmentType(EquipmentWhere... validWheres){
		this.validWheres = validWheres;

		description = name().replace("_", " ");	
		description=SU.proper(description);		
	}
	
}
