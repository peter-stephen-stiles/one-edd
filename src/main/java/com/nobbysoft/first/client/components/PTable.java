package com.nobbysoft.first.client.components;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.invoke.MethodHandles;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.nobbysoft.first.client.utils.GuiUtils;
import com.nobbysoft.first.client.utils.XmlUtilities;

/**
 * Will get much more capability :)
 * 
 * @author nobby
 *
 */
public class PTable extends JTable {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	public PTable() {
		setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		addPopup();
	}

	public PTable(TableModel dm) {
		super(dm);
		setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		addPopup();

	}

	public void selectRow(int row) {
		int rc = getRowCount();
		if (row < rc && rc > 0) {
			getSelectionModel().addSelectionInterval(row, row);
		}
	}

	public boolean isCellEditable(int rowIndex, int colIndex) {
		return false;
	}// Disallow the editing of any cell

	public void clearData() {
		TableModel mData = getModel();
		if (mData instanceof DefaultTableModel) {
			DefaultTableModel tmData = (DefaultTableModel) mData;
			while (tmData.getRowCount() > 0) {
				tmData.removeRow(0);
			}
		}
	}

	private void addPopup() {
		JPopupMenu popup = new JPopupMenu();
//		{
//			JMenuItem mnuCopy = new JMenuItem("Copy");
//			popup.add(mnuCopy);
//			this.add(popup);
//
//			mnuCopy.addActionListener(ae -> {
//				copy(false);
//			});
//		}

		{
			JMenuItem mnuCopy = new JMenuItem("Copy (html)");
			popup.add(mnuCopy);
			this.add(popup);

			mnuCopy.addActionListener(ae -> {
				copy(true);
			});
		}
		this.add(popup);
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

		    public void mousePressed(MouseEvent e) {
		        maybeShowPopup(e);
		    }

		    public void mouseReleased(MouseEvent e) {
		        maybeShowPopup(e);
		    }

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			private void maybeShowPopup(MouseEvent e) {
		        if (e.isPopupTrigger()) {
		            popup.show(e.getComponent(),
		                       e.getX(), e.getY());
		        }
		    }
			
		});
	}

	private void copy(boolean html) {
		TableModel mData = getModel();
		int cols=mData.getColumnCount();
		if (html) {
			Document doc = XmlUtilities.convertStringToXML("<table></table>");
			Element table=(Element)doc.getDocumentElement();
			{
				Element tr = XmlUtilities.addElement(table, "tr");
				
				for(int i=0,n=cols;i<n;i++) {
					XmlUtilities.addElement(tr,"td",mData.getColumnName(i));
				}
			}
			for(int i=0,n=mData.getRowCount();i<n;i++) {
				Element tr = XmlUtilities.addElement(table, "tr");
				for(int j=0,m=cols;j<m;j++) {
					Object o = mData.getValueAt(i, j);
					if(o==null) {

						XmlUtilities.addElement(tr, "td");
					} else {
						XmlUtilities.addElement(tr, "td",o.toString());
					}
				}
			}
			String xmlString="";
			try {
				xmlString = XmlUtilities.xmlToHtmlString(doc);
			} catch (Exception e) {
				LOGGER.error("Unable to show "+doc,e);
			}
			StringSelection stringSelection = new StringSelection(xmlString);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
		}
	}
}
