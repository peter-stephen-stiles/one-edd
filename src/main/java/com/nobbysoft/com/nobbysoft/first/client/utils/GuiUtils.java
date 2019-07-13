package com.nobbysoft.com.nobbysoft.first.client.utils;

import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class GuiUtils {

	private GuiUtils() {
	}

	private static final Map<Fonty,Font> fonts= new HashMap<>();
	
	public static final Font getHeaderFont() {
		return getFont("Nauert Regular",24);//"Pointedly Mad",24);
	}
	
	public static final Font getNormalFont() {

		return getFont("UniversalisADFStd",12);
		
		
		//return getFont("TeXGyreAdventor-Regular",12);
	}

	public static final Font getBoldFont() {
		return getFont("UniversalisADFStd",12).deriveFont(Font.BOLD);
		//return getFont("TeXGyreAdventor-Regular",12).deriveFont(Font.BOLD);
	}
	
	
	
	
	private static final class Fonty implements Comparable<Fonty>{
		private String fontName;
		private int point;
		public Fonty(String font, int point){
			this.fontName=font;
			this.point=point;
		}
		public Fonty(Font font){
			this.fontName=font.getName();
			this.point=font.getSize();
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((fontName == null) ? 0 : fontName.hashCode());
			result = prime * result + point;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Fonty other = (Fonty) obj;
			if (fontName == null) {
				if (other.fontName != null)
					return false;
			} else if (!fontName.equals(other.fontName))
				return false;
			if (point != other.point)
				return false;
			return true;
		}
		@Override
		public int compareTo(Fonty o) {
			if(o==null) {
				return 1;
			}
			String of = o.fontName;
			int op = o.point;
			int ret = of.compareTo(fontName);
			if(ret==0) {
				if(op==point) {
					ret = 0;
				} else {
					if(op>point) {
						ret = -1;
					} else {
						ret = 1;
					}
				}
			}
			return ret;
		}
	}

	public static final Font getFont(String name, int pointSize) {
		
		Fonty fonty = new Fonty(name,pointSize);
		if(fonts.containsKey(fonty)) {
			return fonts.get(fonty);
		}
		
		GraphicsEnvironment ge =   GraphicsEnvironment.getLocalGraphicsEnvironment();
		for(Font f:ge.getAllFonts()) {
			if(f.getName().equalsIgnoreCase(name)) {
				Font t = f.deriveFont((float)pointSize);
				fonts.put(new Fonty(t),t);
				return t;				
			}
		}
		Font font=null;
		try (InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(name);) {			
			font = Font.createFont(Font.TRUETYPE_FONT, stream); 		     
		     ge.registerFont(font);		  
		     Font t = font.deriveFont((float)pointSize);
		     fonts.put(new Fonty(t),t);
		} catch (IOException|FontFormatException e) {
		     //Handle exception
			//"PointedlyMad.ttf"
		}
		
		return font;
	} 
	
	public static final Window getParent(Component c) {

		Component p = c;
		while (p != null && !(p instanceof Window)) {
			p = p.getParent();
		}
		if (p instanceof Window) {
			return (Window) p;
		} else {
			return null;
		}
	}
}
