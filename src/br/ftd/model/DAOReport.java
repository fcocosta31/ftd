package br.ftd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.sql.Date;

import br.ftd.control.ControlServlet;
import br.ftd.entity.NotasReport;
import br.ftd.entity.TotvsDb;
import br.ftd.factory.ConnectionFactory;

public class DAOReport {
	
	public List<NotasReport> getOrcamsTotvs(String filial, String clienteini, String clientefim, Date inicio, Date fim, String aberto) throws ParseException {
		
		Connection con = ConnectionFactory.getInstance().getSqlConnection();
		
		List<NotasReport> lista = new ArrayList<>();
		
		String nota = "C5_NOTA = ''";
		
		if(aberto.equalsIgnoreCase("nao")) {
			nota = "C5_NOTA <> ''";
		}
		
		String sql = "SELECT C5_FILIAL, C5_VEND1, C5_NUM, C5_NOTA, C5_SERIE, C6_TES, C5_CLIENTE, A1_NOME, A1_MUN,"
				+ " C5_EMISSAO, VLRTOTAL FROM (SELECT C5_FILIAL, C5_VEND1, C5_NUM, C5_NOTA, C5_SERIE, C6_TES,"
				+ " C5_CLIENTE, C5_EMISSAO, VLRTOTAL FROM "+TotvsDb.SC5.getTable(ControlServlet.getParams().getGpoemptotvs())+" INNER JOIN (SELECT C6_FILIAL, C6_NUM, C6_TES,"
				+ " SUM(C6_VALOR) AS VLRTOTAL FROM "+TotvsDb.SC6.getTable(ControlServlet.getParams().getGpoemptotvs())+" WHERE D_E_L_E_T_ <> '*' GROUP BY C6_FILIAL, C6_NUM, C6_TES) AS T ON C5_FILIAL"
				+ " = C6_FILIAL AND C5_NUM = C6_NUM AND C5_EMISSAO BETWEEN ? AND ?"
				+ " AND C5_FILIAL = ? AND (C5_CLIENTE >= ? AND C5_CLIENTE <= ?) AND "+nota+" AND "+TotvsDb.SC5.getTable(ControlServlet.getParams().getGpoemptotvs())+".D_E_L_E_T_ <> '*') AS Y INNER"
				+ " JOIN "+TotvsDb.SA1.getTable(ControlServlet.getParams().getGpoemptotvs())+" ON C5_CLIENTE = A1_COD ORDER BY C5_NUM";
		
		try {
			if(con != null) {
				PreparedStatement pstm = con.prepareStatement(sql);
				pstm.setDate(1, inicio);
				pstm.setDate(2, fim);
				pstm.setString(3, filial);
				pstm.setString(4, clienteini);
				pstm.setString(5, clientefim);
				
				ResultSet rs = pstm.executeQuery();
				
				while(rs.next()) {
					NotasReport n = new NotasReport();
					n.setCodigo(rs.getString("C5_CLIENTE"));
					n.setMunicipio(rs.getString("A1_MUN"));
					n.setEmissao(convertDateSQL(rs.getString("C5_EMISSAO")));
					n.setFilial(rs.getString("C5_FILIAL"));
					n.setNome(rs.getString("A1_NOME"));
					n.setNota(rs.getString("C5_NOTA"));
					n.setTes(rs.getString("C6_TES"));
					n.setNumero(rs.getString("C5_NUM"));
					n.setSerie(rs.getString("C5_SERIE"));
					n.setTotal(rs.getDouble("VLRTOTAL"));
					n.setVendedor(rs.getString("C5_VEND1"));
					
					lista.add(n);
				}
				rs.close();
				pstm.close();
				con.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}
	
	public Date convertDateSQL(String datasql) throws ParseException {
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date tradeDate = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(datasql);
		String dt = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(tradeDate);
		Date date = null;		
		date = new Date(df.parse(dt).getTime());
		
		return date;
	}
}
