package com.nobbysoft.first.client.data.panels.attributes;

import java.awt.GridBagLayout;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.client.components.PIntegerCombo;
import com.nobbysoft.first.client.components.PIntegerField;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.special.PExceptionalStrength;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.data.panels.AbstractDataPanel;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.common.entities.staticdto.attributes.Strength;
import com.nobbysoft.first.common.entities.staticdto.attributes.StrengthKey;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class StrengthPanel extends AbstractDataPanel<Strength,StrengthKey> implements MaintenancePanelInterface<Strength> {
	 

		private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());


		private final PIntegerCombo txtAbilityScore = new PIntegerCombo(3,18);
		private final PExceptionalStrength txtExceptionalStrength = new PExceptionalStrength();
		private final PExceptionalStrength txtExceptionalStrengthTo = new PExceptionalStrength();
		
		private final PIntegerCombo txtHitProbability = new PIntegerCombo(-3,+3,"normal");
		private final PIntegerCombo txtDamageAdjustment = new PIntegerCombo(-1,+6,"none");
		private final PIntegerField txtWeightAllowance = new PIntegerField(true);
		private final PIntegerCombo txtOpenDoors = new PIntegerCombo(1,5);
		private final PIntegerCombo txtOpenMagicalDoors = new PIntegerCombo(0,2);
		private final PIntegerCombo txtBendBarsLiftGates = new PIntegerCombo(0,40);
		

		private PDialog parentd;
		public void setParentDialog(PDialog parentd) {
			this.parentd=parentd;
		}
	 

		
		private PDataComponent[] dataComponents = new PDataComponent[] {				
				txtExceptionalStrengthTo,txtHitProbability,txtDamageAdjustment ,
				txtWeightAllowance,txtOpenDoors,txtOpenMagicalDoors,txtBendBarsLiftGates
				 };
		private PDataComponent[] keyComponents = new PDataComponent[] { txtAbilityScore ,txtExceptionalStrength
				};
		private PDataComponent[] buttonComponents = new PDataComponent[] {  };

		private PDataComponent[] mandatoryComponents = new PDataComponent[] { txtAbilityScore, 
				/*txtExceptionalStrength ,*/
				txtHitProbability,
				txtDamageAdjustment ,
				txtWeightAllowance,
				txtOpenDoors,
				txtOpenMagicalDoors,
				txtBendBarsLiftGates};
/*
txtWeightAllowance
txtOpenDoors
txtOpenMagicalDoors
txtBendBarsLiftGates		
 */
		
	public StrengthPanel() {

		setLayout(new GridBagLayout());
		jbInit();
	}

	@Override
	protected PDataComponent[] getButtonComponents() {
		return new PDataComponent[0];
	}

	@Override
	protected DataServiceI<?, ?> getDataService() {
		DataServiceI dao;
		try {
		Class d = DataMapper.INSTANCE.getServiceForEntity(Strength.class); 
			Constructor<DataServiceI> cc = d.getConstructor();
				dao = (DataServiceI) cc.newInstance();
			} catch (Exception e) {
				throw new IllegalStateException("Can't get Service for "+Strength.class);
			}
		return dao;
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
	protected PDataComponent[] getMandatoryComponents() {
		return mandatoryComponents;
	}

	@Override
	protected void populateCombos() {
		// n/a
		
	}

	@Override
	protected void populateFromScreen(Strength value, boolean includingKeys) {
		value.setExceptionalStrengthTo(txtExceptionalStrengthTo.getExceptionalStrength());
		value.setAbilityScore(txtAbilityScore.getIntegerValue());
		value.setExceptionalStrength(txtExceptionalStrength.getExceptionalStrength());
		value.setHitProbability(txtHitProbability.getIntegerValue());
		value.setDamageAdjustment(txtDamageAdjustment.getIntegerValue());
		value.setWeightAllowance(txtWeightAllowance.getIntegerValue());
		value.setOpenDoors(txtOpenDoors.getIntegerValue());
		value.setOpenMagicalDoors(txtOpenMagicalDoors.getIntegerValue());
		value.setBendBarsLiftGates(txtBendBarsLiftGates.getIntegerValue());
		
	}

	@Override
	protected void populateScreen(Strength value) {
		txtAbilityScore.setIntegerValue(value.getAbilityScore()); 
		txtExceptionalStrength.setExceptionalStrength(value.getExceptionalStrength());
		txtExceptionalStrengthTo.setExceptionalStrength(value.getExceptionalStrengthTo());
		txtHitProbability .setIntegerValue(value.getHitProbability());
		txtDamageAdjustment .setIntegerValue(value.getDamageAdjustment());
		txtWeightAllowance.setIntegerValue(value.getWeightAllowance());
		txtOpenDoors.setIntegerValue(value.getOpenDoors());
		txtOpenMagicalDoors.setIntegerValue(value.getOpenMagicalDoors());
		txtBendBarsLiftGates.setIntegerValue(value.getBendBarsLiftGates());
		
		

		
	}

	@Override
	protected ReturnValue<?> validateScreen() {
		// TODO Auto-generated method stub
		return new ReturnValue<>("");
	}

	@Override
	protected Strength newT() {
		// TODO Auto-generated method stub
		return new Strength ();
	}

	public void jbInit() {

		txtAbilityScore.setName("Ability Score");  		
		txtExceptionalStrength .setName("Exceptional strength (from)");
		txtExceptionalStrengthTo.setName("Exceptional strength (to)");
		txtHitProbability.setName("Hit probability");
		txtDamageAdjustment .setName("Damage adjustment");		
		
		txtWeightAllowance.setName("Weight allowance (gp)");
		txtOpenDoors.setName("Open Doors (1-x)");
		txtOpenMagicalDoors.setName("Open Magical Doors (1-x)");
		txtBendBarsLiftGates.setName("Bend Bars/Lift Gates(%)");
		
		PLabel lblAbilityScore            = new PLabel(txtAbilityScore.getName());  
		PLabel lblExceptionalStrength      = new PLabel(txtExceptionalStrength.getName()); 
		PLabel lblExceptionalStrengthTo    = new PLabel(txtExceptionalStrengthTo.getName()); 
		PLabel lblHitProbability   = new PLabel(txtHitProbability.getName()); 
		PLabel lblDamageAdjustment     = new PLabel(txtDamageAdjustment.getName()); 
		PLabel lblWeightAllowance = new PLabel(txtWeightAllowance.getName()); 
		PLabel lblOpenDoors= new PLabel(txtOpenDoors.getName()); 
		PLabel lblOpenMagicalDoors= new PLabel(txtOpenMagicalDoors.getName()); 
		PLabel lblBendBarsLiftGates    = new PLabel(txtBendBarsLiftGates.getName()); 
		
		int row = 0;
		
		add(lblAbilityScore, GBU.label(0, row));
		add(txtAbilityScore, GBU.text(1, row));
		row++;


		add(lblExceptionalStrength, GBU.label(0, row));
		add(txtExceptionalStrength, GBU.text(1, row));
		add(new PLabel(" - "), GBU.label(2, row));
		add(lblExceptionalStrengthTo, GBU.label(3, row));
		add(txtExceptionalStrengthTo, GBU.text(4, row));
		row++;

		
		add(lblHitProbability, GBU.label(0, row));
		add(txtHitProbability, GBU.text(1, row));
		row++;

		
		add(lblDamageAdjustment, GBU.label(0, row));
		add(txtDamageAdjustment, GBU.text(1, row));
		row++;

		/*
		PLabel lblWeightAllowance = new PLabel(txtWeightAllowance.getName()); 
		PLabel lblOpenDoors= new PLabel(txtOpenDoors.getName()); 
		PLabel lblOpenMagicalDoors= new PLabel(txtOpenMagicalDoors.getName()); 
		PLabel lblBendBardLiftGates    = new PLabel(txtBendBarsLiftGates.getName());  
		 */

		add(lblWeightAllowance, GBU.label(0, row));
		add(txtWeightAllowance, GBU.text(1, row));
		row++;
		add(lblOpenDoors, GBU.label(0, row));
		add(txtOpenDoors, GBU.text(1, row));
		row++;
		add(lblOpenMagicalDoors, GBU.label(0, row));
		add(txtOpenMagicalDoors, GBU.text(1, row));
		row++;
		add(lblBendBarsLiftGates, GBU.label(0, row));
		add(txtBendBarsLiftGates, GBU.text(1, row));
		row++;

		add(new PLabel(""),GBU.label(99,99));
		
	}
	
}
