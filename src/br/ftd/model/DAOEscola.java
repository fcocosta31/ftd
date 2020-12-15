package br.ftd.model;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.poi.ss.usermodel.Workbook;

import br.ftd.entity.Adocao;
import br.ftd.entity.Bonus;
import br.ftd.entity.ClassificacaoEscola;
import br.ftd.entity.Escola;
import br.ftd.entity.EscolaAdocoes;
import br.ftd.entity.EscolaMin;
import br.ftd.entity.ItemAdocao;
import br.ftd.entity.ItemMonitorAdocao;
import br.ftd.entity.ItemOrcamento;
import br.ftd.entity.MonitorAdocao;
import br.ftd.entity.Orcamento;
import br.ftd.entity.Produto;
import br.ftd.entity.ResultSetToExcel;
import br.ftd.entity.Roteiro;
import br.ftd.entity.RoteiroResumo;
import br.ftd.entity.Usuario;
import br.ftd.factory.ConnectionFactory;
import br.ftd.quality.GlassView;
import net.sf.jxls.transformer.XLSTransformer;

public class DAOEscola {

	private final static String INF_0 = "Ed.Inf-00 ano";
	private final static String INF_1 = "Ed.Inf-01 ano";
	private final static String INF_2 = "Ed.Inf-02 anos";
	private final static String INF_3 = "Ed.Inf-03 anos";
	private final static String INF_4 = "Ed.Inf-04 anos";
	private final static String INF_5 = "Ed.Inf-05 anos";
	private final static String ANO_1 = "1 Ano";
	private final static String ANO_2 = "2 Ano";
	private final static String ANO_3 = "3 Ano";
	private final static String ANO_4 = "4 Ano";
	private final static String ANO_5 = "5 Ano";
	private final static String ANO_6 = "6 Ano";
	private final static String ANO_7 = "7 Ano";
	private final static String ANO_8 = "8 Ano";
	private final static String ANO_9 = "9 Ano";
	private final static String SERIE_1 = "1 Serie";
	private final static String SERIE_2 = "2 Serie";
	private final static String SERIE_3 = "3 Serie";
	private final static String EJA = "EJA";
	private final static String SUPLETIVO = "Supletivo";
	
	public boolean salvar(Escola escola){
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "insert into escola (nome, classificacao, cnpj, endereco, complemento, bairro, "
				+ "municipio, uf, cep, email, telefone, setor, dependencia, idftd, lat, lon, user_id)"
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		String anoperiodo = DAOUtils.getAnoPeriodo()+"";
				
		try {
			PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstm.setString(1, escola.getNome());
			pstm.setString(2, escola.getClassificacao());
			pstm.setString(3, escola.getCnpj());
			pstm.setString(4, escola.getEndereco());
			pstm.setString(5, escola.getComplemento());
			pstm.setString(6, escola.getBairro());
			pstm.setString(7, escola.getMunicipio());
			pstm.setString(8, escola.getUf());
			pstm.setString(9, escola.getCep());
			pstm.setString(10, escola.getEmail());
			pstm.setString(11, escola.getTelefone());
			pstm.setInt(12, escola.getSetor());
			pstm.setString(13, escola.getDependencia());
			pstm.setInt(14, escola.getIdftd());
			pstm.setDouble(15, 0);
			pstm.setDouble(16, 0);
			pstm.setInt(17, escola.getUser_id());
			pstm.executeUpdate();
			pstm.close();
			ResultSet rs = pstm.getGeneratedKeys();
			int id = -1;
			if (rs.next()) {
			    id = rs.getInt(1);
			}			
			Statement stm = con.createStatement();
			escola.setId(id);
			String SqlAlunos = "insert into escola_serie_aluno values "
					+ "('"+anoperiodo+"', "+escola.getId()+",'"+INF_0+"',"+escola.getInfantil0()+"), "
					+ "('"+anoperiodo+"', "+escola.getId()+",'"+INF_1+"',"+escola.getInfantil1()+"), "
					+ "('"+anoperiodo+"', "+escola.getId()+",'"+INF_2+"',"+escola.getInfantil2()+"), "
					+ "('"+anoperiodo+"', "+escola.getId()+",'"+INF_3+"',"+escola.getInfantil3()+"), "
					+ "('"+anoperiodo+"', "+escola.getId()+",'"+INF_4+"',"+escola.getInfantil4()+"), "
					+ "('"+anoperiodo+"', "+escola.getId()+",'"+INF_5+"',"+escola.getInfantil5()+"), "
					+ "('"+anoperiodo+"', "+escola.getId()+",'"+ANO_1+"',"+escola.getAno1()+"), "
					+ "('"+anoperiodo+"', "+escola.getId()+",'"+ANO_2+"',"+escola.getAno2()+"), "
					+ "('"+anoperiodo+"', "+escola.getId()+",'"+ANO_3+"',"+escola.getAno3()+"), "
					+ "('"+anoperiodo+"', "+escola.getId()+",'"+ANO_4+"',"+escola.getAno4()+"), "
					+ "('"+anoperiodo+"', "+escola.getId()+",'"+ANO_5+"',"+escola.getAno5()+"), "
					+ "('"+anoperiodo+"', "+escola.getId()+",'"+ANO_6+"',"+escola.getAno6()+"), "
					+ "('"+anoperiodo+"', "+escola.getId()+",'"+ANO_7+"',"+escola.getAno7()+"), "
					+ "('"+anoperiodo+"', "+escola.getId()+",'"+ANO_8+"',"+escola.getAno8()+"), "
					+ "('"+anoperiodo+"', "+escola.getId()+",'"+ANO_9+"',"+escola.getAno9()+"), "
					+ "('"+anoperiodo+"', "+escola.getId()+",'"+SERIE_1+"',"+escola.getSerie1()+"), "
					+ "('"+anoperiodo+"', "+escola.getId()+",'"+SERIE_2+"',"+escola.getSerie2()+"), "
					+ "('"+anoperiodo+"', "+escola.getId()+",'"+SERIE_3+"',"+escola.getSerie3()+"), "
					+ "('"+anoperiodo+"', "+escola.getId()+",'"+EJA+"',"+escola.getEja()+"), "
					+ "('"+anoperiodo+"', "+escola.getId()+",'"+SUPLETIVO+"',"+escola.getSupletivo()+")";
			
			stm.execute(SqlAlunos);
			stm.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.getMessage();
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
		}
		return true;
	}
	
	public int importarCadastroEscola(ArrayList<Escola> escolas, String acao) throws SQLException{
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		con.setAutoCommit(false);
		int contar = 0;
		if(acao.equalsIgnoreCase("insert")){
			for(Escola e:escolas){
				salvar(e);
				contar++;
			}
		}else{
			for(Escola e:escolas){
				editar(e);
				contar++;
			}			
		}
		return contar;
	}
	
	public boolean editar(Escola escola){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "update escola set nome=?, classificacao=?, cnpj=?, endereco=?, complemento=?, bairro=?, "
				+ "municipio=?, uf=?, cep=?, email=?, telefone=?, setor=?, dependencia=?, idftd=?, user_id=? where idescola=?";
		System.out.println(escola.toString());				
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, escola.getNome());
			pstm.setString(2, escola.getClassificacao());
			pstm.setString(3, escola.getCnpj());
			pstm.setString(4, escola.getEndereco());
			pstm.setString(5, escola.getComplemento());
			pstm.setString(6, escola.getBairro());
			pstm.setString(7, escola.getMunicipio());
			pstm.setString(8, escola.getUf());
			pstm.setString(9, escola.getCep());
			pstm.setString(10, escola.getEmail());
			pstm.setString(11, escola.getTelefone());
			pstm.setInt(12, escola.getSetor());
			pstm.setString(13, escola.getDependencia());
			pstm.setInt(14, escola.getIdftd());
			pstm.setInt(15, escola.getUser_id());
			pstm.setInt(16, escola.getId());
			
			pstm.execute();
			
			pstm.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		
		
		return true;		
	}
	
	public List<Escola> listar(Usuario usuario){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select t.idescola, descricao, classificacao, cnpj, endereco, complemento,"
				+ " bairro, municipio, uf, cep, emailesc, telefone, id, idftd, setor, usuario,"
				+ " visita, observacao from (select idescola, escola.nome as descricao, classificacao,"
				+ " cnpj, endereco, complemento, bairro, municipio, uf, cep, escola.email as emailesc,"
				+ " telefone, id, idftd, usuario.setor as setor, usuario.nome as usuario from escola"
				+ " join usuario on escola.setor = usuario.setor having usuario.setor > 0"
				+ " order by descricao) t left join (select idescola, max(data) as visita, observacao"
				+ " from roteiro natural join roteiro_escola where idescola in (select idescola from"
				+ " (select idescola, usuario.setor from escola join usuario on escola.setor = usuario.setor"
				+ " having usuario.setor > 0"
				+ ") t) group by idescola) s on t.idescola = s.idescola";
		
		
		String sql1 = "select t.idescola, descricao, classificacao, cnpj, endereco, complemento,"
				+ " bairro, municipio, uf, cep, emailesc, telefone, id, idftd, setor, usuario,"
				+ " visita, observacao from (select idescola, escola.nome as descricao, classificacao,"
				+ " cnpj, endereco, complemento, bairro, municipio, uf, cep, escola.email as emailesc,"
				+ " telefone, id, idftd, usuario.setor as setor, usuario.nome as usuario from escola"
				+ " join usuario on escola.setor = usuario.setor having usuario.setor = "+usuario.getSetor()
				+ " order by descricao) t left join (select idescola, max(data) as visita, observacao"
				+ " from roteiro natural join roteiro_escola where idescola in (select idescola from"
				+ " (select idescola, usuario.setor from escola join usuario on escola.setor = usuario.setor"
				+ " having usuario.setor = "+usuario.getSetor()
				+ ") t) group by idescola) s on t.idescola = s.idescola";
				
		
		List<Escola> escolas = new ArrayList<>();
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = null;
			if(usuario.getSetor()==0)
				rs = stm.executeQuery(sql);
			else
			    rs = stm.executeQuery(sql1);
			
			while(rs.next()){
				Escola e = new Escola();
				e.setId(rs.getInt("idescola"));
				e.setNome(rs.getString("descricao"));
				e.setClassificacao(rs.getString("classificacao"));
				e.setCnpj(rs.getString("cnpj"));
				e.setEndereco(rs.getString("endereco"));
				e.setComplemento(rs.getString("complemento"));
				e.setBairro(rs.getString("bairro"));
				e.setMunicipio(rs.getString("municipio"));
				e.setUf(rs.getString("uf"));
				e.setCep(rs.getString("cep"));
				e.setEmail(rs.getString("emailesc"));
				e.setTelefone(rs.getString("telefone"));
				e.setSetor(rs.getInt("setor"));
				e.setIdftd(rs.getInt("idftd"));
				escolas.add(e);
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
		
		for (Escola e : escolas) {
			setObservacaoEscola(e);
			int setor = e.getSetor();
			e.setVendedor(DAOUsuario.getVendedor(setor));
			setTotalAlunos(e);
			e.setUltimavisita(getUltimasVisitas(e));
		}
		
		return escolas;
	}
	
	public static void setObservacaoEscola(Escola e){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select distinct idescola, data, observacao from roteiro natural join roteiro_escola"
				+ " where idescola = ? order by data desc limit 1";
		
		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, e.getId());
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				e.setObservacao(rs.getString("observacao"));
			}
			rs.close();
			pstm.close();
			con.close();			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static List<Date> getUltimasVisitas(Escola e){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select distinct idescola, data from roteiro natural join roteiro_escola"
				+ " where idescola = ? order by data desc limit 3";
		List<Date> visitas = new ArrayList<>();
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, e.getId());
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				visitas.add(rs.getDate("data"));
			}
			rs.close();
			pstm.close();
			con.close();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return visitas;
	}
	

	public List<Escola> listar(Usuario usuario, String dependencia, String municipio, String[] bairro, String visita, int setor){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
				
		String sqlb = " having (";
		String sqlv = "";
		String sql = "select t.idescola, descricao, classificacao, cnpj, endereco, complemento,"
				+ " bairro, municipio, uf, cep, emailesc, telefone, id, idftd, setor, usuario,"
				+ " visita, observacao from (select idescola, escola.nome as descricao, dependencia, classificacao,"
				+ " cnpj, endereco, complemento, bairro, municipio, uf, cep, escola.email as emailesc,"
				+ " telefone, id, idftd, usuario.setor as setor, usuario.nome as usuario from escola"
				+ " join usuario on escola.setor = usuario.setor having usuario.setor > 0 and escola.dependencia = "
				+ "'"+dependencia+"' order by descricao) t left join (select idescola, max(data) as visita, observacao"
				+ " from roteiro natural join roteiro_escola where idescola in (select idescola from"
				+ " (select idescola, usuario.setor from escola join usuario on escola.setor = usuario.setor"
				+ " having usuario.setor > 0"
				+ ") t) group by idescola) s on t.idescola = s.idescola";
		
		String sql1 = "select t.idescola, descricao, classificacao, cnpj, endereco, complemento,"
				+ " bairro, municipio, uf, cep, emailesc, telefone, id, idftd, setor, usuario,"
				+ " visita, observacao from (select idescola, escola.nome as descricao, dependencia, classificacao,"
				+ " cnpj, endereco, complemento, bairro, municipio, uf, cep, escola.email as emailesc,"
				+ " telefone, id, idftd, usuario.setor as setor, usuario.nome as usuario from escola"
				+ " join usuario on escola.setor = usuario.setor having usuario.setor = "+setor+" and dependencia = "
				+ "'"+dependencia+"' order by descricao) t left join (select idescola, max(data) as visita, observacao"
				+ " from roteiro natural join roteiro_escola where idescola in (select idescola from"
				+ " (select idescola, usuario.setor from escola join usuario on escola.setor = usuario.setor"
				+ " having usuario.setor = "+setor
				+ ") t) group by idescola) s on t.idescola = s.idescola";
		
		boolean flag = true, flagv = true, flagm = false;

		if(!municipio.equalsIgnoreCase("todos")){
			sqlb += "municipio = '"+municipio+"' and (";
			flagm = true;
		}				

		for(String s : bairro){
			if(s.equalsIgnoreCase("todos")){
				if(!municipio.equalsIgnoreCase("todos")){
					sqlb = sqlb.substring(0, sqlb.length()-6);
				}				
				flag = false;
				break;
			}else{
				sqlb += "bairro = '"+s+"' or ";
			}
		}
		
		switch(visita){
		case "30": sqlv = "(datediff(curdate(),visita) > 30 or isnull(visita)))";break;
		case "60": sqlv = "(datediff(curdate(),visita) > 60 or isnull(visita)))";break;
		case "90": sqlv = "(datediff(curdate(),visita) > 90 or isnull(visita)))";break;
		case "120": sqlv = "(datediff(curdate(),visita) > 120 or isnull(visita)))";break;
		case "nunca": sqlv = "isnull(visita))";break;
		default: flagv = false; break;
		}
				
		if(flag){
			sqlb = sqlb.substring(0, sqlb.length()-4);						
		}

		if(!flagm && !flag && flagv){
			sqlv = sqlv.substring(0, sqlv.length()-1);
		}
		
		if(flag && flagv){
			sqlb += ") and ";
			sql += sqlb+sqlv;			
			sql1 += sqlb+sqlv;
			
		}else if(flag && !flagv && flagm){
			sqlb += "))";
			sql += sqlb;			
			sql1 += sqlb;
			
		}else if(flag && !flagv && !flagm){
			sqlb += ")";
			sql += sqlb;			
			sql1 += sqlb;
			
		}else if(!flag && flagv && !flagm){
			sql += " having "+sqlv;			
			sql1 += " having "+sqlv;
			
		}else if(!flag && flagv && flagm){
			sql += sqlb+" and "+sqlv;			
			sql1 += sqlb+" and "+sqlv;			
		}else if(!flag && !flagv && flagm){
			sql += sqlb+")";			
			sql1 += sqlb+")";			
		}
		
		List<Escola> escolas = new ArrayList<>();
		try {
			Statement stm = con.createStatement();
			ResultSet rs = null;
			if(setor==0)
				rs = stm.executeQuery(sql);
			else
			    rs = stm.executeQuery(sql1);
			
			while(rs.next()){
				Escola e = new Escola();
				Usuario u = new Usuario();
				u.setId(rs.getInt("id"));
				u.setNome(rs.getString("usuario"));
				e.setId(rs.getInt("idescola"));
				e.setNome(rs.getString("descricao"));
				e.setClassificacao(rs.getString("classificacao"));
				e.setCnpj(rs.getString("cnpj"));
				e.setEndereco(rs.getString("endereco"));
				e.setComplemento(rs.getString("complemento"));
				e.setBairro(rs.getString("bairro"));
				e.setMunicipio(rs.getString("municipio"));
				e.setUf(rs.getString("uf"));
				e.setCep(rs.getString("cep"));
				e.setEmail(rs.getString("emailesc"));
				e.setTelefone(rs.getString("telefone"));
				e.setSetor(rs.getInt("setor"));
				e.setIdftd(rs.getInt("idftd"));
				//e.setObservacao(rs.getString("observacao"));
				setObservacaoEscola(e);
				e.setVendedor(u);
				setTotalAlunos(e);
				e.setUltimavisita(getUltimasVisitas(e));
				escolas.add(e);
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
		
		return escolas;
	}

	
	public List<EscolaMin> listarmin(Usuario usuario){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select idescola, nome, bairro, municipio from escola where setor > 0 order by nome";
		
		String sql1 = "select idescola, nome, bairro, municipio from escola where setor = "+usuario.getSetor()+" order by nome";
				
		
		List<EscolaMin> escolas = new ArrayList<>();
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = null;
			if(usuario.getSetor()==0)
				rs = stm.executeQuery(sql);
			else
			    rs = stm.executeQuery(sql1);
			
			while(rs.next()){
				EscolaMin e = new EscolaMin();
				e.setId(rs.getInt("idescola"));
				e.setText(rs.getString("nome")+"("+rs.getString("bairro")+"-"+rs.getString("municipio")+")");
				escolas.add(e);
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
		
		return escolas;
	}
	
	
	public static void setTotalAlunos(Escola e){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select ano, sum(quantidade) as qtde from escola_serie_aluno where idescola = ?"
				+ " group by ano, idescola order by ano desc limit 1";
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, e.getId());
			ResultSet rs = pstm.executeQuery();
			if(rs.next()){
				e.setTotalalunos(rs.getInt("qtde"));
			}
			rs.close();
			pstm.close();
			con.close();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public static void getQtdeAlunos(Escola e){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select a.idescola, a.serie, a.quantidade, a.ano from escola_serie_aluno a inner join"
				+ " (select max(ano) as year from escola_serie_aluno where idescola = ? group by idescola) as b"
				+ " on a.ano = b.year and a.idescola = ? group by idescola, serie;";
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, e.getId());
			pstm.setInt(2, e.getId());
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				String serie = rs.getString("serie");
				int quantidade = rs.getInt("quantidade");
				map.put(serie, quantidade);
				e.setAno(rs.getString("ano"));				
			}
			if(!map.isEmpty()){
				if(map.get(ANO_1)!=null)
					e.setAno1(map.get(ANO_1));
				else
					e.setAno1(0);
				if(map.get(ANO_2)!=null)
					e.setAno2(map.get(ANO_2));
				else
					e.setAno2(0);
				if(map.get(ANO_3)!=null)
					e.setAno3(map.get(ANO_3));
				else
					e.setAno3(0);
				if(map.get(ANO_4)!=null)
					e.setAno4(map.get(ANO_4));
				else
					e.setAno4(0);
				if(map.get(ANO_5)!=null)					
					e.setAno5(map.get(ANO_5));
				if(map.get(ANO_6)!=null)
					e.setAno6(map.get(ANO_6));
				else
					e.setAno6(0);
				if(map.get(ANO_7)!=null)
					e.setAno7(map.get(ANO_7));
				else
					e.setAno7(0);
				if(map.get(ANO_8)!=null)					
					e.setAno8(map.get(ANO_8));
				else
					e.setAno8(0);
				if(map.get(ANO_9)!=null)
					e.setAno9(map.get(ANO_9));
				else
					e.setAno9(0);
				if(map.get(INF_0)!=null)
					e.setInfantil0(map.get(INF_0));
				else
					e.setInfantil0(0);
				if(map.get(EJA)!=null)
					e.setEja(map.get(EJA));
				else
					e.setEja(0);
				if(map.get(INF_1)!=null)
					e.setInfantil1(map.get(INF_1));
				else
					e.setInfantil1(0);
				if(map.get(INF_2)!=null)
					e.setInfantil2(map.get(INF_2));
				else
					e.setInfantil2(0);
				if(map.get(INF_3)!=null)
					e.setInfantil3(map.get(INF_3));
				else
					e.setInfantil3(0);
				if(map.get(INF_4)!=null)					
					e.setInfantil4(map.get(INF_4));
				else
					e.setInfantil4(0);
				if(map.get(INF_5)!=null)
					e.setInfantil5(map.get(INF_5));
				else
					e.setInfantil5(0);
				if(map.get(SERIE_1)!=null)
					e.setSerie1(map.get(SERIE_1));
				else
					e.setSerie1(0);
				if(map.get(SERIE_2)!=null)
					e.setSerie2(map.get(SERIE_2));
				else
					e.setSerie2(0);
				if(map.get(SERIE_3)!=null)
					e.setSerie3(map.get(SERIE_3));
				else
					e.setSerie3(0);
				if(map.get(SUPLETIVO)!=null)
					e.setSupletivo(map.get(SUPLETIVO));
				else
					e.setSupletivo(0);
			}else{
				e.setAno1(0);
				e.setAno2(0);
				e.setAno3(0);
				e.setAno4(0);
				e.setAno5(0);
				e.setAno6(0);
				e.setAno7(0);
				e.setAno8(0);
				e.setAno9(0);
				e.setInfantil0(0);
				e.setEja(0);
				e.setInfantil1(0);
				e.setInfantil2(0);
				e.setInfantil3(0);
				e.setInfantil4(0);
				e.setInfantil5(0);
				e.setSerie1(0);
				e.setSerie2(0);
				e.setSerie3(0);
				e.setSupletivo(0);				
			}
			rs.close();
			pstm.close();
			con.close();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	

	public static void getQtdeAlunos(Escola e, String ano){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select idescola, serie, quantidade, ano from escola_serie_aluno"
				+ " where ano = ? and idescola = ? group by idescola, serie;";
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		e.setAno(ano);
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, e.getAno());
			pstm.setInt(2, e.getId());
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				map.put(rs.getString("serie"), rs.getInt("quantidade"));				
			}
			if(!map.isEmpty()){
				if(map.get(ANO_1)!=null)
					e.setAno1(map.get(ANO_1));
				else
					e.setAno1(0);
				if(map.get(ANO_2)!=null)
					e.setAno2(map.get(ANO_2));
				else
					e.setAno2(0);
				if(map.get(ANO_3)!=null)
					e.setAno3(map.get(ANO_3));
				else
					e.setAno3(0);
				if(map.get(ANO_4)!=null)
					e.setAno4(map.get(ANO_4));
				else
					e.setAno4(0);
				if(map.get(ANO_5)!=null)					
					e.setAno5(map.get(ANO_5));
				if(map.get(ANO_6)!=null)
					e.setAno6(map.get(ANO_6));
				else
					e.setAno6(0);
				if(map.get(ANO_7)!=null)
					e.setAno7(map.get(ANO_7));
				else
					e.setAno7(0);
				if(map.get(ANO_8)!=null)					
					e.setAno8(map.get(ANO_8));
				else
					e.setAno8(0);
				if(map.get(ANO_9)!=null)
					e.setAno9(map.get(ANO_9));
				else
					e.setAno9(0);
				if(map.get(INF_0)!=null)
					e.setInfantil0(map.get(INF_0));
				else
					e.setInfantil0(0);
				if(map.get(EJA)!=null)
					e.setEja(map.get(EJA));
				else
					e.setEja(0);
				if(map.get(INF_1)!=null)
					e.setInfantil1(map.get(INF_1));
				else
					e.setInfantil1(0);
				if(map.get(INF_2)!=null)
					e.setInfantil2(map.get(INF_2));
				else
					e.setInfantil2(0);
				if(map.get(INF_3)!=null)
					e.setInfantil3(map.get(INF_3));
				else
					e.setInfantil3(0);
				if(map.get(INF_4)!=null)					
					e.setInfantil4(map.get(INF_4));
				else
					e.setInfantil4(0);
				if(map.get(INF_5)!=null)
					e.setInfantil5(map.get(INF_5));
				else
					e.setInfantil5(0);
				if(map.get(SERIE_1)!=null)
					e.setSerie1(map.get(SERIE_1));
				else
					e.setSerie1(0);
				if(map.get(SERIE_2)!=null)
					e.setSerie2(map.get(SERIE_2));
				else
					e.setSerie2(0);
				if(map.get(SERIE_3)!=null)
					e.setSerie3(map.get(SERIE_3));
				else
					e.setSerie3(0);
				if(map.get(SUPLETIVO)!=null)
					e.setSupletivo(map.get(SUPLETIVO));
				else
					e.setSupletivo(0);
			}else{
				e.setAno1(0);
				e.setAno2(0);
				e.setAno3(0);
				e.setAno4(0);
				e.setAno5(0);
				e.setAno6(0);
				e.setAno7(0);
				e.setAno8(0);
				e.setAno9(0);
				e.setInfantil0(0);
				e.setEja(0);
				e.setInfantil1(0);
				e.setInfantil2(0);
				e.setInfantil3(0);
				e.setInfantil4(0);
				e.setInfantil5(0);
				e.setSerie1(0);
				e.setSerie2(0);
				e.setSerie3(0);
				e.setSupletivo(0);				
			}
			rs.close();
			pstm.close();
			con.close();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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

	
	public List<Escola> pesquisar(Usuario usuario, String nome){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();

		String[] terms = nome.split(" ");
		String query = "(";
		for(String s : terms) {
			query += "(a.nome like '%"+s+"%' or"
					+ " a.municipio like '%"+s+"%') or ";
		}
		
		int len = query.length() - 4;
		
		query = query.substring(0, len) + ")";

		String sql = "select a.idescola, a.nome, a.classificacao, a.cnpj, a.endereco,"
				+ " a.complemento, a.bairro, a.municipio, a.cep, a.email, a.telefone,"
				+ " a.setor, b.id, b.nome as vendedor from escola a inner join usuario b on a.setor = b.setor"
				+ " and a.setor > 0 and "+query
				+ " order by a.nome";
		
		String sql1 = "select a.idescola, a.nome, a.classificacao, a.cnpj, a.endereco,"
				+ " a.complemento, a.bairro, a.municipio, a.cep, a.email, a.telefone,"
				+ " a.setor, b.id, b.nome as vendedor from escola a inner join usuario b on a.setor = b.setor"
				+ " and a.setor > 0 and "+query
				+ " having a.setor = "+usuario.getSetor()+" order by a.nome";
		
		List<Escola> escolas = new ArrayList<>();
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = null;
			if(usuario.getSetor()==0 || usuario.getSetor() > 89)
				rs = stm.executeQuery(sql);
			else
			    rs = stm.executeQuery(sql1);
			
			while(rs.next()){				
				Escola e = new Escola();
				String linha = rs.getString("nome") +" "+ rs.getString("municipio");
				int index = terms.length;
				for(String term : terms) {
					if(containsIgnoreAccents(linha, term)){
						index--;
					}
				}
				if(index == 0) {
					e.setId(rs.getInt("idescola"));
					e.setNome(rs.getString("nome"));
					e.setClassificacao(rs.getString("classificacao"));
					e.setCnpj(rs.getString("cnpj"));
					e.setEndereco(rs.getString("endereco"));
					e.setComplemento(rs.getString("complemento"));
					e.setBairro(rs.getString("bairro"));
					e.setMunicipio(rs.getString("municipio"));
					e.setCep(rs.getString("cep"));
					e.setEmail(rs.getString("email"));
					e.setTelefone(rs.getString("telefone"));
					e.setSetor(rs.getInt("setor"));
					Usuario v = new Usuario();
					v.setId(rs.getInt("id"));
					v.setNome(rs.getString("vendedor"));
					v.setSetor(e.getSetor());
					e.setVendedor(v);
					escolas.add(e);
				}				
			}
			rs.close();
			stm.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return escolas;
	}


	
	public static void recarrega(Escola escola){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select * from escola where idescola = ?";
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, escola.getId());
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				escola.setId(rs.getInt("idescola"));
				escola.setNome(rs.getString("nome"));
				escola.setClassificacao(rs.getString("classificacao"));
				escola.setCnpj(rs.getString("cnpj"));
				escola.setEndereco(rs.getString("endereco"));
				escola.setComplemento(rs.getString("complemento"));
				escola.setBairro(rs.getString("bairro"));
				escola.setMunicipio(rs.getString("municipio"));
				escola.setUf(rs.getString("uf"));
				escola.setCep(rs.getString("cep"));
				escola.setEmail(rs.getString("email"));
				escola.setTelefone(rs.getString("telefone"));
				escola.setSetor(rs.getInt("setor"));
				escola.setIdftd(rs.getInt("idftd"));
				escola.setDependencia(rs.getString("dependencia"));
				escola.setLon(rs.getDouble("lon"));
				escola.setLat(rs.getDouble("lat"));
				escola.setUser_id(rs.getInt("user_id"));
				escola.getLonLat();
			}
			rs.close();
			pstm.close();
			
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
		
	}
	
	
	public static void setLonLat(Escola e){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "update escola set lon = ?, lat = ? where idescola = ?";
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setDouble(1, e.getLon());
			pstm.setDouble(2, e.getLat());
			pstm.setInt(3, e.getId());
			pstm.execute();
			pstm.close();
			con.close();			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static Usuario getUsuarioVendedor(Escola escola){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select setor from escola where idescola = ?";
		Usuario usuario = new Usuario();
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, escola.getId());
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				escola.setSetor(rs.getInt("setor"));
			}
			rs.close();
			pstm.close();
			
			sql = "select * from usuario where setor = ?";
			
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, escola.getSetor());
			rs = pstm.executeQuery();
			
			if(rs.next()){
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setSetor(rs.getInt("setor"));
				usuario.setCargo(rs.getInt("cargo"));
				usuario.setLogin(rs.getString("login"));
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
	
	public static String salvarAdocoes(Adocao adocao){
		
		String mensagem = "";
		
		boolean flag = false;
		
		List<Adocao> a = new ArrayList<>();
		
		String serieant = "x", serie;
		
		List<ItemOrcamento> itens = null;
		
		for(ItemAdocao i : adocao.getItensadocao()){

			serie = i.getSerie();
			
			if(serie.equalsIgnoreCase(serieant)){
				ItemOrcamento item = new ItemOrcamento();
				Produto p = new Produto();
				p.setCodigo(i.getCodigo());
				p.setPreco(i.getPreco());
				item.setProduto(p);
				itens.add(item);
				serieant = serie;
			}else{
				itens = new ArrayList<>();
				Adocao ad = new Adocao();
				ItemOrcamento item = new ItemOrcamento();
				Produto p = new Produto();
				p.setCodigo(i.getCodigo());
				p.setPreco(i.getPreco());
				item.setProduto(p);
				itens.add(item);
				ad.setItens(itens);
				ad.setSerie(serie);
				ad.setAno(adocao.getAno());
				ad.setIdescola(adocao.getEscola().getId());
				ad.setUser_id(adocao.getUser_id());
				a.add(ad);
				serieant = serie;				
			}
		}
		boolean flagb = true;
		for(Adocao adoc : a){
			if(adoc.isEmptyItems()){
				mensagem += adoc.getSerie()+" ";
				flagb = false;
			}
			if(!salvarAdocao(adoc) && flagb){
				mensagem += adoc.getSerie()+" ";
				flag = true;
			}
		}
		
		if(flag){
			return "<p>Ocorreu um erro ao tentar salvar!</p>"
	        		+ "<p>A(s) serie(s) ["+mensagem+"] ja estava(vam) cadastrada(s) ou nao contem itens!</p>";
		}else{
			return "Lista(s) registrada(s) com sucesso!";
		}
	}
	
	
	public static boolean salvarAdocao(Adocao adocao){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "insert into adocao (idescola, serie, ano, qtde, user_id) values (?,?,?,?,?)";
		String sql2 = "insert into item_adocao (idadocao, codigo, preco) values (?,?,?)";
				
		if(verificaAdocao(adocao)) return false;
		
		try {
			con.setAutoCommit(false);
			PreparedStatement pstm = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);			
			pstm.setInt(1, adocao.getIdescola());
			pstm.setString(2, adocao.getSerie());
			pstm.setString(3, adocao.getAno());
			pstm.setInt(4, 0);
			pstm.setInt(5, adocao.getUser_id());
			pstm.execute();
			ResultSet rs = pstm.getGeneratedKeys();
			int id = 0;
			if(rs.next())
				id = rs.getInt(1);
			
			pstm.close();
			adocao.setIdadocao(id);
			PreparedStatement pstmi;
			pstmi = con.prepareStatement(sql2);
			for(ItemOrcamento i : adocao.getItens()){
				pstmi.setInt(1, adocao.getIdadocao());
				pstmi.setString(2, i.getProduto().getCodigo());
				pstmi.setFloat(3, i.getProduto().getPreco());
				pstmi.addBatch();
			}
			pstmi.executeBatch();
			pstmi.close();
			con.commit();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		}
		
		return true;
	}
	

	public static boolean deletarSerieAdocao(Orcamento orcamento) throws SQLException{
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		boolean flag = true;
		String sql = "delete from adocao where idadocao = ?";
		
		PreparedStatement pstm;

		try {
			con.setAutoCommit(false);
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, orcamento.getIdadocao());
			pstm.execute();
			con.commit();
			pstm.close();
			
			sql = "delete from item_adocao where idadocao = ?";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, orcamento.getIdadocao());
			pstm.execute();
			con.commit();

			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			con.rollback();
			flag = false;
		}
				
		return flag;
	}
	
	public static boolean verificaAdocao(Adocao adocao){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select * from adocao where idescola = ? and serie = ? and ano = ?";
		boolean flag = false;
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, adocao.getIdescola());
			pstm.setString(2, adocao.getSerie());
			pstm.setString(3, adocao.getAno());
			ResultSet rs = pstm.executeQuery();
			if(rs.next()){
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
	
	public static boolean editarAdocao(Adocao adocao){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sqlremove = "delete from item_adocao where idadocao = "+adocao.getIdadocao();
		String sqlinsert = "insert into item_adocao (idadocao, codigo, preco) values (?,?,?)";
		String sqluser = "update adocao set user_id="+adocao.getUser_id()+" where idadocao = "+adocao.getIdadocao();

		
		try {						
			
			if(adocao.isEmptyItems()) return false;
			
			Statement stm = con.createStatement();
			stm.execute(sqlremove);
			stm.execute(sqluser);
			stm.close();
			
			PreparedStatement pstm = con.prepareStatement(sqlinsert);
			for(ItemAdocao i : adocao.getItensadocao()){
				pstm.setInt(1, adocao.getIdadocao());
				pstm.setString(2, i.getCodigo());
				pstm.setFloat(3, i.getPreco());
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
	
	
	public EscolaAdocoes listarAdocoes(Escola escola){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select codigo, descricao, item_adocao.preco as preco, serie, ano, idadocao, idescola, escola.nome as nome"
				+ " from escola a inner join (adocao natural join (item_adocao natural join produto)) b on a.idescola = b.idescola "
				+ "having idescola = ? order by idadocao";

		EscolaAdocoes adocoes = new EscolaAdocoes();
		List<ItemOrcamento> itens = new ArrayList<>();
		List<Adocao> adotados = new ArrayList<>();
		ItemOrcamento itorcam = null;
		Produto p = null;
		Adocao a = null;
		
		int idanterior = 0;
		recarrega(escola);
		adocoes.setEscola(escola);
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, escola.getId());
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				if(rs.getInt("idadocao") == idanterior){
					p = new Produto();
					itorcam = new ItemOrcamento();
					p.setCodigo(rs.getString("codigo"));
					p.setDescricao(rs.getString("descricao"));
					p.setPreco(rs.getFloat("preco"));
					itorcam.setProduto(p);
					itorcam.setQuantidade(1);
					itens.add(itorcam);
					
				}else if(!itens.isEmpty()){
					a.setItens(itens);
					adotados.add(a);
					
					a = new Adocao();
					a.setIdadocao(rs.getInt("idadocao"));
					a.setAudit(DAOUtils.getAuditoriaUser("adocao", a.getIdadocao()));
					a.setIdescola(rs.getInt("idescola"));
					a.setSerie(rs.getString("serie"));
					a.setAno(rs.getString("ano"));
					p = new Produto();
					itens = new ArrayList<>();
					itorcam = new ItemOrcamento();
					p.setCodigo(rs.getString("codigo"));
					p.setDescricao(rs.getString("descricao"));
					p.setPreco(rs.getFloat("preco"));
					itorcam.setProduto(p);
					itorcam.setQuantidade(1);
					itens.add(itorcam);
					idanterior = rs.getInt("idadocao");
					
				}else{
					a = new Adocao();
					itens = new ArrayList<>();
					a.setIdadocao(rs.getInt("idadocao"));
					a.setAudit(DAOUtils.getAuditoriaUser("adocao", a.getIdadocao()));
					a.setIdescola(rs.getInt("idescola"));
					a.setSerie(rs.getString("serie"));
					a.setAno(rs.getString("ano"));
					p = new Produto();
					itorcam = new ItemOrcamento();
					p.setCodigo(rs.getString("codigo"));
					p.setDescricao(rs.getString("descricao"));
					p.setPreco(rs.getFloat("preco"));
					itorcam.setProduto(p);
					itorcam.setQuantidade(1);
					itens.add(itorcam);
					idanterior = rs.getInt("idadocao");					
				}
			}
			rs.close();
			pstm.close();
			con.close();
			
			if(a != null){
				a.setItens(itens);
				adotados.add(a);
				adocoes.setAdotados(adotados);
			}
			else{		
				adocoes.setAdotados(null);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return adocoes;
	}


	public List<Adocao> listarSerieAno(Escola escola){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select nome, serie, ano from adocao a inner join escola b"
				+ " on a.idescola = b.idescola and b.idescola = ?";

		List<Adocao> adocoes = new ArrayList<>();
			
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, escola.getId());
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				Adocao a = new Adocao();
				a.setIdescola(escola.getId());
				a.setNomeescola(escola.getNome());
				a.setAno(rs.getString("ano"));
				a.setSerie(rs.getString("serie"));
				adocoes.add(a);
			}			
			rs.close();
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return adocoes;
	}
	
	
	public void detalharAdocoes(Orcamento orcamento, String[] ids){
		Connection con =  ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select idadocao, codigo, descricao, preco from"
				+ " item_adocao natural join produto where ";
		String idsadocao = "idadocao = ";
		int k = ids.length;
		for(int i = 0; i<ids.length; i++){
			idsadocao += ids[i];
			if(k > (i+1)){
				idsadocao += " or idadocao = ";
			}
		}
		idsadocao += " group by codigo";
		sql += idsadocao;

		List<ItemOrcamento> itens = new ArrayList<>();
		itens = orcamento.getItens();
		ItemOrcamento item = null;
		Produto p = null;
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()){
				item = new ItemOrcamento();
				p = new Produto();
				item.setQuantidade(1);
				p.setCodigo(rs.getString("codigo"));
				p.setDescricao(rs.getString("descricao"));
				p.setPreco(rs.getFloat("preco"));
				item.setProduto(p);
				itens.add(item);
			}
			rs.close();
			stm.close();
			con.close();
			DAOProduto.setEstoque(itens);
			orcamento.setItens(itens);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	public static void detalharTodasSeries(Orcamento orcamento, String tabela, Usuario usuario){
		
		Connection con =  ConnectionFactory.getInstance().getMySqlConnection();

		String sqla = "select idadocao, serie from adocao where idescola = ? and ano = ?";

		String sql = "select a.idadocao, a.codigo, b.descricao, b.familia, b.nivel, a.preco"
				+ " from item_adocao a inner join produto b on a.codigo = b.codigo and a.idadocao = ?";

		
		String sql1 = "select a.idadocao, a.codigo, b.descricao, b.familia, b.nivel, b.preco"
				+ " from item_adocao a inner join produto b on a.codigo = b.codigo and a.idadocao = ?";
		
		String sql3 = "SELECT idescola, serie, quantidade from escola_serie_aluno"
				+ " where idescola = "+orcamento.getEscola().getId()+" and ano = "+orcamento.getAno();
		
		Map<String, Integer> map = new HashMap<>();
		
		List<ItemOrcamento> itens = new ArrayList<>();
		ItemOrcamento item = null;
		Produto p = null;
		PreparedStatement pstm, pstma;
		ResultSet rsa;
		Statement stm;
		try {
			stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql3);
			while(rs.next()){
				map.put(rs.getString("serie"), rs.getInt("quantidade"));
			}
			rs.close();
			stm.close();
			

			
			pstma = con.prepareStatement(sqla);
			pstma.setInt(1, orcamento.getEscola().getId());
			pstma.setString(2, orcamento.getAno());
			rsa = pstma.executeQuery();

			while(rsa.next()){

					String serie = rsa.getString("serie");
					int idadocao = rsa.getInt("idadocao");
	
					if(tabela.equalsIgnoreCase("original")){
						 pstm = con.prepareStatement(sql);
					}else{
						pstm = con.prepareStatement(sql1);
					}			
					pstm.setInt(1, idadocao);
					orcamento.getEscola().setAudit(DAOUtils.getAuditoriaUser("adocao", idadocao));
					rs = pstm.executeQuery();
					while(rs.next()){
						item = new ItemOrcamento();
						p = new Produto();								
						p.setCodigo(rs.getString("codigo"));
						p.setDescricao(rs.getString("descricao"));
						p.setPreco(rs.getFloat("preco"));
						p.setSerie(serie);
						p.setFamilia(rs.getString("familia"));
						p.setNivel(rs.getString("nivel"));
						if(usuario != null){
							if(map.get(p.getSerie()) != null){
								if(map.get(p.getSerie()) > 0)
									item.setQuantidade(map.get(p.getSerie()));
								else
									item.setQuantidade(1);
							}else{
								item.setQuantidade(1);
							}
						}else{
							item.setQuantidade(1);
						}
						item.setProduto(p);
						itens.add(item);
				}
				rs.close();
				pstm.close();
			}
			rsa.close();
			pstma.close();
			con.close();

			if(itens !=null){
				for (ItemOrcamento itemOrcamento : itens) {
					DAOProduto.setPrevisaoProduto(itemOrcamento.getProduto());
				}
				DAOProduto.setEstoque(itens);
				orcamento.setItens(itens);
				orcamento.refazTotal();
				Collections.sort(itens);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(con != null)
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
		
	public void listarTodasAdocoes(){
		
	}
	
	public static void recarrega(Adocao adocao, String tabela){
		
		Connection con =  ConnectionFactory.getInstance().getMySqlConnection();
		
		String series = "(serie = '";
		
		for(String s : adocao.getSeries()){
			series += s + "' or serie = '";
		}
		
		series = series.substring(0, series.length()-13);
		series += ")";
		
		String sqla = "select idadocao, serie from adocao where idescola = ? and ano = ? and "+series;
		
		String sql = "select a.idadocao, a.codigo, b.descricao, b.familia, b.nivel, a.preco"
				+ " from item_adocao a inner join produto b on a.codigo = b.codigo and a.idadocao = ?";

		
		String sql1 = "select a.idadocao, a.codigo, b.descricao, b.familia, b.nivel, b.preco"
				+ " from item_adocao a inner join produto b on a.codigo = b.codigo and a.idadocao = ?";
		
		List<ItemOrcamento> itens = new ArrayList<>();
		ItemOrcamento item = null;
		Produto p = null;
		try {
			PreparedStatement pstm, pstma;
			ResultSet rs, rsa;			
			boolean flag = false;			
			
			pstm = con.prepareStatement(sqla);
			pstm.setInt(1, adocao.getIdescola());
			pstm.setString(2, adocao.getAno());			
			rs = pstm.executeQuery();
			while(rs.next()){
				adocao.setIdadocao(rs.getInt("idadocao"));
				adocao.setAudit(DAOUtils.getAuditoriaUser("adocao", adocao.getIdadocao()));
				if(tabela.equalsIgnoreCase("original")){
						pstma = con.prepareStatement(sql);
						pstma.setInt(1, adocao.getIdadocao());
						rsa = pstma.executeQuery();
						while(rsa.next()){
							flag = true;				
							item = new ItemOrcamento();
							p = new Produto();
							p.setSerie(rs.getString("serie"));
							item.setQuantidade(1);
							p.setCodigo(rsa.getString("codigo"));
							p.setDescricao(rsa.getString("descricao"));
							p.setFamilia(rsa.getString("familia"));
							p.setNivel(rsa.getString("nivel"));
							p.setPreco(rsa.getFloat("preco"));
							DAOProduto.setPrevisaoProduto(p);
							item.setProduto(p);
							itens.add(item);
						}
						rsa.close();
						pstma.close();
				}else{
					pstma = con.prepareStatement(sql1);
					pstma.setInt(1, adocao.getIdadocao());
					rsa = pstma.executeQuery();
					while(rsa.next()){
						flag = true;				
						item = new ItemOrcamento();
						p = new Produto();
						p.setSerie(rs.getString("serie"));
						item.setQuantidade(1);
						p.setCodigo(rsa.getString("codigo"));
						p.setDescricao(rsa.getString("descricao"));
						p.setFamilia(rsa.getString("familia"));
						p.setNivel(rsa.getString("nivel"));
						p.setPreco(rsa.getFloat("preco"));
						item.setProduto(p);
						itens.add(item);
					}
					rsa.close();
					pstma.close();
				}								
			}
			rs.close();
			pstm.close();
			con.close();
			
			if(flag){
				for (ItemOrcamento itemOrcamento : itens) {
					DAOProduto.setPrevisaoProduto(itemOrcamento.getProduto());
				}
				DAOProduto.setEstoque(itens);
				Collections.sort(itens);
			}else{
				itens = null;
			}
			
			adocao.setItens(itens);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String deletar(Escola escola) throws SQLException{
		
		Connection con =  ConnectionFactory.getInstance().getMySqlConnection();
		
		String sql = "delete from escola where idescola = ?";
		
		PreparedStatement pstm;
		
		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, escola.getId());
			pstm.execute();
			pstm.close();
			
			sql = "delete from escola_serie_aluno where idescola = ?";

			pstm = con.prepareStatement(sql);
			pstm.setInt(1, escola.getId());
			pstm.execute();
			pstm.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return e.getMessage();			
		}finally{
			con.close();
		}
				
		return "Escola "+escola.getNome()+" deletada com sucesso!";
	}
	
	public static List<ClassificacaoEscola> getListaClassificacao() throws SQLException{
		List<ClassificacaoEscola> lista = new ArrayList<>();
		Connection con =  ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select * from classificacao_escola";
		
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		while(rs.next()){
			ClassificacaoEscola cle = new ClassificacaoEscola();
			cle.setId(rs.getInt("id"));
			cle.setIdclassificacao(rs.getString("idclassificacao"));
			cle.setClassificacaoescola(rs.getString("descricaoclassificacao"));
			lista.add(cle);
		}
		rs.close();
		stm.close();
		con.close();
		
		return lista;
	}
	
	
	public String salvarQtdeAlunos(Escola escola){
		
		Connection con =  ConnectionFactory.getInstance().getMySqlConnection();
		
		String anoperiodo = DAOUtils.getAnoPeriodo()+"";
		
		String SqlSalve = "insert into escola_serie_aluno values "
				+ "('"+anoperiodo+"', "+escola.getId()+",'"+INF_0+"',"+escola.getInfantil0()+"), "
				+ "('"+anoperiodo+"', "+escola.getId()+",'"+INF_1+"',"+escola.getInfantil1()+"), "
				+ "('"+anoperiodo+"', "+escola.getId()+",'"+INF_2+"',"+escola.getInfantil2()+"), "
				+ "('"+anoperiodo+"', "+escola.getId()+",'"+INF_3+"',"+escola.getInfantil3()+"), "
				+ "('"+anoperiodo+"', "+escola.getId()+",'"+INF_4+"',"+escola.getInfantil4()+"), "
				+ "('"+anoperiodo+"', "+escola.getId()+",'"+INF_5+"',"+escola.getInfantil5()+"), "
				+ "('"+anoperiodo+"', "+escola.getId()+",'"+ANO_1+"',"+escola.getAno1()+"), "
				+ "('"+anoperiodo+"', "+escola.getId()+",'"+ANO_2+"',"+escola.getAno2()+"), "
				+ "('"+anoperiodo+"', "+escola.getId()+",'"+ANO_3+"',"+escola.getAno3()+"), "
				+ "('"+anoperiodo+"', "+escola.getId()+",'"+ANO_4+"',"+escola.getAno4()+"), "
				+ "('"+anoperiodo+"', "+escola.getId()+",'"+ANO_5+"',"+escola.getAno5()+"), "
				+ "('"+anoperiodo+"', "+escola.getId()+",'"+ANO_6+"',"+escola.getAno6()+"), "
				+ "('"+anoperiodo+"', "+escola.getId()+",'"+ANO_7+"',"+escola.getAno7()+"), "
				+ "('"+anoperiodo+"', "+escola.getId()+",'"+ANO_8+"',"+escola.getAno8()+"), "
				+ "('"+anoperiodo+"', "+escola.getId()+",'"+ANO_9+"',"+escola.getAno9()+"), "
				+ "('"+anoperiodo+"', "+escola.getId()+",'"+SERIE_1+"',"+escola.getSerie1()+"), "
				+ "('"+anoperiodo+"', "+escola.getId()+",'"+SERIE_2+"',"+escola.getSerie2()+"), "
				+ "('"+anoperiodo+"', "+escola.getId()+",'"+SERIE_3+"',"+escola.getSerie3()+"), "
				+ "('"+anoperiodo+"', "+escola.getId()+",'"+EJA+"',"+escola.getEja()+"), "
				+ "('"+anoperiodo+"', "+escola.getId()+",'"+SUPLETIVO+"',"+escola.getSupletivo()+")";
		
		String sql = "select * from escola_serie_aluno where idescola = "+escola.getId()+" and ano = "+anoperiodo;
		
		String mensagem = "";
		
		try {
			Statement stm = con.createStatement();
			
			ResultSet rs = stm.executeQuery(sql);

			if(rs.next()){
				Statement stmupdate = con.createStatement();
				stmupdate.addBatch("update escola_serie_aluno set quantidade = "
								+ escola.getInfantil0()+" where serie = '"+INF_0+"' and idescola = "+escola.getId()+" and ano = '"+anoperiodo+"'");
				stmupdate.addBatch("update escola_serie_aluno set quantidade = "
						+ escola.getInfantil1()+" where serie = '"+INF_1+"' and idescola = "+escola.getId()+" and ano = '"+anoperiodo+"'");
				stmupdate.addBatch("update escola_serie_aluno set quantidade = "
						+ escola.getInfantil2()+" where serie = '"+INF_2+"' and idescola = "+escola.getId()+" and ano = '"+anoperiodo+"'");
				stmupdate.addBatch("update escola_serie_aluno set quantidade = "
						+ escola.getInfantil3()+" where serie = '"+INF_3+"' and idescola = "+escola.getId()+" and ano = '"+anoperiodo+"'");
				stmupdate.addBatch("update escola_serie_aluno set quantidade = "
						+ escola.getInfantil4()+" where serie = '"+INF_4+"' and idescola = "+escola.getId()+" and ano = '"+anoperiodo+"'");
				stmupdate.addBatch("update escola_serie_aluno set quantidade = "
						+ escola.getInfantil5()+" where serie = '"+INF_5+"' and idescola = "+escola.getId()+" and ano = '"+anoperiodo+"'");
				stmupdate.addBatch("update escola_serie_aluno set quantidade = "
						+ escola.getAno1()+" where serie = '"+ANO_1+"' and idescola = "+escola.getId()+" and ano = '"+anoperiodo+"'");
				stmupdate.addBatch("update escola_serie_aluno set quantidade = "
						+ escola.getAno2()+" where serie = '"+ANO_2+"' and idescola = "+escola.getId()+" and ano = '"+anoperiodo+"'");
				stmupdate.addBatch("update escola_serie_aluno set quantidade = "
						+ escola.getAno3()+" where serie = '"+ANO_3+"' and idescola = "+escola.getId()+" and ano = '"+anoperiodo+"'");
				stmupdate.addBatch("update escola_serie_aluno set quantidade = "
						+ escola.getAno4()+" where serie = '"+ANO_4+"' and idescola = "+escola.getId()+" and ano = '"+anoperiodo+"'");
				stmupdate.addBatch("update escola_serie_aluno set quantidade = "
						+ escola.getAno5()+" where serie = '"+ANO_5+"' and idescola = "+escola.getId()+" and ano = '"+anoperiodo+"'");
				stmupdate.addBatch("update escola_serie_aluno set quantidade = "
						+ escola.getAno6()+" where serie = '"+ANO_6+"' and idescola = "+escola.getId()+" and ano = '"+anoperiodo+"'");
				stmupdate.addBatch("update escola_serie_aluno set quantidade = "
						+ escola.getAno7()+" where serie = '"+ANO_7+"' and idescola = "+escola.getId()+" and ano = '"+anoperiodo+"'");
				stmupdate.addBatch("update escola_serie_aluno set quantidade = "
						+ escola.getAno8()+" where serie = '"+ANO_8+"' and idescola = "+escola.getId()+" and ano = '"+anoperiodo+"'");
				stmupdate.addBatch("update escola_serie_aluno set quantidade = "
						+ escola.getAno9()+" where serie = '"+ANO_9+"' and idescola = "+escola.getId()+" and ano = '"+anoperiodo+"'");
				stmupdate.addBatch("update escola_serie_aluno set quantidade = "
						+ escola.getSerie1()+" where serie = '"+SERIE_1+"' and idescola = "+escola.getId()+" and ano = '"+anoperiodo+"'");
				stmupdate.addBatch("update escola_serie_aluno set quantidade = "
						+ escola.getSerie2()+" where serie = '"+SERIE_2+"' and idescola = "+escola.getId()+" and ano = '"+anoperiodo+"'");
				stmupdate.addBatch("update escola_serie_aluno set quantidade = "
						+ escola.getSerie3()+" where serie = '"+SERIE_3+"' and idescola = "+escola.getId()+" and ano = '"+anoperiodo+"'");
				stmupdate.addBatch("update escola_serie_aluno set quantidade = "
						+ escola.getEja()+" where serie = '"+EJA+"' and idescola = "+escola.getId()+" and ano = '"+anoperiodo+"'");
				stmupdate.addBatch("update escola_serie_aluno set quantidade = "
						+ escola.getSupletivo()+" where serie = '"+SUPLETIVO+"' and idescola = "+escola.getId()+" and ano = '"+anoperiodo+"'");
				
				stmupdate.executeBatch();
				
				mensagem = "Numero de alunos atualizados!!!";
				
				rs.close();
				stm.close();
				stmupdate.close();
				con.close();
				
			}else{
				Statement stmsave = con.createStatement();
				
				stmsave.execute(SqlSalve);
				
				rs.close();
				stm.close();
				stmsave.close();
				con.close();
				
				mensagem = "Numero de alunos salvo!!!";
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			mensagem = "Erro ao salvar. Mensagem: "+e.getMessage();
		}
		
		
		return mensagem;
	}
	
	public String salvarRoteiro(Roteiro roteiro){
		Connection con =  ConnectionFactory.getInstance().getMySqlConnection();
		
		String sql1 = null;
		int id = 0;
		
		boolean flag = false;
		
		if(roteiro.getId() == 0){
			sql1 = "insert into roteiro (idroteiro, data, setor) values (?, ?,?)";
			id = DAOUtils.getProximoId("roteiro");
			roteiro.setId(id);
		}else{
			flag = true;
		}
		
		String mensagem = "";		
		
		try {
			
			if(!flag){
				PreparedStatement pstm = con.prepareStatement(sql1);
				pstm.setInt(1, id);
				pstm.setDate(2, roteiro.getData());
				pstm.setInt(3, roteiro.getVendedor().getSetor());
				pstm.execute();
				pstm.close();
				
				Statement stm = con.createStatement();
				for(Escola e : roteiro.getEscolas()){
					String sql2 = "insert into roteiro_escola (idroteiro, idescola)"
							+ " values ("+id+", "+e.getId()+")";
					stm.addBatch(sql2);				
				}
				stm.executeBatch();
				stm.close();
				con.close();
				
				mensagem = "Roteiro salvo!!";
				
			}else{
				Statement stm = con.createStatement();
				for(Escola e : roteiro.getEscolas()){
					String sql2 = "insert into roteiro_escola (idroteiro, idescola)"
							+ " values ("+id+", "+e.getId()+")";
					stm.addBatch(sql2);
				}
				stm.executeBatch();
				stm.close();
				con.close();
				
				mensagem = "Roteiro atualizado!!";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			mensagem = e.getMessage();
		}
		
		return mensagem;
	}

	public String deletaRoteiro(int idroteiro){
		
		Connection conn = ConnectionFactory.getInstance().getMySqlConnection();
		String sqla = "delete from roteiro where idroteiro = ?";
		String sqlb = "delete from roteiro_escola where idroteiro = ?";
		String mensagem = "Roteiro "+idroteiro+" deletado com sucesso!";
		
		try {
			PreparedStatement pstm = conn.prepareStatement(sqla);
			pstm.setInt(1, idroteiro);
			pstm.execute();
			pstm.close();
			pstm = conn.prepareStatement(sqlb);
			pstm.setInt(1, idroteiro);
			pstm.execute();			
			pstm.close();
			conn.close();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			mensagem = "Erro ao tentar deletar o roteiro "+idroteiro+"\n"
					+ "Mensagem: "+e.getMessage();
		}
		
		return mensagem;
	}
	
	
	public List<Roteiro> listarRoteiros(Usuario vendedor, Date ini, Date fim){
		
		Connection con =  ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select * from roteiro where setor = ? and data between ? and ? "
				+ "order by data desc";
		Usuario vendor = new Usuario();
		List<Roteiro> roteiros = new ArrayList<>();		
			
		try {
			PreparedStatement pstm;
			if(vendedor.getSetor() == 0){
				sql = "select * from roteiro where data between ? and ? "
						+ "order by data desc";
				pstm  = con.prepareStatement(sql);
				pstm.setDate(1, ini);
				pstm.setDate(2, fim);				

			}else{
				pstm  = con.prepareStatement(sql);
				pstm.setInt(1, vendedor.getSetor());
				pstm.setDate(2, ini);
				pstm.setDate(3, fim);				
			}
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				Roteiro roteiro = new Roteiro();
				roteiro.setId(rs.getInt("idroteiro"));
				roteiro.setData(rs.getDate("data"));
				vendor = DAOUsuario.getVendedor(rs.getInt("setor"));
				roteiro.setVendedor(vendor);
				
				Statement stm = con.createStatement();
				ResultSet rst = stm.executeQuery("select * from roteiro_escola natural join escola where idroteiro = "+roteiro.getId());
				List<Escola> escolas = new ArrayList<>();
				
				while(rst.next()){
					Escola escola = new Escola();
					escola.setId(rst.getInt("idescola"));
					escola.setNome(rst.getString("nome"));
					escola.setClassificacao(rst.getString("classificacao"));
					escola.setCnpj(rst.getString("cnpj"));
					escola.setEndereco(rst.getString("endereco"));
					escola.setComplemento(rst.getString("complemento"));
					escola.setBairro(rst.getString("bairro"));
					escola.setMunicipio(rst.getString("municipio"));
					escola.setUf(rst.getString("uf"));
					escola.setCep(rst.getString("cep"));
					escola.setEmail(rst.getString("email"));
					escola.setTelefone(rst.getString("telefone"));
					escola.setSetor(rst.getInt("setor"));
					setObservacaoEscola(escola);
					escola.setVendedor(vendor);
					setTotalAlunos(escola);
					escolas.add(escola);
				}
				rst.close();
				stm.close();
				roteiro.setEscolas(escolas);
				roteiros.add(roteiro);
			}
			rs.close();
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return roteiros;
	}
	
	public String salvarObservacaoEscola(Roteiro roteiro, Escola escola){
		Connection con =  ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "update roteiro_escola set observacao = ? where idroteiro = ? and idescola = ?";
		String mensagem = "";
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, escola.getObservacao());
			pstm.setInt(2, roteiro.getId());
			pstm.setInt(3, escola.getId());
			pstm.execute();
			mensagem = "Observa��o salva!";
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			mensagem = e.getMessage();
		}
				
		return mensagem;
	}
	
	
	public static void setUltimaVisita(Escola e){
		e.setUltimavisita(getUltimasVisitas(e));				
	}
	
	public static List<String> getSeries() throws SQLException{
		Connection con =  ConnectionFactory.getInstance().getMySqlConnection();
		List<String> lista = new ArrayList<>();
		String sql = "select * from serie order by serie_descricao, serie";
		
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		while(rs.next()){
			lista.add(rs.getString("serie"));
		}
		rs.close();
		stm.close();
		con.close();
		
		return lista;
	}

	public static List<String> getSeriesAdocao(int idescola, String ano, Usuario user) throws SQLException{
		Connection con =  ConnectionFactory.getInstance().getMySqlConnection();
		List<String> lista = new ArrayList<>();
		String sql = "select distinct serie_descricao, a.serie from adocao a join"
				+ " serie e on a.serie = e.serie and ano = '"+ano+"' and idescola = "+idescola
				+" order by serie_descricao";
		
		if(user != null)
			lista.add("Todos");
		
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		while(rs.next()){
			lista.add(rs.getString("serie"));
		}
		rs.close();
		stm.close();
		con.close();
		
		return lista;
	}
	
	public static List<String> bairros(Usuario usuario, String dependencia, String municipio) throws SQLException{
		Connection con =  ConnectionFactory.getInstance().getMySqlConnection();
		List<String> lista = new ArrayList<>();
		String sql = " select distinct bairro from"
				+ " escola where setor = "+usuario.getSetor()+" and dependencia = '"+dependencia+"'";
		
		if(usuario.getSetor() == 0){
			sql = " select distinct bairro from"
					+ " escola where setor > "+usuario.getSetor()+" and dependencia = '"+dependencia+"'";			
		}
		
		if(municipio.equalsIgnoreCase("todos")){
			sql += " order by bairro";
		}else{
			sql += " and municipio = '"+municipio+"' order by bairro";
		}
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		while(rs.next()){
			lista.add(rs.getString("bairro"));
		}
		rs.close();
		stm.close();
		con.close();
		
		return lista;
	}
	
	public static List<String> municipios(Usuario usuario, String dependencia){
		Connection con =  ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select distinct municipio from escola where setor = ? and dependencia = ? "
				+ "order by municipio";
		List<String> municipios = new ArrayList<>();
		try {
			PreparedStatement pstm;			
			if(usuario.getSetor() == 0){
				sql = "select distinct municipio from escola where dependencia = ? "
						+ "order by municipio";
				pstm = con.prepareStatement(sql);
				pstm.setString(1, dependencia);				
			}else{
				pstm = con.prepareStatement(sql);
				pstm.setInt(1, usuario.getSetor());
				pstm.setString(2, dependencia);
			}
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				municipios.add(rs.getString("municipio"));
			}
			rs.close();
			pstm.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return municipios;
	}
	
	public static void roteiroResumos(RoteiroResumo resumo, String dependencia){
		Connection con =  ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select count(*) as total from escola where setor = ? and dependencia = ?";
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, resumo.getUsuario().getSetor());
			pstm.setString(2, dependencia);
			ResultSet rs = pstm.executeQuery();
			if(rs.next()){
				resumo.setTotalescolas(rs.getInt("total"));
			}
			rs.close();
			pstm.close();
			sql = "select count(*) as total from escola e inner join (select idescola, "
					+ "setor from roteiro natural join roteiro_escola where setor = ? and data "
					+ "between ? and ? order by idescola) t on e.idescola = t.idescola and e.setor = t.setor and e.dependencia = ?";
			pstm = con.prepareStatement(sql);			
			pstm.setInt(1, resumo.getUsuario().getSetor());
			pstm.setDate(2, resumo.getInicio());
			pstm.setDate(3, resumo.getFim());
			pstm.setString(4, dependencia);
			rs = pstm.executeQuery();
			if(rs.next()){
				resumo.setTotalgeralvisitas(rs.getInt("total"));
			}
			rs.close();
			pstm.close();

			sql = "select distinct data from escola natural join roteiro natural "
					+ "join roteiro_escola where setor = ? and dependencia =? and data between ? and ?"
					+ " order by data asc limit 1";
			pstm = con.prepareStatement(sql);			
			pstm.setInt(1, resumo.getUsuario().getSetor());
			pstm.setString(2, dependencia);
			pstm.setDate(3, resumo.getInicio());
			pstm.setDate(4, resumo.getFim());
			
			rs = pstm.executeQuery();
			if(rs.next()){
				resumo.setInireal(rs.getDate("data"));
			}
			rs.close();
			pstm.close();

			sql = "select distinct data from escola natural join roteiro natural "
					+ "join roteiro_escola where setor = ? and dependencia =? and data"
					+ " between ? and ? order by data desc limit 1";
			pstm = con.prepareStatement(sql);			
			pstm.setInt(1, resumo.getUsuario().getSetor());
			pstm.setString(2, dependencia);
			pstm.setDate(3, resumo.getInicio());
			pstm.setDate(4, resumo.getFim());
			
			rs = pstm.executeQuery();
			if(rs.next()){
				resumo.setFimreal(rs.getDate("data"));
			}
			rs.close();
			pstm.close();

			
			sql = "select count(*) as dias from (select distinct data from escola natural join roteiro natural "
					+ "join roteiro_escola where setor = ? and dependencia =? and data between ? and ?) as t";
			pstm = con.prepareStatement(sql);			
			pstm.setInt(1, resumo.getUsuario().getSetor());
			pstm.setString(2, dependencia);
			pstm.setDate(3, resumo.getInicio());
			pstm.setDate(4, resumo.getFim());
			
			rs = pstm.executeQuery();
			if(rs.next()){
				resumo.setQtdedias(rs.getInt("dias"));
			}
			rs.close();
			pstm.close();

			sql = "select count(*) as total from escola e inner join (select distinct idescola, "
					+ "setor from roteiro natural join roteiro_escola where setor = ? and data "
					+ "between ? and ? order by idescola) t on e.idescola = t.idescola and "
					+ "e.setor = t.setor and e.dependencia = ?";
			pstm = con.prepareStatement(sql);			
			pstm.setInt(1, resumo.getUsuario().getSetor());
			pstm.setDate(2, resumo.getInicio());
			pstm.setDate(3, resumo.getFim());
			pstm.setString(4, dependencia);
			rs = pstm.executeQuery();
			if(rs.next()){
				resumo.setTotalvisitas(rs.getInt("total"));
			}
			rs.close();
			pstm.close();
			sql = "select count(*) as qtde_escolas from (select count(*) as total from escola e inner join (select idescola, "
					+ "setor from roteiro natural join roteiro_escola where setor = ? and data "
					+ "between ? and ? order by idescola) t on e.idescola = t.idescola and e.setor = t.setor and e.dependencia = ? "
					+ "group by e.idescola having total > 1) as f";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, resumo.getUsuario().getSetor());
			pstm.setDate(2, resumo.getInicio());
			pstm.setDate(3, resumo.getFim());
			pstm.setString(4, dependencia);
			rs = pstm.executeQuery();
			if(rs.next()){
				resumo.setMaisdeumavisita(rs.getInt("qtde_escolas"));
			}
			rs.close();
			pstm.close();
			int semvisita = resumo.getTotalescolas() - resumo.getTotalvisitas();
			resumo.setSemvisitas(semvisita);
			
			sql = "select count(*) as total from (select idescola from escola e where e.setor = ?"
					+ " and dependencia = ? and e.idescola not in (select idescola from (select distinct idescola, "
					+ "count(*) as escolas from roteiro natural join roteiro_escola where setor = ?"
					+ " group by idescola) as t) group by idescola) w";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, resumo.getUsuario().getSetor());
			pstm.setString(2, dependencia);
			pstm.setInt(3, resumo.getUsuario().getSetor());
			rs = pstm.executeQuery();
			if(rs.next()){
				resumo.setNuncavisitadas(rs.getInt("total"));
			}
			rs.close();
			pstm.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static ResultSetToExcel relAdocaoTermometro (String ano, Usuario usuario) throws Exception{
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		
		String sql = "select familia, colecao, disciplina, x.codigo, descricao, serie_nivel as segmento,"
				+ " count(idescola) as escolas, sum(quantidade) as alunos, x.ano from produto p inner join"
				+ " (select codigo, idescola, serie, serie_nivel, quantidade, v.ano from item_adocao i"
				+ " inner join (select idadocao, a.idescola, a.serie, serie_nivel, quantidade, a.ano"
				+ " from adocao a inner join (select idescola, e.serie, serie_nivel, quantidade from"
				+ " escola_serie_aluno e inner join serie s on e.serie = s.serie and e.ano = ?) u on a.idescola = u.idescola"
				+ " and a.serie = u.serie and a.ano = ?) v on i.idadocao = v.idadocao order by codigo)"
				+ " x on p.codigo = x.codigo group by serie_nivel, codigo order by familia, colecao, disciplina, codigo";
		
		String sqlu = "select familia, colecao, disciplina, x.codigo, descricao, serie_nivel as segmento,"
				+ " count(idescola) as escolas, sum(quantidade) as alunos, x.ano from produto p inner join"
				+ " (select codigo, idescola, serie, serie_nivel, quantidade, v.ano from item_adocao i"
				+ " inner join (select idadocao, a.idescola, a.serie, serie_nivel, quantidade, a.ano"
				+ " from adocao a inner join (select z.idescola, serie, serie_nivel, quantidade from"
				+ " escola l inner join (select idescola, e.serie, serie_nivel, quantidade from"
				+ " escola_serie_aluno e inner join serie s on e.serie = s.serie and e.ano = ?) z on l.idescola = z.idescola"
				+ " and setor = ?)u on a.idescola = u.idescola"
				+ " and a.serie = u.serie and a.ano = ?) v on i.idadocao = v.idadocao order by codigo)"
				+ " x on p.codigo = x.codigo group by serie_nivel, codigo order by familia, colecao, disciplina, codigo";		
				
		try {
			PreparedStatement pstm;
			if(usuario.getCargo() == 3){
				
				pstm = con.prepareStatement(sqlu);
				pstm.setString(1, ano);
				pstm.setInt(2, usuario.getSetor());
				pstm.setString(3, ano);
				
			}else{
					pstm = con.prepareStatement(sql);
					pstm.setString(1, ano);
					pstm.setString(2, ano);					
			}
			
			ResultSet rs = pstm.executeQuery();
			ResultSetToExcel rsx = new ResultSetToExcel(rs, "termometro");
			//rsx.generate(response);
			return rsx;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		return null;
	}


	public static ResultSetToExcel relAdocaoTermometroAnalitico (String ano, Usuario usuario) throws Exception{
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		String sql = "select ano, setor, nome, a.idescola, idftd, bairro, municipio, familia, colecao, disciplina, a.codigo, descricao, segmento, a.serie, sum(qtalunos) as alunos, situacao"
				+ " from (select codigo, serie, situacao, e.idftd, e.idescola, nome, bairro, municipio, setor"
				+ " from escola e inner join (select a.codigo, a.idescola, a.serie, ifnull((case fator when 1 then 'nova adocao' when"
				+ " null then 'nova adocao' end),'manutencao') as situacao from (select codigo, serie, idescola from adocao a inner"
				+ " join item_adocao b on a.idadocao = b.idadocao and a.ano = ?) as a left join (select codigo, serie, idescola, count(a.ano)"
				+ " as fator from adocao a inner join item_adocao b on a.idadocao= b.idadocao group by idescola, serie, codigo) as b on"
				+ " a.idescola = b.idescola and a.serie = b.serie and a.codigo = b.codigo) as y on e.idescola = y.idescola) as a inner"
				+ " join (select familia, idescola, colecao, disciplina, x.codigo, descricao, x.serie, serie_nivel as segmento,"
				+ " sum(quantidade) as qtalunos, x.ano from produto p inner join (select codigo, idescola, serie, serie_nivel, quantidade,"
				+ " ano from item_adocao i inner join (select idadocao, a.idescola, a.serie, serie_nivel, quantidade, a.ano from adocao a"
				+ " inner join (select idescola, e.serie, serie_nivel, quantidade from escola_serie_aluno e inner join serie s on"
				+ " e.serie = s.serie and e.ano = ?) u on a.idescola = u.idescola and a.serie = u.serie and a.ano = ?) v on i.idadocao = v.idadocao"
				+ " order by codigo) x on p.codigo = x.codigo group by idescola, serie_nivel, serie, codigo) as b on a.codigo = b.codigo"
				+ " and a.serie = b.serie and a.idescola = b.idescola group by nome, segmento, a.serie, a.codigo";
				
		try {
			PreparedStatement pstm;
			if(usuario.getCargo() == 3){
				sql = "select ano, setor, nome, a.idescola, idftd, bairro, municipio, familia, colecao, disciplina, a.codigo, descricao, segmento, a.serie, sum(qtalunos) as alunos, situacao"
				+ " from (select codigo, serie, situacao, e.idftd, e.idescola, nome, bairro, municipio, setor"
				+ " from escola e inner join (select a.codigo, a.idescola, a.serie, ifnull((case fator when 1 then 'nova adocao' when"
				+ " null then 'nova adocao' end),'manutencao') as situacao from (select codigo, serie, idescola from adocao a inner"
				+ " join item_adocao b on a.idadocao = b.idadocao and a.ano = ?) as a left join (select codigo, serie, idescola, count(a.ano)"
				+ " as fator from adocao a inner join item_adocao b on a.idadocao= b.idadocao group by idescola, serie, codigo) as b on"
				+ " a.idescola = b.idescola and a.serie = b.serie and a.codigo = b.codigo) as y on e.idescola = y.idescola) as a inner"
				+ " join (select familia, idescola, colecao, disciplina, x.codigo, descricao, x.serie, serie_nivel as segmento,"
				+ " sum(quantidade) as qtalunos, x.ano from produto p inner join (select codigo, idescola, serie, serie_nivel, quantidade,"
				+ " ano from item_adocao i inner join (select idadocao, a.idescola, a.serie, serie_nivel, quantidade, a.ano from adocao a"
				+ " inner join (select idescola, e.serie, serie_nivel, quantidade from escola_serie_aluno e inner join serie s on"
				+ " e.serie = s.serie and e.ano = ?) u on a.idescola = u.idescola and a.serie = u.serie and a.ano = ?) v on i.idadocao = v.idadocao"
				+ " order by codigo) x on p.codigo = x.codigo group by idescola, serie_nivel, serie, codigo) as b on a.codigo = b.codigo"
				+ " and a.serie = b.serie and a.idescola = b.idescola group by nome, segmento, a.serie, a.codigo having setor = ?";
				
				pstm = con.prepareStatement(sql);
				pstm.setString(1, ano);
				pstm.setString(2, ano);
				pstm.setString(3, ano);
				pstm.setInt(4, usuario.getSetor());
				
			}else{
				
				pstm = con.prepareStatement(sql);
				pstm.setString(1, ano);
				pstm.setString(2, ano);
				pstm.setString(3, ano);
			}
			
			ResultSet rs = pstm.executeQuery();
			ResultSetToExcel rsx = new ResultSetToExcel(rs, "termometroanalitico");
			//rsx.generate(response);
			return rsx;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	

	public static Workbook monitorFTD (String beginArq, String ano, ServletContext context, Usuario usuario) throws Exception{
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		List<MonitorAdocao> lista = new ArrayList<>();
		List<ItemMonitorAdocao> itens = new ArrayList<>();
		Map<String, Integer> contar = new HashMap<>();
		
		String sql = "select grupo, setor, nome, a.idescola, idftd, familia, colecao, a.serie, segmento, sum(qtalunos) as alunos, situacao"
				+ " from (select codigo, serie, situacao, e.idftd, e.idescola, nome, bairro, municipio, setor"
				+ " from escola e inner join (select a.codigo, a.idescola, a.serie, ifnull((case fator when 1 then 'nova' when"
				+ " null then 'nova' end),'renovacao') as situacao from (select codigo, serie, idescola from adocao a inner"
				+ " join item_adocao b on a.idadocao = b.idadocao and a.ano = ?) as a left join (select codigo, serie, idescola, count(a.ano)"
				+ " as fator from adocao a inner join item_adocao b on a.idadocao= b.idadocao group by idescola, serie, codigo) as b on"
				+ " a.idescola = b.idescola and a.serie = b.serie and a.codigo = b.codigo) as y on e.idescola = y.idescola) as a inner"
				+ " join (select grupo, familia, idescola, colecao, disciplina, x.codigo, descricao, x.serie, serie_nivel as segmento,"
				+ " sum(quantidade) as qtalunos, x.ano from produto p inner join (select codigo, idescola, serie, serie_nivel, quantidade,"
				+ " ano from item_adocao i inner join (select idadocao, a.idescola, a.serie, serie_nivel, quantidade, a.ano from adocao a"
				+ " inner join (select idescola, e.serie, serie_nivel, quantidade from escola_serie_aluno e inner join serie s on"
				+ " e.serie = s.serie and e.ano = ?) u on a.idescola = u.idescola and a.serie = u.serie and a.ano = ?) v on i.idadocao = v.idadocao"
				+ " order by codigo) x on p.codigo = x.codigo group by idescola, serie_nivel, serie, codigo) as b on a.codigo = b.codigo"
				+ " and a.serie = b.serie and a.idescola = b.idescola group by nome, colecao, a.serie having not isnull(grupo) and grupo <> ''";
		
		String sqli = "select familia, grupo, a.serie, colecao, sum(qtalunos) as alunos, count(a.idescola) as adocoes from (select codigo,"
				+ " serie, situacao, e.idftd, e.idescola, nome, bairro, municipio, setor from escola e inner join (select a.codigo, a.idescola,"
				+ " a.serie, ifnull((case fator when 1 then 'nova adocao' when null then 'nova adocao' end),'renovacao') as situacao from"
				+ " (select codigo, serie, idescola from adocao a inner join item_adocao b on a.idadocao = b.idadocao and a.ano = ?) as a"
				+ " left join (select codigo, serie, idescola, count(a.ano) as fator from adocao a inner join item_adocao b on a.idadocao ="
				+ " b.idadocao group by idescola, serie, codigo) as b on a.idescola = b.idescola and a.serie = b.serie and a.codigo = b.codigo)"
				+ " as y on e.idescola = y.idescola) as a inner join (select grupo, familia, idescola, colecao, disciplina, x.codigo, descricao,"
				+ " x.serie, serie_nivel as segmento, sum(quantidade) as qtalunos, x.ano from produto p inner join (select codigo, idescola,"
				+ " serie, serie_nivel, quantidade, ano from item_adocao i inner join (select idadocao, a.idescola, a.serie, serie_nivel,"
				+ " quantidade, a.ano from adocao a inner join (select idescola, e.serie, serie_nivel, quantidade from escola_serie_aluno e"
				+ " inner join serie s on e.serie = s.serie and e.ano = ?) u on a.idescola = u.idescola and a.serie = u.serie and a.ano = ?) v on"
				+ " i.idadocao = v.idadocao order by codigo) x on p.codigo = x.codigo group by idescola, serie_nivel, serie, codigo)"
				+ " as b on a.codigo = b.codigo and a.serie = b.serie and a.idescola = b.idescola group by grupo, colecao, a.serie"
				+ " having not isnull(grupo) and grupo <> ''";
		
		String sqlcontar = "select colecao, count(colecao) as adocoes from (select distinct nome, colecao from (select grupo, setor,"
				+ " nome, idftd, colecao, familia, a.serie, sum(qtalunos) as alunos, situacao from (select codigo, serie, situacao,"
				+ " e.idftd, e.idescola, nome, bairro, municipio, setor from escola e inner join (select a.codigo, a.idescola, a.serie,"
				+ " ifnull((case fator when 1 then 'nova adocao' when null then 'nova adocao' end),'renovacao') as situacao from"
				+ " (select codigo, serie, idescola from adocao a inner join item_adocao b on a.idadocao = b.idadocao and a.ano = ?) as"
				+ " a left join (select codigo, serie, idescola, count(a.ano) as fator from adocao a inner join item_adocao b on a.idadocao ="
				+ " b.idadocao group by idescola, serie, codigo) as b on a.idescola = b.idescola and a.serie = b.serie and a.codigo = b.codigo)"
				+ " as y on e.idescola = y.idescola) as a inner join (select grupo, familia, idescola, colecao, disciplina, x.codigo, descricao,"
				+ " x.serie, serie_nivel as segmento, sum(quantidade) as qtalunos, x.ano from produto p inner join (select codigo, idescola,"
				+ " serie, serie_nivel, quantidade, ano from item_adocao i inner join (select idadocao, a.idescola, a.serie, serie_nivel,"
				+ " quantidade, a.ano from adocao a inner join (select idescola, e.serie, serie_nivel, quantidade from escola_serie_aluno"
				+ " e inner join serie s on e.serie = s.serie and e.ano = ?) u on a.idescola = u.idescola and a.serie = u.serie and a.ano = ?) v on"
				+ " i.idadocao = v.idadocao order by codigo) x on p.codigo = x.codigo group by idescola, serie_nivel,"
				+ " serie, codigo) as b on a.codigo = b.codigo and a.serie = b.serie and a.idescola = b.idescola group by nome,"
				+ " colecao, a.serie having not isnull(grupo) and grupo <> '' order by colecao, nome) as b) as b group by colecao";
		
		try {
			PreparedStatement pstm;
			ResultSet rs;
			if(usuario.getCargo() == 3){
				sql = "select grupo, setor, nome, a.idescola, idftd, familia, colecao, a.serie, segmento, sum(qtalunos) as alunos, situacao"
				+ " from (select codigo, serie, situacao, e.idftd, e.idescola, nome, bairro, municipio, setor"
				+ " from escola e inner join (select a.codigo, a.idescola, a.serie, ifnull((case fator when 1 then 'nova' when"
				+ " null then 'nova' end),'renovacao') as situacao from (select codigo, serie, idescola from adocao a inner"
				+ " join item_adocao b on a.idadocao = b.idadocao and a.ano = ?) as a left join (select codigo, serie, idescola, count(a.ano)"
				+ " as fator from adocao a inner join item_adocao b on a.idadocao= b.idadocao group by idescola, serie, codigo) as b on"
				+ " a.idescola = b.idescola and a.serie = b.serie and a.codigo = b.codigo) as y on e.idescola = y.idescola) as a inner"
				+ " join (select grupo, familia, idescola, colecao, disciplina, x.codigo, descricao, x.serie, serie_nivel as segmento,"
				+ " sum(quantidade) as qtalunos, x.ano from produto p inner join (select codigo, idescola, serie, serie_nivel, quantidade,"
				+ " ano from item_adocao i inner join (select idadocao, a.idescola, a.serie, serie_nivel, quantidade, a.ano from adocao a"
				+ " inner join (select idescola, e.serie, serie_nivel, quantidade from escola_serie_aluno e inner join serie s on"
				+ " e.serie = s.serie and e.ano = ?) u on a.idescola = u.idescola and a.serie = u.serie and a.ano = ?) v on i.idadocao = v.idadocao"
				+ " order by codigo) x on p.codigo = x.codigo group by idescola, serie_nivel, serie, codigo) as b on a.codigo = b.codigo"
				+ " and a.serie = b.serie and a.idescola = b.idescola group by nome, colecao, a.serie having not isnull(grupo)"
				+ " and grupo <> '' and setor = ?";
				
				sqli = "select setor, familia, grupo, a.serie, colecao, sum(qtalunos) as alunos, count(a.idescola) as adocoes from (select codigo,"
						+ " serie, situacao, e.idftd, e.idescola, nome, bairro, municipio, setor from escola e inner join (select a.codigo, a.idescola,"
						+ " a.serie, ifnull((case fator when 1 then 'nova adocao' when null then 'nova adocao' end),'renovacao') as situacao from"
						+ " (select codigo, serie, idescola from adocao a inner join item_adocao b on a.idadocao = b.idadocao and a.ano = ?) as a"
						+ " left join (select codigo, serie, idescola, count(a.ano) as fator from adocao a inner join item_adocao b on a.idadocao ="
						+ " b.idadocao group by idescola, serie, codigo) as b on a.idescola = b.idescola and a.serie = b.serie and a.codigo = b.codigo)"
						+ " as y on e.idescola = y.idescola) as a inner join (select grupo, familia, idescola, colecao, disciplina, x.codigo, descricao,"
						+ " x.serie, serie_nivel as segmento, sum(quantidade) as qtalunos, x.ano from produto p inner join (select codigo, idescola,"
						+ " serie, serie_nivel, quantidade, ano from item_adocao i inner join (select idadocao, a.idescola, a.serie, serie_nivel,"
						+ " quantidade, a.ano from adocao a inner join (select idescola, e.serie, serie_nivel, quantidade from escola_serie_aluno e"
						+ " inner join serie s on e.serie = s.serie and e.ano = ?) u on a.idescola = u.idescola and a.serie = u.serie and a.ano = ?) v on"
						+ " i.idadocao = v.idadocao order by codigo) x on p.codigo = x.codigo group by idescola, serie_nivel, serie, codigo)"
						+ " as b on a.codigo = b.codigo and a.serie = b.serie and a.idescola = b.idescola group by setor, grupo, colecao, a.serie"
						+ " having not isnull(grupo) and grupo <> '' and setor = ?";

				sqlcontar = "select colecao, count(colecao) as adocoes from (select distinct nome, colecao from (select grupo, setor,"
						+ " nome, idftd, colecao, familia, a.serie, sum(qtalunos) as alunos, situacao from (select codigo, serie, situacao,"
						+ " e.idftd, e.idescola, nome, bairro, municipio, setor from escola e inner join (select a.codigo, a.idescola, a.serie,"
						+ " ifnull((case fator when 1 then 'nova adocao' when null then 'nova adocao' end),'renovacao') as situacao from"
						+ " (select codigo, serie, idescola from adocao a inner join item_adocao b on a.idadocao = b.idadocao and a.ano = ?) as"
						+ " a left join (select codigo, serie, idescola, count(a.ano) as fator from adocao a inner join item_adocao b on a.idadocao ="
						+ " b.idadocao group by idescola, serie, codigo) as b on a.idescola = b.idescola and a.serie = b.serie and a.codigo = b.codigo)"
						+ " as y on e.idescola = y.idescola) as a inner join (select grupo, familia, idescola, colecao, disciplina, x.codigo, descricao,"
						+ " x.serie, serie_nivel as segmento, sum(quantidade) as qtalunos, x.ano from produto p inner join (select codigo, idescola,"
						+ " serie, serie_nivel, quantidade, ano from item_adocao i inner join (select idadocao, a.idescola, a.serie, serie_nivel,"
						+ " quantidade, a.ano from adocao a inner join (select idescola, e.serie, serie_nivel, quantidade from escola_serie_aluno"
						+ " e inner join serie s on e.serie = s.serie and e.ano = ?) u on a.idescola = u.idescola and a.serie = u.serie and a.ano = ?) v on"
						+ " i.idadocao = v.idadocao order by codigo) x on p.codigo = x.codigo group by idescola, serie_nivel,"
						+ " serie, codigo) as b on a.codigo = b.codigo and a.serie = b.serie and a.idescola = b.idescola group by nome,"
						+ " colecao, a.serie having not isnull(grupo) and grupo <> '' and setor = ? order by colecao, nome) as b) as b group by colecao";				
				
				pstm = con.prepareStatement(sql);
				pstm.setString(1, ano);
				pstm.setString(2, ano);
				pstm.setString(3, ano);
				pstm.setInt(4, usuario.getSetor());
				rs = pstm.executeQuery();
				int idant = 0, idatu;
				String colant = "x", colatu;
				MonitorAdocao ma = null;
				while(rs.next()){					
					idatu = rs.getInt("idescola");
					colatu = rs.getString("colecao");
					String serie = rs.getString("serie");
					String nome = rs.getString("nome");
					int qtde = rs.getInt("alunos");
					String situa = rs.getString("situacao");
	
					if(idant == idatu){
						if(colant.equalsIgnoreCase(colatu)){
							ma.setQtdeSerie(serie, qtde, situa);
						}else{
							lista.add(ma);
							ma = new MonitorAdocao();
							ma.setCodigoftd(rs.getInt("idftd"));
							ma.setGrupo(rs.getString("grupo"));
							if(nome.length()>40){
								ma.setNome(nome.substring(0, 40));
							}else{
								ma.setNome(nome);
							}
							ma.setSetor(rs.getInt("setor"));
							ma.setColecao(rs.getString("colecao"));
							ma.setQtdeSerie(serie, qtde, situa);
							idant = idatu;
							colant = colatu;
						}
					}else{
						if(ma != null){
							lista.add(ma);							
						}
						ma = new MonitorAdocao();
						ma.setCodigoftd(rs.getInt("idftd"));
						ma.setGrupo(rs.getString("grupo"));
						if(nome.length()>40){
							ma.setNome(nome.substring(0, 40));
						}else{
							ma.setNome(nome);
						}
						ma.setSetor(rs.getInt("setor"));
						ma.setColecao(rs.getString("colecao"));
						ma.setQtdeSerie(serie, qtde, situa);
						idant = idatu;
						colant = colatu;						
					}
				}
				lista.add(ma);
				rs.close();
				pstm.close();
				pstm = con.prepareStatement(sqli);
				pstm.setString(1, ano);
				pstm.setString(2, ano);
				pstm.setString(3, ano);
				pstm.setInt(4, usuario.getSetor());
				rs = pstm.executeQuery();				
				ItemMonitorAdocao ima = null;
				colant = "x"; colatu = "";
				while(rs.next()){
					int qtde = rs.getInt("alunos");
					String serie = rs.getString("serie");
					colatu = rs.getString("colecao");
					
					if(colant.equalsIgnoreCase(colatu)){
						ima.setQtdeSerie(serie, qtde);						
					}else if(colant.equalsIgnoreCase("x")){
						ima = new ItemMonitorAdocao();
						ima.setGrupo(rs.getString("grupo"));
						ima.setColecao(rs.getString("colecao"));
						ima.setQtdeSerie(serie, qtde);
					}else{
						ima.refazTotais();
						itens.add(ima);
						ima = new ItemMonitorAdocao();
						ima.setGrupo(rs.getString("grupo"));
						ima.setColecao(rs.getString("colecao"));
						ima.setQtdeSerie(serie, qtde);
					}
					colant = colatu;
				}
				ima.refazTotais();
				itens.add(ima);
				rs.close();
				pstm.close();

				pstm = con.prepareStatement(sqlcontar);
				pstm.setString(1, ano);
				pstm.setString(2, ano);
				pstm.setString(3, ano);
				pstm.setInt(4, usuario.getSetor());
				rs = pstm.executeQuery();
				while(rs.next()){
					contar.put(rs.getString("colecao"), rs.getInt("adocoes"));
				}
				rs.close();
				pstm.close();				
				con.close();
			}else{
				
				pstm = con.prepareStatement(sql);
				pstm.setString(1, ano);
				pstm.setString(2, ano);
				pstm.setString(3, ano);
				rs = pstm.executeQuery();
				int idant = 0, idatu;
				String colant = "x", colatu;
				MonitorAdocao ma = null;
				while(rs.next()){					
					idatu = rs.getInt("idescola");
					colatu = rs.getString("colecao");
					String serie = rs.getString("serie");
					String nome = rs.getString("nome");
					int qtde = rs.getInt("alunos");
					String situa = rs.getString("situacao");

					if(idant == idatu){
						if(colant.equalsIgnoreCase(colatu)){
							ma.setQtdeSerie(serie, qtde, situa);
						}else{
							lista.add(ma);
							ma = new MonitorAdocao();
							ma.setCodigoftd(rs.getInt("idftd"));
							ma.setGrupo(rs.getString("grupo"));
							if(nome.length()>40){
								ma.setNome(nome.substring(0, 40));
							}else{
								ma.setNome(nome);
							}
							ma.setSetor(rs.getInt("setor"));
							ma.setColecao(rs.getString("colecao"));
							ma.setQtdeSerie(serie, qtde, situa);
							idant = idatu;
							colant = colatu;
						}
					}else{
						if(ma != null){
							lista.add(ma);							
						}
						ma = new MonitorAdocao();
						ma.setCodigoftd(rs.getInt("idftd"));
						ma.setGrupo(rs.getString("grupo"));
						if(nome.length()>40){
							ma.setNome(nome.substring(0, 40));
						}else{
							ma.setNome(nome);
						}
						ma.setSetor(rs.getInt("setor"));
						ma.setColecao(rs.getString("colecao"));
						ma.setQtdeSerie(serie, qtde, situa);
						idant = idatu;
						colant = colatu;						
					}
				}
				lista.add(ma);
				rs.close();
				pstm.close();
				pstm = con.prepareStatement(sqli);
				pstm.setString(1, ano);
				pstm.setString(2, ano);
				pstm.setString(3, ano);
				rs = pstm.executeQuery();
				ItemMonitorAdocao ima = null;
				colant = "x"; colatu = "";
				while(rs.next()){
					int qtde = rs.getInt("alunos");
					String serie = rs.getString("serie");
					colatu = rs.getString("colecao");

					if(colant.equalsIgnoreCase(colatu)){
						ima.setQtdeSerie(serie, qtde);						
					}else if(colant.equalsIgnoreCase("x")){
						ima = new ItemMonitorAdocao();
						ima.setGrupo(rs.getString("grupo"));
						ima.setColecao(rs.getString("colecao"));
						ima.setQtdeSerie(serie, qtde);
					}else{
						ima.refazTotais();
						itens.add(ima);
						ima = new ItemMonitorAdocao();
						ima.setGrupo(rs.getString("grupo"));
						ima.setColecao(rs.getString("colecao"));
						ima.setQtdeSerie(serie, qtde);
					}
					colant = colatu;
				}
				ima.refazTotais();
				itens.add(ima);
				rs.close();
				pstm.close();	

				pstm = con.prepareStatement(sqlcontar);
				pstm.setString(1, ano);
				pstm.setString(2, ano);
				pstm.setString(3, ano);
				rs = pstm.executeQuery();
				while(rs.next()){
					contar.put(rs.getString("colecao"), rs.getInt("adocoes"));
				}
				rs.close();
				pstm.close();				
				con.close();				
			}
			
			for(ItemMonitorAdocao i : itens){
				if(contar.get(i.getColecao()) != null)
					i.setAdocoes(contar.get(i.getColecao()));
			}
			
			Collections.sort(itens);
			Collections.sort(lista);
				    	
	    	String realPath = context.getRealPath("/");	    	
			String templatePath = realPath + "/resources/xls/Template_monitoramento_adocao.xls";
			InputStream is = new FileInputStream(templatePath);
			Map<String, Object> beans = new HashMap<>();
			beans.put("anomonitor", "Mercado "+ano);
			beans.put("imon", lista);
			beans.put("itmon", itens);
						
			XLSTransformer transformer = new XLSTransformer();
			Workbook workbook = transformer.transformXLS(is, beans);
			return workbook;
			//workbook.write(response.getOutputStream());						

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	public static ResultSetToExcel relAdocaoFaturamento (String ano, Usuario usuario) throws Exception{
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		String sql = "select ano, setor, x.idescola, idftd, nome, municipio, valor"
				+ " from (select ano, setor, idescola, nome, idftd, municipio, sum(total) as valor from (select ano, setor,"
				+ " idescola, nome, idftd, municipio, serie, codigo, descricao, (alunos*preco) as total from (select ano, setor,"
				+ " nome, a.idescola, idftd, bairro, municipio, familia, colecao, disciplina, a.codigo, a.preco, sum(qtalunos) as"
				+ " alunos, descricao, segmento, a.serie from (select codigo, preco, serie, e.idftd, e.idescola, nome, bairro,"
				+ " municipio, setor from escola e inner join (select a.codigo, a.preco, a.idescola, a.serie from (select codigo,"
				+ " b.preco, serie, idescola from adocao a inner join item_adocao b on a.idadocao = b.idadocao and a.ano = ?)"
				+ " as a left join (select codigo, serie, idescola, count(a.ano) as fator from adocao a inner join item_adocao b on"
				+ " a.idadocao = b.idadocao group by idescola, serie, codigo) as b on a.idescola = b.idescola and a.serie = b.serie"
				+ " and a.codigo = b.codigo) as y on e.idescola = y.idescola) as a inner join (select familia, idescola, colecao,"
				+ " disciplina, x.codigo, descricao, x.serie, serie_nivel as segmento, sum(quantidade) as qtalunos, x.ano from produto"
				+ " p inner join (select codigo, idescola, serie, serie_nivel, quantidade, ano from item_adocao i inner join (select"
				+ " idadocao, a.idescola, a.serie, serie_nivel, quantidade, a.ano from adocao a inner join (select idescola, e.serie,"
				+ " serie_nivel, quantidade from escola_serie_aluno e inner join serie s on e.serie = s.serie and e.ano = ?) u on a.idescola = u.idescola"
				+ " and a.serie = u.serie and a.ano = ?) v on i.idadocao = v.idadocao order by codigo) x on p.codigo = x.codigo"
				+ " group by idescola, serie_nivel, serie, codigo) as b on a.codigo = b.codigo and a.serie = b.serie and a.idescola ="
				+ " b.idescola group by nome, segmento, a.serie, a.codigo) as w) as t group by idescola) as x inner join (select idescola,"
				+ " sum(quantidade) as alunos from escola_serie_aluno group by idescola) as z on x.idescola = z.idescola order by valor"
				+ " desc";
				
		try {
			PreparedStatement pstm;
			if(usuario.getCargo() == 3){
				sql = "select ano, setor, x.idescola, idftd, nome, municipio, valor"
						+ " from (select ano, setor, idescola, nome, idftd, municipio, sum(total) as valor from (select ano, setor,"
						+ " idescola, nome, idftd, municipio, serie, codigo, descricao, (alunos*preco) as total from (select ano, setor,"
						+ " nome, a.idescola, idftd, bairro, municipio, familia, colecao, disciplina, a.codigo, a.preco, sum(qtalunos) as"
						+ " alunos, descricao, segmento, a.serie from (select codigo, preco, serie, e.idftd, e.idescola, nome, bairro,"
						+ " municipio, setor from escola e inner join (select a.codigo, a.preco, a.idescola, a.serie from (select codigo,"
						+ " b.preco, serie, idescola from adocao a inner join item_adocao b on a.idadocao = b.idadocao and a.ano = ?)"
						+ " as a left join (select codigo, serie, idescola, count(a.ano) as fator from adocao a inner join item_adocao b on"
						+ " a.idadocao = b.idadocao group by idescola, serie, codigo) as b on a.idescola = b.idescola and a.serie = b.serie"
						+ " and a.codigo = b.codigo) as y on e.idescola = y.idescola) as a inner join (select familia, idescola, colecao,"
						+ " disciplina, x.codigo, descricao, x.serie, serie_nivel as segmento, sum(quantidade) as qtalunos, x.ano from produto"
						+ " p inner join (select codigo, idescola, serie, serie_nivel, quantidade, ano from item_adocao i inner join (select"
						+ " idadocao, a.idescola, a.serie, serie_nivel, quantidade, a.ano from adocao a inner join (select idescola, e.serie,"
						+ " serie_nivel, quantidade from escola_serie_aluno e inner join serie s on e.serie = s.serie and e.ano = ?) u on a.idescola = u.idescola"
						+ " and a.serie = u.serie and a.ano = ?) v on i.idadocao = v.idadocao order by codigo) x on p.codigo = x.codigo"
						+ " group by idescola, serie_nivel, serie, codigo) as b on a.codigo = b.codigo and a.serie = b.serie and a.idescola ="
						+ " b.idescola group by nome, segmento, a.serie, a.codigo) as w) as t group by idescola) as x inner join (select idescola,"
						+ " sum(quantidade) as alunos from escola_serie_aluno group by idescola) as z on x.idescola = z.idescola having setor = ? order by valor"
						+ " desc";
				
				pstm = con.prepareStatement(sql);
				pstm.setString(1, ano);
				pstm.setString(2, ano);
				pstm.setString(3, ano);
				pstm.setInt(4, usuario.getSetor());
				
			}else{
				
				pstm = con.prepareStatement(sql);
				pstm.setString(1, ano);
				pstm.setString(2, ano);
				pstm.setString(3, ano);
			}
			
			ResultSet rs = pstm.executeQuery();
			ResultSetToExcel rsx = new ResultSetToExcel(rs, ano);
			return rsx;			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static List<Escola> getGlassList(GlassView glass){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		List<Escola> lista = new ArrayList<>();
		String sql = "select distinct a.idescola, nome, bairro, municipio, setor from"
				+ " escola a inner join adocao b on a.idescola = b.idescola and"
				+ " a.dependencia = ? and b.ano = ? and a.setor = ? order by nome";
		PreparedStatement pstm;
		int setor = glass.getConsultor().getSetor();
		try {
		
		if(setor == 0){
			sql = "select distinct a.idescola, nome, bairro, municipio, setor from"
					+ " escola a inner join adocao b on a.idescola = b.idescola and"
					+ " a.dependencia = ? and b.ano = ? order by nome";
				pstm = con.prepareStatement(sql);
				pstm.setString(1, glass.getDependencia());
				pstm.setString(2, glass.getYear());
		}else{
			pstm = con.prepareStatement(sql);
			pstm.setString(1, glass.getDependencia());
			pstm.setString(2, glass.getYear());
			pstm.setInt(3, setor);
		}
		
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			Escola e = new Escola();
			e.setId(rs.getInt("idescola"));
			e.setNome(rs.getString("nome"));
			e.setBairro(rs.getString("bairro"));
			e.setMunicipio(rs.getString("municipio"));
			e.setVendedor(DAOUsuario.getVendedor(rs.getInt("setor")));
			lista.add(e);
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
	
	public static boolean bonusRegistrar(Bonus bonus){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "insert into bonus values(?,?,?,?,?)";
		boolean flag = true;
		
		if(verificaBonus(bonus)){
			return true;
		}
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, bonus.getEscola().getId());
			pstm.setString(2, bonus.getAno());
			pstm.setDouble(3, bonus.getDesconto());
			pstm.setString(4, bonus.getDescricao());
			pstm.setString(5, bonus.getTipo());
			flag = pstm.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public static void bonusEditar(Bonus bonus, String descricao, float desconto){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "update bonus set desconto = ?, descricao = ? where idescola = ? and ano = ? and desconto = ?";
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setDouble(1, desconto);
			pstm.setString(2, descricao);
			pstm.setInt(3, bonus.getEscola().getId());
			pstm.setString(4, bonus.getAno());
			pstm.setDouble(5, bonus.getDesconto());
			pstm.execute();
			bonus.setDesconto(desconto);
			bonus.setDescricao(descricao);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public static boolean bonusDeletar(Bonus bonus){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "delete from bonus where idescola = ? and ano = ? and desconto = ?";
		boolean flag = true;
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, bonus.getEscola().getId());
			pstm.setString(2, bonus.getAno());
			pstm.setDouble(3, bonus.getDesconto());
			flag = pstm.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean verificaBonus(Bonus bonus){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select * from bonus where idescola = ? and ano = ? and desconto = ?";
		
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, bonus.getEscola().getId());
			pstm.setString(2, bonus.getAno());
			pstm.setDouble(3, bonus.getDesconto());
			ResultSet rs = pstm.executeQuery();
			if(rs.next()){
				return true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public static void recarregaBonus(Bonus bonus){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select * from bonus where idescola = ? and ano = ? and desconto = ?";
		recarrega(bonus.getEscola());
		bonus.getEscola().setVendedor(DAOUsuario.getVendedor(bonus.getEscola().getSetor()));
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, bonus.getEscola().getId());
			pstm.setString(2, bonus.getAno());
			pstm.setDouble(3, bonus.getDesconto());
			ResultSet rs = pstm.executeQuery();
			if(rs.next()){
				bonus.setDesconto(rs.getFloat("desconto"));
				bonus.setDescricao(rs.getString("descricao"));
				bonus.setTipo(rs.getString("tipo"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static void setDescontoBonus(Orcamento orcamento){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select * from bonus where idescola = ? and ano = ?";

		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, orcamento.getEscola().getId());
			pstm.setString(2, orcamento.getAno());
			ResultSet rs = pstm.executeQuery();
			if(rs.next()){
				String tipo = rs.getString("tipo");
				float taxa = rs.getFloat("desconto");
				taxa /= 100;
				if(tipo.equalsIgnoreCase("desconto")){
					orcamento.setDesconto(taxa);
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static List<Float> pesquisarBonus(int idescola, String year){
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		String sql = "select desconto from bonus where idescola = ? and ano = ?";
		List<Float> lista = new ArrayList<>();
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, idescola);
			pstm.setString(2, year);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				lista.add(rs.getFloat("desconto"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}
}
