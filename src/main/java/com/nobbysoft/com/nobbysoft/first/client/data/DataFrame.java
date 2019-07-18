package com.nobbysoft.com.nobbysoft.first.client.data;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.com.nobbysoft.first.client.components.PButton;
import com.nobbysoft.com.nobbysoft.first.client.components.PButtonPanel;
import com.nobbysoft.com.nobbysoft.first.client.components.PCodedListCellRenderer;
import com.nobbysoft.com.nobbysoft.first.client.components.PComboBox;
import com.nobbysoft.com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.com.nobbysoft.first.client.components.PTable;
import com.nobbysoft.com.nobbysoft.first.client.components.PTableCellRenderer;
import com.nobbysoft.com.nobbysoft.first.client.data.panels.DataButtonsInterface;
import com.nobbysoft.com.nobbysoft.first.client.data.tabs.DataFrameTabCharacters;
import com.nobbysoft.com.nobbysoft.first.client.data.tabs.DataFrameTabData;
import com.nobbysoft.com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.RaceClassLimit;
import com.nobbysoft.com.nobbysoft.first.common.entities.staticdto.Spell;
import com.nobbysoft.com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.com.nobbysoft.first.utils.DataMapper;

public class DataFrame extends JFrame {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass()); 

	public DataFrame() throws SQLException { 
		jbInit();
	}

	private final Dimension ps = new Dimension(800, 600);

	public Dimension getPreferredSize() {
		return ps;
	} 



	private void jbInit() {
		setLayout(new BorderLayout(5, 5));
		setTitle("First Edition D&D");
		
 
		PButtonPanel pnlBottomButtons = new PButtonPanel();
		JButton btnExit = new JButton("Exit");
		pnlBottomButtons.add(btnExit);

		JTabbedPane pnlTabs = new JTabbedPane();
		pnlTabs.setFont(GuiUtils.getHeaderFont());
		
		DataFrameTabData pnlStaticData = new DataFrameTabData();
		DataFrameTabCharacters pnlCharacters = new DataFrameTabCharacters();		
		pnlTabs.addTab("Characters", pnlCharacters);
		pnlTabs.addTab("Static data", pnlStaticData);

		add(pnlTabs,BorderLayout.CENTER);
		add(pnlBottomButtons, BorderLayout.SOUTH);
		

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				LOGGER.info("Closed");
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				LOGGER.info("Closed");
				System.exit(0);
			}
		});

		btnExit.addActionListener(ae -> {
			System.exit(0);
		});


	}


}
