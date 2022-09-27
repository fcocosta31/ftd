package br.ftd.entity;

public class Demanda implements Comparable<Object>{
	private ItemDemanda itemdemanda;
	private int qtddemanda = 0;
	private int pedido = 0;
	private int vendaatual = 0;
	private int pendente = 0;
	private int qtdchegou = 0;
	private int atendido = 0;
	private int necessidade = 0;
	private int qtdsimulada = 0;
	private int necsimulada = 0;
	private int qtdprevsp = 0;
	private int escolas = 0;
	private int pedsp = 0;
	private String ano;
	
	public void refazTotais(){
		
		int dm, pd, chg, pnd, nec, estq, alm09, vnd1, vnd2, vnd3, vdatu;
		
		dm = this.qtddemanda;
		pd = this.pedido;
		chg = this.qtdchegou;
		vdatu = this.vendaatual;
		estq = this.itemdemanda.getItem().getEstoque();
		alm09 = this.itemdemanda.getItem().getAlm09();
		vnd1 = this.itemdemanda.getVenda1();
		vnd2 = this.itemdemanda.getVenda2();
		vnd3 = this.itemdemanda.getVenda3();
		
		//zerando estoque negativo
		if(estq < 0)
			estq = 0;
		
		if(alm09 < 0)
			alm09 = 0;
		
		//setando o estoque novamente
		this.itemdemanda.getItem().setEstoque(estq);
		
		//aplicando o metodo de Brown
		this.qtdsimulada = (int) metodoBrown(vnd1, vnd2, vnd3);
		
		//aplicando a suavizacao exponencial simples
		//this.qtdsimulada = (int) metodoExponencialSimples(vnd1, vnd2, vnd3);
		
		pnd = pd - chg;		
		
		if(pnd < 0)
			pnd = 0;
		
		nec = dm - (pnd + estq + alm09 + vdatu);				
		
		if(nec < 0)
			nec = 0;		
		
		this.setQtddemanda(dm);
		this.setPedido(pd);
		this.setPendente(pnd);
		this.setNecessidade(nec);
		
		dm = this.qtdsimulada;
		
		nec = dm - (pnd + estq + alm09 + vdatu);
		
		if(nec < 0)
			nec = 0;		
		
		this.setNecsimulada(nec);
		
		setPedsp(arredondar(this.necessidade));
	}
	
	public int arredondar(int num){
		int dez = num/10;
		int dec = num%10;
		if(dec > 4){
			num = dez*10 + 10;
		}else{
			num = dez*10;
		}
		return num;
	}
	
	//previsao de vendas pelo m�todo exponencial duplo (m�todo de Brown)
	public double metodoBrown(double vd1, double vd2, double vd3){
		
		double alfa = 0.63;
		double ae11 = vd1;
		double ae21 = vd1;
		double ae12 = alfa*vd2+(1-alfa)*ae11;
		double ae22 = alfa*ae12+(1-alfa)*ae21;
		double ae13 = alfa*vd3+(1-alfa)*ae12;
		double ae23 = alfa*ae13+(1-alfa)*ae22;
		double at = 2*ae13-ae23;
		double bt = alfa/(1-alfa)*(ae13-ae23);
		double result = at+bt;
		
		return (result>=0)?result:0;
	}
	
	public double metodoExponencialSimples(double vd1, double vd2, double vd3) {
		
		double coef = 0.3;
		double v0 = vd1;
		double v1 = v0+coef*(vd1-v0);
		double v2 = v1+coef*(vd2-v1);
		double v3 = v2+coef*(vd3-v2);
		
		return v3;
	}
	
	
	public int getNecessidade() {
		return necessidade;
	}
	public void setNecessidade(int necessidade) {
		this.necessidade = necessidade;
	}
	public int getPedido() {
		return pedido;
	}
	public void setPedido(int pedido) {
		this.pedido = pedido;
	}
	public int getPendente() {
		return pendente;
	}
	public void setPendente(int pendente) {
		this.pendente = pendente;
	}	
	public ItemDemanda getItemdemanda() {
		return itemdemanda;
	}
	public void setItemdemanda(ItemDemanda itemdemanda) {
		this.itemdemanda = itemdemanda;
	}
	public int getQtddemanda() {
		return qtddemanda;
	}
	public void setQtddemanda(int demanda) {
		this.qtddemanda = demanda;
	}
	public int getEscolas() {
		return escolas;
	}
	public void setEscolas(int escolas) {
		this.escolas = escolas;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		Demanda a = (Demanda)o;
		
		boolean flag = changeFamilia(a);
		
		int i = this.getItemdemanda().getItem().getFamilia().compareTo(a.getItemdemanda().getItem().getFamilia());
		if (i != 0) return i;
		
		i = this.getItemdemanda().getItem().getNivel().compareTo(a.getItemdemanda().getItem().getNivel());
		if (i != 0) return i;
		
		String c1, c2;
		
		if(flag){
			c1 = a.getItemdemanda().getItem().getDescricao();
			c2 = this.getItemdemanda().getItem().getDescricao();
		}else{
			c1 = a.getItemdemanda().getItem().getCodigo();
			c2 = this.getItemdemanda().getItem().getCodigo();			
		}
		
		return c2.compareTo(c1);
	}

	public boolean changeFamilia(Demanda b){
		
		int i = 0, j = 0, k = 0;
		
		if(this.getItemdemanda().getItem().getFamilia().equalsIgnoreCase("02-Paradidatico")){
			this.getItemdemanda().getItem().setFamilia("03-Literatura");
			this.getItemdemanda().getItem().setNivel("05-Geral");
			i = 1;
		}else if(this.getItemdemanda().getItem().getFamilia().equalsIgnoreCase("03-Literatura")){
			this.getItemdemanda().getItem().setNivel("05-Geral");
			i = 1;
		}
		
		if(b.getItemdemanda().getItem().getFamilia().equalsIgnoreCase("02-Paradidatico")){
			b.getItemdemanda().getItem().setFamilia("03-Literatura");
			b.getItemdemanda().getItem().setNivel("05-Geral");
			j = 1;
		}else if(b.getItemdemanda().getItem().getFamilia().equalsIgnoreCase("03-Literatura")){
			b.getItemdemanda().getItem().setNivel("05-Geral");
			j = 1;
		}

		if(this.getItemdemanda().getItem().getFamilia().equalsIgnoreCase("01-DIDLE")){
			this.getItemdemanda().getItem().setFamilia("01-Didatico");
			k = 1;
		}

		if(b.getItemdemanda().getItem().getFamilia().equalsIgnoreCase("01-DIDLE")){
			b.getItemdemanda().getItem().setFamilia("01-Didatico");
			k = 1;
		}
		
		return i == 1 || j == 1 || k == 1;
	}
	
	public int getQtdchegou() {
		return qtdchegou;
	}

	public void setQtdchegou(int qtdchegou) {
		this.qtdchegou = qtdchegou;
	}

	public int getAtendido() {
		return atendido;
	}

	public void setAtendido(int atendido) {
		this.atendido = atendido;
	}

	public int getQtdsimulada() {
		return qtdsimulada;
	}

	public void setQtdsimulada(int qtdsimulada) {
		this.qtdsimulada = qtdsimulada;
	}

	public int getNecsimulada() {
		return necsimulada;
	}

	public void setNecsimulada(int necsimulada) {
		this.necsimulada = necsimulada;
	}

	public int getPedsp() {
		return pedsp;
	}

	public void setPedsp(int pedsp) {
		this.pedsp = pedsp;
	}

	public int getQtdprevsp() {
		return qtdprevsp;
	}

	public void setQtdprevsp(int qtdprevsp) {
		this.qtdprevsp = qtdprevsp;
	}

	public int getVendaatual() {
		return vendaatual;
	}

	public void setVendaatual(int vendaatual) {
		this.vendaatual = vendaatual;
	}	
	
}
