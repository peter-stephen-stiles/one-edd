package com.nobbysoft.first;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.nobbysoft.first.client.components.special.DicePanel;

import junit.framework.TestCase;

public class TestDicePanelLogic extends TestCase {

 
private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());


	
	@Test
	public void testit() {
		
		DicePanel dp = new DicePanel();
		String d;
		
		dp.setTheValue("1d6+1");
		d = dp.getDicePanelData().toString();
		LOGGER.info(d);
		assertEquals("d6+1",d);
		
		dp.setTheValue("d6");
		d = dp.getDicePanelData().toString();
		LOGGER.info(d);
		assertEquals("d6",d);
		
		dp.setTheValue("d8");
		d = dp.getDicePanelData().toString();
		LOGGER.info(d);
		assertEquals("d8",d);


		dp.setTheValue("2d%");
		d = dp.getDicePanelData().toString();
		LOGGER.info(d);
		assertEquals("2d%",d);

		
		dp.setTheValue("d%");
		d = dp.getDicePanelData().toString();
		LOGGER.info(d);
		assertEquals("d%",d);

		dp.setTheValue("d8+1");
		d = dp.getDicePanelData().toString();
		LOGGER.info(d);
		assertEquals("d8+1",d);
		
		dp.setTheValue("3d3-1");
		d = dp.getDicePanelData().toString();
		LOGGER.info(d);
		assertEquals("3d3-1",d);

		dp.setTheValue("2d6+1");
		d = dp.getDicePanelData().toString();
		LOGGER.info(d);
		assertEquals("2d6+1",d);

		dp.setTheValue("d8+1");
		d = dp.getDicePanelData().toString();
		LOGGER.info(d);
		assertEquals("d8+1",d);


		
	}
	
}
