package com.nobbysoft.first.client.utils;

import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterLevel;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.first.common.entities.staticdto.SavingThrow;
import com.nobbysoft.first.common.entities.staticdto.attributes.Charisma;
import com.nobbysoft.first.common.entities.staticdto.attributes.Constitution;
import com.nobbysoft.first.common.entities.staticdto.attributes.Dexterity;
import com.nobbysoft.first.common.entities.staticdto.attributes.Intelligence;
import com.nobbysoft.first.common.entities.staticdto.attributes.Strength;
import com.nobbysoft.first.common.entities.staticdto.attributes.Wisdom;
import com.nobbysoft.first.common.utils.SU;

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
			Element body = doc.createElement("body");
			doc.appendChild(body);

			
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
			
			{ // 
				// if we have multiples, we pick the lowest one of each!
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
			String xmlString = XmlUtilities.xmlToHtmlString(doc);

			return xmlString;

		} catch (Exception e) {
			return makeHtmlError(e);
		}
	}

	
	
 

}
