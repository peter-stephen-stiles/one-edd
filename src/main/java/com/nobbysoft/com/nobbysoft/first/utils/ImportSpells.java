package com.nobbysoft.com.nobbysoft.first.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.nobbysoft.com.nobbysoft.first.MainUtils;
import com.nobbysoft.com.nobbysoft.first.client.utils.XmlUtilities;
import com.nobbysoft.com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.Spell;
import com.nobbysoft.com.nobbysoft.first.server.dao.CharacterClassDAO;
import com.nobbysoft.com.nobbysoft.first.server.dao.SpellDAO;
import com.nobbysoft.com.nobbysoft.first.server.db.StartNetworkServer;
import com.nobbysoft.com.nobbysoft.first.server.utils.ConnectionManager;

public class ImportSpells {

	/**
	 * map their class name anchors to MY character class key for that type of spell
	 */

	CharacterClass cc;

	private Map<String, String> themToMe = new HashMap<>();

	private void populateMapping(Connection con) {
		String clericId = "";
		String muId = "";
		String druidId = "";
		String illId = "";
		CharacterClassDAO ccdao = new CharacterClassDAO();
		try {
			List<CharacterClass> ccs = ccdao.getList(con);
			for (CharacterClass c : ccs) {
				if (c.getName().toLowerCase().indexOf("druid") >= 0) {
					druidId = c.getClassId();
				} else if (c.getName().toLowerCase().indexOf("cleric") >= 0) {
					clericId = c.getClassId();
				} else if (c.getName().toLowerCase().indexOf("ill") >= 0) {
					illId = c.getClassId();
				} else if (c.getName().toLowerCase().indexOf("magic") >= 0) {
					muId = c.getClassId();
				}

			}
		} catch (SQLException e) {
			LOGGER.error("error getting classes", e);
		}

		themToMe.put("CLERICS", clericId);
		themToMe.put("DRUIDS", druidId);
		themToMe.put("MAGIC-USERS", muId);
		themToMe.put("ILLUSIONISTS", illId);

		themToMe.put("CLERIC SPELLS", clericId);
		themToMe.put("DRUID SPELLS", druidId);
		themToMe.put("MAGIC-USER SPELLS", muId);
		themToMe.put("ILLUSIONIST SPELLS", illId);

	}

//	private Map<String, String> colonMap(String[] strings) {
//		Map<String, String> map = new HashMap<>();
//		for (String s : strings) {
//			int c = s.indexOf(':');
//			if (c > 0) {
//				String key = s.substring(0, c);
//				String value = s.substring(c + 1);
//				map.put(key, value);
//			}
//		}
//
//		return map;
//	}

	private String t(String in) {
		String ret = XmlUtilities.trimNbsp(in);
		ret = ret.replace('\t', ' ');
		while (ret.indexOf("  ") >= 0) {
			ret = ret.replace("  ", " ");
		}
		return ret;
	}

	public ImportSpells(boolean clear) throws Exception {
		
		Map<String, String> spellIds = new HashMap<>();
		
		String theUrl = "http://pandaria.rpgworlds.info/cant/rules/adnd_spells.htm";
		URL url = new URL(theUrl);

		Map<String, Map<String,InternalSpell>> spells = new HashMap<>();

		ConnectionManager cm = new ConnectionManager();

		LOGGER.info("starting");
		try {

			// prepare our side of the data :)
			try (Connection conn = cm.getConnection();) {
				populateMapping(conn);
				LOGGER.info("populated maps");
				if (clear) {
					String deleteAll = "DELETE FROM Spell";
					runAction(conn, deleteAll);
				}
				conn.commit();
			} catch (Exception ex) {
				LOGGER.error("ERROR clearing Spells :( " + ex, ex);
			}

			int count = 0;
			URLConnection connection = url.openConnection();
			StringBuilder sb = new StringBuilder();
			try (BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));) {
				String line = bufferedReader.readLine();
				while (line != null) {
					count++;
					sb.append(line);
					line = bufferedReader.readLine();
					if (count % 100 == 0) {
						LOGGER.info("read row " + count);
					}
				}
			}
			LOGGER.info("Now read data.");
			//
			try {
				Document doc = XmlUtilities.cleanHtml(sb.toString());
				if (doc == null) {
					throw new NullPointerException("doc is null!");
				}
				Element docElement = doc.getDocumentElement();
				if (docElement == null) {
					throw new NullPointerException("doc element is null!");
				}
				// now to parse it
				Element body = XmlUtilities.getElement(docElement, "body");// should only be one body node!
				// get all "anchor" tags. These are where the magic lies
				List<Node> anchors = XmlUtilities.getElementsByTagName(body, "a");
				// String className = "";

				// SPELL TABLES
				// // <CLASS>
				// // // number, 1st level, 2nd level etc
				// // <CLASS>
				// // // number, 1st level, 2nd level etc
				// // <CLASS>
				// // // number, 1st level, 2nd level etc
				// // etc
				// SPELL EXPLANATIONS
				// <CLASS> SPELLS
				// note regarding
				// BLAH LEVEL SPELLS
				// <SPELL> <ETC>
				// <ETC>
				// <SPELL> <ETC>
				// <ETC>
				String currentCharacterClass=null;
				for (Node anchor : anchors) {

					// first node may have a CHARACTER CLASS name in.
					String name = XmlUtilities.getAttributeValue(anchor, "name");
					if (name != null && !t(name).isEmpty()) {
						LOGGER.info("anchor name:" + name);
						String nameHRef = "#" + name;
						if(t(name).endsWith("First Level Spells:")) {
							//LOGGER.info("#### oooh! {}",name);
							// need to find new character class id
							// need to look at the table before the table before this node...
							//
							Node table1 = XmlUtilities.getNodeBefore((Element)anchor,"table");
							if(table1!=null)  {
								String tt = XmlUtilities.getText(table1);
								String sss=null;
								Node strong0=XmlUtilities.getElement((Element)table1,"strong");
								if(strong0!=null) {
									String text0 = XmlUtilities.getText(strong0);
									 sss=themToMe.get(text0);
									if(sss!=null) {
										currentCharacterClass=sss;
										//LOGGER.info("####. Current character class {}",sss);
									}
								}
								if(sss==null){
								
								Node table0 = XmlUtilities.getNodeBefore((Element)table1,"table");
								if(table0!=null) {
									String ttt = XmlUtilities.getText(table0);
									Node strong=XmlUtilities.getElement((Element)table0,"strong");
									String text = XmlUtilities.getText(strong);
									sss=themToMe.get(text);
									if(sss!=null) {
										currentCharacterClass=sss;
										//LOGGER.info("#### Current character class {}",sss);
									}
									
								}
								}
							}
							
						} else if (spells.containsKey(nameHRef)) {// name = t(name); //now trim stuffs
							LOGGER.info("Spell group for found name: {}, looking for class: {} ",nameHRef,currentCharacterClass);
							InternalSpell is = spells.get(nameHRef).get(currentCharacterClass);
							{
								if (is != null) {
									LOGGER.info("Found correct spell {} {}",is.getName(),is.getClassId());
									Spell s = is.getSpell();

									//
									// back up and get the table we're in
									//

									Node table = XmlUtilities.findParent(anchor, "table");
									if (table != null) {
										List<List<Node>> spellTable = XmlUtilities.getTableData((Element) table);
										{
											String type = "";
											int rowNum = 0;
											if (rowNum <= spellTable.size()) {
												// first row
												List<Node> row = spellTable.get(rowNum++);
												Node data = row.get(0);
												type = parseType(t(XmlUtilities.getText(data)));
												LOGGER.info("Type:" + type);

												// first row
												// <td width="100%"><font size="2"><b><a name="Command">Command</a></b>
												// (Enchantment/Charm)</font></td>
												// after the "anchor" is a text node holding the spell type
												//
											}
											// second row
											if (rowNum <= spellTable.size()) {
												List<Node> row = spellTable.get(rowNum++);
												Node data = row.get(0); 
												Map<String, String> map = parseProperties(name, data);
												String range = map.get(RANGE);
												String level = map.get(LEVEL);
												String components = map.get(COMPONENTS);
												String castingTime = map.get(CASTING_TIME);
												String duration = map.get(DURATION);
												String save = map.get(SAVE);
												String area = map.get(AREA);
												setComponents(s, components);
 
												if (!(("" + s.getLevel()).equals(t(level)))) {
													LOGGER.info("Dodgy level on spell " + s + " >> " + level);
												}
												s.setSpellType(type);
												s.setRange(range);
												s.setCastingTime(castingTime);
												s.setDuration(duration);
												s.setSavingThrow(save);
												s.setAreaOfEffect(area);

												// Something:something&nbsp;
											}
											// third row
											if (rowNum <= spellTable.size()) {
												List<Node> row = spellTable.get(rowNum++);
												Node data = row.get(0);
												String desc = XmlUtilities.getText(data);
												String mc = "";
												if (desc.startsWith(ED)) {
													desc = desc.substring(ED.length()+1);
												}
												s.setMaterialComponents(mc);
												s.setDescription(desc);
											}

										}
									}
								} else {
									LOGGER.info("Couldn't find spell for {}, {}",currentCharacterClass,nameHRef);
								}
							}
						}  

					}
					String classId = themToMe.get(name);
					if (classId != null && !t(classId).isEmpty()) {
						// its a character class name
						// className = name;
						//LOGGER.info("####### CURRENT CHARACTER CLASS :{} id: {}", name,classId);
						currentCharacterClass=classId;
						// we need to find the "p" tag that this node is in
						Node p = XmlUtilities.findParent(anchor, "p");
						if (p != null) {
							Element pp = (Element) p;
							Node table = XmlUtilities.getNodeAfterwards(pp, "table");
							if (table == null) {
								throw new IllegalArgumentException("Table is null ");
							}
							List<List<Node>> classSpells = XmlUtilities.getTableData((Element) table);
							// the columns in the table are:
							// #, 1st level spells,2nd level spells....nth level spells.
							// THe first row has column headings
							// the second row is blanks
							// data starts on the third row
							
							// will re-use this a fair bit :)
							int[] spellLevelsByColumn = new int[9];// most can be 9
							
							int rowCount = 0;
							for (List<Node> row : classSpells) {
								String colZero = (XmlUtilities.getText(row.get(0)));
								String czt=t(colZero);
								if("Number".equalsIgnoreCase(czt)) {
									// sets spell level base
									for(int j=1,m=row.size();j<m;j++) {
										String colValue = t(XmlUtilities.getText(row.get(j)));
										if(colValue!=null&&!colValue.isEmpty()) {
											String c = colValue.substring(0,1);
											try {
												int level = Integer.parseInt(c);
												spellLevelsByColumn[j]=level;
											} catch (NumberFormatException nfe) {
												//meh
												LOGGER.info("couldn't parse "+czt);
											}
										}
									}
									continue;//nothing else on a number row.
								} else {
									// a number should be in the first table cell
									String numString = colZero;
									try {
										int spellNum = Integer.parseInt(numString);
									} catch (NumberFormatException nfe) {
										rowCount++;
										continue;// skip the line if not a valid number:)
									}
									for (int i = 1, n = row.size(); i < n; i++) {

										int level = spellLevelsByColumn[i];

										Node td = row.get(i);
										Element ahref = XmlUtilities.getElement(td, "a");
										if (ahref != null) {
											String href = XmlUtilities.getAttributeValue(ahref, "href");
											String spellName = t(XmlUtilities.getText(ahref));
											//LOGGER.info("Found spell class {} name {}",classId,spellName);
											InternalSpell is = new InternalSpell(level, classId, href, spellName);
											makeSpellId(spellIds, is);
											Map<String,InternalSpell> iss;
											if (spells.containsKey(href)) {
												iss = spells.get(href);
											} else {
												iss = new HashMap<>();
												spells.put(href, iss);
											}
											LOGGER.info("Storing spell class:{} name:{}",is.getClassId(),is.getName());
											iss.put(is.getClassId(),is);
											
										}
									}

								}

								rowCount++;
							}

						}

					}

				}
				LOGGER.info("");
				LOGGER.info("");
				LOGGER.info("Finally");
				LOGGER.info("");
				LOGGER.info("");
				 

				LOGGER.info("");
				LOGGER.info("");
				LOGGER.info("LOADED");
				LOGGER.info("");
				LOGGER.info("");
			} catch (Exception ex) {
				LOGGER.error("error", ex);
			}

			LOGGER.info("");
			LOGGER.info("");
			LOGGER.info("WRITING TO DATABASE");
			LOGGER.info("");
			LOGGER.info("");

			boolean error = false;
			List<Throwable> errors = new ArrayList<>();
			List<Spell> errorSpells = new ArrayList<>();

			try (Connection con = cm.getConnection();) {
				con.setAutoCommit(false);
				SpellDAO sdao = new SpellDAO();
				for (Map<String,InternalSpell> iss : spells.values()) {
					for (InternalSpell is : iss.values()) {
						Spell ns = is.getSpell();
						Spell sx = null;
						try {
							LOGGER.info("spell class {} name {}",ns.getClass(),ns.getName());
							sx = null;
							try {
								sx = sdao.get(con, ns.getSpellId());
							} catch (Exception ex) {
								LOGGER.info("Spell not in database:" + ns.getSpellId() + " " + ex);
							}
							if (sx == null) {
								LOGGER.info("New spell:" + ns);
								sdao.insert(con, ns);
							} else {
								LOGGER.info("Update spell from :" + sx);
								LOGGER.info("Update spell   to :" + ns);
								sdao.update(con, ns);
							}
						} catch (SQLException ex) {
							error = true;
							errors.add(ex);
							errorSpells.add(ns);
						}
					}
				}
				if (error) {
					LOGGER.error("ERRORS! " + errors.size());
					for (int i = 0, n = errors.size(); i < n; i++) {
						Throwable t = errors.get(i);
						Spell spell = errorSpells.get(i);
						LOGGER.error("Error writing spell " + spell, t);
					}
					 LOGGER.error("End of ERRORS! "+errors.size());
					 con.rollback();
				} else {
					 LOGGER.info("No errors. Committing");

				}
				con.commit();
			}

			LOGGER.info("");
			LOGGER.info("");
			LOGGER.info("COMPLETED");
			LOGGER.info("");
			LOGGER.info("");

		} finally {
			cm.shutdown();
		}

	}

	private void makeSpellId(Map<String, String> spellIds,  InternalSpell is) {
		{
			String sid = guessSpellId(is.getSpell());
			String sidP = sid;
			int sc = 0;
			while (spellIds.containsKey(sidP)) {
				sc++;
				sidP = sid + sc;
			}
			spellIds.put(sidP, sidP);
			is.getSpell().setSpellId(sidP);
			// LOGGER.info("Spell:" + is.getSpell());
		}
	}

	String LEVEL = "Level";
	String COMPONENTS = "Components";
	String RANGE = "Range";
	String CASTING_TIME = "Casting Time";
	String DURATION = "Duration";
	String SAVE = "Saving Throw";
	String AREA = "Area of Effect";
	String ED = "Explanation/Description";

	private Map<String, String> parseProperties(String name, Node data) {
		String props = XmlUtilities.getText(data);
		props = props.replaceAll("\\&quot\\;", "'");
		props = props.replaceAll("\\&nbsp\\;", " ");
		props = props.replaceAll("\\&amp\\;", " ");
		LOGGER.info("props:" + props);
		int p0 = props.indexOf(LEVEL);
		int p1 = p0 + LEVEL.length() + 1;

		if(p0==-1) {
			p1=-1;
		}
		int p2 = props.indexOf(COMPONENTS);
		int p3 = p2 + COMPONENTS.length() + 1;
		if(p2==-1) {
			p3=-1;
		}
		int p4 = props.indexOf(RANGE);
		int p5 = p4 + RANGE.length() + 1;
		if(p4==-1) {
			p5=-1;
		}
		int p6 = props.indexOf(CASTING_TIME);

		int p7 = p6 + CASTING_TIME.length() + 1;
		if(p6==-1) {
			p7=-1;
		}
		int p8 = props.indexOf(DURATION);
		int p9 = p8 + DURATION.length() + 1;
		if(p8==-1) {
			p9=-1;
		}
		int p10 = props.indexOf(SAVE);
		int p11 = p10 + SAVE.length() + 1;
		if(p10==-1) {
			p11=-1;
		}
		int p12 = props.indexOf(AREA);
		int p13 = p12 + AREA.length() + 1;
		if(p12==-1) {
			p13=-1;
		}

		LOGGER.info(name + " >> " + "p0:" + p0 + " " + "p1:" + p1 + " " + "p2:" + p2 + " " + "p3:" + p3 + " " + "p4:"
				+ p4 + " " + "p5:" + p5 + " " + "p6:" + p6 + " " + "p7:" + p7 + " " + "p8:" + p8 + " " + "p9:" + p9
				+ " " + "p1:" + p10 + " " + "p11:" + p11 + " " + "p12:" + p12 + " " + "p13:" + p13 + " ");
		String level;
		if (p1 >= 0 && p2 > 1) {
			level = t(props.substring(p1, p2));
		} else {
			level = "";
		}
		String components;
		if (p3 >= 0 && p4 >= p3) {
			components = t(props.substring(p3, p4));
		} else {
			components = "";
		}
		String range;
		if (p5 >= 0 && p6 >= p5) {
			range = t(props.substring(p5, p6));
		} else {
			range = "";
		}
		String cast;
		if (p7 >= 0 && p8 >= p7) {
			cast = t(props.substring(p7, p8));
		} else {
			cast = "";
		}
		String duration;
		if (p9 >= 0 && p10 >= p9) {
			duration = t(props.substring(p9, p10));
		} else {
			duration = "";
		}
		String save;
		if (p11 >= 0 && p12 >= p11) {
			save = t(props.substring(p11, p12));
		} else {
			save = "";
		}
		String area;
		if (p13 > 0) {
			area = t(props.substring(p13));
		} else {
			area = "";
		}

		Map<String, String> map = new HashMap<>();

		map.put(LEVEL, level);
		map.put(COMPONENTS, components);
		map.put(RANGE, range);
		map.put(CASTING_TIME, cast);
		map.put(DURATION, duration);
		map.put(SAVE, save);
		map.put(AREA, area);
		dumpMap(map);
		return map;
	}

	private String guessSpellId(Spell spell) {
		StringBuilder sb = new StringBuilder();
		sb.append(spell.getSpellClass().substring(0, 2));
		sb.append(spell.getLevel());
		StringTokenizer ts = new StringTokenizer(spell.getName().toUpperCase());
		while (ts.hasMoreTokens()) {
			sb.append(ts.nextToken().substring(0, 1));
		}
		return sb.toString();
	}

	private class InternalSpell {
		private int level;
		private String classId;
		private String anchorName;
		private String name;

		private Spell spell;

		public InternalSpell(int level, String classId, String anchorName, String name) {
			super();
			this.level = level;
			this.classId = classId;
			this.anchorName = anchorName;
			this.name = name;
			spell = new Spell();
			spell.setLevel(level);
			spell.setSpellClass(classId);
			spell.setName(name);
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public String getClassId() {
			return classId;
		}

		public void setClassId(String classId) {
			this.classId = classId;
		}

		public String getAnchorName() {
			return anchorName;
		}

		public void setAnchorName(String anchorName) {
			this.anchorName = anchorName;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Spell getSpell() {
			return spell;
		}

		public void setSpell(Spell spell) {
			this.spell = spell;
		}

		@Override
		public String toString() {
			return "InternalSpell [level=" + level + ", classId=" + classId + ", anchorName=" + anchorName + ", name="
					+ name + ", spell=" + spell + "]";
		}

	}

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public static void main(String[] args) throws Exception {

		CommandLineParser parser = new DefaultParser();
		Options options = MainUtils.baseOptions();
		{
			Option o = new Option("c", true, "Clear spells?");
			o.setLongOpt("clear");
			options.addOption(o);
		}
		CommandLine cmd = parser.parse(options, args);
		if (cmd.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(MethodHandles.lookup().lookupClass().getSimpleName(), options);
			System.exit(0);
		}

		String databaseName = System.getProperty(Constants.PARMNAME_ONEEDDDB, "");// "";//"C:\\Users\\nobby\\Documents\\nobbysoft\\derby\\PCDatabase";

		if (cmd.hasOption("d")) {
			databaseName = cmd.getOptionValue("d");
		}
		boolean clear = false;
		if (cmd.hasOption("c")) {
			String c = cmd.getOptionValue("c");
			if ("Y".equalsIgnoreCase(c)) {
				clear = true;
			} else if ("true".equalsIgnoreCase(c)) {
				clear = true;
			}
		}
		System.setProperty(Constants.PARMNAME_ONEEDDDB, databaseName);

		new StartNetworkServer();
		new ImportSpells(clear);
	}

	public String driver = "org.apache.derby.jdbc.EmbeddedDriver";

	private void runAction(Connection con, String sql) throws SQLException {

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.execute();
		}

	}

	private void setComponents(Spell s, String components) {
		if (components == null) {
			return;
		}
		String[] cs = components.split("\\,");
		for (String c : cs) {
			c = t(c);
			if (c.equalsIgnoreCase("V")) {
				s.setVerbal(true);
			}
			if (c.equalsIgnoreCase("S")) {
				s.setSomatic(true);
			}
			if (c.equalsIgnoreCase("M")) {
				s.setMaterial(true);
			}
		}
	}

	private void dumpMap(Map<?, ?> map) {
		StringBuilder sb =new StringBuilder("["); 
		for (Object key : map.keySet()) {
			Object value = map.get(key);
			sb.append(key).append("=").append(value).append(",");
		}
		sb.append("]");
		LOGGER.info(sb.toString());
	}

	private String parseType(String s) {
		int ob = s.lastIndexOf('(');
		if (ob < 0) {
			return "";
		}
		int cb = s.indexOf(' ', ob);
		if (cb > ob) {
			return s.substring(ob, cb);
		} else {
			return s.substring(ob);
		}
	}
}
