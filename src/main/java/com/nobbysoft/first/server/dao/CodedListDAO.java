package com.nobbysoft.first.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

import com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.first.common.entities.equipment.EquipmentType;
import com.nobbysoft.first.common.entities.equipment.EquipmentWhere;
import com.nobbysoft.first.common.entities.staticdto.Alignment;
import com.nobbysoft.first.common.entities.staticdto.Attribute;
import com.nobbysoft.first.common.entities.staticdto.Gender;
import com.nobbysoft.first.common.entities.staticdto.SavingThrowType;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.utils.DICE;

public class CodedListDAO {

	private static final String OPTION_NONE_DESCRIPTION = "(none)";

	public CodedListDAO() { 
	}
	
	public List<CodedListItem<?>> getCodedListWithNullOption(Connection con,String type)throws SQLException {
		List<CodedListItem<?>> list = getCodedList( con, type);
		list.add(0, new CodedListItem<String>(null,OPTION_NONE_DESCRIPTION));
		return list;
	}
	public Map<Comparable,String> getCodedListMapWithNullOption(Connection con,String type)throws SQLException {
		Map<Comparable,String> list = getCodedListMap( con, type);
		list.put(null,OPTION_NONE_DESCRIPTION);
		return list;
	}
	
	public Map<Comparable,String> getCodedListMap(Connection con,String type) throws SQLException {
		
		Map<Comparable,String> list = new HashMap<>();
		
		if(Constants.CLI_RACE_CLASS_MAX_LEVEL.equals(type)) {
			Map<Comparable,String> map = new HashMap<>();
			for(CodedListItem cli:getRaceClassLimitMaxLevelList()) {
				map.put(cli.getItem(), cli.getDescription());
			}
			return map;
		} else if (Constants.CLI_EQUIPMENT_TYPE.equals(type)) {
			Map<Comparable,String> map = new HashMap<>();
			for(CodedListItem cli:getEquipmentType()) {
				map.put(cli.getItem(), cli.getDescription());
			}
			return map;
		} else if (Constants.CLI_EQUIPMENT_WHERE.equals(type)) {
			Map<Comparable,String> map = new HashMap<>();
			for(CodedListItem cli:getEquipmentWhere()) {
				map.put(cli.getItem(), cli.getDescription());
			}
			return map;			
					
		} else if (Constants.CLI_DICE.equals(type)) {
			Map<Comparable,String> map = new HashMap<>();
			for(CodedListItem cli:getDice()) {
				map.put(cli.getItem(), cli.getDescription());
			}
			return map;
		} else if (Constants.CLI_ATT_ROLLS.equals(type)) {
			Map<Comparable,String> map = new HashMap<>();
			for(CodedListItem cli:getAttRolls()) {
				map.put(cli.getItem(), cli.getDescription());
			}
			return map;
		} else if (Constants.CLI_ATT_BONUS.equals(type)) {
			Map<Comparable,String> map = new HashMap<>();
			for(CodedListItem cli:getAttBonus()) {
				map.put(cli.getItem(), cli.getDescription());
			}
			return map;
		}else if (Constants.CLI_ALIGNMENT.equals(type)) {
			Map<Comparable,String> map = new HashMap<>();
			for(CodedListItem cli:getAlignment()) {
				map.put(cli.getItem(), cli.getDescription());
			}
			return map;
		}else if (Constants.CLI_GENDER.equals(type)) {
			Map<Comparable,String> map = new HashMap<>();
			for(CodedListItem cli:getGender()) {
				map.put(cli.getItem(), cli.getDescription());
			}
			return map;
		}else if (Constants.CLI_SAVING_THROW.equals(type)) {
			Map<Comparable,String> map = new HashMap<>();
			for(CodedListItem cli:getSavingThrow()) {
				map.put(cli.getItem(), cli.getDescription());
			}
			return map;

		} 
		String sql = "";
		
		sql = getSql(type, sql);
		
		try(PreparedStatement ps = con.prepareStatement(sql)){
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					String code = rs.getString(1);
					String desc = rs.getString(2);
					//CodedListItem<String> cli = new CodedListItem<>();
					list.put(code, desc);
				}
			}
			
		}
		
		return list;
	}
	
	
	
	public List<CodedListItem<?>> getCodedList(Connection con,String type) throws SQLException {
		
		List<CodedListItem<?>> list = new ArrayList<>();
		
		if(Constants.CLI_RACE_CLASS_MAX_LEVEL.equals(type)) {
			return getRaceClassLimitMaxLevelList();
		} else if (Constants.CLI_DICE.equals(type)) {
			return getDice();
		} else if (Constants.CLI_EQUIPMENT_WHERE.equals(type)) {
			return getEquipmentWhere();
		} else if (Constants.CLI_EQUIPMENT_TYPE.equals(type)) {
			return getEquipmentWhere();
		} else if(Constants.CLI_ATT_ROLLS.equals(type)) {
			return getAttRolls();
		}else if(Constants.CLI_ATT_BONUS.equals(type)) {
			return getAttBonus();
		} else if(Constants.CLI_ALIGNMENT.equals(type)) {
			return getAlignment();
		} else if(Constants.CLI_GENDER.equals(type)) {
			return getGender();
		} else if (Constants.CLI_SAVING_THROW.equals(type)) {
			return getSavingThrow();
		} 
		
		String sql = "";
		
		sql = getSql(type, sql);
		
		try(PreparedStatement ps = con.prepareStatement(sql)){
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					String code = rs.getString(1);
					String desc = rs.getString(2);
					CodedListItem<String> cli = new CodedListItem<>();
					cli.setItem(code);
					cli.setDescription(desc);
					list.add(cli);
				}
			}
			
		}
		
		return list;
	}

	public String getSql(String type, String sql) {
		if(Constants.CLI_MAGIC_CLASS.equals(type)) {
			sql = "SELECT class_id, name FROM Character_Class WHERE master_spell_class = true ORDER BY name";
		} else if(Constants.CLI_CLASS.equals(type)) {
			sql = "SELECT class_id, name FROM Character_Class ORDER BY name";
			
		} else if(Constants.CLI_RACE.equals(type)) {
			sql = "SELECT race_id, name FROM Race ORDER BY name";
			
		} else if(Constants.CLI_SPELL.equals(type)) {
			sql = "SELECT spell_id, name FROM Spell ORDER BY name";
		}else if(Constants.CLI_PLAYER_CHARACTER.equals(type)) {
			sql = "SELECT pc_id, character_name FROM Player_Character ORDER BY character_name";
		} else if(Constants.CLI_UNDEAD.equals(type)) {
			sql = "SELECT undead_Type, undead_Type_example FROM Undead_type ORDER BY undead_Type";
		}else if(Constants.CLI_THIEF_ABILITY.equals(type)) {
			sql ="SELECT Thief_Ability_Type , Thief_Ability_name from Thief_Ability_Type order by Thief_Ability_Type";
		}
		return sql;
	}

	public List<CodedListItem<?>> getGender(){
		List<CodedListItem<?>> list = new ArrayList<>();
		for(Gender g:Gender.values()) {			
			list.add(new CodedListItem(g.name(),g.name()));
		}
		return list;
	}
	
	public List<CodedListItem<?>> getSavingThrow(){
		List<CodedListItem<?>> list = new ArrayList<>();
		for(SavingThrowType g:SavingThrowType.values()) {			
			list.add(new CodedListItem(g.name(),g.getDescription()));
		}
		return list;
	}
	
	public List<CodedListItem<?>> getAlignment(){
		List<CodedListItem<?>> list = new ArrayList<>();
		for(Alignment g:Alignment.values()) {			
			list.add(new CodedListItem(g.name(),g.name()));
		}
		return list;
	}
	
	public List<CodedListItem<?>> getAttBonus(){
		
		List<CodedListItem<?>> list = new ArrayList<>();
		for(int i=-2,n=3;i<n;i++) {
			list.add(new CodedListItem(i,""+i));
		}
		return list;
	}

	public List<CodedListItem<?>> getAttributesWithZero(){
		
		List<CodedListItem<?>> list = new ArrayList<>();
		list.add(new CodedListItem(0,""));
		for(Attribute at: Attribute.values()) {
			list.add(new CodedListItem(at.getIndex(),at.getAbbr()));
		}
		return list;
	}
	
	public List<CodedListItem<?>> getAttributes(){
		
		List<CodedListItem<?>> list = new ArrayList<>();
		for(Attribute at: Attribute.values()) {
			list.add(new CodedListItem(at.getIndex(),at.getAbbr()));
		}
		return list;
	}
	
	public List<CodedListItem<?>> getAttRolls(){
		
		List<CodedListItem<?>> list = new ArrayList<>();
		for(int i=0,n=22;i<n;i++) {
			list.add(new CodedListItem(i,""+i));
		}
		return list;
	}

	public List<CodedListItem<?>> getEquipmentType(){
		
		List<CodedListItem<?>> list = new ArrayList<>();
		for(EquipmentType value:EquipmentType.values()) {
			list.add(new CodedListItem(value.name(),value.getDesc()));
		}
		return list;
	}
	
	public List<CodedListItem<?>> getEquipmentWhere(){
		
		List<CodedListItem<?>> list = new ArrayList<>();
		for(EquipmentWhere value:EquipmentWhere.values()) {
			list.add(new CodedListItem(value.name(),value.getDesc()));
		}
		return list;
	}
	
	public List<CodedListItem<?>> getDice(){
		
		List<CodedListItem<?>> list = new ArrayList<>();
		for(DICE dice:DICE.values()) {
			list.add(new CodedListItem(dice.getSides(),dice.getDesc()));
		}
		return list;
	}
	public List<CodedListItem<?>> getRaceClassLimitMaxLevelList(){
		
		List<CodedListItem<?>> list = new ArrayList<>();

		list.add(new CodedListItem<>(99,"Unlimited"));
		list.add(new CodedListItem<>(0,"No"));
		for(int i=1;i<99;i++){
			list.add(new CodedListItem<>(i,""+i));
		}
	return list;	
	}

}
