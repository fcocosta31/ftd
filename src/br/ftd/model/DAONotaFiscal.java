package br.ftd.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import br.ftd.control.ControlServlet;
import br.ftd.entity.Fornecedor;
import br.ftd.entity.ItemNotaFiscal;
import br.ftd.entity.NotaFiscal;
import br.ftd.entity.Produto;
import br.ftd.entity.TotvsDb;
import br.ftd.factory.ConnectionFactory;


public class DAONotaFiscal {
	
	public String salvar(NotaFiscal nota) throws SQLException{
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		String sql = "insert into notafiscal (idnota, emissao, total, cnpj, uf, serie, cnpjemit, emitente,"
				+ " cfop, natop, municipio, desconto, liquido, percentual) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String sqli = "insert into item_notafiscal (idnota, codigo, qtde, preco, totalitem, descricao, codbar) "
				+ "values (?,?,?,?,?,?,?)";
		String sqlj = "insert into notapedido values (?,?)";
		
		
		try {
			
			con.setAutoCommit(false);
			
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, nota.getIdnota());
			pstm.setDate(2, nota.getEmissao());
			pstm.setFloat(3, nota.getTotal());
			pstm.setString(4, nota.getCnpj());
			pstm.setString(5, nota.getUF());
			pstm.setString(6, nota.getSerie());
			pstm.setString(7, nota.getCnpjemit());
			pstm.setString(8, nota.getEmitente());
			pstm.setString(9, nota.getCfop());
			pstm.setString(10, nota.getNatop());
			pstm.setString(11, nota.getMunicipio());
			pstm.setFloat(12, nota.getDesconto());
			pstm.setFloat(13, nota.getLiquido());
			pstm.setFloat(14, nota.getPercentual());
			
			pstm.execute();
			pstm.close();
			
			pstm = con.prepareStatement(sqlj);
			pstm.setString(1, nota.getIdnota());
			pstm.setInt(2, nota.getIdpedido());
			pstm.execute();
			pstm.close();
			
			pstm = con.prepareStatement(sqli);
			
			for(ItemNotaFiscal i : nota.getItens()){
				pstm.setString(1, nota.getIdnota());
				pstm.setString(2, i.getItem().getCodigo());
				pstm.setFloat(3, i.getQuantidade());
				pstm.setFloat(4, i.getPreco());
				pstm.setFloat(5, i.getTotal());
				pstm.setString(6, i.getItem().getDescricao());
				pstm.setString(7, i.getItem().getCodbar());
				pstm.addBatch();
			}
						
			pstm.executeBatch();
			con.commit();
			pstm.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			con.rollback();
			return atualizar(nota);
		}
		
		return nota.getIdnota()+"(Ok)";
	}
	

	public String atualizar(NotaFiscal nota) throws SQLException{
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		String sql = "update notafiscal set idnota = ?, emissao = ?, total = ?, cnpj = ?, uf = ?, serie = ?, cnpjemit = ?, emitente = ?,"
				+ " cfop = ?, natop = ?, municipio = ?, desconto = ?, liquido = ?, percentual = ? where idnota = ? and cnpjemit = ?";		
		String sqli = "insert into item_notafiscal (idnota, codigo, qtde, preco, totalitem, descricao, codbar) "
				+ "values (?,?,?,?,?,?,?)";
		
		try {
			
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, nota.getIdnota());
			pstm.setDate(2, nota.getEmissao());
			pstm.setFloat(3, nota.getTotal());
			pstm.setString(4, nota.getCnpj());
			pstm.setString(5, nota.getUF());
			pstm.setString(6, nota.getSerie());
			pstm.setString(7, nota.getCnpjemit());
			pstm.setString(8, nota.getEmitente());
			pstm.setString(9, nota.getCfop());
			pstm.setString(10, nota.getNatop());
			pstm.setString(11, nota.getMunicipio());
			pstm.setFloat(12, nota.getDesconto());
			pstm.setFloat(13, nota.getLiquido());
			pstm.setFloat(14, nota.getPercentual());
			pstm.setString(15, nota.getIdnota());
			pstm.setString(16, nota.getCnpjemit());
			
			pstm.execute();
			pstm.close();									
			Statement stm = con.createStatement();
			stm.execute("delete from item_notafiscal where idnota = '"+nota.getIdnota()+"'");
			stm.close();
			con.setAutoCommit(false);
			pstm = con.prepareStatement(sqli);
			
			for(ItemNotaFiscal i : nota.getItens()){
				pstm.setString(1, nota.getIdnota());
				pstm.setString(2, i.getItem().getCodigo());
				pstm.setFloat(3, i.getQuantidade());
				pstm.setFloat(4, i.getPreco());
				pstm.setFloat(5, i.getTotal());
				pstm.setString(6, i.getItem().getDescricao());
				pstm.setString(7, i.getItem().getCodbar());
				pstm.addBatch();
			}
						
			pstm.executeBatch();
			con.commit();
			pstm.close();
			
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			con.rollback();
			return e.getMessage();
		}
		
		return nota.getIdnota()+"(Ok)";
	}
	
	
	public boolean remover(NotaFiscal nota){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "delete from notafiscal where idnota = '"+nota.getIdnota()+"' and emissao = '"+nota.getEmissao()+"'";
		String sqli = "delete from item_notafiscal where idnota = '"+nota.getIdnota()+"'";
		String sqlp = "delete from notapedido where idnota = '"+nota.getIdnota()+"' and idpedido = "+nota.getIdpedido();
		
		try {
			Statement stm = con.createStatement();
			stm.addBatch(sql);
			stm.addBatch(sqli);
			stm.addBatch(sqlp);
			stm.executeBatch();
			stm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false;
		}
				
		return true;
	}
	
	public List<Fornecedor> loadComboFornecedor() throws SQLException{
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		List<Fornecedor> lista = new ArrayList<>();
		String sql = "select emitente, cnpjemit, uf from notafiscal where emitente <> '' group by cnpjemit, emitente, uf order by cnpjemit != '61186490001633', emitente";
		
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		while(rs.next()) {
			Fornecedor f = new Fornecedor();
			f.setCnpj(rs.getString("cnpjemit"));
			f.setNome(rs.getString("emitente"));
			f.setUf(rs.getString("uf"));
			lista.add(f);
		}
		rs.close();
		stm.close();
		con.close();
		
		return lista;
	}
	
	public void exportaNotaFiscal(String tipoexport, HttpServletResponse response, String nota,
			String fornecedor, String filial, String serie) throws SQLException{
		
		if(tipoexport.equalsIgnoreCase("ftd")){
			exportaNotaFTD(response, fornecedor, nota);
		}else{
			exportaNotaSLD(response, filial, nota, serie);
		}
		
	}
	
	
	public void exportaNotaFTD(HttpServletResponse response, String fornecedor, String nota) throws SQLException{
		
		String nomeArquivo = "NF-"+nota+".csv";
		
		response.setContentType("text/csv;vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename="+nomeArquivo);
		response.setHeader("Set-Cookie", "fileDownload=true; path=/");
		
		ServletOutputStream fout = null;
		
		String sql = "select * from notafiscal a inner join item_notafiscal b "
				+ "on a.idnota = b.idnota and a.cnpjemit = '"+fornecedor+"' and a.idnota = '"+nota+"'";
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		try {
			
			fout = response.getOutputStream();

			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
					
            if(rs.next()){
            
                String linhaarq = "";
                String cnpj = rs.getString("cnpj");

                linhaarq = cnpj+";";
                linhaarq = linhaarq + "\r\n";
                byte[] linha1 = linhaarq.getBytes();
                fout.write(linha1);

                String nrnota = rs.getString("idnota");
                String codigo = rs.getString("codigo");
                String serie = rs.getString("serie");
                int quant = rs.getInt("qtde");
                float d = rs.getFloat("preco");
                String preco = String.format("%.3f", d);
                preco = preco.replace('.', ',');
                float t = d*quant;
                String totali = String.format("%.3f", t);
                totali = totali.replace('.', ',');

                linhaarq = serie+";"+nrnota+";"+codigo+";"+quant+";"+preco+";"+totali+";";
                linhaarq = linhaarq + "\r\n";
                byte[] linha = linhaarq.getBytes();
                fout.write(linha);
                
                while(rs.next()){
                    linhaarq = "";
                    nrnota = rs.getString("idnota");
                    codigo = rs.getString("codigo");
                    quant = rs.getInt("qtde");
                    d = rs.getFloat("preco");
                    preco = String.format("%.3f", d);
                    preco = preco.replace('.', ',');
                    t = d*quant;
                    totali = String.format("%.3f", t);
                    totali = totali.replace('.', ',');
                    
                    linhaarq = serie+";"+nrnota+";"+codigo+";"+quant+";"+preco+";"+totali+";";
                    linhaarq = linhaarq + "\r\n";
                    linha = linhaarq.getBytes();
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
	
	public void exportaNotaSLD(HttpServletResponse response, String filial, String nota, String serien) throws SQLException{
		
		nota = padLeft(nota, 9);

		String nomeArquivo = "NF-"+nota+".csv";
		
		response.setContentType("text/csv;vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename="+nomeArquivo);
		response.setHeader("Set-Cookie", "fileDownload=true; path=/");
		
		ServletOutputStream fout = null;
		
		String sql = "SELECT D2_DOC, D2_SERIE, A1_CGC, D2_COD, D2_QUANT, D2_PRCVEN, D2_TOTAL"
				+ " FROM "+TotvsDb.SD2.getTable(ControlServlet.getParams().getGpoemptotvs())+" INNER JOIN (SELECT F2_FILIAL, F2_SERIE, F2_DOC, A1_CGC"
						+ " FROM "+ TotvsDb.SF2.getTable(ControlServlet.getParams().getGpoemptotvs())+" INNER JOIN "+TotvsDb.SA1.getTable(ControlServlet.getParams().getGpoemptotvs())
						+ " ON F2_CLIENTE = A1_COD AND F2_SERIE = '"+serien+"' AND F2_FILIAL = '"+filial+"'"
						+ " AND F2_DOC = '"+nota+"') AS T"
						+ " ON F2_DOC = D2_DOC AND F2_SERIE = D2_SERIE AND F2_FILIAL = D2_FILIAL";
		
		Connection con = ConnectionFactory.getInstance().getSqlConnection();
		if(con !=null){
		try {
			
			fout = response.getOutputStream();

			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
					
            if(rs.next()){
            
                String linhaarq = "";
                String cnpj = rs.getString("A1_CGC");

                linhaarq = cnpj+";";
                linhaarq = linhaarq + "\r\n";
                byte[] linha1 = linhaarq.getBytes();
                fout.write(linha1);

                String nrnota = rs.getString("D2_DOC");
                String codigo = rs.getString("D2_COD");
                String serie = rs.getString("D2_SERIE");
                int quant = rs.getInt("D2_QUANT");
                float d = rs.getFloat("D2_PRCVEN");
                String preco = String.format("%.3f", d);
                preco = preco.replace('.', ',');
                float t = rs.getFloat("D2_TOTAL");
                String totali = String.format("%.3f", t);
                totali = totali.replace('.', ',');

                linhaarq = serie+";"+nrnota+";"+codigo+";"+quant+";"+preco+";"+totali+";";
                linhaarq = linhaarq + "\r\n";
                byte[] linha = linhaarq.getBytes();
                fout.write(linha);
                
                while(rs.next()){
                    linhaarq = "";
                    nrnota = rs.getString("D2_DOC");
                    codigo = rs.getString("D2_COD");
                    quant = rs.getInt("D2_QUANT");
                    d = rs.getFloat("D2_PRCVEN");
                    preco = String.format("%.3f", d);
                    preco = preco.replace('.', ',');
                    t = rs.getFloat("D2_TOTAL");
                    totali = String.format("%.3f", t);
                    totali = totali.replace('.', ',');
                    
                    linhaarq = serie+";"+nrnota+";"+codigo+";"+quant+";"+preco+";"+totali+";";
                    linhaarq = linhaarq + "\r\n";
                    linha = linhaarq.getBytes();
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
	}
	

	public void exportaNotasFTD(HttpServletResponse response, String fornecedor, String[] notas) throws SQLException{
		
		String nomeArquivo = "NotasFiscais.csv";
		
		response.setContentType("text/csv;vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename="+nomeArquivo);
		response.setHeader("Set-Cookie", "fileDownload=true; path=/");
		
		ServletOutputStream fout = null;
				
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		try {
						
			fout = response.getOutputStream();			
			String sql = "";
			String linhaarq = "";
			boolean flag = false;
			String notaln = "(";
			for(String nota:notas) {
				notaln += "a.idnota = '"+nota+"' or ";
			}
			int len = notaln.length() - 4;
			
			notaln = notaln.substring(0, len) + ")";
			
				sql = "select * from notafiscal a inner join item_notafiscal b "
						+ "on a.idnota = b.idnota and a.cnpjemit = '"+fornecedor+"' and "+notaln;
				
				System.out.println(sql);
				Statement stm = con.createStatement();
				ResultSet rs = stm.executeQuery(sql);
						
	            if(rs.next()){
	            	                
	                String cnpj = rs.getString("cnpj");
	                if(!flag) {
		                linhaarq = cnpj+";";
		                linhaarq = linhaarq + "\r\n";
		                byte[] linha1 = linhaarq.getBytes();
		                fout.write(linha1);
		                flag = true;
	                }
	                
	                String nrnota = rs.getString("idnota");
	                String codigo = rs.getString("codigo");
	                String serie = rs.getString("serie");
	                int quant = rs.getInt("qtde");
	                float d = rs.getFloat("preco");
	                String preco = String.format("%.3f", d);
	                preco = preco.replace('.', ',');
	                float t = d*quant;
	                String totali = String.format("%.3f", t);
	                totali = totali.replace('.', ',');
	
	                linhaarq = serie+";"+nrnota+";"+codigo+";"+quant+";"+preco+";"+totali+";";
	                linhaarq = linhaarq + "\r\n";
	                byte[] linha = linhaarq.getBytes();
	                fout.write(linha);
	                
	                while(rs.next()){
	                    linhaarq = "";
	                    nrnota = rs.getString("idnota");
	                    codigo = rs.getString("codigo");
	                    quant = rs.getInt("qtde");
	                    d = rs.getFloat("preco");
	                    preco = String.format("%.3f", d);
	                    preco = preco.replace('.', ',');
	                    t = d*quant;
	                    totali = String.format("%.3f", t);
	                    totali = totali.replace('.', ',');
	                    
	                    linhaarq = serie+";"+nrnota+";"+codigo+";"+quant+";"+preco+";"+totali+";";
	                    System.out.println(linhaarq);
	                    linhaarq = linhaarq + "\r\n";
	                    linha = linhaarq.getBytes();
	                    fout.write(linha);
	                }
	                
	            }
	            stm.close();
	            rs.close();
			
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
	
	public static String padLeft(String s, int n) {
		 int i = s.length();
		 for(;i<n;i++){
			 s = "0"+s;
		 }
	     return s;  
	}
	
	public boolean setDataChegada (String nota, Date dataatual){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "update notafiscal set datachegada = ? where idnota = ?";
		
		try {
			
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setDate(1, dataatual);
			pstm.setString(2, nota);
			pstm.execute();
			pstm.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
				
		return true;
	}

	public boolean vinculaNota(String nota, int pedido) throws SQLException{
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		String sql = "insert into notapedido values (?,?)";
		String sqln = "select idnota from notafiscal where idnota = ?";
		
		try {
			PreparedStatement pstm = con.prepareStatement(sqln);
			pstm.setString(1, nota);
			ResultSet rs = pstm.executeQuery();
			if(rs.next()){
				PreparedStatement pstm2 = con.prepareStatement(sql);
				pstm2.setString(1, nota);
				pstm2.setInt(2, pedido);
				pstm2.execute();
				pstm2.close();
			}
			else{
				return false;
			}
			rs.close();
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
			
		}finally{			
			con.close();
		}
		
		return true;
	}
	
	public List<NotaFiscal> listar(Date inicio, Date fim){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select distinct a.idnota, b.idpedido, a.emissao, a.cfop, a.natop, a.emitente, a.cnpjemit,"
				+ " a.municipio, a.datachegada, a.total, a.desconto, a.liquido, a.percentual, a.uf"
				+ " from notafiscal a inner join notapedido b on a.idnota = b.idnota and"
				+ " a.emissao between ? and ? and b.idpedido > 0"
				+ " order by a.emissao, a.idnota";
		
		List<NotaFiscal> notas = new ArrayList<>();
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setDate(1, inicio);
			pstm.setDate(2, fim);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				NotaFiscal nota = new NotaFiscal();
				nota.setIdnota(rs.getString("idnota"));
				nota.setIdpedido(rs.getInt("idpedido"));
				nota.setEmissao(rs.getDate("emissao"));
				nota.setCfop(rs.getString("cfop"));
				nota.setNatop(rs.getString("natop"));
				nota.setEmitente(rs.getString("emitente"));
				nota.setCnpjemit(rs.getString("cnpjemit"));
				nota.setMunicipio(rs.getString("municipio"));
				nota.setChegada(rs.getDate("datachegada"));
				nota.setTotal(rs.getFloat("total"));
				nota.setDesconto(rs.getFloat("desconto"));
				nota.setLiquido(rs.getFloat("liquido"));
				nota.setPercentual(rs.getFloat("percentual"));
				if(nota.getChegada()==null)
					DAOProduto.setDataChegadaProduto(nota);
				nota.setUF(rs.getString("uf"));
				notas.add(nota);
			}
			rs.close();
			pstm.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return notas;
	}
	

	public List<NotaFiscal> listar(Produto produto, String ano){
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		int anoant = Integer.parseInt(ano)-1;
		
		Date ini = convertSQLStringToDate((anoant)+"0701");
		Date fim = convertSQLStringToDate(ano+"0630");

		String sql = "select a.idnota, c.idpedido, a.emissao, a.cfop, a.natop, a.emitente, a.cnpjemit,"
				+ " a.municipio, a.datachegada, a.total, a.desconto, a.liquido, a.percentual, a.uf, b.qtde, b.preco"
				+ " from notafiscal a inner join item_notafiscal b inner join notapedido c on a.idnota = b.idnota and"
				+ " a.idnota = c.idnota where a.emissao between ? and ? and b.codigo = ? order by a.emissao, a.idnota desc";
		
		List<NotaFiscal> notas = new ArrayList<>();
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setDate(1, ini);
			pstm.setDate(2, fim);
			pstm.setString(3, produto.getCodigo());
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){				
				NotaFiscal nota = new NotaFiscal();
				List<ItemNotaFiscal> items = new ArrayList<>();
				ItemNotaFiscal item = new ItemNotaFiscal();
				item.setQuantidade(rs.getFloat("qtde"));
				item.setPreco(rs.getFloat("preco"));
				items.add(item);
				nota.setItens(items);
				nota.setIdnota(rs.getString("idnota"));
				nota.setIdpedido(rs.getInt("idpedido"));
				nota.setEmissao(rs.getDate("emissao"));
				nota.setCfop(rs.getString("cfop"));
				nota.setNatop(rs.getString("natop"));
				nota.setEmitente(rs.getString("emitente"));
				nota.setCnpjemit(rs.getString("cnpjemit"));
				nota.setMunicipio(rs.getString("municipio"));
				nota.setChegada(rs.getDate("datachegada"));
				nota.setTotal(rs.getFloat("total"));
				nota.setDesconto(rs.getFloat("desconto"));
				nota.setLiquido(rs.getFloat("liquido"));
				nota.setPercentual(rs.getFloat("percentual"));
				nota.setUF(rs.getString("uf"));
				notas.add(nota);
			}
			rs.close();
			pstm.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (NotaFiscal notaFiscal : notas) {
			if(notaFiscal.getChegada() == null) {
				DAOProduto.setDataChegadaProduto(notaFiscal);
			}
		}
		
		return notas;
	}

	
	public static void recarrega(NotaFiscal nota){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		String sql = "select p.idpedido, n.idnota, n.emissao, n.cfop, n.natop, n.emitente, n.cnpjemit,"
				+ " n.municipio, n.total, n.desconto, n.liquido, n.percentual, n.cnpj, n.datachegada,"
				+ " n.uf, n.serie from notafiscal n inner join notapedido p on n.idnota = p.idnota"
				+ " and n.idnota = ? and emissao = ? and p.idpedido > 0";		
				
		List<ItemNotaFiscal> itens = new ArrayList<>();

		Produto p = null;
		float quantidade, preco, total;
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, nota.getIdnota());
			pstm.setDate(2, nota.getEmissao());
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next()) {
				nota.setIdpedido(rs.getInt("idpedido"));
				nota.setCnpjemit(rs.getString("cnpjemit"));
				nota.setCnpj(rs.getString("cnpj"));
				nota.setCfop(rs.getString("cfop"));
				nota.setNatop(rs.getString("natop"));
				nota.setEmitente(rs.getString("emitente"));
				nota.setMunicipio(rs.getString("municipio"));
				nota.setUF(rs.getString("uf"));
				nota.setSerie(rs.getString("serie"));
				nota.setChegada(rs.getDate("datachegada"));
				nota.setTotal(rs.getFloat("total"));
				nota.setDesconto(rs.getFloat("desconto"));
				nota.setLiquido(rs.getFloat("liquido"));
				nota.setPercentual(rs.getFloat("percentual"));			
			}				
		
			rs.close();
			pstm.close();

			sql = "select idnota, codigo, descricao, qtde, preco, totalitem from item_notafiscal"
					+ " where idnota = ?";
			
			pstm = con.prepareStatement(sql);
			pstm.setString(1, nota.getIdnota());
			rs = pstm.executeQuery();
				
			while(rs.next()) {
				p = new Produto();
				p.setCodigo(rs.getString("codigo"));
				p.setDescricao(rs.getString("descricao"));
				quantidade = rs.getFloat("qtde");
				preco = rs.getFloat("preco");
				total = preco*quantidade;
				itens.add(new ItemNotaFiscal(p, quantidade, preco, total));					
			}
			nota.setItens(itens);
			
			rs.close();
			pstm.close();
			con.close();

			DAOProduto.setDataChegadaProduto(nota);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}

	
	
	public static void recarrega(NotaFiscal nota, Produto p){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select idnota, emissao, datachegada, total, uf, serie, idpedido,"
				+ " cnpj, cnpjemit, m.codigo, p.descricao, qtde, m.preco, totalitem"
				+ " from notafiscal natural join notapedido natural join item_notafiscal m join produto p"
				+ " on m.codigo = p.codigo having idnota = ? and codigo = ? and emissao = ?";
		List<ItemNotaFiscal> itens = new ArrayList<>();

		float quantidade, preco, total;
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, nota.getIdnota());
			pstm.setString(2, p.getCodigo());
			pstm.setDate(3, nota.getEmissao());
			ResultSet rs = pstm.executeQuery();
			if(rs.next()){
				nota.setEmissao(rs.getDate("emissao"));
				nota.setChegada(rs.getDate("datachegada"));
				nota.setCnpj(rs.getString("cnpj"));
				nota.setCnpjemit(rs.getString("cnpjemit"));
				nota.setIdpedido(rs.getInt("idpedido"));
				nota.setTotal(rs.getFloat("total"));
				nota.setChegada(rs.getDate("datachegada"));
				nota.setUF(rs.getString("uf"));
				nota.setSerie(rs.getString("serie"));				
				quantidade = rs.getFloat("qtde");
				preco = rs.getFloat("preco");
				total = preco*quantidade;
				itens.add(new ItemNotaFiscal(p, quantidade, preco, total));
			}
			nota.setItens(itens);
			
			rs.next();
			pstm.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}

	
	public static void setDataChegadaNota(NotaFiscal nota){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "update notafiscal set datachegada = ? where idnota = ? and emissao = ?";
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setDate(1, nota.getChegada());
			pstm.setString(2, nota.getIdnota());
			pstm.setDate(3, nota.getEmissao());
			pstm.execute();
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void setDataChegadaProduto(NotaFiscal nota){

		Connection con;
		String sql;
		ResultSet rs;
		
		sql = "select datachegada from notafiscal where idnota = ? and emissao = ?";
		PreparedStatement pstm;
		con = ConnectionFactory.getInstance().getMySqlConnection();
		
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, nota.getIdnota());
			pstm.setDate(2, nota.getEmissao());
			rs = pstm.executeQuery();
			if(rs.next()){
				nota.setChegada(rs.getDate("datachegada"));
			}
			rs.close();
			pstm.close();
			con.close();
			
			if(nota.getChegada() == null){
				setDataChegadaProdutoTotvs(nota);
			}
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}
	
	public static void setDataChegadaProdutoTotvs(NotaFiscal nota){
		
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
				if(nota.getChegada() != null){
					setDataChegadaNota(nota);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	
	public static void exportaProdutos() {
		
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
	
}
