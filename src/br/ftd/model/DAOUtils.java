package br.ftd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ftd.control.ControlServlet;
import br.ftd.entity.Auditoria;
import br.ftd.entity.Produto;
import br.ftd.entity.TotvsDb;
import br.ftd.factory.ConnectionFactory;

public class DAOUtils {

	public static int getProximoId(String tabela){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "show table status like '"+tabela+"'";
		int proxid = 0;
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			if(rs.next()){
				proxid = rs.getInt("Auto_increment");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return proxid;
	}
	
	public static void setReimpressao(int iddoacao, int reimpressao){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "update doacao set reimpressao = ? where iddoacao = ?";
		
		int nova = reimpressao + 1;
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, nova);
			pstm.setInt(2, iddoacao);
			pstm.execute();
			pstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static List<Produto> getEstoques() throws SQLException{

		Connection conSQL = ConnectionFactory.getInstance().getSqlConnection();
		
		ResultSet rs = null;
		
		Statement stm = null;						
		
		List<Produto> itens = new ArrayList<>();
		
		Map<String, Produto> map = new HashMap<>();
		
		try {

			if(conSQL != null)
			{
				
				stm = conSQL.createStatement();				

				rs = stm.executeQuery("SELECT B2_COD, B2_QATU, B2_RESERVA, B2_QPEDVEN, B2_LOCAL, B2_FILIAL"
						+ " FROM "+TotvsDb.SB2.getTable(ControlServlet.getParams().getGpoemptotvs())+" WHERE D_E_L_E_T_ <> '*'"
						+ " AND B2_LOCAL = '01'"
						+ " ORDER BY B2_COD, B2_FILIAL");
				
				int qtde = 0, reserva = 0, fil01 = 0, fil02 = 0;
				String codigo, filial;
				Produto p = null;				
				
				while(rs.next()){

					codigo = rs.getString("B2_COD");
					codigo = codigo.trim();
					filial = rs.getString("B2_FILIAL");
					
					if(map.get(codigo) == null){
						p = new Produto();
						p.setCodigo(codigo);
						map.put(codigo, p);
						qtde=0;
						reserva=0;
						fil01=0;
						fil02=0;
					}else{
						p = map.get(codigo);
					}
					
					if(filial.equals("01")){					
						qtde += rs.getInt("B2_QATU");
						reserva += rs.getInt("B2_RESERVA");
						reserva += rs.getInt("B2_QPEDVEN");
						p.setAlm01(qtde);
						p.setReserva(reserva);
					}else if(filial.equals("02")){
						fil01 += rs.getInt("B2_QATU");
						p.setFil01(fil01);
					}else if(filial.equals("03")){
						fil02 += rs.getInt("B2_QATU");
						p.setFil02(fil02);
					}
										
				}								
				rs.close();

				rs = stm.executeQuery("SELECT B2_COD, B2_QATU, B2_RESERVA, B2_QPEDVEN, B2_LOCAL, B2_FILIAL"
						+ " FROM "+TotvsDb.SB2.getTable(ControlServlet.getParams().getGpoemptotvs())+" WHERE D_E_L_E_T_ <> '*' AND B2_FILIAL = '01'"
						+ " AND B2_LOCAL = '02'"
						+ " ORDER BY B2_COD");

				qtde=0;
				String ant = "";
				p = null;
				
				while(rs.next()){

					codigo = rs.getString("B2_COD");
					codigo = codigo.trim();
					
					if(map.get(codigo) == null){

						p = new Produto();
						p.setCodigo(codigo);
						map.put(codigo, p);
						qtde = 0;
					}else if(!ant.equals(codigo)){
						p = map.get(codigo);
						qtde = 0;
					}
										
					qtde += rs.getInt("B2_QATU");
					
					p.setAlm02(qtde);
					
					ant = codigo;
				}								
				rs.close();

				rs = stm.executeQuery("SELECT B2_COD, B2_QATU, B2_RESERVA, B2_QPEDVEN, B2_LOCAL, B2_FILIAL"
						+ " FROM "+TotvsDb.SB2.getTable(ControlServlet.getParams().getGpoemptotvs())+" WHERE D_E_L_E_T_ <> '*' AND B2_FILIAL = '01'"
						+ " AND B2_LOCAL = '09'"
						+ " ORDER BY B2_COD");

				qtde=0;
				p = null;
				
				while(rs.next()){

					codigo = rs.getString("B2_COD");
					codigo = codigo.trim();
					
					if(map.get(codigo) == null){
						p = new Produto();
						p.setCodigo(codigo);
						map.put(codigo, p);
					}else{
						p = map.get(codigo);
					}
										
					qtde += rs.getInt("B2_QATU");
					
					p.setAlm09(qtde);
					
					qtde=0;
					
				}								
				rs.close();

				rs = stm.executeQuery("SELECT B1_COD, B1_DESC"
						+ " FROM "+TotvsDb.SB1.getTable(ControlServlet.getParams().getGpoemptotvs())+" WHERE D_E_L_E_T_ <> '*'"
						+ " ORDER BY B1_COD");
				
				while(rs.next()){
					codigo = rs.getString("B1_COD");
					codigo = codigo.trim();
					if(map.get(codigo)!=null){
						p = map.get(codigo);
						if(p.temEstoque()){
							p.setDescricao(rs.getString("B1_DESC"));
							p.refazEstoque();
							itens.add(p);
						}
						
					}
				}
				rs.close();
				stm.close();
				conSQL.close();
				
				Collections.sort(itens);
				
			}
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return itens;
	}
	
	
	public static List<String> getMunicipios(String uf) throws SQLException{
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		List<String> lista = new ArrayList<>();
		String sql1 = "select nome from municipios order by uf, nome";
		String sql2 = "select nome from municipios where uf = '"+uf+"' order by uf, nome";
		
		Statement stm = con.createStatement();;
		ResultSet rs;
		
		if(uf == null){
			rs = stm.executeQuery(sql1);
		}else{
			rs = stm.executeQuery(sql2);
		}
		
		while(rs.next()){
			lista.add(rs.getString("nome"));
		}
		rs.close();
		stm.close();
		con.close();
		
		return lista;
	}

	public static List<String> getUf() throws SQLException{
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		List<String> lista = new ArrayList<>();
		String sql1 = "select distinct uf from municipios order by uf";
		
		Statement stm = con.createStatement();;
		ResultSet rs;
		
		rs = stm.executeQuery(sql1);
		
		while(rs.next()){
			lista.add(rs.getString("uf"));
		}
		rs.close();
		stm.close();
		con.close();
		
		return lista;
	}
	
	public static String getCodMun(String uf, String mun){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select codigo from municipios where uf = ? and nome = ?";		
		String codmun = "";
		
		PreparedStatement pstm;
		
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, uf);
			pstm.setString(2, mun);
			ResultSet rs = pstm.executeQuery();
			int codigo = 0;
			if(rs.next()){
				codigo = rs.getInt("codigo");
			}
			rs.close();
			pstm.close();
			con.close();
			
			codigo = codigo % 2100000;
			codmun = String.format("%05d", codigo);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return codmun;
	}
	
	
	public static String loadCodCliente(){
		Connection con = ConnectionFactory.getInstance().getSqlConnection();
		String sql = "SELECT MAX(A1_COD) AS CODIGO FROM "+TotvsDb.SA1.getTable(ControlServlet.getParams().getGpoemptotvs());
		String codigo = "000000";
		if(con != null){
			try {
				Statement stm = con.createStatement();
				ResultSet rs = stm.executeQuery(sql);
				if(rs.next()){
					codigo = rs.getString("CODIGO");
				}
				rs.close();
				stm.close();
				con.close();

				int cod = Integer.parseInt(codigo.replaceFirst("0*", ""));
				cod++;
				codigo = String.format("%06d", cod);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return codigo;
	}
	
	
	public static List<String> yearSelection(String action){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		List<String> lista = new ArrayList<>();

		String anoperiodo = getAnoPeriodo()+"";
		boolean flag = true;

		String sql = "select distinct year(dataped) as ano from pedido order by year(dataped) desc";
		
		if(action.equals("consultaradocoes")){
			sql = "select distinct ano from adocao where not isnull(ano) order by ano desc";
		}else if(action.equals("consultardoacoes")){
			sql = "select distinct year(emissao) as ano from doacao order by year(emissao) desc";
		}else if(action.equals("consultarkardex")){
			con = null;
			con = ConnectionFactory.getInstance().getSqlConnection();
			sql = "SELECT DISTINCT YEAR(C5_EMISSAO) AS ano FROM "+TotvsDb.SC5.getTable(ControlServlet.getParams().getGpoemptotvs())
					+" ORDER BY YEAR(C5_EMISSAO) DESC";
		}else if(action.equals("consultarpendencias")){
			sql = "select distinct year(emissao) as ano from pedcliente order by year(emissao) desc";
		}else if(action.equals("consultarnotas")){
			sql = "select distinct year(emissao) as ano from notafiscal where emissao <> 0 order by year(emissao) desc";
		}

		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);			
			while(rs.next()){
				String year = rs.getString("ano");
				if(!year.equalsIgnoreCase(anoperiodo) && flag) {
					lista.add(anoperiodo);
					flag = false;
				}
				lista.add(year);
			}
			rs.close();
			stm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return lista;
	}
	
	public static int getAnoPeriodo(){
		
		Calendar agora = Calendar.getInstance();
		
		int mes = agora.get(Calendar.MONTH);
		int ano = agora.get(Calendar.YEAR);
		if(mes > 6){
			ano += 1;
		}
		return ano;
	}
	
	public static int getFlag(String tabela){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select flag from utils where tabela = ?";
		int flag = 1;
		
		PreparedStatement pstm;
		
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, tabela);
			ResultSet rs = pstm.executeQuery();
			if(rs.next()){
				flag = rs.getInt("flag");
			}
			rs.close();
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public static Auditoria getAuditoriaUser(String tabela, int record_id) {

		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select * from auditoria where tabela = ? and record_id = ? order by id desc limit 1";
		Auditoria audit = null;
		
		PreparedStatement pstm;
		
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, tabela);
			pstm.setInt(2, record_id);
			ResultSet rs = pstm.executeQuery();
			if(rs.next()){
				audit = new Auditoria();				
				audit.setUsuario(DAOUsuario.getUsuario(rs.getInt("user_id")));
				audit.setCreated(rs.getString("created"));
				audit.setRegistro(record_id);
			}
			rs.close();
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return audit;
	}
	
}
