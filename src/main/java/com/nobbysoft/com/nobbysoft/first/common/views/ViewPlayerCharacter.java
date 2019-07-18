package com.nobbysoft.com.nobbysoft.first.common.views;

import java.io.Serializable;

import com.nobbysoft.com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.com.nobbysoft.first.common.entities.pc.PlayerCharacter;

@SuppressWarnings("serial")
public class ViewPlayerCharacter implements Serializable,DataDTOInterface<Integer> {

	public ViewPlayerCharacter() {

	}
	
	private PlayerCharacter playerCharacter;
	private String classNames;
	
	public PlayerCharacter getPlayerCharacter() {
		return playerCharacter;
	}
	public void setPlayerCharacter(PlayerCharacter playerCharacter) {
		this.playerCharacter = playerCharacter;
	}
	public String getClassNames() {
		return classNames;
	}
	public void setClassNames(String classNames) {
		this.classNames = classNames;
	}
	@Override
	public Integer getKey() {
		// TODO Auto-generated method stub
		return playerCharacter.getKey();
	}
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return playerCharacter.getCharacterName();
	}

	@Override
	public Object[] getAsRow() { 
		return new Object[] {this,playerCharacter.getPcId(),
				playerCharacter.getCharacterName(), 
				playerCharacter.getRaceId(),
				classNames,
				playerCharacter.getGender().name(),
				playerCharacter.getAlignment().name()
				};
	}

	
	
	@Override
	public String[] getRowHeadings() { 
		return new String[] {"Id","Character Name","Race","Class","Gender","Alignment"
				
		};
	}

	@Override
	public Integer[] getColumnWidths() { 
		return new Integer[] {120,200,100,250,100,150};
	}

	@Override
	public String[] getColumnCodedListTypes() { 
		return new String[] {null,null,
				Constants.CLI_RACE,null,
				Constants.CLI_GENDER,
				Constants.CLI_ALIGNMENT};
	}
	
	

}
