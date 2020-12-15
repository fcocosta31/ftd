package br.ftd.entity;

public class Usuario {
	
	private int id;
	private String nome;
	private String sobrenome;
	private String email;
	private int setor;
	private int cargo;
	private int idempresa;
	private String codigoftdempresa;
	private String nomeempresa;
	private String cnpj;
	private String desconto;
	private String login;
	private String senha;
	private int ativo;

	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public int getCargo() {
		return cargo;
	}
	public void setCargo(int cargo) {
		this.cargo = cargo;
	}
	public int getSetor() {
		return setor;
	}
	public void setSetor(int setor) {
		this.setor = setor;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getIdempresa() {
		return idempresa;
	}
	public void setIdempresa(int idempresa) {
		this.idempresa = idempresa;
	}
	public String getNomeempresa() {
		return nomeempresa;
	}
	public void setNomeempresa(String nomeempresa) {
		this.nomeempresa = nomeempresa;
	}
	
	public String toString(){
		return "Nome: "+this.nome+"\nCargo: "+this.cargo+"\nSetor: "+this.setor+"\nEmail: "+this.email;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public int getAtivo() {
		return ativo;
	}
	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}
	public String getDesconto() {
		return desconto;
	}
	public void setDesconto(String desconto) {
		this.desconto = desconto;
	}
	public boolean isAdmin() {
		return this.cargo == 1;
	}
	public boolean isMaster() {
		return (this.cargo == 1 && this.id == 1);
	}
	public boolean isUser() {
		return (this.cargo != 4 && this.cargo != 3);
	}
	public boolean isConsultor() {
		return this.cargo == 3;
	}
	public String getCodigoftdempresa() {
		return codigoftdempresa;
	}
	public void setCodigoftdempresa(String codigoftdempresa) {
		this.codigoftdempresa = codigoftdempresa;
	}
}
