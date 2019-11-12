package com.nobbysoft.first.client.data.panels;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.client.components.PIntegerCombo;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.common.entities.staticdto.attributes.Constitution;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

public class ConstitutionPanel extends AbstractDataPanel<Constitution,Integer> implements MaintenancePanelInterface<Constitution> {
	 

		private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());


		private final PIntegerCombo txtAbilityScore = new PIntegerCombo(3,18);
		private final PIntegerCombo txtHitPointAdjustment = new PIntegerCombo(-2,+2);
		private final PIntegerCombo txtHitPointAdjustmentHigh = new PIntegerCombo(-2,+4);
		private final PIntegerCombo txtSystemShockSurvival = new PIntegerCombo(35,99);
		private final PIntegerCombo txtResurrectionSurvival = new PIntegerCombo(40,100) {
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
					d.width = d.width+20;
					return d;				
			}
		};
		 
		
		private PDataComponent[] dataComponents = new PDataComponent[] {  				txtHitPointAdjustment ,
				txtHitPointAdjustmentHigh,txtSystemShockSurvival ,
				txtResurrectionSurvival 
				 };
		private PDataComponent[] keyComponents = new PDataComponent[] { txtAbilityScore 
				};
		private PDataComponent[] buttonComponents = new PDataComponent[] {  };

		private PDataComponent[] mandatoryComponents = new PDataComponent[] { txtAbilityScore, 
				txtHitPointAdjustment ,
				txtHitPointAdjustmentHigh,
				txtSystemShockSurvival ,
				txtResurrectionSurvival};
		
		
	public ConstitutionPanel() {

		setLayout(new GridBagLayout());
		jbInit();
	}

	private PDialog parentd;
	public void setParentDialog(PDialog parentd) {
		this.parentd=parentd;
	}
 

	@Override
	PDataComponent[] getButtonComponents() {
		return new PDataComponent[0];
	}

	@Override
	DataServiceI<?, ?> getDataService() {
		DataServiceI dao;
		try {
		Class d = DataMapper.INSTANCE.getServiceForEntity(Constitution.class); 
			Constructor<DataServiceI> cc = d.getConstructor();
				dao = (DataServiceI) cc.newInstance();
			} catch (Exception e) {
				throw new IllegalStateException("Can't get Service for "+Constitution.class);
			}
		return dao;
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
	PDataComponent[] getMandatoryComponents() {
		return mandatoryComponents;
	}

	@Override
	void populateCombos() {
		// n/a
		
	}

	@Override
	void populateFromScreen(Constitution value, boolean includingKeys) {
		value.setAbilityScore(txtAbilityScore.getIntegerValue());
		value.setHitPointAdjustment(txtHitPointAdjustment.getIntegerValue());
		value.setHitPointAdjustmentHigh(txtHitPointAdjustmentHigh.getIntegerValue());
		value.setSystemShockSurvival(txtSystemShockSurvival.getIntegerValue());
		value.setResurrectionSurvival(txtResurrectionSurvival.getIntegerValue()); 
		
	}

	@Override
	void populateScreen(Constitution value) {
		txtAbilityScore.setIntegerValue(value.getAbilityScore()); 
		txtHitPointAdjustment .setIntegerValue(value.getHitPointAdjustment());
		txtHitPointAdjustmentHigh .setIntegerValue(value.getHitPointAdjustmentHigh());
		txtSystemShockSurvival .setIntegerValue(value.getSystemShockSurvival());
		txtResurrectionSurvival.setIntegerValue(value.getResurrectionSurvival());
 
		
	}

	@Override
	ReturnValue<?> validateScreen() {
		// TODO Auto-generated method stub
		return new ReturnValue<>("");
	}

	@Override
	Constitution newT() {
		// TODO Auto-generated method stub
		return new Constitution ();
	}

	public void jbInit() {

		txtAbilityScore.setName("Ability Score");  		
		txtHitPointAdjustment .setName("Hit point adjustment");
		txtHitPointAdjustmentHigh.setName("Hit point adjustment (high)");
		txtSystemShockSurvival .setName("System shock survival");
		txtResurrectionSurvival.setName("Resurrection survival");
 
		
		PLabel lblAbilityScore            = new PLabel(txtAbilityScore.getName());  
		PLabel lblHitPointAdjustment      = new PLabel(txtHitPointAdjustment.getName()); 
		PLabel lblHitPointAdjustmentHigh   = new PLabel(txtHitPointAdjustmentHigh.getName()); 
		PLabel lblSystemShockSurvival     = new PLabel(txtSystemShockSurvival.getName()); 
		PLabel lblResurrectionSurvival    = new PLabel(txtResurrectionSurvival.getName()); 
		 
		
		
		int row = 0;
		
		add(lblAbilityScore, GBU.label(0, row));
		add(txtAbilityScore, GBU.text(1, row));
		row++;

		
		add(lblHitPointAdjustment, GBU.label(0, row));
		add(txtHitPointAdjustment, GBU.text(1, row));
		row++;

		
		add(lblHitPointAdjustmentHigh, GBU.label(0, row));
		add(txtHitPointAdjustmentHigh, GBU.text(1, row));
		row++;

		
		add(lblSystemShockSurvival, GBU.label(0, row));
		add(txtSystemShockSurvival, GBU.text(1, row));
		row++;

		
		add(lblResurrectionSurvival, GBU.label(0, row));
		add(txtResurrectionSurvival, GBU.text(1, row));
		row++;

		
	 		
		
		add(new PLabel(""),GBU.label(99,99));
		
	}
	
}
