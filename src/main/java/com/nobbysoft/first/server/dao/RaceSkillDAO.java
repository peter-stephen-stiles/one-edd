package com.nobbysoft.first.server.dao;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.common.entities.staticdto.RaceSkill;
import com.nobbysoft.first.common.entities.staticdto.RaceSkillKey;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.utils.DbUtils;

public class RaceSkillDAO extends AbstractDAO<RaceSkill, RaceSkillKey> implements DAOI<RaceSkill, RaceSkillKey> {


	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	public RaceSkillDAO() { 
	}

	private String tableName = "Race_Skill";

	@Override
	public void createTable(Connection con) throws SQLException {
		
		String sql = "CREATE TABLE " + tableName
				+ "(race_id varchar(20), skill_id varchar(20), skill_name varchar(60), skill_definition varchar(512), "
				+ "PRIMARY KEY (race_id,skill_id)  )";

		if (DbUtils.doesTableExist(con, tableName)) {

		}  else {
			try (PreparedStatement ps = con.prepareStatement(sql);) {
				ps.execute();
			}
		}
		
 
		
	}

	
	@Override 
	public void createConstraints(Connection con) throws SQLException{
		String sql;
		{
				String column = "race_id";
				String constraintName = "RCL_race_to_skill";
				String otherTable = "Race";
				String otherColumn ="race_id";
				if (!DbUtils.isTableColumnFK(con, tableName, column, otherTable)) {
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


	@Override
	RaceSkill dtoFromRS(ResultSet rs) throws SQLException {
		RaceSkill dto = new RaceSkill();
		int col=1;
		dto.setRaceId(rs.getString(col++));
		dto.setSkillId(rs.getString(col++));
		dto.setSkillName(rs.getString(col++));
		dto.setSkillDefinition(rs.getString(col++));
		return dto;
	}


	@Override
	void setPSFromKey(PreparedStatement ps, RaceSkillKey key) throws SQLException {
		ps.setString(1, key.getRaceId());
		ps.setString(2, key.getSkillId());
		
	}


	@Override
	String[] getKeys() {
		return new String[] {"race_id","skill_id"};
	}


	@Override
	String[] getData() {		
		return new String[] {"skill_name", "skill_definition"};
	}


	@Override
	String getTableName() {
		return tableName;
	}


	@Override
	String addOrderByClause(String sql) {
		sql = sql + "ORDER BY race_id,skill_name ";
		return sql;
	}


	@Override
	String getFilterWhere() {
		return "(skill_name like ? or skill_definition like ?)";
	}


	@Override
	void setFilterParameters(PreparedStatement ps, String filter) throws SQLException {
		String f= "%"+filter+"%";
		ps.setString(1, f);
		ps.setString(2, f);
		
	}


	@Override
	int setPSFromKeys(RaceSkill value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getRaceId());
		ps.setString(col++, value.getSkillId());
		return col;
	}


	@Override
	int setPSFromData(RaceSkill value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getSkillName());
		ps.setString(col++, value.getSkillDefinition());
		return col;
	}


	@Override
	String getSELECTForCodedList() { 
		return "SELECT race_id , skill_id, skill_name FROM "+tableName+" ORDER BY race_id, skill_name";
	}


	@Override
	void setCodedListItemFromRS(CodedListItem<RaceSkillKey> dto, ResultSet rs) throws SQLException {
		RaceSkillKey key = new RaceSkillKey();
		key.setRaceId(rs.getString(1));
		key.setSkillId(rs.getString(2));
		dto.setItem(key);dto.setDescription(rs.getString(3));
		
	}
	
	
	
}
