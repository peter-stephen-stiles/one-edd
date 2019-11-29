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

import com.nobbysoft.first.common.entities.staticdto.RaceThiefAbilityBonus;
import com.nobbysoft.first.common.entities.staticdto.RaceThiefAbilityBonusKey;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.utils.DbUtils;

public class RaceThiefAbilityBonusDAO extends AbstractDAO<RaceThiefAbilityBonus, RaceThiefAbilityBonusKey> implements DAOI<RaceThiefAbilityBonus, RaceThiefAbilityBonusKey> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private String tableName = "Race_Thief_Ability_Bonus"; 

	public RaceThiefAbilityBonusDAO() {

	}
/*
 	private int thiefLevel;
	private String thiefAbilityType;// FK to TheifAbilityType	
	private double percentageChance; 
 */
	@Override
	public void createTable(Connection con) throws SQLException {

		String sql = "CREATE TABLE " + tableName + 
				" (race_id varchar(20), thief_Ability_Type varchar(20) ,  " + 
				"PRIMARY KEY (race_id, thief_Ability_Type)  )";

		if (DbUtils.doesTableExist(con, tableName)) {

		} else {
			try (PreparedStatement ps = con.prepareStatement(sql);) {
				ps.execute();
			}
		}

		DECIMAL[] newInts = new DECIMAL[] {
				new DECIMAL("percentage_Bonus",4,1)}; // one decimal place only
		DAOUtils.createDecimals(con, tableName, newInts);

	}
	
	
	@Override 
	public void createConstraints(Connection con) throws SQLException{
		String sql;
		{
				String column = "race_id";
				String constraintName = "RTA_race_to_race";
				String otherTable = "Race";
				String otherColumn ="race_id";
				if (!DbUtils.isTableColumnFK(con, tableName, column, "race_id")) {
					if(!DbUtils.isConstraint(con, tableName, constraintName)) {
					sql = "ALTER TABLE " + tableName + " add constraint "+ constraintName +" foreign key (" + column
							+ ") references "+otherTable+"("+otherColumn+") ";
					try (PreparedStatement ps = con.prepareStatement(sql);) {
						ps.execute();
					} catch (Exception ex) {
						LOGGER.error("Error running SQL 1\n"+sql,ex);
						throw ex;
					}
				}
				}
				
	 
			}
		
		{
			String column = "thief_Ability_Type";
			String constraintName = "RTA_ability_to_ability";
			String otherTable = "thief_Ability_Type";
			String otherColumn ="thief_Ability_Type";
			if (!DbUtils.isTableColumnFK(con, tableName, column, "race_id")) {
				if(!DbUtils.isConstraint(con, tableName, constraintName)) {
				sql = "ALTER TABLE " + tableName + " add constraint "+ constraintName +" foreign key (" + column
						+ ") references "+otherTable+"("+otherColumn+") ";
				try (PreparedStatement ps = con.prepareStatement(sql);) {
					ps.execute();
				} catch (Exception ex) {
					LOGGER.error("Error running SQL 1\n"+sql,ex);
					throw ex;
				}
			}
			}
 
		}
		
	}
	

	private String[] keys = new String[] { "race_id","thief_Ability_Type" };
	private String[] data = new String[] {"percentage_Bonus"
			};

	String[] getKeys() {
		return keys;
	}

	String[] getData() {
		return data;
	}

	String addOrderByClause(String sql) {
		sql = sql + "ORDER BY race_id, thief_Ability_Type ";
		return sql;
	}


	RaceThiefAbilityBonus dtoFromRS(ResultSet rs) throws SQLException {
		RaceThiefAbilityBonus dto = new RaceThiefAbilityBonus();
		int col = 1;
		dto.setRaceId(rs.getString(col++)); 
		dto.setThiefAbilityType(rs.getString(col++)); 
		dto.setPercentageBonus(rs.getInt(col++)); 
		
		return dto;
	}

	void setPSFromKey(PreparedStatement ps, RaceThiefAbilityBonusKey key) throws SQLException {
		ps.setString(1, key.getRaceId());
		ps.setString(2, key.getThiefAbilityType());
	}

	String getTableName() {
		return tableName;
	}

	int setPSFromKeys(RaceThiefAbilityBonus value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getRaceId());
		ps.setString(col++, value.getThiefAbilityType());
		return col;
	}
	


	int setPSFromData(RaceThiefAbilityBonus value, PreparedStatement ps, int col) throws SQLException {
		ps.setDouble(col++, value.getPercentageBonus());
		return col;
	}


	
	String getSELECTForCodedList() {
		return "SELECT race_id, thief_Ability_Type, percentage_Bonus FROM " + getTableName() + " ORDER BY rcae_id, thief_Ability_Type";
	}

	void setCodedListItemFromRS(CodedListItem<RaceThiefAbilityBonusKey> dto, ResultSet rs) throws SQLException {
		dto.setItem(new RaceThiefAbilityBonusKey(rs.getString(1),rs.getString(2)));
		dto.setDescription(rs.getString(3));
	}

	private boolean justRace = false; 
	
	String getFilterWhere() {
		if(justRace) {
			return " race_id like ? ";
		} else {
			return " race_id like ? OR thief_Ability_Type like ? ";
		}
	};

	void setFilterParameters(PreparedStatement ps, String filter) throws SQLException {

			
			if(justRace) {
				ps.setString(1, filter);
					
			} else {
				String f = filter;
				if(!filter.startsWith("%")) {
					f = "%"+f;
				}
				if(!filter.endsWith("%")) {
					f = f+"%";
				}
				ps.setString(1, f);
				ps.setString(2, f);
			}
			
			
		 
	}

	public List<RaceThiefAbilityBonus> getBonusesForRace(Connection con, String raceId) throws SQLException {
		List<RaceThiefAbilityBonus> bones = new ArrayList<>();
		try{
			justRace = true;		
			bones=getFilteredList(con, raceId);
		} finally {
			justRace = false;
		}
		return bones;
		
	}
}
