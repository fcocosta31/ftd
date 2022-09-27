package br.ftd.entity;

import java.sql.Date;

public class Pagamento {
	private int id;
	private int numero;
	private Date datapgto;
	private String tipo;
	private float valor;
	private String observacao;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public Date getDatapgto() {
		return datapgto;
	}
	public void setDatapgto(Date datapgto) {
		this.datapgto = datapgto;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}	
}
