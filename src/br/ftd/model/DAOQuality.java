package br.ftd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.ftd.factory.ConnectionFactory;
import br.ftd.quality.GlassView;
import br.ftd.quality.MediaFamilia;


public class DAOQuality {
	
	public static void recupera(GlassView glass){
		
		Connection con = ConnectionFactory.getInstance().getMySqlConnection();
		
		String sqlEscolas = "select count(*) as escolas from escola a inner join (select idescola, sum(quantidade)"
				+ " as alunos from escola_serie_aluno where ano = ? group by idescola having alunos > 0) as b on"
				+ " a.idescola = b.idescola and setor = ? and dependencia = ?";
		
		String sqlNumAlunos = "select sum(quantidade) as alunos from escola a inner join escola_serie_aluno b"
				+ " on a.idescola = b.idescola and a.setor = ? and a.dependencia = ? and b.ano = ? and b.serie <> 'Maternal 3'"
				+ " and b.serie <> 'Infantil 3' group by a.setor";
		
		String sqlListas = "select count(*) qtde_listas from (select a.idescola, a.nome, count(*) as qtde_series"
				+ " from escola a inner join adocao b on a.idescola = b.idescola and a.setor = ? and "
				+ "a.dependencia = ? and b.ano = ? group by b.idescola) as t";
		
		String sqlMediaFam = "select a.familia, avg(contar/escolas) as media from (select serie, familia,"
				+ " sum(marketshare) as contar from (select t.serie, b.codigo, a.marketshare, a.familia from"
				+ " produto a inner join item_adocao b inner join (select a.idescola, b.idadocao, b.serie from"
				+ " escola a inner join adocao b on a.idescola = b.idescola and a.setor = ? and a.dependencia = ?"
				+ " and b.ano = ?) as t on b.idadocao = t.idadocao and a.codigo = b.codigo) as w group by serie,"
				+ " familia) as a inner join (select serie, familia, count(*) as escolas from (select idescola,"
				+ " serie, familia, count(*) as contar from (select b.idescola, b.serie, c.familia from produto c"
				+ " inner join item_adocao a inner join (select a.idescola, a.nome, b.idadocao, b.serie from"
				+ " escola a inner join adocao b on a.idescola = b.idescola and a.setor = ? and a.dependencia = ?"
				+ " and b.ano = ?) as b on a.idadocao = b.idadocao and c.codigo = a.codigo) as w group by"
				+ " idescola, serie, familia) as r group by serie, familia) as b on a.serie = b.serie and"
				+ " a.familia = b.familia group by familia";
		
		String sqlMarketShare = "select (sum(mkt)/sum(fmkt))*100 as marketshare from (select serie, sum(marketshare)"
				+ " as mkt from (select t.serie, b.codigo, a.marketshare, a.familia from produto a inner join item_adocao"
				+ " b inner join (select a.idescola, b.idadocao, b.serie from escola a inner join adocao b on a.idescola"
				+ " = b.idescola and a.setor = ? and a.dependencia = ? and b.ano = ?) as t on b.idadocao = t.idadocao and"
				+ " a.codigo = b.codigo) as w where (familia like '01-%' or familia like '12-%') group by serie) as a inner"
				+ " join (select serie_nivel, b.serie, (sum(contar) * (case serie_nivel when 'EI' then 4 else 7 end)) as fmkt"
				+ " from serie a inner join (select a.serie, count(*) as contar from escola_serie_aluno a inner join escola"
				+ " b on a.idescola = b.idescola and b.dependencia = ? and b.setor = ? and quantidade > 0 group by serie)"
				+ " as b on a.serie = b.serie group by b.serie) as b on a.serie = b.serie";
		
		
		PreparedStatement pstm;
				 
		try {
			
			 if(glass.getConsultor().getSetor() == 0){
				 sqlEscolas = "select count(*) as escolas from escola a inner join (select idescola, sum(quantidade)"
							+ " as alunos from escola_serie_aluno where ano = ? group by idescola having alunos > 0) as b on"
							+ " a.idescola = b.idescola and dependencia = ?";
		
					sqlNumAlunos = "select sum(quantidade) as alunos from escola a inner join escola_serie_aluno b"
							+ " on a.idescola = b.idescola and a.dependencia = ? and b.ano = ? and b.serie <> 'Maternal 3'"
							+ " and b.serie <> 'Infantil 3'";
					
					sqlListas = "select count(*) qtde_listas from (select a.idescola, a.nome, count(*) as qtde_series"
							+ " from escola a inner join adocao b on a.idescola = b.idescola and "
							+ "a.dependencia = ? and b.ano = ? group by b.idescola) as t";
					
					sqlMediaFam = "select a.familia, avg(contar/escolas) as media from (select serie, familia,"
					+ " sum(marketshare) as contar from (select t.serie, b.codigo, a.marketshare, a.familia from"
					+ " produto a inner join item_adocao b inner join (select a.idescola, b.idadocao, b.serie from"
					+ " escola a inner join adocao b on a.idescola = b.idescola and a.dependencia = ?"
					+ " and b.ano = ?) as t on b.idadocao = t.idadocao and a.codigo = b.codigo) as w group by serie,"
					+ " familia) as a inner join (select serie, familia, count(*) as escolas from (select idescola,"
					+ " serie, familia, count(*) as contar from (select b.idescola, b.serie, c.familia from produto c"
					+ " inner join item_adocao a inner join (select a.idescola, a.nome, b.idadocao, b.serie from"
					+ " escola a inner join adocao b on a.idescola = b.idescola and a.dependencia = ?"
					+ " and b.ano = ?) as b on a.idadocao = b.idadocao and c.codigo = a.codigo) as w group by"
					+ " idescola, serie, familia) as r group by serie, familia) as b on a.serie = b.serie and"
					+ " a.familia = b.familia group by familia";			 

					
					sqlMarketShare = "select (sum(mkt)/sum(fmkt))*100 as marketshare from (select serie, sum(marketshare)"
							+ " as mkt from (select t.serie, b.codigo, a.marketshare, a.familia from produto a inner join item_adocao"
							+ " b inner join (select a.idescola, b.idadocao, b.serie from escola a inner join adocao b on a.idescola"
							+ " = b.idescola and a.dependencia = ? and b.ano = ?) as t on b.idadocao = t.idadocao and"
							+ " a.codigo = b.codigo) as w where (familia like '01-%' or familia like '12-%') group by serie) as a inner"
							+ " join (select serie_nivel, b.serie, (sum(contar) * (case serie_nivel when 'EI' then 4 else 7 end)) as fmkt"
							+ " from serie a inner join (select a.serie, count(*) as contar from escola_serie_aluno a inner join escola"
							+ " b on a.idescola = b.idescola and b.dependencia = ? and quantidade > 0 group by serie)"
							+ " as b on a.serie = b.serie group by b.serie) as b on a.serie = b.serie";
					
					
					pstm = con.prepareStatement(sqlEscolas);
					pstm.setString(1, glass.getYear());
					pstm.setString(2, glass.getDependencia());
					ResultSet rs = pstm.executeQuery();
					while(rs.next()){
						glass.setEscolas(rs.getInt("escolas"));
					}
					rs.close();
					pstm.close();
					
					pstm = con.prepareStatement(sqlNumAlunos);
					pstm.setString(1, glass.getDependencia());
					pstm.setString(2, glass.getYear());
					rs = pstm.executeQuery();
					while(rs.next()){
						glass.setAlunos(rs.getInt("alunos"));
					}
					rs.close();
					pstm.close();
		
					pstm = con.prepareStatement(sqlListas);
					pstm.setString(1, glass.getDependencia());
					pstm.setString(2, glass.getYear());
					rs = pstm.executeQuery();
					while(rs.next()){
						glass.setListas(rs.getInt("qtde_listas"));
					}
					rs.close();
					pstm.close();
		
					pstm = con.prepareStatement(sqlMediaFam);
					pstm.setString(1, glass.getDependencia());
					pstm.setString(2, glass.getYear());
					pstm.setString(3, glass.getDependencia());
					pstm.setString(4, glass.getYear());
					rs = pstm.executeQuery();
					while(rs.next()){
						glass.getMedias().add(new MediaFamilia(rs.getString("familia"), rs.getFloat("media")));
					}
					rs.close();
					pstm.close();

					pstm = con.prepareStatement(sqlMarketShare);
					pstm.setString(1, glass.getDependencia());
					pstm.setString(2, glass.getYear());
					pstm.setString(3, glass.getDependencia());
					rs = pstm.executeQuery();
					if(rs.next()){
						glass.setMarketshare(rs.getFloat("marketshare"));
					}
					rs.close();
					pstm.close();				
					
					con.close();
					
			 }else{

					pstm = con.prepareStatement(sqlEscolas);
					pstm.setString(1, glass.getYear());
					pstm.setInt(2, glass.getConsultor().getSetor());
					pstm.setString(3, glass.getDependencia());
					ResultSet rs = pstm.executeQuery();
					while(rs.next()){
						glass.setEscolas(rs.getInt("escolas"));
					}
					rs.close();
					pstm.close();
					
					pstm = con.prepareStatement(sqlNumAlunos);
					pstm.setInt(1, glass.getConsultor().getSetor());
					pstm.setString(2, glass.getDependencia());
					pstm.setString(3, glass.getYear());
					rs = pstm.executeQuery();
					while(rs.next()){
						glass.setAlunos(rs.getInt("alunos"));
					}
					rs.close();
					pstm.close();
		
					pstm = con.prepareStatement(sqlListas);
					pstm.setInt(1, glass.getConsultor().getSetor());
					pstm.setString(2, glass.getDependencia());
					pstm.setString(3, glass.getYear());
					rs = pstm.executeQuery();
					while(rs.next()){
						glass.setListas(rs.getInt("qtde_listas"));
					}
					rs.close();
					pstm.close();
		
					pstm = con.prepareStatement(sqlMediaFam);
					pstm.setInt(1, glass.getConsultor().getSetor());
					pstm.setString(2, glass.getDependencia());
					pstm.setString(3, glass.getYear());
					pstm.setInt(4, glass.getConsultor().getSetor());
					pstm.setString(5, glass.getDependencia());
					pstm.setString(6, glass.getYear());
					rs = pstm.executeQuery();
					while(rs.next()){
						glass.getMedias().add(new MediaFamilia(rs.getString("familia"), rs.getFloat("media")));
					}
					rs.close();
					pstm.close();

					pstm = con.prepareStatement(sqlMarketShare);
					pstm.setInt(1, glass.getConsultor().getSetor());
					pstm.setString(2, glass.getDependencia());
					pstm.setString(3, glass.getYear());
					pstm.setString(4, glass.getDependencia());
					pstm.setInt(5, glass.getConsultor().getSetor());
					rs = pstm.executeQuery();
					if(rs.next()){
						glass.setMarketshare(rs.getFloat("marketshare"));
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
