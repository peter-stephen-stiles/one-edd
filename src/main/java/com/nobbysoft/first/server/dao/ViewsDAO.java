package com.nobbysoft.first.server.dao;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
 

		{
			String view = "CREATE VIEW " + viewName + " AS (" + 
		" SELECT '" + EquipmentType.MELEE_WEAPON.name()
					+ "' as type, code , name,encumberance_GP FROM weapon_melee " + " UNION ALL " + 
		" SELECT '"
					+ EquipmentType.WEAPON_RANGED.name() + "' as type, code , name,encumberance_GP FROM weapon_ranged "
					+ " UNION ALL " + 
					" SELECT '" + EquipmentType.AMMUNITION.name()
					+ "'  as type, code , name,encumberance_GP FROM weapon_ammunition " + " UNION ALL " + 
					" SELECT '"
					+ EquipmentType.ARMOUR.name() + "'  as type, code , name,encumberance_GP FROM armour "
					+ " UNION ALL " + 
					" SELECT '" + EquipmentType.SHIELD.name()
					+ "'  as type, code , name,encumberance_GP FROM shield " + ")";

			try (PreparedStatement ps = con.prepareStatement(view);) {
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
