package com.nobbysoft.com.nobbysoft.first;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.com.nobbysoft.first.server.dao.CreateInterface;
import com.nobbysoft.com.nobbysoft.first.server.dao.DAOI;
import com.nobbysoft.com.nobbysoft.first.server.dao.SpellDAO;
import com.nobbysoft.com.nobbysoft.first.server.db.StartNetworkServer;
import com.nobbysoft.com.nobbysoft.first.server.utils.ConnectionManager;
import com.nobbysoft.com.nobbysoft.first.utils.DataMapper;

public class DBMain {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public static void main(String[] args) throws Exception {
		


		CommandLineParser parser = new DefaultParser();
		Options options = MainUtils.baseOptions();
		CommandLine cmd = parser.parse(options, args);
		if(cmd.hasOption("h")){
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp( MethodHandles.lookup().lookupClass().getSimpleName(), options );
			System.exit(0);
		}

		String databaseName = System.getProperty(Constants.PARMNAME_ONEEDDDB,"");//"";//"C:\\Users\\nobby\\Documents\\nobbysoft\\derby\\PCDatabase";
		
		if(cmd.hasOption("d")){
			databaseName = cmd.getOptionValue("d");
		}

		System.setProperty(Constants.PARMNAME_ONEEDDDB, databaseName);
		
		new StartNetworkServer();
		new DBMain();
	}

	public String driver = "org.apache.derby.jdbc.EmbeddedDriver";

	private void runAction(Connection con, String sql) throws SQLException{
		
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.execute();
		}
		
	}
	
	
	
	private DBMain() throws Exception {
		
		ConnectionManager cm = new ConnectionManager();
		
		

		try { 
			try (Connection conn = cm.getConnection();) {
				//runAction(conn,"drop table player_character");
				//runAction(conn,"DROP SEQUENCE  player_character_seq RESTRICT ");
				
				//runAction(conn,"alter table Character_Class add column max_hd_level INTEGER");
				//runAction(conn,"alter table Character_Class add column master_spell_class BOOLEAN");
				//max_hd_level INTEGER, master_spell_class BOOLEAN 
				
				
				runQuery(conn, "SELECT * FROM SYS.SYSTABLES");
				
				//runQuery(conn, "SELECT * FROM Race");
				
				for(Class<CreateInterface> daoClass:DataMapper.INSTANCE.getDaos()){
				
					CreateInterface dao = daoClass.newInstance(); 
					dao.createTable(conn);
				}
				
			}
		} finally {
cm.shutdown();
		}
	}

	public static final void runQuery(Connection conn, String query) throws SQLException {
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			try (ResultSet rs = ps.executeQuery()) {
				ResultSetMetaData m = rs.getMetaData();
				{
					StringBuilder sb = new StringBuilder();

					for (int i = 0, n = m.getColumnCount(); i < n; i++) {
						String ss = m.getColumnLabel(i + 1);

						sb.append(ss).append(",");
					}
					LOGGER.info(sb.toString());

				}

				while (rs.next()) {
					StringBuilder sb = new StringBuilder();
					for (int i = 0, n = m.getColumnCount(); i < n; i++) {
						String ss = rs.getString(i + 1);
						sb.append(ss).append(",");
					}
					LOGGER.info(sb.toString());
				}
			}
		}
	}
}
