package com.nobbysoft.first.client.data.panels;

import java.awt.Window;

import com.nobbysoft.first.client.utils.MakeHTML.TYPE;

@SuppressWarnings("serial")
public class SpellBook extends CharacterOutputter {
	
	public SpellBook(Window owner) {
		super(owner);
	}

	@Override
	public TYPE getHtmlType() {
		return TYPE.SPELL_BOOK;
	}

	
}
