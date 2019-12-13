package com.nobbysoft.first.client.data.panels.staticdata;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import com.nobbysoft.first.client.components.PButton;
import com.nobbysoft.first.client.components.PButtonPanel;
import com.nobbysoft.first.client.components.PCheckBox;
import com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PList;
import com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.first.client.components.PTextField;
import com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.first.common.entities.equipment.EquipmentClass;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.servicei.EquipmentClassService;
import com.nobbysoft.first.common.views.ViewClassEquipment;
import com.nobbysoft.first.utils.DataMapper;

public class CharacterClassEquipmentDialog  extends PDialog{

	private CharacterClass characterClass;
	
	public CharacterClassEquipmentDialog(Window owner, String title) {
		super(owner, title, ModalityType.DOCUMENT_MODAL);
		
		jbInit();
	}
	
	public void initialise(CharacterClass characterClass) {
		this.characterClass=characterClass;
		lblClassId.setText(characterClass.getClassId());
		lblClassName.setText(characterClass.getName());
		//
		// get data
		EquipmentClassService dao = (EquipmentClassService)DataMapper.INSTANCE.getDataService(EquipmentClass.class);
		items.clear();
		try {
			items.addAll(dao.getViewForClassAll(characterClass.getClassId()));
		} catch (SQLException e) {
			Popper.popError(this, e);
		}
		filter();
	}
	
	private final PLabel lblClassId = new PLabel();
	private final PLabel lblClassName = new PLabel();
	private final PTextField txtFilter = new PTextField();
	private List<ViewClassEquipment> items = new ArrayList<>();
	private final PList<ViewClassEquipment> lstItems = new PList<ViewClassEquipment>();
	private final PCheckBox chkFilterAssigned = new PCheckBox("Assigned only?");
	private final PCheckBox chkFilterUnassigned = new PCheckBox("Unassigned only?");
	
	private void jbInit() {
		JPanel thisPane = (JPanel)this.getContentPane();
		thisPane.setLayout(new BorderLayout(5,5));
		
		PPanel pnlHeader = new PPanel(new FlowLayout(5));

		chkFilterUnassigned.addActionListener(ae ->{
			if(chkFilterUnassigned.isSelected()) {
				chkFilterAssigned.setSelected(false);				
			}
			filter();
		});
		chkFilterAssigned.addActionListener(ae ->{
			if(chkFilterAssigned.isSelected()) {
				chkFilterUnassigned.setSelected(false);
			}
			filter();
		});
		
		PButtonPanel pnlButtons = new PButtonPanel();
		thisPane.add(pnlHeader,BorderLayout.NORTH);
		thisPane.add(pnlButtons,BorderLayout.SOUTH);
		
		PButton btnClose = new PButton("Close");
		btnClose.addActionListener(ae-> dispose() );
		pnlButtons.add(btnClose);
		
		pnlHeader.add(new PLabel("Class"));
		pnlHeader.add(lblClassId);
		pnlHeader.add(lblClassName);
		txtFilter.setMinimumWidth(150);
		pnlHeader.add(new PLabel("Filter"));
		pnlHeader.add(txtFilter);
		txtFilter.addActionListener(ae -> filter());
		
		PButton btnFilter = new PButton("Filter");
		btnFilter.addActionListener(ae -> filter());
		
		pnlHeader.add(chkFilterAssigned);
		pnlHeader.add(chkFilterUnassigned);
		pnlHeader.add(btnFilter);
		
		
		JScrollPane sclItems = new JScrollPane(lstItems);
		thisPane.add(sclItems,BorderLayout.CENTER);
	
		
		lstItems.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if(me.getClickCount()==2) {
					ViewClassEquipment e =lstItems.getSelectedValue();
					e.setAssigned(!e.isAssigned());
					lstItems.setSelectedValue(e,false);
				}
			}
		});
		
		lstItems.setCellRenderer(new ListCellRenderer<ViewClassEquipment>() {
			final JCheckBox lbl = new JCheckBox();
			final Color back = lbl.getBackground();
			final Color dark = Color.cyan;
			final boolean op = lbl.isOpaque();
			@Override
			public Component getListCellRendererComponent(JList<? extends ViewClassEquipment> list, ViewClassEquipment value, int index,
					boolean isSelected, boolean cellHasFocus) {
								
				String s= (value.getEquipmentClass().getType()+ " - "+value.getEquipmentName() );				
				lbl.setSelected(value.isAssigned());
				lbl.setText(s);
				if(isSelected||cellHasFocus){
					lbl.setOpaque(true);
					lbl.setBackground(dark);
				} else {
					lbl.setOpaque(op);
					lbl.setBackground(back);
				}
				return lbl;
			}
			
		});
		
		
	}

	
	private void filter() {
		// add all relevant items to filter
		String filter = txtFilter.getText().toLowerCase().trim();
		
		boolean ass = chkFilterAssigned.isSelected();
		boolean unass = chkFilterUnassigned.isSelected();
		if(ass&&unass) {
			chkFilterUnassigned.setSelected(false);
			unass = chkFilterUnassigned.isSelected();
		}
		
		Vector<ViewClassEquipment> v = new Vector<ViewClassEquipment>();
		if(filter.isEmpty() && !ass && !unass) {
			v.addAll(items);
		} else {
			for(ViewClassEquipment i:items) {
				String s= (i.getEquipmentClass().getType()+ " - "+i.getEquipmentName() );
				boolean assigned = i.isAssigned();
				boolean assMatch = true;
				if(ass) {
					assMatch = assigned;
				}
				if(unass) {
					assMatch = !assigned;
				}
				if(s.toLowerCase().contains(filter) && assMatch) {
					v.add(i);
				}
			}
		}
		lstItems.setListData(v);
	}
}

