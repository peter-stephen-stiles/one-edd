package com.nobbysoft.first.client.data.panels;

import java.awt.GraphicsConfiguration;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Dialog.ModalityType;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

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
import com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterSpell;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassSpell;
import com.nobbysoft.first.common.entities.staticdto.Spell;
import com.nobbysoft.first.common.servicei.CharacterClassSpellService;
import com.nobbysoft.first.common.servicei.CodedListService;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.servicei.PlayerCharacterSpellService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.views.ViewPlayerCharacterSpell;
import com.nobbysoft.first.utils.DataMapper;
 

public class PlayerCharacterAddSpell extends PDialog {



	private static final String ANY_CLASS = "{any}";

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	private boolean cancelled= true;
 
	public PlayerCharacterAddSpell(Window owner) {
		super(owner);

		setModalityType(ModalityType.APPLICATION_MODAL);
		jbInit();
		populateCombos();
	}
	
	private PComboBox<CodedListItem<String>> cbxSpellClasses = new PComboBox<>();
	private PIntegerCombo cbxLevel = new PIntegerCombo(0,9,false,false);
	private PTextField txtNameFilter = new PTextField();
	
	private PList<Spell> listOfSpells = new PList();
	
	private PlayerCharacter pc;	
	
	public void setPcId(PlayerCharacter pc) {
		this.pc=pc;
		
		initialiseSpellClassCombo(pc);
	}



	private void initialiseSpellClassCombo(PlayerCharacter pc) {
		List<String> classes = new ArrayList<>();
		classes.add(pc.getFirstClass());
		if(pc.getSecondClass()!=null) {
			classes.add(pc.getSecondClass());
			if(pc.getThirdClass()!=null) {
				classes.add(pc.getThirdClass());			
			}
		}
		
		CharacterClassSpellService ccs = (CharacterClassSpellService)getDataService(CharacterClassSpell.class);
		
		
		CodedListService cliDao = (CodedListService)DataMapper.INSTANCE.getNonDataService(CodedListService.class);
		
		
		
		try {
			
			List<String> valid = ccs.getSpellClassesForClasses(classes);
			
			List<CodedListItem<?>> listy = (cliDao.getCodedList(Constants.CLI_MAGIC_CLASS));

			List<CodedListItem<?>> listo = new ArrayList<>();
			for(CodedListItem cli:listy) {
				if( valid.contains(cli.getItem())) {
					listo.add(cli);
				}
			}
			
			 // filter out of list anything in listy thats not in validSpellClasses
			
			
				
		cbxSpellClasses.setList(listo);
		
		if(listo.size()==0) {
			btnConfirm.setReadOnly(true);
			btnFilter.setReadOnly(true);
		}
		
		
		} catch (SQLException e) {
			LOGGER.error("Error getting coded list",e);
		}
			
		//cbxSpellClasses.setSelectedCode(pc.getFirstClass());
		

	}
	
	private PButton btnFilter = new PButton("Filter");
	private PButton btnConfirm = new PButton("Confirm");
	
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
		
		pnlHeader.add(btnFilter,GBU.button(3,1));
		btnFilter.addActionListener(ae -> refresh());
		pnlHeader.add(new PLabel(""),GBU.label(99, 99));
		
		add(pnlHeader,BorderLayout.NORTH);

		PButton btnCancel = new PButton("Cancel");
		
		btnCancel.addActionListener(ae->dispose());
		btnConfirm.addActionListener(ae->confirm());
		PButtonPanel pnlBottom = new PButtonPanel();
		pnlBottom.add(btnConfirm);
		pnlBottom.add(btnCancel);
		add(pnlBottom,BorderLayout.SOUTH);
		JScrollPane sclListOfSpells = new JScrollPane(listOfSpells) {
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				if(d.width<500) {
					d.width=500;
				}
				if(d.height<300) {
					d.height=300;
				}
				return d;
			}
		};
		add(sclListOfSpells,BorderLayout.CENTER);
		
		listOfSpells.setCellRenderer(new ListCellRenderer<Spell>() {
			final JLabel lbl = new JLabel();
			final Color back = lbl.getBackground();
			final Color dark = Color.cyan;
			final boolean op = lbl.isOpaque();
			@Override
			public Component getListCellRendererComponent(JList<? extends Spell> list, Spell value, int index,
					boolean isSelected, boolean cellHasFocus) {
				StringBuilder sb = new StringBuilder();
				sb.append(value.getSpellId()).append(" - ").append(className(value.getSpellClass())).append( " - ");
				sb.append(value.getName()).append(" ( level ").append(value.getLevel()).append(")");
				lbl.setText(sb.toString());
				if(isSelected||cellHasFocus){
					lbl.setOpaque(true);
					lbl.setBackground(dark);
				} else {
					lbl.setOpaque(op);
					lbl.setBackground(back);
				}
				return lbl;
			}
			
		});
	}

	DataServiceI getDataService(Class clazz) {
		DataServiceI dao = DataMapper.INSTANCE.getDataService(clazz);
		return dao;
	}

	private String className(String classId) {
		return classId;
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
			List<Spell> list = pces.getSpellsNotForPC(pc.getPcId(), level, spellClassId, filterName);
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
		

		
	}
	
	
	public boolean isCancelled() {
		return cancelled;
	}
	
	private void confirm() {
		
		if(listOfSpells.getSelectedValue()!=null) {
			Spell spell = listOfSpells.getSelectedValue();
			PlayerCharacterSpell pcs = new PlayerCharacterSpell();
			pcs.setPcId(pc.getPcId());
			pcs.setSpellId(spell.getSpellId());
			pcs.setInMemory(0);
			PlayerCharacterSpellService pces = (PlayerCharacterSpellService )getDataService(PlayerCharacterSpell.class);
			try {
				pces.insert(pcs);
				cancelled=false;
				dispose();
			} catch (SQLException e) {
				Popper.popError(this, e);
			}
		}
		
		//
		//
		// assuming it worked :)
		cancelled=false;
	}
	
}
