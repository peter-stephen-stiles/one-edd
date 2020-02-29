package com.nobbysoft.first.client.data.panels.pc;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PBasicTableWithModel;
import com.nobbysoft.first.client.components.PBasicTableWithModel.ColumnConfig;
import com.nobbysoft.first.client.components.PButton;
import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterSpell;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassSpell;
import com.nobbysoft.first.common.entities.staticdto.Spell;
import com.nobbysoft.first.common.servicei.CharacterClassSpellService;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.servicei.PlayerCharacterSpellService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.views.ViewPlayerCharacterSpell;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class PlayerCharacterSpellPanel extends PPanel {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public PlayerCharacterSpellPanel() {
		super(new BorderLayout());
		jbInit();
	}

	private PlayerCharacterUpdatedListener pcUpdatedListener;
	
	public void addPlayerCharacterUpdatedListener(PlayerCharacterUpdatedListener pcUpdatedListener) {
		this.pcUpdatedListener = pcUpdatedListener;
	}
	private int pcId;
	private String characterName = null;
	private PlayerCharacter pc = null;
	private Map<String,CharacterClassSpell> allowedSpells = new HashMap<>();

	public void initialiseCharacter(PlayerCharacter pc) {
		this.pcId = pc.getPcId();
		this.characterName = pc.getCharacterName();
		this.pc = pc;

		populateAllowedSpells();
		
		SwingUtilities.invokeLater(() -> populateSpellTable());
	}

	// Spell
	//

	private final PButton btnSpellAdd = new PButton("Add");
	private final PButton btnSpellRemove = new PButton("Remove");
	private final PButton btnSpellMemorise = new PButton("Memorise");
	private final PButton btnSpellForget = new PButton("Forget");

	private PDataComponent[] buttonComponents = new PDataComponent[] { btnSpellAdd, btnSpellRemove, btnSpellMemorise,
			btnSpellForget };

	private final ColumnConfig[] SpellConfigs = new ColumnConfig[] { new ColumnConfig("", 0, 0, 0),
			new ColumnConfig("Spell Class", 20, 200, 5000), new ColumnConfig("Level", 20, 80, 5000),
			new ColumnConfig("Description", 20, 360, 5000), new ColumnConfig("#In Memory", 20, 70, 5000), };

	private final PBasicTableWithModel tblSpell = new PBasicTableWithModel(SpellConfigs);

	private PComboBox<CodedListItem<String>> txtAllByLevel = new PComboBox<>();
	private PComboBox<CodedListItem<String>> txtMemByLevel = new PComboBox<>();

	private void disableThings() {
		for (PDataComponent pc : disableMe) {
			pc.setReadOnly(true);
		}
	}

	private PDataComponent[] disableMe = new PDataComponent[] {};

	private void jbInit() {

		final PPanel pnlSpellButtons = new PPanel(new FlowLayout(FlowLayout.RIGHT));

		pnlSpellButtons.add(btnSpellAdd);
		pnlSpellButtons.add(btnSpellRemove);
		pnlSpellButtons.add(new PLabel("  "));
		pnlSpellButtons.add(btnSpellMemorise);
		pnlSpellButtons.add(btnSpellForget);

		btnSpellMemorise.addActionListener(ae -> memorise());
		btnSpellForget.addActionListener(ae -> forget());

		JScrollPane sclSpell = new JScrollPane(tblSpell);

		PPanel pnlInfo = new PPanel(new GridBagLayout());

		add(pnlSpellButtons, BorderLayout.NORTH);

		add(sclSpell, BorderLayout.CENTER);
		add(pnlInfo, BorderLayout.SOUTH);

		
		//  
		pnlInfo.add(new PLabel("Allowed by level:"), GBU.label(0, 0));
		pnlInfo.add(txtAllByLevel, GBU.text(1, 0));

		pnlInfo.add(new PLabel("Memorised by level:"), GBU.label(0, 1));
		pnlInfo.add(txtMemByLevel, GBU.text(1, 1));

		btnSpellRemove.addActionListener(ae -> {
			removeSpell();
		});

		tblSpell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					btnSpellMemorise.doClick();
				}
			}
		});

		tblSpell.getSelectionModel().addListSelectionListener(se -> {
			if (!se.getValueIsAdjusting()) {
				int rowNum = tblSpell.getSelectedRow();
				ViewPlayerCharacterSpell s = getSelectedSpell(rowNum);
				if (s != null) {
					txtMemByLevel.setSelectedCode(s.getSpell().getSpellClass());
				}
			}
		});

		btnSpellAdd.addActionListener(ae -> {			
			addSpell();

		});

		disableThings();
	}

	public PDataComponent[] getButtonComponents() {
		return buttonComponents;
	}
	private DataServiceI getDataService(Class clazz) {
		DataServiceI dao = DataMapper.INSTANCE.getDataService(clazz);
		return dao;
	}

	PlayerCharacterAddSpell add = null;
	
	private void addSpell() {
		if(pc==null) {
			Popper.popError(this, "Save first", "You have to save your character before you add spells, sorry!");
			return;
		}
		
		if(add==null) {
			add = new PlayerCharacterAddSpell(GuiUtils.getParent(this));
		}
		add.setPcId(pc);
		add.pack();
		add.setLocationRelativeTo(null);
		add.setVisible(true);
		if (!add.isCancelled()) {
			// refresh
			populateSpellTable();
		}

	}

	private void removeSpell() {
		PlayerCharacterSpell pce = null;
		ViewPlayerCharacterSpell vpce = null;
		if(tblSpell.getSelectedRows().length>1) {
			Popper.popError(this, "Too many", "Too many rows selected. When you want to delete please select one spell at a time.");
			return;
		}
		int rowNum = tblSpell.getSelectedRow();
		if (rowNum >= 0) {
			vpce = getSelectedSpell(rowNum);
			if (vpce != null) {
				pce = vpce.getPlayerCharacterSpell();
				if (pce != null) {
					String desc = vpce.getSpell().getName();
					boolean ok = Popper.popYesNoQuestion(this, "Remove Spell",
							"Confirm that you want to remove spell " + desc + "?");
					if (ok) {
						PlayerCharacterSpellService pces = (PlayerCharacterSpellService) getDataService(
								PlayerCharacterSpell.class);
						try {
							pces.delete(pce);
							populateSpellTable();
						} catch (SQLException e) {
							Popper.popError(this, e);
						}
					}
				}
			}
		}
	}

	private ViewPlayerCharacterSpell getSelectedSpell(int rowNum) {
		ViewPlayerCharacterSpell vpce = null;
		int modelRow = tblSpell.convertRowIndexToModel(rowNum);
		if (modelRow >= 0) {
			Object o0 = tblSpell.getValueAt(modelRow, 0);

			vpce = (ViewPlayerCharacterSpell) o0;
		}
		return vpce;
	}

	private PlayerCharacterSpell getSelectedSpell() {
		PlayerCharacterSpell pce = null;
		int rowNum = tblSpell.getSelectedRow();
		if (rowNum >= 0) {
			int modelRow = tblSpell.convertRowIndexToModel(rowNum);
			pce = (PlayerCharacterSpell) tblSpell.getValueAt(modelRow, 0);
		}
		return pce;
	}

	private void memorise() {

		int increment=1;
		
		memoriseOrForget(increment);

	}

	private void memoriseOrForget(int increment) {
		PlayerCharacterSpell pce = null;
		ViewPlayerCharacterSpell vpce = null; 
		
		int[] rowNums = tblSpell.getSelectedRows();
		
		for(int rowNum:rowNums) {
		 
			try {
			
					vpce = getSelectedSpell(rowNum);
					if (vpce != null) {
						pce = vpce.getPlayerCharacterSpell();
						if (pce != null) {
							PlayerCharacterSpellService pces = (PlayerCharacterSpellService) getDataService(
									PlayerCharacterSpell.class);
		
								PlayerCharacterSpell spell = pces.get(pce.getKey());
								
								if(increment>0) {													
									// are we allowed?									
									spell.setInMemory(spell.getInMemory() + increment);
									pces.update(spell);
								} else {
								if (spell.getInMemory() > 0) {
									spell.setInMemory(spell.getInMemory() + increment);
									pces.update(spell);
								}
								}
						
							
		
						}
					}
			} catch (Exception e) {
				Popper.popError(this, e);
			}
		}
		if(rowNums.length>0) {

			populateSpellTable();
		}
	}

	private void forget() {

		int increment=-1;
		
		memoriseOrForget(increment);

	}

	private final Map<String, int[]> memorisedMap = new HashMap<>();

	private void populateSpellTable() {

		int[] sr = tblSpell.getSelectedRows();

		memorisedMap.clear();
		txtMemByLevel.clear();
		tblSpell.clearTable();
		if (pcId > 0) {

			try {
				PlayerCharacterSpellService pces = (PlayerCharacterSpellService) getDataService(
						PlayerCharacterSpell.class);
				List<ViewPlayerCharacterSpell> list = pces.getViewForPC(pcId);

				for (ViewPlayerCharacterSpell vpce : list) {
					PlayerCharacterSpell pce = vpce.getPlayerCharacterSpell();
					Spell s = vpce.getSpell();					

					tblSpell.addRow(vpce.getAsRow());
					int[] memorised;
					String sc = s.getSpellClass();
					if (memorisedMap.containsKey(sc)) {
						memorised = memorisedMap.get(sc);
					} else {
						memorised = new int[10];// no 0th levels :)
						memorisedMap.put(sc, memorised);
					}
					memorised[s.getLevel()] = (pce.getInMemory() + memorised[s.getLevel()]);
				}
			} catch (Exception ex) {
				Popper.popError(this, ex);
			}
			
			if(sr.length>0) {
				for(int row:sr) {
					tblSpell.getSelectionModel().addSelectionInterval(row, row);					
				}
			}

			for (String sc : memorisedMap.keySet()) {
				CharacterClassSpell ccs = allowedSpells.get(sc);
				if(ccs==null) {					
					ccs = new CharacterClassSpell();
					ccs.setLevel1Spells(0);
					ccs.setLevel2Spells(0);
					ccs.setLevel3Spells(0);
					ccs.setLevel4Spells(0);
					ccs.setLevel5Spells(0);
					ccs.setLevel6Spells(0);
					ccs.setLevel7Spells(0);
					ccs.setLevel8Spells(0);
					ccs.setLevel9Spells(0);
				}
				int[] max = new int[] {
						0,
						ccs.getLevel1Spells(),
						ccs.getLevel2Spells(),
						ccs.getLevel3Spells(),
						ccs.getLevel4Spells(),
						ccs.getLevel5Spells(),
						ccs.getLevel6Spells(),
						ccs.getLevel7Spells(),
						ccs.getLevel8Spells(),
						ccs.getLevel9Spells()
				};
				boolean tooMany = false;
				int[] memorised = memorisedMap.get(sc);
				StringBuilder sbMemorised = new StringBuilder(sc).append(" ");
				for (int i = 1, n = 10; i < n; i++) {
					if (i > 1) {
						sbMemorised.append(", ");
					}
					int count =memorised[i];
					sbMemorised.append("Lvl:").append(i).append(" ").append(count);
					int maxSpellsPerLevel =max[i];
					if(count>maxSpellsPerLevel) {
						sbMemorised.append("*");
						tooMany = true;
					}

				}
				if(tooMany) {
					sbMemorised.append(" (TOO MANY!)");
				}
				LOGGER.info(sbMemorised.toString());				
				txtMemByLevel.addItem(new CodedListItem(sc, sbMemorised.toString()));

			}

		}
	}
	
	
	private void populateAllowedSpells() {

		
		
		List<CodedListItem> clis = workOutAllowedSpells(); 		
		
		txtAllByLevel.clear();
		for(CodedListItem cli:clis) {
			txtAllByLevel.addItem(cli);
		}
		
	} 
	
	public List<CodedListItem> workOutAllowedSpells() {
		List<CodedListItem> clis = new ArrayList<>();
		
		CharacterClassSpellService ccss = (CharacterClassSpellService)getDataService(CharacterClassSpell.class); 
		try {
			List<CharacterClassSpell> as =  (ccss.getAllowedSpells(pc.getPcId()));
			for(CharacterClassSpell ccs:as) {
				allowedSpells.put(ccs.getSpellClassId(),ccs);
				int[] max = new int[] {
						0,
						ccs.getLevel1Spells(),
						ccs.getLevel2Spells(),
						ccs.getLevel3Spells(),
						ccs.getLevel4Spells(),
						ccs.getLevel5Spells(),
						ccs.getLevel6Spells(),
						ccs.getLevel7Spells(),
						ccs.getLevel8Spells(),
						ccs.getLevel9Spells()
				};

				StringBuilder sbMax = new StringBuilder(ccs.getSpellClassId()).append(" ");
				for (int i = 1, n = 10; i < n; i++) {

					int maxSpellsPerLevel =max[i];
					if(maxSpellsPerLevel>0) {
						if (i > 1) {
							sbMax.append(", ");
						}
						sbMax.append("Lvl:").append(i).append(" ").append(maxSpellsPerLevel);
					}
				}
				
				
				LOGGER.info(sbMax.toString());			
				
				CodedListItem cli = new CodedListItem(ccs.getSpellClassId(), sbMax.toString());
				clis.add(cli);
				
			
			}
			
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
		return clis;
	}

}
