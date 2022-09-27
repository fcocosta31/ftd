package br.ftd.control;

import java.security.KeyStoreException;
import java.util.List;

import com.fincatto.nfe310.classes.evento.downloadnf.NFDownloadNFeRetorno;
import com.fincatto.nfe310.classes.nota.NFNota;
import com.fincatto.nfe310.classes.nota.NFNotaInfoItem;
import com.fincatto.nfe310.parsers.NotaParser;
import com.fincatto.nfe310.webservices.WSFacade;

public class NfeImportaXML {

	private static NfeConfigNfe config;
	private static String cnpj = "41490756000143";
	
	public static void Download(String chave) {
		
		config = new NfeConfigNfe();

		try {
			config.getCertificadoKeyStore();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		NFDownloadNFeRetorno retorno;
		
		try {
						
			retorno = new WSFacade(config).downloadNota(cnpj, chave);
			final NFNota nota = new NotaParser().notaParaObjeto(retorno.getDataRetorno());;
			List<NFNotaInfoItem> itens = nota.getInfo().getItens();
			for(NFNotaInfoItem i : itens) {
				i.getProduto();
			}
			
			System.out.println(nota.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
