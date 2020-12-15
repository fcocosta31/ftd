package br.ftd.entity;

import java.sql.Date;

public class RoteiroResumo {
	private Usuario usuario;
	private Date inicio;
	private Date fim;
	private Date inireal;
	private Date fimreal;
	private int totalescolas = 0;
	private int totalvisitas = 0;
	private int totalgeralvisitas = 0;
	private int maisdeumavisita = 0;
	private int semvisitas = 0;
	private int nuncavisitadas = 0;
	private int qtdedias = 0;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getTotalescolas() {
		return totalescolas;
	}

	public void setTotalescolas(int totalescolas) {
		this.totalescolas = totalescolas;
	}

	public int getTotalvisitas() {
		return totalvisitas;
	}

	public void setTotalvisitas(int totalvisitas) {
		this.totalvisitas = totalvisitas;
	}

	public int getMaisdeumavisita() {
		return maisdeumavisita;
	}

	public void setMaisdeumavisita(int maisdeumavisita) {
		this.maisdeumavisita = maisdeumavisita;
	}

	public int getSemvisitas() {
		return semvisitas;
	}

	public void setSemvisitas(int semvisitas) {
		this.semvisitas = semvisitas;
	}

	public int getNuncavisitadas() {
		return nuncavisitadas;
	}

	public void setNuncavisitadas(int nuncavisitadas) {
		this.nuncavisitadas = nuncavisitadas;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public int getTotalgeralvisitas() {
		return totalgeralvisitas;
	}

	public void setTotalgeralvisitas(int totalgeralvisitas) {
		this.totalgeralvisitas = totalgeralvisitas;
	}

	public int getQtdedias() {
		return qtdedias;
	}

	public void setQtdedias(int qtdedias) {
		this.qtdedias = qtdedias;
	}

	public Date getInireal() {
		return inireal;
	}

	public void setInireal(Date inireal) {
		this.inireal = inireal;
	}

	public Date getFimreal() {
		return fimreal;
	}

	public void setFimreal(Date fimreal) {
		this.fimreal = fimreal;
	}
}
