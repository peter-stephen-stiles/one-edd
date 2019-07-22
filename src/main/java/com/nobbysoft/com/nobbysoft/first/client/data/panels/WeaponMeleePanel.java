package com.nobbysoft.com.nobbysoft.first.client.data.panels;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.lang.invoke.MethodHandles;

import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.com.nobbysoft.first.client.components.PCheckBox;
import com.nobbysoft.com.nobbysoft.first.client.components.PCodeField;
import com.nobbysoft.com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.com.nobbysoft.first.client.components.PIntegerCombo;
import com.nobbysoft.com.nobbysoft.first.client.components.PIntegerField;
import com.nobbysoft.com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.com.nobbysoft.first.client.components.PTextField;
import com.nobbysoft.com.nobbysoft.first.client.components.special.PComboDICE;
import com.nobbysoft.com.nobbysoft.first.client.components.special.PComboEquipmentHands;
import com.nobbysoft.com.nobbysoft.first.client.components.special.PWeaponVsAc;
import com.nobbysoft.com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.WeaponMelee;
import com.nobbysoft.com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class WeaponMeleePanel extends AbstractDataPanel<WeaponMelee, String> implements MaintenancePanelInterface<WeaponMelee> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public WeaponMeleePanel() {
		setLayout(new GridBagLayout());
		jbInit();
	}

	@Override
	DataServiceI<?, ?> getDataService() {   
		DataServiceI dao  = DataMapper.INSTANCE.getDataService(WeaponMelee.class);
		return dao;
		
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
	private final PComboDICE txtSMDice = new PComboDICE();
	private final PIntegerCombo txtSMDiceCount = new PIntegerCombo(1,4);
	private final PComboDICE txtLDice = new PComboDICE();
	private final PIntegerCombo txtLDiceCount = new PIntegerCombo(1,4);
	private final PTextField txtNotes = new PTextField(255);
	
	private final PCheckBox txtTwiceDamageCharged = new PCheckBox("Does twice damage to charging opponents when grounded");
	private final PCheckBox txtTwiceDamageCharging = new PCheckBox("Does twice damage when charging");
	private final PCheckBox txtTwiceDamageGrounded = new PCheckBox("Does twice damage when grounded");
	

	private final PTextField txtLength = new PTextField(20);
	private final PTextField txtSpaceRequired = new PTextField(20);
	private final PIntegerCombo txtSpeedFactor = new PIntegerCombo(1,10);
	
	private final PComboEquipmentHands txtComboEquipmentHands = new PComboEquipmentHands();
	
	private final PCheckBox txtCapableOfDismountingRider = new PCheckBox("Capable of dismounting target rider");
	private final PCheckBox txtCapableOfDisarmingAgainstAC8 = new PCheckBox("Capable of disarming on AC8 hit");
 
	private PIntegerCombo txtMagicBonus = new PIntegerCombo(-5,+5,true);
	private PIntegerCombo txtExtraMagicBonus = new PIntegerCombo(-5,+5,true);
	private final PTextField txtExtraMagicCondition = new PTextField(255);
 
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
			 txtMagicBonus ,
			 txtExtraMagicBonus ,
			 txtExtraMagicCondition,
			 };
	private PDataComponent[] keyComponents = new PDataComponent[] { txtCode };

	private PDataComponent[] mandatoryComponents = new PDataComponent[] { txtCode,   txtName,  txtWeightGP, };

	private PDataComponent[] buttonComponents = new PDataComponent[] {};

	
	public void jbInit() {

		txtCode.setName("Id");
 
		txtName.setName("Name");
		txtWeightGP.setName("Weight (gp)");
		txtSMDice.setName("Damage (S/M)");
		txtLDice.setName("Damage (L)");
		txtNotes.setName("Notes");
		txtLength.setName("Length");
		txtSpaceRequired.setName("Space required (feet)");
		txtSpeedFactor.setName("Speed factor");
		txtComboEquipmentHands.setName("Requires hands");
		txtMagicBonus .setName("Bonus");
		txtExtraMagicBonus .setName("(Extra bonus");
		txtExtraMagicCondition.setName("for");

		PLabel lblCode = new PLabel(txtCode.getName() );

		PLabel lblName = new PLabel(txtName.getName() );
		PLabel lblWeightGP = new PLabel(txtWeightGP.getName() );
		PLabel lblDamageSM = new PLabel(txtSMDice.getName() );
		PLabel lblDamageL = new PLabel(txtLDice.getName() );
		PLabel lblNotes = new PLabel(txtNotes.getName());
		PLabel lblLength = new PLabel(txtLength.getName());
		PLabel lblSpaceRequired = new PLabel(txtSpaceRequired.getName());
		PLabel lblSpeedFactor = new PLabel(txtSpeedFactor.getName());
		PLabel lblComboEquipmentHands = new PLabel(txtComboEquipmentHands.getName());
		
		PLabel lblMagicBonus = new PLabel(txtMagicBonus.getName());
		PLabel lblExtraMagicBonus = new PLabel(txtExtraMagicBonus.getName());
		PLabel lblExtraMagicCondition = new PLabel(txtExtraMagicCondition.getName());
		
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
		add(txtSMDiceCount, GBU.text(1, row));
		add(txtSMDice, GBU.text(2, row));
		row++;
		
		add(lblDamageL, GBU.label(0, row));
		add(txtLDiceCount, GBU.text(1, row));
		add(txtLDice, GBU.text(2, row));
		row++;
		
		PPanel pnlMagic = new PPanel(new GridBagLayout());
		pnlMagic.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		pnlMagic.add(lblMagicBonus,GBU.label(0, 0));
		pnlMagic.add(txtMagicBonus,GBU.text(1, 0));
		pnlMagic.add(lblExtraMagicBonus,GBU.label(2, 0));
		pnlMagic.add(txtExtraMagicBonus,GBU.text(3, 0));
		pnlMagic.add(lblExtraMagicCondition,GBU.label(4, 0));
		pnlMagic.add(txtExtraMagicCondition,GBU.text(5, 0));
		

		add(pnlMagic,GBU.hPanel(0,row,4,1));
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

	ReturnValue<?> validateScreen() {
		{
 
		}

		return new ReturnValue("");
	}

	WeaponMelee newT() {
		return new WeaponMelee();
	}

	void populateFromScreen(WeaponMelee value, boolean includingKeys) {
		if (includingKeys) {
			value.setCode(txtCode.getText());
		}
  
		value.setCapableOfDisarmingAgainstAC8(txtCapableOfDisarmingAgainstAC8.isSelected());
		value.setCapableOfDismountingRider(txtCapableOfDismountingRider.isSelected());
		value.setCapacityGP(0);// not for weapons 
		value.setDamageLDICE(txtLDice.getDICE());
		value.setDamageLDiceCount(txtLDiceCount.getIntegerValue());
		value.setDamageSMDICE(txtSMDice.getDICE());
		value.setDamageSMDiceCount(txtSMDiceCount.getIntegerValue());
		value.setEncumberanceGP(txtWeightGP.getIntegerValue());
		value.setExtraMagicBonus(txtExtraMagicBonus.getIntegerValue());
		value.setExtraMagicCondition(txtExtraMagicCondition.getText());
		value.setLength(txtLength.getText());
		value.setMagicBonus(txtMagicBonus.getIntegerValue());
		value.setName(txtName.getText());
		value.setNotes(txtNotes.getText());
		value.setRequiresHands(txtComboEquipmentHands.getEquipmentHands());
		value.setSpaceRequired(txtSpaceRequired.getText());
		value.setSpeedFactor(txtSpeedFactor.getIntegerValue());
		value.setTwiceDamageToLargeWhenGroundedAgainstCharge(txtTwiceDamageCharged.isSelected());
		value.setTwiceDamageWhenChargingOnMount(txtTwiceDamageCharging.isSelected());
		value.setTwiceDamageWhenGroundedAgainstCharge(txtTwiceDamageGrounded.isSelected());
		
		 
		weaponVsAc.setFromScreen(value);
 
	}

	void populateScreen(WeaponMelee value) {
		txtCode.setText(value.getCode());
 
		
		txtCapableOfDisarmingAgainstAC8	.setSelected(	value.isCapableOfDisarmingAgainstAC8());
		txtCapableOfDismountingRider	.setSelected(	value.isCapableOfDismountingRider());
		
		txtLDice	.setDICE(	value.getDamageLDICE());
		txtLDiceCount	.setIntegerValue(	value.getDamageLDiceCount());
		txtSMDice	.setDICE(	value.getDamageSMDICE());
		txtSMDiceCount	.setIntegerValue(	value.getDamageSMDiceCount());
		txtWeightGP	.setIntegerValue(	value.getEncumberanceGP());
		txtExtraMagicBonus	.setIntegerValue(	value.getExtraMagicBonus());
		txtExtraMagicCondition	.setText(	value.getExtraMagicCondition());
		txtLength	.setText(	value.getLength());
		txtMagicBonus	.setIntegerValue(	value.getMagicBonus());
		txtName	.setText(	value.getName());
		txtNotes	.setText(	value.getNotes());
		txtComboEquipmentHands	.setEquipmentHands(	value.getRequiresHands());
		txtSpaceRequired	.setText(	value.getSpaceRequired());
		txtSpeedFactor	.setIntegerValue(	value.getSpeedFactor());
		txtTwiceDamageCharged	.setSelected(	value.isTwiceDamageToLargeWhenGroundedAgainstCharge());
		txtTwiceDamageCharging	.setSelected(	value.isTwiceDamageWhenChargingOnMount());
		txtTwiceDamageGrounded	.setSelected(	value.isTwiceDamageWhenGroundedAgainstCharge());

		 
		weaponVsAc.setScreen(value);

	}

	void populateCombos() {

		try {
 
		} catch (Exception ex) {
			Popper.popError(GuiUtils.getParent(this), ex);

		}
	}

	@Override
	PDataComponent[] getDataComponents() {
		return dataComponents;
	}

	@Override
	PDataComponent[] getKeyComponents() {
		return keyComponents;
	}

	@Override
	PDataComponent[] getButtonComponents() {
		return buttonComponents;
	}

	@Override
	PDataComponent[] getMandatoryComponents() {
		return mandatoryComponents;
	}

}
