package com.nobbysoft.first.client.components.special;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;

public class CharacterClassListCellRenderer implements ListCellRenderer<CharacterClass>{

	public CharacterClassListCellRenderer() {
		// TODO Auto-generated constructor stub
	}

	private PLabel lbl = new PLabel();

	@Override
	public Component getListCellRendererComponent(JList<? extends CharacterClass> list, CharacterClass value,
			int index, boolean isSelected, boolean cellHasFocus) {
		String s = "";
		if (value != null) {
			s = value.getName();
		}
		lbl.setText(s);

		if (isSelected || cellHasFocus) {
			lbl.setBackground(Color.GRAY);
			lbl.setOpaque(true);
		} else {
			lbl.setOpaque(false);
		}
		return lbl;
	}

	
}
