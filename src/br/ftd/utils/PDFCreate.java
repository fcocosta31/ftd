package br.ftd.utils;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import br.ftd.entity.Bonus;

@SuppressWarnings("deprecation")
public class PDFCreate {

	@SuppressWarnings({ "unused", "resource" })
	public static void bonusImprimir(Bonus bonus, HttpServletResponse response, ServletContext context) throws IOException {
		String arqpdf = "Bonus-"+bonus.getEscola().getNome()+"-"+bonus.getAno()+".pdf";
		
		try {
			PdfDocument pdfdoc = new PdfDocument(new PdfWriter(arqpdf));
			PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
			Document doc = new Document(pdfdoc);
			doc.setMargins(30,30,20,30);
			
            /*IMAGEM LOGOTIPO*/
            String img = context.getRealPath("/assets/images");
            Image image = new Image(ImageDataFactory.create(img+"/logofe.png"));
            image.setWidth(64);
            image.setHeight(46);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
