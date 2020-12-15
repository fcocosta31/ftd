package br.ftd.entity;

import java.util.List;

public class Adocao implements Comparable<Object>{
		
	private int idadocao;
	private Escola escola;
	private int idescola;
	private String nomeescola;	
	private String municipio;
	private String serie;
	private String[] series;
	private String ano;
	private int qtde;
	private Usuario vendedor;
	private int user_id;
	private Auditoria audit;
	private List<ItemOrcamento> itens;
	private List<ItemAdocao> itensadocao;	
	
	
	public boolean isEmptyItems(){
		return (itensadocao == null && itens == null);
	}
	
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public int getIdadocao() {
		return idadocao;
	}
	public void setIdadocao(int id) {
		this.idadocao = id;
	}	
	public int getIdescola() {
		return idescola;
	}
	public void setIdescola(int idescola) {
		this.idescola = idescola;
	}	
	public String getNomeescola() {
		return nomeescola;
	}
	public void setNomeescola(String nomeescola) {
		this.nomeescola = nomeescola;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}	
	public int getQtde() {
		return qtde;
	}
	public void setQtde(int qtde) {
		this.qtde = qtde;
	}
	public List<ItemOrcamento> getItens() {
		return itens;
	}
	public void setItens(List<ItemOrcamento> itens) {
		this.itens = itens;
	}
	public Escola getEscola() {
		return escola;
	}
	public void setEscola(Escola escola) {
		this.escola = escola;
	}
	public List<ItemAdocao> getItensadocao() {
		return itensadocao;
	}
	public void setItensadocao(List<ItemAdocao> itensadocao) {
		this.itensadocao = itensadocao;
	}
	@Override
	public int compareTo(Object p) {
		// TODO Auto-generated method stub
		Adocao a = (Adocao)p;
		String c1 = a.getEscola().getNome();
		String c2 = this.getEscola().getNome();
		return c2.compareTo(c1);
	}
	public String[] getSeries() {
		return series;
	}
	public void setSeries(String[] series) {
		this.series = series;
	}
	public Usuario getVendedor() {
		return vendedor;
	}
	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Auditoria getAudit() {
		return audit;
	}

	public void setAudit(Auditoria audit) {
		this.audit = audit;
	}
	
}
