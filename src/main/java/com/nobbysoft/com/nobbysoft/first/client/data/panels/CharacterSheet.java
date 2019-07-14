package com.nobbysoft.com.nobbysoft.first.client.data.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.lang.invoke.MethodHandles;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.com.nobbysoft.first.client.components.PButton;
import com.nobbysoft.com.nobbysoft.first.client.components.PButtonPanel;
import com.nobbysoft.com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.com.nobbysoft.first.client.components.PTextField;
import com.nobbysoft.com.nobbysoft.first.client.utils.MakeHTML;
import com.nobbysoft.com.nobbysoft.first.common.entities.pc.PlayerCharacter;

@SuppressWarnings("serial")
public class CharacterSheet extends PDialog {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass()); 

	 
	private PlayerCharacter character;

	public void setPlayerCharacter(PlayerCharacter character) {
		this.character=character;
		createSheet();
	}
	
	public CharacterSheet(Window owner) {
		super(owner);
		jbInit();
	}

	private JEditorPane edtHtml = new JEditorPane("text/html", "<h1>Hello</h1>");
	private PTextField txtName = new PTextField();
	private PTextField txtRace = new PTextField();
	private PTextField txtGender = new PTextField();
	private PTextField txtClass = new PTextField();
	
	private PDataComponent[] disable = new PDataComponent[] {
			txtName,txtRace,txtGender,txtClass
	};
	//private HTMLEditorKit kit = new HTMLEditorKit();
	private void jbInit() {
		setLayout(new BorderLayout());
        
        //
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
        edtHtml.setEditable(false);
        //edtHtml.setContentType("text/html");
        
        for(PDataComponent c:disable) {
        	c.setReadOnly(true);
        }
	}


	private void createSheet(){
		txtClass.setText(character.getFirstClass());
		txtRace.setText(character.getRaceId());
		txtGender.setText(character.getGender().name());
		txtName.setText(character.getCharacterName());
		//
		//
		MakeHTML make = new MakeHTML();
		String html=make.makeDocument(character);
		edtHtml.setText(html);
        //edtHtml.setContentType("text/html");
		LOGGER.info("html\n"+html);

	}

	private void exit() {
		dispose();
	}
	private void save() {
		
	}
	private void print() {
		
	}
}
