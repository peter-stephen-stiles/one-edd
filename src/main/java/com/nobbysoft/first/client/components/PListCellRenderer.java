package com.nobbysoft.first.client.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.*;
import javax.swing.ListCellRenderer;

import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.utils.DataMapper;

public class PListCellRenderer<E> extends JLabel implements ListCellRenderer<E> {

	private PLabel labelT = new PLabel(); // template
	private Color dftBackground = labelT.getBackground();
	private Color focusBackground = dftBackground.darker();
	private Font dftFont = labelT.getFont();
	private Font boldFont = dftFont.deriveFont(Font.ITALIC);
	private boolean opaque = labelT.isOpaque();
	
	public PListCellRenderer() {
		 
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends E> list, E value, int index, boolean isSelected,
			boolean cellHasFocus) {


		if(isSelected ||cellHasFocus) {
			setOpaque(true);
			setBackground(focusBackground);
		} else {
			setOpaque(opaque);
			setBackground(dftBackground);
		}
		//setBackground(isSelected ||cellHasFocus? focusBackground : dftBackground);
		
		if(value!=null) {
			if(value instanceof CodedListItem) {
				setText(((CodedListItem)value).getDescription());
			} else if (value instanceof Class) {
				setText(DataMapper.INSTANCE.getName((Class)value));
			} else if(value instanceof String) {
				setText((String)value);
			} else {
				setText(value.toString());
			}
		} else {
			setText(" ");
		}
		
		return this;
	}

}
