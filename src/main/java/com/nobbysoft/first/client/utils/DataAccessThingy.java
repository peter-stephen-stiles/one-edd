package com.nobbysoft.first.client.utils;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.common.entities.staticdto.attributes.*;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.servicei.StrengthService;
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
		return null;
	}

	public Wisdom getWisdom(int wisdom) {
		return null;
	}

	public Dexterity getDexterity(int dexterity) {
		return null;
	}

	public Constitution getConstitution(int constitution) {
		return null;
	}

	public Charisma getCharisma(int charisma) {
		return null;
	}

}
