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
import com.nobbysoft.first.client.data.panels.SqlPanel;
import com.nobbysoft.first.client.utils.GuiUtils; 

@SuppressWarnings("serial")
public class DataFrameTabSqlMain extends PPanel {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
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
		sqlPanel.addActionListener(ae->{removeSqlTab(ae);});
		sqlPanel.setName("Sql#1");
		pnlSqlTabs.addTab(sqlPanel.getName(), sqlPanel);
		btnNewSqlTab.addActionListener(ae->{
			SqlPanel sqlPanelX = new SqlPanel();
			sqlPanelX.setName("Sql#"+panelCount++);
			pnlSqlTabs.addTab(sqlPanelX.getName(), sqlPanelX);
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
