package com.nobbysoft.first.client.data.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import com.nobbysoft.first.client.components.PButton;
import com.nobbysoft.first.client.components.PButtonPanel;
import com.nobbysoft.first.client.components.PIntegerField;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.first.client.components.PTextArea;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.utils.DICE;
import com.nobbysoft.first.common.utils.Roller;

@SuppressWarnings("serial")
public class RollHPDialog extends JDialog {

	private Window parent;
	public RollHPDialog(Window owner, String title) {
		super(owner, title,ModalityType.APPLICATION_MODAL);
		parent=owner;
		jbInit();
	}
	private  PLabel lblClassName;  
	private  PIntegerField txtConsBonus;
	private PTextArea txtResults;
	private  PIntegerField txtLevel;
	private PButton btnRoll;
	
	public Dimension getPreferredSize() {
		Dimension d = super.getPreferredSize();
		if(d.height<400) {
			d.height=400;
		}
		if(d.width<400) {
			d.width=400;
		}
		return d;
	}
	
	private void jbInit() {
		PPanel pnlBackPane = new PPanel();
		this.getContentPane().add(pnlBackPane);
		PPanel pnlHeader = new PPanel(new GridBagLayout());
		pnlHeader.add(new PLabel("What level character do you want to roll?"),GBU.text(0, 0,3));
		//
		lblClassName = new PLabel("<class>");
		PLabel  lblConsBonus = new PLabel("Consitution bonus HP per die");
		btnRoll = new PButton("Roll!");
		txtConsBonus = new PIntegerField();
		PLabel  lblLevel = new PLabel("Level");
		txtLevel= new PIntegerField();
		
		pnlHeader.add(lblClassName,GBU.text(0, 1));
		pnlHeader.add(lblLevel,GBU.label(0, 2));
		pnlHeader.add(txtLevel,GBU.text(1, 2));
		pnlHeader.add(lblConsBonus,GBU.label(0, 3));
		pnlHeader.add(txtConsBonus,GBU.text(1, 3));
		pnlHeader.add(btnRoll,GBU.button(2, 3));
		txtLevel.setText("1");
		btnRoll.setEnabled(false);
		btnRoll.addActionListener(ae ->{
			roll();
		});
		txtResults = new PTextArea();
		JScrollPane sclResults = new JScrollPane(txtResults) {
			public Dimension getMinimumSize() {
				Dimension d= super.getMinimumSize();
				if(d.height<100) {
					d.height=100;
				}
				return d;
			}
			public Dimension getPreferredSize() {
				Dimension d= super.getPreferredSize();
				if(d.height<100) {
					d.height=100;
				}
				return d;
			}
		};
		
		PButtonPanel pnlButtons = new PButtonPanel();
		PButton btnCancel = new PButton("Cancel");
		btnCancel.addActionListener(ae -> dispose());
		pnlButtons.add(btnCancel);
		
		pnlBackPane.add(pnlHeader,BorderLayout.NORTH);
		pnlBackPane.add(sclResults);
		pnlBackPane.add(pnlButtons,BorderLayout.SOUTH);
		
		
	}
	
	private CharacterClass cc=null;

	public void initialise(CharacterClass cc) {
		this.cc=cc;
		btnRoll.setEnabled(cc!=null);
		lblClassName.setText(cc.getDescription());
		
	}
 
	private void roll() {
		final int level =txtLevel.getIntegerValue();
		if(level<=0) {
			Popper.popError(parent, "Invalid level", "Level must be bigger than zero");
		}
		
		SwingUtilities.invokeLater(() ->{
			//
			int consBonus=txtConsBonus.getIntegerValue();
			DICE dice = Roller.getDICE(cc.getHitDice());
			String line = "==========="+cc.getDescription()+ " level:"+level;
			if(consBonus>0) {
				line = line + " Cons bonus:"+consBonus;
			}
			addLine(line);
			int hd_1 = Roller.roll(dice, cc.getHitDiceAtFirstLevel(), consBonus, 0);
			int total=hd_1;
			addLine("Level 1:"+hd_1);
			if(level>1) {
				int makeRolls = level-1;
				int apresRolls = 0;
				if(level > cc.getMaxHdLevel()) {
					apresRolls = level - cc.getMaxHdLevel();
					makeRolls = cc.getMaxHdLevel() - 1;
				}
				int[] rolls = Roller.rollHistory(dice, makeRolls, consBonus);
				for(int i=0,n=rolls.length;i<n;i++) {
					total=total+rolls[i];
					addLine("Level "+(i+2)+":"+rolls[i]);
				}
				if(apresRolls>0) {
					for(int i=0,n=apresRolls;i<n;i++) {
						int inc = cc.getHpAfterNameLevel(); // note:no cons bonus after name level!
						total += inc;
						addLine("Level "+(i+cc.getMaxHdLevel()+1)+":"+inc);
					}	
					
				}
			}
			addLine("total:"+total);
		});
	}

	private void addLine(String line) {
		txtResults.setText(txtResults.getText()+"\n"+line);
	}
}
