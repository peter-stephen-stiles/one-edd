package com.nobbysoft.com.nobbysoft.first.client.data;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.com.nobbysoft.first.client.components.PButtonPanel;
import com.nobbysoft.com.nobbysoft.first.client.data.tabs.DataFrameTabCharacters;
import com.nobbysoft.com.nobbysoft.first.client.data.tabs.DataFrameTabData;
import com.nobbysoft.com.nobbysoft.first.client.utils.GuiUtils;

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
