package br.ftd.entity;

public enum TotvsDb {
	
	SA1("SA1"), SA2("SA2"), SB1("SB1"), SB2("SB2"),
	SC5("SC5"), SC6("SC6"), SD1("SD1"), SD2("SD2"), SF1("SF1"), SF2("SF2");
	
	private final String tabela;
	 
	TotvsDb(String table){
		 this.tabela = table;
	}
	 
	public String getTable(String empresa){
		return tabela + empresa;
	}
}
