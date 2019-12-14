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

import com.nobbysoft.first.common.entities.staticdto.Spell;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.server.utils.DbUtils;

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

		String[] newBoos = new String[] {"type_Abjuration",
				"type_Alteration",
				"type_Charm",
				"type_Conjuration",
				"type_Divination",
				"type_Enchantment",
				"type_Evocation",
				"type_Illusion",
				"type_Invocation",
				"type_Necromantic",
				"type_Phantasm",
				"type_Possession",
				"type_Summoning"};
		DAOUtils.createBooleans(con, tableName, newBoos);

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
		
		spell.setTypeAbjuration(rs.getBoolean(col++));
		spell.setTypeAlteration(rs.getBoolean(col++));
		spell.setTypeCharm(rs.getBoolean(col++));
		spell.setTypeConjuration(rs.getBoolean(col++));
		spell.setTypeDivination(rs.getBoolean(col++));
		spell.setTypeEnchantment(rs.getBoolean(col++));
		spell.setTypeEvocation(rs.getBoolean(col++));
		spell.setTypeIllusion(rs.getBoolean(col++));
		spell.setTypeInvocation(rs.getBoolean(col++));
		spell.setTypeNecromantic(rs.getBoolean(col++));
		spell.setTypePhantasm(rs.getBoolean(col++));
		spell.setTypePossession(rs.getBoolean(col++));
		spell.setTypeSummoning(rs.getBoolean(col++));

		
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
		
		ps.setBoolean(col++,value.isTypeAbjuration());
		ps.setBoolean(col++,value.isTypeAlteration());
		ps.setBoolean(col++,value.isTypeCharm());
		ps.setBoolean(col++,value.isTypeConjuration());
		ps.setBoolean(col++,value.isTypeDivination());
		ps.setBoolean(col++,value.isTypeEnchantment());
		ps.setBoolean(col++,value.isTypeEvocation());
		ps.setBoolean(col++,value.isTypeIllusion());
		ps.setBoolean(col++,value.isTypeInvocation());
		ps.setBoolean(col++,value.isTypeNecromantic());
		ps.setBoolean(col++,value.isTypePhantasm());
		ps.setBoolean(col++,value.isTypePossession());
		ps.setBoolean(col++,value.isTypeSummoning());

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
				"type_Abjuration",
				"type_Alteration",
				"type_Charm",
				"type_Conjuration",
				"type_Divination",
				"type_Enchantment",
				"type_Evocation",
				"type_Illusion",
				"type_Invocation",
				"type_Necromantic",
				"type_Phantasm",
				"type_Possession",
				"type_Summoning"};
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

	
	public List<Spell> getSpellsNotForPC(Connection con,int pcId, int level, String spellClassId,String filterName) throws SQLException{
		
		boolean filterByLevel = (level >0);
		boolean filterBySpellClassId = (spellClassId!=null&& spellClassId.trim().length()>0);
		boolean filterByName = (filterName!=null&& filterName.trim().length()>0);

		String sql = "SELECT ";
		sql = addKeyFields(sql, "t0");
		sql = addDataFields(sql,"t0",true);
		sql = sql + " FROM "+getTableName()+" as t0 ";
		sql = sql + " WHERE NOT EXISTS(";
		sql = sql + " SELECT '.' FROM player_character_spell pcs WHERE pcs.pc_id = ? AND pcs.spell_id = t0.spell_id ";
		sql = sql + ")";
		
		if(filterByLevel) {
			sql = sql + " AND t0.level = ? ";
		}
		if(filterBySpellClassId) {
			sql = sql + " AND t0.spell_class = ? ";
		}
		if(filterByName) {
			sql = sql + " AND UPPER(t0.name) like ? ";
		}
		sql = sql+  " ORDER BY t0.spell_class, t0.level,t0.name";
		List<Spell> data = new ArrayList<>();
		boolean first=true;
		
		LOGGER.info("SQL:"+sql);
		
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			int col=1;
			ps.setLong(col++, pcId);
			if(filterByLevel) {
				ps.setLong(col++, level);
			}
			if(filterBySpellClassId) {
				ps.setString(col++, spellClassId);
			}
			if(filterByName) {
				ps.setString(col++, "%" + filterName.toUpperCase().trim()+"%");
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Spell dto = dtoFromRS(rs);
	 
					data.add(dto);
					first=false;
				}

			}
		}

		
		
		return data;
		
	}
	
}
