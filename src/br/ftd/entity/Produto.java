package br.ftd.entity;

import java.io.Serializable;
import java.sql.Date;


public class Produto implements Comparable<Object>, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigo;
	private String descricao;
	private float preco;
	private String codbar;
	private String serie;
	private String obs;
	private String autor;
	private String nivel;
	private String familia;
	private String colecao;
	private String disciplina;
	private String editora;
	private String nivelestoque = "Sob Consulta";
	private int estoque = 0;
	private int qtdvendida;
	private int alm01;
	private int alm02;
	private int alm09;
	private int reserva;
	private int fil01;
	private int fil02;
	private int ativo;
	private String imagem;
	private Date previsao;
	private String obspedido;
	private int marketshare;
	private String grupo;
	private int paginas;
	private String lancto;
	private float peso;
	private String status;
	
	public Produto(String codigo, String descricao, float preco) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
		this.preco = preco;
		this.alm01 = 0;
		this.alm02 = 0;
		this.alm09 = 0;
		this.reserva = 0;
		this.fil01 = 0;
		this.fil02 = 0;
		this.estoque = 0;
		this.nivelestoque = "Sob Consulta";
	}
	
	public Produto(){
		
		this.alm01 = 0;
		this.alm02 = 0;
		this.alm09 = 0;
		this.reserva = 0;
		this.fil01 = 0;
		this.fil02 = 0;
		this.estoque = 0;
		this.nivelestoque = "Sob Consulta";
	};
	
	public void refazNivelEstoque(){

		if(this.getEstoque()>10){
			this.setNivelestoque("Disponivel");
		}else{
			
			this.setNivelestoque("Sob Consulta");
		}
		
	}
	
	public void refazNivelEstoquePedCliente(int qtdpendente){
		
		if(this.getEstoque()>0){
			if(this.getFamilia() == "01-Didatico"){
				if(qtdpendente > (this.getEstoque()-30))
					this.setNivelestoque("Sob Consulta");
				else
					this.setNivelestoque("Disponivel");
			}else{
				if(qtdpendente > (this.getEstoque()-10))
					this.setNivelestoque("Sob Consulta");
				else
					this.setNivelestoque("Disponivel");				
			}
		}else{
			this.setNivelestoque("Sob Consulta");
		}		
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}

	public String getCodbar() {
		return codbar;
	}

	public void setCodbar(String codbar) {
		this.codbar = codbar;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getFamilia() {
		return familia;
	}

	public void setFamilia(String familia) {
		this.familia = familia;
	}

	public String getColecao() {
		return colecao;
	}

	public void setColecao(String colecao) {
		this.colecao = colecao;
	}
	
	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}
	
	public int getQtdvendida() {
		return qtdvendida;
	}

	public void setQtdvendida(int qtdvendida) {
		this.qtdvendida = qtdvendida;
	}

	@Override
	public int compareTo(Object p) {
		// TODO Auto-generated method stub
		Produto a = (Produto)p;
		
		String c1 = a.getCodigo();
		String c2 = this.getCodigo();
		return c2.compareTo(c1);
	}

	public String getNivelestoque() {
		return nivelestoque;
	}

	public void setNivelestoque(String nivelestoque) {
		this.nivelestoque = nivelestoque;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public int getAlm01() {
		return alm01;
	}

	public void setAlm01(int alm01) {
		this.alm01 = alm01;
	}

	public int getAlm02() {
		return alm02;
	}

	public void setAlm02(int alm02) {
		this.alm02 = alm02;
	}

	public int getAlm09() {
		return alm09;
	}

	public void setAlm09(int alm09) {
		this.alm09 = alm09;
	}

	public int getReserva() {
		return reserva;
	}

	public void setReserva(int reserva) {
		this.reserva = reserva;
	}
	
	public void refazEstoque(){
		estoque = (alm01 + alm02 + alm09) - reserva;
	}
	
	public boolean temEstoque(){
		
		if(alm01 == 0 && alm02 == 0 && alm09 == 0 && reserva == 0)
			return false;
		
		return true;
	}

	public int getFil01() {
		return fil01;
	}

	public void setFil01(int fil01) {
		this.fil01 = fil01;
	}

	public int getFil02() {
		return fil02;
	}

	public void setFil02(int fil02) {
		this.fil02 = fil02;
	}

	public String getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public int getAtivo() {
		return ativo;
	}

	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public Date getPrevisao() {
		return previsao;
	}

	public void setPrevisao(Date previsao) {
		this.previsao = previsao;
	}

	public String getObspedido() {
		return obspedido;
	}

	public void setObspedido(String obspedido) {
		this.obspedido = obspedido;
	}

	public int getMarketshare() {
		return marketshare;
	}

	public void setMarketshare(int marketshare) {
		this.marketshare = marketshare;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Produto)) return false;
		Produto that = (Produto) o;
		return this.getCodigo().equals(that.getCodigo());
	}

	public int getPaginas() {
		return paginas;
	}

	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}

	public String getLancto() {
		return lancto;
	}

	public void setLancto(String lancto) {
		this.lancto = lancto;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String toString(){
		return "Codigo: "+this.codigo
				+ "\nDescricao: "+this.descricao
				+ "\nCodBarras: "+this.codbar
		        + "\nPreco: "+this.preco;
	}
}
