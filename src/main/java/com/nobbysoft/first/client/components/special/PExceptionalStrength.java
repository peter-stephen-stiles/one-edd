package com.nobbysoft.first.client.components.special;

import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;

import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.common.utils.CodedListItem;

@SuppressWarnings("serial")
public class PExceptionalStrength extends PComboBox<CodedListItem<Integer>> {
	
	
	public PExceptionalStrength(){
		super();
		populate();
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
	
	public Dimension getPreferredSize() {
		Dimension d = super.getPreferredSize();
		if(d.height<21) {
			d.height=21;
		}
		if(d.width<40) {
			d.width=40;
		}
		return d;
	}
	
	private void populate() {
	DefaultComboBoxModel<CodedListItem<Integer>> model = (DefaultComboBoxModel)this.getModel();
	model.addElement(new CodedListItem<Integer>(0,""));
	for(int i=1,n=100;i<n;i++) {
		model.addElement(new CodedListItem<Integer>(i,""+i));	
	}
	model.addElement(new CodedListItem<Integer>(100,"00"));
	}
	
	
	public int getExceptionalStrength() {
		return ((CodedListItem<Integer>)getSelectedItem()).getItem();
	}
	public void setExceptionalStrength(int exceptionalStrength) {
		setSelectedIndex(exceptionalStrength);// clever eh?
	}
	
	
	
}
