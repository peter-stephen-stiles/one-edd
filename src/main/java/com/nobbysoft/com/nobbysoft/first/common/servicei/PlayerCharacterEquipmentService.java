package com.nobbysoft.com.nobbysoft.first.common.servicei;

import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.EquipmentI;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.EquipmentType;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.EquipmentWhere;
import com.nobbysoft.com.nobbysoft.first.common.entities.pc.*;
import com.nobbysoft.com.nobbysoft.first.common.views.ViewPlayerCharacterEquipment; 

public interface PlayerCharacterEquipmentService extends DataServiceI<PlayerCharacterEquipment,PlayerCharacterEquipmentKey>{
 
	public int getNextId() throws SQLException;
	
	public List<ViewPlayerCharacterEquipment> getForPC(int pcId) throws SQLException;
	
	public EquipmentI getUnderlyingEquipment(String code, EquipmentType type) throws SQLException;
	

	public void equip(PlayerCharacterEquipment equipment,EquipmentWhere where) throws SQLException;
	
}
