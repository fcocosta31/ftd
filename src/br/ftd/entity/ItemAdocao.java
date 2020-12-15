package br.ftd.entity;

public class ItemAdocao implements Comparable<Object>{
	
	private int idadocao;
	private String codigo;
	private float preco;
	private String serie;
	public int getIdadocao() {
		return idadocao;
	}
	public void setIdadocao(int idadocao) {
		this.idadocao = idadocao;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public float getPreco() {
		return preco;
	}
	public void setPreco(float preco) {
		this.preco = preco;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	@Override
	public int compareTo(Object p) {
		// TODO Auto-generated method stub
		ItemAdocao a = (ItemAdocao)p;
		String c1 = a.getSerie();
		String c2 = this.getSerie();		
		return c2.compareTo(c1);
	}
	
	
}
