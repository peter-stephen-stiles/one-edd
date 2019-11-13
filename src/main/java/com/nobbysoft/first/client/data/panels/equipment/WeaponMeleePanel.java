package com.nobbysoft.first.client.data.panels.equipment;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.lang.invoke.MethodHandles;

import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PCheckBox;
import com.nobbysoft.first.client.components.PCodeField;
import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.client.components.PIntegerCombo;
import com.nobbysoft.first.client.components.PIntegerField;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.first.client.components.PTextField;
import com.nobbysoft.first.client.components.special.DicePanel;
import com.nobbysoft.first.client.components.special.PComboDICE;
import com.nobbysoft.first.client.components.special.PComboEquipmentHands;
import com.nobbysoft.first.client.components.special.PWeaponMagic;
import com.nobbysoft.first.client.components.special.PWeaponVsAc;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.data.panels.AbstractDataPanel;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.equipment.WeaponMelee;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.common.views.DicePanelData;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class WeaponMeleePanel extends AbstractDataPanel<WeaponMelee, String> implements MaintenancePanelInterface<WeaponMelee> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public WeaponMeleePanel() {
		setLayout(new GridBagLayout());
		jbInit();
	}

	@Override
	protected DataServiceI<?, ?> getDataService() {   
		DataServiceI dao  = DataMapper.INSTANCE.getDataService(WeaponMelee.class);
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
	
	private final PCheckBox txtTwiceDamageCharged = new PCheckBox("Does twice damage to charging opponents when grounded");
	private final PCheckBox txtTwiceDamageCharging = new PCheckBox("Does twice damage when charging");
	private final PCheckBox txtTwiceDamageGrounded = new PCheckBox("Does twice damage when grounded");
	
	private final PWeaponMagic txtWeaponMagic = new PWeaponMagic();

	private final PTextField txtLength = new PTextField(20);
	private final PTextField txtSpaceRequired = new PTextField(20);
	private final PIntegerCombo txtSpeedFactor = new PIntegerCombo(1,10);
	
	private final PComboEquipmentHands txtComboEquipmentHands = new PComboEquipmentHands();
	
	private final PCheckBox txtCapableOfDismountingRider = new PCheckBox("Capable of dismounting target rider");
	private final PCheckBox txtCapableOfDisarmingAgainstAC8 = new PCheckBox("Capable of disarming on AC8 hit");
 

 
	private PWeaponVsAc weaponVsAc = new PWeaponVsAc();

	private PDataComponent[] dataComponents = new PDataComponent[] { 
			txtName, 
			weaponVsAc ,
			txtWeightGP,
			txtNotes,
			 txtTwiceDamageCharged  ,
			 txtTwiceDamageCharging ,
			 txtTwiceDamageGrounded ,
			 txtLength ,
			 txtSpaceRequired,
			 txtSpeedFactor,
			 txtComboEquipmentHands,
			 txtCapableOfDismountingRider,
			 txtCapableOfDisarmingAgainstAC8,
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
		txtLength.setName("Length");
		txtSpaceRequired.setName("Space required (feet)");
		txtSpeedFactor.setName("Speed factor");
		txtComboEquipmentHands.setName("Requires hands");


		PLabel lblCode = new PLabel(txtCode.getName() );

		PLabel lblName = new PLabel(txtName.getName() );
		PLabel lblWeightGP = new PLabel(txtWeightGP.getName() );
		PLabel lblDamageSM = new PLabel(txtSMDamage.getName() );
		PLabel lblDamageL = new PLabel(txtLDamage.getName() );
		PLabel lblNotes = new PLabel(txtNotes.getName());
		PLabel lblLength = new PLabel(txtLength.getName());
		PLabel lblSpaceRequired = new PLabel(txtSpaceRequired.getName());
		PLabel lblSpeedFactor = new PLabel(txtSpeedFactor.getName());
		PLabel lblComboEquipmentHands = new PLabel(txtComboEquipmentHands.getName());
		

		
		add(getLblInstructions(), GBU.text(0, 0, 2));
		int row=1;
		add(lblCode, GBU.label(0, row));
		add(txtCode, GBU.text(1, row)); 

		add(lblName, GBU.label(2, row));
		add(txtName, GBU.text(3, row,2));
		row++;
		
		add(lblComboEquipmentHands, GBU.label(0, row));
		add(txtComboEquipmentHands, GBU.text(1, row)); 		
		add(lblWeightGP, GBU.label(2, row));
		add(txtWeightGP, GBU.text(3, row));
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

		add(txtTwiceDamageCharged, GBU.text(1, row,3));row++;
		add(txtTwiceDamageCharging, GBU.text(1, row,3));row++;
		add(txtTwiceDamageGrounded, GBU.text(1, row,3));row++;
		 

		add(lblLength, GBU.label(0, row));
		add(txtLength, GBU.text(1, row));
		row++;

		add(lblSpaceRequired, GBU.label(0, row));
		add(txtSpaceRequired, GBU.text(1, row));
		row++;

		add(lblSpeedFactor , GBU.label(0, row));
		add(txtSpeedFactor, GBU.text(1, row));
		row++;
		

		add(txtCapableOfDismountingRider, GBU.text(1, row,3));row++;
		add(txtCapableOfDisarmingAgainstAC8, GBU.text(1, row,3));row++;
		
		
		
		
		add(weaponVsAc,GBU.hPanel(0,row,4,1));
		
		
		add(new PLabel(""), GBU.label(99, 99));

	}

	protected ReturnValue<?> validateScreen() {
		{
 
		}

		return new ReturnValue("");
	}

	protected WeaponMelee newT() {
		return new WeaponMelee();
	}

	protected void populateFromScreen(WeaponMelee value, boolean includingKeys) {
		if (includingKeys) {
			value.setCode(txtCode.getText());
		}
  
		value.setCapableOfDisarmingAgainstAC8(txtCapableOfDisarmingAgainstAC8.isSelected());
		value.setCapableOfDismountingRider(txtCapableOfDismountingRider.isSelected());
		value.setCapacityGP(0);// not for weapons		
		value.setDamageL(txtLDamage.getDicePanelData());
				
		value.setDamageSM(txtSMDamage.getDicePanelData());
		
		value.setEncumberanceGP(txtWeightGP.getIntegerValue());
		
		
		txtWeaponMagic.populateFromScreen(value); 
		
		value.setLength(txtLength.getText());
		
		value.setName(txtName.getText());
		value.setNotes(txtNotes.getText());
		value.setRequiresHands(txtComboEquipmentHands.getEquipmentHands());
		value.setSpaceRequired(txtSpaceRequired.getText());
		value.setSpeedFactor(txtSpeedFactor.getIntegerValue());
		value.setTwiceDamageToLargeWhenGroundedAgainstCharge(txtTwiceDamageCharged.isSelected());
		value.setTwiceDamageWhenChargingOnMount(txtTwiceDamageCharging.isSelected());
		value.setTwiceDamageWhenGroundedAgainstCharge(txtTwiceDamageGrounded.isSelected());
		
		 
		weaponVsAc.populateFromScreen(value);
 
	}

	protected void populateScreen(WeaponMelee value) {
		txtCode.setText(value.getCode());
 
		
		txtCapableOfDisarmingAgainstAC8	.setSelected(	value.isCapableOfDisarmingAgainstAC8());
		txtCapableOfDismountingRider	.setSelected(	value.isCapableOfDismountingRider());
						 
		txtLDamage.setDicePanelData(value.getDamageL());				
		txtSMDamage.setDicePanelData(value.getDamageSM());		
		 
		txtWeightGP	.setIntegerValue(	value.getEncumberanceGP());
		
		txtWeaponMagic.populateScreen(value);
		
		txtLength	.setText(	value.getLength());
		txtName	.setText(	value.getName());
		txtNotes	.setText(	value.getNotes());
		txtComboEquipmentHands	.setEquipmentHands(	value.getRequiresHands());
		txtSpaceRequired	.setText(	value.getSpaceRequired());
		txtSpeedFactor	.setIntegerValue(	value.getSpeedFactor());
		txtTwiceDamageCharged	.setSelected(	value.isTwiceDamageToLargeWhenGroundedAgainstCharge());
		txtTwiceDamageCharging	.setSelected(	value.isTwiceDamageWhenChargingOnMount());
		txtTwiceDamageGrounded	.setSelected(	value.isTwiceDamageWhenGroundedAgainstCharge());

		 
		weaponVsAc.populateScreen(value);

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
