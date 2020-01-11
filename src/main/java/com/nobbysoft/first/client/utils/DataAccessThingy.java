package com.nobbysoft.first.client.utils;

import static org.junit.Assert.fail;

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
import com.nobbysoft.first.common.entities.equipment.EquipmentType;
import com.nobbysoft.first.common.entities.equipment.EquipmentWhere;
import com.nobbysoft.first.common.entities.equipment.WeaponDamageI;
import com.nobbysoft.first.common.entities.equipment.WeaponMelee;
import com.nobbysoft.first.common.entities.equipment.WeaponRanged;
import com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterEquipment;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterLevel;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterSpell;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassSpell;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassToHit;
import com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.first.common.entities.staticdto.RaceThiefAbilityBonus;
import com.nobbysoft.first.common.entities.staticdto.ThiefAbility;
import com.nobbysoft.first.common.entities.staticdto.attributes.Charisma;
import com.nobbysoft.first.common.entities.staticdto.attributes.Constitution;
import com.nobbysoft.first.common.entities.staticdto.attributes.Dexterity;
import com.nobbysoft.first.common.entities.staticdto.attributes.Intelligence;
import com.nobbysoft.first.common.entities.staticdto.attributes.Strength;
import com.nobbysoft.first.common.entities.staticdto.attributes.StrengthKey;
import com.nobbysoft.first.common.entities.staticdto.attributes.Wisdom;
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
import com.nobbysoft.first.common.servicei.PlayerCharacterSpellService;
import com.nobbysoft.first.common.servicei.RaceThiefAbilityBonusService;
import com.nobbysoft.first.common.servicei.StrengthService;
import com.nobbysoft.first.common.servicei.ThiefAbilityService;
import com.nobbysoft.first.common.servicei.WeaponMeleeService;
import com.nobbysoft.first.common.servicei.WeaponRangedService;
import com.nobbysoft.first.common.servicei.WisdomService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.common.views.ViewPlayerCharacterEquipment;
import com.nobbysoft.first.common.views.ViewPlayerCharacterSpell;
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
	
	public Map<String,String> getClassNames(){
		Map<String,String> cn = new HashMap<>();
		try {
			CharacterClassService ccs = (CharacterClassService )getDataService(CharacterClass.class);
			for(CharacterClass cc:ccs.getList()) {
				cn.put(cc.getClassId(),cc.getName());
			
			}	
		} catch (Exception e) {
			throw new IllegalStateException("Can't get classes");
		}
		
		return cn;
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
		Set<Integer> levels = new TreeSet<>();
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
	
	public List<ThiefAbility> getThiefAbilities(int level, Race race) {
		
		List<ThiefAbility> ta = new ArrayList<>();
		
		RaceThiefAbilityBonusService rtab = (RaceThiefAbilityBonusService)getDataService(RaceThiefAbilityBonus.class);
		ThiefAbilityService tas = (ThiefAbilityService)getDataService(ThiefAbility.class);
		
		
		try {
			List<RaceThiefAbilityBonus> rb = rtab.getBonusesForRace(race.getRaceId());
			Map<String,Integer> bons = new HashMap<>();
			for(RaceThiefAbilityBonus bon: rb ) {
				bons.put(bon.getThiefAbilityType(), new Integer(bon.getPercentageBonus()));
			}
			List<ThiefAbility> tabs = tas.getListForThiefLevel(level);
			for(ThiefAbility tab: tabs ) {
				double pcd = tab.getPercentageChance();
				if(bons.containsKey(tab.getThiefAbilityType())) {
					int bon = bons.get(tab.getThiefAbilityType());
					LOGGER.info("Race "+race+" tab "+tab.getThiefAbilityType()+" bonus:"+bon);
					pcd = pcd +bon;
					tab.setPercentageChance(pcd);
				}
				ta.add(tab);
			}
			
		} catch (SQLException e) {			
			throw new IllegalStateException("Unable to get tea-leaf abilities "+e,e);			
		}
		
		return ta;
		
	}
	
	public List<CharacterClassToHit> getToHit(PlayerCharacter playerCharacter, Race race) {
		
		List<CharacterClassToHit> list = new ArrayList<>();
		try {
		
		CharacterClassToHitService cc2hs = (CharacterClassToHitService)getDataService(CharacterClassToHit.class);
		//
		
		// if SINGLE CLASS pick numbers
		
		ReturnValue<CharacterClassToHit> rv = cc2hs.getToHitForClassLevel(playerCharacter.getFirstClass(), playerCharacter.getFirstClassLevel());
		if(rv.isError()) {
			LOGGER.error("error getting data" +rv.getErrorMessage());	
			throw new IllegalStateException("Unable to get to hit "+rv.getErrorMessage());
		}
		
		CharacterClassToHit toHit1 = rv.getValue();
		
		if(playerCharacter.getSecondClass()==null||playerCharacter.getSecondClass().isEmpty()){
			// no 2		
			list.add(toHit1);
		} else {
			rv = cc2hs.getToHitForClassLevel(playerCharacter.getSecondClass(), playerCharacter.getSecondClassLevel());
			if(rv.isError()) {
				LOGGER.error("error getting data 2" +rv.getErrorMessage());	
				throw new IllegalStateException("Unable to get to hit 2 "+rv.getErrorMessage());
			} 
			CharacterClassToHit toHit2=rv.getValue();
			
			if(race.isMultiClassable()) {
				
		// if MULTI CLASS pick _best_ numbers				
				CharacterClassToHit toHit3 = toHit2;//cheat
				if(playerCharacter.getThirdClass()==null||playerCharacter.getThirdClass().isEmpty()){
					// no 3
				} else {
					rv= cc2hs.getToHitForClassLevel(playerCharacter.getThirdClass(), playerCharacter.getThirdClassLevel());
					if(rv.isError()) {
						LOGGER.error("error getting data 3" +rv.getErrorMessage());	
						throw new IllegalStateException("Unable to get to hit 3 "+rv.getErrorMessage());
					} 
					toHit3 =rv.getValue();					
				}
				// smallest is best
				if (toHit2.getBiggestACHitBy20()<toHit1.getBiggestACHitBy20()) {
					// prefer th2 to th1
					if (toHit3.getBiggestACHitBy20()<toHit2.getBiggestACHitBy20()) {
						// prefer th3 to either
						list.add(toHit3);
					} else {
						list.add(toHit2);
					}
				} else {
					// prefer th2 to th1
					if (toHit3.getBiggestACHitBy20()<toHit1.getBiggestACHitBy20()) {
						// prefer th3 to either
						list.add(toHit3);
					} else {
						list.add(toHit1);
					}
				}
				
			} else {
				CharacterClassToHit toHit = null;
				
				for(PlayerCharacterLevel pcl:playerCharacter.getClassDetails()) {
					String cls =pcl.getThisClass();
					if(cls!=null&&!cls.trim().isEmpty()) {
						int lvl = pcl.getThisClassLevel();
						rv = cc2hs.getToHitForClassLevel(cls, lvl);
						if(rv.isError()) {
							LOGGER.error("error getting data X" +rv.getErrorMessage());	
							throw new IllegalStateException("Unable to get to hit X "+rv.getErrorMessage());
						} 
						toHit = rv.getValue();						
					}
				}
				list.add(toHit);
			}
		}
		} catch (Exception ex) {
			LOGGER.error("error getting data",ex);	
			throw new IllegalStateException("Unable to get to hit ",ex);
		}
		return list;
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
	
	
	public List<ViewPlayerCharacterSpell> grabSpells(int pcId){
		List<ViewPlayerCharacterSpell> list = null;
		try {
		PlayerCharacterSpellService pces = (PlayerCharacterSpellService) getDataService(
				PlayerCharacterSpell.class);
		
		list = pces.getViewForPC(pcId);
		
		} catch (Exception ex) {
			list = new ArrayList<>();
		}
		return list;
		
	}

	

	public List<CodedListItem> workOutAllowedSpells(PlayerCharacter pc) {
		List<CodedListItem> clis = new ArrayList<>();
		
		CharacterClassSpellService ccss = (CharacterClassSpellService)getDataService(CharacterClassSpell.class); 
		try {
			List<CharacterClassSpell> as =  (ccss.getAllowedSpells(pc.getPcId()));
			for(CharacterClassSpell ccs:as) { 
				int[] max = new int[] {
						0,
						ccs.getLevel1Spells(),
						ccs.getLevel2Spells(),
						ccs.getLevel3Spells(),
						ccs.getLevel4Spells(),
						ccs.getLevel5Spells(),
						ccs.getLevel6Spells(),
						ccs.getLevel7Spells(),
						ccs.getLevel8Spells(),
						ccs.getLevel9Spells()
				};

				StringBuilder sbMax = new StringBuilder(ccs.getSpellClassId()).append(" ");
				for (int i = 1, n = 10; i < n; i++) {

					int maxSpellsPerLevel =max[i];
					if(maxSpellsPerLevel>0) {
						if (i > 1) {
							sbMax.append(", ");
						}
						sbMax.append("Lvl:").append(i).append(" ").append(maxSpellsPerLevel);
					}
				}
				
				
				LOGGER.info(sbMax.toString());			
				
				CodedListItem cli = new CodedListItem(ccs.getSpellClassId(), sbMax.toString());
				clis.add(cli);
				
			
			}
			
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
		return clis;
	}


	public Map<String,List<ViewPlayerCharacterEquipment>> getEquipment(int pcId) {
		
		Map<String,List<ViewPlayerCharacterEquipment>> quip = new HashMap<>();
		List<ViewPlayerCharacterEquipment> equipped = new ArrayList<>();
		List<ViewPlayerCharacterEquipment> notEquipped = new ArrayList<>();
		quip.put("E", equipped);
		quip.put("N", notEquipped);
		
		try {
		
		PlayerCharacterEquipmentService pces = (PlayerCharacterEquipmentService )getDataService(PlayerCharacterEquipment.class);
		List<ViewPlayerCharacterEquipment> list = pces.getForPC(pcId);
		
		
		for(ViewPlayerCharacterEquipment vpce: list) {
			PlayerCharacterEquipment pce = vpce.getPlayerCharacterEquipment();
			String desc = vpce.getDescription();	
			if(pce.isEquipped()) {
				equipped.add(vpce);
			} else {
				notEquipped.add(vpce);
			}
		}
		
		

	} catch (SQLException e) {
		throw new IllegalStateException(e);
	}
		return quip;
	}
	
	
	public WeaponDamageI getWeapon(PlayerCharacterEquipment pce) {
		
		try {
			WeaponMeleeService wms = (WeaponMeleeService )getDataService(WeaponMelee.class);
			WeaponRangedService wrs = (WeaponRangedService )getDataService(WeaponRanged.class);
			if(pce.getEquipmentType().equals(EquipmentType.MELEE_WEAPON)) {
				return wms.get(pce.getCode());
			} else if(pce.getEquipmentType().equals(EquipmentType.WEAPON_RANGED)) {
				return wrs.get(pce.getCode());
			}  
			return null;
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}
	
}
