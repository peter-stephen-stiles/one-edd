package com.nobbysoft.first.client.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.utils.DataMapper;

public class PComboBox<E> extends JComboBox<E> implements PDataComponent {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass()); 

	
	private JComponent linkedComponent=null;
	
	public void linkSizeTo(JComponent c) {
		linkedComponent=c;
	}
	public Dimension getMinimumSize() {
		if(linkedComponent==null) {
			return super.getMinimumSize();
		}
		return linkedComponent.getMinimumSize();
		
	}	public Dimension getMaximumSize() {
		if(linkedComponent==null) {
			return super.getMaximumSize();
		}
		return linkedComponent.getMaximumSize();
	}	

	public Dimension getSize() {
		if(linkedComponent==null) {
			return super.getSize();
		}
		return linkedComponent.getSize();
	}
	
	public PComboBox(){
		super(); 
		setRenderer(new PListCellRenderer<E>());
	}
	
	@Override
	public void setTheValue(Object o) {
		setSelectedItem(o);
	}

	public void setSelectedCode(Object in){
		for(int i=0,n=getItemCount();i<n;i++){
			Object o = getItemAt(i);
			if(o instanceof CodedListItem){
				CodedListItem<?> cli = (CodedListItem<?>)o;
				if(in==null) {
					if(cli==null) {
						setSelectedIndex(i);
						return;
					}
				} else {
					Object oo = cli.getItem();
					if(oo!=null) {
						if(oo.equals(in)){
							setSelectedIndex(i);
							return;
						}
					}
				}
			} else {
				if (o!=null && o.equals(in)) {

					setSelectedIndex(i);
					return;
				} if(o==null && in ==null) {

					setSelectedIndex(i);
					return;
				}
			}
		}
	}
	
	public Object getSelectedCode(){
		Object o = getSelectedItem();
		if(o instanceof CodedListItem){
			CodedListItem<?> cli = (CodedListItem<?>)o;
			return ((CodedListItem) o).getItem();
		} else {
			return o;
		}
	}
	
	@Override
	public E getTheValue() { 
		return (E)getSelectedItem();
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		setEnabled(!readOnly);
	}

	@Override
	public boolean isReadOnly() {
		return !isEnabled();
	}
	
	public void setList(List<?> list){
		// remove all items
		DefaultComboBoxModel model = (DefaultComboBoxModel)this.getModel();
		model.removeAllElements();
		for(Object o:list){
			model.addElement(o);
		}
	}

	int ph = 0;
	int pw=0;
	@Override
	public void setMinimumHeight(int height) {
		ph=height;
	}
	@Override
	public void setMinimumWidth(int width) {
		pw=width;
	}
	@Override
	public Dimension getPreferredSize() {
		
		if(linkedComponent!=null) {
			return linkedComponent.getPreferredSize();
		}
		
		Dimension d = super.getPreferredSize();
		if(d.getWidth()<pw) {
			d.width=pw;
		}
		if(d.getHeight()<ph) {
			d.height=ph;
		}
		return d;
	}
	
}
