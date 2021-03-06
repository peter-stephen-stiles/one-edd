package com.nobbysoft.first.client.data.tabs;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PButton;
import com.nobbysoft.first.client.components.PButtonPanel;
import com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.first.client.components.PTable;
import com.nobbysoft.first.client.components.PTextArea;
import com.nobbysoft.first.client.data.panels.sql.SqlDBMDPanel;
import com.nobbysoft.first.client.data.panels.sql.SqlExportPanel;
import com.nobbysoft.first.client.data.panels.sql.SqlHistoryPanel;
import com.nobbysoft.first.client.data.panels.sql.SqlPanelInterface;
import com.nobbysoft.first.client.data.panels.sql.SqlQueryPanel;
import com.nobbysoft.first.client.utils.GuiUtils; 

@SuppressWarnings("serial")
public class DataFrameTabSql extends PPanel {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	public DataFrameTabSql() {
		super();
		jbInit();
	}

	private int panelCount=2;
	private final JTabbedPane pnlSqlTabs = new JTabbedPane();
	private void jbInit() {
		PButtonPanel pnlTopButtons = new PButtonPanel();
		PButton btnNewSqlTab = new PButton("New sql tab");
		PButton btnNewMetaTab = new PButton("New meta tab");
		PButton btnNewExportTab = new PButton("New export tab");
		PButton btnNewHistoryTab = new PButton("New history tab");
		pnlTopButtons.add(btnNewSqlTab);
		pnlTopButtons.add(btnNewMetaTab);
		pnlTopButtons.add(btnNewExportTab);
		pnlTopButtons.add(btnNewHistoryTab);
		
		add(pnlTopButtons,BorderLayout.NORTH);
		add(pnlSqlTabs,BorderLayout.CENTER);
		SqlQueryPanel sqlPanel = new SqlQueryPanel();
		sqlPanel.addActionListener(ae->{removeSqlTab(ae);});
		sqlPanel.setName("Sql#1");
		pnlSqlTabs.addTab(sqlPanel.getName(), sqlPanel);
		btnNewSqlTab.addActionListener(ae->{
			SqlPanelInterface sqlPanelX = new SqlQueryPanel();
			sqlPanelX.setName("Sql#"+panelCount++);
			pnlSqlTabs.addTab(sqlPanelX.getName(), (JComponent)sqlPanelX);
			pnlSqlTabs.setSelectedComponent((JComponent)sqlPanelX);
			sqlPanelX.addActionListener(a2e->{removeSqlTab(a2e);});
		});
		btnNewMetaTab.addActionListener(ae->{
			SqlPanelInterface sqlPanelX = new SqlDBMDPanel();
			sqlPanelX.setName("Meta#"+panelCount++);
			pnlSqlTabs.addTab(sqlPanelX.getName(), (JComponent)sqlPanelX);
			pnlSqlTabs.setSelectedComponent((JComponent)sqlPanelX);
			sqlPanelX.addActionListener(a2e->{removeSqlTab(a2e);});
		});
		btnNewHistoryTab.addActionListener(ae->{
			SqlPanelInterface sqlPanelX = new SqlHistoryPanel();
			sqlPanelX.setName("History#"+panelCount++);
			pnlSqlTabs.addTab(sqlPanelX.getName(), (JComponent)sqlPanelX);
			pnlSqlTabs.setSelectedComponent((JComponent)sqlPanelX);
			sqlPanelX.addActionListener(a2e->{removeSqlTab(a2e);});
		});
		
		
		btnNewExportTab.addActionListener(ae->{
			SqlPanelInterface sqlPanelX = new SqlExportPanel();
			sqlPanelX.setName("Export#"+panelCount++);
			pnlSqlTabs.addTab(sqlPanelX.getName(), (JComponent)sqlPanelX);
			pnlSqlTabs.setSelectedComponent((JComponent)sqlPanelX);
			sqlPanelX.addActionListener(a2e->{removeSqlTab(a2e);});
		});
		pnlSqlTabs.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton()==MouseEvent.BUTTON2) {
					LOGGER.info("button 2!"+e.getSource());
				}
				if(e.getButton()==MouseEvent.BUTTON3) {
					int st =pnlSqlTabs.getSelectedIndex();
					if(st>=0) {
						LOGGER.info("button 3!"+e.getSource());
						String name = pnlSqlTabs.getSelectedComponent().getName();
						if(GuiUtils.askYesNoQuestion(pnlSqlTabs, "Confirm close tab "+name)) {
							pnlSqlTabs.removeTabAt(st);
						}
					}
				}
				if(e.getButton()==MouseEvent.BUTTON1) {
					LOGGER.info("button 1!"+e.getSource());
				}
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	private void	removeSqlTab(ActionEvent ae) {
	
	}


}
