package com.nobbysoft.first.client.components;

import java.awt.Dimension;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;

import javax.swing.JTextArea;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PTextArea extends JTextArea implements PDataComponent {

	public PTextArea() {
		super();
		setLineWrap(true);
	}

	public PTextArea(int length) {
		super();
		setLineWrap(true);
		LengthLimitingDocument doc = new LengthLimitingDocument(length, false, false);
		setDocument(doc);
	}

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public String getCurrentText() {
		final int cp = getCaretPosition();
		String txt = getText();

		if (cp < 0 || cp > txt.length() || txt.trim().isEmpty()) {
			return "";
		}
		int start = cp;
		// go back from where the cursor is to find a non-blank space.
		LOGGER.info("CP:"+cp);
		if(start < txt.length() && txt.charAt(start)<=' ') {
			start = backwardsToNonBlank(txt, start);
		}		
		LOGGER.info("start:"+start);
		// if we ended up at the start, then read forwards instead
		if (start == 0) {
			start = forwardsToNonBlank(txt, start);
			LOGGER.info("start:"+start);
		}

		String lineSep = "\n";// System.getProperty("line.separator");

		start = findStartOfText(txt, start, lineSep);
		
		int end = findEndOfText(txt, start, lineSep);
		txt = txt+" ";
		LOGGER.info("start:" + start + " >" + txt.charAt(start)+"<");
		LOGGER.info("end:" + end + " >" + txt.charAt(end-1)+"<");
		LOGGER.info("text:" + txt.substring(start, end));

		return txt.substring(start, end);
	}

	private int findEndOfText(final String txt, final int start, final String lineSep) 
		{
			int end = txt.length();// start off with assuming we're going onto the end...
			
			// 012345679
			// FRED
			// length=4
			// char at 4 is not there
			
			boolean stop = false;

			int cur = start;			

			int blankLines = 0;
			while (!stop) {
				cur = txt.indexOf(lineSep, cur + 1);
				if (cur < 0) { // didn't find a line separator at all :(
					stop = true;
					if (blankLines == 0) {
						end = txt.length();
					}
				} else {
					int ncur = txt.indexOf(lineSep, cur + 1);
					if (ncur < 0) { // didn't find a second line separator at all so End Of Text is good
						if(blankLines==0) {
							end = txt.length();
						}
						stop = true;
					} else {
						String text = txt.substring(cur, ncur).trim();
						if (text.isEmpty()) {
							blankLines++;
							if (blankLines == 1) {
								end = cur;
							} else if (blankLines > 2) {
								stop = true;
							}
						} else {
							blankLines = 0;
						}
					}
				}
			}
		// go back one  for substring
		return end;
	}

	private int findStartOfText(final String txt, int start, final String lineSep) {
		{

			int cur = start;
			int blankLines = 0;
			boolean stop = false;
			while (!stop) {
				LOGGER.info("CUR:"+cur);
				cur = txt.lastIndexOf(lineSep, cur - 1);
				if (cur <= 0) {
					if (blankLines == 0) {
						start = 0;
					}
					stop = true;
				} else {
					// we've found a CR!
					int pcur = txt.lastIndexOf(lineSep, cur - 1);
					LOGGER.info("PCUR:"+pcur);
					if (pcur <= 0) {
						// we've ended up at the beginning!
						if(blankLines==0) {
							start = 0;
						}
						stop = true;
					} else  {
						String text = txt.substring(pcur, cur).trim();
						if (text.isEmpty()) {
							LOGGER.info("text empty "+blankLines);
							// empty!
							blankLines++;
							if (blankLines == 1) {
								start = cur;
							} else if (blankLines > 2) {
								stop = true;
							}

						} else {
							blankLines = 0;
						}
					}

				}
			}
			LOGGER.info("Start from " + start);
		}
		return start;
	}

	private int backwardsToNonBlank(String txt, int start) {
		// read BACK until you hit a non-blankspace character
		boolean stop = false;
		// read back from CP until you reach a non-blankspace character
		
		while (!stop) {
			if (start <= 0) {
				stop = true;
			} else {
				start--;
				char c = txt.charAt(start);
				if (c > ' ') {
					stop = true;
				}
			}
		}
		return start;
	}

	private int forwardsToNonBlank(String txt, int start) {
		char c;
		int totalLen = txt.length();
		boolean stop = false;
		while (!stop) {
			start++;
			if (start > totalLen) {
				stop = true;
			} else {
				c = txt.charAt(start);
				if (c > ' ') {
					stop = true;
				}
			}
		}
		return start;
	}

	@Override
	public void setTheValue(Object o) {
		if (o != null) {
			if (o instanceof BigDecimal) {
				setText(((BigDecimal) o).toPlainString());
			} else {
				setText(o.toString());
			}
		}

	}

	@Override
	public Object getTheValue() {
		return getText();
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		setEditable(!readOnly);
	}

	@Override
	public boolean isReadOnly() {
		return !isEditable();
	}

	int ph = 0;
	int pw = 0;

	@Override
	public void setMinimumHeight(int height) {
		ph = height;
	}

	@Override
	public void setMinimumWidth(int width) {
		pw = width;
	}

	@Override
	public Dimension getPreferredSize() {

		Dimension d = super.getPreferredSize();
		if (d.getWidth() < pw) {
			d.width = pw;
		}
		if (d.getHeight() < ph) {
			d.height = ph;
		}
		return d;
	}

}
