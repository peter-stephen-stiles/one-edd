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

import com.nobbysoft.first.common.entities.staticdto.TurnUndead;
import com.nobbysoft.first.common.entities.staticdto.TurnUndeadKey; 
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.utils.DbUtils;

public class TurnUndeadDAO extends AbstractDAO<TurnUndead, TurnUndeadKey> implements DAOI<TurnUndead, TurnUndeadKey> {


	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	public TurnUndeadDAO() { 
	}
/**
	private int effectiveClericLevelFrom;	PK
	private int undeadType; //FK PK
	private int effectiveClericLevelTo;
	private int rollRequired; //-1=D, 0=T, 1-20=RR,21=IMPO
	private int numberAffectedFrom;
	private int numberAffectedTo;
 */
	

	String tableName = "turn_undead";

	@Override
	public void createTable(Connection con) throws SQLException {
		String sql = "CREATE TABLE " + tableName
				+ "(effective_Cleric_Level_From integer, undead_type integer, "
				+ "PRIMARY KEY (effective_Cleric_Level_From , undead_type)  )";

		if (DbUtils.doesTableExist(con, tableName)) {

		}  else {
			try (PreparedStatement ps = con.prepareStatement(sql);) {
				ps.execute();
			}
		}
		
 
 
 
		// limitingAttribute limiting_Attribute
		{
			String[] newInts = new String[] {
					"effective_Cleric_Level_To",
					"roll_Required",
					"number_Affected_From",
					"number_Affected_To"};
			DAOUtils.createInts(con, tableName, newInts);
		}
		
	}

	
	public void createConstraints(Connection con) throws SQLException{
		String sql; 
		{
			 		
					// add constraints
					String column = "undead_type";
					String constraintName = "CCL_turn_to_undead_type";
					if (!DbUtils.isTableColumnFK(con, tableName, column, "undead_type")) {
						String otherColumn = "undead_type";
						String otherTable =  "undead_type";
						sql = "ALTER TABLE " + tableName + " add constraint "+ constraintName +" foreign key (" + column
								+ ") references "+otherTable+"("+otherColumn+") ";
						try (PreparedStatement ps = con.prepareStatement(sql);) {
							ps.execute();
						} catch (Exception ex) {
							LOGGER.error("Error running SQL\n"+sql,ex);
							throw ex;
						}
					}
					
		 
				}
		
		
	}

	private String[] keys = new String[] { "effective_Cleric_Level_From",
			"undead_type", };
	
	private String[] data = new String[] {
			"effective_Cleric_Level_To",
			"roll_Required",
			"number_Affected_From",
			"number_Affected_To"
			};
	

	String[] getKeys() {
		return keys;
	}

	String[] getData() {
		return data;
	}

	String addOrderByClause(String sql) {
		sql = sql + "ORDER BY effective_Cleric_Level_From, undead_type ";
		return sql;
	}


	TurnUndead dtoFromRS(ResultSet rs) throws SQLException {
		TurnUndead dto = new TurnUndead();
		int col = 1;
		dto.setEffectiveClericLevelFrom(rs.getInt(col++));
		dto.setUndeadType(rs.getInt(col++));
		dto.setEffectiveClericLevelTo(rs.getInt(col++));
		dto.setRollRequired(rs.getInt(col++));
		dto.setNumberAffectedFrom(rs.getInt(col++));
		dto.setNumberAffectedTo(rs.getInt(col++));  
		
		return dto;
	}

	void setPSFromKey(PreparedStatement ps, TurnUndeadKey key) throws SQLException {
		ps.setInt(1, key.getEffectiveClericLevelFrom());
		ps.setInt(2, key.getUndeadType());
	}

	String getTableName() {
		return tableName;
	}

	int setPSFromKeys(TurnUndead value, PreparedStatement ps, int col) throws SQLException {
		ps.setInt(col++, value.getEffectiveClericLevelFrom());
		ps.setInt(col++, value.getUndeadType());
		return col;
	}
	


	int setPSFromData(TurnUndead value, PreparedStatement ps, int col) throws SQLException {

		ps.setInt(col++, value.getEffectiveClericLevelTo());
		ps.setInt(col++, value.getRollRequired());
		ps.setInt(col++, value.getNumberAffectedFrom());
		ps.setInt(col++, value.getNumberAffectedTo());
		return col;
	}


	
	String getSELECTForCodedList() {
		return "SELECT Effective_Cleric_Level_from, undead_type,roll_required FROM " + getTableName() + " ORDER BY Effective_Cleric_Level_from, undead_type";
	}

	void setCodedListItemFromRS(CodedListItem<TurnUndeadKey> dto, ResultSet rs) throws SQLException {
		dto.setItem(new TurnUndeadKey(rs.getInt(1),rs.getInt(2)));
		dto.setDescription(rs.getString(3));
	}

	String getFilterWhere() {
		return " ? >= Effective_Cleric_Level_from and ? <= Effective_Cleric_Level_to ";
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

	public List<TurnUndead> getListForClericLevel(Connection con, int level) throws SQLException {

		List<TurnUndead> list = new ArrayList<>();
		String sql = "SELECT effective_cleric_level_from,undead_type FROM turn_undead WHERE effective_cleric_level_from <= ? AND effective_cleric_level_to >= ? ORDER BY effective_cleric_level_from ";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, level);
			ps.setInt(2, level);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					int from = rs.getInt(1);
					int ut = rs.getInt(2);
					TurnUndeadKey tuk = new TurnUndeadKey();
					tuk.setEffectiveClericLevelFrom(from);
					tuk.setUndeadType(ut);
					TurnUndead tu = super.get(con, tuk);
					list.add(tu);
				}
			}
		}

		return list;

	}
	
	
	 
}
