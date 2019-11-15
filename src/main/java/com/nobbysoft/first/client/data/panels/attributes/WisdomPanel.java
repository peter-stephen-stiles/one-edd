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
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.data.panels.AbstractDataPanel;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.common.entities.staticdto.attributes.Wisdom;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class WisdomPanel extends AbstractDataPanel<Wisdom,Integer> implements MaintenancePanelInterface<Wisdom> {
	 

		private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());


		private final PIntegerCombo txtAbilityScore = new PIntegerCombo(3,18);

		private final PIntegerCombo txtMagicalAttackAdjustment = new PIntegerCombo(-3,+4,"none") {
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
					d.width = d.width+20;
					return d;				
			}
		};
		
		private final PIntegerCombo txtDivineSpellBonusSpellLevel = new PIntegerCombo(0,4,"");
		private final PIntegerCombo txtDivineMaxSpellLevel = new PIntegerCombo(0,7,"");
		private final PIntegerCombo txtDivineSpellChanceFailure = new PIntegerCombo(0,99);
		{
			
		}

		
		private PDataComponent[] dataComponents = new PDataComponent[] { txtMagicalAttackAdjustment ,
				
				txtDivineSpellBonusSpellLevel,
				txtDivineMaxSpellLevel,
				txtDivineSpellChanceFailure
				 };
		private PDataComponent[] keyComponents = new PDataComponent[] { txtAbilityScore 
				};
		private PDataComponent[] buttonComponents = new PDataComponent[] {  };

		private PDataComponent[] mandatoryComponents = new PDataComponent[] { txtAbilityScore};
		
		
	public WisdomPanel() {

		setLayout(new GridBagLayout());
		jbInit();
	}

	private PDialog parentd;
	public void setParentDialog(PDialog parentd) {
		this.parentd=parentd;
	}
 

	@Override
	protected PDataComponent[] getButtonComponents() {
		return new PDataComponent[0];
	}

	@Override
	protected DataServiceI<?, ?> getDataService() {
		DataServiceI dao;
		try {
		Class d = DataMapper.INSTANCE.getServiceForEntity(Wisdom.class); 
			Constructor<DataServiceI> cc = d.getConstructor();
				dao = (DataServiceI) cc.newInstance();
			} catch (Exception e) {
				throw new IllegalStateException("Can't get Service for "+Wisdom.class);
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
	protected void populateFromScreen(Wisdom value, boolean includingKeys) {
		value.setAbilityScore(txtAbilityScore.getIntegerValue());
		value.setMagicalAttackAdjustment(txtMagicalAttackAdjustment.getIntegerValue());
		value.setDivineSpellBonusSpellLevel(txtDivineSpellBonusSpellLevel.getIntegerValue());
		value.setDivineMaxSpellLevel(txtDivineMaxSpellLevel.getIntegerValue());
		value.setDivineSpellChanceFailure(txtDivineSpellChanceFailure.getIntegerValue());
		
	}

	@Override
	protected void populateScreen(Wisdom value) {
		txtAbilityScore.setIntegerValue(value.getAbilityScore()); 

		txtMagicalAttackAdjustment.setIntegerValue(value.getMagicalAttackAdjustment());

		txtDivineSpellBonusSpellLevel.setIntegerValue(value.getDivineSpellBonusSpellLevel());
		txtDivineMaxSpellLevel.setIntegerValue(value.getDivineMaxSpellLevel());
		txtDivineSpellChanceFailure.setIntegerValue(value.getDivineSpellChanceFailure());
		
	}

	@Override
	protected ReturnValue<?> validateScreen() {
		// TODO Auto-generated method stub
		return new ReturnValue<>("");
	}

	@Override
	protected Wisdom newT() {
		// TODO Auto-generated method stub
		return new Wisdom ();
	}

	public void jbInit() {

		txtAbilityScore.setName("Ability Score");  		
		txtMagicalAttackAdjustment .setName("Magical attack adjustment");
		

		//txtDivineSpellBonusSpellLevel, txtDivineMaxSpellLevel,		txtDivineSpellChanceFailure
/*
 "Spell Bonus (lvl)",
				"Spell Failure %",
				"Max Spell Lvl" 
 */
		txtDivineSpellBonusSpellLevel.setName("Divine bonus spell (lvl)");
		txtDivineMaxSpellLevel.setName("Divine max spell Lvl");
		txtDivineSpellChanceFailure.setName("Divine spell failure %");
		
		PLabel lblAbilityScore            = new PLabel(txtAbilityScore.getName());  
		PLabel lblMagicalAttackAdjustment      = new PLabel(txtMagicalAttackAdjustment.getName());
 
		
		PLabel lblDivineSpellBonusSpellLevel   = new PLabel(txtDivineSpellBonusSpellLevel.getName()); 
		PLabel lblDivineMaxSpellLevel          = new PLabel(txtDivineMaxSpellLevel       .getName()); 
		PLabel lblDivineSpellChanceFailure     = new PLabel(txtDivineSpellChanceFailure  .getName()); 
		
		
		int row = 0;
		
		add(lblAbilityScore, GBU.label(0, row));
		add(txtAbilityScore, GBU.text(1, row));
		row++;

		
		add(lblMagicalAttackAdjustment, GBU.label(0, row));
		add(txtMagicalAttackAdjustment, GBU.text(1, row));
		row++;

	 

		
		add(lblDivineSpellBonusSpellLevel, GBU.label(0, row));
		add(txtDivineSpellBonusSpellLevel, GBU.text(1, row));
		row++;		
		
		add(lblDivineMaxSpellLevel, GBU.label(0, row));
		add(txtDivineMaxSpellLevel, GBU.text(1, row));
		row++;

		add(lblDivineSpellChanceFailure, GBU.label(0, row));
		add(txtDivineSpellChanceFailure, GBU.text(1, row));
		row++;		
		
		add(new PLabel(""),GBU.label(99,99));
		
	}
	
}
