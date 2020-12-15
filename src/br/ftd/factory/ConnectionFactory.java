package br.ftd.factory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import br.ftd.entity.Params;
import br.ftd.model.DAOParams;




public class ConnectionFactory {
	
	private static Connection con;
	private static ConnectionFactory connectionFactory;
	private static String driver = "jdbc:mysql://localhost:3306/";
	private static String database = "bdftdrp?useUnicode=yes";
	private static String user = "chicoh";
	//private static String password = "4ybZwnwIONVR";
	private static String password = "chc1234";
	private static String sqlDbClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			//"net.sourceforge.jtds.jdbc.Driver";
							//"net.sourceforge.jtds.jdbc.Driver";
	
	public ConnectionFactory(){};
	

	public Connection getMySqlConnection(){
		
		try {

			Properties p = new Properties();
			p.setProperty("user", user);
			p.setProperty("password", password);
			p.setProperty("MaxPooledStatements", "500");
			
			//+"?useTimezone=true&amp;serverTimezone=UTC&amp;useUnicode=true&amp;characterEncoding=utf8"
			Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
			con = DriverManager.getConnection(driver+database, p);
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
	}

	public Connection getSqlConnection(){
		
		Params params = DAOParams.getSystemParams();
		
		String sqlServerAddress = params.getMssqladdress();
		//String domainServer = params.getMssqldomain();	
		String SqlDbName = params.getMssqldb();
		String sqlDbUser = params.getMssqluser();
		String sqlDbPassword = params.getMssqlpswd();
		String sqlDbPort = params.getMssqlport();
 
		
		String sqlJtdsUrl = "jdbc:sqlserver://"+sqlServerAddress+":"+sqlDbPort+";databaseName="+SqlDbName;
		
		if(isReachable(sqlServerAddress, Integer.parseInt(sqlDbPort), 200)){
		
			
			//usando o drive jtsd com autenticacao no MS SQL Server via usuario do Windows
			DBSql db = new DBSql();
			con = db.dbConnect(sqlJtdsUrl, sqlDbUser, sqlDbPassword);

			
		}else{
			
			con = null;
			
		}
		
		return con;
	}
	
	public static ConnectionFactory getInstance(){		
		connectionFactory = new ConnectionFactory();
		return connectionFactory;
	}

	
    private static boolean isReachable(String addr, int openPort, int timeOutMillis) {
        // Any Open port on other machine
        // openPort =  22 - ssh, 80 or 443 - webserver, 25 - mailserver etc.
        try {
            try (Socket soc = new Socket()) {
                soc.connect(new InetSocketAddress(addr, openPort), timeOutMillis);
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    
	class DBSql 
	{
		public DBSql(){}
		

		public Connection dbConnect(String db_connect_string, String db_user_id, String db_password){
			Connection conn = null;
			try {
				
				Class.forName(sqlDbClass).getDeclaredConstructor().newInstance();
				conn = DriverManager.getConnection(db_connect_string, db_user_id, db_password);				
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return conn;
		}
	}
}
