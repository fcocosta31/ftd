package br.ftd.model;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.report.ResultSetCollection;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.ss.usermodel.Workbook;

import br.ftd.control.ControlServlet;
import br.ftd.entity.Demanda;
import br.ftd.entity.ItemDemanda;
import br.ftd.entity.NotaFiscal;
import br.ftd.entity.Produto;
import br.ftd.entity.TotvsDb;
import br.ftd.factory.ConnectionFactory;

public class DAODemanda {
	
	public List<Demanda> relatorioDemanda(Date datainicio, Date datafim, String ano, String TESVd,
			float taxa, String itens, String familia, String simula){
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();		
		
		Map<String, Integer> mappedidos = new HashMap<>();
		Map<String, Integer> maprecebidos = new HashMap<>();
		Map<String, Integer> mapprevisao = new HashMap<>();
		
		String sqlDemanda = "select codigo, descricao, status, familia, nivel, lancto, sum(qtde) as qtadotada from (select codigo,"
						+ " descricao, d.idescola, d.serie, status, familia, nivel, lancto, format(quantidade*taxa,0) as qtde from"
						+ " escola_serie_aluno c inner join (select a.codigo, a.descricao, a.idescola, a.serie, a.nivel,"
						+ " a.status, a.familia, a.lancto, ifnull((case fator when 1 then 1 when null then 1 else (case when a.nivel"
						+ " = '01-Infantil' then 1 when a.serie = '1 Ano' then 1 when a.serie = '6 Ano' then 1 end) end),?)"
						+ " as taxa from (select a.codigo, descricao, a.serie, idescola, nivel, status, familia, lancto from produto"
						+ " u inner join (select codigo, serie, idescola from adocao natural join item_adocao where ano = ?) as a"
						+ " on a.codigo = u.codigo) as a left join (select codigo, idescola, count(ano) as fator from adocao"
						+ " natural join item_adocao group by idescola, codigo) as b on a.idescola = b.idescola and a.codigo"
						+ " = b.codigo) as d on c.idescola = d.idescola and c.serie = d.serie and c.ano = ?) as t group by codigo";
		
		if(itens.equalsIgnoreCase("tabela")){
			if(familia.equalsIgnoreCase("sim")){
				sqlDemanda = "select a.codigo, a.descricao, a.status, a.familia, a.nivel, a.inativo, a.lancto, ifnull(qtadotada,0) as qtadotada from produto a left join"
								+ " (select codigo, descricao, status, familia, nivel, sum(qtde) as qtadotada from (select codigo,"
								+ " descricao, d.idescola, d.serie, status, familia, nivel, format(quantidade*taxa,0) as qtde from"
								+ " escola_serie_aluno c inner join (select a.codigo, a.descricao, a.idescola, a.serie, a.nivel,"
								+ " a.status, a.familia, ifnull((case fator when 1 then 1 when null then 1 else (case when a.nivel"
								+ " = '01-Infantil' then 1 when a.serie = '1 Ano' then 1 when a.serie = '6 Ano' then 1 end) end),?)"
								+ " as taxa from (select a.codigo, descricao, a.serie, idescola, nivel, status, familia from produto"
								+ " u inner join (select codigo, serie, idescola from adocao natural join item_adocao where ano = ?) as a"
								+ " on a.codigo = u.codigo) as a left join (select codigo, idescola, count(ano) as fator from adocao"
								+ " natural join item_adocao group by idescola, codigo) as b on a.idescola = b.idescola and a.codigo"
								+ " = b.codigo) as d on c.idescola = d.idescola and c.serie = d.serie and c.ano = ?) as t group by codigo) as b"
								+ " on a.codigo = b.codigo having inativo = 0 and (familia = '01-DIDATICO' or familia = '01-DIDLE' or familia like '12-SE%' or"
								+ " familia ='13-OPEE')";
			}else{
				sqlDemanda = "select a.codigo, a.descricao, a.status, a.familia, a.nivel, a.inativo, a.lancto, ifnull(qtadotada,0) as qtadotada from produto a left join"
						+ " (select codigo, descricao, status, familia, nivel, sum(qtde) as qtadotada from (select codigo,"
						+ " descricao, d.idescola, d.serie, status, familia, nivel, format(quantidade*taxa,0) as qtde from"
						+ " escola_serie_aluno c inner join (select a.codigo, a.descricao, a.idescola, a.serie, a.nivel,"
						+ " a.status, a.familia, ifnull((case fator when 1 then 1 when null then 1 else (case when a.nivel"
						+ " = '01-Infantil' then 1 when a.serie = '1 Ano' then 1 when a.serie = '6 Ano' then 1 end) end),?)"
						+ " as taxa from (select a.codigo, descricao, a.serie, idescola, nivel, status, familia from produto"
						+ " u inner join (select codigo, serie, idescola from adocao natural join item_adocao where ano = ?) as a"
						+ " on a.codigo = u.codigo) as a left join (select codigo, idescola, count(ano) as fator from adocao"
						+ " natural join item_adocao group by idescola, codigo) as b on a.idescola = b.idescola and a.codigo"
						+ " = b.codigo) as d on c.idescola = d.idescola and c.serie = d.serie and c.ano = ?) as t group by codigo) as b having inativo = 0";
			}
		}else{
			if(familia.equalsIgnoreCase("sim")){
				sqlDemanda = "select codigo, descricao, status, familia, nivel, lancto, sum(qtde) as qtadotada from (select codigo,"
						+ " descricao, d.idescola, d.serie, status, familia, nivel, lancto, format(quantidade*taxa,0) as qtde from"
						+ " escola_serie_aluno c inner join (select a.codigo, a.descricao, a.idescola, a.serie, a.nivel,"
						+ " a.status, a.familia, a.lancto, ifnull((case fator when 1 then 1 when null then 1 else (case when a.nivel"
						+ " = '01-Infantil' then 1 when a.serie = '1 Ano' then 1 when a.serie = '6 Ano' then 1 end) end),?)"
						+ " as taxa from (select a.codigo, descricao, a.serie, idescola, nivel, status, familia, lancto from produto"
						+ " u inner join (select codigo, serie, idescola from adocao natural join item_adocao where ano = ?) as a"
						+ " on a.codigo = u.codigo) as a left join (select codigo, idescola, count(ano) as fator from adocao"
						+ " natural join item_adocao group by idescola, codigo) as b on a.idescola = b.idescola and a.codigo"
						+ " = b.codigo) as d on c.idescola = d.idescola and c.serie = d.serie and c.ano = ?) as t where (familia = '01-Didatico'"
						+ " or familia = '01-DIDLE' or familia like '12-SE%' or familia='13-OPEE') group by codigo";
			}			
		}
		
		String sqlPedido = "select codigo, sum(qtde) as qtpedida from pedido a inner join item_pedido b "
				+ "on a.idpedido = b.idpedido and b.cancelado = 0 and a.dataped between ? and ? group by codigo";
		
		String sqlNotaFiscal = "select distinct a.idnota, a.emissao, a.cnpjemit, a.datachegada"
				+ " from notafiscal a inner join (select idnota from notapedido b inner join pedido c on"
				+ " b.idpedido = c.idpedido) d on a.idnota = d.idnota and isnull(datachegada) and a.emissao between ? and ?";

		String sqlRecebido = "select codigo, sum(qtde) as qtdchegou from (select d.idnota, b.codigo, b.qtde from item_notafiscal"
				+ " b inner join (select a.idnota from notafiscal a inner join (select b.idnota from pedido a inner join notapedido"
				+ " b on a.idpedido = b.idpedido and a.dataped between ? and ?) as c on a.idnota = c.idnota and not"
				+ " isnull(a.datachegada)) as d on b.idnota = d.idnota) as k group by codigo"; 
				
		/*		"select codigo, sum(qtde) as qtdchegou from (select a.idnota, b.codigo, b.qtde from"
				+ " notafiscal a inner join item_notafiscal b"
				+ " on a.idnota = b.idnota and a.emissao between ? and ? and not isnull(a.datachegada)"
				+ " and a.idnota in (select a.idnota from pedido a inner join notapedido b on a.idpedido = b.idpedido and"
				+ " a.dataped between ? and ?)) as t group by codigo";
		*/
				
		String sqlPrevSP = "select codigo, quantidade from previsao_sp where ano = ?";
		
		List<Demanda> listademanda = new ArrayList<>();
		
		try {
			PreparedStatement pstm = con.prepareStatement(sqlDemanda);
			pstm.setFloat(1, taxa);
			pstm.setString(2, ano);
			pstm.setString(3, ano);
			
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				Demanda d = new Demanda();
				ItemDemanda itemdemanda = new ItemDemanda();
				Produto p = new Produto();
				p.setCodigo(rs.getString("codigo"));
				p.setDescricao(rs.getString("descricao"));
				p.setStatus(rs.getString("status"));
				p.setFamilia(rs.getString("familia"));
				p.setNivel(rs.getString("nivel"));
				p.setLancto(rs.getString("lancto"));
				itemdemanda.setItem(p);
				d.setItemdemanda(itemdemanda);
				d.setQtddemanda(rs.getInt("qtadotada"));
				d.setAno(ano);
				listademanda.add(d);
			}
			
			rs.close();
			pstm.close();

			int anoatual = Integer.parseInt(ano) - 1;
			
			Date datainicial = ControlServlet.convertStringToDatePrev((anoatual)+"-07-01");
			Date datafinal = ControlServlet.convertStringToDatePrev(ano+"-06-30");

			pstm = con.prepareStatement(sqlNotaFiscal);
			pstm.setDate(1, datainicial);
			pstm.setDate(2, datafinal);
			rs = pstm.executeQuery();
			while(rs.next()){
				NotaFiscal n = new NotaFiscal();
				n.setIdnota(rs.getString("idnota"));
				n.setEmissao(rs.getDate("emissao"));
				n.setCnpjemit(rs.getString("cnpjemit"));
				Date d = rs.getDate("datachegada");
				if(d != null) {
					n.setChegada(rs.getDate("datachegada"));
				}else {
					DAONotaFiscal.setDataChegadaProduto(n);
				}
			}
			rs.close();
			pstm.close();
			
									
			/* Carregando o Map pedidos */
			pstm = con.prepareStatement(sqlPedido);
			pstm.setDate(1, datainicial);
			pstm.setDate(2, datafinal);
			rs = pstm.executeQuery();
			while(rs.next()) {
				String codigo = rs.getString("codigo");
				int qtde = rs.getInt("qtpedida");
				mappedidos.put(codigo, qtde);
			}
			rs.close();
			pstm.close();
			
			/* Carregando o Map recebidos */
			pstm = con.prepareStatement(sqlRecebido);
			pstm.setDate(1, datainicial);
			pstm.setDate(2, datafinal);
			rs = pstm.executeQuery();
			while(rs.next()){
				String codigo = rs.getString("codigo");
				int qtde = rs.getInt("qtdchegou");
				maprecebidos.put(codigo, qtde);
			}
			rs.close();
			pstm.close();
			
			/* Carregando o Map previsoes */
			pstm = con.prepareStatement(sqlPrevSP);
			pstm.setString(1, ano);
			rs = pstm.executeQuery();
			while(rs.next()){
				String codigo = rs.getString("codigo");
				int qtde = rs.getInt("quantidade");
				mapprevisao.put(codigo, qtde);
			}
			rs.close();
			pstm.close();
			con.close();
			
			setVendas(listademanda, TESVd, ano, simula);
			
			for(Demanda d : listademanda){
				
				if(mappedidos.get(d.getItemdemanda().getItem().getCodigo()) != null) {
					int qtdpedida = mappedidos.get(d.getItemdemanda().getItem().getCodigo());
					d.setPedido(qtdpedida);
				}else {
					d.setPedido(0);
				}

				if(maprecebidos.get(d.getItemdemanda().getItem().getCodigo()) != null) {
					int qtdrecebida = maprecebidos.get(d.getItemdemanda().getItem().getCodigo());
					d.setQtdchegou(qtdrecebida);
				}else {
					d.setQtdchegou(0);
				}

				if(mapprevisao.get(d.getItemdemanda().getItem().getCodigo()) != null) {
					int qtdprev = mapprevisao.get(d.getItemdemanda().getItem().getCodigo());
					d.setQtdprevsp(qtdprev);
				}else {
					d.setQtdprevsp(0);
				}
				DAOProduto.setEstoque(d.getItemdemanda());
				d.refazTotais();
			}			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		Collections.sort(listademanda);
		return listademanda;
	}

	
	public void setEstoque(List<Demanda> lista) throws SQLException{
		
		for(Demanda i : lista){
			DAOProduto.setEstoque(i.getItemdemanda());
		}
	}

	public static String padRight(String s, int n) {
	     return String.format("%1$-" + n + "s", s);  
	}
	
	public void setVendas(List<Demanda> lista, String TESvd, String ano, String simula) throws SQLException{
		
		Connection con = ConnectionFactory.getInstance().getSqlConnection();		
		String sql = "select D2_COD, SUM(D2_QUANT) AS QTDE from "+TotvsDb.SD2.getTable(ControlServlet.getParams().getGpoemptotvs())+" where (D2_TES = ? or D2_TES = ?)"
				+ " and D2_EMISSAO between ? and ? and D_E_L_E_T_ <> '*' group by D2_COD order by D2_COD";
		String sql2 = "select C6_PRODUTO, SUM(C6_QTDVEN) AS QTDE from "+TotvsDb.SC6.getTable(ControlServlet.getParams().getGpoemptotvs())+" inner join "+TotvsDb.SC5.getTable(ControlServlet.getParams().getGpoemptotvs())+" on C6_NUM = C5_NUM and"
				+ " C6_CLI <> '' and C6_TES = ? and C5_NOTA = '' and C5_EMISSAO between ? and ?"
				+ " and "+TotvsDb.SC6.getTable(ControlServlet.getParams().getGpoemptotvs())+".D_E_L_E_T_ <> '*' group by C6_PRODUTO order by C6_PRODUTO";
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Map<String, Integer> vendas = new HashMap<>();
		Map<String, Integer> vendas1 = new HashMap<>();
		Map<String, Integer> vendas2 = new HashMap<>();
		Map<String, Integer> vendas3 = new HashMap<>();
		//Map<String, Integer> reservas = new HashMap<>();
		

		//tomando por base o ano da previs�o 2017: anoatual=2016, ano1=2013, ano2=2014 e ano3=2015
		int anoatual = Integer.parseInt(ano)-1;
		int ano1 = anoatual-3;
		int ano2 = anoatual-2;
		int ano3 = anoatual-1;
		int ano4 = anoatual + 1; 
		
		Date inicio = ControlServlet.convertStringToDateS(anoatual+"-07-01");
		Date fim = ControlServlet.convertStringToDateS(ano4+"-06-30");
		
		
		try {
			if(con!=null){			
				
				//Venda periodo atual (ano 4)
				pstm = con.prepareStatement(sql);
				pstm.setString(1, TESvd);
				pstm.setString(2, "501");
				pstm.setDate(3, inicio);
				pstm.setDate(4, fim);
				rs = pstm.executeQuery();

				while(rs.next()){
					String codigo = rs.getString("D2_COD");
					codigo = codigo.trim();
					int venda = rs.getInt("QTDE");
					vendas.put(codigo, venda);
				}
				rs.close();
				pstm.close();

				pstm = con.prepareStatement(sql2);
				pstm.setString(1, TESvd);
				pstm.setDate(2, inicio);
				pstm.setDate(3, fim);
				rs = pstm.executeQuery();
				while(rs.next()){
					String codigo = rs.getString("C6_PRODUTO");
					codigo = codigo.trim();
					int venda = rs.getInt("QTDE");
					if(vendas.get(codigo) != null){
						int vd = vendas.get(codigo);
						venda += vd;
						vendas.replace(codigo, venda);
					}else{
						vendas.put(codigo, venda);
					}					
				}
				rs.close();
				pstm.close();
				
				if(simula.equalsIgnoreCase("sim")){
					
					inicio = ControlServlet.convertStringToDateS(ano3+"-07-01");
					ControlServlet.convertStringToDateS(anoatual+"-06-30");
					
					//Venda no Ano 3
					pstm = con.prepareStatement(sql);
					pstm.setString(1, TESvd);
					pstm.setString(2, "501");
					pstm.setDate(3, inicio);
					pstm.setDate(4, fim);
					rs = pstm.executeQuery();
	
					while(rs.next()){
						String codigo = rs.getString("D2_COD");
						codigo = codigo.trim();
						int venda = rs.getInt("QTDE");
						vendas3.put(codigo, venda);
					}
					rs.close();
					pstm.close();
					
					
					//Venda no Ano 1
					inicio = ControlServlet.convertStringToDateS(ano1+"-07-01");
					fim = ControlServlet.convertStringToDateS(ano2+"-06-30");
					
					pstm = con.prepareStatement(sql);
					pstm.setString(1, TESvd);
					pstm.setString(2, "501");
					pstm.setDate(3, inicio);
					pstm.setDate(4, fim);
					rs = pstm.executeQuery();

					while(rs.next()){
						String codigo = rs.getString("D2_COD");
						codigo = codigo.trim();
						int venda = rs.getInt("QTDE");
						vendas1.put(codigo, venda);
					}
					rs.close();
					pstm.close();
					
					//Venda no Ano 2
					inicio = ControlServlet.convertStringToDateS(ano2+"-07-01");
					fim = ControlServlet.convertStringToDateS(ano3+"-06-30");
					
					pstm = con.prepareStatement(sql);
					pstm.setString(1, TESvd);
					pstm.setString(2, "501");
					pstm.setDate(3, inicio);
					pstm.setDate(4, fim);
					rs = pstm.executeQuery();

					while(rs.next()){
						String codigo = rs.getString("D2_COD");
						codigo = codigo.trim();
						int venda = rs.getInt("QTDE");
						vendas2.put(codigo, venda);
					}
					rs.close();
					pstm.close();
						
					for(Demanda i : lista){

						int venda = 0;
						if(vendas.get(i.getItemdemanda().getItem().getCodigo()) !=null)
							venda = vendas.get(i.getItemdemanda().getItem().getCodigo());
						i.setVendaatual(venda);
						
						int venda1 = 0;
						if(vendas1.get(i.getItemdemanda().getItem().getCodigo()) !=null)
							venda1 = vendas1.get(i.getItemdemanda().getItem().getCodigo());
						i.getItemdemanda().setVenda1(venda1);

						int venda2 = 0;
						if(vendas2.get(i.getItemdemanda().getItem().getCodigo()) !=null)
							venda2 = vendas2.get(i.getItemdemanda().getItem().getCodigo());
						i.getItemdemanda().setVenda2(venda2);

						int venda3 = 0;
						if(vendas3.get(i.getItemdemanda().getItem().getCodigo()) !=null)
							venda3 = vendas3.get(i.getItemdemanda().getItem().getCodigo());
						i.getItemdemanda().setVenda3(venda3);
					}
				}else {
					
					for(Demanda i : lista){
						int venda = 0;
						if(vendas.get(i.getItemdemanda().getItem().getCodigo()) !=null)
							venda = vendas.get(i.getItemdemanda().getItem().getCodigo());
						i.setVendaatual(venda);
					}					
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void pendentesToExcel(Date ini, Date fim, HttpServletResponse response, ServletContext context){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		String sql = "select m.idpedido, d.dataped, m.previsao, m.codigo, p.descricao, m.qtde,"
				+ " ifnull(n.qtde,0) as atendido, (m.qtde - ifnull(n.qtde,0)) as qtpend, cancelado, observacao from"
				+ " produto p natural join item_pedido m natural join pedido d left join "
				+ "(select idpedido, codigo, sum(qtde) as qtde from item_notafiscal natural "
				+ "join notafiscal natural join notapedido where idpedido > 0 group by idpedido, "
				+ "codigo) as n on m.idpedido = n.idpedido and m.codigo = n.codigo having dataped "
				+ "between ? and ? and qtpend > 0 and cancelado = 0 order by m.idpedido, m.codigo";	
				 
	        
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setDate(1, ini);
			pstm.setDate(2, fim);
			ResultSet rs = pstm.executeQuery();

			response.setContentType("application/vnd.ms-excel");
	    	response.setHeader("Content-Disposition", "attachment; filename=pendentes.xls");			
	    	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
	    	
	    	ResultSetCollection rsc = new ResultSetCollection(rs, false);
	    	
			
	    	String realPath = context.getRealPath("/");
	    	
			String templatePath = realPath + "/resources/xls/pendenteTemplate.xls";		
			
			InputStream is = new FileInputStream(templatePath);

			Map<String, Object> beans = new HashMap<>();
			
			beans.put("pend", rsc);
			beans.put("datainicial", ini);
			beans.put("datafinal", fim);
			
			XLSTransformer transformer = new XLSTransformer();
					
			Workbook workbook = transformer.transformXLS(is, beans);
			
			workbook.write(response.getOutputStream());											
			
			
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
		
	}
	
	
	public int importPrevisaoSP(List<Demanda> itens, String acao){
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		String sql = "insert into previsao_sp (codigo, ano, quantidade) values (?,?,?)";
		
		PreparedStatement pstm;
		int count = 0;

		try {
		con.setAutoCommit(false);
		
		if(acao.equalsIgnoreCase("update")){
			sql = "update previsao_sp set quantidade = ? where ano = ? and codigo = ?";
				pstm = con.prepareStatement(sql);
			for(Demanda i : itens){
				pstm.setInt(1, i.getQtdprevsp());
				pstm.setString(2, i.getAno());
				pstm.setString(3, i.getItemdemanda().getItem().getCodigo());
				pstm.addBatch();
				count++;
				if(count%100 == 0){
					pstm.executeBatch();
				}
			}
			pstm.executeBatch();
			con.commit();
			pstm.close();
		}else{
			pstm = con.prepareStatement(sql);
			for (Demanda i : itens){
				pstm.setString(1, i.getItemdemanda().getItem().getCodigo());
				pstm.setString(2, i.getAno());
				pstm.setInt(3, i.getQtdprevsp());
				pstm.addBatch();
				count++;
				if(count%100 == 0){
					pstm.executeBatch();
				}
			}
			pstm.executeBatch();
			con.commit();
			pstm.close();
		}
		
		con.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}
	
	
}
