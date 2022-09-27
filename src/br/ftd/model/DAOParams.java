package br.ftd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.ftd.entity.Params;
import br.ftd.factory.ConnectionFactory;

public class DAOParams {

	public static String setSystemParams(Params params) {
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select * from systemparams";
		String result = "";
		try {
			
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			if(rs.next()) {
				sql = "update systemparams set pagetitle = ?, pageemail = ?, pagefone = ?, pageuf = ?, pagemsg = ?,"
						+ " gpoemptotvs = ?, mssqladdress = ?, mssqldomain = ?, mssqldb = ?, mssqlport=?, mssqluser = ?, mssqlpswd = ?,"
						+ " loginmail = ?, pswdmail = ?, ccmails = ?, loginrecmail = ?, pswrecmail = ?, hostrecmail = ?, protocolrecmail = ?,"
						+ " portrecmail = ? where id = 1";
				PreparedStatement pstm = con.prepareStatement(sql);
				pstm.setString(1, params.getPagetitle());
				pstm.setString(2, params.getPageemail());
				pstm.setString(3, params.getPagefone());
				pstm.setString(4, params.getPageuf());
				pstm.setString(5, params.getPagemsg());
				pstm.setString(6, params.getGpoemptotvs());
				pstm.setString(7, params.getMssqladdress());
				pstm.setString(8, params.getMssqldomain());
				pstm.setString(9, params.getMssqldb());
				pstm.setString(10, params.getMssqlport());
				pstm.setString(11, params.getMssqluser());
				pstm.setString(12, params.getMssqlpswd());
				pstm.setString(13, params.getLoginmail());
				pstm.setString(14, params.getPswdmail());
				pstm.setString(15, params.getCcmails());
				pstm.setString(16, params.getLoginrecmail());
				pstm.setString(17, params.getPswrecmail());
				pstm.setString(18, params.getHostrecmail());
				pstm.setString(19, params.getProtocolrecmail());
				pstm.setString(20, params.getPortrecmail());
				
				
				pstm.execute();
				pstm.close();
				result = "System Params Sucessfully Updated!";
			}else {
				sql = "insert into systemparams (pagetitle, pageemail, pagefone, pageuf, pagemsg,"
						+ " gpoemptotvs, mssqladdress, mssqldomain, mssqldb, mssqlport, mssqluser, mssqlpswd,"
						+ " loginmail, pswdmail, ccmails, loginrecmail, pswrecmail, hostrecmail, protocolrecmail, portrecmail)"
						+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement pstm = con.prepareStatement(sql);
				pstm.setString(1, params.getPagetitle());
				pstm.setString(2, params.getPageemail());
				pstm.setString(3, params.getPagefone());
				pstm.setString(4, params.getPageuf());
				pstm.setString(5, params.getPagemsg());
				pstm.setString(6, params.getGpoemptotvs());
				pstm.setString(7, params.getMssqladdress());
				pstm.setString(8, params.getMssqldomain());
				pstm.setString(9, params.getMssqldb());
				pstm.setString(10, params.getMssqlport());
				pstm.setString(11, params.getMssqluser());
				pstm.setString(12, params.getMssqlpswd());
				pstm.setString(13, params.getLoginmail());
				pstm.setString(14, params.getPswdmail());
				pstm.setString(15, params.getCcmails());
				pstm.setString(16, params.getLoginrecmail());
				pstm.setString(17, params.getPswrecmail());
				pstm.setString(18, params.getHostrecmail());
				pstm.setString(19, params.getProtocolrecmail());
				pstm.setString(20, params.getPortrecmail());
				
				pstm.execute();
				pstm.close();
				result = "System Params Sucessfully Saved!";
				
			}
			stm.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = e.getMessage();
		}
		return result;
	}
	
	public static Params getSystemParams() {
		Params p = new Params();
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select * from systemparams";
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				p.setPagetitle(rs.getString("pagetitle"));
				p.setPageemail(rs.getString("pageemail"));
				p.setPagefone(rs.getString("pagefone"));
				p.setPageuf(rs.getString("pageuf"));
				p.setPagemsg(rs.getString("pagemsg"));
				p.setGpoemptotvs(rs.getString("gpoemptotvs"));
				p.setMssqladdress(rs.getString("mssqladdress"));
				p.setMssqldb(rs.getString("mssqldb"));
				p.setMssqlport(rs.getString("mssqlport"));
				p.setMssqldomain(rs.getString("mssqldomain"));
				p.setMssqlpswd(rs.getString("mssqlpswd"));
				p.setMssqluser(rs.getString("mssqluser"));
				p.setLoginmail(rs.getString("loginmail"));
				p.setPswdmail(rs.getString("pswdmail"));
				p.setCcmails(rs.getString("ccmails"));
				p.setLoginrecmail(rs.getString("loginrecmail"));
				p.setPswrecmail(rs.getString("pswrecmail"));
				p.setHostrecmail(rs.getString("hostrecmail"));
				p.setProtocolrecmail(rs.getString("protocolrecmail"));
				p.setPortrecmail(rs.getString("portrecmail"));
				
			}
			rs.close();
			stm.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}
}
