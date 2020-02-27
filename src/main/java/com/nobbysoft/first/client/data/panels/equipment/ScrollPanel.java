package com.nobbysoft.first.client.data.panels.equipment;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PCheckBox;
import com.nobbysoft.first.client.components.PCodeField;
import com.nobbysoft.first.client.components.PComboBox;
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
import com.nobbysoft.first.common.entities.equipment.Scroll;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.equipment.EquipmentHands;
import com.nobbysoft.first.common.servicei.CharacterClassService;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class ScrollPanel extends AbstractDataPanel<Scroll, String> implements MaintenancePanelInterface<Scroll> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public ScrollPanel() {
		setLayout(new GridBagLayout());
		jbInit();
	}

	@Override
	protected
	DataServiceI<?, ?> getDataService() {   
		DataServiceI dao  = DataMapper.INSTANCE.getDataService(Scroll.class);
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
	
/*
 	private String code;
	private boolean spellScroll;
	private String name;  // only populated if NOT "spellScroll"
	private int encumberanceGP;	
	private String spellClass; // only populated if "spellScroll"
	private int countSpells; // only populated if "spellScroll"
	 	
 */
	
	private final PCheckBox txtSpellScroll = new PCheckBox("Spell scroll?");
	
	private final PIntegerField txtWeightGP = new PIntegerField();
	

	private final PComboBox<CodedListItem<String>> txtSpellClass = new PComboBox<>();

	private final PIntegerCombo txtCountSpells = new PIntegerCombo(0,20);
	 
 
	
	private PDataComponent[] dataComponents = new PDataComponent[] { 
			txtName, 
			txtWeightGP,
			txtSpellScroll,
			txtSpellClass,
			txtCountSpells			
			 };
	private PDataComponent[] keyComponents = new PDataComponent[] { txtCode };

	private PDataComponent[] mandatoryComponents = new PDataComponent[] { 
			txtCode};

	private PDataComponent[] buttonComponents = new PDataComponent[] {};

	
	public void jbInit() {
 
		
		txtCode.setName("Id");
 
		txtName.setName("Name");
		txtWeightGP.setName("Weight (gp)");
		txtSpellScroll.setName("Scroll of spells?");
		txtSpellClass.setName("Spell class");		
		txtCountSpells.setName("How many spells?");
 
		txtSpellScroll.addChangeListener(ce->{
			LOGGER.info("change listener " +ce);
			boolean s = txtSpellScroll.isSelected();
			txtName.setEnabled(!s);
			txtSpellClass.setEnabled(s);
			txtCountSpells.setEditable(s);
		});

 
		
		add(getLblInstructions(), GBU.text(0, 0, 2));
		int row=1;
		add(new JLabel(txtCode.getName()), GBU.label(0, row));
		add(txtCode, GBU.text(1, row)); 
		row++;
		
		//add(lblBaseAC, GBU.label(0, row));
		add(txtSpellScroll, GBU.text(1, row));  
		row++;
		
		add(new JLabel(txtName.getName()), GBU.label(0, row));
		add(txtName, GBU.text(1, row,3));
		row++;

		add(new JLabel(txtWeightGP.getName()), GBU.label(0, row));
		add(txtWeightGP, GBU.text(1, row));
		row++;
		

		add(new JLabel(txtSpellClass.getName()), GBU.label(0, row));
		add(txtSpellClass, GBU.text(1, row)); 

		add(new JLabel(txtCountSpells.getName()), GBU.label(2, row));
		add(txtCountSpells, GBU.text(3, row));
		row++;
		 
		
		add(new PLabel(""), GBU.label(99, 99));

	}

	protected ReturnValue<?> validateScreen() {
		
		{
			boolean s =txtSpellScroll.isSelected();
			int count = txtCountSpells.getIntegerValue();
 
		}

		return new ReturnValue("");
	}

	protected Scroll newT() {
		return new Scroll();
	}

	protected void populateFromScreen(Scroll value, boolean includingKeys) {
		if (includingKeys) {
			value.setCode(txtCode.getText());
		}
   
		value.setCapacityGP(0);// not for weapons 
	 
		
		value.setEncumberanceGP(txtWeightGP.getIntegerValue());
		 
		value.setRequiresHands(EquipmentHands.NONE);// no hands for scrolls
		
		value.setSpellScroll(txtSpellScroll.isSelected());
		if(value.isSpellScroll()) {			
			int count = txtCountSpells.getIntegerValue();
			String className=((CodedListItem)txtSpellClass.getSelectedItem()).getDescription();			
			value.setName("Scroll of "+count+" "+className+" spells");
			value.setSpellClass((String) txtSpellClass.getTheValue().getItem());
			value.setCountSpells(count);
		} else {
			value.setName(txtName.getText());
			value.setSpellClass(null);
			value.setCountSpells(0);			
		}
	}

	protected void populateScreen(Scroll value) {
		txtCode.setText(value.getCode());  
		txtWeightGP	.setIntegerValue(	value.getEncumberanceGP());		
		txtSpellScroll.setSelected(value.isSpellScroll());
		if(value.isSpellScroll()) {			
			txtName.setText("");
			txtSpellClass.setSelectedCode(value.getSpellClass());
			txtCountSpells.setIntegerValue(value.getCountSpells());
		} else {
			// ignore the combo
			txtName	.setText(value.getName());
			txtCountSpells.setIntegerValue(0);
		}

	}

	protected void populateCombos() {


		try {
			CharacterClassService dao = (CharacterClassService)DataMapper.INSTANCE.getDataService(CharacterClass.class);
				List<CodedListItem<String>> list = dao.getSpellClassesAsCodedList();

				for (CodedListItem<String> cli : list) {
					LOGGER.info("spell class " + cli);
					txtSpellClass.addItem(cli);
				}
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
