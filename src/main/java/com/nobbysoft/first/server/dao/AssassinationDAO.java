package com.nobbysoft.first.server.dao;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.common.entities.staticdto.Assassination;
import com.nobbysoft.first.common.entities.staticdto.AssassinationKey; 
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.utils.DbUtils;

public class AssassinationDAO extends AbstractDAO<Assassination, AssassinationKey> implements DAOI<Assassination, AssassinationKey> {


	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	public AssassinationDAO() { 
	}

	

	String tableName = "Assassination";

	@Override
	public void createTable(Connection con) throws SQLException {
		String sql = "CREATE TABLE " + tableName
				+ "(assassin_Level integer, victim_Level_from integer, "
				+ "PRIMARY KEY (assassin_Level , victim_Level_from)  )";

		if (DbUtils.doesTableExist(con, tableName)) {

		}  else {
			try (PreparedStatement ps = con.prepareStatement(sql);) {
				ps.execute();
			}
		}
		
 
 
 
		// limitingAttribute limiting_Attribute
		{
			String[] newInts = new String[] {
					"victim_Level_to",
					"percentage_Chance" };
			DAOUtils.createInts(con, tableName, newInts);
		}
		
	}

	
	public void createConstraints(Connection con) throws SQLException{ 		
	}
	


	private String[] keys = new String[] { 
			"assassin_Level",
			"victim_Level_from", };
	
	private String[] data = new String[] {
			"victim_Level_to",
			"percentage_Chance"
			};
	

	String[] getKeys() {
		return keys;
	}

	String[] getData() {
		return data;
	}


	
	String addOrderByClause(String sql) {
		sql = sql + "ORDER BY assassin_Level, victim_Level_from ";
		return sql;
	}


	Assassination dtoFromRS(ResultSet rs) throws SQLException {
		Assassination dto = new Assassination();
		int col = 1;
		dto.setAssassinLevel(rs.getInt(col++));
		dto.setVictimLevelFrom(rs.getInt(col++));  
		dto.setVictimLevelTo(rs.getInt(col++));  
		dto.setPercentageChance(rs.getInt(col++));  
		
		return dto;
	}

	void setPSFromKey(PreparedStatement ps, AssassinationKey key) throws SQLException {
		ps.setInt(1, key.getAssassinLevel());
		ps.setInt(2, key.getVictimLevelFrom());
	}

	String getTableName() {
		return tableName;
	}

	int setPSFromKeys(Assassination value, PreparedStatement ps, int col) throws SQLException {
		ps.setInt(col++, value.getAssassinLevel());
		ps.setInt(col++, value.getVictimLevelFrom());
		return col;
	}
	


	int setPSFromData(Assassination value, PreparedStatement ps, int col) throws SQLException {

		ps.setInt(col++, value.getVictimLevelTo());
		ps.setInt(col++, value.getPercentageChance()); 
		return col;
	}



	
	String getSELECTForCodedList() {
		return "SELECT assassin_Level, victim_Level_from,percentage_Chance FROM " + getTableName() + " ORDER BY assassin_Level, victim_Level_from ";
	}

	void setCodedListItemFromRS(CodedListItem<AssassinationKey> dto, ResultSet rs) throws SQLException {
		dto.setItem(new AssassinationKey(rs.getInt(1),rs.getInt(2)));
		dto.setDescription(rs.getString(3));
	}

	String getFilterWhere() {
		return " ? = assassin_Level  ";
	};

	void setFilterParameters(PreparedStatement ps, String filter) throws SQLException {
		try {
			int score = Integer.parseInt(filter);
			ps.setInt(1, score); 
		} catch (Exception ex) {
			ps.setInt(1, 0);
		}
	}

	
	
	 
}
