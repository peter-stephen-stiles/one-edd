package com.nobbysoft.first.common.views;

import java.io.Serializable;

import com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterSpell;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterSpellKey;
import com.nobbysoft.first.common.entities.staticdto.Spell;

public class ViewPlayerCharacterSpell 
implements Serializable,DataDTOInterface<PlayerCharacterSpellKey>{

	
	private PlayerCharacterSpell playerCharacterSpell;
	private Spell spell;

	public ViewPlayerCharacterSpell(PlayerCharacterSpell playerCharacterSpell, Spell spell) {
		this.playerCharacterSpell=playerCharacterSpell;
		this.spell=spell;
		
	}
	
	public ViewPlayerCharacterSpell() {
		
	}

	@Override
	public PlayerCharacterSpellKey getKey() {
		return playerCharacterSpell.getKey();
	}

	@Override
	public String getDescription() {
		return spell.getDescription();
	}

	@Override
	public Object[] getAsRow() {
		return new Object[] {
				this,
				spell.getSpellClass(),
				spell.getLevel(), 
				spell.getName(),
				playerCharacterSpell.getInMemory()
				};
	}

	@Override
	public String[] getRowHeadings() {
		return new String[] {"","Spell Class","Level","Name","#Mem"};
	}

	@Override
	public Integer[] getColumnWidths() {
		return new Integer[] {0,100,50,250,50};
	}

	@Override
	public String[] getColumnCodedListTypes() {
		return new String[] {
				null,
				Constants.CLI_CLASS,
				null,
				null,
				null,};
	}

	public PlayerCharacterSpell getPlayerCharacterSpell() {
		return playerCharacterSpell;
	}

	public void setPlayerCharacterSpell(PlayerCharacterSpell playerCharacterSpell) {
		this.playerCharacterSpell = playerCharacterSpell;
	}

	public Spell getSpell() {
		return spell;
	}

	public void setSpell(Spell spell) {
		this.spell = spell;
	}

}
