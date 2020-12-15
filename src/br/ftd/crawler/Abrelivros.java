package br.ftd.crawler;

public class Abrelivros {
	private String a_href;
	private String a_text;
	private String p_text;
	private String dd_text;
	public String getA_href() {
		return a_href;
	}
	public void setA_href(String a_href) {
		this.a_href = a_href;
	}
	public String getA_text() {
		return a_text;
	}
	public void setA_text(String a_text) {
		this.a_text = a_text;
	}
	public String getP_text() {
		return p_text;
	}
	public void setP_text(String p_text) {
		this.p_text = p_text;
	}
	public String getDd_text() {
		return dd_text;
	}
	public void setDd_text(String dd_text) {
		this.dd_text = dd_text;
	}
	
	public String toString() {
		return "Link: "+a_href+" - Titulo: "+a_text+" - Texto: "+p_text;
	}
}
