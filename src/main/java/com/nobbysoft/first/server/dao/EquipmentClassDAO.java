package com.nobbysoft.first.server.dao;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.common.entities.equipment.EquipmentClass;
import com.nobbysoft.first.common.entities.equipment.EquipmentClassKey;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.common.views.ViewClassEquipment;
import com.nobbysoft.first.server.utils.DbUtils;

public class EquipmentClassDAO extends AbstractDAO<EquipmentClass, EquipmentClassKey>
		implements DAOI<EquipmentClass, EquipmentClassKey> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private String tableName = "equipment_class";

	public EquipmentClassDAO() {

	}

	@Override
	public void createTable(Connection con) throws SQLException {

		String sql = "CREATE TABLE " + tableName + " (type varchar(20), code varchar(20) , class_id varchar(20) ,  "
				+ "PRIMARY KEY (type, code, class_id)  )";

		if (DbUtils.doesTableExist(con, tableName)) {

		} else {
			try (PreparedStatement ps = con.prepareStatement(sql);) {
				ps.execute();
			}
		}

	}

	public void createConstraints(Connection con) throws SQLException {

		String sql;
		{

			// add constraints
			String column = "class_id";
			String constraintName = "ect_to_class";
			if (!DbUtils.isTableColumnFK(con, tableName, column, "character_class")) {
				String otherColumn = "class_id";
				String otherTable = "character_class";
				sql = "ALTER TABLE " + tableName + " add constraint " + constraintName + " foreign key (" + column
						+ ") references " + otherTable + "(" + otherColumn + ") ";
				try (PreparedStatement ps = con.prepareStatement(sql);) {
					ps.execute();
				} catch (Exception ex) {
					LOGGER.error("Error running SQL\n" + sql, ex);
					throw ex;
				}
			}

		}

	};

	private String[] keys = new String[] { "type", "code", "class_id" };
	private String[] data = new String[] {};

	String[] getKeys() {
		return keys;
	}

	String[] getData() {
		return data;
	}

	String addOrderByClause(String sql) {
		sql = sql + "ORDER BY type,code,class_id ";
		return sql;
	}

	EquipmentClass dtoFromRS(ResultSet rs) throws SQLException {
		EquipmentClass dto = new EquipmentClass();
		int col = 1;
		dto.setType(rs.getString(col++));
		dto.setCode(rs.getString(col++));
		dto.setClassId(rs.getString(col++));

		return dto;
	}

	void setPSFromKey(PreparedStatement ps, EquipmentClassKey key) throws SQLException {
		ps.setString(1, key.getType());
		ps.setString(2, key.getCode());
		ps.setString(3, key.getClassId());
	}

	String getTableName() {
		return tableName;
	}

	int setPSFromKeys(EquipmentClass value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getType());
		ps.setString(col++, value.getCode());
		ps.setString(col++, value.getClassId());
		return col;
	}

	int setPSFromData(EquipmentClass value, PreparedStatement ps, int col) throws SQLException {

		return col;
	}

	String getSELECTForCodedList() {
		return "SELECT type,code,class_id FROM " + getTableName() + " ORDER BY type,code,class_id";
	}

	void setCodedListItemFromRS(CodedListItem<EquipmentClassKey> dto, ResultSet rs) throws SQLException {
		EquipmentClassKey eck = new EquipmentClassKey(rs.getString(1), rs.getString(2), rs.getString(3));
		dto.setItem(eck);
		dto.setDescription(eck.toString());
	}

	String getFilterWhere() {
		return " ?  = class_id ";
	};

	void setFilterParameters(PreparedStatement ps, String filter) throws SQLException {

		ps.setString(1, filter);

	}

	
	private String viewSelect = "select type,code,equipment_name,class_id,class_name,true from view_equipment_class ";
	public List<ViewClassEquipment> getViewForClass(Connection con, String classId0 )throws SQLException{
		
		List<ViewClassEquipment> list = new ArrayList<>();
		String sql = viewSelect + " WHERE class_id = ?";
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.setString(1, classId0);
			readView(list, ps);
		}
		
		return list;
		
	}

	private void readView(List<ViewClassEquipment> list, PreparedStatement ps) throws SQLException {
		try(ResultSet rs = ps.executeQuery()){
			while(rs.next()) {
				int col=1;
				String type = rs.getString(col++);
				String code = rs.getString(col++);
				String equipmentName = rs.getString(col++);
				String classId = rs.getString(col++);
				String className = rs.getString(col++);
				boolean assigned=rs.getBoolean(col++);
				
				EquipmentClass equipmentClass = new EquipmentClass();
				equipmentClass.setClassId(classId);
				equipmentClass.setCode(code);
				equipmentClass.setType(type);
				ViewClassEquipment vce = new ViewClassEquipment();
				vce.setClassName(className);
				vce.setEquipmentClass(equipmentClass);
				vce.setEquipmentName(equipmentName);
				vce.setAssigned(assigned);
				list.add(vce);
			}				
		}
	}
	public List<ViewClassEquipment> getViewForEquipmentType(Connection con, String type) throws SQLException{
		List<ViewClassEquipment> list = new ArrayList<>();
		String sql = viewSelect + " WHERE type = ?";
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.setString(1, type);
			readView(list, ps);
		}
		return list;
	}
	public List<ViewClassEquipment> getViewForEquipment(Connection con, String type,String code) throws SQLException{
		List<ViewClassEquipment> list = new ArrayList<>();
		String sql = viewSelect + " WHERE type = ? AND code = ?";
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.setString(1, type);
			ps.setString(2, code);
			readView(list, ps);
		}
		return list;
	}

	
	private String viewSelectAll0 = "select ve.type,ve.code,ve.name as equipment_name, cc.class_id, cc.name as class_name ," + 
			"((select count(*) " + 
			"   from view_equipment_class vec " + 
			" where vec.type = ve.type " + 
			"   and vec.code = ve.code " + 
			"   and vec.equipment_name = ve.name " + 
			"   and vec.class_id = cc.class_id) = 1) as is_there " + 
			" from view_equipment ve " + 
			"cross join character_class cc " + 
			" WHERE cc.class_id = ? ";
	
	public List<ViewClassEquipment> getViewForClassAll(Connection con, String classId0 )throws SQLException{
		
		List<ViewClassEquipment> list = new ArrayList<>();
		{
			String sql = viewSelectAll0;
			try(PreparedStatement ps = con.prepareStatement(sql)){
				ps.setString(1, classId0);
				readView(list, ps);
			}
		}
		
		return list;
		
	}
	 
	
	public ReturnValue<String> updateViewForEquipmentAll(Connection con, String type,String code,List<ViewClassEquipment> list) throws SQLException{

		// remove all for class
		int rc = 0;
		{
			String del = "DELETE FROM "+tableName+" WHERE type = ? AND code = ?";
			
			try(PreparedStatement ps = con.prepareStatement(del)){
					ps.setString(1, type);
					ps.setString(2, code);
					rc = ps.executeUpdate();
				
			}
		}
			int ins =0;
		{
			for(ViewClassEquipment vce: list) {
//				if(!vce.getEquipmentClass().getType().name().equals(type)) {
//					throw new SQLException("Wrong type on "+vce);
//				}
				if(!vce.getEquipmentClass().getCode().equals(code)) {
					throw new SQLException("Wrong code on "+vce);
				}
				if(vce.isAssigned()) {
					insert(con,vce.getEquipmentClass());
					ins++;
				}
			}
		}
		
		String results = "Deleted "+rc+ " inserted "+ins+" valid classes for equipment.";
		// all good :) Do a Billy Joel
		return new ReturnValue<>(results);
	}
	
	public ReturnValue<String> updateViewForClassAll(Connection con, String classId, List<ViewClassEquipment> list )throws SQLException{
		
		// remove all for class
		int rc = 0;
		{
			String del = "DELETE FROM "+tableName+" WHERE class_id = ?";
			
			try(PreparedStatement ps = con.prepareStatement(del)){
					ps.setString(1, classId);
					rc = ps.executeUpdate();
				
			}
		}
			int ins =0;
		{
			for(ViewClassEquipment vce: list) {
				if(!vce.getEquipmentClass().getClassId().equals(classId)) {
					throw new SQLException("Wrong class id on "+vce);
				}
				if(vce.isAssigned()) {
					insert(con,vce.getEquipmentClass());
					ins++;
				}
			}
		}
		
		String results = "Deleted "+rc+ " inserted "+ins+" valid equipment for class .";
		// all good :) Do a Billy Joel
		return new ReturnValue<>(results);
	}
		

	private String viewSelectAll2 = "select ve.type,ve.code,ve.name as equipment_name, cc.class_id, cc.name as class_name ," + 
			"((select count(*) " + 
			"   from view_equipment_class vec " + 
			" where vec.type = ve.type " + 
			"   and vec.code = ve.code " + 
			"   and vec.equipment_name = ve.name " + 
			"   and vec.class_id = cc.class_id) = 1) as is_there " + 
			" from view_equipment ve " + 
			"cross join character_class cc " + 
			" WHERE ve.type= ? "+ 
			"   AND ve.code= ? ";
	
	public List<ViewClassEquipment> getViewForEquipmentAll(Connection con, String type,String code) throws SQLException{
		List<ViewClassEquipment> list = new ArrayList<>();
		String sql = viewSelectAll2;
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.setString(1, type);
			ps.setString(2, code);
			readView(list, ps);
		}
		return list;
	}
	
	
}
