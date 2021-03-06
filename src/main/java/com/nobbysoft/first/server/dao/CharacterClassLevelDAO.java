package com.nobbysoft.first.server.dao;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.common.entities.DTORowListener;
import com.nobbysoft.first.common.entities.equipment.Armour;
import com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.first.common.entities.staticdto.*;
import com.nobbysoft.first.common.exceptions.RecordNotFoundException;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.utils.DbUtils;

public class CharacterClassLevelDAO extends AbstractDAO<CharacterClassLevel, CharacterClassLevelKey> implements DAOI<CharacterClassLevel, CharacterClassLevelKey> {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	public CharacterClassLevelDAO() {
	}
 
	private String tableName = "Character_Class_Level";
	@Override
	public void createTable(Connection con) throws SQLException {
		/**
		 * private String classId; private String name; private boolean
		 * hasMagicDefenceBonus;
		 */
		String sql = "CREATE TABLE " + tableName +
				 "(class_id varchar(20), "+			
				" level INTEGER, "
				+ "PRIMARY KEY (class_id,level) " + 
				"  )";

		if (DbUtils.doesTableExist(con, tableName)) {

		} else {
			try (PreparedStatement ps = con.prepareStatement(sql);) {
				ps.execute();
			} catch (Exception ex) {
				LOGGER.error("sql\n\n"+sql);
				throw ex;
			}
		}

 
		{
			VC[] newStrings = new VC[] {
					new VC("level_title",30),
					new VC("notes",4000)
			};
			DAOUtils.createStrings(con, tableName, newStrings);
		}
		
		{
			String[] newInts = new String[] {
					"from_xp",
					"to_xp"
			};
			DAOUtils.createInts(con, tableName, newInts);
		}
		{
			String[] newBooleans = new String[] {
					"name_Level"
			};
			DAOUtils.createBooleans(con, tableName, newBooleans);
		}
	}

	
	
	public void createConstraints(Connection con) throws SQLException{
		{
			String sql;
//			CONSTRAINT FLTS_FK FOREIGN KEY (FLIGHT_ID, SEGMENT_NUMBER)  			REFERENCES Flights (FLIGHT_ID, SEGMENT_NUMBER)			
					// add some constraints
					String column = "class_id";
					String constraintName = "CCL_class_to_class";
					if (!DbUtils.isTableColumnFK(con, tableName, column, "character_class")) {
						sql = "ALTER TABLE " + tableName + " add constraint "+ constraintName +" foreign key (" + column
								+ ") references Character_Class(class_id) ";
						try (PreparedStatement ps = con.prepareStatement(sql);) {
							ps.execute();
						} catch (Exception ex) {
							LOGGER.error("Error running SQL\n"+sql,ex);
							throw ex;
						}
					}
					
		 
				}
	};
	
	@Override
	String[] getKeys() {
		return new String[] {"class_id","level"};
	}

	@Override
	String[] getData() { 
		return new String[] {"level_Title",
				"from_Xp",
				"to_Xp",
				"notes",
				"name_Level"};
	}
	
	
	@Override
	CharacterClassLevel dtoFromRS(ResultSet rs) throws SQLException {
		CharacterClassLevel dto = new CharacterClassLevel();
		int col=1;
		dto.setClassId(rs.getString(col++));
		dto.setLevel(rs.getInt(col++));
		dto.setLevelTitle(rs.getString(col++));
		dto.setFromXp(rs.getInt(col++));
		dto.setToXp(rs.getInt(col++));
		dto.setNotes(rs.getString(col++));
		dto.setNameLevel(rs.getBoolean(col++));		
		return dto;
	}

	@Override
	void setPSFromKey(PreparedStatement ps, CharacterClassLevelKey key) throws SQLException {
		ps.setString(1, key.getClassId());
		ps.setInt(2, key.getLevel());
		
	}


	@Override
	String getTableName() { 
		return tableName;
	}

	@Override
	String addOrderByClause(String sql) {

		return sql+" ORDER BY class_id,level";
	}

	@Override
	String getFilterWhere() {
		return "  class_id = ? ";
	}

	@Override
	void setFilterParameters(PreparedStatement ps, String filter) throws SQLException {
		ps.setString(1, filter);
	}

	@Override
	int setPSFromKeys(CharacterClassLevel value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getClassId());
		ps.setInt(col++, value.getLevel());
		return col;
	}

	/*
	String class_Id; 
	int level;
	
	String level_Title;
	int from_Xp;
	int to_Xp;
	String notes;
	boolean name_Level; 
	 */	
	
	@Override
	int setPSFromData(CharacterClassLevel value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getLevelTitle());
		ps.setInt(col++, value.getFromXp());
		ps.setInt(col++, value.getToXp());
		ps.setString(col++, value.getNotes());
		ps.setBoolean(col++, value.isNameLevel());
		return col;
	}

	@Override
	String getSELECTForCodedList() {

		return "SELECT class_id,level,class_id|| ' - ' ||level||' - '||levelTitle";
	}

	@Override
	void setCodedListItemFromRS(CodedListItem<CharacterClassLevelKey> dto, ResultSet rs) throws SQLException {
		CharacterClassLevelKey key = new CharacterClassLevelKey();
		key.setClassId(rs.getString(1));
		key.setLevel(rs.getInt(2));
		String de = rs.getString(3);
		dto.setItem(key);
		dto.setDescription(de);
		
	}

 
	
	public int getMaxLevelInTable(Connection con,String characterClassId) throws SQLException {
		
		String sql = "SELECT MAX(level) FROM "+tableName+" WHERE class_id = ?";
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.setString(1, characterClassId);
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					return rs.getInt(1);
				} else {
					return 0;
				}
			}
		}
		
	}
	
	public CharacterClassLevel getNameLevelInTable(Connection con,String characterClassId) throws SQLException {
		
		String sql = "SELECT MAX(level) FROM "+tableName+" WHERE class_id = ? and name_Level = TRUE";
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.setString(1, characterClassId);
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					int level = rs.getInt(1);
					if(level<1) {
						return null;
					}
					CharacterClassLevelKey key = new CharacterClassLevelKey();
					key.setClassId(characterClassId);
					key.setLevel(level);
					return get(con,key);
				} else {
					return null;
				}
			}
		}
		
	}
	

	public int getMaxAllowedLevel(Connection con,
			int pcId, 
			String characterClassId) throws SQLException{
		PlayerCharacterDAO pcDAO = new PlayerCharacterDAO();
		RaceDAO raceDAO = new RaceDAO();
		RaceClassLimitDAO rclDAO = new RaceClassLimitDAO(); 
		
		PlayerCharacter pc = pcDAO.get(con, pcId);
		Race race = raceDAO.get(con, pc.getRaceId());
		RaceClassLimitKey rclKey = new RaceClassLimitKey();
		rclKey.setClassId(characterClassId);
		rclKey.setRaceId(race.getRaceId());
		RaceClassLimit raceClassLimit= rclDAO.get(con, rclKey);
		
		
		int limit = raceClassLimit.getMaxLevel();
		// need to codify the ATTRIBUTE that we're comparing against for the max levels...
		if(raceClassLimit.getLimitingAttribute()>0) {
			// need to check against the XX attribute of the PV
			int attValue = pc.getAttributeValue(Attribute.fromIndex(raceClassLimit.getLimitingAttribute()));
			if(attValue<17) {
				 limit = raceClassLimit.getMaxLevelPrLt17();
			} else if(attValue==17){
				limit = raceClassLimit.getMaxLevelPrEq17();
			} 
		}
		
		return  (limit);
		 
	}

	
	public CharacterClassLevel getThisLevel(Connection con,String characterClassId, int level) throws SQLException {

		CharacterClassDAO ccDAO = new CharacterClassDAO (); 
		
		CharacterClass characterClass = ccDAO.get(con, characterClassId);
		
		CharacterClassLevel wanted = null;

		CharacterClassLevelKey key = new CharacterClassLevelKey();		
		key.setClassId(characterClassId);
		key.setLevel(level);
		
		//int ml = getMaxLevelInTable(con,characterClassId);
		//if(ml>level) {		
		//} else {
		//	key.setLevel(ml);
		//}
		CharacterClassLevel tryOne = null;
		try {
			tryOne=get(con,key);
		} catch (RecordNotFoundException ee) {
			// ok, we're allowed to be here
		}
		// gets the level OR the biggest level
		
		CharacterClassLevel nameLevel = getNameLevelInTable(con,characterClassId);
		
		if(nameLevel==null ) {
			// no name level, so we have to have the right level or else we can't do jack!
			// at this point if tryOne isn't null we've got an error :O
			return tryOne;
		} else {
			// there is a name level
			if(level<nameLevel.getLevel()) {
				return tryOne;
			} else {
				// need to make up a level!				
				int diff = level - nameLevel.getLevel(); 
				CharacterClassLevel characterClasslevel = new CharacterClassLevel();
				int fromXp = nameLevel.getFromXp()+ diff * characterClass.getXpPerLevelAfterNameLevel();
				int toXp = nameLevel.getToXp()+ diff * characterClass.getXpPerLevelAfterNameLevel();
				characterClasslevel.setClassId(characterClassId);
				characterClasslevel.setLevel(level);
				characterClasslevel.setLevelTitle(nameLevel.getLevelTitle()+ "("+level+"th level)");
				characterClasslevel.setNameLevel(false);
				characterClasslevel.setNotes("");
				characterClasslevel.setFromXp(fromXp);
				characterClasslevel.setToXp(toXp);
				return characterClasslevel; 
			}
		}		
	}
	
}
