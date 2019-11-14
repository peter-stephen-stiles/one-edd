package com.nobbysoft.first.client.utils;

import java.awt.Component;
import java.awt.Window;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

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
		String[] s1=s.split("\\R");
		Document doc = XmlUtilities.convertStringToXML("<html></html>");
		Node docEle = doc.getDocumentElement();
		Node table=XmlUtilities.addElement(docEle, "table");
		XmlUtilities.addAttribute((Element)table, "width", "500");
		Node row= XmlUtilities.addElement(table, "tr");
		Node td= XmlUtilities.addElement(row, "td");
		for(String s2:s1) {
			XmlUtilities.addElement(td, "p", s2);
		}
		String x;
		try {
			x = XmlUtilities.xmlToHtmlString(doc);
			LOGGER.info("xml:\n"+x);
		} catch (Exception e) {
			LOGGER.error("Error splitting and formatting:\n"+s,e);
			return s;
		}


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
