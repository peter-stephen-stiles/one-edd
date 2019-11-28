package com.nobbysoft.first.client.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterLevel;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassToHit;
import com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.first.common.entities.staticdto.SavingThrow;
import com.nobbysoft.first.common.entities.staticdto.ThiefAbility;
import com.nobbysoft.first.common.entities.staticdto.attributes.Charisma;
import com.nobbysoft.first.common.entities.staticdto.attributes.Constitution;
import com.nobbysoft.first.common.entities.staticdto.attributes.Dexterity;
import com.nobbysoft.first.common.entities.staticdto.attributes.Intelligence;
import com.nobbysoft.first.common.entities.staticdto.attributes.Strength;
import com.nobbysoft.first.common.entities.staticdto.attributes.Wisdom;
import com.nobbysoft.first.common.utils.SU;
import com.nobbysoft.first.common.utils.ToHitUtils;

public class MakeHTML {

	public MakeHTML() {

	}

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

	public String makeDocument(PlayerCharacter pc,
			Map<String,CharacterClass> characterClasses,
			Race race,
			List<SavingThrow> savingThrows,
			DataAccessThingy data) {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element html = doc.createElement("html");
			doc.appendChild(html);
			
//			Element head = XmlUtilities.addElement(html, "head");
						
//			Element head = XmlUtilities.addElement(html, "head");

/*			
			Element link = XmlUtilities.addElement(head, "link");
			XmlUtilities.addAttribute(link, "href", "http://nobby.co.uk/cola.css");
			XmlUtilities.addAttribute(link, "rel", "stylesheet");
			XmlUtilities.addAttribute(link, "type", "text/css");
			//<link href="cola.css" rel="stylesheet" type="text/css" />
	*/		
			Element body = XmlUtilities.addElement(html, "body");
			
			
			List<PlayerCharacterLevel> classLevels= new ArrayList<>();

			
			Element mainRow;
			XmlUtilities.addElement(body, "h1", "Character Sheet");
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
				mainRow=row;
			}
			int hp=0; 
			{
				Element table = XmlUtilities.addElement(body, "table");
				XmlUtilities.addAttribute(table, "border", "1");
				{
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElement(row, "th", "Class");
					XmlUtilities.addElement(row, "th", "Level");
					XmlUtilities.addElement(row, "th", "XP"); //need to ","ise these
					XmlUtilities.addElement(row, "th", "HP");
				}
				for(PlayerCharacterLevel pcl:pc.getClassDetails()) {
					if(pcl!=null && pcl.getThisClass()!=null) {
						classLevels.add(pcl);
						CharacterClass cc = characterClasses.get(pcl.getThisClass());
						Element row = XmlUtilities.addElement(table, "tr");
						XmlUtilities.addElement(row, "td", cc.getName());
						XmlUtilities.addElement(row, "td", pcl.getThisClassLevel());
						XmlUtilities.addElement(row, "td", pcl.getThisClassExperience());
						hp+=pcl.getThisClassHp();
						XmlUtilities.addElement(row, "td", pcl.getThisClassHp());
						
					}
					
				}
			}
			
			
			Strength strength=data.getStrength(pc.getAttrStr(),pc.getExceptionalStrength()); 
			Intelligence intelligence = data.getIntelligence(pc.getAttrInt());
			Wisdom wisdom= data.getWisdom(pc.getAttrWis());
			Dexterity dexterity = data.getDexterity(pc.getAttrDex());
			Constitution constitution= data.getConstitution(pc.getAttrCon());
			Charisma charisma = data.getCharisma(pc.getAttrChr());
			
			List<String> divSpellBonuses = data.getDivineSpellBonus(pc.getAttrWis());			
			
			 Map<Comparable,String> stNames= data.getSavingThrowNameMap();
			
			 List<CharacterClassToHit> toHits = data.getToHit(pc,race);
			 
			
			boolean extraHitPointBonus=false;
			for(CharacterClass c:characterClasses.values()) {
				if(c!=null) {
					if(c.isHighConBonus()) {
						extraHitPointBonus=true;
					}				
				}
			}
			String magicDefenceBonus = "";
			if(race.isHasMagicDefenceBonus()) {
				int mdb = (int) (((1.0f * pc.getAttrCon()) / 3.5f));
				if(mdb>0) {
					magicDefenceBonus ="Magic defence bonus:"+mdb;
				}
			}

			
			XmlUtilities.addElement(mainRow, "th", "HP");
			XmlUtilities.addElement(mainRow, "td", ""+hp);
			
			{
				Element table = XmlUtilities.addElement(body, "table");

				XmlUtilities.addAttribute(table, "border", "1");
				{
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElementWithAttribute(row, "th", "STR","class","attribute");
					XmlUtilities.addElementWithAttribute(row, "td", pc.getStrengthString(),"class","strength");
					XmlUtilities.addElement(row, "td", "To-hit");
					XmlUtilities.addElement(row, "td", SU.a(strength.getHitProbability(),"normal"));
					XmlUtilities.addElement(row, "td", "Dmg adj");
					XmlUtilities.addElement(row, "td", SU.a(strength.getDamageAdjustment(),"normal"));
					XmlUtilities.addElement(row, "td", "Weight all.");
					XmlUtilities.addElement(row, "td", SU.a(strength.getWeightAllowance(),"normal"));
					XmlUtilities.addElement(row, "td", "Open doors");
					XmlUtilities.addElement(row, "td", strength.getOpenDoorsString());
					XmlUtilities.addElement(row, "td", "Bend bars/Lift gates");
					XmlUtilities.addElement(row, "td", SU.p(strength.getBendBarsLiftGates(),"0%"));					
				}
				{
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElementWithAttribute(row, "th", "INT","class","attribute");
					XmlUtilities.addElementWithAttribute(row, "td", pc.getAttrInt(),"class","intelligence");
					XmlUtilities.addElement(row, "td", "Add. lang");
					XmlUtilities.addElement(row, "td", intelligence.getPossibleAdditionalLanguages());
					XmlUtilities.addElement(row, "td", "Chance to know spell");
					XmlUtilities.addElement(row, "td", SU.p(intelligence.getChanceToKnowSpell(),""));
					XmlUtilities.addElement(row, "td", "Min spells/lvl");					
					XmlUtilities.addElement(row, "td", intelligence.getMinSpellsPerLevel());
					XmlUtilities.addElement(row, "td", "Max spells/lvl");
					XmlUtilities.addElement(row, "td", intelligence.getMaxSpellsPerLevel());
				}
				{
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElementWithAttribute(row, "th", "WIS","class","attribute");
					XmlUtilities.addElementWithAttribute(row, "td", pc.getAttrWis(),"class","wisdom");
					XmlUtilities.addElement(row, "td", "MAA");
					XmlUtilities.addElement(row, "td", SU.a(wisdom.getMagicalAttackAdjustment(),"none"));
					XmlUtilities.addElement(row, "td", "Spell bonus");					
					Node bs = XmlUtilities.addElement(row, "td");
					for(String s:divSpellBonuses) {
						XmlUtilities.addElement(bs, "p", s);			
					}

					XmlUtilities.addElement(row, "td", "Chance of spell failure");
					XmlUtilities.addElement(row, "td", SU.p(wisdom.getDivineSpellChanceFailure(),"0%"));
				}
				{
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElementWithAttribute(row, "th", "DEX","class","attribute");
					XmlUtilities.addElementWithAttribute(row, "td", pc.getAttrDex(),"class","dexterity");
					XmlUtilities.addElement(row, "td", "Reaction/attack adj");
					XmlUtilities.addElement(row, "td", SU.a(dexterity.getReactionAttackAdjustment(),"0"));
					XmlUtilities.addElement(row, "td", "Def adj");
					XmlUtilities.addElement(row, "td", SU.a(dexterity.getDefensiveAdjustment(),"0"));					
				}
				{
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElementWithAttribute(row, "th", "CON","class","attribute");
					XmlUtilities.addElementWithAttribute(row, "td", pc.getAttrCon(),"class","constitution");
					XmlUtilities.addElement(row, "td", "HP adj");
					
					if(extraHitPointBonus && constitution.getHitPointAdjustmentHigh()>0) {
						XmlUtilities.addElement(row, "td", 
								SU.a(constitution.getHitPointAdjustment(),"0")+"("+
								
								SU.a(constitution.getHitPointAdjustmentHigh(),"0")+")*");
					} else {
						XmlUtilities.addElement(row, "td", SU.a(constitution.getHitPointAdjustment(),"0"));
					}
					XmlUtilities.addElement(row, "td", "Sys shk surv.");
					XmlUtilities.addElement(row, "td", SU.p(constitution.getSystemShockSurvival()));
					XmlUtilities.addElement(row, "td", "Ress. surv.");
					XmlUtilities.addElement(row, "td", SU.p(constitution.getResurrectionSurvival()));
					
				}
				{
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElementWithAttribute(row, "th", "CHR","class","attribute");
					XmlUtilities.addElementWithAttribute(row, "td",pc.getAttrChr(),"class","charisma");
					XmlUtilities.addElement(row, "td", "Max hench.");
					XmlUtilities.addElement(row, "td", (charisma.getMaxHenchmen()));
					XmlUtilities.addElement(row, "td", "Loyalty base");
					XmlUtilities.addElement(row, "td", SU.p(charisma.getLoyaltyBase(),"normal"));
					XmlUtilities.addElement(row, "td", "Reaction adj");
					XmlUtilities.addElement(row, "td", SU.p(charisma.getReactionAdjustment(),"normal"));
				}
			}
			
			{ // Saving throws
				Element table = XmlUtilities.addElement(body, "table");

				XmlUtilities.addAttribute(table, "border", "1");
				{
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElement(row, "th", "Saving Throws");
					XmlUtilities.addElement(row, "td",magicDefenceBonus);
				}
				for(SavingThrow st: savingThrows) {
					Element row = XmlUtilities.addElement(table, "tr");
					String name =stNames.get(st.getSavingThrowTypeString());
					XmlUtilities.addElement(row, "th", name);
					XmlUtilities.addElement(row, "td",st.getRollRequired());
					
				}
			}
			{
				// to-hit values
				Element table = XmlUtilities.addElement(body, "table");
				XmlUtilities.addAttribute(table, "border", "1");
				// 
				
				
				boolean first=true;
				for(CharacterClassToHit toHit:toHits) {
					
					Map<Integer,Integer>ma= ToHitUtils.getACToHitMap(toHit.getBiggestACHitBy20());
					List<Integer> ints = new ArrayList<>();
					ints.addAll(ma.keySet());
					ints.sort(new Comparator<Integer>(){

						@Override
						public int compare(Integer o1, Integer o2) {
							return o2.compareTo(o1);
						}
						
					});
					if(first){
						Element row = XmlUtilities.addElement(table, "tr");
						XmlUtilities.addElement(row, "th", "");
						for(int i:ints) {
							XmlUtilities.addElement(row, "th", ""+i);
						}
					}
					first=false;
					{
						Element row = XmlUtilities.addElement(table, "tr"); 
						String cnam = characterClasses.get(toHit.getClassId()).getName();
						XmlUtilities.addElement(row, "th", "To hit as "+cnam);
						for(int i:ints) {
							XmlUtilities.addElement(row, "td", ""+ma.get(i));
						}
					}
					
					
					
					
				}
				
			}
			
			{
				// do we have any thief skills?
				for(PlayerCharacterLevel cpl:classLevels) {
					CharacterClass cc = characterClasses.get(cpl.getThisClass());
					// 0 : as thief ;  < 0 no abilities; 1 one level worse than thief; 2 two levels worse than thief et
					if(cc.getThiefAbilities()>=0) {
						int effTL = cpl.getThisClassLevel() - cc.getThiefAbilities();
						if(effTL>0) {
							List<ThiefAbility> abilities = data.getThiefAbilities(effTL,race);
							
							
						}
					}
				}
				//
				
				
			}
			String xmlString = XmlUtilities.xmlToHtmlString(doc);

			return xmlString;

		} catch (Exception e) {
			return makeHtmlError(e);
		}
	}

	
	
 

}
