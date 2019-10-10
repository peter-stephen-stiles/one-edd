package com.nobbysoft.first.client.data.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PBasicTableWithModel;
import com.nobbysoft.first.client.components.PButton;
import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.client.components.PIntegerField;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.first.client.components.PBasicTableWithModel.ColumnConfig;
import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.pc.PlayerCharacter;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterSpell;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterSpellKey;
import com.nobbysoft.first.common.entities.staticdto.Spell;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.servicei.PlayerCharacterSpellService;
import com.nobbysoft.first.common.views.ViewPlayerCharacterSpell;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class PlayerCharacterSpellPanel extends PPanel {


	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	
	public PlayerCharacterSpellPanel() {
		super(new BorderLayout());
		jbInit();
	}
	
	private int pcId;
	private String characterName=null;
	private PlayerCharacter pc=null;
	
	public void initialiseCharacter(PlayerCharacter pc) {
		this.pcId=pc.getPcId();
		this.characterName=pc.getCharacterName();
		this.pc = pc;
		

		SwingUtilities.invokeLater(()->populateSpellTable());
	}
	
	// Spell
	// 
	 
	private final PButton btnSpellAdd = new PButton("Add");
	private final PButton btnSpellRemove = new PButton("Remove");
	private final PButton btnSpellMemorise = new PButton("Memorise");
	private final PButton btnSpellForget = new PButton("Forget");
	
	private PDataComponent[] buttonComponents = new PDataComponent[] {btnSpellAdd ,
		    btnSpellRemove ,
		    btnSpellMemorise ,
			btnSpellForget  };

private final ColumnConfig[] SpellConfigs = new ColumnConfig[] {
		new ColumnConfig("",0,0,0),
		new ColumnConfig("Spell Class",20,200,5000),
		new ColumnConfig("Level",20,80,5000),
		new ColumnConfig("Description",20,360,5000),
		new ColumnConfig("#In Memory",20,70,5000),
};

private final PBasicTableWithModel tblSpell = new PBasicTableWithModel(SpellConfigs);


 
	private PLabel txtMemByLevel = new PLabel("");
	

	private void disableThings() {
		for(PDataComponent pc :disableMe ) {
			pc.setReadOnly(true);
		}
	}
	
	private PDataComponent[] disableMe = new PDataComponent[] {};
	
	private void jbInit() {
		 

		final PPanel pnlSpellButtons = new PPanel(new FlowLayout(FlowLayout.RIGHT));
		
		pnlSpellButtons.add(btnSpellAdd);
		pnlSpellButtons.add(btnSpellRemove);
		pnlSpellButtons.add(new PLabel("  "));
		pnlSpellButtons.add(btnSpellMemorise);
		pnlSpellButtons.add(btnSpellForget);
		
		
		btnSpellMemorise.addActionListener(ae->memorise());
		btnSpellForget.addActionListener(ae->forget());
		
		JScrollPane sclSpell = new JScrollPane(tblSpell);
		
		PPanel pnlInfo = new PPanel(new FlowLayout(FlowLayout.LEFT));
		
		
		add(pnlSpellButtons,BorderLayout.NORTH);
		
		add(sclSpell,BorderLayout.CENTER);
		add(pnlInfo,BorderLayout.SOUTH);
		
		pnlInfo.add(new PLabel("Memorised by level:"));
		pnlInfo.add(txtMemByLevel); 
		
		

		btnSpellRemove.addActionListener(ae ->{
			removeSpell();
		});
		
 
		
		tblSpell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					btnSpellMemorise.doClick();
				}
			}
		});
		
		
		btnSpellAdd.addActionListener(ae ->{
			LOGGER.info("pop up now!");
			addSpell();
			
		});

		disableThings();
	}

	public PDataComponent[] getButtonComponents() { 
		return buttonComponents;
	}

	
	
	DataServiceI getDataService(Class clazz) {
		DataServiceI dao = DataMapper.INSTANCE.getDataService(clazz);
		return dao;
	}

 
	
	private void addSpell() {
		
		AddPlayerCharacterSpell add = new AddPlayerCharacterSpell(GuiUtils.getParent(this));
		add.setPcId(pc.getPcId(),pc.getFirstClass());
		add.pack();
		add.setLocationRelativeTo(null);
		add.setVisible(true);
		if(!add.isCancelled()) {
			// refresh
			populateSpellTable();
		}
		
		
	}
	
	private void removeSpell() {
		PlayerCharacterSpell pce=null;
		ViewPlayerCharacterSpell vpce=null;
		//String name="";
		int rowNum=tblSpell.getSelectedRow();
		if(rowNum>=0) {
			vpce = getSelectedSpell(rowNum);
		
			pce = vpce.getPlayerCharacterSpell();
			if(pce!=null) {
				String desc = vpce.getSpell().getName();
				boolean ok=Popper.popYesNoQuestion(this,"Remove Spell","Confirm that you want to remove spell "+desc+"?");
				if(ok) {
					PlayerCharacterSpellService pces = (PlayerCharacterSpellService )getDataService(PlayerCharacterSpell.class);
					try {
						pces.delete(pce);
						populateSpellTable();
					} catch (SQLException e) {
						Popper.popError(this, e);
					}				
				}
			}
		}
	}

	private ViewPlayerCharacterSpell getSelectedSpell(int rowNum) {
		ViewPlayerCharacterSpell vpce;
		int modelRow =tblSpell.convertRowIndexToModel(rowNum);
		Object o0=tblSpell.getValueAt(modelRow, 0);
		//Object o1=tblSpell.getValueAt(modelRow, 1);
		//Object o2=tblSpell.getValueAt(modelRow, 2);
		//Object o3=tblSpell.getValueAt(modelRow, 3);
		//LOGGER.info(" o0:"+o0+"o1:"+o1+" o2:"+o2+" o3:"+o3);
		vpce=(ViewPlayerCharacterSpell)o0;			
		//name = (String)o1;
		return vpce;
	}


	private PlayerCharacterSpell getSelectedSpell() {
		PlayerCharacterSpell pce=null;
		int rowNum=tblSpell.getSelectedRow();
		if(rowNum>=0) {
			int modelRow =tblSpell.convertRowIndexToModel(rowNum);
			pce = (PlayerCharacterSpell)tblSpell.getValueAt(modelRow, 0);
		}
		return pce;
	}
	
	private void memorise() {
		
		PlayerCharacterSpell pce=null;
		ViewPlayerCharacterSpell vpce=null;
		//String name="";
		int rowNum=tblSpell.getSelectedRow();
		if(rowNum>=0) {
			vpce = getSelectedSpell(rowNum);		
			pce = vpce.getPlayerCharacterSpell();
			if(pce!=null) {
				PlayerCharacterSpellService pces = (PlayerCharacterSpellService )getDataService(PlayerCharacterSpell.class);
				try {
					PlayerCharacterSpell spell = pces.get(pce.getKey());				
					spell.setInMemory(spell.getInMemory()+1);
					pces.update(spell);
					populateSpellTable();
				} catch (Exception e) {
					Popper.popError(this, e);
				}
			}
			}
		
	}
	private void forget() {
		
		PlayerCharacterSpell pce=null;
		ViewPlayerCharacterSpell vpce=null;
		//String name="";
		int rowNum=tblSpell.getSelectedRow();
		if(rowNum>=0) {
			vpce = getSelectedSpell(rowNum);		
			pce = vpce.getPlayerCharacterSpell();
			if(pce!=null) {
				PlayerCharacterSpellService pces = (PlayerCharacterSpellService )getDataService(PlayerCharacterSpell.class);
				try {
					PlayerCharacterSpell spell = pces.get(pce.getKey());
					if(spell.getInMemory()>0) {
						spell.setInMemory(spell.getInMemory()-1);
						pces.update(spell);
					}
					populateSpellTable();					
				} catch (Exception e) {
					Popper.popError(this, e);
				}
			}
			}
		
	}
	
	private void populateSpellTable() {

		int sr = tblSpell.getSelectedRow();
		ViewPlayerCharacterSpell selectedObject = null;
		if(sr>=0 &&sr<tblSpell.getRowCount()) {
			int modelRow =tblSpell.convertRowIndexToModel(sr);
			selectedObject = (ViewPlayerCharacterSpell)tblSpell.getValueAt(modelRow, 0);
		}
 
		int[] memorised = new int[10];// no 0th levels :)
		
		tblSpell.clearTable();
		if (pcId>0) {
			
			try {
			PlayerCharacterSpellService pces = (PlayerCharacterSpellService )getDataService(PlayerCharacterSpell.class);
			List<ViewPlayerCharacterSpell> list = pces.getViewForPC(pcId);
			
			
			for(ViewPlayerCharacterSpell vpce: list) {
				PlayerCharacterSpell pce = vpce.getPlayerCharacterSpell();
				Spell s = vpce.getSpell();
				String desc = vpce.getDescription();			
 
				tblSpell.addRow(vpce.getAsRow());
				memorised[s.getLevel()]=(pce.getInMemory()+memorised[s.getLevel()]); 
			}
			} catch (Exception ex) {
				Popper.popError(this, ex);
			}
			if(selectedObject!=null) {
				//LOGGER.info("selected object "+selectedObject);
				PlayerCharacterSpellKey k0=selectedObject.getKey();
				for(int i=0,n=tblSpell.getRowCount();i<n;i++) {
					int modelRow =tblSpell.convertRowIndexToModel(i);
					ViewPlayerCharacterSpell value = (ViewPlayerCharacterSpell)tblSpell.getValueAt(modelRow, 0);
					//LOGGER.info("value "+value);
					PlayerCharacterSpellKey k1=value.getKey();
					if(k0.equals(k1)) {
						//LOGGER.info("selected "+i + " model row:"+modelRow);
						tblSpell.getSelectionModel().setSelectionInterval(i,i);
						break;
					}
				}
			}
			StringBuilder sb = new StringBuilder();
			for(int i=1,n=10;i<n;i++) {
				if(i>1) {
					sb.append(", ");
				}
				sb.append("Lvl:").append(i).append(" ").append(memorised[i]);
				
			}
			txtMemByLevel.setText(sb.toString());
		} 
	}
	
}
