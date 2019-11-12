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
import com.nobbysoft.first.common.entities.equipment.Armour;
import com.nobbysoft.first.common.entities.equipment.EquipmentHands;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class ArmourPanel extends AbstractDataPanel<Armour, String> implements MaintenancePanelInterface<Armour> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public ArmourPanel() {
		setLayout(new GridBagLayout());
		jbInit();
	}

	@Override
	protected
	DataServiceI<?, ?> getDataService() {   
		DataServiceI dao  = DataMapper.INSTANCE.getDataService(Armour.class);
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
	

	private final PIntegerCombo txtBaseAC = new PIntegerCombo(-50,10);
	 
	
	private final PIntegerCombo txtMagicBonus = new PIntegerCombo(-5,+5,true);
  
	private final PTextField txtFinalAC = new PTextField();

	private final PIntegerCombo txtBaseMovement = new PIntegerCombo(3,12);
	private final PComboBulk txtBulk = new PComboBulk();
	
	private PDataComponent[] dataComponents = new PDataComponent[] { 
			txtName, 
			txtWeightGP,
			txtBaseAC,
			txtMagicBonus,
			txtBaseMovement,
			txtBulk,
			
			 };
	private PDataComponent[] keyComponents = new PDataComponent[] { txtCode };

	private PDataComponent[] mandatoryComponents = new PDataComponent[] { 
			txtCode,   
			txtName,  
			txtWeightGP, 
			txtBaseAC,
			txtBaseMovement,
			txtBulk};

	private PDataComponent[] buttonComponents = new PDataComponent[] {};

	
	public void jbInit() {

		txtFinalAC.setReadOnly(true);
		
		txtCode.setName("Id");
 
		txtName.setName("Name");
		txtWeightGP.setName("Weight (gp)");
		txtBaseAC.setName("Base AC");
		txtMagicBonus.setName("Magic Bonus");		
		txtFinalAC.setName("Final AC");
		txtBaseMovement.setName("Base movement (\")");
		txtBulk.setName("Bulk");

		PLabel lblCode = new PLabel(txtCode.getName() );

		PLabel lblName = new PLabel(txtName.getName() );
		PLabel lblWeightGP = new PLabel(txtWeightGP.getName() );
		PLabel lblBaseAC = new PLabel(txtBaseAC.getName() );
		PLabel lblMagicBonus = new PLabel(txtMagicBonus.getName() );
		PLabel lblFinalAC = new PLabel(txtFinalAC.getName()); 
		PLabel lblBaseMovement = new PLabel(txtBaseMovement.getName()); 
		PLabel lblBulk = new PLabel(txtBulk.getName()); 
		
		ActionListener al = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				calculateFinalAC();				
			}
			
		};
		
		txtBaseAC.addActionListener(al);
		txtMagicBonus.addActionListener(al);
		
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
		

		add(lblBaseAC, GBU.label(0, row));
		add(txtBaseAC, GBU.text(1, row));  
		
		add(lblMagicBonus, GBU.label(2, row));
		add(txtMagicBonus, GBU.text(3, row)); 
		
		add(lblFinalAC, GBU.label(4, row));
		add(txtFinalAC, GBU.text(5, row));
		row++;

		
		add(lblBulk, GBU.label(0, row));
		add(txtBulk, GBU.text(1, row));	
		add(lblBaseMovement, GBU.label(2, row));
		add(txtBaseMovement, GBU.text(3, row)); 
		row++;
		
		add(new PLabel(""), GBU.label(99, 99));

	}

	protected ReturnValue<?> validateScreen() {
		{
 
		}

		return new ReturnValue("");
	}

	protected Armour newT() {
		return new Armour();
	}

	protected void populateFromScreen(Armour value, boolean includingKeys) {
		if (includingKeys) {
			value.setCode(txtCode.getText());
		}
   
		value.setCapacityGP(0);// not for weapons 
	 
		
		value.setEncumberanceGP(txtWeightGP.getIntegerValue());
		 
		 
		
		value.setName(txtName.getText()); 
		value.setRequiresHands(EquipmentHands.NONE);// no hands for armour
		value.setBaseAC(txtBaseAC.getIntegerValue());
		value.setMagicBonus(txtMagicBonus.getIntegerValue());
		value.setBaseMovement(txtBaseMovement.getIntegerValue());
		value.setBulk(txtBulk.getBULK());
	}

	protected void populateScreen(Armour value) {
		txtCode.setText(value.getCode());  
		txtWeightGP	.setIntegerValue(	value.getEncumberanceGP());
		txtName	.setText(	value.getName()); 
		txtBaseAC.setIntegerValue(value.getBaseAC());
		txtMagicBonus.setIntegerValue(value.getMagicBonus());
		txtBaseMovement.setIntegerValue(value.getBaseMovement());
		txtBulk.setBULK(value.getBulk());

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
	
	private void calculateFinalAC() {
		SwingUtilities.invokeLater(()->{
			int b = txtBaseAC.getIntegerValue();
			int m = txtMagicBonus.getIntegerValue();
			txtFinalAC.setText(""+(b-m));
		});
	}
	

}
