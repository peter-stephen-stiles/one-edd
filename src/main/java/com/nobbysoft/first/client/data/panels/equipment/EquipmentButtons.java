package com.nobbysoft.first.client.data.panels.equipment;

import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import com.nobbysoft.first.client.data.panels.DataButtonsInterface;
import com.nobbysoft.first.common.entities.equipment.EquipmentI;

public class EquipmentButtons implements DataButtonsInterface<EquipmentI>{

	private static final String CLASSES = "Classes";
	public EquipmentButtons() {
		// TODO Auto-generated constructor stub
		rowButtons.add(CLASSES);
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
	public boolean doRowButton(Window window,String name, EquipmentI object) {
		
		if(CLASSES.equals(name)) {
			EquipmentCharacterClassDialog dialog = new EquipmentCharacterClassDialog (window,"Classes for "+object.getName());
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
