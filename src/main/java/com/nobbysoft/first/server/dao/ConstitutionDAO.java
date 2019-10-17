package com.nobbysoft.first.server.dao;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.common.entities.staticdto.attributes.Constitution;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.utils.DbUtils;

public class ConstitutionDAO extends AbstractDAO<Constitution, Integer> implements DAOI<Constitution, Integer> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private String tableName = "Constitution"; 

	public ConstitutionDAO() {

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

		String[] newInts = new String[] { "hit_Point_Adjustment", "hit_Point_Adjustment_High", "system_Shock_Survival",
				"resurrection_Survival","divine_Spell_Chance_Failure", "divine_Spell_Bonus_Spell_Level","divine_Max_Spell_Level" };
		DAOUtils.createInts(con, tableName, newInts);

	}

	private String[] keys = new String[] { "ability_score" };
	private String[] data = new String[] { "hit_Point_Adjustment", "hit_Point_Adjustment_High", "system_Shock_Survival",
			"resurrection_Survival","divine_Spell_Chance_Failure", "divine_Spell_Bonus_Spell_Level","divine_Max_Spell_Level" };

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

	Constitution dtoFromRS(ResultSet rs) throws SQLException {
		Constitution dto = new Constitution();
		int col = 1;
		dto.setAbilityScore(rs.getInt(col++));
		dto.setHitPointAdjustment(rs.getInt(col++));
		dto.setHitPointAdjustmentHigh(rs.getInt(col++));
		dto.setSystemShockSurvival(rs.getInt(col++));
		dto.setResurrectionSurvival(rs.getInt(col++));
		dto.setDivineSpellChanceFailure(rs.getInt(col++));
		dto.setDivineSpellBonusSpellLevel(rs.getInt(col++));
		dto.setDivineMaxSpellLevel(rs.getInt(col++));
		return dto;
	}

	void setPSFromKey(PreparedStatement ps, Integer key) throws SQLException {
		ps.setInt(1, key);
	}

	String getTableName() {
		return tableName;
	}

	int setPSFromKeys(Constitution value, PreparedStatement ps, int col) throws SQLException {
		ps.setInt(col++, value.getAbilityScore());
		return col;
	}

	int setPSFromData(Constitution value, PreparedStatement ps, int col) throws SQLException {
		ps.setInt(col++, value.getHitPointAdjustment());
		ps.setInt(col++, value.getHitPointAdjustmentHigh());
		ps.setInt(col++, value.getSystemShockSurvival());
		ps.setInt(col++, value.getResurrectionSurvival());
		ps.setInt(col++, value.getDivineSpellChanceFailure());
		ps.setInt(col++, value.getDivineSpellBonusSpellLevel());
		ps.setInt(col++, value.getDivineMaxSpellLevel());
		
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
