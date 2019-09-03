package com.nobbysoft.first.server.db;

import org.apache.derby.drda.NetworkServerControl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.net.InetAddress;

public class StartNetworkServer {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	public StartNetworkServer() throws Exception {
try {
		NetworkServerControl server = new NetworkServerControl
			(InetAddress.getByName("localhost"),1527);
		server.start(null);
} catch (Exception ex) {
	LOGGER.error("Unable to start DERBY network server",ex);
}
	}

}
