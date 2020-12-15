package br.ftd.crawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import com.lowagie.text.List;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class EscreverArquivoXM {
	public static void gravaXMLCarrousel(Map<String, Carrousel> itens, String localArquivo){
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("itens", List.class);
		File arquivo = new File(localArquivo + "/carrousel.xml");
		FileOutputStream gravar;
		
		try {
			gravar = new FileOutputStream(arquivo);
			gravar.write(xStream.toXML(itens).getBytes());
			gravar.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
