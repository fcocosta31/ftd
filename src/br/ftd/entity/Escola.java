package br.ftd.entity;

import java.io.IOException;
import java.io.Reader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import br.ftd.model.FTDProperties;


public class Escola {
	
	private int id;
	private int idftd;
	private String nome;
	private String classificacao;
	private String cnpj;
	private String endereco;
	private String complemento;
	private String bairro;
	private String municipio;
	private String uf;
	private String cep;
	private String email;
	private String telefone;
	private Usuario vendedor;
	private int setor;
	private int infantil0;
	private int infantil1;
	private int infantil2;
	private int infantil3;
	private int infantil4;
	private int infantil5;
	private int ano1;
	private int ano2;
	private int ano3;
	private int ano4;
	private int ano5;
	private int ano6;
	private int ano7;
	private int ano8;
	private int ano9;
	private int serie1;
	private int serie2;
	private int serie3;
	private int eja;
	private int supletivo;
	private int totalalunos;
	private String observacao;
	private List<Date> ultimavisita;
	private ArrayList<Professor> professores;
	private ArrayList<Adocao> adocoes;
	private int ativo;
	private String dependencia;
	private String ano;
	private double lon = 0;
	private double lat = 0;
	private int user_id;
	private Auditoria audit;
	private static final String apikey = new FTDProperties().getApikey(); 
	private static final String urlapi = new FTDProperties().getUrlapi();
	
	public Escola(){
		idftd = 0;
		infantil0 = 0;
		infantil1 = 0;
		infantil2 = 0;
		infantil3 = 0;
		infantil4 = 0;
		infantil5 = 0;
		ano1 = 0;
		ano2 = 0;
		ano3 = 0;
		ano4 = 0;
		ano5 = 0;
		ano6 = 0;
		ano7 = 0;
		ano8 = 0;
		ano9 = 0;
		serie1 = 0;
		serie2 = 0;
		serie3 = 0;
		eja = 0;
		supletivo = 0;
		lon = 0;
		lat = 0;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getClassificacao() {
		return classificacao;
	}
	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public int getSetor() {
		return setor;
	}
	public void setSetor(int setor) {
		this.setor = setor;
	}
	public ArrayList<Professor> getProfessores() {
		return professores;
	}
	public void setProfessores(ArrayList<Professor> professores) {
		this.professores = professores;
	}
	public ArrayList<Adocao> getAdocoes() {
		return adocoes;
	}
	public void setAdocoes(ArrayList<Adocao> adocoes) {
		this.adocoes = adocoes;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public Usuario getVendedor() {
		return vendedor;
	}
	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	
	public String toString(){ 
		return id+"-"+nome+"-"+classificacao+"-"+cnpj+"\n"
				+endereco+"-"+complemento+"-"+bairro+"\n"
						+ municipio+"-"+uf+"-"+cep+"-"+email+"\n"
								+ telefone+"-"+setor;
	}
	public int getInfantil0() {
		return infantil0;
	}
	public void setInfantil0(int infantil0) {
		this.infantil0 = infantil0;
	}
	public int getInfantil1() {
		return infantil1;
	}
	public void setInfantil1(int infantil1) {
		this.infantil1 = infantil1;
	}
	public int getInfantil2() {
		return infantil2;
	}
	public void setInfantil2(int infantil2) {
		this.infantil2 = infantil2;
	}
	public int getInfantil3() {
		return infantil3;
	}
	public void setInfantil3(int infantil3) {
		this.infantil3 = infantil3;
	}
	public int getAno1() {
		return ano1;
	}
	public void setAno1(int ano1) {
		this.ano1 = ano1;
	}
	public int getAno2() {
		return ano2;
	}
	public void setAno2(int ano2) {
		this.ano2 = ano2;
	}
	public int getAno3() {
		return ano3;
	}
	public void setAno3(int ano3) {
		this.ano3 = ano3;
	}
	public int getAno4() {
		return ano4;
	}
	public void setAno4(int ano4) {
		this.ano4 = ano4;
	}
	public int getAno5() {
		return ano5;
	}
	public void setAno5(int ano5) {
		this.ano5 = ano5;
	}
	public int getAno6() {
		return ano6;
	}
	public void setAno6(int ano6) {
		this.ano6 = ano6;
	}
	public int getAno7() {
		return ano7;
	}
	public void setAno7(int ano7) {
		this.ano7 = ano7;
	}
	public int getAno8() {
		return ano8;
	}
	public void setAno8(int ano8) {
		this.ano8 = ano8;
	}
	public int getAno9() {
		return ano9;
	}
	public void setAno9(int ano9) {
		this.ano9 = ano9;
	}
	public int getSerie1() {
		return serie1;
	}
	public void setSerie1(int serie1) {
		this.serie1 = serie1;
	}
	public int getSerie2() {
		return serie2;
	}
	public void setSerie2(int serie2) {
		this.serie2 = serie2;
	}
	public int getSerie3() {
		return serie3;
	}
	public void setSerie3(int serie3) {
		this.serie3 = serie3;
	}
	public int getEja() {
		return eja;
	}
	public void setEja(int eja) {
		this.eja = eja;
	}
	public int getSupletivo() {
		return supletivo;
	}
	public void setSupletivo(int supletivo) {
		this.supletivo = supletivo;
	}

	public int getTotalalunos() {
		return totalalunos;
	}

	public void setTotalalunos(int totalalunos) {
		this.totalalunos = totalalunos;
	}
	
	public void refazTotalAlunos(){
		this.totalalunos = infantil0 + infantil1 + infantil2 + infantil3 + infantil4 + infantil5
				+ ano1 + ano2 + ano3 + ano4 + ano5 + ano6 + ano7 + ano8 + ano9 + serie1 + serie2
				+ serie3 + eja + supletivo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public int getIdftd() {
		return idftd;
	}

	public void setIdftd(int idftd) {
		this.idftd = idftd;
	}

	public List<Date> getUltimavisita() {
		return ultimavisita;
	}

	public void setUltimavisita(List<Date> ultimavisita) {
		this.ultimavisita = ultimavisita;
	}

	@Override
	public boolean equals(Object o){
		if(this == o) return true;
		if(!(o instanceof Escola)) return false;
		Escola that = (Escola) o;
		return (this.getId() == that.getId());
	}

	public int getAtivo() {
		return ativo;
	}

	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}

	public String getDependencia() {
		return dependencia;
	}

	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public static String getApikey() {
		return apikey;
	}

	public static String getUrlapi() {
		return urlapi;
	}

	public void getLonLat(){
	/*	
		if(this.lon == 0 && this.lat == 0){
			ObjectMapper mapper = new ObjectMapper();
			String jsonUrl = urlapi+this.endereco+","+this.complemento+","+this.bairro+","+this.municipio+","+this.uf;
			jsonUrl = jsonUrl.replaceAll("  ", " ");
			jsonUrl = jsonUrl.replaceAll(" ", "+");
			jsonUrl += "&key="+apikey;
			
			InputStream is;
			Staff obj = new Staff();
			
			try {
				
				URL url = new URL(jsonUrl);
				HttpURLConnection connection = (HttpURLConnection)url.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();
	
				int code = connection.getResponseCode();
						
				if(code == 200){
					is = new URL(jsonUrl).openStream();
					BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
					String jsonText = readAll(rd);
				      
					//JSON from String to Object
					obj = mapper.readValue(jsonText, Staff.class);				
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			if(obj.getStatus() != null){
				if(obj.getStatus().equalsIgnoreCase("OK")){
				this.lat = obj.getResults().get(0).getGeometry().getLocation().getLat();
				this.lon = obj.getResults().get(0).getGeometry().getLocation().getLng();
				DAOEscola.setLonLat(this);
				}
			}
		}
		*/
		this.lat = 0;
		this.lon = 0;
	}

	
	@SuppressWarnings("unused")
	private static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
	}

	public int getInfantil4() {
		return infantil4;
	}

	public void setInfantil4(int infantil4) {
		this.infantil4 = infantil4;
	}

	public int getInfantil5() {
		return infantil5;
	}

	public void setInfantil5(int infantil5) {
		this.infantil5 = infantil5;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
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

}
