package br.ftd.entity;

import java.util.List;

public class EscolaAdocoes {
	private Escola escola;
	private List<Adocao> adotados;
	
	public Escola getEscola() {
		return escola;
	}
	public void setEscola(Escola escola) {
		this.escola = escola;
	}
	public List<Adocao> getAdotados() {
		return adotados;
	}
	public void setAdotados(List<Adocao> adotados) {
		this.adotados = adotados;
	}	
}
