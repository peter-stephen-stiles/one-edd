package com.nobbysoft.com.nobbysoft.first.server.dao;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.EquipmentHands;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.EquipmentType;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.WeaponMelee;
import com.nobbysoft.com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.com.nobbysoft.first.common.utils.DICE;
import com.nobbysoft.com.nobbysoft.first.server.utils.DbUtils; 

public class WeaponMeleeDAO extends AbstractDAO<WeaponMelee,String>
implements DAOI<WeaponMelee, String> {


	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass()); 

	
	public WeaponMeleeDAO() { 
	}

	private String tableName = "weapon_melee";
	
	@Override
	public void createTable(Connection con) throws SQLException {
		String sql = "CREATE TABLE " + tableName + " (code varchar(20), PRIMARY KEY (code)  )"; 

		if (DbUtils.doesTableExist(con, tableName)) {

		} else {
			try (PreparedStatement ps = con.prepareStatement(sql);) {
				ps.execute();
			}
		}
		
		// view_equipment: type,name,code,name
		
		
		{
			String viewName="view_equipment";
		if(DbUtils.doesViewExist(con, viewName))
		{
			String view = "DROP VIEW "+viewName;
			try (PreparedStatement ps = con.prepareStatement(view);) {			
				ps.execute();
			} catch (Exception ex){
				LOGGER.info("Error in sql\n"+view);
				throw ex;
			}
		}
		
		{	
			String view = "CREATE VIEW "+viewName+" AS ("+
			" SELECT '"+EquipmentType.MELEE_WEAPON.name()+"' as type, code , name FROM weapon_melee " +
			" UNION ALL " +
			" SELECT '"+EquipmentType.WEAPON_RANGED.name()+"' as type, code , name FROM weapon_ranged " +
			" UNION ALL " +
			" SELECT '"+EquipmentType.AMMUNITION.name()+"'  as type, code , name FROM weapon_ammunition " +
			" UNION ALL " +
			" SELECT '"+EquipmentType.ARMOUR.name()+"'  as type, code , name FROM armour " +
			" UNION ALL " +
			" SELECT '"+EquipmentType.SHIELD.name()+"'  as type, code , name FROM shield " +			
			")"
			;
					
			
			try (PreparedStatement ps = con.prepareStatement(view);) {			
				ps.execute();
			} catch (Exception ex){
				LOGGER.info("Error in sql\n"+view);
				throw ex;
			}
		}
		}
		
		{
		String[] newInts = new String[] { 
				"AC_Adjustment_02",
				"AC_Adjustment_03",
				"AC_Adjustment_04",
				"AC_Adjustment_05",
				"AC_Adjustment_06",
				"AC_Adjustment_07",
				"AC_Adjustment_08",
				"AC_Adjustment_09",
				"AC_Adjustment_10",
				"capacity_GP",
				"damage_L_Dice_Count",
				"damage_SM_Dice_Count",
				"encumberance_GP",
				"speed_Factor",
				"magic_bonus",
				"extra_magic_bonus",
				"damage_L_mod",
				"damage_SM_mod"
				};
		DAOUtils.createInts(con, tableName, newInts);
		}
		{	
			String[] newStrings = new String[] { 
					"name",
					"damage_L_DICE",
					"damage_SM_DICE",
					"requires_Hands",
					"length",
					"notes",
					"space_Required",
					"extra_magic_condition",
					};
			int[] stringLengths = new int[] {60,20,20,20,20,1024,20,255};
		DAOUtils.createStrings(con, tableName, newStrings, stringLengths);
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
			"AC_Adjustment_02",
			"AC_Adjustment_03",
			"AC_Adjustment_04",
			"AC_Adjustment_05",
			"AC_Adjustment_06",
			"AC_Adjustment_07",
			"AC_Adjustment_08",
			"AC_Adjustment_09",
			"AC_Adjustment_10",
			"capacity_GP",
			"damage_L_Dice_Count",
			"damage_SM_Dice_Count",
			"encumberance_GP",
			"speed_Factor",
			"magic_bonus",
			"extra_magic_bonus",
			"damage_L_DICE",
			"damage_SM_DICE",
			"requires_Hands",
			"length",
			"notes",
			"space_Required",
			"extra_magic_condition",
			"capable_Of_Disarming_Against_AC8",
			"capable_Of_Dismounting_Rider",
			"twice_Damage_To_Large_When_Grounded_Against_Charge",
			"twice_Damage_When_Charging_On_Mount",
			"twice_Damage_When_Grounded_Against_Charge",
			"damage_L_mod",
			"damage_SM_mod"
			};
	
	
	@Override
	WeaponMelee dtoFromRS(ResultSet rs) throws SQLException {
		WeaponMelee dto = new WeaponMelee();
		int col=1;
		dto.setCode(rs.getString(col++));
		dto.setName(rs.getString(col++));
		dto.setACAdjustment02(rs.getInt(col++));
		dto.setACAdjustment03(rs.getInt(col++));
		dto.setACAdjustment04(rs.getInt(col++));
		dto.setACAdjustment05(rs.getInt(col++));
		dto.setACAdjustment06(rs.getInt(col++));
		dto.setACAdjustment07(rs.getInt(col++));
		dto.setACAdjustment08(rs.getInt(col++));
		dto.setACAdjustment09(rs.getInt(col++));
		dto.setACAdjustment10(rs.getInt(col++));
		dto.setCapacityGP(rs.getInt(col++));
		dto.setDamageLDiceCount(rs.getInt(col++));
		dto.setDamageSMDiceCount(rs.getInt(col++));
		dto.setEncumberanceGP(rs.getInt(col++));
		dto.setSpeedFactor(rs.getInt(col++));
		dto.setMagicBonus(rs.getInt(col++));
		dto.setExtraMagicBonus(rs.getInt(col++));
		dto.setDamageLDICE(DICE.fromDescription(rs.getString(col++)));
		dto.setDamageSMDICE(DICE.fromDescription(rs.getString(col++)));
		dto.setRequiresHands(EquipmentHands.valueOf(rs.getString(col++)));
		dto.setLength(rs.getString(col++));
		dto.setNotes(rs.getString(col++));
		dto.setSpaceRequired(rs.getString(col++));
		dto.setExtraMagicCondition(rs.getString(col++));
		dto.setCapableOfDisarmingAgainstAC8(rs.getBoolean(col++));
		dto.setCapableOfDismountingRider(rs.getBoolean(col++));
		dto.setTwiceDamageToLargeWhenGroundedAgainstCharge(rs.getBoolean(col++));
		dto.setTwiceDamageWhenChargingOnMount(rs.getBoolean(col++));
		dto.setTwiceDamageWhenGroundedAgainstCharge(rs.getBoolean(col++));
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
	int setPSFromKeys(WeaponMelee value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getCode());
		return col;
	}

	@Override
	int setPSFromData(WeaponMelee value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getName()); 
		ps.setInt(col++, value.getACAdjustment02());
		ps.setInt(col++, value.getACAdjustment03());
		ps.setInt(col++, value.getACAdjustment04());
		ps.setInt(col++, value.getACAdjustment05());
		ps.setInt(col++, value.getACAdjustment06());
		ps.setInt(col++, value.getACAdjustment07());
		ps.setInt(col++, value.getACAdjustment08());
		ps.setInt(col++, value.getACAdjustment09());
		ps.setInt(col++, value.getACAdjustment10());
		ps.setInt(col++, value.getCapacityGP());
		ps.setInt(col++, value.getDamageLDiceCount());
		ps.setInt(col++, value.getDamageSMDiceCount());
		ps.setInt(col++, value.getEncumberanceGP());
		ps.setInt(col++, value.getSpeedFactor());
		ps.setInt(col++, value.getMagicBonus());
		ps.setInt(col++, value.getExtraMagicBonus());
		ps.setString(col++, value.getDamageLDICE().getDesc()); 
		ps.setString(col++, value.getDamageSMDICE().getDesc()); 
		ps.setString(col++, value.getRequiresHands().name()); 
		ps.setString(col++, value.getLength()); 
		ps.setString(col++, value.getNotes()); 
		ps.setString(col++, value.getSpaceRequired()); 
		ps.setString(col++, value.getExtraMagicCondition()); 
		ps.setBoolean(col++, value.isCapableOfDisarmingAgainstAC8());
		ps.setBoolean(col++, value.isCapableOfDismountingRider());
		ps.setBoolean(col++, value.isTwiceDamageToLargeWhenGroundedAgainstCharge());
		ps.setBoolean(col++, value.isTwiceDamageWhenChargingOnMount());
		ps.setBoolean(col++, value.isTwiceDamageWhenGroundedAgainstCharge()); 
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

}
