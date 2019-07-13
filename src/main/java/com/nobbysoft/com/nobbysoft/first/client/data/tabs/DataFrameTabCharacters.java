package com.nobbysoft.com.nobbysoft.first.client.data.tabs;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.com.nobbysoft.first.client.components.PButtonPanel;
import com.nobbysoft.com.nobbysoft.first.client.components.PCodedListCellRenderer;
import com.nobbysoft.com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.com.nobbysoft.first.client.components.PTable;
import com.nobbysoft.com.nobbysoft.first.client.components.PTableCellRenderer;
import com.nobbysoft.com.nobbysoft.first.client.data.MaintenanceDialog;
import com.nobbysoft.com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.com.nobbysoft.first.client.data.panels.CharacterRoller;
import com.nobbysoft.com.nobbysoft.first.client.data.panels.PlayerCharacterPanel;
import com.nobbysoft.com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.com.nobbysoft.first.utils.DataMapper;

public class DataFrameTabCharacters extends PPanel {
	

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass()); 

	 
	private DefaultTableModel tmData = new DefaultTableModel();
	private PTable tblData = new PTable(tmData);

	private JTextField txtFilter = new JTextField();

	private final PButtonPanel pnlDataButtons = new PButtonPanel();
	
	
	
	
	public DataFrameTabCharacters() {
		jbInit();
	}
	
	private void jbInit() {
		
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				refresh();
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});


		PButtonPanel pnlTopButtons = new PButtonPanel();
		JButton btnRefresh = new JButton("Refresh");
		JButton btnAdd = new JButton("Add");
		JButton btnCopy = new JButton("Copy");
		JButton btnEdit = new JButton("Edit");
		JButton btnDelete = new JButton("Delete");

		pnlTopButtons.add(btnAdd);
		pnlTopButtons.add(btnCopy);
		pnlTopButtons.add(btnEdit);
		pnlTopButtons.add(btnDelete);
		GridBagLayout layTop = new GridBagLayout();

		btnRefresh.addActionListener(ae -> refresh());
		btnAdd.addActionListener(ae -> add());
		btnCopy.addActionListener(ae -> copy());
		btnEdit.addActionListener(ae -> edit());
		btnDelete.addActionListener(ae -> delete());	
		
		JPanel pnlTop = new JPanel(layTop); 
		pnlTop.add(pnlTopButtons, GBU.label(1, 0));
		PPanel pnlFilter = new PPanel();
		pnlTop.add(pnlFilter, GBU.panel(0, 1, 10, 1));
		pnlFilter.add(new PLabel("Filter"), BorderLayout.WEST);
		pnlFilter.add(txtFilter, BorderLayout.CENTER);
		pnlFilter.add(btnRefresh, BorderLayout.EAST);

		JScrollPane sclData = new JScrollPane(tblData);
		add(pnlTop, BorderLayout.NORTH);
		PPanel pnlData = new PPanel();
		pnlData.add(sclData);
		pnlData.add(pnlDataButtons,BorderLayout.SOUTH);
		add(pnlData, BorderLayout.CENTER);


		txtFilter.addActionListener(ae ->{
			btnRefresh.doClick();
		});
		
		tblData.setAutoResizeMode(PTable.AUTO_RESIZE_LAST_COLUMN);
		

//		tblData.getSelectionModel().addListSelectionListener(se ->{
////			if(!se.getValueIsAdjusting()) {
////				enableRowButtons(tblData.getSelectedRowCount()>0);
////			}
//			
//		});
		
		tblData.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					edit();
				}

			}

		});

 

 
		
		
//		PButtonPanel btnPanel = new PButtonPanel ();
//		
//		
//		PButton btnRoll = new PButton("Roll new character");
//		
//		
//		btnPanel.add(btnRoll);
//		
//
//		btnRoll.addActionListener(ae ->{
//			rollingRollingRolling();
//		});
//		
//		add(btnPanel,BorderLayout.NORTH);
	}

	
	
	private void rollingRollingRolling() {
		LOGGER.info("Rolling");
		Window w= GuiUtils.getParent(this) ;
		CharacterRoller cr = new CharacterRoller(w,"Roll new character");
		cr.pack();		
		cr.setLocationRelativeTo(null);
		cr.setVisible(true);
	}
	
	
	public void clearTable(boolean needNewStructure) {
		while (tmData.getRowCount() > 0) {
			tmData.removeRow(0);
		}
	}
	
	private boolean first=true;
	
	private void populateTable() {

		try {
			boolean needNewStructure = false;

			int selectedRow = tblData.getSelectedRow();

			Class c = PlayerCharacter.class;
			if (first) {
				needNewStructure = true;
				first=false;
				selectedRow = -1;
			}
			
			{
				// populateTable

				
				Class d = DataMapper.INSTANCE.getServiceForEntity(c);
				Class bd = DataMapper.INSTANCE.getButtonClass(c);
				if (d != null) {
					Constructor<DataServiceI> cc = d.getConstructor();

					DataServiceI dao = (DataServiceI) cc.newInstance();
					clearTable(needNewStructure);

					try {

						List<PlayerCharacter > data = dao.getFilteredList(txtFilter.getText().trim());
						boolean first = true;
						for (PlayerCharacter  dd : data) {
							if (first) {
								if (needNewStructure) {
									columns(dd);
								}
								first = false;
							}
							tmData.addRow(dd.getAsRow());
						}
						if (selectedRow >= 0 && selectedRow <= tblData.getRowCount()) {
							if (selectedRow == tblData.getRowCount()) {
								selectedRow--;
							}
							tblData.selectRow(selectedRow);
						}
					} catch (Exception ex) {
						Popper.popError(this, ex);
						return;
					}
				}

			}
		} catch (Exception ex) {
			Popper.popError(this, ex);
		}
	}

	private TableCellRenderer defaultTcr = new PTableCellRenderer();
	private Map<String, TableCellRenderer> renderers = new HashMap<>(); 

	private void columns(PlayerCharacter  d) {

		LOGGER.info("setting columns");
		tmData.addColumn("");
		String[] h = d.getRowHeadings();
		for (String s : h) {
			tmData.addColumn(s);
		}
		TableColumnModel tcm = tblData.getColumnModel();
		TableColumn fc = tcm.getColumn(0);
		fc.setWidth(0);
		fc.setMinWidth(0);
		fc.setPreferredWidth(0);
		fc.setMaxWidth(0);
		fc.setWidth(0);
		Integer[] w = d.getColumnWidths();
		String[] clis = d.getColumnCodedListTypes();
		for (int i = 1, n = w.length; i <= n; i++) {
			int width = w[i - 1];
			//LOGGER.info("setting column " + i + " width " + width);
			TableColumn tc = tcm.getColumn(i);
			tc.setMinWidth(20);
			if (width >= 0) {
				tc.setWidth(width);
				tc.setPreferredWidth(width);
			} else {
				//LOGGER.info("no width set");
			}
			tc.setMaxWidth(5000);
			TableCellRenderer tcr = defaultTcr;
			if (clis.length > (i - 1)) {
				String cli = clis[i - 1];
				if (cli != null) {
					if (renderers.containsKey(cli)) {
						tcr = renderers.get(cli);
					} else {
						tcr = new PCodedListCellRenderer(cli);
						renderers.put(cli, tcr);
					}
				}
			}
			tc.setCellRenderer(tcr);
		}

	}
	private void refresh() {
		populateTable();
	}
	
	private void add() {
		// get panel for current class and instantiate one
		Class<?> mpc = PlayerCharacterPanel.class;
		if (mpc != null) {
			MaintenancePanelInterface mpi;
			try {
				Constructor cc = mpc.getConstructor();
				mpi = (MaintenancePanelInterface) cc.newInstance();
			} catch (Exception e) {
				Popper.popError(this, e);
				return;
			} 
			mpi.initAdd("Add " + DataMapper.INSTANCE.getName(PlayerCharacter.class));
			MaintenanceDialog md = new MaintenanceDialog(getWindow(), "Add", mpi);
			md.pack();
			md.setLocationRelativeTo(null);
			md.setVisible(true);
			populateTable();

		}

	}
	
	private void edit() {
		int r = tblData.getSelectedRow();
		if (r < 0 && tblData.getRowCount() > 0) {
			r = 0;
			tblData.selectRow(r);
		}
		if (r >= 0 && r < tblData.getRowCount()) {
			//
			PlayerCharacter dto = (PlayerCharacter) tmData.getValueAt(r, 0);
			if (dto != null) {
				// get panel for current class and instantiate one
				Class<?> mpc = PlayerCharacterPanel.class;
				if (mpc != null) {
					MaintenancePanelInterface<PlayerCharacter> mpi;
					try {
						Constructor cn = mpc.getConstructor();
						mpi = (MaintenancePanelInterface<PlayerCharacter>) cn.newInstance();
					} catch (Exception e) {
						Popper.popError(this, e);
						return;
					} 
					mpi.initUpdate(dto, "Edit " + DataMapper.INSTANCE.getName(PlayerCharacter.class));
					MaintenanceDialog md = new MaintenanceDialog(getWindow(), "Edit", mpi);
					md.pack();
					md.setLocationRelativeTo(null);
					md.setVisible(true);
					populateTable();

				}
			}
		}
	}

	private void delete() {
		int r = tblData.getSelectedRow();
		if (r >= 0 && r < tblData.getRowCount()) {
			//
			PlayerCharacter dto = (PlayerCharacter) tmData.getValueAt(r, 0);
			if (dto != null) {
				// get panel for current class and instantiate one
				Class<?> mpc = PlayerCharacterPanel.class;
				if (mpc != null) {
					MaintenancePanelInterface<PlayerCharacter> mpi;
					try {
						Constructor cn = mpc.getConstructor();
						mpi = (MaintenancePanelInterface<PlayerCharacter>) cn.newInstance();
					} catch (Exception e) {
						Popper.popError(this, e);
						return;
					} 
					mpi.initDelete(dto, "Delete " + DataMapper.INSTANCE.getName(PlayerCharacter.class));
					MaintenanceDialog md = new MaintenanceDialog(getWindow(), "Delete", mpi);
					md.pack();
					md.setLocationRelativeTo(null);
					md.setVisible(true);
					populateTable();

				}
			}
		}
	}

	private void copy() {
		if(tblData.getRowCount()==0) {
			return;
		}
		int r = tblData.getSelectedRow();
		if (r < 0 && tblData.getRowCount() > 0) {
			r = 0;
			tblData.selectRow(r);
		}
		if (r >= 0 && r < tblData.getRowCount()) {
			//
			PlayerCharacter dto = (PlayerCharacter) tmData.getValueAt(r, 0);
			if (dto != null) {
				// get panel for current class and instantiate one
				Class<?> mpc = PlayerCharacterPanel.class;
				if (mpc != null) {
					MaintenancePanelInterface<PlayerCharacter> mpi;
					try {
						Constructor cn = mpc.getConstructor();
						mpi = (MaintenancePanelInterface<PlayerCharacter>) cn.newInstance();
					} catch (Exception e) {
						Popper.popError(this, e);
						return;
					} 
					mpi.initCopy(dto, "Add " + DataMapper.INSTANCE.getName(PlayerCharacter.class));
					MaintenanceDialog md = new MaintenanceDialog(getWindow(), "Add", mpi);
					md.pack();
					md.setLocationRelativeTo(null);
					md.setVisible(true);
					populateTable();

				}
			}
		}
	}
	
	
	
}
