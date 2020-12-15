package br.ftd.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ftd.entity.Doacao;
import br.ftd.entity.Escola;
import br.ftd.entity.ItemOrcamento;
import br.ftd.entity.Orcamento;
import br.ftd.entity.Produto;
import br.ftd.entity.Professor;
import br.ftd.entity.Usuario;
import br.ftd.factory.ConnectionFactory;

public class DAODoacao {
	
	public int salvar(Doacao doacao){
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "insert into doacao (emissao, idprofessor, idusuario, idescola) "
				+ "values (?,?,?,?)";
		String sql2 = "delete from item_doacao where iddoacao = "+doacao.getId();
		String sql3 = "insert into item_doacao (iddoacao, codigo, qtde, preco) "
				+ "values (?,?,?,?)";
		int iddoacao = 0;
		
		try {
			
			PreparedStatement pstm;
			con.setAutoCommit(false);						

			if(doacao.getId()>0){
				iddoacao = doacao.getId();
				Statement stm = con.createStatement();
				stm.execute(sql2);
				stm.close();
				
				pstm = con.prepareStatement(sql3);
				for(ItemOrcamento i : doacao.getItens()){
					pstm.setInt(1, iddoacao);
					pstm.setString(2, i.getProduto().getCodigo());
					pstm.setInt(3, i.getQuantidade());
					pstm.setFloat(4, i.getProduto().getPreco());
					pstm.addBatch();
				}
				pstm.executeBatch();
				pstm.close();

			}else{
				pstm = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				pstm.setDate(1, doacao.getEmissao());
				pstm.setInt(2, doacao.getProfessor().getId());
				pstm.setInt(3, doacao.getUsuario().getId());
				pstm.setInt(4, doacao.getEscola().getId());
				pstm.execute();				

				ResultSet rs = pstm.getGeneratedKeys();
				if(rs.next())
					iddoacao = rs.getInt(1);
				
				pstm.close();

				pstm = con.prepareStatement(sql3);
				for(ItemOrcamento i : doacao.getItens()){
					pstm.setInt(1, iddoacao);
					pstm.setString(2, i.getProduto().getCodigo());
					pstm.setInt(3, i.getQuantidade());
					pstm.setFloat(4, i.getProduto().getPreco());
					pstm.addBatch();
				}
				pstm.executeBatch();
				pstm.close();
			}
			con.commit();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return iddoacao;
	}

	
	public String deletar(Doacao doacao){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "delete from doacao where iddoacao = ?";
		String mensagem = "Doacao "+doacao.getId()+" deletada com sucesso!";
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, doacao.getId());
			pstm.execute();
			pstm.close();
			
			sql = "delete from item_doacao where iddoacao = ?";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, doacao.getId());
			pstm.execute();
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			mensagem = "Ocorreu um erro! "+e.getMessage()+"!";
		}
		
		return mensagem;
	}
	
	public static void recarrega(Doacao doacao){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		String sql = "select iddoacao, emissao, id, u.nome, setor, idprofessor, nomeprofessor, idescola, escola, reimpressao from usuario u natural join "
				+ "(select iddoacao, emissao, setor, idprofessor, nomeprofessor, idescola, e.nome as escola, reimpressao from escola e natural join "
				+ "(select iddoacao, emissao, idprofessor, nomeprofessor, idescola, reimpressao from doacao natural join professor)"
				+ " as t having iddoacao = ?) as r";
		
		String sql1 = "select a.iddoacao, a.codigo, p.descricao, a.qtde, a.preco  from item_doacao a join produto p on a.codigo = p.codigo and iddoacao = ?";
		
		Professor professor = new Professor();
		Escola escola = new Escola();
		Usuario usuario = new Usuario();
		List<ItemOrcamento> itens = new ArrayList<>();
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, doacao.getId());
			ResultSet rs = pstm.executeQuery();
			if(rs.next()){
				doacao.setId(rs.getInt("iddoacao"));
				doacao.setEmissao(rs.getDate("emissao"));
				doacao.setReimpressao(rs.getInt("reimpressao"));
				DAOUtils.setReimpressao(doacao.getId(), doacao.getReimpressao());
				professor.setId(rs.getInt("idprofessor"));
				DAOProfessor.recarrega(professor);
				escola.setId(rs.getInt("idescola"));
				escola.setNome(rs.getString("escola"));
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setSetor(rs.getInt("setor"));				
			}
			rs.close();
			pstm.close();
			
			pstm = con.prepareStatement(sql1);
			pstm.setInt(1, doacao.getId());
			rs = pstm.executeQuery();
			while(rs.next()){
				ItemOrcamento item = new ItemOrcamento();
				Produto produto = new Produto();
				produto.setCodigo(rs.getString("codigo"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setPreco(rs.getFloat("preco"));
				item.setProduto(produto);
				item.setQuantidade(rs.getInt("qtde"));
				itens.add(item);
			}
			
			doacao.setEscola(escola);
			doacao.setProfessor(professor);
			doacao.setUsuario(usuario);
			doacao.setItens(itens);
			doacao.refazTotal();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public List<Doacao> listar(Usuario usuario, Date dataini, Date datafim){
		List<Doacao> lista = new ArrayList<>();
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		String sql = "select iddoacao, idprofessor, idescola, emissao, setor, nomeprofessor, qtd, total from"
				+ " professor natural join (select iddoacao, idprofessor, idescola, emissao, setor from doacao"
				+ " natural join escola where setor = ? and emissao between ? and ?) as n natural join"
				+ " (select iddoacao, sum(qtde) as qtd, sum(qtde*preco) as total from item_doacao group by iddoacao) as t"
				+ " order by iddoacao;";
		
		String sql1 = "select iddoacao, idprofessor, idescola, emissao, setor, nomeprofessor, qtd, total from"
				+ " professor natural join (select iddoacao, idprofessor, idescola, emissao, setor from doacao"
				+ " natural join escola where setor > ? and emissao between ? and ?) as n natural join"
				+ " (select iddoacao, sum(qtde) as qtd, sum(qtde*preco) as total from item_doacao group by iddoacao) as t"
				+ " order by iddoacao;";
		
		PreparedStatement pstm = null;
		try {		
			if(usuario.getSetor() == 0){
				pstm = con.prepareStatement(sql1);				
			}else{
				pstm = con.prepareStatement(sql);
			}
			
			pstm.setInt(1, usuario.getSetor());
			pstm.setDate(2, dataini);
			pstm.setDate(3, datafim);
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next()){
				Doacao doacao = new Doacao();
				Escola escola = new Escola();
				escola.setId(rs.getInt("idescola"));
				DAOEscola.recarrega(escola);
				Professor professor = new Professor();
				professor.setId(rs.getInt("idprofessor"));
				professor.setNome(rs.getString("nomeprofessor").toUpperCase());
				doacao.setId(rs.getInt("iddoacao"));
				doacao.setEmissao(rs.getDate("emissao"));
				doacao.setQtdtotal(rs.getInt("qtd"));
				doacao.setTotal(rs.getFloat("total"));
				Usuario u = DAOUsuario.getVendedor(rs.getInt("setor"));
				doacao.setUsuario(u);
				doacao.setEscola(escola);
				doacao.setProfessor(professor);
				lista.add(doacao);
			}
			
			rs.close();
			pstm.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}
	
	public Orcamento listar(){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select d.codigo, p.descricao, d.preco, sum(qtde) as qtd"
				+ " from doacao natural join item_doacao d natural join produto p group by d.codigo";
		
		Orcamento orcam = new Orcamento();
		List<ItemOrcamento> itens = new ArrayList<>();
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				ItemOrcamento itemorcam = new ItemOrcamento();
				Produto item = new Produto();
				item.setCodigo(rs.getString("codigo"));
				item.setDescricao(rs.getString("descricao"));
				item.setPreco(rs.getFloat("preco"));
				itemorcam.setQuantidade(rs.getInt("qtd"));
				itemorcam.setProduto(item);
				itens.add(itemorcam);
			}
			orcam.setItens(itens);
			orcam.refazTotal();
			rs.close();
			pstm.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return orcam;
	}
	
	public void gerarRelatorioAcerto(List<ItemOrcamento> lista, String[] params){
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		String sqlparams = "(";
		boolean flag = true;
		
		for(String s : params){
			if(s.equalsIgnoreCase("0")){
				sqlparams = "setor > 0";
				flag = false;				
				break;
			}else{
				sqlparams += "setor = "+s+" or ";
			}
		}
		
		if(flag){
			sqlparams = sqlparams.substring(0, sqlparams.length()-4);
			sqlparams += ")";
		}
		sqlparams += ")";
		String sql = "select w.codigo, p.descricao, w.preco, qtd from produto p"
				+ " inner join (select codigo, preco, sum(qtde) as qtd from item_doacao i"
				+ " inner join (select iddoacao, idescola from doacao where idescola in"
				+ " (select idescola from escola where "+sqlparams+") t on i.iddoacao = t.iddoacao"
				+ " and acertoftd = 0 group by codigo) w on w.codigo = p.codigo;";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()){
				ItemOrcamento item = new ItemOrcamento();
				Produto p = new Produto();
				p.setCodigo(rs.getString("codigo"));
				p.setDescricao(rs.getString("descricao"));
				p.setPreco(rs.getFloat("preco"));
				item.setProduto(p);
				item.setQuantidade(rs.getInt("qtd"));
				lista.add(item);
			}
			rs.close();
			stm.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	public static boolean marcarItensAcertados(String[] params){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		boolean flagok = false;
		String sqlparams = "(";
		boolean flag = true;
		
		for(String s : params){
			if(s.equalsIgnoreCase("0")){
				sqlparams = "setor > 0";
				flag = false;				
				break;
			}else{
				sqlparams += "setor = "+s+" or ";
			}
		}
		
		if(flag){
			sqlparams = sqlparams.substring(0, sqlparams.length()-4);
			sqlparams += ")";
		}
		sqlparams += ")";
		String sql = "update item_doacao a inner join (select i.iddoacao from item_doacao i"
				+ " inner join (select iddoacao, idescola from doacao where idescola in"
				+ " (select idescola from escola where "+sqlparams+") t on i.iddoacao = t.iddoacao"
				+ " and acertoftd = 0 group by iddoacao) b on a.iddoacao = b.iddoacao set a.acertoftd = 1";
		try {
			Statement stm = con.createStatement();
			flagok = stm.execute(sql);
			stm.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flagok;
	}
}
