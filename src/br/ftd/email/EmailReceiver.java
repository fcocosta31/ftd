package br.ftd.email;

import java.io.File;
import java.io.IOException;
import javax.mail.PasswordAuthentication;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.sun.mail.pop3.POP3Folder;
import com.sun.mail.util.MailSSLSocketFactory;

import br.ftd.control.ControlServlet;
import br.ftd.entity.NotaFiscal;
import br.ftd.model.DAONotaFiscal;

public class EmailReceiver {
	
    /**
     * Returns a Properties object which is configured for a POP3/IMAP server
     *
     * @param protocol either "imap" or "pop3"
     * @param host
     * @param port
     * @return a Properties object
     * @throws GeneralSecurityException 
     */
    private Properties getServerProperties(String protocol, String host,
            String port) throws GeneralSecurityException {
        Properties properties = new Properties();
        
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put(String.format("mail.%s.ssl.trust", protocol), host);
        properties.put(String.format("mail.%s.ssl.checkserveridentity", protocol), "false");
        
        // server setting
        properties.put(String.format("mail.%s.host", protocol), host);
        properties.put(String.format("mail.%s.port", protocol), port);
        properties.put(String.format("mail.%s.auth", protocol), "true");
        if(protocol.equalsIgnoreCase("pop3s")) {
            properties.put(String.format("mail.%s.ssl.socketFactory", protocol), sf);
        	properties.put(String.format("mail.%s.ssl.enable", protocol), "true");
        	properties.put(String.format("mail.%s.starttls.enable", protocol), "true");
        	
            // SSL setting
            properties.setProperty(
                    String.format("mail.%s.socketFactory.class", protocol),
                    "javax.net.ssl.SSLSocketFactory");
            properties.setProperty(
                    String.format("mail.%s.socketFactory.fallback", protocol),
                    "false");
            properties.setProperty(
                    String.format("mail.%s.socketFactory.port", protocol),
                    String.valueOf(port));
        	
        }else {
        	properties.put(String.format("mail.%s.ssl.enable", protocol), "false");
        	properties.put(String.format("mail.%s.starttls.enable", protocol), "false");
        }
         
        return properties;
    }
 
    /**
     * Downloads new messages and fetches details for each message.
     * @param protocol
     * @param host
     * @param port
     * @param userName
     * @param password
     * @throws ParserConfigurationException 
     * @throws SAXException 
     * @throws ParseException 
     * @throws DOMException 
     * @throws SQLException 
     * @throws GeneralSecurityException 
     */
    public String downloadEmails(String protocol, String host, String port,
            String userName, String password, ServletContext context) throws ParserConfigurationException, SAXException, DOMException, ParseException, SQLException, GeneralSecurityException {       
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.DAY_OF_MONTH, -10);
    	Date begin = calendar.getTime();
    	System.out.println("Data inicial: "+begin.toString());
    	SearchTerm searchCondition = new SearchTerm() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean match(Message msg) {
				try {
					if(msg.getSubject().equalsIgnoreCase("NFe Nacional")
							&& msg.getSentDate().compareTo(begin) > 0) {
						return true;
					}
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// TODO Auto-generated method stub
				return false;
			}
		};
    	
    	Properties properties = getServerProperties(protocol, host, port);
        Session session = Session.getDefaultInstance(properties,
        		new Authenticator() {
        			protected PasswordAuthentication getPasswordAuthentication() {
        				return new PasswordAuthentication(userName, password);
        			}
				});
        String mensagem = "Notas: ";
        boolean flagpop3folder = false;
        try {
        	session.setDebug(true);
            // connects to the message store
            Store store = session.getStore(protocol);
            store.connect(host, userName, password);
 
            // opens the inbox folder
            Folder folderInbox = store.getFolder("INBOX");
            if(folderInbox instanceof POP3Folder) {
            	flagpop3folder = true;
            }
            folderInbox.open(Folder.READ_WRITE);           
            Flags seen = new Flags(Flags.Flag.SEEN);
            @SuppressWarnings("unused")
			FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
            Message messages[] = folderInbox.search(searchCondition);
            
    		String upload_dir = "uploadFiles";
            String realPath = context.getRealPath("");
            String uploadFilePath = realPath + File.separator + upload_dir;

            // creates the save directory if it does not exists
            File fileSaveDir = new File(uploadFilePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdirs();
            }
            
    		NotaFiscal nota = new NotaFiscal();
    		DAONotaFiscal dao = new DAONotaFiscal();
    		
    		if(messages.length < 1) {
    			mensagem = "Não há novas notas na caixa de e-mail!";
    		}else {
    			
    			
    			for (int i = 0; i < messages.length; i++) {
    				
                    Message msg = messages[i];

					Address[] fromAddress;
	                
	                fromAddress = msg.getFrom();

					String from = fromAddress[0].toString();
	                
	                String domain = "";
	                if(from.contains("@")) {
	                	domain = from.split("@")[1];
	                }	
	                
                    System.out.println(">>>> Data: "+msg.getSentDate().toString()+"   >>>>>  Assunto:"+msg.getSubject());

                    if(domain.equalsIgnoreCase("ftd.com.br") 
	                		&& msg.getSubject().equalsIgnoreCase("NFe Nacional"))
                    {
	                    msg.setFlag(Flags.Flag.SEEN, true);                    
	            		
	                	Multipart multiPart = (Multipart) msg.getContent();
	                	MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(1);
	                	String filename = MimeUtility.decodeText(part.getFileName());
	                	
	                	File f = new File(fileSaveDir + File.separator + filename);                    
	                	
	                	part.saveFile(f);
	                	
						DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
						dbFactory.setNamespaceAware(false);
				        DocumentBuilder dBuilder;
				        dBuilder = dbFactory.newDocumentBuilder();
				        Document doc = dBuilder.parse(f);
		
		
				        nota = ControlServlet.importaXML(doc);
						
						mensagem = mensagem + "["+dao.salvar(nota)+"] ";
                        break;	                    	 
                    }
	                 
                    
                    if(flagpop3folder) {
                    	msg.setFlag(Flags.Flag.SEEN, true);
                    }
                    
                }    			
    			
    		}    		
            
            // disconnect
            folderInbox.close(flagpop3folder);
            store.close();
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider for protocol: " + protocol);
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store!");
            ex.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
        	System.out.println("Could not get message content attachment!");
			e.printStackTrace();
		}
        
        return mensagem;
    }
 
}
