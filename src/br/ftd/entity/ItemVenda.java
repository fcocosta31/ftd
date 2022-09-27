package br.ftd.entity;

public class ItemVenda {
	
	private Produto item;
	private int quantidade;
	private double preco;
	private double totalitem;
	
	public Produto getItem() {
		return item;
	}
	public void setItem(Produto item) {
		this.item = item;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public double getTotalitem() {
		return totalitem;
	}
	public void setTotalitem(double totalitem) {
		this.totalitem = totalitem;
	}
}
