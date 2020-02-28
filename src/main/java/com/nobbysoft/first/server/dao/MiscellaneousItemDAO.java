package com.nobbysoft.first.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.nobbysoft.first.common.entities.equipment.EquipmentHands;
import com.nobbysoft.first.common.entities.equipment.EquipmentType;
import com.nobbysoft.first.common.entities.equipment.MiscellaneousItem;
import com.nobbysoft.first.common.utils.COINAGE;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.utils.DbUtils; 

public class MiscellaneousItemDAO extends EquipmentDAO<MiscellaneousItem,String>
implements DAOI<MiscellaneousItem, String> {


	
	

	public String getEquipmentTypeString() {
		return EquipmentType.MISCELLANEOUS_ITEM.toString();
	}
	
/*
	private String code;
	private String name; 
	private int encumberanceGP;
	private int capacityGP;	
	private String definition;
	private COINAGE valueCoin;
	private int value;
 */
	public MiscellaneousItemDAO() { 
	}
	
	private String tableName = "miscellaneous_item";
	
	@Override
	public void createTable(Connection con) throws SQLException {
		String sql = "CREATE TABLE " + tableName + " (code varchar(20), PRIMARY KEY (code)  )"; 

		if (DbUtils.doesTableExist(con, tableName)) {

		} else {
			try (PreparedStatement ps = con.prepareStatement(sql);) {
				ps.execute();
			}
		}
		
		{
		String[] newInts = new String[] {
				"encumberance_GP",
				"capacity_GP",
				"value"
				};
		DAOUtils.createInts(con, tableName, newInts);
		}
		{	
		VC[] newStrings = new VC[] { 
				new VC("name",60,false),
				new VC("definition",255),
				new VC("value_coin",20),				
				};		
		DAOUtils.createStrings(con, tableName, newStrings);
		}
		{
		String[] newBoos = new String[] { 
				//"capable_Of_Disarming_Against_AC8",
				};
		DAOUtils.createBooleans(con, tableName, newBoos);
		}
	}

	private String[] keys = new String[] {"code"};
	private String[] data = new String[] {
			"name",
			"definition",
			"encumberance_GP",
			"capacity_GP",
			"value_coin",
			"value",			
			};
	
	
	@Override
	MiscellaneousItem dtoFromRS(ResultSet rs) throws SQLException {
		MiscellaneousItem dto = new MiscellaneousItem();
		int col=1;
		dto.setCode(rs.getString(col++));
		dto.setName(rs.getString(col++));		
		dto.setDefinition(rs.getString(col++));		
		dto.setEncumberanceGP(rs.getInt(col++));
		dto.setCapacityGP(rs.getInt(col++));
		dto.setValueCoinFromString(rs.getString(col++));
		dto.setValue(rs.getInt(col++));

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
		return sql+" ORDER BY name";
	}

	@Override
	String getFilterWhere() {
		return " upper(name) like ? or code like ?";
	}

	@Override
	void setFilterParameters(PreparedStatement ps, String filter) throws SQLException {
		String f = filter.toUpperCase();
		ps.setString(1, "%"+f+"%");
		ps.setString(2, "%"+f+"%");
	}

	@Override
	int setPSFromKeys(MiscellaneousItem value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getCode());
		return col;
	}

	
	@Override
	int setPSFromData(MiscellaneousItem value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getName()); 
		ps.setString(col++, value.getDefinition()); 		
		ps.setInt(col++, value.getEncumberanceGP());
		ps.setInt(col++, value.getCapacityGP()); 
		ps.setString(col++, value.getValueCoinToString());
		ps.setInt(col++, value.getValue());

		return col;
	}

	@Override
	String getSELECTForCodedList() {
		
		return "SELECT code,name  FROM " + tableName + " ORDER BY name";
	}

	@Override
	void setCodedListItemFromRS(CodedListItem<String> dto, ResultSet rs) throws SQLException {
		dto.setItem(rs.getString(1));
		String desc  =rs.getString(2);
		dto.setDescription(desc );
	}

}
