package com.nobbysoft.first.client.data.panels;

import java.awt.GraphicsConfiguration;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PButton;
import com.nobbysoft.first.client.components.PButtonPanel;
import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.client.components.PIntegerCombo;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PList;
import com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.first.client.components.PTextField;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterSpell;
import com.nobbysoft.first.common.entities.staticdto.Spell;
import com.nobbysoft.first.common.servicei.CodedListService;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.servicei.PlayerCharacterSpellService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.views.ViewPlayerCharacterSpell;
import com.nobbysoft.first.utils.DataMapper;

public class AddPlayerCharacterSpell extends PDialog {



	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	private boolean cancelled= true;
 
	public AddPlayerCharacterSpell(Window owner) {
		super(owner);

		setModalityType(ModalityType.APPLICATION_MODAL);
		jbInit();
		populateCombos();
	}
	
	private PComboBox<CodedListItem<String>> cbxSpellClasses = new PComboBox<>();
	private PIntegerCombo cbxLevel = new PIntegerCombo(0,9,false,false);
	private PTextField txtNameFilter = new PTextField();
	
	private PList<Spell> listOfSpells = new PList();
	
	private int pcId;
	
	public void setPcId(int pcId) {
		this.pcId=pcId;
	}
	
	
	
	private void jbInit() {
		this.setLayout(new BorderLayout(5,5));
		//basically need to pick a spell
		
		// so the header will be filter criteria and the centre will be a list of spells and the bottom will show the selected spell details.
		PPanel pnlHeader = new PPanel(new GridBagLayout());
		pnlHeader.add(new PLabel("Spell class:"),GBU.label(0, 0));
		pnlHeader.add(cbxSpellClasses,GBU.text(1,0));
		pnlHeader.add(new PLabel("Level:"),GBU.label(2,0));
		pnlHeader.add(cbxLevel,GBU.text(3,0));
		pnlHeader.add(new PLabel("Name filter:"),GBU.label(0,1));
		pnlHeader.add(txtNameFilter,GBU.text(1,1,2));
		PButton btnFilter = new PButton("Filter");
		pnlHeader.add(btnFilter,GBU.button(3,1));
		btnFilter.addActionListener(ae -> refresh());
		pnlHeader.add(new PLabel(""),GBU.label(99, 99));
		
		add(pnlHeader,BorderLayout.NORTH);

		PButton btnCancel = new PButton("Cancel");
		PButton btnConfirm = new PButton("Confirm");
		
		btnCancel.addActionListener(ae->dispose());
		btnConfirm.addActionListener(ae->confirm());
		PButtonPanel pnlBottom = new PButtonPanel();
		pnlBottom.add(btnConfirm);
		pnlBottom.add(btnCancel);
		add(pnlBottom,BorderLayout.SOUTH);
		
		add(listOfSpells,BorderLayout.CENTER);
		
	}

	DataServiceI getDataService(Class clazz) {
		DataServiceI dao = DataMapper.INSTANCE.getDataService(clazz);
		return dao;
	}

	
	private void refresh() {
		LOGGER.info("filtering:");
		LOGGER.info("spell class :"+cbxSpellClasses.getSelectedCode());
		LOGGER.info("level       :"+cbxLevel.getIntegerValue());
		LOGGER.info("name        :"+txtNameFilter.getText());
		//
		int level = cbxLevel.getIntegerValue();
		String spellClassId = (String)cbxSpellClasses.getSelectedCode();
		String filterName = txtNameFilter.getText().toLowerCase().trim();
		PlayerCharacterSpellService pces = (PlayerCharacterSpellService )getDataService(PlayerCharacterSpell.class);
		try {
			List<Spell> list = pces.getViewNotForPC(pcId, level, spellClassId, filterName);
			Vector<Spell> v= new Vector<>();
			v.addAll(list);
			listOfSpells.setListData(v);
		} catch (SQLException e) {
			Popper.popError(this, e);
		}
		//
		//
	}
	
	private void populateCombos() {
		
		CodedListService cliDao = (CodedListService)DataMapper.INSTANCE.getNonDataService(CodedListService.class);
		
		try {
			cbxSpellClasses.setList(cliDao.getCodedList(Constants.CLI_MAGIC_CLASS));
		} catch (SQLException e) {
			LOGGER.error("Error getting coded list",e);
		}
		// plus		
		cbxSpellClasses.insertItemAt(new CodedListItem<String>(null,"{any}"),0);
		
	}
	
	
	public boolean isCancelled() {
		return cancelled;
	}
	
	private void confirm() {
		
		//
		//
		// assuming it worked :)
		cancelled=false;
	}
	
}
