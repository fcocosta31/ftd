package br.ftd.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.axis2.databinding.types.soapencoding.Array;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;

import br.ftd.crawler.Abrelivros;
import br.ftd.crawler.Carrousel;
import br.ftd.crawler.HTMLJsoup;
import br.ftd.email.EmailReceiver;
//import br.ftd.email.JavaMailApp;
import br.ftd.email.JavaMailApp2;
import br.ftd.entity.Adocao;
import br.ftd.entity.Bonus;
import br.ftd.entity.ClassificacaoEscola;
import br.ftd.entity.Demanda;
import br.ftd.entity.Doacao;
import br.ftd.entity.DoacaoRelat;
import br.ftd.entity.Empresa;
import br.ftd.entity.EmpresaMin;
import br.ftd.entity.EnumList;
import br.ftd.entity.Escola;
import br.ftd.entity.EscolaAdocoes;
import br.ftd.entity.EscolaMin;
import br.ftd.entity.Fornecedor;
import br.ftd.entity.ItemAdocao;
import br.ftd.entity.ItemDemanda;
import br.ftd.entity.ItemNotaFiscal;
import br.ftd.entity.ItemOrcamento;
import br.ftd.entity.ItemPedCliente;
import br.ftd.entity.ItemPedido;
import br.ftd.entity.ItemTabela;
import br.ftd.entity.Kardex;
import br.ftd.entity.KeyPass;
import br.ftd.entity.NotaFiscal;
import br.ftd.entity.NotasReport;
import br.ftd.entity.Orcamento;
import br.ftd.entity.Params;
import br.ftd.entity.PedCliente;
import br.ftd.entity.Pedido;
import br.ftd.entity.Produto;
import br.ftd.entity.ProdutoMin;
import br.ftd.entity.Professor;
import br.ftd.entity.ProfessorMin;
import br.ftd.entity.ResponseToExcel;
import br.ftd.entity.ResultSetToExcel;
import br.ftd.entity.Roteiro;
import br.ftd.entity.RoteiroResumo;
import br.ftd.entity.TotvsDb;
import br.ftd.entity.Usuario;
import br.ftd.entity.Venda;
import br.ftd.factory.ConnectionFactory;
import br.ftd.librery.TypeToken;
import br.ftd.model.DAODemanda;
import br.ftd.model.DAODoacao;
import br.ftd.model.DAOEmpresa;
import br.ftd.model.DAOEscola;
import br.ftd.model.DAOKeypass;
import br.ftd.model.DAONotaFiscal;
import br.ftd.model.DAOParams;
import br.ftd.model.DAOPedCliente;
import br.ftd.model.DAOPedido;
import br.ftd.model.DAOProduto;
import br.ftd.model.DAOProfessor;
import br.ftd.model.DAOQuality;
import br.ftd.model.DAOReport;
import br.ftd.model.DAOUsuario;
import br.ftd.model.DAOUtils;
import br.ftd.model.DAOVenda;
import br.ftd.quality.GlassView;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;


/**
 * ControlServlet implementation class FTDControlServlet
 */
@SuppressWarnings("unused")
@WebServlet({"/srl" })
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB 
				maxFileSize=1024*1024*10,      // 10MB
				maxRequestSize=1024*1024*50)   // 50MB
public class ControlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private java.util.Date iniData = null;
	private static final String beginArq = "SL";
	private static Params params = DAOParams.getSystemParams();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControlServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			actionControl(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			actionControl(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Params getParams() {
		return params;
	}
	
	//verifica qual m�todo ser� chamado pelo servlet
	private void actionControl(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Params p = params;
		
		getServletContext().setAttribute("systemparams", p);
		
		//Headers of webpages
		getServletContext().setAttribute("pageTitle", p.getPagetitle());		
		getServletContext().setAttribute("pageEmail", p.getPageemail());
		getServletContext().setAttribute("pageFone", p.getPagefone());
		getServletContext().setAttribute("pageUF", p.getPageuf());
		getServletContext().setAttribute("pageMSG", p.getPagemsg());
		
		//Crawler para carregar o carrousel e as noticias do index.jsp
		getCrawlerWeb();				
		
		String acao = request.getParameter("acao");
		
		if(acao != null){
			if(acao.equals("pesquisarprd"))
				pesquisar(request, response);
			else if(acao.equals("cadastrarprd"))	
				cadastrarProduto(request, response);
			else if(acao.equals("editarprd"))	
				editarProduto(request, response);
			else if(acao.equals("ativarprd"))	
				ativarProduto(request, response);
			else if(acao.equals("importcadprod"))	
			   	importCadastroProduto(request, response);
			else if(acao.equals("importcadescola"))	
			   	importCadastroEscola(request, response);
			else if(acao.equals("importped"))   	
		    	importPedido(request, response);
			else if(acao.equals("updateitempedido"))   	
		    	updateItemPedido(request, response);
			else if(acao.equals("exportprc"))	
		    	exportPrecos(request, response);	
			else if(acao.equals("consultarprd"))	
				consultarProduto(request, response);
			else if(acao.equals("atualizarprc"))	
				atualizarPrecos(request, response);
			else if(acao.equals("dadosprd"))
				dadosProduto(request, response);
			else if(acao.equals("detalheproduto"))
				detalheProduto(request, response);
			else if(acao.equals("consultarped"))
				consultarPedido(request, response);
			else if(acao.equals("detalharped"))
				detalharPedido(request, response);
			else if(acao.equals("detalharhistoricopedcliente"))
				detalharHistoricoPedCliente(request, response);
			else if(acao.equals("additem"))
				adicionaItem(request, response);
			else if(acao.equals("descartar"))
				descartarOrcamento(request, response);
			else if(acao.equals("remitem"))
				removeItem(request, response);
			else if(acao.equals("remitemdoacao"))
				removeItemDoacao(request, response);
			else if(acao.equals("deletardoacao"))
				deletarDoacao(request, response);
			else if(acao.equals("logon"))
				usuarioLogon(request, response);
			else if(acao.equals("logout"))
				usuarioLogout(request, response);
		//	else if(acao.equals("reportProd")) 
		//		gerarRelatorioProduto(request, response);
			else if(acao.equals("notapedido"))
				vinculaNotaPedido(request, response);			
			else if(acao.equals("importnotafiscal"))
				importarNotaFiscal(request, response);			
			else if(acao.equals("pesquisarescola"))
				pesquisarEscolas(request, response);
			else if(acao.equals("recuperarEscola"))
				recuperarEscola(request, response);
			else if(acao.equals("viewmap"))
				viewMap(request, response);
			else if(acao.equals("editarmarker"))
				editarMarker(request, response);
			else if(acao.equals("selecionarescola"))
				selecionarEscola(request, response);
			else if(acao.equals("esquecerescola"))
				esquecerEscola(request, response);
			else if(acao.equals("registraradocao"))
				registrarAdocao(request,response);
			else if(acao.equals("pesquisaradocao"))
				pesquisarAdocao(request,response);
			else if(acao.equals("detalharadocao"))
				detalharAdocao(request, response);
			else if(acao.equals("deletarserieadocao"))
				deletarSerieAdocao(request, response);
			else if(acao.equals("seladocoes"))
				selecionaAdocoes(request, response);			
			else if(acao.equals("exportnotafiscal"))
				exportarNotaFiscal(request, response);
			else if(acao.equals("salvarescola"))
				salvarEscola(request, response);
			else if(acao.equals("listardemanda"))
				listarDemanda(request, response);
			else if(acao.equals("adocaodetalhartodos"))
				detalharAdocaoTodos(request, response);
			else if(acao.equals("doacaoregistrar"))
				registrarDoacao(request, response);
			else if(acao.equals("doacaoimprimir"))
				imprimirDoacao(request, response);			
			else if(acao.equals("pesquisardoacao"))
				pesquisarDoacao(request, response);			
			else if(acao.equals("pendentesexcel"))
				pendentesToExcel(request, response);			
			//else if(acao.equals("tabeladeprecos"))
			//	produtoToExcel(request, response);			
			else if(acao.equals("datachegadanota"))
				dataChegadaNota(request, response);			
			else if(acao.equals("listardoacao"))
				listarDoacao(request, response);			
			else if(acao.equals("listaritensdoacao"))
				listarItensDoacao(request, response);			
			else if(acao.equals("geradoacoestoexcel"))
				geraDoacoesToExcel(request, response);			
			else if(acao.equals("listardoacoestoexcel"))
				listarDoacoesToExcel(request, response);			
			else if(acao.equals("marcaritensacertados"))
				marcarItensDoacaoAcertados(request, response);			
			else if(acao.equals("loadvendedores"))
				listarVendedores(request, response);			
			else if(acao.equals("listarnotafiscal"))
				listarNotaFiscal(request, response);			
			else if(acao.equals("detalharnotafiscal"))
				detalharNotaFiscal(request, response);			
			else if(acao.equals("consultarpedidos"))
				consultarPedidos(request, response);
			else if(acao.equals("consultarnotas"))
				consultarNotas(request, response);
			else if(acao.equals("consultarkardex"))
				consultarKardex(request, response);
			else if(acao.equals("detalharkardex"))
				detalharKardex(request, response);
			else if(acao.equals("adocaoescolaselect"))
				adocaoEscolaSelect(request, response);			
			else if(acao.equals("detalharPedidoToExcel"))
				detalharPedidoToExcel(request, response);			
			else if(acao.equals("orcamentoToExcel"))
				orcamentoToExcel(request, response);			
			else if(acao.equals("veritempedido"))
				verItemPedido(request, response);			
			else if(acao.equals("obsitempedido"))
				obsItemPedido(request, response);			
			else if(acao.equals("listarescolas"))
				listarEscolas(request, response);			
			else if(acao.equals("listarescolasexcel"))
				listarEscolasExcel(request, response);			
			else if(acao.equals("detalheescola"))
				detalheEscola(request, response);			
			else if(acao.equals("deletarescola"))
				deletarEscola(request, response);			
			else if(acao.equals("editarescola"))
				editarEscola(request, response);			
			else if(acao.equals("loadclassificacao"))
				listarClassificacao(request, response);			
			else if(acao.equals("cadastrarusuario"))
				cadastrarUsuario(request, response);			
			else if(acao.equals("verificalogin"))
				verificaLogin(request, response);			
			else if(acao.equals("editaritempedido"))
				editarItemPedido(request, response);
			else if(acao.equals("relatorioestoque"))
				estoqueToExcel(request, response);
			else if(acao.equals("consultaranopedido"))
				consultarAnoPedido(request, response);
			else if(acao.equals("vendassintetico"))
				listaVendasSintetico(request, response);
			else if(acao.equals("roteiroaddescola"))
				roteiroAddEscola(request, response);
			else if(acao.equals("excluirescolaroteiro"))
				roteiroExcluirEscola(request, response);
			else if(acao.equals("salvarroteiro"))
				roteiroSalvar(request, response);
			else if(acao.equals("deletaroteiro"))
				roteiroDeletar(request, response);
			else if(acao.equals("cancelarroteiro"))
				roteiroCancelar(request, response);
			else if(acao.equals("listarroteiros"))
				roteirosListar(request, response);				
			else if(acao.equals("resumoroteiro"))
				roteiroResumo(request, response);				
			else if(acao.equals("detalharroteiro"))
				roteiroDetalhar(request, response);
			else if(acao.equals("roteirosalvarobservacao"))
				roteiroSalvarObservacao(request, response);
			else if(acao.equals("salvaralunos"))
				escolaSalvarAlunos(request, response);
			else if(acao.equals("tabeladeprecos"))
				getTabelaPrecos(request, response);			
			else if(acao.equals("loadempresas"))
				loadEmpresas(request, response);			
			else if(acao.equals("carregartodosusuarios"))
				carregarTodosUsuarios(request, response);			
			else if(acao.equals("carregarusuario"))
				carregarUsuario(request, response);			
			else if(acao.equals("loaduf"))
				loadUF(request, response);			
			else if(acao.equals("loadmunicipios"))
				loadMunicipios(request, response);			
			else if(acao.equals("loadseriesadocao"))
				loadSeriesAdocao(request, response);			
			else if(acao.equals("loadcombosproduto"))
				loadCombosProduto(request, response);			
			else if(acao.equals("remitemadocao"))
				removeItemAdocao(request, response);			
			else if(acao.equals("finalizarlistaadocao"))
				finalizarRegistroAdocao(request, response);			
			else if(acao.equals("abandonarregistroadocao"))
				abandonarRegistroAdocao(request, response);			
			else if(acao.equals("loadseriesproduto"))
				loadSeriesProduto(request, response);			
			else if(acao.equals("exportaprodutostxt"))
				exportaProdutos(request, response);			
			else if(acao.equals("consultaradocoes"))
				consultarAdocoes(request, response);			
			else if(acao.equals("consultardoacoes"))
				consultarDoacoes(request, response);			
			else if(acao.equals("loadcomboprofessor"))
				loadComboProfessor(request, response);
			else if(acao.equals("listarprofessores"))
				listarProfessores(request, response);			
			else if(acao.equals("listartodosprofessores"))
				listarTodosProfessores(request, response);			
			else if(acao.equals("dadosprofessor"))
				dadosProfessor(request, response);			
			else if(acao.equals("alterardoacao"))
				alterarDoacao(request, response);			
			else if(acao.equals("loadcombodisciplinas"))
				loadComboDisciplinas(request, response);			
			else if(acao.equals("cadastrarprofessor"))
				cadastrarProfessor(request, response);
			else if(acao.equals("editarprofessor"))
				editarProfessor(request, response);						
			else if(acao.equals("deletarprofessor"))
				deletarProfessor(request, response);						
			else if(acao.equals("adicionaraopedido"))
				adicionarAoPedido(request, response);			
			else if(acao.equals("descartarpedido"))
				descartarPedido(request, response);			
			else if(acao.equals("remitempedcliente"))
				removeItemPedido(request, response);			
			else if(acao.equals("getdadosempresa"))
				getDadosEmpresa(request, response);			
			else if(acao.equals("editarusuario"))
				editarUsuario(request, response);			
			else if(acao.equals("deletarusuario"))
				deletarUsuario(request, response);			
			else if(acao.equals("alterarsenhausuario"))
				alterarSenhaUsuario(request, response);			
			else if(acao.equals("pesquisarempresa"))
				pesquisarEmpresa(request, response);			
			else if(acao.equals("pedidoregistrar"))
				salvarPedCliente(request, response);			
			else if(acao.equals("alterarnomecliente"))
				alterarNomeCliente(request, response);			
			else if(acao.equals("numeroalunosescola"))
				numeroAlunosEscola(request, response);			
			else if(acao.equals("listarpedcliente"))
				listarPedCliente(request, response);			
			else if(acao.equals("downloadpedcliente"))
				downloadPedCliente(request, response);
			else if(acao.equals("updateitemvalue"))
				updateItemValue(request, response);
			else if(acao.equals("pedidoatendidos"))
				atendidosPedCliente(request, response);
			else if(acao.equals("importaorcam"))
				importaOrcamento(request, response);
			else if(acao.equals("pedidouploadfile"))
				importaOrcamento(request, response);
			else if(acao.equals("detalharpedcliente"))
				detalharPedCliente(request, response);
			else if(acao.equals("downloadpedclientecsv"))
				downloadPedClienteCSV(request, response);
			else if(acao.equals("deletepedcliente"))
				deletePedCliente(request, response);
			else if(acao.equals("exportaconsultaexcel"))
				exportaConsultaExcel(request, response);
			else if(acao.equals("downloadtemplate"))
				downloadTemplateCadastroProduto(request, response);
			else if(acao.equals("listarbairros"))
				listarBairros(request, response);
			else if(acao.equals("listarmunicipios"))
				listarMunicipios(request, response);
			else if(acao.equals("aplicardesconto"))
				aplicarDesconto(request, response);
			else if(acao.equals("pedclientependente"))
				pedClientePendente(request, response);
			else if(acao.equals("loadyearselection"))
				loadYearSelection(request, response);
			else if(acao.equals("loadadocaotermometro"))
				loadAdocaoTermometro(request, response);
			else if(acao.equals("deletarpedidofornecedor"))
				deletarPedidoFornecedor(request, response);
			else if(acao.equals("deletarnotafiscal"))
				deletarNotaFiscal(request, response);
			else if(acao.equals("vendasprodutos"))
				vendasPorProdutos(request, response);
			else if(acao.equals("atualizarprevisoes"))
				atualizarPrevisoes(request, response);
			else if(acao.equals("loadcodcliente"))
				loadCodCliente(request, response);
			else if(acao.equals("loadcodmun"))
				loadCodMun(request, response);
			else if(acao.equals("cadastrarcliente"))
				cadastrarCliente(request, response);
			else if(acao.equals("preparedoacao"))
				prepareDoacao(request, response);
			else if(acao.equals("consultarpendencias"))
				pendenciasDoProduto(request, response);
			else if(acao.equals("getglassview"))
				getGlassView(request, response);
			else if(acao.equals("verlistas"))
				verListas(request, response);
			else if(acao.equals("bonusregistrar"))
				bonusRegistrar(request, response);
			else if(acao.equals("bonusdetalhar"))
				bonusDetalhar(request, response);
			else if(acao.equals("bonuspesquisar"))
				bonusPesquisar(request, response);
			else if(acao.equals("sendmailorcam"))
				sendMailOrcam(request, response);
			else if(acao.equals("sendmailpedidoorcam"))
				sendMailPedidoOrcam(request, response);
			else if(acao.equals("importprevisaosp"))
				importPrevisaoSp(request, response);
			else if(acao.equals("importkeypass"))
				importKeyPass(request, response);
			else if(acao.equals("sendmailnf"))
				sendMailNf(request, response);
			else if(acao.equals("sendmailpedcliente"))
				sendMailPedCliente(request, response);
			else if(acao.equals("listaritensatendidos"))
				listarItensAtendidos(request, response);
			else if(acao.equals("listclientesdopedcliente"))
				listClientesDoPedcliente(request, response);
			else if(acao.equals("itenspedclientependentes"))
				itensPedClientePendentes(request, response);
			else if(acao.equals("descartarbonus"))
				descartarBonus(request, response);
			else if(acao.equals("bonuseditar"))
				bonusEditar(request, response);
			else if(acao.equals("bonusdeletar"))
				bonusDeletar(request, response);
			else if(acao.equals("listitensemtransito"))
				listItensEmTransito(request, response);
			else if(acao.equals("alteraritematendido"))
				alterarItemAtendido(request, response);
			else if(acao.equals("alterarqtdeitempedido"))
				alterarQtdeItemPedido(request, response);
			else if(acao.equals("notasreport"))
				notasReport(request, response);
			else if(acao.equals("notasorcamsexcel"))
				notasOrcamsExcel(request, response);
			else if(acao.equals("notasfiscaisexcel"))
				notasFiscaisExcel(request, response);
			else if(acao.equals("loadcombofornecedor"))
				loadComboFornecedor(request, response);
			else if(acao.equals("setsystemparams"))
				setSystemParams(request, response);
			else if(acao.equals("loadsystemparams"))
				loadSystemParams(request, response);
			else if(acao.equals("downloadnotasemail"))
				downloadNotasEmail(request, response);
			else if(acao.equals("exportallnotas"))
				exportAllNotas(request, response);
			else if(acao.equals("alteraguardarpendencia"))
				alteraGuardarPendencia(request, response);
			else if(acao.equals("cancelaguardarpendencia"))
				cancelaGuardarPendencia(request, response);
			
		}else{
			response.sendRedirect("index.jsp");
		}
		
	}
		
	private void cancelaGuardarPendencia(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		// TODO Auto-generated method stub
		Date ini = convertStringToDate(request.getParameter("dataini"));
		Date fim = convertStringToDate(request.getParameter("datafim"));
		DAOPedCliente dao = new DAOPedCliente();
		String mensagem = dao.cancelaGuardarPendencia(ini, fim);
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);						
	}

	private void alteraGuardarPendencia(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		// TODO Auto-generated method stub
		
		int idpedido = Integer.parseInt(request.getParameter("txtidpedido"));
		int opcao = Integer.parseInt(request.getParameter("opcao"));
		DAOPedCliente dao = new DAOPedCliente();
		String mensagem = dao.alteraGuardarPendencia(idpedido, opcao);
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);				
	}

	@SuppressWarnings({ "static-access", "unchecked" })
	private void ativarProduto(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(false);
		
		List<Produto> itens = (List<Produto>) session.getAttribute("tabela_servlet");
		
		if(itens == null){
			session = request.getSession(true);
			itens = (List<Produto>) session.getAttribute("tabela_servlet");
		}
		
		String codigo = request.getParameter("txtcodigo");
	
		Produto p = new Produto();
		p.setCodigo(codigo);
		
		DAOProduto dao = new DAOProduto();
		dao.recarrega(p);
		
		boolean flag = false;
		String mensagem = "";

		try {
			flag = dao.ativarProduto(p);
			dao.setEstoque(p);

			if(flag){
				if(itens != null){
					itens.add(p);
				}else {
					itens = new ArrayList<>();
					itens.add(p);
				}
				mensagem = "Produto ativado com sucesso!";
			}else{
				mensagem = "Erro ao tentar ativar o produto!";
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		session.removeAttribute("tabela_servlet");
		session.setAttribute("tabela_servlet", itens);
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);				
		
	}

	private void exportAllNotas(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		// TODO Auto-generated method stub
		String[] notas = request.getParameterValues("_notas-id");
		String[] cnpjs = request.getParameterValues("_cnpj-id");
		String fornecedor = cnpjs[0];
		DAONotaFiscal dao = new DAONotaFiscal();
		System.out.println("CNPJ: "+fornecedor+" - 1a Nota: "+notas[0]);
		dao.exportaNotasFTD(response, fornecedor, notas);
	}

	private void detalharHistoricoPedCliente(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		
		session.removeAttribute("pedcliente");
		//session.removeAttribute("historico");
		
		int idpedido = Integer.parseInt(request.getParameter("idpedido"));
		PedCliente pedido = new PedCliente();
		pedido.setIdpedido(idpedido);
		Date atendimento = convertStringToDatePrev(request.getParameter("txtdata"));
		DAOPedCliente.recarrega(pedido, atendimento);
		session.setAttribute("pedcliente", pedido);
		response.sendRedirect("pedClienteRegistrar.jsp");
	}

	private void alterarNomeCliente(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		Usuario user = (Usuario) session.getAttribute("usuariologado");

		
		PedCliente pedido = (PedCliente) session.getAttribute("pedcliente");
		String clienteantigo = "["+pedido.getCliente().getCodigoftd()+"] - "+pedido.getCliente().getRazaosocial();
		Empresa cliente = new Empresa();
		cliente.setCodigoftd(request.getParameter("_idempresa"));
		DAOEmpresa.getDadosEmpresa(cliente);
		pedido.setCliente(cliente);
		String mensagem = DAOPedCliente.alterarNomeCliente(pedido);
		session.setAttribute("pedcliente", pedido);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		Gson gson = new Gson();
		String jsonObject = gson.toJson(mensagem);
		response.getWriter().write(jsonObject);														
		
	}

	private void updateItemValue(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		PedCliente pedido = (PedCliente) session.getAttribute("pedcliente");
		int quantidade = Integer.parseInt(request.getParameter("valor"));
		String codigo = request.getParameter("codigo");
		
		for(ItemPedCliente i : pedido.getItens()){
			if(i.getItem().getCodigo().equals(codigo)) {
				i.setQtdatendida(quantidade);
				i.setFlag(true);
				i.refazPendente();
				break;
			}
		}
		session.setAttribute("pedcliente", pedido);
		
	}

	private void downloadNotasEmail(HttpServletRequest request, HttpServletResponse response) throws DOMException, ParserConfigurationException, SAXException, ParseException, SQLException, IOException, GeneralSecurityException {
		// TODO Auto-generated method stub

		EmailReceiver emails = new EmailReceiver();
		Params params = getParams();
		String protocol = params.getProtocolrecmail(); //pop3
        String host = params.getHostrecmail(); //"pop.veloxmail.com.br"
        String port = params.getPortrecmail(); //"995"
 
 
        String userName = params.getLoginrecmail(); //"fcocosta@veloxmail.com.br"
        String password = params.getPswrecmail(); //"fco677"

		String mensagem = emails.downloadEmails(protocol, host, port, userName, password, getServletContext());
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);														

	}

	private void loadSystemParams(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		String mensagem = "0";
		Usuario user = null;
		user = (Usuario) session.getAttribute("usuariologado");
		if(user != null && user.isMaster()) {
			Params params = DAOParams.getSystemParams();			
			session.removeAttribute("params");
			session.setAttribute("params", params);
			mensagem = "1";
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			Gson gson = new Gson();
			String jsonObject = gson.toJson(mensagem);		
					
			response.getWriter().write(jsonObject);																	
		}else {
			session.setAttribute("message", "Acesso negado!");
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			Gson gson = new Gson();
			String jsonObject = gson.toJson(mensagem);		
					
			response.getWriter().write(jsonObject);														
		}
	}

	private void setSystemParams(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		String mensagem = "Acesso negado!";
		Usuario user = (Usuario) session.getAttribute("usuariologado");
		if(user != null && user.isMaster()) {

			String pagetitle = request.getParameter("pagetitle");
			String pageemail = request.getParameter("pageemail");
			String pagefone = request.getParameter("pagefone");
			String pageuf = request.getParameter("pageuf");
			String pagemsg = request.getParameter("pagemsg");
			String gpoemptotvs = request.getParameter("gpoemptotvs");
			String mssqladdress = request.getParameter("mssqladdress");
			String mssqldomain = request.getParameter("mssqldomain");
			String mssqldb = request.getParameter("mssqldb");
			String mssqlport = request.getParameter("mssqlport");
			String mssqluser = request.getParameter("mssqluser");
			String mssqlpswd = request.getParameter("mssqlpswd");
			String loginmail = request.getParameter("loginmail");
			String pswdmail = request.getParameter("pswdmail");
			String ccmails = request.getParameter("ccmails");
			String loginrecmail = request.getParameter("loginrecmail");
			String pswrecmail = request.getParameter("pswrecmail");
			String hostrecmail = request.getParameter("hostrecmail");
			String protocolrecmail = request.getParameter("protocolrecmail");
			String portrecmail = request.getParameter("portrecmail");
			
			this.params = new Params();
			
			params.setPagetitle(pagetitle);
			params.setPageemail(pageemail);
			params.setPagefone(pagefone);
			params.setPageuf(pageuf);
			params.setPagemsg(pagemsg);
			params.setGpoemptotvs(gpoemptotvs);
			params.setMssqladdress(mssqladdress);
			params.setMssqldomain(mssqldomain);
			params.setMssqldb(mssqldb);
			params.setMssqlport(mssqlport);
			params.setMssqluser(mssqluser);
			params.setMssqlpswd(mssqlpswd);
			params.setLoginmail(loginmail);
			params.setPswdmail(pswdmail);
			params.setCcmails(ccmails);
			params.setLoginrecmail(loginrecmail);
			params.setPswrecmail(pswrecmail);
			params.setHostrecmail(hostrecmail);
			params.setProtocolrecmail(protocolrecmail);
			params.setPortrecmail(portrecmail);
			
			mensagem = DAOParams.setSystemParams(params);
			//Remove Older Attributes
			getServletContext().removeAttribute("systemparams");
			getServletContext().removeAttribute("pageTitle");
			getServletContext().removeAttribute("pageEmail");
			getServletContext().removeAttribute("pageFone");
			getServletContext().removeAttribute("pageUF");
			getServletContext().removeAttribute("pageMSG");
			//Headers of webpages
			getServletContext().setAttribute("systemparams", params);
			getServletContext().setAttribute("pageTitle", params.getPagetitle());		
			getServletContext().setAttribute("pageEmail", params.getPageemail());
			getServletContext().setAttribute("pageFone", params.getPagefone());
			getServletContext().setAttribute("pageUF", params.getPageuf());
			getServletContext().setAttribute("pageMSG", params.getPagemsg());
			
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			Gson gson = new Gson();
			String jsonObject = gson.toJson(mensagem);		
					
			response.getWriter().write(jsonObject);														
		}else {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			Gson gson = new Gson();
			String jsonObject = gson.toJson(mensagem);		
					
			response.getWriter().write(jsonObject);														
		}
	}

	private void loadComboFornecedor(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		// TODO Auto-generated method stub
		DAONotaFiscal dao = new DAONotaFiscal();
		List<Fornecedor> lista = dao.loadComboFornecedor();
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		Type listType = new TypeToken<ArrayList<Fornecedor>>(){}.getType();
		
		String jsonObject = gson.toJson(lista, listType);		
				
		response.getWriter().write(jsonObject);						
		
	}

	private void notasFiscaisExcel(HttpServletRequest request, HttpServletResponse response) throws ParsePropertyException, InvalidFormatException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/vnd.ms-excel");
    	response.setHeader("Content-Disposition", "attachment; filename=notasfiscais.xls");
    	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
    	
    	ServletContext context = request.getServletContext();
    	
    	String realPath = context.getRealPath("/");
    	    	
		String templatePath = realPath + "/resources/xls/notasFiscaisTemplate.xls";		
				
		
		InputStream is = new FileInputStream(templatePath);	

		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		
		@SuppressWarnings("unchecked")
		List<NotaFiscal> notas = (List<NotaFiscal>) session.getAttribute("notasfiscais");
				
		if(notas != null){
			Map<String, Object> beans = new HashMap<>();
			beans.put("nf", notas);
			
			XLSTransformer transformer = new XLSTransformer();
					
			Workbook workbook = transformer.transformXLS(is, beans);
			
			workbook.write(response.getOutputStream());		
		}else{
			response.getWriter().print("Erro ao gerar o arquivo");
		}		
	}

	private void notasReport(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		
		session.removeAttribute("reportnotas");
		
		Date inicio = convertStringToDate(request.getParameter("dataini"));
		Date fim = convertStringToDate(request.getParameter("datafim"));
		String filial = request.getParameter("filial");
		String aberto = request.getParameter("aberto");
		String clienteini = request.getParameter("clienteini");
		String clientefim = request.getParameter("clientefim");
		
		DAOReport dao = new DAOReport();
		
		List<NotasReport> lista = dao.getOrcamsTotvs(filial, clienteini, clientefim, inicio, fim, aberto);
		
		session.setAttribute("reportnotas", lista);
		
		response.sendRedirect("reportnotas.jsp");
	}

	private void alterarQtdeItemPedido(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		
		PedCliente pedido = (PedCliente) session.getAttribute("pedcliente");
				
		String codigo = request.getParameter("codigo");
		int quantidade = Integer.parseInt(request.getParameter(codigo+""));
		
		DAOPedCliente.alterarQtdeItemPedido(pedido, codigo, quantidade);
		
		session.setAttribute("pedcliente", pedido);
		
		response.sendRedirect("pedClienteRegistrar.jsp");		
	}

	private void alterarItemAtendido(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		
		PedCliente pedido = (PedCliente) session.getAttribute("pedcliente");
				
		String codigo = request.getParameter("codigo");
		int item = Integer.parseInt(request.getParameter("item"));
		int quantidade = Integer.parseInt(request.getParameter(codigo+"item"+item));
		Date emissao = convertStringToDatePrev(request.getParameter(codigo+"date"));
		
		DAOPedCliente.alterarItemAtendido(pedido, codigo, quantidade, emissao);
			
		session.setAttribute("pedcliente", pedido);
		
		response.sendRedirect("pedClienteRegistrar.jsp");
	}

	private void listItensEmTransito(HttpServletRequest request, HttpServletResponse response) throws ParsePropertyException, InvalidFormatException, IOException, SQLException {
		// TODO Auto-generated method stub
		Date inicio = convertStringToDate(request.getParameter("dataini"));
		Date fim = convertStringToDate(request.getParameter("datafim"));
		
		response.setContentType("application/vnd.ms-excel");
    	response.setHeader("Content-Disposition", "attachment; filename=itens_em_transito.xls");
    	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
    	
    	ServletContext context = request.getServletContext();
    	
    	String realPath = context.getRealPath("/");
    	    	
		String templatePath = realPath + "/resources/xls/pedidoTransitoTemplate.xls";
						
		InputStream is = new FileInputStream(templatePath);				
		
		DAOPedido dao = new DAOPedido();

		List<ItemPedido> lista = dao.getItensEmTransito(inicio, fim);
		
		Map<String, Object> beans = new HashMap<>();
		beans.put("lp", lista);
		beans.put("datainicial", inicio);
		beans.put("datafinal", fim);
		
		XLSTransformer transformer = new XLSTransformer();
				
		Workbook workbook = transformer.transformXLS(is, beans);
		
		workbook.write(response.getOutputStream());										
		
	}

	private void bonusDeletar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		Bonus bonus = (Bonus) session.getAttribute("bonus");
		boolean flag = DAOEscola.bonusDeletar(bonus);
		if(!flag){
			session.removeAttribute("bonus");
			response.sendRedirect("index.jsp");
		}else{
			String mensagem = "Ocorreu um erro ao tentar deletar!";
			session.setAttribute("message", mensagem);
			response.sendRedirect("result.jsp");
		}		
	}

	private void bonusEditar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		Bonus bonus = (Bonus) session.getAttribute("bonus");

		float desconto = Float.parseFloat(request.getParameter("txtdesconto"));
		String descricao = request.getParameter("txtdescricao");
		String tipo = request.getParameter("txttipo");
		bonus.setTipo(tipo);
		bonus.setContext(request.getServletContext());
		bonus.geraQRCode();
		DAOEscola.bonusEditar(bonus, descricao, desconto);
		session.setAttribute("bonus", bonus);
		response.sendRedirect("bonusImprimir.jsp");		
	}

	private void descartarBonus(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		session.removeAttribute("bonus");
		response.sendRedirect("index.jsp");
	}

	private void itensPedClientePendentes(HttpServletRequest request, HttpServletResponse response) throws IOException, ParsePropertyException, InvalidFormatException {
		// TODO Auto-generated method stub
		
		String codigoftd = request.getParameter("txtcliente");
		String datainicio = request.getParameter("dataini");
		String datafim = request.getParameter("datafim");
		String tipo = request.getParameter("txttipo");
		Date inicio = convertStringToDate(datainicio);
		Date fim = convertStringToDate(datafim);
		
		response.setContentType("application/vnd.ms-excel");
    	response.setHeader("Content-Disposition", "attachment; filename=pendencias-"+codigoftd+"-"+inicio+"_a_"+fim+".xls");
    	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
    	
    	ServletContext context = request.getServletContext();
    	
    	String realPath = context.getRealPath("/");
    	    	
		String templatePath = realPath + "/resources/xls/pedclientePendenteTemplate.xls";
		
		if(tipo.equalsIgnoreCase("resumido")) {
			templatePath = realPath + "/resources/xls/pedclientePendenteSinteticoTemplate.xls";
		}
		
		InputStream is = new FileInputStream(templatePath);				
		
		DAOPedCliente dao = new DAOPedCliente();

		List<ItemPedCliente> lista = dao.pedClientePendentes(codigoftd, tipo, inicio, fim);
		
		Map<String, Object> beans = new HashMap<>();
		beans.put("lp", lista);
		beans.put("datainicial", inicio);
		beans.put("datafinal", fim);
		beans.put("codigoftd", codigoftd);
		
		XLSTransformer transformer = new XLSTransformer();
				
		Workbook workbook = transformer.transformXLS(is, beans);
		
		workbook.write(response.getOutputStream());										
	}

	private void listClientesDoPedcliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		List<Empresa> lista = new ArrayList<>();
		DAOPedCliente dao = new DAOPedCliente();
		lista = dao.listClientesDoPedcliente();
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		Type listType = new TypeToken<ArrayList<Empresa>>(){}.getType();
		
		String jsonObject = gson.toJson(lista, listType);		
				
		response.getWriter().write(jsonObject);						
	}

	private void listarItensAtendidos(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String codigo = request.getParameter("txtcodigo");
		int idpedido = Integer.parseInt(request.getParameter("txtidpedido"));
		DAOPedCliente dao = new DAOPedCliente();		
		List<ItemPedCliente> itens = dao.listarItensAtendidos(codigo, idpedido);
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		Type listType = new TypeToken<ArrayList<ItemPedCliente>>(){}.getType();
		
		String jsonObject = gson.toJson(itens, listType);		
				
		response.getWriter().write(jsonObject);				
	}

	private void sendMailPedCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		//session.setMaxInactiveInterval(2*60*60);
				
		Usuario usuario = (Usuario) session.getAttribute("usuariologado");
		
		String pretitle = "FTD-Pedido";
		PedCliente pedido = (PedCliente) session.getAttribute("pedcliente");		
		String semail = request.getParameter("emailcliente");
		if(semail.equalsIgnoreCase("true")) {
			pedido.setSendmail(true);
		}

		JavaMailApp2 mail = new JavaMailApp2();
		String mensagem = mail.sendMailPedCliente(pedido, pretitle, "atualizado", usuario);
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);
		
	}

	private void sendMailNf(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		Orcamento orcam = (Orcamento) session.getAttribute("orcamento");
		
		Usuario usuario = (Usuario) session.getAttribute("usuariologado");
		
		String realPath = getServletContext().getRealPath("");
		
		String nome = request.getParameter("txtnome");
		String endereco = request.getParameter("txtendereco");
		String bairro = request.getParameter("txtbairro");
		String cidade = request.getParameter("txtcidade");
		String cep = request.getParameter("txtcep");
		String cpf = request.getParameter("txtcpf");		
		String fone = request.getParameter("txtfone");
		String email = request.getParameter("txtemail");
		String observacao = request.getParameter("txtobservacao");
		
		JavaMailApp2 mail = new JavaMailApp2();
		String mensagem = mail.sendMail(realPath, orcam, nome, endereco, bairro, cidade, cep, fone, cpf, email, usuario, observacao);				
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);												
	}

	private void importKeyPass(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
			String key = request.getParameter("keypass");
			DAOKeypass.setKeyPass(key);
			response.sendRedirect("index.jsp");
	}

	private void importPrevisaoSp(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
		// TODO Auto-generated method stub
		String acao = request.getParameter("txtacao");
		
		String mensagem = "";
		      
		FileInputStream arquivo = new FileInputStream(importArquivo(request, response));
		
		ArrayList<Demanda> itens = new ArrayList<>();
        
        BufferedReader br = null;
		
		br = new BufferedReader(new InputStreamReader(arquivo));
		String linha = null;
		int count = 0;		
		while((linha = br.readLine()) != null){			
			String[] ln = linha.split(";");
			
				if(count==0){
					count++;
					continue;
				}else{					
					Demanda t = new Demanda();
					Produto p = new Produto();
					ItemDemanda item = new ItemDemanda();
						t.setAno(ln[0]);
						p.setCodigo(ln[1]);
						String num = ln[2];
						t.setQtdprevsp(parseStringToInt(num));
						item.setItem(p);
						t.setItemdemanda(item);
					itens.add(t);
				}				
				count++;
		}
		
		DAODemanda dao = new DAODemanda();
		int contador = 0;
		contador = dao.importPrevisaoSP(itens, acao);
		
        arquivo.close();
        
        
        
        if(contador>0){
        	if(acao.equals("insert"))
        		mensagem = contador+" itens salvos com sucesso!";
        	else
        		mensagem = contador+" itens atualizados com sucesso!";
        }else{
        	mensagem = "Erro ao importar o arquivo!";
        }
        
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
		response.getWriter().write(jsonObject);
		
	}

	private void sendMailOrcam(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		Orcamento orcam = (Orcamento) session.getAttribute("orcamento");
		String nome = request.getParameter("txtnome");
		String fone = request.getParameter("txtfone");
		String email = request.getParameter("txtemail");
		JavaMailApp2 mail = new JavaMailApp2();
		String mensagem = mail.sendMail(orcam, nome, fone, email);

		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);										
	}


	private void sendMailPedidoOrcam(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		
		Orcamento orcam = null;
		
		orcam = (Orcamento) session.getAttribute("orcamento");

		Usuario usuario = (Usuario) session.getAttribute("usuariologado");
		
		//String realPath = getServletContext().getRealPath("");
		
		String codigoftd = request.getParameter("txtidempresa");
		String formapgto = request.getParameter("txtfonecontato");
		String transportadora = request.getParameter("txttransportadora");
		String obs = request.getParameter("txtobs");		
		
		PedCliente pedido = new PedCliente();
		
		Empresa cliente = new Empresa();
		
		if(usuario.getCargo() == 4) {
			cliente.setCodigoftd(usuario.getCodigoftdempresa());
		}else {
			cliente.setCodigoftd(codigoftd);
		}
		
		DAOEmpresa.getDadosEmpresa(cliente);

		
		if(usuario.getCargo() == 4) {
			cliente.setRazaosocial(usuario.getNome());
			cliente.setCnpj(usuario.getCnpj());
		}
		
		pedido.setCliente(cliente);
		pedido.setUsuario(usuario);
		
		boolean flag = false;
		
		String mensagem = "Orcamento sem itens! Favor, selecione os itens e quantidades para envio!!!";
		
		if(orcam != null) {
			
			for(ItemOrcamento i : orcam.getItens()){
				ItemPedCliente ip = new ItemPedCliente();
				DAOProduto.setPrevisaoProduto(i.getProduto());
				ip.setItem(i.getProduto());
				ip.setQtdpedida(i.getQuantidade());
				ip.refazPendente();
				ip.getItem().refazNivelEstoquePedCliente(ip.getQtdpendente());
				int index = pedido.getItens().indexOf(ip);
				if(index >= 0){
					ip = pedido.getItens().get(index);
					int qtde = ip.getQtdpedida() + i.getQuantidade();
					ip.setQtdpedida(qtde);
					ip.refazPendente();
					flag = true;
				}else{
					pedido.getItens().add(ip);
					flag = false;
				}
				if(pedido.getIdpedido() > 0){
					DAOPedCliente.adicionaItem(ip, pedido.getIdpedido(), flag);
				}
			}
			
			pedido.refazTotal();
			
			Collections.sort(pedido.getItens());
			
			pedido.setUser_id(usuario.getId());
					
			pedido.refazSituacao("0");
			
		    System.out.println(":::::::::::::::::: IMPLANTANDO PEDIDO :::::::::::::::::::::::::::");
		    System.out.println(":::::::::::::::::: Usuário logado: "+usuario.getNome());
		    System.out.println(":::::::::::::::::: Cliente: ["+pedido.getCliente().getCodigoftd()+"] "
		    		+ ""+pedido.getCliente().getRazaosocial());
		    DAOPedCliente.salvar(pedido);
		    System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	
			JavaMailApp2 mail = new JavaMailApp2();
			
			mensagem = mail.sendMailPedido(pedido, formapgto, transportadora, obs, usuario);
			
		}
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);						
	}
	
	
	private void bonusPesquisar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		int idescola = Integer.parseInt(request.getParameter("txtidescola"));
		String year = request.getParameter("txtyear");
		List<Float> lista = DAOEscola.pesquisarBonus(idescola, year);
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		Type listType = new TypeToken<ArrayList<Float>>(){}.getType();
		
		String jsonObject = gson.toJson(lista, listType);		
				
		response.getWriter().write(jsonObject);		
	}

	private void bonusDetalhar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		String acao = request.getParameter("action");
		int idescola = Integer.parseInt(request.getParameter("txtidescola"));
		String ano = request.getParameter("txtano");
		float desconto = Float.parseFloat(request.getParameter("txtdesconto"));
		Bonus bonus = new Bonus();
		Escola escola = new Escola();
		escola.setId(idescola);
		bonus.setEscola(escola);
		bonus.setAno(ano);
		bonus.setDesconto(desconto);
		DAOEscola.recarregaBonus(bonus);
		bonus.setContext(request.getServletContext());
		bonus.geraQRCode();
		session.setAttribute("bonus", bonus);
		if(acao.equalsIgnoreCase("imprimir")){
			response.sendRedirect("bonusImprimir.jsp");
		}else{
			response.sendRedirect("bonusEditar.jsp");
		}
		
	}

	private void bonusRegistrar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}


		int idescola = Integer.parseInt(request.getParameter("txtidescola"));
		Escola escola = new Escola();
		escola.setId(idescola);
		DAOEscola.recarrega(escola);
		escola.setVendedor(DAOUsuario.getVendedor(escola.getSetor()));
		String ano = request.getParameter("txtyear");
		float desconto = parseStringToFloat(request.getParameter("txtdesconto"));
		String descricao = request.getParameter("txtdescricao");
		String tipo = request.getParameter("txttipo");
		Bonus bonus = new Bonus();
		bonus.setEscola(escola);
		bonus.setAno(ano);
		bonus.setDesconto(desconto);
		bonus.setDescricao(descricao);
		bonus.setTipo(tipo);
		boolean flag = DAOEscola.bonusRegistrar(bonus);
		bonus.setContext(request.getServletContext());
		bonus.geraQRCode();
		if(!flag){
			session.setAttribute("bonus", bonus);
			response.sendRedirect("bonusImprimir.jsp");
		}else{
			String mensagem = "O bonus já existe ou ocorreu um erro!";
			session.setAttribute("message", mensagem);
			response.sendRedirect("result.jsp");
		}
	}

	private void verListas(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		GlassView glass = (GlassView) session.getAttribute("glass");

		List<Escola> lista = DAOEscola.getGlassList(glass);
		session.setAttribute("glasslista", lista);
		response.sendRedirect("glasslista.jsp");
	}

	private void getGlassView(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}


		int setor = Integer.parseInt(request.getParameter("txtsetor"));
		String dependencia = request.getParameter("txtdependencia");
		String year = request.getParameter("txtyear");
		GlassView glass = new GlassView();
		if(setor > 0){
			glass.setConsultor(DAOUsuario.getVendedor(setor));
		}else{
			glass.getConsultor().setSetor(setor);
		}
		glass.setDependencia(dependencia);
		glass.setYear(year);
		DAOQuality.recupera(glass);
		session.setAttribute("glass", glass);
		//response.sendRedirect("glassview.jsp");
	}

	private void editarMarker(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}


		int idescola = Integer.parseInt(request.getParameter("txtid"));
		double lon = Double.parseDouble(request.getParameter("txtlon"));
		double lat = Double.parseDouble(request.getParameter("txtlat"));
		Escola escola = new Escola();
		escola.setId(idescola);
		escola.setLon(lon);
		escola.setLat(lat);
		DAOEscola.setLonLat(escola);
		DAOEscola.recarrega(escola);
		session.setAttribute("mapdata", escola);
		response.sendRedirect("escolaMapa.jsp");
		/*String mensagem = "Localiza��o atualizada!";
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);*/										
	}

	private void viewMap(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}


		int idescola = Integer.parseInt(request.getParameter("idescola"));
		Escola escola = new Escola();
		escola.setId(idescola);
		DAOEscola.recarrega(escola);
		
		session.setAttribute("mapdata", escola);
		response.sendRedirect("escolaMapa.jsp");
	}

	private void pendenciasDoProduto(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		
		String codigo = request.getParameter("txtcodigo");
		String dataini = request.getParameter("txtinicio");
		Date inicio = convertStringToDate(dataini);
		String datafim = request.getParameter("txtfinal");
		Date fim = convertStringToDate(datafim);
		
		List<ItemPedCliente> lista = DAOProduto.getPendenciasDoProduto(inicio, fim, codigo);
		

		session.setAttribute("pendencias", lista);
	}

	@SuppressWarnings("static-access")
	private void getCrawlerWeb() throws InterruptedException, ServletException, IOException{
		
		HTMLJsoup soup = null;
		List<Carrousel> ls = null;
		List<Abrelivros> lsab = null;
		//List<MEC> lsmc = null;
		
		Calendar c = Calendar.getInstance();

		java.util.Date fimData = null;
		
		fimData = c.getInstance().getTime();
			if(iniData == null){
				soup = new HTMLJsoup();
				ls = soup.getCarrouselFTD("https://ftd.com.br/index.php");
				Thread.sleep(500);
				lsab = soup.getNoticiasAbrelivros("https://abrelivros.org.br/site/");
				Thread.sleep(500);
				//lsmc = soup.getCarrouselMEC("http://portal.mec.gov.br/index.php");
				//Thread.sleep(500);
				getServletContext().setAttribute("carrousel", ls);		
				getServletContext().setAttribute("abrelivros", lsab);
				//getServletContext().setAttribute("mec", lsmc);
				lancamentos();
				c.add(Calendar.HOUR_OF_DAY, 12);
				iniData = c.getTime();
			}else if(fimData.after(iniData)){
				soup = new HTMLJsoup();
				ls = soup.getCarrouselFTD("https://ftd.com.br/index.php");
				Thread.sleep(500);
				lsab = soup.getNoticiasAbrelivros("https://abrelivros.org.br/site/");
				Thread.sleep(500);
				//lsmc = soup.getCarrouselMEC("http://portal.mec.gov.br");
				//Thread.sleep(500);
				getServletContext().setAttribute("carrousel", ls);		
				getServletContext().setAttribute("abrelivros", lsab);
				//getServletContext().setAttribute("mec", lsmc);
				lancamentos();
				c.add(Calendar.HOUR_OF_DAY, 12);
				iniData = c.getTime();
			}

	}
	
	
	private void prepareDoacao(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		Orcamento orcam = (Orcamento) session.getAttribute("orcamento");
		List<ItemOrcamento> itens = new ArrayList<>();
		ItemOrcamento ant = null;
		int quant = 0;
		for(ItemOrcamento i : orcam.getItens()){			
			if(ant != null){
				if(i.getProduto().getCodigo().equals(ant.getProduto().getCodigo())){
					quant += i.getQuantidade();
					
				}else{
					ant.setQuantidade(quant);
					itens.add(ant);
					quant = i.getQuantidade();
					ant = i;
				}
			}else{
				ant = i;
				quant = i.getQuantidade();
			}
		}
		ant.setQuantidade(quant);
		itens.add(ant);
		
		orcam.setItens(itens);
		
		Collections.sort(orcam.getItens());
		
		session.setAttribute("orcamento", orcam);

	}

	private void loadCodCliente(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String codigo = DAOUtils.loadCodCliente();
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(codigo);		
				
		response.getWriter().write(jsonObject);								
	}

	private void cadastrarCliente(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		// TODO Auto-generated method stub
		String a1cod, a1nome, a1nreduz, a1pessoa, 
		a1cgc, a1vend, a1grptrib, a1end, a1bairro, 
		a1est, a1mun, a1codmun, a1cep, a1email, a1lc, a1venclc;
		
		a1cod = request.getParameter("a1cod");
		a1nome = request.getParameter("a1nome");
		a1nreduz = request.getParameter("a1nreduz");
		a1pessoa = request.getParameter("a1pessoa");
		a1cgc = request.getParameter("a1cgc");
		a1vend = request.getParameter("a1vend");
		a1grptrib = request.getParameter("a1grptrib");
		a1end = request.getParameter("a1end");
		a1bairro = request.getParameter("a1bairro");
		a1est = request.getParameter("a1est");
		a1mun = request.getParameter("a1mun");
		a1codmun = request.getParameter("a1codmun");
		a1cep = request.getParameter("a1cep");
		a1email = request.getParameter("a1email");
		a1lc = request.getParameter("a1lc");
		a1venclc = request.getParameter("a1venclc");
		
		Connection con = ConnectionFactory.getInstance().getSqlConnection();
		
		String mensagem = "erro";
		
		if(con != null){
			String sql = "INSERT INTO "+TotvsDb.SA1.getTable(ControlServlet.getParams().getGpoemptotvs())+" "
					+ "(A1_COD, A1_LOJA, A1_NOME, A1_PESSOA, A1_NREDUZ, A1_TIPO, A1_END, "
					+ "A1_EST, A1_NATUREZ, A1_MUN, A1_COD_MUN, A1_BAIRRO, A1_CEP, A1_CGC, "
					+ "A1_INSCR, A1_EMAIL, A1_VEND, A1_TPFRET, A1_GRPTRIB, A1_TRANSP, "
					+ "A1_COND, A1_RISCO, A1_LC, A1_VENCLC, A1_CLASSE) VALUES "
					+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, a1cod);
			ps.setString(2, "01");
			ps.setString(3, a1nome);
			ps.setString(4, a1pessoa);
			ps.setString(5, a1nreduz);
			ps.setString(6, "F");
			ps.setString(7, a1end);
			ps.setString(8, a1est);
			ps.setString(9, "VENDA");
			ps.setString(10, a1mun);
			ps.setString(11, a1codmun);
			ps.setString(12, a1bairro);
			ps.setString(13, a1cep);
			ps.setString(14, a1cgc);
			ps.setString(15, "ISENTO");
			ps.setString(16, a1email);
			ps.setString(17, a1vend);
			ps.setString(18, "F");
			ps.setString(19, a1grptrib);
			ps.setString(20, "002");
			ps.setString(21, "002");
			ps.setString(22, "A");
			ps.setString(23, a1lc);
			ps.setString(24, a1venclc);
			ps.setString(25, "A");
			
			ps.execute();
			ps.close();
			con.close();
			
			mensagem = "ok";			
		}

		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);						

	}

	private void loadCodMun(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String uf = request.getParameter("txtuf");
		String mun = request.getParameter("txtmun");
		String codigo = DAOUtils.getCodMun(uf, mun);

		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(codigo);		
				
		response.getWriter().write(jsonObject);						
	}

	private void deletarDoacao(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		@SuppressWarnings("unchecked")
		List<Doacao> lista = (List<Doacao>) session.getAttribute("listadoacao");
		int iddoacao = Integer.parseInt(request.getParameter("txtiddoacao"));
		Doacao doacao = null;
		
		for(Doacao d : lista){	
			if(d.getId() == iddoacao){
				doacao = d;
				lista.remove(d);
				break;
			}
		}
		
		DAODoacao dao = new DAODoacao();
		
		String mensagem = dao.deletar(doacao);
		
		session.setAttribute("listadoacao", lista);
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);						
		
	}

	private void atualizarPrevisoes(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		Pedido pedido = (Pedido) session.getAttribute("detalhepedido");
				
		for(ItemPedido i : pedido.getItens()){
			String previsao = request.getParameter(i.getItem().getCodigo()+"prev");
			Date prev = convertStringToDatePrev(previsao);
			i.setPrevisao(prev);
		}
		
		DAOPedido dao = new DAOPedido();
				
		String mensagem = dao.alterarPrevisoes(pedido);
		
		session.setAttribute("detalhepedido", pedido);
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);						
	}

	private void detalharKardex(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		String filial = request.getParameter("txtfilial");
		String idorcam = request.getParameter("txtidorcam");
		String idcliente = request.getParameter("txtidcliente");
		String nomecli = request.getParameter("txtnome");
		
		Orcamento orcamento = DAOProduto.getDetalheKardex(filial, idorcam, idcliente, nomecli);		
				
		session.setAttribute("orcamento", orcamento);		
	}

	private void consultarKardex(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		
		String codigo = request.getParameter("txtcodigo");
		String ano = request.getParameter("txtano");
		String filial = request.getParameter("txtfilial");
		List<Kardex> kardex = new ArrayList<>();
		kardex = DAOProduto.getKardex(codigo, ano, filial);
		
		session.setAttribute("kardex", kardex);		
	}

	private void vendasPorProdutos(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String filial = request.getParameter("txtfilial");
		String cliente = request.getParameter("txtcliente");
		String dataini = request.getParameter("dataini");
		String datafim = request.getParameter("datafim");
		String serieini = request.getParameter("txtserieini");
		String seriefim = request.getParameter("txtseriefim");
		String[] grpcli = request.getParameterValues("cmbgrpcli");
		Date inicio = convertStringToDate(dataini);
		Date fim = convertStringToDate(datafim);
		String TESVenda = request.getParameter("tesvd");
		String TESDevolucao = request.getParameter("tesdv");
		DAOVenda dao = new DAOVenda();
		dao.getVendasProduto(filial, cliente, inicio, fim, grpcli, TESVenda, TESDevolucao, serieini, seriefim, response);
	}

	private void deletarNotaFiscal(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		String idnota = request.getParameter("txtidnota");

		@SuppressWarnings("unchecked")
		List<NotaFiscal> lista = (List<NotaFiscal>) session.getAttribute("notasfiscais");
		NotaFiscal nota = null;
		for(NotaFiscal n : lista){
			if(n.getIdnota().equals(idnota)){
				nota = n;
				lista.remove(n);
				break;
			}
		}
		String mensagem = "Nota Fiscal "+nota.getIdnota()+" deletada com sucesso!!";
		DAONotaFiscal dao = new DAONotaFiscal();
		if(!dao.remover(nota)){
			mensagem = "Erro ao tentar remover a nota Nota Fiscal "+nota.getIdnota();
		}
		session.setAttribute("notasfiscais", lista);
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);				
		
	}

	private void deletarPedidoFornecedor(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		@SuppressWarnings("unchecked")
		List<Pedido> lista = (List<Pedido>) session.getAttribute("listapedidos");
		int idpedido = Integer.parseInt(request.getParameter("txtidpedido"));
		Pedido pedido = null;
		for(Pedido p : lista){
			if(p.getIdpedido() == idpedido){
				pedido = p;
				lista.remove(p);
				break;
			}
		}
		DAOPedido dao = new DAOPedido();
		String mensagem = dao.deletar(pedido);
		
		session.setAttribute("listapedidos", lista);
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);				
	}

	private void loadAdocaoTermometro(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		int setor = Integer.parseInt(request.getParameter("txtsetor"));
		String tipo = request.getParameter("txttipo");
		ServletContext context = request.getServletContext();
		
		Usuario usuario;
		if(setor == 0){
			usuario = (Usuario) session.getAttribute("usuariologado");
			usuario.setSetor(setor);
		}else{
			usuario = DAOUsuario.getVendedor(setor);
			usuario.setSetor(setor);
		}
		String ano = request.getParameter("txtano");
		if(tipo.equals("analitico")){
	    	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
	    	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.setContentType("application/vnd.ms-excel");
	    	response.setHeader("Content-Disposition", "attachment; filename=termometroanalitico.xls");
	    	ResultSetToExcel rsx = DAOEscola.relAdocaoTermometroAnalitico(ano, usuario);
	    	rsx.generate(response);
			
		}else if(tipo.equals("monitorftd")){
	    	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
	    	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.setContentType("application/vnd.ms-excel");
	    	response.setHeader("Content-Disposition", "attachment; filename="+beginArq+"-MONITORAMENTO_"+ano+".xls");

	    	Workbook workbook = DAOEscola.monitorFTD(beginArq, ano, context, usuario);
	    	workbook.write(response.getOutputStream());
		}else if(tipo.equals("faturamento")){
	    	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
	    	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.setContentType("application/vnd.ms-excel");
	    	response.setHeader("Content-Disposition", "attachment; filename=adocoes_por_faturamento-"+ano+".xls");

	    	ResultSetToExcel rsx = DAOEscola.relAdocaoFaturamento(ano, usuario);
			rsx.generate(response);
		}else{
	    	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
	    	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.setContentType("application/vnd.ms-excel");
	    	response.setHeader("Content-Disposition", "attachment; filename=termometro.xls");			
	    	ResultSetToExcel rsx = DAOEscola.relAdocaoTermometro(ano, usuario);
			rsx.generate(response);
		}
	}

	private void listarDoacoesToExcel(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		@SuppressWarnings("unchecked")
		List<Doacao> lista = (List<Doacao>) session.getAttribute("listadoacao");
		Collections.sort(lista);
		List<String> cb = new ArrayList<>();
		cb.add("ID");
		cb.add("EMISSÃO");
		cb.add("PROFESSOR");
		cb.add("QTDE");
		cb.add("TOTAL");
		cb.add("ESCOLA");
		cb.add("VENDEDOR");
		ResponseToExcel.getExcelListagemDoacoes(cb, lista, response, "doacoes");		
	}

	private void marcarItensDoacaoAcertados(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String vendedores = request.getParameter("txtvendedor");
		String[] params = vendedores.split(";");
		String mensagem = "Houve um erro ao tentar marcar os itens!!!";
		boolean flag = DAODoacao.marcarItensAcertados(params);
		if(!flag){
			mensagem = "Itens marcados com sucesso!!!";
		}
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);		
	}

	private void geraDoacoesToExcel(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		List<ItemOrcamento> lista = new ArrayList<>();
		DAODoacao dao = new DAODoacao();
		String vendedores = request.getParameter("txtvendedor");
		String[] params = vendedores.split(";");
		dao.gerarRelatorioAcerto(lista, params);
		List<String> cb = new ArrayList<>();
		cb.add("CODIGO");
		cb.add("DESCRICAO");
		cb.add("QTDE");
		ResponseToExcel.getExcelDoacoes(cb, lista, response, "doacoes");				
	}

	private void loadYearSelection(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("txtaction");
		List<String> lista = DAOUtils.yearSelection(action);
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		Type listType = new TypeToken<ArrayList<String>>(){}.getType();
		
		String jsonObject = gson.toJson(lista, listType);		
				
		response.getWriter().write(jsonObject);
	}

	@SuppressWarnings("unchecked")
	private void listarEscolasExcel(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		List<Escola> lista = (List<Escola>) session.getAttribute("listaescolas");
		List<String> cb = new ArrayList<>();
		cb.add("ID");
		cb.add("FTD");
		cb.add("NOME");
		cb.add("ENDERECO");
		cb.add("BAIRRO");
		cb.add("MUNICIPIO");
		cb.add("UF");
		cb.add("CEP");
		cb.add("E-MAIL");
		cb.add("TELEFONE");
		cb.add("CNPJ");
		cb.add("NR.ALUNOS");
		cb.add("SETOR");
		cb.add("VENDEDOR");
		ResponseToExcel.getExcelEscolas(cb, lista, response, "escolas");
	}

	private void alterarSenhaUsuario(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("txtid"));
		String senha = request.getParameter("txtsenha");

		Usuario u = new Usuario();
		u.setId(id);
		u.setSenha(senha);
		DAOUsuario dao = new DAOUsuario();
		String mensagem = dao.alterarSenha(u);

		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);		
		
	}

	private void deletarUsuario(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("txtid"));
		Usuario u = new Usuario();
		u.setId(id);
		
		DAOUsuario dao = new DAOUsuario();
		String mensagem = dao.deletar(u);

		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);		
	}

	private void carregarTodosUsuarios(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		Usuario u = (Usuario) session.getAttribute("usuariologado");
		List<Usuario> lista = new ArrayList<>();
		if(u.getCargo() == 1){
			 lista = DAOUsuario.listarTodos();
		}else{
			lista.add(u);
		}

		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		Type listType = new TypeToken<ArrayList<Usuario>>(){}.getType();
		
		String jsonObject = gson.toJson(lista, listType);		
				
		response.getWriter().write(jsonObject);
	}

	private void roteiroResumo(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		
		int setor = Integer.parseInt(request.getParameter("txtsetor"));
		String dependencia = request.getParameter("cmbdependencia");
		Date ini = convertStringToDate(request.getParameter("txtdataini"));
		Date fim = convertStringToDate(request.getParameter("txtdatafim"));
		
		RoteiroResumo resumo = new RoteiroResumo();
		resumo.setInicio(ini);
		resumo.setFim(fim);
		Usuario usuario = new Usuario();
		usuario = DAOUsuario.getVendedor(setor);
		resumo.setUsuario(usuario);
		DAOEscola.roteiroResumos(resumo, dependencia);
		
		session.setAttribute("roteiroresumo", resumo);
	}

	private void pedClientePendente(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException, SQLException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		String id = null;
		id = request.getParameter("idpedido");
		PedCliente pedido = null;
		List<ItemPedCliente> lista = null;
		
		if(id == null){
			pedido = (PedCliente) session.getAttribute("pedcliente");
			lista = new ArrayList<>();
	
			for(ItemPedCliente i : pedido.getItens()){
				if(i.getQtdpendente() > 0){
					i.setQtdpedida(i.getQtdpendente());
					i.setQtdatendida(0);
					i.refazPendente();
					lista.add(i);
				}
			}
			pedido.setItens(lista);
			pedido.refazTotal();		
		}else{
			int idpedido = Integer.parseInt(id);
			pedido = new PedCliente();
			lista = new ArrayList<>();
			
			pedido.setIdpedido(idpedido);
			DAOPedCliente.recarrega(pedido);
			Collections.sort(pedido.getItens());
			for(ItemPedCliente i : pedido.getItens()){
				if(i.getQtdpendente() > 0){
					i.setQtdpedida(i.getQtdpendente());
					i.setQtdatendida(0);
					i.refazPendente();
					lista.add(i);
				}
			}
			pedido.setItens(lista);
			pedido.refazTotal();					
		}
		session.setAttribute("pedcliente", pedido);
		response.sendRedirect("pedClientePendente.jsp");
	}

	private void deletarSerieAdocao(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}


		Orcamento orcamento = (Orcamento) session.getAttribute("orcamento");
		System.out.println("IdAdocao =>>>> "+orcamento.getIdadocao());
		String mensagem = "";
		
		if(DAOEscola.deletarSerieAdocao(orcamento)){
			mensagem = "Série/Ano: "+orcamento.getSerie()+" / Ano: "+orcamento.getAno()+" "
					+ "deletada com sucesso!";
			session.removeAttribute("orcamento");
		}else{
			mensagem = "Houve um erro ao tentar deletar!";
		}
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);

	}

	private void aplicarDesconto(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		PedCliente pedido = null;
		pedido = (PedCliente) session.getAttribute("pedcliente");
		String mensagem = "";
		float taxa = Float.parseFloat(request.getParameter("txttaxa"));
		
		if(pedido != null){
			pedido.setDesconto(taxa);
			pedido.refazTotal();
			session.setAttribute("pedcliente", pedido);
			mensagem = "pedcliente";
		}else{
			Orcamento orcamento = (Orcamento) session.getAttribute("orcamento");
			orcamento.setDesconto(taxa);
			orcamento.refazTotal();
			session.setAttribute("orcamento", orcamento);
		}
		Gson gson = new Gson();			
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");			
		String jsonObject = gson.toJson(mensagem);							
		response.getWriter().write(jsonObject);					
	}

	private void listarMunicipios(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		int setor = Integer.parseInt(request.getParameter("txtsetor"));
		String dependencia = request.getParameter("txtdependencia");
		List<String> lista = null;
		
		Usuario usuario = (Usuario) session.getAttribute("usuariologado");
		if(usuario.getCargo() == 3){
			lista = DAOEscola.municipios(usuario, dependencia);
		}else{
			if(setor > 0)
				lista = DAOEscola.municipios(DAOUsuario.getVendedor(setor), dependencia);
			else
				lista = DAOEscola.municipios(usuario, dependencia);
		}
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		//Type listType = new TypeToken<ArrayList<String>>(){}.getType();
		
		String jsonObject = gson.toJson(lista);		
				
		response.getWriter().write(jsonObject);
	}

	
	private void listarBairros(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		int setor = Integer.parseInt(request.getParameter("txtsetor"));
		String municipio = request.getParameter("txtmunicipio");
		String dependencia = request.getParameter("txtdependencia");
		
		List<String> lista = null;
		
		Usuario usuario = (Usuario) session.getAttribute("usuariologado");
		if(usuario.getCargo() == 3){
			lista = DAOEscola.bairros(usuario, dependencia, municipio);
		}else{
			if(setor > 0)
				lista = DAOEscola.bairros(DAOUsuario.getVendedor(setor), dependencia, municipio);
			else
				lista = DAOEscola.bairros(usuario, dependencia, municipio);
		}
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		//Type listType = new TypeToken<ArrayList<String>>(){}.getType();
		
		String jsonObject = gson.toJson(lista);		
				
		response.getWriter().write(jsonObject);
	}
	
	private void dadosProfessor(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		int idprofessor = Integer.parseInt(request.getParameter("txtidprofessor"));
		Professor professor = new Professor();
		professor.setId(idprofessor);
		DAOProfessor.recarrega(professor);

		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(professor);		
				
		response.getWriter().write(jsonObject);
	}

	private void listarProfessores(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		List<Professor> lista = null;
					
		int idescola = Integer.parseInt(request.getParameter("txtidescola"));		
		
		Escola e = new Escola();
		e.setId(idescola);

		lista = DAOProfessor.listar(e);
		session.setAttribute("professores", lista);			
	}

	
	private void listarTodosProfessores(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("usuariologado");
		List<Professor> lista = null;
		List<ProfessorMin> listamin = new ArrayList<>();
		String nome = request.getParameter("txtnome");
		lista = DAOProfessor.listar(usuario, nome);
		
		for(Professor p : lista) {
			ProfessorMin m = new ProfessorMin();
			m.setId(p.getId());
			m.setText(p.getNome());
			listamin.add(m);
		}
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		Type listType = new TypeToken<ArrayList<ProfessorMin>>(){}.getType();
		
		String jsonObject = gson.toJson(listamin, listType);		
				
		response.getWriter().write(jsonObject);			
	}

	private void downloadTemplateCadastroProduto(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("application/vnd.ms-excel");
    	response.setHeader("Content-Disposition", "attachment; filename=modelo.xls");
    	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
    	
    	ServletContext context = request.getServletContext();
    	
    	String realPath = context.getRealPath("/");
    	    	
		String templatePath = realPath + "/resources/xls/Template_importa_cadastro_produto.xlsx";
		
		FileInputStream in = new FileInputStream(templatePath);
		
		OutputStream out = response.getOutputStream();
		
		byte[] buffer = new byte[4096];
		int length;
		while ((length = in.read(buffer)) > 0){
		    out.write(buffer, 0, length);
		}
		in.close();
		out.flush();		
	}

	private void exportaConsultaExcel(HttpServletRequest request,
			HttpServletResponse response) throws IOException, RuntimeException, Exception {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		@SuppressWarnings("unchecked")
		List<Produto> lista = (List<Produto>) session.getAttribute("tabela_servlet");
		Usuario usuario = (Usuario) session.getAttribute("usuariologado");
		
		response.setContentType("application/vnd.ms-excel");
    	response.setHeader("Content-Disposition", "attachment; filename=produtos.xls");
    	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
    	
    	ServletContext context = request.getServletContext();
    	
    	String realPath = context.getRealPath("/");
    	    	
		String templatePath = realPath + "/resources/xls/tabelaTemplate.xls";
				
		if(usuario != null){
				templatePath = realPath + "/resources/xls/tabelaTemplateAdmin.xls";
		}
				
		
		InputStream is = new FileInputStream(templatePath);
		
		List<ItemTabela> tabela = new ArrayList<>();
		
		for (Produto p : lista){
			ItemTabela i = new ItemTabela();
			i.setItem(p);
			tabela.add(i);
		}
		
		Map<String, Object> beans = new HashMap<>();
		beans.put("itemtab", tabela);
		
		XLSTransformer transformer = new XLSTransformer();
				
		Workbook workbook = transformer.transformXLS(is, beans);
		
		workbook.write(response.getOutputStream());						

	}

	private void consultarDoacoes(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		Usuario user = (Usuario) session.getAttribute("usuariologado");
		
		String codigo = request.getParameter("txtcodigo");
		String ano = request.getParameter("txtano");
		Produto produto = new Produto();
		produto.setCodigo(codigo);
		DAOProduto.recarrega(produto);
		
		List<DoacaoRelat> lista = DAOProduto.consultarDoacoes(produto, ano, user);
		
		session.setAttribute("doacaoProd", lista);

		//ControlReport.exportDoacoesToPDF(getServletContext(), response, lista);
		
	}

	private void roteiroDeletar(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		int idroteiro = Integer.parseInt(request.getParameter("txtidroteiro"));
		
		DAOEscola dao = new DAOEscola();
		
		String mensagem = dao.deletaRoteiro(idroteiro);
		
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		
		@SuppressWarnings("unchecked")
		List<Roteiro> lista = (List<Roteiro>) session.getAttribute("listarroteiros");
		
		for(Roteiro r : lista){
			if(r.getId()==idroteiro){
				lista.remove(r);
				break;
			}
		}
		
		request.setAttribute("listarroteiros", lista);
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);										
	}

	@SuppressWarnings("unchecked")
	private void deletePedCliente(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException {
		// TODO Auto-generated method stub
		
		int idpedido = Integer.parseInt(request.getParameter("txtidpedido"));
		
		String mensagem = DAOPedCliente.delete(idpedido);
		
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		session.removeAttribute("pedcliente");
		session.removeAttribute("historico");
		
		List<PedCliente> lista = (List<PedCliente>) session.getAttribute("listapedcliente");
		
		int i = 0;
		
		for(PedCliente p : lista){
			if(p.getIdpedido() == idpedido){
				lista.remove(i);
				break;
			}
			i++;			
		}
			
		session.setAttribute("listapedcliente", lista);
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);								
	}

	private void atendidosPedCliente(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		PedCliente pedido = (PedCliente) session.getAttribute("pedcliente");
		
		for(ItemPedCliente i : pedido.getItens()){
			int qtdatendida = 0;
			if(i.getItem().getEstoque() < i.getQtdpedida()){
				if(i.getItem().getEstoque()<0)
					qtdatendida = 0;
				else
					qtdatendida = i.getItem().getEstoque();
			}else{
				qtdatendida = i.getQtdpedida();
			}
			
			i.setQtdatendida(qtdatendida);
			i.setFlag(true);
			i.refazPendente();
		}
		
		//pedido.refazSituacao();
		
		session.setAttribute("pedcliente", pedido);
		
		String mensagem = "Pedido atualizado!";
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);						
	}

	private void downloadPedClienteCSV(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		ServletOutputStream fout = null;
		fout = response.getOutputStream();
    	
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

    	PedCliente pedido = (PedCliente) session.getAttribute("pedcliente");

		String nomeArquivo = "Pedido-"+pedido.getIdpedido()+".csv";
		
		response.setContentType("text/csv;vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename="+nomeArquivo);
		response.setHeader("Set-Cookie", "fileDownload=true; path=/");
		
        byte[] linha;
        String linhaarq = "";
        
        for(ItemPedCliente i : pedido.getItens()){
        	if(i.getQtdatendida()>0){
	            linhaarq = "";            
	            linhaarq = i.getItem().getCodigo()+";"+i.getQtdatendida()+";";
	            linhaarq = linhaarq + "\r\n";
	            linha = linhaarq.getBytes();
	            fout.write(linha);
        	}
        }
        fout.flush();
		fout.close();        
	}

	private void detalharPedCliente(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		int idpedido = Integer.parseInt(request.getParameter("idpedido"));
		PedCliente pedido = new PedCliente();
		pedido.setIdpedido(idpedido);
		DAOPedCliente.recarrega(pedido);
		List<Date> historico = DAOPedCliente.getDatasAtendimentoPedido(idpedido);
		Collections.sort(pedido.getItens());
		session.setAttribute("pedcliente", pedido);
		session.setAttribute("historico", historico);
		response.sendRedirect("pedClienteRegistrar.jsp");
	}


	private void pedidoUploadFile(HttpServletRequest request,
			HttpServletResponse response) throws FileNotFoundException, IOException, ServletException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		PedCliente pedido = (PedCliente) session.getAttribute("pedcliente");
		Map<String, Integer> map = new HashMap<>();
		
		FileInputStream arquivo = new FileInputStream(importArquivo(request, response));
		BufferedReader br = null;
		
		br = new BufferedReader(new InputStreamReader(arquivo));
		String linha = null;
		
		while((linha = br.readLine()) != null){
			String[] ln = null;
			ln = linha.split(";");
			String quant = ln[1].trim().replace(".", "");
			if(quant.indexOf(',')>-1){
				String[] numero = quant.split(",");
				quant = numero[0];
			}
			quant = quant.trim();			
			map.put(ln[0].trim(), Integer.parseInt(quant));
		}
		arquivo.close();
		
		for(ItemPedCliente i : pedido.getItens()){
			if(map.get(i.getItem().getCodigo())!=null){
				int atendido = map.get(i.getItem().getCodigo()) + i.getQtdatendida();				
				i.setQtdatendida(atendido);
				i.refazPendente();
			}
		}
		pedido.refazTotal();

		session.setAttribute("pedcliente", pedido);
		response.sendRedirect("pedClienteRegistrar.jsp");
		
	}

	
	private void importaOrcamento(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		
		FileInputStream arquivo = new FileInputStream(importArquivo(request, response));
		
        List<ItemOrcamento> itens = new ArrayList<>();
		BufferedReader br = null;
		
		br = new BufferedReader(new InputStreamReader(arquivo));
		String linha = null;
		
		while((linha = br.readLine()) != null){
			String[] ln = null;
			ln = linha.split(";");
			Produto t = new Produto();
			ItemOrcamento item = new ItemOrcamento();
			t.setCodigo(ln[0].trim());
			if(DAOProduto.recarrega(t)){
				item.setProduto(t);
				String quant = ln[1].trim().replace(".", "");
				if(quant.indexOf(',')>-1){
					String[] numero = quant.split(",");
					quant = numero[0];
				}
				quant = quant.trim();
				item.setQuantidade(Integer.parseInt(quant));
				itens.add(item);
			}else{
				continue;
			}
		}
		
        arquivo.close();
        
        DAOProduto.setEstoque(itens);
        Orcamento orcamento = (Orcamento) session.getAttribute("orcamento");
        if(orcamento == null) {
        	orcamento = new Orcamento();
            orcamento.setItens(itens);
        }else {
        	for (ItemOrcamento i : itens) {
        		orcamento.getItens().add(i);
        	}
        }
        
        orcamento.refazTotal();
        
        session.setAttribute("orcamento", orcamento);
        
        response.sendRedirect("orcamentoImprimir.jsp");        
	}

	private PedCliente atualizarPedCliente(HttpServletRequest request, HttpSession session) throws IOException {
		// TODO Auto-generated method stub

		PedCliente pedido = (PedCliente) session.getAttribute("pedcliente");
		
		for(ItemPedCliente i : pedido.getItens()){
			int quantidade = Integer.parseInt(request.getParameter(i.getItem().getCodigo()));
			int atendido = Integer.parseInt(request.getParameter(i.getItem().getCodigo()+"atd"));

			if(atendido > quantidade) {
				i.setQtdatendida(quantidade);
			}else {
				i.setQtdatendida(atendido);
			}
			i.setQtdpedida(quantidade);
			i.setFlag(true);
			i.refazPendente();
		}
		
		//pedido.refazSituacao();
		
		return pedido;		
	}

	private void downloadPedCliente(HttpServletRequest request,
			HttpServletResponse response) throws IOException, RuntimeException, Exception{
		// TODO Auto-generated method stub
		response.setContentType("application/vnd.ms-excel");
    	response.setHeader("Content-Disposition", "inline; filename=pedido_de_cliente.xls");
    	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
    	
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

    	PedCliente pedido = (PedCliente) session.getAttribute("pedcliente");
    	
    	String idempresa = request.getParameter("txtidempresa");
    	if(idempresa == null){
    		if(pedido.getCliente().getCodigoftd() != null)
    			idempresa = pedido.getCliente().getCodigoftd();
    	}    	
    	String razao = request.getParameter("txtdescricaoempresa");
    	if(razao == null){
    		if(pedido.getCliente().getRazaosocial() != null)
    			razao = pedido.getCliente().getRazaosocial();
    	}
    	ServletContext context = getServletContext();
    	
    	String realPath = context.getRealPath("/");
    	    	
		String templatePath = realPath + "/resources/xls/pedclienteTemplate.xls";		
		
		InputStream is = new FileInputStream(templatePath);		
				
		Map<String, Object> beans = new HashMap<>();
		beans.put("itens", pedido.getItens());
		beans.put("razaosocial", razao);
		beans.put("codigoftd", idempresa);
		
		XLSTransformer transformer = new XLSTransformer();
				
		Workbook workbook = transformer.transformXLS(is, beans);
		
		workbook.write(response.getOutputStream());						
	}

	private void listarPedCliente(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		Usuario usuario = (Usuario) session.getAttribute("usuariologado");

		String xpedido = null;
		
		xpedido = request.getParameter("txtpedido");
		if(!xpedido.equals("")){
			int idpedido = Integer.parseInt(xpedido);
			PedCliente pedido = new PedCliente();
			pedido.setIdpedido(idpedido);
			pedido.setUsuario(usuario);
			DAOPedCliente.recarrega(pedido);
			List<Date> atendimentos = DAOPedCliente.getDatasAtendimentoPedido(idpedido);
			session.setAttribute("pedcliente", pedido);
			session.setAttribute("historico", atendimentos);
			response.sendRedirect("pedClienteRegistrar.jsp");
		}else{			
			String codigoftd = request.getParameter("txtcliente");			
			Date ini = convertStringToDate(request.getParameter("dataini"));
			Date fim = convertStringToDate(request.getParameter("datafim"));
			System.out.println(codigoftd+" - "+ini+" - "+fim);
			List<PedCliente> lista = DAOPedCliente.listar(usuario, ini, fim, codigoftd);
			session.setAttribute("listapedcliente", lista);
			response.sendRedirect("pedClienteListagem.jsp");			
		}										
	}

	private void numeroAlunosEscola(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub

		int id = Integer.parseInt(request.getParameter("txtidescola"));
		Escola escola = new Escola();
		escola.setId(id);

		DAOEscola.recarrega(escola);
			
		DAOEscola.getQtdeAlunos(escola);
		escola.refazTotalAlunos();
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(escola);		
				
		response.getWriter().write(jsonObject);				
	}

	private void salvarPedCliente(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		//session.setMaxInactiveInterval(2*60*60);
				
		Usuario usuario = (Usuario) session.getAttribute("usuariologado");
				
		PedCliente pedido = atualizarPedCliente(request, session);
		String gpend = request.getParameter("guardapend");
		String semail = request.getParameter("emailcliente");
		String tiporecord = request.getParameter("tiporecord");
		if(semail.equalsIgnoreCase("true")) {
			pedido.setSendmail(true);
		}
		
		if(pedido.getCliente() == null && pedido.getUsuario() == null){
			
			String idempresa = request.getParameter("txtidempresa");
			Empresa cliente = new Empresa();
			cliente.setCodigoftd(idempresa);
			DAOEmpresa.getDadosEmpresa(cliente);
			
			pedido.setCliente(cliente);
			pedido.setUsuario(usuario);			
			pedido.setUser_id(usuario.getId());	
		}
		
		pedido.refazSituacao(tiporecord);
		
		String mensagem = "";
		String title = "implantado";
		String pretitle = "FTD-Pedido";
				
		if(pedido.getIdpedido() == 0){

			pedido.setUsuario(usuario);			
			pedido.setUser_id(usuario.getId());	

			for(ItemPedCliente i : pedido.getItens()){
				int quantidade = Integer.parseInt(request.getParameter(i.getItem().getCodigo()));
				int atendido = Integer.parseInt(request.getParameter(i.getItem().getCodigo()+"atd"));
				if(atendido > quantidade) {
					i.setQtdatendida(quantidade);
				}else {
					i.setQtdatendida(atendido);
				}
				i.setQtdpedida(quantidade);				
				i.refazPendente();
			}
									
		   System.out.println(":::::::::::::::::: IMPLANTANDO PEDIDO :::::::::::::::::::::::::::");
		   System.out.println(":::::::::::::::::: Cliente: ["+pedido.getCliente().getCodigoftd()+"] "
		    		+ ""+pedido.getCliente().getRazaosocial());		   
		   System.out.println(":::::::::::::::::: Usuário logado: "+usuario.getNome());		   
		   mensagem = DAOPedCliente.salvar(pedido);
		   System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		}
		else{
			
			for(ItemPedCliente i : pedido.getItens()){
				int quantidade = i.getQtdpedida();
				int atendido = Integer.parseInt(request.getParameter(i.getItem().getCodigo()+"atd"));

				if(atendido > quantidade) {
					i.setQtdatendida(quantidade);
				}else {
					i.setQtdatendida(atendido);
				}
				i.refazPendente();
			}
			if(gpend != null) {
				int guardarpendencia = Integer.parseInt(gpend);
				pedido.setGuardarpendencia(guardarpendencia);				
			}
			System.out.println(":::::::::::::::::: ATUALIZANDO PEDIDO ::::::::::::::::::::::::::::");
			System.out.println(":::::::::::::::::: Usuário logado: "+usuario.getNome());
			System.out.println(":::::::::::::::::: Pedido nº: "+pedido.getIdpedido());			
			mensagem = DAOPedCliente.atualizar(pedido);
			System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
			title = "atualizado";
		}
		
		JavaMailApp2 mail = new JavaMailApp2();
		
		mail.sendMailPedCliente(pedido, pretitle, title, usuario);
		
		session.removeAttribute("pedcliente");
		session.removeAttribute("historico");
		session.setAttribute("pedcliente", pedido);
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);		
	}

	
	private void pesquisarEmpresa(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		List<EmpresaMin> lista = new ArrayList<>();

		DAOEmpresa dao = new DAOEmpresa();
		
		String nome = request.getParameter("txtdescricao");

		lista = dao.listar(nome);
		
		Gson gson = new Gson();
		Type listType = new TypeToken<ArrayList<EmpresaMin>>(){}.getType();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(lista, listType);		
		response.getWriter().write(jsonObject);		
	}

	private void editarUsuario(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String id, nome, sobrenome, cargo, email, setor, empresa;
		
		id = request.getParameter("txtid");
		nome = request.getParameter("txtnome");
		sobrenome = request.getParameter("txtsobrenome");
		String nomeusuario = nome.replaceFirst("\\s++$", "") + " " + sobrenome;
		email = request.getParameter("txtemail");
		setor = request.getParameter("txtsetor");
		cargo = request.getParameter("txtcargo");
		empresa = request.getParameter("txtidempresa");
		
		Usuario usuario = new Usuario();
		usuario.setId(Integer.parseInt(id));
		usuario.setNome(nomeusuario);
		usuario.setEmail(email);
		usuario.setSetor(Integer.parseInt(setor));
		usuario.setCargo(Integer.parseInt(cargo));
		usuario.setIdempresa(Integer.parseInt(empresa));
		
		DAOUsuario dao = new DAOUsuario();
		String mensagem = dao.editar(usuario);

		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);		
	}

	private void getDadosEmpresa(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		Empresa empresa = new Empresa();
		String codigoftd = request.getParameter("txtcodigoftd");
		empresa.setCodigoftd(codigoftd);
		DAOEmpresa.getDadosEmpresa(empresa);
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(empresa);		
		response.getWriter().write(jsonObject);												
		
	}

	private void removeItemPedido(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		
		Usuario usuario = (Usuario) session.getAttribute("usuariologado");
		
		String codigo = request.getParameter("txtcodigo");
		
		PedCliente pedido = (PedCliente) session.getAttribute("pedcliente");
		
		for(ItemPedCliente i : pedido.getItens()){
			if(i.getItem().getCodigo().equals(codigo)){
				pedido.remove(i);
				if(pedido.getIdpedido()>0){
				   System.out.println(":::::::::::::::::: DELETANDO ITEM DO PEDIDO ::::::::::::::::::::::::");
				   System.out.println(":::::::::::::::::: Usuário logado: "+usuario.getNome());
				   System.out.println(":::::::::::::::::: Pedido nº: "+pedido.getIdpedido());
				   System.out.println(":::::::::::::::::: Código: "+codigo+" ::: QtPedida: "+i.getQtdpedida()
				   +" ::: QtAtendida: "+i.getQtdatendida());
				   DAOPedCliente.removerItem(i, pedido.getIdpedido());
				   System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
				}
				break;
			}
		}
	}

	private void descartarPedido(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		session.removeAttribute("pedcliente");
		session.removeAttribute("historico");
	}

	private void adicionarAoPedido(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		PedCliente pedido = null;
		pedido = (PedCliente) session.getAttribute("pedcliente");
		Orcamento orcam = (Orcamento) session.getAttribute("orcamento");
		Usuario usuario = (Usuario) session.getAttribute("usuariologado");
		String mensagem = "0";
		
		if(pedido == null){
			
			pedido = new PedCliente();
			
			if(usuario.getCargo()==4){
				Empresa cliente = new Empresa();
				cliente.setCodigoftd(usuario.getCodigoftdempresa());
				DAOEmpresa.getDadosEmpresa(cliente);
				pedido.setCliente(cliente);
				pedido.setUsuario(usuario);				
			}
			
		}
		
		for(ItemOrcamento i : orcam.getItens()){
			ItemPedCliente ip = new ItemPedCliente();
			DAOProduto.setPrevisaoProduto(i.getProduto());
			ip.setItem(i.getProduto());
			ip.setQtdpedida(i.getQuantidade());
			ip.refazPendente();
			ip.getItem().refazNivelEstoquePedCliente(ip.getQtdpendente());
			int index = pedido.getItens().indexOf(ip);
			boolean flag = false;
			if(index >= 0){
				ip = pedido.getItens().get(index);
				int qtde = ip.getQtdpedida() + i.getQuantidade();
				ip.setQtdpedida(qtde);
				ip.refazPendente();
				mensagem = "1";
				flag = true;
			}else{
				pedido.getItens().add(ip);
				flag = false;
				mensagem = "1";
			}
			if(pedido.getIdpedido() > 0){
			   System.out.println(":::::::::::::::::: ADICIONANDO ITEM AO PEDIDO ::::::::::::::::::::::::");
			   System.out.println(":::::::::::::::::: Usuário logado: "+usuario.getNome());
			   System.out.println(":::::::::::::::::: Pedido nº: "+pedido.getIdpedido());
			   System.out.println(":::::::::::::::::: Código: "+ip.getItem().getCodigo()+" ::: QtPedida: "+ip.getQtdpedida());
			   System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");	
				mensagem = DAOPedCliente.adicionaItem(ip, pedido.getIdpedido(), flag);
			}			
		}
		
		pedido.refazTotal();
		
		Collections.sort(pedido.getItens());
		
		session.removeAttribute("orcamento");
		
		session.setAttribute("pedcliente", pedido);
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
		response.getWriter().write(jsonObject);												
		
	}

	private void cadastrarProfessor(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String nome, escola, cargo, email, telefone, aniversario, endereco, bairro, municipio, uf;
		String[] nivel, disciplina;
		nome = request.getParameter("txtnome");
		cargo = request.getParameter("txtcargo");
		email = request.getParameter("txtemail");
		telefone = request.getParameter("txttelefone");
		aniversario = request.getParameter("txtaniversario");
		escola = request.getParameter("txtescola");
		nivel = request.getParameterValues("txtnivel");
		disciplina = request.getParameterValues("txtdisciplina");
		endereco = request.getParameter("txtendereco");
		bairro = request.getParameter("txtbairro");
		municipio = request.getParameter("txtmunicipio");
		uf = request.getParameter("txtuf");
		
		List<String> disciplinas = new ArrayList<>();
		List<String> niveis = new ArrayList<>();
		
		Escola e = new Escola();
		int id = Integer.parseInt(escola);
		e.setId(id);
		
		for(String s : nivel){
			niveis.add(s);
		}
		for(String s : disciplina){
			disciplinas.add(s);
		}
		
		Professor professor = new Professor();
		professor.setNome(nome);
		professor.setCargo(cargo);
		professor.setEmail(email);
		professor.setTelefone(telefone);
		professor.setAniversario(aniversario);
		professor.setEscola(e);
		professor.setNiveis(niveis);
		professor.setDisciplinas(disciplinas);
		professor.setEndereco(endereco);
		professor.setBairro(bairro);
		professor.setMunicipio(municipio);
		professor.setUf(uf);
		
		String mensagem = "";
		
		DAOProfessor dao = new DAOProfessor();
		if(dao.salvar(professor))
			mensagem = "Registro salvo com sucesso!";
		else
			mensagem = "Ocorreu um erro! Registro não foi salvo!";
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
		response.getWriter().write(jsonObject);	

	}

	@SuppressWarnings("unchecked")
	private void editarProfessor(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		List<Professor> lista = null;
		lista = (List<Professor>) session.getAttribute("professores");
				
		String escola, nome, cargo, email, telefone, aniversario, endereco, bairro, municipio, uf;
		String[] nivel, disciplina;
		int idprofessor = Integer.parseInt(request.getParameter("txtidprofessor"));
		
		nome = request.getParameter("txtnome");
		cargo = request.getParameter("txtcargo");
		email = request.getParameter("txtemail");
		telefone = request.getParameter("txttelefone");
		aniversario = request.getParameter("txtaniversario");
		escola = request.getParameter("txtescola");
		nivel = request.getParameterValues("txtnivel");
		disciplina = request.getParameterValues("txtdisciplina");
		endereco = request.getParameter("txtendereco");
		bairro = request.getParameter("txtbairro");
		municipio = request.getParameter("txtmunicipio");
		uf = request.getParameter("txtuf");
		
		List<String> disciplinas = new ArrayList<>();
		List<String> niveis = new ArrayList<>();
		
		Escola e = new Escola();
		
		if(escola != null){			
			int id = Integer.parseInt(escola);
			e.setId(id);		
		}

		if(nivel != null){
			for(String s : nivel){
				niveis.add(s);
			}
		}
		
		if(disciplina != null){
			for(String s : disciplina){
				disciplinas.add(s);
			}
		}
		
		Professor professor = new Professor();
		professor.setId(idprofessor);
		professor.setNome(nome);
		professor.setCargo(cargo);
		professor.setEmail(email);
		professor.setTelefone(telefone);
		professor.setAniversario(aniversario);
		professor.setEscola(e);
		professor.setNiveis(niveis);
		professor.setDisciplinas(disciplinas);
		professor.setEndereco(endereco);
		professor.setBairro(bairro);
		professor.setMunicipio(municipio);
		professor.setUf(uf);
		
				
		String mensagem = "";		

		DAOProfessor dao = new DAOProfessor();
		if(dao.editar(professor)){
			mensagem = professor.getNome() + " alterado com sucesso!";
		}else{
			mensagem = "Ocorreu um erro! "+professor.getNome()+" nao foi alterado!!!";
		}		
		if(lista != null){
			for(Professor p : lista){
				if(p.getId() == professor.getId()){
					int i = lista.indexOf(p);
					lista.set(i, professor);
				}
			}
		}

		session.setAttribute("professores", lista);

		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
		response.getWriter().write(jsonObject);	
		
	}

	@SuppressWarnings("unchecked")
	private void deletarProfessor(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		List<Professor> lista = null;
		lista = (List<Professor>) session.getAttribute("professores");
		
		int idprofessor = Integer.parseInt(request.getParameter("txtidprofessor"));		
		Professor professor = new Professor();
		professor.setId(idprofessor);
		
		String mensagem = "";

		DAOProfessor dao = new DAOProfessor();
		if(dao.deletar(professor)){
			mensagem = "Registro deletado com sucesso!";
		}else{
			mensagem = "Erro ao tentar deletar!!!!";
		}
		
		if(lista != null){
			for(Professor p : lista){
				if(p.getId() == professor.getId()){
					lista.remove(p);
					break;
				}
			}
		}
		session.setAttribute("professores", lista);
		
		Gson gson = new Gson();
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
		response.getWriter().write(jsonObject);

	}
	
	private void loadComboDisciplinas(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		List<String> lista = DAOProfessor.getDisciplinas();
		Gson gson = new Gson();

		Type listType = new TypeToken<List<String>>(){}.getType();

		response.setContentType("application/json");
		response.setCharacterEncoding("ISO-8859-1");
		
		String jsonObject = gson.toJson(lista, listType);		
		response.getWriter().write(jsonObject);										
	}

	private void loadComboProfessor(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub		
		int idescola = Integer.parseInt(request.getParameter("txtidescola"));
		Escola escola = new Escola();
		escola.setId(idescola);
		
		List<Professor> lista = new ArrayList<>();
		lista = DAOProfessor.listar(escola);
		Gson gson = new Gson();

		Type listType = new TypeToken<List<Professor>>(){}.getType();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(lista, listType);		
		response.getWriter().write(jsonObject);								

	}

	private void consultarAdocoes(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		
		Usuario user = (Usuario) session.getAttribute("usuariologado");
		
		String codigo = request.getParameter("txtcodigo");
		String ano = request.getParameter("txtano");
		Produto produto = new Produto();
		produto.setCodigo(codigo);
		DAOProduto.recarrega(produto);
		
		List<Adocao> lista = DAOProduto.consultarAdocoes(produto, ano, user);
		
		Collections.sort(lista);
		
		session.setAttribute("adocaoProd", lista);
		
	}

	private void finalizarRegistroAdocao(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		
		Orcamento orcamento = (Orcamento) session.getAttribute("orcamento");
		
		Escola escola = new Escola();
		Adocao adocao = new Adocao();
		
		if(orcamento.getEscola()==null){
			int idescola = Integer.parseInt(request.getParameter("txtidescola"));		
			escola.setId(idescola);
			DAOEscola.recarrega(escola);
			adocao.setEscola(escola);
			adocao.setNomeescola(escola.getNome());
			adocao.setAno(getAnoPeriodo()+"");			
		}else{
			escola = orcamento.getEscola();
			adocao.setIdadocao(orcamento.getIdadocao());
			adocao.setEscola(escola);
			adocao.setNomeescola(escola.getNome());
			adocao.setAno(orcamento.getAno());
		}
		
		int j = 1;
		for(ItemOrcamento i : orcamento.getItens()){
			String serie = request.getParameter(i.getProduto().getCodigo()+j);
			i.getProduto().setSerie(serie);
			j++;
		}		
				
		session.setAttribute("adocao", adocao);
	}

	private void exportaProdutos(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
		// TODO Auto-generated method stub		
		int ano = getAnoPeriodo() - 1;
		String txtano = ano + "";
		String filtro = request.getParameter("filtro");
		String descricao = request.getParameter("txtdescricao");

		DAOProduto.exportaProdutos(response, txtano, filtro, descricao);
	}

	@SuppressWarnings("unchecked")
	private void loadSeriesProduto(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		List<String> series = null;
		series = (List<String>) session.getAttribute("seriesproduto");
		if(series == null || series.isEmpty()){
			series = new ArrayList<>();
			series = DAOEscola.getSeries();			
		}		
		session.setAttribute("seriesproduto", series);
		
		Orcamento orcam = null;
		orcam = (Orcamento) session.getAttribute("orcamento");
		
		if(orcam != null){
			List<Produto> lista = new ArrayList<>();
			for(ItemOrcamento i : orcam.getItens()){
				lista.add(i.getProduto());
			}
			Gson gson = new Gson();
			Type listType = new TypeToken<List<Produto>>(){}.getType();

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			
			String jsonObject = gson.toJson(lista, listType);		
			response.getWriter().write(jsonObject);											
		}		
	}

	private void abandonarRegistroAdocao(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		session.removeAttribute("orcamento");
	}

	@SuppressWarnings("unchecked")
	private void removeItemAdocao(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		
		String codigo = request.getParameter("txtcodigo");
		
		List<ItemOrcamento> itens;
		itens = (List<ItemOrcamento>) session.getAttribute("selecaoadocao");
		for(ItemOrcamento i : itens){
			if(i.getProduto().getCodigo().equals(codigo)){
				itens.remove(i);
				break;
			}
		}
	}
		

	private void loadCombosProduto(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		// TODO Auto-generated method stub
		EnumList lista = new EnumList();
		lista = DAOProduto.getItensCombosProduto();
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(lista);		
		response.getWriter().write(jsonObject);										
	}

	private void loadSeriesAdocao(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		Usuario user = null;
		user = (Usuario) session.getAttribute("usuariologado");
		
		int id = Integer.parseInt(request.getParameter("txtidescola"));
		String ano = request.getParameter("txtano");
		
		List<String> lista = DAOEscola.getSeriesAdocao(id, ano, user);
		
		Gson gson = new Gson();
		Type listType = new TypeToken<List<String>>(){}.getType();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(lista, listType);		
		response.getWriter().write(jsonObject);								
	}


	private void loadUF(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		// TODO Auto-generated method stub

		List<String> lista;
		
		lista = DAOUtils.getUf();
		
		Gson gson = new Gson();
		Type listType = new TypeToken<List<String>>(){}.getType();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(lista, listType);		
		response.getWriter().write(jsonObject);						
	}

	private void loadMunicipios(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		// TODO Auto-generated method stub
		
		String uf = request.getParameter("txtuf");
		List<String> lista;
		
		if(uf.equalsIgnoreCase("todos")){
			lista = DAOUtils.getMunicipios(null);
		}else{
			lista = DAOUtils.getMunicipios(uf);
		}
		
		Gson gson = new Gson();
		Type listType = new TypeToken<List<String>>(){}.getType();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(lista, listType);		
		response.getWriter().write(jsonObject);				
	}

	private void carregarUsuario(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("txtid"));
		
		Usuario user = DAOUsuario.getUsuario(id);

		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(user);		
		response.getWriter().write(jsonObject);		
		
	}

	private void loadEmpresas(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		DAOEmpresa dao = new DAOEmpresa();
		List<Empresa> lista = new ArrayList<>();
		lista = dao.listar();
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Empresa>>(){}.getType();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(lista, listType);		
		response.getWriter().write(jsonObject);		
		
	}

	private void getTabelaPrecos(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException, RuntimeException, Exception {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		Usuario usuario = (Usuario) session.getAttribute("usuariologado");
		
		response.setContentType("application/vnd.ms-excel");
    	response.setHeader("Content-Disposition", "inline; filename=tabela_de_precos.xls");
    	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
    	
    	ServletContext context = request.getServletContext();
    	String estoque = "", inativo = "";
    	String realPath = context.getRealPath("/");
		String templatePath = realPath + "/resources/xls/tabelaTemplate.xls";		
		
		if(usuario != null){
			
			if(usuario.getCargo() == 1){
				estoque = request.getParameter("tabestoque");
				inativo = request.getParameter("tabinativo");
				if(estoque.equalsIgnoreCase("sim")) {
					templatePath = realPath + "/resources/xls/tabelaTemplateAdmin.xls";
				}
			}
			InputStream is = new FileInputStream(templatePath);		
			
			List<ItemTabela> tabela = DAOProduto.getTabelaPrecos(usuario, estoque, inativo);
						
			Map<String, Object> beans = new HashMap<>();
			beans.put("itemtab", tabela);
			
			XLSTransformer transformer = new XLSTransformer();
					
			Workbook workbook = transformer.transformXLS(is, beans);
			
			workbook.write(response.getOutputStream());
			
		}else{
			session.setAttribute("message", "Para baixar a tabela faça o login digitando seu e-mail e cnpj cadastrados!");
			response.sendRedirect("result.jsp");
		}
		
	}

	private void roteiroSalvarObservacao(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		Roteiro roteiro = (Roteiro) session.getAttribute("roteiro");
		int idescola = Integer.parseInt(request.getParameter("txtidescola"));
		String observacao = request.getParameter("txtobservacao");
		String mensagem = "";
		DAOEscola dao = new DAOEscola();
		
		for(Escola e : roteiro.getEscolas()){
			if(e.getId()==idescola){
				e.setObservacao(observacao);
				mensagem = dao.salvarObservacaoEscola(roteiro, e);
				break;
			}
		}
		
		session.setAttribute("roteiro", roteiro);
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);		
		
	}

	@SuppressWarnings("unchecked")
	private void roteiroDetalhar(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		List<Roteiro> roteiros = (List<Roteiro>) session.getAttribute("listarroteiros");
		int id = Integer.parseInt(request.getParameter("txtid"));
		Roteiro roteiro = null;
		for (Roteiro r : roteiros){
			if(r.getId() == id){
				roteiro = r;
				for(Escola e : r.getEscolas()){
					DAOEscola.setTotalAlunos(e);
				}
				break;
			}
		}
		session.setAttribute("roteiro", roteiro);
	}

	private void roteirosListar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		Usuario usuario = (Usuario) session.getAttribute("usuariologado");
		Usuario vendedor = null;
		
		int setor;
		
		Date inicio = convertStringToDate(request.getParameter("txtdataini"));
		Date fim = convertStringToDate(request.getParameter("txtdatafim"));
		
		if(usuario.getCargo() != 3){
			setor = Integer.parseInt(request.getParameter("txtsetor"));
			usuario.setSetor(setor);
			vendedor = usuario;
		}else{
			vendedor = usuario;
		}
		
		List<Roteiro> roteiros = new ArrayList<>();
		DAOEscola dao = new DAOEscola();
		
		roteiros = dao.listarRoteiros(vendedor, inicio, fim);
		
		session.setAttribute("listarroteiros", roteiros);		
	}

	@SuppressWarnings("unchecked")
	private void roteiroSalvar(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		List<Escola> escolas = (List<Escola>) session.getAttribute("roteiroescolas");
		String dataroteiro = request.getParameter("dataroteiro");
		Date datarot = convertStringToDatePrev(dataroteiro);
		int setor = Integer.parseInt(request.getParameter("txtsetor"));
		Usuario vendedor = new Usuario();
		vendedor.setSetor(setor);
		vendedor = DAOUsuario.getVendedor(setor);
		
		Roteiro roteiro = new Roteiro();
		roteiro.setData(datarot);
		roteiro.setVendedor(vendedor);
		roteiro.setEscolas(escolas);				
		
		DAOEscola dao = new DAOEscola();
		
		for(Escola e : escolas){
			DAOEscola.getQtdeAlunos(e);
		}
		
		String mensagem = dao.salvarRoteiro(roteiro);
		
		session.removeAttribute("roteiroescolas");
		session.removeAttribute("listaescolas");
		session.setAttribute("roteiro", roteiro);
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);		
	}

	@SuppressWarnings("unchecked")
	private void escolaSalvarAlunos(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		String mensagem = "";
		
		List<Escola> lista =  null;
		lista = (List<Escola>) session.getAttribute("listaescolas");
		Roteiro roteiro = null;
		roteiro = (Roteiro) session.getAttribute("roteiro");
		
		if(lista == null && roteiro != null){
			lista = roteiro.getEscolas();
		}
		
		int idescola = Integer.parseInt(request.getParameter("txtidescola"));
		
		int berc, mat1, mat2, mat3, inf1, inf2, ano1, ano2, ano3, ano4, ano5,
			ano6, ano7, ano8, ano9, ser1, ser2, ser3, eja, supl;
		
		berc = Integer.parseInt(request.getParameter("txtbercario"));
		mat1 = Integer.parseInt(request.getParameter("txtmaternal1"));
		mat2 = Integer.parseInt(request.getParameter("txtmaternal2"));
		mat3 = Integer.parseInt(request.getParameter("txtmaternal3"));
		inf1 = Integer.parseInt(request.getParameter("txtinfantil1"));
		inf2 = Integer.parseInt(request.getParameter("txtinfantil2"));
		ano1 = Integer.parseInt(request.getParameter("txtano1"));
		ano2 = Integer.parseInt(request.getParameter("txtano2"));
		ano3 = Integer.parseInt(request.getParameter("txtano3"));
		ano4 = Integer.parseInt(request.getParameter("txtano4"));
		ano5 = Integer.parseInt(request.getParameter("txtano5"));
		ano6 = Integer.parseInt(request.getParameter("txtano6"));
		ano7 = Integer.parseInt(request.getParameter("txtano7"));
		ano8 = Integer.parseInt(request.getParameter("txtano8"));
		ano9 = Integer.parseInt(request.getParameter("txtano9"));
		ser1 = Integer.parseInt(request.getParameter("txtser1"));
		ser2 = Integer.parseInt(request.getParameter("txtser2"));
		ser3 = Integer.parseInt(request.getParameter("txtser3"));
		eja = Integer.parseInt(request.getParameter("txteja"));
		supl = Integer.parseInt(request.getParameter("txtsupl"));
		
		DAOEscola dao = new DAOEscola();
		
		if(lista != null){
			for (Escola escola : lista){
	
				if(escola.getId()==idescola){
					escola.setInfantil0(berc);
					escola.setInfantil1(mat1);
					escola.setInfantil2(mat2);
					escola.setInfantil3(mat3);
					escola.setInfantil4(inf1);
					escola.setInfantil5(inf2);
					escola.setAno1(ano1);
					escola.setAno2(ano2);
					escola.setAno3(ano3);
					escola.setAno4(ano4);
					escola.setAno5(ano5);
					escola.setAno6(ano6);
					escola.setAno7(ano7);
					escola.setAno8(ano8);
					escola.setAno9(ano9);
					escola.setSerie1(ser1);
					escola.setSerie2(ser2);
					escola.setSerie3(ser3);
					escola.setEja(eja);
					escola.setSupletivo(supl);
					mensagem = dao.salvarQtdeAlunos(escola);
					escola.refazTotalAlunos();
				break;
				}
				
			}
		}else{
			Escola escola = new Escola();
			escola.setId(idescola);
			escola.setInfantil0(berc);
			escola.setInfantil1(mat1);
			escola.setInfantil2(mat2);
			escola.setInfantil3(mat3);
			escola.setInfantil4(inf1);
			escola.setInfantil5(inf2);
			escola.setAno1(ano1);
			escola.setAno2(ano2);
			escola.setAno3(ano3);
			escola.setAno4(ano4);
			escola.setAno5(ano5);
			escola.setAno6(ano6);
			escola.setAno7(ano7);
			escola.setAno8(ano8);
			escola.setAno9(ano9);
			escola.setSerie1(ser1);
			escola.setSerie2(ser2);
			escola.setSerie3(ser3);
			escola.setEja(eja);
			escola.setSupletivo(supl);
			
			mensagem = dao.salvarQtdeAlunos(escola);
		}

		
    	if(roteiro==null && lista != null){
			session.setAttribute("listaescolas", lista);
    	}else if(roteiro != null){
    		roteiro.setEscolas(lista);
			session.setAttribute("roteiro", roteiro);        		
    	}
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);
		
	}

	private void roteiroCancelar(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		session.removeAttribute("roteiroescolas");
		session.removeAttribute("roteiro");
		String mensagem = "Roteiro excluido com sucesso!";

		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);
		
	}

	@SuppressWarnings({ "unchecked" })
	private void roteiroExcluirEscola(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		
		List<Escola> roteiro = (List<Escola>) session.getAttribute("roteiroescolas");
		
		int idescola = Integer.parseInt(request.getParameter("idescola"));
			
		String mensagem = "";
		
		for(Escola e : roteiro){

			if(e.getId() == idescola){
				roteiro.remove(e);
				mensagem = e.getNome()+" excluida do roteiro!";
				break;
			}
		}
		
		session.setAttribute("roteiroescolas", roteiro);
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);
		
	}

	@SuppressWarnings("unchecked")
	private void roteiroAddEscola(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		
		Usuario vendedor = null;
		
		vendedor = (Usuario) session.getAttribute("vendedor");
		
		List<Escola> escolas = (List<Escola>) session.getAttribute("listaescolas");
		
		List<Escola> roteiro = null;
		
		roteiro = (List<Escola>) session.getAttribute("roteiroescolas");
		
		int idescola = Integer.parseInt(request.getParameter("idescola"));
				
		boolean isRoteiro = false;
		
		String mensagem = "-1";
		
		if(roteiro == null){
			
			roteiro = new ArrayList<>();

			for(Escola e : escolas){
				if(e.getId() == idescola){
					roteiro.add(e);
					if(vendedor == null){
						vendedor = DAOEscola.getUsuarioVendedor(e);
						session.setAttribute("vendedor", vendedor);
					}
					mensagem = "1";
					break;
				}
			}
			
			
		}else{
			
			for(Escola e : roteiro){
				
				if(e.getId() == idescola){
					isRoteiro = true;
					mensagem = "0";
					break;
				}
				
			}

			if(!isRoteiro){
				
				for(Escola e : escolas){
					if(e.getId() == idescola){
						roteiro.add(e);
						if(vendedor == null){
							vendedor = DAOEscola.getUsuarioVendedor(e);
							session.setAttribute("vendedor", vendedor);
						}
						mensagem = "1";
						break;
					}
				}
				
			}
			
		}		
		
		session.setAttribute("roteiroescolas", roteiro);
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);
		
	}

	private void listaVendasSintetico(HttpServletRequest request,
			HttpServletResponse response) throws IOException, RuntimeException, Exception{
		// TODO Auto-generated method stub
		response.setContentType("application/vnd.ms-excel");
    	response.setHeader("Content-Disposition", "attachment; filename=vendas_sintetico.xls");
    	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
    	
    	ServletContext context = request.getServletContext();
    	
    	String realPath = context.getRealPath("/");
    	    	
		String templatePath = realPath + "/resources/xls/vendaSinteticoTemplate.xls";		
		
		InputStream is = new FileInputStream(templatePath);	

		String codigoftd = request.getParameter("txtcliente");
		String datainicial = request.getParameter("txtinicio");
		String datafinal = request.getParameter("txtfim");
		String TESVenda = request.getParameter("tesvd");
		String TESDevolucao = request.getParameter("tesdv");
		String filial = request.getParameter("txtfilial");
		
		Date dtini = convertStringToDate(datainicial);
		Date dtfim = convertStringToDate(datafinal);
		
		DAOVenda dao = new DAOVenda();
		
		List<Venda> vendas = new ArrayList<>();
		
		vendas = dao.getVendasSintetico(filial, dtini, dtfim, TESVenda, TESDevolucao, codigoftd);
		
		if(vendas != null){
			Map<String, Object> beans = new HashMap<>();
			beans.put("venda", vendas);
			beans.put("datainicio", datainicial);
			beans.put("datafim", datafinal);
			
			XLSTransformer transformer = new XLSTransformer();
					
			Workbook workbook = transformer.transformXLS(is, beans);
			
			workbook.write(response.getOutputStream());		
		}else{
			response.getWriter().print("Erro ao gerar o arquivo");
		}
	}

	@SuppressWarnings("unchecked")
	private void notasOrcamsExcel(HttpServletRequest request,
			HttpServletResponse response) throws IOException, RuntimeException, Exception{
		// TODO Auto-generated method stub
		response.setContentType("application/vnd.ms-excel");
    	response.setHeader("Content-Disposition", "attachment; filename=notas_orcams.xls");
    	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
    	
    	ServletContext context = request.getServletContext();
    	
    	String realPath = context.getRealPath("/");
    	    	
		String templatePath = realPath + "/resources/xls/notasOrcamsTemplate.xls";		
				
		
		InputStream is = new FileInputStream(templatePath);	

		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		
		List<NotasReport> notas = (List<NotasReport>) session.getAttribute("reportnotas");
				
		if(notas != null){
			Map<String, Object> beans = new HashMap<>();
			beans.put("rp", notas);
			
			XLSTransformer transformer = new XLSTransformer();
					
			Workbook workbook = transformer.transformXLS(is, beans);
			
			workbook.write(response.getOutputStream());		
		}else{
			response.getWriter().print("Erro ao gerar o arquivo");
		}
	}
	
	private void consultarAnoPedido(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		List<String> anos = DAOPedido.getAnosPedido();

		Gson gson = new Gson();
		Type listType = new TypeToken<List<String>>(){}.getType();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(anos, listType);		
		response.getWriter().write(jsonObject);		
	}

	private void estoqueToExcel(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException, RuntimeException, Exception{
		// TODO Auto-generated method stub
		
		response.setContentType("application/vnd.ms-excel");
    	response.setHeader("Content-Disposition", "inline; filename=estoques.xls");
    	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
    	
    	ServletContext context = request.getServletContext();
    	
    	String realPath = context.getRealPath("/");
    	String templatePath = realPath + "/resources/xls/estoqueTemplate.xls"; 
    	String uf = (String) getServletContext().getAttribute("pageUF");
    	if(!uf.equalsIgnoreCase("Maranhão")) {
    		templatePath = realPath + "/resources/xls/estoqueTemplate2.xls";
    	}
    	
		InputStream is = new FileInputStream(templatePath);		
		
		List<Produto> estoques = DAOUtils.getEstoques();
		
		Map<String, Object> beans = new HashMap<>();
		beans.put("produtos", estoques);
		
		XLSTransformer transformer = new XLSTransformer();
				
		Workbook workbook = transformer.transformXLS(is, beans);
		
		workbook.write(response.getOutputStream());		

	}

	private void editarItemPedido(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		Pedido pedido = null;
		pedido = (Pedido) session.getAttribute("detalhepedido");
		
		String codigo, observacao;
		int idpedido, situacao;
		
		idpedido = Integer.parseInt(request.getParameter("txtidpedido"));
		situacao = Integer.parseInt(request.getParameter("chkcancelar"));
		codigo = request.getParameter("txtcodigo");
		int qtdpedida = Integer.parseInt(request.getParameter("txtqtdpedida"));
		String previsao = request.getParameter("txtprevisao");
		Date prev = convertStringToDatePrev(previsao);
		observacao = request.getParameter("txtobservacao");
		
		ItemPedido item = new ItemPedido();
		Produto p = new Produto();
		p.setCodigo(codigo);
		item.setIdpedido(idpedido);
		item.setQtdpedida(qtdpedida);
		item.setPrevisao(prev);
		item.setCancelado(situacao);
		item.setObservacao(observacao);
		item.setItem(p);
		
		if(pedido != null){
			int index = -1;
			for(ItemPedido i : pedido.getItens()){
				index++;
				if(i.getItem().getCodigo().equals(item.getItem().getCodigo())){
					item.getItem().setDescricao(i.getItem().getDescricao());
					item.setQtdchegou(i.getQtdchegou());
					item.refazPendente();
					pedido.getItens().remove(index);
					break;
				}
					
			}
				
			pedido.getItens().add(index, item);
			
			session.setAttribute("detalhepedido", pedido);
		}

		DAOPedido dao = new DAOPedido();
		
		
		String mensagem = dao.alterarItemPedido(item);
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);				
	}

	private void cadastrarUsuario(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		String nome, sobrenome, cargo, email, setor, empresa, login, senha;
		
		nome = request.getParameter("txtnome");
		sobrenome = request.getParameter("txtsobrenome");
		String nomeusuario = nome + " " + sobrenome;
		email = request.getParameter("txtemail");
		setor = request.getParameter("txtsetor");
		cargo = request.getParameter("txtcargo");
		empresa = request.getParameter("txtempresa");
		login = request.getParameter("txtlogin");
		senha = request.getParameter("txtsenha");
		
		Usuario usuario = new Usuario();
		usuario.setNome(nomeusuario);
		usuario.setEmail(email);
		usuario.setSetor(Integer.parseInt(setor));
		usuario.setCargo(Integer.parseInt(cargo));
		usuario.setIdempresa(Integer.parseInt(empresa));
		usuario.setLogin(login);
		usuario.setSenha(senha);
		
		DAOUsuario dao = new DAOUsuario();
		String mensagem = dao.salvar(usuario);

		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);				
	}
	
	
	private void verificaLogin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nome = request.getParameter("txtlogin");

		DAOUsuario dao = new DAOUsuario();
		
		String mensagem = dao.verificaLogin(nome);

		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);			
		
	}
	
	
	@SuppressWarnings("unchecked")
	private void editarEscola(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		
		Usuario usuario = (Usuario) session.getAttribute("usuariologado");
		
		List<Escola> lista = null;
		lista = (List<Escola>) session.getAttribute("listaescolas");
		Roteiro roteiro = null;
		roteiro = (Roteiro) session.getAttribute("roteiro");
		if(lista == null && roteiro == null){
			session = request.getSession(true);
		}
		lista = (List<Escola>) session.getAttribute("listaescolas");
		roteiro = (Roteiro) session.getAttribute("roteiro");
		if(lista == null && roteiro != null){
			lista = roteiro.getEscolas();
		}
		
		String nome, classificacao, cnpj, endereco, complemento, bairro, municipio, uf, cep, email, telefone, dependencia;
		
		int id = Integer.parseInt(request.getParameter("txtidescola"));
		int idftd = Integer.parseInt(request.getParameter("txtidftd"));
		int setor = Integer.parseInt(request.getParameter("txtsetor"));
		
		nome = request.getParameter("txtnome");
		classificacao = request.getParameter("txtclassificacao");
		cnpj = request.getParameter("cnpj");
		cnpj = cnpj.replace('.', ';');
		cnpj = cnpj.replace('/', ';');
		cnpj = cnpj.replace('-', ';');
		String novocnpj[] = cnpj.split(";");
		cnpj = "";
		for (String s : novocnpj){
			cnpj += s;
		}
		
		endereco = request.getParameter("txtendereco");
		complemento = request.getParameter("txtcomplemento");
		bairro = request.getParameter("txtbairro");
		municipio = request.getParameter("txtmunicipio");
		uf = request.getParameter("txtuf");
		cep = request.getParameter("cep");
		cep = cep.replace('.', ';');
		cep = cep.replace('-', ';');
		String novocep[] = cep.split(";");
		cep = "";
		for (String s : novocep){
			cep += s;
		}
		email = request.getParameter("txtemail");
		telefone = request.getParameter("txttelefone");
		dependencia = request.getParameter("txtdependencia");
		
		String mensagem = "Erro ao alterar a escola!";
		
		boolean flag = false;
		
		DAOEscola dao = new DAOEscola();
		
		if(lista != null){
	    	for(Escola escola : lista){
	    		escola.setUser_id(usuario.getId());
	    		if(escola.getId() == id ){
	
	    			escola.setId(id);
	    			escola.setIdftd(idftd);
	    			escola.setNome(nome);
	    			escola.setClassificacao(classificacao);
	    			escola.setCnpj(cnpj);
	    			escola.setEndereco(endereco);
	    			escola.setComplemento(complemento);
	    			escola.setBairro(bairro);
	    			escola.setMunicipio(municipio);
	    			escola.setUf(uf);
	    			escola.setCep(cep);
	    			escola.setEmail(email);
	    			escola.setTelefone(telefone);
	    			escola.setSetor(setor);
	    			escola.setDependencia(dependencia);
	    			escola.setVendedor(DAOUsuario.getVendedor(setor));
		    			
	    			flag = dao.editar(escola);
	    			break;
	    		}
	    	}
		}else{
			Escola escola = new Escola();
			escola.setUser_id(usuario.getId());
			escola.setId(id);
			escola.setIdftd(idftd);
			escola.setNome(nome);
			escola.setClassificacao(classificacao);
			escola.setCnpj(cnpj);
			escola.setEndereco(endereco);
			escola.setComplemento(complemento);
			escola.setBairro(bairro);
			escola.setMunicipio(municipio);
			escola.setUf(uf);
			escola.setCep(cep);
			escola.setEmail(email);
			escola.setTelefone(telefone);
			escola.setSetor(setor);
			escola.setDependencia(dependencia);
			escola.setVendedor(DAOUsuario.getVendedor(setor));
			
			flag = dao.editar(escola);
		}
		
		
		
		
		
		if(flag){

			mensagem = "Escola alterada com sucesso!";
       	
        	if(roteiro!=null){
        		roteiro.setEscolas(lista);
        		session.setAttribute("roteiro", roteiro);
        	}else{
        		session.setAttribute("listaescolas", lista);				        		
        	}
		}

		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);		

	}

	@SuppressWarnings("unchecked")
	private void deletarEscola(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		List<Escola> lista = null;
		lista = (List<Escola>) session.getAttribute("listaescolas");		
		if(lista == null){
			session = request.getSession(true);
		}
		int id = Integer.parseInt(request.getParameter("idescola"));
		Escola escola = new Escola();
		escola.setId(id);
		DAOEscola.recarrega(escola);
		DAOEscola dao = new DAOEscola();
		String mensagem = dao.deletar(escola);
		for(Escola e : lista){
			if(e.getId() == id){
				lista.remove(e);
				session.setAttribute("listaescolas", lista);
				break;
			}
		}

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);		
	}

	private void detalheEscola(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		int id = Integer.parseInt(request.getParameter("idescola"));
		Escola escola = new Escola();
		escola.setId(id);
		
		DAOEscola.recarrega(escola);
		
		
		DAOEscola.getQtdeAlunos(escola);
		escola.refazTotalAlunos();
		
		session.setAttribute("detalheescola", escola);
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(escola);		
				
		response.getWriter().write(jsonObject);
		
	}

	@SuppressWarnings("unchecked")
	private void listarEscolas(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		List<Escola> lista = null;
		lista = (List<Escola>) session.getAttribute("listaescolas");
		if(lista == null){
			session = request.getSession(true);
		}
		int setor = Integer.parseInt(request.getParameter("txtsetor"));
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("usuariologado");
		String dependencia = request.getParameter("cmbdependencia");
		String municipio = request.getParameter("cmbmunicipio");
		String[] bairro = request.getParameterValues("cmbbairro");
		String visita = request.getParameter("cmbvisita");
		
		if(lista == null){
			lista = new ArrayList<>();
		}
		DAOEscola dao = new DAOEscola();
		lista = dao.listar(usuario, dependencia, municipio, bairro, visita, setor);
		
		session.removeAttribute("roteiro");
		session.setAttribute("listaescolas", lista);
		
	}

	private void verItemPedido(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		String codigo = request.getParameter("txtcodigo");
		ItemPedido item = new ItemPedido();
		Pedido pedido = (Pedido) session.getAttribute("detalhepedido");
		
		for(ItemPedido i : pedido.getItens()){
			if(i.getItem().getCodigo().equalsIgnoreCase(codigo)){
				item.setIdpedido(pedido.getIdpedido());
				item.setItem(i.getItem());
				item.setPrevisao(i.getPrevisao());
				item.setQtdchegou(i.getQtdchegou());
				item.setQtdpedida(i.getQtdpedida());
				item.setQtdpendente(i.getQtdpendente());
				item.setCancelado(i.getCancelado());
				item.setObservacao(i.getObservacao());
				break;
			}
		}				
		
		session.setAttribute("itempedido", item);
	}

	private void obsItemPedido(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		String codigo = request.getParameter("txtcodigo");
		int idpedido = Integer.parseInt(request.getParameter("txtidpedido"));
		ItemPedido item = new ItemPedido();
		@SuppressWarnings("unchecked")
		ArrayList<Pedido> pedidos = (ArrayList<Pedido>) session.getAttribute("pedProd");
		Pedido pedido = new Pedido();
		for(Pedido p : pedidos){
			if(p.getIdpedido() == idpedido){
				pedido = p;
				break;
			}
		}
		
		for(ItemPedido i : pedido.getItens()){
			if(i.getItem().getCodigo().equalsIgnoreCase(codigo)){
				item.setIdpedido(pedido.getIdpedido());
				item.setItem(i.getItem());
				item.setPrevisao(i.getPrevisao());
				item.setQtdchegou(i.getQtdchegou());
				item.setQtdpedida(i.getQtdpedida());
				item.setQtdpendente(i.getQtdpendente());
				item.setCancelado(i.getCancelado());
				item.setObservacao(i.getObservacao());
				break;
			}
		}				
		
		session.removeAttribute("pedProd");
		session.setAttribute("itempedido", item);
		response.getWriter().write("");
	}
	

	private void orcamentoToExcel(HttpServletRequest request,
			HttpServletResponse response) throws IOException, RuntimeException, Exception {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		
		Orcamento orcamento = null;
		float desconto = Float.parseFloat(request.getParameter("taxadesconto"));
		
		response.setContentType("application/vnd.ms-excel");
    	response.setHeader("Content-Disposition", "inline; filename=orcamento.xls");
    	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
    	
    	ServletContext context = request.getServletContext();
    	
    	String realPath = context.getRealPath("/");
    	    	
		String templatePath = realPath + "/resources/xls/orcamentoTemplate.xls";		
		
		InputStream is = new FileInputStream(templatePath);
		
		orcamento = (Orcamento) session.getAttribute("orcamento");
		
		orcamento.setDesconto(orcamento.getTotal() * desconto);
		
		Map<String, Object> beans = new HashMap<>();
		beans.put("orcam", orcamento);
		beans.put("itens", orcamento.getItens());

		XLSTransformer transformer = new XLSTransformer();
				
		Workbook workbook = transformer.transformXLS(is, beans);
		
		workbook.write(response.getOutputStream());				
	}

	private void adocaoEscolaSelect(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub

		int id = Integer.parseInt(request.getParameter("txtidescola"));
		Escola escola = new Escola();
		escola.setId(id);
		DAOEscola dao = new DAOEscola();
		List<Adocao> adocoes = dao.listarSerieAno(escola);

		Gson gson = new Gson();
		Type listType = new TypeToken<ArrayList<Adocao>>(){}.getType();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(adocoes, listType);		
		response.getWriter().write(jsonObject);
		
	}

	@SuppressWarnings("static-access")
	private void consultarPedidos(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		
		String codigo = request.getParameter("codigo");
		String ano = request.getParameter("ano");
					
		Produto produto = new Produto();
		produto.setCodigo(codigo);
		DAOProduto daop = new DAOProduto();
		daop.recarrega(produto);
		DAOPedido dao = new DAOPedido();
		ArrayList<Pedido> pedidos = null;
		try {
			pedidos = dao.listar(produto, ano);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		session.setAttribute("pedProd", pedidos);
		
	}

	@SuppressWarnings("static-access")
	private void consultarNotas(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		
		String codigo = request.getParameter("codigo");
		String ano = request.getParameter("ano");
					
		Produto produto = new Produto();
		produto.setCodigo(codigo);
		DAOProduto daop = new DAOProduto();
		daop.recarrega(produto);
		DAONotaFiscal dao = new DAONotaFiscal();
		List<NotaFiscal> notas = null;
		notas = dao.listar(produto, ano);
		
		session.setAttribute("notasProd", notas);
		
	}

	private void listarItensDoacao(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		session.removeAttribute("orcamento");
		Orcamento orcamento = new Orcamento();
		DAODoacao dao = new DAODoacao();
		orcamento = dao.listar();
		request.setAttribute("orcamento", orcamento);
    	getServletContext().getRequestDispatcher("/doacaoDetalharTodos.jsp").forward(
            request, response);        																					
	}


	private void detalharNotaFiscal(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		
		String idnota = request.getParameter("txtidnota");
		String emissao = request.getParameter("txtemissao");
		Date emit = convertStringToDatePrev(emissao);
		NotaFiscal nota = new NotaFiscal();
		nota.setIdnota(idnota);
		nota.setEmissao(emit);
		DAONotaFiscal.recarrega(nota);
		
		session.removeAttribute("notafiscal");
		session.setAttribute("notafiscal", nota);
    	//getServletContext().getRequestDispatcher("/notafiscalDetalhar.jsp").forward(
        //    request, response);
	}

	private void listarNotaFiscal(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		String inicio = request.getParameter("dataini");
		String fim = request.getParameter("datafim");
		Date dtini = convertStringToDate(inicio);
		Date dtfim = convertStringToDate(fim);
		DAONotaFiscal dao = new DAONotaFiscal();
		List<NotaFiscal> notas = dao.listar(dtini, dtfim);
		session.setAttribute("notasfiscais", notas);
	}

	

	private void listarClassificacao(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException {
		// TODO Auto-generated method stub
		List<ClassificacaoEscola> lista = null;
		
		lista = DAOEscola.getListaClassificacao();
		
		Gson gson = new Gson();
		Type listType = new TypeToken<ArrayList<ClassificacaoEscola>>(){}.getType();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(lista, listType);		
		response.getWriter().write(jsonObject);
		
	}

	
	@SuppressWarnings({ })
	private void listarVendedores(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		Usuario usuario = (Usuario) session.getAttribute("usuariologado");
		
		List<Usuario> lista = null;
		
			DAOUsuario dao = new DAOUsuario();
			lista = dao.listarvendedores(usuario);

			Gson gson = new Gson();
			Type listType = new TypeToken<ArrayList<Usuario>>(){}.getType();

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			
			String jsonObject = gson.toJson(lista, listType);		
			response.getWriter().write(jsonObject);		
	}

	private void listarDoacao(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		int setor = Integer.parseInt(request.getParameter("txtsetor"));
		String txtini = request.getParameter("txtdataini");
		String txtfim = request.getParameter("txtdatafim");
		
		Date dataini = convertStringToDate(txtini);
		Date datafim = convertStringToDate(txtfim);
		
		Usuario usuario = new Usuario();
		usuario.setSetor(setor);
		DAODoacao dao = new DAODoacao();
		List<Doacao> listadoacao = new ArrayList<>();
		listadoacao = dao.listar(usuario, dataini, datafim);
		session.setAttribute("listadoacao", listadoacao);
		response.sendRedirect("doacaoListagem.jsp");
	}

	private void dataChegadaNota(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		String nota = request.getParameter("txtnota");
		Date chegada = convertStringToDate(request.getParameter("txtdata"));
		DAONotaFiscal dao = new DAONotaFiscal();
		
		if(dao.setDataChegada(nota, chegada)){
			request.setAttribute("message", "Data "+chegada+" registrada para a Nota "+nota+"!");
	    	getServletContext().getRequestDispatcher("/result.jsp").forward(
	            request, response);        													
		}else{
			request.setAttribute("message", "Erro ao registrar a data!");
	    	getServletContext().getRequestDispatcher("/result.jsp").forward(
	            request, response);        													
		}
	}


	private void produtoToExcel(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Set-Cookie", "fileDownload=true; path=/");
		response.setHeader("Content-Disposition", "inline; filename=tabela.xls");    	
    	
		DAOProduto dao = new DAOProduto();
		
		dao.produtoToExcel(response);		
	}

	private void pendentesToExcel(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Date ini = convertStringToDate(request.getParameter("txtinicio"));
		Date fim = convertStringToDate(request.getParameter("txtfim"));

    	ServletContext context = request.getServletContext();
    	
		DAODemanda dao = new DAODemanda();
		
		dao.pendentesToExcel(ini, fim, response, context);
		
	}

	private void pesquisarDoacao(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		Usuario usuario = (Usuario) session.getAttribute("usuariologado");
		String nome = request.getParameter("txtdescricao");
		DAOProfessor dao = new DAOProfessor();
		List<Professor> professores = new ArrayList<>();
		professores = dao.buscar(usuario, nome);
		
    	request.setAttribute("professor", professores);
    	getServletContext().getRequestDispatcher("/doacaoBuscar.jsp").forward(
            request, response);        																	
	}

	private void imprimirDoacao(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		int iddoacao = Integer.parseInt(request.getParameter("txtiddoacao"));
		Doacao doacao = new Doacao();
		doacao.setId(iddoacao);
		DAODoacao.recarrega(doacao);
    	session.setAttribute("doacao", doacao);
	}

	private void registrarDoacao(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		Orcamento orcamento = (Orcamento) session.getAttribute("orcamento");
		
		for(ItemOrcamento i : orcamento.getItens()){		
			int quantidade = Integer.parseInt(request.getParameter(i.getProduto().getCodigo()));
			i.setQuantidade(quantidade);
		}

		Usuario usuario = (Usuario) session.getAttribute("usuariologado");
		DAOUsuario.recarrega(usuario);
		
		Professor professor = new Professor();
		Escola escola = new Escola();
		escola.setId(Integer.parseInt(request.getParameter("txtidescola")));		
		professor.setId(Integer.parseInt(request.getParameter("txtidprofessor")));
		DAOProfessor.recarrega(professor);
		Doacao doacao = new Doacao();
		doacao.setEscola(escola);
		doacao.setProfessor(professor);
		doacao.setUsuario(usuario);
		Date atual = new Date(System.currentTimeMillis());
		doacao.setEmissao(atual);
		doacao.setItens(orcamento.getItens());
		doacao.refazTotal();
		doacao.setId(orcamento.getIddoacao());
		
		DAODoacao dao = new DAODoacao();		
		
		if(DAOUtils.getFlag("doacao") == 1){

			int id = dao.salvar(doacao); 
			doacao.setId(id);
			DAODoacao.recarrega(doacao);
			doacao.refazTotal();

			if(id > 0){
				session.removeAttribute("orcamento");
	        	session.setAttribute("doacao", doacao);
			}else{
	        	request.setAttribute("message", "Ocorreu um erro ao registrar!");
	        	getServletContext().getRequestDispatcher("/result.jsp").forward(
	                request, response);        										
			}
		}else{
        	request.setAttribute("message", "Registros de doacao estao suspensos!!!");
        	getServletContext().getRequestDispatcher("/result.jsp").forward(
                request, response);        													
		}
	}
	
	
	private void alterarDoacao(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		int iddoacao = Integer.parseInt(request.getParameter("txtiddoacao"));
		
		Doacao doacao = new Doacao();
		doacao.setId(iddoacao);
		
		DAODoacao.recarrega(doacao);
		
		Orcamento orcamento = new Orcamento();
		
		orcamento.setItens(doacao.getItens());
		orcamento.setIddoacao(iddoacao);
		orcamento.setEscola(doacao.getEscola());
		orcamento.setProfessor(doacao.getProfessor());
		orcamento.refazTotal();
		
		session.setAttribute("orcamento", orcamento);
		
	}
	
	private void removeItemDoacao(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		
		Orcamento orcamento = (Orcamento) session.getAttribute("orcamento");
		
		String codigo = request.getParameter("codigoitem");

		ItemOrcamento item = new ItemOrcamento();
		Produto produto = new Produto();
		produto.setCodigo(codigo);
		DAOProduto.recarrega(produto);
		item.setProduto(produto);

		orcamento.remove(item);
		
		response.sendRedirect("doacaoRegistrar.jsp");		
	}

	@SuppressWarnings("unchecked")
	private void cadastrarProduto(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(false);
		
		List<Produto> itens = (List<Produto>) session.getAttribute("tabela_servlet");
		
		if(itens == null){
			session = request.getSession(true);
			itens = (List<Produto>) session.getAttribute("tabela_servlet");
		}
		
		String codigo, descricao, preco, autor, serie, codbar, nivel,
		familia, colecao, disciplina, editora, obs, imagem, grupo, lancto, status, xpeso;
		
		codigo = request.getParameter("txtcodigo").toUpperCase();
		descricao = request.getParameter("txtdescricao");
		preco = request.getParameter("txtpreco");
		autor = request.getParameter("txtautor");
		codbar = request.getParameter("txtcodbar");
		serie = request.getParameter("txtserie");
		nivel = request.getParameter("txtnivel");
		familia = request.getParameter("txtfamilia");
		colecao = request.getParameter("txtcolecao");
		obs = request.getParameter("txtobs").toUpperCase();
		disciplina = request.getParameter("txtdisciplina");
		editora = request.getParameter("txteditora");
		imagem = request.getParameter("txtimagem");
		grupo = request.getParameter("txtgrupo");
		xpeso = request.getParameter("txtpeso");
		float prc = parseStringToFloat(preco);		
		int marketshare = Integer.parseInt(request.getParameter("txtmarketshare"));
		float peso = parseStringToFloat(xpeso);
		int paginas = Integer.parseInt(request.getParameter("txtpaginas"));;		
		lancto = request.getParameter("txtlancto");
		status = request.getParameter("txtstatus");
		
		Produto p = new Produto();
		p.setCodigo(codigo);
		p.setDescricao(descricao);
		p.setPreco(prc);
		p.setAutor(autor);
		p.setCodbar(codbar);
		p.setSerie(serie);
		p.setNivel(nivel);
		p.setFamilia(familia);
		p.setColecao(colecao);
		p.setObs(obs);
		p.setDisciplina(disciplina);
		p.setEditora(editora);
		p.setImagem(imagem);
		p.setMarketshare(marketshare);
		p.setGrupo(grupo);
		p.setPaginas(paginas);
		p.setPeso(peso);
		p.setLancto(lancto);
		p.setStatus(status);
		
		DAOProduto dao = new DAOProduto();
		String mensagem = "";
		try {
			mensagem = dao.salvar(p);
			if(itens != null && mensagem.equalsIgnoreCase("Ok")){
				itens.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				

		session.removeAttribute("tabela_servlet");
		session.setAttribute("tabela_servlet", itens);
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);				
	}

	private void listarDemanda(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, RuntimeException, Exception{
		// TODO Auto-generated method stub
		String ano = request.getParameter("txtano");
		String itens = request.getParameter("txtitens");
		String familia = request.getParameter("txtfamilia");
		String uf = (String) getServletContext().getAttribute("pageUF");
		int anoprev = Integer.parseInt(ano);
		int anoatual = anoprev - 1;
		Date hoje = new Date(System.currentTimeMillis());
		SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");		
		String datainicial = (anoatual-1)+"-07-01";
		String datafinal = anoatual+"-06-30";
		Date dataini = convertStringToDate(datainicial);
		Date datafim = convertStringToDate(datafinal);
		String TESVenda = "511";//request.getParameter("tesvd");
		String simula = request.getParameter("txtsimula");
		float taxa = Float.parseFloat(request.getParameter("txttaxa"));
		taxa /= 100;
		
		String tipo = "";
		if(itens.equalsIgnoreCase("tabela"))
			tipo = "Itens da Tabela";
		else
			tipo = "Listas Registradas";
		
		List<Demanda> listademanda = new ArrayList<>();
		
		DAODemanda dao = new DAODemanda();
		
		listademanda = dao.relatorioDemanda(dataini, datafim, ano, TESVenda, taxa, itens, familia, simula);

		response.setContentType("application/vnd.ms-excel");
    	response.setHeader("Content-Disposition", "attachment; filename="+beginArq+"-PV_"+ano+"-"+f.format(hoje)+".xls");
    	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
    	response.setHeader("Cache-Control", "no-cache");
    	
    	ServletContext context = request.getSession().getServletContext();
    	
    	String realPath = context.getRealPath("/");
    	    	
		String templatePath = realPath + "/resources/xls/demandaTemplate.xls";		
		
		InputStream is = new FileInputStream(templatePath);
		
		Map<String, Object> beans = new HashMap<>();
		beans.put("listad", listademanda);
		beans.put("ano", ano);
		beans.put("distribuidor", uf);
		beans.put("considerar", tipo);
		beans.put("ano1", (anoatual-2));
		beans.put("ano2", (anoatual-1));
		beans.put("ano3", (anoatual));
		XLSTransformer transformer = new XLSTransformer();
				
		Workbook workbook = transformer.transformXLS(is, beans);
				
		workbook.write(response.getOutputStream());
		
	}

	private void salvarEscola(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nome, classificacao, cnpj, endereco, complemento, bairro, municipio, uf, cep, email, telefone, dependencia;
		
		int berc=0, mat1=0, mat2=0, mat3=0, inf1=0, inf2=0, ano1=0, ano2=0, ano3=0, ano4=0, ano5=0,
		ano6=0, ano7=0, ano8=0, ano9=0, ser1=0, ser2=0, ser3=0, eja=0, supl=0;

		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		Usuario usuario = (Usuario) session.getAttribute("usuariologado");
	
		int setor = Integer.parseInt(request.getParameter("txtsetor"));
		nome = request.getParameter("txtnome");
		classificacao = request.getParameter("txtclassificacao");
		cnpj = request.getParameter("cnpj");
		cnpj = cnpj.replace('.', ';');
		cnpj = cnpj.replace('/', ';');
		cnpj = cnpj.replace('-', ';');
		String novocnpj[] = cnpj.split(";");
		cnpj = "";
		for (String s : novocnpj){
			cnpj += s;
		}
		endereco = request.getParameter("txtendereco");
		complemento = request.getParameter("txtcomplemento");
		bairro = request.getParameter("txtbairro");
		municipio = request.getParameter("txtmunicipio");
		uf = request.getParameter("txtuf");
		cep = request.getParameter("cep");
		cep = cep.replace('.', ';');
		cep = cep.replace('-', ';');
		String novocep[] = cep.split(";");
		cep = "";
		for (String s : novocep){
			cep += s;
		}
		email = request.getParameter("txtemail");
		telefone = request.getParameter("txttelefone");
		dependencia = request.getParameter("txtdependencia");
		
		Escola escola = new Escola();
		
		escola.setNome(nome);
		escola.setClassificacao(classificacao);
		escola.setCnpj(cnpj);
		escola.setEndereco(endereco);
		escola.setComplemento(complemento);
		escola.setBairro(bairro);
		escola.setMunicipio(municipio);
		escola.setUf(uf);
		escola.setCep(cep);
		escola.setEmail(email);
		escola.setTelefone(telefone);
		escola.setSetor(setor);
		escola.setDependencia(dependencia);
		escola.setUser_id(usuario.getId());
		
		escola.setInfantil0(berc);
		escola.setInfantil1(mat1);
		escola.setInfantil2(mat2);
		escola.setInfantil3(mat3);
		escola.setInfantil4(inf1);
		escola.setInfantil5(inf2);
		escola.setAno1(ano1);
		escola.setAno2(ano2);
		escola.setAno3(ano3);
		escola.setAno4(ano4);
		escola.setAno5(ano5);
		escola.setAno6(ano6);
		escola.setAno7(ano7);
		escola.setAno8(ano8);
		escola.setAno9(ano9);
		escola.setSerie1(ser1);
		escola.setSerie2(ser2);
		escola.setSerie3(ser3);
		escola.setEja(eja);
		escola.setSupletivo(supl);
		
		DAOEscola dao = new DAOEscola();
		
		String mensagem = "";
		
		if(dao.salvar(escola)){
        	mensagem = "Escola cadastrada com sucesso!";
		}else{
        	mensagem = "Erro ao cadastrar a escola!";
		}

		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);						
	}

	private void exportarNotaFiscal(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String fornecedor = null;
		String nota = null;
		String filial = null;
		String serie = null;
        String tipoexport = request.getParameter("tipoexport");
        
        DAONotaFiscal dao = new DAONotaFiscal();
        
        if(tipoexport.equalsIgnoreCase("ftd")) {
        	fornecedor = request.getParameter("fornecedor");
        	nota = request.getParameter("txtnota");
        }else {
        	filial = request.getParameter("filial");
        	nota = request.getParameter("numeronotafiscal");        	
        	serie = request.getParameter("serienotafiscal");        	
        }
        
		
		try {
			dao.exportaNotaFiscal(tipoexport, response, nota, fornecedor, filial, serie);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void importarNotaFiscal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		String tipoimport = request.getParameter("tipoimport");

		NotaFiscal nota = new NotaFiscal();
		DAONotaFiscal dao = new DAONotaFiscal();

		String mensagem = "Notas: ";

		
		if(tipoimport.equals("arquivo")) {
			
			List<File> files = importArquivoMultiple(request, response);			

			try {
				
				for(File f : files) {
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					dbFactory.setNamespaceAware(false);
			        DocumentBuilder dBuilder;
			        dBuilder = dbFactory.newDocumentBuilder();
			        Document doc = dBuilder.parse(f);
	
	
			        nota = importaXML(doc);
					
					mensagem = mensagem + "["+dao.salvar(nota)+"] ";
					
				}
			
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}else {

			mensagem = "Nota importada via CHAVE!";
			String chave = request.getParameter("chave_notafiscal");
			NfeImportaXML.Download(chave);
			
		}
		
		
	
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
		response.getWriter().write(jsonObject);		
	}

	
	public static NotaFiscal importaXML(Document doc) throws DOMException, ParseException, SQLException{

		NotaFiscal nota = new NotaFiscal();
		List<ItemNotaFiscal> itens = new ArrayList<>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dt = null;
		Date emissao = null;	
		
		Element e = null;
		doc.getDocumentElement().normalize();
		
		e = doc.getDocumentElement();
		
		//pegando o numero da nota, emissao, e s�rie do xml
		nota.setIdnota(e.getElementsByTagName("nNF").item(0).getTextContent());		
		dt = (java.util.Date) df.parse(e.getElementsByTagName("dhEmi").item(0).getTextContent());
		emissao = new Date(dt.getTime());
		nota.setEmissao(emissao);
		nota.setSerie(e.getElementsByTagName("serie").item(0).getTextContent());
		nota.setCfop(e.getElementsByTagName("CFOP").item(0).getTextContent());
		nota.setNatop(e.getElementsByTagName("natOp").item(0).getTextContent());
		
		
		//pegando os dados do emitente
		NodeList nEmit = doc.getElementsByTagName("emit");
		Element emit = (Element) nEmit.item(0);
		nota.setUF(emit.getElementsByTagName("UF").item(0).getTextContent());
		nota.setCnpjemit(emit.getElementsByTagName("CNPJ").item(0).getTextContent());
		nota.setEmitente(emit.getElementsByTagName("xNome").item(0).getTextContent());
		nota.setMunicipio(emit.getElementsByTagName("xMun").item(0).getTextContent());
		
		//pegando os dados do destinatario
		NodeList nDest = doc.getElementsByTagName("dest");
		Element dest = (Element) nDest.item(0);
		nota.setCnpj(dest.getElementsByTagName("CNPJ").item(0).getTextContent());
		
		//pegando os dados dos produtos
		NodeList prods = doc.getElementsByTagName("det");

		for(int temp = 0; temp < prods.getLength(); temp++){
			Element prod = (Element) prods.item(temp);

			String codigo = null, descricao = null, codbar = null, codbarant = null;
			float quantidade = 0, desconto = 0, total = 0, preco;
			
			codigo = prod.getElementsByTagName("cProd").item(0).getTextContent();
			descricao = prod.getElementsByTagName("xProd").item(0).getTextContent();
			codbar = prod.getElementsByTagName("cEAN").item(0).getTextContent();
			codbarant = prod.getElementsByTagName("cEANTrib").item(0).getTextContent();
			descricao = descricao.toUpperCase();
			quantidade = Float.parseFloat(prod.getElementsByTagName("qCom").item(0).getTextContent());
			if(prod.hasAttribute("vDesc")){
				desconto = Float.parseFloat(prod.getElementsByTagName("vDesc").item(0).getTextContent());
			}
			
			total = Float.parseFloat(prod.getElementsByTagName("vProd").item(0).getTextContent());

			total -= desconto;
			preco = Float.parseFloat(prod.getElementsByTagName("vUnTrib").item(0).getTextContent());
			total = preco*quantidade;

			Produto item = null;
			/*if(codigo.length() < 8) {
				item = DAOProduto.getProdutoFromCodBar(codbar, codbarant);
				if(item == null) {
					System.out.println("NOTA Nº "+nota.getIdnota()+" >>> Item: "+codigo+" Descrição: "+descricao+" Quantidade: "+quantidade+" não foi contrado!");
				}else {
					item.setPreco(preco);
					item.setCodbar(codbar);	
					itens.add(new ItemNotaFiscal(item, quantidade, preco, total));
				}
			}else {*/
				item = new Produto();
				item.setCodigo(codigo);
				item.setDescricao(descricao);
				item.setCodbar(codbar);
				item.setPreco(preco);				
				itens.add(new ItemNotaFiscal(item, quantidade, preco, total));
			//}
						
		}
		
		//pegando o numero do pedido de compra
		int idpedido = 0;
		NodeList nPedido = doc.getElementsByTagName("compra");
		if(nPedido.getLength()>0){
			if(nPedido.item(0).hasChildNodes()){
				idpedido = Integer.parseInt(nPedido.item(0).getFirstChild().getTextContent());	
			}
		}
		nota.setIdpedido(idpedido);
		
		//pegando os totais da nota
		NodeList nTot = doc.getElementsByTagName("total");
		Element totais = (Element) nTot.item(0);
		float desconto = Float.parseFloat(totais.getElementsByTagName("vDesc").item(0).getTextContent());
		float total = Float.parseFloat(totais.getElementsByTagName("vProd").item(0).getTextContent());
		float liquido = Float.parseFloat(totais.getElementsByTagName("vNF").item(0).getTextContent());
		nota.setDesconto(desconto);
		nota.setTotal(total);
		nota.setLiquido(liquido);
		nota.setPercentual((desconto/total));
		nota.setItens(itens);

		return nota;
	}
	
	
	
	@SuppressWarnings("static-access")
	private void detalharAdocaoTodos(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		Escola escola = (Escola) session.getAttribute("escola");
		Usuario usuario = (Usuario) session.getAttribute("usuariologado");
		
		DAOEscola dao = new DAOEscola();
		String tabela = "atual";
		Orcamento orcamento = new Orcamento();
		orcamento.setEscola(escola);
		
		dao.detalharTodasSeries(orcamento, tabela, usuario);		
		
		orcamento.refazTotal();
		
		session.removeAttribute("listaadocao");
		
		session.setAttribute("orcamento", orcamento);
		response.sendRedirect("adocaoDetalharTodos.jsp");		
	}

	private void selecionaAdocoes(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		String[] ids = request.getParameterValues("idadocao");
		EscolaAdocoes listaadocao = (EscolaAdocoes) session.getAttribute("listaadocao");
		
		Orcamento orcamento = new Orcamento();
		
		Escola escola = listaadocao.getEscola();
		orcamento.setEscola(escola);
		
		DAOEscola dao = new DAOEscola();
		dao.detalharAdocoes(orcamento, ids);
		
		orcamento.refazTotal();
		
		session.setAttribute("orcamento", orcamento);
		response.sendRedirect("adocaoDetalhar.jsp");
		
	}
	
	private void detalharAdocao(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(false);
		
		if(session == null){
			session = request.getSession(true);
		}
		
		session.removeAttribute("orcamento");
		
		Usuario usuario = (Usuario) session.getAttribute("usuariologado");
		
		String idescola = request.getParameter("txtidescola");
		int id = Integer.parseInt(idescola);		
		String[] serie = request.getParameterValues("txtserie");
		String ano = request.getParameter("txtano");
		String tabela = request.getParameter("txttabela");
		
		Adocao adocao = new Adocao();
		adocao.setIdescola(id);
		adocao.setSeries(serie);
		adocao.setAno(ano);
		
		Escola escola = new Escola();
		escola.setId(id);
		DAOEscola.recarrega(escola);
		
		Usuario vendedor = DAOUsuario.getVendedor(escola.getSetor());
		
		String series = "";
		for(String s : serie){
			series += "[" + s + "]";
		}
		
		Orcamento orcamento = new Orcamento();
		orcamento.setEscola(escola);
		orcamento.setSerie(series);
		orcamento.setAno(ano);
		orcamento.setVendedor(vendedor);
		
		if(!serie[0].equalsIgnoreCase("Todos")){
						
			DAOEscola.recarrega(adocao, tabela);
			orcamento.setIdadocao(adocao.getIdadocao());
			if(adocao.getItens()!=null){				
				orcamento.setItens(adocao.getItens());
				orcamento.getEscola().setAudit(adocao.getAudit());
				DAOEscola.setDescontoBonus(orcamento);
				orcamento.refazTotal();
			}else{
				orcamento.setItens(null);
				orcamento.setTotal(0);
			}
		}else{
			DAOEscola.detalharTodasSeries(orcamento, tabela, usuario);
		}
				
	
		session.setAttribute("orcamento", orcamento);
		
	}

	private void pesquisarAdocao(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		Escola escola = (Escola) session.getAttribute("escola");
		DAOEscola dao = new DAOEscola();
		EscolaAdocoes listaadocao = dao.listarAdocoes(escola);
		session.setAttribute("listaadocao", listaadocao);
		response.sendRedirect("adocaoPesquisar.jsp");
	}

	private void registrarAdocao(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		Usuario usuario = (Usuario) session.getAttribute("usuariologado");


		Adocao adocao = (Adocao) session.getAttribute("adocao");
		Orcamento orcamento = (Orcamento) session.getAttribute("orcamento");
		
		String mensagem = "";
		
		List<ItemAdocao> itensadocao = new ArrayList<>();
		
		for(ItemOrcamento i : orcamento.getItens()){
			ItemAdocao a = new ItemAdocao();
			a.setCodigo(i.getProduto().getCodigo());
			a.setPreco(i.getProduto().getPreco());
			a.setSerie(i.getProduto().getSerie());
			itensadocao.add(a);
		}
		
		session.removeAttribute("orcamento");
		session.removeAttribute("adocao");
		
		Collections.sort(itensadocao);
		
		adocao.setItensadocao(itensadocao);
		
		adocao.setUser_id(usuario.getId());
		
		if(adocao.getIdadocao() != 0){
			boolean ok = DAOEscola.editarAdocao(adocao);
			if(ok)
				mensagem = "Adocao alterada com sucesso!";
			else
				mensagem = "Erro ao tentar alterar a adocao!";
		}else{
			mensagem = DAOEscola.salvarAdocoes(adocao);
		}
				
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);						
		
	}
	
	//retorna o ano-calend�rio referente ao per�odo de venda em quest�o
	private int getAnoPeriodo(){
		
		Calendar agora = Calendar.getInstance();
			
		int mes = agora.get(Calendar.MONTH);
		int ano = agora.get(Calendar.YEAR);

		if(mes > 6){
			ano += 1;
		}
		return ano;
	}
	
	private void esquecerEscola(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		session.removeAttribute("escola");
		session.removeAttribute("orcamento");
		response.sendRedirect("index.jsp");
	}

	private void selecionarEscola(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("txtidescola"));
		Escola escola = new Escola();
		escola.setId(id);
		DAOEscola.recarrega(escola);
		
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		
		session.removeAttribute("escola");
		session.setAttribute("escola", escola);
		pesquisarAdocao(request, response);
	}

	private void pesquisarEscolas(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Escola> escolas = new ArrayList<>();
		List<EscolaMin> escmin = new ArrayList<>();

		DAOEscola dao = new DAOEscola();
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		
		Usuario usuario = (Usuario) session.getAttribute("usuariologado");
		
		if(usuario == null){
			usuario = new Usuario();
			usuario.setCargo(2);
			usuario.setSetor(0);
		}
		
		String nome = request.getParameter("txtdescricao");

		
		if(nome.equalsIgnoreCase("todos")){
			escmin = dao.listarmin(usuario);
		}
		else{
			escolas = dao.pesquisar(usuario, nome);

			for(Escola e : escolas){
				EscolaMin esc = new EscolaMin();
				esc.setId(e.getId());
				esc.setIdvend(e.getVendedor().getId());
				esc.setText(e.getNome());
				esc.setEndereco(e.getEndereco()+" / "+e.getComplemento()+" / "+e.getBairro()+" / "+e.getMunicipio());
				escmin.add(esc);
			}
			session.removeAttribute("escolas");
			session.setAttribute("escolas", escolas);
		}
		
		Gson gson = new Gson();
		Type listType = new TypeToken<ArrayList<EscolaMin>>(){}.getType();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(escmin, listType);		
		response.getWriter().write(jsonObject);

	}


	private void recuperarEscola(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		Escola escola = new Escola();
		
		String nome = request.getParameter("txtdescricao");
		
		escola.setId(Integer.parseInt(nome));
		
		DAOEscola.recarrega(escola);
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(escola);		
		response.getWriter().write(jsonObject);

	}

	
	private void vinculaNotaPedido(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		// TODO Auto-generated method stub
		String idnota = request.getParameter("txtnota");
		int idpedido = Integer.parseInt(request.getParameter("txtpedido"));
		DAONotaFiscal dao = new DAONotaFiscal();
		String mensagem = "Erro ao vincular a Nota "+idnota+" ao pedido "+idpedido+"!";
		
		try {
			if(dao.vinculaNota(idnota, idpedido)){
				mensagem = "Nota "+idnota+" vinculada ao pedido "+idpedido+"!";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);				        

	}

	private void usuarioLogout(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if (session != null) {
		    session.invalidate();
		}
		response.sendRedirect("index.jsp");	
	}

	private void usuarioLogon(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException, DOMException, ParserConfigurationException, SAXException, ParseException, SQLException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		session.setMaxInactiveInterval(2*60*60);
		String login = request.getParameter("txtlogin");
		String senha = request.getParameter("txtsenha");
		String usertype = request.getParameter("usertype");
				
		Usuario usuarioLogado = null;
		
		String mensagem = "erro";
		
		if(!login.equals("") || !senha.equals("")) {
		
			if(usertype.equalsIgnoreCase("usuario")){
				DAOUsuario dUsuario = new DAOUsuario();
				usuarioLogado = dUsuario.validaUsuario(login, senha);
			}else{
				DAOEmpresa dao = new DAOEmpresa();
				usuarioLogado = dao.validaLivreiro(login, senha);
			}
			
			Date keyDate = DAOKeypass.getDataLimite();
			
			if(keyDate == null){
				response.sendRedirect("keypass.jsp");
			}else if(!KeyPass.checkKeyPass(keyDate)){
			   response.sendRedirect("keypass.jsp");
			}else{
				
				if(!(usuarioLogado == null)) {
					session.setAttribute("usuariologado", usuarioLogado);
					session.setAttribute("nomeusuario", usuarioLogado.getNome());
					mensagem = "ok";
				}
			}
		}
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);		

	}

	private void removeItem(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		
		Orcamento orcamento = (Orcamento) session.getAttribute("orcamento");
		
		String codigo = request.getParameter("codigo");

		ItemOrcamento item = new ItemOrcamento();
		Produto produto = new Produto();
		produto.setCodigo(codigo);
		DAOProduto.recarrega(produto);
		item.setProduto(produto);

		orcamento.remove(item);
		
		String mensagem = orcamento.getTotalitens()+"";
		
		session.removeAttribute("orcamento");
		session.setAttribute("orcamento", orcamento);
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);		
		
	}

	private void descartarOrcamento(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		session.removeAttribute("orcamento");
		response.sendRedirect("index.jsp");
	}

	private void adicionaItem(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException {
		// TODO Auto-generated method stub
		

		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}


		Orcamento orcamento = (Orcamento) session.getAttribute("orcamento");
		
		if(orcamento == null){
			orcamento = new Orcamento();
		}

		String codigo = request.getParameter("codigo");
		int quantidade = Integer.parseInt(request.getParameter("quantidade"));
		Produto produto = new Produto();
		produto.setCodigo(codigo);
		DAOProduto.recarrega(produto);
		ItemOrcamento item = new ItemOrcamento();
		item.setProduto(produto);
		item.setQuantidade(quantidade);
		orcamento.adiciona(item);
				
		DAOProduto.setEstoque(orcamento.getItens());
		
		session.removeAttribute("orcamento");
		session.setAttribute("orcamento", orcamento);
		
		String mensagem = orcamento.getTotalitens()+"";
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);		
		
	}
	
	private void detalharPedido(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		String idpedido = request.getParameter("idpedido");

		int id = Integer.parseInt(idpedido);
		DAOPedido dao = new DAOPedido();
		Pedido pedido = null; 
		
		try {
			pedido = dao.detalhar(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.setAttribute("detalhepedido", pedido);
		
	}

	

	private void detalharPedidoToExcel(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, RuntimeException, Exception{
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		
		Pedido pedido = new Pedido(); 
		
		response.setContentType("application/vnd.ms-excel");
    	response.setHeader("Content-Disposition", "inline; filename=pedidoDetalhado.xls");
    	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
    	
    	ServletContext context = request.getServletContext();
    	
    	String realPath = context.getRealPath("/");
    	    	
		String templatePath = realPath + "/resources/xls/pedidoTemplate.xls";		
		
		InputStream is = new FileInputStream(templatePath);
		
		pedido = (Pedido) session.getAttribute("detalhepedido");
		Map<String, Object> beans = new HashMap<>();
		beans.put("pedido", pedido);
		beans.put("itens", pedido.getItens());
		XLSTransformer transformer = new XLSTransformer();
				
		Workbook workbook = transformer.transformXLS(is, beans);
		
		workbook.write(response.getOutputStream());		

	}

	
	private void consultarPedido(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		
		
		String datainicial, datafinal;
		ArrayList<Pedido> pedidos = new ArrayList<>();
		
		datainicial = request.getParameter("dataini");
		datafinal = request.getParameter("datafim");
		
		Date dtini = convertStringToDate(datainicial);
		Date dtfim = convertStringToDate(datafinal);
		
		DAOPedido dao = new DAOPedido();

		try {
			pedidos = dao.listar(dtini, dtfim);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	session.setAttribute("listapedidos", pedidos);
	}

	//faz atualiza��o de pre�os com base em um arquivo .csv onde conste c�digo e pre�o
	private void atualizarPrecos(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			FileNotFoundException {
		
		String separador = request.getParameter("separador");

		FileInputStream arquivo = new FileInputStream(importArquivo(request, response));

		
        ArrayList<Produto> itens = new ArrayList<>();
		BufferedReader br = null;
		
		br = new BufferedReader(new InputStreamReader(arquivo));
		String linha = null;
		
		while((linha = br.readLine()) != null){
			String[] ln = null;
			if(separador.equals("pontovirgula")){
				ln = linha.split(";");
			}else if(separador.equals("espaco")){
				ln = linha.split(" ");
				ln[1] = ln[ln.length - 1];
			}
			Produto t = new Produto();
			t.setCodigo(ln[0]);
			t.setPreco(parseStringToFloat(ln[1]));
			itens.add(t);
		}
        
        DAOProduto daotab = new DAOProduto();

		int contador = 0;
		try {
			contador = daotab.atualizarPrecos(itens);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        arquivo.close();
        
        String mensagem = "";
        
        if(contador>0){
        	mensagem = contador+" preços atualizados com sucesso!";
        }else{
        	mensagem = "Erro ao importar o arquivo!";
        }
        
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);				        
	}
	
	
	//retorna o arquivo salvo via upload na pasta informada no realPath
	public File importArquivo(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, FileNotFoundException {
		
		String upload_dir = "uploadFiles";
        String realPath = getServletContext().getRealPath("");
        String uploadFilePath = realPath + File.separator + upload_dir;
        File output;
        
        // creates the save directory if it does not exists
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        
        String fileName = "";
         
        for (Part part : request.getParts()) {
            fileName = getFileName(part);
            
            if(!fileName.equals("")){
            	Path path = Paths.get(uploadFilePath + File.separator + fileName);
            	Files.deleteIfExists(path);
            	part.write(uploadFilePath + File.separator + fileName);
            	break;
            }
        }
        
        output = new File(uploadFilePath + File.separator + fileName);        
        
        return output;
        
	}
	

	//retorna os arquivos salvos via upload na pasta informada no realPath
	
	public List<File> importArquivoMultiple(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		List<File> files = new ArrayList<>();
		
		String upload_dir = "uploadFiles";
        String realPath = getServletContext().getRealPath("");
        String uploadFilePath = realPath + File.separator + upload_dir;

        // creates the save directory if it does not exists
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        
        for(Part part : request.getParts()) {
        	String fileName = getFileName(part);
            if(!fileName.equals("")){
            	Path path = Paths.get(uploadFilePath + File.separator + fileName);
            	Files.deleteIfExists(path);
            	part.write(uploadFilePath + File.separator + fileName);
            	files.add(new File(uploadFilePath + File.separator + fileName));
            	continue;
            }
        }
                
        return files;        
        
	}

	
	private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        System.out.println("content-disposition header= "+contentDisp);
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length()-1);
            }
        }
        return "";
    }
    
	//realiza a consulta aos dados cadastrais do produto e aos pedidos realizados para aquele produto
	@SuppressWarnings({ })
	private void consultarProduto(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, SQLException {
		String codigo = request.getParameter("codigo");
		Produto p = new Produto();
		p.setCodigo(codigo);
		DAOProduto.recarrega(p);
		DAOProduto.setEstoque(p);
		
	/*	request.setAttribute("consprd_servlet", cp);
		RequestDispatcher dsp = request.getRequestDispatcher("produtoConsultar.jsp");
		dsp.forward(request, response); */
		
		Gson gson = new Gson();
		String jsonObject = gson.toJson(p);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonObject);
	}
	
	//exporta os pre�os para um arquivo txt separado por espa�os no padr�o do sistema totvs
	private void exportPrecos(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		DAOProduto dao = new DAOProduto();
		String filtro = request.getParameter("filtro");
		String descricao = request.getParameter("txtdescricao");
		String separador = request.getParameter("separador");
		
		try {
			dao.exportaPrecos(response, separador, filtro, descricao);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	//importa o relat�rio de pedidos de um arquivo txt
	private void importPedido(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			FileNotFoundException {
        
		ImportaPedidoSP ipsp = new ImportaPedidoSP();
		
		String tipoped = request.getParameter("tipoimport");

		String linha = null;
		
		DAOPedido daoped = new DAOPedido();
		String sPedido = "";
        Pedido pedido = null;
        ArrayList<ItemPedido> itens = new ArrayList<>();
		
		String mensagem = "";
		
		if(tipoped.equals("arquivo")) {
			
			String separador = request.getParameter("separador");
	               
	        FileInputStream arquivo = new FileInputStream(importArquivo(request, response));
	        
			BufferedReader br = null;
			
			br = new BufferedReader(new InputStreamReader(arquivo));
	
			
			try {
				if(separador.equals("espaco")){
					mensagem = ipsp.importaTXT(br, daoped, pedido, itens, sPedido, linha);
				}else{
					mensagem = ipsp.importaCSV(br, daoped, pedido, itens, sPedido, linha);
				}
	
			} catch (ParseException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
			arquivo.close();
		}else {
			String textpedido = request.getParameter("txtpedcolado");
			String[] brln = textpedido.split("\n");
			
			try {
				mensagem = ipsp.importaTextArea(brln, daoped, pedido, itens, sPedido, linha);
			} catch (NumberFormatException | ParseException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
		response.getWriter().write(jsonObject);					

	}

	
	//importa o relat�rio de pedidos de um arquivo txt
	private void updateItemPedido(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			FileNotFoundException {
        
		ImportaPedidoSP ipsp = new ImportaPedidoSP();
		
		String linha = null;
		
		String sPedido = "";
		
		String mensagem = "";
		
		
        FileInputStream arquivo = new FileInputStream(importArquivo(request, response));
        
		BufferedReader br = null;
		
		br = new BufferedReader(new InputStreamReader(arquivo));

		try {
			
			mensagem = ipsp.updatePedidoCSV(br, sPedido, linha);

		} catch (ParseException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        
		arquivo.close();
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
		response.getWriter().write(jsonObject);					

	}

	
	public static Date convertStringToDate(String dt) {
		dt = dt.replaceAll("/", "");
		dt = dt.substring(4, 8)+"-"+dt.substring(2, 4) + "-" + dt.substring(0, 2);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;		
		
		try {
			date = new Date(df.parse(dt).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return date;
	}


	public static Date convertStringToDatePrev(String dt) {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;		
		
		try {
			date = new Date(df.parse(dt).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return date;
	}

	
	public static Date convertStringToDateS(String dt) {
		dt = dt.replaceAll("-", "");
		//dt = dt.substring(4, 8)+"-"+dt.substring(2, 4) + "-" + dt.substring(0, 2);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date date = null;		
		
		try {
			date = new Date(df.parse(dt).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return date;
	}

	
	//importa escolas de um arquivo .csv
	private void importCadastroEscola(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			FileNotFoundException {
		
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}

		Usuario usuario = (Usuario) session.getAttribute("usuariologado");

		String acao = request.getParameter("txtacao");
		
		String mensagem = "";
		      
		FileInputStream arquivo = new FileInputStream(importArquivo(request, response));
		
		ArrayList<Escola> itens = new ArrayList<>();
        
        BufferedReader br = null;
		
		br = new BufferedReader(new InputStreamReader(arquivo));
		String linha = null;
		String[] cabecalho = null;
		int count = 0;		
		while((linha = br.readLine()) != null){			
			String[] ln = linha.split(";");
			
				if(count==0){
					cabecalho = linha.split(";");
				}else{					
					Escola t = new Escola();
					t.setUser_id(usuario.getId());
					if(acao.equalsIgnoreCase("update")){
						t.setId(Integer.parseInt(ln[0]));
						DAOEscola.recarrega(t);
						for(int i = 0; i < cabecalho.length; i++){
							String s = cabecalho[i].toLowerCase();
							switch (s){
								case "nome":t.setNome(ln[i].toUpperCase());break;
								case "classificacao":t.setClassificacao(ln[i].toUpperCase());break; 
								case "dependencia":t.setDependencia(ln[i].toLowerCase());break;
								case "cnpj":t.setCnpj(ln[i]); break;
								case "endereco":t.setEndereco(ln[i].toUpperCase());break;
								case "complemento":t.setComplemento(ln[i]);break;
								case "bairro":t.setBairro(ln[i].toUpperCase());break;
								case "municipio":t.setMunicipio(ln[i].toUpperCase());break;
								case "uf":t.setUf(ln[i].toUpperCase());break;
								case "cep":t.setCep(ln[i]);break;
								case "email":t.setEmail(ln[i]);break;
								case "telefone":t.setTelefone(ln[i]);break;
								case "setor":t.setSetor(Integer.parseInt(ln[i]));break;
								case "idftd":t.setIdftd(Integer.parseInt(ln[i]));break;
								default: break;
							}												
						}
					}else{						
						for(int i = 0; i < cabecalho.length; i++){
							String s = cabecalho[i].toLowerCase();
							switch (s){
								case "nome":t.setNome(ln[i].toUpperCase());break;
								case "classificacao":t.setClassificacao(ln[i].toUpperCase());break; 
								case "dependencia":t.setDependencia(ln[i].toLowerCase());break;
								case "cnpj":t.setCnpj(ln[i]); break;
								case "endereco":t.setEndereco(ln[i].toUpperCase());break;
								case "complemento":t.setComplemento(ln[i]);break;
								case "bairro":t.setBairro(ln[i].toUpperCase());break;
								case "municipio":t.setMunicipio(ln[i].toUpperCase());break;
								case "uf":t.setUf(ln[i].toUpperCase());break;
								case "cep":t.setCep(ln[i]);break;
								case "email":t.setEmail(ln[i]);break;
								case "telefone":t.setTelefone(ln[i]);break;
								case "setor":t.setSetor(Integer.parseInt(ln[i]));break;
								case "idftd":t.setIdftd(Integer.parseInt(ln[i]));break;
								default: break;
							}												
						}
					}
					itens.add(t);
				}				
				count++;
		}
		arquivo.close();
		DAOEscola dao = new DAOEscola();
		int contador = 0;
		try {
			contador = dao.importarCadastroEscola(itens, acao);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
        
        
        if(contador>0){
        	mensagem = contador+" itens salvos com sucesso!";
        }else{
        	mensagem = "Erro ao importar o arquivo!";
        }
        
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
		response.getWriter().write(jsonObject);
		
	}
	
		
	
	//importa a tabela de pre�os de um arquivo .csv
	private void importCadastroProduto(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			FileNotFoundException {
		String acao = request.getParameter("txtacao");
		
		String mensagem = "";
		      
		FileInputStream arquivo = new FileInputStream(importArquivo(request, response));
		
		ArrayList<Produto> itens = new ArrayList<>();
        
        BufferedReader br = null;
		
		br = new BufferedReader(new InputStreamReader(arquivo));
		String linha = null;
		String[] cabecalho = null;
		int count = 0;		
		while((linha = br.readLine()) != null){
			
			String[] ln = linha.split(";");
				if(count==0){
					cabecalho = linha.split(";");
				}else{					
					Produto t = new Produto();
					String c = ln[0];
					c = c.replace('"', ' ');
					c = c.trim();
					t.setCodigo(c);
					
					//Recarregando o produto caso seja um update
					if(acao.equalsIgnoreCase("update")){
						DAOProduto.recarrega(t);
					}		
					
					//percorrendo o cabeçalho do arquivo e pegando os itens
					for(int i = 1; i < cabecalho.length; i++){
						String s = cabecalho[i].toLowerCase();
						s = s.replace('"', ' ');
						s = s.trim();
						String l;
						if(ln.length < i+1){
							l = "";
						}else{
							l = ln[i];
						}

						switch (s){
							case "descricao":t.setDescricao(l.toUpperCase());break;
							case "preco":t.setPreco(parseStringToFloat(l));break; 
							case "codbar":t.setCodbar(l); break;
							case "obs":t.setObs(l.toUpperCase());break;
							case "serie":t.setSerie(l);break;
							case "autor":t.setAutor(l.toUpperCase());break;
							case "nivel":t.setNivel(l);break;
							case "familia":t.setFamilia(l);break;
							case "colecao":t.setColecao(l);break;
							case "disciplina":t.setDisciplina(l.toUpperCase());break;
							case "editora":t.setEditora(l);break;
							case "ativo":t.setAtivo(Integer.parseInt(l));break;
							case "marketshare":t.setMarketshare(Integer.parseInt(l));break;
							case "grupo":t.setGrupo(l);break;
							case "paginas":t.setPaginas(Integer.parseInt(l));break;
							case "lancto":t.setLancto(l);break;
							case "peso":t.setPeso(parseStringToFloat(l));break;
							case "status":t.setStatus(l);break;
							case "imagem":t.setImagem(l);break;
							default: break;
						}
						
					}

					itens.add(t);
				}				
				count++;
		}
		
		DAOProduto dao = new DAOProduto();
		int contador = 0;
		try {
			contador = dao.importarCadastroProdutos(itens, acao);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        arquivo.close();
        
        
        
        if(contador>0){
        	mensagem = contador+" itens salvos com sucesso!";
        }else{
        	mensagem = "Nenhum item foi importado!";
        }
        
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
		response.getWriter().write(jsonObject);
		
	}

	//salva as altera��es de um determinado produto no banco de dados
	@SuppressWarnings({ "unchecked", "static-access" })
	private void editarProduto(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		
		List<Produto> itens = (List<Produto>) session.getAttribute("tabela_servlet");
		
		if(itens == null){
			session = request.getSession(true);
			itens = (List<Produto>) session.getAttribute("tabela_servlet");
		}
		
		String codigo, descricao, preco, autor, serie, codbar, nivel,
		familia, colecao, disciplina, editora, obs, imagem, grupo, lancto, status, xpeso;
		
		codigo = request.getParameter("txtcodigo");
		descricao = request.getParameter("txtdescricao");
		preco = request.getParameter("txtpreco");
		autor = request.getParameter("txtautor");
		codbar = request.getParameter("txtcodbar");
		serie = request.getParameter("txtserie");
		nivel = request.getParameter("txtnivel");
		familia = request.getParameter("txtfamilia");
		colecao = request.getParameter("txtcolecao");
		obs = request.getParameter("txtobs").toUpperCase();
		disciplina = request.getParameter("txtdisciplina");
		editora = request.getParameter("txteditora");
		imagem = request.getParameter("txtimagem");
		grupo = request.getParameter("txtgrupo");
		xpeso = request.getParameter("txtpeso");
		float prc = parseStringToFloat(preco);		
		int marketshare = Integer.parseInt(request.getParameter("txtmarketshare"));
		float peso = parseStringToFloat(xpeso);
		int paginas = Integer.parseInt(request.getParameter("txtpaginas"));;		
		lancto = request.getParameter("txtlancto");
		status = request.getParameter("txtstatus");
		int inativo = Integer.parseInt(request.getParameter("txtativo"));;
		
		Produto p = new Produto();
		p.setCodigo(codigo);
		p.setDescricao(descricao);
		p.setPreco(prc);
		p.setAutor(autor);
		p.setCodbar(codbar);
		p.setSerie(serie);
		p.setNivel(nivel);
		p.setFamilia(familia);
		p.setColecao(colecao);
		p.setDisciplina(disciplina);
		p.setEditora(editora);
		p.setObs(obs);
		p.setAtivo(inativo);
		p.setImagem(imagem);
		p.setGrupo(grupo);
		p.setMarketshare(marketshare);
		p.setPeso(peso);
		p.setPaginas(paginas);
		p.setLancto(lancto);
		p.setStatus(status);
		
		DAOProduto dao = new DAOProduto();
		boolean flag = false;
		try {
			flag = dao.alterarProduto(p);
			dao.setEstoque(p);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String mensagem = "";
		if(flag){
			int i;
			if(itens != null){
				i = itens.indexOf(p);
				if(inativo == 1){
					itens.remove(i);
				}else{
					itens.set(i, p);
				}				
			}
			mensagem = "Produto alterado com sucesso!";
		}else{
			mensagem = "Erro ao tentar alterar o produto!";
		}
		
		session.setAttribute("tabela_servlet", itens);
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(mensagem);		
				
		response.getWriter().write(jsonObject);		
		
	}
	
	
	//faz a pesquisa a itens de lan�amento da tabela retornando codigo, nome
	private void lancamentos() throws ServletException, IOException {
				
		Usuario usuario = null;
		ArrayList<Produto> array = new ArrayList<>();
		int ano = getAnoPeriodo();

		DAOProduto dao = new DAOProduto();
		try {
			array = dao.lancamentos(ano+"", usuario);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<ProdutoMin> descricoes = new ArrayList<>();
		for(Produto p : array){
			ProdutoMin pm = new ProdutoMin();
			pm.setId(p.getCodigo());
			pm.setText(p.getDescricao());
			pm.setAutor(p.getAutor());
			descricoes.add(pm);
		}

		getServletContext().setAttribute("lanctos", array);
		
	}
	
	
	
	//faz a pesquisa a um determinado item da tabela retornando codigo, nome, pre�o e estoque
	private void pesquisar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}


				
		Usuario usuario = null;
		usuario = (Usuario) session.getAttribute("usuariologado"); 
		
		String descricao = "";
		ArrayList<Produto> array = new ArrayList<>();
		descricao = request.getParameter("txtdescricao");

		if(descricao.equalsIgnoreCase("") || descricao.equalsIgnoreCase(" ")){
			session.setAttribute("tabela_servlet", null);
	
		}else{		
			DAOProduto dao = new DAOProduto();
			try {
				array = dao.pesquisar(descricao, usuario);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ArrayList<ProdutoMin> descricoes = new ArrayList<>();
			for(Produto p : array){
				ProdutoMin pm = new ProdutoMin();
				pm.setId(p.getCodigo());
				if(!p.getObs().equalsIgnoreCase(""))
					pm.setText(p.getDescricao());
				else
					pm.setText(p.getDescricao());
				pm.setAutor(p.getAutor());
				descricoes.add(pm);
			}

			session.setAttribute("tabela_servlet", array);
			
			Gson gson = new Gson();
			Type listType = new TypeToken<ArrayList<ProdutoMin>>(){}.getType();

			response.setContentType("application/json");
			response.setCharacterEncoding("ISO-8859-1");
			
			String jsonObject = gson.toJson(descricoes, listType);		
			response.getWriter().write(jsonObject);			
		}
	}

	//retorna os dados do produto para edi��o
	private void detalheProduto(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, SQLException {
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}


		String codigo = request.getParameter("txtcodigo");
		
		Produto p = new Produto();
		p.setCodigo(codigo);
		DAOProduto.recarrega(p);
		DAOProduto.setEstoque(p);

		session.setAttribute("detalheproduto", p);
		response.sendRedirect("produtoDetalhe.jsp");
	}

	
	//retorna os dados do produto para edi��o
	private void dadosProduto(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String codigo = request.getParameter("txtcodigo");
		
		DAOProduto dao = new DAOProduto();
		Produto p = new Produto();
		try {
			p = dao.dadosProduto(codigo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Gson gson = new Gson();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String jsonObject = gson.toJson(p);		
		response.getWriter().write(jsonObject);					
	}
	
/*	private void gerarRelatorioProduto(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		ServletContext contextoServlet = getServletContext();
		OutputStream out = response.getOutputStream();
		
		//Gera��o do relat�rio
		ControlReport cReport = new ControlReport();
		JasperPrint rel = cReport.gerarReportProduto(contextoServlet);
		
		//Exporta��o em PDF do arquivo via outputstream do servlet
		JRExporter exporterPDF = new JRPdfExporter();
		exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, rel);
		exporterPDF.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
		
		try {
			exporterPDF.exportReport();
			
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Visualiza��o do arquivo pelo pr�prio visualizador
		//JasperViewer.viewReport(rel, false);
		
	}
	*/
	
	private float parseStringToFloat(String preco){
		float valor = 0;
		String p = preco.replace('.', ' ');
		p = p.replaceAll("\\s", "");
		p = p.replace(',', '.');
		valor = Float.parseFloat(p);
		return valor;
	}
	
	private int parseStringToInt(String numero){
		int valor = 0;
		String p = numero.replace('.', ' ');
		//p = p.substring(0, p.indexOf(','));
		p = p.replaceAll("\\s", "");
		valor = Integer.parseInt(p);
		return valor;
	}
	
}
