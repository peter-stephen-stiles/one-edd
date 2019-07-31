package com.nobbysoft.com.nobbysoft.first.common.servicei;

import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.com.nobbysoft.first.common.entities.pc.*;
import com.nobbysoft.com.nobbysoft.first.common.views.ViewPlayerCharacterEquipment; 

public interface PlayerCharacterEquipmentService extends DataServiceI<PlayerCharacterEquipment,PlayerCharacterEquipmentKey>{
 
	public int getNextId() throws SQLException;
	
	public List<ViewPlayerCharacterEquipment> getForPC(int pcId) throws SQLException;
	
}
