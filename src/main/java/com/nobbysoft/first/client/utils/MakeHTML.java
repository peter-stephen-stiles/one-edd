package com.nobbysoft.first.client.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.first.common.entities.equipment.Armour;
import com.nobbysoft.first.common.entities.equipment.EquipmentType;
import com.nobbysoft.first.common.entities.equipment.EquipmentWhere;
import com.nobbysoft.first.common.entities.equipment.MiscellaneousMagicItem;
import com.nobbysoft.first.common.entities.equipment.Shield;
import com.nobbysoft.first.common.entities.equipment.WeaponACAdjustmentsI;
import com.nobbysoft.first.common.entities.equipment.WeaponDamageI;
import com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterEquipment;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterLevel;
import com.nobbysoft.first.common.entities.staticdto.AffectsACType;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassSkill;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassToHit;
import com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.first.common.entities.staticdto.RaceSkill;
import com.nobbysoft.first.common.entities.staticdto.SavingThrow;
import com.nobbysoft.first.common.entities.staticdto.Spell;
import com.nobbysoft.first.common.entities.staticdto.ThiefAbility;
import com.nobbysoft.first.common.entities.staticdto.TurnUndead;
import com.nobbysoft.first.common.entities.staticdto.UndeadType;
import com.nobbysoft.first.common.entities.staticdto.attributes.Charisma;
import com.nobbysoft.first.common.entities.staticdto.attributes.Constitution;
import com.nobbysoft.first.common.entities.staticdto.attributes.Dexterity;
import com.nobbysoft.first.common.entities.staticdto.attributes.Intelligence;
import com.nobbysoft.first.common.entities.staticdto.attributes.Strength;
import com.nobbysoft.first.common.entities.staticdto.attributes.Wisdom;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.utils.SU;
import com.nobbysoft.first.common.utils.ToHitUtils;
import com.nobbysoft.first.common.views.ViewPlayerCharacterEquipment;
import com.nobbysoft.first.common.views.ViewPlayerCharacterSpell;

public class MakeHTML {

	public MakeHTML() {

	}

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private String makeHtmlError(Throwable t) {
		StringBuilder sb = new StringBuilder("<html><body>");
		sb.append("<h1>").append(t.toString()).append("</h1>");
		sb.append("<table>");
		for (StackTraceElement ste : t.getStackTrace()) {
			sb.append("<tr>");
			sb.append("<td>");
			sb.append("at");
			sb.append("</td>");
			sb.append("<td>");
			sb.append(ste.getClassName()).append(".").append(ste.getMethodName());
			sb.append("</td>");
			sb.append("<td>");
			sb.append("(").append(ste.getLineNumber()).append(")");
			sb.append("</td>");
			sb.append("</tr>");
		}

		sb.append("</table>");
		sb.append("</body></html>");
		return sb.toString();
	}


	public enum TYPE{
		CHARACTER_SHEET("char"),
		SPELL_BOOK("spells");
		private String prefix;
		TYPE(String prefix){
			this.prefix=prefix;
		}
		public String getPrefix() {
			return prefix;
		}
	};
	
	public String makeDocument(PlayerCharacter pc, Map<String, CharacterClass> myCharacterClasses, Race race,
			List<SavingThrow> savingThrows, DataAccessThingy data, TYPE htmlType,Map<String, CharacterClass> allCharacterClasses) {


		try {
			
			Strength strength = data.getStrength(pc.getAttrStr(), pc.getExceptionalStrength());
			Intelligence intelligence = data.getIntelligence(pc.getAttrInt());
			Wisdom wisdom = data.getWisdom(pc.getAttrWis());
			Dexterity dexterity = data.getDexterity(pc.getAttrDex());
			Constitution constitution = data.getConstitution(pc.getAttrCon());
			Charisma charisma = data.getCharisma(pc.getAttrChr());

			List<String> divSpellBonuses = data.getDivineSpellBonus(pc.getAttrWis());

			Map<Comparable, String> stNames = data.getSavingThrowNameMap();

			List<CharacterClassToHit> toHits = data.getToHit(pc, race);
			
			Map<String,MiscellaneousMagicItem> armourAffecting = data.getArmourAffecting();
			
			List<String> activeClasses = data.getActiveClasses(pc, race);
			
	
			
			boolean extraHitPointBonus = false;
			for (CharacterClass c : myCharacterClasses.values()) {
				if (c != null) {
					if (c.isHighConBonus()) {
						extraHitPointBonus = true;
					}
				}
			}
			String magicDefenceBonus = "";
			if (race.isHasMagicDefenceBonus()) {
				int mdb = (int) (((1.0f * pc.getAttrCon()) / 3.5f));
				if (mdb > 0) {
					magicDefenceBonus = "Magic defence bonus: +" + mdb;
				}
			}

			Map<String, List<ViewPlayerCharacterEquipment>> equipment = data.getEquipment(pc.getPcId());
			List<ViewPlayerCharacterEquipment> equipped = equipment.get("E");
			List<ViewPlayerCharacterEquipment> unEquipped = equipment.get("N");


			/// need class map

			Map<String, String> classNames = data.getClassNames();			


			List<CodedListItem> allowedSpells = data.workOutAllowedSpells(pc);
			
			
			Map<Comparable, String> abilitiesMap = data.getCodedListMap(Constants.CLI_THIEF_ABILITY);
			
			List<ViewPlayerCharacterSpell> spells = data.grabSpells(pc.getPcId());
			
			List<RaceSkill> raceSkills = data.getRaceSkills(pc.getRaceId());			
			List<CharacterClassSkill> classSkills1 = data.getCharacterClassSkills(pc.getFirstClass(), pc.getFirstClassLevel());
			List<CharacterClassSkill> classSkills2 = data.getCharacterClassSkills(pc.getSecondClass(), pc.getSecondClassLevel());
			List<CharacterClassSkill> classSkills3 = data.getCharacterClassSkills(pc.getThirdClass(), pc.getThirdClassLevel());			
			
			
			int effectiveClericLevelForTurning=0;
			PlayerCharacterLevel[] pcla = pc.getClassDetails();
			Map<String,PlayerCharacterLevel> mapclasslevels = new HashMap<>();
			for(int i=0,n=pcla.length;i<n;i++) {
				PlayerCharacterLevel pcl= pcla[i];
				if(pcl!=null) {
					if(pcl.getThisClass()!=null) {
						mapclasslevels.put(pcl.getThisClass(),pcl);
					}
				}
			}
			
			for(CharacterClass cc:myCharacterClasses.values()) {
				if(activeClasses.contains(cc.getClassId())) {
					if(cc.getTurnUndead()>=0) {
						int efflcl = mapclasslevels.get(cc.getClassId()).getThisClassLevel() - cc.getTurnUndead();
						if(efflcl>effectiveClericLevelForTurning) {
							effectiveClericLevelForTurning = efflcl;
						}
					}
				}
			}
			List<TurnUndead> turnUndead= data.getTurnUndead(effectiveClericLevelForTurning);
			Map<Integer,UndeadType> undead = data.getUndeadTypes();
			
			
			
			/// GOT data, build output
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			docBuilder = docFactory.newDocumentBuilder();

			/**
			 * AC is made up of
			 * DEX BONUS
			 * ARMOUR
			 * SHIELD
 			 * EQUIPPED MISC MAGIC
 			 * 
			 */
			int dexBonus =dexterity.getDefensiveAdjustment();
			List<ViewPlayerCharacterEquipment> aae = new ArrayList<>();
			for(ViewPlayerCharacterEquipment vpce: equipped) {
				EquipmentType arm = vpce.getPlayerCharacterEquipment().getEquipmentType();
				if(arm.equals(EquipmentType.ARMOUR)) {
					aae.add(vpce);
				} else if (arm.equals(EquipmentType.SHIELD)) {
					aae.add(vpce);
				} else if(arm.equals(EquipmentType.MISCELLANEOUS_MAGIC)) {
					MiscellaneousMagicItem item = armourAffecting.get(vpce.getPlayerCharacterEquipment().getCode());
					if(item!=null) {
						aae.add(vpce);
					}					
				}
			}
			// now go through and find out the base AC
			//
			int baseAc=10;
			int shieldBonus=0;
			int itemBonus=0;
			String armourName="";
			String shieldName="";
			String itemName="";
			for(ViewPlayerCharacterEquipment vpce: aae) {
				PlayerCharacterEquipment pce =vpce.getPlayerCharacterEquipment();
				String code = pce.getCode();
				EquipmentType type = pce.getEquipmentType();
				if(type.equals(EquipmentType.ARMOUR)) {
					if(pce.getEquippedWhere()!=null) {
						if(pce.getEquippedWhere().equals(EquipmentWhere.TORSO)) {
							Armour arm = data.getArmour(code);
							int ac= arm.getAC();
							if(ac<baseAc) {
								baseAc=ac;
								LOGGER.info("ARMOUR (arm) Using {}"+arm.getName()+baseAc);
								armourName=arm.getName();
							}
						}
					}
				} else if(type.equals(EquipmentType.SHIELD)) {
					if(pce.getEquippedWhere().equals(EquipmentWhere.HAND_L)||
							pce.getEquippedWhere().equals(EquipmentWhere.HAND_R)) {
						Shield  shield = data.getShield(code);
						int acb = (1+ shield.getMagicBonus());
						if(shieldBonus<acb) {
							LOGGER.info("SHIELD (shl) Using {}"+shield.getName() + " - "+shieldBonus);
							shieldBonus = acb;
							shieldName=shield.getName();
						}
					}
				} else /* miss c magic */{
					// as long as its equipped somewhere that's not the PACK then ok...
					
					EquipmentWhere w =pce.getEquippedWhere(); 
					if(w==null|| w.equals(EquipmentWhere.PACK)) {
						// in the pack, ignoring
					} else {
					
						// 
						MiscellaneousMagicItem item = armourAffecting.get(code);
						
						if(item.getAffectsAC().equals(AffectsACType.S.name())) {
							// like armour
							int ac = item.getEffectOnAC();
							if(ac<baseAc) {
								baseAc=ac;
								LOGGER.info("ARMOUR (msc) Using {}"+item.getName() + " - "+baseAc);
								armourName=item.getName();
							}
						} else if(item.getAffectsAC().equals(AffectsACType.P.name())) {
							int acb = (item.getEffectOnAC());
							if(itemBonus<acb) {
								LOGGER.info("BONUS (msc) Using {}"+item.getName() + " - "+itemBonus);
								itemBonus = acb;
								itemName=item.getName();
							}
	
						} else {
							///ahh??
						}
					}
				}
			}
			/*
			 * 			int baseAc=10;
			int shieldBonus=0;
			int itemBonus=0;
			 */
			int finalArmourClass = (baseAc +dexBonus) - (shieldBonus + itemBonus) ;
			
			Document doc = docBuilder.newDocument();
			Element html = doc.createElement("html");
			doc.appendChild(html);

			Element head = XmlUtilities.addElement(html, "head");

			String styles = readStyles();

			Element style = XmlUtilities.addElement(head, "style", styles);

			Element body = XmlUtilities.addElement(html, "body");

			List<PlayerCharacterLevel> classLevels = new ArrayList<>();

			Element mainRow;
			
			String title;
			if(htmlType.equals(TYPE.CHARACTER_SHEET)) {
				title="Character Sheet - ";
			} else {
				title="Spell Book - ";
			}
			
			XmlUtilities.addElement(body, "h1", title + pc.getCharacterName());
			mainRow = characterBasicsTable(pc, race, body);
			int hp = 0;
			hp = characterClassesTable(pc, myCharacterClasses, body, classLevels, hp);


			XmlUtilities.addElement(mainRow, "th", "HP");
			XmlUtilities.addElement(mainRow, "td", "" + hp);
			
			if(htmlType.equals(TYPE.CHARACTER_SHEET)) {


				characterAttributesTable(pc, strength, intelligence, wisdom, dexterity, constitution, charisma,
						divSpellBonuses, extraHitPointBonus, body);
	
				armourClassTable(dexBonus, baseAc, shieldBonus, itemBonus, armourName, shieldName, itemName,
						finalArmourClass, body);			
				
				savingThrowsTable(savingThrows, stNames, magicDefenceBonus, wisdom,body);
				
				toHitTable(myCharacterClasses, toHits, body);
				
				weaponTable(data, strength, dexterity, equipped, unEquipped, body);
				
				thiefSkillsTable(myCharacterClasses, race, data, abilitiesMap, body, classLevels);
	
				turnUndeadTable(effectiveClericLevelForTurning, turnUndead, undead, body);									

				allowedSpellsTable(activeClasses, classNames, allowedSpells, body);
	
				baseSpellsTable(classNames, spells, body);
				
				equippedTables(equipped, unEquipped, body);
				
				raceSkillsTable(raceSkills, body);
	
				classSkillsTable(myCharacterClasses, activeClasses, classSkills1, classSkills2, classSkills3, body);
			
			} else if(htmlType.equals(TYPE.SPELL_BOOK)) {
				
				allowedSpellsTable(activeClasses, classNames, allowedSpells, body);
				
				spellsByClass(spells,body,allCharacterClasses);
				
				
			} else {
				
			}
			LOGGER.info("ready to make as html "+(doc==null?"null doc":"ok doc"));
			String xmlString = XmlUtilities.xmlToHtmlString(doc);

			return xmlString;

		} catch (Exception e) {
			return makeHtmlError(e);
		}
	}


	private void turnUndeadTable(int effectiveClericLevelForTurning, List<TurnUndead> turnUndead,
			Map<Integer, UndeadType> undead, Element body) {
		if(effectiveClericLevelForTurning>0) {

			XmlUtilities.addElement(body, "h2", "Turn undead (as cleric level "+effectiveClericLevelForTurning+")");
			// turning table for cleric level
			Element table = XmlUtilities.addElement(body, "table");
			XmlUtilities.addAttribute(table, "border", "1");
			Element rowHead = XmlUtilities.addElement(table, "tr");
			Element rowData = XmlUtilities.addElement(table, "tr");
			for(TurnUndead tu:turnUndead) {
				UndeadType ut = undead.get(tu.getUndeadType());						
				XmlUtilities.addElement(rowHead, "th",ut.getDescription());					
				XmlUtilities.addElement(rowData, "td",tu.getRollRequiredString());
				}						
			}
	}

	
	private void spellsByClass(List<ViewPlayerCharacterSpell> allCharacterSpells,Element body,Map<String, CharacterClass> characterClasses) {
		
		Map<String,Map<Integer,List<Spell>>> spellMap = new HashMap<>();
		
		for(ViewPlayerCharacterSpell spell:allCharacterSpells) {
			
			Spell s = spell.getSpell();
			String spellClass = s.getSpellClass();
			Integer level = s.getLevel();
			List<Spell> someSpells;
			Map<Integer,List<Spell>> classSpells;
			classSpells = spellMap.get(spellClass);
			if(classSpells==null) {
				classSpells = new HashMap<>();				
				spellMap.put(spellClass,classSpells);
			}
			someSpells = classSpells.get(level);
			if(someSpells==null) {
				someSpells = new ArrayList<>();
				classSpells.put(level, someSpells);
			}
			someSpells.add(s);			
		}
		// we now have a list of spells by class level.. now for the book
		for(String spellClass:spellMap.keySet()) {
			CharacterClass characterClass =characterClasses.get(spellClass);
			if(characterClass==null) {
				
			}
			XmlUtilities.addElement(body, "h2", "Spell book for "+characterClass.getName()+" spells");
			Map<Integer,List<Spell>> spellsByLevel =spellMap.get(spellClass);
			TreeSet<Integer> levels = new TreeSet<>();
			levels.addAll(spellsByLevel.keySet());
			for(Integer level:levels) {
				
				
				Element table = XmlUtilities.addElement(body, "table");
				XmlUtilities.addAttribute(table, "border", "1");
				{
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addAttribute(row, "border", "0");					
					Element td =XmlUtilities.addElement(row, "td");					
					XmlUtilities.addElement(td, "h3", "Level "+level+" spells");
					XmlUtilities.addAttribute(td, "colspan", "6");
					XmlUtilities.addAttribute(td, "border", "0");
				}
				{
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElement(row, "th", "Name");
					XmlUtilities.addElement(row, "th", "Type");
					XmlUtilities.addElement(row, "th", "Comp");
					XmlUtilities.addElement(row, "th", "Range");
					XmlUtilities.addElement(row, "th", "AoE");
					XmlUtilities.addElement(row, "th", "Casting Time");
				}
				
				List<Spell> spells = spellsByLevel.get(level);
				for(Spell spell:spells) {
					{
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElement(row, "td", spell.getName());
					XmlUtilities.addElement(row, "td", spell.getSpellType());					
					XmlUtilities.addElement(row, "td", spell.getComponents());
					XmlUtilities.addElement(row, "td", spell.getRange());
					XmlUtilities.addElement(row, "td", spell.getAreaOfEffect());
					XmlUtilities.addElement(row, "td", spell.getCastingTime());
					}
					{
						Element row = XmlUtilities.addElement(table, "tr");
						Element td=XmlUtilities.addElement(row, "td", spell.getDescription());
						XmlUtilities.addAttribute(td, "colspan", "6");
					}
					if(spell.getBasedUponSpell()!=null) {
						Spell base = spell.getBasedUponSpell();
						{
							Element row = XmlUtilities.addElement(table, "tr");
							Element td=XmlUtilities.addElement(row, "td", base.getDescription());
							XmlUtilities.addAttribute(td, "colspan", "6");
						}
					}
				}
			}
			
		}
		
	}
	
	private void classSkillsTable(Map<String, CharacterClass> characterClasses, List<String> activeClasses,
			List<CharacterClassSkill> classSkills1, List<CharacterClassSkill> classSkills2,
			List<CharacterClassSkill> classSkills3, Element body) {
		if(classSkills1.size()>0 ||classSkills2.size()>0 ||classSkills3.size()>0) {
			XmlUtilities.addElement(body, "h2", "Class Skills");
			Element table = XmlUtilities.addElement(body, "table");
			XmlUtilities.addAttribute(table, "border", "1");
			{
				Element row = XmlUtilities.addElement(table, "tr");
				XmlUtilities.addElement(row, "th", "Class");
				XmlUtilities.addElement(row, "th", "Level");
				XmlUtilities.addElement(row, "th", "Skill");
				XmlUtilities.addElement(row, "th", "Definition");
			}
			List<CharacterClassSkill> classSkills = new ArrayList<>();
			classSkills.addAll(classSkills1);
			classSkills.addAll(classSkills2);
			classSkills.addAll(classSkills3);
			boolean anyInact=classSkills(characterClasses, classSkills, table,activeClasses);
			if(anyInact) {
				Element row = XmlUtilities.addElement(table, "tr");
				XmlUtilities.addElementWithAttribute(row, "td", "* = Inactive skills due to dual class restrictions","colspan","4");
			}
		}
	}

	private void raceSkillsTable(List<RaceSkill> raceSkills, Element body) {
		if(raceSkills.size()>0) {
			XmlUtilities.addElement(body, "h2", "Race Skills");
			Element table = XmlUtilities.addElement(body, "table");
			XmlUtilities.addAttribute(table, "border", "1");
			{
				Element row = XmlUtilities.addElement(table, "tr");
				XmlUtilities.addElement(row, "th", "Skill");
				XmlUtilities.addElement(row, "th", "Definition");
			}
			for (RaceSkill pce : raceSkills) {
				Element row = XmlUtilities.addElement(table, "tr");
				XmlUtilities.addElement(row, "td", pce.getSkillName());
				XmlUtilities.addElement(row, "td", pce.getSkillDefinition());
			}
		}
	}

	private void equippedTables(List<ViewPlayerCharacterEquipment> equipped,
			List<ViewPlayerCharacterEquipment> unEquipped, Element body) {
		{

			int enc = 0;

			Map<EquipmentWhere, List<ViewPlayerCharacterEquipment>> quip = new HashMap<>();
			for (EquipmentWhere ew : EquipmentWhere.values()) {
				quip.put(ew, new ArrayList<>());
			}

			if (equipped != null && equipped.size() > 0) {

				for (ViewPlayerCharacterEquipment pce : equipped) {
					{
						EquipmentWhere w = pce.getPlayerCharacterEquipment().getEquippedWhere();
						List<ViewPlayerCharacterEquipment> list = quip.get(w);
						list.add(pce);
						quip.put(w, list);
					}
				}

				{
					XmlUtilities.addElement(body, "h2", "Equipment");

					Element table = XmlUtilities.addElement(body, "table");
					XmlUtilities.addAttribute(table, "border", "1");
					{
						Element row = XmlUtilities.addElement(table, "tr");
						XmlUtilities.addElement(row, "th", "Where");
						XmlUtilities.addElement(row, "th", "Type");
						XmlUtilities.addElement(row, "th", "Name");
						XmlUtilities.addElement(row, "th", "Encumbrance");
					}

					List<EquipmentWhere> wheres = new ArrayList<>();
					wheres.addAll(quip.keySet());
					wheres.sort(new Comparator<EquipmentWhere>() {

						@Override
						public int compare(EquipmentWhere o1, EquipmentWhere o2) {
							if (o1.getIndex() > o2.getIndex()) {
								return 1;
							}
							if (o1.getIndex() < o2.getIndex()) {
								return -1;
							}
							return 0;
						}

					});
					for (EquipmentWhere key : wheres) {
						List<ViewPlayerCharacterEquipment> pces = quip.get(key);
						if (pces.size() == 0) {
							Element row = XmlUtilities.addElement(table, "tr");
							XmlUtilities.addElement(row, "td", key.getDesc());
							XmlUtilities.addElementWithAttribute(row, "td", "colspan", "3");
						} else {

							for (ViewPlayerCharacterEquipment pce : pces) {

								Element row = XmlUtilities.addElement(table, "tr");
								XmlUtilities.addElement(row, "td", key.getDesc());
								if (pce != null) {
									XmlUtilities.addElement(row, "td", pce.getPlayerCharacterEquipment().getEquipmentType().getDescription());
									String desc = pce.getEquipmentDescription();
									int co=(pce.getPlayerCharacterEquipment().getCountOwned());
									if(co>1) {
										desc = desc + " ("+co +" of)";
									}
									XmlUtilities.addElement(row, "td", desc);
									enc += pce.getEncumbrance();
									XmlUtilities.addElement(row, "td", pce.getEncumbrance());
								} else {
									XmlUtilities.addElementWithAttribute(row, "td", "colspan", "3");
								}
							}
						}
					}
				}
				{
					Element table = XmlUtilities.addElement(body, "table");
					XmlUtilities.addAttribute(table, "border", "1");
					{
						Element row = XmlUtilities.addElement(table, "tr");
						XmlUtilities.addElement(row, "th", "Total encumbrance");
						XmlUtilities.addElement(row, "th", "" + enc);
					}
				}

			}
			if (unEquipped != null && unEquipped.size() > 0) {

				XmlUtilities.addElement(body, "h2", "Unequipped");
				Element table = XmlUtilities.addElement(body, "table");
				XmlUtilities.addAttribute(table, "border", "1");
				{
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElement(row, "th", "What");
					XmlUtilities.addElement(row, "th", "Type");
					XmlUtilities.addElement(row, "th", "Encumbrance");
				}
				for (ViewPlayerCharacterEquipment pce : unEquipped) {
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElement(row, "td", pce.getDescription());
					XmlUtilities.addElement(row, "td", pce.getEquipmentDescription());
					XmlUtilities.addElement(row, "td", pce.getEncumbrance());
				}
			}

		}
	}

	private void baseSpellsTable(Map<String, String> classNames, List<ViewPlayerCharacterSpell> spells, Element body) {
		{
			
			if (spells != null && spells.size() > 0) {

				XmlUtilities.addElement(body, "h2", "Spells");

				Element table = XmlUtilities.addElement(body, "table");
				XmlUtilities.addAttribute(table, "border", "1");
				{
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElement(row, "th", "Class");
					XmlUtilities.addElement(row, "th", "Spell level");
					XmlUtilities.addElement(row, "th", "Name");
					XmlUtilities.addElement(row, "th", "Type");
					XmlUtilities.addElement(row, "th", "Comp");
					XmlUtilities.addElement(row, "th", "# Memorised");
				}
				int lastLevel = 0;

				for (ViewPlayerCharacterSpell spell : spells) {
					int level = spell.getSpell().getLevel();
					if (lastLevel != level && lastLevel > 0) {
						Element brow = XmlUtilities.addElement(table, "tr");
						XmlUtilities.addElementWithAttribute(brow, "td", "", "colspan", "6");
					}
					lastLevel = level;
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElement(row, "td", classNames.get(spell.getSpell().getSpellClass()));
					XmlUtilities.addElement(row, "td", spell.getSpell().getLevel());
					XmlUtilities.addElement(row, "td", spell.getSpell().getName());

					XmlUtilities.addElement(row, "td", spell.getSpell().getSpellType());
					XmlUtilities.addElement(row, "td", spell.getSpell().getComponents());

					XmlUtilities.addElementWithAttribute(row, "td", spell.getPlayerCharacterSpell().getInMemory(),
							"class", "cent");
				}

			}

		}
	}

	private void allowedSpellsTable(List<String> activeClasses, Map<String, String> classNames,
			List<CodedListItem> allowedSpells, Element body) {
		{

			if (allowedSpells.size() > 0) {
				XmlUtilities.addElement(body, "h2", "Spells per level");
				Element table = XmlUtilities.addElement(body, "table");
				XmlUtilities.addAttribute(table, "border", "1");
				{
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElementWithAttribute(row, "th", "Allowed Spells", "colspan", "3");
				}
				{
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElement(row, "th", "Class");
					XmlUtilities.addElement(row, "th", "Spell Class");
					XmlUtilities.addElement(row, "th", "Spells");
				}
				boolean anyInactive=false;
				for (CodedListItem<?> all : allowedSpells) {
					Element row = XmlUtilities.addElement(table, "tr");
					String[] ccs = ((String) all.getItem()).split(":");
					String classId =ccs[0];
					String spellClassId = ccs[1];
					boolean activeClass = activeClasses.contains(classId);
					if(activeClass) {
						XmlUtilities.addElement(row, "td", classNames.get(classId));
						XmlUtilities.addElement(row, "td", classNames.get(spellClassId));
						XmlUtilities.addElement(row, "td", all.getDescription());
					} else {
						anyInactive=true;
						String name = classNames.get(classId)+" *";
						XmlUtilities.addElementWithAttribute(row, "td", name,"class","inactive");
						XmlUtilities.addElementWithAttribute(row, "td", classNames.get(spellClassId),"class","inactive");
						XmlUtilities.addElementWithAttribute(row, "td", all.getDescription(),"class","inactive");
					}
				}
				if(anyInactive) {

					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElementWithAttribute(row, "td", "* = Inactive spells due to dual class restrictions","colspan","3");
				}
			}

		}
	}

	private void thiefSkillsTable(Map<String, CharacterClass> characterClasses, Race race, DataAccessThingy data,
			Map<Comparable, String> abilitiesMap, Element body, List<PlayerCharacterLevel> classLevels) {
		{

			

			boolean header = false;
			Element table = null;
			// do we have any thief skills?
			for (PlayerCharacterLevel cpl : classLevels) {
				CharacterClass cc = characterClasses.get(cpl.getThisClass());
				// 0 : as thief ; < 0 no abilities; 1 one level worse than thief; 2 two levels
				// worse than thief et
				if (cc.getThiefAbilities() >= 0) {
					int effTL = cpl.getThisClassLevel() - cc.getThiefAbilities();
					if (effTL > 0) {
						XmlUtilities.addElement(body, "h2", "Thief abilities");

						List<ThiefAbility> abilities = data.getThiefAbilities(effTL, race);
						if (header == false) {
							table = XmlUtilities.addElement(body, "table");
							XmlUtilities.addAttribute(table, "border", "1");
							Element row = XmlUtilities.addElement(table, "tr");
							for (ThiefAbility tab : abilities) {
								String ab = abilitiesMap.getOrDefault(tab.getThiefAbilityType(),
										tab.getThiefAbilityType());
								XmlUtilities.addElement(row, "th", ab);
							}

							header = true;
						}
						Element row = XmlUtilities.addElement(table, "tr");
						for (ThiefAbility tab : abilities) {
							XmlUtilities.addElement(row, "td", tab.getPercentageChanceString());
						}

					}
				}
			}
			//

		}
	}

	private void weaponTable(DataAccessThingy data, Strength strength, Dexterity dexterity,
			List<ViewPlayerCharacterEquipment> equipped, List<ViewPlayerCharacterEquipment> unEquipped, Element body) {
		{
			List<ViewPlayerCharacterEquipment> weaponsE = new ArrayList<>();
			List<ViewPlayerCharacterEquipment> weaponsU = new ArrayList<>();
			// equipped weapons
			for(ViewPlayerCharacterEquipment equip:equipped) {
				if(SU.inList(equip.getPlayerCharacterEquipment().getEquipmentType(),
						EquipmentType.MELEE_WEAPON,EquipmentType.WEAPON_RANGED)) {
					weaponsE.add(equip);
				}
			}
			for(ViewPlayerCharacterEquipment equip:unEquipped) {
				if(SU.inList(equip.getPlayerCharacterEquipment().getEquipmentType(),
						EquipmentType.MELEE_WEAPON,EquipmentType.WEAPON_RANGED)) {
					weaponsU.add(equip);
				}
			}
			if(weaponsE.size()+weaponsU.size()>0) {
				XmlUtilities.addElement(body, "h2", "Attacks");
				Element table = XmlUtilities.addElement(body, "table");
				XmlUtilities.addAttribute(table, "border", "1");
				
				{
					{
						Element row = XmlUtilities.addElement(table, "tr");
						Element th0=XmlUtilities.addElement(row, "th", "Weapon");
						XmlUtilities.addAttribute(th0, "colspan", "3");
						Element th1=XmlUtilities.addElement(row, "th", "Damage");
						XmlUtilities.addAttribute(th1, "colspan", "3");
						Element th2=XmlUtilities.addElement(row, "th", "To Hit Adjustments");
						XmlUtilities.addAttribute(th2, "colspan", "14");
					}
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElement(row, "th", "What");
					XmlUtilities.addElement(row, "th", "Type");
					XmlUtilities.addElement(row, "th", "Equipped?");
					XmlUtilities.addElement(row, "th", "SM");
					XmlUtilities.addElement(row, "th", "L");
					XmlUtilities.addElement(row, "th", "str adj");
					for(int i=2,n=10;i<=n;i++) {
						XmlUtilities.addElement(row, "th", "AC "+i);
					}
					XmlUtilities.addElement(row, "th", "str/dex");
				}
				for(ViewPlayerCharacterEquipment weapon:weaponsE) {
					doWeapon(table,weapon,true,data,strength,dexterity);
				}
				for(ViewPlayerCharacterEquipment weapon:weaponsU) {
					doWeapon(table,weapon,false,data,strength,dexterity);
				}
			}
			
		}
	}

	private void toHitTable(Map<String, CharacterClass> characterClasses, List<CharacterClassToHit> toHits,
			Element body) {
		{

			XmlUtilities.addElement(body, "h2", "To-hit");

			// to-hit values
			Element table = XmlUtilities.addElement(body, "table");
			XmlUtilities.addAttribute(table, "border", "1");
			//

			boolean first = true;
			for (CharacterClassToHit toHit : toHits) {

				Map<Integer, Integer> ma = ToHitUtils.getACToHitMap(toHit.getBiggestACHitBy20());
				List<Integer> ints = new ArrayList<>();
				ints.addAll(ma.keySet());
				ints.sort(new Comparator<Integer>() {

					@Override
					public int compare(Integer o1, Integer o2) {
						return o2.compareTo(o1);
					}

				});
				if (first) {
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElement(row, "th", "");
					for (int i : ints) {
						XmlUtilities.addElement(row, "th", "" + i);
					}
				}
				first = false;
				{
					Element row = XmlUtilities.addElement(table, "tr");
					String cnam = characterClasses.get(toHit.getClassId()).getName();
					XmlUtilities.addElement(row, "th", "To hit as " + cnam);
					for (int i : ints) {
						XmlUtilities.addElement(row, "td", "" + ma.get(i));
					}
				}

			}

		}
	}

	private void savingThrowsTable(List<SavingThrow> savingThrows, Map<Comparable, String> stNames,
			String magicDefenceBonus, Wisdom wisdom, Element body) {
		{ // Saving throws

			XmlUtilities.addElement(body, "h2", "Saving throws");

			//Element table = XmlUtilities.addElementWithAttribute(body, "table", "class", "fiddy");
			Element table = XmlUtilities.addElement(body, "table");

			XmlUtilities.addAttribute(table, "border", "1");
//				{


//					XmlUtilities.addElement(row, "th", "Saving Throws");

//				}
			Element row1 = XmlUtilities.addElement(table, "tr");
			Element row2 = XmlUtilities.addElement(table, "tr");
			for (SavingThrow st : savingThrows) {					
				String name = stNames.get(st.getSavingThrowTypeString());
				XmlUtilities.addElement(row1, "th", name);
				XmlUtilities.addElement(row2, "td", st.getRollRequired());

			}
						
			if(magicDefenceBonus!=null&&!magicDefenceBonus.trim().isEmpty()) {
				Element row = XmlUtilities.addElement(table, "tr");
				XmlUtilities.addElementWithAttribute(row, "td", magicDefenceBonus,"colspan","5");
			}
			if(wisdom.getMagicalAttackAdjustment()!=0) {
				Element row = XmlUtilities.addElement(table, "tr");
				XmlUtilities.addElementWithAttribute(row, "td", "Wisdom magical attack adjustment="+SU.a(wisdom.getMagicalAttackAdjustment()),"colspan","5");				
			}
		}
	}

	private void armourClassTable(int dexBonus, int baseAc, int shieldBonus, int itemBonus, String armourName,
			String shieldName, String itemName, int finalArmourClass, Element body) {
		/* Armour class table */
		{
			Element table = XmlUtilities.addElement(body, "table");

			XmlUtilities.addAttribute(table, "border", "1");
			Element row = XmlUtilities.addElement(table, "tr");
			XmlUtilities.addElement(row, "th", "Armour Class");
			XmlUtilities.addElement(row, "td", "");
			XmlUtilities.addElement(row, "th", "Base A/C");
			XmlUtilities.addElement(row, "th", "Dexterity bonus");
			XmlUtilities.addElement(row, "th", "Shield bonus");
			XmlUtilities.addElement(row, "th", "Magic item bonus");
			
			row = XmlUtilities.addElement(table, "tr");
			XmlUtilities.addElement(row, "td", ""+finalArmourClass);
			XmlUtilities.addElement(row, "td", "");
			XmlUtilities.addElement(row, "td", ""+baseAc);
			XmlUtilities.addElement(row, "td", ""+SU.a(dexBonus,"none"));
			XmlUtilities.addElement(row, "td", ""+SU.a(shieldBonus,"none"));
			XmlUtilities.addElement(row, "td", ""+SU.a(itemBonus,"none"));
			
			row = XmlUtilities.addElement(table, "tr");
			XmlUtilities.addElement(row, "td", "");
			XmlUtilities.addElement(row, "td", "");
			XmlUtilities.addElement(row, "td", armourName);
			XmlUtilities.addElement(row, "td", "");
			XmlUtilities.addElement(row, "td", shieldName);
			XmlUtilities.addElement(row, "td", itemName);
			
		
		}
	}

	private void characterAttributesTable(PlayerCharacter pc, Strength strength, Intelligence intelligence,
			Wisdom wisdom, Dexterity dexterity, Constitution constitution, Charisma charisma,
			List<String> divSpellBonuses, boolean extraHitPointBonus, Element body) {
		{
			Element table = XmlUtilities.addElement(body, "table");

			XmlUtilities.addAttribute(table, "border", "1");
			{
				Element row = XmlUtilities.addElement(table, "tr");
				XmlUtilities.addElementWithAttribute(row, "th", "STR", "class", "attribute");
				XmlUtilities.addElementWithAttribute(row, "td", pc.getStrengthString(), "class", "strength");
				XmlUtilities.addElement(row, "td", "To-hit");
				XmlUtilities.addElement(row, "td", SU.a(strength.getHitProbability(), "normal"));
				XmlUtilities.addElement(row, "td", "Dmg adj");
				XmlUtilities.addElement(row, "td", SU.a(strength.getDamageAdjustment(), "normal"));
				XmlUtilities.addElement(row, "td", "Weight all.");
				XmlUtilities.addElement(row, "td", SU.a(strength.getWeightAllowance(), "normal"));
				XmlUtilities.addElement(row, "td", "Open doors");
				XmlUtilities.addElement(row, "td", strength.getOpenDoorsString());
				XmlUtilities.addElement(row, "td", "Bend bars/Lift gates");
				XmlUtilities.addElement(row, "td", SU.p(strength.getBendBarsLiftGates(), "0%"));
			}
			{
				Element row = XmlUtilities.addElement(table, "tr");
				XmlUtilities.addElementWithAttribute(row, "th", "INT", "class", "attribute");
				XmlUtilities.addElementWithAttribute(row, "td", pc.getAttrInt(), "class", "intelligence");
				XmlUtilities.addElement(row, "td", "Add. lang");
				XmlUtilities.addElement(row, "td", intelligence.getPossibleAdditionalLanguages());
				XmlUtilities.addElement(row, "td", "Chance to know spell");
				XmlUtilities.addElement(row, "td", SU.p(intelligence.getChanceToKnowSpell(), ""));
				XmlUtilities.addElement(row, "td", "Min spells/lvl");
				XmlUtilities.addElement(row, "td", intelligence.getMinSpellsPerLevel());
				XmlUtilities.addElement(row, "td", "Max spells/lvl");
				XmlUtilities.addElement(row, "td", intelligence.getMaxSpellsPerLevel());
			}
			{
				Element row = XmlUtilities.addElement(table, "tr");
				XmlUtilities.addElementWithAttribute(row, "th", "WIS", "class", "attribute");
				XmlUtilities.addElementWithAttribute(row, "td", pc.getAttrWis(), "class", "wisdom");
				XmlUtilities.addElement(row, "td", "MAA");
				XmlUtilities.addElement(row, "td", SU.a(wisdom.getMagicalAttackAdjustment(), "none"));
				XmlUtilities.addElement(row, "td", "Spell bonus");
				Element bs = XmlUtilities.addElement(row, "td");
				XmlUtilities.addAttribute(bs, "colspan", "3");
				for (String s : divSpellBonuses) {
					XmlUtilities.addElement(bs, "div", s);
				}

				XmlUtilities.addElement(row, "td", "Chance of spell failure");
				XmlUtilities.addElement(row, "td", SU.p(wisdom.getDivineSpellChanceFailure(), "0%"));
			}
			{
				Element row = XmlUtilities.addElement(table, "tr");
				XmlUtilities.addElementWithAttribute(row, "th", "DEX", "class", "attribute");
				XmlUtilities.addElementWithAttribute(row, "td", pc.getAttrDex(), "class", "dexterity");
				XmlUtilities.addElement(row, "td", "Reaction/attack adj");
				XmlUtilities.addElement(row, "td", SU.a(dexterity.getReactionAttackAdjustment(), "0"));
				XmlUtilities.addElement(row, "td", "Def adj");
				XmlUtilities.addElement(row, "td", SU.a(dexterity.getDefensiveAdjustment(), "0"));
			}
			{
				Element row = XmlUtilities.addElement(table, "tr");
				XmlUtilities.addElementWithAttribute(row, "th", "CON", "class", "attribute");
				XmlUtilities.addElementWithAttribute(row, "td", pc.getAttrCon(), "class", "constitution");
				XmlUtilities.addElement(row, "td", "HP adj");

				if (extraHitPointBonus && constitution.getHitPointAdjustmentHigh() > 0) {
					XmlUtilities.addElement(row, "td", SU.a(constitution.getHitPointAdjustment(), "0") + "(" +

							SU.a(constitution.getHitPointAdjustmentHigh(), "0") + ")*");
				} else {
					XmlUtilities.addElement(row, "td", SU.a(constitution.getHitPointAdjustment(), "0"));
				}
				XmlUtilities.addElement(row, "td", "System shock surv.");
				XmlUtilities.addElement(row, "td", SU.p(constitution.getSystemShockSurvival()));
				XmlUtilities.addElement(row, "td", "Ress. surv.");
				XmlUtilities.addElement(row, "td", SU.p(constitution.getResurrectionSurvival()));

			}
			{
				Element row = XmlUtilities.addElement(table, "tr");
				XmlUtilities.addElementWithAttribute(row, "th", "CHR", "class", "attribute");
				XmlUtilities.addElementWithAttribute(row, "td", pc.getAttrChr(), "class", "charisma");
				XmlUtilities.addElement(row, "td", "Max henchmen");
				XmlUtilities.addElement(row, "td", (charisma.getMaxHenchmen()));
				XmlUtilities.addElement(row, "td", "Loyalty base");
				XmlUtilities.addElement(row, "td", SU.p(charisma.getLoyaltyBase(), "normal"));
				XmlUtilities.addElement(row, "td", "Reaction adj");
				XmlUtilities.addElement(row, "td", SU.p(charisma.getReactionAdjustment(), "normal"));
			}
		}
	}

	private int characterClassesTable(PlayerCharacter pc, Map<String, CharacterClass> characterClasses, Element body,
			List<PlayerCharacterLevel> classLevels, int hp) {
		{
			Element table = XmlUtilities.addElement(body, "table");
			XmlUtilities.addAttribute(table, "border", "1");
			{
				Element row = XmlUtilities.addElement(table, "tr");
				XmlUtilities.addElement(row, "th", "Class");
				XmlUtilities.addElement(row, "th", "Level");
				XmlUtilities.addElement(row, "th", "XP"); // need to ","ise these
				XmlUtilities.addElement(row, "th", "HP");
			}
			for (PlayerCharacterLevel pcl : pc.getClassDetails()) {
				if (pcl != null && pcl.getThisClass() != null) {
					classLevels.add(pcl);
					CharacterClass cc = characterClasses.get(pcl.getThisClass());
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElement(row, "td", cc.getName());
					XmlUtilities.addElement(row, "td", pcl.getThisClassLevel());
					XmlUtilities.addElement(row, "td", pcl.getThisClassExperience());
					hp += pcl.getThisClassHp();
					XmlUtilities.addElement(row, "td", pcl.getThisClassHp());

				}

			}
		}
		return hp;
	}

	private Element characterBasicsTable(PlayerCharacter pc, Race race, Element body) {
		Element mainRow;
		{
			Element table = XmlUtilities.addElement(body, "table");
			XmlUtilities.addAttribute(table, "border", "1");
			Element row = XmlUtilities.addElement(table, "tr");

			XmlUtilities.addElement(row, "th", "Character Name");
			XmlUtilities.addElement(row, "td", pc.getCharacterName());
			XmlUtilities.addElement(row, "th", "Gender");
			XmlUtilities.addElement(row, "td", pc.getGender().name());
			XmlUtilities.addElement(row, "th", "Race");
			XmlUtilities.addElement(row, "td", race.getName());
			mainRow = row;
		}
		return mainRow;
	}

	private boolean classSkills(Map<String, CharacterClass> characterClasses, List<CharacterClassSkill> classSkills1,
			Element table, List<String> activeClasses) {
		boolean anInactiveClass=false;
		for (CharacterClassSkill pce : classSkills1) {
			boolean activeClass= activeClasses.contains(pce.getClassId());
			String name = characterClasses.get(pce.getClassId()).getName();
			if(!activeClass) {
				name = name+" *";
				anInactiveClass=true;
			}
			Element row = XmlUtilities.addElement(table, "tr");
			if(activeClass){
				XmlUtilities.addElement(row, "td", name);
				XmlUtilities.addElement(row, "td", ""+pce.getFromLevel());
				XmlUtilities.addElement(row, "td", pce.getSkillName());
				XmlUtilities.addElement(row, "td", pce.getSkillDefinition());
			} else {
				XmlUtilities.addElementWithAttribute(row, "td", name,"class","inactive");
				XmlUtilities.addElementWithAttribute(row, "td", ""+pce.getFromLevel(),"class","inactive");
				XmlUtilities.addElementWithAttribute(row, "td", pce.getSkillName(),"class","inactive");
				XmlUtilities.addElementWithAttribute(row, "td", pce.getSkillDefinition(),"class","inactive");				
			}
		}
		return anInactiveClass;
	}

	private String readStyles() {
		String fileName = "embed.css";
		StringBuilder sb = new StringBuilder();
		try {
			File file = new File(getClass().getClassLoader().getResource(fileName).getFile());

			try (FileReader fr = new FileReader(file)) {
				try (BufferedReader br = new BufferedReader(fr)) {
					String line = br.readLine();
					while (line != null) {
						sb.append(line).append(System.getProperty("line.separator"));
						line = br.readLine();
					}
				}

			}
		} catch (Exception ex) {
			LOGGER.error("Error reading styles", ex);
		}
		return sb.toString();
	}

	
	private void doWeapon(Element table,ViewPlayerCharacterEquipment weapon,boolean equipped,DataAccessThingy data,
			Strength str,Dexterity dex) {
		PlayerCharacterEquipment pce = weapon.getPlayerCharacterEquipment();
		boolean melee=weapon.getPlayerCharacterEquipment().getEquipmentType().equals(EquipmentType.MELEE_WEAPON);
		Element row = XmlUtilities.addElement(table, "tr");
		XmlUtilities.addElement(row, "td", weapon.getEquipmentDescription());
		XmlUtilities.addElement(row, "td", weapon.getPlayerCharacterEquipment().getEquipmentType().getDescription());
		String eq="No";
		if(equipped) {
		eq = pce.getEquippedWhere().getDescription();	
		}
		XmlUtilities.addElement(row, "td", eq);
		// damage and stuff
		WeaponDamageI dam=data.getWeapon(pce);
		if(melee) {
			XmlUtilities.addElement(row, "td", dam.getSMDamage());
			XmlUtilities.addElement(row, "td", dam.getLDamage() );
			XmlUtilities.addElement(row, "td", SU.a(str.getDamageAdjustment(),""));
		} else {
		XmlUtilities.addElement(row, "td", dam.getSMDamage());
		XmlUtilities.addElement(row, "td", dam.getLDamage());
		XmlUtilities.addElement(row, "td", "");
		}
		WeaponACAdjustmentsI aca = (WeaponACAdjustmentsI)dam;
		Map<String,Integer> acas = aca.getACAdjustments();
		for(int i=2,n=10;i<=n;i++) {
			int adj = acas.get(""+i);
			XmlUtilities.addElement(row, "td", SU.a(adj, "-"));
		}
		String tha ="";
		if(melee) {
			tha = SU.a(str.getHitProbability(),"");
		} else {
			tha = SU.a(dex.getReactionAttackAdjustment(),"");
		}
		XmlUtilities.addElement(row, "td", tha);
	}
}
