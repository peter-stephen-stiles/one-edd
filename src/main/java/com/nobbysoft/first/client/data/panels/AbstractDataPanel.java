package com.nobbysoft.first.client.data.panels;

import java.awt.Font;
import java.lang.invoke.MethodHandles;

import javax.swing.JComponent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.client.components.PDataComponent;
import com.nobbysoft.first.client.components.PLabel;
import com.nobbysoft.first.client.components.PPanel;
import com.nobbysoft.first.client.data.MaintenancePanelInterface.MODE;
import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.first.common.servicei.DataServiceI;
import com.nobbysoft.first.common.utils.ReturnValue;

public abstract class AbstractDataPanel<T extends DataDTOInterface<? extends K>, K extends Comparable> extends PPanel {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	private MODE mode = null;
	private PLabel lblInstructions = new PLabel();
	
	T dto = null;
 

	public AbstractDataPanel() {
		
	}

	public void blankKeys(){
		for (PDataComponent c : getKeyComponents()) {
			c.setTheValue("");
		}
	}

	public ReturnValue<?> cancel() {
		return new ReturnValue("");
	}

	protected void enableButtons(boolean enable) {
		for (PDataComponent c : getButtonComponents()) {
			c.setReadOnly(!enable);
		}
	}

	protected void enableData(boolean enable) {
		for (PDataComponent c : getDataComponents()) {
			c.setReadOnly(!enable);
		}
	}

	protected void enableKeys(boolean enable) {
		for (PDataComponent c : getKeyComponents()) {
			c.setReadOnly(!enable);
		}
	}

	abstract protected PDataComponent[] getButtonComponents();

 

	abstract protected DataServiceI<?,?> getDataService();
	
	abstract protected PDataComponent[] getDataComponents();

	abstract protected PDataComponent[] getKeyComponents();

	
	protected PLabel getLblInstructions() {			
		Font pm24 = GuiUtils.getHeaderFont();
		lblInstructions.setFont(pm24);
		return lblInstructions;
	}

	abstract protected PDataComponent[] getMandatoryComponents();

	public MODE getMode(){
		return mode;
	}

	public ReturnValue<?> initAdd(String instructions) {
		if (mode != null) {
			return new ReturnValue(true, "Cannot initialise panel twice!");
		}
		populateCombos();
		mode = MODE.ADD;
		getLblInstructions().setText(instructions);
		enableData(true);
		enableKeys(true);
		enableButtons(false);
		return new ReturnValue("");
	}
	public ReturnValue<?> initCopy(T value,String instructions) {
		if (mode != null) {
			return new ReturnValue(true, "Cannot initialise panel twice!");
		}
		populateCombos();
		mode = MODE.COPY;
		getLblInstructions().setText(instructions);
		enableData(true);
		enableKeys(true);
		enableButtons(false);
		populateScreen(value);
		blankKeys();
		return new ReturnValue("");
	}
	
 public ReturnValue<?> initDelete(T value,String instructions) {
	if (mode != null) {
		return new ReturnValue(true, "Cannot initialise panel twice!");
	}
	populateCombos();
	dto = value;
	mode = MODE.DELETE;
	getLblInstructions().setText(instructions);
	enableData(false);
	enableKeys(false);
	enableButtons(false);
	populateScreen(value);
	return new ReturnValue("");
}
	public ReturnValue<?> initUpdate(T value,String instructions) {
		if (mode != null) {
			return new ReturnValue(true, "Cannot initialise panel twice!");
		}
		try {
			populateCombos();
			dto = value;
			mode = MODE.UPDATE;
			getLblInstructions().setText(instructions);
			enableData(true);
			enableKeys(false);
			enableButtons(true);
			populateScreen(value);
		} catch (Exception ex) {
			LOGGER.error("Error initialising screen",ex);
			return new ReturnValue(ReturnValue.IS_ERROR.TRUE,""+ex);
		}
		return new ReturnValue("");
	}

	abstract protected void jbInit();
	
 
	abstract protected void populateCombos() ;
 
	abstract protected void populateFromScreen(T value, boolean includingKeys);
 
	abstract protected void populateScreen(T value);
 
 
	
	protected ReturnValue<?> validateMandatory() {
		{
			for (PDataComponent c : getMandatoryComponents()) {
				Object s = c.getTheValue();
				if (s == null || (s.toString().trim().isEmpty())) {
					((JComponent)c).requestFocusInWindow();
					return new ReturnValue(true, "Must enter a " + (((JComponent) c).getName()));
				}
			}

		}

		return new ReturnValue("");
	}
 abstract protected ReturnValue<?> validateScreen();
 
 abstract protected T newT();
 
	public ReturnValue<?> ok() {
		/// try to do wotsit
		if (getMode().equals(MODE.ADD) || getMode().equals(MODE.COPY)) {
			ReturnValue<?> ret = validateMandatory();
			if(ret.isError()){
				return ret;
			}
			ret = validateScreen();
			if (ret.isError()) {
				return ret;
			}
			T dataDto = newT();
			populateFromScreen(dataDto, true);
			try  {

				DataServiceI  dao=  getDataService();
				dao.insert( dataDto);
			} catch (Exception ex) {
				LOGGER.error("Error inserting "+dto,ex);
				return new ReturnValue<>(true, ex.toString());
			}
		} else if (mode.equals(MODE.DELETE)) {
			try {

				DataServiceI  dao=  getDataService();
				dao.delete(dto);

			} catch (Exception ex) {
				LOGGER.error("Error deleting "+dto,ex);
				return new ReturnValue<>(true, ex.toString());
			}
		} else if (mode.equals(MODE.UPDATE)) {
			ReturnValue<?> ret = validateMandatory();
			if(ret.isError()){
				return ret;
			}
			ret = validateScreen();
			if(ret.isError()) {
				return ret;
			}
			populateFromScreen(dto, false);
			try  {

				DataServiceI  dao=  getDataService();
				dao.update( dto);

			} catch (Exception ex) {
				LOGGER.error("Error updating "+dto,ex);
				return new ReturnValue<>(true, ex.toString());
			}
		}
		return new ReturnValue<>("");
	}
 
}
