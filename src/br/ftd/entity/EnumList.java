package br.ftd.entity;

import java.util.ArrayList;
import java.util.List;


public class EnumList {

	private List<String> familia = new ArrayList<>();
	private List<String> nivel = new ArrayList<>();
	private List<String> serie = new ArrayList<>();
	private List<String> editora = new ArrayList<>();
	private List<String> disciplina = new ArrayList<>();
	public List<String> getFamilia() {
		return familia;
	}
	public void setFamilia(List<String> familia) {
		this.familia = familia;
	}
	public List<String> getNivel() {
		return nivel;
	}
	public void setNivel(List<String> nivel) {
		this.nivel = nivel;
	}
	public List<String> getSerie() {
		return serie;
	}
	public void setSerie(List<String> serie) {
		this.serie = serie;
	}
	public List<String> getEditora() {
		return editora;
	}
	public void setEditora(List<String> editora) {
		this.editora = editora;
	}
	public List<String> getDisciplina() {
		return disciplina;
	}
	public void setDisciplina(List<String> disciplina) {
		this.disciplina = disciplina;
	}
	
	
}
