package br.ftd.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.codec.binary.Base64;

import br.ftd.factory.ConnectionFactory;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Statement;

public class DAOKeypass {
	
	public static Date getDataLimite(){
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		String sql = "select * from keypass";
		
		String chave = "";
		
		String data = "";
		
		try {
			Statement st = con.createStatement();
			
			ResultSet rs = st.executeQuery(sql);
			
			if(rs.next()){
				chave = rs.getString("keypass");
			}else{

				rs.close();
				st.close();
				con.close();
				return null;
			}
			
			data = getDecode(chave);
			System.out.println("Data limite >>>>> "+data);
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		
		return convertStringToDate(data);
	}
	
	
	public static void setKeyPass(String key){
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		boolean flag = false;
		
		try {
			String sql = "select * from keypass";								
			
			Statement st = con.createStatement();
			
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()){
				flag = true;				
			}
			rs.close();
						
			if(!flag){
				sql = "insert into keypass values('"+key+"')";
				st.execute(sql);
			}else{
				sql = "update keypass set keypass = '"+key+"'";
				st.execute(sql);				
			}
			
			st.close();
			con.close();
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}
	
	
	
	public static Date convertStringToDate(String dt) {
		//dt = dt.replaceAll("/", "");
		//dt = dt.substring(4, 8)+"-"+dt.substring(2, 4) + "-" + dt.substring(0, 2);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;		
		
		try {
			date = new Date(df.parse(dt).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return date;
	}

	public static String getDecode(String chave) throws NoSuchAlgorithmException{
				
		return new String(Base64.decodeBase64(chave.getBytes()));
	}

}
