package com.nobbysoft.first.common.views;

import java.io.Serializable;

import com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterHp;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterHpKey;

@SuppressWarnings("serial")
public class ViewPlayerCharacterHp 
implements Serializable,DataDTOInterface<PlayerCharacterHpKey>{

	
	private PlayerCharacterHp playerCharacterHp; 
	private String className;

	public ViewPlayerCharacterHp(PlayerCharacterHp playerCharacterHp ,String className) {
		this.playerCharacterHp=playerCharacterHp; 
		this.className=className;
		
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
		return new Object[] {this, className,playerCharacterHp.getLevel(),playerCharacterHp.getHpIncrement()};
	}

	@Override
	public String[] getRowHeadings() {
		return new String[] {"","Class","Level","+hp"};
	}

	@Override
	public Integer[] getColumnWidths() {
		return new Integer[] {0,200,100,100};
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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
 

}
