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

import com.nobbysoft.first.common.entities.staticdto.ThiefAbility;
import com.nobbysoft.first.common.entities.staticdto.ThiefAbilityKey;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.utils.DbUtils;

public class ThiefAbilityDAO extends AbstractDAO<ThiefAbility, ThiefAbilityKey> implements DAOI<ThiefAbility, ThiefAbilityKey> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private String tableName = "Thief_Ability"; 

	public ThiefAbilityDAO() {

	}
/*
 	private int thiefLevel;
	private String thiefAbilityType;// FK to TheifAbilityType	
	private double percentageChance; 
 */
	@Override
	public void createTable(Connection con) throws SQLException {

		String sql = "CREATE TABLE " + tableName + 
				" (thief_Level integer, thief_Ability_Type varchar(20) ,  " + 
				"PRIMARY KEY (thief_Level, thief_Ability_Type)  )";

		if (DbUtils.doesTableExist(con, tableName)) {

		} else {
			try (PreparedStatement ps = con.prepareStatement(sql);) {
				ps.execute();
			}
		}

		DECIMAL[] newInts = new DECIMAL[] {
				new DECIMAL("percentage_Chance",4,1)}; // one decimal place only
		DAOUtils.createDecimals(con, tableName, newInts);

	}

	private String[] keys = new String[] { "thief_Level","thief_Ability_Type" };
	private String[] data = new String[] {"percentage_Chance"
			};

	String[] getKeys() {
		return keys;
	}

	String[] getData() {
		return data;
	}

	String addOrderByClause(String sql) {
		sql = sql + "ORDER BY thief_Level, thief_Ability_Type ";
		return sql;
	}


	ThiefAbility dtoFromRS(ResultSet rs) throws SQLException {
		ThiefAbility dto = new ThiefAbility();
		int col = 1;
		dto.setThiefLevel(rs.getInt(col++)); 
		dto.setThiefAbilityType(rs.getString(col++)); 
		dto.setPercentageChance(rs.getDouble(col++)); 
		
		return dto;
	}

	void setPSFromKey(PreparedStatement ps, ThiefAbilityKey key) throws SQLException {
		ps.setInt(1, key.getThiefLevel());
		ps.setString(2, key.getThiefAbilityType());
	}

	String getTableName() {
		return tableName;
	}

	int setPSFromKeys(ThiefAbility value, PreparedStatement ps, int col) throws SQLException {
		ps.setInt(col++, value.getThiefLevel());
		ps.setString(col++, value.getThiefAbilityType());
		return col;
	}
	


	int setPSFromData(ThiefAbility value, PreparedStatement ps, int col) throws SQLException {
		ps.setDouble(col++, value.getPercentageChance());
		return col;
	}


	
	String getSELECTForCodedList() {
		return "SELECT thief_Level, thief_Ability_Type, percentage_Chance FROM " + getTableName() + " ORDER BY thief_Level, thief_Ability_Type";
	}

	void setCodedListItemFromRS(CodedListItem<ThiefAbilityKey> dto, ResultSet rs) throws SQLException {
		dto.setItem(new ThiefAbilityKey(rs.getInt(1),rs.getString(2)));
		dto.setDescription(rs.getString(3));
	}

	String getFilterWhere() {
		return " ?  >= thief_Level AND  ? <= thief_Level ";
	};

	void setFilterParameters(PreparedStatement ps, String filter) throws SQLException {
		try {
			int score = Integer.parseInt(filter);
			ps.setInt(1, score);
			ps.setInt(2, score);
		} catch (Exception ex) {
			ps.setInt(1, 0);
			ps.setInt(1, 99);
		}
	}

	
	public List<ThiefAbility> getListForThiefLevel(Connection con,int level) throws SQLException {
		List<ThiefAbility> list = null;
		
		String sql = "SELECT MAX(thief_level) FROM "+tableName+" WHERE thief_level <= ?";
		try(PreparedStatement ps =con.prepareStatement(sql)){
			ps.setInt(1, level);
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					int levelToUse = rs.getInt(1);
					String filter= ""+levelToUse;
					list =  getFilteredList(con,filter);
				} else {
					list = new ArrayList<>();
				}
			}
			
		}
		
		
		return list;
		
	}
	

}
