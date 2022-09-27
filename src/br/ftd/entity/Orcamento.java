package br.ftd.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
public class Orcamento {
	
	private Cliente cliente;
	private Escola escola;
	private Professor professor;
	private String serie;
	private String ano;
	private int idadocao;
	private int iddoacao;
	private Usuario vendedor;
	private List<ItemOrcamento> itens = new ArrayList<>();
	private int qtdtotal = 0;
	private double desconto = 0.0;
	private double valordesconto = 0.0;
	private double total = 0.0;
	private double totaliquido = 0.0;
	private int totalitens = 0;
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}	
	public Escola getEscola() {
		return escola;
	}
	public void setEscola(Escola escola) {
		this.escola = escola;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public List<ItemOrcamento> getItens() {
		return itens;
	}
	public void setItens(List<ItemOrcamento> itens) {
		this.itens = itens;
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
	
	public int getQtdtotal() {
		return qtdtotal;
	}
	public void setQtdtotal(int qtdtotal) {
		this.qtdtotal = qtdtotal;
	}
	public void adiciona(ItemOrcamento item){
		itens.add(item);
		total += item.getProduto().getPreco() * item.getQuantidade();
		BigDecimal bdm = new BigDecimal(item.getProduto().getPreco()*(1 - desconto)).setScale(2, RoundingMode.HALF_UP);
		item.setPrecoliquido(bdm.doubleValue());
		qtdtotal += item.getQuantidade();
		totalitens += 1;
		totaliquido = total * (1 - desconto);
		BigDecimal bd = new BigDecimal(totaliquido).setScale(2, RoundingMode.HALF_UP);
		totaliquido = bd.doubleValue();
		valordesconto = total - totaliquido;
	}
	public void refazTotal(){
		total = 0.0;
		qtdtotal = 0;
		totalitens = 0;
		for(ItemOrcamento i : itens){
			total += i.getProduto().getPreco() * i.getQuantidade();
			qtdtotal += i.getQuantidade();
			totalitens += 1;
			BigDecimal bdl = new BigDecimal(i.getProduto().getPreco() * (1 - desconto)).setScale(2, RoundingMode.HALF_UP);
			i.setPrecoliquido(bdl.doubleValue());
		}
		BigDecimal bd1 = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP);
		total = bd1.doubleValue();
		totaliquido = total * (1 - desconto);
		BigDecimal bd = new BigDecimal(totaliquido).setScale(2, RoundingMode.HALF_UP);
		totaliquido = bd.doubleValue();
		valordesconto = total - totaliquido;		
	}
	public void remove(ItemOrcamento item){
		
		int i = 0;
		
		for(ItemOrcamento it : itens){
			if(it.getProduto().getCodigo().equals(item.getProduto().getCodigo())){
				item = itens.remove(i);
				break;
			}
			i++;
		}
		
		total -= item.getProduto().getPreco() * item.getQuantidade();
		qtdtotal -= item.getQuantidade();
		totalitens -= 1;
		totaliquido = total * (1 - desconto);
		valordesconto = total - totaliquido;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
	public int getIdadocao() {
		return idadocao;
	}
	public void setIdadocao(int idadocao) {
		this.idadocao = idadocao;
	}
	public int getIddoacao() {
		return iddoacao;
	}
	public void setIddoacao(int iddoacao) {
		this.iddoacao = iddoacao;
	}
	public Professor getProfessor() {
		return professor;
	}
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	public double getTotaliquido() {
		return totaliquido;
	}
	public void setTotaliquido(double totaliquido) {
		this.totaliquido = totaliquido;
	}
	public double getValordesconto() {
		return valordesconto;
	}
	public void setValordesconto(double valordesconto) {
		this.valordesconto = valordesconto;
	}
	public int getTotalitens() {
		return totalitens;
	}
	public void setTotalitens(int totalitens) {
		this.totalitens = totalitens;
	}
	public Usuario getVendedor() {
		return vendedor;
	}
	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}
}
