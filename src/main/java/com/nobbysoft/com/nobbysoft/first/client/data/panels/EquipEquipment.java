package com.nobbysoft.com.nobbysoft.first.client.data.panels;

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

import com.nobbysoft.com.nobbysoft.first.client.components.*;
import com.nobbysoft.com.nobbysoft.first.client.components.PBasicTableWithModel.ColumnConfig;
import com.nobbysoft.com.nobbysoft.first.client.components.special.PComboEquipmentHands;
import com.nobbysoft.com.nobbysoft.first.client.utils.GBU;
import com.nobbysoft.com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.EquipmentHands;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.EquipmentI;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.EquipmentType;
import com.nobbysoft.com.nobbysoft.first.common.entities.equipment.WeaponMelee;
import com.nobbysoft.com.nobbysoft.first.common.entities.pc.PlayerCharacterEquipment;
import com.nobbysoft.com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.com.nobbysoft.first.common.servicei.PlayerCharacterEquipmentService;
import com.nobbysoft.com.nobbysoft.first.common.servicei.WeaponMeleeService;
import com.nobbysoft.com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.com.nobbysoft.first.utils.DataMapper;

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
		txtCharacterName.setText(characterName);
		txtEquipmentType.setText(pce.getEquipmentType().getDesc());
		EquipmentHands eh=equipment.getRequiresHands();
		txtEquipmentHands.setEquipmentHands(eh);
		if(eh==null||eh==EquipmentHands.NONE) {
			txtEquipmentWhere.setReadOnly(true);
		} else {
			txtEquipmentWhere.setReadOnly(false);
		}	
	 	//
	}

	private PTextField txtCharacterName = new PTextField();
	private PTextField txtEquipmentType = new PTextField();
	private PTextField txtEquipmentName = new PTextField();
	private PComboEquipmentHands txtEquipmentHands = new PComboEquipmentHands();
	private PComboEquipmentHands txtEquipmentWhere = new PComboEquipmentHands();
	
	
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
			ReturnValue<?> ok =validateData(); 
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

 
	private  ReturnValue<?> validateData() {
		ReturnValue<?> ret = new ReturnValue<>("");
		
		
		
		return ret;
	}

}
