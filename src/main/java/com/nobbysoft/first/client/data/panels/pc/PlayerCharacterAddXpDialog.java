package com.nobbysoft.first.client.data.panels.pc;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JPanel;

import com.nobbysoft.first.client.components.*;
import com.nobbysoft.first.client.components.special.ThreeClasses;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterLevel;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.entities.staticdto.CharacterClassLevel;
import com.nobbysoft.first.common.entities.staticdto.Race;
import com.nobbysoft.first.common.entities.staticdto.attributes.Constitution;
import com.nobbysoft.first.common.servicei.CharacterClassLevelService;
import com.nobbysoft.first.common.servicei.ConstitutionService;
import com.nobbysoft.first.common.servicei.PlayerCharacterService;
import com.nobbysoft.first.common.utils.DICE;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.common.utils.Roller;
import com.nobbysoft.first.utils.DataMapper; 

@SuppressWarnings("serial")
public class PlayerCharacterAddXpDialog extends JDialog {

 
 private boolean cancelled=true;
 public boolean isCancelled() {
	return cancelled;
}
	public PlayerCharacterAddXpDialog(Window owner) {
		super(owner,ModalityType.APPLICATION_MODAL);
		jbInit();
	}
	
	private PlayerCharacter playerCharacter;
	private Race race;
	private final PLabel lblCharacterName = new PLabel();
	private final PLabel lblRaceName = new PLabel();
	private final ThreeClasses threeClasses = new ThreeClasses();
	
	private final PIntegerField txtXp = new PIntegerField();
	
	public void setPlayerCharacter(PlayerCharacter playerCharacter,Race race) {
		cancelled=true;
		
		this.playerCharacter=playerCharacter;
		this.race=race;
		//
		lblCharacterName.setText(playerCharacter.getCharacterName());
		lblRaceName.setText(race.getName());

		threeClasses.setFromPlayer(playerCharacter);
		
		// initialise !
	}
	
	private void jbInit() {
		this.getContentPane().setLayout(new BorderLayout());
		
		setTitle("Add XP");
		
		txtXp.setMinimumWidth(120);
		
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

		PButton btnAddXp = new PButton("Add XP");
		btnAddXp.addActionListener(ae->addXpListener());
		
		PPanel pnlDetails = new PPanel(new GridBagLayout());
		pnlDetails.add(threeClasses,GBU.hPanel(0, 0, 5, 1));
	
		
		pnlDetails.add(new PLabel("XP to add:"),GBU.label(0,1));
		pnlDetails.add(txtXp,GBU.text(1,1));
		pnlDetails.add(btnAddXp,GBU.button(2,1));
		
		pnlDetails.add(new PLabel(""),GBU.label(99,99));
		
		content.add(pnlHeader,BorderLayout.NORTH);
		content.add(pnlDetails,BorderLayout.CENTER);
		content.add(pnlButtons,BorderLayout.SOUTH);
	}

	private void addXpListener() {
		cancelled=true;
		try {
		ReturnValue<String> ret = addXp();
		if(ret.isError()) {
			Popper.popError(this, "Sorry, I can't do that, Dave", ret);
			return;
		}
		} catch (Exception ex) {
			Popper.popError(this, ex);
			return;
		}
		
		cancelled=false;
		dispose();
	}
	
	public PlayerCharacter getPlayerCharacter() {
		if(cancelled) {
			throw new IllegalStateException("But I was cancelled!");
		}
		return playerCharacter;
	}
	
	
	CharacterClassLevelService getDataService() {
		CharacterClassLevelService dao  = (CharacterClassLevelService)DataMapper.INSTANCE.getDataService(CharacterClassLevel.class);
		return dao;
	}

	ConstitutionService getCONService() {
		ConstitutionService dao  = (ConstitutionService)DataMapper.INSTANCE.getDataService(Constitution.class);
		return dao;
	}

	PlayerCharacterService getPCService() {
		PlayerCharacterService dao  = (PlayerCharacterService)DataMapper.INSTANCE.getDataService(PlayerCharacter.class);
		return dao;
	}
	
	private ReturnValue<String> addXp() throws Exception{
		
		int xpAdd =txtXp.getIntegerValue();
		if(xpAdd<=0) {
			return new ReturnValue<>(ReturnValue.IS_ERROR.TRUE,"You should really specify a number of XP for me to add!");
		}
		
		CharacterClass[] characterClasses = threeClasses.getClasses();
		ConstitutionService conService = getCONService();
		Constitution constitution = conService.get(playerCharacter.getAttrCon());
		PlayerCharacterService playerCharacterService =getPCService();
		
		PlayerCharacterLevel[] playerCharacterLevels = playerCharacter.getClassDetails();
		
		if(playerCharacter.getSecondClass()==null) {
			// only one class, p'easy!
			// remember can only go up one level at a time..
			CharacterClass characterClass = characterClasses[0];
			PlayerCharacterLevel playerCharacterLevel = playerCharacterLevels[0];
			
			ReturnValue<String> ret = addSomeXpToAClass( playerCharacter,playerCharacterLevel, characterClass, constitution,xpAdd,0);
			if(ret.isError()) {
				return ret;
			} else {
				Popper.popInfo(this, "What happened?", ret.getValue());
				playerCharacterService.update(playerCharacter);
			}
			
		} else {
		
			if(race.isMultiClassable()) {
				// multi-classable; so divide XP by number of classes then add to each individually
				int classCount = playerCharacter.classCount();
				int perClassXp = xpAdd / classCount;
				if(perClassXp==0) {
					return new ReturnValue<>(ReturnValue.IS_ERROR.TRUE,"Its not worth adding the number of XP :(");
				}
				String retValue="";
				for (int i=0,n=classCount;i<n;i++) {
					CharacterClass characterClass = characterClasses[i];
					PlayerCharacterLevel playerCharacterLevel = playerCharacterLevels[i];
					ReturnValue<String> ret =  addSomeXpToAClass( playerCharacter,playerCharacterLevel, characterClass, constitution,perClassXp,0);
					if(ret.isError()) {
						return ret;
					} else {
						retValue = retValue  + characterClass.getName()+ "-  "+ ret.getValue()+"\n";
					}
				}
				Popper.popInfo(this, "What happened?", retValue);
				playerCharacterService.update(playerCharacter);
			} else {
				
				
				
				// dual classable only
				// add XP to biggest class only
				int classCount = playerCharacter.classCount();
				int maxLevel = 0;
				for(int i=0,n=classCount-1;i<n;i++){
					CharacterClass characterClass = characterClasses[i];
					int pcl = playerCharacterLevels[i].getThisClassLevel();
					if(pcl>maxLevel) {
						maxLevel = pcl;
					}
				}

				CharacterClass characterClass = characterClasses[classCount-1];
				PlayerCharacterLevel playerCharacterLevel = playerCharacterLevels[classCount-1];
				ReturnValue<String> ret = addSomeXpToAClass( playerCharacter,playerCharacterLevel, characterClass, constitution,xpAdd,maxLevel);
				if(ret.isError()) {
					return ret;
				} else {

					Popper.popInfo(this, "What happened?", ret.getValue());
					playerCharacterService.update(playerCharacter);
				}
				
			}
		}
		return new ReturnValue<>(ReturnValue.IS_ERROR.FALSE,"");
	}

	private ReturnValue<String> addSomeXpToAClass(PlayerCharacter playerCharacter,
			PlayerCharacterLevel playerCharacterLevel, 
			CharacterClass characterClass,
			Constitution constitution,
			int xpAdd,
			int maxOtherLevel) throws SQLException {

		String retVal="";
		
		CharacterClassLevelService cclService = getDataService();
		
		int level = playerCharacterLevel.getThisClassLevel();
		int xp = playerCharacterLevel.getThisClassExperience();
		int hp  = playerCharacterLevel.getThisClassHp();
		String classId = playerCharacterLevel.getThisClass();
		// are we at maximum level?
		int maxLevel = cclService.getMaxAllowedLevel(playerCharacter.getPcId(), characterClass.getClassId());
		if(level>=maxLevel) {
			return new ReturnValue<String>(ReturnValue.IS_ERROR.TRUE,"Already at maximum level!");
		}
		CharacterClassLevel thisLevel = cclService.getThisLevel(characterClass.getClassId(),level);
		if(thisLevel==null) {
			return  new ReturnValue<String>(ReturnValue.IS_ERROR.TRUE,"I can't work out what level you are!");
		}
		

		int mostHD = characterClass.getMaxHdLevel();
//		if(level==mostHD) {
//			return  new ReturnValue<String>(ReturnValue.IS_ERROR.TRUE,"Already at maximum level!");
//		}
		
		
		CharacterClassLevel nameLevel = cclService.getNameLevel(characterClass.getClassId()); // could be null!
		
		int newXp = xp +  xpAdd;
		// maxOtherLevel is used by DualClass character.
		boolean canHaveMoreHP= (level>=maxOtherLevel);
		if(newXp>thisLevel.getToXp() ) {
			int lostXp = newXp -thisLevel.getToXp();  
			// level up!			
			int newLevel = level+1;
			retVal=retVal+"Leveled up to level "+newLevel + " ";
			int newHp=0;
			if(canHaveMoreHP) {
				if(nameLevel==null || newLevel<=nameLevel.getLevel()) {
					// add one dice HP plus CON bonus
					DICE dice = Roller.getDICE(characterClass.getHitDice());
					int cb = getConBonus(characterClass, constitution);
					newHp = Roller.roll(dice, 1, 0, cb);
					if(newHp<1) {
						newHp=1;
					}
				} else {
					// just add spome points
					newHp = characterClass.getHpAfterNameLevel();
				}
				retVal=retVal+"Extra hitpoints "+newHp + " making total "+(hp+newHp)+ " ";
				if(lostXp>0) {
					retVal=retVal+"You wasted "+lostXp+" XP :( ";
				}
			}
			
			CharacterClassLevel newLevelDetails = cclService.getThisLevel(characterClass.getClassId(),newLevel);
			playerCharacterLevel.setThisClassLevel(newLevel);
			playerCharacterLevel.setThisClassExperience(newLevelDetails.getFromXp());
			playerCharacterLevel.setThisClassHp(hp+newHp);
			//						
		} else {
			// just increment the XP and return
			retVal=retVal+"New xp total is "+newXp + " ";
			playerCharacterLevel.setThisClassExperience(newXp);			
		}
		

		playerCharacter.updateClassDetails(playerCharacterLevel);
		
		
		return  new ReturnValue<String>(retVal);
	}

	private int getConBonus(CharacterClass characterClass, Constitution constitution) {
		int cb=0;
		if(characterClass.isHighConBonus()) {
			constitution.getHitPointAdjustmentHigh();
		} else {
			constitution.getHitPointAdjustment();
		}
		return cb;
	}
	
	
}
