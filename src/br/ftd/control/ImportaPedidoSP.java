package br.ftd.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import br.ftd.entity.ItemPedido;
import br.ftd.entity.Pedido;
import br.ftd.entity.Produto;
import br.ftd.model.DAOPedido;

public class ImportaPedidoSP {

	public String importaTXT(BufferedReader br, DAOPedido daoped, Pedido pedido, ArrayList<ItemPedido> itens, String sPedido, String linha) throws NumberFormatException, IOException, ParseException, SQLException {
		sPedido = "";
		linha = null;
		int pedidoatual = 0;
		boolean flag = false;
		
		while((linha = br.readLine()) != null){
			String[] ln = linha.split("\\t", -1);
			if(linha.length() > 14){
				if(linha.substring(0, 7).equalsIgnoreCase("Pedido:")){
					if(pedidoatual > 0){
						sPedido += ";"+pedido.getIdpedido();
						pedido.setItens(itens);
						flag = daoped.salvar(pedido);
						itens = new ArrayList<>();
						pedidoatual = 0;
					}
					String st = linha.substring(8,15).trim();
					int idped = Integer.parseInt(st);
					pedidoatual++;
					pedido = new Pedido();
					pedido.setIdpedido(idped);
					Date date = new Date(System.currentTimeMillis());//convertStringToDate(dt);
					pedido.setData(date);
				}else if(linha.substring(3, 10).equalsIgnoreCase("Pedido:")){
					if(pedidoatual > 0){
						sPedido += ";"+pedido.getIdpedido();
						pedido.setItens(itens);
						flag = daoped.salvar(pedido);
						itens = new ArrayList<>();
						pedidoatual = 0;
					}						
					String st = linha.substring(11,20).trim();
					int idped = Integer.parseInt(st);
					pedidoatual++;
					pedido = new Pedido();
					pedido.setIdpedido(idped);
					Date date = new Date(System.currentTimeMillis());//convertStringToDate(dt);
					pedido.setData(date);						
				}
			}
			if(ln.length == 7){				
				if(ln[1].trim().matches("^[\\d].*")){					
					String s2 = null;
					if(ln[2].contains(".")){
						int i = ln[2].indexOf(".");
						s2 = ln[2].substring(0, i) + ln[2].substring(i+1);
						s2 = s2.trim();
						//s2 = s2.substring(0, s2.indexOf(','));
					}else{
						s2 = ln[2];
						s2 = s2.trim();
						//s2 = s2.substring(0, s2.indexOf(','));
					}						
					int qtd = Integer.parseInt(s2);
					String d = ln[6];
					Date date = ControlServlet.convertStringToDate(d);
					ItemPedido itempedido = new ItemPedido();
					Produto item = new Produto();
					String codref = ln[1].substring(0, ln[1].indexOf(' '));
					codref = codref.trim();
					item.setCodigo(codref);
					itempedido.setItem(item);
					itempedido.setQtdpedida(qtd);
					itempedido.setPrevisao(date);
					itens.add(itempedido);
				}
			}
			else if(ln.length == 8){
				if(ln[0].trim().matches("^[\\d].*")){						
					String s2 = null;
					if(ln[2].contains(".")){
						int i = ln[2].indexOf(".");
						s2 = ln[2].substring(0, i) + ln[2].substring(i+1);
						s2 = s2.trim();
						//s2 = s2.substring(0, s2.indexOf(','));
					}else{
						s2 = ln[2];
						s2 = s2.trim();
						//s2 = s2.substring(0, s2.indexOf(','));
					}						
					int qtd = Integer.parseInt(s2);
					String d = ln[7];
					Date date = ControlServlet.convertStringToDateS(d);
					ItemPedido itempedido = new ItemPedido();
					Produto item = new Produto();
					String codref = ln[0];
					codref = codref.trim();
					item.setCodigo(codref);
					itempedido.setItem(item);
					itempedido.setQtdpedida(qtd);
					itempedido.setPrevisao(date);
					itens.add(itempedido);
				}					
			}
		}
		sPedido += ";"+pedido.getIdpedido();
		pedido.setItens(itens);
		flag = daoped.salvar(pedido);
		String mensagem;
		if(!flag){

        	mensagem = "Pedido "+sPedido+" ja existe ou houve um erro ao importar!";
        	
        }else{

        	mensagem = "Pedido(s) "+sPedido+" importado(s) com sucesso!";
        }
		
		return mensagem;
	}
	
	public String importaCSV(BufferedReader br, DAOPedido daoped, Pedido pedido, ArrayList<ItemPedido> itens, String sPedido, String linha) throws NumberFormatException, IOException, ParseException, SQLException {
		
		sPedido = "";
		linha = null;
		boolean flag = false;

		int idant = 0;
		int idpedido = 0;
		
		while((linha = br.readLine()) != null){
			
			String[] ln = linha.split(";", -1);
			String datapedido = ln[0];
			Date dataemissao = ControlServlet.convertStringToDate(datapedido);
			idpedido = Integer.parseInt(ln[1]);

			if(idant != idpedido){
				if(pedido != null){
					sPedido += ";"+pedido.getIdpedido();
					pedido.setItens(itens);
					flag = daoped.salvar(pedido);							
				}
				pedido = new Pedido();
				pedido.setIdpedido(idpedido);
				pedido.setData(dataemissao);
				itens = new ArrayList<>();
			}
			
			String codigoref = ln[2];
			String s2 = null;
			if(ln[3].contains(".")){
				int i = ln[3].indexOf(".");
				s2 = ln[3].substring(0, i) + ln[3].substring(i+1);
				s2 = s2.trim();
			}else{
				s2 = ln[3];
				s2 = s2.trim();
			}											
			int quantidade = Integer.parseInt(s2);
			String dataprevisao = ln[4];
			Date dataprev = ControlServlet.convertStringToDate(dataprevisao);
			Produto p = new Produto();
			ItemPedido i = new ItemPedido();
			p.setCodigo(codigoref);
			i.setItem(p);
			i.setQtdpedida(quantidade);
			i.setPrevisao(dataprev);
			i.setIdpedido(idpedido);
			itens.add(i);

			idant = idpedido;
		}

		sPedido += ";"+pedido.getIdpedido();
		pedido.setItens(itens);
		flag = daoped.salvar(pedido);		
		String mensagem;
		if(!flag){

        	mensagem = "Pedido "+sPedido+" ja existe ou houve um erro ao importar!";
        	
        }else{

        	mensagem = "Pedido(s) "+sPedido+" importado(s) com sucesso!";
        }
		
		return mensagem;
	}
	
	public String importaTextArea(String[] br, DAOPedido daoped, Pedido pedido, ArrayList<ItemPedido> itens, String sPedido, String linha) throws NumberFormatException, IOException, ParseException, SQLException {

		sPedido = "";
		linha = null;
		int pedidoatual = 0;
		boolean flag = false;
				
		for(int w = 0; w < br.length; w++) {
			linha = br[w];
			String[] ln = linha.split("\\t", -1);
			if(linha.length() > 14){
				if(linha.substring(0, 7).equalsIgnoreCase("Pedido:")){
					if(pedidoatual > 0){
						sPedido += ";"+pedido.getIdpedido();
						pedido.setItens(itens);
						flag = daoped.salvar(pedido);
						itens = new ArrayList<>();
						pedidoatual = 0;
					}
					String st = linha.substring(8,15).trim();
					int idped = Integer.parseInt(st);
					pedidoatual++;
					pedido = new Pedido();
					pedido.setIdpedido(idped);
					Date date = new Date(System.currentTimeMillis());//convertStringToDate(dt);
					pedido.setData(date);
				}else if(linha.substring(3, 10).equalsIgnoreCase("Pedido:")){
					if(pedidoatual > 0){
						sPedido += ";"+pedido.getIdpedido();
						pedido.setItens(itens);
						flag = daoped.salvar(pedido);
						itens = new ArrayList<>();
						pedidoatual = 0;
					}						
					String st = linha.substring(11,20).trim();
					int idped = Integer.parseInt(st);
					pedidoatual++;
					pedido = new Pedido();
					pedido.setIdpedido(idped);
					Date date = new Date(System.currentTimeMillis());//convertStringToDate(dt);
					pedido.setData(date);						
				}
			}
			if(ln.length == 7){
				if(ln[1].trim().matches("^[\\d].*")){
					String s2 = null;
					if(ln[2].contains(".")){
						int i = ln[2].indexOf(".");
						s2 = ln[2].substring(0, i) + ln[2].substring(i+1);
						s2 = s2.trim();
						//s2 = s2.substring(0, s2.indexOf(','));
					}else{
						s2 = ln[2];
						s2 = s2.trim();
						//s2 = s2.substring(0, s2.indexOf(','));
					}						
					int qtd = Integer.parseInt(s2);
					String d = ln[6];
					Date date = ControlServlet.convertStringToDate(d);
					ItemPedido itempedido = new ItemPedido();
					Produto item = new Produto();
					String codref = ln[1].substring(0, ln[1].indexOf(' '));
					codref = codref.trim();
					item.setCodigo(codref);
					itempedido.setItem(item);
					itempedido.setQtdpedida(qtd);
					itempedido.setPrevisao(date);
					itens.add(itempedido);
				}
			}
			else if(ln.length == 8){
				if(ln[0].trim().matches("^[\\d].*")){
					String s2 = null;
					if(ln[2].contains(".")){
						int i = ln[2].indexOf(".");
						s2 = ln[2].substring(0, i) + ln[2].substring(i+1);
						s2 = s2.trim();
						//s2 = s2.substring(0, s2.indexOf(','));
					}else{
						s2 = ln[2];
						s2 = s2.trim();
						//s2 = s2.substring(0, s2.indexOf(','));
					}						
					int qtd = Integer.parseInt(s2);
					String d = ln[7];
					Date date = ControlServlet.convertStringToDate(d);
					ItemPedido itempedido = new ItemPedido();
					Produto item = new Produto();
					String codref = ln[0];
					codref = codref.trim();
					item.setCodigo(codref);
					itempedido.setItem(item);
					itempedido.setQtdpedida(qtd);
					itempedido.setPrevisao(date);
					itens.add(itempedido);
				}					
			}
		}
		sPedido += ";"+pedido.getIdpedido();
		pedido.setItens(itens);
		flag = daoped.salvar(pedido);
		String mensagem;
		if(!flag){

        	mensagem = "Pedido "+sPedido+" ja existe ou houve um erro ao importar!";
        	
        }else{

        	mensagem = "Pedido(s) "+sPedido+" importado(s) com sucesso!";
        }
		
		return mensagem;
	}


	public String updatePedidoCSV(BufferedReader br, String sPedido, String linha) throws NumberFormatException, IOException, ParseException, SQLException {
		
		linha = null;
		boolean flag = false;
		int idpedido = 0;
		DAOPedido dao = new DAOPedido();
		List<ItemPedido> itens = new ArrayList<>();
		
		while((linha = br.readLine()) != null){
			
			String[] ln = linha.split(";", -1);
			idpedido = Integer.parseInt(ln[0]);			
			String codigoref = ln[1];
			String dataprevisao = ln[2];
			String observacao = ln[3];
			Date dataprev = ControlServlet.convertStringToDate(dataprevisao);
			Produto p = new Produto();
			ItemPedido i = new ItemPedido();
			p.setCodigo(codigoref);
			i.setItem(p);
			i.setPrevisao(dataprev);
			i.setObservacao(observacao);
			i.setIdpedido(idpedido);
			itens.add(i);
		}

		flag = dao.update(itens);		
		String mensagem;
		if(!flag){

        	mensagem = " Houve um erro ao tentar salvar!";
        	
        }else{

        	mensagem = "Itens atualizados com sucesso!";
        }
		
		return mensagem;
	}
	
	
	
}
