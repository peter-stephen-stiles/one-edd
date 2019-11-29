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

import com.nobbysoft.first.client.data.panels.attributes.*;
import com.nobbysoft.first.client.data.panels.equipment.*;
import com.nobbysoft.first.client.data.panels.pc.*;
import com.nobbysoft.first.client.data.panels.staticdata.*;
import com.nobbysoft.first.common.entities.equipment.*;
import com.nobbysoft.first.common.entities.pc.*;
import com.nobbysoft.first.common.entities.staticdto.*;
import com.nobbysoft.first.common.entities.staticdto.attributes.*;
import com.nobbysoft.first.common.servicei.*;
import com.nobbysoft.first.server.dao.*;
import com.nobbysoft.first.server.service.*;

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
		daoimap.put(ThiefAbilityType.class, ThiefAbilityTypeDAO.class);
		daoimap.put(CharacterClass.class, CharacterClassDAO.class);
		daoimap.put(SavingThrow.class, SavingThrowDAO.class);
		daoimap.put(CharacterClassSpell.class, CharacterClassSpellDAO.class);
		daoimap.put(CharacterClassLevel.class, CharacterClassLevelDAO.class);
		daoimap.put(CharacterClassToHit.class, CharacterClassToHitDAO.class);//
		daoimap.put(SavingThrow.class, SavingThrowDAO.class);//
		daoimap.put(RaceClassLimit.class, RaceClassLimitDAO.class);//
		daoimap.put(RaceThiefAbilityBonus.class, RaceThiefAbilityBonusDAO.class);//
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
		daoimap.put(TurnUndead.class, TurnUndeadDAO.class);
		daoimap.put(UndeadType.class, UndeadTypeDAO.class);
		daoimap.put(Assassination.class, AssassinationDAO.class); //
		daoimap.put(ThiefAbility.class, ThiefAbilityDAO.class); //

		
		equipmentDAO.put(EquipmentType.MELEE_WEAPON,WeaponMeleeDAO.class);
		equipmentDAO.put(EquipmentType.WEAPON_RANGED,WeaponRangedDAO.class);
		equipmentDAO.put(EquipmentType.AMMUNITION,WeaponAmmunitionDAO.class);
		equipmentDAO.put(EquipmentType.ARMOUR,ArmourDAO.class);
		equipmentDAO.put(EquipmentType.SHIELD,ShieldDAO.class);
		 
		
		
		staticDataPanels.put(PlayerCharacter.class,PlayerCharacterPanel.class);
		staticDataPanels.put(Spell.class,SpellPanel.class);
		staticDataPanels.put(Race.class,RacePanel.class);//
		staticDataPanels.put(ThiefAbilityType.class,ThiefAbilityTypePanel.class);//
		staticDataPanels.put(CharacterClass.class,CharacterClassPanel.class);
		staticDataPanels.put(RaceClassLimit.class, RaceClassLimitPanel.class); //
		staticDataPanels.put(RaceThiefAbilityBonus.class, RaceThiefAbilityBonusPanel.class); //
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
		staticDataPanels.put(UndeadType.class,UndeadTypePanel.class);//
		staticDataPanels.put(TurnUndead.class,TurnUndeadPanel.class);// 
		staticDataPanels.put(Assassination.class,AssassinationPanel.class);// 
		staticDataPanels.put(ThiefAbility.class,ThiefAbilityPanel.class);// 

		entityservicemap.put(PlayerCharacter.class, PlayerCharacterService.class);
		entityservicemap.put(PlayerCharacterHp.class, PlayerCharacterHpService.class);
		entityservicemap.put(PlayerCharacterSpell.class, PlayerCharacterSpellService.class);
		entityservicemap.put(PlayerCharacterEquipment.class, PlayerCharacterEquipmentService.class);
		entityservicemap.put(CharacterClass.class, CharacterClassService.class);
		entityservicemap.put(CharacterClassSpell.class, CharacterClassSpellService.class);
		entityservicemap.put(CharacterClassLevel.class, CharacterClassLevelService.class);
		entityservicemap.put(CharacterClassToHit.class, CharacterClassToHitService.class);//
		entityservicemap.put(SavingThrow.class, SavingThrowService.class);//
		entityservicemap.put(Spell.class, SpellService.class);
		entityservicemap.put(Race.class, RaceService.class);//
		entityservicemap.put(ThiefAbilityType.class, ThiefAbilityTypeService.class);//
		entityservicemap.put(RaceClassLimit.class, RaceClassLimitService.class);//
		entityservicemap.put(RaceThiefAbilityBonus.class, RaceThiefAbilityBonusService.class);//
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
		entityservicemap.put(UndeadType.class, UndeadTypeService.class);//
		entityservicemap.put(Assassination.class, AssassinationService.class);//
		entityservicemap.put(ThiefAbility.class, ThiefAbilityService.class);//
		entityservicemap.put(TurnUndead.class, TurnUndeadService.class);//
		

		servicemap.put(PlayerCharacterService.class, PlayerCharacterServiceImpl.class);
		servicemap.put(PlayerCharacterEquipmentService.class, PlayerCharacterEquipmentServiceImpl.class);
		servicemap.put(PlayerCharacterHpService.class, PlayerCharacterHpServiceImpl.class);
		servicemap.put(PlayerCharacterSpellService.class, PlayerCharacterSpellServiceImpl.class);
		servicemap.put(CharacterClassService.class, CharacterClassServiceImpl.class);
		servicemap.put(CharacterClassSpellService.class, CharacterClassSpellServiceImpl.class);
		servicemap.put(CharacterClassLevelService.class, CharacterClassLevelServiceImpl.class);
		servicemap.put(CharacterClassToHitService.class, CharacterClassToHitServiceImpl.class);//
		servicemap.put(SavingThrowService.class, SavingThrowServiceImpl.class);//
		servicemap.put(SpellService.class, SpellServiceImpl.class);
		servicemap.put(RaceService.class, RaceServiceImpl.class);//
		servicemap.put(ThiefAbilityTypeService.class, ThiefAbilityTypeServiceImpl.class);//
		servicemap.put(RaceClassLimitService.class, RaceClassLimitServiceImpl.class);//
		servicemap.put(RaceThiefAbilityBonus.class, RaceThiefAbilityBonusServiceImpl.class);//
		servicemap.put(CodedListService.class, CodedListServiceImpl.class);
		servicemap.put(ConstitutionService.class, ConstitutionServiceImpl.class);//
		servicemap.put(IntelligenceService.class, IntelligenceServiceImpl.class);//
		servicemap.put(StrengthService.class, StrengthServiceImpl.class);
		servicemap.put(DexterityService.class, DexterityServiceImpl.class);//
		servicemap.put(CharismaService.class, CharismaServiceImpl.class);//
		servicemap.put(WisdomService.class, WisdomServiceImpl.class);//
		servicemap.put(WeaponMeleeService.class, WeaponMeleeServiceImpl.class);
		servicemap.put(WeaponRangedService.class, WeaponRangedThrownImpl.class);
		servicemap.put(WeaponAmmunitionService.class, WeaponAmmunitionServiceImpl.class);
		servicemap.put(ArmourService.class, ArmourServiceImpl.class);
		servicemap.put(ShieldService.class, ShieldServiceImpl.class);
		servicemap.put(UndeadTypeService.class, UndeadTypeServiceImpl.class);//
		servicemap.put(AssassinationService.class, AssassinationImpl.class);//
		servicemap.put(ThiefAbilityService.class, ThiefAbilityServiceImpl.class);//
		servicemap.put(TurnUndeadService.class, TurnUndeadServiceImpl.class);//
		

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
