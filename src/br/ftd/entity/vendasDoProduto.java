package br.ftd.entity;

import java.util.List;

public class vendasDoProduto {
	
	private Produto produto;
	private String ano;
	private List<ItemVendasDoProduto> vendas;
	private int qtdtotal;
	private int totalitens;
	
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public List<ItemVendasDoProduto> getVendas() {
		return vendas;
	}
	public void setVendas(List<ItemVendasDoProduto> vendas) {
		this.vendas = vendas;
	}
	public void refazTotal(){
		setQtdtotal(0);
		setTotalitens(0);
		for (ItemVendasDoProduto i : vendas){
			setTotalitens(getTotalitens() + 1);
			setQtdtotal(getQtdtotal() + i.getQuantidade());
		}
	}
	public int getQtdtotal() {
		return qtdtotal;
	}
	public void setQtdtotal(int qtdtotal) {
		this.qtdtotal = qtdtotal;
	}
	public int getTotalitens() {
		return totalitens;
	}
	public void setTotalitens(int totalitens) {
		this.totalitens = totalitens;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
}
