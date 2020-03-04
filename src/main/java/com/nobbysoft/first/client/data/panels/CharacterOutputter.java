package com.nobbysoft.first.client.data.panels;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PButton;
import com.nobbysoft.first.client.components.PButtonPanel;
import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.first.client.components.PTextField;
import com.nobbysoft.first.client.components.special.PComboGender;
import com.nobbysoft.first.client.utils.DataAccessThingy;
import com.nobbysoft.first.client.utils.MakeHTML;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.client.utils.MakeHTML.TYPE;
import com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterLevel;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.first.common.entities.staticdto.SavingThrow;
import com.nobbysoft.first.common.servicei.CharacterClassService;
import com.nobbysoft.first.common.servicei.RaceService;
import com.nobbysoft.first.common.servicei.SavingThrowService;
import com.nobbysoft.first.utils.DataMapper;

public abstract class CharacterOutputter extends PDialog {

	
	public abstract MakeHTML.TYPE getHtmlType();
	
 
	
	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass()); 

	 
	private PlayerCharacter character;

	public boolean setPlayerCharacter(PlayerCharacter character) {
		this.character=character;
		try {
			populateData();
		} catch (SQLException e) {
			Popper.popError(this, e);
			return false;
		}
		return createSheet();
	}
	
	public CharacterOutputter(Window owner) {
		super(owner);
		jbInit();
	}

	private JEditorPane edtResults = new JEditorPane();
	private PTextField txtName = new PTextField();
	private PTextField txtRace = new PTextField();
	private PComboGender txtGender = new PComboGender();
	private PTextField txtClass = new PTextField();
	
	private PDataComponent[] disable = new PDataComponent[] {
			txtName,txtRace,txtGender,txtClass
	};

	private void jbInit() {
		setLayout(new BorderLayout());
        
        //
        JScrollPane sclResults = new JScrollPane(edtResults) {
        	public Dimension getPreferredSize() {
        		Dimension d = super.getPreferredSize();
        		if(d.height>200) {
        			d.height=200;
        		}
        		return d;
        	}
        }; 
        
        PPanel pnlHead = new PPanel(new FlowLayout(FlowLayout.LEFT)); 
        PButtonPanel pnlButtons = new PButtonPanel(); 
        PButton btnExit = new PButton("Exit"); 
        pnlButtons.add(btnExit);
        //,btnSave,btnPrint);
        btnExit.addActionListener(ae ->exit()); 

        pnlHead.add(new JLabel("Character name"));
        pnlHead.add(txtName);
        pnlHead.add(new JLabel("Race"));
        pnlHead.add(txtRace);
        pnlHead.add(new JLabel("Gender"));
        pnlHead.add(txtGender);
        pnlHead.add(new JLabel("Class(es)"));
        pnlHead.add(txtClass);
        
        add(pnlHead,BorderLayout.NORTH);
        add(sclResults,BorderLayout.CENTER);
        add(pnlButtons,BorderLayout.SOUTH);
        edtResults.setEditable(false); 
        
        for(PDataComponent c:disable) {
        	c.setReadOnly(true);
        }
	}

	
	private Map<String,List<SavingThrow>> savingThrows = new HashMap<>();
	private Map<String,SavingThrow> bestSaves = new HashMap<>();
	
	private void populateData() throws SQLException{
		
		RaceService raceService = (RaceService)DataMapper.INSTANCE.getDataService(Race.class);
		SavingThrowService savingThrowService = (SavingThrowService)DataMapper.INSTANCE.getDataService(SavingThrow.class);
		
		
		
		
		{
			CharacterClassService characterClassService = (CharacterClassService)DataMapper.INSTANCE.getDataService(CharacterClass.class);
			List<CharacterClass> classes =characterClassService.getList();	
			for(CharacterClass cc:classes) {
				allCharacterClasses.put(cc.getClassId(), cc);
			}
		}
		

		
		
		race = raceService.get(character.getRaceId());
		
		CharacterClass c0 = allCharacterClasses.get(character.getFirstClass());
		myCharacterClasses.put(c0.getClassId(),c0);
		if(character.getSecondClass()!=null) {
			CharacterClass c1 = allCharacterClasses.get(character.getSecondClass());
			myCharacterClasses.put(c1.getClassId(),c1);			
			if(character.getThirdClass()!=null) {
				CharacterClass c2 = allCharacterClasses.get(character.getThirdClass());
				myCharacterClasses.put(c2.getClassId(),c2);			
			}

		}
		for(PlayerCharacterLevel pcd:character.getClassDetails()) {
			if(pcd!=null) {
				
			String classId = pcd.getThisClass();
			if(classId!=null) {
				int lvl = pcd.getThisClassLevel();
				savingThrows.put(classId,savingThrowService.getSavesForClassLevel(classId, lvl));			
				}
			} 
		}
		 {
			// need to get just one set of saves
			
			for(String key:savingThrows.keySet()) {
				List<SavingThrow> saves = savingThrows.get(key);
				for(SavingThrow save:saves) {
					String type=save.getSavingThrowTypeString();
					if(!bestSaves.containsKey(type)) {
						// add save in
						bestSaves.put(type,save);
					} else {
						SavingThrow last = bestSaves.get(type);
						
							if(last.getRollRequired()>save.getRollRequired()) {
								bestSaves.put(type,save);
							}
						}
					}
					
				}
			}
			 
	}
	
	private Map<String,CharacterClass> myCharacterClasses = new HashMap<>();
	private Map<String,CharacterClass> allCharacterClasses = new HashMap<>();
	private Race race = null;
	
	private String classy() {
		

		StringBuilder sb = new StringBuilder();
		int i=0;
		for(PlayerCharacterLevel pcd:character.getClassDetails()) {
			if(pcd!=null) {
				
			String classId = pcd.getThisClass();
			if(classId!=null) {
			int xp =pcd.getThisClassExperience();
			int hp =pcd.getThisClassHp();
			int lvl = pcd.getThisClassLevel();
			
			CharacterClass cc = allCharacterClasses.get(classId);
			
			if(i>0) {
				sb.append(" / ");
			}
			sb.append(cc.getName()).append(" (").append(lvl).append(")");
			
			i++;
			}
			}
		}		
		
		
		return sb.toString();
	}

	private boolean createSheet(){
		txtClass.setText(classy());
		txtRace.setText(race.getName());
		txtGender.setSelectedCode(character.getGender());
		txtName.setText(character.getCharacterName());
		//
		//
		List<SavingThrow> saves = new ArrayList<>();
		saves.addAll(bestSaves.values());
		MakeHTML make = new MakeHTML();
		DataAccessThingy data = new DataAccessThingy();
		TYPE htmlType = getHtmlType();
		String html=make.makeDocument(character, myCharacterClasses, race, saves,data,htmlType,allCharacterClasses);
		
		try {
			File tmp = File.createTempFile("~"+htmlType.getPrefix(), ".html");
			
			try(FileWriter fw = new FileWriter(tmp)){
				try(BufferedWriter bw = new BufferedWriter(fw)){
					bw.write(html);
				}
			}
			
			URI uri=java.nio.file.FileSystems.getDefault().getPath( tmp.getPath() ).toAbsolutePath().toUri();
 
			
			Desktop.getDesktop().browse(uri);
			// all good, close popup 
			return true;
		} catch (IOException e) {
			//all bad leave pop-up open 
			edtResults.setText(e.toString());
			Popper.popError(this, e);
		}
		return false;
	}

	private void exit() {
		dispose();
	}
 
	@Override
	public void show() {
		// ignore
		dispose();
	}
	
	@Override
	public void setVisible(boolean visible) {
		// ignore!
		dispose();
	}

	

}
