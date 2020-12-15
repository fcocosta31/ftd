package br.ftd.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class NotaFiscal implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idnota;
	private Date emissao;
	private String emitente;
	private Date chegada;
	private String UF;
	private String serie;
	private String cnpj;
	private String cnpjemit;
	private int idpedido;
	private float qtdtotal;
	private float total;
	private float desconto;
	private float liquido;
	private float percentual;
	private String cfop;
	private String natop;
	private String municipio;
	
	private List<ItemNotaFiscal> itens;
	
	public String getIdnota() {
		return idnota;
	}

	public void setIdnota(String idnota) {
		this.idnota = idnota;
	}

	public Date getEmissao() {
		return emissao;
	}

	public void setEmissao(Date emissao) {
		this.emissao = emissao;
	}
	
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public Date getChegada() {
		return chegada;
	}

	public void setChegada(Date chegada) {
		this.chegada = chegada;
	}
	
	public String getUF() {
		return UF;
	}

	public void setUF(String uF) {
		UF = uF;
	}
	
	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public int getIdpedido() {
		return idpedido;
	}

	public void setIdpedido(int idpedido) {
		this.idpedido = idpedido;
	}
	
	public float getQtdtotal() {
		return qtdtotal;
	}

	public void setQtdtotal(float qtdtotal) {
		this.qtdtotal = qtdtotal;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public List<ItemNotaFiscal> getItens() {
		return itens;
	}

	public void setItens(List<ItemNotaFiscal> itens) {
		this.itens = itens;
	}
	
	@Override
	public String toString(){
		String cabecalho = "Nota No.: "+idnota+"\r\n"
				+ "Emissao: "+emissao+"\r\n"
				+ "Emitente: "+emitente+"\r\n"
				+ "Cfop: "+cfop+"\r\n"
				+ "Natureza: "+natop+"\r\n"
				+ "CNPJ: "+cnpj+"\r\n"
				+ "Pedido: "+idpedido+"\r\n"
				+ "Total: "+total
				+ "Desconto: "+desconto
				+ "Liquido: "+liquido;
		String itensnota = "\r\nItens da nota.................";
		for (ItemNotaFiscal itemNotaFiscal : itens) {
			itensnota+="\r\nCodigo"+itemNotaFiscal.getItem().getCodigo()
					+"\r\nDescricao: "+itemNotaFiscal.getItem().getDescricao()
					+"\r\nCodBarras: "+itemNotaFiscal.getItem().getCodbar()
					+"\r\nPreco: "+itemNotaFiscal.getPreco()
					+"\r\nQuantidade: "+itemNotaFiscal.getQuantidade()
					+"\r\nTotal: "+itemNotaFiscal.getTotal();
		}
		return cabecalho+itensnota;				
	}

	public String getCnpjemit() {
		return cnpjemit;
	}

	public void setCnpjemit(String cnpjemit) {
		this.cnpjemit = cnpjemit;
	}

	public String getEmitente() {
		return emitente;
	}

	public void setEmitente(String emitente) {
		this.emitente = emitente;
	}

	public float getDesconto() {
		return desconto;
	}

	public void setDesconto(float desconto) {
		this.desconto = desconto;
	}

	public float getLiquido() {
		return liquido;
	}

	public void setLiquido(float liquido) {
		this.liquido = liquido;
	}

	public float getPercentual() {
		return percentual;
	}

	public void setPercentual(float percentual) {
		this.percentual = percentual;
	}

	public String getCfop() {
		return cfop;
	}

	public void setCfop(String cfop) {
		this.cfop = cfop;
	}

	public String getNatop() {
		return natop;
	}

	public void setNatop(String natop) {
		this.natop = natop;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	
}
