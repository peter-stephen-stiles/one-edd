package com.nobbysoft.first.client.data.panels.sql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.nobbysoft.first.client.utils.XmlUtilities;

public class SqlHistory {
	

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private  SqlHistory() {

	}
	
	public static final void initialise() {
		
	}
	
	private static final String SQL = "sql";
	private static final int MAX_HISTORY = 100;
	
	public static final void saveHistory() {
		LOGGER.info("Saving history");
		try {
		
		File f = new File("history.xml");
		Document doc = XmlUtilities.convertStringToXML("<doc></doc>");
		Node n2 = doc.getDocumentElement();
		for(String hist:history) {
			XmlUtilities.addCDataElement(n2, SQL, hist);
		}
		try(FileWriter fw = new FileWriter(f)){
			fw.write(XmlUtilities.xmlToHtmlString(doc));
		}
		
		LOGGER.info("history file "+f.getAbsolutePath());
		
		} catch (Exception ex) {
			LOGGER.error("Error writing history XML",ex);
		}
		
		
	}
	
	private static final List<String> history = new ArrayList<>();
	static {
		
		try {
		
		/// read "history.xml" file and load into "history" arraylist
		File f = new File("history.xml");

		LOGGER.info("history file "+f.getAbsolutePath());
		
		if(f.exists()) {
			
			try(FileReader fr = new FileReader(f)){
			try(BufferedReader br = new BufferedReader(fr)){				
				Document doc = XmlUtilities.convertReaderToXML(br);
				NodeList sql = doc.getElementsByTagName(SQL);
				for(int i=0,n=sql.getLength();i<n;i++) {
					Node nnn = sql.item(i);					
					history.add(XmlUtilities.getText(nnn));
				}
				
			}
			}
		} else {
			LOGGER.info("No history file");
		}
			
		} catch (Exception ex) {
			LOGGER.error("Couldn't load history :(",ex);
		}
		 
		Runtime r=Runtime.getRuntime();
		r.addShutdownHook(new Thread(new Runnable(){
			@Override
			public void run() {
				LOGGER.info("Save history");
				saveHistory();
			}			
		}));
		
	}
	
	public static final List<String> getHistory(){
		return history;
	}
	public static final void addHistory(String sql) {
		history.remove(sql);// in case its there already
		history.add(sql);
		while(history.size()>MAX_HISTORY) {
			history.remove(0);//
		}
	}
	

}
