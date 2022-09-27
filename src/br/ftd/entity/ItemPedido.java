package br.ftd.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ItemPedido{
	
	private Produto item;
	private int idpedido;
	private int qtdpedida;
	private float qtdchegou;
	private Date previsao;
	private Date datachegada;
	private List<NotaFiscal> notas;
	private String notafiscal;
	private Date emissaonf;
	private float qtdpendente;
	private int cancelado;
	private String observacao;
	
	public ItemPedido(){
		observacao = "";
		qtdpedida = 0;
		qtdchegou = 0;
		notas = new ArrayList<>();
		qtdpendente = 0;
		cancelado = 0;
	}
	
	public Produto getItem() {
		return item;
	}
	public void setItem(Produto item) {
		this.item = item;
	}
	public List<NotaFiscal> getNotas() {
		return notas;
	}
	public void setNotas(List<NotaFiscal> notas) {
		this.notas = notas;
	}
	public int getQtdpedida() {
		return qtdpedida;
	}
	public void setQtdpedida(int quantidade) {
		this.qtdpedida = quantidade;
	}
	
	public float getQtdchegou() {
		return qtdchegou;
	}
	public void setQtdchegou(float qtdchegou) {
		this.qtdchegou = qtdchegou;
	}
	public Date getPrevisao() {
		return previsao;
	}
	public void setPrevisao(Date previsao) {
		this.previsao = previsao;
	}
	public void refazTotalQtdChegou(){
		for(NotaFiscal n : notas){
			qtdchegou += n.getQtdtotal();
		}
		float pendente = qtdpedida - qtdchegou;
		setQtdpendente(pendente);
	}
	public void refazPendente(){
		qtdpendente = qtdpedida - qtdchegou;
	}
	public float getQtdpendente() {
		return qtdpendente;
	}
	public void setQtdpendente(float qtdpendente) {
		this.qtdpendente = qtdpendente;
	}
	public int getIdpedido() {
		return idpedido;
	}
	public void setIdpedido(int idpedido) {
		this.idpedido = idpedido;
	}
	public int getCancelado() {
		return cancelado;
	}
	public void setCancelado(int cancelado) {
		this.cancelado = cancelado;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String toString(){
		return "["+item.getCodigo()+"]["+item.getDescricao()+"]\n"
				+ "[Ped:"+idpedido+"][Qt:"+qtdpedida+"][At:"+qtdchegou+"][Pd:"+qtdpendente+"]\n"
						+ "[Obs:"+observacao+"]";
		
	}

	public Date getDatachegada() {
		return datachegada;
	}

	public void setDatachegada(Date datachegada) {
		this.datachegada = datachegada;
	}

	public String getNotafiscal() {
		return notafiscal;
	}

	public void setNotafiscal(String notafiscal) {
		this.notafiscal = notafiscal;
	}

	public Date getEmissaonf() {
		return emissaonf;
	}

	public void setEmissaonf(Date emissaonf) {
		this.emissaonf = emissaonf;
	}
}
