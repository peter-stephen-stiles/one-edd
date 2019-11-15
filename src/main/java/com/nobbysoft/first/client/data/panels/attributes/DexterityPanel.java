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
import com.nobbysoft.first.common.entities.staticdto.attributes.Dexterity;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class DexterityPanel extends AbstractDataPanel<Dexterity,Integer> implements MaintenancePanelInterface<Dexterity> {
	 

		private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
 
		private final PIntegerCombo txtAbilityScore = new PIntegerCombo(3,18);
		private final PIntegerCombo txtReactionAttackAdjustment = new PIntegerCombo(-3,+3);
		private final PIntegerCombo txtDefensiveAdjustment = new PIntegerCombo(-4,+4);
		private final PIntegerField txtPickPockets = new PIntegerField(true);
		private final PIntegerField txtOpenLocks = new PIntegerField(true) {
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
					d.width = d.width+20;
					return d;				
			}
		}; 
		private final PIntegerField txtLocateRemoveTraps = new PIntegerField(true);
		private final PIntegerField txtMoveSilently = new PIntegerField(true);
		private final PIntegerField txtHideInShadows = new PIntegerField(true);
		
 
		private PDataComponent[] dataComponents = new PDataComponent[] {  				
				txtReactionAttackAdjustment ,
				txtDefensiveAdjustment,txtPickPockets ,
				txtOpenLocks,
				txtLocateRemoveTraps,		 txtMoveSilently,txtHideInShadows
				 };
		private PDataComponent[] keyComponents = new PDataComponent[] { 
				txtAbilityScore 
				};
		private PDataComponent[] buttonComponents = new PDataComponent[] {  };

		private PDataComponent[] mandatoryComponents = new PDataComponent[] { 
				txtAbilityScore, 
				txtReactionAttackAdjustment ,
				txtDefensiveAdjustment};
		

		private PDialog parentd;
		public void setParentDialog(PDialog parentd) {
			this.parentd=parentd;
		}
	 

	public DexterityPanel() {

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
		Class d = DataMapper.INSTANCE.getServiceForEntity(Dexterity.class); 
			Constructor<DataServiceI> cc = d.getConstructor();
				dao = (DataServiceI) cc.newInstance();
			} catch (Exception e) {
				throw new IllegalStateException("Can't get Service for "+Dexterity.class);
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
	protected void populateFromScreen(Dexterity value, boolean includingKeys) {
		value.setAbilityScore(txtAbilityScore.getIntegerValue());
		value.setReactionAttackAdjustment(txtReactionAttackAdjustment.getIntegerValue());
		value.setDefensiveAdjustment(txtDefensiveAdjustment.getIntegerValue());
		value.setPickPockets(txtPickPockets.getIntegerValue());
		value.setOpenLocks(txtOpenLocks.getIntegerValue());
	value.setLocateRemoveTraps(txtLocateRemoveTraps.getIntegerValue());
	value.setMoveSilently(txtMoveSilently.getIntegerValue());
	value.setHideInShadows(txtHideInShadows.getIntegerValue());
	}

	@Override
	protected void populateScreen(Dexterity value) {
		txtAbilityScore.setIntegerValue(value.getAbilityScore()); 
		txtReactionAttackAdjustment .setIntegerValue(value.getReactionAttackAdjustment());
		txtDefensiveAdjustment .setIntegerValue(value.getDefensiveAdjustment());
		txtPickPockets.setIntegerValue(value.getPickPockets());
		txtOpenLocks.setIntegerValue(value.getOpenLocks());

	txtLocateRemoveTraps.setIntegerValue(value.getLocateRemoveTraps());
	txtMoveSilently.setIntegerValue(value.getMoveSilently());
	txtHideInShadows.setIntegerValue(value.getHideInShadows());
	}

	@Override
	protected ReturnValue<?> validateScreen() {
		// TODO Auto-generated method stub
		return new ReturnValue<>("");
	}

	@Override
	protected Dexterity newT() {
		// TODO Auto-generated method stub
		return new Dexterity ();
	}

	public void jbInit() {

		txtAbilityScore.setName("Ability Score");  		
		txtReactionAttackAdjustment .setName("Reaction/attack adjustment");
		txtDefensiveAdjustment.setName("Defensive adjustment");
		txtPickPockets .setName("Pick pockets");
		txtOpenLocks.setName("Open locks");
		txtLocateRemoveTraps.setName("Locate/remove traps");
		txtMoveSilently.setName("Move silently");
		txtHideInShadows.setName("Hide in shadows");
		
		PLabel lblAbilityScore            = new PLabel(txtAbilityScore.getName());  
		PLabel lblReactionAttackAdjustment      = new PLabel(txtReactionAttackAdjustment.getName()); 
		PLabel lblDefensiveAdjustment   = new PLabel(txtDefensiveAdjustment.getName()); 
		PLabel lblPickPockets     = new PLabel(txtPickPockets.getName()); 
		PLabel lblOpenLocks    = new PLabel(txtOpenLocks.getName());  
		PLabel lblLocateRemoveTraps    = new PLabel(txtLocateRemoveTraps.getName());  
		PLabel lblMoveSilently    = new PLabel(txtMoveSilently.getName());  
		PLabel lblHideInShadows  = new PLabel(txtHideInShadows.getName()); 
		
		int row = 0;
		
		add(lblAbilityScore, GBU.label(0, row));
		add(txtAbilityScore, GBU.text(1, row));
		row++;

		
		add(lblReactionAttackAdjustment, GBU.label(0, row));
		add(txtReactionAttackAdjustment, GBU.text(1, row));
		row++;

		
		add(lblDefensiveAdjustment, GBU.label(0, row));
		add(txtDefensiveAdjustment, GBU.text(1, row));
		row++;

		
		add(lblPickPockets, GBU.label(0, row));
		add(txtPickPockets, GBU.text(1, row));
		row++;

		
		add(lblOpenLocks, GBU.label(0, row));
		add(txtOpenLocks, GBU.text(1, row));
		row++;		
		add(lblLocateRemoveTraps, GBU.label(0, row));
		add(txtLocateRemoveTraps, GBU.text(1, row));
		row++;		
		add(lblMoveSilently, GBU.label(0, row));
		add(txtMoveSilently, GBU.text(1, row));
		row++;		
		add(lblHideInShadows, GBU.label(0, row));
		add(txtHideInShadows, GBU.text(1, row));
		row++;

		
		
		add(new PLabel(""),GBU.label(99,99));
		
	}
	
}
