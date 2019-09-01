package com.nobbysoft.com.nobbysoft.first.client.data.panels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.util.List;
 
import javax.swing.JScrollPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.com.nobbysoft.first.client.components.*;
import com.nobbysoft.com.nobbysoft.first.client.components.PCodeField;
import com.nobbysoft.com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.com.nobbysoft.first.client.components.PIntegerField;
import com.nobbysoft.com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.com.nobbysoft.first.client.components.PTextArea;
import com.nobbysoft.com.nobbysoft.first.client.components.PTextField;
import com.nobbysoft.com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.Spell;
import com.nobbysoft.com.nobbysoft.first.common.servicei.CharacterClassService;
import com.nobbysoft.com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.com.nobbysoft.first.server.dao.VC;
import com.nobbysoft.com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class SpellPanel extends AbstractDataPanel<Spell, String> implements MaintenancePanelInterface<Spell> {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public SpellPanel() {
		setLayout(new GridBagLayout());
		jbInit();
	}

	@Override
	DataServiceI<?, ?> getDataService() {   
		DataServiceI dao  = DataMapper.INSTANCE.getDataService(Spell.class);
		return dao;
		
	}

 
 
	private final PCodeField txtSpellId = new PCodeField();
	private final PComboBox<CodedListItem<String>> txtSpellClass = new PComboBox<>();
	private final PIntegerCombo txtLevel = new PIntegerCombo(1,9);
	private final PTextField txtName = new PTextField(128) {
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			if(d.getWidth()< 300) {
				d.width=300;
			}
			return d;
		}
	};
	private final PCheckBox cbxVerbal = new PCheckBox("Verbal");
	private final PCheckBox cbxSomatic = new PCheckBox("Somatic");
	private final PCheckBox cbxMaterial = new PCheckBox("Material");
	private final PTextArea txtMaterialComponents = new PTextArea(256);
	private final PTextArea txtDescription = new PTextArea();
/*
				new VC("range",30),
				new VC("casting_time",30),
				new VC("duration",30),
				new VC("saving_throw",30),
				new VC("area_of_effect",60),
				new VC("spell_type",60),
 */
	private final PTextField txtRange = new PTextField(30);
	private final PTextField txtCastingTime = new PTextField(30);
	private final PTextField txtDuration = new PTextField(30);
	private final PTextField txtSavingThrow = new PTextField(30);
	private final PTextField txtAreaOfEffect = new PTextField(60);
	private final PTextField txtSpellType = new PTextField(60);
	
	
	private PDataComponent[] dataComponents = new PDataComponent[] { txtSpellClass, txtLevel, txtName, cbxVerbal,
			cbxSomatic, cbxMaterial, txtMaterialComponents, txtDescription,
			txtRange ,
			txtCastingTime,
			txtDuration,
			txtSavingThrow,
			txtAreaOfEffect,
			txtSpellType};
	private PDataComponent[] keyComponents = new PDataComponent[] { txtSpellId };

	private PDataComponent[] mandatoryComponents = new PDataComponent[] { txtSpellId, txtSpellClass, txtLevel, txtName,
			txtDescription };

	private PDataComponent[] buttonComponents = new PDataComponent[] {};

	public void jbInit() {

		txtSpellId.setName("Spell id");
		txtSpellClass.setName("Spell class");
		txtLevel.setName("Spell level");
		txtName.setName("Spell name");
		cbxVerbal.setName("Verbal");
		cbxSomatic.setName("Somatic");
		cbxMaterial.setName("Material");
		txtMaterialComponents.setName("Material components");
		txtDescription.setName("Description");
		txtRange.setName("Range");
		txtCastingTime.setName("Casting time");
		txtDuration.setName("Duration");
		txtSavingThrow.setName("Saving throw");
		txtAreaOfEffect.setName("Area of effect");
		txtSpellType.setName("Type");

		PLabel lblSpellId = new PLabel("Spell id");
		PLabel lblSpellClass = new PLabel("Class");
		PLabel lblSpellLevel = new PLabel("Spell level");
		PLabel lblName = new PLabel("Spell name");
		PLabel lblMaterialComponents = new PLabel("Material components");
		PLabel lblDescription = new PLabel("Description");

		PLabel lblRange = new PLabel(txtRange.getName());
		PLabel lblCastingTime = new PLabel(txtCastingTime.getName());
		PLabel lblDuration = new PLabel(txtDuration.getName());
		PLabel lblSavingThrow = new PLabel(txtSavingThrow.getName());
		PLabel lblAreaOfEffect = new PLabel(txtAreaOfEffect.getName());
		PLabel lblSpellType = new PLabel(txtSpellType.getName());
		
		add(getLblInstructions(), GBU.text(0, 0, 2));
		int row=1;
		add(lblSpellId, GBU.label(0, row));
		add(txtSpellId, GBU.text(1, row++));
		add(lblSpellClass, GBU.label(0, row));
		add(txtSpellClass, GBU.text(1, row++));

		add(lblSpellLevel, GBU.label(0, row));
		add(txtLevel, GBU.text(1, row++));

		add(lblName, GBU.label(0, row));
		add(txtName, GBU.text(1, row++));
		

		add(lblSpellType, GBU.label(0, row));
		add(txtSpellType, GBU.text(1, row++));
		

		PPanel pnlChecks = new PPanel(new FlowLayout(FlowLayout.LEFT));
		
		add(pnlChecks, GBU.text(1, row++));
		
		pnlChecks.add(cbxVerbal, GBU.text(1, 5));
		pnlChecks.add(cbxSomatic, GBU.text(1, 6));
		pnlChecks.add(cbxMaterial, GBU.text(1, 7));

		add(lblRange, GBU.label(0, row));			
		add(txtRange, GBU.text(1, row++));
		add(lblCastingTime, GBU.label(0, row));	
		add(txtCastingTime, GBU.text(1, row++));
		add(lblDuration, GBU.label(0, row));			
		add(txtDuration, GBU.text(1, row++));
		add(lblSavingThrow, GBU.label(0, row));
		add(txtSavingThrow, GBU.text(1, row++));
		add(lblAreaOfEffect, GBU.label(0, row));	
		add(txtAreaOfEffect	, GBU.text(1, row++));
		add(lblSpellType, GBU.label(0, row));	
		add(txtSpellType, GBU.text(1, row++));
		
		add(lblMaterialComponents, GBU.label(0, row));
		JScrollPane sclMaterialComponents = new JScrollPane(txtMaterialComponents) {
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				if(d.getWidth()< 300) {
					d.width=300;
				}
				if(d.getWidth()>1000) {
					d.width=1000;
				}
				if(d.getHeight()<100){
					d.height=100;
				}
				return d;
			}
		};
		add(sclMaterialComponents, GBU.panel(1, row++, 1, 3));
		add(new PLabel(" "), GBU.label(0, row++));
		add(new PLabel(" "), GBU.label(0, row++));

		add(lblDescription, GBU.label(0, row));
		JScrollPane sclDescription = new JScrollPane(txtDescription) {
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				if(d.getWidth()< 300) {
					d.width=300;
				}

				if(d.getWidth()>1000) {
					d.width=1000;
				}
				if(d.getHeight()<100){
					d.height=100;
				}
				return d;
			}
		};
		add(sclDescription, GBU.panel(1, row++, 1, 4));
		add(new PLabel( ""), GBU.label(0, row++));
		add(new PLabel(" "), GBU.label(0, row++));
		add(new PLabel(" "), GBU.label(0, row++));

		add(new PLabel(""), GBU.label(99, 99));

	}

	ReturnValue<?> validateScreen() {
		{

			String s = txtMaterialComponents.getText();
			if (cbxMaterial.isSelected()) {
				if (s == null || s.trim().isEmpty()) {
					return new ReturnValue(true, "Must detail the material components");
				}
			}
		}

		return new ReturnValue("");
	}

	Spell newT() {
		return new Spell();
	}

	void populateFromScreen(Spell value, boolean includingKeys) {
		if (includingKeys) {
			value.setSpellId(txtSpellId.getText());
		}
		value.setDescription(txtDescription.getText());
		value.setLevel((Integer) txtLevel.getTheValue());
		value.setMaterial(cbxMaterial.isSelected());
		value.setMaterialComponents(txtMaterialComponents.getText());
		value.setName(txtName.getText());
		value.setSomatic(cbxSomatic.isSelected());
		value.setSpellClass((String) txtSpellClass.getTheValue().getItem());
		value.setVerbal(cbxVerbal.isSelected());
		
		value.setAreaOfEffect(txtAreaOfEffect.getText());
		value.setCastingTime(txtCastingTime.getText());
		value.setDuration(txtDuration.getText());
		value.setRange(txtRange.getText());
		value.setSavingThrow(txtSavingThrow.getText());
		value.setSpellType(txtSpellType.getText());
	}

	void populateScreen(Spell value) {
		txtSpellId.setText(value.getSpellId());
		txtDescription.setText(value.getDescription());
		txtLevel.setIntegerValue(value.getLevel());
		cbxMaterial.setSelected(value.isMaterial());
		cbxSomatic.setSelected(value.isSomatic());
		cbxVerbal.setSelected(value.isVerbal());
		txtMaterialComponents.setText(value.getMaterialComponents());
		txtName.setText(value.getName());
		txtSpellClass.setSelectedCode(value.getSpellClass());
		txtAreaOfEffect.setText(value.getAreaOfEffect());
		txtCastingTime.setText(value.getCastingTime());
		txtDuration.setText(value.getDuration());
		txtRange.setText(value.getRange());
		txtSavingThrow.setText(value.getSavingThrow());
		txtSpellType.setText(value.getSpellType());

	}

	void populateCombos() {

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
