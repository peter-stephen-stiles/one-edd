package com.nobbysoft.com.nobbysoft.first.client.components.special;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

import com.nobbysoft.com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.com.nobbysoft.first.client.components.PIntegerCombo;
import com.nobbysoft.com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.com.nobbysoft.first.client.components.PTextField;
import com.nobbysoft.com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.WeaponMagicI;

public class PWeaponMagic extends PPanel implements PDataComponent {

	public PWeaponMagic() { 
		super(new GridBagLayout());
		jbInit();
	}
 
	@Override
	public void setTheValue(Object o) {
		populateScreen((WeaponMagicI)o);

	}

	@Override
	public Object getTheValue() {
		throw new IllegalStateException("you can't call this method, sorry");
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		for(PDataComponent  c: dataComponents) {
			c.setReadOnly(readOnly);
		}

	}

	@Override
	public boolean isReadOnly() {
		return dataComponents[0].isReadOnly();
	}

	private PIntegerCombo txtMagicBonus = new PIntegerCombo(-5,+5,true);
	private PIntegerCombo txtExtraMagicBonus = new PIntegerCombo(-5,+5,true);
	private final PTextField txtExtraMagicCondition = new PTextField(255);
	
	private PDataComponent[] dataComponents = new PDataComponent[] { 
			 txtMagicBonus ,
			 txtExtraMagicBonus ,
			 txtExtraMagicCondition,
			 };
	
	private void jbInit() {
		txtMagicBonus .setName("Magic bonus");
		txtExtraMagicBonus .setName("(Extra bonus");
		txtExtraMagicCondition.setName("for");
		
		PLabel lblMagicBonus = new PLabel(txtMagicBonus.getName());
		PLabel lblExtraMagicBonus = new PLabel(txtExtraMagicBonus.getName());
		PLabel lblExtraMagicCondition = new PLabel(txtExtraMagicCondition.getName());
		
		 
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		add(lblMagicBonus,GBU.label(0, 0));
		add(txtMagicBonus,GBU.text(1, 0));
		add(lblExtraMagicBonus,GBU.label(2, 0));
		add(txtExtraMagicBonus,GBU.text(3, 0));
		add(lblExtraMagicCondition,GBU.label(4, 0));
		add(txtExtraMagicCondition,GBU.text(5, 0));
		add(new PLabel(")"),GBU.label(6, 0));
		add(new PLabel(),GBU.label(99, 99));
		
	}
	
	public void populateFromScreen(WeaponMagicI value) {
		value.setExtraMagicBonus(txtExtraMagicBonus.getIntegerValue());
		value.setExtraMagicCondition(txtExtraMagicCondition.getText());
		value.setMagicBonus(txtMagicBonus.getIntegerValue());
	}
	
	
	public void populateScreen(WeaponMagicI value) {
		txtExtraMagicBonus	.setIntegerValue(	value.getExtraMagicBonus());
		txtExtraMagicCondition	.setText(	value.getExtraMagicCondition());
		txtMagicBonus	.setIntegerValue(	value.getMagicBonus());

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
