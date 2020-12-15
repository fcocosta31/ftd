package br.ftd.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ftd.entity.ItemPedido;
import br.ftd.entity.NotaFiscal;
import br.ftd.entity.Params;
import br.ftd.entity.Pedido;
import br.ftd.entity.Produto;
import br.ftd.factory.ConnectionFactory;


public class DAOPedido {
	
	public DAOPedido(){}

	public boolean salvar(Pedido pedido) throws ParseException, SQLException{
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		PreparedStatement pstm = null;
		String sql = "insert into pedido (idpedido, dataped) values (?,?)";
		String sql1 = "insert into item_pedido (idpedido, codigo, qtde, previsao) values (?,?,?,?)";
		String consulta = "select idpedido from pedido where idpedido = ?";
		ArrayList<ItemPedido> itens = pedido.getItens();
		
		try {
			pstm = con.prepareStatement(consulta);
			pstm.setInt(1, pedido.getIdpedido());
			ResultSet rs = pstm.executeQuery();
			if(rs.next()){
				return false;
			}
			rs.close();
			pstm.close();
			
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, pedido.getIdpedido());
			pstm.setDate(2, pedido.getData());
			pstm.execute();
			pstm.close();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		

		try {
			pstm = con.prepareStatement(sql1);
			
				for(ItemPedido p : itens){
					pstm.setInt(1, pedido.getIdpedido());
					pstm.setString(2, p.getItem().getCodigo());
					pstm.setInt(3, p.getQtdpedida());
					pstm.setDate(4, p.getPrevisao());
					pstm.addBatch();
				}
				pstm.executeBatch();
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
	

	public boolean update(List<ItemPedido> itens) {
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		PreparedStatement pstm = null;
		String sql = "update item_pedido set previsao = ?, observacao = ? where idpedido = ? and codigo = ?";
		
		try {
			pstm = con.prepareStatement(sql);
			
			for(ItemPedido i : itens) {
				pstm.setDate(1, i.getPrevisao());
				pstm.setString(2, i.getObservacao());
				pstm.setInt(3, i.getIdpedido());
				pstm.setString(4, i.getItem().getCodigo());
				pstm.addBatch();
			}
			pstm.executeBatch();
			pstm.close();
			con.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		
		return true;
	}

	
	
	public String deletar(Pedido pedido){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "delete from pedido where idpedido = "+pedido.getIdpedido();
		String sqli = "delete from item_pedido where idpedido = "+pedido.getIdpedido();
		String sqln = "delete from notapedido where idpedido = "+pedido.getIdpedido();
		
		try {
			Statement stm = con.createStatement();
			stm.addBatch(sql);
			stm.addBatch(sqli);
			stm.addBatch(sqln);
			stm.executeBatch();
			stm.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return "Erro: "+e.getMessage()+" \n"
					+ "pedido "+pedido.getIdpedido()+" nao foi deletado!";
		}
		
		return "Pedido "+pedido.getIdpedido()+" deletado com sucesso!";
	}
	
	public ArrayList<Pedido> listar(Date dtini, Date dtfim) throws SQLException{
		ArrayList<Pedido> pedidos = new ArrayList<>();
		String sql = "select idpedido, dataped, itens, qtdtotal from pedido natural join (select idpedido, count(codigo) as itens,"
				+ " sum(qtde) as qtdtotal from item_pedido group by idpedido) as n"
				+ " where dataped between ? and ?";
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		try {
			
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setDate(1, dtini);
			pstm.setDate(2, dtfim);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				int idpedido = rs.getInt(1);
				Date dataped = rs.getDate(2);
				Pedido pedido = new Pedido();
				pedido.setIdpedido(idpedido);
				pedido.setData(dataped);
				pedido.setQtitens(rs.getInt("itens"));
				pedido.setQttotal(rs.getInt("qtdtotal"));
				pedidos.add(pedido);
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			con.close();
		}
		
		return pedidos;
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

	public ArrayList<Pedido> listar(Produto produto, String ano) throws SQLException{
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		int anoant = Integer.parseInt(ano)-1;
		
		Date ini = convertSQLStringToDate((anoant)+"0701");
		Date fim = convertSQLStringToDate(ano+"0630");
		
		ArrayList<Pedido> pedidos = null;
		
		String sql = "select d.idpedido, d.codigo, d.qtde, d.previsao, d.cancelado, d.observacao, c.dataped"
				+ " from pedido c inner join (select b.idpedido, a.codigo, b.qtde, b.previsao, b.cancelado,"
				+ " b.observacao from produto a inner join item_pedido b on a.codigo = b.codigo and a.codigo"
				+ " = '"+produto.getCodigo()+"') as d on c.idpedido = d.idpedido and c.dataped between"
						+ " '"+ini+"' and '"+fim+"' order by d.idpedido desc";

		try {
			
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			boolean flag = true;
			Pedido pedido = null;
			ArrayList<ItemPedido> itenspedido = null;
			DAOProduto daop = new DAOProduto();
			pedidos = new ArrayList<>();
			
			while(rs.next()){
								
				if(rs.getInt("qtde")>0){
					flag = false;
					pedido = new Pedido();
					itenspedido = new ArrayList<>();
					ItemPedido itempedido = new ItemPedido();
					itempedido.setQtdpedida(rs.getInt("qtde"));
					itempedido.setPrevisao(rs.getDate("previsao"));
					itempedido.setItem(produto);
					itempedido.setCancelado(rs.getInt("cancelado"));
					itempedido.setObservacao(rs.getString("observacao"));
					pedido.setIdpedido(rs.getInt("idpedido"));
					pedido.setData(rs.getDate("dataped"));
					daop.setNotasQtdeRecebida(pedido, itempedido);
					itempedido.refazTotalQtdChegou();
					itenspedido.add(itempedido);
					pedido.setItens(itenspedido);
					pedidos.add(pedido);
				}
				
			}
			if(flag){pedidos = null;}
			rs.close();
			stm.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			con.close();
		}
		
		return pedidos;
	}
	
	
	public Pedido detalhar(int idpedido) throws SQLException{
		Pedido pedido = new Pedido();
		ArrayList<ItemPedido> itens = new ArrayList<>();
		ArrayList<NotaFiscal> notas = new ArrayList<>();
		Map<String, Integer> map = new HashMap<String, Integer>();
		Map<String, Date> mapdt = new HashMap<String, Date>();
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select idpedido, dataped, codigo, descricao, qtde, previsao, observacao, cancelado "
				+ "from pedido natural join (item_pedido natural join produto) "
				+ "where idpedido = ?";
		
		try {

			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, idpedido);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				pedido.setIdpedido(rs.getInt("idpedido"));
				pedido.setData(rs.getDate("dataped"));
				ItemPedido itempedido = new ItemPedido();
				Produto item = new Produto();
				item.setCodigo(rs.getString("codigo"));
				item.setDescricao(rs.getString("descricao"));
				itempedido.setQtdpedida(rs.getInt("qtde"));
				itempedido.setPrevisao(rs.getDate("previsao"));
				itempedido.setItem(item);
				itempedido.setObservacao(rs.getString("observacao"));
				itempedido.setCancelado(rs.getInt("cancelado"));
				itens.add(itempedido);
			}
			
			pedido.setItens(itens);					
			
			rs.close();
			pstm.close();
			con.close();
			
			sql = "select codigo, sum(qtde) as qtdchegou, datachegada from notafiscal natural join"
					+ " (notapedido natural join item_notafiscal) where idpedido = ? group by codigo, datachegada";

			con = ConnectionFactory.getInstance().getMySqlConnection();
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, idpedido);
			rs = pstm.executeQuery();
			while(rs.next()){
				map.put(rs.getString("codigo"), rs.getInt("qtdchegou"));
				mapdt.put(rs.getString("codigo"), rs.getDate("datachegada"));
			}
			rs.close();
			pstm.close();
			con.close();
			
			for(ItemPedido itempedido : itens){
				if(map.get(itempedido.getItem().getCodigo())!=null){
					itempedido.setQtdchegou(map.get(itempedido.getItem().getCodigo()));
					itempedido.setDatachegada(mapdt.get(itempedido.getItem().getCodigo()));
					itempedido.refazPendente();
				}else{
					itempedido.refazPendente();
				}
			}			
			
			sql = "select idpedido, idnota, emissao, uf from pedido natural join (notapedido "
					+ "natural join notafiscal) where idpedido = ?";
			
			con = ConnectionFactory.getInstance().getMySqlConnection();
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, idpedido);
			rs = pstm.executeQuery();
									
			while(rs.next()){
				NotaFiscal nota = new NotaFiscal();
				nota.setIdpedido(idpedido);
				nota.setIdnota(rs.getString("idnota"));
				nota.setEmissao(rs.getDate("emissao"));
				nota.setUF(rs.getString("uf"));
				notas.add(nota);
			}
			
			if(!notas.isEmpty()){
				pedido.setNotas(notas);
			}else{
				pedido.setNotas(null);
			}
			
			rs.close();
			pstm.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			con.close();
		}
		
		return pedido;
	}
	
	public static void recarrega(ItemPedido item){

	}
	
	public String alterarItemPedido(ItemPedido item){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "update item_pedido set qtde = ?, previsao = ?, cancelado = ?, observacao = ? "
				+ "where idpedido = ? and codigo = ?";
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, item.getQtdpedida());
			pstm.setDate(2, item.getPrevisao());
			pstm.setInt(3, item.getCancelado());
			pstm.setString(4, item.getObservacao());
			pstm.setInt(5, item.getIdpedido());
			pstm.setString(6, item.getItem().getCodigo());
			
			pstm.executeUpdate();
			pstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			
			return "Erro "+e.getErrorCode()+" ao tentar alterar "+item.getItem().getCodigo();
			
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return "Item "+item.getItem().getCodigo()+" alterado com sucesso!";
	}
	
	
	public static List<String> getAnosPedido(){
		List<String> anos = new ArrayList<>();
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select distinct year(dataped) as ano from pedido";
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()){
				anos.add(rs.getString("ano"));
			}
			rs.close();
			stm.close();
			con.close();
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return anos;
	}

	public String alterarPrevisoes(Pedido pedido) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "update item_pedido set previsao = ? where idpedido = ? and codigo = ?";
		con.setAutoCommit(false);
		
		PreparedStatement ps = con.prepareStatement(sql);
		for(ItemPedido i : pedido.getItens()){
			ps.setDate(1, i.getPrevisao());
			ps.setInt(2, pedido.getIdpedido());
			ps.setString(3, i.getItem().getCodigo());
			ps.addBatch();
		}
		ps.executeBatch();
		con.commit();
		ps.close();
		con.close();
		
		return "Pedido "+pedido.getIdpedido()+" atualizado com sucesso!";
	}
	
	public List<ItemPedido> getItensEmTransito(Date inicio, Date fim) throws SQLException{
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select a.idpedido, b.idnota, b.emissao, b.codigo, b.descricao, b.qtde from notapedido a"
				+ " inner join (select a.emissao, a.idnota, b.codigo, b.descricao, b.qtde from notafiscal a"
				+ " inner join item_notafiscal b on a.idnota = b.idnota and isnull(a.datachegada)) b"
				+ " on a.idnota = b.idnota and a.idpedido > 0 and b.emissao between ? and ?";
		List<ItemPedido> lista = new ArrayList<>();
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setDate(1, inicio);
			pstm.setDate(2, fim);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				ItemPedido i = new ItemPedido();
				Produto p = new Produto();
				i.setIdpedido(rs.getInt("idpedido"));
				i.setNotafiscal(rs.getString("idnota"));
				i.setEmissaonf(rs.getDate("emissao"));
				i.setQtdchegou(rs.getInt("qtde"));
				p.setCodigo(rs.getString("codigo"));
				p.setDescricao(rs.getString("descricao"));
				i.setItem(p);
				lista.add(i);
			}
			rs.close();
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (ItemPedido itemPedido : lista) {
			DAOProduto.setEstoque(itemPedido.getItem());
		}
		
		return lista;
	}
	
	
}
