package br.ftd.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.exception.ParsePropertyException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import br.ftd.control.ControlServlet;
import br.ftd.entity.Cliente;
import br.ftd.entity.ResultSetToExcel;
import br.ftd.entity.TotvsDb;
import br.ftd.entity.Venda;
import br.ftd.factory.ConnectionFactory;

public class DAOVenda {
	
	public List<Venda> getVendasSintetico(String filial, Date inicio, Date fim, String TESvd, String TESdv, String codigoftd){

		Connection con = ConnectionFactory.getInstance().getSqlConnection();		
		
		String cliente = " ON A1_COD = F2_CLIENTE AND F2_CLIENTE = "+codigoftd;
		
		if(codigoftd.equalsIgnoreCase("todos")){
			cliente = " ON A1_COD = F2_CLIENTE";
		}
		
		List<Venda> lista = new ArrayList<>();
		
		String sqlVdSintetico = "SELECT F2_VEND1, F2_CLIENTE, A1_NOME, A1_CGC, A1_MUN, BRUTO, DESCONTO, LIQUIDO"
				+ " FROM "+TotvsDb.SA1.getTable(ControlServlet.getParams().getGpoemptotvs())+" INNER JOIN"
				+ " (SELECT F2_VEND1, F2_CLIENTE, F2_FILIAL, "
				+ " (SUM(F2_VALMERC)+SUM(F2_DESCONT)) AS BRUTO, SUM(F2_DESCONT) AS DESCONTO,"
				+ " SUM(F2_VALBRUT) AS LIQUIDO"
				+ " FROM "+TotvsDb.SF2.getTable(ControlServlet.getParams().getGpoemptotvs())+" INNER JOIN" 
				+ " (SELECT DISTINCT D2_DOC, D2_CLIENTE, D2_TES, D2_FILIAL"
				+ " FROM "+TotvsDb.SD2.getTable(ControlServlet.getParams().getGpoemptotvs())+" WHERE D2_EMISSAO BETWEEN ? AND ?"
				+ " AND (D2_TES = ? OR D2_TES = '501') AND D_E_L_E_T_ <> '*') AS D"
				+ " ON F2_CLIENTE = D2_CLIENTE"
				+ " AND F2_DOC = D2_DOC"
				+ " AND F2_FILIAL = D2_FILIAL"
				+ " AND F2_FILIAL = ?"
				//+ " AND (F2_CLIENTE <> '003439' AND F2_CLIENTE <> '003636' AND F2_CLIENTE <> '003639')"
				+ " AND D_E_L_E_T_ <> '*'"
				//+ " AND (F2_SERIE = '005' OR F2_SERIE = 'A1')"
				+ " AND F2_EMISSAO BETWEEN ? AND ?"
				+ " GROUP BY F2_VEND1, F2_CLIENTE, F2_FILIAL) AS T"
				+ cliente
				+ " AND D_E_L_E_T_ <> '*'"
				+ " ORDER BY A1_NOME";
		
		String sqlDvSintetico = "SELECT DISTINCT F1_FORNECE, (SUM(F1_VALMERC) - SUM(F1_DESCONT)) AS DEVOLUCAO"
				+ " FROM "+TotvsDb.SF1.getTable(ControlServlet.getParams().getGpoemptotvs())+" INNER JOIN"
				+ " (SELECT DISTINCT D1_FORNECE, D1_DOC, D1_TES FROM "+TotvsDb.SD1.getTable(ControlServlet.getParams().getGpoemptotvs())
				+ " WHERE D1_EMISSAO BETWEEN ? AND ? AND D1_TES = ? AND D1_FILIAL = ?) AS D"
				+ " ON F1_FORNECE = D1_FORNECE"
				+ " AND F1_DOC = D1_DOC"				
				+ " AND F1_FORNECE = ?"
				+ " AND D_E_L_E_T_ <> '*'"
				+ " AND F1_EMISSAO BETWEEN ? AND ?"
				+ " GROUP BY F1_FORNECE";
		
		
		String sqlDvSint2 = "SELECT DISTINCT F1_FORNECE, A1_COD, (SUM(F1_VALMERC) - SUM(F1_DESCONT)) AS DEVOLUCAO"
				+ " FROM "+TotvsDb.SF1.getTable(ControlServlet.getParams().getGpoemptotvs())+" S INNER JOIN"
				+ " (SELECT A1_COD, A2_COD FROM "+TotvsDb.SA1.getTable(ControlServlet.getParams().getGpoemptotvs())+" INNER JOIN "+TotvsDb.SA2.getTable(ControlServlet.getParams().getGpoemptotvs())
				+ " ON A1_COD <> A2_COD AND A1_CGC = A2_CGC AND A1_COD = ?) AS R"
				+ " ON F1_FORNECE = A2_COD"
				+ " AND S.D_E_L_E_T_ <> '*'"
				+ " AND F1_EMISSAO BETWEEN ? AND ?"
				+ " AND F1_FILIAL = ?"
				+ " GROUP BY F1_FORNECE, A1_COD";
		
		PreparedStatement pstmVd = null, pstmDv = null;
		ResultSet rsVd = null, rsDv = null;		
		
		try {
			if(con!=null){			
				
					
					pstmVd = con.prepareStatement(sqlVdSintetico);
					pstmVd.setDate(1, inicio);
					pstmVd.setDate(2, fim);
					pstmVd.setString(3, TESvd);
					pstmVd.setString(4, filial);
					pstmVd.setDate(5, inicio);
					pstmVd.setDate(6, fim);
					
					rsVd = pstmVd.executeQuery();

					while(rsVd.next()){
						Venda v = new Venda();
						Cliente c = new Cliente();
						c.setCodigocl(rsVd.getString("F2_CLIENTE"));
						c.setNome(rsVd.getString("A1_NOME"));
						c.setVendedor(rsVd.getString("F2_VEND1"));
						//c.setCgc(rsVd.getString("A1_CGC"));
						v.setCliente(c);
						v.setBruto(rsVd.getDouble("BRUTO"));
						v.setDesconto(rsVd.getDouble("DESCONTO"));
						v.setTotal(rsVd.getDouble("LIQUIDO"));
						if(c.getCodigocl().equals("000001")){
							v.setBruto(v.getBruto() - v.getDesconto());
						}
						pstmDv = con.prepareStatement(sqlDvSintetico);
						pstmDv.setDate(1, inicio);
						pstmDv.setDate(2, fim);
						pstmDv.setString(3, TESdv);
						pstmDv.setString(4, filial);
						pstmDv.setString(5, c.getCodigocl());
						pstmDv.setDate(6, inicio);
						pstmDv.setDate(7, fim);
						rsDv = pstmDv.executeQuery();
						if(rsDv.next()){
							v.setDevolucao(rsDv.getDouble("DEVOLUCAO"));
							rsDv.close();
							pstmDv.close();
							pstmDv = con.prepareStatement(sqlDvSint2);
							if(c.getCodigocl().equals("000001")){								
								pstmDv.setString(1, "003636");
							}else{
								pstmDv.setString(1, c.getCodigocl());
							}
							pstmDv.setDate(2, inicio);
							pstmDv.setDate(3, fim);
							pstmDv.setString(4, filial);
							rsDv = pstmDv.executeQuery();
							if(rsDv.next()){
								double dv = v.getDevolucao();
								v.setDevolucao(dv + rsDv.getDouble("DEVOLUCAO"));
							}							
						}else{
							rsDv.close();
							pstmDv.close();
							pstmDv = con.prepareStatement(sqlDvSint2);
							if(c.getCodigocl().equals("000001")){
								pstmDv.setString(1, "003636");
							}else{
								pstmDv.setString(1, c.getCodigocl());
							}
							pstmDv.setDate(2, inicio);
							pstmDv.setDate(3, fim);
							pstmDv.setString(4, filial);
							rsDv = pstmDv.executeQuery();
							if(rsDv.next()){
								v.setDevolucao(rsDv.getDouble("DEVOLUCAO"));
							}else{
								v.setDevolucao(0);
							}
						}
						rsDv.close();
						pstmDv.close();
						lista.add(v);
					}
					rsVd.close();
					pstmVd.close();
					con.close();
								
			}else{
				lista = null;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lista;
	}

	
	
	public void getVendasProduto(String filial, String cliente, Date inicio, Date fim, String[] Grpcli, String TESVenda, String TESDevolucao, String SerieIni, String SerieFim, HttpServletResponse response){
		Connection con = ConnectionFactory.getInstance().getSqlConnection();

		String grupo = " AND (A1_GRPTRIB = '";
		String codigocli = "";
		String series = "";
		if(!SerieIni.equals("")) {
			if(!SerieFim.equals("")) {
				series = " AND D2_SERIE BETWEEN '"+SerieIni+"' AND '"+SerieFim+"'";
			}else {
				series = " AND D2_SERIE = '"+SerieIni+"'";
			}
		}
		if(!cliente.equalsIgnoreCase("todos")) {
			codigocli = " AND D2_CLIENTE = '"+cliente+"'";
		}
		for(String s : Grpcli) {
			grupo += s + "' OR A1_GRPTRIB = '";
		}
		
		grupo = grupo.substring(0, grupo.length() - 18)+")";
		
		String sql = "SELECT A1_VEND AS VENDEDOR, B1_FAMCML AS FAMCML, B1_NIVEL AS SEGMENTO,"
					+ " B1_COD AS CODIGO, B1_DESC AS DESCRICAO, QTDEV AS VENDAS, VLRV AS VLR_VENDAS, QTDDEV AS DEVOL, VLRDEV AS VLR_DEVOL,"
					+ " (QTDEV - QTDDEV) AS TOTAL, (VLRV - VLRDEV) AS VLR_TOTAL"
					+ " FROM "+TotvsDb.SB1.getTable(ControlServlet.getParams().getGpoemptotvs())+" INNER JOIN"
					+ " (SELECT A.A1_VEND, D2_COD, QTDEV, VLRV, ISNULL(QTDED,0) AS QTDDEV, ISNULL(VLRD,0) AS VLRDEV FROM"
					+ " (SELECT R.A1_VEND, D2_COD, SUM(D2_QUANT) AS QTDEV, SUM(D2_TOTAL) AS VLRV"
					+ " FROM "+TotvsDb.SD2.getTable(ControlServlet.getParams().getGpoemptotvs())+" S INNER JOIN"
					+ " "+TotvsDb.SA1.getTable(ControlServlet.getParams().getGpoemptotvs())+" R"
					+ " ON R.A1_COD = D2_CLIENTE"
					+ grupo
					+ " AND R.A1_VEND <> '' AND (D2_TES = ? OR D2_TES = '501')"
					+ " AND S.D_E_L_E_T_ <> '*'"
					+ " AND D2_EMISSAO BETWEEN ? AND ? AND D2_FILIAL = ?"+codigocli+series
					+ " GROUP BY R.A1_VEND, D2_COD) AS A"
					+ " LEFT JOIN"
					+ " (SELECT A1_VEND, D1_COD, SUM(D1_QUANT) AS QTDED, SUM(D1_CUSTO) AS VLRD"
					+ " FROM "+TotvsDb.SD1.getTable(ControlServlet.getParams().getGpoemptotvs())+" Y INNER JOIN"
					+ " (SELECT B.A1_VEND, B.A1_COD, A2_COD, B.A1_GRPTRIB FROM"
					+ " (SELECT A1_VEND, A1_COD, A1_NOME, A1_GRPTRIB, A2_COD"
					+ " FROM "+TotvsDb.SA1.getTable(ControlServlet.getParams().getGpoemptotvs())+" INNER JOIN "+TotvsDb.SA2.getTable(ControlServlet.getParams().getGpoemptotvs())+""
					+ " ON A1_COD <> A2_COD"
					+ " AND A1_CGC = A2_CGC"
					+ grupo+") AS A"
					+ " RIGHT JOIN"
					+ " (SELECT DISTINCT A1_VEND, A1_COD, A1_NOME, A1_GRPTRIB, D1_FORNECE"
					+ " FROM "+TotvsDb.SA1.getTable(ControlServlet.getParams().getGpoemptotvs())+" INNER JOIN "+TotvsDb.SD1.getTable(ControlServlet.getParams().getGpoemptotvs())+" Z"
					+ " ON A1_COD = D1_FORNECE AND Z.D_E_L_E_T_<>'*'"
					+ grupo+") AS B"
					+ " ON A.A1_COD = B.A1_COD) AS W"
					+ " ON (D1_FORNECE = A1_COD OR D1_FORNECE = A2_COD)"
					+ " AND D1_TES = ?"
					+ " AND Y.D_E_L_E_T_<>'*'"
					+ " AND D1_EMISSAO BETWEEN ? AND ? AND D1_FILIAL = ?"
					+ " GROUP BY A1_VEND, D1_COD) AS T"
					+ " ON A.A1_VEND = T.A1_VEND AND D2_COD = D1_COD) AS B"
					+ " ON B1_COD = D2_COD"
					+ "	ORDER BY B1_FAMCML, B1_NIVEL, B1_COD";
		
		if(con!= null){			
			try {
				PreparedStatement pstm = con.prepareStatement(sql);
				pstm.setString(1, TESVenda);
				pstm.setDate(2, inicio);
				pstm.setDate(3, fim);
				pstm.setString(4, filial);
				pstm.setString(5, TESDevolucao);
				pstm.setDate(6, inicio);
				pstm.setDate(7, fim);				
				pstm.setString(8, filial);
				
				ResultSet rs = pstm.executeQuery();

				response.setContentType("application/vnd.ms-excel");
		    	response.setHeader("Content-Disposition", "attachment; filename=vdprodutos.xls");			
		    	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
		    	
		    	ResultSetToExcel rsx = new ResultSetToExcel(rs, "vdprod");		    	
		    	rsx.generate(response);
		    					
				pstm.close();
				rs.close();
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParsePropertyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}		
	}
}
