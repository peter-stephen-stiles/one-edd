package com.nobbysoft.com.nobbysoft.first;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;

import javax.swing.UIManager;

import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainUtils {
	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private MainUtils() { 

	}

	public static final void loadFonts() {
		MainUtils.setUpFont("fonts/PointedlyMad.ttf") ;
		MainUtils.setUpFont("fonts/texgyreadventor-bold.otf");
		MainUtils.setUpFont("fonts/texgyreadventor-bolditalic.otf");
		MainUtils.setUpFont("fonts/texgyreadventor-italic.otf");
		MainUtils.setUpFont("fonts/texgyreadventor-regular.otf");
		MainUtils.setUpFont("fonts/Nauert-Italic.ttf");
		MainUtils.setUpFont("fonts/NauertRegular.ttf");
		MainUtils.setUpFont("fonts/NauertWd-Plain.ttf");
		
		MainUtils.setUpFont("fonts/UniversalisADFStd-Bold.otf");	
		MainUtils.setUpFont("fonts/UniversalisADFStd-BoldCond.otf");	
		MainUtils.setUpFont("fonts/UniversalisADFStd-BoldCondIt.otf");	
		MainUtils.setUpFont("fonts/UniversalisADFStd-BoldItalic.otf");	
		MainUtils.setUpFont("fonts/UniversalisADFStd-Cond.otf");	
		MainUtils.setUpFont("fonts/UniversalisADFStd-CondItalic.otf");	
		MainUtils.setUpFont("fonts/UniversalisADFStd-Italic.otf");	
		MainUtils.setUpFont("fonts/UniversalisADFStd-Regular.otf");				
						
	}
		public static final Font setUpFont(String name) {
		Font font=null;
		try (InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(name);) {
			if(name.endsWith(".ttf")) {
				font = Font.createFont(Font.TRUETYPE_FONT, stream);
			} else if (name.endsWith(".otf")) {
				font = Font.createFont(Font.TRUETYPE_FONT, stream);
			} else {
				font = Font.createFont(Font.TYPE1_FONT, stream);
			}
			LOGGER.info("Loaded font "+font.getFamily() + " "+font.getName());
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(font);
		} catch (IOException|FontFormatException e) {
			LOGGER.error("Error fonting for font file "+name,e);
		}
		
		return font;
	}
	
  
	public static final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	 
	public static final Options baseOptions(){
		// create Options object
		Options options = new Options();

		addOption(options, "d", true, "database path");
		addOption(options, "h", false, "help");
		
		return options;
	}
	
	public static final void addOption(Options options, String abbr,boolean hasArgument, String name){
		if(options.hasOption(abbr)){
			throw new IllegalArgumentException("Already got an "+abbr);
		}

		options.addOption(abbr, hasArgument, name);
	}
 

	public static final void laf(){
	
    try {
    	
     	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	
//        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//            if ("Nimbus".equals(info.getName())) {
//                UIManager.setLookAndFeel(info.getClassName());
//                break;
//            }
//        }
	} catch (Exception e) {
		LOGGER.error("Can't set LAF",e);
	}
	
}
	
}
