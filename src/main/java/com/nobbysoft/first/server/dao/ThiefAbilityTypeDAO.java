package com.nobbysoft.first.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.nobbysoft.first.common.entities.staticdto.ThiefAbilityType;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.utils.DbUtils;

public class ThiefAbilityTypeDAO extends AbstractDAO<ThiefAbilityType, String>  implements DAOI<ThiefAbilityType, String> {

	public ThiefAbilityTypeDAO() {
	}


	private final String tableName = "Thief_Ability_Type";
	
	@Override
	public void createTable(Connection con) throws SQLException {

		String sql = "CREATE TABLE " + tableName
				+ "(Thief_Ability_Type varchar(20), Thief_Ability_name varchar(256), "
				+ "PRIMARY KEY (Thief_Ability_Type) ," + " UNIQUE  (Thief_Ability_name) )";

		if (!DbUtils.doesTableExist(con, tableName)) {
			try (PreparedStatement ps = con.prepareStatement(sql);) {
				ps.execute();
			}
		}
  
	}

	private String[] keys = new String[] {"Thief_Ability_Type"};
	private String[] data = new String[] {"Thief_Ability_name"};
	
	@Override
	ThiefAbilityType dtoFromRS(ResultSet rs) throws SQLException {
		ThiefAbilityType dto = new ThiefAbilityType();
		int col=1;
		dto.setThiefAbilityType(rs.getString(col++));
		dto.setThiefAbilityName(rs.getString(col++));
		return dto;
	}

	@Override
	void setPSFromKey(PreparedStatement ps, String key) throws SQLException {
		ps.setString(1, key);
		
	}

	@Override
	String[] getKeys() {
		return keys;
	}

	@Override
	String[] getData() {

		return data;
	}

	@Override
	String getTableName() {
		return tableName;
	}

	@Override
	String addOrderByClause(String sql) {

		return sql+" ORDER BY Thief_Ability_Type";
	}

	@Override
	String getFilterWhere() { 
		return " Thief_Ability_Type like ? OR Thief_Ability_name like ? ";
	}

	@Override
	void setFilterParameters(PreparedStatement ps, String filter) throws SQLException {
		String f = filter;
		if(!filter.startsWith("%")) {
			f ="%"+f;
		}
		if(!filter.endsWith("%")) {
			f = f + "%";
		}
		ps.setString(1, f.toUpperCase()); // code is upper cased
		ps.setString(2, f);
	}

	@Override
	int setPSFromKeys(ThiefAbilityType value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++,value.getThiefAbilityType());
		return col;
	}

	@Override
	int setPSFromData(ThiefAbilityType value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++,value.getThiefAbilityName());
		return col;

	}

	@Override
	String getSELECTForCodedList() {		
		return "SELECT Thief_Ability_Type , Thief_Ability_name from Thief_Ability_Type order by Thief_Ability_Type";
	}

	@Override
	void setCodedListItemFromRS(CodedListItem<String> dto, ResultSet rs) throws SQLException {
		dto.setItem(rs.getString(1));
		dto.setDescription(rs.getString(2));		
	}


}
