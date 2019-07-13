package com.nobbysoft.com.nobbysoft.first.server.dao;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.Alignment;
import com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.Gender;
import com.nobbysoft.com.nobbysoft.first.common.exceptions.RecordNotFoundException;
import com.nobbysoft.com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.com.nobbysoft.first.server.utils.DbUtils;

public class PlayerCharacterDAO implements DAOI<PlayerCharacter, Integer>{

	public PlayerCharacterDAO() { 
	}


	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
 

	String tableName = "Player_Character";
	String sequenceName = tableName+"_seq";
	
	public int getNextid(Connection con) throws SQLException {
		String sql = "values (next value for "+sequenceName+")";
		try(PreparedStatement ps = con.prepareStatement(sql)){
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
		}
			}
		throw new SQLException("Sequence failure :(");
	}

	@Override
	public void createTable(Connection con) throws SQLException {
		/**
		 * private String classId; private String character_name; private boolean
		 * hasMagicDefenceBonus;
		 */
		String sql = "CREATE TABLE " + tableName
				+ "(pc_id varchar(20), character_name varchar(256), "
				+ "PRIMARY KEY (pc_id) ," + " UNIQUE  (character_name) )";

		if (DbUtils.doesTableExist(con, tableName)) {

		} else {
			try (PreparedStatement ps = con.prepareStatement(sql);) {
				ps.execute();
			}
			// sequence
			String s = "CREATE SEQUENCE "+sequenceName + " start with 1";
			try (PreparedStatement ps = con.prepareStatement(s);) {
				ps.execute();
			}
		}

	
			
			String[] newStrings = new String[] { 
					"player_name",
					"alignment",
					"race_id",
					"gender",
					"first_class",
					"second_class",
					"third_class"
					};
			int[] stringLengths = new int[] {255,60,20,20,20,20,20};
			DAOUtils.createStrings(con, tableName, newStrings, stringLengths);

		String[] newInts = new String[] { 
				"attr_str",
				"attr_int",
				"attr_wis",
				"attr_dex",
				"attr_con",
				"attr_chr" };
		DAOUtils.createInts(con, tableName, newInts);

	}



	@Override
	public PlayerCharacter get(Connection con, Integer key) throws SQLException {
		String sql = "SELECT ";
		sql = addKeyFields(sql);
		sql = addDataFields(sql);
		sql = sql + " FROM Player_Character  WHERE ";
		sql = addKeyColumnsForUpdate(sql);
		//sql = sql + " pc_id = ? ";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, key);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					PlayerCharacter dto = dtoFromRS(rs);
					return (dto);

				} else {
					throw new RecordNotFoundException("Cannot find player " + key);
				}

			}
		}

	}

	private PlayerCharacter dtoFromRS(ResultSet rs) throws SQLException {
		PlayerCharacter dto = new PlayerCharacter();
		int col = 1;
		dto.setPcId(rs.getInt(col++));
		dto.setCharacterName(rs.getString(col++));
		dto.setPlayerName(rs.getString(col++));
		
		dto.setAttrStr(rs.getInt(col++));
		dto.setAttrInt(rs.getInt(col++));
		dto.setAttrWis(rs.getInt(col++));
		
		dto.setAttrDex(rs.getInt(col++));
		dto.setAttrCon(rs.getInt(col++));
		dto.setAttrChr(rs.getInt(col++));
		
		String align = rs.getString(col++);
		if(align!=null) {
			dto.setAlignment(Alignment.valueOf(align));
		} else {
			dto.setAlignment(null);
		}
		dto.setRaceId(rs.getString(col++));
		String gender = rs.getString(col++);
		if(gender!=null) {
			dto.setGender(Gender.valueOf(gender));
		} else {
			dto.setGender(null);
		}
		dto.setFirstClass(rs.getString(col++));
		dto.setSecondClass(rs.getString(col++));
		dto.setThirdClass(rs.getString(col++));

		return dto;
	}

	@Override
	public void insert(Connection con, PlayerCharacter value) throws SQLException {
		String sql = "INSERT INTO Player_Character ( ";
		sql = addKeyFields(sql);
		sql = addDataFields(sql);
		sql = sql + ") values ( ";
		sql = sql + "?,?,?, ?,?,?, ?,?,?, ?,?,? ,?,?,?";
		sql = sql + ")";
		LOGGER.info("sql is:"+sql);
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			int col = 1;
			col = setPSFromKeys(value, ps, col);
			col = setPSFromData(value, ps, col);
			ps.executeUpdate();
		}

	}

	private String addDataFields(String sql) {
		sql = sql +"character_name ,"+
					"player_name,"+
					"attr_str,"+
					"attr_int,"+
					"attr_wis,"+
					"attr_dex,"+
					"attr_con,"+
					"attr_chr,"+
					"alignment,"+
					"race_Id,"+
					"gender,"+
					"first_class,"+
					"second_class,"+
					"third_class"
					;
		return sql;
	}

	private int setPSFromKeys(PlayerCharacter value, PreparedStatement ps, int col) throws SQLException {
		ps.setInt(col++, value.getPcId());
		return col;
	}

	private int setPSFromData(PlayerCharacter value, PreparedStatement ps, int col) throws SQLException {
 
		ps.setString(col++, value.getCharacterName());
		ps.setString(col++, value.getPlayerName());

		ps.setInt(col++, value.getAttrStr()); 
		ps.setInt(col++, value.getAttrInt()); 
		ps.setInt(col++, value.getAttrWis()); 
		
		ps.setInt(col++, value.getAttrDex()); 
		ps.setInt(col++, value.getAttrCon()); 
		ps.setInt(col++, value.getAttrChr()); 
		
		ps.setString(col++, value.getAlignment().name());
		ps.setString(col++, value.getRaceId());
		ps.setString(col++, value.getGender().name());

		ps.setString(col++, value.getFirstClass());
		ps.setString(col++, value.getSecondClass());
		ps.setString(col++, value.getThirdClass());

		return col;
	}

	@Override
	public List<PlayerCharacter> getList(Connection con) throws SQLException {
		String sql = "SELECT ";
		sql = addKeyFields(sql);
		sql = addDataFields(sql);
		sql = sql + " FROM Player_Character  ";
		sql = addOrderByClause(sql);
		List<PlayerCharacter> data = new ArrayList<>();
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					PlayerCharacter dto = dtoFromRS(rs);
					data.add(dto);

				}

			}
		}

		return data;
	}

	@Override
	public List<PlayerCharacter> getFilteredList(Connection con, String filter) throws SQLException {
		if (filter == null || filter.isEmpty()) {
			return getList(con);
		}
		String sql = "SELECT ";
		sql = addKeyFields(sql);
		sql = addDataFields(sql);
		sql = sql + " FROM Player_Character  WHERE ";
		sql = sql + "  character_name like ? or player_name like ?";
		sql = addOrderByClause(sql);
		List<PlayerCharacter> data = new ArrayList<>();
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			String f = "%" + filter + "%";
			ps.setString(1, f); 
			ps.setString(2, f); 
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					PlayerCharacter dto = dtoFromRS(rs);
					data.add(dto);

				}

			}
		}

		return data;
	}

	private String addOrderByClause(String sql) {
		sql = sql + "ORDER BY character_name ";
		return sql;
	}

	private String addKeyFields(String sql) {
		sql = sql + " pc_id, ";
		return sql;
	}

	@Override
	public void delete(Connection con, PlayerCharacter value) throws SQLException {
		String sql = "DELETE FROM Player_Character WHERE pc_id =?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			int col = 1;
			col = setPSFromKeys(value, ps, col);
			int rows = ps.executeUpdate();
			if (rows == 0) {
				throw new RecordNotFoundException("Can't find player to delete it id=" + 
						value.getPcId() + " "+value.getCharacterName());
			}
		}

	}

	@Override
	public void update(Connection con, PlayerCharacter value) throws SQLException {
		String sql = " UPDATE Player_Character  SET ";
		sql = addDataColumnsForUpdate(sql);
		sql = sql + " WHERE ";
		sql = addKeyColumnsForUpdate(sql);

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			int col = 1;
			col = setPSFromData(value, ps, col);

			col = setPSFromKeys(value, ps, col);

			int rows = ps.executeUpdate();
			if (rows == 0) {
				throw new RecordNotFoundException("Can't find character to update it id=" + 
			value.getPcId() + " "+value.getCharacterName());
			}
		}
	}

	private String addKeyColumnsForUpdate(String sql) {
		sql = sql + "pc_id = ?";
		return sql;
	}

	private String addDataColumnsForUpdate(String sql) {
		
		sql = sql +
				"character_name =?,"+
				"player_name    =?,"+
				"attr_str       =?,"+
				"attr_int       =?,"+
				"attr_wis       =?,"+
				"attr_dex       =?,"+
				"attr_con       =?,"+
				"attr_chr       =?,"+
				"alignment      =?,"+
				"race_id        =?,"+
				"gender         =?,"+
				"first_class    =?,"+
				"second_class   =?,"+
				"third_class    =?"
				;
		return sql;
	}

	@Override
	public List<CodedListItem<Integer>> getAsCodedList(Connection con) throws SQLException {
		String sql = "SELECT   pc_id, character_name   FROM Player_Character ORDER BY character_name ";
		List<CodedListItem<Integer>> data = new ArrayList<>();
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					CodedListItem<Integer> dto = new CodedListItem<>();
					int col = 1;
					dto.setItem(rs.getInt(col++));
					dto.setDescription(rs.getString(col++));
					data.add(dto);
				}

			}
		}

		return data;
	}

 
	
}
