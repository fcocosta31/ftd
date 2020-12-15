package br.ftd.entity;

import java.util.ArrayList;
import java.util.List;

public class Professor implements Comparable<Object>{
	
	private int id;
	private String nome;
	private String cargo;
	private String email;
	private String telefone;
	private String celular;
	private String endereco;
	private String bairro;
	private String municipio;
	private String uf;
	private Escola escola;
	private List<String> niveis = new ArrayList<>();
	private List<String> disciplinas = new ArrayList<>();
	private int ativo;
	private String aniversario;
	
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

	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public List<String> getDisciplinas() {
		return disciplinas;
	}
	public void setDisciplinas(List<String> disciplinas) {
		this.disciplinas = disciplinas;
	}
	public Escola getEscola() {
		return escola;
	}
	public void setEscola(Escola escola) {
		this.escola = escola;
	}
	public List<String> getNiveis() {
		return niveis;
	}
	public void setNiveis(List<String> niveis) {
		this.niveis = niveis;
	}
	
	public String toString(){
		return "Id: "+this.id+" - Nome: "+this.nome 
				+ " - Cargo: "+this.cargo
				+ " - Email: "+this.email
				+ " - Telefone: "+this.telefone;
	}
	public int getAtivo() {
		return ativo;
	}
	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}
	public String getAniversario() {
		return aniversario;
	}
	public void setAniversario(String aniversario) {
		this.aniversario = aniversario;
	}
	
	@Override
	public int compareTo(Object p) {
		// TODO Auto-generated method stub
		Professor a = (Professor)p;
		
		String c1 = a.getNome();
		String c2 = this.getNome();
		return c2.compareTo(c1);
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
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	
}
