package com.nobbysoft.first.client.data.panels.pc;

import java.awt.Window;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.data.panels.CharacterSheet;
import com.nobbysoft.first.client.data.panels.DataButtonsInterface;
import com.nobbysoft.first.client.data.panels.DatasButtonsInterface;
import com.nobbysoft.first.client.data.panels.SpellBook;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.servicei.PlayerCharacterService;
import com.nobbysoft.first.common.servicei.RaceService;
import com.nobbysoft.first.common.views.ViewPlayerCharacter;
import com.nobbysoft.first.utils.DataMapper;

public class PlayerCharacterButtons implements DatasButtonsInterface<ViewPlayerCharacter> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	private static final String EQUIPMENT = "Equipment";

	private static final String SPELLS = "Spells";

	private static final String ADD_XP = "Add XP";
	
	private static final String DUAL_CLASS = "Dual class";
	private static final String SHEET=	"Character sheet";
	private static final String SPELLBOOK=	"Spell book";

	private List<String> rowButtonNames = new ArrayList<>();

	private List<String> tableButtonNames = new ArrayList<>();
	
	public PlayerCharacterButtons() { 
		rowButtonNames.add(ADD_XP);
		rowButtonNames.add(SPELLS);
		rowButtonNames.add(EQUIPMENT);
		rowButtonNames.add(DUAL_CLASS);
		rowButtonNames.add(SHEET);
		rowButtonNames.add(SPELLBOOK);
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
	 
	public boolean doRowsButton(Window window, String name, List<ViewPlayerCharacter> objects) {
		
		
		ViewPlayerCharacter object=null;
		if(objects.size()>0) {
			object = objects.get(0);
		}
		
		LOGGER.info("Button "+name);
		if(ADD_XP.equals(name)) {	
			boolean anyOk=false;
			try {
				boolean again=true;
				
				while(again) {
					PlayerCharacterService ccs = (PlayerCharacterService) getDataService(PlayerCharacter.class);
					RaceService rs = (RaceService) getDataService(Race.class);
					PlayerCharacter pc = ccs.get(object.getPlayerCharacter().getPcId());
					Race race = rs.get(pc.getRaceId());
					PlayerCharacterAddXpDialog dialog = new PlayerCharacterAddXpDialog(window,again);
					dialog.setPlayerCharacter(pc, race);
					dialog.pack();
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
					if (!dialog.isCancelled()) {
						anyOk=true;
						again = dialog.getAgain();
						ccs.update(pc);
						pc = ccs.get(pc.getPcId());
						if(!again) {
							return true;
						}
					} else {
						// cancelled so not again
						again=false;
					}
				}
			} catch (Exception ex) {
				Popper.popError(window, ex);
				return false;
			} 
			return anyOk;
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
			//
		} else if(DUAL_CLASS.equals(name)) {
			try {
				PlayerCharacterService ccs = (PlayerCharacterService) getDataService(PlayerCharacter.class);			
				PlayerCharacter pc = ccs.get(object.getPlayerCharacter().getPcId());
				if(pc.getThirdClass()!=null) {
					Popper.popError(window, "Greedy bugger ", 
							"Your character has already dual classed. Twice. That should be enough for anyone. Sorry.");
					return false;
				}
				RaceService rs = (RaceService) getDataService(Race.class);
				Race race = rs.get(pc.getRaceId());
				if(race.isMultiClassable()) {
					Popper.popError(window, "Can't dual class a "+race.getName(), 
							"Your character is a "+race.getName()+" and they are not allowed to dual class. Sorry.");
					return false;
				}
				PlayerCharacterDualClassDialog dialog = new PlayerCharacterDualClassDialog(window);
				dialog.initialiseCharacter(pc);
				dialog.pack();
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
				if(dialog.isCancelled()){
					return false;
				}				
				return true;
			} catch (Exception ex) {
				Popper.popError(window, ex);
				return false;
			} 
			
		} else if(SHEET.equals(name)) {
			try {
				PlayerCharacterService ccs = (PlayerCharacterService) getDataService(PlayerCharacter.class);	
				
				
				for(ViewPlayerCharacter o2:objects) {
				
					PlayerCharacter dto = ccs.get(o2.getPlayerCharacter().getPcId());
				
					if (dto != null) {
						// now to make character sheet up
						CharacterSheet dialog = new CharacterSheet(window);
						boolean ok =dialog.setPlayerCharacter(dto);
						if(ok) {
							dialog.pack();
							dialog.setLocationRelativeTo(null);
							dialog.setVisible(true);
						}
						// no refresh just for character sheet!
						}			
				}
			} catch (Exception ex) {
				Popper.popError(window, ex);			
			}
			return false;
			
			//
		} else if(SPELLBOOK.equals(name)) {
			
			try {
				PlayerCharacterService ccs = (PlayerCharacterService) getDataService(PlayerCharacter.class);	
				
				
				for(ViewPlayerCharacter o2:objects) {
				
					PlayerCharacter dto = ccs.get(o2.getPlayerCharacter().getPcId());
				
					if (dto != null) {
						// now to make character sheet up
						SpellBook dialog = new SpellBook(window);
						boolean ok =dialog.setPlayerCharacter(dto);
						if(ok) {
							dialog.pack();
							dialog.setLocationRelativeTo(null);
							dialog.setVisible(true);
						}
						// no refresh just for character sheet!
						}			
				}
			} catch (Exception ex) {
				Popper.popError(window, ex);			
			}
			return false;


		} else {
			
		}
			 
		return false;
	}

	@Override
	public boolean doTableButton(Window window, String name) {
		return false;
		
	}



 
}
