import java.io.*;
import java.sql.*;
import java.util.*;
import oracle.jdbc.driver.OracleInputStream;

public class Zp {

	private static String dburl;
	private static String username;
	private static String password;
	private static String tablename;
	private static String column_pk;
	private static String column_zp;
	private static String zpdelete;
	private static String display;
	private static String showsql;
    private static String zhsql;
    private static String sfzh;
    private static String del="1";

	public static void initParam() throws Exception{
		 Properties props = new Properties();
         InputStream in = new BufferedInputStream (new FileInputStream("zp.properties"));
         props.load(in);
         dburl=props.getProperty("dburl");
         username=props.getProperty("username");
         password=props.getProperty("password");
         tablename=props.getProperty("tablename");
         column_pk=props.getProperty("column_pk");
         column_zp=props.getProperty("column_zp");
         zpdelete=props.getProperty("zpdelete");
         display=props.getProperty("display");
         showsql=props.getProperty("showsql");
         zhsql=props.getProperty("zhsql");
         sfzh=props.getProperty("sfzh");     
         if(sfzh==null || sfzh.length()<1)
             sfzh="0";
         in.close();
	}

	public static void insertZp() throws Exception{
		String sql;
		String tmp;
		
		OutputStream outStream=null;
		String pk=null;
		
		byte[] data=new byte[1];
		FileInputStream in = null;  
	    FileOutputStream out = null;
	    FileOutputStream outlog = null;
	    
		File zpdir=new File("zp");
		File[] zp=zpdir.listFiles();
		if(zp==null){
			System.out.println("directory zp is not exists");
			return;
		}
		if(zp.length<1){
			System.out.println("directory zp is empty");
			return;
		}
		File zpdirnew=new File("zpok");
		if(!zpdirnew.exists())
			zpdirnew.mkdir();
		
		outlog=new FileOutputStream(new File("zp.log"));
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn=DriverManager.getConnection(dburl,username,password);
		conn.setAutoCommit(false);
		
		for(int i=0;i<zp.length;i++){
			//
			data=new byte[(int)zp[i].length()];
			in=new FileInputStream(zp[i].getAbsoluteFile());
			in.read(data);
			
			pk=zp[i].getName().substring(0,zp[i].getName().lastIndexOf("."));

		    if(sfzh.equals("1")){
		        sql=zhsql;
                printSql(outlog,"sql0",sql,i);
                PreparedStatement pstmt = conn.prepareStatement(sql);  
                pstmt.setString(1,pk);  
                ResultSet rs1 = pstmt.executeQuery();  
                if(rs1.next()){
                    pk=rs1.getString(1);
                }
                rs1.close();
                pstmt.close();
            }

		    Statement st = conn.createStatement();
            if(del.equals("1")){
		        sql="delete from "+tablename+" where "+column_pk+"='"+pk+"'";
                printSql(outlog,"sql1",sql,i);
		        st.executeUpdate(sql);
		    }
		    
		    sql="insert into "+tablename+"(wid,"+column_pk+","+column_zp+") select '"+pk+"','"+pk+"',empty_blob() from dual where not exists (select 1 from "+tablename+" where "+column_pk+"='"+pk+"')";
            printSql(outlog,"sql2",sql,i);
		    st.executeUpdate(sql);
		    
		    sql="update "+tablename+" set "+column_zp+"=empty_blob() where "+column_pk+"='"+pk+"'";
            printSql(outlog,"sql21",sql,i);
		    st.executeUpdate(sql);
		    
		    sql="select "+column_zp+" from "+tablename+" where "+column_pk+"='"+pk+"' for update";
            printSql(outlog,"sql3",sql,i);
		    ResultSet rs = st.executeQuery(sql);
		    
		    if (rs.next())
		    {
		        //得到java.sql.Blob对象后强制转换为oracle.sql.BLOB
		        oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob(column_zp);
		        outStream = blob.getBinaryOutputStream();
		        outStream.write(data, 0, data.length);
		    }
		    outStream.flush();
		    outStream.close();
			rs.close();
			st.close();
		    conn.commit();
		    if(display!=null && display.equals("true")){
		    	tmp="insert:\t"+i+"/"+zp.length+"\t"+pk+"\r\n";
		    	System.out.print(tmp);
		    	outlog.write(tmp.getBytes());
		    }
//		    copy file
		    out=new FileOutputStream(zpdirnew.getAbsoluteFile()+System.getProperty("file.separator")+zp[i].getName());
			out.write(data);
			in.close();
			out.close();
			if(zpdelete!=null && zpdelete.equals("true"))
				zp[i].delete();
		}
	    conn.close();
	    outlog.close();
	}
    public static void printSql(FileOutputStream outlog,String head,String sql,int i) throws Exception{
        if(showsql!=null && showsql.equals("true")){
		        	String tmp=head+":"+i+"\t"+sql+"\r\n";
		        	System.out.print(tmp);
		        	outlog.write(tmp.getBytes());
		        }
 
    }
	public static void main(String[] args) throws Exception{
		initParam();
        if(args.length>0){
            HashMap<String,String> map=new HashMap<String,String>();
            for(String s:args)
                map.put(s,s);
            if(map.containsKey("sfzh")){
                sfzh="1";
            }
            if(map.containsKey("nodel")){
                del="0";
            }
        }
		insertZp();
	}
	
}
