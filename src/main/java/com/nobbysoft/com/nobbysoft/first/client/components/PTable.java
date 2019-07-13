package com.nobbysoft.com.nobbysoft.first.client.components;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.nobbysoft.com.nobbysoft.first.client.utils.GuiUtils;

/**
 * Will get much more capability :)
 * 
 * @author nobby
 *
 */
public class PTable extends JTable {

	public PTable() {
		
	}

	public PTable(TableModel dm) {
		super(dm);

	}

	public void selectRow(int row){
		int rc = getRowCount();
		if(row<rc && rc>0){
			getSelectionModel().addSelectionInterval(row, row);
		}
	}
	
	public boolean isCellEditable(int rowIndex, int colIndex) { 
		return false; 
		}//Disallow the editing of any cell 


}
