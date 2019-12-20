package com.nobbysoft.first.client.data.panels.sql;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PButton;
import com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.first.client.components.PTable;
import com.nobbysoft.first.client.components.PTextArea;
import com.nobbysoft.first.client.components.TableUtils;
import com.nobbysoft.first.common.entities.DataDTOInterface;

@SuppressWarnings("serial")
public class SqlHistoryPanel extends PPanel implements SqlPanelInterface {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public SqlHistoryPanel() {
		super();
		jbInit();

	}

	private TableUtils tableUtils = new TableUtils();

	private Set<ActionListener> listeners = new HashSet<>();

	public void addActionListener(ActionListener al) {
		listeners.add(al);
	}

	public void removeActionListener(ActionListener al) {
		listeners.remove(al);
	}
 
	private DefaultTableModel tmTables = new DefaultTableModel();
	private PTable tblTables = new PTable(tmTables);
 
 
	private void jbInit() {

		tblTables.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblTables.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);


		
		
		PButton btnRefresh = new PButton("Refresh");

		PPanel pnlFilter = new PPanel(new FlowLayout(FlowLayout.LEFT));

		pnlFilter.add(btnRefresh);
		add(pnlFilter, BorderLayout.NORTH);
		JPopupMenu popup = new JPopupMenu();
		JMenuItem mnuCloseTab = new JMenuItem("Close tab");
		popup.add(mnuCloseTab);
		this.add(popup);
		btnRefresh.addActionListener(ex -> populateTables());
		mnuCloseTab.addActionListener(ae -> {
			ActionEvent event = new ActionEvent(this, 1, "CLOSETAB", 0, 0);
			for (ActionListener al : listeners) {
				al.actionPerformed(event);
			}
		});

		JScrollPane sclTables = new JScrollPane(tblTables);
 
		
		final PTextArea edtData = new PTextArea();
		JScrollPane sclData = new JScrollPane(edtData); 

		JSplitPane splPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sclTables, sclData);
		splPane.setDividerLocation(0.5d);
		add(splPane, BorderLayout.CENTER);

		SwingUtilities.invokeLater(() -> {
			splPane.setDividerLocation(150);
		});
		addComponentListener(new ComponentAdapter() {
			
			public void	componentShown(ComponentEvent e){
		        System.out.println("Component is Visible");
		        btnRefresh.doClick();
				}
			

		});
 
		tblTables.getSelectionModel().addListSelectionListener(se->{
			
			if(!se.getValueIsAdjusting()) {
				int row=se.getLastIndex();
				if(row>=0 && row < tblTables.getRowCount()) {
					String sql =(String)tblTables.getValueAt(row, 1);
					edtData.setText(sql);
				}
			}
			
		});
		
		
	}

	private boolean tableTableSetUp = false; 

	private void populateTables() {

		try {
  
 
				tblTables.clearData();
 
				for (String s : SqlHistory.getHistory()) {
					HistoryItem dto = new HistoryItem(s); 
					if (!tableTableSetUp) {
						tmTables.setColumnCount(0);
						tableUtils.columns(dto, tmTables, tblTables);
						tableTableSetUp = true;
					}
					tmTables.addRow(dto.getAsRow());
				}
		 
		} catch (Exception ex) {
			LOGGER.error(ex, ex);
		} finally {
		}
	}

 private class HistoryItem implements DataDTOInterface<String>{

	 private String sql;
	 
	 public HistoryItem(String sql) {
		 this.sql=sql;
	 }
	 
	@Override
	public String getKey() {
		return "";
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public Object[] getAsRow() {

		return new Object[] {this,sql};
	}

	@Override
	public String[] getRowHeadings() {
		return new String[]{"Sql"};
	}

	@Override
	public Integer[] getColumnWidths() {
		return new Integer[]{5000};
	}

	@Override
	public String[] getColumnCodedListTypes() {
		return new String[]{};
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	 
 }
 

}
