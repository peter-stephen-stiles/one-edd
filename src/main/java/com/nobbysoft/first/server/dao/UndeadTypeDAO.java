package com.nobbysoft.first.server.dao;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.common.entities.staticdto.UndeadType;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.utils.DbUtils;

public class UndeadTypeDAO extends AbstractDAO<UndeadType, Integer> implements DAOI<UndeadType, Integer> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private String tableName = "Undead_Type"; 

	public UndeadTypeDAO() {

	}

  
	
	@Override
	public void createTable(Connection con) throws SQLException {

		String sql = "CREATE TABLE " + tableName + " (undead_type integer,  "
				+ "" + "PRIMARY KEY (undead_type)  )";

		if (DbUtils.doesTableExist(con, tableName)) {

		} else {
			try (PreparedStatement ps = con.prepareStatement(sql);) {
				ps.execute();
			}
		} 
		
		VC[] newStrings = new VC[] {new VC("undead_type_example",60)};
		DAOUtils.createStrings(con, tableName, newStrings);
		 

	}

	private String[] keys = new String[] { "undead_type" };
	private String[] data = new String[] { "undead_type_example"};

	String[] getKeys() {
		return keys;
	}

	String[] getData() {
		return data;
	}

	String addOrderByClause(String sql) {
		sql = sql + "ORDER BY undead_type ";
		return sql;
	}

	UndeadType dtoFromRS(ResultSet rs) throws SQLException {
		UndeadType dto = new UndeadType();
		int col = 1;
		dto.setUndeadType(rs.getInt(col++));
		dto.setUndeadTypeExample(rs.getString(col++));
		return dto;
	}

	void setPSFromKey(PreparedStatement ps, Integer key) throws SQLException {
		ps.setInt(1, key);
	}

	String getTableName() {
		return tableName;
	}

	int setPSFromKeys(UndeadType value, PreparedStatement ps, int col) throws SQLException {
		ps.setInt(col++, value.getUndeadType());
		return col;
	}


	
	int setPSFromData(UndeadType value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getUndeadTypeExample());
		return col;
	}

	String getSELECTForCodedList() {
		return "SELECT undead_type, undead_type_example " + getTableName() + " ORDER BY undead_type";
	}

	void setCodedListItemFromRS(CodedListItem<Integer> dto, ResultSet rs) throws SQLException {
		dto.setItem(rs.getInt(1));
		dto.setDescription(rs.getString(2));
	}

	String getFilterWhere() {
		return " undead_type_example like ? ";
	};

	void setFilterParameters(PreparedStatement ps, String filter) throws SQLException {		
		ps.setString(1, "%"+filter+"%");
		
	}

}
