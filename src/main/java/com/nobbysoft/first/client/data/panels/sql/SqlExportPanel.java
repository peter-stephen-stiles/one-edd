package com.nobbysoft.first.client.data.panels.sql;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
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
import com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.first.client.components.PTable;
import com.nobbysoft.first.client.components.PTextField;
import com.nobbysoft.first.client.components.TableUtils;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.meta.DTOColumn;
import com.nobbysoft.first.common.entities.meta.DTOConstraint;
import com.nobbysoft.first.common.entities.meta.DTOIndex;
import com.nobbysoft.first.common.entities.meta.DTOTable;
import com.nobbysoft.first.common.servicei.SqlService;
import com.nobbysoft.first.common.utils.CodedListItem;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class SqlExportPanel extends PPanel implements SqlPanelInterface {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public SqlExportPanel() {
		super();
		jbInit();
		SwingUtilities.invokeLater(()->{			
			populateFilters();
			loadPrefs();
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


	private static final String FILTER_CAT="catalog";
	private static final String FILTER_SCH="schema";
	private final Preferences prefs = Preferences.userNodeForPackage(getClass()).node(getClass().getSimpleName());
	
	private void loadPrefs() {
		String dftCat = prefs.get(FILTER_CAT, null);
		String dftSch = prefs.get(FILTER_SCH, null);
		txtFileName.setText(prefs.get("export-file", ""));		
	}
	private void savePrefs() {
		prefs.put("export-file",txtFileName.getText());
		
		
		try {
			Object cat = filterCatalogs.getSelectedCode();
			if(cat!=null) {
				prefs.put(FILTER_CAT,(String)cat);
			} else {
				prefs.put(FILTER_CAT,null);
			}
		} catch (Exception e) {
			LOGGER.error("error getting cat from prefs "+e);
		}
		
		try {
			Object she=filterSchema.getSelectedCode();
			if(she!=null) {
				prefs.put(FILTER_SCH,(String)she);
			} else {
				prefs.put(FILTER_SCH,null);
			}
		} catch (Exception e) {
			LOGGER.error("error getting schema from prefs "+e);
		}
		
	}
	
	private PComboBox<CodedListItem<String>> filterCatalogs = new PComboBox<>();
	private PComboBox<CodedListItem<String>> filterSchema = new PComboBox<>();
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

	private PTextField txtFileName = new PTextField();
 
	private void jbInit() {
		txtFileName.setMinimumWidth(200);
		
		tblTables.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblTables.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

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
			savePrefs();
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

		
		JButton btnFile = new JButton("..");
		JButton btnExport = new JButton("Export");
				
		JPanel pnlAction = new JPanel(new GridBagLayout());
		add(pnlAction,BorderLayout.SOUTH);
		
		pnlAction.add(new JLabel("Export to file:"),GBU.label(0, 0));
		pnlAction.add(txtFileName,GBU.text(1, 0));
		pnlAction.add(btnFile,GBU.button(2, 0));
		pnlAction.add(new JLabel("    "),GBU.label(3, 0));
		pnlAction.add(btnExport,GBU.button(4, 0));		
		
		btnFile.addActionListener(ae->selectFile());
		btnExport.addActionListener(ae->export());
		
		SwingUtilities.invokeLater(() -> {
			splPane.setDividerLocation(150);
		});

 
	}
	
	private File lastFile = null;
	
	private void selectFile() {
		String file = txtFileName.getText();
		JFileChooser chooser = new JFileChooser();
		File f = new File(file);
		if(f.exists()) {
			chooser.setSelectedFile(f);
		} else if (lastFile!=null) {
			chooser.setSelectedFile(lastFile);
		}
		int ret = chooser.showSaveDialog(this);
		if(ret==JFileChooser.APPROVE_OPTION){
			lastFile = chooser.getSelectedFile();
			txtFileName.setText(lastFile.getAbsolutePath());
			savePrefs();
		}
		
	}
	
	private void export() {
		String file = txtFileName.getText();
		if(file==null||file.trim().isEmpty()) {
			txtFileName.requestFocusInWindow();
			Popper.popError(this, "Specify file", "You must specify a file name");
			return;
		}
		

		SqlService sqlService = getSqlService();
		String catalog = (String)filterCatalogs.getSelectedCode();
		String schema = (String)filterSchema.getSelectedCode();
		savePrefs();
		try {
		ReturnValue<String> rv = sqlService.export(catalog, schema, file);
		if(rv.isError()) {
			Popper.popError(this, "Error exporting "+catalog+"/"+schema, rv);			
		} else {
			Popper.popInfo(this, "Export", "Well done, you exported the "+catalog+"/"+schema+"!");
		}
		
		} catch (Exception ex) {
			Popper.popError(this,ex);
		}
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
				String catalog = (String)filterCatalogs.getSelectedCode();
				String schema = (String)filterSchema.getSelectedCode();
				LOGGER.info("Reading "+catalog+"."+ schema+"."+filter);
				tblTables.clearData();
				List<DTOTable> list = sqlService.metaDataTables(catalog,schema,filter.toUpperCase());

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

	private void populateFilters() {
		SqlService sqlService = getSqlService();
		
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
		 
		
	}
 

}
