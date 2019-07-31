package com.nobbysoft.com.nobbysoft.first.server.dao;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.EquipmentType;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.EquipmentWhere;
import com.nobbysoft.com.nobbysoft.first.common.entities.pc.PlayerCharacterEquipment;
import com.nobbysoft.com.nobbysoft.first.common.entities.pc.PlayerCharacterEquipmentKey;
import com.nobbysoft.com.nobbysoft.first.common.exceptions.RecordNotFoundException;
import com.nobbysoft.com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.com.nobbysoft.first.server.utils.DbUtils;

public class PlayerCharacterEquipmentDAO 
 	extends AbstractDAO<PlayerCharacterEquipment,PlayerCharacterEquipmentKey> 
	implements DAOI<PlayerCharacterEquipment, PlayerCharacterEquipmentKey>,
	PlayerCharacterDetailI<PlayerCharacterEquipment>{

	public PlayerCharacterEquipmentDAO() { 
	}


	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
 

	String tableName = "Player_Character_equipment";
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
				+ "(pc_id integer, equipment_id integer, "
				+ "PRIMARY KEY (pc_id,equipment_id)   )";

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

	
			
			VC[] newStrings = new VC[] { 
					new VC("equipment_type",20),
					new VC("code",20),
					new VC("equipped_where",20),
					
					}; 
			DAOUtils.createStrings(con, tableName, newStrings);

			String[] newBoos = new String[] { 
					"equipped", 
					};
			
			DAOUtils.createBooleans(con, tableName, newBoos);
			
//		String[] newInts = new String[] { 
//				"attr_str", 
//				};
//		DAOUtils.createInts(con, tableName, newInts);

	}

	PlayerCharacterEquipment dtoFromRS(ResultSet rs) throws SQLException {
		PlayerCharacterEquipment dto = new PlayerCharacterEquipment();
		int col = 1;
		dto.setPcId(rs.getInt(col++));
		dto.setEquipmentId(rs.getInt(col++));
		String et = rs.getString(col++);
		if(et!=null) {
			dto.setEquipmentType(EquipmentType.valueOf(et));
		} else {
			dto.setEquipmentType(null);
		}

		dto.setCode(rs.getString(col++));
		
		
		String where = rs.getString(col++);
		if(where!=null) {
			dto.setEquippedWhere(EquipmentWhere.valueOf(where));
		} else {
			dto.setEquippedWhere(null);
		}
	 
		dto.setEquipped(rs.getBoolean(col++));
		
		return dto;
	}

 


	 int setPSFromKeys(PlayerCharacterEquipment value, PreparedStatement ps, int col) throws SQLException {
		ps.setInt(col++, value.getPcId());
		ps.setInt(col++, value.getEquipmentId());
		return col;
	}

	 int setPSFromData(PlayerCharacterEquipment value, PreparedStatement ps, int col) throws SQLException {
 
		 
		ps.setString(col++, value.getEquipmentType().name());
		ps.setString(col++, value.getCode());	
		String ew = null;
		if(value.getEquippedWhere()!=null) {
			ew = value.getEquippedWhere().name();
		}
		ps.setString(col++, ew);
		ps.setBoolean(col++, value.isEquipped());
		
		return col;
	}

	@Override
	void setPSFromKey(PreparedStatement ps, PlayerCharacterEquipmentKey key) throws SQLException {		 
		ps.setInt(1, key.getPcId());
		ps.setInt(2, key.getEquipmentId());
	}

	@Override
	String[] getKeys() { 
		return new String[] {"pc_id","equipment_id"};
	}

	@Override
	String[] getData() { 
		return new String[] {
				"equipment_type",
				"code",
				"equipped_where",
				"equipped"
			};
	}

	@Override
	String getTableName() {
		return "player_character_equipment";
	}

	@Override
	String addOrderByClause(String sql) { 
		return sql +" ORDER BY  pc_id, where, equipment_type, equipment_id";
	}

	@Override
	String getFilterWhere() { 
		return " where like ? or equipment_type like ?";
	}

	@Override
	void setFilterParameters(PreparedStatement ps, String filter) throws SQLException {
		String f = "%" + filter.trim().toUpperCase() + "%";
		ps.setString(1, f); 
		ps.setString(2, f); 
	}

	@Override
	String getSELECTForCodedList() { 
		return  "SELECT e.pc_id, e.equipment_id ,v.name"+
				" FROM Player_Character_equipment e "+
				" JOIN view_equipment v on v.type = e.type and v.code = e.code"+
				" ORDER BY e.pc_id,v.name,e.equipment_id ";
	}
	
	//	view_equipment: type,name,code,name
	
	@Override
	void setCodedListItemFromRS(CodedListItem<PlayerCharacterEquipmentKey> dto, ResultSet rs) throws SQLException {
		int col = 1;
		PlayerCharacterEquipmentKey key = new PlayerCharacterEquipmentKey();
		key.setPcId(rs.getInt(col++));
		key.setEquipmentId(rs.getInt(col++));
		String d= rs.getString(col++);
		dto.setItem(key);
		dto.setDescription(d);
	}

 
 

	@Override
	public void deleteForPC(Connection con, int pcId) throws SQLException {
		String sql = "DELETE FROM "+getTableName()+" WHERE pc_id = ?";
		

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, pcId);
			int rows = ps.executeUpdate();

		}
	}

	@Override
	public List<PlayerCharacterEquipment> getForPC(Connection con, int pcId) throws SQLException {
		return getListFromPartialKey( con, new String[] {"pc_id"},new Object[] {pcId}) ;
	}
	
	
}
