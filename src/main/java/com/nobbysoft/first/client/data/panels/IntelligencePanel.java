package com.nobbysoft.first.client.data.panels;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PIntegerCombo;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.common.entities.staticdto.attributes.Intelligence;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

public class IntelligencePanel extends AbstractDataPanel<Intelligence,Integer> implements MaintenancePanelInterface<Intelligence> {
	 

		private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());


		private final PIntegerCombo txtAbilityScore = new PIntegerCombo(3,19);
		private final PIntegerCombo txtPossibleAdditionalLanguages = new PIntegerCombo(0,7);
		private final PIntegerCombo txtChanceToKnowSpell = new PIntegerCombo(0,95,5);
		private final PIntegerCombo txtMinSpellsPerLevel = new PIntegerCombo(0,99);
		private final PIntegerCombo txtMaxSpellsPerLevel = new PIntegerCombo(0,99) {
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
					d.width = d.width+20;
					return d;				
			}
		};
				
		private PDataComponent[] dataComponents = new PDataComponent[] {  				txtPossibleAdditionalLanguages ,
				txtChanceToKnowSpell,txtMinSpellsPerLevel ,
				txtMaxSpellsPerLevel
				 };
		private PDataComponent[] keyComponents = new PDataComponent[] { txtAbilityScore 
				};
		private PDataComponent[] buttonComponents = new PDataComponent[] {  };

		private PDataComponent[] mandatoryComponents = new PDataComponent[] { txtAbilityScore, 
				txtPossibleAdditionalLanguages ,
				txtChanceToKnowSpell,
				txtMinSpellsPerLevel ,
				txtMaxSpellsPerLevel};
		
		
	public IntelligencePanel() {

		setLayout(new GridBagLayout());
		jbInit();
	}

	@Override
	PDataComponent[] getButtonComponents() {
		return new PDataComponent[0];
	}

	@Override
	DataServiceI<?, ?> getDataService() {
		DataServiceI dao;
		try {
		Class d = DataMapper.INSTANCE.getServiceForEntity(Intelligence.class); 
			Constructor<DataServiceI> cc = d.getConstructor();
				dao = (DataServiceI) cc.newInstance();
			} catch (Exception e) {
				throw new IllegalStateException("Can't get Service for "+Intelligence.class);
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
	void populateFromScreen(Intelligence value, boolean includingKeys) {
		value.setAbilityScore(txtAbilityScore.getIntegerValue());
		value.setPossibleAdditionalLanguages(txtPossibleAdditionalLanguages.getIntegerValue());
		value.setChanceToKnowSpell(txtChanceToKnowSpell.getIntegerValue());
		value.setMinSpellsPerLevel(txtMinSpellsPerLevel.getIntegerValue());
		value.setMaxSpellsPerLevel(txtMaxSpellsPerLevel.getIntegerValue());
		
	}

	@Override
	void populateScreen(Intelligence value) {
		txtAbilityScore.setIntegerValue(value.getAbilityScore()); 
		txtPossibleAdditionalLanguages .setIntegerValue(value.getPossibleAdditionalLanguages());
		txtChanceToKnowSpell .setIntegerValue(value.getChanceToKnowSpell());
		txtMinSpellsPerLevel .setIntegerValue(value.getMinSpellsPerLevel());
		txtMaxSpellsPerLevel.setIntegerValue(value.getMaxSpellsPerLevel());
		
	}

	@Override
	ReturnValue<?> validateScreen() { 
		int min = txtMinSpellsPerLevel.getIntegerValue();
		int max = txtMaxSpellsPerLevel.getIntegerValue();
		if(max<min) {
			txtMinSpellsPerLevel.requestFocusInWindow();
			return new ReturnValue(ReturnValue.IS_ERROR.TRUE,"MIN spells per level must be less than MAX spells per level");
		}
		return new ReturnValue<>("");
	}

	@Override
	Intelligence newT() {
		// TODO Auto-generated method stub
		return new Intelligence ();
	}

	public void jbInit() {

		txtAbilityScore.setName("Ability Score");  		
		txtPossibleAdditionalLanguages .setName("Possible Additional Languages");
		txtChanceToKnowSpell.setName("MU: Chance to know spell");
		txtMinSpellsPerLevel .setName("MU: Min spells per level");
		txtMaxSpellsPerLevel.setName("MU: Max spells per level");
		
		PLabel lblAbilityScore            = new PLabel(txtAbilityScore.getName());  
		PLabel lblPossibleAdditionalLanguages      = new PLabel(txtPossibleAdditionalLanguages.getName()); 
		PLabel lblChanceToKnowSpell   = new PLabel(txtChanceToKnowSpell.getName()); 
		PLabel lblMinSpellsPerLevel     = new PLabel(txtMinSpellsPerLevel.getName()); 
		PLabel lblMaxSpellsPerLevel    = new PLabel(txtMaxSpellsPerLevel.getName()); 
		
		int row = 0;
		
		add(lblAbilityScore, GBU.label(0, row));
		add(txtAbilityScore, GBU.text(1, row));
		row++;

		
		add(lblPossibleAdditionalLanguages, GBU.label(0, row));
		add(txtPossibleAdditionalLanguages, GBU.text(1, row));
		row++;

		
		add(lblChanceToKnowSpell, GBU.label(0, row));
		add(txtChanceToKnowSpell, GBU.text(1, row));
		row++;

		
		add(lblMinSpellsPerLevel, GBU.label(0, row));
		add(txtMinSpellsPerLevel, GBU.text(1, row));
		row++;

		
		add(lblMaxSpellsPerLevel, GBU.label(0, row));
		add(txtMaxSpellsPerLevel, GBU.text(1, row));
		row++;

		add(new PLabel(""),GBU.label(99,99));
		
	}
	
}
