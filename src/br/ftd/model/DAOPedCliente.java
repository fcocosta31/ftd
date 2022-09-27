package br.ftd.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import br.ftd.entity.Empresa;
import br.ftd.entity.ItemPedCliente;
import br.ftd.entity.PedCliente;
import br.ftd.entity.Produto;
import br.ftd.entity.Usuario;
import br.ftd.factory.ConnectionFactory;

public class DAOPedCliente {
		
	public static String salvar(PedCliente pedido) throws SQLException{
		
		updateNomeClienteFTD(pedido);
		
		Connection con = null;
		PreparedStatement pstm = null;
		PreparedStatement pstmitens = null;
		PreparedStatement pstmatend = null;
		String mensagem = "";
		String sqlped = "insert into pedcliente (codigoftd, nomeftd, endereco, municipio, uf, idusuario, emissao, situacao, guardarpendencia, email, user_id) values (?,?,?,?,?,?,?,?,?,?,?)";
		String sqlitem = "insert into item_pedcliente (idpedido, codigo, preco, qtdpedida, codigoftd) values (?,?,?,?,?)";
		int idpedido = 0;
		try {
			con = ConnectionFactory.getInstance().getMySqlConnection();
			con.setAutoCommit(false);
			pstm = con.prepareStatement(sqlped, PreparedStatement.RETURN_GENERATED_KEYS);
			pstm.setString(1, pedido.getCliente().getCodigoftd());
			pstm.setString(2, pedido.getCliente().getRazaosocial());
			pstm.setString(3, pedido.getCliente().getEndereco());
			pstm.setString(4, pedido.getCliente().getMunicipio());
			pstm.setString(5, pedido.getCliente().getUf());
			pstm.setInt(6, pedido.getUsuario().getId());
			pstm.setDate(7, pedido.getEmissao());
			pstm.setString(8, pedido.getSituacao());
			pstm.setInt(9, pedido.getGuardarpendencia());
			pstm.setString(10, pedido.getCliente().getEmail());
			pstm.setInt(11, pedido.getUser_id());
			pstm.execute();
			ResultSet rs = pstm.getGeneratedKeys();
			if(rs.next())
				idpedido = rs.getInt(1);
			
			pedido.setIdpedido(idpedido);
			
			System.out.println(":::::::::::::::::: Pedido nº: "+pedido.getIdpedido());
			
			pstmitens = con.prepareStatement(sqlitem);
			for(ItemPedCliente i : pedido.getItens()){
				pstmitens.setInt(1, pedido.getIdpedido());
				pstmitens.setString(2, i.getItem().getCodigo());
				pstmitens.setFloat(3, i.getItem().getPreco());
				pstmitens.setInt(4, i.getQtdpedida());
				pstmitens.setString(5, pedido.getCliente().getCodigoftd());
				pstmitens.addBatch();
			}
			pstmitens.executeBatch();
			
			Date data = new Date(System.currentTimeMillis());
			
			sqlitem = "insert into item_pedcliente_atendido (idpedido, codigo, qtdatendida, data, codigoftd) values (?,?,?,?,?)";
			pstmatend = con.prepareStatement(sqlitem);
			for(ItemPedCliente i : pedido.getItens()){
				pstmatend.setInt(1, idpedido);
				pstmatend.setString(2, i.getItem().getCodigo());
				pstmatend.setInt(3, i.getQtdatendida());
				pstmatend.setDate(4, data);
				pstmatend.setString(5, pedido.getCliente().getCodigoftd());
				pstmatend.addBatch();
			}
			pstm.executeBatch();	
			
			con.commit();
			con.setAutoCommit(true);
			
			mensagem = "Pedido no."+pedido.getIdpedido()+" salvo!"
					+ "\nDeseja imprimir?";
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			mensagem = "Pedido nao foi salvo!"
					+ "\nErro interno: "+e.getMessage()+"!!!";
			con.rollback();
		}finally {
			if(pstm != null) pstm.close();
			if(pstmitens != null) pstmitens.close();
			if(pstmatend != null) pstmatend.close();
			if(con != null) 	con.close();			
		}
		pedido.printItensPedCliente();
		return mensagem;
	}
	
	public static void updateNomeClienteFTD(PedCliente pedido) throws SQLException {

		Connection con = null;
		String sql = "select codigoftd, nomeftd from pedcliente where codigoftd = ?";
		
		PreparedStatement pstma = null;
		PreparedStatement pstmb = null;
		boolean flag = false;
		
		try {
			con = ConnectionFactory.getInstance().getMySqlConnection();
			con.setAutoCommit(false);
			pstma = con.prepareStatement(sql);
			pstma.setString(1, pedido.getCliente().getCodigoftd());
			ResultSet rs = pstma.executeQuery();
			if(rs.next()) {
				sql = "update pedcliente set nomeftd = ? where codigoftd = ?";
				flag = true;
			}
			
			if(flag) {
				pstmb = con.prepareStatement(sql);
				pstmb.setString(1, pedido.getCliente().getRazaosocial());
				pstmb.setString(2, pedido.getCliente().getCodigoftd());
				pstmb.executeUpdate();
			}
			con.commit();
			con.setAutoCommit(true);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			con.rollback();
		}finally {
			if(pstma != null) pstma.close();
			if(pstmb != null) pstmb.close();
			if(con != null) 	con.close();				
		}
	}
	
	
	public static int getAtendido(String codigo, int idpedido){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();

		String sql = "select idpedido, codigo, sum(qtdatendida) as atendido from item_pedcliente_atendido"
				+ " where idpedido = ? and codigo = ? group by codigo";
		int atendido = 0;
		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, idpedido);
			pstm.setString(2, codigo);
			ResultSet rs = pstm.executeQuery();
			if(rs.next()){
				atendido += rs.getInt("atendido");
			}
			rs.close();
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return atendido;
	}
	
	public static String alterarNomeCliente(PedCliente pedido) throws SQLException {		
		Connection con = null;
		PreparedStatement ps = null;		
		
		String sql = "update pedcliente set codigoftd = ?, nomeftd = ?, endereco = ?, "
				+ "municipio = ?, uf = ?, email = ? where idpedido = ?";
		String sql1 = "update item_pedcliente set codigoftd = ? where idpedido = ?";
		String sql2 = "update item_pedcliente_atendido set codigoftd = ? where idpedido = ?";
		
		String mensagem = "Cliente alterado com sucesso!";
		try {
			con = ConnectionFactory.getInstance().getMySqlConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql);
			ps.setString(1, pedido.getCliente().getCodigoftd());
			ps.setString(2, pedido.getCliente().getRazaosocial());
			ps.setString(3, pedido.getCliente().getEndereco());
			ps.setString(4, pedido.getCliente().getMunicipio());
			ps.setString(5, pedido.getCliente().getUf());
			ps.setString(6, pedido.getCliente().getEmail());
			ps.setInt(7, pedido.getIdpedido());
			ps.execute();
			ps.close();
			
			ps = con.prepareStatement(sql1);
			ps.setString(1, pedido.getCliente().getCodigoftd());
			ps.setInt(2, pedido.getIdpedido());
			ps.execute();
			ps.close();
			
			ps = con.prepareStatement(sql2);
			ps.setString(1, pedido.getCliente().getCodigoftd());
			ps.setInt(2, pedido.getIdpedido());
			ps.execute();
			ps.close();
			
			
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mensagem = "Erro: "+e.getMessage();
			con.rollback();
		}finally {
			if(ps != null) ps.close();
			if(con != null) 	con.close();				
		}
		
		return mensagem;
	}
	
	public static String atualizar(PedCliente pedido) throws SQLException{
		
		updateNomeClienteFTD(pedido);
		
		Connection con = null;
		PreparedStatement pstm = null;
		PreparedStatement pstmatend = null;
		String mensagem = "";
		String sqlped = "update pedcliente set situacao = ?, guardarpendencia = ?, user_id = ? where idpedido= ?";
		String sqlitem = "";
		
		int idpedido = pedido.getIdpedido();
		Date data = new Date(System.currentTimeMillis());
		
		try {
			con = ConnectionFactory.getInstance().getMySqlConnection();
			con.setAutoCommit(false);
			pstm = con.prepareStatement(sqlped);
			pstm.setString(1, pedido.getSituacao());
			pstm.setInt(2, pedido.getGuardarpendencia());
			pstm.setInt(3, pedido.getUser_id());
			pstm.setInt(4, idpedido);
			pstm.execute();
					
			sqlitem = "insert into item_pedcliente_atendido (idpedido, codigo, qtdatendida, data, codigoftd) values (?,?,?,?,?)";
			pstmatend = con.prepareStatement(sqlitem);

			for(ItemPedCliente i : pedido.getItens()){
				pstmatend.setInt(1, idpedido);
				pstmatend.setString(2, i.getItem().getCodigo());
				pstmatend.setInt(3, i.getQtdatendida());
				pstmatend.setDate(4, data);
				pstmatend.setString(5, pedido.getCliente().getCodigoftd());
				pstmatend.addBatch();				
			}
						
			pstmatend.executeBatch();
			
			con.commit();
			con.setAutoCommit(true);
			
			mensagem = "Pedido no."+idpedido+" atualizado!";
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			mensagem = "Pedido nao foi atualizado!"
					+ "\nErro interno: "+e.getMessage()+"!!!";
			con.rollback();
		}finally {
			if(pstm != null) pstm.close();
			if(pstmatend != null) pstmatend.close();
			if(con != null) 	con.close();				
		}
		pedido.printItensPedCliente();
		return mensagem;		
	}
	
	public static String updateSituacao(PedCliente pedido) throws SQLException{
		
		Connection con = null;
		PreparedStatement pstm = null;
		String mensagem = "";
		String sql = "update pedcliente set guardarpendencia = ?, situacao = ?, cancelado = ?, user_id = ? where idpedido = ?";
		
		try {
			con = ConnectionFactory.getInstance().getMySqlConnection();
			con.setAutoCommit(false);
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, pedido.getGuardarpendencia());
			pstm.setString(2, pedido.getSituacao());
			pstm.setInt(3, pedido.getCancelado());
			pstm.setInt(4, pedido.getUser_id());
			pstm.setInt(5, pedido.getIdpedido());
			pstm.executeUpdate();
			
			con.commit();
			con.setAutoCommit(true);
			
			mensagem = "Pedido no."+pedido.getIdpedido()+" atualizado com sucesso!";
			
			updateItens(pedido.getItens(), pedido.getIdpedido());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			mensagem = "Pedido nao foi atualizado!"
					+ "\nErro interno: "+e.getMessage()+"!!!";
			con.rollback();
		}finally {
			if(pstm != null) pstm.close();
			if(con != null) 	con.close();				
		}
		
		return mensagem;
	}
	
	public static void updateItens(List<ItemPedCliente> itens, int idpedido) throws SQLException{
		Connection con = null;
		String sql = "";
		PreparedStatement pstm = null;
		
		try {
			sql = "update item_pedcliente set qtdpedida = ?, qtdatendida = ?, cancelado = ?"
					+ " where idpedido = ? and codigo = ?";
			con = ConnectionFactory.getInstance().getMySqlConnection();
			con.setAutoCommit(false);
			pstm = con.prepareStatement(sql);
			
			for(ItemPedCliente i : itens){
				pstm.setInt(1, i.getQtdpedida());
				pstm.setInt(2, i.getQtdatendida());
				pstm.setInt(3, i.getCancelado());
				pstm.setInt(4, idpedido);
				pstm.setString(5, i.getItem().getCodigo());
				pstm.addBatch();
			}
			pstm.executeBatch();
			con.commit();
			con.setAutoCommit(true);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			con.rollback();
		}finally {
			if(pstm != null) pstm.close();
			if(con != null) 	con.close();				
		}
	}
	
	public static void recarrega(PedCliente pedido) throws SQLException{
		Connection con = null;
		PreparedStatement pstm = null;
		PreparedStatement pstma = null;
		PreparedStatement pstmb = null;
		String sql = "select idpedido, codigoftd, nomeftd, endereco, municipio, uf, idusuario, emissao, guardarpendencia, situacao, email from"
				+ " pedcliente where idpedido = ?";
		
		Map<String, Integer> map = new HashMap<>();
		
		try {
			con = ConnectionFactory.getInstance().getMySqlConnection();
			con.setAutoCommit(false);
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, pedido.getIdpedido());
			ResultSet rs = pstm.executeQuery();
			if(rs.next()){
				Empresa e = new Empresa();
				Usuario u = new Usuario();
				e.setCodigoftd(rs.getString("codigoftd"));
				e.setRazaosocial(rs.getString("nomeftd"));
				e.setEndereco(rs.getString("endereco"));
				e.setMunicipio(rs.getString("municipio"));
				e.setUf(rs.getString("uf"));
				e.setEmail(rs.getString("email"));
				pedido.setCliente(e);
				u.setId(rs.getInt("idusuario"));
				pedido.setUsuario(u);
				pedido.setEmissao(rs.getDate("emissao"));
				pedido.setGuardarpendencia(rs.getInt("guardarpendencia"));
				pedido.setSituacao(rs.getString("situacao"));
			}
			rs.close();
			
			DAOUsuario.recarrega(pedido.getUsuario());
			
			sql = "select codigo, preco, qtdpedida from item_pedcliente"
					+ " where idpedido = ?";
			pstma = con.prepareStatement(sql);
			pstma.setInt(1, pedido.getIdpedido());
			ResultSet rsa = pstm.executeQuery();
			rsa = pstma.executeQuery();
			while(rsa.next()){
				ItemPedCliente i = new ItemPedCliente();
				Produto p = new Produto();
				p.setCodigo(rsa.getString("codigo"));
				DAOProduto.recarrega(p);
				DAOProduto.setEstoque(p);
				p.setPreco(rsa.getFloat("preco"));
				i.setQtdpedida(rsa.getInt("qtdpedida"));				
				i.refazPendente();
				p.refazNivelEstoquePedCliente(i.getQtdpendente());
				i.setItem(p);				
				pedido.getItens().add(i);
			}
			rsa.close();

			sql = "select idpedido, codigo, sum(qtdatendida) as atendido from item_pedcliente_atendido"
					+ " where idpedido = ? group by idpedido, codigo";
			pstmb = con.prepareStatement(sql);
			pstmb.setInt(1, pedido.getIdpedido());
			ResultSet rsb = pstm.executeQuery();
			rsb = pstmb.executeQuery();
			while(rsb.next()){
				map.put(rsb.getString("codigo"), rsb.getInt("atendido"));
			}
			rsb.close();
			con.commit();
			con.setAutoCommit(true);
			
			for(ItemPedCliente i : pedido.getItens()){
				if(map.get(i.getItem().getCodigo())!=null){
					i.setQtdatendida(map.get(i.getItem().getCodigo()));
					i.refazPendente();
				}
			}			
			
			pedido.refazTotal();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			con.rollback();
		}finally {
			if(pstm != null) pstm.close();
			if(pstma != null) pstma.close();
			if(pstmb != null) pstmb.close();
			if(con != null) 	con.close();				
		}
	}
	

	public static void recarrega(PedCliente pedido, Date atendimento) throws SQLException{
		Connection con = null;
		PreparedStatement pstm = null;
		PreparedStatement pstma = null;
		PreparedStatement pstmb = null;
		String sql = "select idpedido, codigoftd, nomeftd, endereco, municipio, uf, idusuario, emissao, guardarpendencia, situacao, email from"
				+ " pedcliente where idpedido = ?";
		
		Map<String, Integer> map = new HashMap<>();
		
		try {
			con = ConnectionFactory.getInstance().getMySqlConnection();
			con.setAutoCommit(false);
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, pedido.getIdpedido());
			ResultSet rs = pstm.executeQuery();
			if(rs.next()){
				Empresa e = new Empresa();
				Usuario u = new Usuario();
				e.setCodigoftd(rs.getString("codigoftd"));
				e.setRazaosocial(rs.getString("nomeftd"));
				e.setEndereco(rs.getString("endereco"));
				e.setMunicipio(rs.getString("municipio"));
				e.setUf(rs.getString("uf"));
				e.setEmail(rs.getString("email"));
				pedido.setCliente(e);
				u.setId(rs.getInt("idusuario"));
				pedido.setUsuario(u);
				pedido.setEmissao(rs.getDate("emissao"));
				pedido.setGuardarpendencia(rs.getInt("guardarpendencia"));
				pedido.setSituacao(rs.getString("situacao"));
			}
			rs.close();

			DAOUsuario.recarrega(pedido.getUsuario());
			
			sql = "select a.codigo, a.preco, (a.qtdpedida - ifnull(b.qtdeatend,0)) AS qtdeped "
					+ "from item_pedcliente a left join (select idpedido, codigo, sum(qtdatendida) AS "
					+ "qtdeatend from item_pedcliente_atendido where data < ? and "
					+ "idpedido = ? group by idpedido, codigo) AS b on a.idpedido = b.idpedido "
					+ "and a.codigo = b.codigo where a.idpedido = ? and (a.qtdpedida - ifnull(b.qtdeatend,0)) > 0";
			pstma = con.prepareStatement(sql);
			pstma.setDate(1, atendimento);
			pstma.setInt(2, pedido.getIdpedido());
			pstma.setInt(3, pedido.getIdpedido());
			rs = pstma.executeQuery();
			while(rs.next()){
				ItemPedCliente i = new ItemPedCliente();
				Produto p = new Produto();
				p.setCodigo(rs.getString("codigo"));
				DAOProduto.recarrega(p);
				DAOProduto.setEstoque(p);
				p.setPreco(rs.getFloat("preco"));
				i.setQtdpedida(rs.getInt("qtdeped"));				
				i.refazPendente();
				p.refazNivelEstoquePedCliente(i.getQtdpendente());
				i.setItem(p);				
				pedido.getItens().add(i);
			}
			rs.close();


			sql = " select codigo, sum(qtdatendida) as qtdeatend from "
					+ "item_pedcliente_atendido where data = ? and "
					+ "idpedido = ? group by idpedido, codigo;";
			pstmb = con.prepareStatement(sql);
			pstmb.setDate(1, atendimento);
			pstmb.setInt(2, pedido.getIdpedido());
			rs = pstmb.executeQuery();
			while(rs.next()){
				map.put(rs.getString("codigo"), rs.getInt("qtdeatend"));
			}
			rs.close();
			
			con.commit();
			con.setAutoCommit(true);
			
			for(ItemPedCliente i : pedido.getItens()){
				if(map.get(i.getItem().getCodigo())!=null){
					i.setQtdatendida(map.get(i.getItem().getCodigo()));
					i.refazPendente();
				}
			}			
			
			pedido.refazTotal();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			con.close();
		}finally {
			if(pstm != null) pstm.close();
			if(pstma != null) pstma.close();
			if(pstmb != null) pstmb.close();
			if(con != null) 	con.close();				
		}
	}

	
	public static List<PedCliente> listar(Usuario usuario, Date inicio, Date fim, String codigoftd) throws SQLException{
		
		Connection con = null;
		PreparedStatement pstm = null;
		String sql = "";
		
		List<PedCliente> lista = new ArrayList<>();
		
		try{
			con = ConnectionFactory.getInstance().getMySqlConnection();
			con.setAutoCommit(false);
			
			if(usuario.getCargo() < 4){
				if(codigoftd.equalsIgnoreCase("todos")){
					sql = "select idpedido, codigoftd, nomeftd, endereco, municipio, uf, idusuario, emissao, guardarpendencia, situacao from"
						+ " pedcliente where emissao between ? and ?";
					pstm = con.prepareStatement(sql);
					pstm.setDate(1, inicio);
					pstm.setDate(2, fim);
				}else{
					sql = "select idpedido, codigoftd, nomeftd, endereco, municipio, uf, idusuario, emissao, guardarpendencia, situacao from"
							+ " pedcliente where emissao between ? and ? and codigoftd = ?";
						pstm = con.prepareStatement(sql);
						pstm.setDate(1, inicio);
						pstm.setDate(2, fim);
						pstm.setString(3, codigoftd);
				}
			}else{
				if(codigoftd.equalsIgnoreCase("todos")){
					sql = "select idpedido, codigoftd, nomeftd, endereco, municipio, uf, idusuario, emissao, guardarpendencia, situacao from"
							+ " pedcliente where idusuario = ? and emissao between ? and ?";
					pstm = con.prepareStatement(sql);
					pstm.setInt(1, usuario.getId());
					pstm.setDate(2, inicio);
					pstm.setDate(3, fim);			
				}else{
					sql = "select idpedido, codigoftd, nomeftd, endereco, municipio, uf, idusuario, emissao, guardarpendencia, situacao from"
							+ " pedcliente where idusuario = ? and emissao between ? and ? and codigoftd = ?";
					pstm = con.prepareStatement(sql);
					pstm.setInt(1, usuario.getId());
					pstm.setDate(2, inicio);
					pstm.setDate(3, fim);
					pstm.setString(4, codigoftd);
				}			
			}
		
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next()){
				PedCliente pedido = new PedCliente();
				Empresa cliente = new Empresa();
				Usuario u = new Usuario();
				pedido.setIdpedido(rs.getInt("idpedido"));
				cliente.setCodigoftd(rs.getString("codigoftd"));
				cliente.setRazaosocial(rs.getString("nomeftd"));
				cliente.setEndereco(rs.getString("endereco"));
				cliente.setMunicipio(rs.getString("municipio"));
				cliente.setUf(rs.getString("uf"));
				u.setId(rs.getInt("idusuario"));
				//DAOUsuario.recarrega(u);
				pedido.setCliente(cliente);
				pedido.setUsuario(u);
				pedido.setEmissao(rs.getDate("emissao"));
				pedido.setGuardarpendencia(rs.getInt("guardarpendencia"));
				pedido.setSituacao(rs.getString("situacao"));
				lista.add(pedido);
			}
			rs.close();
			
			con.commit();
			con.setAutoCommit(true);
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			con.rollback();
		}finally {
			if(pstm != null) pstm.close();
			if(con != null) 	con.close();			
		}
		
		return lista;
	}
	
	
	public static boolean removerItem(ItemPedCliente item, int idpedido) throws SQLException{

		Connection con = null;
		PreparedStatement pstm = null;
		PreparedStatement pstma = null;
		
		String sql = "delete from item_pedcliente where idpedido = ? and codigo = ?";

		try {
			con = ConnectionFactory.getInstance().getMySqlConnection();
			
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery("select idpedido, codigo, qtdatendida, data from item_pedcliente_atendido where "
					+ "idpedido = "+idpedido+" and codigo = '"+item.getItem().getCodigo()+"' and qtdatendida > 0");
			System.out.println(":::::::::::::::::: HISTÓRICO DE ATENDIMENTO DO ITEM :::::::::::::::: ");
			while(rs.next()) {
				System.out.println(":::::::::::::::::: Data: "+rs.getDate("data")+" ::: Quantidade: "+rs.getInt("qtdatendida"));				
			}
			rs.close();
			stm.close();
			
			con.setAutoCommit(false);
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, idpedido);
			pstm.setString(2, item.getItem().getCodigo());
			pstm.executeUpdate();
			
			sql = "delete from item_pedcliente_atendido where idpedido = ? and codigo = ?";
			pstma = con.prepareStatement(sql);
			pstma.setInt(1, idpedido);
			pstma.setString(2, item.getItem().getCodigo());
			pstma.executeUpdate();
			
			con.commit();
			con.setAutoCommit(true);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			con.rollback();
		}finally {
			if(pstm != null) pstm.close();
			if(pstma != null) pstma.close();
			if(con != null) 	con.close();
		}
		
		return DAOPedCliente.isPedClienteVazio(idpedido);			
	}
	

	public static String adicionaItem(ItemPedCliente item, int idpedido, boolean flag) throws SQLException{
		
		PedCliente pedido = new PedCliente();
		pedido.setIdpedido(idpedido);
		DAOPedCliente.recarrega(pedido);
		Connection con = null;
		PreparedStatement pstm = null;
		//Date data = new Date(System.currentTimeMillis());
		String mensagem = "1";
		String sql = "insert into item_pedcliente (idpedido, codigo, preco, qtdpedida, codigoftd) values (?,?,?,?,?)";
		
		try {
			con = ConnectionFactory.getInstance().getMySqlConnection();
			con.setAutoCommit(false);
			if(flag) {
				sql = "update item_pedcliente set qtdpedida = ? where idpedido = ? and codigo = ?";
				pstm = con.prepareStatement(sql);
				pstm.setInt(1, item.getQtdpedida());
				pstm.setInt(2, idpedido);
				pstm.setString(3, item.getItem().getCodigo());				
			}else {
				pstm = con.prepareStatement(sql);
				pstm.setInt(1, idpedido);
				pstm.setString(2, item.getItem().getCodigo());
				pstm.setFloat(3, item.getItem().getPreco());
				pstm.setInt(4, item.getQtdpedida());
				pstm.setString(5, pedido.getCliente().getCodigoftd());
			}
			pstm.execute();
			con.commit();
			con.setAutoCommit(true);
			
			/*
			sql = "insert into item_pedcliente_atendido (idpedido, codigo, qtdatendida, data) values (?,?,?,?)";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, idpedido);
			pstm.setString(2, item.getItem().getCodigo());
			pstm.setInt(3, 0);
			pstm.setDate(4, data);
			pstm.execute();
			pstm.close();
			*/
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			mensagem = "0";
			e.printStackTrace();
			con.rollback();
		}finally {
			if(pstm != null) pstm.close();
			if(con != null) 	con.close();			
		}
		
		return mensagem;
	}
	
	
	public static List<Date> getDatasAtendimentoPedido(int idpedido) {
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		String sql = "select distinct idpedido, data from item_pedcliente_atendido where idpedido = ? order by data desc";
		
		List<Date> lista = new ArrayList<>();
		
		try {
			
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, idpedido);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				lista.add(rs.getDate("data"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}	
	
	
	public static void downloadPedClienteCSV(HttpServletResponse response, String idpedido) throws SQLException{
		
		String nomeArquivo = "orcam.csv";
		
		response.setContentType("text/csv;vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename="+nomeArquivo);
		response.setHeader("Set-Cookie", "fileDownload=true; path=/");
		
		ServletOutputStream fout = null;
		
		String sql = "select idpedido, codigo, (qtdped - qtdatend) as pendente from "
				+ "(select idpedido, codigo, sum(qtdpedida) as qtdped, sum(qtdatendida) "
				+ "as qtdatend from item_pedcliente natural join item_pedcliente_atendido "
				+ "where idpedido = "+idpedido+" group by codigo) as n where qtdped > qtdatend";
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		try {
			
			fout = response.getOutputStream();

			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
					
			String codigo;
			int quant;
			
            byte[] linha;
            String linhaarq = "";
                
            while(rs.next()){
                linhaarq = "";
                codigo = rs.getString("codigo");
                quant = rs.getInt("pendente");
                
                linhaarq = codigo+";"+quant+";";
                linhaarq = linhaarq + "\r\n";
                linha = linhaarq.getBytes();
                fout.write(linha);
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
	
	public static String delete(int idpedido) throws SQLException{
		
		Connection con = null;
		Statement stm = null;
		
		String sql = "delete from pedcliente where idpedido = "+idpedido;
		String sqli = "delete from item_pedcliente where idpedido = "+idpedido;
		String sqla = "delete from item_pedcliente_atendido where idpedido = "+idpedido;
		
		String mensagem = "";
		
		try {
			con = ConnectionFactory.getInstance().getMySqlConnection();
			con.setAutoCommit(false);
			stm = con.createStatement();			
			stm.executeUpdate(sql);
			stm.executeUpdate(sqli);
			stm.executeUpdate(sqla);
			con.commit();			
			
			mensagem = "Pedido "+idpedido+" deletado com sucesso!";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			mensagem = "Pedido "+idpedido+" NAO foi deletado!"
					+ "\nErro interno: "+e.getMessage()+"!!!";	
			con.rollback();
		}finally {
			if(stm != null) stm.close();
			if(con != null) con.close();
		}
		
		return mensagem;
	}
	
	public List<ItemPedCliente> listarItensAtendidos(String codigo, int idpedido){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select * from item_pedcliente_atendido where idpedido = ? and codigo = ? and qtdatendida <> 0";
		List<ItemPedCliente> itens = new ArrayList<>();
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, idpedido);
			pstm.setString(2, codigo);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				ItemPedCliente item = new ItemPedCliente();
				item.setId(rs.getInt("id"));
				item.setEmissao(rs.getDate("data"));
				item.setSdate(rs.getDate("data").toString());
				item.setQtdatendida(rs.getInt("qtdatendida"));
				itens.add(item);
			}
			rs.close();
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return itens;
	}
	
	public List<Empresa> listClientesDoPedcliente(){
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select distinct codigoftd, nomeftd from pedcliente order by nomeftd";
		List<Empresa> lista = new ArrayList<>();
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			Empresa e = new Empresa();
			e.setCodigoftd("todos");
			e.setRazaosocial("TODOS");
			lista.add(e);
			while(rs.next()){
				e = new Empresa();
				e.setCodigoftd(rs.getString("codigoftd"));
				e.setRazaosocial(rs.getString("nomeftd"));
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

	
	public List<ItemPedCliente> pedClientePendentes(String codigoftd, String tipo, Date inicio, Date fim){

		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		List<ItemPedCliente> lista = new ArrayList<>();
		
		String sql = "select familia, nivel, codigo, descricao, sum(qtdpedida) as qtdpedida, sum(qtdatd) as qtdatd, sum(qtdpend) as qtdpend from"
				+ " (select b.idpedido, b.codigoftd, b.nomeftd, b.emissao, a.familia, a.nivel, b.codigo,"
				+ " a.descricao, b.qtdpedida, b.qtdatd, b.qtdpend from produto a inner join"
				+ " (select a.idpedido, codigoftd, nomeftd, emissao, b.codigo, b.qtdpedida, b.qtdatd,"
				+ " b.qtdpend from pedcliente a inner join (select a.idpedido, a.codigo, qtdpedida,"
				+ " IFNULL(qtdatd,0) as qtdatd, (qtdpedida - IFNULL(qtdatd,0)) as qtdpend from item_pedcliente a left join (select"
				+ " idpedido, codigo, IFNULL(sum(qtdatendida),0) as qtdatd from item_pedcliente_atendido group"
				+ " by idpedido, codigo) b on a.idpedido = b.idpedido and a.codigo = b.codigo where"
				+ " (qtdpedida - IFNULL(qtdatd,0)) > 0) b on a.idpedido = b.idpedido and a.guardarpendencia = 0"
				+ " and emissao between ? and ?) b on a.codigo = b.codigo) as w"
				+ " group by familia, nivel, codigo, descricao"
				+ " order by familia, nivel, descricao";

		String sql0 = "select b.idpedido, b.codigoftd, b.nomeftd, b.emissao, a.familia, a.nivel, b.codigo,"
				+ " a.descricao, b.qtdpedida, b.qtdatd, b.qtdpend from produto a inner join"
				+ " (select a.idpedido, codigoftd, nomeftd, emissao, b.codigo, b.qtdpedida, b.qtdatd,"
				+ " b.qtdpend from pedcliente a inner join (select a.idpedido, a.codigo, qtdpedida,"
				+ " IFNULL(qtdatd,0) as qtdatd, (qtdpedida - IFNULL(qtdatd,0)) as qtdpend from item_pedcliente a left join (select"
				+ " idpedido, codigo, IFNULL(sum(qtdatendida),0) as qtdatd from item_pedcliente_atendido group"
				+ " by idpedido, codigo) b on a.idpedido = b.idpedido and a.codigo = b.codigo where"
				+ " (qtdpedida - IFNULL(qtdatd,0)) > 0) b on a.idpedido = b.idpedido and a.guardarpendencia = 0"
				+ " and emissao between ? and ?) b on a.codigo = b.codigo";


		String sql1 = "select familia, nivel, codigo,"
				+ " descricao, sum(qtdpedida) as qtdpedida, sum(qtdatd) as qtdatd, sum(qtdpend) as qtdpend from"
				+ " (select b.idpedido, b.codigoftd, b.nomeftd, b.emissao, a.familia, a.nivel, b.codigo,"
				+ " a.descricao, b.qtdpedida, b.qtdatd, b.qtdpend from produto a inner join"
				+ " (select a.idpedido, codigoftd, nomeftd, emissao, b.codigo, b.qtdpedida, b.qtdatd,"
				+ " b.qtdpend from pedcliente a inner join (select a.idpedido, a.codigo, qtdpedida,"
				+ " IFNULL(qtdatd,0) as qtdatd, (qtdpedida - IFNULL(qtdatd,0)) as qtdpend from item_pedcliente a left join (select"
				+ " idpedido, codigo, IFNULL(sum(qtdatendida),0) as qtdatd from item_pedcliente_atendido group"
				+ " by idpedido, codigo) b on a.idpedido = b.idpedido and a.codigo = b.codigo where"
				+ " (qtdpedida - IFNULL(qtdatd,0)) > 0) b on a.idpedido = b.idpedido and a.guardarpendencia = 0"
				+ " and emissao between ? and ? and codigoftd = ?) b on a.codigo = b.codigo) as w"
				+ " group by familia, nivel, codigo, descricao"
				+ " order by familia, nivel, descricao";

		String sql2 = "select b.idpedido, b.codigoftd, b.nomeftd, b.emissao, a.familia, a.nivel, b.codigo,"
				+ " a.descricao, b.qtdpedida, b.qtdatd, b.qtdpend from produto a inner join"
				+ " (select a.idpedido, codigoftd, nomeftd, emissao, b.codigo, b.qtdpedida, b.qtdatd,"
				+ " b.qtdpend from pedcliente a inner join (select a.idpedido, a.codigo, qtdpedida,"
				+ " IFNULL(qtdatd,0) as qtdatd, (qtdpedida - IFNULL(qtdatd,0)) as qtdpend from item_pedcliente a left join (select"
				+ " idpedido, codigo, IFNULL(sum(qtdatendida),0) as qtdatd from item_pedcliente_atendido group"
				+ " by idpedido, codigo) b on a.idpedido = b.idpedido and a.codigo = b.codigo where"
				+ " (qtdpedida - IFNULL(qtdatd,0)) > 0) b on a.idpedido = b.idpedido and a.guardarpendencia = 0"
				+ " and emissao between ? and ? and codigoftd = ?) b on a.codigo = b.codigo";

		
		PreparedStatement pstm;
		ResultSet rs;
		try {
			if(codigoftd.equalsIgnoreCase("todos") && tipo.equalsIgnoreCase("resumido")){				
					pstm = con.prepareStatement(sql);
					pstm.setDate(1, inicio);
					pstm.setDate(2, fim);
			}else if(codigoftd.equalsIgnoreCase("todos") && tipo.equalsIgnoreCase("detalhado")) {
					pstm = con.prepareStatement(sql0);
					pstm.setDate(1, inicio);
					pstm.setDate(2, fim);
			}else if(!codigoftd.equalsIgnoreCase("todos") && tipo.equalsIgnoreCase("resumido")) {
				pstm = con.prepareStatement(sql1);
				pstm.setDate(1, inicio);
				pstm.setDate(2, fim);
				pstm.setString(3, codigoftd);				
			}else{
				pstm = con.prepareStatement(sql2);
				pstm.setDate(1, inicio);
				pstm.setDate(2, fim);
				pstm.setString(3, codigoftd);
			}
			
			rs = pstm.executeQuery();
			while(rs.next()){
				ItemPedCliente item = new ItemPedCliente();
				Produto p = new Produto();
				if(tipo.equalsIgnoreCase("detalhado")) {

					item.setIdpedido(rs.getInt("idpedido"));
					item.setCodigoftd(rs.getString("codigoftd"));
					item.setNomeftd(rs.getString("nomeftd"));
					item.setEmissao(rs.getDate("emissao"));					

				}
				p.setCodigo(rs.getString("codigo"));
				p.setDescricao(rs.getString("descricao"));
				p.setFamilia(rs.getString("familia"));
				p.setNivel(rs.getString("nivel"));
				item.setQtdpedida(rs.getInt("qtdpedida"));
				item.setQtdatendida(rs.getInt("qtdatd"));
				item.setQtdpendente(rs.getInt("qtdpend"));
				p.refazNivelEstoquePedCliente(item.getQtdpendente());
				item.setItem(p);
				DAOProduto.setEstoque(item);
				lista.add(item);
			}			
			rs.close();
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(lista != null) Collections.sort(lista);
		
		return lista;
	}
	


	public List<ItemPedCliente> pedClienteNaoAtendidos(String codigoftd, String tipo, Date inicio, Date fim){

		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		List<ItemPedCliente> lista = new ArrayList<>();
		
		String sql = "select  a.familia"
				+ "		, a.nivel"
				+ "		, b.codigo"
				+ "		, a.descricao"
				+ "		, count(b.qtdpend) as nrpedidos"
				+ "		, sum(b.qtdpend) as totalpendente"
				+ "		, round(avg(b.qtdpend),0) as mediapendente"
				+ "		, max(b.qtdpend) as maiorpendencia"
				+ " from produto a"
				+ "	inner join	("
				+ "					select  a1.idpedido"
				+ "							, a1.codigoftd"
				+ "							, a1.nomeftd"
				+ "							, a1.emissao"
				+ "							, b1.codigo"
				+ "							, b1.qtdpedida"
				+ "							, b1.qtdatd"
				+ "							, b1.qtdpend "
				+ "					from pedcliente a1 "
				+ "						inner join ("
				+ "										select a2.idpedido"
				+ "										, a2.codigo"
				+ "										, a2.qtdpedida"
				+ "										, IFNULL(qtdatd,0) as qtdatd"
				+ "										, (a2.qtdpedida - IFNULL(b2.qtdatd,0)) as qtdpend "
				+ "										from item_pedcliente a2 "
				+ "											left join ("
				+ "														select idpedido"
				+ "														, codigo"
				+ "														, IFNULL(sum(qtdatendida),0) as qtdatd"
				+ "														from item_pedcliente_atendido"
				+ "														group by idpedido, codigo "
				+ "													) b2 on a2.idpedido = b2.idpedido and a2.codigo = b2.codigo "
				+ "										where (a2.qtdpedida - IFNULL(qtdatd,0)) > 0 "
				+ "										and a2.id in ("
				+ "													select max(ip.id) as id_item_pedido"
				+ "													from pedcliente p"
				+ "														inner join item_pedcliente ip on ip.idpedido = p.idpedido and ip.codigoftd = p.codigoftd "
				+ "													where true "
				+ "														and p.guardarpendencia = 1 "
				+ "														and p.nomeftd is not null "
				+ "														and p.emissao between ? and ? "
				+ "													group by ip.codigoftd, ip.codigo																				"
				+ "										)"
				+ "									) b1 on a1.idpedido = b1.idpedido "
				+ "				) b on a.codigo = b.codigo "
				+ "group by b.codigo, a.descricao "
				+ "order by a.familia, a.nivel, a.descricao;";

		String sql0 = "select  a.familia, a.nivel, b.codigo"
				+ "		, a.descricao"
				+ "		, b.qtdpedida"
				+ "		, b.qtdatd"
				+ "		, b.qtdpend"
				+ "		, b.idpedido"
				+ "     , b.emissao"
				+ "		, b.codigoftd"
				+ "		, b.nomeftd		"
				+ "from produto a"
				+ "	inner join	("
				+ "					select  a1.idpedido"
				+ "							, a1.codigoftd"
				+ "							, a1.nomeftd"
				+ "							, a1.emissao"
				+ "							, b1.codigo"
				+ "							, b1.qtdpedida"
				+ "							, b1.qtdatd"
				+ "							, b1.qtdpend "
				+ "					from pedcliente a1 "
				+ "						inner join ("
				+ "										select a2.idpedido"
				+ "										, a2.codigo"
				+ "										, a2.qtdpedida"
				+ "										, IFNULL(qtdatd,0) as qtdatd"
				+ "										, (a2.qtdpedida - IFNULL(b2.qtdatd,0)) as qtdpend"
				+ "										from item_pedcliente a2 "
				+ "											left join ("
				+ "														select idpedido"
				+ "														, codigo"
				+ "														, IFNULL(sum(qtdatendida),0) as qtdatd"
				+ "														from item_pedcliente_atendido"
				+ "														group by idpedido, codigo"
				+ "													) b2 on a2.idpedido = b2.idpedido and a2.codigo = b2.codigo "
				+ "										where (a2.qtdpedida - IFNULL(qtdatd,0)) > 0"
				+ "										and a2.id in ("
				+ "													select max(ip.id) as id_item_pedido"
				+ "													from pedcliente p"
				+ "														inner join item_pedcliente ip on ip.idpedido = p.idpedido and ip.codigoftd = p.codigoftd"
				+ "													where true"
				+ "														and p.guardarpendencia = 1"
				+ "														and p.nomeftd is not null"
				+ "														and p.emissao between ? and ?"
				+ "													group by ip.codigoftd, ip.codigo																				"
				+ "										)"
				+ "									) b1 on a1.idpedido = b1.idpedido "
				+ "				) b on a.codigo = b.codigo "
				+ "order by a.descricao, b.nomeftd;";


		String sql1 = "select  a.familia, a.nivel, b.codigo"
				+ "		, a.descricao"
				+ "		, b.qtdpedida"
				+ "		, b.qtdatd"
				+ "		, b.qtdpend"
				+ "		, b.idpedido"
				+ "     , b.emissao"
				+ "		, b.codigoftd"
				+ "		, b.nomeftd		"
				+ "from produto a"
				+ "	inner join	("
				+ "					select  a1.idpedido"
				+ "							, a1.codigoftd"
				+ "							, a1.nomeftd"
				+ "							, a1.emissao"
				+ "							, b1.codigo"
				+ "							, b1.qtdpedida"
				+ "							, b1.qtdatd"
				+ "							, b1.qtdpend "
				+ "					from pedcliente a1 "
				+ "						inner join ("
				+ "										select a2.idpedido"
				+ "										, a2.codigo"
				+ "										, a2.qtdpedida"
				+ "										, IFNULL(qtdatd,0) as qtdatd"
				+ "										, (a2.qtdpedida - IFNULL(b2.qtdatd,0)) as qtdpend"
				+ "										from item_pedcliente a2 "
				+ "											left join ("
				+ "														select idpedido"
				+ "														, codigo"
				+ "														, IFNULL(sum(qtdatendida),0) as qtdatd"
				+ "														from item_pedcliente_atendido"
				+ "														group by idpedido, codigo"
				+ "													) b2 on a2.idpedido = b2.idpedido and a2.codigo = b2.codigo "
				+ "										where (a2.qtdpedida - IFNULL(qtdatd,0)) > 0"
				+ "										and a2.id in ("
				+ "													select max(ip.id) as id_item_pedido"
				+ "													from pedcliente p"
				+ "														inner join item_pedcliente ip on ip.idpedido = p.idpedido and ip.codigoftd = p.codigoftd"
				+ "													where true"
				+ "														and p.guardarpendencia = 1"
				+ "														and p.nomeftd is not null"
				+ "														and p.emissao between ? and ?"
				+ "													group by ip.codigoftd, ip.codigo																				"
				+ "										) and a2.codigoftd = ?"
				+ "									) b1 on a1.idpedido = b1.idpedido "
				+ "				) b on a.codigo = b.codigo "
				+ "order by a.descricao, b.nomeftd;";
		
		PreparedStatement pstm;
		ResultSet rs;
		try {
			if(codigoftd.equalsIgnoreCase("todos") && tipo.equalsIgnoreCase("resumido")){				
					pstm = con.prepareStatement(sql);
					pstm.setDate(1, inicio);
					pstm.setDate(2, fim);
			}else if(codigoftd.equalsIgnoreCase("todos") && tipo.equalsIgnoreCase("detalhado")) {
					pstm = con.prepareStatement(sql0);
					pstm.setDate(1, inicio);
					pstm.setDate(2, fim);
			}else{
				pstm = con.prepareStatement(sql1);
				pstm.setDate(1, inicio);				
				pstm.setDate(2, fim);
				pstm.setString(3, codigoftd);				
			}
			
			rs = pstm.executeQuery();
			while(rs.next()){
				ItemPedCliente item = new ItemPedCliente();
				Produto p = new Produto();
				p.setCodigo(rs.getString("codigo"));
				p.setDescricao(rs.getString("descricao"));
				p.setFamilia(rs.getString("familia"));
				p.setNivel(rs.getString("nivel"));
				item.setItem(p);
				
				if(tipo.equalsIgnoreCase("resumido") && codigoftd.equalsIgnoreCase("todos")) {
						
					item.setNrpedidos(rs.getInt("nrpedidos"));
					item.setPendenciatotal(rs.getInt("totalpendente"));
					item.setMediapendente(rs.getInt("mediapendente"));
					item.setMaiorpendencia(rs.getInt("maiorpendencia"));

				}else {					

					item.setIdpedido(rs.getInt("idpedido"));
					item.setCodigoftd(rs.getString("codigoftd"));
					item.setNomeftd(rs.getString("nomeftd"));
					item.setEmissao(rs.getDate("emissao"));				
					item.setQtdpedida(rs.getInt("qtdpedida"));
					item.setQtdatendida(rs.getInt("qtdatd"));
					item.setQtdpendente(rs.getInt("qtdpend"));

				}
				//p.refazNivelEstoquePedCliente(item.getQtdpendente());				
				//DAOProduto.setEstoque(item);
				lista.add(item);
			}			
			rs.close();
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//if(lista != null) Collections.sort(lista);
		
		return lista;
	}
	
	
	
	public static void alterarItemAtendido(PedCliente pedido, String codigo, int quantidade, Date emissao) throws SQLException{
		Connection con = null;
		String sql = "update item_pedcliente_atendido set qtdatendida = ? where"
				+ " qtdatendida > 0 and idpedido = ? and codigo = ? and data = ?";
		
		PreparedStatement pstm = null;
		
		try {
			con = ConnectionFactory.getInstance().getMySqlConnection();
			con.setAutoCommit(false);
			
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, quantidade);
			pstm.setInt(2, pedido.getIdpedido());
			pstm.setString(3, codigo);
			pstm.setDate(4, emissao);
			pstm.execute();
			
			con.commit();
			con.setAutoCommit(true);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			con.rollback();
		}finally {
			if(pstm != null) pstm.close();
			if(con != null) con.close();
		}
		pedido.setItens(new ArrayList<>());
		recarrega(pedido);
	}

	public static void deletarItemAtendido(PedCliente pedido, int item) throws SQLException{
		Connection con = null;
		String sql = "delete from item_pedcliente_atendido where id = ?";
		
		PreparedStatement pstm = null;
		
		try {
			con = ConnectionFactory.getInstance().getMySqlConnection();
			con.setAutoCommit(false);
			
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, item);
			pstm.execute();
			
			con.commit();
			con.setAutoCommit(true);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			con.rollback();
		}finally {
			if(pstm != null) pstm.close();
			if(con != null) con.close();
		}
		pedido.setItens(new ArrayList<>());
		recarrega(pedido);
	}
	
	public static void alterarQtdeItemPedido(PedCliente pedido, String codigo, int quantidade) throws SQLException{
		Connection con = null;
		String sql = "update item_pedcliente set qtdpedida = ? where"
				+ " idpedido = ? and codigo = ?";
		
		PreparedStatement pstm = null;
		
		try {
			con = ConnectionFactory.getInstance().getMySqlConnection();
			con.setAutoCommit(false);
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, quantidade);
			pstm.setInt(2, pedido.getIdpedido());
			pstm.setString(3, codigo);
			pstm.execute();
			
			con.commit();
			con.setAutoCommit(true);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			con.rollback();
		}finally {
			if(pstm != null) pstm.close();
			if(con != null) con.close();			
		}
		pedido.setItens(new ArrayList<>());
		recarrega(pedido);
	}

	public String alteraGuardarPendencia(int idpedido, int opcao) throws SQLException {
		Connection con = null;
		String sql = "update pedcliente set guardarpendencia = ? where idpedido = ?";
		String tipo = "";
		if(opcao == 0) {
			tipo = " ativada";
		}else {
			tipo = " cancelada";
		}
		String mensagem = "Pendência do pedido nº "+idpedido+tipo+" com sucesso!";
		PreparedStatement pstm = null;	

		try {
			con = ConnectionFactory.getInstance().getMySqlConnection();
			con.setAutoCommit(false);
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, opcao);
			pstm.setInt(2, idpedido);
			pstm.execute();
			
			con.commit();
			con.setAutoCommit(true);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mensagem = "Erro: "+e.getMessage();
			con.rollback();
		}finally {
			if(pstm != null) pstm.close();
			if(con != null) con.close();			
		}				
		return mensagem;
	}

	public String cancelaGuardarPendencia(Date inicio, Date fim) throws SQLException {
		Connection con = null;
		String sql = "update pedcliente set guardarpendencia = 1 where emissao between ? and ?";

		String mensagem = "Pendências canceladas com sucesso!";
		PreparedStatement pstm = null;	

		try {
			con = ConnectionFactory.getInstance().getMySqlConnection();
			con.setAutoCommit(false);
			pstm = con.prepareStatement(sql);
			pstm.setDate(1, inicio);
			pstm.setDate(2, fim);
			pstm.execute();
			
			con.commit();
			con.setAutoCommit(true);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mensagem = "Erro: "+e.getMessage();
			con.rollback();
		}finally {
			if(pstm != null) pstm.close();
			if(con != null) con.close();			
		}				
		return mensagem;
	}
	
	
	public static boolean isPedClienteVazio(int idpedido) {
		
		Connection con = null;
		String sql = "select * from item_pedcliente where idpedido = ?";

		PreparedStatement pstm = null;
		boolean flag = false;

		try {
			con = ConnectionFactory.getInstance().getMySqlConnection();
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, idpedido);
			ResultSet rs = pstm.executeQuery();
			if (!rs.next()) {
				flag = true;
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
	
}
