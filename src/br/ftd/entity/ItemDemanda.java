package br.ftd.entity;

public class ItemDemanda {
	
	private Produto item;	
	private int qtdepedida = 0;
	private int qtdenota = 0;
	private int venda1 = 0;
	private int venda2 = 0;
	private int venda3 = 0;

	public Produto getItem() {
		return item;
	}
	public void setItem(Produto item) {
		this.item = item;
	}
	public int getQtdepedida() {
		return qtdepedida;
	}
	public void setQtdepedida(int qtdepedida) {
		this.qtdepedida = qtdepedida;
	}
	public int getQtdenota() {
		return qtdenota;
	}
	public void setQtdenota(int qtdenota) {
		this.qtdenota = qtdenota;
	}
	public int getVenda1() {
		return venda1;
	}
	public void setVenda1(int venda1) {
		this.venda1 = venda1;
	}
	public int getVenda2() {
		return venda2;
	}
	public void setVenda2(int venda2) {
		this.venda2 = venda2;
	}
	public int getVenda3() {
		return venda3;
	}
	public void setVenda3(int venda3) {
		this.venda3 = venda3;
	}	
}
