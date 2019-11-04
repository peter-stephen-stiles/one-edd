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
import com.nobbysoft.first.common.entities.pc.PlayerCharacterHp;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterHpKey;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.views.ViewPlayerCharacterHp;
import com.nobbysoft.first.server.utils.DbUtils;

public class PlayerCharacterHpDAO 
	extends AbstractDAO<PlayerCharacterHp,PlayerCharacterHpKey> 
	implements DAOI<PlayerCharacterHp, PlayerCharacterHpKey>,PlayerCharacterDetailI<PlayerCharacterHp>{

	public PlayerCharacterHpDAO() { 
	}


	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
 

	String tableName = "Player_Character_Hp";
 

	@Override
	public void createTable(Connection con) throws SQLException {
		/**
		 * private String classId; private String character_name; private boolean
		 * hasMagicDefenceBonus;
		 */
		String sql = "CREATE TABLE " + tableName
				+ "(pc_id integer, class_id varchar(20), level integer,"
				+ " PRIMARY KEY (pc_id,class_id,level) )";

		if (DbUtils.doesTableExist(con, tableName)) {

		} else {
			try (PreparedStatement ps = con.prepareStatement(sql);) {
				ps.execute();
			}
 
		}



		String[] newInts = new String[] { 
				"hp_Increment",};
		DAOUtils.createInts(con, tableName, newInts);

	}

	PlayerCharacterHp dtoFromRS(ResultSet rs) throws SQLException {
		PlayerCharacterHp dto = new PlayerCharacterHp();
		int col = 1;
		dto.setPcId(rs.getInt(col++));
		dto.setClassId(rs.getString(col++)); 
		dto.setLevel(rs.getInt(col++));
		dto.setHpIncrement(rs.getInt(col++));
	
		
		return dto;
	}

 


	 int setPSFromKeys(PlayerCharacterHp value, PreparedStatement ps, int col) throws SQLException {
		ps.setInt(col++, value.getPcId());
		ps.setString(col++, value.getClassId());
		ps.setInt(col++, value.getLevel());
		return col;
	}

	 int setPSFromData(PlayerCharacterHp value, PreparedStatement ps, int col) throws SQLException {
  
		ps.setInt(col++, value.getHpIncrement()); 
		 
		return col;
	}

 

	@Override
	void setPSFromKey(PreparedStatement ps, PlayerCharacterHpKey key) throws SQLException {		 
		ps.setInt(1, key.getPcId());
		ps.setString(2, key.getClassId());
		ps.setInt(3, key.getLevel());
	}

	@Override
	String[] getKeys() { 
		return new String[] {"pc_id","class_id","level"};
	}

	@Override
	String[] getData() { 
		return new String[] {
				"hp_increment ",				
		};
	}

	@Override
	String getTableName() {
		return tableName;
	}

	@Override
	String addOrderByClause(String sql) { 
		return sql +" ORDER BY pc_id,class_id,level";
	}

	@Override
	String getFilterWhere() { 
		return " class_id like ? ";
	}

	@Override
	void setFilterParameters(PreparedStatement ps, String filter) throws SQLException {
		String f = "%" + filter.toUpperCase().trim() + "%";
		ps.setString(1, f);  
	}

	@Override
	String getSELECTForCodedList() { 
		
		return  
			"SELECT pcs.pc_id, pcs.class_id ,pcs.level,pcs.hp_increment "+
			" FROM Player_Character_Hp pcs "+
		    " ORDER BY pcs.pc_id,pcs.level";
	}

	@Override
	void setCodedListItemFromRS(CodedListItem<PlayerCharacterHpKey> dto, ResultSet rs) throws SQLException {
		int col = 1;
		PlayerCharacterHpKey key = new PlayerCharacterHpKey();
		key.setPcId(rs.getInt(col++));
		key.setClassId(rs.getString(col++));
		key.setLevel(rs.getInt(col++));
		String desc = ""+key.toString()+" = "+ rs.getInt(col++);
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
	public List<PlayerCharacterHp> getForPC(Connection con, int pcId) throws SQLException {
		return getListFromPartialKey( con, new String[] {"pc_id"},new Object[] {pcId}) ;
	}

 


	public List<ViewPlayerCharacterHp> getViewForPC(Connection con, int pcId) throws SQLException {
		
		Map<String,CharacterClass> classes = new HashMap<>();
		CharacterClassDAO ccDAO = new CharacterClassDAO();
		
		List<ViewPlayerCharacterHp> views = new ArrayList<>();
		
		getListFromPartialKey( con, new String[] {"pc_id"},new Object[] {pcId}, new DTORowListener<PlayerCharacterHp>() {

			@Override
			public void hereHaveADTO(PlayerCharacterHp dto, boolean first)   {
			 
				try { 					
					ViewPlayerCharacterHp v = new ViewPlayerCharacterHp();
					v.setPlayerCharacterHp(dto);
					CharacterClass cc=null;
					String classId = dto.getClassId();
					if(classes.containsKey(classId)) {
						cc = classes.get(classId);
					} else {
						cc = ccDAO.get(con, classId);
						classes.put(classId, cc);
					}
					v.setClassName(cc.getName());
					views.add(v);
				} catch (Exception e) {
					LOGGER.error("its all gone wrong for "+dto.getKey(),e);
				}
				
			}
			
		}) ;
		
		return views;
	}


	

	
}
