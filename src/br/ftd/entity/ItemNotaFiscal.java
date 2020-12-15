package br.ftd.entity;

public class ItemNotaFiscal {
	private Produto item;
	private float quantidade;
	private float preco;
	private float total;
		
	public ItemNotaFiscal(Produto item, float quantidade, float preco, float total){
		this.item = item;
		this.quantidade = quantidade;
		this.preco = preco;
		this.total = total;
	}
	
	public ItemNotaFiscal(){}
	
	public Produto getItem() {
		return item;
	}
	public void setItem(Produto item) {
		this.item = item;
	}
	public String getCodigo() {
		return item.getCodigo();
	}
	public void setCodigo(String codigo) {
		this.item.setCodigo(codigo);
	}
	public float getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(float quantidade) {
		this.quantidade = quantidade;
	}
	public float getPreco() {
		return preco;
	}
	public void setPreco(float preco) {
		this.preco = preco;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	
	@Override
	public String toString(){
		return "Codigo: "+item.getCodigo()+"; Qtde: "+quantidade+"; Preco: "+preco+"; Total: "+total;
	}
}
