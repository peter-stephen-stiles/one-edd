package com.nobbysoft.com.nobbysoft.first;

import java.awt.Font;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.com.nobbysoft.first.client.data.DataFrame;
import com.nobbysoft.com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.com.nobbysoft.first.server.db.StartNetworkServer;
import com.nobbysoft.com.nobbysoft.first.server.utils.ConnectionManager;

/**
 * Hello world!
 *
 */
public class DataMain {
	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    public static void main( String[] args )   {
    	Properties prop = System.getProperties();
    	for(Object key :prop.keySet()){
        	LOGGER.info(key+":"+prop.getProperty((String)key));	
    	}
		CommandLineParser parser=null;
		Options options=null;
		CommandLine cmd=null;
		try{
		parser= new DefaultParser();
		options= MainUtils.baseOptions();
		cmd= parser.parse(options, args);
		if(cmd.hasOption("h")){
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp( MethodHandles.lookup().lookupClass().getSimpleName(), options );
			System.exit(0);
		}
		} catch (Exception ex){
			LOGGER.error("Error initialising program",ex);
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp( MethodHandles.lookup().lookupClass().getSimpleName(), options );
			System.exit(1);
		}
		MainUtils.loadFonts();

		MainUtils.laf();
		setUIFont();
		try{
			
			String databaseName = System.getProperty(Constants.PARMNAME_ONEEDDDB,"");//"";//"C:\\Users\\nobby\\Documents\\nobbysoft\\derby\\PCDatabase";
			
			if(cmd.hasOption("d")){
				databaseName = cmd.getOptionValue("d");
			}

			System.setProperty(Constants.PARMNAME_ONEEDDDB, databaseName);
			
			new StartNetworkServer();
		new DataMain( );
		} catch (Throwable t){
			LOGGER.error("Error running program",t);
		}
    }
    
    public static void setUIFont() {
    	FontUIResource f = new FontUIResource("TeX Gyre Adventor",Font.PLAIN,12);
    	setUIFont(f);    	
    }
    
    public static void setUIFont(FontUIResource f) {
    	Enumeration  keys = UIManager.getLookAndFeelDefaults().keys();
    	while(keys.hasMoreElements()) {
    		Object key = keys.nextElement();
    		Object value = UIManager.get(key);
    				if(value instanceof FontUIResource) {
    					UIManager.put(key, f);
    				}
    	}
    }
    
    private DataMain() throws SQLException{
    	DataFrame df = new DataFrame();
    	SwingUtilities.invokeLater(()->{
    		df.pack();
    		df.setVisible(true);
    		df.setLocationRelativeTo(null);
    	});
    }
    
}
