package com.nobbysoft.first.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.nobbysoft.first.common.entities.equipment.EquipmentType;
import com.nobbysoft.first.common.entities.equipment.Scroll;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.utils.DbUtils; 

public class ScrollDAO extends EquipmentDAO<Scroll,String>
implements DAOI<Scroll, String> {



	public String getEquipmentTypeString() {
		return EquipmentType.SCROLL.toString();
	}
	
	
	public ScrollDAO() { 
	}

	private String tableName = "equipment_scroll";
	
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
				"encumberance_GP",	
				"count_spells"
				};
		DAOUtils.createInts(con, tableName, newInts);
		}
		{	
		VC[] newStrings = new VC[] { 
				new VC("name",60,false),
				new VC("spell_class",20)
				};		
		DAOUtils.createStrings(con, tableName, newStrings);
		}
		{
		String[] newBoos = new String[] { 
				"spell_scroll",
				};
		DAOUtils.createBooleans(con, tableName, newBoos);
		}
	}

	private String[] keys = new String[] {"code"};
	private String[] data = new String[] {
			"name",
			"encumberance_GP",
			"spell_scroll",
			"spell_class",
			"count_spells"
			};
	
	
	@Override
	Scroll dtoFromRS(ResultSet rs) throws SQLException {
		Scroll dto = new Scroll();
		int col=1;
		dto.setCode(rs.getString(col++));
		dto.setName(rs.getString(col++));
		dto.setEncumberanceGP(rs.getInt(col++));
		dto.setSpellScroll(rs.getBoolean(col++));
		dto.setSpellClass(rs.getString(col++));
		dto.setCountSpells(rs.getInt(col++));
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
		return " upper(name) like ? or code like ?";
	}

	@Override
	void setFilterParameters(PreparedStatement ps, String filter) throws SQLException {
		String f = filter.toUpperCase();
		ps.setString(1, "%"+f+"%");
		ps.setString(2, "%"+f+"%");
	}

	@Override
	int setPSFromKeys(Scroll value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getCode());
		return col;
	}

/*
		dto.setCode(rs.getString(col++));
		dto.setName(rs.getString(col++));
		dto.setEncumberanceGP(rs.getInt(col++));
		dto.setSpellScroll(rs.getBoolean(col++));
		dto.setSpellClass(rs.getString(col++));
		dto.setCountSpells(rs.getInt(col++)); 	
 */
	
	@Override
	int setPSFromData(Scroll value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getName()); 
		ps.setInt(col++, value.getEncumberanceGP());
		ps.setBoolean(col++, value.isSpellScroll());  
		ps.setString(col++, value.getSpellClass());
		ps.setInt(col++, value.getCountSpells());
		return col;
	}

	@Override
	String getSELECTForCodedList() {
		
		return "SELECT s.code,s.name, s.spell_scroll,(SELECT cc.name FROM character_class cc where cc.class_id = s.spell_class) , s.count_spells FROM " + tableName + " s ORDER BY name";
	}

	@Override
	void setCodedListItemFromRS(CodedListItem<String> dto, ResultSet rs) throws SQLException {
		int col=1;
		dto.setItem(rs.getString(col++));
		String name  =rs.getString(col++);
		boolean scrollSpell = rs.getBoolean(col++);
		String className = rs.getString(col++);
		int count = rs.getInt(col++);
		if(scrollSpell) {	
			dto.setDescription("Scroll of "+count+" "+className+" spells");
		} else {
			dto.setDescription(name);
		}
	}

}
