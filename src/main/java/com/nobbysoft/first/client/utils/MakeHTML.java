package com.nobbysoft.first.client.utils;

import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
			
			
			
			XmlUtilities.addElement(mainRow, "th", "HP");
			XmlUtilities.addElement(mainRow, "td", ""+hp);
			
			{
				Element table = XmlUtilities.addElement(body, "table");

				XmlUtilities.addAttribute(table, "border", "1");
				{
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElement(row, "th", "str");
					XmlUtilities.addElement(row, "td", pc.getAttrStr());
				}
				{
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElement(row, "th", "int");
					XmlUtilities.addElement(row, "td", pc.getAttrInt());
				}
				{
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElement(row, "th", "wis");
					XmlUtilities.addElement(row, "td", pc.getAttrWis());
				}
				{
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElement(row, "th", "dex");
					XmlUtilities.addElement(row, "td", pc.getAttrDex());
				}
				{
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElement(row, "th", "con");
					XmlUtilities.addElement(row, "td", pc.getAttrCon());
				}
				{
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElement(row, "th", "chr");
					XmlUtilities.addElement(row, "td",pc.getAttrChr());
				}
			}
			
			{ // saving throws
				// if we have multiples, we pick the lowest one of each!
				Element table = XmlUtilities.addElement(body, "table");

				XmlUtilities.addAttribute(table, "border", "1");
				{
					Element row = XmlUtilities.addElement(table, "tr");
					XmlUtilities.addElement(row, "th", "str");
					XmlUtilities.addElement(row, "td", pc.getAttrStr());
				}
			}
			String xmlString = XmlUtilities.xmlToHtmlString(doc);

			return xmlString;

		} catch (Exception e) {
			return makeHtmlError(e);
		}
	}

	
	
 

}
