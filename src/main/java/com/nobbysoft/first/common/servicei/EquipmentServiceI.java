package com.nobbysoft.first.common.servicei;

import java.sql.SQLException;
import java.util.List;

import com.nobbysoft.first.common.entities.DataDTOInterface;

public interface EquipmentServiceI <T extends DataDTOInterface<K>, K extends Comparable<K>>{

	public List<T> getValidEquipmentForCharactersClasses(int pcId) throws SQLException ;
	 
	
	
}
