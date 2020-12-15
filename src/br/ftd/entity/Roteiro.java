package br.ftd.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Roteiro {
	
	private int id;
	private Date data;
	private Usuario vendedor;
	private List<Escola> escolas = new ArrayList<>();
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Usuario getVendedor() {
		return vendedor;
	}
	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}
	public List<Escola> getEscolas() {
		return escolas;
	}
	public void setEscolas(List<Escola> escolas) {
		this.escolas = escolas;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
}
