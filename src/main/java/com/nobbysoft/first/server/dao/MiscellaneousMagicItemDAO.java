package com.nobbysoft.first.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.nobbysoft.first.common.entities.equipment.EquipmentHands;
import com.nobbysoft.first.common.entities.equipment.EquipmentType;
import com.nobbysoft.first.common.entities.equipment.MiscellaneousMagicItem;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.utils.DbUtils; 

public class MiscellaneousMagicItemDAO extends EquipmentDAO<MiscellaneousMagicItem,String>
implements DAOI<MiscellaneousMagicItem, String> {


	
	

	public String getEquipmentTypeString() {
		return EquipmentType.MISCELLANEOUS_MAGIC.toString();
	}
	
	
	public MiscellaneousMagicItemDAO() { 
	}
	
	private String tableName = "miscellaneous_magic_item";
	
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
				};
		DAOUtils.createInts(con, tableName, newInts);
		}
		{	
		VC[] newStrings = new VC[] { 
				new VC("name",60,false),
				new VC("requires_hands",20),
				new VC("definition",255),
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
			"requires_hands",
			"definition",
			"encumberance_GP",
			};
	
	
	@Override
	MiscellaneousMagicItem dtoFromRS(ResultSet rs) throws SQLException {
		MiscellaneousMagicItem dto = new MiscellaneousMagicItem();
		int col=1;
		dto.setCode(rs.getString(col++));
		dto.setName(rs.getString(col++));
		dto.setRequiresHands(EquipmentHands.valueOf(rs.getString(col++)));
		dto.setDefinition(rs.getString(col++));		
		dto.setEncumberanceGP(rs.getInt(col++));

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
	int setPSFromKeys(MiscellaneousMagicItem value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getCode());
		return col;
	}

	@Override
	int setPSFromData(MiscellaneousMagicItem value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getName()); 
		ps.setString(col++, value.getRequiresHands().name());
		ps.setString(col++, value.getDefinition()); 		
		ps.setInt(col++, value.getEncumberanceGP()); 

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
