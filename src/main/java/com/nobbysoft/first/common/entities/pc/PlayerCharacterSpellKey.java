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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + pcId;
		result = prime * result + ((spellId == null) ? 0 : spellId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerCharacterSpellKey other = (PlayerCharacterSpellKey) obj;
		if (pcId != other.pcId)
			return false;
		if (spellId == null) {
			if (other.spellId != null)
				return false;
		} else if (!spellId.equals(other.spellId))
			return false;
		return true;
	}
}
