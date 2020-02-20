package com.nobbysoft.first.client.data.panels.equipment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import com.nobbysoft.first.common.entities.equipment.EquipmentI;
import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.servicei.CharacterClassService;
import com.nobbysoft.first.common.servicei.EquipmentClassService;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.nobbysoft.first.common.views.ViewClassEquipment;
import com.nobbysoft.first.utils.DataMapper;

@SuppressWarnings("serial")
public class EquipmentCharacterClassDialog extends PDialog{


	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	private EquipmentI equipment;
	
	public EquipmentCharacterClassDialog(Window owner, String title) {
		super(owner, title, ModalityType.DOCUMENT_MODAL);
		
		jbInit();
	}
	
	public void initialise(EquipmentI equipment) {
		this.equipment=equipment;
		lblEquipmentType.setText(equipment.getType().getDescription());
		lblEquipmentName.setText(equipment.getName());
		//
		//
		CharacterClassService ccs = (CharacterClassService)DataMapper.INSTANCE.getDataService(CharacterClass.class);
		try {
			List<CharacterClass> ccl = ccs.getList();
			for(CharacterClass cc:ccl) {
				characterClassNames.put(cc.getClassId(),cc.getName());
			}
		} catch (SQLException e) {
			Popper.popError(this, e);
		}
		// get data
		EquipmentClassService dao = (EquipmentClassService)DataMapper.INSTANCE.getDataService(EquipmentClass.class);
		items.clear();
		try {
			items.addAll(dao.getViewForEquipmentAll(equipment.getType().name(),equipment.getCode()));
		} catch (SQLException e) {
			Popper.popError(this, e);
		}
		filter();
	}
	
	private final PLabel lblEquipmentType = new PLabel();
	private final PLabel lblEquipmentName = new PLabel();
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

		
		PButton btnSelectAll = new PButton("Select All");
		btnSelectAll.addActionListener(ae-> selectAll() );

		PButton btnSelectNone = new PButton("Select None");
		btnSelectNone.addActionListener(ae-> selectNone() );

		
		PButton btnSave = new PButton("Save");
		btnSave.addActionListener(ae-> save() );
		
		
		PButton btnClose = new PButton("Cancel");
		btnClose.addActionListener(ae-> dispose() );
		
		pnlButtons.add(btnSelectAll);
		pnlButtons.add(btnSelectNone);
		pnlButtons.add(new PLabel("      "));
		
		pnlButtons.add(btnSave);
		pnlButtons.add(btnClose);
		
		pnlHeader.add(new PLabel("Equipment"));
		pnlHeader.add(lblEquipmentType);
		pnlHeader.add(lblEquipmentName);
		txtFilter.setMinimumWidth(150);
		pnlHeader.add(new PLabel("Filter"));
		pnlHeader.add(txtFilter);
		txtFilter.addActionListener(ae -> filter());
		
		PButton btnFilter = new PButton("Filter");
		btnFilter.addActionListener(ae -> filter());
		
		pnlHeader.add(chkFilterAssigned);
		pnlHeader.add(chkFilterUnassigned);
		pnlHeader.add(btnFilter);
		
		
		JScrollPane sclItems = new JScrollPane(lstItems) {
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				if(d.height<240) {
					d.height=240;
				}
				return d;
			}
		};
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
								
				String n= characterClassNames.get(value.getEquipmentClass().getClassId());
				String s= (value.getEquipmentClass().getClassId()+ " - "+n );				
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

	
	private final Map<String,String> characterClassNames= new HashMap<>();
	
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
	
	
	private void save() {
		
		try {
		EquipmentClassService dao = (EquipmentClassService)DataMapper.INSTANCE.getDataService(EquipmentClass.class);
		
		ReturnValue<String> res =dao.updateViewForEquipmentAll(equipment.getType().name(),equipment.getCode(), items);
		if(res.isError()) {
			Popper.popError(this, "Problem : saving data", res);
			return;
		} else {
			LOGGER.info("All good. Database updated "+ res.getValue());
			//Popper.popError(this, "All good. Database updated", res.getValue());
		}
		dispose();
		} catch (Exception ex) {
			Popper.popError(this, ex);
			return;
		}
	}
	

	private void selectNone() {
		// go through visible and untick them...		
		setAssigned(false);
	}

	private void setAssigned(boolean ass) {
		ListModel<ViewClassEquipment> model = lstItems.getModel();
		
		for(int i=0,n=model.getSize();i<n;i++) {
			ViewClassEquipment vce = model.getElementAt(i);
			vce.setAssigned(ass);			
		}
		SwingUtilities.invokeLater(()->{
			lstItems.repaint();	
		});
		
	}
	
	private void selectAll() {
		setAssigned(true);
	}

}

