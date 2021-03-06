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

import com.nobbysoft.first.common.entities.DTORowListener;
import com.nobbysoft.first.common.entities.staticdto.RaceClassLimit;
import com.nobbysoft.first.common.entities.staticdto.RaceClassLimitKey;
import com.nobbysoft.first.common.exceptions.RecordNotFoundException;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.utils.DbUtils;

public class RaceClassLimitDAO implements DAOI<RaceClassLimit, RaceClassLimitKey> {


	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	public RaceClassLimitDAO() { 
	}

	private String tableName = "Race_Class_Limit";

	@Override
	public void createTable(Connection con) throws SQLException {
		
		String sql = "CREATE TABLE " + tableName
				+ "(class_id varchar(20), race_id varchar(20), max_level INTEGER, limiting_factors varchar(256), "
				+ "PRIMARY KEY (class_id,race_id)  )";

		if (DbUtils.doesTableExist(con, tableName)) {

		}  else {
			try (PreparedStatement ps = con.prepareStatement(sql);) {
				ps.execute();
			}
		}
		
		{
			//hpAfterNameLevel hp_after_name_level
			String column="npc_class_only";
			String dataType = "BOOLEAN";
				
			if(!DbUtils.doesTableColumnExist(con, tableName, column)){
				sql = "ALTER TABLE " + tableName +" add column "+column+" " +dataType;
				try (PreparedStatement ps = con.prepareStatement(sql);) {
					ps.execute();
				} 
			}
		}
		
 
 
		// limitingAttribute limiting_Attribute
		{
			String[] newInts = new String[] {"max_level_pr_eq_17","max_level_pr_lt_17","limiting_Attribute"};
			DAOUtils.createInts(con, tableName, newInts);
		}
		
	}

	
	@Override 
	public void createConstraints(Connection con) throws SQLException{
		String sql;
		{
				String column = "race_id";
				String constraintName = "RCL_race_to_race";
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
		
		{
			String column = "class_id";
			String constraintName = "RCL_class_to_class";
			String otherTable = "Character_class";
			String otherColumn ="class_id";
			if (!DbUtils.isTableColumnFK(con, tableName, column, otherColumn)) {
				
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
	
	
	
	// Race_Class_Limit // class_id , race_id , max_level , limiting_factors
	
	@Override
	public RaceClassLimit get(Connection con, RaceClassLimitKey key) throws SQLException {
		String sql = "SELECT " + 
					" race_id, class_id ,max_level , limiting_factors,npc_class_only "+
				",max_level_pr_eq_17, max_level_pr_lt_17,limiting_Attribute"+
				" FROM Race_Class_Limit "
				+ " WHERE race_id = ? and class_id = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			int col=1;
			ps.setString(col++, key.getRaceId());
			ps.setString(col++, key.getClassId());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					RaceClassLimit dto = new RaceClassLimit();
					col = 1;
					dto.setRaceId(rs.getString(col++));
					dto.setClassId(rs.getString(col++));
					dto.setMaxLevel(rs.getInt(col++));
					dto.setLimitingFactors(rs.getString(col++));
					dto.setNpcClassOnly(rs.getBoolean(col++));
					dto.setMaxLevelPrEq17(rs.getInt(col++));
					dto.setMaxLevelPrLt17(rs.getInt(col++));
					dto.setLimitingAttribute(rs.getInt(col++));
					return (dto);

				} else {
					throw new RecordNotFoundException("Cannot find race-class-limit" + key);
				}

			}
		}
	}

	@Override
	public void insert(Connection con, RaceClassLimit value) throws SQLException {
		String sql = "INSERT INTO Race_Class_limit ( " + 
	              " race_id, class_id ,max_level , limiting_factors,npc_class_only,max_level_pr_eq_17, max_level_pr_lt_17,limiting_Attribute ) VALUES ( "
				+ "?,?,?,?,?,?,?,?) ";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			int col=1;
			ps.setString(col++, value.getRaceId());
			ps.setString(col++, value.getClassId());
			ps.setInt(col++, value.getMaxLevel());
			ps.setString(col++, value.getLimitingFactors());
			ps.setBoolean(col++, value.isNpcClassOnly());
			ps.setInt(col++, value.getMaxLevelPrEq17());
			ps.setInt(col++, value.getMaxLevelPrLt17());
			ps.setInt(col++, value.getLimitingAttribute());
			ps.executeUpdate();
		}
	}



	@Override
	public List<RaceClassLimit> getList(Connection con) throws SQLException {
		return getList(con,null);
	}
	
	@Override
	public List<RaceClassLimit> getList(Connection con,DTORowListener<RaceClassLimit> listener) throws SQLException {
		String sql = "SELECT t0.race_id, t0.class_id ,t0.max_level , t0.limiting_factors,t0.npc_class_only "+
	",t0.max_level_pr_eq_17, t0.max_level_pr_lt_17,t0.limiting_Attribute "+
	            " FROM Race_Class_Limit t0 "+
				" LEFT OUTER JOIN Character_Class t1 on t1.class_id = t0.class_id"+
	            " LEFT OUTER JOIN Race t2 ON t2.race_id = t0.race_id "+
				" ORDER BY t2.name, t1.name,t0.race_id,t0.class_id "+
				"";
		 List<RaceClassLimit> list = new ArrayList<>();
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			try(ResultSet rs = ps.executeQuery()){
				boolean first=true;
				while(rs.next()) {
					RaceClassLimit dto = new RaceClassLimit();
					int col=1;
					dto.setRaceId(rs.getString(col++));
					dto.setClassId(rs.getString(col++));
					dto.setMaxLevel(rs.getInt(col++));
					dto.setLimitingFactors(rs.getString(col++));
					dto.setNpcClassOnly(rs.getBoolean(col++));
					dto.setMaxLevelPrEq17(rs.getInt(col++));
					dto.setMaxLevelPrLt17(rs.getInt(col++));
					dto.setLimitingAttribute(rs.getInt(col++));
					if(listener!=null) {
						listener.hereHaveADTO(dto, first);
					} else {
						list.add(dto);
					}
					first=false;
				}
			}
		}
		return list;
	}

	@Override
	public List<RaceClassLimit> getFilteredList(Connection con, String filter) throws SQLException {
		return getFilteredList(con,filter,null);
	}
	
	@Override
	public List<RaceClassLimit> getFilteredList(Connection con, String filter,
			DTORowListener listener) throws SQLException {
		if (filter == null || filter.isEmpty()) {
			return getList(con);
		}
		
		String sql = "SELECT t0.race_id, t0.class_id ,t0.max_level , t0.limiting_factors, t0.npc_class_only "+
		",t0.max_level_pr_eq_17, t0.max_level_pr_lt_17,t0.limiting_Attribute"+
	            " FROM Race_Class_Limit t0 "+
				" LEFT OUTER JOIN Character_Class t1 on t1.class_id = t0.class_id"+
	            " LEFT OUTER JOIN Race t2 ON t2.race_id = t0.race_id "+
	            " WHERE (lower(t2.name) like ? OR lower(t1.name) like ?) "+
				" ORDER BY t2.name, t1.name,t0.race_id,t0.class_id "+
				"";
		 List<RaceClassLimit> list = new ArrayList<>();
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			String f = "%" + filter.toLowerCase() + "%";
			ps.setString(1, f);
			ps.setString(2, f);
			
			try(ResultSet rs = ps.executeQuery()){
				boolean first=true;
				while(rs.next()) {
					RaceClassLimit dto = new RaceClassLimit();
					int col=1;
					dto.setRaceId(rs.getString(col++));
					dto.setClassId(rs.getString(col++));
					dto.setMaxLevel(rs.getInt(col++));
					dto.setLimitingFactors(rs.getString(col++));
					dto.setNpcClassOnly(rs.getBoolean(col++));
					dto.setMaxLevelPrEq17(rs.getInt(col++));
					dto.setMaxLevelPrLt17(rs.getInt(col++));
					dto.setLimitingAttribute(rs.getInt(col++));
					if(listener!=null) {
						listener.hereHaveADTO(dto, first);
					} else {
						list.add(dto);
					}
					first=false;
				}
			}
		}
		return list;
		
	}

	@Override
	public void delete(Connection con, RaceClassLimit value) throws SQLException {
		String sql = "DELETE FROM Race_Class_Limit WHERE race_id = ? AND class_id = ? ";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			int col = 1;
			ps.setString(col++, value.getRaceId());
			ps.setString(col++, value.getClassId());
			int rows = ps.executeUpdate();
			if (rows == 0) {
				throw new RecordNotFoundException("Can't find Race Class LImit to delete it id=" + value.getKey());
			}
		}
	}

	@Override
	public void update(Connection con, RaceClassLimit value) throws SQLException {
		String sql=
				" UPDATE Race_Class_Limit "+
						" SET max_level      = ?, "+
						" limiting_factors   = ?, "+
						" npc_class_only     = ?, "+
						" max_level_pr_eq_17 = ?, "+
					    " max_level_pr_lt_17 = ?, " +
						" limiting_Attribute = ?  " +
				" WHERE race_id = ? "+
				"   AND class_id = ? "
				;

		try(PreparedStatement ps = con.prepareStatement(sql)){
			int col=1;
			ps.setInt(col++, value.getMaxLevel());
			ps.setString(col++, value.getLimitingFactors()); 
			ps.setBoolean(col++, value.isNpcClassOnly()); 
			ps.setInt(col++, value.getMaxLevelPrEq17());
			ps.setInt(col++, value.getMaxLevelPrLt17());
			ps.setInt(col++, value.getLimitingAttribute());
			//
			//
			ps.setString(col++, value.getRaceId()); 
			ps.setString(col++, value.getClassId()); 
			int rows=ps.executeUpdate();
			if(rows==0){
				throw new RecordNotFoundException("Can't find Race Class Limit to delete it id=" + value.getKey());
			}
		}
	}

	@Override
	public List<CodedListItem<RaceClassLimitKey>> getAsCodedList(Connection con) throws SQLException {
		List<CodedListItem<RaceClassLimitKey>> list = new ArrayList<>();
		String sql = "SELECT t0.race_id, t0.class_id ,t1.name,t2.name "+
	            " FROM Race_Class_Limit t0 "+
				" LEFT OUTER JOIN Character_Class t1 on t1.class_id = t0.class_id"+
	            " LEFT OUTER JOIN Race t2 ON t2.race_id = t0.race_id "+
				" ORDER BY t2.name, t1.name,t0.race_id,t0.class_id "+
				""; 
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					RaceClassLimitKey dto = new RaceClassLimitKey();
					int col=1;
					dto.setRaceId(rs.getString(col++));
					dto.setClassId(rs.getString(col++));
					String rn = rs.getString(col++);
					String cn = rs.getString(col++);
					CodedListItem<RaceClassLimitKey> cli = new CodedListItem<>();
					cli.setItem(dto);
					cli.setDescription(rn + " " +cn);
					list.add(cli);
				}
			}
		}
		return list;
	}
}
