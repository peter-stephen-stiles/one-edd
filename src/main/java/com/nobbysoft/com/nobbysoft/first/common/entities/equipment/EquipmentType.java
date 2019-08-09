package com.nobbysoft.com.nobbysoft.first.common.entities.equipment;

public enum EquipmentType {
	MELEE_WEAPON(EquipmentWhere.HAND_L,EquipmentWhere.HAND_R,EquipmentWhere.PACK,EquipmentWhere.OTHER_OR_NOT),
	AMMUNITION(EquipmentWhere.PACK,EquipmentWhere.OTHER_OR_NOT),
	WEAPON_RANGED(EquipmentWhere.HAND_L,EquipmentWhere.HAND_R,EquipmentWhere.PACK,EquipmentWhere.OTHER_OR_NOT),
	SHIELD(EquipmentWhere.HAND_L,EquipmentWhere.HAND_R,EquipmentWhere.PACK,EquipmentWhere.OTHER_OR_NOT),
	ARMOUR(EquipmentWhere.TORSO,EquipmentWhere.PACK,EquipmentWhere.OTHER_OR_NOT),
	ARMOUR_BONUS_GIVING(),  // don't specify so can go anywhere
	OTHER() // don't specify so can go anywhere
	;
	
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
	}
	
}
