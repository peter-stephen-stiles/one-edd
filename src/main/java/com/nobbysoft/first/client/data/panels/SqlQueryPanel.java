package com.nobbysoft.first.client.data.panels;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.invoke.MethodHandles;
import java.sql.ResultSetMetaData;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PButton;
import com.nobbysoft.first.client.components.PButtonPanel;
import com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.first.client.components.PTable;
import com.nobbysoft.first.client.components.PTextArea;
import com.nobbysoft.first.client.utils.Utils;
import com.nobbysoft.first.common.servicei.SqlService;
import com.nobbysoft.first.common.utils.ResultSetListener;
import com.nobbysoft.first.utils.DataMapper;

public class SqlQueryPanel extends PPanel {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass()); 
	public SqlQueryPanel() {
		super();
		jbInit();
	}
	private Set<ActionListener> listeners = new HashSet<>();
	public void addActionListener(ActionListener al) {
		listeners.add(al);
	}
	public void removeActionListener(ActionListener al) {
		listeners.remove(al);
	}
	private DefaultTableModel tmData = new DefaultTableModel();
	private PTable tblData = new PTable(tmData);
	private PTextArea txtEditor = new PTextArea();
	private PTextArea txtDetails = new PTextArea();
	private PTextArea txtError = new PTextArea();

	private JTabbedPane pnlData = new JTabbedPane();
	private PButton btnQuery = new PButton("Query!");
	private PButton btnUpdate = new PButton("Update!");
	
	private void jbInit() {

		JPopupMenu popup = new JPopupMenu();
		JMenuItem mnuCloseTab = new JMenuItem("Close tab");
		popup.add(mnuCloseTab);
		this.add(popup);

		mnuCloseTab.addActionListener(ae->{
			ActionEvent event = new ActionEvent(this, 1, "CLOSETAB", 0, 0);
			for(ActionListener al:listeners) {
				al.actionPerformed(event);
			}
		});
		PButtonPanel pnlButtons = new PButtonPanel();
		pnlButtons.add(btnQuery);
		pnlButtons.add(btnUpdate);
		add(pnlButtons,BorderLayout.NORTH);
		btnQuery.addActionListener(ae->{execute(false);});
		btnUpdate.addActionListener(ae->{execute(true);});
		
		JScrollPane sclEditor = new JScrollPane(txtEditor);
		

		JScrollPane sclData = new JScrollPane(tblData);

		JScrollPane sclError = new JScrollPane(txtError);
		JScrollPane sclDetails = new JScrollPane(txtDetails);

		pnlData.addTab("Data", sclData);
		pnlData.addTab("Details", sclDetails);
		pnlData.addTab("Error", sclError);
		
		JSplitPane splPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,sclEditor,pnlData);
		splPane.setDividerLocation(0.5d);
		add(splPane,BorderLayout.CENTER);
		
		SwingUtilities.invokeLater(()->{
			splPane.setDividerLocation(150);
		});
		
	}
	
	private void execute(boolean update) {
		String sql = txtEditor.getText().trim();
		if(sql.isEmpty()) {
			pnlData.getModel().setSelectedIndex(2);
			txtError.setText("You haven't entered any SQL!");
			return;
		}

		btnQuery.setEnabled(false);
		btnUpdate.setEnabled(false);
		try {
			
		Object sqlo = DataMapper.INSTANCE.getNonDataService(SqlService.class);
		SqlService sqlService = (SqlService)sqlo;
		
		if(update) {
			sqlService.runUpdate(sql);
			btnQuery.setEnabled(true);
			btnUpdate.setEnabled(true);
		} else {
		
		tmData.setColumnCount(0);
		tblData.clearData();
		sqlService.runSql(sql, new ResultSetListener() {
	
				@Override
				public void haveARow(int count, Object[] data) {
					LOGGER.info("here row:"+data);
					tmData.addRow(data);
					
				}
	
				@Override
				public void haveTheMetaData(ResultSetMetaData metadata) {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void finished(){
	
					pnlData.getModel().setSelectedIndex(0);
					btnQuery.setEnabled(true);
					btnUpdate.setEnabled(true);
				}
				@Override
				public void haveTheColumnLabels(String[] labels) {
					for(int i=0,n=labels.length;i<n;i++) {
						String label = labels[i];
						tmData.addColumn(label);
					}
					
				}
				
			});
		}
		} catch (Exception ex) {
			pnlData.getModel().setSelectedIndex(2);
			txtError.setText(Utils.stackTrace(ex));
			btnQuery.setEnabled(true);
			btnUpdate.setEnabled(true);
		} finally {
		}
	}

}