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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.first.common.entities.equipment.EquipmentType;
import com.nobbysoft.first.common.entities.equipment.EquipmentWhere;
import com.nobbysoft.first.common.entities.equipment.WeaponACAdjustmentsI;
import com.nobbysoft.first.common.entities.equipment.WeaponDamageI;
import com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterEquipment;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterLevel;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassSkill;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassToHit;
import com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.first.common.entities.staticdto.RaceSkill;
import com.nobbysoft.first.common.entities.staticdto.SavingThrow;
import com.nobbysoft.first.common.entities.staticdto.ThiefAbility;
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

	public String makeDocument(PlayerCharacter pc, Map<String, CharacterClass> characterClasses, Race race,
			List<SavingThrow> savingThrows, DataAccessThingy data) {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element html = doc.createElement("html");
			doc.appendChild(html);

			Element head = XmlUtilities.addElement(html, "head");

			String styles = readStyles();

			Element style = XmlUtilities.addElement(head, "style", styles);

			Element body = XmlUtilities.addElement(html, "body");

			List<PlayerCharacterLevel> classLevels = new ArrayList<>();

			Element mainRow;
			XmlUtilities.addElement(body, "h1", "Character Sheet - " + pc.getCharacterName());
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
			int hp = 0;
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

			Strength strength = data.getStrength(pc.getAttrStr(), pc.getExceptionalStrength());
			Intelligence intelligence = data.getIntelligence(pc.getAttrInt());
			Wisdom wisdom = data.getWisdom(pc.getAttrWis());
			Dexterity dexterity = data.getDexterity(pc.getAttrDex());
			Constitution constitution = data.getConstitution(pc.getAttrCon());
			Charisma charisma = data.getCharisma(pc.getAttrChr());

			List<String> divSpellBonuses = data.getDivineSpellBonus(pc.getAttrWis());

			Map<Comparable, String> stNames = data.getSavingThrowNameMap();

			List<CharacterClassToHit> toHits = data.getToHit(pc, race);
			
			
			List<String> activeClasses = data.getActiveClasses(pc, race);

			boolean extraHitPointBonus = false;
			for (CharacterClass c : characterClasses.values()) {
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
					magicDefenceBonus = "Magic defence bonus:" + mdb;
				}
			}

			XmlUtilities.addElement(mainRow, "th", "HP");
			XmlUtilities.addElement(mainRow, "td", "" + hp);

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

			{ // Saving throws

				XmlUtilities.addElement(body, "h2", "Saving Throws");

				Element table = XmlUtilities.addElementWithAttribute(body, "table", "class", "fiddy");

				XmlUtilities.addAttribute(table, "border", "1");
				{
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElement(row, "th", "Saving Throws");
					XmlUtilities.addElement(row, "td", magicDefenceBonus);
				}
				for (SavingThrow st : savingThrows) {
					Element row = XmlUtilities.addElement(table, "tr");
					String name = stNames.get(st.getSavingThrowTypeString());
					XmlUtilities.addElement(row, "th", name);
					XmlUtilities.addElement(row, "td", st.getRollRequired());

				}
			}
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
			
			Map<String, List<ViewPlayerCharacterEquipment>> equipment = data.getEquipment(pc.getPcId());
			List<ViewPlayerCharacterEquipment> equipped = equipment.get("E");
			List<ViewPlayerCharacterEquipment> unEquipped = equipment.get("N");
			
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
			{

				Map<Comparable, String> abilitiesMap = data.getCodedListMap(Constants.CLI_THIEF_ABILITY);

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
			/// need class map

			Map<String, String> classNames = data.getClassNames();

			// List<String> activeClasses
			
			{

				List<CodedListItem> allowed = data.workOutAllowedSpells(pc);
				if (allowed.size() > 0) {
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
					for (CodedListItem<?> all : allowed) {
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

			{
				List<ViewPlayerCharacterSpell> spells = data.grabSpells(pc.getPcId());
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
			
			// SKILLS
			//
			//
			//
			List<RaceSkill> raceSkills = data.getRaceSkills(pc.getRaceId());
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

			
			List<CharacterClassSkill> classSkills1 = data.getCharacterClassSkills(pc.getFirstClass(), pc.getFirstClassLevel());
			List<CharacterClassSkill> classSkills2 = data.getCharacterClassSkills(pc.getSecondClass(), pc.getSecondClassLevel());
			List<CharacterClassSkill> classSkills3 = data.getCharacterClassSkills(pc.getThirdClass(), pc.getThirdClassLevel());
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
			
			
			String xmlString = XmlUtilities.xmlToHtmlString(doc);

			return xmlString;

		} catch (Exception e) {
			return makeHtmlError(e);
		}
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
