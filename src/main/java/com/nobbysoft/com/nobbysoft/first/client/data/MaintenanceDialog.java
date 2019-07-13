package com.nobbysoft.com.nobbysoft.first.client.data;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;

import com.nobbysoft.com.nobbysoft.first.client.components.PButtonPanel;
import com.nobbysoft.com.nobbysoft.first.client.components.PDialog;
import com.nobbysoft.com.nobbysoft.first.client.utils.Popper;
import com.nobbysoft.com.nobbysoft.first.common.utils.ReturnValue;

@SuppressWarnings("serial")
public class MaintenanceDialog extends PDialog {

	public MaintenanceDialog(Window owner, String title, MaintenancePanelInterface<?> panel) {
		super(owner, title, ModalityType.DOCUMENT_MODAL);

		JButton btnOk = new JButton("OK");
		JButton btnCancel = new JButton("Cancel");
		BorderLayout layThis = new BorderLayout(5, 5);
		getContentPane().setLayout(layThis);
		getContentPane().add((JComponent) panel, BorderLayout.CENTER);
		PButtonPanel pnlButtons = new PButtonPanel();
		pnlButtons.add(btnOk);
		pnlButtons.add(btnCancel);
		btnOk.addActionListener(ae -> {
			ReturnValue<?> ret = panel.ok();
			if (ret.isError()) {
				Popper.popError(this, "Error", ret.getErrorMessage());
			} else {
				dispose();
			}
		});
		btnCancel.addActionListener(ae -> {
			ReturnValue<?> ret = panel.cancel();
			if (ret.isError()) {				
				Popper.popError(this, "Error", ret.getErrorMessage());
			} else {
				dispose();
			}
		});
		getContentPane().add(pnlButtons, BorderLayout.SOUTH);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				btnCancel.doClick();
			}
		});

	}

}
