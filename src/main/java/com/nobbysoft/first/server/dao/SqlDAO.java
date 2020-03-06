package com.nobbysoft.first.server.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.nobbysoft.first.client.utils.XmlUtilities;
import com.nobbysoft.first.common.entities.meta.DTOColumn;
import com.nobbysoft.first.common.entities.meta.DTOConstraint;
import com.nobbysoft.first.common.entities.meta.DTOIndex;
import com.nobbysoft.first.common.entities.meta.DTOTable;
import com.nobbysoft.first.common.utils.ResultSetListener;
import com.nobbysoft.first.common.utils.ReturnValue;
import com.sun.xml.internal.ws.wsdl.writer.document.Types;

public class SqlDAO {

	public SqlDAO() {
	}

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public void runSqlUpdate(Connection con, String sql) throws SQLException {

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.executeUpdate();
		}
	}

	public void runSql(Connection con, String sql, ResultSetListener listener) throws SQLException {

		try (PreparedStatement ps = con.prepareStatement(sql)) {

			try (ResultSet rs = ps.executeQuery()) {

				ResultSetMetaData rsmd = rs.getMetaData();
				listener.haveTheMetaData(rsmd);

				List<String> labels = new ArrayList<>();
				for (int i = 0, n = rsmd.getColumnCount(); i < n; i++) {
					labels.add(rsmd.getColumnLabel(i + 1));
				}
				listener.haveTheColumnLabels(labels.toArray(new String[labels.size()]));

				int row = 0;
				while (rs.next()) {

					List<Object> data = new ArrayList<>();
					for (int i = 0, n = rsmd.getColumnCount(); i < n; i++) {
						Object o = rs.getObject(i + 1);
						data.add(o);
					}
					listener.haveARow(row, data.toArray());
					row++;
				}

			}

		} finally {
			listener.finished();
		}

	}

	private String[] tableTypes(DatabaseMetaData dbmd) throws SQLException {
		List<String> ts = new ArrayList<>();
		try (ResultSet rs = dbmd.getTableTypes()) {
			while (rs.next()) {
				ts.add(rs.getString(1));
			}
		}
		return ts.toArray(new String[ts.size()]);
	}

	public List<DTOTable> metaDataTables(Connection con, String catalog, String schema, String tableFilter)
			throws SQLException {
		List<DTOTable> list = new ArrayList<>();
		DatabaseMetaData d = con.getMetaData();
		try {

			try (ResultSet rs = d.getTables(catalog, schema, tableFilter, tableTypes(d))) {

				/*
				 * TABLE_CAT String => table catalog (may be null) 2.TABLE_SCHEM String => table
				 * schema (may be null) 3.TABLE_NAME String => table name 4.TABLE_TYPE String =>
				 * table type. Typical types are "TABLE","VIEW", "SYSTEM TABLE",
				 * "GLOBAL TEMPORARY","LOCAL TEMPORARY", "ALIAS", "SYNONYM". 5.REMARKS String =>
				 * explanatory comment on the table 6.TYPE_CAT String => the types catalog (may
				 * be null) 7.TYPE_SCHEM String => the types schema (may be null) 8.TYPE_NAME
				 * String => type name (may be null) 9.SELF_REFERENCING_COL_NAME String => name
				 * of the designated"identifier" column of a typed table (may be null)
				 * 10.REF_GENERATION String => specifies how values inSELF_REFERENCING_COL_NAME
				 * are created. Values are"SYSTEM", "USER", "DERIVED". (may be null)
				 * 
				 */

				int row = 0;
				while (rs.next()) {
					DTOTable dto = new DTOTable();
					dto.setTableCat(rs.getString("TABLE_CAT"));
					dto.setTableSchem(rs.getString("TABLE_SCHEM"));
					dto.setTableName(rs.getString("TABLE_NAME"));
					dto.setTableType(rs.getString("TABLE_TYPE"));
					dto.setRemarks(rs.getString("REMARKS"));
					dto.setTypeCat(rs.getString("TYPE_CAT"));
					dto.setTypeSchem(rs.getString("TYPE_SCHEM"));
					dto.setTypeName(rs.getString("TYPE_NAME"));
					dto.setSelfReferencingColName(rs.getString("SELF_REFERENCING_COL_NAME"));
					dto.setRefGeneration(rs.getString("REF_GENERATION"));

					list.add(dto);
					row++;
				}

			}

		} finally {

		}
		return list;
	}

	public List<DTOTable> metaDataTables(Connection con, String tableFilter) throws SQLException {
		List<DTOTable> list = new ArrayList<>();
		DatabaseMetaData d = con.getMetaData();
		try {

			try (ResultSet rs = d.getTables(null, null, tableFilter, tableTypes(d))) {

				/*
				 * TABLE_CAT String => table catalog (may be null) 2.TABLE_SCHEM String => table
				 * schema (may be null) 3.TABLE_NAME String => table name 4.TABLE_TYPE String =>
				 * table type. Typical types are "TABLE","VIEW", "SYSTEM TABLE",
				 * "GLOBAL TEMPORARY","LOCAL TEMPORARY", "ALIAS", "SYNONYM". 5.REMARKS String =>
				 * explanatory comment on the table 6.TYPE_CAT String => the types catalog (may
				 * be null) 7.TYPE_SCHEM String => the types schema (may be null) 8.TYPE_NAME
				 * String => type name (may be null) 9.SELF_REFERENCING_COL_NAME String => name
				 * of the designated"identifier" column of a typed table (may be null)
				 * 10.REF_GENERATION String => specifies how values inSELF_REFERENCING_COL_NAME
				 * are created. Values are"SYSTEM", "USER", "DERIVED". (may be null)
				 * 
				 */

				int row = 0;
				while (rs.next()) {
					DTOTable dto = new DTOTable();
					dto.setTableCat(rs.getString("TABLE_CAT"));
					dto.setTableSchem(rs.getString("TABLE_SCHEM"));
					dto.setTableName(rs.getString("TABLE_NAME"));
					dto.setTableType(rs.getString("TABLE_TYPE"));
					dto.setRemarks(rs.getString("REMARKS"));
					dto.setTypeCat(rs.getString("TYPE_CAT"));
					dto.setTypeSchem(rs.getString("TYPE_SCHEM"));
					dto.setTypeName(rs.getString("TYPE_NAME"));
					dto.setSelfReferencingColName(rs.getString("SELF_REFERENCING_COL_NAME"));
					dto.setRefGeneration(rs.getString("REF_GENERATION"));

					list.add(dto);
					row++;
				}

			}

		} finally {

		}
		return list;
	}

	public List<DTOColumn> metaDataColumns(Connection con, String catalog, String schema, String tableName)
			throws SQLException {

		List<DTOColumn> list = new ArrayList<>();
		DatabaseMetaData d = con.getMetaData();
		try {

			try (ResultSet rs = d.getColumns(catalog, schema, tableName, "%")) {

				int row = 0;
				while (rs.next()) {
					DTOColumn dto = new DTOColumn();
					dto.setTableCat(rs.getString("TABLE_CAT"));
					dto.setTableSchem(rs.getString("TABLE_SCHEM"));
					dto.setTableName(rs.getString("TABLE_NAME"));
					dto.setColumnName(rs.getString("COLUMN_NAME"));
					dto.setDataType(rs.getInt("DATA_TYPE"));
					dto.setTypeName(rs.getString("TYPE_NAME"));
					dto.setColumnSize(rs.getInt("COLUMN_SIZE"));
					dto.setDecimalDigits(rs.getInt("DECIMAL_DIGITS"));
					dto.setNumPrecRadix(rs.getInt("NUM_PREC_RADIX"));
					dto.setNullable(rs.getInt("NULLABLE"));
					dto.setRemarks(rs.getString("REMARKS"));
					dto.setColumnDef(rs.getString("COLUMN_DEF"));
					dto.setCharOctetLength(rs.getInt("CHAR_OCTET_LENGTH"));
					dto.setOrdinalPosition(rs.getInt("ORDINAL_POSITION"));
					dto.setIsNullable(rs.getString("IS_NULLABLE"));
					dto.setScopeCatalog(rs.getString("SCOPE_CATALOG"));
					dto.setScopeSchema(rs.getString("SCOPE_SCHEMA"));
					dto.setScopeTable(rs.getString("SCOPE_TABLE"));
					dto.setSourceDataType(rs.getShort("SOURCE_DATA_TYPE"));
					dto.setIsAutoincrement(rs.getString("IS_AUTOINCREMENT"));
					dto.setIsGeneratedColumn(rs.getString("IS_GENERATEDCOLUMN"));

					list.add(dto);
					row++;
				}

			}

		} finally {

		}
		return list;
	}

	public List<DTOIndex> metaDataIndexes(Connection con, String catalog, String schema, String tableName)
			throws SQLException {

		List<DTOIndex> list = new ArrayList<>();
		DatabaseMetaData d = con.getMetaData();
		try {

			try (ResultSet rs = d.getIndexInfo(catalog, schema, tableName, false, true)) {

				while (rs.next()) {
					DTOIndex dto = new DTOIndex();
					dto.setTableCat(rs.getString("TABLE_CAT"));
					dto.setTableSchem(rs.getString("TABLE_SCHEM"));
					dto.setTableName(rs.getString("TABLE_NAME"));
					dto.setNonUnique(rs.getBoolean("NON_UNIQUE"));
					dto.setIndexQualifier(rs.getString("INDEX_QUALIFIER"));
					dto.setIndexName(rs.getString("INDEX_NAME"));
					dto.setType(rs.getShort("TYPE"));
					dto.setOrdinalPosition(rs.getShort("ORDINAL_POSITION"));
					dto.setColumnName(rs.getString("COLUMN_NAME"));
					dto.setAscOrDesc(rs.getString("ASC_OR_DESC"));
					dto.setCardinality(rs.getLong("CARDINALITY"));
					dto.setPages(rs.getLong("PAGES"));
					dto.setFilterCondition(rs.getString("FILTER_CONDITION"));

					list.add(dto);
				}

			}

		} finally {

		}
		return list;
	}

	public List<DTOConstraint> metaDataConstraints(Connection con, String catalog, String schema, String tableName)
			throws SQLException {

		List<DTOConstraint> list = new ArrayList<>();
		DatabaseMetaData d = con.getMetaData();
		try {

			try (ResultSet rs = d.getExportedKeys(catalog, schema, tableName)) {

				while (rs.next()) {
					DTOConstraint dto = new DTOConstraint();
					dto.setPktableCat(rs.getString("PKTABLE_CAT"));
					dto.setPktableSchem(rs.getString("PKTABLE_SCHEM"));
					dto.setPktableName(rs.getString("PKTABLE_NAME"));
					dto.setPkcolumnName(rs.getString("PKCOLUMN_NAME"));
					dto.setFktableCat(rs.getString("FKTABLE_CAT"));
					dto.setFktableSchem(rs.getString("FKTABLE_SCHEM"));
					dto.setFktableName(rs.getString("FKTABLE_NAME"));
					dto.setFkcolumnName(rs.getString("FKCOLUMN_NAME"));
					dto.setKeySeq(rs.getShort("KEY_SEQ"));
					dto.setUpdateRule(rs.getShort("UPDATE_RULE"));
					dto.setDeleteRule(rs.getShort("DELETE_RULE"));
					dto.setFkName(rs.getString("FK_NAME"));
					dto.setPkName(rs.getString("PK_NAME"));
					dto.setDeferrability(rs.getShort("DEFERRABILITY"));

					list.add(dto);
				}

			}

			try (ResultSet rs = d.getImportedKeys(catalog, schema, tableName)) {

				while (rs.next()) {
					DTOConstraint dto = new DTOConstraint();
					dto.setPktableCat(rs.getString("PKTABLE_CAT"));
					dto.setPktableSchem(rs.getString("PKTABLE_SCHEM"));
					dto.setPktableName(rs.getString("PKTABLE_NAME"));
					dto.setPkcolumnName(rs.getString("PKCOLUMN_NAME"));
					dto.setFktableCat(rs.getString("FKTABLE_CAT"));
					dto.setFktableSchem(rs.getString("FKTABLE_SCHEM"));
					dto.setFktableName(rs.getString("FKTABLE_NAME"));
					dto.setFkcolumnName(rs.getString("FKCOLUMN_NAME"));
					dto.setKeySeq(rs.getShort("KEY_SEQ"));
					dto.setUpdateRule(rs.getShort("UPDATE_RULE"));
					dto.setDeleteRule(rs.getShort("DELETE_RULE"));
					dto.setFkName(rs.getString("FK_NAME"));
					dto.setPkName(rs.getString("PK_NAME"));
					dto.setDeferrability(rs.getShort("DEFERRABILITY"));

					list.add(dto);
				}

			}

		} finally {

		}
		return list;
	}

	public List<String> metaCatalogs(Connection con) throws SQLException {

		DatabaseMetaData d = con.getMetaData();

		List<String> list = new ArrayList<>();

		try (ResultSet rs = d.getCatalogs()) {
			while (rs.next()) {
				list.add(rs.getString(1));
			}

		}

		return list;
	}

	public List<String> metaSchema(Connection con) throws SQLException {
		DatabaseMetaData d = con.getMetaData();

		List<String> list = new ArrayList<>();

		try (ResultSet rs = d.getSchemas()) {
			while (rs.next()) {
				list.add(rs.getString(1));
			}

		}

		return list;
	}

	enum ROUGH_TYPE {
		NUMBER, STRING, BLOB, DATE, DATETIME
	}

	private ROUGH_TYPE type(int sqlType) {
		switch (sqlType) {
		case java.sql.Types.BIGINT:
		case java.sql.Types.BIT:
		case java.sql.Types.DECIMAL:
		case java.sql.Types.DOUBLE:
		case java.sql.Types.FLOAT:
		case java.sql.Types.INTEGER:
		case java.sql.Types.NUMERIC:
		case java.sql.Types.REAL:
		case java.sql.Types.SMALLINT:
		case java.sql.Types.TINYINT:
			return ROUGH_TYPE.NUMBER;

		case java.sql.Types.DATE:
			return ROUGH_TYPE.DATE;

		case java.sql.Types.TIMESTAMP:
		case java.sql.Types.TIMESTAMP_WITH_TIMEZONE:
			return ROUGH_TYPE.DATETIME;

		case java.sql.Types.BINARY:
		case java.sql.Types.BLOB:
		case java.sql.Types.LONGVARBINARY:
			return ROUGH_TYPE.BLOB;
		default:
			return ROUGH_TYPE.STRING;

		}
	}

	public ReturnValue<String> export(Connection con, String catalog, String schema, String fileName)
			throws SQLException {

		File outputFileName = new File(fileName);
		if (outputFileName.exists()) {
			return new ReturnValue<>(ReturnValue.IS_ERROR.TRUE,
					"Export to " + outputFileName.getAbsolutePath() + " failed - file already exists!");
		}
		if (outputFileName.isDirectory()) {
			return new ReturnValue<>(ReturnValue.IS_ERROR.TRUE,
					"Export to " + outputFileName.getAbsolutePath() + " failed - file is a directory!");
		}
		if (!outputFileName.getParentFile().canWrite()) {
			return new ReturnValue<>(ReturnValue.IS_ERROR.TRUE,
					"Export to " + outputFileName.getAbsolutePath() + " failed - can't write to directory!");
		}
		String cName = "";
		String tNam = "";

		try (FileOutputStream fos = new FileOutputStream(outputFileName);) {
			try (ZipOutputStream zipOut = new ZipOutputStream(fos);) {

//		tmpFolder = Files.createTempDirectory("dbexport");
//		LOGGER.info("temp folder "+tmpFolder.toString());

				DatabaseMetaData d = con.getMetaData();

				// TODO
				// process each SEQUENCE in the database in turn.

				// process each TABLE in the database in turn
				String[] types = new String[] { "TABLE" };
				// not all tableTypes(d)
				try (ResultSet rs = d.getTables(catalog, schema, "%", types)) {
					while (rs.next()) {
						DTOTable dto = new DTOTable();
						String tCat = rs.getString("TABLE_CAT");
						String tSch = rs.getString("TABLE_SCHEM");
						tNam = rs.getString("TABLE_NAME");
						String tTyp = rs.getString("TABLE_TYPE");
						dto.setTableCat(tCat);
						dto.setTableSchem(tSch);
						dto.setTableName(tNam);
						dto.setTableType(tTyp);
						dto.setRemarks(rs.getString("REMARKS"));
						dto.setTypeCat(rs.getString("TYPE_CAT"));
						dto.setTypeSchem(rs.getString("TYPE_SCHEM"));
						dto.setTypeName(rs.getString("TYPE_NAME"));
						dto.setSelfReferencingColName(rs.getString("SELF_REFERENCING_COL_NAME"));
						dto.setRefGeneration(rs.getString("REF_GENERATION"));
						LOGGER.info("Doing table {}.{}.{}", tCat, tSch, tNam);
						//
						String fileForTable = dto.getTableName() + ".table.xml";

						DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
						DocumentBuilder docBuilder;
						docBuilder = docFactory.newDocumentBuilder();
						Document doc = docBuilder.newDocument();

						//
						Element table = doc.createElement("table");
						doc.appendChild(table);

						XmlUtilities.addAttribute(table, "cat", tCat);
						XmlUtilities.addAttribute(table, "schema", tSch);
						XmlUtilities.addAttribute(table, "name", tNam);

						String tn = "";
						if (tCat != null && tCat.trim().isEmpty()) {
							// no catalkog
						} else {
							tn = tCat + ".";
						}
						if (tSch != null && tSch.trim().isEmpty()) {
							// noschema
						} else {
							tn = tn + tSch + ".";
						}
						tn = tn + tNam;
						SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
						SimpleDateFormat ymdhms = new SimpleDateFormat("yyyyMMddHHmmss");
						//
						String sql = "SELECT * FROM " + tn;
						try (PreparedStatement ps1 = con.prepareStatement(sql);) {
							try (ResultSet rs1 = ps1.executeQuery()) {
								ResultSetMetaData rsmd1 = rs1.getMetaData();
								while (rs1.next()) {
									Element row = XmlUtilities.addElement(table, "row");
									for (int i = 0, n = rsmd1.getColumnCount(); i < n; i++) {
										int col = i + 1;
										cName = rsmd1.getColumnName(col);
										String cClassName = rsmd1.getColumnClassName(col);
										int cType = rsmd1.getColumnType(col);

										ROUGH_TYPE type = type(cType);
										Element el = null;
										switch (type) {
										case NUMBER:
											BigDecimal bd;
											bd = rs1.getBigDecimal(col);
											if (bd != null) {
												el = XmlUtilities.addElement(row, cName, bd.toPlainString());
											} else {
												el = XmlUtilities.addElement(row, cName);
												el.setAttribute("null", "true");
											}
											break;
										case STRING:
											String s = rs1.getString(col);
											if (s != null) {
												el = XmlUtilities.addCDataElement(row, cName, s);
											} else {
												el = XmlUtilities.addElement(row, cName);
												el.setAttribute("null", "true");
											}
											break;
										case BLOB:
											// crud. will need to base-64 it. Laters.
											break;
										case DATE:
											Date dt = rs1.getDate(col);
											if (dt != null) {
												el = XmlUtilities.addElement(row, cName, ymd.format(dt));
											} else {
												el = XmlUtilities.addElement(row, cName);
												el.setAttribute("null", "true");
											}
											break;
										case DATETIME:
											Date ts = rs1.getTimestamp(col);
											if (ts != null) {
												el = XmlUtilities.addElement(row, cName, ymd.format(ts));
											} else {
												el = XmlUtilities.addElement(row, cName);
												el.setAttribute("null", "true");
											}
											break;
										}

										XmlUtilities.addAttribute(el, "ROUGH_TYPE", type.name());
										LOGGER.info("Added node " + el);
									}

								}

							}

						}
						// finsihed, save to zippy the thingy

						File fileToZip = new File(fileForTable);

						ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
						zipOut.putNextEntry(zipEntry);
						XmlUtilities.xmlToPlainStream(doc, zipOut);

					}

				}
			}
			return new ReturnValue<>("Export to " + fileName + " ok.");

		} catch (Exception ioe) {
			LOGGER.error("Error trying to do stuff with database :( " + tNam + "." + cName, ioe);
			return new ReturnValue<String>(ReturnValue.IS_ERROR.TRUE, ioe.getMessage() + " - " + tNam + "." + cName);
		}
	}
}
