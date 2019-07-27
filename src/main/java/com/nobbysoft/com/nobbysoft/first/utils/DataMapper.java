package com.nobbysoft.com.nobbysoft.first.utils;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.com.nobbysoft.first.client.data.panels.*;
import com.nobbysoft.com.nobbysoft.first.client.data.panels.CharacterClassPanel;
import com.nobbysoft.com.nobbysoft.first.client.data.panels.*;
import com.nobbysoft.com.nobbysoft.first.client.data.panels.PlayerCharacterPanel;
import com.nobbysoft.com.nobbysoft.first.client.data.panels.RaceClassLimitPanel;
import com.nobbysoft.com.nobbysoft.first.client.data.panels.RacePanel;
import com.nobbysoft.com.nobbysoft.first.client.data.panels.SpellPanel;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.WeaponAmmunition;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.WeaponMelee;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.WeaponRangedThrown;
import com.nobbysoft.com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.RaceClassLimit;
import com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.Spell;
import com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.attributes.*;
import com.nobbysoft.com.nobbysoft.first.common.servicei.CharacterClassService;
import com.nobbysoft.com.nobbysoft.first.common.servicei.CodedListService;
import com.nobbysoft.com.nobbysoft.first.common.servicei.*;
import com.nobbysoft.com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.com.nobbysoft.first.common.servicei.PlayerCharacterService;
import com.nobbysoft.com.nobbysoft.first.common.servicei.RaceClassLimitService;
import com.nobbysoft.com.nobbysoft.first.common.servicei.RaceService;
import com.nobbysoft.com.nobbysoft.first.common.servicei.SpellService;
import com.nobbysoft.com.nobbysoft.first.server.dao.CharacterClassDAO;
import com.nobbysoft.com.nobbysoft.first.server.dao.*;
import com.nobbysoft.com.nobbysoft.first.server.dao.DAOI;
import com.nobbysoft.com.nobbysoft.first.server.dao.PlayerCharacterDAO;
import com.nobbysoft.com.nobbysoft.first.server.dao.RaceClassLimitDAO;
import com.nobbysoft.com.nobbysoft.first.server.dao.RaceDAO;
import com.nobbysoft.com.nobbysoft.first.server.dao.SpellDAO;
import com.nobbysoft.com.nobbysoft.first.server.service.CharacterClassServiceImpl;
import com.nobbysoft.com.nobbysoft.first.server.service.CodedListServiceImpl;
import com.nobbysoft.com.nobbysoft.first.server.service.*;
import com.nobbysoft.com.nobbysoft.first.server.service.PlayerCharacterServiceImpl;
import com.nobbysoft.com.nobbysoft.first.server.service.RaceClassLimitServiceImpl;
import com.nobbysoft.com.nobbysoft.first.server.service.RaceServiceImpl;
import com.nobbysoft.com.nobbysoft.first.server.service.SpellServiceImpl;

public enum DataMapper {
	INSTANCE;
	private DataMapper() {
	}

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	
	public Object getNonDataService(Class clazz) {
		Object dao;
		try {
		Class d = getServiceForInterface(clazz); 
			Constructor cc = d.getConstructor();
				dao =cc.newInstance();
			} catch (Exception e) {
				throw new IllegalStateException("Can't get Service for "+clazz,e);
			}
		return dao;
	}
	
	public DataServiceI getDataService(Class clazz) {
		DataServiceI dao;
		try {
		Class d = getServiceForEntity(clazz); 
			Constructor<DataServiceI> cc = d.getConstructor();
				dao = (DataServiceI) cc.newInstance();
			} catch (Exception e) {
				throw new IllegalStateException("Can't get Service for "+clazz,e);
			}
		return dao;
	}

	private Map<Class,Class> servicemap =new HashMap<>();


	private Map<Class,Class> entityservicemap =new HashMap<>();
	private Map<Class,Class> daoimap =new HashMap<>();
	private Map<Class,Class> buttonmap =new HashMap<>();
	private Map<Class<?>, String> names = new HashMap<>();
	private Map<Class<?>, Class<?>> panels = new HashMap<>();
	
	{
		buttonmap.put(CharacterClass.class, CharacterClassButtons.class);
		

		daoimap.put(PlayerCharacter.class, PlayerCharacterDAO.class);
		daoimap.put(Spell.class, SpellDAO.class);
		daoimap.put(Race.class, RaceDAO.class);
		daoimap.put(CharacterClass.class, CharacterClassDAO.class);
		daoimap.put(RaceClassLimit.class, RaceClassLimitDAO.class);
		daoimap.put(Constitution.class,ConstitutionDAO.class);
		daoimap.put(Strength.class,StrengthDAO.class);
		daoimap.put(Dexterity.class,DexterityDAO.class);
		daoimap.put(WeaponMelee.class,WeaponMeleeDAO.class);
		daoimap.put(WeaponRangedThrown.class,WeaponRangedThrownDAO.class);
		daoimap.put(WeaponAmmunition.class,WeaponAmmunitionDAO.class);

		
		
		panels.put(PlayerCharacter.class,PlayerCharacterPanel.class);
		panels.put(Spell.class,SpellPanel.class);
		panels.put(Race.class,RacePanel.class);
		panels.put(CharacterClass.class,CharacterClassPanel.class);
		panels.put(RaceClassLimit.class, RaceClassLimitPanel.class);
		panels.put(Constitution.class, ConstitutionPanel.class);
		panels.put(Strength.class, StrengthPanel.class);
		panels.put(Dexterity.class, DexterityPanel.class);
		panels.put(WeaponMelee.class, WeaponMeleePanel.class);
		panels.put(WeaponRangedThrown.class, WeaponRangedThrownPanel.class);
		panels.put(WeaponAmmunition.class, WeaponAmmunitionPanel.class);

		entityservicemap.put(PlayerCharacter.class, PlayerCharacterService.class);
		entityservicemap.put(CharacterClass.class, CharacterClassService.class);
		entityservicemap.put(Spell.class, SpellService.class);
		entityservicemap.put(Race.class, RaceService.class);
		entityservicemap.put(RaceClassLimit.class, RaceClassLimitService.class);
		entityservicemap.put(Constitution.class, ConstitutionService.class);
		entityservicemap.put(Strength.class, StrengthService.class);
		entityservicemap.put(Dexterity.class,DexterityService.class);
		entityservicemap.put(WeaponMelee.class,WeaponMeleeService.class);
		entityservicemap.put(WeaponRangedThrown.class,WeaponRangedThrownService.class);
		entityservicemap.put(WeaponAmmunition.class,WeaponAmmunitionService.class);
		
		

		servicemap.put(PlayerCharacterService.class, PlayerCharacterServiceImpl.class);
		servicemap.put(CharacterClassService.class, CharacterClassServiceImpl.class);
		servicemap.put(SpellService.class, SpellServiceImpl.class);
		servicemap.put(RaceService.class, RaceServiceImpl.class);
		servicemap.put(RaceClassLimitService.class, RaceClassLimitServiceImpl.class);
		servicemap.put(CodedListService.class, CodedListServiceImpl.class);

		servicemap.put(ConstitutionService.class, ConstitutionServiceImpl.class);
		servicemap.put(StrengthService.class, StrengthServiceImpl.class);
		servicemap.put(DexterityService.class, DexterityServiceImpl.class);
		servicemap.put(WeaponMeleeService.class, WeaponMeleeServiceImpl.class);
		servicemap.put(WeaponRangedThrownService.class, WeaponRangedThrownServiceImpl.class);
		servicemap.put(WeaponAmmunitionService.class, WeaponAmmunitionServiceImpl.class);
		
		
	}
	
	
	public Class<?> getServiceForInterface(Class<?> clazz){
		return servicemap.get(clazz);
	}
	
	public Class<?> getServiceForEntity(Class<?> clazz){
		return servicemap.get(entityservicemap.get(clazz));
	}
	
	public List<Class> getDTOs(){
		List<Class>  d =new ArrayList<>();
		d.addAll(entityservicemap.keySet());
		d.remove(PlayerCharacter.class);
		Collections.sort(d,new Comparator<Class>() {

			@Override
			public int compare(Class o1, Class o2) {
				int ret=0;
				ret = o1.getSimpleName().compareTo(o2.getSimpleName());
				if(ret==0) {
					ret = o1.getName().compareTo(o2.getName());
				}
				return ret;
			}
			
		});
		return d;
	}
	
	public Class<?> getButtonClass(Class<?> clazz){
			return buttonmap.get(clazz);
	}
	
	public Class<?> getMaintenancePanel(Class<?> clazz){
		return panels.get(clazz);
	}
	
	
	@SuppressWarnings("rawtypes")
	public Class<?> getDAO(Class clazz){
		return daoimap.get(clazz);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class<DAOI>[] getDaos(){
		return new Class[]{
			PlayerCharacterDAO.class,
			SpellDAO.class,
			RaceDAO.class,
			CharacterClassDAO.class,
			RaceClassLimitDAO.class,
			ConstitutionDAO.class,
			StrengthDAO.class,
			DexterityDAO.class,
			WeaponMeleeDAO.class,
			WeaponAmmunitionDAO.class,
			WeaponRangedThrownDAO.class,
		};
	}

 
	public String getName(Class<?> clazz){
		// get the name to use for the data class
		if(names.containsKey(clazz)){
			return names.get(clazz);
		}
		
		{
			StringBuilder sb = new StringBuilder();
			
			String cn = clazz.getSimpleName();
			
			String ucn = cn.toUpperCase();
			
			sb.append(cn.substring(0,1));
			for(int i=1,n=cn.length();i<n;i++){
				char c = cn.charAt(i);
				char uc = ucn.charAt(i);
				if(uc==c){
					sb.append(" ");
				}
			sb.append(c);
			}
			String n = sb.toString();
			names.put(clazz,n);
			return n;
		}
		
	}

}
