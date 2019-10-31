package com.nobbysoft.first.common.views;

import java.io.Serializable;

import com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterHp;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterHpKey;

@SuppressWarnings("serial")
public class ViewPlayerCharacterHp 
implements Serializable,DataDTOInterface<PlayerCharacterHpKey>{

	
	private PlayerCharacterHp playerCharacterHp; 

	public ViewPlayerCharacterHp(PlayerCharacterHp playerCharacterHp ) {
		this.playerCharacterHp=playerCharacterHp; 
		
	}
	
	public ViewPlayerCharacterHp() {
		
	}

	@Override
	public PlayerCharacterHpKey getKey() {
		return playerCharacterHp.getKey();
	}

	@Override
	public String getDescription() {
		return playerCharacterHp.getDescription();
	}

	@Override
	public Object[] getAsRow() {
		return playerCharacterHp.getAsRow();
	}

	@Override
	public String[] getRowHeadings() {
		return playerCharacterHp.getRowHeadings();
	}

	@Override
	public Integer[] getColumnWidths() {
		return playerCharacterHp.getColumnWidths();
	}

	@Override
	public String[] getColumnCodedListTypes() {
		return playerCharacterHp.getColumnCodedListTypes();
	}

	public PlayerCharacterHp getPlayerCharacterHp() {
		return playerCharacterHp;
	}

	public void setPlayerCharacterHp(PlayerCharacterHp playerCharacterHp) {
		this.playerCharacterHp = playerCharacterHp;
	}
 

}
