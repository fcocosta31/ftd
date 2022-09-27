package br.ftd.entity;

import java.util.ArrayList;

public class ConsProduto {
	
	private Produto item;
	private ArrayList<Adocao> adocoes;
	private ArrayList<Pedido> pedidos;
	
	public ConsProduto(Produto item, ArrayList<Pedido> pedidos) {
		super();
		this.item = item;
		this.pedidos = pedidos;
	}
	
	public ConsProduto(){}
	
	public Produto getItem() {
		return item;
	}

	public void setItem(Produto item) {
		this.item = item;
	}

	public ArrayList<Pedido> getPedidos() {
		return pedidos;
	}
	public void setPedidos(ArrayList<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public ArrayList<Adocao> getAdocoes() {
		return adocoes;
	}

	public void setAdocoes(ArrayList<Adocao> adocoes) {
		this.adocoes = adocoes;
	}
	
}
