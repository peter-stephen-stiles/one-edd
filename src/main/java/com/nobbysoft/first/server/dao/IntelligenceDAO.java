package com.nobbysoft.first.server.dao;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.common.entities.staticdto.attributes.Intelligence;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.utils.DbUtils;

public class IntelligenceDAO extends AbstractDAO<Intelligence, Integer> implements DAOI<Intelligence, Integer> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private String tableName = "Intelligence"; 

	public IntelligenceDAO() {

	}

	@Override
	public void createTable(Connection con) throws SQLException {

		String sql = "CREATE TABLE " + tableName + " (ability_score integer,  " + "PRIMARY KEY (ability_score)  )";

		if (DbUtils.doesTableExist(con, tableName)) {

		} else {
			try (PreparedStatement ps = con.prepareStatement(sql);) {
				ps.execute();
			}
		}

		String[] newInts = new String[] { 
				"possible_Additional_Languages", 
				"chance_To_Know_Spell" , 
				"min_Spells_Per_Level", 
				"max_Spells_Per_Level" };
		DAOUtils.createInts(con, tableName, newInts);

	}

	private String[] keys = new String[] { "ability_score" };
	private String[] data = new String[] { "possible_Additional_Languages", 
			"chance_To_Know_Spell" , 
			"min_Spells_Per_Level", 
			"max_Spells_Per_Level" };

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

	Intelligence dtoFromRS(ResultSet rs) throws SQLException {
		Intelligence dto = new Intelligence();
		int col = 1;
		dto.setAbilityScore(rs.getInt(col++));
		dto.setPossibleAdditionalLanguages(rs.getInt(col++));
		dto.setChanceToKnowSpell(rs.getInt(col++));
		dto.setMinSpellsPerLevel(rs.getInt(col++));
		dto.setMaxSpellsPerLevel(rs.getInt(col++));
		return dto;
	}

	void setPSFromKey(PreparedStatement ps, Integer key) throws SQLException {
		ps.setInt(1, key);
	}

	String getTableName() {
		return tableName;
	}

	int setPSFromKeys(Intelligence value, PreparedStatement ps, int col) throws SQLException {
		ps.setInt(col++, value.getAbilityScore());
		return col;
	}

	int setPSFromData(Intelligence value, PreparedStatement ps, int col) throws SQLException {
		ps.setInt(col++, value.getPossibleAdditionalLanguages());
		ps.setInt(col++, value.getChanceToKnowSpell());
		ps.setInt(col++, value.getMinSpellsPerLevel());
		ps.setInt(col++, value.getMaxSpellsPerLevel());
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

}
