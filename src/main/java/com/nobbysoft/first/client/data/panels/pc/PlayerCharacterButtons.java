package com.nobbysoft.first.client.data.panels.pc;

import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import com.nobbysoft.first.client.data.panels.DataButtonsInterface;
import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.servicei.PlayerCharacterService;
import com.nobbysoft.first.common.servicei.RaceService;
import com.nobbysoft.first.common.views.ViewPlayerCharacter;
import com.nobbysoft.first.utils.DataMapper;

public class PlayerCharacterButtons implements DataButtonsInterface<ViewPlayerCharacter> {

	private static final String EQUIPMENT = "Equipment";

	private static final String SPELLS = "Spells";

	private static final String ADD_XP = "Add XP";

	private List<String> rowButtonNames = new ArrayList<>();

	private List<String> tableButtonNames = new ArrayList<>();
	
	public PlayerCharacterButtons() { 
		rowButtonNames.add(ADD_XP);
		rowButtonNames.add(SPELLS);
		rowButtonNames.add(EQUIPMENT);
	}


	
	@Override
	public List<String> getRowButtonNames() {
		return rowButtonNames;
	}

	
	@Override
	public List<String> getTableButtonNames() {
		return tableButtonNames;
	}

	DataServiceI getDataService(Class clazz) {
		DataServiceI dao = DataMapper.INSTANCE.getDataService(clazz);
		return dao;
	}
	
	@Override
	public boolean doRowButton(Window window, String name, ViewPlayerCharacter object) {
		if(ADD_XP.equals(name)) {	
			try {
				PlayerCharacterService ccs = (PlayerCharacterService) getDataService(PlayerCharacter.class);
				RaceService rs = (RaceService) getDataService(Race.class);
				PlayerCharacter pc = ccs.get(object.getPlayerCharacter().getPcId());
				Race race = rs.get(pc.getRaceId());
				PlayerCharacterAddXpDialog dialog = new PlayerCharacterAddXpDialog(window);
				dialog.setPlayerCharacter(pc, race);
				dialog.pack();
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
				if (!dialog.isCancelled()) {
					ccs.update(pc);
					pc = ccs.get(pc.getPcId());
					return true;
				}
			} catch (Exception ex) {
				Popper.popError(window, ex);
				return false;
			} 
			return false;
		} else if(SPELLS.equals(name)) {	
			try {
			PlayerCharacterService ccs = (PlayerCharacterService) getDataService(PlayerCharacter.class);
			RaceService rs = (RaceService) getDataService(Race.class);
			PlayerCharacter pc = ccs.get(object.getPlayerCharacter().getPcId());
			
			PlayerCharacterSpellDialog dialog = new PlayerCharacterSpellDialog(window,"Spells for "+object.getDescription());
			dialog.initialiseCharacter(pc);
			dialog.pack();
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
			
			return true;
			} catch (Exception ex) {
				Popper.popError(window, ex);
				return false;
			} 
			
		}else if(EQUIPMENT.equals(name)) {	
			try {
			PlayerCharacterService ccs = (PlayerCharacterService) getDataService(PlayerCharacter.class);
			RaceService rs = (RaceService) getDataService(Race.class);
			PlayerCharacter pc = ccs.get(object.getPlayerCharacter().getPcId());
			
			PlayerCharacterEquipmentDialog dialog = new PlayerCharacterEquipmentDialog(window,"Equipment for "+object.getDescription());
			dialog.initialiseCharacter(pc);
			dialog.pack();
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
			
			return true;
			} catch (Exception ex) {
				Popper.popError(window, ex);
				return false;
			} 
			
		} else {
			
		}
			 
		return false;
	}

	@Override
	public boolean doTableButton(Window window, String name) {
		return false;
		
	}

}
