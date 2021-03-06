package com.nobbysoft.first.server.dao;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.common.entities.DTORowListener;
import com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterHp;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterHpKey;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterLevel;
import com.nobbysoft.first.common.entities.staticdto.Alignment;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.staticdto.Gender;
import com.nobbysoft.first.common.exceptions.RecordNotFoundException;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.views.ViewPlayerCharacter;
import com.nobbysoft.first.server.utils.DbUtils;

public class PlayerCharacterDAO extends AbstractDAO<PlayerCharacter,Integer> implements DAOI<PlayerCharacter, Integer>{

	public PlayerCharacterDAO() { 

		hpDao = new PlayerCharacterHpDAO();
	}

	private final PlayerCharacterHpDAO hpDao;
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
 

	String tableName = "Player_Character";
	String sequenceName = tableName+"_seq";
	
	public int getNextid(Connection con) throws SQLException {
		String sql = "values (next value for "+sequenceName+")";
		try(PreparedStatement ps = con.prepareStatement(sql)){
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
		}
			}
		throw new SQLException("Sequence failure :(");
	}

	@Override
	public void createTable(Connection con) throws SQLException {
		/**
		 * private String classId; private String character_name; private boolean
		 * hasMagicDefenceBonus;
		 */
		String sql = "CREATE TABLE " + tableName
				+ "(pc_id integer, character_name varchar(256), "
				+ "PRIMARY KEY (pc_id) ," + " UNIQUE  (character_name) )";

		if (DbUtils.doesTableExist(con, tableName)) {

		} else {
			try (PreparedStatement ps = con.prepareStatement(sql);) {
				ps.execute();
			}
			// sequence
			String s = "CREATE SEQUENCE "+sequenceName + " start with 1";
			try (PreparedStatement ps = con.prepareStatement(s);) {
				ps.execute();
			}
		}

	
			
			String[] newStrings = new String[] { 
					"player_name",
					"alignment",
					"race_id",
					"gender",
					"first_class",
					"second_class",
					"third_class"
					};
			int[] stringLengths = new int[] {255,60,20,20,20,20,20};
			DAOUtils.createStrings(con, tableName, newStrings, stringLengths);

		String[] newInts = new String[] { 
				"attr_str",
				"attr_int",
				"attr_wis",
				"attr_dex",
				"attr_con",
				"attr_chr",
				"first_class_hp",
				"second_class_hp",
				"third_class_hp",
				"first_class_level",
				"second_class_level",
				"third_class_level",
				"exceptional_strength",
				"first_class_experience",
				"second_class_experience",
				"third_class_experience",};
		DAOUtils.createInts(con, tableName, newInts);

	}

	PlayerCharacter dtoFromRS(ResultSet rs) throws SQLException {
		PlayerCharacter dto = new PlayerCharacter();
		int col = 1;
		dto.setPcId(rs.getInt(col++));
		dto.setCharacterName(rs.getString(col++));
		dto.setPlayerName(rs.getString(col++));
		
		dto.setAttrStr(rs.getInt(col++));
		dto.setAttrInt(rs.getInt(col++));
		dto.setAttrWis(rs.getInt(col++));
		
		dto.setAttrDex(rs.getInt(col++));
		dto.setAttrCon(rs.getInt(col++));
		dto.setAttrChr(rs.getInt(col++));
		
		String align = rs.getString(col++);
		if(align!=null) {
			dto.setAlignment(Alignment.valueOf(align));
		} else {
			dto.setAlignment(null);
		}
		dto.setRaceId(rs.getString(col++));
		String gender = rs.getString(col++);
		if(gender!=null) {
			dto.setGender(Gender.valueOf(gender));
		} else {
			dto.setGender(null);
		}
		dto.setFirstClass(rs.getString(col++));
		dto.setSecondClass(rs.getString(col++));
		dto.setThirdClass(rs.getString(col++));


		dto.setFirstClassHp(rs.getInt(col++));
		dto.setSecondClassHp(rs.getInt(col++));
		dto.setThirdClassHp(rs.getInt(col++));
		dto.setFirstClassLevel(rs.getInt(col++));
		dto.setSecondClassLevel(rs.getInt(col++));
		dto.setThirdClassLevel(rs.getInt(col++));
						
		dto.setExceptionalStrength(rs.getInt(col++));
		
		dto.setFirstClassExperience(rs.getInt(col++));
		dto.setSecondClassExperience(rs.getInt(col++));
		dto.setThirdClassExperience(rs.getInt(col++));

		
		return dto;
	}

 


	 int setPSFromKeys(PlayerCharacter value, PreparedStatement ps, int col) throws SQLException {
		ps.setInt(col++, value.getPcId());
		return col;
	}

	 int setPSFromData(PlayerCharacter value, PreparedStatement ps, int col) throws SQLException {
 
		ps.setString(col++, value.getCharacterName());
		ps.setString(col++, value.getPlayerName());

		ps.setInt(col++, value.getAttrStr()); 
		ps.setInt(col++, value.getAttrInt()); 
		ps.setInt(col++, value.getAttrWis()); 
		
		ps.setInt(col++, value.getAttrDex()); 
		ps.setInt(col++, value.getAttrCon()); 
		ps.setInt(col++, value.getAttrChr()); 
		
		ps.setString(col++, value.getAlignment().name());
		ps.setString(col++, value.getRaceId());
		ps.setString(col++, value.getGender().name());

		ps.setString(col++, value.getFirstClass());
		ps.setString(col++, value.getSecondClass());
		ps.setString(col++, value.getThirdClass());

		ps.setInt(col++, value.getFirstClassHp());
		ps.setInt(col++, value.getSecondClassHp());
		ps.setInt(col++, value.getThirdClassHp()); 

		ps.setInt(col++, value.getFirstClassLevel());
		ps.setInt(col++, value.getSecondClassLevel());
		ps.setInt(col++, value.getThirdClassLevel());

		ps.setInt(col++, value.getExceptionalStrength());

		ps.setInt(col++, value.getFirstClassExperience());
		ps.setInt(col++, value.getSecondClassExperience());
		ps.setInt(col++, value.getThirdClassExperience());
		
		return col;
	}

 
	public List<ViewPlayerCharacter> getViewList(Connection con,String filter)throws SQLException{

		CharacterClassDAO ccdao = new CharacterClassDAO();
		
		Map<String,CharacterClass> classMap = ccdao.getMap(con);
		
		List<ViewPlayerCharacter> data = new ArrayList<>();
		getFilteredList(con,filter,new DTORowListener<PlayerCharacter> () {

			@Override
			public void hereHaveADTO(PlayerCharacter dto, boolean first) {
				ViewPlayerCharacter view = new ViewPlayerCharacter();
				view.setPlayerCharacter(dto);
				StringBuilder sb = new StringBuilder();
				String cn1=classMap.get(dto.getFirstClass()).getDescription();
				CharacterClass c2=classMap.get(dto.getSecondClass());
				CharacterClass c3=classMap.get(dto.getThirdClass());
				
				sb.append(cn1).append("(").append(dto.getFirstClassLevel()).append(")");
				if(c2!=null) {
					sb.append("/").append(c2.getDescription());
					sb.append("(").append(dto.getSecondClassLevel()).append(")");
				}
				if(c3!=null) {
					sb.append("/").append(c3.getDescription());
					sb.append("(").append(dto.getThirdClassLevel()).append(")");
					
				}
				view.setClassNames(sb.toString());
				data.add(view);
			}
			
		});
		
		return data;
	}

	@Override
	void setPSFromKey(PreparedStatement ps, Integer key) throws SQLException {		 
		ps.setInt(1, key);						
	}

	@Override
	String[] getKeys() { 
		return new String[] {"pc_id"};
	}

	@Override
	String[] getData() { 
		return new String[] {"character_name ",
				"player_name    ",
				"attr_str       ",
				"attr_int       ",
				"attr_wis       ",
				"attr_dex       ",
				"attr_con       ",
				"attr_chr       ",
				"alignment      ",
				"race_id        ",
				"gender         ",
				"first_class    ",
				"second_class   ",
				"third_class    ",
				"first_class_hp",
				"second_class_hp",
				"third_class_hp",
				"first_class_level",
				"second_class_level",
				"third_class_level",
				"exceptional_strength",
				"first_class_experience",
				"second_class_experience",
				"third_class_experience"
};
	}

	@Override
	String getTableName() {
		return "player_character";
	}

	@Override
	String addOrderByClause(String sql) { 
		return sql +" ORDER BY  character_name";
	}

	@Override
	String getFilterWhere() { 
		return " character_name like ? or player_name like ?";
	}

	@Override
	void setFilterParameters(PreparedStatement ps, String filter) throws SQLException {
		String f = "%" + filter.trim() + "%";
		ps.setString(1, f); 
		ps.setString(2, f); 
	}

	@Override
	String getSELECTForCodedList() { 
		return  "SELECT   pc_id, character_name   FROM Player_Character ORDER BY character_name ";
	}

	@Override
	void setCodedListItemFromRS(CodedListItem<Integer> dto, ResultSet rs) throws SQLException {
		int col = 1;
		dto.setItem(rs.getInt(col++));
		dto.setDescription(rs.getString(col++));
	}

	

	public void delete(Connection con,PlayerCharacter pc) throws SQLException {
		// need to delete from the child tables
		for( PlayerCharacterDetailI<?> pcd:new PlayerCharacterDetailI[] {
				new PlayerCharacterEquipmentDAO(),
				new PlayerCharacterHpDAO(),
				new PlayerCharacterSpellDAO(),
				}) {
			pcd.deleteForPC(con, pc.getPcId());
		}
		super.delete(con, pc);
		
	}
	
 
	public void update(Connection con,PlayerCharacter pc) throws SQLException {		
		PlayerCharacterLevel[] classDetailsBefore=get(con,pc.getPcId()).getClassDetails(); // get prior details
		super.update(con, pc);
		PlayerCharacterLevel[] classDetailsAfter=get(con,pc.getPcId()).getClassDetails(); // get after details
		for(int i=0,n=classDetailsAfter.length;i<n;i++) {
			PlayerCharacterLevel after = classDetailsAfter[i];
			PlayerCharacterLevel before = null;
			if(i<classDetailsBefore.length) {
				before = classDetailsBefore[i];
			}
			if(before==null) {
				// no "before" value so just add one
				 PlayerCharacterHp hp = new PlayerCharacterHp();
				 hp.setPcId(pc.getPcId());
				 hp.setClassId(after.getThisClass());
				 hp.setLevel(after.getThisClassLevel());
				 hp.setHpIncrement(after.getThisClassHp());
				 hpDao.insert(con, hp);
			} else {
				// before is not null
				int increment = after.getThisClassHp() - before.getThisClassHp();
				
				if(after.getThisClassLevel()!=before.getThisClassLevel()) {
					// new level - so even if increment zero we want to add a row
					 PlayerCharacterHp hp = new PlayerCharacterHp();
					 hp.setPcId(pc.getPcId());
					 hp.setClassId(after.getThisClass());
					 hp.setLevel(after.getThisClassLevel());
					 hp.setHpIncrement(increment);
					 hpDao.insert(con, hp);
				} else if (increment!=0) {
					// same level and the HP have changed! need to update the row (if its there)
					PlayerCharacterHp hp = null;
					try {
						PlayerCharacterHpKey key = new PlayerCharacterHpKey (pc.getPcId(),after.getThisClass(),after.getThisClassLevel()); 
						hp=hpDao.get(con, key);
						hp.setHpIncrement(hp.getHpIncrement()+increment);
						hpDao.update(con, hp);
						//
					} catch (Exception ex) {
						// not there insert a new one!
						 hp = new PlayerCharacterHp();
						 hp.setPcId(pc.getPcId());
						 hp.setClassId(after.getThisClass());
						 hp.setLevel(after.getThisClassLevel());
						 hp.setHpIncrement(increment);
						 hpDao.insert(con, hp);
					}
				}
			}
		}
	}
	
	
	public void insert(Connection con,PlayerCharacter pc) throws SQLException {
		super.insert(con,pc);
	 PlayerCharacterLevel[] classDetails=pc.getClassDetails();
	 for(PlayerCharacterLevel details: classDetails) {
		 if(details.getThisClass()!=null) {
			 PlayerCharacterHp hp = new PlayerCharacterHp();
			 hp.setPcId(pc.getPcId());
			 hp.setClassId(details.getThisClass());
			 hp.setLevel(details.getThisClassLevel());
			 hp.setHpIncrement(details.getThisClassHp());
			 hpDao.insert(con, hp);
		 }
	 }
	}
	
}
