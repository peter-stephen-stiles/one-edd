package com.nobbysoft.first.client.data.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.lang.invoke.MethodHandles;
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
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassSpell;

@SuppressWarnings("serial")
public class ClassSpellsDialog extends JDialog {

 
	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private Window parent;
	public ClassSpellsDialog(Window owner,String title) {
		super(owner,title,ModalityType.APPLICATION_MODAL); 
		parent=owner;
		jbInit();
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
		
		PButton btnExit = new PButton("Exit");
		btnExit.addActionListener(ae->dispose());
		

		
	}
 

	private TableCellRenderer defaultTcr = new PTableCellRenderer();
	private Map<String, TableCellRenderer> renderers = new HashMap<>();
	
private void setupTable() {
	CharacterClassSpell c = new CharacterClassSpell(); // just need a DTO to get the basics.
	String[] rh = c.getRowHeadings();
	Integer[] rw = c.getColumnWidths();
	String[] cci = c.getColumnCodedListTypes();
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
		if(cci[i]!=null) {
			
		}
		
		
	}
	
	
	/**
	 * 
	 * 
	 * brokens
	 * 
	 */
	
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

	private void populateTable() {
		
	}
	

}
