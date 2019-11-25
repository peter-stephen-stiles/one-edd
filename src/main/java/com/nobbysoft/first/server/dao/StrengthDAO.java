package com.nobbysoft.first.server.dao;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.common.entities.staticdto.attributes.Strength;
import com.nobbysoft.first.common.entities.staticdto.attributes.StrengthKey;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.utils.DbUtils;

public class StrengthDAO extends AbstractDAO<Strength, StrengthKey> implements DAOI<Strength, StrengthKey> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private String tableName = "Strength"; 

	public StrengthDAO() {

	}

	@Override
	public void createTable(Connection con) throws SQLException {

		String sql = "CREATE TABLE " + tableName + " (ability_score integer, exceptional_strength integer,  " + "PRIMARY KEY (ability_score,exceptional_strength)  )";

		if (DbUtils.doesTableExist(con, tableName)) {

		} else {
			try (PreparedStatement ps = con.prepareStatement(sql);) {
				ps.execute();
			}
		}

		String[] newInts = new String[] { "hitProbability",
				"damageAdjustment",
				"weightAllowance",
				"openDoors",
				"openMagicalDoors",
				"bendBarsLiftGates",
				"exceptionalStrengthTo"};
		DAOUtils.createInts(con, tableName, newInts);

	}

	private String[] keys = new String[] { "ability_score","exceptional_strength" };
	private String[] data = new String[] {"exceptionalStrengthTo",
			"hitProbability",
			"damageAdjustment",
			"weightAllowance",
			"openDoors",
			"openMagicalDoors",
			"bendBarsLiftGates"
			};

	String[] getKeys() {
		return keys;
	}

	String[] getData() {
		return data;
	}

	String addOrderByClause(String sql) {
		sql = sql + "ORDER BY ability_score, exceptional_strength ";
		return sql;
	}


	Strength dtoFromRS(ResultSet rs) throws SQLException {
		Strength dto = new Strength();
		int col = 1;
		dto.setAbilityScore(rs.getInt(col++)); 
		dto.setExceptionalStrength(rs.getInt(col++)); 
		dto.setExceptionalStrengthTo(rs.getInt(col++)); 
		dto.setHitProbability(rs.getInt(col++)); 
		dto.setDamageAdjustment(rs.getInt(col++)); 
		dto.setWeightAllowance(rs.getInt(col++)); 
		dto.setOpenDoors(rs.getInt(col++)); 
		dto.setOpenMagicalDoors(rs.getInt(col++)); 
		dto.setBendBarsLiftGates(rs.getInt(col++)); 
		
		return dto;
	}

	void setPSFromKey(PreparedStatement ps, StrengthKey key) throws SQLException {
		ps.setInt(1, key.getAbilityScore());
		ps.setInt(2, key.getExceptionalStrength());
	}

	String getTableName() {
		return tableName;
	}

	int setPSFromKeys(Strength value, PreparedStatement ps, int col) throws SQLException {
		ps.setInt(col++, value.getAbilityScore());
		ps.setInt(col++, value.getExceptionalStrength());
		return col;
	}
	


	int setPSFromData(Strength value, PreparedStatement ps, int col) throws SQLException {

		ps.setInt(col++, value.getExceptionalStrengthTo());
		ps.setInt(col++, value.getHitProbability());
		ps.setInt(col++, value.getDamageAdjustment());
		ps.setInt(col++, value.getWeightAllowance());
		ps.setInt(col++, value.getOpenDoors());
		ps.setInt(col++, value.getOpenMagicalDoors());
		ps.setInt(col++, value.getBendBarsLiftGates());
		return col;
	}

	/*
	 * 	/***
	 * "hitProbability",
"damageAdjustment",
"weightAllowance",
"openDoors",
"openMagicalDoors",
"bendBarsLiftGates"
	 */ 
	
	String getSELECTForCodedList() {
		return "SELECT ability_score, exceptional_strength, ability_score FROM " + getTableName() + " ORDER BY ability_score, exceptional_strength";
	}

	void setCodedListItemFromRS(CodedListItem<StrengthKey> dto, ResultSet rs) throws SQLException {
		dto.setItem(new StrengthKey(rs.getInt(1),rs.getInt(2)));
		dto.setDescription(rs.getString(3));
	}

	String getFilterWhere() {
		return " ability_score >= ? and ability_score <= ?";
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

	
	public Strength getStrengthFor(Connection con,int strength, int exceptional) throws SQLException{
		//
		if(strength!=18) {
			StrengthKey key = new  StrengthKey (strength,0);
			return get(con,key);
		} else  {
			int ex = 0;
			String sql = "SELECT max(exceptional_strength) FROM strength WHERE ability_score = ? and exceptional_strength <= ? ";
			try(PreparedStatement ps = con.prepareStatement(sql)){
				ps.setInt(1, strength);
				ps.setInt(2, exceptional);
				try(ResultSet rs = ps.executeQuery()){
					if(rs.next()) {
						ex = rs.getInt(1);
					}
					
				}
				
			}
			
			StrengthKey key = new  StrengthKey (strength,ex);
			return get(con,key);
			
		}
		
	}
	

}
