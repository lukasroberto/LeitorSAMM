/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.grupofortress.controller;

import br.com.grupofortress.dao.ClientesDao;
import br.com.grupofortress.model.Cliente;
import java.net.MalformedURLException;
import java.text.Normalizer;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.EmailException;

public class Agendamentos {

    public void terceiraTarefa() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 9);
        c.set(Calendar.MINUTE, 40);
        c.set(Calendar.SECOND, 0);

        Date time = c.getTime();

        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Enviando email _________________________________________________________________ \n");

                ClientesDao cli = new ClientesDao();
                int qtdSemComunicacao = 0;
                String msg = "<table width=\"100%\" cellspacing=\"1\" cellpadding=\"3\" border=\"0\" bgcolor=\"#CCCCCC\">\n"
                        + "  <tr>\n"
                        + "    <td bgcolor=\"#CC0000\"><font size=1 face=\"verdana, arial, helvetica\" color=\"#FFFFFF\"><b>Clientes Sem Comunicação</b></font></td>\n"
                        + "  </tr>\n"
                        + "  <tr>\n"
                        + "    <td bgcolor=\"#F5ECB9\"><table width=\"95%\" cellspacing=\"1\" cellpadding=\"1\" border=\"0\" align=\"center\">\n"
                        + "        <tr>\n"
                        + "          <td valign=top><font face=\"verdana, arial, helvetica\" size=1><strong>Código</strong></font></td>\n"
                        + "          <td><font face=\"verdana, arial, helvetica\" size=1><strong>Nome</strong></font></td>\n"
                        + "          <td><font size=\"1\" face=\"verdana, arial, helvetica\"><strong>Ultimo Evento Recebido</strong></font></td>";

                for (Cliente cliente : cli.getClientesSemComunicação()) {
                    qtdSemComunicacao++;

                    msg = msg + "<tr>"
                            + "  <td valign=top><font face=\"verdana, arial, helvetica\" size=1>" + cliente.getCli_codigo() + "</font></td>\n"
                            + "  <td><font face=\"verdana, arial, helvetica\" size=1>" + cliente.getCli_nome() + "</font></td>\n"
                            + "  <td><font size=\"1\" face=\"verdana, arial, helvetica\">" + Universal.getInstance().calendarToString(cliente.getCli_ultima_comunicacao()) + "</font></td>"
                            + "</tr>";

                }
                msg = msg + "      </table></td>\n"
                        + "  </tr>\n"
                        + "  <tr>\n"
                        + "      <td bgcolor=\"#CCCCCC\"><font size=1 face=\"verdana, arial, helvetica\"><b>Total de Clientes sem Comunicação: " + qtdSemComunicacao + "</b></font></td>\n"
                        + "</tr>"
                        + "</table> ";

                CommonsMail enviaEmail = new CommonsMail();

                try {
                    enviaEmail.enviaEmailFormatoHtml(formatString(msg));
                } catch (EmailException ex) {
                    Logger.getLogger(GerarTarefasAgendadas.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(GerarTarefasAgendadas.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }, time);
    }
        public static String formatString(String s) {
        String temp = Normalizer.normalize(s, java.text.Normalizer.Form.NFD);
        return temp.replaceAll("[^\\p{ASCII}]", "");
    }
}
