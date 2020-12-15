package br.ftd.entity;

import java.io.Serializable;
import java.sql.Date;

public class DoacaoRelat implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int iddoacao;
	Date emissao;
	String vendedor;
	String escola;
	String professor;
	int qtde;
	
	public int getIddoacao() {
		return iddoacao;
	}
	public void setIddoacao(int iddoacao) {
		this.iddoacao = iddoacao;
	}
	public Date getEmissao() {
		return emissao;
	}
	public void setEmissao(Date emissao) {
		this.emissao = emissao;
	}
	public String getVendedor() {
		return vendedor;
	}
	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}
	public String getEscola() {
		return escola;
	}
	public void setEscola(String escola) {
		this.escola = escola;
	}
	public String getProfessor() {
		return professor;
	}
	public void setProfessor(String professor) {
		this.professor = professor;
	}
	public int getQtde() {
		return qtde;
	}
	public void setQtde(int qtde) {
		this.qtde = qtde;
	}	
}
