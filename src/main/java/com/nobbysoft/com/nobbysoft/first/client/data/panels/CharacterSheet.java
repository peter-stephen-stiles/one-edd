package com.nobbysoft.com.nobbysoft.first.client.data.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.Window;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;

import com.nobbysoft.com.nobbysoft.first.client.components.PButton;
import com.nobbysoft.com.nobbysoft.first.client.components.PButtonPanel;
import com.nobbysoft.com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.com.nobbysoft.first.client.components.PTextField;
import com.nobbysoft.com.nobbysoft.first.common.entities.pc.PlayerCharacter;

@SuppressWarnings("serial")
public class CharacterSheet extends PDialog {

	private PlayerCharacter character;

	public void setPlayerCharacter(PlayerCharacter character) {
		this.character=character;
		createSheet();
	}
	
	public CharacterSheet(Window owner) {
		super(owner);
		jbInit();
	}

	private JEditorPane edtHtml = new JEditorPane();
	private PTextField txtName = new PTextField();
	private PTextField txtRace = new PTextField();
	private PTextField txtGender = new PTextField();
	private PTextField txtClass = new PTextField();
	
	private PDataComponent[] disable = new PDataComponent[] {
			txtName,txtRace,txtGender,txtClass
	};
	
	private void jbInit() {
		setLayout(new BorderLayout());
        HTMLEditorKit kit = new HTMLEditorKit();
        edtHtml.setEditorKit(kit);
        JScrollPane sclHtml = new JScrollPane(edtHtml);
        
        PPanel pnlHead = new PPanel(new FlowLayout(FlowLayout.LEFT)); 
        PButtonPanel pnlButtons = new PButtonPanel(); 
        PButton btnExit = new PButton("Exit");
        PButton btnSave = new PButton("Save");
        PButton btnPrint = new PButton("Print");
        pnlButtons.add(btnExit,btnSave,btnPrint);
        btnExit.addActionListener(ae ->exit());
        btnSave.addActionListener(ae ->save());
        btnPrint.addActionListener(ae ->print());

        pnlHead.add(new JLabel("Character"));
        pnlHead.add(txtName);
        pnlHead.add(new JLabel("Race"));
        pnlHead.add(txtRace);
        pnlHead.add(new JLabel("Gender"));
        pnlHead.add(txtGender);
        pnlHead.add(new JLabel("Class"));
        pnlHead.add(txtClass);
        
        add(pnlHead,BorderLayout.NORTH);
        add(sclHtml,BorderLayout.CENTER);
        add(pnlButtons,BorderLayout.SOUTH);
        
        
        
        for(PDataComponent c:disable) {
        	c.setReadOnly(true);
        }
	}


	private void createSheet(){
		txtClass.setText(character.getFirstClass());
		txtRace.setText(character.getRaceId());
		txtGender.setText(character.getGender().name());
		txtName.setText(character.getCharacterName());
	}

	private void exit() {
		dispose();
	}
	private void save() {
		
	}
	private void print() {
		
	}
}
