package com.nobbysoft.first.client.data.panels;

import java.awt.Window;
import java.util.*;

import com.nobbysoft.first.client.components.PButton;
import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;

public class CharacterClassButtons implements DataButtonsInterface<CharacterClass> {

	private static final String ROLL_HP = "Roll HP";
	private static final String SPELLS = "Spells By Level";

	public CharacterClassButtons() {

		rowButtons.add(ROLL_HP);
		rowButtons.add(SPELLS);
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
	public void doRowButton(Window window,String name, CharacterClass object) {
		if(ROLL_HP.equals(name)) {
			// roll up a character class
			RollHPDialog dialog = new RollHPDialog(window,"Roll "+object.getName()+" hit points");
			dialog.initialise(object);
			dialog.setLocationRelativeTo(null);
			dialog.pack();
			dialog.setVisible(true);
			
		} else if (SPELLS.equals(name)) {			
			ClassSpellsDialog dialog = new ClassSpellsDialog(window,"Spells for "+object.getName());
			dialog.initialise(object);
			dialog.setLocationRelativeTo(null);
			dialog.pack();
			dialog.setVisible(true);
		}
		
	}

	@Override
	public void doTableButton(Window window,String name) {
		// TODO Auto-generated method stub
		
	}

}
