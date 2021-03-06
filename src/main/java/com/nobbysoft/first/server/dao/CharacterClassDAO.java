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
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.exceptions.RecordNotFoundException;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.utils.DbUtils;

public class CharacterClassDAO extends AbstractDAO<CharacterClass,String> implements DAOI<CharacterClass, String> {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	public CharacterClassDAO() {
	}

	private final String tableName = "Character_Class";
	@Override
	public void createConstraints(Connection con) throws SQLException{
		String sql;
		
//		CONSTRAINT FLTS_FK FOREIGN KEY (FLIGHT_ID, SEGMENT_NUMBER)  			REFERENCES Flights (FLIGHT_ID, SEGMENT_NUMBER)			
				// add some constraints
				String column = "parent_class_id";
				String constraintName = "CCL_class_to_parent";
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
				
		
	};
	
	
//	 
	
	@Override
	public void createTable(Connection con) throws SQLException {
		/**
		 * private String classId; private String name; private boolean
		 * hasMagicDefenceBonus;
		 */
		String sql = "CREATE TABLE " + tableName +
				 "(class_id varchar(20), "+
				" name varchar(256) , "+
				" hit_dice INTEGER, "+
				" hit_dice_at_first_level INTEGER, "+
				" max_hd_level INTEGER, "+
				" master_spell_class BOOLEAN, "
				+ "PRIMARY KEY (class_id) ," + 
				" UNIQUE  (name) )";

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
			// add some columns
			String column = "parent_class_id";
			String dataType = "varchar(20)";

			if (!DbUtils.doesTableColumnExist(con, tableName, column)) {
				sql = "ALTER TABLE " + tableName + " add column " + column + " " + dataType;
				try (PreparedStatement ps = con.prepareStatement(sql);) {
					ps.execute();
				}

			}
		}
		{
			// hpAfterNameLevel hp_after_name_level
			String column = "hp_after_name_level";
			String dataType = "INTEGER";

			if (!DbUtils.doesTableColumnExist(con, tableName, column)) {
				sql = "ALTER TABLE " + tableName + " add column " + column + " " + dataType;
				try (PreparedStatement ps = con.prepareStatement(sql);) {
					ps.execute();
				}
			}
		}
		
		 
//	turn_undead	private int turnUndead; // 0 : as cleric;  < 0 no turning; 1 one level worse than cleric; 2 two levels worse than cleric  etc
//	thief_abilities 	private int thiefAbilities; // 0 : as thief ;  < 0 no abilities; 1 one level worse than thief; 2 two levels worse than thief et

		
		String[] newInts = new String[] { "min_str", "min_int", "min_wis", "min_dex", "min_con", "min_chr",
				"prime_requisite_1", "prime_requisite_2", "prime_requisite_3", "xp_bonus_percent",
				"pr_value_for_xp_bonus","proficiencies_at_first_level","non_proficiency_penalty","new_proficiency_every_x_levels",
				"xp_Per_Level_After_Name_Level","turn_undead","thief_abilities"};
		String dataType = "INTEGER";
		for (String column : newInts) {
			if (!DbUtils.doesTableColumnExist(con, tableName, column)) {
				sql = "ALTER TABLE " + tableName + " add column " + column + " " + dataType;
				try (PreparedStatement ps = con.prepareStatement(sql);) {
					ps.execute();
				}
			}
		}
		
		
		{
			String[] newBoos = new String[] {"high_con_bonus",
					"alignment_Allowed_LG",
					"alignment_Allowed_LN",
					"alignment_Allowed_LE",
					"alignment_Allowed_NG",
					"alignment_Allowed_N",
					"alignment_Allowed_NE",
					"alignment_Allowed_CG",
					"alignment_Allowed_CN",
					"alignment_Allowed_CE"};
			DAOUtils.createBooleans(con, tableName, newBoos);
		}
		
		{
		VC[] newStrings = new VC[] {new VC("arcane_Or_Divine_Master_Spell_Class",1)};
		DAOUtils.createStrings(con, tableName, newStrings);
		}
		

	}
//
//	@Override
//	public CharacterClass get(Connection con, String key) throws SQLException {
//		String sql = "SELECT ";
//		sql = addKeyFields(sql);
//		sql = sql
//				+ "name ,hit_dice, hit_dice_at_first_level,max_hd_level, master_spell_class, parent_class_id,hp_after_name_level, "
//				+ "min_str,min_int,min_wis,min_dex,min_con,min_chr, prime_requisite_1,prime_requisite_2,prime_requisite_3,xp_bonus_percent,"+
//				"pr_value_for_xp_bonus,proficiencies_at_first_level, non_proficiency_penalty, new_proficiency_every_x_levels,high_con_bonus,"+
//				"arcane_Or_Divine_Master_Spell_Class,xp_Per_Level_After_Name_Level, turn_undead,thief_abilities";
//		sql = sql + " FROM Character_Class  WHERE ";
//		sql = addKeyColumnsForUpdate(sql);
//		//sql = sql + " class_id = ? ";
//		try (PreparedStatement ps = con.prepareStatement(sql)) {
//			ps.setString(1, key);
//			try (ResultSet rs = ps.executeQuery()) {
//				if (rs.next()) {
//					CharacterClass dto = dtoFromRS(rs);
//					return (dto);
//
//				} else {
//					throw new RecordNotFoundException("Cannot find class " + key);
//				}
//
//			}
//		}
//
//	}

	public CharacterClass dtoFromRS(ResultSet rs) throws SQLException {
		CharacterClass dto = new CharacterClass();
		int col = 1;
		dto.setClassId(rs.getString(col++));
		dto.setName(rs.getString(col++));
		dto.setHitDice(rs.getInt(col++));
		dto.setHitDiceAtFirstLevel(rs.getInt(col++));
		dto.setMaxHdLevel(rs.getInt(col++));
		dto.setMasterSpellClass(rs.getBoolean(col++));
		dto.setParentClassId(rs.getString(col++)); // , parent_class_id
		dto.setHpAfterNameLevel(rs.getInt(col++));
		dto.setMinStr(rs.getInt(col++));
		dto.setMinInt(rs.getInt(col++));
		dto.setMinWis(rs.getInt(col++));
		dto.setMinDex(rs.getInt(col++));
		dto.setMinCon(rs.getInt(col++));
		dto.setMinChr(rs.getInt(col++));
		dto.setPrimeRequisite1(rs.getInt(col++));
		dto.setPrimeRequisite2(rs.getInt(col++));
		dto.setPrimeRequisite3(rs.getInt(col++));
		dto.setXpBonusPercent(rs.getInt(col++));
		dto.setPrValueForXpBonus(rs.getInt(col++));
		dto.setProficienciesAtFirstLevel(rs.getInt(col++));
		dto.setNonProficiencyPenalty(rs.getInt(col++));
		dto.setNewProficiencyEveryXLevels(rs.getInt(col++));
		dto.setHighConBonus(rs.getBoolean(col++));
		dto.setArcaneOrDivineMasterSpellClass(rs.getString(col++));
		dto.setXpPerLevelAfterNameLevel(rs.getInt(col++));
		dto.setTurnUndead(rs.getInt(col++));
		dto.setThiefAbilities(rs.getInt(col++));
		dto.setAlignmentAllowedLG(rs.getBoolean(col++));
		dto.setAlignmentAllowedLN(rs.getBoolean(col++));
		dto.setAlignmentAllowedLE(rs.getBoolean(col++));
		dto.setAlignmentAllowedNG(rs.getBoolean(col++));
		dto.setAlignmentAllowedN(rs.getBoolean(col++));
		dto.setAlignmentAllowedNE(rs.getBoolean(col++));
		dto.setAlignmentAllowedCG(rs.getBoolean(col++));
		dto.setAlignmentAllowedCN(rs.getBoolean(col++));
		dto.setAlignmentAllowedCE(rs.getBoolean(col++));
		return dto;
	}

//	@Override
//	public void insert(Connection con, CharacterClass value) throws SQLException {
//		String sql = "INSERT INTO Character_Class ( ";
//		sql = addKeyFields(sql);
//		sql = addDataFIelds(sql);
//		sql = sql + ") values ( ";
//		sql = sql + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ";
//		sql = sql + ")";
//		try (PreparedStatement ps = con.prepareStatement(sql)) {
//			int col = 1;
//			col = setPSFromKeys(value, ps, col);
//			col = setPSFromData(value, ps, col);
//			ps.executeUpdate();
//		}
//
//	}
//
//	private String addDataFIelds(String sql) {
//		sql = sql
//				+ "name ,hit_dice, hit_dice_at_first_level,max_hd_level, master_spell_class,parent_class_id,hp_after_name_level , "
//				+ "min_str,min_int,min_wis,min_dex,min_con,min_chr, prime_requisite_1,prime_requisite_2,prime_requisite_3,"
//				+ "xp_bonus_percent,pr_value_for_xp_bonus,proficiencies_at_first_level, non_proficiency_penalty, "+
//				"new_proficiency_every_x_levels,high_con_bonus,arcane_Or_Divine_Master_Spell_Class,xp_Per_Level_After_Name_Level,"
//				+ "turn_undead , thief_abilities";
//		return sql;
//	}

	public int setPSFromKeys(CharacterClass value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getClassId());
		return col;
	}

	public int setPSFromData(CharacterClass value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getName());
		ps.setInt(col++, value.getHitDice());
		ps.setInt(col++, value.getHitDiceAtFirstLevel());
		ps.setInt(col++, value.getMaxHdLevel());
		ps.setBoolean(col++, value.isMasterSpellClass());
		ps.setString(col++, value.getParentClassId());
		ps.setInt(col++, value.getHpAfterNameLevel());
		ps.setInt(col++, value.getMinStr());
		ps.setInt(col++, value.getMinInt());
		ps.setInt(col++, value.getMinWis());
		ps.setInt(col++, value.getMinDex());
		ps.setInt(col++, value.getMinCon());
		ps.setInt(col++, value.getMinChr());
		ps.setInt(col++, value.getPrimeRequisite1());
		ps.setInt(col++, value.getPrimeRequisite2());
		ps.setInt(col++, value.getPrimeRequisite3());
		ps.setInt(col++, value.getXpBonusPercent());
		ps.setInt(col++, value.getPrValueForXpBonus());
		ps.setInt(col++, value.getProficienciesAtFirstLevel());
		ps.setInt(col++, value.getNonProficiencyPenalty());
		ps.setInt(col++, value.getNewProficiencyEveryXLevels());
		ps.setBoolean(col++, value.isHighConBonus());
		ps.setString(col++, value.getArcaneOrDivineMasterSpellClass());
		ps.setInt(col++, value.getXpPerLevelAfterNameLevel());
		ps.setInt(col++, value.getTurnUndead());
		ps.setInt(col++, value.getThiefAbilities());
		//

		ps.setBoolean(col++, value.isAlignmentAllowedLG());
		ps.setBoolean(col++, value.isAlignmentAllowedLN());
		ps.setBoolean(col++, value.isAlignmentAllowedLE());
		ps.setBoolean(col++, value.isAlignmentAllowedNG());
		ps.setBoolean(col++, value.isAlignmentAllowedN());
		ps.setBoolean(col++, value.isAlignmentAllowedNE());
		ps.setBoolean(col++, value.isAlignmentAllowedCG());
		ps.setBoolean(col++, value.isAlignmentAllowedCN());
		ps.setBoolean(col++, value.isAlignmentAllowedCE());
		return col;
	}

// 
	public Map<String,CharacterClass> getMap(Connection con) throws SQLException {
		 Map<String,CharacterClass> map = new HashMap<>();
		 getList(con,new DTORowListener<CharacterClass>() {
			@Override
			public void hereHaveADTO(CharacterClass dto, boolean first) {
				map.put(dto.getClassId(), dto);
			} 
		 });
		 return map;
	}
//	
//
//	@Override
//	public List<CharacterClass> getList(Connection con) throws SQLException {
//	return getList(con,null);
//	}
//	
//	@Override
//	public List<CharacterClass> getList(Connection con,DTORowListener<CharacterClass> listener) throws SQLException {
//		String sql = "SELECT ";
//		sql = addKeyFields(sql);
//		sql = addDataFIelds(sql);
//		sql = sql + " FROM Character_Class  ";
//		sql = addOrderByClause(sql);
//		List<CharacterClass> data = new ArrayList<>();
//		boolean first = true;
//		try (PreparedStatement ps = con.prepareStatement(sql)) {
//			try (ResultSet rs = ps.executeQuery()) {
//				while (rs.next()) {
//					CharacterClass dto = dtoFromRS(rs);
//					if(listener!=null) {
//						listener.hereHaveADTO(dto,first);
//					} else {
//						data.add(dto);
//					}
//					first = false;
//
//				}
//
//			}
//		}
//
//		return data;
//	}
////,DTORowListener<CharacterClass> listener
//	@Override
//	public List<CharacterClass> getFilteredList(Connection con, String filter) throws SQLException {
//		
//		return getFilteredList(con,filter,null);
//	}
//	
//	
//	@Override
//	public List<CharacterClass> getFilteredList(Connection con, String filter
//			,DTORowListener<CharacterClass> listener) throws SQLException {
//		if (filter == null || filter.isEmpty()) {
//			return getList(con);
//		}
//		String sql = "SELECT ";
//		sql = addKeyFields(sql);
//		sql = addDataFIelds(sql);
//		sql = sql + " FROM Character_Class  WHERE ";
//		sql = sql + "class_id like ? " + " OR name like ? ";
//		sql = addOrderByClause(sql);
//		List<CharacterClass> data = new ArrayList<>();
//		try (PreparedStatement ps = con.prepareStatement(sql)) {
//			String f = "%" + filter + "%";
//			ps.setString(1, f.toUpperCase());
//			ps.setString(2, f);
//			try (ResultSet rs = ps.executeQuery()) {
//				boolean first=true;
//				while (rs.next()) {
//					CharacterClass dto = dtoFromRS(rs);
//					if(listener!=null) {
//					listener.hereHaveADTO(dto,first);
//					} else {
//						data.add(dto);
//					}
//						first=false;
//				}
//
//			}
//		}
//
//		return data;
//	}
//
	public String addOrderByClause(String sql) {
		sql = sql + "ORDER BY name ";
		return sql;
	}

//	private String addKeyFields(String sql) {
//		sql = sql + " class_id, ";
//		return sql;
//	}

//	@Override
//	public void delete(Connection con, CharacterClass value) throws SQLException {
//		String sql = "DELETE FROM Character_Class WHERE class_id =?";
//
//		try (PreparedStatement ps = con.prepareStatement(sql)) {
//			int col = 1;
//			col = setPSFromKeys(value, ps, col);
//			int rows = ps.executeUpdate();
//			if (rows == 0) {
//				throw new RecordNotFoundException("Can't find class to delete it id=" + value.getClassId());
//			}
//		}
//
//	}
//
//	@Override
//	public void update(Connection con, CharacterClass value) throws SQLException {
//		String sql = " UPDATE Character_Class  SET ";
//		sql = addDataColumnsForUpdate(sql);
//		sql = sql + " WHERE ";
//		sql = addKeyColumnsForUpdate(sql);
//
//		try (PreparedStatement ps = con.prepareStatement(sql)) {
//			int col = 1;
//			col = setPSFromData(value, ps, col);
//
//			col = setPSFromKeys(value, ps, col);
//
//			int rows = ps.executeUpdate();
//			if (rows == 0) {
//				throw new RecordNotFoundException("Can't find class to update it id=" + value.getClassId());
//			}
//		}
//	}

//	public String addKeyColumnsForUpdate(String sql) {
//		sql = sql + "class_id = ?";
//		return sql;
//	}
//
//	public String addDataColumnsForUpdate(String sql) {
//		sql = sql + "  name  = ?, " + " hit_dice = ?, " + " hit_dice_at_first_level = ?," + " max_hd_level = ?,"
//				+ " master_spell_class = ?," + " parent_class_id = ?," + " hp_after_name_level = ?," + " min_str = ?,"
//				+ " min_int = ?," + " min_wis = ?," + " min_dex = ?," + " min_con = ?," + " min_chr = ?,"
//				+ " prime_requisite_1 = ?," + " prime_requisite_2 = ?," + " prime_requisite_3 = ?,"
//				+ " xp_bonus_percent = ?," + " pr_value_for_xp_bonus = ?,"+
//				"proficiencies_at_first_level = ?," + "non_proficiency_penalty = ?,"+ "new_proficiency_every_x_levels = ?,"
//				+"high_con_bonus = ?, arcane_Or_Divine_Master_Spell_Class = ?,xp_Per_Level_After_Name_Level=?,"+
//				"turn_undead=? , thief_abilities=?";
//		return sql;
//	}

//	@Override
//	public List<CodedListItem<String>> getAsCodedList(Connection con) throws SQLException {
//		String sql = "SELECT   class_id, name   FROM Character_Class ORDER BY name ";
//		List<CodedListItem<String>> data = new ArrayList<>();
//		try (PreparedStatement ps = con.prepareStatement(sql)) {
//			try (ResultSet rs = ps.executeQuery()) {
//				while (rs.next()) {
//					CodedListItem<String> dto = new CodedListItem<String>();
//					int col = 1;
//					dto.setItem(rs.getString(col++));
//					dto.setDescription(rs.getString(col++));
//					data.add(dto);
//				}
//
//			}
//		}
//
//		return data;
//	}

	public List<CodedListItem<String>> getSpellClassesAsCodedList(Connection con) throws SQLException {
		String sql = "SELECT   class_id, name   FROM Character_Class WHERE master_spell_class = true ORDER BY name ";
		List<CodedListItem<String>> data = new ArrayList<>();
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					CodedListItem<String> dto = new CodedListItem<String>();
					int col = 1;
					dto.setItem(rs.getString(col++));
					dto.setDescription(rs.getString(col++));
					data.add(dto);
				}

			}
		}

		return data;
	}


 

	@Override
	void setPSFromKey(PreparedStatement ps, String key) throws SQLException {
		ps.setString(1, key);		
		
	}


	@Override
	String[] getKeys() {
		return new String[]{"class_Id"};
	}


	@Override
	String[] getData() {
		return new String[] {
				"name","hit_dice","hit_dice_at_first_level","max_hd_level","master_spell_class","parent_class_id","hp_after_name_level",
				"min_str","min_int","min_wis","min_dex","min_con","min_chr","prime_requisite_1","prime_requisite_2","prime_requisite_3",
				"xp_bonus_percent","pr_value_for_xp_bonus","proficiencies_at_first_level","non_proficiency_penalty",
				"new_proficiency_every_x_levels","high_con_bonus","arcane_Or_Divine_Master_Spell_Class","xp_Per_Level_After_Name_Level",
				"turn_undead","thief_abilities",
				"alignment_Allowed_LG",
				"alignment_Allowed_LN",
				"alignment_Allowed_LE",
				"alignment_Allowed_NG",
				"alignment_Allowed_N",
				"alignment_Allowed_NE",
				"alignment_Allowed_CG",
				"alignment_Allowed_CN",
				"alignment_Allowed_CE",
		};
	}


	@Override
	String getTableName() {
		return tableName;
	}


	@Override
	String getFilterWhere() { 
		return  " class_id like ? " + " OR name like ? ";
	}


	@Override
	void setFilterParameters(PreparedStatement ps, String filter) throws SQLException {
		String f = "%" + filter + "%";
		ps.setString(1, f.toUpperCase());
		ps.setString(2, f);		
	}


	@Override
	String getSELECTForCodedList() {
		return "SELECT   class_id, name   FROM Character_Class ORDER BY name ";
	}


	@Override
	void setCodedListItemFromRS(CodedListItem<String> dto, ResultSet rs) throws SQLException {
		int col = 1;
		dto.setItem(rs.getString(col++));
		dto.setDescription(rs.getString(col++));
	}

}
