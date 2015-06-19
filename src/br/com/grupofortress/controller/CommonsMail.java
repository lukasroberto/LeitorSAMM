package br.com.grupofortress.controller;

/**
 *
 * @author lukas
 */
import java.net.MalformedURLException;
import java.util.regex.Pattern;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import propriedades.Propriedades;

public final class CommonsMail {

    /**
     * envia email simples(somente texto)
     *
     * @throws EmailException
     */
    public void enviaEmailSimples() throws EmailException {

        SimpleEmail email = new SimpleEmail();
        email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do e-mail
        email.addTo("teste@gmail.com", "Guilherme"); //destinatário
        email.setFrom("teste@gmail.com", "Eu"); // remetente
        email.setSubject("Teste -> Email simples"); // assunto do e-mail
        email.setMsg("Teste de Email utilizando commons-email"); //conteudo do e-mail
        email.setAuthentication("teste", "xxxxx");
        email.setSmtpPort(465);
        email.setSSL(true);
        email.setTLS(true);
        email.send();
    }

    /**
     * envia email com arquivo anexo
     *
     * @throws EmailException
     */
    public void enviaEmailComAnexo() throws EmailException {

        // cria o anexo 1.
        EmailAttachment anexo1 = new EmailAttachment();
        anexo1.setPath("teste/teste.txt"); //caminho do arquivo (RAIZ_PROJETO/teste/teste.txt)
        anexo1.setDisposition(EmailAttachment.ATTACHMENT);
        anexo1.setDescription("Exemplo de arquivo anexo");
        anexo1.setName("teste.txt");

        // cria o anexo 2.
        EmailAttachment anexo2 = new EmailAttachment();
        anexo2.setPath("teste/teste2.jsp"); //caminho do arquivo (RAIZ_PROJETO/teste/teste2.jsp)
        anexo2.setDisposition(EmailAttachment.ATTACHMENT);
        anexo2.setDescription("Exemplo de arquivo anexo");
        anexo2.setName("teste2.jsp");

        // configura o email
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do e-mail
        email.addTo("teste@gmail.com", "Guilherme"); //destinatário
        email.setFrom("teste@gmail.com", "Eu"); // remetente
        email.setSubject("Teste -> Email com anexos"); // assunto do e-mail
        email.setMsg("Teste de Email utilizando commons-email"); //conteudo do e-mail
        email.setAuthentication("teste", "xxxxx");
        email.setSmtpPort(465);
        email.setSSL(true);
        email.setTLS(true);

        // adiciona arquivo(s) anexo(s)
        email.attach(anexo1);
        email.attach(anexo2);
        // envia o email
        email.send();
    }

    /**
     * Envia email no formato HTML
     *
     * @param msg
     * @throws EmailException
     * @throws MalformedURLException
     */
    public void enviaEmailFormatoHtml(String msg) throws EmailException, MalformedURLException {

        try {

            String de = Propriedades.getProp().getProperty("de");
            String senha = Propriedades.getProp().getProperty("senha").trim();
            String user = Propriedades.getProp().getProperty("user").trim();
            String serverMail = Propriedades.getProp().getProperty("servidorEmail").trim();

            String para[] = Propriedades.getProp().getProperty("para").trim().split(Pattern.quote(";"));
            for (String enviaPara : para) {

                HtmlEmail email = new HtmlEmail();

                // adiciona uma imagem ao corpo da mensagem e retorna seu id
                //URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
                //String cid = email.embed(url, "Apache logo");	
                // configura a mensagem para o formato HTML
                email.setHtmlMsg("<html>Logo do Apache - <img ></html>");

                // configure uma mensagem alternativa caso o servidor não suporte HTML
                email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML");

                email.setHostName(serverMail); // o servidor SMTP para envio do e-mail
                email.addTo(enviaPara); //destinatário
                email.setFrom(de); // remetente
                email.setSubject("Relatório de Comunicação"); // assunto do e-mail
                email.setMsg(msg); //conteudo do e-mail
                email.setAuthentication(user, senha);
                email.setSmtpPort(587);
                email.setSSL(false);
                email.setTLS(false);
                // envia email
                email.send();

                email = null;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
