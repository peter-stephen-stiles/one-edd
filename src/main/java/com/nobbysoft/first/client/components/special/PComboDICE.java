package com.nobbysoft.first.client.components.special;

import javax.swing.DefaultComboBoxModel;

import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.common.utils.DICE;

@SuppressWarnings("serial")
public class PComboDICE extends PComboBox<DICE> {
	
	public PComboDICE(){
		super();
		populate();
	}
	
	private void populate() {
		
		DefaultComboBoxModel model = (DefaultComboBoxModel)this.getModel();
		
		for(DICE dice:DICE.values()) {
			model.addElement(dice);	
		}
	}

	public void setDICE(DICE g) {
		this.setSelectedItem(g);
	}
	
	public DICE getDICE() {
		return (DICE)getSelectedItem();
	}
	
}
