package br.ftd.entity;

public enum PedidoStatus {
	
	AguardaSep("AGUARDANDO SEPARACAO"),
	AtendParc("ATENDIDO PARCIALMENTE"),
	AtendTot("ATENDIDO TOTALMENTE");
	
	private final String status;
	
	PedidoStatus(String status){
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
}
