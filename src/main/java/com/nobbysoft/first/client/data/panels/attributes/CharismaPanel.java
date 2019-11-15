package com.nobbysoft.first.client.data.panels.attributes;

import java.awt.Dimension;
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
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.data.panels.AbstractDataPanel;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.common.entities.staticdto.attributes.Charisma;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class CharismaPanel extends AbstractDataPanel<Charisma,Integer> implements MaintenancePanelInterface<Charisma> {
	 

		private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
 
		private final PIntegerCombo txtAbilityScore = new PIntegerCombo(3,18);
		
		private final PIntegerCombo txtMaxHenchmen = new PIntegerCombo(1,15);
		private final PIntegerCombo txtLoyaltyBase = new PIntegerCombo(-30,+40,"normal");
		private final PIntegerCombo txtReactionAdjustment = new PIntegerCombo(-25,+35,"normal")  {
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
					d.width = d.width+20;
					return d;				
			}
		};  
		
 
		private PDataComponent[] dataComponents = new PDataComponent[] {  				
				txtMaxHenchmen ,
				txtLoyaltyBase,txtReactionAdjustment ,
				 
				 };
		private PDataComponent[] keyComponents = new PDataComponent[] { 
				txtAbilityScore 
				};
		private PDataComponent[] buttonComponents = new PDataComponent[] {  };

		private PDataComponent[] mandatoryComponents = new PDataComponent[] { 
				txtAbilityScore, 
				txtMaxHenchmen ,
				txtLoyaltyBase};
		

		private PDialog parentd;
		public void setParentDialog(PDialog parentd) {
			this.parentd=parentd;
		}
	 

	public CharismaPanel() {

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
		Class d = DataMapper.INSTANCE.getServiceForEntity(Charisma.class); 
			Constructor<DataServiceI> cc = d.getConstructor();
				dao = (DataServiceI) cc.newInstance();
			} catch (Exception e) {
				throw new IllegalStateException("Can't get Service for "+Charisma.class);
			}
		return dao;
	}

	@Override
	protected PDataComponent[] getDataComponents() {
		return dataComponents;
	}

	@Override
	protected 	PDataComponent[] getKeyComponents() {
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
	protected void populateFromScreen(Charisma value, boolean includingKeys) {
		value.setAbilityScore(txtAbilityScore.getIntegerValue());
		value.setMaxHenchmen(txtMaxHenchmen.getIntegerValue());
		value.setLoyaltyBase(txtLoyaltyBase.getIntegerValue());
		value.setReactionAdjustment(txtReactionAdjustment.getIntegerValue());
 
	}

	@Override
	protected void populateScreen(Charisma value) {
		txtAbilityScore.setIntegerValue(value.getAbilityScore()); 
		txtMaxHenchmen .setIntegerValue(value.getMaxHenchmen());
		txtLoyaltyBase .setIntegerValue(value.getLoyaltyBase());
		txtReactionAdjustment.setIntegerValue(value.getReactionAdjustment());
 
	}

	@Override
	protected ReturnValue<?> validateScreen() {
		// TODO Auto-generated method stub
		return new ReturnValue<>("");
	}

	@Override
	protected Charisma newT() {
		// TODO Auto-generated method stub
		return new Charisma ();
	}

	public void jbInit() {

		txtAbilityScore.setName("Ability Score");  		
		txtMaxHenchmen .setName("Max henchmen");
		txtLoyaltyBase.setName("Loyalty base");
		txtReactionAdjustment .setName("Reactin adjustment"); 
		
		PLabel lblAbilityScore            = new PLabel(txtAbilityScore.getName());  
		PLabel lblMaxHenchmen      = new PLabel(txtMaxHenchmen.getName()); 
		PLabel lblLoyaltyBase   = new PLabel(txtLoyaltyBase.getName()); 
		PLabel lblReactionAdjustment     = new PLabel(txtReactionAdjustment.getName());  
		
		int row = 0;
		
		add(lblAbilityScore, GBU.label(0, row));
		add(txtAbilityScore, GBU.text(1, row));
		row++;

		
		add(lblMaxHenchmen, GBU.label(0, row));
		add(txtMaxHenchmen, GBU.text(1, row));
		row++;

		
		add(lblLoyaltyBase, GBU.label(0, row));
		add(txtLoyaltyBase, GBU.text(1, row));
		row++;

		
		add(lblReactionAdjustment, GBU.label(0, row));
		add(txtReactionAdjustment, GBU.text(1, row));
		row++;

		
 

		
		
		add(new PLabel(""),GBU.label(99,99));
		
	}
	
}
