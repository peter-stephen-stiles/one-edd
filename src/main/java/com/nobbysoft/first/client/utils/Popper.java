package com.nobbysoft.first.client.utils;

import java.awt.Component;
import java.awt.Window;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.common.utils.ReturnValue;
 

public class Popper {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	private Popper() { 
	}

	private static String asHtml(String s) {
		if(s==null) {
			return "";
		}
		s = s.trim();
		if(s.length()<50) {
			return s;
		}
		//
		String x="";
		int dotSpace = s.indexOf(". ",1);
		int space = s.indexOf(" ",1);
		int dot = s.indexOf(".",1);
		if(dotSpace>0) {
			x = "<p>"+s.replace(". ", "</p><p>")+"</p>";
		} else if(space>0) {
				x = "<p>"+s.replace(" ", "</p><p>")+"</p>";	
		} else if(dot>0) {
			x = "<p>"+s.replace(".", "</p><p>")+"</p>";
		} else {
			StringBuilder sb = new StringBuilder("<p>");
			x = s;
			while(x.length()>50) {
				sb.append(x.substring(0,50));
				sb.append("</p><p>");
				x = x.substring(50);
			}
			sb.append(x.substring(50));
			sb.append("</p>");
			
		}
		x = "<html>"+x+"</html>";
		
		return x;
	}
	
	
	public static final void popError(Component component,String title,ReturnValue error){
		JOptionPane.showMessageDialog(GuiUtils.getParent(component),
				asHtml(error.getErrorMessage()),
			    title,
			    JOptionPane.ERROR_MESSAGE);
	}
	
	public static final void popError(Component component,String title,String error){
		JOptionPane.showMessageDialog(GuiUtils.getParent(component),
				asHtml(error),
			    title,
			    JOptionPane.ERROR_MESSAGE);
	}
 
 
	
	public static final void popError(Component component,Throwable t){
		LOGGER.error("Error "+t,t);
		JOptionPane.showMessageDialog(GuiUtils.getParent(component),
			    asHtml(t.toString()),
			    "Error " +t.getMessage(),
			    JOptionPane.ERROR_MESSAGE);
	}
	public static final void popInfo(Component component,String title,String error){
		JOptionPane.showMessageDialog(GuiUtils.getParent(component),
			    asHtml(error),
			    title,
			    JOptionPane.PLAIN_MESSAGE);
	}
	
	
	public static final boolean popYesNoQuestion(Component component,String title,String question){
		boolean yes=false;
		int n = JOptionPane.showConfirmDialog(
				GuiUtils.getParent(component),			    
			    question,
			    title,
			    JOptionPane.YES_NO_OPTION);
		
		yes = (n==JOptionPane.YES_OPTION);
		return yes;
		
	}
	
	
	
	
	
}
