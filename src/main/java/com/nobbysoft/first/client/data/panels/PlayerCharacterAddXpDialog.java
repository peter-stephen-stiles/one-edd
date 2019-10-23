package com.nobbysoft.first.client.data.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JPanel;

import com.nobbysoft.first.client.components.*;
import com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.first.common.entities.staticdto.Race;

@SuppressWarnings("serial")
public class PlayerCharacterAddXpDialog extends JDialog {

 
 

	public PlayerCharacterAddXpDialog(Window owner) {
		super(owner,ModalityType.APPLICATION_MODAL);
		jbInit();
	}
	
	private PlayerCharacter playerCharacter;
	private Race race;
	private final PLabel lblCharacterName = new PLabel();
	private final PLabel lblRaceName = new PLabel();
	
	
	public void setPlayerCharacter(PlayerCharacter playerCharacter,Race race) {
		this.playerCharacter=playerCharacter;
		this.race=race;
		//
		lblCharacterName.setText(playerCharacter.getCharacterName());
		lblRaceName.setText(race.getName());
		
		// initialise !
	}
	
	private void jbInit() {
		this.getContentPane().setLayout(new BorderLayout());
		
		setTitle("Add XP");
		
		JPanel content = (JPanel)getContentPane();
		PButton btnClose = new PButton("Close");
		PButtonPanel pnlButtons = new PButtonPanel();
		pnlButtons.add(btnClose);
		
		
		PPanel pnlHeader = new PPanel(new FlowLayout(FlowLayout.LEFT));
		pnlHeader.add(new PLabel("Character"));
		pnlHeader.add(lblCharacterName);
		pnlHeader.add(new PLabel("Race"));
		pnlHeader.add(lblRaceName);
		
		
		btnClose.addActionListener(ae->dispose());
		
		content.add(pnlHeader,BorderLayout.NORTH);
		content.add(pnlButtons,BorderLayout.SOUTH);
	}


}
