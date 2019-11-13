package com.nobbysoft.first.client.data.panels.sql;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PButton;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.first.client.components.PTable;
import com.nobbysoft.first.client.components.PTextField;
import com.nobbysoft.first.client.components.TableUtils;
import com.nobbysoft.first.common.entities.meta.DTOColumn;
import com.nobbysoft.first.common.entities.meta.DTOConstraint;
import com.nobbysoft.first.common.entities.meta.DTOIndex;
import com.nobbysoft.first.common.entities.meta.DTOTable;
import com.nobbysoft.first.common.servicei.SqlService;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class SqlExportPanel extends PPanel implements SqlPanelInterface {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public SqlExportPanel() {
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
	private PTextField txtFilter = new PTextField() {
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			if (d.width < 200) {
				d.width = 200;
			}
			return d;
		}
	};

 
	private void jbInit() {

		tblTables.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblTables.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		PButton btnRefresh = new PButton("Refresh");

		PPanel pnlFilter = new PPanel(new FlowLayout(FlowLayout.LEFT));
		pnlFilter.add(new PLabel("Table filter:"));
		pnlFilter.add(txtFilter);
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
 
		JTabbedPane pnlData = new JTabbedPane(); 

		JSplitPane splPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sclTables, pnlData);
		splPane.setDividerLocation(0.5d);
		add(splPane, BorderLayout.CENTER);

		SwingUtilities.invokeLater(() -> {
			splPane.setDividerLocation(150);
		});

 
	}

	private boolean tableTableSetUp = false; 

	private void populateTables() {

		try {

			SqlService sqlService = getSqlService();
			{
				String filter = txtFilter.getText().trim();
				if (filter.isEmpty()) {
					filter = "%";
				} else {
					if (!filter.startsWith("%")) {
						filter = "%" + filter;
					}
					if (!filter.endsWith("%")) {
						filter = filter + "%";
					}
				}
				tblTables.clearData();
				List<DTOTable> list = sqlService.metaDataTables(filter.toUpperCase());

				for (DTOTable dto : list) {
					if (!tableTableSetUp) {
						tmTables.setColumnCount(0);
						tableUtils.columns(dto, tmTables, tblTables);
						tableTableSetUp = true;
					}
					tmTables.addRow(dto.getAsRow());
				}
			}
		} catch (Exception ex) {
			LOGGER.error(ex, ex);
		} finally {
		}
	}

	private SqlService getSqlService() {
		Object sqlo = DataMapper.INSTANCE.getNonDataService(SqlService.class);
		SqlService sqlService = (SqlService) sqlo;
		return sqlService;
	}

 

}
