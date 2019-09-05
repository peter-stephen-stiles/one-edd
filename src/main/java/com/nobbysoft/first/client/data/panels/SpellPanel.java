package com.nobbysoft.first.client.data.panels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.swing.JScrollPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PCheckBox;
import com.nobbysoft.first.client.components.PCodeField;
import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PIntegerCombo;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.first.client.components.PTextArea;
import com.nobbysoft.first.client.components.PTextField;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.staticdto.Spell;
import com.nobbysoft.first.common.servicei.CharacterClassService;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

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

	private final PTextField txtRange = new PTextField(30);
	private final PTextField txtCastingTime = new PTextField(30);
	private final PTextField txtDuration = new PTextField(30);
	private final PTextField txtSavingThrow = new PTextField(30);
	private final PTextField txtAreaOfEffect = new PTextField(60); 
	

	private final PCheckBox cbxTypeAbjuration = new PCheckBox("Abjuration");
	private final PCheckBox cbxTypeAlteration = new PCheckBox("Alteration");
	private final PCheckBox cbxTypeCharm = new PCheckBox("Charm");
	private final PCheckBox cbxTypeConjuration = new PCheckBox("Conjuration");
	private final PCheckBox cbxTypeDivination = new PCheckBox("Divination");
	private final PCheckBox cbxTypeEnchantment = new PCheckBox("Enchantment");
	private final PCheckBox cbxTypeEvocation = new PCheckBox("Evocation");
	private final PCheckBox cbxTypeIllusion = new PCheckBox("Illusion");
	private final PCheckBox cbxTypeInvocation = new PCheckBox("Invocation");
	private final PCheckBox cbxTypeNecromantic = new PCheckBox("Necromantic");
	private final PCheckBox cbxTypePhantasm = new PCheckBox("Phantasm");
	private final PCheckBox cbxTypePossession = new PCheckBox("Possession");
	private final PCheckBox cbxTypeSummoning = new PCheckBox("Summoning");

	
	
	private PDataComponent[] dataComponents = new PDataComponent[] { txtSpellClass, txtLevel, txtName, cbxVerbal,
			cbxSomatic, cbxMaterial, txtMaterialComponents, txtDescription,
			txtRange ,
			txtCastingTime,
			txtDuration,
			txtSavingThrow,
			txtAreaOfEffect,
			cbxTypeAbjuration ,
			cbxTypeAlteration, 
			cbxTypeCharm ,
			cbxTypeConjuration,
			cbxTypeDivination ,
			cbxTypeEnchantment,
			cbxTypeEvocation ,
			cbxTypeIllusion  ,
			cbxTypeInvocation ,
			cbxTypeNecromantic,
			cbxTypePhantasm  ,
			cbxTypePossession, 
			cbxTypeSummoning };
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
		
		cbxTypeAbjuration.setName("Abjuration");
		cbxTypeAlteration.setName("Alteration");
		cbxTypeCharm.setName("Charm");
		cbxTypeConjuration.setName("Conjuration");
		
		cbxTypeDivination.setName("Divination");
		cbxTypeEnchantment.setName("Enchantment");
		cbxTypeEvocation.setName("Evocation");
		cbxTypeIllusion.setName("Illusion");
		
		cbxTypeInvocation.setName("Invocation");
		cbxTypeNecromantic.setName("Necromantic");
		cbxTypePhantasm.setName("Phantasm");
		cbxTypePossession.setName("Possession");
		
		cbxTypeSummoning .setName("Summoning");


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
		
 
		

		PPanel pnlChecks = new PPanel(new FlowLayout(FlowLayout.LEFT));

		PLabel lblChecks = new PLabel("Component(s)"); 
		add(lblChecks, GBU.label(0, row));
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
		

		PPanel pnlTypes = new PPanel(new GridBagLayout());
		int nrow=0;
		pnlTypes.add(cbxTypeAbjuration, GBU.button(0, nrow));
		pnlTypes.add(cbxTypeAlteration, GBU.button(1, nrow));
		pnlTypes.add(cbxTypeCharm, GBU.button(2, nrow++));
		
		pnlTypes.add(cbxTypeConjuration, GBU.button(0, nrow));
		pnlTypes.add(cbxTypeDivination, GBU.button(1, nrow));
		pnlTypes.add(cbxTypeEnchantment, GBU.button(2, nrow++));
		
		pnlTypes.add(cbxTypeEvocation, GBU.button(0, nrow));
		pnlTypes.add(cbxTypeIllusion, GBU.button(1, nrow));
		pnlTypes.add(cbxTypeInvocation, GBU.button(2, nrow++));
		
		pnlTypes.add(cbxTypeNecromantic, GBU.button(0, nrow));
		pnlTypes.add(cbxTypePhantasm, GBU.button(1, nrow));
		pnlTypes.add(cbxTypePossession, GBU.button(2, nrow++));
		
		pnlTypes.add(cbxTypeSummoning , GBU.button(0, nrow));


		PLabel lblType = new PLabel("Type(s)"); 
		add(lblType, GBU.label(0, row));
		add(pnlTypes, GBU.text(1, row++));
		
		add(lblMaterialComponents, GBU.label(0, row));
		txtMaterialComponents.setWrapStyleWord(true);
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
		sclMaterialComponents.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(sclMaterialComponents, GBU.panel(1, row++, 1, 3));
		add(new PLabel(" "), GBU.label(0, row++));
		add(new PLabel(" "), GBU.label(0, row++));

		add(lblDescription, GBU.label(0, row));
		txtDescription.setWrapStyleWord(true);
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
		sclDescription.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
		
		value.setTypeAbjuration(cbxTypeAbjuration.isSelected());
		value.setTypeAlteration(cbxTypeAlteration.isSelected());
		value.setTypeCharm(cbxTypeCharm.isSelected());
		value.setTypeConjuration(cbxTypeConjuration.isSelected());
		value.setTypeDivination(cbxTypeDivination.isSelected());
		value.setTypeEnchantment(cbxTypeEnchantment.isSelected());
		value.setTypeEvocation(cbxTypeEvocation.isSelected());
		value.setTypeIllusion(cbxTypeIllusion.isSelected());
		value.setTypeInvocation(cbxTypeInvocation.isSelected());
		value.setTypeNecromantic(cbxTypeNecromantic.isSelected());
		value.setTypePhantasm(cbxTypePhantasm.isSelected());
		value.setTypePossession(cbxTypePossession.isSelected());
		value.setTypeSummoning(cbxTypeSummoning .isSelected());

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

		cbxTypeAbjuration.setSelected(value.isTypeAbjuration());
		cbxTypeAlteration.setSelected(value.isTypeAlteration());
		cbxTypeCharm.setSelected(value.isTypeCharm());
		cbxTypeConjuration.setSelected(value.isTypeConjuration());
		cbxTypeDivination.setSelected(value.isTypeDivination());
		cbxTypeEnchantment.setSelected(value.isTypeEnchantment());
		cbxTypeEvocation.setSelected(value.isTypeEvocation());
		cbxTypeIllusion.setSelected(value.isTypeIllusion());
		cbxTypeInvocation.setSelected(value.isTypeInvocation());
		cbxTypeNecromantic.setSelected(value.isTypeNecromantic());
		cbxTypePhantasm.setSelected(value.isTypePhantasm());
		cbxTypePossession.setSelected(value.isTypePossession());
		cbxTypeSummoning .setSelected(value.isTypeSummoning());

		
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
