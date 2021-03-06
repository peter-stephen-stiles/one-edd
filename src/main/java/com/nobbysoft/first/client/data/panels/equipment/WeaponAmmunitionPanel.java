package com.nobbysoft.first.client.data.panels.equipment;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PCodeField;
import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.client.components.PIntegerCombo;
import com.nobbysoft.first.client.components.PIntegerField;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PTextField;
import com.nobbysoft.first.client.components.special.DicePanel;
import com.nobbysoft.first.client.components.special.PComboDICE;
import com.nobbysoft.first.client.components.special.PComboEquipmentHands;
import com.nobbysoft.first.client.components.special.PWeaponMagic;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.data.panels.AbstractDataPanel;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.equipment.EquipmentHands;
import com.nobbysoft.first.common.entities.equipment.WeaponAmmunition;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.common.views.DicePanelData;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class WeaponAmmunitionPanel extends AbstractDataPanel<WeaponAmmunition, String> implements MaintenancePanelInterface<WeaponAmmunition> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public WeaponAmmunitionPanel() {
		setLayout(new GridBagLayout());
		jbInit();
	}

	@Override
	protected DataServiceI<?, ?> getDataService() {   
		DataServiceI dao  = DataMapper.INSTANCE.getDataService(WeaponAmmunition.class);
		return dao;
		
	}


	private PDialog parentd;
	public void setParentDialog(PDialog parentd) {
		this.parentd=parentd;
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
	 
	private final DicePanel txtSMDamage = new DicePanel();
	private final DicePanel txtLDamage = new DicePanel();
	
	
	
	private final PTextField txtNotes = new PTextField(255);
	 
	
	private final PWeaponMagic txtWeaponMagic = new PWeaponMagic();
  
  

	private PDataComponent[] dataComponents = new PDataComponent[] { 
			txtName, 
			txtWeightGP,
			txtNotes,
			 txtWeaponMagic,
			 txtSMDamage,
			 txtLDamage
			 };
	private PDataComponent[] keyComponents = new PDataComponent[] { txtCode };

	private PDataComponent[] mandatoryComponents = new PDataComponent[] { txtCode,   txtName,  txtWeightGP, };

	private PDataComponent[] buttonComponents = new PDataComponent[] {};

	
	public void jbInit() {

		txtCode.setName("Id");
 
		txtName.setName("Name");
		txtWeightGP.setName("Weight (gp)");
		txtSMDamage.setName("Damage (S/M)");
		txtLDamage.setName("Damage (L)");
		txtNotes.setName("Notes");


		PLabel lblCode = new PLabel(txtCode.getName() );

		PLabel lblName = new PLabel(txtName.getName() );
		PLabel lblWeightGP = new PLabel(txtWeightGP.getName() );
		PLabel lblDamageSM = new PLabel(txtSMDamage.getName() );
		PLabel lblDamageL = new PLabel(txtLDamage.getName() );
		PLabel lblNotes = new PLabel(txtNotes.getName()); 
		

		
		add(getLblInstructions(), GBU.text(0, 0, 2));
		int row=1;
		add(lblCode, GBU.label(0, row));
		add(txtCode, GBU.text(1, row)); 

		add(lblName, GBU.label(2, row));
		add(txtName, GBU.text(3, row,2));
		row++;
	
		add(lblWeightGP, GBU.label(0, row));
		add(txtWeightGP, GBU.text(1, row));
		row++;
		

		add(lblDamageSM, GBU.label(0, row));
		add(txtSMDamage, GBU.text(1, row)); 
		row++;
		
		add(lblDamageL, GBU.label(0, row));
		add(txtLDamage, GBU.text(1, row)); 
		row++;
		
		add(txtWeaponMagic,GBU.hPanel(0,row,4,1));
		row++;
		
		add(lblNotes, GBU.label(0, row));
		add(txtNotes, GBU.text(1, row,3));
		row++;

		add(new PLabel(""), GBU.label(99, 99));

	}

	protected ReturnValue<?> validateScreen() {
		{
 
		}

		return new ReturnValue("");
	}

	protected WeaponAmmunition newT() {
		return new WeaponAmmunition();
	}

	protected void populateFromScreen(WeaponAmmunition value, boolean includingKeys) {
		if (includingKeys) {
			value.setCode(txtCode.getText());
		}
   
		value.setCapacityGP(0);// not for weapons 
	
		value.setDamageL(txtLDamage.getDicePanelData());
			
		value.setDamageSM(txtSMDamage.getDicePanelData());
		
		value.setEncumberanceGP(txtWeightGP.getIntegerValue());
		
		
		txtWeaponMagic.populateFromScreen(value); 
		 
		
		value.setName(txtName.getText());
		value.setNotes(txtNotes.getText()); 
		value.setRequiresHands(EquipmentHands.NONE);// no hands for ammo
	}

	protected void populateScreen(WeaponAmmunition value) {
		txtCode.setText(value.getCode());
 
		txtLDamage.setDicePanelData(value.getDamageL());
		
		txtSMDamage.setDicePanelData(value.getDamageSM());
		 
		txtWeightGP	.setIntegerValue(	value.getEncumberanceGP());
		
		txtWeaponMagic.populateScreen(value);
		 
		txtName	.setText(	value.getName());
		txtNotes	.setText(	value.getNotes()); 
 

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
