package com.nobbysoft.first.client.components;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import com.nobbysoft.first.common.entities.staticdto.Alignment;

public class AlignmentPicker extends JPanel implements PDataComponent{

	public AlignmentPicker() {
		this(true);	
	}

	private boolean singleSelection=false;
	
	public AlignmentPicker(boolean singleSelection) {
		super(new GridLayout(3,3));		
		setSingleSelection(singleSelection);
		jbInit();
	}
 
	private JToggleButton cbxLG = new JToggleButton("LG");
	private JToggleButton cbxLN = new JToggleButton("LN");
	private JToggleButton cbxLE = new JToggleButton("LE");
	
	private JToggleButton cbxNG = new JToggleButton("NG");
	private JToggleButton cbxN = new JToggleButton("N");
	private JToggleButton cbxNE = new JToggleButton("NE");
	
	private JToggleButton cbxCG = new JToggleButton("CG");
	private JToggleButton cbxCN = new JToggleButton("CN");
	private JToggleButton cbxCE = new JToggleButton("CE");
	
	/**
	 * must be in sequence of Alignment.getIndex()
	 */
	private JToggleButton[] cbxAll = new JToggleButton[] {
			cbxLG,
			cbxLN,
			cbxLE,	
			cbxNG,
			cbxN,
			cbxNE,	
			cbxCG,
			cbxCN,
			cbxCE
	};
	
	
	public Alignment getAlignment() {
		for(Alignment al:Alignment.values()) {
			if(cbxAll[al.getIndex()].isSelected()) {
				return al;
			}
		}
		return null;
	}
	
	public void setAlignment(Alignment al) {
		bgAll.clearSelection();
		setSelected(al);
	}
	
	private void setSingleSelection(boolean singleSelection) {
		
		this.singleSelection=singleSelection;
		
		for( JToggleButton cbx: cbxAll) {
			if(singleSelection) {
			 bgAll.add(cbx);
			} else {
			 bgAll.remove(cbx);
			}
		}
		
	}
	
	public boolean[] getIsSelecteds() {
		return new boolean[] {
				cbxLG.isSelected(),
				cbxLN.isSelected(),
				cbxLE.isSelected(),	
				cbxNG.isSelected(),
				cbxN.isSelected(),
				cbxNE.isSelected(),	
				cbxCG.isSelected(),
				cbxCN.isSelected(),
				cbxCE.isSelected()
		};
	}
	
	public void setIsSelecteds(boolean[] selected) {
		for( int i=0,n=selected.length;i<n;i++){
			cbxAll[i].setSelected(selected[i]);
		}
	}
	
	
	
	public Alignment[] getSelected() {
		List<Alignment> als = new ArrayList<>();
		for(Alignment al:Alignment.values()) {
			if(cbxAll[al.getIndex()].isSelected()) {
				als.add(al);
			}
		}
		
		return als.toArray(new Alignment[als.size()]);
	}
	
	public void setSelected(Alignment... alignments) {
		if(singleSelection && alignments.length<2) {
			bgAll.clearSelection();
		}
		for(Alignment al:alignments){
			cbxAll[al.getIndex()].setSelected(true);
		}
	}
	
	
	private ButtonGroup bgAll = new ButtonGroup();
	
	private void jbInit() {
		for(JComponent c:cbxAll) {
			this.add(c);
		}
	 
	}

	@Override
	public void setTheValue(Object o) {
		Alignment al = (Alignment)o;
		setAlignment(al);
		
	}

	@Override
	public Object getTheValue() {
		// TODO Auto-generated method stub
		return getAlignment();
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		for(JComponent c:cbxAll) {
			c.setEnabled(!readOnly);
		}
		
	}

	private int mh=0;
	private int mw=0;
	
	public Dimension getMinimumSize() {
		Dimension d = super.getMinimumSize();
		if(d.width<mw) {
			d.width=mw;
		}
		if(d.height<mh) {
			d.height=mh;
		}
		return d;
	}
	
	@Override
	public boolean isReadOnly() {		
		return !cbxAll[0].isEnabled();
	}

	@Override
	public void setMinimumHeight(int height) {
		mh = height;
	}

	@Override
	public void setMinimumWidth(int width) {
		mw = width;
		
	}

}
