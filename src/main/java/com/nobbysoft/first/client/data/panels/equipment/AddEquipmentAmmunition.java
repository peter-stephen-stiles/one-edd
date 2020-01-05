package com.nobbysoft.first.client.data.panels.equipment;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.*;
import com.nobbysoft.first.client.components.PBasicTableWithModel.ColumnConfig;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.equipment.EquipmentType;
import com.nobbysoft.first.common.entities.equipment.WeaponAmmunition;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterEquipment;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.servicei.PlayerCharacterEquipmentService;
import com.nobbysoft.first.common.servicei.WeaponAmmunitionService;
import com.nobbysoft.first.utils.DataMapper;

public class AddEquipmentAmmunition extends PDialog implements AddEquipmentI {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public AddEquipmentAmmunition(Window owner) {
		super(owner);
		setModalityType(ModalityType.APPLICATION_MODAL);
		jbInit();
	}

	private EquipmentType type = EquipmentType.AMMUNITION;
	private boolean cancelled = true;
	private String characterName;
	private int pcId;
	private PTextField txtFilter = new PTextField();

	private final ColumnConfig[] equipmentConfigs = new ColumnConfig[] { new ColumnConfig("", 0, 0, 0),
			new ColumnConfig("Name", 20, 200, 5000), 
			new ColumnConfig("Dmg SM", 20, 100, 5000), new ColumnConfig("Dmg L", 20, 100, 5000), };

	private PBasicTableWithModel tblEquipment = new PBasicTableWithModel(equipmentConfigs);

	public void setTitleAndCharacterName(String title, int pcId, String characterName) {
		setTitle(title);
		this.characterName = characterName;
		this.pcId = pcId;
		populateTable();
		filter();
	}

	public void jbInit() {
		setLayout(new BorderLayout());
		PButtonPanel pnlButtons = new PButtonPanel();
		PButton btnCancel = new PButton("Cancel");
		PButton btnSelect = new PButton("Select");

		// list of available melee weapons
		// select one
		// whoosh
		pnlButtons.add(btnCancel);
		pnlButtons.add(btnSelect);

		btnCancel.addActionListener(ae -> {
			cancelled = true;
			dispose();
		});
		btnSelect.addActionListener(ae -> {
			//
			//
			selectCurrent();
		});

		txtFilter.setName("Filter");
		PLabel lblFilter = new PLabel(txtFilter.getName());
		PButton btnFilter = new PButton(txtFilter.getName());
		txtFilter.addActionListener(ae -> filter());
		btnFilter.addActionListener(ae -> filter());
		PPanel pnlFilter = new PPanel(new GridBagLayout());
		pnlFilter.add(lblFilter, GBU.label(0, 0));
		pnlFilter.add(txtFilter, GBU.text(1, 0));
		pnlFilter.add(btnFilter, GBU.button(2, 0));

		JScrollPane sclEquipment = new JScrollPane(tblEquipment);

		add(pnlFilter, BorderLayout.NORTH);
		add(sclEquipment, BorderLayout.CENTER);
		add(pnlButtons, BorderLayout.SOUTH);

		tblEquipment.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					btnSelect.doClick();
				}
			}

		});

	}

	DataServiceI<?,?> getDataService(Class<?> clazz) {
		DataServiceI<?,?> dao = DataMapper.INSTANCE.getDataService(clazz);
		return dao;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	private Vector<Object[]> tableData = new Vector<>();

	private void populateTable() {
		//
		WeaponAmmunitionService wms = (WeaponAmmunitionService) getDataService(WeaponAmmunition.class);

		List<WeaponAmmunition> list;
		try {
			//list = wms.getList();
			
			list = wms.getValidEquipmentForCharactersClasses(pcId);
			
		} catch (SQLException e) {
			Popper.popError(this, e);
			return;
		}
		for (WeaponAmmunition wm : list) {
			Object[] row = new Object[] { wm, wm.getName(), wm.getSMDamage(), wm.getLDamage() };
			tableData.add(row);
		}

	}

	private void filter() {
		SwingUtilities.invokeLater(() -> {
			String filter = txtFilter.getText().trim().toUpperCase();
			tblEquipment.clearTable();
			if (filter.isEmpty()) {
				for (Object[] oa : tableData) {
					tblEquipment.addRow(oa);
				}
			} else {
				for (Object[] oa : tableData) {
					String s = ((WeaponAmmunition) oa[0]).getName();
					for (int i = 1, n = oa.length; i < n; i++) {
						s = s + "|" + oa[i];
					}
					s = s.toUpperCase();
					if (s.contains(filter)) {
						tblEquipment.addRow(oa);
					}
				}
			}
		});
	}

	private void selectCurrent() {
		try {

			int rownumber = tblEquipment.getSelectedRow();
			if (tblEquipment.getSelectedRowCount() != 1) {
				Popper.popError(this, "Nothing selected :(", "Must select a row in the table to continue!");
				return;
			}
			int rowIndex = tblEquipment.convertRowIndexToModel(rownumber);
			WeaponAmmunition wm = (WeaponAmmunition) tblEquipment.getModel().getValueAt(rowIndex, 0);
			//Popper.popInfo(this, "Selected " + wm, "You selected " + wm.getName());
			/// now then

			PlayerCharacterEquipmentService wms = (PlayerCharacterEquipmentService) getDataService(
					PlayerCharacterEquipment.class);

			PlayerCharacterEquipment pce = new PlayerCharacterEquipment();
			pce.setCode(wm.getCode());
			pce.setPcId(pcId);
			pce.setEquipmentId(wms.getNextId());
			pce.setEquipmentType(wm.getType());
			pce.setEquipped(false);
			pce.setEquippedWhere(null);
			wms.insert(pce);
			cancelled = false;
			dispose();
		} catch (Exception ex) {
			Popper.popError(this, ex);
		}

	}

}
