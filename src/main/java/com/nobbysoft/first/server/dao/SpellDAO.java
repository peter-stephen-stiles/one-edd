package com.nobbysoft.first.server.dao;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.common.entities.DTORowListener;
import com.nobbysoft.first.common.entities.equipment.WeaponMelee;
import com.nobbysoft.first.common.entities.staticdto.Spell;
import com.nobbysoft.first.common.exceptions.RecordNotFoundException;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.utils.DbUtils;
import com.nobbysoft.first.utils.DataMapper;

public class SpellDAO extends AbstractDAO<Spell, String> implements DAOI<Spell, String> {

	/*
	 * private String range; private String castingTime; private String duration;
	 * private String savingThrow; private String areaOfEffect;
	 */

	@Override
	public void createTable(Connection con) throws SQLException {
		String tableName = "Spell";
		String sql = "CREATE TABLE " + tableName
				+ " (spell_id varchar(20), spell_class varchar(128), level integer, name varchar(128), "
				+ "verbal boolean, somatic boolean, material boolean,"
				+ "material_components varchar(1000), description clob," + "PRIMARY KEY(spell_id),"
				+ "UNIQUE  (spell_class,level,name) )";
		// does table already exist
		if (!DbUtils.doesTableExist(con, tableName)) {
			try (PreparedStatement ps = con.prepareStatement(sql);) {
				ps.execute();
			};
		}
		
		VC[] newStrings= new VC[] {
				new VC("range",30),
				new VC("casting_time",30),
				new VC("duration",30),
				new VC("saving_throw",30),
				new VC("area_of_effect",60),
				new VC("spell_type",60),
				
		};
		DAOUtils.createStrings(con, tableName, newStrings);


	}

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public SpellDAO() {
	}

	@Override
	Spell dtoFromRS(ResultSet rs) throws SQLException {
		Spell spell = new Spell();
		int col = 1;
		spell.setSpellId(rs.getString(col++));
		spell.setSpellClass(rs.getString(col++));
		spell.setLevel(rs.getInt(col++));
		spell.setName(rs.getString(col++));
		spell.setVerbal(rs.getBoolean(col++));
		spell.setSomatic(rs.getBoolean(col++));
		spell.setMaterial(rs.getBoolean(col++));
		spell.setMaterialComponents(rs.getString(col++));
		spell.setDescription(rs.getString(col++));
		spell.setRange(rs.getString(col++));
		spell.setCastingTime(rs.getString(col++));
		spell.setDuration(rs.getString(col++));
		spell.setSavingThrow(rs.getString(col++));
		spell.setAreaOfEffect(rs.getString(col++));
		spell.setSpellType(rs.getString(col++));
		
		return spell;
	}

	@Override
	void setPSFromKey(PreparedStatement ps, String key) throws SQLException {
		ps.setString(1, key);

	}

	@Override
	int setPSFromKeys(Spell value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getSpellId());
		return col;
	}

	@Override
	int setPSFromData(Spell value, PreparedStatement ps, int col) throws SQLException {
		ps.setString(col++, value.getSpellClass());
		ps.setInt(col++, value.getLevel());
		ps.setString(col++, value.getName());
		ps.setBoolean(col++, value.isVerbal());
		ps.setBoolean(col++, value.isSomatic());
		ps.setBoolean(col++, value.isMaterial());
		ps.setString(col++, value.getMaterialComponents());
		ps.setString(col++, value.getDescription());

		ps.setString(col++, value.getRange());
		ps.setString(col++, value.getCastingTime());
		ps.setString(col++, value.getDuration());
		ps.setString(col++, value.getSavingThrow());
		ps.setString(col++, value.getAreaOfEffect());
		ps.setString(col++, value.getSpellType());
		return col;
	}

	@Override
	String[] getKeys() {
		return new String[] { "spell_id" };
	}

	@Override
	String[] getData() {
		return new String[] { "spell_class", "level", "name", "verbal", "somatic", "material", "material_components",
				"description", 
				"range",
				"casting_time",
				"duration",
				"saving_throw",
				"area_of_effect",
				"spell_type"};
	}

	@Override
	String getTableName() {
		return "Spell";
	}

	@Override
	String addOrderByClause(String sql) {
		return sql + " ORDER BY spell_class, level,name";
	}

	@Override
	String getFilterWhere() {
		return "  spell_id like ? " + " OR upper(name) like ? " + " OR upper(spell_class) like ? ";
	}

	@Override
	void setFilterParameters(PreparedStatement ps, String filter) throws SQLException {
		String f = ("%" + filter + "%").toUpperCase();
		ps.setString(1, f);
		ps.setString(2, f);
		ps.setString(3, f);

	}

	@Override
	String getSELECTForCodedList() {
		return "SELECT spell_id, name FROM Spell ORDER BY name ";
	}

	@Override
	void setCodedListItemFromRS(CodedListItem<String> dto, ResultSet rs) throws SQLException {
		int col = 1;
		dto.setItem(rs.getString(col++));
		dto.setDescription(rs.getString(col++));
	}

}
