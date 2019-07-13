package com.nobbysoft.com.nobbysoft.first.client.utils;

import java.awt.Component;
import java.awt.Window;
import java.lang.invoke.MethodHandles;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
 

public class Popper {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	private Popper() { 
	}

	public static final void popError(Component component,String title,String error){
		JOptionPane.showMessageDialog(GuiUtils.getParent(component),
			    error,
			    title,
			    JOptionPane.ERROR_MESSAGE);
	}
	
	
 
	
	public static final void popError(Component component,Throwable t){
		LOGGER.error("Error "+t,t);
		JOptionPane.showMessageDialog(GuiUtils.getParent(component),
			    t.toString(),
			    "Error " +t.getMessage(),
			    JOptionPane.ERROR_MESSAGE);
	}
	public static final void popInfo(Component component,String title,String error){
		JOptionPane.showMessageDialog(GuiUtils.getParent(component),
			    error,
			    title,
			    JOptionPane.PLAIN_MESSAGE);
	}
}
