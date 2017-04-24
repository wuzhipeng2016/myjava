package com.mystudy;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Dbutil {
	private static final Log LOG = LogFactory.getLog(Dbutil.class);

	private static String CONNECTION_DRIVER_STR = "oracle.jdbc.driver.OracleDriver";
	private static String CONNECTION_STR = "jdbc:oracle:thin:@localhost:1521:orcl";
	private static String username = "scott";
	private static String password = "tiger";

	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(CONNECTION_DRIVER_STR);
			conn = DriverManager.getConnection(CONNECTION_STR, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static <T> List<T> getResult(Class<T> clazz, String sql, Object[] params) throws Exception {
		LOG.info(sql + "\r\n" + Arrays.toString(params) + "\r\n" + clazz.getName());
		QueryRunner qr = new QueryRunner();
		return (List) qr.query(getConnection(), sql, new BeanListHandler(clazz), params);
	}

	public static <T> List<T> getResult(Connection conn, Class<T> clazz, String sql, Object[] params) throws Exception {
		LOG.info(sql + "\r\n" + Arrays.toString(params) + "\r\n" + clazz.getName());
		QueryRunner qr = new QueryRunner();
		return (List) qr.query(conn, sql, new BeanListHandler(clazz), params);
	}

	public static List<Map<String, Object>> getResult(String sql, Object[] params) throws Exception {
		LOG.info(sql + "\r\n" + Arrays.toString(params));
		QueryRunner qr = new QueryRunner();
		return (List) qr.query(getConnection(), sql, new MapListHandler(), params);
	}

	public static List<Map<String, Object>> getResult(Connection conn, String sql, Object[] params) throws Exception {
		LOG.info(sql + "\r\n" + Arrays.toString(params));
		QueryRunner qr = new QueryRunner();
		return (List) qr.query(conn, sql, new MapListHandler(), params);
	}

	public static void update(String sql, Object[] params) throws Exception {
		LOG.info(sql + "\r\n" + Arrays.toString(params));
		QueryRunner qr = new QueryRunner();
		qr.update(getConnection(), sql, params);
	}

	public static void update(Connection conn, String sql, Object[] params) throws Exception {
		LOG.info(sql + "\r\n" + Arrays.toString(params));
		QueryRunner qr = new QueryRunner();
		qr.update(conn, sql, params);
	}

	public static Map<String, Object> queryOne(String sql, Object[] params) throws Exception {
		LOG.info(sql + "\r\n" + Arrays.toString(params));
		QueryRunner qr = new QueryRunner();
		return (Map) qr.query(getConnection(), sql, new MapHandler(), params);
	}

	public static Map<String, Object> queryOne(Connection conn, String sql, Object[] params) throws Exception {
		LOG.info(sql + "\r\n" + Arrays.toString(params));
		QueryRunner qr = new QueryRunner();
		return (Map) qr.query(conn, sql, new MapHandler(), params);
	}

	private static String getProcedureName(String sql, Object[] inParams, Object[] outParams) {
		StringBuilder plens = new StringBuilder();

		String proc = sql.toLowerCase().startsWith("call ") ? sql.substring(4).trim() : sql;
		int pall = 0;
		if ((inParams != null) && (inParams.length > 0)) {
			pall = inParams.length;
		}
		if ((outParams != null) && (outParams.length > 0)) {
			pall += outParams.length;
		}
		for (int i = 0; i < pall; i++) {
			plens.append(",?");
		}
		if ((pall > 0) && (sql.indexOf("(") < 1)) {
			proc = proc + "(" + plens.substring(1) + ")";
		}
		return proc;
	}

	public static int callProcedure(Connection conn, String sql, Object[] inParams, Object[] outParams) {
		CallableStatement stmt = null;
		int pcnt = -1;
		try {
			String proc = getProcedureName(sql, inParams, outParams);

			stmt = conn.prepareCall("{call " + proc + "}");
			pcnt = 1;
			if ((inParams != null) && (inParams.length > 0)) {
				for (int i = 0; i < inParams.length; i++) {
					stmt.setString(pcnt, (String) inParams[i]);
					pcnt++;
				}
			}
			if ((outParams != null) && (outParams.length > 0)) {
				for (int i = 0; i < outParams.length; i++) {
					stmt.registerOutParameter(pcnt + i, 12);
				}
			}
			stmt.execute();

			if ((outParams != null) && (outParams.length > 0))
				for (int i = 0; i < outParams.length; i++)
					outParams[i] = stmt.getString(pcnt + i);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return pcnt;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				LOG.info(e.getMessage(), e);
			}
		}
		return 1;
	}

	public static int callProcedure(String sql, Object[] inParams, Object[] outParams) {
		Connection conn = null;
		try {
			conn = getConnection();
			int i = callProcedure(conn, sql, inParams, outParams);
			return i;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return -1;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				LOG.info(e.getMessage(), e);
			}
		}
	}
	
	public static void main(String[] args) throws Exception{
		Connection conn = getConnection();
		String sql = "select * from DEPT";
		List<Map<String, Object>> list = getResult(conn,sql,new Object[]{});
		System.out.println(list);
		conn.close();
	}
}
