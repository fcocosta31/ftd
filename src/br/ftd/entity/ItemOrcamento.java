package br.ftd.entity;

public class ItemOrcamento implements Comparable<Object>{
	private Produto produto;
	private String tes;
	private double precoliquido;
	private int quantidade;
	
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		ItemOrcamento a = (ItemOrcamento)o;
			
		String c1, c2;
		
		c1 = a.getProduto().getCodigo();
		c2 = this.getProduto().getCodigo();			

		return c2.compareTo(c1);
	}
	public double getPrecoliquido() {
		return precoliquido;
	}
	public void setPrecoliquido(double precoliquido) {
		this.precoliquido = precoliquido;
	}
	public String getTes() {
		return tes;
	}
	public void setTes(String tes) {
		this.tes = tes;
	}
}
