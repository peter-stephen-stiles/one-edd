package com.nobbysoft.first.client.data.panels.pc;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PBasicTableWithModel;
import com.nobbysoft.first.client.components.PBasicTableWithModel.ColumnConfig;
import com.nobbysoft.first.client.components.PButton;
import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterHp;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterHpKey;
import com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.servicei.PlayerCharacterHpService;
import com.nobbysoft.first.common.servicei.RaceService;
import com.nobbysoft.first.common.views.ViewPlayerCharacterHp;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class PlayerCharacterHpPanel extends PPanel {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public PlayerCharacterHpPanel() {
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

	public void initialiseCharacter(PlayerCharacter pc) {
		this.pcId = pc.getPcId();
		this.characterName = pc.getCharacterName();
		this.pc = pc;

		SwingUtilities.invokeLater(() -> populateTable());
	}

	// Spell
	//

	private final PButton btnAdd = new PButton("Add XP"); 

	private PDataComponent[] buttonComponents = new PDataComponent[] { btnAdd };
//this,pcId,classId,level,hpIncrement
	private final ColumnConfig[] colConfigs = new ColumnConfig[] { new ColumnConfig("", 0, 0, 0),
			new ColumnConfig("Class", 20, 200, 5000), new ColumnConfig("Level", 20, 80, 5000),
			new ColumnConfig("Hp Increment", 20, 360, 5000) };

	private final PBasicTableWithModel tblHp = new PBasicTableWithModel(colConfigs);
 

	private void disableThings() {
		for (PDataComponent pc : disableMe) {
			pc.setReadOnly(true);
		}
	}

	private PDataComponent[] disableMe = new PDataComponent[] {};

	private void jbInit() {

		final PPanel pnlSpellButtons = new PPanel(new FlowLayout(FlowLayout.RIGHT));

		pnlSpellButtons.add(btnAdd); 
 

		JScrollPane sclSpell = new JScrollPane(tblHp);

		PPanel pnlInfo = new PPanel(new GridBagLayout());

		add(pnlSpellButtons, BorderLayout.NORTH);

		add(sclSpell, BorderLayout.CENTER);
		add(pnlInfo, BorderLayout.SOUTH);

 


		btnAdd.addActionListener(ae -> {			
			addXp();

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

	private void addXp() {
		if(pc==null) {
			Popper.popError(this, "Save first", "You have to save your character before you add XP, sorry!");
			return;
		}
		
		Race race=null;
		RaceService raceService = (RaceService)getDataService(Race.class);
		try {
			race = raceService.get(pc.getRaceId());
		} catch (SQLException e) {
			Popper.popError(this, e);
			return;
		}
		PlayerCharacterAddXpDialog add = new PlayerCharacterAddXpDialog(GuiUtils.getParent(this));
		add.setPlayerCharacter(pc, race);
		add.pack();
		add.setLocationRelativeTo(null);
		add.setVisible(true);
		if (!add.isCancelled()) {
			// refresh
			LOGGER.info("Refreshing table");
			if(pcUpdatedListener!=null) {
				pcUpdatedListener.playerCharacterUpdated(pc);
			}
			//populateTable();
		}

	}
 

 
 
  

	private void populateTable() {

		int sr = tblHp.getSelectedRow();
		ViewPlayerCharacterHp selectedObject = null;
		if (sr >= 0 && sr < tblHp.getRowCount()) {
			int modelRow = tblHp.convertRowIndexToModel(sr);
			selectedObject = (ViewPlayerCharacterHp) tblHp.getValueAt(modelRow, 0);
		}
 
		tblHp.clearTable();
		if (pcId > 0) {

			try {
				PlayerCharacterHpService pces = (PlayerCharacterHpService) getDataService(
						PlayerCharacterHp.class);
				List<ViewPlayerCharacterHp> list = pces.getViewForPC(pcId);

				for (ViewPlayerCharacterHp vpce : list) {
					//this,pcId,classId,level,hpIncrement
					tblHp.addRow(vpce.getAsRow());
				}
			} catch (Exception ex) {
				Popper.popError(this, ex);
			}
			if (selectedObject != null) {
				// LOGGER.info("selected object "+selectedObject);
				PlayerCharacterHpKey k0 = selectedObject.getKey();
				for (int i = 0, n = tblHp.getRowCount(); i < n; i++) {
					int modelRow = tblHp.convertRowIndexToModel(i);
					ViewPlayerCharacterHp value = (ViewPlayerCharacterHp) tblHp.getValueAt(modelRow, 0);
					// LOGGER.info("value "+value);
					PlayerCharacterHpKey k1 = value.getKey();
					if (k0.equals(k1)) {
						// LOGGER.info("selected "+i + " model row:"+modelRow);
						tblHp.getSelectionModel().setSelectionInterval(i, i);
						break;
					}
				}
			}
 

		}
	}
	
 

}
