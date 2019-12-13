package com.nobbysoft.first.client.data.panels.staticdata;

import java.awt.Window;
import java.util.*;

import com.nobbysoft.first.client.components.PButton;
import com.nobbysoft.first.client.data.panels.DataButtonsInterface;
import com.nobbysoft.first.client.data.panels.RollHPDialog;
import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;

public class CharacterClassButtons implements DataButtonsInterface<CharacterClass> {

	private static final String ROLL_HP = "Roll HP";
	private static final String LEVELS = "Levels";
	private static final String SPELLS = "Spells By Level";
	private static final String TO_HIT = "To-Hit Values";
	private static final String SAVES = "Saving Throws";
	private static final String EQUIPMENT = "Equipment";

	public CharacterClassButtons() {

		rowButtons.add(ROLL_HP);
		rowButtons.add(LEVELS);
		rowButtons.add(SPELLS);
		rowButtons.add(TO_HIT);
		rowButtons.add(SAVES);
		rowButtons.add(EQUIPMENT);
		tableButtons.add("Everybody dance now");
	}

	private List<String> rowButtons = new ArrayList<>();
	private List<String> tableButtons = new ArrayList<>();

	@Override
	public List<String> getRowButtonNames() {
		return rowButtons;
	}

	@Override
	public List<String> getTableButtonNames() {
		return tableButtons;
	}

	@Override
	public boolean doRowButton(Window window,String name, CharacterClass object) {
		if(ROLL_HP.equals(name)) {
			// roll up a character class
			RollHPDialog dialog = new RollHPDialog(window,"Roll "+object.getName()+" hit points");
			dialog.initialise(object);
			dialog.pack();
			dialog.setLocationRelativeTo(null);			
			dialog.setVisible(true);
			return false;
			
		} else if (LEVELS.equals(name)) {			
			ClassLevelsDialog dialog = new ClassLevelsDialog(window,"Levels for "+object.getName());
			dialog.initialise(object);
			dialog.pack();
			dialog.setLocationRelativeTo(null);			
			dialog.setVisible(true);
			return false;
		}else if (SPELLS.equals(name)) {			
			ClassSpellsDialog dialog = new ClassSpellsDialog(window,"Spells for "+object.getName());
			dialog.initialise(object);
			dialog.pack();
			dialog.setLocationRelativeTo(null);			
			dialog.setVisible(true);
			return false;
		}else if (TO_HIT.equals(name)) {			
			ClassToHitDialog dialog = new ClassToHitDialog(window,"To-Hit values for "+object.getName());
			dialog.initialise(object);
			dialog.pack();
			dialog.setLocationRelativeTo(null);			
			dialog.setVisible(true);
			return false;
		}else if (SAVES.equals(name)) {			
			ClassSavingThrowsDialog dialog = new ClassSavingThrowsDialog(window,"Saving Throw values for "+object.getName());
			dialog.initialise(object);
			dialog.pack();
			dialog.setLocationRelativeTo(null);			
			dialog.setVisible(true);
			return false;
		} else if(EQUIPMENT.equals(name)) {
			CharacterClassEquipmentDialog dialog = new CharacterClassEquipmentDialog (window,"Equipment values for "+object.getName());
			dialog.initialise(object);
			dialog.pack();
			dialog.setLocationRelativeTo(null);			
			dialog.setVisible(true);
			return false;
		}
		return false;
		
	}

	@Override
	public boolean doTableButton(Window window,String name) {
		return false;		
	}

}
