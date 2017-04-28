import java.io.*;
import java.sql.*;
import java.util.*;
import oracle.jdbc.driver.OracleInputStream;

public class TestDB {

	private static String dburl;
	private static String username;
	private static String password;
	
	public static void initParam() throws Exception{
		dburl="jdbc:oracle:thin:@localhost:1521:orcl";
		username="usr_jw_version";
		password="wisedu";
	}

	public static void insertZp() throws Exception{
		String sql;
		String tmp;
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn=DriverManager.getConnection(dburl,username,password);
		conn.setAutoCommit(false);
		
		System.out.println(conn);
		    conn.commit();
				conn.close();
	}
	public static void main(String[] args) throws Exception{
		initParam();
		insertZp();
	}
	
}
