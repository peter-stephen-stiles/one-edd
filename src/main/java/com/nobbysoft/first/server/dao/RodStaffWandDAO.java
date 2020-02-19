package com.nobbysoft.first.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.nobbysoft.first.common.entities.equipment.EquipmentHands;
import com.nobbysoft.first.common.entities.equipment.EquipmentType;
import com.nobbysoft.first.common.entities.equipment.RodStaffWand;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.utils.DbUtils; 

public class RodStaffWandDAO extends EquipmentDAO<RodStaffWand,String>
implements DAOI<RodStaffWand, String> {



	public String getEquipmentTypeString() {
		return EquipmentType.ROD_STAFF_WAND.toString();
	}
	
	
	public RodStaffWandDAO() { 
	}

	private String tableName = "rod_staff_wand";
	
/**
	private String code;
	private String name;
	private String rodStaffOrWand;
	private int encumberanceGP;
	private String definition;
	private EquipmentHands requiresHands; 	
 */
	
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
				"encumberance_GP"
				};
		DAOUtils.createInts(con, tableName, newInts);
		}
		{	
		VC[] newStrings = new VC[] { 
				new VC("name",60,false),
				new VC("requires_hands",20),
				new VC("rod_staff_or_wand",20),
				new VC("definition",2000),
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
			"encumberance_GP",
			"requires_Hands",
			"rod_staff_or_wand",
			"definition"
			};
	
	
	@Override
	RodStaffWand dtoFromRS(ResultSet rs) throws SQLException {
		RodStaffWand dto = new RodStaffWand();
		int col=1;
		dto.setCode(rs.getString(col++));
		dto.setName(rs.getString(col++));		
		dto.setEncumberanceGP(rs.getInt(col++));		
		dto.setRequiresHands(EquipmentHands.valueOf(rs.getString(col++)));
		dto.setRodStaffOrWand(rs.getString(col++));
		dto.setDefinition(rs.getString(col++));

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
	int setPSFromKeys(RodStaffWand value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getCode());
		return col;
	}

/*
 		dto.setCode(rs.getString(col++));
		dto.setName(rs.getString(col++));		
		dto.setEncumberanceGP(rs.getInt(col++));		
		dto.setRequiresHands(EquipmentHands.valueOf(rs.getString(col++)));
		dto.setRodStaffOrWand(rs.getString(col++));
		dto.setDefinition(rs.getString(col++)); 	
 */
	
	@Override
	int setPSFromData(RodStaffWand value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getName()); 
		ps.setInt(col++, value.getEncumberanceGP());		 
		ps.setString(col++, value.getRequiresHands().name());
		ps.setString(col++, value.getRodStaffOrWand());
		ps.setString(col++, value.getDefinition());
		return col;
	}

	@Override
	String getSELECTForCodedList() {
		
		return "SELECT code,name FROM " + tableName + " ORDER BY name";
	}

	@Override
	void setCodedListItemFromRS(CodedListItem<String> dto, ResultSet rs) throws SQLException {
		dto.setItem(rs.getString(1));
		String desc  =rs.getString(2); 
		dto.setDescription(desc);
	}

}
