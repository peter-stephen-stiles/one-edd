package com.nobbysoft.first.client.utils;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GBU {
	private GBU() {

	}

	private static final Insets fiveByFive = new Insets(5, 5, 0, 0);
	private static final Insets none = new Insets(0, 0, 0, 0);

	public static final GridBagConstraints labelC(int x, int y) {
		return new GridBagConstraints(x, y, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				fiveByFive, 0, 0);
	}
	
	public static final GridBagConstraints label(int x, int y) {
		return new GridBagConstraints(x, y, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				fiveByFive, 0, 0);
	}

	public static final GridBagConstraints label(int x, int y, int width) {
		return new GridBagConstraints(x, y, width, 1, 0.0, 0.0, 
				GridBagConstraints.EAST, GridBagConstraints.NONE, 
				fiveByFive, 0, 0);
	}

	public static final GridBagConstraints labelC(int x, int y, int width) {
		return new GridBagConstraints(x, y, width, 1, 0.0, 0.0, 
				GridBagConstraints.CENTER, GridBagConstraints.NONE, 
				fiveByFive, 0, 0);
	}
	
	public static final GridBagConstraints text(int x, int y) {
		return new GridBagConstraints(x, y, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				fiveByFive, 0, 0);

	} 

	public static final GridBagConstraints text(int x, int y, int width) {
		return new GridBagConstraints(x, y, width, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				fiveByFive, 0, 0);

	}
	public static final GridBagConstraints text(int x, int y, int width, int height) {
		return new GridBagConstraints(x, y, width, height, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				fiveByFive, 0, 0);

	}

	public static final GridBagConstraints button(int x, int y) {
		return new GridBagConstraints(x, y, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				fiveByFive, 0, 0);

	}

	public static final GridBagConstraints panel(int x, int y) {
		return new GridBagConstraints(x, y, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
				fiveByFive, 0, 0);
	}

	public static final GridBagConstraints panel(int x, int y, int width, int height) {
		return new GridBagConstraints(x, y, width, height, 1.0, 1.0, 
				GridBagConstraints.WEST, GridBagConstraints.BOTH,
				fiveByFive, 0, 0);
	}

	public static final GridBagConstraints hPanel(int x, int y, int width, int height) {
		return new GridBagConstraints(x, y, width, height, 1.0, 0.0, 
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				fiveByFive, 0, 0);
	}
	public static final GridBagConstraints hPanelNoPad(int x, int y, int width, int height) {
		return new GridBagConstraints(x, y, width, height, 1.0, 0.0, 
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				none, 0, 0);
	}
	public static final GridBagConstraints vPanel(int x, int y, int width, int height) {
		return new GridBagConstraints(x, y, width, height, 0.0, 1.0, 
				GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
				fiveByFive, 0, 0);
	}

}
