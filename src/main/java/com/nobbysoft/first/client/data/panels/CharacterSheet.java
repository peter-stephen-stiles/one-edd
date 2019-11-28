package com.nobbysoft.first.client.data.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;
import java.lang.invoke.MethodHandles;
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
import com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterLevel;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.first.common.entities.staticdto.SavingThrow;
import com.nobbysoft.first.common.servicei.CharacterClassService;
import com.nobbysoft.first.common.servicei.RaceService;
import com.nobbysoft.first.common.servicei.SavingThrowService;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class CharacterSheet extends PDialog {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass()); 

	 
	private PlayerCharacter character;

	public void setPlayerCharacter(PlayerCharacter character) {
		this.character=character;
		try {
			populateData();
		} catch (SQLException e) {
			Popper.popError(this, e);
			return;
		}
		createSheet();
	}
	
	public CharacterSheet(Window owner) {
		super(owner);
		jbInit();
	}

	private JEditorPane edtHtml = new JEditorPane("text/html", "<h1>No character sheet :(</h1>");
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

        pnlHead.add(new JLabel("Character name"));
        pnlHead.add(txtName);
        pnlHead.add(new JLabel("Race"));
        pnlHead.add(txtRace);
        pnlHead.add(new JLabel("Gender"));
        pnlHead.add(txtGender);
        pnlHead.add(new JLabel("Class(es)"));
        pnlHead.add(txtClass);
        
        add(pnlHead,BorderLayout.NORTH);
        add(sclHtml,BorderLayout.CENTER);
        add(pnlButtons,BorderLayout.SOUTH);
        edtHtml.setEditable(false); 
        
        for(PDataComponent c:disable) {
        	c.setReadOnly(true);
        }
	}

	
	private Map<String,List<SavingThrow>> savingThrows = new HashMap<>();
	private Map<String,SavingThrow> bestSaves = new HashMap<>();
	
	private void populateData() throws SQLException{
		CharacterClassService characterClassService = (CharacterClassService)DataMapper.INSTANCE.getDataService(CharacterClass.class);
		RaceService raceService = (RaceService)DataMapper.INSTANCE.getDataService(Race.class);
		SavingThrowService savingThrowService = (SavingThrowService)DataMapper.INSTANCE.getDataService(SavingThrow.class);
		
		race = raceService.get(character.getRaceId());
		
		CharacterClass c0 = characterClassService.get(character.getFirstClass());
		characterClasses.put(c0.getClassId(),c0);
		if(character.getSecondClass()!=null) {
			CharacterClass c1 = characterClassService.get(character.getSecondClass());
			characterClasses.put(c1.getClassId(),c1);			
			if(character.getThirdClass()!=null) {
				CharacterClass c2 = characterClassService.get(character.getThirdClass());
				characterClasses.put(c2.getClassId(),c2);			
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
	
	private Map<String,CharacterClass> characterClasses = new HashMap<>();
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
			
			CharacterClass cc = characterClasses.get(classId);
			
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

	private void createSheet(){
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
		String html=make.makeDocument(character, characterClasses, race, saves,data);
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
