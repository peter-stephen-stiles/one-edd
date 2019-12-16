package com.nobbysoft.first.server.dao;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.common.entities.DTORowListener;
import com.nobbysoft.first.common.entities.equipment.EquipmentClass;
import com.nobbysoft.first.common.entities.equipment.EquipmentHands;
import com.nobbysoft.first.common.entities.equipment.EquipmentType;
import com.nobbysoft.first.common.entities.equipment.WeaponAmmunition;
import com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterLevel;
import com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.utils.DICE;
import com.nobbysoft.first.server.utils.DbUtils; 

public class WeaponAmmunitionDAO extends EquipmentDAO<WeaponAmmunition,String>
implements DAOI<WeaponAmmunition, String> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public WeaponAmmunitionDAO() { 
	}

	private String tableName = "weapon_ammunition";
	
	@Override
	public void createTable(Connection con) throws SQLException {
		String sql = "CREATE TABLE " + tableName + " (code varchar(20), PRIMARY KEY (code)  )"; 

		if (DbUtils.doesTableExist(con, tableName)) {

		} else {
			try (PreparedStatement ps = con.prepareStatement(sql);) {
				ps.execute();
			}
		}
		
		{
		String[] newInts = new String[] { 
				"capacity_GP",
				"damage_L_Dice_Count",
				"damage_SM_Dice_Count",
				"encumberance_GP",
				"magic_bonus",
				"extra_magic_bonus",
				"damage_L_mod",
				"damage_SM_mod"
				};
		DAOUtils.createInts(con, tableName, newInts);
		}
		{	
		VC[] newStrings = new VC[] { 
				new VC("name",60,false),
				new VC("damage_L_DICE",20),
				new VC("damage_SM_DICE",20),
				new VC("requires_Hands",20),
				new VC("notes",1024),
				new VC("extra_magic_condition",255),
				};		
		DAOUtils.createStrings(con, tableName, newStrings);
		}
		{
		String[] newBoos = new String[] { 
				"capable_Of_Disarming_Against_AC8",
				"capable_Of_Dismounting_Rider",
				"twice_Damage_To_Large_When_Grounded_Against_Charge",
				"twice_Damage_When_Charging_On_Mount",
				"twice_Damage_When_Grounded_Against_Charge",
				};
		DAOUtils.createBooleans(con, tableName, newBoos);
		}
	}

	private String[] keys = new String[] {"code"};
	private String[] data = new String[] {
			"name",
			"capacity_GP",
			"damage_L_Dice_Count",
			"damage_SM_Dice_Count",
			"encumberance_GP",
			"magic_bonus",
			"extra_magic_bonus",
			"damage_L_DICE",
			"damage_SM_DICE",
			"requires_Hands",
			"notes",
			"extra_magic_condition",
			"damage_L_mod",
			"damage_SM_mod"
			};
	
	
	@Override
	WeaponAmmunition dtoFromRS(ResultSet rs) throws SQLException {
		WeaponAmmunition dto = new WeaponAmmunition();
		int col=1;
		dto.setCode(rs.getString(col++));
		dto.setName(rs.getString(col++));
		dto.setCapacityGP(rs.getInt(col++));
		dto.setDamageLDiceCount(rs.getInt(col++));
		dto.setDamageSMDiceCount(rs.getInt(col++));
		dto.setEncumberanceGP(rs.getInt(col++));
		dto.setMagicBonus(rs.getInt(col++));
		dto.setExtraMagicBonus(rs.getInt(col++));
		dto.setDamageLDICE(DICE.fromDescription(rs.getString(col++)));
		dto.setDamageSMDICE(DICE.fromDescription(rs.getString(col++)));
		dto.setRequiresHands(EquipmentHands.valueOf(rs.getString(col++)));
		dto.setNotes(rs.getString(col++));
		dto.setExtraMagicCondition(rs.getString(col++));
		dto.setDamageLMod(rs.getInt(col++));
		dto.setDamageSMMod(rs.getInt(col++));
		return dto;
	}

	@Override
	void setPSFromKey(PreparedStatement ps, String key) throws SQLException {
		ps.setString(1, key);
	}

	@Override
	String[] getKeys() {
		return keys;
	}

	@Override
	String[] getData() {
		return data;
	}

	@Override
	String getTableName() {
		return tableName;
	}

	@Override
	String addOrderByClause(String sql) {
		return sql+" ORDER BY name";
	}

	@Override
	String getFilterWhere() {
		return " name like ? or code like ?";
	}

	@Override
	void setFilterParameters(PreparedStatement ps, String filter) throws SQLException {
		String f = filter.toUpperCase();
		ps.setString(1, "%"+filter+"%");
		ps.setString(2, "%"+f+"%");
	}

	@Override
	int setPSFromKeys(WeaponAmmunition value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getCode());
		return col;
	}

	@Override
	int setPSFromData(WeaponAmmunition value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getName()); 

		ps.setInt(col++, value.getCapacityGP());
		ps.setInt(col++, value.getDamageLDiceCount());
		ps.setInt(col++, value.getDamageSMDiceCount());
		ps.setInt(col++, value.getEncumberanceGP());
		ps.setInt(col++, value.getMagicBonus());
		ps.setInt(col++, value.getExtraMagicBonus());
		ps.setString(col++, value.getDamageLDICE().getDesc()); 
		ps.setString(col++, value.getDamageSMDICE().getDesc()); 
		ps.setString(col++, value.getRequiresHands().name()); 
		ps.setString(col++, value.getNotes()); 
		ps.setString(col++, value.getExtraMagicCondition()); 
		ps.setInt(col++, value.getDamageLMod());
		ps.setInt(col++, value.getDamageSMMod());
		return col;
	}

	@Override
	String getSELECTForCodedList() {
		
		return "SELECT code,name FROM " + tableName + " ORDER BY name";
	}

	@Override
	void setCodedListItemFromRS(CodedListItem<String> dto, ResultSet rs) throws SQLException {
		dto.setItem(rs.getString(1));
		dto.setDescription(rs.getString(2));
	}


	public String getEquipmentTypeString() {
		return EquipmentType.AMMUNITION.toString();
	}
	
		
	
}
