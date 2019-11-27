package com.nobbysoft.first.client.utils;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.first.common.entities.staticdto.attributes.*;
import com.nobbysoft.first.common.servicei.*;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

public class DataAccessThingy {

	public DataAccessThingy() { 
	}


	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass()); 
	
	protected DataServiceI<?, ?> getDataService(Class clazz) {
		DataServiceI s=null;
		try {
		Class d = DataMapper.INSTANCE.getServiceForEntity(clazz); 
			Constructor<DataServiceI> cc = d.getConstructor();
				s = (DataServiceI) cc.newInstance();
			} catch (Exception e) {
				throw new IllegalStateException("Can't get Service for "+clazz);
			}
		return s;
	}
	
	
	public Strength getStrength(int strength, int exceptional) {
		Strength s=null;
		try {
		StrengthService service =(StrengthService)getDataService(Strength.class);
		
		StrengthKey sk = new StrengthKey();
		sk.setAbilityScore(strength);
		if(strength==18) {
			sk.setExceptionalStrength(exceptional);
		}
	
			s= service.getStrengthFor(strength, exceptional);
		} catch (SQLException e) {
			LOGGER.error("error getting data",e);	
			throw new IllegalStateException("Unable to get strength "+strength+"/"+exceptional,e);
		}
		
		
		return s;
		
	}

	public Intelligence getIntelligence(int intelligence) {
		IntelligenceService service =(IntelligenceService)getDataService(Intelligence.class);
		try {
			return service.get(intelligence);
		} catch (SQLException e) {
			LOGGER.error("error getting data",e);	
			throw new IllegalStateException("Unable to get intelligence "+intelligence,e);
		}
		
	}

	private WisdomService wisdomService =(WisdomService)getDataService(Wisdom.class);
	private Map<Integer,Wisdom> wis = new HashMap<>();
	
	public Wisdom getWisdom(int wisdom) {
		Integer wisInt = new Integer(wisdom);
		Wisdom w;
		if(wis.containsKey(wisInt)) {
			return wis.get(wisInt);			
		} else {
			try {
				w = wisdomService.get(wisdom);
				wis.put(wisInt,w);
			} catch (SQLException e) {
				LOGGER.error("error getting data",e);	
				throw new IllegalStateException("Unable to get wisdom "+wisdom,e);
			}
		}
		return w;
	}

	public Dexterity getDexterity(int dexterity) {
		DexterityService service =(DexterityService)getDataService(Dexterity.class);
		try {
			return service.get(dexterity);
		} catch (SQLException e) {
			LOGGER.error("error getting data",e);	
			throw new IllegalStateException("Unable to get dexterity "+dexterity,e);
		}
	}

	private ConstitutionService conService =(ConstitutionService)getDataService(Constitution.class);
	private Map<Integer,Constitution> cons = new HashMap<>();
	
	public Constitution getConstitution(int constitution) {
		Integer consInt = new Integer(constitution); 
 
		Constitution c ;
		if(cons.containsKey(consInt)) {
			c = cons.get(consInt); 
		} else {
			try {
				c=conService.get(consInt);
				cons.put(consInt, c);
				
			} catch (SQLException e) {
				LOGGER.error("error getting data",e);	
				throw new IllegalStateException("Unable to get constitution "+constitution,e);
			}
		}
		return c;
	}
	
	public List<String> getDivineSpellBonus(int wisdom){
		 List<String> bs = new ArrayList<>();
		Map<Integer,Integer> bonuses = new HashMap<>();
		for(int i=3,n=wisdom;i<=n;i++) {
			Wisdom c= getWisdom(i);
			int lvl = c.getDivineSpellBonusSpellLevel();
			if(c.getDivineSpellBonusSpellLevel()>0) {
				int count = bonuses.getOrDefault(lvl, 0) + 1;				
				bonuses.put(lvl,count);
			}
		}
		Set<Integer> levels = new TreeSet();
		levels.addAll(bonuses.keySet());
		for(Integer i:levels) {
			int count = bonuses.get(i);
			bs.add("+"+count+" level "+i+" spell(s)");
		}
		return bs;
	}

	public Charisma getCharisma(int charisma) {
		CharismaService service =(CharismaService)getDataService(Charisma.class);
		try {
			return service.get(charisma);
		} catch (SQLException e) {
			LOGGER.error("error getting data",e);	
			throw new IllegalStateException("Unable to get charisma "+charisma,e);
		}
	}
	
	public Map<Comparable,String> getSavingThrowNameMap(){
		return getCodedListMap(Constants.CLI_SAVING_THROW);
	}

	public  Map<Comparable,String> getCodedListMap(String type){
		CodedListService cliDao = (CodedListService)DataMapper.INSTANCE.getNonDataService(CodedListService.class);
		try {
			return cliDao.getCodedListMap(type);
		} catch (SQLException e) {
			LOGGER.error("error getting data",e);	
			throw new IllegalStateException("Unable to get code list "+type,e);
		}
		
	}
	
	
	
}
