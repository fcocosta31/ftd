package br.ftd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ftd.entity.Usuario;
import br.ftd.factory.ConnectionFactory;

public class DAOUsuario {
	
	public DAOUsuario() {
		
	}
	
	public String salvar(Usuario usuario){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "insert into usuario (nome, email, setor, cargo, idempresa, login, senha) values (?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, usuario.getNome());
			pstm.setString(2, usuario.getEmail());
			pstm.setInt(3, usuario.getSetor());
			pstm.setInt(4, usuario.getCargo());
			pstm.setInt(5, usuario.getIdempresa());
			pstm.setString(6, usuario.getLogin());
			pstm.setString(7, usuario.getSenha());
			pstm.execute();
			pstm.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return "Erro ao salvar o usuario! "+e.getMessage();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return "Usuario salvo com sucesso!";
	}
	
	
	public String editar(Usuario usuario){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "update usuario set nome=?, email=?, setor=?, cargo=?, idempresa=? where id=?";
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, usuario.getNome());
			pstm.setString(2, usuario.getEmail());			
			pstm.setInt(3, usuario.getSetor());
			pstm.setInt(4, usuario.getCargo());
			pstm.setInt(5, usuario.getIdempresa());
			pstm.setInt(6, usuario.getId());
			pstm.executeUpdate();
			pstm.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return "Erro ao tentar alterar o usuario!";
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "Usuario alterado com sucesso!";
	}
	
	public String deletar(Usuario usuario){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "update usuario set inativo=1 where id=?";
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, usuario.getId());
			pstm.execute();
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return "Erro!! "+e.getMessage();
		}
		
		return "Usuario deletado com sucesso!";
	}
	
	public Usuario validaUsuario(String login, String senha) {
		Connection conn = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select * from usuario where (login=? or email=?) and senha=? and inativo = 0";
		Usuario usuarioLogado = null;
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, login);
			pstm.setString(2, login);
			pstm.setString(3, senha);
			ResultSet res = pstm.executeQuery();
			if(res.next()) {
				usuarioLogado = new Usuario();
				usuarioLogado.setId(res.getInt("id"));
				usuarioLogado.setNome(res.getString("nome"));
				usuarioLogado.setCargo(res.getInt("cargo"));
				usuarioLogado.setSetor(res.getInt("setor"));
				usuarioLogado.setEmail(res.getString("email"));
				usuarioLogado.setIdempresa(res.getInt("idempresa"));
				usuarioLogado.setLogin(login);				
			}
			res.close();
			pstm.close();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return usuarioLogado;		
	}
	
	public static void recarrega(Usuario usuario){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select nome, setor, cargo, login, t.email, t.idempresa, cnpj"
				+ " from empresa e join (select * from usuario where id = ?"
				+ " ) as t on e.idempresa = t.idempresa;";
		
		try {
		
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, usuario.getId());
			ResultSet rs = pstm.executeQuery();
			if(rs.next()){
				usuario.setNome(rs.getString("nome"));
				usuario.setSetor(rs.getInt("setor"));
				usuario.setCargo(rs.getInt("cargo"));
				usuario.setLogin(rs.getString("login"));
				usuario.setEmail(rs.getString("email"));
				usuario.setIdempresa(rs.getInt("idempresa"));
				usuario.setCnpj(rs.getString("cnpj"));
			}
			rs.close();
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<Usuario> listarvendedores(Usuario usuariologado){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select * from usuario where cargo = 3 and inativo = 0 order by nome";
		List<Usuario> lista = new ArrayList<>();
		
		try {

			if(usuariologado.getCargo() == 1){
				Statement stm = con.createStatement();
				ResultSet rs = stm.executeQuery(sql);
				Usuario usuario = null;
				usuario = new Usuario();
				usuario.setId(0);
				usuario.setSetor(0);
				usuario.setCargo(2);
				usuario.setNome("Todos");
				lista.add(usuario);
				while(rs.next()){
					usuario = new Usuario();
					usuario.setId(rs.getInt("id"));
					usuario.setNome(rs.getString("nome"));
					usuario.setSetor(rs.getInt("setor"));
					usuario.setCargo(rs.getInt("cargo"));
					usuario.setLogin(rs.getString("login"));
					lista.add(usuario);
				}
				rs.close();
				stm.close();
				con.close();
			}else if(usuariologado.getCargo() == 3){
				lista.add(usuariologado);
			}else{
				Statement stm = con.createStatement();
				ResultSet rs = stm.executeQuery(sql);
				Usuario usuario = null;
				while(rs.next()){
					usuario = new Usuario();
					usuario.setId(rs.getInt("id"));
					usuario.setNome(rs.getString("nome"));
					usuario.setSetor(rs.getInt("setor"));
					usuario.setCargo(rs.getInt("cargo"));
					usuario.setLogin(rs.getString("login"));
					lista.add(usuario);
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
	
	public static Usuario getVendedor(int setor){
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select nome, setor, cargo, login, t.email, t.idempresa, cnpj"
				+ " from empresa e join (select * from usuario where setor = ?"
				+ " ) as t on e.idempresa = t.idempresa";		
		
		Usuario usuario = new Usuario();
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, setor);
			ResultSet rs = pstm.executeQuery();
			
			if(rs.next()){
				usuario.setNome(rs.getString("nome"));
				usuario.setSetor(rs.getInt("setor"));
				usuario.setCargo(rs.getInt("cargo"));
				usuario.setLogin(rs.getString("login"));
				usuario.setEmail(rs.getString("email"));
				usuario.setIdempresa(rs.getInt("idempresa"));
				usuario.setCnpj(rs.getString("cnpj"));
			}
			rs.close();
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return usuario;
	}
	
	public String verificaLogin(String nome){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select login from usuario where login = ? and inativo = 0";		
		String nomeusuario = "xxxx";
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, nome);
			ResultSet rs = pstm.executeQuery();
			
			if(rs.next()){
				nomeusuario = rs.getString("login");
			}
			rs.close();
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return nomeusuario;
	}
	
	public static Usuario getUsuario(int id) throws SQLException{
		Usuario user = new Usuario();
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select nome, setor, cargo, login, t.email, "
				+ "t.idempresa, e.razao_social, cnpj from empresa e inner join "
				+ "(select * from usuario where id = ?) as t on e.idempresa = t.idempresa;";
		
		PreparedStatement pstm;
		
		pstm = con.prepareStatement(sql);
		
		pstm.setInt(1, id);
		
		ResultSet rs = pstm.executeQuery();
		
		if(rs.next()){
			user.setId(id);
			String[] nome = rs.getString("nome").split(" ");			

			String firstname = "";
			int j = nome.length;
			for(int i = 0; i < (j - 1); i++){
				firstname += nome[i]+" ";
			}
			user.setNome(firstname);
			user.setSobrenome(nome[j-1]);
			user.setEmail(rs.getString("email"));
			user.setCargo(rs.getInt("cargo"));
			user.setSetor(rs.getInt("setor"));
			user.setIdempresa(rs.getInt("idempresa"));
			user.setNomeempresa(rs.getString("razao_social"));
			user.setCnpj(rs.getString("cnpj"));
			user.setLogin(rs.getString("login"));
		}
		rs.close();
		pstm.close();
		con.close();
		
		return user;
	}
	
	public static List<Usuario> listarTodos(){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select id, nome from usuario where inativo = 0 order by nome";
		List<Usuario> lista = new ArrayList<>();
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()){
				Usuario u = new Usuario();
				u.setId(rs.getInt("id"));
				u.setNome(rs.getString("nome"));
				lista.add(u);
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
	
	public String alterarSenha(Usuario usuario){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "update usuario set senha=? where id=?";
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, usuario.getSenha());
			pstm.setInt(2, usuario.getId());
			pstm.execute();
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return "Erro!!! "+e.getMessage();
		}
		
		return "Senha alterada com sucesso!";
	}
}
