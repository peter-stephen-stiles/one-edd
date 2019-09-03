package com.nobbysoft.first.common.entities.pc;

import java.io.Serializable;

import com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.first.common.entities.DataDTOInterface;

@SuppressWarnings("serial")
public class PlayerCharacterSpell implements Serializable,DataDTOInterface<PlayerCharacterSpellKey> {

	private int pcId;
	private String spellId;
	private int inMemory=0;
	
	public PlayerCharacterSpell() {

	}

	@Override
	public PlayerCharacterSpellKey getKey() { 
		return new PlayerCharacterSpellKey(pcId,spellId);
	}

	@Override
	public String getDescription() { 
		return pcId+":"+spellId;
	}

	@Override
	public Object[] getAsRow() {
		// TODO Auto-generated method stub
		return new Object[] {this,pcId,spellId,inMemory};
	}

	@Override
	public String[] getRowHeadings() {
		// TODO Auto-generated method stub
		return new String[] {"Player","Spell","# in memory"};
	}

	@Override
	public Integer[] getColumnWidths() {
		// TODO Auto-generated method stub
		return new Integer[] {200,200,100};
	}

	@Override
	public String[] getColumnCodedListTypes() {
		// TODO Auto-generated method stub
		return new String[] {Constants.CLI_PLAYER_CHARACTER,Constants.CLI_SPELL,null};
	}

	public int getInMemory() {
		return inMemory;
	}

	public void setInMemory(int inMemory) {
		this.inMemory = inMemory;
	}

	public int getPcId() {
		return pcId;
	}

	public void setPcId(int pcId) {
		this.pcId = pcId;
	}

	public String getSpellId() {
		return spellId;
	}

	public void setSpellId(String spellId) {
		this.spellId = spellId;
	}

}
