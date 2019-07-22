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

public class PComboRenderer<E> extends DefaultListCellRenderer  {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass()); 

	
	private PLabel labelT = new PLabel(); // template
	private Color dftBackground = labelT.getBackground();
	private Font dftFont = labelT.getFont();
	private Font boldFont = dftFont.deriveFont(Font.BOLD);
	private boolean opaque = labelT.isOpaque();
	
	 
	
	public PComboRenderer() {
		addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				LOGGER.info("mouse clicked");
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				LOGGER.info("mouse pressed");
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				LOGGER.info("mouse released");
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				LOGGER.info("mouse entered");
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				LOGGER.info("mouse exited");
				
			}
			
		});
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
		
		if(value!=null) {
			if(value instanceof Class) {
				label.setText(DataMapper.INSTANCE.getName((Class)value));
			} else {
				label.setText(value.toString());
			}
		}
		LOGGER.info("selected "+isSelected+" focus "+cellHasFocus+" value"+value);
		 
		if(isSelected ||cellHasFocus) {
			label.setOpaque(true);
		} else {
			label.setOpaque(opaque);
		}
		label.setBackground(isSelected ||cellHasFocus? Color.GRAY : dftBackground);
		label.setFont(cellHasFocus ? boldFont : dftFont);
		return label;
	}

}
 
