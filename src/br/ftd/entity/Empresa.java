package br.ftd.entity;

public class Empresa {
	
	private int id;
	private String codigoftd;
	private String razaosocial;
	private String endereco;
	private String municipio;
	private String uf;
	private String telefone;
	private String email;
	private String cnpj;
	private String filialftd;
	private String nomereduz;
	private boolean isdefault;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCodigoftd() {
		return codigoftd;
	}
	public void setCodigoftd(String codigoftd) {
		this.codigoftd = codigoftd;
	}
	public String getRazaosocial() {
		return razaosocial;
	}
	public void setRazaosocial(String razaosocial) {
		this.razaosocial = razaosocial;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}		
	public String getFilialftd() {
		return filialftd;
	}
	public void setFilialftd(String filialftd) {
		this.filialftd = filialftd;
	}
	public String getNomereduz() {
		return nomereduz;
	}
	public void setNomereduz(String nomereduz) {
		this.nomereduz = nomereduz;
	}
	public boolean isIsdefault() {
		return isdefault;
	}
	public void setIsdefault(boolean isdefault) {
		this.isdefault = isdefault;
	}
	public String toString(){
		return "["+codigoftd+"] "+razaosocial+"\n"
				+ endereco+" / "+municipio+" / "+uf;
	}
}