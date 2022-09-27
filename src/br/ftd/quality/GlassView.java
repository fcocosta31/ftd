package br.ftd.quality;

import java.util.ArrayList;
import java.util.List;

import br.ftd.entity.Usuario;

public class GlassView {
	
	private Usuario consultor;
	private String dependencia;
	private String year;
	private int escolas;
	private int alunos;
	private int listas;
	private float marketshare;
	private List<MediaFamilia> medias;
	
	public GlassView(){
		this.consultor = new Usuario();
		this.dependencia = "privada";
		this.escolas = 0;
		this.alunos = 0;
		this.listas = 0;
		this.marketshare = 0;
		medias = new ArrayList<>();
	}

	public Usuario getConsultor() {
		return consultor;
	}



	public void setConsultor(Usuario consultor) {
		this.consultor = consultor;
	}



	public String getYear() {
		return year;
	}



	public void setYear(String year) {
		this.year = year;
	}



	public int getEscolas() {
		return escolas;
	}



	public void setEscolas(int escolas) {
		this.escolas = escolas;
	}



	public int getAlunos() {
		return alunos;
	}



	public void setAlunos(int alunos) {
		this.alunos = alunos;
	}



	public int getListas() {
		return listas;
	}



	public void setListas(int listas) {
		this.listas = listas;
	}



	public float getMarketshare() {
		return marketshare;
	}



	public void setMarketshare(float marketshare) {
		this.marketshare = marketshare;
	}



	public List<MediaFamilia> getMedias() {
		return medias;
	}



	public void setMedias(List<MediaFamilia> medias) {
		this.medias = medias;
	}

	public String getDependencia() {
		return dependencia;
	}

	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}
	
	public String toString(){
		return this.consultor.getNome()+"/n"
				+ this.dependencia+" / escolas="+this.escolas+" / listas="+this.listas+" / alunos="+this.alunos;
	}
}
