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
import com.nobbysoft.first.common.entities.equipment.EquipmentI;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterEquipment;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.servicei.PlayerCharacterEquipmentService;
import com.nobbysoft.first.common.views.ViewPlayerCharacterEquipment;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class PlayerCharacterEquipmentPanel extends PPanel {


	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	
	public PlayerCharacterEquipmentPanel() {
		super(new BorderLayout());
		jbInit();
	}
	
	private PlayerCharacterUpdatedListener pcUpdatedListener;
	
	public void addPlayerCharacterUpdatedListener(PlayerCharacterUpdatedListener pcUpdatedListener) {
		this.pcUpdatedListener = pcUpdatedListener;
	}
	
	private int pcId;
	private String characterName=null;
	
	public void initialiseCharacter(int pcId,String characterName) {
		this.pcId=pcId;
		this.characterName=characterName;
		

		SwingUtilities.invokeLater(()->populateEquipmentTable());
	}
	
	// equipment
	// 
	 
	private final PButton btnEquipmentAdd = new PButton("Add");
	private final PButton btnEquipmentRemove = new PButton("Remove");
	private final PButton btnEquipmentEquip = new PButton("Equip");
	private final PButton btnEquipmentUnEquip = new PButton("Unequip");
	
	private PDataComponent[] buttonComponents = new PDataComponent[] {btnEquipmentAdd ,
		    btnEquipmentRemove ,
		    btnEquipmentEquip ,
			btnEquipmentUnEquip  };

	
		
private final ColumnConfig[] equipmentConfigs = new ColumnConfig[] {
		new ColumnConfig("",0,0,0),
		new ColumnConfig("Name",20,200,5000),
		new ColumnConfig("Type",20,150,5000),
		new ColumnConfig("Equipped",20,60,5000),
		new ColumnConfig("Where",20,100,5000),
};

private final PBasicTableWithModel tblEquipment = new PBasicTableWithModel(equipmentConfigs);



private Map<String,Class<? extends AddEquipmentI>> equipmentMenu = new HashMap<>();
{
	equipmentMenu.put("Arms - Melee", AddEquipmentMelee.class);
	equipmentMenu.put("Arms - Ranged/Thrown", AddEquipmentRanged.class);
	equipmentMenu.put("Ammunition", AddEquipmentAmmunition.class); 
	equipmentMenu.put("Armour", AddEquipmentArmour.class); 
	equipmentMenu.put("Shield", AddEquipmentShield.class); 
}

	private PIntegerField txtTotalEnc = new PIntegerField();
	private PIntegerField txtEquippedEnd = new PIntegerField();

	private void disableThings() {
		for(PDataComponent pc :disableMe ) {
			pc.setReadOnly(true);
		}
	}
	
	private PDataComponent[] disableMe = new PDataComponent[] {txtTotalEnc ,
			txtEquippedEnd , };
	
	private void jbInit() {
		
		txtTotalEnc.setMinimumWidth(70);
		txtEquippedEnd.setMinimumWidth(70);

		final PPanel pnlEquipmentButtons = new PPanel(new FlowLayout(FlowLayout.RIGHT));
		
		pnlEquipmentButtons.add(btnEquipmentAdd);
		pnlEquipmentButtons.add(btnEquipmentRemove);
		pnlEquipmentButtons.add(new PLabel("  "));
		pnlEquipmentButtons.add(btnEquipmentEquip);
		pnlEquipmentButtons.add(btnEquipmentUnEquip);
		
		
		JScrollPane sclEquipment = new JScrollPane(tblEquipment);
		
		PPanel pnlInfo = new PPanel(new FlowLayout(FlowLayout.LEFT));
		
		
		add(pnlEquipmentButtons,BorderLayout.NORTH);
		
		add(sclEquipment,BorderLayout.CENTER);
		add(pnlInfo,BorderLayout.SOUTH);
		
		pnlInfo.add(new PLabel("Total enc:"));
		pnlInfo.add(txtTotalEnc);
		pnlInfo.add(new PLabel("Equipped enc:"));
		pnlInfo.add(txtEquippedEnd);
		
		

		btnEquipmentRemove.addActionListener(ae ->{
			removeEquipment();
		});
		
		btnEquipmentEquip.addActionListener(ae ->{
			equipEquipment();
		});

		btnEquipmentUnEquip.addActionListener(ae ->{
			unEquipEquipment();
		});
		
		tblEquipment.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					btnEquipmentEquip.doClick();
				}
			}
		});
		
		
		btnEquipmentAdd.addActionListener(ae ->{
			LOGGER.info("pop up now!");
			JPopupMenu menu = new JPopupMenu("Add");
			for(String m:equipmentMenu.keySet() ){
				JMenuItem mi = new JMenuItem(m);
				menu.add(mi);
				mi.addActionListener(a2e ->{
					addEquipment(m,equipmentMenu.get(m));
				});
			}
			
			menu.show(btnEquipmentAdd,5,5);
			
		});

		disableThings();
	}

	public PDataComponent[] getButtonComponents() { 
		return buttonComponents;
	}


	private void addEquipment(String title,Class<? extends AddEquipmentI> clazz) {
		
		Class[] pt = new Class[] {Window.class};
		
		Constructor<? extends AddEquipmentI> c;
		try {
			c = clazz.getConstructor(pt);

			AddEquipmentI aei = c.newInstance(GuiUtils.getParent(this));
			
			aei.setTitleAndCharacterName(title, pcId, characterName);
			PDialog dialog =(PDialog)aei;
			
			
			dialog.pack();
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
			if(!aei.isCancelled()) {
				// refresh the list!
				populateEquipmentTable();
			}
		
		
		} catch (Exception e) {
			Popper.popError(this, e);
		}
		
	}	

	private void unEquipEquipment() {
		PlayerCharacterEquipment pce=null;
		String name="";
		int rowNum=tblEquipment.getSelectedRow();
		if(rowNum>=0) {
			int modelRow =tblEquipment.convertRowIndexToModel(rowNum);
			Object o0=tblEquipment.getValueAt(modelRow, 0);
			Object o1=tblEquipment.getValueAt(modelRow, 1);
			pce = (PlayerCharacterEquipment)o0;
			name = (String)o1;
		}
		if(pce!=null) {
			if(!pce.isEquipped()) {
				Popper.popError(this, "Not equipped!", "That's not equipped.");
				return;
			} else {
				
				String desc = pce.getDescription();
				boolean ok=Popper.popYesNoQuestion(this,"Remove equipment","Confirm that you want to remove "+name+"?");
				if(ok) {
					PlayerCharacterEquipmentService pces = (PlayerCharacterEquipmentService )getDataService(PlayerCharacterEquipment.class);
					try {
						pce.setEquipped(false);
						pce.setEquippedWhere(null);
						pces.update(pce);
						populateEquipmentTable();
					} catch (SQLException e) {
						Popper.popError(this, e);
					}				
				}
				
				
				
			}
			
		}
		
	}
	
	
	DataServiceI getDataService(Class clazz) {
		DataServiceI dao = DataMapper.INSTANCE.getDataService(clazz);
		return dao;
	}

	private void equipEquipment() {
		PlayerCharacterEquipment pce=null;
		String name="";
		int rowNum=tblEquipment.getSelectedRow();
		if(rowNum>=0) {
			int modelRow =tblEquipment.convertRowIndexToModel(rowNum);
			Object o0=tblEquipment.getValueAt(modelRow, 0);
			Object o1=tblEquipment.getValueAt(modelRow, 1);
			pce = (PlayerCharacterEquipment)o0;
			name = (String)o1;
		}
		if(pce!=null) {
//			if(pce.isEquipped()) {
//				Popper.popError(this, "Already equipped!", "That's already equipped");
//				return;
//			} else {
//				
//			}
			
			EquipEquipment aei = new EquipEquipment(GuiUtils.getParent(this));
			
			EquipmentI equipment=null;
			try {
			PlayerCharacterEquipmentService pces = (PlayerCharacterEquipmentService )getDataService(PlayerCharacterEquipment.class);
				equipment=pces.getUnderlyingEquipment(pce.getCode(), pce.getEquipmentType());
			} catch (SQLException e) {
			Popper.popError(this, e);
		}	
			aei.setTitleAndCharacterName("Equip "+name, pcId, characterName,
					pce,equipment);
			PDialog dialog =(PDialog)aei;
			
			
			dialog.pack();
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
			if(!aei.isCancelled()) {
				// refresh the list!
				populateEquipmentTable();
			}
			
		}
	}
	
	private void removeEquipment() {
		PlayerCharacterEquipment pce=null;
		String name="";
		int rowNum=tblEquipment.getSelectedRow();
		if(rowNum>=0) {
			int modelRow =tblEquipment.convertRowIndexToModel(rowNum);
			Object o0=tblEquipment.getValueAt(modelRow, 0);
			Object o1=tblEquipment.getValueAt(modelRow, 1);
			//Object o2=tblEquipment.getValueAt(modelRow, 2);
			//Object o3=tblEquipment.getValueAt(modelRow, 3);
			//LOGGER.info(" o0:"+o0+"o1:"+o1+" o2:"+o2+" o3:"+o3);
			pce = (PlayerCharacterEquipment)o0;
			name = (String)o1;
		}
		if(pce!=null) {
			String desc = pce.getDescription();
			boolean ok=Popper.popYesNoQuestion(this,"Remove equipment","Confirm that you want to remove "+name+"?");
			if(ok) {
				PlayerCharacterEquipmentService pces = (PlayerCharacterEquipmentService )getDataService(PlayerCharacterEquipment.class);
				try {
					pces.delete(pce);
					populateEquipmentTable();
				} catch (SQLException e) {
					Popper.popError(this, e);
				}				
			}
		}
	}


	private PlayerCharacterEquipment getSelectedEquipment() {
		PlayerCharacterEquipment pce=null;
		int rowNum=tblEquipment.getSelectedRow();
		if(rowNum>=0) {
			int modelRow =tblEquipment.convertRowIndexToModel(rowNum);
			pce = (PlayerCharacterEquipment)tblEquipment.getValueAt(modelRow, 0);
		}
		return pce;
	}
	
	private void populateEquipmentTable() {

		int sr = tblEquipment.getSelectedRow();
		Object selectedObject = null;
		if(sr>=0 &&sr<tblEquipment.getRowCount()) {
			int modelRow =tblEquipment.convertRowIndexToModel(sr);
			selectedObject = (PlayerCharacterEquipment)tblEquipment.getValueAt(modelRow, 0);
		}

		int enc = 0;
		int equippedEnc = 0;
		
		tblEquipment.clearTable();
		if (pcId>0) {
			
			try {
			PlayerCharacterEquipmentService pces = (PlayerCharacterEquipmentService )getDataService(PlayerCharacterEquipment.class);
			List<ViewPlayerCharacterEquipment> list = pces.getForPC(pcId);
			
			
			for(ViewPlayerCharacterEquipment vpce: list) {
				PlayerCharacterEquipment pce = vpce.getPlayerCharacterEquipment();
				String desc = vpce.getDescription();			
				enc+=vpce.getEncumberence();
				if(pce.isEquipped()) {
					equippedEnc+=vpce.getEncumberence();
				}
				tblEquipment.addRow(new Object[] {
						pce,	
						desc,
						pce.getEquipmentType(),
						pce.isEquipped(),
						pce.getEquippedWhere()
				});
			}
			} catch (Exception ex) {
				Popper.popError(this, ex);
			}
			if(selectedObject!=null) {
				//LOGGER.info("selected object "+selectedObject);
				for(int i=0,n=tblEquipment.getRowCount();i<n;i++) {
					int modelRow =tblEquipment.convertRowIndexToModel(i);
					Object value = tblEquipment.getValueAt(modelRow, 0);
					//LOGGER.info("value "+value);
					if(value.equals(selectedObject)) {
						//LOGGER.info("selected "+i + " model row:"+modelRow);
						tblEquipment.getSelectionModel().setSelectionInterval(i,i);
						break;
					}
				}
			}
		}
		txtTotalEnc.setIntegerValue(enc);
		txtEquippedEnd.setIntegerValue(equippedEnc);
	}
	
}
