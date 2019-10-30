package com.nobbysoft.first.client.data.panels;

import java.awt.BorderLayout;
import java.awt.Window;
import java.lang.invoke.MethodHandles;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PButton;
import com.nobbysoft.first.client.components.PButtonPanel;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.common.entities.pc.PlayerCharacter;

public class PlayerCharacterSpellDialog extends PDialog {


	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
		private Window parent;
		public PlayerCharacterSpellDialog(Window owner,String title) {
			super(owner,title,ModalityType.APPLICATION_MODAL); 
			parent=owner;
			jbInit();
		}
		private PlayerCharacterSpellPanel panel=null;
		private void jbInit() {
			JPanel contentPane = (JPanel)getContentPane();
			contentPane.setLayout(new BorderLayout());
			PButtonPanel pnlButtons = new PButtonPanel();
			PButton btnClose = new PButton("Close");
			btnClose.addActionListener(ae->dispose());
			pnlButtons.add(btnClose);
			
			panel = new PlayerCharacterSpellPanel();

			contentPane.add(panel, BorderLayout.CENTER);
			contentPane.add(pnlButtons, BorderLayout.SOUTH);
			
			
		}
		
		public void initialiseCharacter(PlayerCharacter pc) {
			panel.initialiseCharacter(pc);
		}
		
}
