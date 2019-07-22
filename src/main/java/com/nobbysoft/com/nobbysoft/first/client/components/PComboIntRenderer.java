package com.nobbysoft.com.nobbysoft.first.client.components;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.invoke.MethodHandles;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.com.nobbysoft.first.utils.DataMapper;

public class PComboIntRenderer extends DefaultListCellRenderer  {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass()); 

	
	private PLabel labelT = new PLabel(); // template
	private Color dftBackground = labelT.getBackground();
	private Font dftFont = labelT.getFont();
	private Font boldFont = dftFont.deriveFont(Font.BOLD);
	private boolean opaque = labelT.isOpaque();
	private boolean showPlus=false; 
	 
	
	public PComboIntRenderer(boolean showPlus) {
		this.showPlus=showPlus;
	}
 
	
	@Override
	public Component getListCellRendererComponent(
	        JList<?> list,
	        Object value,
	        int index,
	        boolean isSelected,
	        boolean cellHasFocus) {
		JLabel label = (JLabel)super.getListCellRendererComponent( list,value, index,
	 isSelected,  cellHasFocus);
		
		String tv="";
		if(value!=null) {
			if(value instanceof Number) {
				if(showPlus) {
					Number n =(Number)value;
					if(n.doubleValue()>0) {
						tv= ("+"+value.toString());
					} else {
						tv=(value.toString());						
					}
				} else {
					tv=(value.toString());
				}
			} else {
				tv=(value.toString());				
			}
		} 
		label.setText(tv);
		if(isSelected ||cellHasFocus) {
			label.setOpaque(true);
		} else {
			label.setOpaque(opaque);
		}
		label.setBackground(isSelected ||cellHasFocus? Color.GRAY : dftBackground);
		label.setFont(cellHasFocus ? boldFont : dftFont);
		return label;
	}





	public boolean isShowPlus() {
		return showPlus;
	}





	public void setShowPlus(boolean showPlus) {
		this.showPlus = showPlus;
	}

}
 
