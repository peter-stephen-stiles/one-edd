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
import com.nobbysoft.first.common.entities.pc.*;
import com.nobbysoft.first.common.entities.staticdto.Alignment;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.staticdto.Gender;
import com.nobbysoft.first.common.entities.staticdto.Spell;
import com.nobbysoft.first.common.exceptions.RecordNotFoundException;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.views.NameAndEncumbrance;
import com.nobbysoft.first.common.views.ViewPlayerCharacterEquipment;
import com.nobbysoft.first.common.views.ViewPlayerCharacterSpell;
import com.nobbysoft.first.server.utils.DbUtils;

public class PlayerCharacterSpellDAO 
	extends AbstractDAO<PlayerCharacterSpell,PlayerCharacterSpellKey> 
	implements DAOI<PlayerCharacterSpell, PlayerCharacterSpellKey>,PlayerCharacterDetailI<PlayerCharacterSpell>{

	public PlayerCharacterSpellDAO() { 
	}


	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
 

	String tableName = "Player_Character_spell";
 

	@Override
	public void createTable(Connection con) throws SQLException {
		/**
		 * private String classId; private String character_name; private boolean
		 * hasMagicDefenceBonus;
		 */
		String sql = "CREATE TABLE " + tableName
				+ "(pc_id integer, spell_id varchar(20), "
				+ " PRIMARY KEY (pc_id,Spell_id) )";

		if (DbUtils.doesTableExist(con, tableName)) {

		} else {
			try (PreparedStatement ps = con.prepareStatement(sql);) {
				ps.execute();
			}
 
		}



		String[] newInts = new String[] { 
				"in_memory",};
		DAOUtils.createInts(con, tableName, newInts);

	}

	PlayerCharacterSpell dtoFromRS(ResultSet rs) throws SQLException {
		PlayerCharacterSpell dto = new PlayerCharacterSpell();
		int col = 1;
		dto.setPcId(rs.getInt(col++));
		dto.setSpellId(rs.getString(col++)); 
		dto.setInMemory(rs.getInt(col++)); 
	
		
		return dto;
	}

 


	 int setPSFromKeys(PlayerCharacterSpell value, PreparedStatement ps, int col) throws SQLException {
		ps.setInt(col++, value.getPcId());
		return col;
	}

	 int setPSFromData(PlayerCharacterSpell value, PreparedStatement ps, int col) throws SQLException {
 
		ps.setString(col++, value.getSpellId());	
		ps.setInt(col++, value.getInMemory()); 
		
		
		return col;
	}

 

	@Override
	void setPSFromKey(PreparedStatement ps, PlayerCharacterSpellKey key) throws SQLException {		 
		ps.setInt(1, key.getPcId());
		ps.setString(2, key.getSpellId());
	}

	@Override
	String[] getKeys() { 
		return new String[] {"pc_id","spell_id"};
	}

	@Override
	String[] getData() { 
		return new String[] {
				"in_memory       ",				
		};
	}

	@Override
	String getTableName() {
		return tableName;
	}

	@Override
	String addOrderByClause(String sql) { 
		return sql +" ORDER BY pc_id,spell_id";
	}

	@Override
	String getFilterWhere() { 
		return " spell_id like ? ";
	}

	@Override
	void setFilterParameters(PreparedStatement ps, String filter) throws SQLException {
		String f = "%" + filter.toUpperCase().trim() + "%";
		ps.setString(1, f);  
	}

	@Override
	String getSELECTForCodedList() { 
		
		return  
			"SELECT pcs.pc_id, pcs.spell_id , s.name"+
			" FROM Player_Character_spell pcs "+
			" JOIN Spell s on s.spell_id = pcs.spell_id "+
		    " ORDER BY pcs.pc_id, s.name ";
	}

	@Override
	void setCodedListItemFromRS(CodedListItem<PlayerCharacterSpellKey> dto, ResultSet rs) throws SQLException {
		int col = 1;
		PlayerCharacterSpellKey key = new PlayerCharacterSpellKey();
		key.setPcId(rs.getInt(col++));
		key.setSpellId(rs.getString(col++));
		String desc = rs.getString(col++);
		dto.setItem(key);		
		dto.setDescription(desc);
	}

	
	public void deleteForPC(Connection con, int  pcId) throws SQLException {
		String sql = "DELETE FROM "+getTableName()+" WHERE pc_id = ?";
		

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, pcId);
			int rows = ps.executeUpdate();
		}

	}

	@Override
	public List<PlayerCharacterSpell> getForPC(Connection con, int pcId) throws SQLException {
		return getListFromPartialKey( con, new String[] {"pc_id"},new Object[] {pcId}) ;
	}

	private SpellDAO spellDAO = new SpellDAO();
	private Map<String,Spell> spellMap = new HashMap<>(); // cache for a bit :)
	
	private Spell getSpell(Connection con,String spellId) throws SQLException {
		if(spellMap.containsKey(spellId)) {
			return spellMap.get(spellId);
		}
		Spell s= spellDAO.get(con, spellId);
		spellMap.put(spellId, s);
		return s;
	}


	public List<ViewPlayerCharacterSpell> getViewForPC(Connection con, int pcId) throws SQLException {
		
		List<ViewPlayerCharacterSpell> views = new ArrayList<>();
		
		getListFromPartialKey( con, new String[] {"pc_id"},new Object[] {pcId}, new DTORowListener<PlayerCharacterSpell>() {

			@Override
			public void hereHaveADTO(PlayerCharacterSpell dto, boolean first)   {
				Spell d;
				try {
					d = getSpell(con,dto.getSpellId());					
					ViewPlayerCharacterSpell v = new ViewPlayerCharacterSpell();
					v.setPlayerCharacterSpell(dto);
					v.setSpell(d);
					views.add(v);
				} catch (SQLException e) {
					LOGGER.error("its all gone wrong for "+dto.getKey(),e);
				}
				
			}
			
		}) ;
		
		return views;
	}
	

	
}
