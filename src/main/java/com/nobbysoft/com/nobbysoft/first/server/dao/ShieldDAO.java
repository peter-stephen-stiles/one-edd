package com.nobbysoft.com.nobbysoft.first.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.Shield;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.BULK;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.EquipmentHands;
import com.nobbysoft.com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.com.nobbysoft.first.server.utils.DbUtils; 

public class ShieldDAO extends AbstractDAO<Shield,String>
implements DAOI<Shield, String> {

	public ShieldDAO() { 
	}

	private String tableName = "Shield";
	
	@Override
	public void createTable(Connection con) throws SQLException {
		String sql = "CREATE TABLE " + tableName + " (code varchar(20), PRIMARY KEY (code)  )"; 

		if (DbUtils.doesTableExist(con, tableName)) {

		} else {
			try (PreparedStatement ps = con.prepareStatement(sql);) {
				ps.execute();
			}
		}
		
		/*
	private String code;
	private String name;
	private int capacityGP;
	private int encumberanceGP;	
	private int magicBonus;	
	private int attacksPerRound;		 
		 */
		
		{
		String[] newInts = new String[] { 
				"capacity_GP",
				"encumberance_GP",
				"magic_bonus",
				"attacks_per_round", 
				};
		DAOUtils.createInts(con, tableName, newInts);
		}
		{	
		VC[] newStrings = new VC[] { 
				new VC("name",60,false),
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
			"attacks_per_round",
			};
	
	
	@Override
	Shield dtoFromRS(ResultSet rs) throws SQLException {
		Shield dto = new Shield();
		int col=1;
		dto.setCode(rs.getString(col++));
		dto.setName(rs.getString(col++));
		dto.setCapacityGP(rs.getInt(col++));
		dto.setEncumberanceGP(rs.getInt(col++));
		dto.setMagicBonus(rs.getInt(col++));
		dto.setAttacksPerRound(rs.getInt(col++));
		
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
	int setPSFromKeys(Shield value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getCode());
		return col;
	}

	@Override
	int setPSFromData(Shield value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getName()); 

		ps.setInt(col++, value.getCapacityGP()); 
		ps.setInt(col++, value.getEncumberanceGP());
		ps.setInt(col++, value.getMagicBonus());  
		ps.setInt(col++, value.getAttacksPerRound());
		return col;
	}

	@Override
	String getSELECTForCodedList() {
		
		return "SELECT code,name, magic_bonus FROM " + tableName + " ORDER BY name";
	}

	@Override
	void setCodedListItemFromRS(CodedListItem<String> dto, ResultSet rs) throws SQLException {
		dto.setItem(rs.getString(1));
		String desc  =rs.getString(2);
		int ma= rs.getInt(3);
		if(ma>0) {
			// magic +
			dto.setDescription(desc + " (+"+(ma)+")");
		} else if(ma<0) {
			// cursed - 
			dto.setDescription(desc + " ("+(ma)+")");
		} else {
			// no maj :)
			dto.setDescription(desc);
		}
	}

}
