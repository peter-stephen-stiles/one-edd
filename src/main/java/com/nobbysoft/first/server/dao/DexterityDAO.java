package com.nobbysoft.first.server.dao;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.common.entities.staticdto.attributes.Dexterity;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.utils.DbUtils;

public class DexterityDAO extends AbstractDAO<Dexterity, Integer> implements DAOI<Dexterity, Integer> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private String tableName = "Dexterity"; 

	public DexterityDAO() {

	}

	@Override
	public void createTable(Connection con) throws SQLException {

		String sql = "CREATE TABLE " + tableName + " (ability_score integer,  "
				+ "" + "PRIMARY KEY (ability_score)  )";

		if (DbUtils.doesTableExist(con, tableName)) {

		} else {
			try (PreparedStatement ps = con.prepareStatement(sql);) {
				ps.execute();
			}
		}

		String[] newInts = new String[] { 
				"reaction_attack_adjustment",
				"defensive_adjustment",
				"pick_pockets",
				"open_locks",
				"locate_remove_traps",
				"move_silently",
				"hide_in_shadows"
				};
		DAOUtils.createInts(con, tableName, newInts);

	}

	private String[] keys = new String[] { "ability_score" };
	private String[] data = new String[] { 
			"reaction_attack_adjustment",
			"defensive_adjustment",
			"pick_pockets",
			"open_locks",
			"locate_remove_traps",
			"move_silently",
			"hide_in_shadows"
			};

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

	Dexterity dtoFromRS(ResultSet rs) throws SQLException {
		Dexterity dto = new Dexterity();
		int col = 1;
		dto.setAbilityScore(rs.getInt(col++));
		dto.setReactionAttackAdjustment(rs.getInt(col++));
		dto.setDefensiveAdjustment(rs.getInt(col++));
		dto.setPickPockets(rs.getInt(col++));
		dto.setOpenLocks(rs.getInt(col++));
		dto.setLocateRemoveTraps(rs.getInt(col++));
		dto.setMoveSilently(rs.getInt(col++));
		dto.setHideInShadows(rs.getInt(col++));
		return dto;
	}

	void setPSFromKey(PreparedStatement ps, Integer key) throws SQLException {
		ps.setInt(1, key);
	}

	String getTableName() {
		return tableName;
	}

	int setPSFromKeys(Dexterity value, PreparedStatement ps, int col) throws SQLException {
		ps.setInt(col++, value.getAbilityScore());
		return col;
	}


	
	int setPSFromData(Dexterity value, PreparedStatement ps, int col) throws SQLException {
		ps.setInt(col++, value.getReactionAttackAdjustment()); 
		ps.setInt(col++, value.getDefensiveAdjustment()); 
		ps.setInt(col++, value.getPickPockets()); 
		ps.setInt(col++, value.getOpenLocks()); 
		ps.setInt(col++, value.getLocateRemoveTraps()); 
		ps.setInt(col++, value.getMoveSilently()); 
		ps.setInt(col++, value.getHideInShadows()); 
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
