package com.nobbysoft.first.client.data.tabs;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PButton;
import com.nobbysoft.first.client.components.PButtonPanel;
import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.first.client.components.PTable;
import com.nobbysoft.first.client.components.TableUtils;
import com.nobbysoft.first.client.data.MaintenanceDialog;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.data.panels.ButtonActioner;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.utils.DataMapper;

public class DataFrameTabData extends PPanel{


	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass()); 
	
	private PComboBox<Class<?>> cbxData = new PComboBox<>();
	private DefaultTableModel tmData = new DefaultTableModel();
	private PTable tblData = new PTable(tmData);

	private TableUtils tableUtils = new TableUtils();
	private JTextField txtFilter = new JTextField();

	private final PButtonPanel pnlDataButtons = new PButtonPanel();
	
	
	public DataFrameTabData() {
		jbInit();
	}
	

	private void jbInit() {
		
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
		pnlTop.add(cbxData, GBU.text(0, 0));
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

		populateComboBox();

		tblData.getSelectionModel().addListSelectionListener(se ->{
			if(!se.getValueIsAdjusting()) {
				if(buttonActioner!=null) {
					buttonActioner.enableRowButtons(tblData.getSelectedRowCount()>0);
				}
			}
			
		});
		
		tblData.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					edit();
				}

			}

		});

		cbxData.addActionListener(ae -> {
			try {
				populateTable();
			} catch (Exception e1) {
				Popper.popError(this, e1);
			}
		});
		

		cbxData.setMaximumRowCount(20);
		
	}


	private void populateComboBox() {
		
	for(Class clazz:	DataMapper.INSTANCE.getDTOs()){
		cbxData.addItem(clazz);
	}
		

		populateTable();
	}

	private Class last = null;
	//private DataButtonsInterface dbi = null;
//
//	private void enableRowButtons(boolean enable) {
//		SwingUtilities.invokeLater(() ->{
//			for(PButton button:rowButtons) {
//				button.setEnabled(enable);
//			} 
//	});
//	}
	
	//private List<PButton> rowButtons = new ArrayList<>();
	//private List<PButton> tableButtons = new ArrayList<>();
	private ActionListener rowButtonListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(buttonActioner==null) {
				LOGGER.info("tried to press button but it didn't work (1)");
				return;
			}
			if (!buttonActioner.haveDBI()) {
				LOGGER.info("tried to press button but it didn't work (2)");
				return;
			}
			PButton source = (PButton) e.getSource();
			String name = e.getActionCommand();

			int r = tblData.getSelectedRow();
			if (r < 0 && tblData.getRowCount() > 0) {
				r = 0;
				tblData.selectRow(r);
			}
			if (r >= 0 && r < tblData.getRowCount()) {
				DataDTOInterface dto = (DataDTOInterface) tmData.getValueAt(r, 0);
				boolean refresh=buttonActioner.doRowButton(getWindow(),name, dto);
				if(refresh) {
					populateTable();
				}
			}

		}

	};


	
	private ActionListener tableButtonListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(buttonActioner==null) {
				LOGGER.info("tried to press button but it didn't work (3)");
				return;
			}
			if (!buttonActioner.haveDBI()) {
				LOGGER.info("tried to press button but it didn't work (4)");
				return;
			}
			PButton source = (PButton) e.getSource();
			String name = e.getActionCommand();
			boolean refresh=buttonActioner.doTableButton(getWindow(),name);
			if(refresh) {
				populateTable();
			}
		}

	};

	
	private ButtonActioner buttonActioner;
	
	private void buttons(Class<?> bd) {
		if(buttonActioner==null) {
			buttonActioner=new ButtonActioner(getWindow(), pnlDataButtons, rowButtonListener, tableButtonListener);
		}
		buttonActioner.buttons(bd);
	}


	private void populateTable() {

		try {
			boolean needNewStructure = false;

			int selectedRow = tblData.getSelectedRow();

			Class c = (Class) cbxData.getSelectedItem();
			if (last == null || !last.equals(c) || tblData.getRowCount() == 0) {
				needNewStructure = true;
				last = c;
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

						List<DataDTOInterface<?>> data = dao.getFilteredList(txtFilter.getText().trim());
						boolean first = true;
						for (DataDTOInterface<?> dd : data) {
							if (first) {
								if (needNewStructure) {
									columns(dd);
									buttons(bd);
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


	private void columns(DataDTOInterface<?> d) {
		tableUtils.columns(d,tmData,tblData);
	}
		


	public void clearTable(boolean needNewStructure) {
		while (tmData.getRowCount() > 0) {
			tmData.removeRow(0);
		}
		if (needNewStructure) {
			tmData.setColumnCount(0);
		}
	}

	private void refresh() {
		populateTable();
	}

	private void add() {
		// get panel for current class and instantiate one
		Class<?> mpc = DataMapper.INSTANCE.getMaintenancePanel((Class) cbxData.getSelectedItem());
		if (mpc != null) {
			MaintenancePanelInterface mpi;
			try {
				Constructor cc = mpc.getConstructor();
				mpi = (MaintenancePanelInterface) cc.newInstance();
			} catch (Exception e) {
				Popper.popError(this, e);
				return;
			} 
			mpi.initAdd("Add " + DataMapper.INSTANCE.getName((Class) cbxData.getSelectedItem()));
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
			DataDTOInterface dto = (DataDTOInterface) tmData.getValueAt(r, 0);
			if (dto != null) {
				// get panel for current class and instantiate one
				Class<?> mpc = DataMapper.INSTANCE.getMaintenancePanel((Class) cbxData.getSelectedItem());
				if (mpc != null) {
					MaintenancePanelInterface<DataDTOInterface> mpi;
					try {
						Constructor cn = mpc.getConstructor();
						mpi = (MaintenancePanelInterface<DataDTOInterface>) cn.newInstance();
					} catch (Exception e) {
						Popper.popError(this, e);
						return;
					} 
					mpi.initUpdate(dto, "Edit " + DataMapper.INSTANCE.getName((Class) cbxData.getSelectedItem()));
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
			DataDTOInterface dto = (DataDTOInterface) tmData.getValueAt(r, 0);
			if (dto != null) {
				// get panel for current class and instantiate one
				Class<?> mpc = DataMapper.INSTANCE.getMaintenancePanel((Class) cbxData.getSelectedItem());
				if (mpc != null) {
					MaintenancePanelInterface<DataDTOInterface> mpi;
					try {
						Constructor cn = mpc.getConstructor();
						mpi = (MaintenancePanelInterface<DataDTOInterface>) cn.newInstance();
					} catch (Exception e) {
						Popper.popError(this, e);
						return;
					} 
					mpi.initDelete(dto, "Delete " + DataMapper.INSTANCE.getName((Class) cbxData.getSelectedItem()));
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
			DataDTOInterface dto = (DataDTOInterface) tmData.getValueAt(r, 0);
			if (dto != null) {
				// get panel for current class and instantiate one
				Class<?> mpc = DataMapper.INSTANCE.getMaintenancePanel((Class) cbxData.getSelectedItem());
				if (mpc != null) {
					MaintenancePanelInterface<DataDTOInterface> mpi;
					try {
						Constructor cn = mpc.getConstructor();
						mpi = (MaintenancePanelInterface<DataDTOInterface>) cn.newInstance();
					} catch (Exception e) {
						Popper.popError(this, e);
						return;
					} 
					mpi.initCopy(dto, "Add " + DataMapper.INSTANCE.getName((Class) cbxData.getSelectedItem()));
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
