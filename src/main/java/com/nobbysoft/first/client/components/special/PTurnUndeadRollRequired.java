package com.nobbysoft.first.client.components.special;

import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;

import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.sun.media.sound.ModelAbstractChannelMixer;

@SuppressWarnings("serial")
public class PTurnUndeadRollRequired extends PComboBox<CodedListItem<Integer>> {
	
	
	public PTurnUndeadRollRequired(){
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
	model.addElement(new CodedListItem<Integer>(-1,"D"));
	model.addElement(new CodedListItem<Integer>(0,"T"));	
	for(int i=1,n=21;i<n;i++) {
		model.addElement(new CodedListItem<Integer>(i,""+i));	
	}
	model.addElement(new CodedListItem<Integer>(99,"--"));
	}
	
	
	public int getRollRequired() {
		
		Object o = getSelectedItem();
		if(o==null) {
			return 0;
		} else {		
			return ((CodedListItem<Integer>)getSelectedItem()).getItem();
		}
	}
	public void setRollRequired(int roll) {
		DefaultComboBoxModel<CodedListItem<Integer>> model = (DefaultComboBoxModel)this.getModel();
		for(int i=0,n=model.getSize();i<n;i++) {
			int val = model.getElementAt(i).getItem();
			if(val==roll) {
				setSelectedIndex(i);// clever eh?
				break;
			}
		}
	}
	
	
	
}
