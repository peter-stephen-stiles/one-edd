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

import com.nobbysoft.first.common.entities.staticdto.CharacterClassSkill;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassSkillKey;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.server.utils.DbUtils;

public class CharacterClassSkillDAO extends AbstractDAO<CharacterClassSkill, CharacterClassSkillKey> implements DAOI<CharacterClassSkill, CharacterClassSkillKey> {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	public CharacterClassSkillDAO() {
	}
 
	private String tableName = "Character_Class_Skill";
	@Override
	public void createTable(Connection con) throws SQLException {
		/**
		 * private String classId; private String name; private boolean
		 * hasMagicDefenceBonus;
		 * 
		 *		String sql = "CREATE TABLE " + tableName
				+ "(race_id varchar(20), skill_id varchar(20), skill_name varchar(60), skill_definition varchar(512), "
				+ "PRIMARY KEY (race_id,skill_id)  )";
 
		 * 
		 */
		String sql = "CREATE TABLE " + tableName +
				 "(class_id varchar(20), "+			
					" skill_id varchar(20), " +
					" from_Level INTEGER, skill_name varchar(60), skill_definition varchar(512),"+
				 "PRIMARY KEY (class_id,skill_id) " + 
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
	
//		{
//			String[] newInts = new String[] {"biggest_AC_Hit_By_20"};
//			DAOUtils.createInts(con, tableName, newInts);
//		}

	}

	public void createConstraints(Connection con) throws SQLException{
			String sql; 
			{
				
//				CONSTRAINT FLTS_FK FOREIGN KEY (FLIGHT_ID, SEGMENT_NUMBER)  			REFERENCES Flights (FLIGHT_ID, SEGMENT_NUMBER)			
						// add some constraints
						String column = "class_id";
						String constraintName = "CCL_class_to_skill";
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
			
		}
		
	
	
	@Override
	String[] getKeys() {
		return new String[] {"class_id","skill_id"};
	}

	@Override
	String[] getData() { 
		return new String[] {"from_level","skill_name","skill_definition"};
	}
	
	
	@Override
	CharacterClassSkill dtoFromRS(ResultSet rs) throws SQLException {
		CharacterClassSkill dto = new CharacterClassSkill();
		int col=1;
		dto.setClassId(rs.getString(col++));
		dto.setSkillId(rs.getString(col++));
		dto.setFromLevel(rs.getInt(col++));
		dto.setSkillName(rs.getString(col++));
		dto.setSkillDefinition(rs.getString(col++));
		return dto;
	}

	@Override
	void setPSFromKey(PreparedStatement ps, CharacterClassSkillKey key) throws SQLException {
		ps.setString(1, key.getClassId());
		ps.setString(2, key.getSkillId());		
	}


	@Override
	String getTableName() { 
		return tableName;
	}

	@Override
	String addOrderByClause(String sql) {

		return sql+" ORDER BY class_id,from_level, skill_name";
	}

	@Override
	String getFilterWhere() {
		return "  class_id = ? ";
	}

	/**
	 * filter by class id :) only cheating me
	 */
	@Override
	void setFilterParameters(PreparedStatement ps, String filter) throws SQLException {
		ps.setString(1, filter);
	}

	@Override
	int setPSFromKeys(CharacterClassSkill value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getClassId());
		ps.setString(col++, value.getSkillId());		
		return col;
	}

	
	
	@Override
	int setPSFromData(CharacterClassSkill value, PreparedStatement ps, int col) throws SQLException {
		ps.setInt(col++, value.getFromLevel());
		ps.setString(col++, value.getSkillName());
		ps.setString(col++, value.getSkillDefinition());
		return col;
	}

	@Override
	String getSELECTForCodedList() {
		return "SELECT class_id,skill_id from_level,skill_name ";
	}

	@Override
	void setCodedListItemFromRS(CodedListItem<CharacterClassSkillKey> dto, ResultSet rs) throws SQLException {
		CharacterClassSkillKey key = new CharacterClassSkillKey();
		int col=1;
		key.setClassId(rs.getString(col++));
		key.setSkillId(rs.getString(col++));		
		String de = rs.getString(col++);
		dto.setItem(key);
		dto.setDescription(de);
		
	}

	
	public ReturnValue<List<CharacterClassSkill>> getSkillForClassLevel(Connection con,String classId,int level)throws SQLException{
		ReturnValue<List<CharacterClassSkill>> rv=null;

		List<CharacterClassSkill> skills = new ArrayList<>(); 
		
		String sql0 = "SELECT skill_id FROM character_class_skill WHERE class_id = ? AND from_level <= ? ";
		try(PreparedStatement ps0 = con.prepareStatement(sql0)){
			ps0.setString(1, classId);
			ps0.setInt(2, level);
			try(ResultSet rss0= ps0.executeQuery()){
				while(rss0.next()) {
					String skillId = rss0.getString(1);
					CharacterClassSkillKey key = new CharacterClassSkillKey();
					key.setClassId(classId);
					key.setSkillId(skillId);
					CharacterClassSkill x = get(con,key);
					skills.add(x);					
				} 
			}
			
		}
		
		rv = new ReturnValue(skills);
		return rv;
		
	}
	 
  
 
	
}
