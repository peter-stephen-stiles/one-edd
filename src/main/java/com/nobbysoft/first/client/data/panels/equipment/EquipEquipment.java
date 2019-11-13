package com.nobbysoft.first.client.data.panels.equipment;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.*;
import com.nobbysoft.first.client.components.PBasicTableWithModel.ColumnConfig;
import com.nobbysoft.first.client.components.special.PComboEquipmentHands;
import com.nobbysoft.first.client.components.special.PComboEquipmentWhere;
import com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.equipment.EquipmentHands;
import com.nobbysoft.first.common.entities.equipment.EquipmentI;
import com.nobbysoft.first.common.entities.equipment.EquipmentType;
import com.nobbysoft.first.common.entities.equipment.EquipmentWhere;
import com.nobbysoft.first.common.entities.equipment.WeaponMelee;
import com.nobbysoft.first.common.entities.pc.PlayerCharacterEquipment;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.servicei.PlayerCharacterEquipmentService;
import com.nobbysoft.first.common.servicei.WeaponMeleeService;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.utils.DataMapper;

public class EquipEquipment extends PDialog   {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public EquipEquipment(Window owner) {
		super(owner);
		setModalityType(ModalityType.APPLICATION_MODAL);
		jbInit();
	}
 
	private boolean cancelled = true;
	private String characterName;
	private int pcId;
	private PlayerCharacterEquipment pce;
	private EquipmentI equipment;

	public void setTitleAndCharacterName(String title, 
			int pcId, 
			String characterName, 
			PlayerCharacterEquipment pce,
			EquipmentI equipment) {
		setTitle(title);
		this.characterName = characterName;
		this.pcId = pcId;
		this.pce = pce;  
		this.equipment = equipment;
		//
		
		txtEquipmentWhere.setSubsetOfWheres(pce.getEquipmentType().getValidWheres());
		
		txtEquipmentName.setText(equipment.getName());
		txtCharacterName.setText(characterName);
		txtEquipmentType.setText(pce.getEquipmentType().getDesc());
		EquipmentHands eh=equipment.getRequiresHands();
		txtEquipmentHands.setEquipmentHands(eh);
		
		if(pce.isEquipped()) {
			txtEquipmentWhere.setEquipmentWhere(pce.getEquippedWhere());
		}
		
	 	//
	}

	private PTextField txtCharacterName = new PTextField();
	private PTextField txtEquipmentType = new PTextField();
	private PTextField txtEquipmentName = new PTextField();
	private PComboEquipmentHands txtEquipmentHands = new PComboEquipmentHands();
	private PComboEquipmentWhere txtEquipmentWhere = new PComboEquipmentWhere();
	
	
	private PDataComponent[] disableMe = new PDataComponent[] {
			txtCharacterName,txtEquipmentType,txtEquipmentName,txtEquipmentHands
	};
	
	public void jbInit() {
		setLayout(new BorderLayout());
		PButtonPanel pnlButtons = new PButtonPanel();
		PButton btnCancel = new PButton("Cancel");
		PButton btnEquip  = new PButton("Equip");

		// list of available melee weapons
		// select one
		// whoosh
		pnlButtons.add(btnCancel);
		pnlButtons.add(btnEquip );

		btnCancel.addActionListener(ae -> {
			cancelled = true;
			dispose();
		});
		btnEquip.addActionListener(ae -> {
			//
			ReturnValue<?> ok =validateAndProcessData(); 
			if(!ok.isError()) {

				cancelled=false;
				dispose();
			} else {
				Popper.popError(this, "Error equipping", ok);
			}
		});
  
		PPanel pnlData = new PPanel(new GridBagLayout());
  
		int row=0;
		pnlData.add(new PLabel("Character Name"),GBU.label(0, row));
		pnlData.add(txtCharacterName,GBU.text(1, row));
		row++;

		pnlData.add(new PLabel("Equipment Type"),GBU.label(0, row));
		pnlData.add(txtEquipmentType,GBU.text(1, row));
		row++;

		pnlData.add(new PLabel("Equipment Name"),GBU.label(0, row));
		pnlData.add(txtEquipmentName,GBU.text(1, row));
		row++;

		pnlData.add(new PLabel("Hands?"),GBU.label(0, row));
		pnlData.add(txtEquipmentHands,GBU.text(1, row));
		row++;

		pnlData.add(new PLabel("Where to equip?"),GBU.label(0, row));
		pnlData.add(txtEquipmentWhere,GBU.text(1, row));
		row++;


		pnlData.add(new PLabel(""),GBU.label(99, 99));
		
		
		add(pnlData, BorderLayout.CENTER);
		add(pnlButtons, BorderLayout.SOUTH);

	 for(PDataComponent disable: disableMe) {
		 disable.setReadOnly(true);
	 }

	}

	DataServiceI getDataService(Class clazz) {
		DataServiceI dao = DataMapper.INSTANCE.getDataService(clazz);
		return dao;
	}
 
	public boolean isCancelled() {
		return cancelled;
	}

 
	private  ReturnValue<?> validateAndProcessData() {
		ReturnValue<?> ret = new ReturnValue<>("");
		
		boolean anyGood = txtEquipmentWhere.getEquipmentWhere().equals(EquipmentWhere.PACK)
				|| txtEquipmentWhere.getEquipmentWhere().equals(EquipmentWhere.OTHER); 
		
		if(!anyGood) {
			boolean selectedHands =txtEquipmentWhere.getEquipmentWhere().equals(EquipmentWhere.HAND_L)
					|| txtEquipmentWhere.getEquipmentWhere().equals(EquipmentWhere.HAND_R); 
			if(equipment.getRequiresHands().equals(EquipmentHands.NONE)) {
				if(selectedHands){
					return new ReturnValue(true,"Must not select a hand unless you have to..");
				}
			} else {
				if(!selectedHands){
					return new ReturnValue(true,"Must select a hand. (If double handed- just pick one)");
				}
			}
		}
		// all gone well, let's equip!
		
		PlayerCharacterEquipmentService pces = (PlayerCharacterEquipmentService )getDataService(PlayerCharacterEquipment.class);
		try {
			
			pces.equip(pce, txtEquipmentWhere.getEquipmentWhere());
		} catch (SQLException e) {
			Popper.popError(this, e);
		}		
		// 
		
		
		return ret;
	}

}
