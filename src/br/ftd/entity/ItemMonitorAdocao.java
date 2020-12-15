package br.ftd.entity;

import java.util.HashMap;
import java.util.Map;

public class ItemMonitorAdocao implements Comparable<Object>{
	private String grupo;
	private String colecao;
	private int adocoes;
	private int inf02anos = 0;
	private int inf03anos = 0;
	private int inf04anos = 0;
	private int inf05anos = 0;
	private int fund1ano = 0;
	private int fund2ano = 0;
	private int fund3ano = 0;
	private int fund4ano = 0;
	private int fund5ano = 0;
	private int fund6ano = 0;
	private int fund7ano = 0;
	private int fund8ano = 0;
	private int fund9ano = 0;
	private int em1serie = 0;
	private int em2serie = 0;
	private int em3serie = 0;
	private int totalinf = 0;
	private int totalfund1 = 0;
	private int totalfund2 = 0;
	private int totalmedio = 0;
	private Integer ordem;
	private Map<String, Integer> map;
	
	public ItemMonitorAdocao(){
		setMap(new HashMap<String, Integer>());
		map.put("Infantil", new Integer(1));
		map.put("Religião", new Integer(2));
		map.put("Idiomas", new Integer(3));
		map.put("306", new Integer(4));
		map.put("EM", new Integer(5));
		map.put("OPEE", new Integer(6));
		map.put("Cultivando Leitores", new Integer(7));
		map.put("Porta Aberta", new Integer(8));
		map.put("Mundo Melhor", new Integer(9));
		map.put("Coleções EF1", new Integer(10));
		map.put("Coleções EF2", new Integer(11));
		map.put("Athos", new Integer(12));
		map.put("Faça", new Integer(13));
	}
	
	public void refazTotais(){
		setTotalinf(inf02anos + inf03anos + inf04anos + inf05anos);
		setTotalfund1(fund1ano + fund2ano + fund3ano + fund4ano + fund5ano);
		setTotalfund2(fund6ano + fund7ano + fund8ano + fund9ano);
		setTotalmedio(em1serie + em2serie + em3serie);
	}
	public void setQtdeSerie(String serie, int qtde){
		switch(serie){
			case "Ed.Inf-02 anos":inf02anos = qtde;break;
			case "Ed.Inf-03 anos":inf03anos = qtde;break;
			case "Ed.Inf-04 anos":inf04anos = qtde;break;
			case "Ed.Inf-05 anos":inf05anos = qtde;break;
			case "1 Ano":fund1ano = qtde;break;
			case "2 Ano":fund2ano = qtde;break;
			case "3 Ano":fund3ano = qtde;break;
			case "4 Ano":fund4ano = qtde;break;
			case "5 Ano":fund5ano = qtde;break;
			case "6 Ano":fund6ano = qtde;break;
			case "7 Ano":fund7ano = qtde;break;
			case "8 Ano":fund8ano = qtde;break;
			case "9 Ano":fund9ano = qtde;break;
			case "1 Serie":em1serie = qtde;break;
			case "2 Serie":em2serie = qtde;break;
			case "3 Serie":em3serie = qtde;break;
			default:break;
		}
	}

	public boolean isCultivandoLeitores(){
		if(this.grupo.equalsIgnoreCase("Cultivando Leitores  Ed. Infantil")){
			return inf03anos == 0 || inf04anos == 0 || inf05anos == 0;
		}else if(this.grupo.equalsIgnoreCase("Cultivando Leitores Fund.I")){
			return fund1ano == 0 || fund2ano == 0 || fund3ano == 0 || fund4ano == 0 || fund5ano == 0;
		}else if(this.grupo.equalsIgnoreCase("Cultivando Leitores Fund.II")){
			return fund6ano == 0 || fund7ano == 0 || fund8ano == 0 || fund9ano == 0;
		}
		return false;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getColecao() {
		return colecao;
	}

	public void setColecao(String colecao) {
		this.colecao = colecao;
	}

	public int getAdocoes() {
		return adocoes;
	}

	public void setAdocoes(int adocoes) {
		this.adocoes = adocoes;
	}

	public int getInf02anos() {
		return inf02anos;
	}

	public void setInf02anos(int inf02anos) {
		this.inf02anos = inf02anos;
	}

	public int getInf03anos() {
		return inf03anos;
	}

	public void setInf03anos(int inf03anos) {
		this.inf03anos = inf03anos;
	}

	public int getInf04anos() {
		return inf04anos;
	}

	public void setInf04anos(int inf04anos) {
		this.inf04anos = inf04anos;
	}

	public int getInf05anos() {
		return inf05anos;
	}

	public void setInf05anos(int inf05anos) {
		this.inf05anos = inf05anos;
	}

	public int getFund1ano() {
		return fund1ano;
	}

	public void setFund1ano(int fund1ano) {
		this.fund1ano = fund1ano;
	}

	public int getFund2ano() {
		return fund2ano;
	}

	public void setFund2ano(int fund2ano) {
		this.fund2ano = fund2ano;
	}

	public int getFund3ano() {
		return fund3ano;
	}

	public void setFund3ano(int fund3ano) {
		this.fund3ano = fund3ano;
	}

	public int getFund4ano() {
		return fund4ano;
	}

	public void setFund4ano(int fund4ano) {
		this.fund4ano = fund4ano;
	}

	public int getFund5ano() {
		return fund5ano;
	}

	public void setFund5ano(int fund5ano) {
		this.fund5ano = fund5ano;
	}

	public int getFund6ano() {
		return fund6ano;
	}

	public void setFund6ano(int fund6ano) {
		this.fund6ano = fund6ano;
	}

	public int getFund7ano() {
		return fund7ano;
	}

	public void setFund7ano(int fund7ano) {
		this.fund7ano = fund7ano;
	}

	public int getFund8ano() {
		return fund8ano;
	}

	public void setFund8ano(int fund8ano) {
		this.fund8ano = fund8ano;
	}

	public int getFund9ano() {
		return fund9ano;
	}

	public void setFund9ano(int fund9ano) {
		this.fund9ano = fund9ano;
	}

	public int getEm1serie() {
		return em1serie;
	}

	public void setEm1serie(int em1serie) {
		this.em1serie = em1serie;
	}

	public int getEm2serie() {
		return em2serie;
	}

	public void setEm2serie(int em2serie) {
		this.em2serie = em2serie;
	}

	public int getEm3serie() {
		return em3serie;
	}

	public void setEm3serie(int em3serie) {
		this.em3serie = em3serie;
	}
	public int getTotalinf() {
		return totalinf;
	}
	public void setTotalinf(int totalinf) {
		this.totalinf = totalinf;
	}
	public int getTotalfund1() {
		return totalfund1;
	}
	public void setTotalfund1(int totalfund1) {
		this.totalfund1 = totalfund1;
	}
	public int getTotalfund2() {
		return totalfund2;
	}
	public void setTotalfund2(int totalfund2) {
		this.totalfund2 = totalfund2;
	}
	public int getTotalmedio() {
		return totalmedio;
	}
	public void setTotalmedio(int totalmedio) {
		this.totalmedio = totalmedio;
	}

	public Map<String, Integer> getMap() {
		return map;
	}

	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		ItemMonitorAdocao a = (ItemMonitorAdocao)o;
		int s = 0;
		int p = 0;
		if(map.get(a.getGrupo()) != null){
			s = map.get(a.getGrupo());
		}
		if(map.get(this.getGrupo()) != null){
			p = map.get(this.getGrupo());
		}
		return p > s ? +1 : p < s ? -1 : 0;
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}	
}
