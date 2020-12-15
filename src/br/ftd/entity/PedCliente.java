package br.ftd.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PedCliente implements Comparable<Object>{
	
	private int idpedido;
	private Empresa cliente;
	private Usuario usuario;
	private Date emissao;
	private int guardarpendencia; // 0 -> sim; 1 -> n�o
	private String situacao; // Aguardando separação; Em conferência; Atendido totalmente; Atendido parcialmente; Cancelado
	private int cancelado; // 0 -> não; 1 -> sim
	private List<ItemPedCliente> itens;
	private int qtdtotal = 0;
	private int qtdatendida = 0;
	private double desconto = 0.0;
	private double valordesconto = 0.0;
	private double total = 0.0;
	private double liquido = 0.0;
	private int user_id;
	private boolean sendmail = false; 
	private Auditoria audit;
	
	public PedCliente(){
		idpedido = 0;
		emissao = new Date(System.currentTimeMillis());
		guardarpendencia = 1;
		itens = new ArrayList<>();
		situacao = "AGUARDANDO SEPARACAO";
	}

	public void adiciona(ItemPedCliente item){
		itens.add(item);
		total += item.getItem().getPreco() * item.getQtdpedida();
		liquido = total*(1 - desconto);
		qtdtotal += item.getQtdpedida();
		qtdatendida += item.getQtdatendida();
	}
	
	public void refazTotal(){
		total = 0;
		qtdtotal = 0;
		qtdatendida = 0;
		for(ItemPedCliente i : itens){
			total += i.getItem().getPreco() * i.getQtdpedida();
			qtdtotal += i.getQtdpedida();
			qtdatendida += i.getQtdatendida();
		}
		liquido = total*(1 - desconto);
		valordesconto = total - liquido;
		BigDecimal bd1 = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP);
		total = bd1.doubleValue();
		BigDecimal bd = new BigDecimal(liquido).setScale(2, RoundingMode.HALF_UP);
		liquido = bd.doubleValue();
		BigDecimal bd2 = new BigDecimal(valordesconto).setScale(2, RoundingMode.HALF_UP);
		valordesconto = bd2.doubleValue();
	}
	
	public void refazSituacao(String tiporecord){
		this.refazTotal();
		if(qtdatendida == 0){
			if(tiporecord.equalsIgnoreCase("0"))
				this.setSituacao("AGUARDANDO SEPARACAO");
		}else if(qtdtotal > qtdatendida){
			this.setSituacao("ATENDIDO PARCIALMENTE");
		}else if(qtdtotal == qtdatendida){
			this.setSituacao("ATENDIDO TOTALMENTE");
		}
	}
	
	public void remove(ItemPedCliente item){
		
		int i = 0;
		
		for(ItemPedCliente it : itens){
			if(it.getItem().getCodigo().equals(item.getItem().getCodigo())){
				item = itens.remove(i);
				break;
			}
			i++;
		}
		
		total -= item.getItem().getPreco() * item.getQtdpedida();
		liquido = total - (total*(1 - desconto));
		qtdtotal -= item.getQtdpedida();
		qtdatendida -= item.getQtdatendida();
	}
	
	public int getIdpedido() {
		return idpedido;
	}
	public void setIdpedido(int idpedido) {
		this.idpedido = idpedido;
	}
	public Empresa getCliente() {
		return cliente;
	}
	public void setCliente(Empresa cliente) {
		this.cliente = cliente;
	}
	public Date getEmissao() {
		return emissao;
	}
	public void setEmissao(Date emissao) {
		this.emissao = emissao;
	}
	public int getGuardarpendencia() {
		return guardarpendencia;
	}
	public void setGuardarpendencia(int guardarpendencia) {
		this.guardarpendencia = guardarpendencia;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<ItemPedCliente> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedCliente> itens) {
		this.itens = itens;
	}

	public int getCancelado() {
		return cancelado;
	}

	public void setCancelado(int cancelado) {
		this.cancelado = cancelado;
	}

	public int getQtdtotal() {
		return qtdtotal;
	}

	public void setQtdtotal(int qtdtotal) {
		this.qtdtotal = qtdtotal;
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

	public double getLiquido() {
		return liquido;
	}

	public void setLiquido(double liquido) {
		this.liquido = liquido;
	}

	public int getQtdatendida() {
		return qtdatendida;
	}

	public void setQtdatendida(int qtdatendida) {
		this.qtdatendida = qtdatendida;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		PedCliente a = (PedCliente)o;
		
		String c1, c2;
		
		c1 = a.getCliente().getCodigoftd();
		c2 = this.getCliente().getCodigoftd();			

		return c2.compareTo(c1);
	}

	public double getValordesconto() {
		return valordesconto;
	}

	public void setValordesconto(double valordesconto) {
		this.valordesconto = valordesconto;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Auditoria getAudit() {
		return audit;
	}

	public void setAudit(Auditoria audit) {
		this.audit = audit;
	}

	public boolean isSendmail() {
		return sendmail;
	}

	public void setSendmail(boolean sendmail) {
		this.sendmail = sendmail;
	}
	
	public void printItensPedCliente() {
		for(ItemPedCliente i : this.getItens()) {
			   System.out.println(":::::::::::::::::: Código: "+i.getItem().getCodigo()+" ::: QtPedida: "+i.getQtdpedida()
			   +" ::: QtAtendida: "+i.getQtdatendida());			
		}
	}
}
