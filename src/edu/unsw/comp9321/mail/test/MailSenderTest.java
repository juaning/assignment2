package edu.unsw.comp9321.mail.test;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Session;
import javax.mail.URLName;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Reference;
import javax.naming.StringRefAddr;

import org.apache.naming.ResourceRef;

import edu.unsw.comp9321.mail.MailSender;
import edu.unsw.comp9321.mail.exceptions.MailSenderException;
import edu.unsw.comp9321.mail.exceptions.ServiceLocatorException;

import junit.framework.TestCase;
/**
 * This class is a JUnit 4 Test Case for testing MailSender class
 * 
 * We set up the Tomcat JNDI environment manually in this case. 
 * Then, we add a mail/Session object that refers to a MailSessionFactory
 * We set up the properties for the mail session 
 * The session factory provides a MailSession object with those properties 
 * 
 * These properties are mirrored in context.xml which is used by MailSender when
 * running inside a Tomcat container.
 * 
 * @author srikumarv
 *
 */
public class MailSenderTest extends TestCase {

	static Logger logger = Logger.getLogger(MailSenderTest.class.getName());
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		// To test JNDI outside Tomcat, we need to set these
		 // values manually ... (just for testing purposes)
		 System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
            "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES, 
            "org.apache.naming");            

        // Create InitialContext with java:/comp/env/mail
        InitialContext ic = new InitialContext();

        ic.createSubcontext("java:");
        ic.createSubcontext("java:comp");
        ic.createSubcontext("java:comp/env");
        ic.createSubcontext("java:comp/env/mail");
       
        //Construct a naming reference
        ResourceRef ref = new ResourceRef("javax.mail.Session", "mail session", "Shareable", "Container", true, 
        		                             "org.apache.naming.factory.MailSessionFactory", null);
        ref.add(new StringRefAddr("auth", "Container"));
        //SMTP host address of the mail provider
        // for e.g. smtp.gmail.com port 587
        ref.add(new StringRefAddr("mail.smtp.host","mail_provider_host"));
        ref.add(new StringRefAddr("mail.smtp.port", "mail_provider_port"));
        ref.add(new StringRefAddr("mail.smtp.auth","true"));
        //Replace as necessary
        ref.add(new StringRefAddr("mail.smtp.user","your_login"));
        ref.add(new StringRefAddr("password","your_password"));
        //Turn this off to avoid username and password appearing in log files.
        ref.add(new StringRefAddr("mail.debug","false"));
        ref.add(new StringRefAddr("mail.smtp.starttls.enable","true"));
        
        
        ic.bind("java:comp/env/mail/Session", ref);
	}
	
	public void testSession(){
		MailSender sender = null;
		try {
			sender = MailSender.getMailSender();
			assertNotNull(sender);
		} catch (ServiceLocatorException e) {
			e.printStackTrace();
			fail("Could not obtain session");
		} catch (MailSenderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Could not obtain session");
		}
	}
	
	public void testSendMessage(){
		MailSender sender = null;
		try{
			sender = MailSender.getMailSender();
			//Replace as necessary
			String fromAddress = "from_at_mail_provider";
			String toAddress = "to_at_mail_provider";
			String subject = "test";
			StringBuffer mailBody = new StringBuffer();
			mailBody.append("Dear Mailer, Why you email me?!!");
			sender.sendMessage(fromAddress, toAddress, subject, mailBody);
			assertTrue(true);
 		}catch(Exception e){
			e.printStackTrace();
			fail("MailSender did not send message");
		}
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
		InitialContext ic = new InitialContext();
		Context subContext = (Context) ic.lookup("java:comp");
		logger.info("Found sub-context");
		subContext.destroySubcontext("/env/mail");
		ic.destroySubcontext("java:comp");
		ic.destroySubcontext("java:");
	}

}
