package com.nobbysoft.first.client.data.panels;

import java.awt.Window;

import com.nobbysoft.first.client.utils.MakeHTML.TYPE;

@SuppressWarnings("serial")
public class CharacterSheet extends CharacterOutputter {
	
	public CharacterSheet(Window owner) {
		super(owner);
	}

	@Override
	public TYPE getHtmlType() {
		return TYPE.CHARACTER_SHEET;
	}

	
}
