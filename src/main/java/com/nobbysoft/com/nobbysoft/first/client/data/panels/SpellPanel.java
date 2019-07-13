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

	private PDataComponent[] dataComponents = new PDataComponent[] { txtSpellClass, txtLevel, txtName, cbxVerbal,
			cbxSomatic, cbxMaterial, txtMaterialComponents, txtDescription };
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

		PLabel lblSpellId = new PLabel("Spell id");
		PLabel lblSpellClass = new PLabel("Class");
		PLabel lblSpellLevel = new PLabel("Spell level");
		PLabel lblName = new PLabel("Spell name");
		PLabel lblMaterialComponents = new PLabel("Material components");
		PLabel lblDescription = new PLabel("Description");

		add(getLblInstructions(), GBU.text(0, 0, 2));
		add(lblSpellId, GBU.label(0, 1));
		add(txtSpellId, GBU.text(1, 1));
		add(lblSpellClass, GBU.label(0, 2));
		add(txtSpellClass, GBU.text(1, 2));

		add(lblSpellLevel, GBU.label(0, 3));
		add(txtLevel, GBU.text(1, 3));

		add(lblName, GBU.label(0, 4));
		add(txtName, GBU.text(1, 4));

		PPanel pnlChecks = new PPanel(new FlowLayout(FlowLayout.LEFT));
		
		add(pnlChecks, GBU.text(1, 5));
		
		pnlChecks.add(cbxVerbal, GBU.text(1, 5));
		pnlChecks.add(cbxSomatic, GBU.text(1, 6));
		pnlChecks.add(cbxMaterial, GBU.text(1, 7));

		add(lblMaterialComponents, GBU.label(0, 8));
		add(new PLabel(" "), GBU.label(0, 9));
		add(new PLabel(" "), GBU.label(0, 10));
		JScrollPane sclMaterialComponents = new JScrollPane(txtMaterialComponents) {
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				if(d.getWidth()< 300) {
					d.width=300;
				}
				if(d.getHeight()<100){
					d.height=100;
				}
				return d;
			}
		};
		add(sclMaterialComponents, GBU.panel(1, 8, 1, 3));

		add(lblDescription, GBU.label(0, 11));
		add(new PLabel( ""), GBU.label(0, 12));
		add(new PLabel(" "), GBU.label(0, 13));
		add(new PLabel(" "), GBU.label(0, 14));
		JScrollPane sclDescription = new JScrollPane(txtDescription) {
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				if(d.getWidth()< 300) {
					d.width=300;
				}
				if(d.getHeight()<100){
					d.height=100;
				}
				return d;
			}
		};
		add(sclDescription, GBU.panel(1, 11, 1, 4));

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
