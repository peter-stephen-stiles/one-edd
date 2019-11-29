package com.nobbysoft.first.common.entities.staticdto;

import java.io.Serializable;

import com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.first.common.utils.SU;

@SuppressWarnings("serial")
public class RaceThiefAbilityBonus  implements Serializable, DataDTOInterface<RaceThiefAbilityBonusKey> {

	
	public RaceThiefAbilityBonus(  ) {
		super(); 
	}
	
	public RaceThiefAbilityBonus(String raceId, String thiefAbilityType ) {
		super();
		this.raceId = raceId;
		this.thiefAbilityType = thiefAbilityType;
	}
	
	
	private String raceId;
	private String thiefAbilityType;
	private int percentageBonus;



	public int getPercentageBonus() {
		return percentageBonus;
	}
	public void setPercentageBonus(int percentageBonus) {
		this.percentageBonus = percentageBonus;
	}


	public String getRaceId() {
		return raceId;
	}
	public void setRaceId(String raceId) {
		this.raceId = raceId;
	}
	public String getThiefAbilityType() {
		return thiefAbilityType;
	}
	public void setThiefAbilityType(String thiefAbilityType) {
		this.thiefAbilityType = thiefAbilityType;
	}
	@Override
	public RaceThiefAbilityBonusKey getKey() { 
		return new RaceThiefAbilityBonusKey(raceId,thiefAbilityType);
	}
	@Override
	public String getDescription() { 
		return raceId+","+thiefAbilityType;
	}
	@Override
	public Object[] getAsRow() { 
		return new Object[] {this,raceId,thiefAbilityType,
			SU.ap(percentageBonus,"--")
					};
	}
	@Override
	public String[] getRowHeadings() { 
		return new String[] {"Race","Ability","%ag Bonus"};
	}
	@Override
	public Integer[] getColumnWidths() { 
		return new Integer[] {150,150,200};
	}
	@Override
	public String[] getColumnCodedListTypes() {
		return new String[] {Constants.CLI_RACE,Constants.CLI_THIEF_ABILITY,null};
	}

 
	
	
}
