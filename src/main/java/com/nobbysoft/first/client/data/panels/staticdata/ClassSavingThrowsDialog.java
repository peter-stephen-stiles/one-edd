package com.nobbysoft.first.client.data.panels.staticdata;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PButton;
import com.nobbysoft.first.client.components.PButtonPanel;
import com.nobbysoft.first.client.components.PCodedListCellRenderer;
import com.nobbysoft.first.client.components.PTable;
import com.nobbysoft.first.client.components.PTableCellRenderer;
import com.nobbysoft.first.client.data.MaintenanceDialog;
import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.staticdto.SavingThrow;
import com.nobbysoft.first.common.entities.staticdto.SavingThrowKey;
import com.nobbysoft.first.common.servicei.CharacterClassService;
import com.nobbysoft.first.common.servicei.CharacterClassToHitService;
import com.nobbysoft.first.common.servicei.SavingThrowService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class ClassSavingThrowsDialog extends JDialog {

 
	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private Window parent;
	public ClassSavingThrowsDialog(Window owner,String title) {
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
		
		tblData.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
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
		PButton btnNext = new PButton("Next");
		PButton btnEdit = new PButton("Edit");
		PButton btnDelete = new PButton("Delete");

		PButton btnCopyAll = new PButton("Copy all from another class");
		pnlTop.add(btnAdd);
		pnlTop.add(btnCopy);
		pnlTop.add(btnNext);
		pnlTop.add(btnEdit);
		pnlTop.add(btnDelete);
		pnlTop.add(btnCopyAll);
		btnAdd.addActionListener(ae -> add());
		btnCopy.addActionListener(ae -> copy());
		btnNext.addActionListener(ae -> next());
		btnEdit.addActionListener(ae -> edit());
		btnDelete.addActionListener(ae -> delete());
		btnCopyAll.addActionListener(ae -> copyAllFromAnotherClass());
		
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
	SavingThrow c = new SavingThrow(); // just need a DTO to get the basics.
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

SavingThrowService getDataService() {
	SavingThrowService dao  = (SavingThrowService)DataMapper.INSTANCE.getDataService(SavingThrow.class);
	return dao;
}

	private void populateTable() {

		int r = tblData.getSelectedRow();
		
		SavingThrowService service = getDataService();
		String filter = characterClass.getClassId();
		tblData.clearData();
		try {
			List<SavingThrow> list = service.getFilteredList(filter);
			for(SavingThrow savingThrow:list) {
				tmData.addRow(savingThrow.getAsRow());
			}
		} catch (SQLException e) {
			Popper.popError(this,e);
		}
		if(r<tblData.getRowCount() && r >=0) {
			tblData.getSelectionModel().addSelectionInterval(r, r);
		}
		
	}


	private void add() {
		// get panel for current class and instantiate one
		 {
			ClassSavingThrowEditDialog mpi = new ClassSavingThrowEditDialog();
			mpi.setParent(characterClass);
			mpi.initAdd("Add Saving Throw for "+characterClass.getName() );

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
			SavingThrow dto = (SavingThrow) tmData.getValueAt(r, 0);
			if (dto != null) { 
				{
					ClassSavingThrowEditDialog mpi = new ClassSavingThrowEditDialog();
					mpi.setParent(characterClass);
					mpi.initUpdate(dto, "Edit Saving Throw for "+characterClass.getName() );
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
		int[] rs = tblData.getSelectedRows();
		int i=0;
		for(int r:rs) {
			i++;
			//
			SavingThrow dto = (SavingThrow) tmData.getValueAt(r, 0);
			if (dto != null) {
				// get panel for current class and instantiate one
				 {
					ClassSavingThrowEditDialog mpi = new ClassSavingThrowEditDialog();
					((ClassSavingThrowEditDialog)mpi).setParent(characterClass);
					mpi.initDelete(dto, "Delete Saving Throw for "+characterClass.getName() + "( #"+i+" of "+rs.length+")");
					MaintenanceDialog md = new MaintenanceDialog(getWindow(), "Delete", mpi);
					md.pack();
					md.setLocationRelativeTo(null);
					md.setVisible(true);

				}
			}
		}

		populateTable();
	}

	private void next() {
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
			SavingThrow dto = (SavingThrow) tmData.getValueAt(r, 0);
			if (dto != null) {
				// get panel for current class and instantiate one
				 {
					ClassSavingThrowEditDialog mpi = new ClassSavingThrowEditDialog();
					mpi.setParent(characterClass);
					int diff = dto.getToLevel() - dto.getFromLevel();
					dto.setFromLevel(dto.getToLevel()+1);
					dto.setToLevel(dto.getFromLevel()+diff);
					
					mpi.initCopy(dto, "Add Saving Throw for "+characterClass.getName());
					MaintenanceDialog md = new MaintenanceDialog(getWindow(), "Add", mpi);
					md.pack();
					md.setLocationRelativeTo(null);
					md.setVisible(true);					
					populateTable();
					int sr=tblData.getSelectedRow();
					tblData.clearSelection();
					tblData.addRowSelectionInterval(sr+1, sr+1);
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
			SavingThrow dto = (SavingThrow) tmData.getValueAt(r, 0);
			if (dto != null) {
				// get panel for current class and instantiate one
				 {
					ClassSavingThrowEditDialog mpi = new ClassSavingThrowEditDialog();
					mpi.setParent(characterClass);
					mpi.initCopy(dto, "Add Saving Throw for "+characterClass.getName());
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

	
	private void copyAllFromAnotherClass() {
		// need to pick another class!
		CharacterClassService ccsDao = (CharacterClassService)DataMapper.INSTANCE.getDataService(CharacterClass.class);

		List<CodedListItem<String>> classes;
		try {
			classes = ccsDao.getAsCodedList();
		} catch (SQLException e) {
			Popper.popError(this, e);
			return;
		}
		CodedListItem<String> thisCharacterClass = new CodedListItem<String>();
		thisCharacterClass.setItem(characterClass.getClassId());
		thisCharacterClass.setDescription(characterClass.getDescription());
		
		classes.remove(thisCharacterClass);
		CodedListItem<String>[] ccia = new CodedListItem[classes.size()];
		CodedListItem<String>[] possibilities = classes.toArray(ccia);
		@SuppressWarnings("unchecked")
		CodedListItem<String> s = (CodedListItem<String>)JOptionPane.showInputDialog(
		                    GuiUtils.getParent(this),
		                    "Select class to copy from ",
		                    "Copy to-hit from another class",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    possibilities,
		                    possibilities[0]);

		//If a string was returned, say so.
		if ((s != null) ) {
			SavingThrowService thDao = getDataService();
			try {
				ReturnValue<Integer> ret = thDao.copyFrom(s.getItem(),characterClass.getClassId());
				if(ret.isError()) {
					Popper.popError(this, "Error copying", ret);
				}
			} catch (SQLException e) {
				Popper.popError(this, e);
				return;
			}
			// all good!
			populateTable();
		}

		
	
	}
 
	
}
