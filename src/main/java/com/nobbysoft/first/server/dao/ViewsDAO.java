package com.nobbysoft.first.server.dao;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nobbysoft.first.common.entities.equipment.EquipmentType;
import com.nobbysoft.first.server.utils.DbUtils;

public class ViewsDAO implements CreateInterface {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	@Override
	public void createTable(Connection con) throws SQLException {

		dropViewEquipmentClass(con);
		dropViewEquipment(con);
		createViewEquipment(con);
		createViewEquipmentClass(con);

	}


	private void dropViewEquipment(Connection con) throws SQLException {
		
		
		
		
		String viewName = "view_equipment";
		if (DbUtils.doesViewExist(con, viewName)) {
			String view = "DROP VIEW " + viewName;
			try (PreparedStatement ps = con.prepareStatement(view);) {
				ps.execute();
			} catch (Exception ex) {
				LOGGER.info("Error in sql\n" + view);
				throw ex;
			}
		}
	
	}
	private void createViewEquipment(Connection con) throws SQLException {
		
		
		
		
		String viewName = "view_equipment";
 
		Map<EquipmentType,String> typesToTableNames = new HashMap<>();
		typesToTableNames.put(EquipmentType.MELEE_WEAPON,        "weapon_melee");
		typesToTableNames.put(EquipmentType.WEAPON_RANGED,       "weapon_ranged");
		typesToTableNames.put(EquipmentType.AMMUNITION,          "weapon_ammunition");	
		typesToTableNames.put(EquipmentType.ROD_STAFF_WAND,      "rod_staff_wand");
		typesToTableNames.put(EquipmentType.MISCELLANEOUS_MAGIC, "miscellaneous_magic_item");
		typesToTableNames.put(EquipmentType.MISCELLANEOUS_ITEM,  "miscellaneous_item");
		typesToTableNames.put(EquipmentType.ARMOUR,              "armour");
		typesToTableNames.put(EquipmentType.SHIELD,              "shield");
		typesToTableNames.put(EquipmentType.SCROLL,              "equipment_scroll");
		

		String stdSelect = " SELECT '%s' as type, code, name, encumberance_GP FROM %s ";
		String ua = " UNION ALL ";
		
		{
			StringBuilder view = new StringBuilder("CREATE VIEW " + viewName + " AS ( "); 
		
			boolean first=true;
			for(EquipmentType et:typesToTableNames.keySet()) {
				String tableName =typesToTableNames.get(et) ;
				if(!first) {
					view.append(ua);
				}
				first=false;										
				String s= String.format(stdSelect, et.name(),tableName);
				view.append(s);
			}
			
			
			view.append(")");
		
 

			try (PreparedStatement ps = con.prepareStatement(view.toString());) {
				ps.execute();
			} catch (Exception ex) {
				LOGGER.info("Error in sql\n" + view);
				throw ex;
			}
		}
	}
	

	private void dropViewEquipmentClass(Connection con) throws SQLException {
		
		
		
		
		String viewName = "view_equipment_class";
		if (DbUtils.doesViewExist(con, viewName)) {
			String view = "DROP VIEW " + viewName;
			try (PreparedStatement ps = con.prepareStatement(view);) {
				ps.execute();
			} catch (Exception ex) {
				LOGGER.info("Error in sql\n" + view);
				throw ex;
			}
		}

	}
	private void createViewEquipmentClass(Connection con) throws SQLException {
		
		
		
		
		String viewName = "view_equipment_class";
 

		{
			String view = "CREATE VIEW " + viewName + " AS (" +
				"	select ec.type,ec.code,ve.name as equipment_name,ec.class_id,cc.name as class_name "+
				"	  from equipment_class ec "+
				"	  join character_class cc on cc.class_id = ec.class_id "+
				"	  join view_equipment ve on ve.code = ec.code and ve.type = ec.type "+
				 ")";

			try (PreparedStatement ps = con.prepareStatement(view);) {
				ps.execute();
			} catch (Exception ex) {
				LOGGER.info("Error in sql\n" + view);
				throw ex;
			}
		}
	}
	

}
