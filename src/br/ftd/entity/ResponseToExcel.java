package br.ftd.entity;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;

@SuppressWarnings("deprecation")
public class ResponseToExcel {

	@SuppressWarnings("unused")
	public static void getExcel(List<String> cabecalho, List<Object> lista, HttpServletResponse response, String sheetname) throws IOException{

		HSSFWorkbook workbook = new HSSFWorkbook();    	
    	HSSFSheet sheet = workbook.createSheet(sheetname);
    	
    	HSSFFont boldFont = workbook.createFont();
        boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(boldFont);
    	
        int rownum = 0;
        
    	HSSFRow row = sheet.createRow(rownum++);
    	
    	int cellnum = 0;
    	
    	for(String nome : cabecalho){
        	HSSFCell cell = row.createCell(cellnum);
        	cell.setCellStyle(style);    	
        	cell.setCellValue(nome);
        	cellnum++;    		
    	}

    	for(Object item : lista){
    		row = sheet.createRow(rownum++);
    		for (cellnum = 0; cellnum < cabecalho.size(); cellnum++){
	    		HSSFCell cell = row.createCell(cellnum);
	    		cell.setCellValue("");
    		}
    	}
    	
    	for(int i = 0; i < cabecalho.size(); i++){
    		sheet.autoSizeColumn((short) i);
    	}
    	
    	workbook.write(response.getOutputStream());		
	}
	
	public static void getExcelEscolas(List<String> cabecalho, List<Escola> lista, HttpServletResponse response, String sheetname) throws IOException{

		HSSFWorkbook workbook = new HSSFWorkbook();    	
    	HSSFSheet sheet = workbook.createSheet(sheetname);
    	
    	HSSFFont boldFont = workbook.createFont();
        boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(boldFont);
    	
        int rownum = 0;
        
    	HSSFRow row = sheet.createRow(rownum++);
    	
    	int cellnum = 0;
    	
    	for(String nome : cabecalho){
        	HSSFCell cell = row.createCell(cellnum);
        	cell.setCellStyle(style);    	
        	cell.setCellValue(nome);
        	cellnum++;    		
    	}

    	for(Escola item : lista){
    		row = sheet.createRow(rownum++);
    		for (cellnum = 0; cellnum < cabecalho.size(); cellnum++){
	    		HSSFCell id = row.createCell(0);
	    		id.setCellValue(item.getId());
	    		HSSFCell idftd = row.createCell(1);
	    		idftd.setCellValue(item.getIdftd());
	    		HSSFCell nome = row.createCell(2);
	    		nome.setCellValue(item.getNome());
	    		HSSFCell endereco = row.createCell(3);
	    		endereco.setCellValue(item.getEndereco());
	    		HSSFCell bairro = row.createCell(4);
	    		bairro.setCellValue(item.getBairro());
	    		HSSFCell municipio = row.createCell(5);
	    		municipio.setCellValue(item.getMunicipio());
	    		HSSFCell uf = row.createCell(6);
	    		uf.setCellValue(item.getUf());
	    		HSSFCell cep = row.createCell(7);
	    		cep.setCellValue(item.getCep());
	    		HSSFCell email = row.createCell(8);
	    		email.setCellValue(item.getEmail());
	    		HSSFCell fone = row.createCell(9);
	    		fone.setCellValue(item.getTelefone());
	    		HSSFCell cnpj = row.createCell(10);
	    		cnpj.setCellValue(item.getCnpj());
	    		HSSFCell alunos = row.createCell(11);
	    		alunos.setCellValue(item.getTotalalunos());	    		
	    		HSSFCell setor = row.createCell(12);
	    		setor.setCellValue(item.getSetor());
	    		HSSFCell vendedor = row.createCell(13);
	    		vendedor.setCellValue(item.getVendedor().getNome());	    		
    		}
    	}
    	
    	for(int i = 0; i < cabecalho.size(); i++){
    		sheet.autoSizeColumn((short) i);
    	}
		response.setContentType("application/vnd.ms-excel");
    	response.setHeader("Content-Disposition", "attachment; filename=escolas.xls");
    	response.setHeader("Set-Cookie", "fileDownload=true; path=/");

    	workbook.write(response.getOutputStream());		
	}	

	public static void getExcelDoacoes(List<String> cabecalho, List<ItemOrcamento> lista, HttpServletResponse response, String sheetname) throws IOException{

		HSSFWorkbook workbook = new HSSFWorkbook();    	
    	HSSFSheet sheet = workbook.createSheet(sheetname);
    	
    	HSSFFont boldFont = workbook.createFont();
        boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(boldFont);
    	
        int rownum = 0;
        
    	HSSFRow row = sheet.createRow(rownum++);
    	
    	int cellnum = 0;
    	
    	for(String nome : cabecalho){
        	HSSFCell cell = row.createCell(cellnum);
        	cell.setCellStyle(style);    	
        	cell.setCellValue(nome);
        	cellnum++;    		
    	}
    	
    	for(ItemOrcamento item : lista){
    		row = sheet.createRow(rownum++);
    		for (cellnum = 0; cellnum < cabecalho.size(); cellnum++){
	    		HSSFCell codigo = row.createCell(0);
	    		codigo.setCellValue(item.getProduto().getCodigo());
	    		HSSFCell descricao = row.createCell(1);
	    		descricao.setCellValue(item.getProduto().getDescricao());
	    		HSSFCell quantidade = row.createCell(2);
	    		quantidade.setCellValue(item.getQuantidade());
    		}
    	}
    	
    	for(int i = 0; i < cabecalho.size(); i++){
    		sheet.autoSizeColumn((short) i);
    	}
		response.setContentType("application/vnd.ms-excel");
    	response.setHeader("Content-Disposition", "attachment; filename=doacoes.xls");
    	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
    	
    	workbook.write(response.getOutputStream());		
	}	


	public static void getExcelListagemDoacoes(List<String> cabecalho, List<Doacao> lista, HttpServletResponse response, String sheetname) throws IOException{

		HSSFWorkbook workbook = new HSSFWorkbook();    	
    	HSSFSheet sheet = workbook.createSheet(sheetname);
    	
    	HSSFFont boldFont = workbook.createFont();
        boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(boldFont);
    	
        int rownum = 0;
        
    	HSSFRow row = sheet.createRow(rownum++);
    	
    	int cellnum = 0;
    	HSSFCell celcab = row.createCell(cellnum);
    	celcab.setCellStyle(style);
    	celcab.setCellValue("RELATÓRIO SINTÉTICO DAS DOAÇÕES A FILHOS DE PROFESSORES");
    	CellRangeAddress region = new CellRangeAddress(0, 0, 0, 4);
    	sheet.addMergedRegion(region);
    	row = sheet.createRow(rownum++);
    	
    	for(String nome : cabecalho){
        	HSSFCell cell = row.createCell(cellnum);
        	cell.setCellStyle(style);    	
        	cell.setCellValue(nome);
        	cellnum++;    		
    	}

    	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	
    	String nomevendedor = "", nomeanterior = "";
    	int last = 0, current;
    	int qtdtotal = 0;
    	int qtdtotalgeral = 0;
    	double valortotal = 0;
    	double valortotalgeral = 0;
    	//DecimalFormat dfm = new DecimalFormat("#,##0.00");
    	NumberFormat dfm = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    	for(Doacao item : lista){
    		
    		row = sheet.createRow(rownum++);
    		
    		current = item.getUsuario().getSetor();
    		nomevendedor = item.getUsuario().getNome();
    		qtdtotalgeral += item.getQtdtotal();
    		valortotalgeral += item.getTotal();
    		
    		if(last == 0 || last == current){
    			qtdtotal += item.getQtdtotal();
    			valortotal+= item.getTotal();
    			last = current;
    		}else{
    			HSSFCell rodape = row.createCell(2);
    			rodape.setCellStyle(style);
    			rodape.setCellValue("Totais para o vendedor: "+nomeanterior);
    			HSSFCell qtd = row.createCell(3);
    			qtd.setCellStyle(style);
    			qtd.setCellValue(qtdtotal);
    			HSSFCell vlr = row.createCell(4);
    			vlr.setCellStyle(style);
    			vlr.setCellValue(dfm.format(valortotal));
    			qtdtotal = item.getQtdtotal();
    			valortotal = item.getTotal();
    			last = current;
    			row = sheet.createRow(rownum++);
    		}

    		for (cellnum = 0; cellnum < cabecalho.size(); cellnum++){
	    		HSSFCell iddoacao = row.createCell(0);
	    		iddoacao.setCellValue(item.getId());
	    		HSSFCell emissao = row.createCell(1);
	    		emissao.setCellValue(df.format(item.getEmissao()));
	    		HSSFCell professor = row.createCell(2);
	    		professor.setCellValue(item.getProfessor().getNome());
	    		HSSFCell quantidade = row.createCell(3);
	    		quantidade.setCellValue(item.getQtdtotal());
	    		HSSFCell valor = row.createCell(4);
	    		valor.setCellValue(dfm.format(item.getTotal()));    		
	    		HSSFCell escola = row.createCell(5);
	    		escola.setCellValue(item.getEscola().getNome());
	    		HSSFCell vendedor = row.createCell(6);
	    		vendedor.setCellValue(item.getUsuario().getNome());	    		
    		}
    		nomeanterior = nomevendedor;
    	}
    	
    	row = sheet.createRow(rownum++);
		HSSFCell rodape = row.createCell(2);
		rodape.setCellStyle(style);
		rodape.setCellValue("Totais para o vendedor: "+nomeanterior);
		HSSFCell qtd = row.createCell(3);
		qtd.setCellStyle(style);
		qtd.setCellValue(qtdtotal);
		HSSFCell vlr = row.createCell(4);
		vlr.setCellStyle(style);
		vlr.setCellValue(dfm.format(valortotal));

    	row = sheet.createRow(rownum++);
		HSSFCell rodapet = row.createCell(2);
		rodapet.setCellStyle(style);
		rodapet.setCellValue("TOTAL GERAL: ");
		HSSFCell qtdt = row.createCell(3);
		qtdt.setCellStyle(style);
		qtdt.setCellValue(qtdtotalgeral);
		HSSFCell vlrt = row.createCell(4);
		vlrt.setCellStyle(style);
		vlrt.setCellValue(dfm.format(valortotalgeral));
		
    	for(int i = 0; i < cabecalho.size(); i++){
    		sheet.autoSizeColumn((short) i);
    	}
		response.setContentType("application/vnd.ms-excel");
    	response.setHeader("Content-Disposition", "attachment; filename=listagemdoacoes.xls");
    	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
    	
    	workbook.write(response.getOutputStream());		
	}	
	
}
