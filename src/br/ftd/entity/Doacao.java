package br.ftd.entity;

import java.sql.Date;
import java.util.List;

public class Doacao implements Comparable<Object>{
	private int id;
	private Date emissao;
	private Professor professor;
	private Escola escola;
	private Usuario usuario;
	private List<ItemOrcamento> itens;
	private int qtdtotal = 0;
	private double total = 0.0;
	private int reimpressao;
	private int qtditens;
	private int user_id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Professor getProfessor() {
		return professor;
	}
	public Date getEmissao() {
		return emissao;
	}
	public void setEmissao(Date emissao) {
		this.emissao = emissao;
	}
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public Escola getEscola() {
		return escola;
	}
	public void setEscola(Escola escola) {
		this.escola = escola;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<ItemOrcamento> getItens() {
		return itens;
	}
	public void setItens(List<ItemOrcamento> itens) {
		this.itens = itens;
	}
	public int getQtdtotal() {
		return qtdtotal;
	}
	public void setQtdtotal(int qtdtotal) {
		this.qtdtotal = qtdtotal;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public void adiciona(ItemOrcamento item){
		itens.add(item);
		total += item.getProduto().getPreco() * item.getQuantidade();
		qtdtotal += item.getQuantidade();
	}
	public void refazTotal(){
		qtditens = 0;
		total = 0;
		qtdtotal = 0;
		for(ItemOrcamento i : itens){
			total += i.getProduto().getPreco() * i.getQuantidade();
			qtdtotal += i.getQuantidade();
			qtditens += 1;
		}
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
	}
	public int getReimpressao() {
		return reimpressao;
	}
	public void setReimpressao(int reimpressao) {
		this.reimpressao = reimpressao;
	}
	public int getQtditens() {
		return qtditens;
	}
	public void setQtditens(int qtditens) {
		this.qtditens = qtditens;
	}
	
	@Override
	public int compareTo(Object p) {
		// TODO Auto-generated method stub
		Doacao a = (Doacao)p;
		int i = 0;
		
		if(this.getUsuario() != null && a.getUsuario() != null){
			i = this.getUsuario().getNome().compareTo(a.getUsuario().getNome());			
		}else{
			i = -1;
		}
		
		if (i != 0) return i;
		
		i = this.getEscola().getNome().compareTo(a.getEscola().getNome());
		if (i != 0) return i;
		
		String c1 = a.getProfessor().getNome();
		String c2 = this.getProfessor().getNome();
		
		return c2.compareTo(c1);
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}	
}
