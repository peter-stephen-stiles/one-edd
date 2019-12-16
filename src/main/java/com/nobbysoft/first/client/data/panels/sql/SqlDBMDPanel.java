package com.nobbysoft.first.client.data.panels.sql;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.prefs.Preferences;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PButton;
import com.nobbysoft.first.client.components.PComboBox;
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
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class SqlDBMDPanel extends PPanel implements SqlPanelInterface  {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public SqlDBMDPanel() {
		super();
		jbInit();
		SwingUtilities.invokeLater(()->{			
			populateFilters();
			
		});
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
	
	
	private PComboBox<CodedListItem<String>> filterCatalogs = new PComboBox<>();
	private PComboBox<CodedListItem<String>> filterSchema = new PComboBox<>();
	

	private DefaultTableModel tmColumns = new DefaultTableModel();
	private PTable tblColumns = new PTable(tmColumns);

	private DefaultTableModel tmIndexes = new DefaultTableModel();
	private PTable tblIndexes = new PTable(tmIndexes);

	private DefaultTableModel tmConstraints = new DefaultTableModel();
	private PTable tblConstraints = new PTable(tmConstraints);
	private void jbInit() {

		tblTables.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblColumns.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblIndexes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblConstraints.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		PButton btnRefresh = new PButton("Refresh");

		PPanel pnlFilter = new PPanel(new FlowLayout(FlowLayout.LEFT));
		pnlFilter.add(new PLabel("Catalog:"));
		pnlFilter.add(filterCatalogs);
		pnlFilter.add(new PLabel("Schema:"));
		pnlFilter.add(filterSchema);
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

		JScrollPane sclColumns = new JScrollPane(tblColumns);
		JScrollPane sclIndexes = new JScrollPane(tblIndexes);
		JScrollPane sclConstraints = new JScrollPane(tblConstraints);
		JTabbedPane pnlData = new JTabbedPane();
		pnlData.addTab("Columns", sclColumns);
		pnlData.addTab("Indexes", sclIndexes);
		pnlData.addTab("Constraints", sclConstraints);

		JSplitPane splPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sclTables, pnlData);
		splPane.setDividerLocation(0.5d);
		add(splPane, BorderLayout.CENTER);

		SwingUtilities.invokeLater(() -> {
			splPane.setDividerLocation(150);
		});

		tblTables.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				selectTable();
			}
		});
	}

	private boolean tableTableSetUp = false;
	private boolean columnTableSetUp = false;
	private boolean indexTableSetUp = false;
	private boolean constraintTableSetUp = false;

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
				String catalog = (String)filterCatalogs.getSelectedCode();
				String schema = (String)filterSchema.getSelectedCode();
				LOGGER.info("Reading "+catalog+"."+ schema+"."+filter);
				tblTables.clearData();
				List<DTOTable> list = sqlService.metaDataTables(catalog,schema,filter.toUpperCase());
				savePrefs();

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

	private static final String FILTER_CAT="catalog";
	private static final String FILTER_SCH="schema";
	
	private final Preferences prefs = Preferences.userNodeForPackage(getClass()).node(getClass().getSimpleName());
	private void savePrefs() {
		
		
		try {
			Object cat = filterCatalogs.getSelectedCode();
			prefs.put(FILTER_CAT,(String)cat);
		} catch (Exception e) {
			LOGGER.error("error catting",e);
		}
		
		try {
			Object she=filterSchema.getSelectedCode();
			
			prefs.put(FILTER_SCH,(String)she);
		} catch (Exception e) {
			LOGGER.error("error scheming",e);
		}
		
	}
	
	private void populateFilters() {
		SqlService sqlService = getSqlService();
		
		String dftCat = prefs.get(FILTER_CAT, null);
		String dftSch = prefs.get(FILTER_SCH, null);
		
		List<String> cats = new ArrayList<>();
		List<String> schema = new ArrayList<>();
		
		try {
			cats.addAll(sqlService.metaCatalogs());
		} catch (Exception ex) {
			LOGGER.error("Error getting catalogs",ex);
		}
				
		try {
			schema.addAll(sqlService.metaSchema());
		} catch (Exception ex) {
			LOGGER.error("Error getting schema",ex);
		}
 
		CodedListItem<String> NULL = new CodedListItem<>(null,"{null}");
		CodedListItem<String> BLANK = new CodedListItem<>(null,"{blank}");
		
		{
			List<CodedListItem<String>> list = new ArrayList<>();
			list.add(NULL);
			list.add(BLANK);
			for(String s:cats) {
				list.add(new CodedListItem<>(s,s));	
			}
			filterCatalogs.setList(list);
		
		}
		{
			List<CodedListItem<String>> list = new ArrayList<>();
			list.add(NULL);
			list.add(BLANK);
			for(String s:schema) {
				list.add(new CodedListItem<>(s,s));	
			}
			filterSchema.setList(list);
		
		}
		 
		filterCatalogs.setSelectedCode(dftCat);
		filterSchema.setSelectedCode(dftSch);
	}
	
	
	private void selectTable() {
		if (tblTables.getSelectedRowCount() > 0) {
			try {
				DTOTable dtot = (DTOTable) tblTables.getValueAt(tblTables.getSelectedRow(), 0);
				if (dtot != null) {
					SqlService sqlService = getSqlService();
					LOGGER.info("table selected " + dtot.getKey());
					///
					{
						tblColumns.clearData();
						List<DTOColumn> list = sqlService.metaDataColumns(dtot.getTableCat(), dtot.getTableSchem(),
								dtot.getTableName());

						for (DTOColumn dto : list) {
							if (!columnTableSetUp) {
								tmColumns.setColumnCount(0);
								tableUtils.columns(dto, tmColumns, tblColumns);
								columnTableSetUp = true;
							}
							tmColumns.addRow(dto.getAsRow());
						}
						//
					}
					{
						tblIndexes.clearData();
						List<DTOIndex> list = sqlService.metaDataIndexes(dtot.getTableCat(), dtot.getTableSchem(),
								dtot.getTableName());

						for (DTOIndex dto : list) {
							if (!indexTableSetUp) {
								tmIndexes.setColumnCount(0);
								tableUtils.columns(dto, tmIndexes, tblIndexes);
								indexTableSetUp = true;
							}
							tmIndexes.addRow(dto.getAsRow());
						}
						//
					}

					{
						tblConstraints.clearData();
						List<DTOConstraint> list = sqlService.metaDataConstraints(dtot.getTableCat(), dtot.getTableSchem(),
								dtot.getTableName());

						for (DTOConstraint dto : list) {
							if (!constraintTableSetUp) {
								tmConstraints.setColumnCount(0);
								tableUtils.columns(dto, tmConstraints, tblConstraints);
								constraintTableSetUp = true;
							}
							tmConstraints.addRow(dto.getAsRow());
						}
						//
					}
				}
			} catch (Exception ex) {
				LOGGER.error(ex, ex);
			} finally {
			}

		}
	}

}
