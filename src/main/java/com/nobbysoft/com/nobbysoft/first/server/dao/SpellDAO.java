package com.nobbysoft.com.nobbysoft.first.server.dao;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.com.nobbysoft.first.common.entities.DTORowListener;
import com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.Spell;
import com.nobbysoft.com.nobbysoft.first.common.exceptions.RecordNotFoundException;
import com.nobbysoft.com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.com.nobbysoft.first.server.utils.DbUtils;
import com.nobbysoft.com.nobbysoft.first.utils.DataMapper;

public class SpellDAO implements DAOI<Spell, String> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	public SpellDAO() {
	}

	@Override
	public Spell get(Connection con, String key) throws SQLException {
		String sql = "SELECT " +
				" spell_id,spell_class , level , name , "+
				" verbal , somatic , material ,"+
				" material_components , description "+
				" FROM Spell WHERE spell_id = ? "; 
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.setString(1, key);
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()){
					Spell spell = new Spell();
					int col=1;
					spell.setSpellId(rs.getString(col++));
					spell.setSpellClass(rs.getString(col++)); 
					spell.setLevel( rs.getInt(col++)); 
					spell.setName(rs.getString(col++));
					spell.setVerbal(rs.getBoolean(col++));
					spell.setSomatic(rs.getBoolean(col++)); 
					spell.setMaterial(rs.getBoolean(col++)); 
					spell.setMaterialComponents(rs.getString(col++));
					spell.setDescription(rs.getString(col++)); 
					return spell;
				} else {
					throw new RecordNotFoundException("Cannot find spell "+key);
				}
				
			}
		}
	}

	@Override
	public void insert(Connection con, Spell value) throws SQLException {
		String sql=
				"INSERT INTO Spell (spell_id , spell_class , level , name , "+
				"verbal , somatic , material ,"+
				"material_components , description ) values (?,?,?,?,?,?,?,?,?)";

		try(PreparedStatement ps = con.prepareStatement(sql)){
			int col=1;
			ps.setString(col++, value.getSpellId());
			ps.setString(col++, value.getSpellClass());
			ps.setInt(col++, value.getLevel());
			ps.setString(col++, value.getName());
			ps.setBoolean(col++, value.isVerbal());
			ps.setBoolean(col++, value.isSomatic());
			ps.setBoolean(col++, value.isMaterial());
			ps.setString(col++, value.getMaterialComponents());
			ps.setString(col++, value.getDescription());
			ps.executeUpdate();
		}

	}

	@Override
	public List<Spell> getList(Connection con) throws SQLException {
		return getList(con,null);
	}
	
	@Override
	public List<Spell> getList(Connection con,DTORowListener<Spell> listener) throws SQLException {
		String sql = "SELECT " +
				" spell_id,spell_class , level , name , "+
				" verbal , somatic , material ,"+
				" material_components , description "+
				" FROM Spell ORDER BY spell_class,level,name ";
		List<Spell> data = new ArrayList<>();
		try(PreparedStatement ps = con.prepareStatement(sql)){
			try(ResultSet rs = ps.executeQuery()){
				boolean first=true;
				while(rs.next()){
					Spell spell = new Spell();
					int col=1;
					spell.setSpellId(rs.getString(col++));
					spell.setSpellClass(rs.getString(col++)); 
					spell.setLevel( rs.getInt(col++)); 
					spell.setName(rs.getString(col++));
					spell.setVerbal(rs.getBoolean(col++));
					spell.setSomatic(rs.getBoolean(col++)); 
					spell.setMaterial(rs.getBoolean(col++)); 
					spell.setMaterialComponents(rs.getString(col++));
					spell.setDescription(rs.getString(col++));
					if(listener!=null) {
						listener.hereHaveADTO(spell, first);
					} else {
					data.add(spell);
					}
					first=false;
				}
				
			}
		}
		
		return data;
	}


	@Override
	public List<Spell> getFilteredList(Connection con, String filter) throws SQLException {
		return getFilteredList(con,filter,null);
	}
	
	@Override
	public List<Spell> getFilteredList(Connection con, String filter,
			DTORowListener<Spell> listener) throws SQLException {
		if(filter==null||filter.isEmpty()){
			return getList(con);
		}
		String sql = "SELECT " +
				" spell_id,spell_class , level , name , "+
				" verbal , somatic , material ,"+
				" material_components , description "+
				"FROM Spell "+
				" WHERE spell_id like ? "+
		        " OR name like ? " +
		        " OR spell_class like ? " +
				"ORDER BY spell_class,level,name ";
		List<Spell> data = new ArrayList<>();
		try(PreparedStatement ps = con.prepareStatement(sql)){ 
				String f = "%"+filter+"%";
				ps.setString(1, f.toUpperCase());
				ps.setString(2, f); 
				ps.setString(3, f); 
			try(ResultSet rs = ps.executeQuery()){
				boolean first=true;
				while(rs.next()){
					Spell spell = new Spell();
					int col=1;
					spell.setSpellId(rs.getString(col++));
					spell.setSpellClass(rs.getString(col++)); 
					spell.setLevel( rs.getInt(col++)); 
					spell.setName(rs.getString(col++));
					spell.setVerbal(rs.getBoolean(col++));
					spell.setSomatic(rs.getBoolean(col++)); 
					spell.setMaterial(rs.getBoolean(col++)); 
					spell.setMaterialComponents(rs.getString(col++));
					spell.setDescription(rs.getString(col++));
					if(listener!=null) {
						listener.hereHaveADTO(spell, first);
					} else {
						data.add(spell);
					}
					first=false;
				}
				
			}
		}
		
		return data;
	}

	@Override
	public void delete(Connection con, Spell value) throws SQLException {
		String sql= "DELETE FROM Spell WHERE spell_id = ?";

		try(PreparedStatement ps = con.prepareStatement(sql)){
			int col=1;
			ps.setString(col++, value.getSpellId());
			int rows=ps.executeUpdate();
			if(rows==0){
				throw new RecordNotFoundException("Can't find spell to delete it id="+value.getSpellId());
			}
		}

	}

	@Override
	public void update(Connection con, Spell value) throws SQLException {
		String sql=
				"UPDATE Spell SET spell_class =?, level  =?, name  =?, "+
				"verbal  =?, somatic  =?, material  =?,"+
				"material_components  =?, description  =? WHERE spell_id = ?";

		try(PreparedStatement ps = con.prepareStatement(sql)){
			int col=1;
			ps.setString(col++, value.getSpellClass());
			ps.setInt(col++, value.getLevel());
			ps.setString(col++, value.getName());
			ps.setBoolean(col++, value.isVerbal());
			ps.setBoolean(col++, value.isSomatic());
			ps.setBoolean(col++, value.isMaterial());
			ps.setString(col++, value.getMaterialComponents());
			ps.setString(col++, value.getDescription());
			ps.setString(col++, value.getSpellId());
			int rows=ps.executeUpdate();
			if(rows==0){
				throw new RecordNotFoundException("Can't find spell to update it id="+value.getSpellId());
			}
		}


	}

	/**
	 * 	private String spellClass;
	private int level;
	private String name;
	private boolean verbal;
	private boolean somatic;
	 */
	@Override
	public void createTable(Connection con) throws SQLException {
		String tableName = "Spell";
		String sql = "CREATE TABLE " + tableName + 
				" (spell_id varchar(20), spell_class varchar(128), level integer, name varchar(128), "+
				"verbal boolean, somatic boolean, material boolean,"+
				"material_components varchar(1000), description clob,"+
				"PRIMARY KEY(spell_id),"
				+ "UNIQUE  (spell_class,level,name) )";
		// does table already exist
		if(DbUtils.doesTableExist(con, tableName)){
			return;
		}
		try (PreparedStatement ps = con.prepareStatement(sql);) {

			ps.execute();
		}

	}

	@Override
	public List<CodedListItem<String>> getAsCodedList(Connection con) throws SQLException {
		String sql = "SELECT   spell_id, name   FROM Spell ORDER BY name ";
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

}
