package br.ftd.entity;

import java.sql.Date;
import java.util.List;

public class Fatura {

	private int numero;
	private Date emissao;
	private Date vencimento;
	private Cliente cliente;
	private List<NotaFiscal> notas;
	private List<Pagamento> pagamentos;
	private float totalNotas = 0;
	private float totalPagamentos = 0;
	private boolean liquidada;
	private int ativo;
	
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public Date getEmissao() {
		return emissao;
	}
	public void setEmissao(Date emissao) {
		this.emissao = emissao;
	}
	public Date getVencimento() {
		return vencimento;
	}
	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public List<NotaFiscal> getNotas() {
		return notas;
	}
	public void setNotas(List<NotaFiscal> notas) {
		this.notas = notas;
	}
	public void addPagamento(Pagamento pagamento){
		this.pagamentos.add(pagamento);		
	}
	public void refazTotalPagamentos(){
		for(Pagamento p : pagamentos){
			this.totalPagamentos += p.getValor();
		}
	}
	public List<Pagamento> getPagamentos() {
		return pagamentos;
	}
	public void setPagamentos(List<Pagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}
	public float getTotalNotas() {
		return totalNotas;
	}
	public void setTotalNotas(float totalNotas) {
		this.totalNotas = totalNotas;
	}
	public void refazTotalNotas(){
		for(NotaFiscal f : notas){
			this.totalNotas += f.getTotal();
		}
	}
	public float getTotalPagamentos() {
		return totalPagamentos;
	}
	public void setTotalPagamentos(float totalPagamentos) {
		this.totalPagamentos = totalPagamentos;
	}
	public boolean isLiquidada() {
		return liquidada;
	}
	public void setLiquidada(boolean liquidada) {
		this.liquidada = liquidada;
	}
	public int getAtivo() {
		return ativo;
	}
	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}	
}
