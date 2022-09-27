package br.ftd.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import br.ftd.control.ControlServlet;
import br.ftd.entity.Adocao;
import br.ftd.entity.ConsProduto;
import br.ftd.entity.DoacaoRelat;
import br.ftd.entity.EnumList;
import br.ftd.entity.Escola;
import br.ftd.entity.ItemDemanda;
import br.ftd.entity.ItemOrcamento;
import br.ftd.entity.ItemPedCliente;
import br.ftd.entity.ItemPedido;
import br.ftd.entity.ItemTabela;
import br.ftd.entity.ItemVendasDoProduto;
import br.ftd.entity.Kardex;
import br.ftd.entity.NotaFiscal;
import br.ftd.entity.Orcamento;
import br.ftd.entity.Pedido;
import br.ftd.entity.Produto;
import br.ftd.entity.ResultSetToExcel;
import br.ftd.entity.TotvsDb;
import br.ftd.entity.Usuario;
import br.ftd.entity.vendasDoProduto;
import br.ftd.factory.ConnectionFactory;

public class DAOProduto {

	
	public DAOProduto(){}

	
	public String salvar(Produto p) throws SQLException{
		Connection con = null;
		PreparedStatement pstm = null;
		
		String sql = "insert into produto values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String mensagem = "Ok";
		if(!verificaCadastroProduto(p)) {
			try {
				con = ConnectionFactory.getInstance().getMySqlConnection();
				con.setAutoCommit(false);
				pstm = con.prepareStatement(sql);
				
				pstm.setString(1, p.getCodigo());
				pstm.setString(2, p.getDescricao());
				pstm.setFloat(3, p.getPreco());
				pstm.setString(4, p.getCodbar());
				pstm.setString(5, p.getSerie());
				pstm.setString(6, p.getObs());
				pstm.setString(7, p.getAutor());
				pstm.setString(8, p.getNivel());
				pstm.setString(9, p.getFamilia());
				pstm.setString(10, p.getColecao());
				pstm.setString(11, p.getDisciplina());
				pstm.setString(12, p.getEditora());
				pstm.setInt(13, 0);
				pstm.setString(14, p.getImagem());
				pstm.setInt(15, p.getMarketshare());
				pstm.setString(16, p.getGrupo());
				pstm.setInt(17, p.getPaginas());
				pstm.setString(18, p.getLancto());
				pstm.setFloat(19, p.getPeso());
				pstm.setString(20, p.getStatus());
				
				pstm.execute();
				
				con.commit();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mensagem = e.getMessage();
				con.rollback();
				
			}finally{
				if(pstm != null) pstm.close();
				if(con != null) 	con.close();				
			}
		}else {
			mensagem = "Erro";
		}
		
		return mensagem;
	}
	
	public boolean verificaCadastroProduto(Produto p) {
		boolean flag = false;
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select codigo from produto where codigo = ?";
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, p.getCodigo());
			ResultSet rs = pstm.executeQuery();
			if(rs.next()) {
				flag = true;
			}
			pstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return flag;
	}
	
	
	public boolean remover(Produto p){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "delete from produto where codigo = ?";
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, p.getCodigo());
			pstm.execute();
			pstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	

	public static boolean containsIgnoreAccents(String a, String b) {
	    String input1 = Normalizer.normalize(a, Normalizer.Form.NFD)
	            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
	            .toLowerCase();

	    String input2 = Normalizer.normalize(b, Normalizer.Form.NFD)
	            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
	            .toLowerCase();

	    return input1.contains(input2);
	}
	
	public ArrayList<Produto> pesquisar(String descricao, Usuario usuario) throws SQLException{
			
		
		String sql = "";
		String[] desc = null;
		String[] terms = null;
		boolean comma = false;
		if(descricao.contains(";")){
			comma = true;
			desc = descricao.split(";");
			
			String d = "";
			
			for(String st : desc){
				String[] s = st.split("=");
				if(s.length > 1)
					d = d + s[0] + " like '%" + s[1] + "%' and ";
			}
			
			d = d.substring(0, d.length()-4);
			if(usuario != null){
				sql = "Select codigo, descricao, preco, autor, codbar, obs, status, colecao, editora, disciplina, serie, familia, nivel, imagem from produto where"
						+ " ("+d+") and inativo = 0 order by codigo";
			}else{
				sql = "Select codigo, descricao, preco, autor, codbar, obs, status, colecao, editora, disciplina, serie, familia, nivel, imagem from produto where"
						+ " ("+d+") and inativo = 0 and familia not like '12-%' order by codigo";				
			}
			
		}else{
			
			terms = descricao.split(" ");
			
			String query = "(";
			
			for(String s : terms) {
				query += "(codigo like '%"+s+"%' or descricao like '%"+s+"%'"
						+ " or obs like '%"+s+"%' or autor like '%"+s+"%'"
						+ " or colecao like '%"+s+"%' or codbar like '%"+s+"%' or "
							+ "disciplina like '%"+s+"%' or "
								+ "familia like '%"+s+"%' or "
										+ "nivel like '%"+s+"%' or "
											+ "serie like '%"+s+"%' or "
												+ "editora like '%"+s+"%') or ";
			}

			int len = query.length() - 4;
			
			query = query.substring(0, len) + ")";						
			
			if(usuario != null){
				sql = "Select codigo, descricao, preco, autor, codbar, obs, status, colecao, editora, disciplina, serie, familia, nivel, imagem from produto where "
						+ query +" and inativo = 0 order by codigo";
			}else{
				sql = "Select codigo, descricao, preco, autor, codbar, obs, status, colecao, editora, disciplina, serie, familia, nivel, imagem from produto where "
						+ query + " and inativo = 0 and familia not like '12-%' order by codigo";				
			}
		}
		
		ArrayList<Produto> t = new ArrayList<>();
		Connection con = null;	
		Statement stm = null;
		
		try {
			con = ConnectionFactory.getInstance().getMySqlConnection();	
			con.setAutoCommit(false);
			stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()){
				String codigo, descr, obs, codb, editora, status, discip, autor;
				float preco;
				codigo = rs.getString("codigo");
				descr = rs.getString("descricao");
				discip = rs.getString("disciplina");
				autor = rs.getString("autor");
				preco =  rs.getFloat("preco");
				obs = rs.getString("obs");
				status = rs.getString("status");
				codb = rs.getString("codbar");
				editora = rs.getString("editora");
				Produto n;
				if(!comma) {
					String linha = codigo + " " + descr + " " + codb + " " + editora + " " + status + " " + obs + " " + discip + " " + autor;					

					int index = terms.length;
					for(String term : terms) {
						if(containsIgnoreAccents(linha, term)){
							index--;
						}
					}
					if(index == 0) {
						n = new Produto(codigo, descr, preco);
						n.setAutor(rs.getString("autor"));
						n.setDisciplina(rs.getString("disciplina"));
						n.setSerie(rs.getString("serie"));
						n.setFamilia(rs.getString("familia"));
						n.setNivel(rs.getString("nivel"));
						n.setColecao(rs.getString("colecao"));
						n.setImagem(rs.getString("imagem"));
						n.setEditora(editora);
						n.setCodbar(codb);
						n.setEstoque(0);
						n.setObs(obs);
						n.setStatus(status);
						t.add(n);
					}
					
				}else {
					n = new Produto(codigo, descr, preco);
					n.setAutor(rs.getString("autor"));
					n.setDisciplina(rs.getString("disciplina"));
					n.setSerie(rs.getString("serie"));
					n.setFamilia(rs.getString("familia"));
					n.setNivel(rs.getString("nivel"));
					n.setColecao(rs.getString("colecao"));
					n.setImagem(rs.getString("imagem"));
					n.setEditora(editora);
					n.setCodbar(codb);
					n.setEstoque(0);
					n.setObs(obs);
					n.setStatus(status);
					t.add(n);
				}
				
			}
			rs.close();
			con.commit();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			con.rollback();
		}finally{
			if(stm != null) stm.close();
			if(con != null) con.close();
		}
		if(!t.isEmpty())
			setEstoque(t);
		
		return t;
	}
	

	
	public ArrayList<Produto> lancamentos(String ano, Usuario usuario) throws SQLException{
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		//Map<String, Date> map = new HashMap<>();
		//Map<String, String> mapobs = new HashMap<>();
		
		//Date agora = new Date(System.currentTimeMillis());
		
		String sql = "";
		
		if(usuario != null){
			sql = "Select codigo, descricao, preco, autor, codbar, obs, colecao, editora, disciplina, serie, familia, nivel, imagem from produto where"
					+ " status like '%"+ano+"%' and inativo = 0 order by codigo";
		}else{
			sql = "Select codigo, descricao, preco, autor, codbar, obs, colecao, editora, disciplina, serie, familia, nivel, imagem from produto where"
					+ " status like '%"+ano+"%' and inativo = 0 and familia <> '12-SEFTD' and familia <> '12-SEPAR' order by codigo";				
		}
		
		ArrayList<Produto> t = new ArrayList<>();

		try {
			
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()){
				String codigo, descr, obs, codb, editora;
				float preco;
				codigo = rs.getString("codigo");
				descr = rs.getString("descricao");
				preco =  rs.getFloat("preco");
				obs = rs.getString("obs");
				codb = rs.getString("codbar");
				editora = rs.getString("editora");
				Produto n = new Produto(codigo, descr, preco);
				n.setAutor(rs.getString("autor"));
				n.setDisciplina(rs.getString("disciplina"));
				n.setSerie(rs.getString("serie"));
				n.setFamilia(rs.getString("familia"));
				n.setNivel(rs.getString("nivel"));
				n.setColecao(rs.getString("colecao"));
				n.setImagem(rs.getString("imagem"));
				n.setEditora(editora);
				n.setCodbar(codb);
				n.setEstoque(0);
				n.setObs(obs);
				t.add(n);
			}
			rs.close();
			stm.close();
			
/*			sql = "select codigo, max(previsao) as prev, observacao from"
					+ " item_pedido where cancelado = 0 and previsao >= ? group by codigo";
			
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setDate(1, agora);
			rs = pstm.executeQuery();
			
			while(rs.next()){
				map.put(rs.getString("codigo"), rs.getDate("prev"));
				mapobs.put(rs.getString("codigo"), rs.getString("observacao"));
			}
			rs.close();
			pstm.close();
			
			for(Produto p : t){
				if(map.get(p.getCodigo())!=null){
					p.setPrevisao(map.get(p.getCodigo()));
					p.setObspedido(mapobs.get(p.getCodigo()));
				}
			}
*/			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			con.close();
		}
		//if(!t.isEmpty())
			//setEstoque(t);
		
		return t;
	}

	
	
	public ArrayList<Produto> pesquisar(String[] descricao) throws SQLException{
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		String sql = "Select codigo, descricao, autor, obs, editora, disciplina, serie, familia, nivel from produto where "
					 + "editora = ? and familia = ? and nivel = ? order by descricao";
		String sql1 = "Select codigo, descricao, autor, obs, editora, disciplina, serie, familia, nivel from produto where "
				 + "editora = ? and familia = ? order by descricao";
		
		ArrayList<Produto> t = new ArrayList<>();

		try {
			
			PreparedStatement pstm;
			
			
			if(descricao[2].equalsIgnoreCase("todos")){
				pstm = con.prepareStatement(sql1);
				pstm.setString(1, descricao[0]);
				pstm.setString(2, descricao[1]);
			}else{
				pstm = con.prepareStatement(sql);
				pstm.setString(1, descricao[0]);
				pstm.setString(2, descricao[1]);
				pstm.setString(3, descricao[2]);
			}						
			
			ResultSet rs = pstm.executeQuery();

			while(rs.next()){
				Produto n = new Produto();
				n.setCodigo(rs.getString("codigo"));
				n.setDescricao(rs.getString("descricao"));
				n.setAutor(rs.getString("autor"));
				n.setDisciplina(rs.getString("disciplina"));
				n.setSerie(rs.getString("serie"));
				n.setFamilia(rs.getString("familia"));
				n.setNivel(rs.getString("nivel"));
				n.setEditora(rs.getString("editora"));
				n.setObs(rs.getString("obs"));
				n.setImagem(rs.getString("imagem"));
				t.add(n);
			}
			rs.close();
			pstm.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			con.close();
		}
		
		return t;
	}

	
	
	
	public static void setEstoque(Produto p)throws SQLException{
		Connection con = null;
		Connection cona = null;
		
		ResultSet rs = null;
		ResultSet rsa = null;
		Statement stm = null;
		Statement stma = null;
		
		p.setEstoque(0);
		p.setAlm09(0);
		p.refazNivelEstoque();
		
		boolean flag = true;
			try {
				con = ConnectionFactory.getInstance().getSqlConnection();
				if(con == null) flag = false;
				if(flag) {
					//con.setAutoCommit(false);
					stm = con.createStatement();
		
					rs = stm.executeQuery("SELECT B2_FILIAL, B2_COD, (SUM(B2_QATU) - SUM(B2_QPEDVEN)) AS ESTQ"
							+ " FROM "+TotvsDb.SB2.getTable(ControlServlet.getParams().getGpoemptotvs())+" WHERE B2_COD = '"+p.getCodigo()+"' AND D_E_L_E_T_ <> '*'"
									+ " AND (B2_LOCAL = '01' OR B2_LOCAL = '09' OR B2_LOCAL = '02')"
							+ " GROUP BY B2_FILIAL, B2_COD");
		
					while(rs.next()){
						
						String filial = rs.getString("B2_FILIAL");
						int estoque = rs.getInt("ESTQ");
						if(filial.equals("01")) {
							p.setEstoque(estoque);
							p.refazNivelEstoque();
						}else if(filial.equals("02")) {
							p.setFil01(estoque);
						}else if(filial.equals("03")) {
							p.setFil02(estoque);
						}
						
					}
	
					//con.commit();
				}
			}catch(SQLException e) {
				e.printStackTrace();
				con.rollback();
			}finally {
				if(rs != null) rs.close();
				if(stm !=null) stm.close();
				if(con != null) con.close();
			}
			
			try {
				if(flag) {
					cona = ConnectionFactory.getInstance().getMySqlConnection();
					//cona.setAutoCommit(false);
					stma = cona.createStatement();
					rsa = stma.executeQuery("select codigo, sum(qtde) as qtd from item_doacao natural join"
							+ " (select iddoacao, idusuario from doacao join (select id, nome, cargo, setor"
							+ " from usuario where setor <> 2 and setor <> 7 and setor <> 8"
							+ " and setor <> 9) as t on idusuario = id) as n where acertoftd = 0 and"
							+ " codigo = '"+p.getCodigo()+"' group by codigo");		
					int doado = 0;
					if(rsa.next()){
						
						doado = rsa.getInt("qtd");					
					}
					int estoque = p.getEstoque() - doado;
					p.setEstoque(estoque);
					p.refazNivelEstoque();
		
					//cona.commit();
				}
			}catch(SQLException e) {
				e.printStackTrace();
				cona.rollback();
				
			}finally {
				if(rsa != null) rsa.close();
				if(stma !=null) stma.close();
				if(cona != null) cona.close();				
			}
			
	}
	
	
	public static void setEstoque(ItemDemanda item) throws SQLException{

		Connection con = ConnectionFactory.getInstance().getSqlConnection();
		
		ResultSet rs = null;
		Statement stm = null;

		if(con != null)
		{
			
			stm = con.createStatement();

			rs = stm.executeQuery("SELECT B2_COD, (SUM(B2_QATU) - SUM(B2_QPEDVEN)) AS ESTQ"
					+ " FROM "+TotvsDb.SB2.getTable(ControlServlet.getParams().getGpoemptotvs())+" WHERE B2_COD = '"+item.getItem().getCodigo()+"' AND D_E_L_E_T_ <> '*'"
							+ " AND (B2_LOCAL = '01' OR B2_LOCAL = '09' OR B2_LOCAL = '02')"
					+ " AND B2_FILIAL = '01' GROUP BY B2_COD");

		if(rs.next()){
			item.getItem().setEstoque(rs.getInt("ESTQ"));
		}
		rs.close();
		stm.close();

		stm = con.createStatement();
		rs = stm.executeQuery("SELECT B2_COD, (SUM(B2_QATU) - SUM(B2_QPEDVEN)) AS ESTQ"
				+ " FROM "+TotvsDb.SB2.getTable(ControlServlet.getParams().getGpoemptotvs())+" WHERE B2_COD = '"+item.getItem().getCodigo()+"'"
						+ " AND B2_LOCAL = '09'"
				+ " AND B2_FILIAL = '01' AND D_E_L_E_T_ <> '*' GROUP BY B2_COD");

		if(rs.next()){
			item.getItem().setAlm09(rs.getInt("ESTQ")*-1);
		}else{
			item.getItem().setAlm09(0);
		}
		rs.close();
		stm.close();				
		con.close();
		
		
		con = ConnectionFactory.getInstance().getMySqlConnection();
		stm = con.createStatement();
		rs = stm.executeQuery("select codigo, sum(qtde) as qtd from item_doacao natural join"
				+ " (select iddoacao, idusuario from doacao join (select id, nome, cargo, setor"
				+ " from usuario where setor <> 2 and setor <> 7 and setor <> 8"
				+ " and setor <> 9) as t on idusuario = id) as n where acertoftd = 0 and"
				+ " codigo = '"+item.getItem().getCodigo()+"' group by codigo");		

		if(rs.next()){
			
			int estoque = item.getItem().getEstoque() - rs.getInt("qtd");
			
			item.getItem().setEstoque(estoque);
			
		}
		rs.close();
		stm.close();
		con.close();
		
		}else{
			item.getItem().setEstoque(0);
			item.getItem().setAlm09(0);
		}

	}
	
	
	
	public static void setEstoque(ArrayList<Produto> lista) throws SQLException{
			
		Connection con = null;
		Connection cona = null;
		
		ResultSet rs = null;
		ResultSet rsa = null;
		Statement stm = null;						
		Statement stma = null;
		
		Map<String, Integer> estoque = new HashMap<>();
		Map<String, Integer> doacoes = new HashMap<>();
		
		Collections.sort(lista);
		
		try {
			con = ConnectionFactory.getInstance().getSqlConnection();
			if(con != null)
			{	
				con.setAutoCommit(false);
				stm = con.createStatement();
				Produto prim, ulti;
				prim = lista.get(0);
				ulti = lista.get(lista.size() - 1);
				

				rs = stm.executeQuery("SELECT B2_COD, (SUM(B2_QATU) - SUM(B2_QPEDVEN)) AS ESTQ"
						+ " FROM "+TotvsDb.SB2.getTable(ControlServlet.getParams().getGpoemptotvs())+" WHERE B2_COD >= '"+prim.getCodigo()+"' AND B2_COD <= '"+ulti.getCodigo()+"'"
								+ " AND (B2_LOCAL = '01' OR B2_LOCAL = '09' OR B2_LOCAL = '02')"
						+ " AND B2_FILIAL = '01' AND D_E_L_E_T_ <> '*' GROUP BY B2_COD");

				while(rs.next()){
					String codigo = rs.getString("B2_COD");
					codigo = codigo.trim();				
					estoque.put(codigo, rs.getInt("ESTQ"));
				}
				rs.close();				
				con.commit();	
				
				cona = ConnectionFactory.getInstance().getMySqlConnection();
				cona.setAutoCommit(false);
				stma = cona.createStatement();
				rsa = stma.executeQuery("select codigo, sum(qtde) as qtd from item_doacao a inner join (select a.iddoacao from doacao a inner join usuario b on a.idusuario = b.id where setor <> 2 and setor <> 7 and setor <> 8 and setor <> 9) as t on a.iddoacao = t.iddoacao and acertoftd = 0 group by codigo");
				
				while(rsa.next()){
					doacoes.put(rsa.getString("codigo"), rsa.getInt("qtd"));
				}
				rsa.close();				
				cona.commit();
				
				for(Produto i : lista){
					
					if(estoque.get(i.getCodigo())!=null){
						
						i.setEstoque(estoque.get(i.getCodigo()));

						if(doacoes.get(i.getCodigo())!=null){
							int estq = i.getEstoque();
							int doac = doacoes.get(i.getCodigo());
							i.setEstoque(estq - doac);
						}
						i.refazNivelEstoque();
					}else{
						i.setEstoque(0);
						i.refazNivelEstoque();
					}
				}
				
			}else
			
			{
				for(Produto i : lista){				
					i.setEstoque(0);
					i.refazNivelEstoque();
				}
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			con.rollback();
			cona.rollback();
		}finally {
			if(stm != null) stm.close();
			if(stma != null) stma.close();
			if(con != null) con.close();
			if(cona != null)cona.close();
		}
		
	}
	

	
	public void setVendas(List<ItemOrcamento> lista, Date inicio) throws SQLException{
		
		Connection con = ConnectionFactory.getInstance().getSqlConnection();		
		String sql = "select D2_COD, SUM(D2_QUANT) AS QTDE from "+TotvsDb.SD2.getTable(ControlServlet.getParams().getGpoemptotvs())+" where (D2_TES = '511' or D2_TES = '901')"
				+ " and D2_EMISSAO >= ? and D2_COD = ? group by D2_COD";
		String sql2 = "select C6_PRODUTO, SUM(C6_QTDVEN) AS QTDE from "+TotvsDb.SC6.getTable(ControlServlet.getParams().getGpoemptotvs())+", "+TotvsDb.SC5.getTable(ControlServlet.getParams().getGpoemptotvs())+" where C5_NOTA = ''"
				+ " and C6_CLI <> '' and C6_TES = '511' and C6_NUM = C5_NUM and C5_EMISSAO >= ? and C6_PRODUTO = ? group by C6_PRODUTO order by C6_PRODUTO";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			if(con!=null){			
				
				for(ItemOrcamento i : lista){
					
					pstm = con.prepareStatement(sql);
					pstm.setDate(1, inicio);
					pstm.setString(2, i.getProduto().getCodigo());
					rs = pstm.executeQuery();					
					int venda = rs.getInt("QTDE");
					pstm.close();
					rs.close();
					
					pstm = con.prepareStatement(sql2);
					pstm.setDate(1, inicio);
					pstm.setString(2, i.getProduto().getCodigo());
					rs = pstm.executeQuery();
					int reserva = rs.getInt("QTDE");
					pstm.close();
					rs.close();
					con.close();
					
					i.getProduto().setQtdvendida(venda+reserva);
				}				
			}else{
				for(ItemOrcamento i : lista){
					i.getProduto().setQtdvendida(0);
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public static void setEstoque(List<ItemOrcamento> lista) throws SQLException{
		
		/*Iterator<ItemOrcamento> itr = lista.iterator();		
		while(itr.hasNext()) {
			setEstoque(itr.next());
		}
		*/
		for(ItemOrcamento i:lista) {
			setEstoque(i);
		}
	}
	
	public static void setEstoque(ItemOrcamento i) throws SQLException {
		setEstoque(i.getProduto());
	}
	
	public boolean checkProduto(Produto produto) {
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select * from produto where codigo = '"+produto.getCodigo()+"'";
		boolean flag = false;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) {
				flag = true;
			}
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return flag;
	}
	
	public int importarCadastroProdutos(ArrayList<Produto> itens, String acao) throws SQLException{
		Connection con = null;
		List<Produto> produtos = new ArrayList<>();
		
		String sql = "insert into produto (codigo, descricao, preco, codbar, obs, serie, autor, "
				+ "nivel, familia, colecao, disciplina, editora, marketshare, paginas, lancto, peso, status)"
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String sql1 = "update produto set descricao = ?, preco = ?, codbar = ?, obs = ?, serie = ?, "
				+ "autor = ?, nivel = ?, familia = ?, colecao = ?, disciplina = ?, editora = ?, inativo = ?, "
				+ "marketshare = ?, grupo = ?, paginas = ?, lancto = ?, peso = ?, status = ?, imagem = ? where codigo = ?";
		
		int contador = 0;
		
		try {
				PreparedStatement pstm;
				if(acao.equalsIgnoreCase("insert")){
					
					for(Produto produto:itens) {
						if(!checkProduto(produto)) {
							produtos.add(produto);
						}
					}
					con = ConnectionFactory.getInstance().getMySqlConnection();
					con.setAutoCommit(false);
					
					pstm  = con.prepareStatement(sql);
					
					for(Produto t : produtos){
						pstm.setString(1, t.getCodigo());
						pstm.setString(2, t.getDescricao());
						pstm.setFloat(3, t.getPreco());
						pstm.setString(4, t.getCodbar());
						pstm.setString(5, t.getObs());
						pstm.setString(6, t.getSerie());
						pstm.setString(7, t.getAutor());
						pstm.setString(8, t.getNivel());
						pstm.setString(9, t.getFamilia());
						pstm.setString(10, t.getColecao());
						pstm.setString(11, t.getDisciplina());
						pstm.setString(12, t.getEditora());
						pstm.setInt(13, t.getMarketshare());
						pstm.setInt(14, t.getPaginas());
						pstm.setString(15, t.getLancto());
						pstm.setFloat(16, t.getPeso());
						pstm.setString(17, t.getStatus());						
						pstm.addBatch();				
						contador++;
						if(contador%100 == 0){
							pstm.executeBatch();
						}
					}
					pstm.executeBatch();
					con.commit();
					
					pstm.close();
				}
				else{
					con = ConnectionFactory.getInstance().getMySqlConnection();
					con.setAutoCommit(false);
					pstm = con.prepareStatement(sql1);
					for(Produto t : itens){				
						pstm.setString(1, t.getDescricao());
						pstm.setFloat(2, t.getPreco());
						pstm.setString(3, t.getCodbar());
						pstm.setString(4, t.getObs());
						pstm.setString(5, t.getSerie());
						pstm.setString(6, t.getAutor());
						pstm.setString(7, t.getNivel());
						pstm.setString(8, t.getFamilia());
						pstm.setString(9, t.getColecao());
						pstm.setString(10, t.getDisciplina());
						pstm.setString(11, t.getEditora());
						pstm.setInt(12, t.getAtivo());
						pstm.setInt(13, t.getMarketshare());
						pstm.setString(14, t.getGrupo());
						pstm.setInt(15, t.getPaginas());
						pstm.setString(16, t.getLancto());
						pstm.setFloat(17, t.getPeso());
						pstm.setString(18, t.getStatus());
						pstm.setString(19, t.getImagem());
						pstm.setString(20, t.getCodigo());
						pstm.addBatch();				
						contador++;
						if(contador%100 == 0){
							pstm.executeBatch();
						}
					}
					pstm.executeBatch();
					con.commit();
					
					pstm.close();
				}
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			con.rollback();
			return 0;
		}finally{
			if(con != null) con.close();
		}
		
		return contador;
	}
	
	public int atualizarPrecos(ArrayList<Produto> itens) throws SQLException{
		int contador = 0;
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "update produto set preco = ? where codigo = ?";
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			for(Produto t : itens){
				pstm.setFloat(1, t.getPreco());
				pstm.setString(2, t.getCodigo());
				pstm.addBatch();
				contador++;
				if(contador%1000 == 0){
					pstm.executeBatch();
				}				
			}
			pstm.executeBatch();
			pstm.close();			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return 0;
		}finally{
			con.close();
		}
		
		return contador;
	}
	

	public boolean alterarProduto(Produto p) throws SQLException{
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "update produto set descricao=?, preco=?, autor=?, codbar=?, serie=?,"
				+ " nivel=?, colecao=?, familia=?, obs=?, disciplina=?, editora=?, inativo=?,"
				+ " imagem=?, marketshare = ?, grupo = ?, paginas = ?, lancto = ?, peso = ?,"
				+ " status = ? where codigo=?";
						
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, p.getDescricao());
			pstm.setFloat(2, p.getPreco());
			pstm.setString(3, p.getAutor());
			pstm.setString(4, p.getCodbar());
			pstm.setString(5, p.getSerie());
			pstm.setString(6, p.getNivel());
			pstm.setString(7, p.getColecao());
			pstm.setString(8, p.getFamilia());
			pstm.setString(9, p.getObs());
			pstm.setString(10, p.getDisciplina());
			pstm.setString(11, p.getEditora());
			pstm.setInt(12, p.getAtivo());
			pstm.setString(13, p.getImagem());
			pstm.setInt(14, p.getMarketshare());
			pstm.setString(15, p.getGrupo());
			pstm.setInt(16, p.getPaginas());
			pstm.setString(17, p.getLancto());
			pstm.setFloat(18, p.getPeso());
			pstm.setString(19, p.getStatus());
			pstm.setString(20, p.getCodigo());
			pstm.executeUpdate();
			
			pstm.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally{
			con.close();
		}		
		
		return true;
	}

	public boolean ativarProduto(Produto p) throws SQLException {
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "update produto set inativo=0 where codigo=?";
						
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, p.getCodigo());
			pstm.executeUpdate();
			
			pstm.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally{
			con.close();
		}		
		
		return true;
	}
	
	
	public void exportaPrecos(HttpServletResponse response, String separador, String filtro, String txtdescricao) throws SQLException{
		
		ServletOutputStream fout = null;
		String sql = "select codigo, descricao, preco, autor, codbar, obs, status, colecao, editora, disciplina, serie, familia, nivel, imagem from produto";
		String[] terms = null;
		boolean flag = false;
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		if(filtro.equalsIgnoreCase("sim")){
			flag = true;
			terms = txtdescricao.split(" ");
			
			String query = "(";
			
			for(String s : terms) {
				query += "(codigo like '%"+s+"%' or descricao like '%"+s+"%'"
						+ " or obs like '%"+s+"%' or autor like '%"+s+"%'"
						+ " or colecao like '%"+s+"%' or "
							+ "disciplina like '%"+s+"%' or "
								+ "familia like '%"+s+"%' or "
										+ "nivel like '%"+s+"%' or "
											+ "serie like '%"+s+"%' or "
												+ "editora like '%"+s+"%') or ";
			}

			int len = query.length() - 4;
			
			query = query.substring(0, len) + ")";						
			
			sql = "select codigo, descricao, preco, autor, codbar, obs, status, colecao, editora, disciplina, serie, familia, nivel, imagem from produto where "
					+ query +" and inativo = 0 order by codigo";
			
			
			
		}
		
		try {
						
			fout = response.getOutputStream(); 

			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			if(separador.equalsIgnoreCase("espaco")){
				response.setContentType("text/plain;charset=UTF-8");
		    	response.setHeader("Content-Disposition", "attachment; filename=preco.txt");
		    	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
	            while(rs.next()){
					String codigo, descr, obs, codb, editora, status, discip, autor;
					float preco;
					codigo = rs.getString("codigo");
					descr = rs.getString("descricao");
					discip = rs.getString("disciplina");
					autor = rs.getString("autor");
					preco =  rs.getFloat("preco");
					obs = rs.getString("obs");
					status = rs.getString("status");
					codb = rs.getString("codbar");
					editora = rs.getString("editora");
					
					String linha = codigo + " " + descr + " " + codb + " " + editora + " " + status + " " + obs + " " + discip + " " + autor;
					int index = 0;
					
					if(flag) {
						index = terms.length;
						for(String term : terms) {
							if(containsIgnoreAccents(linha, term)){
								index--;
							}
						}						
					}
					
					if(index == 0) {
						
		            	int n = 27;
		                String linhaarq = "";
		                String precod = String.format("%.2f", preco);
		                precod = precod.replace('.', ',');
		                n = n - (codigo.length() + precod.length());
		                char[] repetir = new char[n];
		                Arrays.fill(repetir, ' ');
		                linhaarq = codigo + new String(repetir) + precod;
		                linhaarq = linhaarq + "\r\n";
		                byte[] linhaq = linhaarq.getBytes();
		                fout.write(linhaq);
		            }	            	
	            }
			}else{
				response.setContentType("text/csv;vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment; filename=preco.csv");
				response.setHeader("Set-Cookie", "fileDownload=true; path=/");
	            while(rs.next()){
		            
	                String linhaarq = "";
	                String codigo = rs.getString("codigo");
	                float d = rs.getFloat("preco");
	                String preco = String.format("%.2f", d);
	                preco = preco.replace('.', ',');
	                linhaarq = codigo + ";" + preco;
	                linhaarq = linhaarq + "\n";
	                byte[] linha = linhaarq.getBytes();
	                fout.write(linha);
	            }				
			}
            fout.flush();
			fout.close();
			rs.close();
			stm.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			con.close();
		}
				
	}		


	public static void exportaProdutos(HttpServletResponse response, String ano, String filtro, String txtdescricao) throws SQLException{
		
		
		ServletOutputStream fout = null;
		
		String sql = "select codigo, descricao, codbar, serie, autor, nivel, status, obs, chave as chave_nivel,"
				+ " serie_descricao, chave_serie, familia, disciplina, chave_disciplina from nivel a"
				+ " inner join (select codigo, descricao, codbar, serie, autor, nivel, status, obs, serie_descricao,"
				+ " chave_serie, familia, disciplina, chave as chave_disciplina from disciplina c"
				+ " inner join (select a.codigo, a.descricao, a.codbar, a.serie, a.autor, a.nivel, a.status, a.obs,"
				+ " b.serie_descricao, b.chave as chave_serie, a.familia, a.disciplina from produto"
				+ " a inner join serie b on a.serie = b.serie and inativo = 0) d on c.nome = d.disciplina) f"
				+ " on f.nivel = a.nome;";

		String[] terms = null;
		boolean flag = false;

		if(filtro.equalsIgnoreCase("sim")){
			
			flag = true;
			terms = txtdescricao.split(" ");
			
			String query = "(";
			
			for(String s : terms) {
				query += "(a.codigo like '%"+s+"%' or a.descricao like '%"+s+"%'"
						+ " or a.obs like '%"+s+"%' or a.autor like '%"+s+"%'"
						+ " or a.colecao like '%"+s+"%' or "
							+ "a.disciplina like '%"+s+"%' or "
								+ "a.familia like '%"+s+"%' or "
										+ "a.nivel like '%"+s+"%' or "
											+ "a.serie like '%"+s+"%' or "
												+ "a.editora like '%"+s+"%') or ";
			}

			int len = query.length() - 4;
			
			query = query.substring(0, len) + ")";						
				
			sql = "select codigo, descricao, codbar, serie, autor, nivel, status, obs, chave as chave_nivel,"
					+ " serie_descricao, chave_serie, familia, disciplina, chave_disciplina from"
					+ " nivel a inner join (select codigo, descricao, codbar, serie, autor, nivel, status, obs,"
					+ " serie_descricao, chave_serie, familia, disciplina, chave as chave_disciplina"
					+ " from disciplina c inner join (select a.codigo, a.descricao, a.codbar, a.serie,"
					+ " a.autor, a.nivel, a.status, a.obs, b.serie_descricao, b.chave as chave_serie, a.familia, a.disciplina"
					+ " from produto a inner join serie b on a.serie = b.serie and "+query+" and inativo = 0) d"
							+ " on c.nome = d.disciplina) f on f.nivel = a.nome";			
			
		}
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		
		try {
						
			fout = response.getOutputStream(); 
			response.setContentType("text/plain;charset=Windows-1252");
	    	response.setHeader("Content-Disposition", "attachment; filename=produto.txt");
	    	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
	    	
			Statement stm = con.createStatement();
			ResultSet rs;
						
			rs = stm.executeQuery(sql);
	    	
            while(rs.next()){
            	
            	//Declarando as variaveis necessarias para gravacao do arquivo de texto
            	String codigo, descricao, codbarras, autor, disciplina, classificacao, status, obs,
            	famcml, nivel, serie, descdiscipl, descnivel, descserie, descfamcml, ncm;            	            	
            	
            	String linhaarq;
            	            	
            	//Cria um novo produto com os dados do banco
            	codigo = rs.getString("codigo").trim();
            	descricao = rs.getString("descricao").trim();
            	codbarras = rs.getString("codbar").trim();
            	autor = rs.getString("autor").trim();
            	descnivel = rs.getString("nivel").trim();
            	status = rs.getString("status");
            	obs = rs.getString("obs");
            	descfamcml = rs.getString("familia").trim();
            	descdiscipl = rs.getString("disciplina").trim();            	
            	disciplina = rs.getString("chave_disciplina").trim();
            	nivel = rs.getString("chave_nivel").trim();
            	serie = rs.getString("chave_serie").trim();
            	descserie = rs.getString("serie_descricao").trim();
            	int n = descfamcml.length();
            	if(n > 8) famcml = descfamcml.substring(0,8);
            	else famcml = descfamcml;
            	
            	classificacao = "49019900";
            	ncm = "49019900";


				String linhapesq = codigo + " " + descricao + " " + status + " " + obs + " " + autor;
				int index = 0;
				
				if(flag) {
					index = terms.length;
					for(String term : terms) {
						if(containsIgnoreAccents(linhapesq, term)){
							index--;
						}
					}						
				}
				
				if(index == 0) {
            	
                //Setando as variaveis com os tamanhos e espacos
                //Codigo
                n = 15;
                n = n - codigo.length();
                char[] repetir = new char[n];
                Arrays.fill(repetir, ' ');
                linhaarq = codigo + new String(repetir);
                
                //Descricao
                n = 55;
                n = n - descricao.length();
                if(n <= 0){
                	descricao = descricao.substring(0, 55);
                	linhaarq += descricao;
                }else{
	                repetir = new char[n];
	                Arrays.fill(repetir, ' ');
	                linhaarq += descricao + new String(repetir);
                }
                
                //Cod Barras
                n = 15;
                n = n - codbarras.length();
                repetir = new char[n];
                Arrays.fill(repetir, ' ');
                linhaarq += codbarras + new String(repetir);
                
                //Autor
                n = 30;
                n = n - autor.length();
                if(n <= 0){
                	autor = autor.substring(0, 30);
                	linhaarq += autor;
                }else{
                    repetir = new char[n];
                    Arrays.fill(repetir, ' ');
                    linhaarq += autor + new String(repetir);
                }                
                
                //N.Paginas
                linhaarq += "  0";
                
                //N.Edicao
                linhaarq += " 1";
                
                //Ano
                linhaarq += ano;
                
                //Disciplina
                linhaarq += disciplina.substring(0, 3);
                
                //Familia comercial
                n = 8 - famcml.length();
                if(n > 0){
	                repetir = new char[n];
	                Arrays.fill(repetir, ' ');
	                linhaarq += famcml + new String(repetir);
                }else{
                	linhaarq += famcml;
                }
                //Nivel
                linhaarq += nivel.substring(0, 2);
                
                //Serie
                linhaarq += serie.substring(0, 2);
                
                //Classificacao
                linhaarq += classificacao.substring(0, 8);
                
                //Descricao disciplina
                n = 55;
                n = n - descdiscipl.length();
                if(n <= 0){
                	descdiscipl = descdiscipl.substring(0, 55);
                	linhaarq += descdiscipl;
                }else{
	                repetir = new char[n];
	                Arrays.fill(repetir, ' ');
	                linhaarq += descdiscipl + new String(repetir);
                }
                
                //Descricao nivel
                n = 55;
                n = n - descnivel.length();
                if(n <= 0){
                	descnivel = descnivel.substring(0, 55);
                	linhaarq += descnivel;
                }else{
	                repetir = new char[n];
	                Arrays.fill(repetir, ' ');
	                linhaarq += descnivel + new String(repetir);
                }
                
                //Descricao serie
                n = 55;
                n = n - descserie.length();
                if(n <= 0){
                	descserie = descserie.substring(0, 55);
                	linhaarq += descserie;
                }else{
	                repetir = new char[n];
	                Arrays.fill(repetir, ' ');
	                linhaarq += descserie + new String(repetir);
                }

                //Descricao familia
                n = 55;
                n = n - descfamcml.length();
                if(n <= 0){
                	descfamcml = descfamcml.substring(0, 55);
                	linhaarq += descfamcml;
                }else{
	                repetir = new char[n];
	                Arrays.fill(repetir, ' ');
	                linhaarq += descfamcml + new String(repetir);
                }
                
                //NCM
                linhaarq += ncm.substring(0, 8);
                
                
                linhaarq = linhaarq + "\r\n";
                byte[] linha = linhaarq.getBytes();
                fout.write(linha);
                
				}
            }
			rs.close();
			stm.close();
			
	        fout.flush();
			fout.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			con.close();
		}								
	}		


	public static List<Adocao> consultarAdocoes(Produto produto, String ano, Usuario user){
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		String sql = "select e.idadocao, e.idescola, f.nome, f.setor, f.endereco, f.bairro, f. municipio, f.uf, e.serie, e.ano, e.quantidade"
				+ " from (select c.idadocao, c.idescola, c.serie, c.ano, d.quantidade from (select"
				+ " a.idadocao, a.idescola, a.serie, a.ano from adocao a inner join item_adocao b"
				+ " on a.idadocao = b.idadocao and b.codigo = ?) as c inner join escola_serie_aluno"
				+ " d on c.idescola = d.idescola and c.serie = d.serie and c.ano = d.ano and c.ano = ?)"
				+ " as e inner join escola f on e.idescola = f.idescola";
		
		List<Adocao> lista = new ArrayList<>();
		
		try {
			
			if(user.getCargo() == 3) {
				sql = "select e.idadocao, e.idescola, f.nome, f.setor, f.endereco, f.bairro, f. municipio, f.uf, e.serie, e.ano, e.quantidade"
						+ " from (select c.idadocao, c.idescola, c.serie, c.ano, d.quantidade from (select"
						+ " a.idadocao, a.idescola, a.serie, a.ano from adocao a inner join item_adocao b"
						+ " on a.idadocao = b.idadocao and b.codigo = ?) as c inner join escola_serie_aluno"
						+ " d on c.idescola = d.idescola and c.serie = d.serie and c.ano = d.ano and c.ano = ?)"
						+ " as e inner join escola f on e.idescola = f.idescola and f.setor = ?";				
			}
			
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, produto.getCodigo());
			pstm.setString(2, ano);
			if(user.getCargo() == 3) {
				pstm.setInt(3, user.getSetor());
			}
			
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next()){
				Adocao a = new Adocao();
				Escola e = new Escola();
				e.setId(rs.getInt("idescola"));
				e.setNome(rs.getString("nome"));
				e.setEndereco(rs.getString("endereco"));
				e.setBairro(rs.getString("bairro"));
				e.setMunicipio(rs.getString("municipio"));
				e.setUf(rs.getString("uf"));
				e.setSetor(rs.getInt("setor"));
				a.setIdadocao(rs.getInt("idadocao"));
				a.setIdescola(e.getId());
				a.setNomeescola(e.getNome());
				a.setEscola(e);
				a.setAno(ano);
				a.setSerie(rs.getString("serie"));
				a.setQtde(rs.getInt("quantidade"));
				a.setVendedor(DAOUsuario.getVendedor(e.getSetor()));
				lista.add(a);
			}
			rs.close();
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}
	
	
	public static List<DoacaoRelat> consultarDoacoes(Produto produto, String ano, Usuario user){

		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		int anoant = Integer.parseInt(ano)-1;
		
		Date ini = convertSQLStringToDate((anoant)+"0701");
		Date fim = convertSQLStringToDate(ano+"0630");

		String sql = "select a.iddoacao, a.emissao, d.nome as vendedor, c.nome as escola, "
				+ "b.nomeprofessor as professor, qtde from (((doacao a natural join item_doacao) "
				+ "join escola c) join professor b) join usuario d on a.idusuario = d.id "
				+ "and a.idescola = c.idescola and a.idprofessor = b.idprofessor "
				+ "and emissao between ? and ? and codigo = ?";
		
		if(user.getCargo() == 3) {
			sql = "select a.iddoacao, a.emissao, d.nome as vendedor, c.nome as escola, "
					+ "b.nomeprofessor as professor, qtde from (((doacao a natural join item_doacao) "
					+ "join escola c) join professor b) join usuario d on a.idusuario = d.id "
					+ "and a.idescola = c.idescola and a.idprofessor = b.idprofessor "
					+ "and emissao between ? and ? and codigo = ? and c.setor = ?";			
		}
		
		List<DoacaoRelat> lista = new ArrayList<>();
		
		try {
			
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setDate(1, ini);
			pstm.setDate(2, fim);
			pstm.setString(3, produto.getCodigo());
			if(user.getCargo() == 3) {
				pstm.setInt(4, user.getSetor());
			}
			
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next()){
				DoacaoRelat a = new DoacaoRelat();
				a.setIddoacao(rs.getInt("iddoacao"));
				a.setEmissao(rs.getDate("emissao"));
				a.setVendedor(rs.getString("vendedor"));
				a.setEscola(rs.getString("escola"));
				a.setProfessor(rs.getString("professor"));
				a.setQtde(rs.getInt("qtde"));
				
				lista.add(a);
			}
			rs.close();
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return lista;
	}
	
	public ConsProduto consultar(Produto produto) throws SQLException{
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		String sql = "select idpedido, dataped, produto.codigo, qtde, previsao, observacao from produto left join pedido"
				+ " natural join item_pedido on produto.codigo = item_pedido.codigo"
				+ " having produto.codigo = '"+produto.getCodigo()+"' order by idpedido desc limit 7";
		String sql1 = "select codigo, nome, municipio, serie, qtde, ano from adocao_produto"
				+ " natural join adocao natural join escola natural join (select distinct ano from adocao order by ano desc limit 1) as t"
				+ " where codigo = '"+produto.getCodigo()+"' and ano = t.ano order by nome";
		
		ArrayList<Pedido> pedidos = new ArrayList<>();
		ArrayList<Adocao> adocoes = new ArrayList<>();
		ConsProduto cp = new ConsProduto();
		recarrega(produto);
		cp.setItem(produto);
		
		try {
			
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			Adocao adocao = null;
			Pedido pedido = null;
			ArrayList<ItemPedido> itenspedido = null;
			
			while(rs.next()){
								
				if(rs.getInt("qtde")>0){
					pedido = new Pedido();
					itenspedido = new ArrayList<>();
					ItemPedido itempedido = new ItemPedido();
					itempedido.setQtdpedida(rs.getInt("qtde"));
					itempedido.setPrevisao(rs.getDate("previsao"));
					itempedido.setItem(produto);
					pedido.setIdpedido(rs.getInt("idpedido"));
					pedido.setData(rs.getDate("dataped"));
					setNotasQtdeRecebida(pedido, itempedido);
					itempedido.refazTotalQtdChegou();
					itenspedido.add(itempedido);
					pedido.setItens(itenspedido);
					pedidos.add(pedido);
				}
				
			}
			rs.close();
			rs = stm.executeQuery(sql1);
			while(rs.next()){
				adocao = new Adocao();
				adocao.setAno(rs.getString("ano"));
				adocao.setNomeescola(rs.getString("nome"));
				adocao.setQtde(rs.getInt("qtde"));
				adocao.setSerie(rs.getString("serie"));
				adocao.setMunicipio(rs.getString("municipio"));
				adocoes.add(adocao);
			}
			
			rs.close();
			stm.close();
			
			cp.setPedidos(pedidos);
			cp.setAdocoes(adocoes);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			con.close();
		}
		
		return cp;
	}
	
	public void setNotasQtdeRecebida(Pedido pedido, ItemPedido itempedido){
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select idnota, emissao, uf, cnpjemit, qtde from notafiscal natural join"
				+ " (notapedido natural join item_notafiscal) where idpedido = ? and codigo = ?";
		
		List<NotaFiscal> notas = new ArrayList<>();
	
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, pedido.getIdpedido());
			pstm.setString(2, itempedido.getItem().getCodigo());
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next()){				
				NotaFiscal n = new NotaFiscal();
				n.setIdnota(rs.getString("idnota"));
				n.setEmissao(rs.getDate("emissao"));
				n.setCnpjemit(rs.getString("cnpjemit"));
				n.setUF(rs.getString("uf"));
				setDataChegadaProduto(n);
				
				float quantidade;
				quantidade = rs.getFloat("qtde");
				n.setQtdtotal(quantidade);
				notas.add(n);
			}
			
			itempedido.setNotas(notas);
			
			rs.close();
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		

	public static void setDataChegadaProduto(NotaFiscal nota){

		if(nota.getChegada() == null) {
			Connection con = ConnectionFactory.getInstance().getSqlConnection();
			String emissao = nota.getEmissao()+"";
			
			String[] s = emissao.split("-");
			emissao = s[0]+s[1]+s[2];
			
			String sql = "SELECT F1_DOC, F1_DTDIGIT FROM "+TotvsDb.SF1.getTable(ControlServlet.getParams().getGpoemptotvs())+""
					+ " WHERE F1_DOC LIKE '%"+nota.getIdnota()+"' AND F1_DTDIGIT > '"+emissao+"'";
			
			if(con != null){
				try {
					Statement stm = con.createStatement();			
					ResultSet rs = stm.executeQuery(sql);
					if(rs.next()){
						String trDate = rs.getString("F1_DTDIGIT");
						//dt = dt.substring(0, 4)+"-"+dt.substring(4, 2) + "-" + dt.substring(6, 2);
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						java.util.Date tradeDate = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(trDate);
						String dt = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(tradeDate);
						Date date = null;		
						date = new Date(df.parse(dt).getTime());				
						nota.setChegada(date);
					}
					stm.close();
					rs.close();
					con.close();
			
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public Produto dadosProduto(String codigo) throws SQLException{
		Produto p = new Produto();
		p.setCodigo(codigo);
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select * from produto where codigo = ?";
		
		try {
			
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, codigo);
			ResultSet rs = pstm.executeQuery();
			if(rs.next()){
				p.setDescricao(rs.getString("descricao"));
				p.setAutor(rs.getString("autor"));
				p.setPreco(rs.getFloat("preco"));
				p.setCodbar(rs.getString("codbar"));
				p.setSerie(rs.getString("serie"));
				p.setColecao(rs.getString("colecao"));
				p.setFamilia(rs.getString("familia"));
				p.setNivel(rs.getString("nivel"));
				p.setObs(rs.getString("obs"));
				p.setDisciplina(rs.getString("disciplina"));
				p.setEditora(rs.getString("editora"));
				p.setAtivo(rs.getInt("inativo"));
				p.setImagem(rs.getString("imagem"));
				p.setMarketshare(rs.getInt("marketshare"));
				p.setGrupo(rs.getString("grupo"));
				p.setPaginas(rs.getInt("paginas"));
				p.setLancto(rs.getString("lancto"));
				p.setPeso(rs.getFloat("peso"));
				p.setStatus(rs.getString("status"));
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			con.close();
		}
		
		return p;
	}
		
	public static Produto getProdutoFromCodBar(String codbar, String codbarant) throws SQLException {
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select * from produto where codbar = ? or codbar = ?";		
		Produto produto = null;
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, codbar);
			pstm.setString(2, codbarant);
			ResultSet rs = pstm.executeQuery();
			if(rs.next()) {
				produto = new Produto();
				produto.setCodigo(rs.getString("codigo"));
				produto.setDescricao(rs.getString("descricao"));
			}
			rs.close();
			pstm.close();
			
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			if(con != null) con.close();
		}
		
		return produto;
	}
	
	public static boolean recarrega(Produto produto){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select * from produto where codigo = ?";
		boolean flag = true;
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, produto.getCodigo());
			ResultSet rs = pstm.executeQuery();
			if(rs.next()){
				produto.setDescricao(rs.getString("descricao"));
				produto.setPreco(rs.getFloat("preco"));
				produto.setAutor(rs.getString("autor"));
				produto.setSerie(rs.getString("serie"));
				produto.setCodbar(rs.getString("codbar"));
				produto.setObs(rs.getString("obs"));
				produto.setNivel(rs.getString("nivel"));
				produto.setFamilia(rs.getString("familia"));
				produto.setColecao(rs.getString("colecao"));
				produto.setDisciplina(rs.getString("disciplina"));
				produto.setEditora(rs.getString("editora"));
				produto.setAtivo(rs.getInt("inativo"));
				produto.setImagem(rs.getString("imagem"));
				produto.setMarketshare(rs.getInt("marketshare"));
				produto.setGrupo(rs.getString("grupo"));
				produto.setPaginas(rs.getInt("paginas"));
				produto.setLancto(rs.getString("lancto"));
				produto.setPeso(rs.getFloat("peso"));
				produto.setStatus(rs.getString("status"));				
			}else{
				flag = false;
			}
			rs.close();
			pstm.close();
			
			Date agora = new Date(System.currentTimeMillis());
			
			sql = "select codigo, max(previsao) as prev, observacao from"
					+ " item_pedido where cancelado = 0 and codigo = ? and previsao >= ? group by codigo, observacao";
			
			pstm = con.prepareStatement(sql);
			pstm.setString(1, produto.getCodigo());
			pstm.setDate(2, agora);
			rs = pstm.executeQuery();
			
			if(rs.next()){
				produto.setPrevisao(rs.getDate("prev"));
				produto.setObspedido(rs.getString("observacao"));
			}
			rs.close();
			pstm.close();			
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	
	public static boolean setEstoque(ItemPedCliente item) {
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select codigo, max(previsao) as prev, observacao from"
				+ " item_pedido where cancelado = 0 and codigo = ? and previsao >= ? group by codigo, observacao";
		boolean flag = true;
		Produto produto = item.getItem();
		try {
			
			setEstoque(produto);
			
			PreparedStatement pstm = con.prepareStatement(sql);			
			Date agora = new Date(System.currentTimeMillis());
					
			pstm = con.prepareStatement(sql);
			pstm.setString(1, produto.getCodigo());
			pstm.setDate(2, agora);
			ResultSet rs = pstm.executeQuery();
			
			if(rs.next()){
				produto.setPrevisao(rs.getDate("prev"));
				produto.setObspedido(rs.getString("observacao"));
			}

			item.setItem(produto);
			
			rs.close();
			pstm.close();			
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		}
		
		return flag;
	}
	
	
	
	
	public List<Produto> listaTudo(){
		List<Produto> lista = new ArrayList<>();
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select * from produto";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()){
				String codigo, descricao;
				float preco;
				codigo = rs.getString("codigo");
				descricao = rs.getString("descricao");
				preco = rs.getFloat("preco");
				Produto p = new Produto();
				p.setCodigo(codigo);
				p.setDescricao(descricao);
				p.setPreco(preco);
				lista.add(p);
			}
			rs.close();
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return lista;
	}
	
	
	public void produtoToExcel(HttpServletResponse response){

		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		String sql = "select codigo, descricao, preco, codbar, autor, nivel, familia, colecao,"
				+ " editora, marketshare, paginas, lancto, peso, status"
				+ " from produto order by familia, codigo, nivel";	
		
		
	    //String path = "c:\\UploadedFiles\\Tabela.xls";    
	        
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			ResultSetToExcel resultSetToExcel = new ResultSetToExcel(rs, new ResultSetToExcel.FormatType[] { ResultSetToExcel.FormatType.TEXT,
					ResultSetToExcel.FormatType.TEXT, ResultSetToExcel.FormatType.FLOAT, ResultSetToExcel.FormatType.TEXT, ResultSetToExcel.FormatType.TEXT,
					ResultSetToExcel.FormatType.TEXT, ResultSetToExcel.FormatType.TEXT, ResultSetToExcel.FormatType.TEXT},"tabela");
		    
			resultSetToExcel.generate(response);
			
			pstm.close();
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//return path;		
	}
	
	public static List<ItemTabela> getTabelaPrecos(Usuario usuario, String estoque, String inativo) throws SQLException{
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		List<ItemTabela> lista = new ArrayList<>();
		
		String sql = "";


		if(usuario.getCargo() == 1){
			if(inativo.equalsIgnoreCase("sim")) {
				sql = "select * from (select codigo, descricao, preco, status, obs, codbar, autor, nivel, disciplina, familia, serie,"
						+ " colecao, inativo from produto order by familia, nivel) as t";												
			}else {
				sql = "select * from (select codigo, descricao, preco, status, obs, codbar, autor, nivel, disciplina, familia, serie,"
						+ " colecao, inativo from produto where inativo = 0 order by familia, nivel) as t";																	
			}
		}else if(usuario.getCargo() == 4 || usuario.getId() == 2){
			sql = "select * from (select codigo, descricao, preco, status, obs, codbar, autor, nivel, disciplina, familia, serie,"
					+ " colecao, inativo from produto where inativo = 0 and (status <> 'descontinuado' or status <> 'reduzido')"
					+ " order by familia, nivel) as t having familia <> '12-SEFTD' and familia <> '12-SEPAR'";			
		}else{
			sql = "select * from (select codigo, descricao, preco, status, obs, codbar, autor, nivel, disciplina, familia, serie,"
					+ " colecao, inativo from produto where inativo = 0 order by familia, nivel) as t";															
		}
		
		try {
			Statement stm = con.createStatement();
			
			ResultSet rs = stm.executeQuery(sql);
			
			while(rs.next()){
				Produto p = new Produto();
				ItemTabela item = new ItemTabela();
				p.setCodigo(rs.getString("codigo"));
				p.setDescricao(rs.getString("descricao"));
				p.setPreco(rs.getFloat("preco"));
				p.setStatus(rs.getString("status"));
				p.setObs(rs.getString("obs"));
				p.setCodbar(rs.getString("codbar"));
				p.setAutor(rs.getString("autor"));
				p.setNivel(rs.getString("nivel"));
				p.setFamilia(rs.getString("familia"));
				p.setColecao(rs.getString("colecao"));
				p.setSerie(rs.getString("serie"));
				p.setAtivo(rs.getInt("inativo"));
				item.setItem(p);
				lista.add(item);
			}
				
			rs.close();
			stm.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(usuario.getCargo() == 1){
			if(estoque.equalsIgnoreCase("sim")) {
				for(ItemTabela i : lista){
					setEstoque(i.getItem());
				}					
			}
		}
		
		Collections.sort(lista);
		
		return lista;
	}
	
	public static EnumList getItensCombosProduto() throws SQLException{
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		EnumList lista = new EnumList();
		
		String sqlEditora = "select * from editora order by marca";
		String sqlFamilia = "select * from familia order by nome";
		String sqlNivel = "select * from nivel order by nome";
		String sqlDisciplina = "select * from disciplina order by nome";
		String sqlSerie = "select * from serie order by serie_descricao";
		
		Statement stm = con.createStatement(); 
		ResultSet rs = stm.executeQuery(sqlEditora);
		while(rs.next()){
			lista.getEditora().add(rs.getString("marca"));
		}
		rs.close();
		rs = stm.executeQuery(sqlFamilia);
		while(rs.next()){
			lista.getFamilia().add(rs.getString("nome"));
		}
		rs.close();
		rs = stm.executeQuery(sqlNivel);
		while(rs.next()){
			lista.getNivel().add(rs.getString("nome"));
		}
		rs.close();
		rs = stm.executeQuery(sqlDisciplina);
		while(rs.next()){
			lista.getDisciplina().add(rs.getString("nome"));
		}
		rs.close();
		rs = stm.executeQuery(sqlSerie);
		while(rs.next()){
			lista.getSerie().add(rs.getString("serie"));
		}
		rs.close();
		stm.close();
		con.close();
		
		return lista;
	}

	
	public static List<Kardex> getKardex(String codigo, String ano, String filial){
		
		Connection con = ConnectionFactory.getInstance().getSqlConnection();
		int anoant = Integer.parseInt(ano)-1;
		
		Date ini = convertSQLStringToDate((anoant)+"0701");
		Date fim = convertSQLStringToDate(ano+"0630");
		
		List<Kardex> lista = new ArrayList<>();
		
		String sql = "SELECT C5_NUM, C5_NOTA, C5_SERIE, C5_CLIENTE, A1_NOME,"
				+ " A1_MUN, C5_EMISSAO, C6_PRODUTO, C6_QTDVEN, C6_TES"
					  +" FROM "+TotvsDb.SA1.getTable(ControlServlet.getParams().getGpoemptotvs())+" INNER JOIN"
					  +" (SELECT C5_NUM, C5_NOTA, C5_SERIE, C5_CLIENTE, C5_EMISSAO, C6_PRODUTO, C6_QTDVEN, C6_TES"
					  +" FROM  "+TotvsDb.SC5.getTable(ControlServlet.getParams().getGpoemptotvs())+" INNER JOIN  "+TotvsDb.SC6.getTable(ControlServlet.getParams().getGpoemptotvs())
					  +" ON C5_NUM = C6_NUM"
					  +" AND C6_FILIAL = "+filial
					  +" AND C6_PRODUTO = ?"
					  +" AND (C6_TES = '511' OR C6_TES = '901')"
					  +" AND C5_EMISSAO BETWEEN ? AND ?"
					  +" AND "+TotvsDb.SC6.getTable(ControlServlet.getParams().getGpoemptotvs())+".D_E_L_E_T_ <> '*') AS W"
					  +" ON C5_CLIENTE = A1_COD"
					  +" ORDER BY C5_EMISSAO DESC";
		if(con != null){
			try {
				
				Kardex k = new Kardex();
				String dataemissao = ano+"0101";
				k.setEmissao(convertSQLStringToDate(dataemissao));
				setKardexCupom(k, codigo, filial);
				lista.add(k);

				PreparedStatement pstm = con.prepareStatement(sql);
				pstm.setString(1, codigo);
				pstm.setDate(2, ini);
				pstm.setDate(3, fim);
				ResultSet rs = pstm.executeQuery();
				while(rs.next()){
					k = new Kardex();
					dataemissao = rs.getString("C5_EMISSAO");
					k.setFilial(filial);
					k.setEmissao(convertSQLStringToDate(dataemissao));
					k.setNumero(rs.getString("C5_NUM"));
					k.setNota(rs.getString("C5_NOTA"));
					k.setSerie(rs.getString("C5_SERIE"));
					k.setNome(rs.getString("A1_NOME"));
					k.setMunicipio(rs.getString("A1_MUN"));
					k.setCodigo(rs.getString("C5_CLIENTE"));
					k.setQtde(rs.getInt("C6_QTDVEN"));
					lista.add(k);
				}
				rs.close();
				pstm.close();
				con.close();
								
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return lista;
	}
	
	
	public static void setKardexCupom(Kardex k, String produto, String filial){

		Connection con = ConnectionFactory.getInstance().getSqlConnection();
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(k.getEmissao());
		int ano = calendar.get(Calendar.YEAR);
		
		String sql = "SELECT D2_CLIENTE, SUM(D2_QUANT) AS QTDE "
				+ "FROM "+TotvsDb.SD2.getTable(ControlServlet.getParams().getGpoemptotvs())+" WHERE (D2_TES = '501' OR D2_TES = '511') "
				+ "AND D2_CLIENTE = '000001' AND YEAR(D2_EMISSAO) = ? "
				+ "AND D2_COD = ? AND D_E_L_E_T_ <> '*' AND D2_FILIAL = "+filial
				+ " GROUP BY D2_CLIENTE";
		if(con != null){
			try {
				PreparedStatement pstm = con.prepareStatement(sql);
				pstm.setString(1, ano+"");
				pstm.setString(2, produto);
				ResultSet rs = pstm.executeQuery();
				if(rs.next()){
					k.setFilial(filial);
					k.setNumero("XXXXXX");
					k.setNota("CUPOM");
					k.setSerie("CO");
					k.setNome("VENDAS COM CUPOM FISCAL");
					k.setMunicipio("FILIAL "+filial);
					k.setCodigo(rs.getString("D2_CLIENTE"));
					k.setQtde(rs.getInt("QTDE"));
				}
				rs.close();
				pstm.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public static Orcamento getDetalheKardex(String filial, String numero, String codigo, String nome){

		Connection con = ConnectionFactory.getInstance().getSqlConnection();
		
		Orcamento orcam = new Orcamento();
		Escola escola = new Escola();
		escola.setNome(nome);
		orcam.setEscola(escola);
		orcam.setSerie(codigo);
		orcam.setAno(numero);
		List<ItemOrcamento> lista = new ArrayList<>();
		
		String sql =  "SELECT C6_NUM, C6_PRODUTO, B1_DESC, C6_QTDVEN, C6_PRCVEN, C6_PRUNIT, C6_TES"
					  +" FROM  "+TotvsDb.SC6.getTable(ControlServlet.getParams().getGpoemptotvs())+" INNER JOIN  "+TotvsDb.SB1.getTable(ControlServlet.getParams().getGpoemptotvs())
					  +" ON C6_PRODUTO = B1_COD"
					  +" AND C6_NUM = ? AND C6_FILIAL = "+filial;
		
		if(con!=null){
			ItemOrcamento item = new ItemOrcamento();
			try {
				PreparedStatement pstm = con.prepareStatement(sql);
				pstm.setString(1, numero);
				ResultSet rs = pstm.executeQuery();
				while(rs.next()){
					item = new ItemOrcamento();
					Produto p = new Produto();
					p.setCodigo(rs.getString("C6_PRODUTO"));
					p.setDescricao(rs.getString("B1_DESC"));
					p.setPreco(rs.getFloat("C6_PRUNIT"));
					item.setProduto(p);
					item.setPrecoliquido(rs.getDouble("C6_PRCVEN"));
					item.setQuantidade(rs.getInt("C6_QTDVEN"));
					item.setTes(rs.getString("C6_TES"));
					lista.add(item);
				}
				Collections.sort(lista);
				orcam.setItens(lista);
				double pl = item.getPrecoliquido();
				double pb = item.getProduto().getPreco();
				double taxa = (pb - pl)/pb;
				orcam.setDesconto(taxa);
				orcam.refazTotal();
				rs.close();
				pstm.close();
				con.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}						
		}		
		
		return orcam;
	}
	
	
	public static Date convertSQLStringToDate(String dt) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date date = null;		
		
		try {
			date = new Date(df.parse(dt).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return date;
	}

	public static void setSaidas(vendasDoProduto p, String ano){
		
		Connection con = ConnectionFactory.getInstance().getSqlConnection();
		
		String sql = "SELECT C5_CLIENTE, A1_NOME, C6_PRODUTO, VENDA FROM"
				+ TotvsDb.SA1.getTable(ControlServlet.getParams().getGpoemptotvs())+" INNER JOIN (SELECT C5_CLIENTE,"
				+ " C6_PRODUTO, SUM(C6_QTDVEN) AS VENDA FROM "+ TotvsDb.SC5.getTable(ControlServlet.getParams().getGpoemptotvs())
				+ " INNER JOIN "+TotvsDb.SC6.getTable(ControlServlet.getParams().getGpoemptotvs())+" ON C5_NUM = C6_NUM AND C5_FILIAL = C6_FILIAL"
				+ " AND C5_FILIAL = '01' AND YEAR(C5_EMISSAO) = ? AND (C6_TES = '511' OR C6_TES = '901') AND"
				+ " C6_PRODUTO = ? AND "+TotvsDb.SC6.getTable(ControlServlet.getParams().getGpoemptotvs())+".D_E_L_E_T_ <> '*' GROUP BY C5_CLIENTE,"
				+ " C6_PRODUTO) AS T ON A1_COD = C5_CLIENTE";
	
		if(con != null){
			
			try {
				PreparedStatement pstm = con.prepareStatement(sql);
				pstm.setString(1, p.getAno());
				pstm.setString(2, p.getProduto().getCodigo());
				ResultSet rs = pstm.executeQuery();
				
				while(rs.next()){
					ItemVendasDoProduto i = new ItemVendasDoProduto();
					i.setIdcliente(rs.getString("C5_CLIENTE"));
					i.setCliente(rs.getString("A1_NOME"));
					i.setQuantidade(rs.getInt("VENDA"));
					p.getVendas().add(i);
				}
				rs.close();
				pstm.close();
				con.close();
				
				p.refazTotal();				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}					
			
		}
		
	}
	
	public static List<ItemPedCliente> getPendenciasDoProduto(Date ini, Date fim, String codigo){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		List<ItemPedCliente> lista = new ArrayList<>();
		
		String sql = "select a.idpedido, codigoftd, nomeftd, emissao, b.codigo, qtdpedida, atendido, "
				+ "(qtdpedida - atendido) as pendente from pedcliente a inner join (select "
				+ "l.idpedido, l.codigo, qtdpedida,atendido from item_pedcliente l inner join "
				+ "(select idpedido, codigo, sum(qtdatendida) as atendido from item_pedcliente_atendido "
				+ "group by idpedido, codigo) f on l.idpedido = f.idpedido and l.codigo = f.codigo) b on "
				+ "a.idpedido = b.idpedido "
				+ "and emissao between ? and ? having pendente > 0 and codigo = ?";
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setDate(1, ini);
			pstm.setDate(2, fim);
			pstm.setString(3, codigo);
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next()){
				ItemPedCliente item = new ItemPedCliente();
				item.setIdpedido(rs.getInt("idpedido"));
				item.setCodigoftd(rs.getString("codigoftd"));
				item.setNomeftd(rs.getString("nomeftd"));
				item.setEmissao(rs.getDate("emissao"));
				item.setQtdpedida(rs.getInt("qtdpedida"));
				item.setQtdatendida(rs.getInt("atendido"));
				item.setQtdpendente(rs.getInt("pendente"));
				lista.add(item);
			}
			
			rs.close();
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}
	
	
	public static void setPrevisaoProduto(Produto produto){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		Date agora = new Date(System.currentTimeMillis());
		
		String sql = "select codigo, max(previsao) as prev, observacao from"
				+ " item_pedido where cancelado = 0 and codigo = ? and previsao >= ? group by codigo, observacao";
		
		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, produto.getCodigo());
			pstm.setDate(2, agora);
			ResultSet rs = pstm.executeQuery();
			
			if(rs.next()){
				produto.setPrevisao(rs.getDate("prev"));
				produto.setObspedido(rs.getString("observacao"));
			}
			rs.close();
			pstm.close();						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
}
