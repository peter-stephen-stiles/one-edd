package com.nobbysoft.com.nobbysoft.first.client.components;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.invoke.MethodHandles;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.text.JTextComponent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.com.nobbysoft.first.utils.DataMapper;

public class PComboIntRenderer  extends PListCellRenderer   {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass()); 

	

	private boolean showPlus=false; 
	private boolean showZero = true;
	 

	public PComboIntRenderer(boolean showPlus) {
		this.showPlus=showPlus;
	}
	public PComboIntRenderer(boolean showPlus,boolean showZero) {
		this.showPlus=showPlus;
		this.showZero=showZero;
	}
 
	private String getString(Object value) {
		String tv="";
		if(value!=null) {
			if(value instanceof Number) {
				Number n =(Number)value;
				if(showPlus) {					
					if(n.doubleValue()>0) {
						tv= ("+"+value.toString());
					} else {
						tv=(value.toString());						
					}
				} else {
					tv=(value.toString());
				}
				if(!showZero) {
					if( n.intValue()==0) {
						// nearly zero, 						
						double d=Math.abs(n.doubleValue());
						if(d < 0.00001) {
							tv="  ";// near enough for me!
						}
					}
				}
			} else {
				tv=(value.toString());				
			}
		} 
		return tv;
	}
	

	public boolean isShowPlus() {
		return showPlus;
	}
 

	public void setShowPlus(boolean showPlus) {
		this.showPlus = showPlus;
	}
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) { 
		
		Component c= super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		
		if(c instanceof JTextComponent) {
			((JTextComponent)c).setText(getString(value));
		} else if(c instanceof JLabel) {
			((JLabel)c).setText(getString(value));
		} else {
			LOGGER.info("component is a naughty component !"+c.getClass().getSimpleName());
		}
		
		return c;
	}

}
 
