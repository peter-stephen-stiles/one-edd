package com.nobbysoft.first.client.data.tabs;

import java.awt.BorderLayout;
import java.awt.LayoutManager;

import javax.swing.JTabbedPane;

import com.nobbysoft.first.client.components.PButton;
import com.nobbysoft.first.client.components.PButtonPanel;
import com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.first.client.data.panels.SqlPanel;

public class DataFrameTabSqlMain extends PPanel {

	public DataFrameTabSqlMain() {
		super();
		jbInit();
	}
	
	private int panelCount=2;
	private final JTabbedPane pnlSqlTabs = new JTabbedPane();
	private void jbInit() {
		
		PButtonPanel pnlTopButtons = new PButtonPanel();
		PButton btnNewSqlTab = new PButton("New sql tab");
		pnlTopButtons.add(btnNewSqlTab);
		add(pnlTopButtons,BorderLayout.NORTH);
		add(pnlSqlTabs,BorderLayout.CENTER);
		SqlPanel sqlPanel = new SqlPanel();
		pnlSqlTabs.addTab("Sql#1", sqlPanel);
		btnNewSqlTab.addActionListener(ae->{
			SqlPanel sqlPanelX = new SqlPanel();
			pnlSqlTabs.addTab("Sql#"+panelCount++, sqlPanelX);
		});
		
	}



}
