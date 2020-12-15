package br.ftd.entity;

import java.sql.Date;
import java.util.ArrayList;


public class Pedido {
	
	private int idpedido;
	private Date data;
	private int qtitens;
	private int qttotal;
	private ArrayList<ItemPedido> itens;
	private ArrayList<NotaFiscal> notas;
	
	public Pedido(int idpedido, Date data, ArrayList<ItemPedido> itens) {
		super();
		this.idpedido = idpedido;
		this.data = data;
		this.itens = itens;
	}
	
	public Pedido(){}
	
	public int getIdpedido() {
		return idpedido;
	}
	public void setIdpedido(int idpedido) {
		this.idpedido = idpedido;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public ArrayList<ItemPedido> getItens() {
		return itens;
	}
	public void setItens(ArrayList<ItemPedido> itens) {
		this.itens = itens;
	}

	public ArrayList<NotaFiscal> getNotas() {
		return notas;
	}

	public void setNotas(ArrayList<NotaFiscal> notas) {
		this.notas = notas;
	}

	public int getQtitens() {
		return qtitens;
	}

	public void setQtitens(int qtitens) {
		this.qtitens = qtitens;
	}

	public int getQttotal() {
		return qttotal;
	}

	public void setQttotal(int qttotal) {
		this.qttotal = qttotal;
	}	
}
