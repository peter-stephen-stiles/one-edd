package com.nobbysoft.com.nobbysoft.first.client.components;

import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

public class PDialog extends JDialog {

	protected JRootPane createRootPane() {
		 ActionListener actionListener = new ActionListener() {
			  public void actionPerformed(ActionEvent actionEvent) {
				 setVisible(false);
			     dispose();
			  }
			};
		  KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		  JRootPane rootPane = new JRootPane();
		  rootPane.registerKeyboardAction(actionListener, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
		  return rootPane;
		}
	
	public PDialog() {
		super();
	}
 

	public PDialog(Window owner) {
		super(owner); 
	}

 

	public PDialog(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
	}

	public PDialog(Window owner, String title) {
		super(owner, title);
	}
 

	public PDialog(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
	}
 

	public PDialog(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
	}

}
