package com.nobbysoft.first.utils;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.data.panels.attributes.CharismaPanel;
import com.nobbysoft.first.client.data.panels.attributes.ConstitutionPanel;
import com.nobbysoft.first.client.data.panels.attributes.DexterityPanel;
import com.nobbysoft.first.client.data.panels.attributes.IntelligencePanel;
import com.nobbysoft.first.client.data.panels.attributes.StrengthPanel;
import com.nobbysoft.first.client.data.panels.attributes.WisdomPanel;
import com.nobbysoft.first.client.data.panels.equipment.ArmourPanel;
import com.nobbysoft.first.client.data.panels.equipment.ShieldPanel;
import com.nobbysoft.first.client.data.panels.equipment.WeaponAmmunitionPanel;
import com.nobbysoft.first.client.data.panels.equipment.WeaponMeleePanel;
import com.nobbysoft.first.client.data.panels.equipment.WeaponRangedPanel;
import com.nobbysoft.first.client.data.panels.pc.PlayerCharacterButtons;
import com.nobbysoft.first.client.data.panels.pc.PlayerCharacterPanel;
import com.nobbysoft.first.client.data.panels.staticdata.CharacterClassButtons;
import com.nobbysoft.first.client.data.panels.staticdata.CharacterClassPanel;
import com.nobbysoft.first.client.data.panels.staticdata.RaceClassLimitPanel;
import com.nobbysoft.first.client.data.panels.staticdata.RacePanel;
import com.nobbysoft.first.client.data.panels.staticdata.SpellPanel;
import com.nobbysoft.first.common.entities.equipment.Armour;
import com.nobbysoft.first.common.entities.equipment.EquipmentType;
import com.nobbysoft.first.common.entities.equipment.Shield;
import com.nobbysoft.first.common.entities.equipment.WeaponAmmunition;
import com.nobbysoft.first.common.entities.equipment.WeaponMelee;
import com.nobbysoft.first.common.entities.equipment.WeaponRanged;
import com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterEquipment;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterHp;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterSpell;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassLevel;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassSpell;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassToHit;
import com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.first.common.entities.staticdto.RaceClassLimit;
import com.nobbysoft.first.common.entities.staticdto.SavingThrow;
import com.nobbysoft.first.common.entities.staticdto.Spell;
import com.nobbysoft.first.common.entities.staticdto.attributes.Charisma;
import com.nobbysoft.first.common.entities.staticdto.attributes.Constitution;
import com.nobbysoft.first.common.entities.staticdto.attributes.Dexterity;
import com.nobbysoft.first.common.entities.staticdto.attributes.Intelligence;
import com.nobbysoft.first.common.entities.staticdto.attributes.Strength;
import com.nobbysoft.first.common.entities.staticdto.attributes.Wisdom;
import com.nobbysoft.first.common.servicei.ArmourService;
import com.nobbysoft.first.common.servicei.CharacterClassLevelService;
import com.nobbysoft.first.common.servicei.CharacterClassService;
import com.nobbysoft.first.common.servicei.CharacterClassSpellService;
import com.nobbysoft.first.common.servicei.CharacterClassToHitService;
import com.nobbysoft.first.common.servicei.CharismaService;
import com.nobbysoft.first.common.servicei.CodedListService;
import com.nobbysoft.first.common.servicei.ConstitutionService;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.servicei.DexterityService;
import com.nobbysoft.first.common.servicei.IntelligenceService;
import com.nobbysoft.first.common.servicei.PlayerCharacterEquipmentService;
import com.nobbysoft.first.common.servicei.PlayerCharacterHpService;
import com.nobbysoft.first.common.servicei.PlayerCharacterService;
import com.nobbysoft.first.common.servicei.PlayerCharacterSpellService;
import com.nobbysoft.first.common.servicei.RaceClassLimitService;
import com.nobbysoft.first.common.servicei.RaceService;
import com.nobbysoft.first.common.servicei.SavingThrowService;
import com.nobbysoft.first.common.servicei.ShieldService;
import com.nobbysoft.first.common.servicei.SpellService;
import com.nobbysoft.first.common.servicei.SqlService;
import com.nobbysoft.first.common.servicei.StrengthService;
import com.nobbysoft.first.common.servicei.WeaponAmmunitionService;
import com.nobbysoft.first.common.servicei.WeaponMeleeService;
import com.nobbysoft.first.common.servicei.WeaponRangedService;
import com.nobbysoft.first.common.servicei.WisdomService;
import com.nobbysoft.first.server.dao.ArmourDAO;
import com.nobbysoft.first.server.dao.CharacterClassDAO;
import com.nobbysoft.first.server.dao.CharacterClassLevelDAO;
import com.nobbysoft.first.server.dao.CharacterClassSpellDAO;
import com.nobbysoft.first.server.dao.CharacterClassToHitDAO;
import com.nobbysoft.first.server.dao.CharismaDAO;
import com.nobbysoft.first.server.dao.ConstitutionDAO;
import com.nobbysoft.first.server.dao.CreateInterface;
import com.nobbysoft.first.server.dao.DexterityDAO;
import com.nobbysoft.first.server.dao.IntelligenceDAO;
import com.nobbysoft.first.server.dao.PlayerCharacterDAO;
import com.nobbysoft.first.server.dao.PlayerCharacterEquipmentDAO;
import com.nobbysoft.first.server.dao.PlayerCharacterHpDAO;
import com.nobbysoft.first.server.dao.PlayerCharacterSpellDAO;
import com.nobbysoft.first.server.dao.RaceClassLimitDAO;
import com.nobbysoft.first.server.dao.RaceDAO;
import com.nobbysoft.first.server.dao.SavingThrowDAO;
import com.nobbysoft.first.server.dao.ShieldDAO;
import com.nobbysoft.first.server.dao.SpellDAO;
import com.nobbysoft.first.server.dao.StrengthDAO;
import com.nobbysoft.first.server.dao.ViewsDAO;
import com.nobbysoft.first.server.dao.WeaponAmmunitionDAO;
import com.nobbysoft.first.server.dao.WeaponMeleeDAO;
import com.nobbysoft.first.server.dao.WeaponRangedDAO;
import com.nobbysoft.first.server.dao.WisdomDAO;
import com.nobbysoft.first.server.service.ArmourServiceImpl;
import com.nobbysoft.first.server.service.CharacterClassLevelServiceImpl;
import com.nobbysoft.first.server.service.CharacterClassServiceImpl;
import com.nobbysoft.first.server.service.CharacterClassSpellServiceImpl;
import com.nobbysoft.first.server.service.CharacterClassToHitServiceImpl;
import com.nobbysoft.first.server.service.CharismaServiceImpl;
import com.nobbysoft.first.server.service.CodedListServiceImpl;
import com.nobbysoft.first.server.service.ConstitutionServiceImpl;
import com.nobbysoft.first.server.service.DexterityServiceImpl;
import com.nobbysoft.first.server.service.IntelligenceServiceImpl;
import com.nobbysoft.first.server.service.PlayerCharacterEquipmentServiceImpl;
import com.nobbysoft.first.server.service.PlayerCharacterHpServiceImpl;
import com.nobbysoft.first.server.service.PlayerCharacterServiceImpl;
import com.nobbysoft.first.server.service.PlayerCharacterSpellServiceImpl;
import com.nobbysoft.first.server.service.RaceClassLimitServiceImpl;
import com.nobbysoft.first.server.service.RaceServiceImpl;
import com.nobbysoft.first.server.service.SavingThrowServiceImpl;
import com.nobbysoft.first.server.service.ShieldServiceImpl;
import com.nobbysoft.first.server.service.SpellServiceImpl;
import com.nobbysoft.first.server.service.SqlServiceImpl;
import com.nobbysoft.first.server.service.StrengthServiceImpl;
import com.nobbysoft.first.server.service.WeaponAmmunitionServiceImpl;
import com.nobbysoft.first.server.service.WeaponMeleeServiceImpl;
import com.nobbysoft.first.server.service.WeaponRangedThrownImpl;
import com.nobbysoft.first.server.service.WisdomServiceImpl;

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
	private Map<Class,Class> daoimap =new LinkedHashMap<>(); // so that the tables are ordered as added to the map...
	private Map<Class,Class> buttonmap =new HashMap<>();
	private Map<Class<?>, String> names = new HashMap<>();
	private Map<Class<?>, Class<?>> staticDataPanels = new HashMap<>();
	
	private Map<EquipmentType,Class<?>> equipmentDAO = new HashMap<>();
	
	public Class getEquipmentDao(EquipmentType type){
		return equipmentDAO.get(type);
	}
	
	{
		buttonmap.put(CharacterClass.class, CharacterClassButtons.class);
		buttonmap.put(PlayerCharacter.class, PlayerCharacterButtons.class);
		

		daoimap.put(PlayerCharacter.class, PlayerCharacterDAO.class);
		daoimap.put(PlayerCharacterHp.class, PlayerCharacterHpDAO.class);
		daoimap.put(PlayerCharacterSpell.class, PlayerCharacterSpellDAO.class);
		daoimap.put(PlayerCharacterEquipment.class, PlayerCharacterEquipmentDAO.class);
		
		
		daoimap.put(Spell.class, SpellDAO.class);
		daoimap.put(Race.class, RaceDAO.class);
		daoimap.put(CharacterClass.class, CharacterClassDAO.class);
		daoimap.put(SavingThrow.class, SavingThrowDAO.class);
		daoimap.put(CharacterClassSpell.class, CharacterClassSpellDAO.class);
		daoimap.put(CharacterClassLevel.class, CharacterClassLevelDAO.class);
		daoimap.put(CharacterClassToHit.class, CharacterClassToHitDAO.class);//
		daoimap.put(SavingThrow.class, SavingThrowDAO.class);//SavingThrow
		daoimap.put(RaceClassLimit.class, RaceClassLimitDAO.class);
		daoimap.put(Constitution.class,ConstitutionDAO.class);
		daoimap.put(Intelligence.class,IntelligenceDAO.class);
		daoimap.put(Strength.class,StrengthDAO.class);
		daoimap.put(Dexterity.class,DexterityDAO.class);
		daoimap.put(Charisma.class,CharismaDAO.class);
		daoimap.put(Wisdom.class,WisdomDAO.class);
		daoimap.put(WeaponMelee.class,WeaponMeleeDAO.class);
		daoimap.put(WeaponRanged.class,WeaponRangedDAO.class);
		daoimap.put(WeaponAmmunition.class,WeaponAmmunitionDAO.class);
		daoimap.put(Armour.class,ArmourDAO.class);
		daoimap.put(Shield.class,ShieldDAO.class);

		
		equipmentDAO.put(EquipmentType.MELEE_WEAPON,WeaponMeleeDAO.class);
		equipmentDAO.put(EquipmentType.WEAPON_RANGED,WeaponRangedDAO.class);
		equipmentDAO.put(EquipmentType.AMMUNITION,WeaponAmmunitionDAO.class);
		equipmentDAO.put(EquipmentType.ARMOUR,ArmourDAO.class);
		equipmentDAO.put(EquipmentType.SHIELD,ShieldDAO.class);
		 
		
		
		staticDataPanels.put(PlayerCharacter.class,PlayerCharacterPanel.class);
		staticDataPanels.put(Spell.class,SpellPanel.class);
		staticDataPanels.put(Race.class,RacePanel.class);
		staticDataPanels.put(CharacterClass.class,CharacterClassPanel.class);
		staticDataPanels.put(RaceClassLimit.class, RaceClassLimitPanel.class); 
		staticDataPanels.put(Constitution.class, ConstitutionPanel.class);
		staticDataPanels.put(Intelligence.class, IntelligencePanel.class);
		staticDataPanels.put(Strength.class, StrengthPanel.class);
		staticDataPanels.put(Dexterity.class, DexterityPanel.class);//
		staticDataPanels.put(Charisma.class, CharismaPanel.class);//
		staticDataPanels.put(Wisdom.class, WisdomPanel.class);
		staticDataPanels.put(WeaponMelee.class, WeaponMeleePanel.class);
		staticDataPanels.put(WeaponRanged.class, WeaponRangedPanel.class);
		staticDataPanels.put(WeaponAmmunition.class, WeaponAmmunitionPanel.class);
		staticDataPanels.put(Armour.class, ArmourPanel.class);
		staticDataPanels.put(Shield.class, ShieldPanel.class);
		

		entityservicemap.put(PlayerCharacter.class, PlayerCharacterService.class);
		entityservicemap.put(PlayerCharacterHp.class, PlayerCharacterHpService.class);
		entityservicemap.put(PlayerCharacterSpell.class, PlayerCharacterSpellService.class);
		entityservicemap.put(PlayerCharacterEquipment.class, PlayerCharacterEquipmentService.class);
		entityservicemap.put(CharacterClass.class, CharacterClassService.class);
		entityservicemap.put(CharacterClassSpell.class, CharacterClassSpellService.class);
		entityservicemap.put(CharacterClassLevel.class, CharacterClassLevelService.class);
		entityservicemap.put(CharacterClassToHit.class, CharacterClassToHitService.class);//SavingThrow
		entityservicemap.put(SavingThrow.class, SavingThrowService.class);//
		entityservicemap.put(Spell.class, SpellService.class);
		entityservicemap.put(Race.class, RaceService.class);
		entityservicemap.put(RaceClassLimit.class, RaceClassLimitService.class);
		entityservicemap.put(Constitution.class, ConstitutionService.class);//
		entityservicemap.put(Intelligence.class, IntelligenceService.class);//
		entityservicemap.put(Strength.class, StrengthService.class);
		entityservicemap.put(Dexterity.class,DexterityService.class);//
		entityservicemap.put(Charisma.class,CharismaService.class);//
		entityservicemap.put(Wisdom.class, WisdomService.class);//
		entityservicemap.put(WeaponMelee.class,WeaponMeleeService.class);
		entityservicemap.put(WeaponRanged.class,WeaponRangedService.class);
		entityservicemap.put(WeaponAmmunition.class,WeaponAmmunitionService.class);
		entityservicemap.put(Armour.class,ArmourService.class);
		entityservicemap.put(Shield.class,ShieldService.class);
		
		

		servicemap.put(PlayerCharacterService.class, PlayerCharacterServiceImpl.class);
		servicemap.put(PlayerCharacterEquipmentService.class, PlayerCharacterEquipmentServiceImpl.class);
		servicemap.put(PlayerCharacterHpService.class, PlayerCharacterHpServiceImpl.class);
		servicemap.put(PlayerCharacterSpellService.class, PlayerCharacterSpellServiceImpl.class);
		servicemap.put(CharacterClassService.class, CharacterClassServiceImpl.class);
		servicemap.put(CharacterClassSpellService.class, CharacterClassSpellServiceImpl.class);
		servicemap.put(CharacterClassLevelService.class, CharacterClassLevelServiceImpl.class);
		servicemap.put(CharacterClassToHitService.class, CharacterClassToHitServiceImpl.class);//
		servicemap.put(SavingThrowService.class, SavingThrowServiceImpl.class);//SavingThrow
		servicemap.put(SpellService.class, SpellServiceImpl.class);
		servicemap.put(RaceService.class, RaceServiceImpl.class);
		servicemap.put(RaceClassLimitService.class, RaceClassLimitServiceImpl.class);
		servicemap.put(CodedListService.class, CodedListServiceImpl.class);
		servicemap.put(ConstitutionService.class, ConstitutionServiceImpl.class);//
		servicemap.put(IntelligenceService.class, IntelligenceServiceImpl.class);//
		servicemap.put(StrengthService.class, StrengthServiceImpl.class);
		servicemap.put(DexterityService.class, DexterityServiceImpl.class);//Charisma
		servicemap.put(CharismaService.class, CharismaServiceImpl.class);//
		servicemap.put(WisdomService.class, WisdomServiceImpl.class);//
		servicemap.put(WeaponMeleeService.class, WeaponMeleeServiceImpl.class);
		servicemap.put(WeaponRangedService.class, WeaponRangedThrownImpl.class);
		servicemap.put(WeaponAmmunitionService.class, WeaponAmmunitionServiceImpl.class);
		servicemap.put(ArmourService.class, ArmourServiceImpl.class);
		servicemap.put(ShieldService.class, ShieldServiceImpl.class);
		

		servicemap.put(SqlService.class, SqlServiceImpl.class);
		
	}
	
	
	public Class<?> getServiceForInterface(Class<?> clazz){
		return servicemap.get(clazz);
	}
	
	public Class<DataServiceI> getServiceForEntity(Class<?> clazz){
		return servicemap.get(entityservicemap.get(clazz));
	}
	
	public List<Class> getDTOsForStaticMaintenance(){
		List<Class>  d =new ArrayList<>();
		for(Class clazz:staticDataPanels.keySet()) {
			d.add(clazz);	
		}
		
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
		return staticDataPanels.get(clazz);
	}
	
	
	@SuppressWarnings("rawtypes")
	public Class<?> getDAO(Class clazz){
		return daoimap.get(clazz);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class<CreateInterface>[] getDaos(){
		List<Class> v = new ArrayList();
		v.addAll(daoimap.values());
		// extra creates
		v.add(ViewsDAO.class);
		return v.toArray(new Class[daoimap.size()]);
 
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
