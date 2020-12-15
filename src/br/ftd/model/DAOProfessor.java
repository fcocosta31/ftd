package br.ftd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.ftd.entity.Escola;
import br.ftd.entity.Professor;
import br.ftd.entity.Usuario;
import br.ftd.factory.ConnectionFactory;

public class DAOProfessor {
	
	public boolean salvar(Professor professor){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "insert into professor (idprofessor, nomeprofessor, cargo,"
				+ " email, telefone, aniversario, endereco, bairro, municipio, uf) values (?,?,?,?,?,?,?,?,?,?)";
		String sql1 = "insert into professor_escola (idprofessor, idescola) values (?,?)";
		String sql2 = "insert into professor_nivel (idprofessor, nivel) values (?,?)";
		String sql3 = "insert into professor_disciplina (idprofessor, disciplina) values (?,?)";
		
		professor.setId(DAOUtils.getProximoId("professor"));
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, professor.getId());
			pstm.setString(2, professor.getNome());
			pstm.setString(3, professor.getCargo());
			pstm.setString(4, professor.getEmail());
			pstm.setString(5, professor.getTelefone());
			pstm.setString(6, professor.getAniversario());
			pstm.setString(7, professor.getEndereco());
			pstm.setString(8, professor.getBairro());
			pstm.setString(9, professor.getMunicipio());
			pstm.setString(10, professor.getUf());
			pstm.execute();
			pstm.close();
			
			pstm = con.prepareStatement(sql1);
			pstm.setInt(1, professor.getId());
			pstm.setInt(2, professor.getEscola().getId());
			pstm.execute();
			pstm.close();
			
			pstm = con.prepareStatement(sql2);
			for(String n : professor.getNiveis()){
				pstm.setInt(1, professor.getId());
				pstm.setString(2, n);
				pstm.addBatch();
			}
			pstm.executeBatch();
			pstm.close();
			
			pstm = con.prepareStatement(sql3);
			for(String n : professor.getDisciplinas()){
				pstm.setInt(1, professor.getId());
				pstm.setString(2, n);
				pstm.addBatch();
			}
			pstm.executeBatch();
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
				
		return true;
	}

	public boolean editar(Professor professor){
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		String delesc = "delete from professor_escola where idprofessor = "+professor.getId();
		String delniv = "delete from professor_nivel where idprofessor = "+professor.getId();
		String deldsc = "delete from professor_disciplina where idprofessor = "+professor.getId();
		
		String sql = "update professor set nomeprofessor = ?, cargo = ?, email = ?, telefone = ?,"
				+ " aniversario = ?, endereco = ?, bairro = ?, municipio = ?, uf = ?"
				+ " where idprofessor = ?";
		String sql1 = "insert into professor_escola (idprofessor, idescola) values (?,?)";
		String sql2 = "insert into professor_nivel (idprofessor, nivel) values (?,?)";
		String sql3 = "insert into professor_disciplina (idprofessor, disciplina) values (?,?)";
				
		try {
			Statement stm = con.createStatement();
			stm.addBatch(delesc);
			stm.addBatch(delniv);
			stm.addBatch(deldsc);
			stm.executeBatch();
			stm.clearBatch();
			stm.close();
			
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, professor.getNome());
			pstm.setString(2, professor.getCargo());
			pstm.setString(3, professor.getEmail());
			pstm.setString(4, professor.getTelefone());
			pstm.setString(5, professor.getAniversario());
			pstm.setString(6, professor.getEndereco());
			pstm.setString(7, professor.getBairro());
			pstm.setString(8, professor.getMunicipio());
			pstm.setString(9, professor.getUf());			
			pstm.setInt(10, professor.getId());
			pstm.execute();
			pstm.close();
			
			pstm = con.prepareStatement(sql1);
			pstm.setInt(1, professor.getId());
			pstm.setInt(1, professor.getEscola().getId());
			pstm.execute();
			pstm.close();
			
			pstm = con.prepareStatement(sql2);
			for(String n : professor.getNiveis()){
				pstm.setInt(1, professor.getId());
				pstm.setString(2, n);
				pstm.addBatch();
			}
			pstm.executeBatch();
			pstm.close();
			
			pstm = con.prepareStatement(sql3);
			for(String n : professor.getDisciplinas()){
				pstm.setInt(1, professor.getId());
				pstm.setString(2, n);
				pstm.addBatch();
			}
			pstm.executeBatch();
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
				
		return true;		
	}
	
	public boolean deletar(Professor professor){

		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
				
		String sql = "update professor set inativo = 1"
				+ " where idprofessor = ?";
				
		try {
			
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, professor.getId());
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
	
	public List<Professor> buscar(Usuario usuario, String nome){
		List<Professor> resultado = new ArrayList<>();
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = null;
		
		if(usuario.getSetor() == 0){
			sql = "select setor, idprofessor, nomeprofessor, idescola from "
				+ "escola natural join (select idprofessor, nomeprofessor, idescola from "
				+ "professor natural join professor_escola) as t having setor > ? and inativo = 0 and"
				+ "nomeprofessor like ?";
		}else{
			sql = "select setor, idprofessor, nomeprofessor, idescola from "
					+ "escola natural join (select idprofessor, nomeprofessor, idescola from "
					+ "professor natural join professor_escola) as t having setor = ? and inativo = 0 and "
					+ "nomeprofessor like ?";			
		}

		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, usuario.getSetor());
			pstm.setString(2, "%"+nome+"%");
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				Professor professor = new Professor();
				professor.setId(rs.getInt("idprofessor"));
				professor.setNome(rs.getString("nomeprofessor"));
				resultado.add(professor);
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return resultado;
	}
	
	public static List<Professor> listar(Escola escola){
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		String sql = "select t.idprofessor, t.nomeprofessor, t.cargo, t.endereco, t.bairro, t.municipio,"
				+ " t.idescola, t.inativo from escola a inner join (select idprofessor, nomeprofessor,"
				+ " cargo, endereco, bairro, municipio, idescola, inativo from professor natural join"
				+ " professor_escola) as t on a.idescola = t.idescola having inativo = 0 and"
				+ " idescola = ? order by nomeprofessor;";
		
		List<Professor> lista = new ArrayList<>();
		
		try {
			
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, escola.getId());
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				Professor p = new Professor();
				p.setId(rs.getInt("idprofessor"));
				p.setNome(rs.getString("nomeprofessor").toUpperCase());
				p.setCargo(rs.getString("cargo"));
				p.setEndereco(rs.getString("endereco"));
				p.setBairro(rs.getString("bairro"));
				p.setMunicipio(rs.getString("municipio"));
				lista.add(p);
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


	public static List<Professor> listar(Usuario usuario, String nome){
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		String nomeprof = "";
		
		if(nome != null) {
			nomeprof = " and nomeprofessor like '%"+nome+"%' ";
		}
		
		
		String sql = "select idprofessor, nomeprofessor, idescola, nome, setor, inativo"
				+ " from escola natural join (select idprofessor, nomeprofessor, idescola, inativo"
				+ " from professor natural join professor_escola) as t having inativo = 0 and"
				+ " setor = ? "+nomeprof+"order by idprofessor";
		
		if(usuario.getCargo() == 1){
			sql = "select idprofessor, nomeprofessor, idescola, nome, setor, inativo"
					+ " from escola natural join (select idprofessor, nomeprofessor, idescola, inativo"
					+ " from professor natural join professor_escola) as t having inativo = 0 and"
					+ " setor > ? "+nomeprof+"order by idprofessor";
		}
		
		List<Professor> lista = new ArrayList<>();
		
		try {
			
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, usuario.getSetor());
			ResultSet rs = pstm.executeQuery();
			int idlast = 0, idcurrent, count = 0;
			String namelast = "", namecurrent = "", escola = "";
			
			while(rs.next()){
				
				idcurrent = rs.getInt("idprofessor");
				namecurrent = rs.getString("nomeprofessor").toUpperCase() + " >> {["+rs.getString("nome")+"]";				
				
				if(idcurrent == idlast){
					escola += "; ["+rs.getString("nome")+"]";
					continue;
				}else{
					if(count > 0){
						namelast += escola + "}";
						Professor p = new Professor();
						p.setId(idlast);
						p.setNome(namelast);
						lista.add(p);
						escola = "";
					}
					namelast = namecurrent;
					idlast = idcurrent;
				}
				count++;
			}

			namelast += escola + "}";
			Professor p = new Professor();
			p.setId(idlast);
			p.setNome(namelast);
			lista.add(p);
			
			Collections.sort(lista);
			
			rs.close();
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}
	
	
	public static void recarrega (Professor professor){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select idprofessor, nomeprofessor, cargo, email, telefone, aniversario, "
				+ "endereco, bairro, municipio, uf from "
				+ " professor where idprofessor = ?";		
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, professor.getId());
			ResultSet rs = pstm.executeQuery();
			if(rs.next()){
				professor.setId(rs.getInt("idprofessor"));
				professor.setNome(rs.getString("nomeprofessor"));
				professor.setCargo(rs.getString("cargo"));
				professor.setEmail(rs.getString("email"));
				professor.setTelefone(rs.getString("telefone"));
				professor.setAniversario(rs.getString("aniversario"));
				professor.setEndereco(rs.getString("endereco"));
				professor.setBairro(rs.getString("bairro"));
				professor.setMunicipio(rs.getString("municipio"));
				professor.setUf(rs.getString("uf"));
			}
			rs.close();
			pstm.close();
			sql = "select idprofessor, nivel from professor_nivel "
					+ "where idprofessor = ?";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, professor.getId());
			rs = pstm.executeQuery();
			while(rs.next()){
				professor.getNiveis().add(rs.getString("nivel"));
			}
			rs.close();
			pstm.close();
			sql = "select idprofessor, disciplina from professor_disciplina "
					+ "where idprofessor = ?";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, professor.getId());
			rs = pstm.executeQuery();
			while(rs.next()){
				professor.getDisciplinas().add(rs.getString("disciplina"));
			}
			rs.close();
			pstm.close();

			sql = "select idprofessor, idescola from professor_escola "
					+ "where idprofessor = ?";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, professor.getId());
			rs = pstm.executeQuery();
			if(rs.next()){
				Escola e = new Escola();
				e.setId(rs.getInt("idescola"));
				DAOEscola.recarrega(e);
				professor.setEscola(e);
			}
			
			rs.close();
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<String> getDisciplinas(){
		List<String> lista = new ArrayList();
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select nome from disciplina order by nome";
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()){
				lista.add(rs.getString("nome"));
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
}
