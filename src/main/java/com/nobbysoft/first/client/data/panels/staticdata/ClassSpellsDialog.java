package com.nobbysoft.first.client.data.panels.staticdata;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.htmlcleaner.Utils;

import com.nobbysoft.first.client.components.*;
import com.nobbysoft.first.client.components.PBasicTableWithModel.ColumnConfig;
import com.nobbysoft.first.client.data.MaintenanceDialog;
import com.nobbysoft.first.client.data.MaintenancePanelInterface;
import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassSpell;
import com.nobbysoft.first.common.servicei.CharacterClassSpellService;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class ClassSpellsDialog extends JDialog {

 
	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private Window parent;
	public ClassSpellsDialog(Window owner,String title) {
		super(owner,title,ModalityType.APPLICATION_MODAL); 
		parent=owner;
		jbInit();
	}	
	public Dimension getPreferredSize() {
		Dimension d = super.getPreferredSize();
		LOGGER.info("ps:"+d);
		if(d.width<710) {
			d.width=710;
		}
		return d;
	}
	
	private CharacterClass characterClass;
	
	public void initialise(CharacterClass characterClass) {
	this.characterClass=characterClass;
		setupTable();
		populateTable();
	}
 
	private final DefaultTableModel tmData =  new DefaultTableModel();
	private final PTable tblData = new PTable(tmData);
	
	private void jbInit() {
		setLayout(new BorderLayout(5,5));
		PButtonPanel pnlTop = new PButtonPanel();
		PButtonPanel pnlBottom = new PButtonPanel();
		JScrollPane sclData = new JScrollPane(tblData) {
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				if(d.height<150) {
					d.height=150;
				}
				if(d.width<150) {
					d.width=150;
				}
				return d;
			}
		};
		add(pnlTop,BorderLayout.NORTH);
		add(sclData,BorderLayout.CENTER);
		add(pnlBottom,BorderLayout.SOUTH);
		
		PButton btnAdd = new PButton("Add");
		PButton btnCopy = new PButton("Copy");
		PButton btnEdit = new PButton("Edit");
		PButton btnDelete = new PButton("Delete");
		pnlTop.add(btnAdd);
		pnlTop.add(btnCopy);
		pnlTop.add(btnEdit);
		pnlTop.add(btnDelete);
		btnAdd.addActionListener(ae -> add());
		btnCopy.addActionListener(ae -> copy());
		btnEdit.addActionListener(ae -> edit());
		btnDelete.addActionListener(ae -> delete());	
		
		PButton btnExit = new PButton("Exit");
		btnExit.addActionListener(ae->dispose());
		
		tblData.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					btnEdit.doClick();
				}
				
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {} 

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) { }
			
		});
		
	}
 

	private PTableCellRenderer defaultTcr = new PTableCellRenderer();
	private Map<String, TableCellRenderer> renderers = new HashMap<>();
	
private void setupTable() {
	CharacterClassSpell c = new CharacterClassSpell(); // just need a DTO to get the basics.
	String[] rh = c.getRowHeadings();
	Integer[] rw = c.getColumnWidths();
	String[] cci = c.getColumnCodedListTypes();
		
	int wid = 0;
	
	tmData.setColumnIdentifiers(rh);
	for(int i=0,n=rh.length;i<n;i++) {
		TableColumn tc = tblData.getColumn(rh[i]);
		int w = rw[i];
		if(w==0) {
			tc.setMinWidth(0);
			tc.setMaxWidth(0);
			tc.setPreferredWidth(0);
		} else {
			tc.setMinWidth(20);
			tc.setMaxWidth(w);
			tc.setPreferredWidth(5000);
		}
		wid+=tc.getPreferredWidth();
		if(cci[i]!=null) {
			
		}
		
	
	}
	
	
	/**
	 * 
	 * 
	 * brokens
	 * 
	 */
	defaultTcr.setZerosAsBlanks(true);
	for (int i = 1, n = rh.length; i < n; i++) {
		LOGGER.info("column "+i);
		
		TableColumn tc = tblData.getColumnModel().getColumn(i);
 
		TableCellRenderer tcr = defaultTcr;
		
		if (cci.length > (i - 1)) {
			String cli = cci[i - 1];
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

CharacterClassSpellService getDataService() {
	CharacterClassSpellService dao  = (CharacterClassSpellService)DataMapper.INSTANCE.getDataService(CharacterClassSpell.class);
	return dao;
}

	private void populateTable() {

		CharacterClassSpellService service = getDataService();
		String filter = characterClass.getClassId();
		tblData.clearData();
		try {
			List<CharacterClassSpell> list = service.getFilteredList(filter);
			for(CharacterClassSpell spell:list) {
				tmData.addRow(spell.getAsRow());
			}
		} catch (SQLException e) {
			Popper.popError(this,e);
		}
		
		
	}


	private void add() {
		// get panel for current class and instantiate one
		 {
			ClassSpellsEditDialog mpi = new ClassSpellsEditDialog();
			mpi.setParent(characterClass);
			mpi.initAdd("Add spells for "+characterClass.getName() );
			mpi.defaultAdd(1+getMaxLevel(characterClass.getClassId()));
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
			CharacterClassSpell dto = (CharacterClassSpell) tmData.getValueAt(r, 0);
			if (dto != null) { 
				{
					ClassSpellsEditDialog mpi = new ClassSpellsEditDialog();
					mpi.setParent(characterClass);
					mpi.initUpdate(dto, "Edit spells for "+characterClass.getName() );
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
			CharacterClassSpell dto = (CharacterClassSpell) tmData.getValueAt(r, 0);
			if (dto != null) {
				// get panel for current class and instantiate one
				 {
					ClassSpellsEditDialog mpi = new ClassSpellsEditDialog();
					((ClassSpellsEditDialog)mpi).setParent(characterClass);
					mpi.initDelete(dto, "Delete spells for "+characterClass.getName() );
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
			CharacterClassSpell dto = (CharacterClassSpell) tmData.getValueAt(r, 0);
			if (dto != null) {
				// get panel for current class and instantiate one
				 {
					ClassSpellsEditDialog mpi = new ClassSpellsEditDialog();
					mpi.setParent(characterClass);
					mpi.initCopy(dto, "Add spells for "+characterClass.getName());
					MaintenanceDialog md = new MaintenanceDialog(getWindow(), "Add", mpi);
					md.pack();
					md.setLocationRelativeTo(null);
					md.setVisible(true);
					populateTable();

				}
			}
		}
	}

	public Window getWindow() {
		return GuiUtils.getParent(this);
	}

	private int getMaxLevel(String classId) {
		CharacterClassSpellService service = getDataService();
		
		try{
			return service.getMaxSpellLevelInTable(classId)	;
		} catch (SQLException e) {
			Popper.popError(this,e);
		}
		return 1;
	}
	
}
