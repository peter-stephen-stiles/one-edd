package com.nobbysoft.com.nobbysoft.first.client.components.special;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import com.nobbysoft.com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.com.nobbysoft.first.client.components.*;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.WeaponACAdjustmentsI;

public class PWeaponVsAc extends PPanel implements PDataComponent  {

	public PWeaponVsAc() {
		jbInit();
	}

	private PIntegerCombo txtVsAc02 =new PIntegerCombo(-9,9,true);
	private PIntegerCombo txtVsAc03 =new PIntegerCombo(-9,9,true);
	private PIntegerCombo txtVsAc04 =new PIntegerCombo(-9,9,true);
	private PIntegerCombo txtVsAc05 =new PIntegerCombo(-9,9,true);
	private PIntegerCombo txtVsAc06 =new PIntegerCombo(-9,9,true);
	private PIntegerCombo txtVsAc07 =new PIntegerCombo(-9,9,true);
	private PIntegerCombo txtVsAc08 =new PIntegerCombo(-9,9,true);
	private PIntegerCombo txtVsAc09 =new PIntegerCombo(-9,9,true);
	private PIntegerCombo txtVsAc10 =new PIntegerCombo(-9,9,true);

	private PIntegerCombo[] fields = new PIntegerCombo[] {
			txtVsAc02,
			txtVsAc03,
			txtVsAc04,
			txtVsAc05,
			txtVsAc06,
			txtVsAc07,
			txtVsAc08,
			txtVsAc09,
			txtVsAc10
	};
	
	 private void jbInit() {
		 setLayout(new GridLayout(2,1));// rows, cols		 
		 setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		 PPanel pnlData = new PPanel(new GridLayout(2,9));
		 PLabel lblTitle = new PLabel("Bonus to hit against AC");
		 lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		 add(lblTitle);
		 add(pnlData);
		 PLabel lblVsAc02 = new PLabel("2");
		 PLabel lblVsAc03 = new PLabel("3");
		 PLabel lblVsAc04 = new PLabel("4");
		 PLabel lblVsAc05 = new PLabel("5");
		 PLabel lblVsAc06 = new PLabel("6");
		 PLabel lblVsAc07 = new PLabel("7");
		 PLabel lblVsAc08 = new PLabel("8");
		 PLabel lblVsAc09 = new PLabel("9");
		 PLabel lblVsAc10 = new PLabel("10"); 

		 lblVsAc02.setHorizontalAlignment(SwingConstants.CENTER);
		 lblVsAc03.setHorizontalAlignment(SwingConstants.CENTER);
		 lblVsAc04.setHorizontalAlignment(SwingConstants.CENTER);
		 lblVsAc05.setHorizontalAlignment(SwingConstants.CENTER);
		 lblVsAc06.setHorizontalAlignment(SwingConstants.CENTER);
		 lblVsAc07.setHorizontalAlignment(SwingConstants.CENTER);
		 lblVsAc08.setHorizontalAlignment(SwingConstants.CENTER);
		 lblVsAc09.setHorizontalAlignment(SwingConstants.CENTER);
		 lblVsAc10.setHorizontalAlignment(SwingConstants.CENTER);
		 
		 pnlData.add(lblVsAc02);
		 pnlData.add(lblVsAc03);
		 pnlData.add(lblVsAc04);
		 pnlData.add(lblVsAc05);
		 pnlData.add(lblVsAc06);
		 pnlData.add(lblVsAc07);
		 pnlData.add(lblVsAc08);
		 pnlData.add(lblVsAc09);
		 pnlData.add(lblVsAc10);
		 pnlData.add(txtVsAc02); 
		 pnlData.add(txtVsAc03); 
		 pnlData.add(txtVsAc04); 
		 pnlData.add(txtVsAc05); 
		 pnlData.add(txtVsAc06); 
		 pnlData.add(txtVsAc07); 
		 pnlData.add(txtVsAc08); 
		 pnlData.add(txtVsAc09);
		 pnlData.add(txtVsAc10);  
	 }

	 public void populateScreen(WeaponACAdjustmentsI weapon) {
		 txtVsAc02.setIntegerValue(weapon.getACAdjustment02());
		 txtVsAc03.setIntegerValue(weapon.getACAdjustment03());
		 txtVsAc04.setIntegerValue(weapon.getACAdjustment04());
		 txtVsAc05.setIntegerValue(weapon.getACAdjustment05());
		 txtVsAc06.setIntegerValue(weapon.getACAdjustment06());
		 txtVsAc07.setIntegerValue(weapon.getACAdjustment07());
		 txtVsAc08.setIntegerValue(weapon.getACAdjustment08());
		 txtVsAc09.setIntegerValue(weapon.getACAdjustment09());
		 txtVsAc10.setIntegerValue(weapon.getACAdjustment10()); 
	 }
	 
	 public void populateFromScreen(WeaponACAdjustmentsI weapon) {
		 weapon.setACAdjustment02(txtVsAc02.getIntegerValue());
		 weapon.setACAdjustment03(txtVsAc03.getIntegerValue());
		 weapon.setACAdjustment04(txtVsAc04.getIntegerValue());
		 weapon.setACAdjustment05(txtVsAc05.getIntegerValue());
		 weapon.setACAdjustment06(txtVsAc06.getIntegerValue());
		 weapon.setACAdjustment07(txtVsAc07.getIntegerValue());
		 weapon.setACAdjustment08(txtVsAc08.getIntegerValue());
		 weapon.setACAdjustment09(txtVsAc09.getIntegerValue());
		 weapon.setACAdjustment10(txtVsAc10.getIntegerValue());
	 }

	@Override
	public void setTheValue(Object o) {
		throw new IllegalStateException("don't push me");
	}

	@Override
	public Object getTheValue() {
		throw new IllegalStateException("don't push me");
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		for(PIntegerCombo f:fields) {
			f.setReadOnly(readOnly);
		}
		
	}

	@Override
	public boolean isReadOnly() { 
		return txtVsAc02.isReadOnly();
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
