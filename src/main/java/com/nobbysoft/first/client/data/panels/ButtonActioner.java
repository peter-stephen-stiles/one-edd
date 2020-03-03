package com.nobbysoft.first.client.data.panels;

import java.awt.Window;
import java.awt.event.ActionListener;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PButton;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PButtonPanel;
import com.nobbysoft.first.client.utils.Popper;

public class ButtonActioner {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public ButtonActioner(Window window, PButtonPanel pnlDataButtons, ActionListener rowButtonListener,
			ActionListener tableButtonListener) {
		this.window = window;
		this.pnlDataButtons = pnlDataButtons;
		this.rowButtonListener = rowButtonListener;
		this.tableButtonListener = tableButtonListener;
	}

	public boolean haveDBI() {
		return dbi != null || dsbi!=null;
	}

	// need to initialise
	private Window window;
	private PButtonPanel pnlDataButtons;
	private ActionListener rowButtonListener; 
	private ActionListener tableButtonListener;

	public void enableRowButtons(boolean enable) {
		SwingUtilities.invokeLater(() -> {
			for (PButton button : rowButtons) {
				button.setEnabled(enable);
			}
		});
	}

	private List<PButton> rowButtons = new ArrayList<>();
	private List<PButton> tableButtons = new ArrayList<>();
	//
	private DataButtonsInterface dbi;
	private DatasButtonsInterface dsbi;

	public void buttons(Class buttonClass) {

		SwingUtilities.invokeLater(() -> {

			try {

				pnlDataButtons.removeAll();
				for (PButton button : rowButtons) {
					button.removeActionListeners();
				}
				for (PButton button : tableButtons) {
					button.removeActionListeners();
				}
				// removed old, do we need new?
				if (buttonClass == null) {
					return;
				}

				try {
					Constructor cn = buttonClass.getConstructor();

					Object o = cn.newInstance();
					
					if (o instanceof DataButtonsInterface) {

						singleRowerOnly(o);

					} else if(o instanceof DatasButtonsInterface ) {

						multiRower(o);

					} else {
						LOGGER.error("boohh "+buttonClass.getSimpleName()+" not assignable from DBI or DSBI :(");
					}

				} catch (Exception ex) {
					LOGGER.error("Error doing buttons", ex);
					Popper.popError(window, ex);
				}
			} finally {
				pnlDataButtons.revalidate();
			}
		});
	}

	private void multiRower(Object o)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		dsbi = (DatasButtonsInterface) o;
		rowButtons.clear();
		tableButtons.clear();

		for (Object name : dsbi.getRowButtonNames()) {
			PButton button = new PButton(name.toString());
			button.setActionCommand(button.getName());
			button.addActionListener(rowButtonListener);
			button.setEnabled(false);
			rowButtons.add(button);
			pnlDataButtons.add(button);
		}
		if (rowButtons.size() > 0 && tableButtons.size() > 0) {
			pnlDataButtons.add(new PLabel("  "));
		}
		for (Object name : dsbi.getTableButtonNames()) {
			PButton button = new PButton(name.toString());
			button.setActionCommand(button.getName());
			button.addActionListener(tableButtonListener);
			tableButtons.add(button);
			pnlDataButtons.add(button);
		}
	}
	
	private void singleRowerOnly(Object o)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		dbi = (DataButtonsInterface) o;
		rowButtons.clear();
		tableButtons.clear();

		for (Object name : dbi.getRowButtonNames()) {
			PButton button = new PButton(name.toString());
			button.setActionCommand(button.getName());
			button.addActionListener(rowButtonListener);
			button.setEnabled(false);
			rowButtons.add(button);
			pnlDataButtons.add(button);
		}
		if (rowButtons.size() > 0 && tableButtons.size() > 0) {
			pnlDataButtons.add(new PLabel("  "));
		}
		for (Object name : dbi.getTableButtonNames()) {
			PButton button = new PButton(name.toString());
			button.setActionCommand(button.getName());
			button.addActionListener(tableButtonListener);
			tableButtons.add(button);
			pnlDataButtons.add(button);
		}
	}

	public boolean doRowButton(Window window, String name, Object object) {
		return dbi.doRowButton(window, name, object);
	}


	public boolean doRowsButton(Window window, String name, List<?> objects) {
		return dsbi.doRowsButton(window, name, objects);
	}
	
	public boolean doTableButton(Window window, String name) {
		if(dbi!=null) {
		return dbi.doTableButton(window, name);
		}
		if(dsbi!=null) {
		return dsbi.doTableButton(window, name);
		}
	return false;
	}

}
