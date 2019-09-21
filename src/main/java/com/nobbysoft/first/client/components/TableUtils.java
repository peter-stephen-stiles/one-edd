package com.nobbysoft.first.client.components;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.common.entities.DataDTOInterface;

public class TableUtils {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass()); 
	public TableUtils() { 
	}

	private TableCellRenderer defaultTcr = new PTableCellRenderer();
	private Map<String, TableCellRenderer> renderers = new HashMap<>();
	public void columns(DataDTOInterface<?> d,DefaultTableModel tmData, PTable tblData) {

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
	
}
