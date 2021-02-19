package br.ftd.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.ejb.Asynchronous;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.ejb.Singleton;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

import br.ftd.entity.ItemOrcamento;
import br.ftd.entity.ItemPedCliente;
import br.ftd.entity.Orcamento;
import br.ftd.entity.Params;
import br.ftd.entity.PedCliente;
import br.ftd.entity.Usuario;
import br.ftd.model.DAOParams;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

@Singleton
public class JavaMailApp2 {
	
	private final static Params params = DAOParams.getSystemParams();
	private final static String mailuser = params.getLoginmail();
	private final static String mailsender = params.getCcmails();
	private final static String password = params.getPswdmail();
	private final static String filial = params.getPagetitle();
	private final static String fones = params.getPagefone();
	private final static String emailfilial = params.getPageemail();
	private final static String smtp_host = "mail.smtp.host";
	private final static String smtp_gmail = "smtp.gmail.com";
	private final static String smtp_ftdrp = "smtp-vip-farm64.kinghost.net";
	private final static String socketfactory_port = "mail.smtp.socketFactory.port";
	private final static String socketfactory_class = "mail.smtp.socketFactory.class";
	private final static String socketfactory_ssl = "javax.net.ssl.SSLSocketFactory";
	private final static String smtp_auth = "mail.smtp.auth";
	private final static String smtp_port = "mail.smtp.port";
	private final static String port_gmail = "465";
	private final static String port_ftdrp = "587";
	private Properties props;
	
	private MimeMessage message;
	
	private Session session;
	
	public JavaMailApp2(){		
		
		props = new Properties();
	    /** Par�metros de conex�o com servidor Gmail */
		if(params.getPageuf().equalsIgnoreCase("Maranhão")) {
		    props.put(smtp_host, smtp_gmail);
		    props.put(socketfactory_port, port_gmail);
		    props.put(socketfactory_class, socketfactory_ssl);
		    props.put(smtp_auth, "true");		
		    props.put(smtp_port, port_gmail);
		}else {
		    props.put(smtp_host, smtp_ftdrp);
		    props.put(socketfactory_port, port_ftdrp);
		    props.put(socketfactory_class, socketfactory_ssl);
		    props.put(smtp_auth, "true");				    
		    props.put(smtp_port, port_ftdrp);
		}
	    
	}
	
	@Asynchronous
	@Lock(LockType.READ)
	public void asyncMail(@Observes(during = TransactionPhase.AFTER_SUCCESS) MailEvent event) {
		
		System.out.println("Enviando e-mail para "+event.getTo());
		
		try {
			Transport.send(message);
		}catch(MessagingException e){
            System.out.println("Ops, email nao pode ser enviado para "+event.getTo());
            throw new RuntimeException(e);			
		}
		
	}
	
	private boolean isValidEmail(String email) {
		boolean isValid = true;
		try {
			
			if(email.indexOf(",") > -1) {
				String[] mails = email.split(",");
				if(mails.length > 1) {
					for (String ml : mails) {
						InternetAddress internetAddress = new InternetAddress(ml);
						internetAddress.validate();
					}
				}
			}else {
				InternetAddress internetAddress = new InternetAddress(email);
				internetAddress.validate();
			}
			
		}catch(AddressException e) {
			System.out.println(">>>>>>>>>>>> ::::::::::: Email inválido: "+email);
			isValid = false;
		}
		
		return isValid;
	}
	
	public String sendMail(Orcamento orcam, String nome, String fone, String email){
		
		email = email.trim();
		
	    NumberFormat formatter = NumberFormat.getCurrencyInstance();
	    	    
	    session = Session.getInstance(props,
	                new javax.mail.Authenticator() {
	                     protected PasswordAuthentication getPasswordAuthentication() 
	                     {
	                           return new PasswordAuthentication(mailuser, password);
	                     }
	                });
	    /** Ativa Debug para sess�o */
	    session.setDebug(true);
	    try {
	
	          message = new MimeMessage(session);
	          message.setFrom(new InternetAddress(mailuser)); //Remetente
	          
	          Address[] toUser = InternetAddress //Destinat�rio(s)
	                     .parse(email);	          
	          message.setRecipients(Message.RecipientType.TO, toUser);
	          
	          message.setSubject("Orçamento de Livros");//Assunto
	          StringBuilder ms = new StringBuilder();

	          ms.append("<html><body>"
	          		+ "<h3>Orçamento de Livros</h3>Data: ");
	          ms.append(new Date(System.currentTimeMillis())+"<br/><br/>");
	          ms.append("Sr(a). "+nome+"<br/>");
	          ms.append("Fone: "+fone+"<br/><br/>");
	          ms.append("<table style='border:1px solid black'>"
	          		+ "<tr><th>Código</th><th>Descrição</th><th>Qtde</th>"
	          		+ "<th>Pr.Unit</th><th>Pr.Liq.</th><th>Total</th></tr>");

	          for(ItemOrcamento i : orcam.getItens()){
		          ms.append("<tr style='border:1px solid black'>");
	        	  ms.append("<td>");
	        	  ms.append(i.getProduto().getCodigo());
	        	  ms.append("</td>");
	        	  ms.append("<td>");
	        	  ms.append(i.getProduto().getDescricao());
	        	  ms.append("</td>");
	        	  ms.append("<td>");
	        	  ms.append(i.getQuantidade());
	        	  ms.append("</td>");
	        	  ms.append("<td>");
	        	  double preco = i.getProduto().getPreco();
	        	  double precoliq = i.getPrecoliquido();
	        	  ms.append(formatter.format(preco));
	        	  ms.append("</td>");
	        	  ms.append("<td>");
	        	  ms.append(formatter.format(precoliq));
	        	  ms.append("</td>");
	        	  ms.append("<td>");
	        	  double total = i.getPrecoliquido() * i.getQuantidade();
	        	  ms.append(formatter.format(total));
	        	  ms.append("</td>");	        	 
		          ms.append("</tr>");
	          }

	          ms.append("<tr>");
        	  ms.append("<td colspan='2'>"+orcam.getTotalitens()+" itens"+"</td>");
        	  ms.append("<td>"+orcam.getQtdtotal()+"</td>");
        	  ms.append("<td>"+formatter.format(orcam.getTotal())+"</td>");
        	  ms.append("<td colspan='2'>"+formatter.format(orcam.getTotaliquido())+"</td>");
        	  ms.append("</tr>");
        	  ms.append("</table>");
        	  
        	  ms.append("<br/><h4>"+filial+"</h4>");
        	  ms.append("<p>"+fones+"<br>");
        	  ms.append("<p>E-mail: "+emailfilial+"</p>");
        	  ms.append("</body></html>");
	          message.setText(ms.toString(), "utf-8", "html");

	          /**M�todo para enviar a mensagem criada*/	          	     

	          MailEvent event = new MailEvent();
	          event.setTo(email);
	          
	          this.asyncMail(event);
	          
	          return "Enviado para "+email;
	          
	     } catch (MessagingException e) {
	          return e.getMessage();
	    }
	}
	

	public String sendMailPedCliente(PedCliente pedido, String pretitle, String title, Usuario usuario){

	    @SuppressWarnings("unused")
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
	    @SuppressWarnings("unused")
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	    String destinatarios = mailsender;
	    String mensagem = "Pedido "+pedido.getIdpedido()+title+" com sucesso!";
	    String email = pedido.getCliente().getEmail().trim();
	    
	    if(pedido.isSendmail() && this.isValidEmail(email)) {
    		destinatarios = email+", "+mailsender;   	
	    }

		    session = Session.getInstance(props,
		                new javax.mail.Authenticator() {
		                     protected PasswordAuthentication getPasswordAuthentication() 
		                     {
		                           return new PasswordAuthentication(mailuser, password);
		                     }
		                });
		    /** Ativa Debug para sess�o */
		    session.setDebug(true);
		    try {
		
		          message = new MimeMessage(session);
		          message.setFrom(new InternetAddress(mailuser)); //Remetente
		
		          Address[] toUser = InternetAddress //Destinat�rio(s)
		                     .parse(destinatarios);  
		          message.setRecipients(Message.RecipientType.TO, toUser);
		          message.setSubject(pretitle+" "+pedido.getIdpedido()+" "+title+" - Cliente: "+pedido.getCliente().getCodigoftd()+"-"+pedido.getCliente().getRazaosocial());
		          //Assunto
		          StringBuilder ms = new StringBuilder();
	
		          ms.append("<html><body>"
		          		+ "<h3>Pedido nº:"+pedido.getIdpedido()+"</h3>");
		          ms.append("Data: "+pedido.getEmissao()+"<br/>");
		          ms.append("Cliente: "+pedido.getCliente().getRazaosocial()+"<br/>");
		          ms.append("Endereço: "+pedido.getCliente().getEndereco()+" / "+pedido.getCliente().getMunicipio()+"-"+pedido.getCliente().getUf()+"<br/>");
		          ms.append("Status: "+pedido.getSituacao()+"<br/><br/>");
		          ms.append("<table style='border:1px solid black'>"
		          		+ "<tr><th>Código</th><th>Descrição</th><th>Qt.Ped.</th>"
		          		+ "<th>Qt.Atend.</th><th>Qt.Pend.</th></tr>");
	
		          for(ItemPedCliente i : pedido.getItens()){
			          ms.append("<tr style='border:1px solid black'>");
		        	  ms.append("<td>");
		        	  ms.append(i.getItem().getCodigo());
		        	  ms.append("</td>");
		        	  ms.append("<td>");
		        	  ms.append(i.getItem().getDescricao());
		        	  ms.append("</td>");
		        	  ms.append("<td>");
		        	  ms.append(i.getQtdpedida());
		        	  ms.append("</td>");
		        	  ms.append("<td>");
		        	  ms.append(i.getQtdatendida());
		        	  ms.append("</td>");
		        	  ms.append("<td>");
		        	  ms.append(i.getQtdpendente());
		        	  ms.append("</td>");	        	 
			          ms.append("</tr>");
		          }
	
		          ms.append("<tr>");
	        	  ms.append("<td colspan='2'>"+pedido.getQtdtotal()+" itens");
	        	  ms.append("</td>");
	        	  ms.append("<td colspan='3'>");
	        	  //ms.append("Valor total: "+formatter.format(pedido.getTotal()));
	        	  ms.append("</td>");
	        	  ms.append("</tr>");
	        	  ms.append("</table><br/><br/>");
		          ms.append("Implantado por: "+pedido.getUsuario().getNome()+ "<br/>");
		          ms.append("Atualizado por: "+usuario.getNome()+"<br/>");
	        	  ms.append("</body></html>");
		          message.setText(ms.toString(), "utf-8", "html");
	
		          /**M�todo para enviar a mensagem criada*/
		          MailEvent event = new MailEvent();
		          event.setTo(destinatarios);
		          
		          this.asyncMail(event);

		          
		          return mensagem;
		     } catch (MessagingException e) {
		          return e.getMessage();
		    }	   	   
	    
	}


	public String sendMail(String realPath, Orcamento orcamento, String nome, String endereco, 
			String bairro, String cidade, String cep, String fone, String cpf, String email, Usuario usuario, String observacao){		
		
		email = email.trim();
		
		//ENVIANDO O E-MAIL
	    session = Session.getInstance(props,
	                new javax.mail.Authenticator() {
	                     protected PasswordAuthentication getPasswordAuthentication() 
	                     {
	                           return new PasswordAuthentication(mailuser, password);
	                     }
	                });
	    /** Ativa Debug para sess�o */
	    session.setDebug(true);
	    try {
	
	          message = new MimeMessage(session);
	          BodyPart msbodypart = new MimeBodyPart();
	          Multipart multpart = new MimeMultipart();
	          message.setFrom(new InternetAddress(mailuser)); //Remetente
	
	          Address[] toUser = InternetAddress //Destinat�rio(s)
	                     .parse(mailsender+", "+usuario.getEmail().trim()+", "+"");  
	          message.setRecipients(Message.RecipientType.TO, toUser);
	          message.setSubject("Emitir Nota Fiscal - Cliente: "+nome+" / "+cidade);//Assunto
	          StringBuilder ms = new StringBuilder();

	          ms.append("<html><body>"
	          		+ "<h3>Pedido para emissão de Nota Fiscal</h3><br/>Data: ");
	          ms.append(new Date(System.currentTimeMillis())+"<br/><br/>");
	          ms.append("Nome cliente: "+nome+"<br/>");
	          ms.append("Endereço: "+endereco+"<br/>");
	          ms.append("Bairro: "+bairro+"<br/>");
	          ms.append("Cidade: "+cidade+"<br/>");
	          ms.append("CEP: "+cep+"<br/>");
	          ms.append("E-mail: "+email+"<br/>");
	          ms.append("Fone: "+fone+"<br/>");
	          ms.append("CPF/CNPJ: "+cpf+"<br/>");
	          ms.append("Observação: "+observacao+"<br/><br/>");
	          ms.append("Usuário solicitante: "+usuario.getNome()+ "<br/>");
	          ms.append("E-mail: "+usuario.getEmail());
	          ms.append("</body></html>");
	        
	        MimeBodyPart htmlPart = new MimeBodyPart();
	        htmlPart.setContent( ms.toString(), "text/html; charset=utf-8" );
 
	  		//CRIANDO A PLANILHA EXCEL    	

	        String templatePath = realPath + "/resources/xls/orcamentoTemplate.xls";		
	  		
	  		String fileName = "orcamento.xls";
	  		
	  		String upload_dir = "uploadFiles";
	  		
	        String uploadFilePath = realPath + File.separator + upload_dir;
	        
	        // creates the save directory if it does not exists
	        File fileSaveDir = new File(uploadFilePath);
	        if (!fileSaveDir.exists()) {
	            fileSaveDir.mkdirs();
	        }	  			        
	        
	  		FileOutputStream file = new FileOutputStream(uploadFilePath + File.separator + fileName);
	  		
	  		InputStream is = new FileInputStream(templatePath);
	  				
	  		Map<String, Object> beans = new HashMap<>();
	  		beans.put("orcam", orcamento);
	  		beans.put("itens", orcamento.getItens());

	  		XLSTransformer transformer = new XLSTransformer();
	  				
	  		Workbook workbook = transformer.transformXLS(is, beans);
	  		
	  		workbook.write(file);		
	        
	  		FileDataSource source = new FileDataSource(uploadFilePath + File.separator + fileName);
	  		
	  		msbodypart.setDataHandler(new DataHandler(source));
	  		msbodypart.setFileName(fileName);
	        multpart.addBodyPart(msbodypart);
	        multpart.addBodyPart(htmlPart);
	        
	        message.setContent(multpart);
	  		
	        /**M�todo para enviar a mensagem criada*/
	          MailEvent event = new MailEvent();
	          event.setTo(email);
	          
	          this.asyncMail(event);
	    
	    } catch (MessagingException e) {
	          return e.getMessage();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParsePropertyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    return "Enviado para "+mailsender;
	    
	}
	
	
	@SuppressWarnings("unused")
	public String sendMailPedido(PedCliente pedido, String nome, String cnpj, String formapgto, String transp, String obs, Usuario usuario){		
		
		String resposta = "Pedido nº "+pedido.getIdpedido()+" gerado com sucesso!";
		
	    NumberFormat formatter = NumberFormat.getCurrencyInstance();
	    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	    
	    String mailcliente = pedido.getCliente().getEmail().trim();
	    String destinatarios = mailsender;
	    
	    if(this.isValidEmail(mailcliente)) {
	    	destinatarios = mailcliente+","+mailsender;
	    }else {
	    	resposta += " [Obs.: o e-mail: "+mailcliente+" não é válido]";
	    }
	    
	    session = Session.getInstance(props,
	                new javax.mail.Authenticator() {
	                     protected PasswordAuthentication getPasswordAuthentication() 
	                     {
	                           return new PasswordAuthentication(mailuser, password);
	                     }
	                });
	    /** Ativa Debug para sess�o */
	    session.setDebug(true);
	    try {
	
	          message = new MimeMessage(session);
	          message.setFrom(new InternetAddress(mailuser)); //Remetente
	
	          Address[] toUser = InternetAddress //Destinat�rio(s)
	                     .parse(destinatarios);  
	          message.setRecipients(Message.RecipientType.TO, toUser);
	          message.setSubject("Cliente-Pedido nº "+pedido.getIdpedido()+" "+"implantado Cliente: "+pedido.getCliente().getCodigoftd()+"-"+pedido.getCliente().getRazaosocial()+" "
	          		+" - Data: "+pedido.getEmissao());//Assunto
	          StringBuilder ms = new StringBuilder();

	          ms.append("<html><body>"
	          		+ "<h3>Pedido nº:"+pedido.getIdpedido()+"</h3>");
	          ms.append("Data: "+pedido.getEmissao()+"<br/>");
	          ms.append("Cliente: "+pedido.getCliente().getRazaosocial()+"<br/>");
	          ms.append("CNPJ: "+cnpj+"<br/>");
	          ms.append("Endereço: "+pedido.getCliente().getEndereco()+" / "+pedido.getCliente().getMunicipio()+"-"+pedido.getCliente().getUf()+"<br/>");
	          ms.append("Status: "+pedido.getSituacao()+"<br/>");
	          ms.append("Telefone de Contato: "+formapgto+"<br/>");
	          ms.append("Transportadora: "+transp+"<br/>");
	          ms.append("Observações: "+obs+"<br/>");
	          ms.append("Usuário solicitante: "+usuario.getNome()+ "<br/>");
	          ms.append("E-mail: "+usuario.getEmail()+"<br/><br/>");
	          
	          ms.append("<table style='border:1px solid black'>"
	          		+ "<tr><th>Código</th><th>Descrição</th><th>Qt.Ped.</th>"
	          		+ "<th>Qt.Atend.</th><th>Qt.Pend.</th></tr>");

	          for(ItemPedCliente i : pedido.getItens()){
		          ms.append("<tr style='border:1px solid black'>");
	        	  ms.append("<td>");
	        	  ms.append(i.getItem().getCodigo());
	        	  ms.append("</td>");
	        	  ms.append("<td>");
	        	  ms.append(i.getItem().getDescricao());
	        	  ms.append("</td>");
	        	  ms.append("<td>");
	        	  ms.append(i.getQtdpedida());
	        	  ms.append("</td>");
	        	  ms.append("<td>");
	        	  ms.append(i.getQtdatendida());
	        	  ms.append("</td>");
	        	  ms.append("<td>");
	        	  ms.append(i.getQtdpendente());
	        	  ms.append("</td>");	        	 
		          ms.append("</tr>");
	          }

	          ms.append("<tr>");
        	  ms.append("<td colspan='2'>"+pedido.getQtdtotal()+" itens");
        	  ms.append("</td>");
        	  ms.append("<td colspan='3'>");
        	  //ms.append("Valor total: "+formatter.format(pedido.getTotal()));
        	  ms.append("</td>");
        	  ms.append("</tr>");
        	  ms.append("</table>");
        	  ms.append("</body></html>");
	          message.setText(ms.toString(), "utf-8", "html");

	          /**M�todo para enviar a mensagem criada*/
	          
	          MailEvent event = new MailEvent();
	          event.setTo(destinatarios);
	          
	          this.asyncMail(event);

	          return resposta;
	     } catch (MessagingException e) {
	          return e.getMessage();
	    }	    
	}	

	
	
	
}
