package br.ftd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import br.ftd.control.ControlServlet;
import br.ftd.entity.Empresa;
import br.ftd.entity.EmpresaMin;
import br.ftd.entity.TotvsDb;
import br.ftd.entity.Usuario;
import br.ftd.factory.ConnectionFactory;

public class DAOEmpresa {
	
	
	public List<Empresa> listar(){
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select * from empresa order by filialftd";
		List<Empresa> lista = new ArrayList<>();
		
		try {
				Statement stm = con.createStatement();
				ResultSet rs = stm.executeQuery(sql);
				while(rs.next()){
					Empresa e = new Empresa();
					e.setId(rs.getInt("idempresa"));				
					e.setCodigoftd(rs.getString("codigoftd"));
					e.setRazaosocial(rs.getString("razao_social"));
					e.setTelefone(rs.getString("telefone"));
					e.setEmail(rs.getString("email"));
					e.setCnpj(rs.getString("cnpj"));
					e.setNomereduz(rs.getString("nomereduz"));
					e.setFilialftd(rs.getString("filialftd"));
					
					lista.add(e);
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

	public boolean containsIgnoreAccents(String a, String b) {
	    String input1 = Normalizer.normalize(a, Normalizer.Form.NFD)
	            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
	            .toLowerCase();

	    String input2 = Normalizer.normalize(b, Normalizer.Form.NFD)
	            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
	            .toLowerCase();

	    return input1.contains(input2);
	}
	
	public List<EmpresaMin> listar(String nome){
		
		Connection con = ConnectionFactory.getInstance().getSqlConnection();
		
		String[] terms = nome.split(" ");
		String query = "(";
		for(String s : terms) {
			query += "(A1_NOME like '%"+s+"%' or A1_NREDUZ like '%"+s+"%' or A1_MUN like '%"+s+"%' or A1_CGC like '%"+s+"%') or ";
		}
		
		int len = query.length() - 4;
		
		query = query.substring(0, len) + ")";

		String sql = "select A1_COD, A1_NOME, A1_NREDUZ, A1_CGC, A1_MUN "
				+ "from "+TotvsDb.SA1.getTable(ControlServlet.getParams().getGpoemptotvs())+" where "+query
								+ " AND A1_MSBLQL <> '1' order by A1_NOME";
		
		List<EmpresaMin> lista = new ArrayList<>();
		
		try {			
			Statement stm;
			ResultSet rs;
			if(con != null){
				stm = con.createStatement();
				rs = stm.executeQuery(sql);
				while(rs.next()){
					EmpresaMin e = new EmpresaMin();
					String linha = rs.getString("A1_NOME") +" "+ rs.getString("A1_CGC") + " " + rs.getString("A1_NREDUZ") + " " + rs.getString("A1_MUN");
					int index = terms.length;
					for(String term : terms) {
						if(containsIgnoreAccents(linha, term)){
							index--;
						}
					}
					if(index == 0) {
						e.setId(rs.getString("A1_COD"));				
						e.setText(rs.getString("A1_NOME"));
						e.setDesc(rs.getString("A1_CGC")+" / "+rs.getString("A1_NREDUZ")+" / "+rs.getString("A1_MUN"));
						lista.add(e);
					}
				}
				rs.close();
				stm.close();
				con.close();					
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return lista;
	}
	
	public static void recarrega(Empresa empresa){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select * from empresa where idempresa = "+empresa.getId()+" "
				+ "or codigoftd = '"+empresa.getCodigoftd()+"'";
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			if(rs.next()){
				empresa.setId(rs.getInt("idempresa"));
				empresa.setCodigoftd(rs.getString("codigoftd"));
				empresa.setRazaosocial(rs.getString("razao_social"));
				empresa.setTelefone(rs.getString("telefone"));
				empresa.setEmail(rs.getString("email"));
				empresa.setCnpj(rs.getString("cnpj"));
			}
			rs.close();
			stm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}
	
	public Usuario validaLivreiro(String login, String senha){
		Connection con = ConnectionFactory.getInstance().getSqlConnection();
		String sql = "SELECT A1_COD, A1_NOME, A1_CGC, A1_EMAIL, A1_DESC FROM "+TotvsDb.SA1.getTable(ControlServlet.getParams().getGpoemptotvs())
		+ " WHERE (SELECT SUBSTRING(A1_EMAIL,1, CASE CHARINDEX(',', LTRIM(RTRIM(A1_EMAIL))) WHEN 0 THEN LEN(LTRIM(RTRIM(A1_EMAIL)))"
		+ " ELSE CHARINDEX(',', LTRIM(RTRIM(A1_EMAIL))) - 1 END) AS EMAIL) = '"+login+"' AND A1_CGC = '"+senha+"' AND A1_MSBLQL <> '1'";
		Usuario usuario = null;
		try {
			if(con != null){
					Statement stm = con.createStatement();
					ResultSet rs = stm.executeQuery(sql);
					if(rs.next()){
						usuario = new Usuario();
						usuario.setId(99);
						usuario.setCodigoftdempresa(rs.getString("A1_COD"));
						usuario.setNome(rs.getString("A1_NOME").trim());
						usuario.setEmail(rs.getString("A1_EMAIL").trim());
						usuario.setCnpj(rs.getString("A1_CGC"));
						usuario.setDesconto(rs.getString("A1_DESC"));
						usuario.setCargo(4);
						usuario.setSetor(90);
					}
					rs.close();
					stm.close();
					con.close();
					
				}
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usuario;
	}
	
	
	public static void getDadosEmpresa(Empresa empresa){
		Connection con = ConnectionFactory.getInstance().getSqlConnection();
		String sql = "SELECT A1_NOME, A1_CGC, A1_TEL, A1_EMAIL, A1_END, A1_EST, A1_MUN, A1_BAIRRO FROM "+TotvsDb.SA1.getTable(ControlServlet.getParams().getGpoemptotvs())
				+ " WHERE A1_COD = ?";
		try {
			if(con != null){
	
					PreparedStatement pstm = con.prepareStatement(sql);
					pstm.setString(1, empresa.getCodigoftd());
					ResultSet rs = pstm.executeQuery();
					while(rs.next()){
						empresa.setRazaosocial(rs.getString("A1_NOME"));
						empresa.setCnpj(rs.getString("A1_CGC"));
						empresa.setEmail(rs.getString("A1_EMAIL"));
						empresa.setTelefone(rs.getString("A1_TEL"));
						empresa.setEndereco(rs.getString("A1_END")+" - "+rs.getString("A1_BAIRRO"));
						empresa.setMunicipio(rs.getString("A1_MUN"));
						empresa.setUf(rs.getString("A1_EST"));
					}
					rs.close();
					pstm.close();
					con.close();
					
			}else{
				con = ConnectionFactory.getInstance().getMySqlConnection();
				sql = " select distinct codigoftd, nomeftd, endereco, municipio, uf, email from pedcliente where codigoftd = ?;";
				PreparedStatement pstm = con.prepareStatement(sql);
				pstm.setString(1, empresa.getCodigoftd());
				ResultSet rs = pstm.executeQuery();
				if(rs.next()){
					empresa.setRazaosocial(rs.getString("nomeftd"));
					empresa.setEndereco(rs.getString("endereco"));
					empresa.setMunicipio(rs.getString("municipio"));
					empresa.setUf(rs.getString("uf"));
					empresa.setEmail(rs.getString("email"));
				}else{
					empresa.setRazaosocial("Nao identificada");
					empresa.setCnpj("");
					empresa.setEmail("");
					empresa.setTelefone("");
					empresa.setEndereco("Endereco");
					empresa.setMunicipio("Municipio");
					empresa.setUf("UF");
				}
				rs.close();
				pstm.close();
				con.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
