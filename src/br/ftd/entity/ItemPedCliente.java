package br.ftd.entity;

import java.io.Serializable;
import java.sql.Date;

public class ItemPedCliente implements Comparable<Object>, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idpedido;
	private Produto item;
	private int qtdpedida;
	private int qtdatendida;
	private int qtdpendente;
	private int cancelado; // 0 -> n�o; 1 -> sim
	private boolean flag = false;
	private boolean update = false;
	private Date emissao;
	private String codigoftd;
	private String nomeftd;
	private String sdate;
	
	public ItemPedCliente(){
		idpedido = 0;
		qtdpedida = 0;
		qtdatendida = 0;
		qtdpendente = 0;
		cancelado = 0;
	}
	
	public int getIdpedido() {
		return idpedido;
	}
	public void setIdpedido(int idpedido) {
		this.idpedido = idpedido;
	}
	public Produto getItem() {
		return item;
	}
	public void setItem(Produto item) {
		this.item = item;
	}
	public int getQtdpedida() {
		return qtdpedida;
	}
	public void setQtdpedida(int qtdpedida) {
		this.qtdpedida = qtdpedida;
	}
	public int getQtdatendida() {
		return qtdatendida;
	}
	public void setQtdatendida(int qtdatendida) {
		this.qtdatendida = qtdatendida;
	}
	public int getCancelado() {
		return cancelado;
	}
	public void setCancelado(int cancelado) {
		this.cancelado = cancelado;
	}

	public boolean changeFamilia(ItemPedCliente b){
		
		int i = 0, j = 0;		
		
		
		if(this.item.getFamilia().equalsIgnoreCase("02-Paradidatico")){
			this.item.setFamilia("03-Literatura");
			this.item.setNivel("05-Geral");
			i = 1;
		}else if(this.item.getFamilia().equalsIgnoreCase("03-Literatura")){
			this.item.setNivel("05-Geral");
			i = 1;
		}
		
		if(b.item.getFamilia().equalsIgnoreCase("02-Paradidatico")){
			b.item.setFamilia("03-Literatura");
			b.item.setNivel("05-Geral");
			j = 1;
		}else if(b.item.getFamilia().equalsIgnoreCase("03-Literatura")){
			b.item.setNivel("05-Geral");
			j = 1;
		}
		
		return i == 1 || j == 1;
	}
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		ItemPedCliente a = (ItemPedCliente)o;
		
		boolean flag = changeFamilia(a);

		int i = Integer.compare(this.idpedido, a.idpedido);
		if(i != 0) return i;
		
		i = this.item.getFamilia().compareTo(a.item.getFamilia());
		if (i != 0) return i;
		
		i = this.item.getNivel().compareTo(a.item.getNivel());
		if (i != 0) return i;
				
		String c1, c2;
		
		if(flag){
			c1 = a.getItem().getDescricao();
			c2 = this.getItem().getDescricao();
		}else{
			c1 = a.getItem().getCodigo();
			c2 = this.getItem().getCodigo();			
		}
		
		return c2.compareTo(c1);
	}
	
	@Override
	public boolean equals(Object o){
		if(this == o) return true;
		if(!(o instanceof ItemPedCliente)) return false;
		ItemPedCliente that = (ItemPedCliente) o;
		return this.item.getCodigo().equals(that.item.getCodigo());
	}

	public int getQtdpendente() {
		return qtdpendente;
	}

	public void setQtdpendente(int qtdpendente) {
		this.qtdpendente = qtdpendente;
	}
	
	public void refazPendente(){
		qtdpendente = qtdpedida - qtdatendida;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Date getEmissao() {
		return emissao;
	}

	public void setEmissao(Date emissao) {
		this.emissao = emissao;
	}

	public String getCodigoftd() {
		return codigoftd;
	}

	public void setCodigoftd(String codigoftd) {
		this.codigoftd = codigoftd;
	}

	public String getNomeftd() {
		return nomeftd;
	}

	public void setNomeftd(String nomeftd) {
		this.nomeftd = nomeftd;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}
	
	public String toString(){		
		
		return "IdPed: "+idpedido+"-Cod.FTD: "+codigoftd+"-NomeFTD: "+nomeftd+"\n"
				+"Emissao: "+emissao+"-CodRef: "+item.getCodigo()+"-"+item.getDescricao()+"\n"
				+"Qtd.Pedida: "+qtdpedida+" - Qtd.Atendida: "+qtdatendida+" - Qtd.Pendente: "+qtdpendente+"\n"
				+flag;
		
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
}
