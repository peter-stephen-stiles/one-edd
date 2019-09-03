package com.nobbysoft.first.client.components;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("serial")
public class PBasicTableWithModel extends PTable {



	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	
	
	
	private final DefaultTableModel tmData;
	private final TableCellRenderer defaultTcr = new PTableCellRenderer();
	
	
	public PBasicTableWithModel() {
		super();
		setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tmData = new DefaultTableModel();
		super.setModel(tmData);
	}
	
	public PBasicTableWithModel(ColumnConfig... configs) {
		this();
		List<ColumnConfig> list = new ArrayList<>();
		for(ColumnConfig c:configs) {
			list.add(c);
		}
		setUpColumns(list);
		
	}	
	
	public PBasicTableWithModel(List<ColumnConfig> list) {
		this();
		setUpColumns(list);
		
	}
	
	public void clearTable() {
		while (tmData.getRowCount() > 0) {
			tmData.removeRow(0);
		}
	}

	public static final class ColumnConfig  {
		private String name;
		private int minWidth;
		private int preferredWidth;
		private int maxWidth;
		public ColumnConfig( String name, int width) { 
			this.name = name;
			this.minWidth = width;
			this.preferredWidth = width;
			this.maxWidth = width;
			this.renderer = null;
		}
		public ColumnConfig( String name, int width,TableCellRenderer renderer) { 
			this.name = name;
			this.minWidth = width;
			this.preferredWidth = width;
			this.maxWidth = width;
			this.renderer = renderer;
		}
		public ColumnConfig( String name, int minWidth, int preferredWidth, int maxWidth) {
			super();
			this.name = name;
			this.minWidth = minWidth;
			this.preferredWidth = preferredWidth;
			this.maxWidth = maxWidth;
			this.renderer = null;
		}
		public ColumnConfig(String name, int minWidth, int preferredWidth, int maxWidth,
				TableCellRenderer renderer) {
			super(); 
			this.name = name;
			this.minWidth = minWidth;
			this.preferredWidth = preferredWidth;
			this.maxWidth = maxWidth;
			this.renderer = renderer;
		}
		private TableCellRenderer renderer;
 
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getMinWidth() {
			return minWidth;
		}
		public void setMinWidth(int minWidth) {
			this.minWidth = minWidth;
		}
		public int getPreferredWidth() {
			return preferredWidth;
		}
		public void setPreferredWidth(int preferredWidth) {
			this.preferredWidth = preferredWidth;
		}
		public int getMaxWidth() {
			return maxWidth;
		}
		public void setMaxWidth(int maxWidth) {
			this.maxWidth = maxWidth;
		}
		public TableCellRenderer getRenderer() {
			return renderer;
		}
		public void setRenderer(TableCellRenderer renderer) {
			this.renderer = renderer;
		}
		
	};
	
	
	public void setUpColumns(List<ColumnConfig> configs) {
		for(int i=0,n=configs.size();i<n;i++) {
			ColumnConfig config = configs.get(i);
			tmData.addColumn(config.getName());
		}
		for(int i=0,n=configs.size();i<n;i++) {
			ColumnConfig config = configs.get(i);
			setUpColumn(i,config.getName(),config.getMinWidth(),
					config.getPreferredWidth(),config.getMaxWidth(),config.getRenderer());
		}
	}
	public TableColumn setUpColumn(int index,String name,int minWidth,int prefWidth,int maxWidth) {
		
		return setUpColumn(index,name,minWidth,prefWidth,maxWidth,defaultTcr);
	}
	
	public TableColumn setUpColumn(int index,String name,int minWidth,int preferredWidth,int maxWidth,TableCellRenderer renderer) {
		LOGGER.info("setting up column "+index+" - "+name);
		TableColumnModel tcm = getColumnModel();
		if(renderer==null) {
			renderer=defaultTcr;
		}
		// if we have missed a column set one up with a blank header
		if(index == tcm.getColumnCount()) {
			tmData.addColumn(name);
		}
		if(index>=tcm.getColumnCount()) {
			throw new IllegalArgumentException("Must set up columns in order. "+index+" is wrong...");
		}
		
		TableColumn fc = tcm.getColumn(index);
		fc.setHeaderValue(name);		
		
		fc.setWidth(0);
		fc.setMinWidth(0);
		fc.setPreferredWidth(0);
		fc.setMaxWidth(0);
		fc.setWidth(0);
		
		fc.setMaxWidth(maxWidth);
		fc.setPreferredWidth(preferredWidth);
		fc.setMinWidth(minWidth);
		fc.setWidth(preferredWidth);
		
		fc.setResizable(maxWidth!= minWidth);
		
		fc.setCellRenderer(renderer);		
		
		return fc;
		
		
	}

	public void addRow(Vector row) {
		tmData.addRow(row);
	}
	
	
	public void addRow(Object[] row) {
		tmData.addRow(row);
	}
	
	

}
