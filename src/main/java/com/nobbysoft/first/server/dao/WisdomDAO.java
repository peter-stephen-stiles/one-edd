package com.nobbysoft.first.server.dao;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.common.entities.staticdto.attributes.Wisdom;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.utils.DbUtils;

public class WisdomDAO extends AbstractDAO<Wisdom, Integer> implements DAOI<Wisdom, Integer> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private String tableName = "Wisdom"; 

	public WisdomDAO() {

	}

	@Override
	public void createTable(Connection con) throws SQLException {

		String sql = "CREATE TABLE " + tableName + 
				" (ability_score integer,  " + "PRIMARY KEY (ability_score)  )";

		if (DbUtils.doesTableExist(con, tableName)) {

		} else {
			try (PreparedStatement ps = con.prepareStatement(sql);) {
				ps.execute();
			}
		}

		String[] newInts = new String[] {   
				 "divine_Spell_Chance_Failure", 
				 "divine_Spell_Bonus_Spell_Level",
				 "divine_Max_Spell_Level",
				 "magical_Attack_Adjustment"};
		DAOUtils.createInts(con, tableName, newInts);

	}

	private String[] keys = new String[] { "ability_score" };
	private String[] data = new String[] {  "divine_Spell_Chance_Failure", "divine_Spell_Bonus_Spell_Level","divine_Max_Spell_Level" ,
			 "magical_Attack_Adjustment"};

	String[] getKeys() {
		return keys;
	}

	String[] getData() {
		return data;
	}

	String addOrderByClause(String sql) {
		sql = sql + "ORDER BY ability_score ";
		return sql;
	}

	Wisdom dtoFromRS(ResultSet rs) throws SQLException {
		Wisdom dto = new Wisdom();
		int col = 1;
		dto.setAbilityScore(rs.getInt(col++));
		dto.setDivineSpellChanceFailure(rs.getInt(col++));
		dto.setDivineSpellBonusSpellLevel(rs.getInt(col++));
		dto.setDivineMaxSpellLevel(rs.getInt(col++));
		dto.setMagicalAttackAdjustment(rs.getInt(col++));
		return dto;
	}

	void setPSFromKey(PreparedStatement ps, Integer key) throws SQLException {
		ps.setInt(1, key);
	}

	String getTableName() {
		return tableName;
	}

	int setPSFromKeys(Wisdom value, PreparedStatement ps, int col) throws SQLException {
		ps.setInt(col++, value.getAbilityScore());
		return col;
	}

	int setPSFromData(Wisdom value, PreparedStatement ps, int col) throws SQLException {

		ps.setInt(col++, value.getDivineSpellChanceFailure());
		ps.setInt(col++, value.getDivineSpellBonusSpellLevel());
		ps.setInt(col++, value.getDivineMaxSpellLevel());
		ps.setInt(col++, value.getMagicalAttackAdjustment());
		return col;
	}

	String getSELECTForCodedList() {
		return "SELECT ability_score, ability_score FROM " + getTableName() + " ORDER BY ability_score";
	}

	void setCodedListItemFromRS(CodedListItem<Integer> dto, ResultSet rs) throws SQLException {
		dto.setItem(rs.getInt(1));
		dto.setDescription(rs.getString(2));
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
	
	public Map<Integer,Integer> getBonusSpells(Connection con, int Wisdom) throws SQLException {
		int[] bonuses = new int[10];
		for(int i=0,n=bonuses.length;i<n;i++) {
			bonuses[i]=0;
		}
		Map<Integer,Integer> bonus = new HashMap<>();
		for(int i=3,n = Wisdom;i<=n;i++) {
			Wisdom c= get(con,i);
			int lvl=c.getDivineSpellBonusSpellLevel();
			if(lvl>=0) {
				bonuses[lvl] = bonuses[lvl]+1;
			}
			
		}
		for(int i=1,n=10;i<n;i++) {
			int bon = bonuses[i];
			if(bon>0) {
				bonus.put(i, bon);
			}
		}
		return bonus;	
	}
	
	

}
