package com.nobbysoft.first.common.entities.pc;

public class PlayerCharacterSpellKey implements Comparable<PlayerCharacterSpellKey> {


	
	public PlayerCharacterSpellKey(int pcId,String spellId) { 
		this.pcId=pcId;
		this.spellId=spellId;
	}
	
	public PlayerCharacterSpellKey() { 
	}

	@Override
	public int compareTo(PlayerCharacterSpellKey o) {
		int ret=0;
		if(pcId<o.getPcId()) {
			ret = 1;
		} else if (pcId>o.getPcId()) {
			ret = -1;
		} else {
			ret = spellId.compareTo(o.getSpellId());
		}
		return ret;
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

	private int pcId;
	private String spellId;
}
