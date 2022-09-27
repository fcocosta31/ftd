package br.ftd.entity;

public class Cliente {

	private String codigocl;
	private String codigofn;
	private String nome;
	private String endereco;
	private String bairro;
	private String municipio;
	private String cep;
	private String estado;
	private String cgc;
	private String vendedor;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCgc() {
		return cgc;
	}
	public void setCgc(String cgc) {
		this.cgc = cgc;
	}
	public String getVendedor() {
		return vendedor;
	}
	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}
	public String getCodigocl() {
		return codigocl;
	}
	public void setCodigocl(String codigocl) {
		this.codigocl = codigocl;
	}
	public String getCodigofn() {
		return codigofn;
	}
	public void setCodigofn(String codigofn) {
		this.codigofn = codigofn;
	}	
}
