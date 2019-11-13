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
import com.nobbysoft.first.common.entities.equipment.Armour;
import com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.first.common.entities.staticdto.*;
import com.nobbysoft.first.common.entities.staticdto.attributes.Constitution;
import com.nobbysoft.first.common.exceptions.RecordNotFoundException;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.utils.DbUtils;

public class CharacterClassSpellDAO extends AbstractDAO<CharacterClassSpell, CharacterClassSpellKey> implements DAOI<CharacterClassSpell, CharacterClassSpellKey> {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	public CharacterClassSpellDAO() {
	}
 
	private String tableName = "Character_Class_Spell";
	@Override
	public void createTable(Connection con) throws SQLException {
		/**
		 * private String classId; private String name; private boolean
		 * hasMagicDefenceBonus;
		 */
		String sql = "CREATE TABLE " + tableName +
				 "(class_id varchar(20), "+
				" spell_class_id varchar(20) , "+				
				" level INTEGER, "
				+ "PRIMARY KEY (class_id,spell_Class_Id,level) " + 
				"  )";

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
			String[] newInts = new String[] {
					"level_1_Spells",
					"level_2_Spells",
					"level_3_Spells",
					"level_4_Spells",
					"level_5_Spells",
					"level_6_Spells",
					"level_7_Spells",
					"level_8_Spells",
					"level_9_Spells"
			};
			DAOUtils.createInts(con, tableName, newInts);
		}
	}


	public void createConstraints(Connection con) throws SQLException{
		{
//CONSTRAINT FLTS_FK FOREIGN KEY (FLIGHT_ID, SEGMENT_NUMBER)  			REFERENCES Flights (FLIGHT_ID, SEGMENT_NUMBER)			
		// add some constraints
			String sql;
		String column = "class_id";
		String constraintName = "CCS_class_to_class";
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
		
		column = "spell_class_id";			
	 constraintName = "CCS_spell_class_to_class";
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
	}
		
	}
	
	
	@Override
	CharacterClassSpell dtoFromRS(ResultSet rs) throws SQLException {
		CharacterClassSpell dto = new CharacterClassSpell();
		int col=1;
		dto.setClassId(rs.getString(col++));
		dto.setSpellClassId(rs.getString(col++));
		dto.setLevel(rs.getInt(col++));
		dto.setLevel1Spells(rs.getInt(col++));
		dto.setLevel2Spells(rs.getInt(col++));
		dto.setLevel3Spells(rs.getInt(col++));
		dto.setLevel4Spells(rs.getInt(col++));
		dto.setLevel5Spells(rs.getInt(col++));
		dto.setLevel6Spells(rs.getInt(col++));
		dto.setLevel7Spells(rs.getInt(col++));
		dto.setLevel8Spells(rs.getInt(col++));
		dto.setLevel9Spells(rs.getInt(col++));		
		return dto;
	}

	@Override
	void setPSFromKey(PreparedStatement ps, CharacterClassSpellKey key) throws SQLException {
		ps.setString(1, key.getClassId());
		ps.setString(2, key.getSpellClassId());
		ps.setInt(3, key.getLevel());
		
	}

	@Override
	String[] getKeys() {
		return new String[] {"class_id","spell_class_id","level"};
	}

	@Override
	String[] getData() { 
		return new String[] {"level_1_Spells",
				"level_2_Spells",
				"level_3_Spells",
				"level_4_Spells",
				"level_5_Spells",
				"level_6_Spells",
				"level_7_Spells",
				"level_8_Spells",
				"level_9_Spells"};
	}

	@Override
	String getTableName() { 
		return tableName;
	}

	@Override
	String addOrderByClause(String sql) {

		return sql+" ORDER BY class_id,spell_class_id,level";
	}

	@Override
	String getFilterWhere() {
		return "  class_id = ? ";
	}

	@Override
	void setFilterParameters(PreparedStatement ps, String filter) throws SQLException {
		ps.setString(1, filter);
	}

	@Override
	int setPSFromKeys(CharacterClassSpell value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getClassId());
		ps.setString(col++, value.getSpellClassId());
		ps.setInt(col++, value.getLevel());
		return col;
	}

	@Override
	int setPSFromData(CharacterClassSpell value, PreparedStatement ps, int col) throws SQLException {
		ps.setInt(col++, value.getLevel1Spells());
		ps.setInt(col++, value.getLevel2Spells());
		ps.setInt(col++, value.getLevel3Spells());
		ps.setInt(col++, value.getLevel4Spells());
		ps.setInt(col++, value.getLevel5Spells());
		ps.setInt(col++, value.getLevel6Spells());
		ps.setInt(col++, value.getLevel7Spells());
		ps.setInt(col++, value.getLevel8Spells());
		ps.setInt(col++, value.getLevel9Spells());
		return col;
	}

	@Override
	String getSELECTForCodedList() {

		return "SELECT class_id,spell_class_id,level,level1Spells||level2Spells||level3Spells";
	}

	@Override
	void setCodedListItemFromRS(CodedListItem<CharacterClassSpellKey> dto, ResultSet rs) throws SQLException {
		CharacterClassSpellKey key = new CharacterClassSpellKey();
		key.setClassId(rs.getString(1));
		key.setSpellClassId(rs.getString(2));
		key.setLevel(rs.getInt(3));
		String de = rs.getString(4);
		dto.setItem(key);
		dto.setDescription(de);
		
	}

	
	/**
	 * this includes constitution bonuses
	 * @param con
	 * @param pcId
	 * @return
	 * @throws SQLException
	 */
	public List<CharacterClassSpell> getAllowedSpells(Connection con, int pcId) throws SQLException{
		
		List<CharacterClassSpell> allowedSpells = new ArrayList<>();
		
		// get the character
		PlayerCharacterDAO pcDAO = new PlayerCharacterDAO();
		PlayerCharacter pc = pcDAO.get(con, pcId);
		CharacterClassDAO ccDAO = new CharacterClassDAO();

		
		ConstitutionDAO conDAO = new ConstitutionDAO();
		
		WisdomDAO wisDAO = new WisdomDAO();
		
		Map<Integer,Integer> bonusSpells = wisDAO.getBonusSpells(con, pc.getAttrWis());
		
		Map<String,Integer> classAndLevel = new HashMap<>();
		classAndLevel.put(pc.getFirstClass(),pc.getFirstClassLevel());
		if(pc.getSecondClass()!=null) {
			classAndLevel.put(pc.getSecondClass(),pc.getSecondClassLevel());
			if(pc.getThirdClass()!=null) {
				classAndLevel.put(pc.getThirdClass(),pc.getThirdClassLevel());
			}

		}
		String[] queryFields = new String[] {"CLASS_ID","LEVEL"};
		for(String classId:classAndLevel.keySet()) {
			int ml = getMaxSpellLevelInTable(con,classId);
			boolean isDivine =  "D".equalsIgnoreCase( ccDAO.get(con,classId).getArcaneOrDivineMasterSpellClass());
			int lvl = classAndLevel.get(classId);
			//
			Object[] fieldValues = new Object[] {classId,(lvl<ml)?lvl:ml};
			List<CharacterClassSpell> spells =super.getListFromPartialKey(con, queryFields, fieldValues);
			for(CharacterClassSpell s:spells ) {
				s =withBonus(s,bonusSpells);
				allowedSpells.add(s);
			}			
		}
		
		
		return allowedSpells;
	}
	
	private CharacterClassSpell withBonus(CharacterClassSpell ccs,Map<Integer,Integer> bon){
		boolean anyBonus=false;
		Map<Integer,Integer> sbl = ccs.spellsByLevel();
		for(Integer level:sbl.keySet()) {
			int count =sbl.get(level);
			if(count>0) {
				Integer bonus =bon.get(level);
				if(bonus!=null) {
					int bi = bonus.intValue();
					int tot = bi+count;
					ccs.setLevelXSpell(level,tot);
					anyBonus=true;
				}
			}
		}
		

		LOGGER.info("ccs "+ccs+" any bonus?"+anyBonus);
		return ccs;
	}
	
	
	
	public int getMaxSpellLevelInTable(Connection con,String characterClassId) throws SQLException {
		
		String sql = "SELECT MAX(level) FROM "+tableName+" WHERE class_id = ?";
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.setString(1, characterClassId);
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					return rs.getInt(1);
				} else {
					return 0;
				}
			}
		}
		
	}
	
	public List<String> getSpellClassesForClasses(Connection con,List<String> classIds) throws SQLException {
		List<String> spellClassesForClasses = new ArrayList<>();
		
		String sql = "SELECT spell_class_id FROM "+tableName+" WHERE class_id = ?";
		try(PreparedStatement ps = con.prepareStatement(sql)){
			for(String characterClassId:classIds) {					
				ps.setString(1, characterClassId);
				try(ResultSet rs = ps.executeQuery()){
					while(rs.next()) {
						String cid = rs.getString(1);
						if(!spellClassesForClasses.contains(cid)) {
							spellClassesForClasses.add(cid);
						}
					} 
				}
			}
			ps.clearParameters();
		}
		return spellClassesForClasses;
	}
	

}
