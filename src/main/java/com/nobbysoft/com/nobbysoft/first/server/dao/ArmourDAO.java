package com.nobbysoft.com.nobbysoft.first.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.Armour;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.BULK;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.EquipmentHands;
import com.nobbysoft.com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.com.nobbysoft.first.server.utils.DbUtils; 

public class ArmourDAO extends AbstractDAO<Armour,String>
implements DAOI<Armour, String> {

	public ArmourDAO() { 
	}

	private String tableName = "armour";
	
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
				"capacity_GP",
				"encumberance_GP",
				"magic_bonus",
				"base_ac",
				"base_movement"
				};
		DAOUtils.createInts(con, tableName, newInts);
		}
		{	
		VC[] newStrings = new VC[] { 
				new VC("name",60,false),
				new VC("requires_Hands",20),
				new VC("bulk",20),
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
			"capacity_GP",
			"encumberance_GP",
			"magic_bonus",
			"requires_Hands",
			"base_ac",
			"base_movement",
			"bulk",
			};
	
	
	@Override
	Armour dtoFromRS(ResultSet rs) throws SQLException {
		Armour dto = new Armour();
		int col=1;
		dto.setCode(rs.getString(col++));
		dto.setName(rs.getString(col++));
		dto.setCapacityGP(rs.getInt(col++));
		dto.setEncumberanceGP(rs.getInt(col++));
		dto.setMagicBonus(rs.getInt(col++));
		dto.setRequiresHands(EquipmentHands.valueOf(rs.getString(col++)));
		dto.setBaseAC(rs.getInt(col++));
		dto.setBaseMovement(rs.getInt(col++));
		String b = rs.getString(col++);
		if(b!=null) {
			dto.setBulk(BULK.valueOf(b));
		} else {
			dto.setBulk(null);
		}
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
		return " name like ? or code like ?";
	}

	@Override
	void setFilterParameters(PreparedStatement ps, String filter) throws SQLException {
		String f = filter.toUpperCase();
		ps.setString(1, "%"+filter+"%");
		ps.setString(2, "%"+f+"%");
	}

	@Override
	int setPSFromKeys(Armour value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getCode());
		return col;
	}

	@Override
	int setPSFromData(Armour value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getName()); 

		ps.setInt(col++, value.getCapacityGP()); 
		ps.setInt(col++, value.getEncumberanceGP());
		ps.setInt(col++, value.getMagicBonus());  
		ps.setString(col++, value.getRequiresHands().name());
		ps.setInt(col++, value.getBaseAC());  
		ps.setInt(col++, value.getBaseMovement());
		ps.setString(col++, value.getBulk().name());
		return col;
	}

	@Override
	String getSELECTForCodedList() {
		
		return "SELECT code,name, base_ac,magic_bonus FROM " + tableName + " ORDER BY name";
	}

	@Override
	void setCodedListItemFromRS(CodedListItem<String> dto, ResultSet rs) throws SQLException {
		dto.setItem(rs.getString(1));
		String desc  =rs.getString(2);
		int ba= rs.getInt(3);
		int ma = rs.getInt(4);
		dto.setDescription(desc + " ("+(ba-ma)+")");
	}

}
