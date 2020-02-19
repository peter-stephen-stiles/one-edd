package com.nobbysoft.first.client.data.panels.equipment;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.lang.invoke.MethodHandles;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PCodeField;
import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.client.components.PIntegerField;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PTextArea;
import com.nobbysoft.first.client.components.PTextField;
import com.nobbysoft.first.client.components.special.PComboEquipmentHands;
import com.nobbysoft.first.client.components.special.PComboRodStaffWand;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.data.panels.AbstractDataPanel;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.equipment.RodStaffWand;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class RodStaffWandPanel extends AbstractDataPanel<RodStaffWand, String> implements MaintenancePanelInterface<RodStaffWand> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public RodStaffWandPanel() {
		setLayout(new GridBagLayout());
		jbInit();
	}

	@Override
	protected
	DataServiceI<?, ?> getDataService() {   
		DataServiceI dao  = DataMapper.INSTANCE.getDataService(RodStaffWand.class);
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
	private final PTextArea txtDefinition = new PTextArea(2000);
	private final PComboEquipmentHands txtComboEquipmentHands = new PComboEquipmentHands();
	private final PComboRodStaffWand txtComboRodStaffWand = new PComboRodStaffWand();
	
	
	private PDataComponent[] dataComponents = new PDataComponent[] { 
			txtName, 
			txtWeightGP, 
			txtDefinition	,
			txtComboEquipmentHands,
			txtComboRodStaffWand
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
		txtComboEquipmentHands.setName("Requires hands");
		txtDefinition.setName("Definition");
		txtComboRodStaffWand.setName("Type");
		
				
		add(getLblInstructions(), GBU.text(0, 0, 2));
		int row=1;
		add(new JLabel(txtCode.getName()), GBU.label(0, row));
		add(txtCode, GBU.text(1, row)); 
		 
		row++;
		add(new JLabel(txtComboRodStaffWand.getName()), GBU.label(0, row));
		add(txtComboRodStaffWand, GBU.text(1, row)); 
		 
		row++;
		row++;
		add(new JLabel(txtName.getName()), GBU.label(0, row));
		add(txtName, GBU.text(1, row,3));
		row++;
	
		add(new JLabel(txtWeightGP.getName()), GBU.label(0, row));
		add(txtWeightGP, GBU.text(1, row));
		row++;
		 

		add(new JLabel(txtComboEquipmentHands.getName()), GBU.label(0, row));
		add(txtComboEquipmentHands, GBU.text(1, row)); 
		 
		row++;		 

		add(new JLabel(txtDefinition.getName()), GBU.label(0, row));
		row++;
		
		JScrollPane sclDefinition= new JScrollPane(txtDefinition) {
			public Dimension getPreferredSize() {
				Dimension d= super.getPreferredSize();
				if(d.height<80) {
					d.height=80;
				}
				if(d.width<150) {
					d.width=150;
				}
				return d;
			}
		};
		
		add(sclDefinition, GBU.panel(0, row,3,3)); 
		 
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

	protected RodStaffWand newT() {
		return new RodStaffWand();
	}

	protected void populateFromScreen(RodStaffWand value, boolean includingKeys) {
		if (includingKeys) {
			value.setCode(txtCode.getText());
		}   
		value.setEncumberanceGP(txtWeightGP.getIntegerValue());		
		value.setName(txtName.getText()); 
		value.setRequiresHands(txtComboEquipmentHands.getEquipmentHands());// no hands for RodStaffWand 		
		value.setDefinition(txtDefinition.getText());
		value.setRodStaffOrWand(txtComboRodStaffWand.getText());
	}

	protected void populateScreen(RodStaffWand value) {
		txtCode.setText(value.getCode());  
		txtWeightGP	.setIntegerValue(	value.getEncumberanceGP());
		txtName	.setText(	value.getName());  
		txtDefinition.setText(value.getDefinition());
		txtComboRodStaffWand.setText(value.getRodStaffOrWand());
		txtComboEquipmentHands.setEquipmentHands(value.getRequiresHands());
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
