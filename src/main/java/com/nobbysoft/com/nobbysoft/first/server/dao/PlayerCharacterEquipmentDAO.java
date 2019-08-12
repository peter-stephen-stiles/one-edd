package com.nobbysoft.com.nobbysoft.first.server.dao;

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

import com.nobbysoft.com.nobbysoft.first.common.entities.DTORowListener;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.EquipmentHands;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.EquipmentI;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.EquipmentType;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.EquipmentWhere;
import com.nobbysoft.com.nobbysoft.first.common.entities.pc.PlayerCharacterEquipment;
import com.nobbysoft.com.nobbysoft.first.common.entities.pc.PlayerCharacterEquipmentKey;
import com.nobbysoft.com.nobbysoft.first.common.exceptions.RecordNotFoundException;
import com.nobbysoft.com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.com.nobbysoft.first.common.views.NameAndEncumberence;
import com.nobbysoft.com.nobbysoft.first.common.views.ViewPlayerCharacterEquipment;
import com.nobbysoft.com.nobbysoft.first.server.utils.DbUtils;

public class PlayerCharacterEquipmentDAO 
 	extends AbstractDAO<PlayerCharacterEquipment,PlayerCharacterEquipmentKey> 
	implements DAOI<PlayerCharacterEquipment, PlayerCharacterEquipmentKey>,
	PlayerCharacterDetailI<ViewPlayerCharacterEquipment>{

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
		return sql +" ORDER BY  pc_id, equipped_where, equipment_type, equipment_id";
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

	private Map<String,NameAndEncumberence> descCache = new HashMap<>();
	private NameAndEncumberence getEquipmentDescription(Connection con,String type,String code) throws SQLException  {
		String key = type+":"+code;
		if(descCache.containsKey(key)) {
			return descCache.get(key);
		}
		String desc=null;
		int encum=0;
		String sql = "SELECT name,encumberance_GP FROM view_equipment WHERE type = ? and code = ?";
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.setString(1, type);
			ps.setString(2, code);
			try(ResultSet rs= ps.executeQuery()){
				if(rs.next()) {
					desc = rs.getString(1);		
					encum = rs.getInt(2);
				}
				
			}
			
		}
		if(desc==null) {
			desc="UNKNOWN:"+key;
		}		
		NameAndEncumberence ne = new NameAndEncumberence(desc,encum);
		descCache.put(key,ne);
		return ne;
	}
	
	@Override
	public List<ViewPlayerCharacterEquipment> getForPC(Connection con, int pcId) throws SQLException {
		
		List<ViewPlayerCharacterEquipment> views = new ArrayList<>();
		
		getListFromPartialKey( con, new String[] {"pc_id"},new Object[] {pcId}, new DTORowListener<PlayerCharacterEquipment>() {

			@Override
			public void hereHaveADTO(PlayerCharacterEquipment dto, boolean first)   {
				NameAndEncumberence d;
				try {
					d = getEquipmentDescription(con,dto.getEquipmentType().name(),dto.getCode());					
				ViewPlayerCharacterEquipment v = new ViewPlayerCharacterEquipment(dto,d.getName(),d.getEncumberence());
				views.add(v);
				} catch (SQLException e) {
					LOGGER.error("ist all gone wrong for "+dto.getKey(),e);
				}
				
			}
			
		}) ;
		
		return views;
	}
	

	public void equip(Connection con,PlayerCharacterEquipment pce,EquipmentI equipment,EquipmentWhere where) throws SQLException {
		
		// when we equip  to PACK or OTHER we don't need to unequip other things
		
		boolean unequipOthers= !((EquipmentWhere.OTHER.equals(where) ||EquipmentWhere.PACK.equals(where) ));
		
		if (unequipOthers) {

			{
				String sqlUE = "UPDATE " + tableName + " SET equipped_where = null, equipped = false"
						+ " WHERE pc_id = ? and equipped_where =?";

				try (PreparedStatement ps = con.prepareStatement(sqlUE)) {
					ps.setInt(1, pce.getPcId());
					ps.setString(2, where.getDesc());
					ps.execute();
				}
			}

			// if the weapon is double-handed, hard-coded remove L HAND and R HAND
			EquipmentHands hands = equipment.getRequiresHands();
			if (hands != null) {
				if (hands.equals(EquipmentHands.DOUBLE_HANDED)) {
					if (EquipmentWhere.HAND_L.equals(where) || EquipmentWhere.HAND_R.equals(where)) {
						String sql0 = "UPDATE " + tableName + " SET equipped_where = null, equipped = false"
								+ " WHERE pc_id = ? and ( equipped_where =? OR equipped_where =? )";
						try (PreparedStatement ps = con.prepareStatement(sql0)) {
							ps.setInt(1, pce.getPcId());
							ps.setString(2, EquipmentWhere.HAND_L.getDesc());
							ps.setString(3, EquipmentWhere.HAND_R.getDesc());
							ps.execute();
						}
					}
				} else if (hands.equals(EquipmentHands.SINGLE_HANDED)) {
					/// do we have any double-handed weapons installed?
					String sql = "SELECT t0.pc_id , t0.equipment_id, w0.code,w1.code FROM Player_Character_equipment t0 "
							+ "  LEFT OUTER JOIN weapon_melee w0 ON w0.code = t0.code AND t0.equipment_type ='MELEE_WEAPON' AND w0.requires_hands = 'DOUBLE_HANDED' "
							+ "  LEFT OUTER JOIN weapon_ranged w1 ON w1.code = t0.code AND t0.equipment_type ='RANGED_WEAPON' AND w1.requires_hands = 'DOUBLE_HANDED' "
							+ " WHERE   (w0.code IS NOT NULL OR w1.code IS NOT NULL )" + "  AND t0.pc_id = ? ";
					try (PreparedStatement ps = con.prepareStatement(sql)) {
						ps.setInt(1, pce.getPcId());
						try (ResultSet rs = ps.executeQuery()) {
							while (rs.next()) {
								int pcId = rs.getInt(1);
								int equipmentId = rs.getInt(2);

								String sqlUE = "UPDATE " + tableName + " SET equipped_where = null, equipped = false"
										+ " WHERE pc_id = ? and equipment_id =?";

								try (PreparedStatement ps2 = con.prepareStatement(sqlUE)) {
									ps2.setInt(1, pcId);
									ps2.setInt(2, equipmentId);
									ps2.execute();
								}

							}
						}
					}

				}

			}
		}
		//		
		pce.setEquipped(true);
		pce.setEquippedWhere(where);
		update(con, pce);
		
	}
	
}
