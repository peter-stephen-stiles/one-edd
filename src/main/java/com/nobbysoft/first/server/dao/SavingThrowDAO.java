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

public class SavingThrowDAO extends AbstractDAO<SavingThrow, SavingThrowKey> implements DAOI<SavingThrow, SavingThrowKey> {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	public SavingThrowDAO() {
	}
 
	private String tableName = "saving_throw";
	@Override
	public void createTable(Connection con) throws SQLException {
		/**
		 * private String classId; private String name; private boolean
		 * hasMagicDefenceBonus;
		 */
		String sql = "CREATE TABLE " + tableName +
				 "(class_id varchar(20), "+			
					" from_level INTEGER, "	+		
					" to_level INTEGER, "	+		
					" saving_throw_type_string VARCHAR(60), "
				+ "PRIMARY KEY (class_id,from_level,to_level,saving_throw_type_string) " + 
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
			
			
//	CONSTRAINT FLTS_FK FOREIGN KEY (FLIGHT_ID, SEGMENT_NUMBER)  			REFERENCES Flights (FLIGHT_ID, SEGMENT_NUMBER)			
			// add some constraints
			String column = "class_id";
			String constraintName = "CCL_save_to_class";
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
 
//		{
//			VC[] newStrings = new VC[] {
//					new VC("level_title",30),
//					new VC("notes",4000)
//			};
//			DAOUtils.createStrings(con, tableName, newStrings);
//		}
		
		{
			String[] newInts = new String[] {
					"roll_required",
			};
			DAOUtils.createInts(con, tableName, newInts);
		}
//		{
//			String[] newBooleans = new String[] {
//					"name_Level"
//			};
//			DAOUtils.createBooleans(con, tableName, newBooleans);
//		}
	}

	@Override
	String[] getKeys() {
		return new String[] {"class_id","from_level","to_level","saving_throw_type_string"};
	}

	@Override
	String[] getData() { 
		return new String[] {"roll_required"};
	}
	
	
	@Override
	SavingThrow dtoFromRS(ResultSet rs) throws SQLException {
		SavingThrow dto = new SavingThrow();
		int col=1;
		dto.setClassId(rs.getString(col++));
		dto.setFromLevel(rs.getInt(col++));
		dto.setToLevel(rs.getInt(col++));
		dto.setSavingThrowTypeString(rs.getString(col++));
		dto.setRollRequired(rs.getInt(col++));		
		return dto;
	}

	@Override
	void setPSFromKey(PreparedStatement ps, SavingThrowKey key) throws SQLException {
		int col=1;
		ps.setString(col++, key.getClassId());
		ps.setInt(col++, key.getFromLevel());
		ps.setInt(col++, key.getToLevel());
		ps.setString(col++, key.getSavingThrowTypeString());
		
	}


	@Override
	String getTableName() { 
		return tableName;
	}

	@Override
	String addOrderByClause(String sql) {

		return sql+" ORDER BY class_id,saving_throw_type_string,from_level,to_level";
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
	int setPSFromKeys(SavingThrow value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getClassId());
		ps.setInt(col++, value.getFromLevel());
		ps.setInt(col++, value.getToLevel());
		ps.setString(col++, value.getSavingThrowTypeString());
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
	int setPSFromData(SavingThrow value, PreparedStatement ps, int col) throws SQLException {
		ps.setInt(col++, value.getRollRequired()); 
		return col;
	}

	@Override
	String getSELECTForCodedList() {

		return "SELECT class_id,from_level,to_level,saving_throw_string,class_id|| ' - ' ||from_level||' - '||to_level||' - '||saving_throw_string||' - '||roll_required";
	}

	@Override
	void setCodedListItemFromRS(CodedListItem<SavingThrowKey> dto, ResultSet rs) throws SQLException {
		SavingThrowKey key = new SavingThrowKey();
		int col=1;
		key.setClassId(rs.getString(col++));
		key.setFromLevel(rs.getInt(col++));
		key.setToLevel(rs.getInt(col++));
		key.setSavingThrowTypeString(rs.getString(col++));
		String de = rs.getString(3);
		dto.setItem(key);
		dto.setDescription(de);
		
	}

 
	
	public int getMaxLevelInTable(Connection con,String SavingThrowId) throws SQLException {
		
		String sql = "SELECT MAX(from_level) FROM "+tableName+" WHERE class_id = ?";
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.setString(1, SavingThrowId);
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					return rs.getInt(1);
				} else {
					return 0;
				}
			}
		}
		
	}
 
	
 public List<SavingThrow> getSavesForClassLevel(Connection con,String classId, int level) throws SQLException {
	 List<SavingThrow> list = new ArrayList<>();
	 // for each type of saving throw
	 String sql0 = "SELECT MAX(from_level), count(*) FROM " + tableName+" WHERE class_id = ? and saving_throw_type_string = ? and from_level <= ?";
	 String sql1 = "SELECT to_level FROM " + tableName+" WHERE class_id = ? and saving_throw_type_string = ? and from_level = ? ";
	 PreparedStatement ps0 = con.prepareStatement(sql0);
	 PreparedStatement ps1 = con.prepareStatement(sql1);
	 for(SavingThrowType type:SavingThrowType.values()) {
		 String save=type.toString();
		 int col=1;
		 ps0.setString(col++, classId);
		 ps0.setString(col++, save);
		 ps0.setInt(col++, level);
		 try(ResultSet rs0 = ps0.executeQuery()){
			 if(rs0.next()) {
				 col=1;
				 int useThisFromLevel = rs0.getInt(col++);
				 int count = rs0.getInt(col++);
				 if(count>0) {
					col=1;
					 ps1.setString(col++, classId);
					 ps1.setString(col++, save);
					 ps1.setInt(col++, useThisFromLevel);
					 try(ResultSet rs1 = ps1.executeQuery()){
						 if(rs1.next()) {
							 col=1;
							 int useThisToLevel = rs1.getInt(col++);
							 SavingThrowKey key = new SavingThrowKey(classId,useThisFromLevel,useThisToLevel,save);
							 SavingThrow value = get(con,key);
							 list.add(value);
						 }
					 }
					 ps1.clearParameters();
				 }
			 } else {
				 LOGGER.info("Couldn't find save for "+classId+"-"+level+"-"+type+" :(");
			 }
			 
		 }
		 
		 ps0.clearParameters();
	 }
	 return list;
	 
 }
	
}
