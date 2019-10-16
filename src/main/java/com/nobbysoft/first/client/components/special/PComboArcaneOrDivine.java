package com.nobbysoft.first.client.components.special;

import javax.swing.DefaultComboBoxModel;

import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.common.entities.staticdto.Alignment;
import com.nobbysoft.first.common.utils.CodedListItem;

@SuppressWarnings("serial")
public class PComboArcaneOrDivine extends PComboBox<Alignment> {

	public PComboArcaneOrDivine(){
		super();
		populate();
	}
	
	private void populate() {
		
		DefaultComboBoxModel model = (DefaultComboBoxModel)this.getModel();
		CodedListItem<String> arcane = new CodedListItem<>("A","Arcane");
		CodedListItem<String> divine = new CodedListItem<>("D","Divine");
		CodedListItem<String> neither = new CodedListItem<>(null,"{neither}") ;
		
		model.addElement(neither);
		model.addElement(arcane);
		model.addElement(divine);	
		
	}

	public void setArcaneOrDivine(String g) {
		this.setSelectedItem(g);
	}
	
	public String getArcaneOrDivine() {
		Object o=getSelectedItem();
		if(o==null) {
			return null;
		} else {
			return ((CodedListItem<String>)o).getItem();
		}
	}
	
}
