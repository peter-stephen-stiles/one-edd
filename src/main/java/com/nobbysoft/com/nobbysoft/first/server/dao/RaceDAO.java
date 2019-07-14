package com.nobbysoft.com.nobbysoft.first.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nobbysoft.com.nobbysoft.first.common.entities.DTORowListener;
import com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.Spell;
import com.nobbysoft.com.nobbysoft.first.common.exceptions.RecordNotFoundException;
import com.nobbysoft.com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.com.nobbysoft.first.server.utils.DbUtils;
import com.nobbysoft.com.nobbysoft.first.utils.DataMapper;

public class RaceDAO implements DAOI<Race, String> {

	public RaceDAO() {
	}

	@Override
	public void createTable(Connection con) throws SQLException {
		/**
		 * private String raceId; private String name; private boolean
		 * hasMagicDefenceBonus;
		 */
		String tableName = "Race";
		String sql = "CREATE TABLE " + tableName
				+ "(race_id varchar(20), name varchar(256), has_magic_defence_bonus boolean,"
				+ "PRIMARY KEY (race_id) ," + " UNIQUE  (name) )";

		if (!DbUtils.doesTableExist(con, tableName)) {
			try (PreparedStatement ps = con.prepareStatement(sql);) {
				ps.execute();
			}
		}

		String[] atts = new String[] { "str", "int", "wis", "dex", "con", "chr" };
		String[] sexes = new String[] { "male", "female" };
		String[] prefixes = new String[] { "min", "max" };
		String dataType = "integer";
		for (String att : atts) {
			for (String sex : sexes) {
				for (String prefix : prefixes) {
					String column = prefix + "_" + sex + "_" + att;

					if (!DbUtils.doesTableColumnExist(con, tableName, column)) {
						sql = "ALTER TABLE " + tableName + " add column " + column + " " + dataType;
						try (PreparedStatement ps = con.prepareStatement(sql);) {
							ps.execute();
						}
					}
				}
			}
		}
		for (String att : atts) {
			 {
				String prefix ="bonus";
				{
					String column = prefix + "_" + att;

					if (!DbUtils.doesTableColumnExist(con, tableName, column)) {
						sql = "ALTER TABLE " + tableName + " add column " + column + " " + dataType;
						try (PreparedStatement ps = con.prepareStatement(sql);) {
							ps.execute();
						}
					}
				}
			}
		}
		String[] newBoos = new String[] {"multi_classable"};
		DAOUtils.createBooleans(con, tableName, newBoos);
	}

	@Override
	public Race get(Connection con, String key) throws SQLException {
		String sql = "SELECT " + " race_id, name , " + " has_magic_defence_bonus, multi_classable," + 
				"min_male_str, max_male_str, min_female_str, max_female_str, min_male_int, max_male_int," +
				"min_female_int, max_female_int, min_male_wis, max_male_wis, min_female_wis, max_female_wis," + 
				"min_male_dex, max_male_dex, min_female_dex, max_female_dex, min_male_con, max_male_con, " +
				"min_female_con, max_female_con, min_male_chr, max_male_chr, min_female_chr, max_female_chr," + 
				"bonus_str, bonus_int, bonus_wis, bonus_dex, bonus_con, bonus_chr"	+				
				" FROM Race "
				+ "WHERE race_id = ? ";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, key);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Race dto = new Race();
					int col = 1;
					dto.setRaceId(rs.getString(col++));
					dto.setName(rs.getString(col++));
					dto.setHasMagicDefenceBonus(rs.getBoolean(col++));
					dto.setMultiClassable(rs.getBoolean(col++));
					dto.setMinMaleStr(rs.getInt(col++));
					dto.setMaxMaleStr(rs.getInt(col++));
					dto.setMinFemaleStr(rs.getInt(col++));
					dto.setMaxFemaleStr(rs.getInt(col++));
					dto.setMinMaleInt(rs.getInt(col++));
					dto.setMaxMaleInt(rs.getInt(col++));
					dto.setMinFemaleInt(rs.getInt(col++));
					dto.setMaxFemaleInt(rs.getInt(col++));
					dto.setMinMaleWis(rs.getInt(col++));
					dto.setMaxMaleWis(rs.getInt(col++));
					dto.setMinFemaleWis(rs.getInt(col++));
					dto.setMaxFemaleWis(rs.getInt(col++));
					dto.setMinMaleDex(rs.getInt(col++));
					dto.setMaxMaleDex(rs.getInt(col++));
					dto.setMinFemaleDex(rs.getInt(col++));
					dto.setMaxFemaleDex(rs.getInt(col++));
					dto.setMinMaleCon(rs.getInt(col++));
					dto.setMaxMaleCon(rs.getInt(col++));
					dto.setMinFemaleCon(rs.getInt(col++));
					dto.setMaxFemaleCon(rs.getInt(col++));
					dto.setMinMaleChr(rs.getInt(col++));
					dto.setMaxMaleChr(rs.getInt(col++));
					dto.setMinFemaleChr(rs.getInt(col++));
					dto.setMaxFemaleChr(rs.getInt(col++));

					dto.setBonusStr(rs.getInt(col++));
					dto.setBonusInt(rs.getInt(col++));
					dto.setBonusWis(rs.getInt(col++));
					dto.setBonusDex(rs.getInt(col++));
					dto.setBonusCon(rs.getInt(col++));
					dto.setBonusChr(rs.getInt(col++));
					return (dto);

				} else {
					throw new RecordNotFoundException("Cannot find race " + key);
				}

			}
		}

	}

	@Override
	public void insert(Connection con, Race value) throws SQLException {
		String sql = "INSERT INTO Race ( " + 
	" race_id, name , " + 
				" has_magic_defence_bonus, multi_classable" +
				"min_male_str, max_male_str, min_female_str, max_female_str, min_male_int, max_male_int," +
				"min_female_int, max_female_int, min_male_wis, max_male_wis, min_female_wis, max_female_wis," + 
				"min_male_dex, max_male_dex, min_female_dex, max_female_dex, min_male_con, max_male_con, " +
				"min_female_con, max_female_con, min_male_chr, max_male_chr, min_female_chr, max_female_chr," + 
				"bonus_str, bonus_int, bonus_wis, bonus_dex, bonus_con, bonus_chr"	+	
				") values ( ?,?,?,?," +
				"?,?,?,?,?,?," +
				"?,?,?,?,?,?," +
				"?,?,?,?,?,?," +
				"?,?,?,?,?,?," +
				"?,?,?,?,?,?" +
				")";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			int col=1;
			ps.setString(col++, value.getRaceId());
			ps.setString(col++, value.getName());
			ps.setBoolean(col++, value.isHasMagicDefenceBonus());
			ps.setBoolean(col++, value.isMultiClassable());
			ps.setInt(col++, value.getMinMaleStr());
			ps.setInt(col++, value.getMaxMaleStr());
			ps.setInt(col++, value.getMinFemaleStr());
			ps.setInt(col++, value.getMaxFemaleStr());
			ps.setInt(col++, value.getMinMaleInt());
			ps.setInt(col++, value.getMaxMaleInt());
			ps.setInt(col++, value.getMinFemaleInt());
			ps.setInt(col++, value.getMaxFemaleInt());
			ps.setInt(col++, value.getMinMaleWis());
			ps.setInt(col++, value.getMaxMaleWis());
			ps.setInt(col++, value.getMinFemaleWis());
			ps.setInt(col++, value.getMaxFemaleWis());
			ps.setInt(col++, value.getMinMaleDex());
			ps.setInt(col++, value.getMaxMaleDex());
			ps.setInt(col++, value.getMinFemaleDex());
			ps.setInt(col++, value.getMaxFemaleDex());
			ps.setInt(col++, value.getMinMaleCon());
			ps.setInt(col++, value.getMaxMaleCon());
			ps.setInt(col++, value.getMinFemaleCon());
			ps.setInt(col++, value.getMaxFemaleCon());
			ps.setInt(col++, value.getMinMaleChr());
			ps.setInt(col++, value.getMaxMaleChr());
			ps.setInt(col++, value.getMinFemaleChr());
			ps.setInt(col++, value.getMaxFemaleChr());

			ps.setInt(col++, value.getBonusStr());
			ps.setInt(col++, value.getBonusInt());
			ps.setInt(col++, value.getBonusWis());
			ps.setInt(col++, value.getBonusDex());
			ps.setInt(col++, value.getBonusCon());
			ps.setInt(col++, value.getBonusChr());
			
			ps.executeUpdate();
			}

	}


	@Override
	public List<Race> getList(Connection con) throws SQLException {
		return getList(con,null);
	}
	
	@Override
	public List<Race> getList(Connection con,DTORowListener<Race> listener) throws SQLException {
		String sql = "SELECT " + " race_id, name ," + " has_magic_defence_bonus, multi_classable," +
				"min_male_str, max_male_str, min_female_str, max_female_str, min_male_int, max_male_int," +
				"min_female_int, max_female_int, min_male_wis, max_male_wis, min_female_wis, max_female_wis," + 
				"min_male_dex, max_male_dex, min_female_dex, max_female_dex, min_male_con, max_male_con, " +
				"min_female_con, max_female_con, min_male_chr, max_male_chr, min_female_chr, max_female_chr," + 
				"bonus_str, bonus_int, bonus_wis, bonus_dex, bonus_con, bonus_chr"	+
				" FROM Race " + "ORDER BY name ";
		List<Race> data = new ArrayList<>();
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				boolean first = true;
				while (rs.next()) {
					Race dto = new Race();
					int col = 1;
					dto.setRaceId(rs.getString(col++));
					dto.setName(rs.getString(col++));
					dto.setHasMagicDefenceBonus(rs.getBoolean(col++));
					dto.setMultiClassable(rs.getBoolean(col++));

					dto.setMinMaleStr(rs.getInt(col++));
					dto.setMaxMaleStr(rs.getInt(col++));
					dto.setMinFemaleStr(rs.getInt(col++));
					dto.setMaxFemaleStr(rs.getInt(col++));
					dto.setMinMaleInt(rs.getInt(col++));
					dto.setMaxMaleInt(rs.getInt(col++));
					dto.setMinFemaleInt(rs.getInt(col++));
					dto.setMaxFemaleInt(rs.getInt(col++));
					dto.setMinMaleWis(rs.getInt(col++));
					dto.setMaxMaleWis(rs.getInt(col++));
					dto.setMinFemaleWis(rs.getInt(col++));
					dto.setMaxFemaleWis(rs.getInt(col++));
					dto.setMinMaleDex(rs.getInt(col++));
					dto.setMaxMaleDex(rs.getInt(col++));
					dto.setMinFemaleDex(rs.getInt(col++));
					dto.setMaxFemaleDex(rs.getInt(col++));
					dto.setMinMaleCon(rs.getInt(col++));
					dto.setMaxMaleCon(rs.getInt(col++));
					dto.setMinFemaleCon(rs.getInt(col++));
					dto.setMaxFemaleCon(rs.getInt(col++));
					dto.setMinMaleChr(rs.getInt(col++));
					dto.setMaxMaleChr(rs.getInt(col++));
					dto.setMinFemaleChr(rs.getInt(col++));
					dto.setMaxFemaleChr(rs.getInt(col++));

					dto.setBonusStr(rs.getInt(col++));
					dto.setBonusInt(rs.getInt(col++));
					dto.setBonusWis(rs.getInt(col++));
					dto.setBonusDex(rs.getInt(col++));
					dto.setBonusCon(rs.getInt(col++));
					dto.setBonusChr(rs.getInt(col++));
					
					if(listener!=null) {
						listener.hereHaveADTO(dto, first);
					} else {
					data.add(dto);
					}
					first=false;

				}

			}
		}

		return data;
	}

	@Override
	public List<Race> getFilteredList(Connection con, String filter) throws SQLException {
		return getFilteredList(con,filter,null);
	}
	
	@Override
	public List<Race> getFilteredList(Connection con, String filter,
			DTORowListener<Race> listener) throws SQLException {
		if (filter == null || filter.isEmpty()) {
			return getList(con);
		}
		String sql = "SELECT " + " race_id, name , " + " has_magic_defence_bonus, multi_classable,"+
				"min_male_str, max_male_str, min_female_str, max_female_str, min_male_int, max_male_int," +
				"min_female_int, max_female_int, min_male_wis, max_male_wis, min_female_wis, max_female_wis," + 
				"min_male_dex, max_male_dex, min_female_dex, max_female_dex, min_male_con, max_male_con, " +
				"min_female_con, max_female_con, min_male_chr, max_male_chr, min_female_chr, max_female_chr," + 
				"bonus_str, bonus_int, bonus_wis, bonus_dex, bonus_con, bonus_chr"	+ 
		" FROM Race "
				+ " WHERE race_id like ? " + " OR name like ? " + "ORDER BY name ";
		List<Race> data = new ArrayList<>();
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			String f = "%" + filter + "%";
			ps.setString(1, f.toUpperCase());
			ps.setString(2, f);
			try (ResultSet rs = ps.executeQuery()) {
				boolean first=true;
				while (rs.next()) {
					Race dto = new Race();
					int col = 1;
					dto.setRaceId(rs.getString(col++));
					dto.setName(rs.getString(col++));
					dto.setHasMagicDefenceBonus(rs.getBoolean(col++));
					dto.setMultiClassable(rs.getBoolean(col++));

					dto.setMinMaleStr(rs.getInt(col++));
					dto.setMaxMaleStr(rs.getInt(col++));
					dto.setMinFemaleStr(rs.getInt(col++));
					dto.setMaxFemaleStr(rs.getInt(col++));
					dto.setMinMaleInt(rs.getInt(col++));
					dto.setMaxMaleInt(rs.getInt(col++));
					dto.setMinFemaleInt(rs.getInt(col++));
					dto.setMaxFemaleInt(rs.getInt(col++));
					dto.setMinMaleWis(rs.getInt(col++));
					dto.setMaxMaleWis(rs.getInt(col++));
					dto.setMinFemaleWis(rs.getInt(col++));
					dto.setMaxFemaleWis(rs.getInt(col++));
					dto.setMinMaleDex(rs.getInt(col++));
					dto.setMaxMaleDex(rs.getInt(col++));
					dto.setMinFemaleDex(rs.getInt(col++));
					dto.setMaxFemaleDex(rs.getInt(col++));
					dto.setMinMaleCon(rs.getInt(col++));
					dto.setMaxMaleCon(rs.getInt(col++));
					dto.setMinFemaleCon(rs.getInt(col++));
					dto.setMaxFemaleCon(rs.getInt(col++));
					dto.setMinMaleChr(rs.getInt(col++));
					dto.setMaxMaleChr(rs.getInt(col++));
					dto.setMinFemaleChr(rs.getInt(col++));
					dto.setMaxFemaleChr(rs.getInt(col++));

					dto.setBonusStr(rs.getInt(col++));
					dto.setBonusInt(rs.getInt(col++));
					dto.setBonusWis(rs.getInt(col++));
					dto.setBonusDex(rs.getInt(col++));
					dto.setBonusCon(rs.getInt(col++));
					dto.setBonusChr(rs.getInt(col++));
					if(listener!=null) {
						listener.hereHaveADTO(dto, first);
					} else {
					data.add(dto);
					}
					first=false;
				}

			}
		}

		return data;
	}

	@Override
	public void delete(Connection con, Race value) throws SQLException {
		String sql = "DELETE FROM Race WHERE race_id =?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			int col = 1;
			ps.setString(col++, value.getRaceId());
			int rows = ps.executeUpdate();
			if (rows == 0) {
				throw new RecordNotFoundException("Can't find race to delete it id=" + value.getRaceId());
			}
		}

	}

	@Override
	public void update(Connection con, Race value) throws SQLException {
		String sql=
				" UPDATE Race SET   name  =?, "+
				" has_magic_defence_bonus  =?, multi_classable= ?, "+
				"min_male_str = ?,max_male_str = ?,min_female_str = ?,max_female_str = ?,min_male_int = ?,max_male_int = ?,"+
						"min_female_int = ?,max_female_int = ?,min_male_wis = ?,max_male_wis = ?,min_female_wis = ?,max_female_wis = ?,"+
						"min_male_dex = ?,max_male_dex = ?,min_female_dex = ?,max_female_dex = ?,min_male_con = ?,max_male_con = ?,"+
						"min_female_con = ?,max_female_con = ?,min_male_chr = ?,max_male_chr = ?,min_female_chr = ?,max_female_chr = ?," +
						"bonus_str = ?,bonus_int = ?,bonus_wis = ?,bonus_dex = ?,bonus_con = ?,bonus_chr = ?" +						
				" WHERE race_id = ?";

		try(PreparedStatement ps = con.prepareStatement(sql)){
			int col=1;
			ps.setString(col++, value.getName());
			ps.setBoolean(col++, value.isHasMagicDefenceBonus()); 
			ps.setBoolean(col++, value.isMultiClassable());

			ps.setInt(col++, value.getMinMaleStr());
			ps.setInt(col++, value.getMaxMaleStr());
			ps.setInt(col++, value.getMinFemaleStr());
			ps.setInt(col++, value.getMaxFemaleStr());
			ps.setInt(col++, value.getMinMaleInt());
			ps.setInt(col++, value.getMaxMaleInt());
			ps.setInt(col++, value.getMinFemaleInt());
			ps.setInt(col++, value.getMaxFemaleInt());
			ps.setInt(col++, value.getMinMaleWis());
			ps.setInt(col++, value.getMaxMaleWis());
			ps.setInt(col++, value.getMinFemaleWis());
			ps.setInt(col++, value.getMaxFemaleWis());
			ps.setInt(col++, value.getMinMaleDex());
			ps.setInt(col++, value.getMaxMaleDex());
			ps.setInt(col++, value.getMinFemaleDex());
			ps.setInt(col++, value.getMaxFemaleDex());
			ps.setInt(col++, value.getMinMaleCon());
			ps.setInt(col++, value.getMaxMaleCon());
			ps.setInt(col++, value.getMinFemaleCon());
			ps.setInt(col++, value.getMaxFemaleCon());
			ps.setInt(col++, value.getMinMaleChr());
			ps.setInt(col++, value.getMaxMaleChr());
			ps.setInt(col++, value.getMinFemaleChr());
			ps.setInt(col++, value.getMaxFemaleChr());

			ps.setInt(col++, value.getBonusStr());
			ps.setInt(col++, value.getBonusInt());
			ps.setInt(col++, value.getBonusWis());
			ps.setInt(col++, value.getBonusDex());
			ps.setInt(col++, value.getBonusCon());
			ps.setInt(col++, value.getBonusChr());
			
			ps.setString(col++, value.getRaceId()); 
			int rows=ps.executeUpdate();
			if(rows==0){
				throw new RecordNotFoundException("Can't find race to update it id="+value.getRaceId());
			}
		}
	}

	@Override
	public List<CodedListItem<String>> getAsCodedList(Connection con) throws SQLException {
		String sql = "SELECT   race_id, name   FROM Race ORDER BY name ";
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
