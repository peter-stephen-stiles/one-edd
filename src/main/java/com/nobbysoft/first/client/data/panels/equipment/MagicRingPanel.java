package com.nobbysoft.first.client.data.panels.equipment;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.invoke.MethodHandles;

import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PCodeField;
import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.client.components.PIntegerCombo;
import com.nobbysoft.first.client.components.PIntegerField;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PTextField;
import com.nobbysoft.first.client.components.special.PComboBulk;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.data.panels.AbstractDataPanel;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.equipment.MagicRing;
import com.nobbysoft.first.common.entities.DTORowListener;
import com.nobbysoft.first.common.entities.equipment.EquipmentHands;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class MagicRingPanel extends AbstractDataPanel<MagicRing, String> implements MaintenancePanelInterface<MagicRing> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public MagicRingPanel() {
		setLayout(new GridBagLayout());
		jbInit();
	}

	@Override
	protected
	DataServiceI<?, ?> getDataService() {   
		DataServiceI dao  = DataMapper.INSTANCE.getDataService(MagicRing.class);
		return dao;
		
	}
	
	private PDialog parent;
	public void setParentDialog(PDialog parent) {
		this.parent=parent;
	}
 
 
	private final PCodeField txtCode = new PCodeField(); 
	private final PTextField txtName = new PTextField(60) {
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			if(d.getWidth()< 300) {
				d.width=300;
			}
			return d;
		}
	};
	private final PIntegerField txtWeightGP = new PIntegerField();
	private final PIntegerCombo txtMagicalBonus = new PIntegerCombo(-5,+5,true);

	private final PTextField txtExtendedBonus = new PTextField(255);
 
	
	private PDataComponent[] dataComponents = new PDataComponent[] { 
			txtName, 
			txtWeightGP,
			txtMagicalBonus,
			txtExtendedBonus			
			 };
	private PDataComponent[] keyComponents = new PDataComponent[] { txtCode };

	private PDataComponent[] mandatoryComponents = new PDataComponent[] { 
			txtCode,   
			txtName,  
			txtWeightGP };

	private PDataComponent[] buttonComponents = new PDataComponent[] {};

	
	public void jbInit() {

		
		txtCode.setName("Id");
 
		txtName.setName("Name");
		txtWeightGP.setName("Weight (gp)");
		
		txtMagicalBonus.setName("Magic Bonus");		
		
		PLabel lblCode = new PLabel(txtCode.getName() );

		PLabel lblName = new PLabel(txtName.getName() );
		PLabel lblWeightGP = new PLabel(txtWeightGP.getName() );
		
		PLabel lblMagicalBonus = new PLabel(txtMagicalBonus.getName() );
   
	

		PLabel lblExtendedBonus = new PLabel(txtExtendedBonus.getName() );
		
		
		add(getLblInstructions(), GBU.text(0, 0, 2));
		int row=1;
		add(lblCode, GBU.label(0, row));
		add(txtCode, GBU.text(1, row)); 
		row++;
		add(lblName, GBU.label(0, row));
		add(txtName, GBU.text(1, row,3));
		row++;
	
		add(lblWeightGP, GBU.label(0, row));
		add(txtWeightGP, GBU.text(1, row));
		row++;
		 

		add(lblMagicalBonus, GBU.label(0, row));
		add(txtMagicalBonus, GBU.text(1, row)); 
		 
		row++;
		add(txtExtendedBonus, GBU.label(0, row));
		row++;
		add(txtExtendedBonus, GBU.text(0, row,3)); 
		 
		row++;

//		
//		add(lblBulk, GBU.label(0, row));
//		add(txtBulk, GBU.text(1, row));	 
		row++;
		
		add(new PLabel(""), GBU.label(99, 99));

	}

	protected ReturnValue<?> validateScreen() {
		{
 
		}

		return new ReturnValue("");
	}

	protected MagicRing newT() {
		return new MagicRing();
	}

	protected void populateFromScreen(MagicRing value, boolean includingKeys) {
		if (includingKeys) {
			value.setCode(txtCode.getText());
		}   
		value.setEncumberanceGP(txtWeightGP.getIntegerValue());		
		value.setName(txtName.getText()); 
		value.setRequiresHands(EquipmentHands.NONE);// no hands for MagicRing 
		value.setMagicalBonus(txtMagicalBonus.getIntegerValue()); 
		value.setExtendedBonus(txtExtendedBonus.getText());
	}

	protected void populateScreen(MagicRing value) {
		txtCode.setText(value.getCode());  
		txtWeightGP	.setIntegerValue(	value.getEncumberanceGP());
		txtName	.setText(	value.getName());  
		txtMagicalBonus.setIntegerValue(value.getMagicalBonus());  
		txtExtendedBonus.setText(value.getExtendedBonus());

	}

	protected void populateCombos() {

		try {
 
		} catch (Exception ex) {
			Popper.popError(GuiUtils.getParent(this), ex);

		}
	}

	@Override
	protected PDataComponent[] getDataComponents() {
		return dataComponents;
	}

	@Override
	protected PDataComponent[] getKeyComponents() {
		return keyComponents;
	}

	@Override
	protected PDataComponent[] getButtonComponents() {
		return buttonComponents;
	}

	@Override
	protected PDataComponent[] getMandatoryComponents() {
		return mandatoryComponents;
	}
	
 
	

}
