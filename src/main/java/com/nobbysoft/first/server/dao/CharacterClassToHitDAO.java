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

public class CharacterClassToHitDAO extends AbstractDAO<CharacterClassToHit, CharacterClassToHitKey> implements DAOI<CharacterClassToHit, CharacterClassToHitKey> {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	public CharacterClassToHitDAO() {
	}
 
	private String tableName = "Character_Class_to_hit";
	@Override
	public void createTable(Connection con) throws SQLException {
		/**
		 * private String classId; private String name; private boolean
		 * hasMagicDefenceBonus;
		 */
		String sql = "CREATE TABLE " + tableName +
				 "(class_id varchar(20), "+			
					" from_Level INTEGER, " +
					" to_Level INTEGER, "+
				 "PRIMARY KEY (class_id,from_level,to_level) " + 
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
			String[] newInts = new String[] {"biggest_AC_Hit_By_20"};
			DAOUtils.createInts(con, tableName, newInts);
		}

	}

	public void createConstraints(Connection con) throws SQLException{
			String sql; 
			{
				
//				CONSTRAINT FLTS_FK FOREIGN KEY (FLIGHT_ID, SEGMENT_NUMBER)  			REFERENCES Flights (FLIGHT_ID, SEGMENT_NUMBER)			
						// add some constraints
						String column = "class_id";
						String constraintName = "CCL_classtohot_to_class";
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
		return new String[] {"class_id","from_level","to_level"};
	}

	@Override
	String[] getData() { 
		return new String[] {"biggest_AC_Hit_By_20"};
	}
	
	
	@Override
	CharacterClassToHit dtoFromRS(ResultSet rs) throws SQLException {
		CharacterClassToHit dto = new CharacterClassToHit();
		int col=1;
		dto.setClassId(rs.getString(col++));
		dto.setFromLevel(rs.getInt(col++));
		dto.setToLevel(rs.getInt(col++));
		dto.setBiggestACHitBy20(rs.getInt(col++));		
		return dto;
	}

	@Override
	void setPSFromKey(PreparedStatement ps, CharacterClassToHitKey key) throws SQLException {
		ps.setString(1, key.getClassId());
		ps.setInt(2, key.getFromLevel());
		ps.setInt(3, key.getToLevel());
		
	}


	@Override
	String getTableName() { 
		return tableName;
	}

	@Override
	String addOrderByClause(String sql) {

		return sql+" ORDER BY class_id,from_level";
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
	int setPSFromKeys(CharacterClassToHit value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getClassId());
		ps.setInt(col++, value.getFromLevel());
		ps.setInt(col++, value.getToLevel());
		return col;
	}

	
	
	@Override
	int setPSFromData(CharacterClassToHit value, PreparedStatement ps, int col) throws SQLException {
		ps.setInt(col++, value.getBiggestACHitBy20());
		return col;
	}

	@Override
	String getSELECTForCodedList() {

		return "SELECT class_id,from_level,to_level,class_id|| ' - ' ||from_level||' - '||to_level";
	}

	@Override
	void setCodedListItemFromRS(CodedListItem<CharacterClassToHitKey> dto, ResultSet rs) throws SQLException {
		CharacterClassToHitKey key = new CharacterClassToHitKey();
		int col=1;
		key.setClassId(rs.getString(col++));
		key.setFromLevel(rs.getInt(col++));
		key.setToLevel(rs.getInt(col++));
		String de = rs.getString(col++);
		dto.setItem(key);
		dto.setDescription(de);
		
	}

 
  
 
	
}
