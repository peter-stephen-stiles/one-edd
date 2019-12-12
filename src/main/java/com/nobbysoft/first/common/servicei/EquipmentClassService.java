package com.nobbysoft.first.common.servicei;

import com.nobbysoft.first.common.entities.equipment.EquipmentClass;
import com.nobbysoft.first.common.entities.equipment.EquipmentClassKey;
import com.nobbysoft.first.common.views.ViewClassEquipment;

import java.sql.SQLException;
import java.util.List;

public interface EquipmentClassService extends DataServiceI<EquipmentClass,EquipmentClassKey>{

	
 
	public List<ViewClassEquipment> getViewForClass(String classId) throws SQLException;
	public List<ViewClassEquipment> getViewForEquipmentType(String type) throws SQLException;
	public List<ViewClassEquipment> getViewForEquipment(String type,String code) throws SQLException;
	
	public List<ViewClassEquipment> getViewForClassAll(String classId) throws SQLException;
	
	 
	
}
