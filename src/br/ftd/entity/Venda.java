package br.ftd.entity;

import java.sql.Date;
import java.util.List;

public class Venda {
	
	private String nota;
	private String serie;
	private Cliente cliente;
	private Date emissao;
	private int quantidade;
	private double bruto;
	private double desconto;
	private double total;
	private double devolucao;
	private List<ItemVenda> itens;
	
	public Venda(){
		quantidade = 0;
		bruto = 0;
		desconto = 0;
		total = 0;
		devolucao = 0;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Date getEmissao() {
		return emissao;
	}
	public void setEmissao(Date emissao) {
		this.emissao = emissao;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public double getDesconto() {
		return desconto;
	}
	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public List<ItemVenda> getItens() {
		return itens;
	}
	public void setItens(List<ItemVenda> itens) {
		this.itens = itens;
	}
	public double getBruto() {
		return bruto;
	}
	public void setBruto(double bruto) {
		this.bruto = bruto;
	}
	public double getDevolucao() {
		return devolucao;
	}
	public void setDevolucao(double devolucao) {
		this.devolucao = devolucao;
	}
}
