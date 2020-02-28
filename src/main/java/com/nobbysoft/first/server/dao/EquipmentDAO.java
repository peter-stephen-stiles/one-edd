package com.nobbysoft.first.server.dao;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.common.entities.DTORowListener;
import com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.first.common.entities.equipment.EquipmentClass;
import com.nobbysoft.first.common.entities.equipment.EquipmentI;
import com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterLevel;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.first.common.utils.CodedListItem;

public abstract class EquipmentDAO<T extends DataDTOInterface,K extends Comparable> extends AbstractDAO<T,K> {


	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	public abstract String getEquipmentTypeString();
	
	@Override
	public void insert(Connection con, T value) throws SQLException{
		
		super.insert(con, value);
		if(value instanceof EquipmentI) {
			
			EquipmentI eq = (EquipmentI)value;
			if(eq.defaultAllClasses()) {
				String equipmentType = getEquipmentTypeString();
				
				EquipmentClassDAO ecDAO = new EquipmentClassDAO();
				CharacterClassDAO ccDAO = new CharacterClassDAO ();
				for(CharacterClass cc:ccDAO.getList(con)) {
					
					EquipmentClass ec = new  EquipmentClass();
					ec.setClassId(cc.getClassId());
					ec.setCode(eq.getCode());
					ec.setType(equipmentType);				
					ecDAO.insert(con, ec);
				}
				
			}
		}
		
	}
	
	
	public List<T> getValidEquipmentForAClass(Connection con,
			EquipmentClassDAO ecDAO,
			String equipmentType,
			String classId) throws SQLException {
		//
		final List<T> valid = new ArrayList<>();
		String[] queryFields = new String[] {"type","class_id"};
		Object[] fieldValues = new Object[] {equipmentType,classId};		
		ecDAO.getListFromPartialKey(con, queryFields, fieldValues, new DTORowListener<EquipmentClass>() {

			@Override
			public void hereHaveADTO(EquipmentClass dto, boolean first) {
				try {
				T wham = get(con,(K) dto.getCode());
				valid.add(wham);
				} catch (SQLException ex) {
					LOGGER.error("Error reading whams ",ex);
				}
			}
			
		});
		return valid;
	}
	

public List<T> getValidEquipmentForCharactersClasses(Connection con,int pcId) throws SQLException {
		
		String equipmentType = getEquipmentTypeString();
		List<T> wham = new ArrayList<>();
		RaceDAO raceDAO = new RaceDAO();
		PlayerCharacterDAO playerCharacterDAO  = new PlayerCharacterDAO ();
		EquipmentClassDAO ecDAO = new EquipmentClassDAO();
		PlayerCharacter pc = playerCharacterDAO  .get(con, pcId);
		if(pc.classCount()>1) {
			
		// difficult :(
		Race rc = raceDAO.get(con, pc.getRaceId());
		if(rc.isMultiClassable()) {
			// 	multi class character
			java.util.Set<T> set = new HashSet<>();//
			
			for(PlayerCharacterLevel pcl:pc.getClassDetails()) {
				if(pcl!=null && pcl.getThisClass()!=null) {
					set.addAll(getValidEquipmentForAClass(con,
					ecDAO,
					equipmentType,
					pcl.getThisClass()));
				}
			}
			wham.addAll(set);
		} else {
			java.util.Set<T> set = new HashSet<>();
			// dual class character
			
			PlayerCharacterLevel[] pcls=pc.getClassDetails();
			 
			List<List<T>> lists = new ArrayList<>();
			List<Integer> classLevels = new ArrayList<>();
			List<String> classes= new ArrayList<>();
			for(PlayerCharacterLevel pcl:pcls) {				
				if(pcl!=null && pcl.getThisClass()!=null) {
					classLevels.add(pcl.getThisClassLevel());
					classes.add(pcl.getThisClass());
					List<T> list = getValidEquipmentForAClass(con,
							ecDAO,
							equipmentType,
							pcl.getThisClass());
					lists.add(list);
				}
			}
			// starting position
			set.addAll(lists.get(pc.classCount()-1));
			int lastLevel = classLevels.get(pc.classCount()-1);
			for(int i=0,n=pc.classCount()-1;i<n;i++) {
				//
				// if three classes then this goes 0,1
				// we want to compare backwards, so
				// subtract that from pc.ClassCount 
				// to get 2,1; then subtract one more to show where we're at
				// because we've already got #2
				int sindex = (pc.classCount() - i) - 2;
				int thisLevel = classLevels.get(sindex);
				if(thisLevel<lastLevel) {
					String cls = classes.get(sindex);
					// we've surpassed that old class so we can use its equipment willy nilly
					set.addAll(getValidEquipmentForAClass(con,
							ecDAO,
							equipmentType,
							cls));
				}
			}
			wham.addAll(set);
		}
		} else {
			
			wham.addAll(getValidEquipmentForAClass(con,
					ecDAO,
					equipmentType,
					pc.getFirstClass()));
			
		}
		
		return wham;
	}

	
}
