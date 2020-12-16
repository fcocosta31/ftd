package br.ftd.crawler;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class HTMLJsoup {
	
	//private Connection con;
	
	public HTMLJsoup() {
			 
    }
	
	private static Document sendRequest(String url) {
        Document doc = null;
        try {
        	InputStream inStream = new URL(url).openStream();        	
            doc = Jsoup.parse(inStream, "UTF-8",url);
            return doc;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }	
	
	public List<Carrousel> getCarrouselFTD(String html){
		Document doc;
		Elements body;
		Elements elements;
		Elements links;

		//con = Jsoup.connect(html);				
		List<Carrousel> objs = new ArrayList<Carrousel>();
		
		try {
				 // Parse the String into a Jsoup Document
				 Carrousel carrouselObj;
				 doc = sendRequest(html); //con.timeout(3*1000).get();
				 body = doc.body().children();				 
				 elements = body.select(Parametros.TAGCarrousel);
				 links = elements.select("a[href]");
	 
				 for (Element i : links) {
					 carrouselObj = new Carrousel();				 
					 carrouselObj.setUrlLink(i.absUrl("href"));					 
					 objs.add(carrouselObj);
				 }
				 
				 links = elements.select("a[title]");
				 int count = 0;
				 for (Element i : links) {					 
					 objs.get(count).setTitleLink(i.attr("title"));
					 count++;					 
				 }
				 
				 links = elements.select("img");
				 count = 0;				 
				 for (Element i : links) {
					 if(i.absUrl("src").isEmpty()) {
						 objs.get(count).setUrlImage(i.absUrl("data-lazy"));
						 System.out.println(i.absUrl("data-lazy"));						 
					 }else {
						 objs.get(count).setUrlImage(i.absUrl("src"));
						 System.out.println(i.absUrl("src"));
					 }					 					 
					 count++;					 
				 }
				 
		 } catch (Exception erro) {
				 System.out.println(erro.getMessage());
		 }
		
		return objs;
	}
	
	public List<Abrelivros> getNoticiasAbrelivros(String html){
		Document doc;
		Elements body;
		Elements elements;
		Elements links;
		
		//con = Jsoup.connect(html);
		List<Abrelivros> objs = new ArrayList<Abrelivros>();
		Abrelivros abrelivros;

		doc = sendRequest(html);//con.timeout(5*1000).get();
		body = doc.body().children();				 
		elements = body.select(Parametros.TAGNoticias);
		links = elements.select("article.elementor-post");
		for(Element i : links){
			abrelivros = new Abrelivros();
			abrelivros.setA_href(i.select("a[href").first().absUrl("href"));
			abrelivros.setA_text(i.select("a[href").first().text());
			abrelivros.setP_text(i.select("p").text());
			String author_post = i.select("div.elementor-post__meta-data > span.elementor-post-author").text();
			String data_post = i.select("div.elementor-post__meta-data > span.elementor-post-date").text();
			abrelivros.setDd_text(data_post);
			abrelivros.setAuthor(author_post);
			objs.add(abrelivros);
		}
		 
		
		return objs;
	}
	
	public List<MEC> getCarrouselMEC(String html){
		Document doc;
		Elements body;
		Elements div;
		Elements elements;
		Elements links;

		//con = Jsoup.connect(html);				
		List<MEC> objs = new ArrayList<MEC>();
		
		try {
				 // Parse the String into a Jsoup Document
				 MEC carrouselObj;
				 doc = sendRequest(html);//con.get();
				 body = doc.body().children();				 
				 elements = body.select(Parametros.TAG_MEC_Carousel);
				 div = elements.select(Parametros.TAG_MEC_Carousel_Inner);
				 links = div.select("a[href]");
	 
				 for (Element i : links) {
					 carrouselObj = new MEC();				 
					 carrouselObj.setA_href(i.select("a[href").first().absUrl("href"));
					 carrouselObj.setA_text(i.select("a[title").first().text());
					 if(!carrouselObj.getA_text().equals("")){
						 objs.add(carrouselObj); 
					 }
				 }

				 links = elements.select("img[src]");
				 int count = 0;
				 for (Element i : links) {					 
					 objs.get(count).setImg_src(i.absUrl("src"));
					 count++;					 
				 }
				 
				 				  
		 } catch (Exception erro) {
				 System.out.println(erro.getMessage());
				 erro.printStackTrace();
		 }
		
		return objs;
	}
}
