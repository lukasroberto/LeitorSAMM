package br.com.grupofortress.controller;

import br.com.grupofortress.dao.ClientesDao;
import br.com.grupofortress.model.Cliente;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.EmailException;

/**
 * Classe para manipular a execução de tarefas agendadas automaticamentes
 *
 * @author Jean C Becker
 * @version 1.0
 */
public class GerarTarefasAgendadas {

    private static GerarTarefasAgendadas instance;

    public static GerarTarefasAgendadas getInstance() {
        if (instance == null) {
            instance = new GerarTarefasAgendadas();
        }
        return instance;
    }

    Timer timer;

    /**
     * Método para iniciar a execução das tarefas.
     */
    public void iniciar() {

        timer = new Timer();
        //Executa tarefa a cada 24 horas a partir da primeira
        //       timer.schedule(new tarefasDiarias(),
        //        0,
        //        1 * 1000 * 60 * 60 * 24);

        //Executa tarefa todo dia as 6 da manha
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 6);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date time = calendar.getTime();
        timer.schedule(new tarefasDiarias(), time);
    }

    /**
     * Método para interromper a execução das tarefas.
     */
    public void parar() {
        timer.cancel();
    }

    /**
     * Método que contém as tarefas agendadas que serão executadas.
     */
    class tarefasDiarias extends TimerTask {

        public void run() {

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
                    + "          <td><font size=\"1\" face=\"verdana, arial, helvetica\"><strong>Ultima Evento Recebido</strong></font></td>";

            for (Cliente cliente : cli.getClientesSemComunicação()) {
                qtdSemComunicacao++;

                msg = msg + "<tr>"
                        + "  <td valign=top><font face=\"verdana, arial, helvetica\" size=1>" + cliente.getCli_codigo() + "</font></td>\n"
                        + "  <td><font face=\"verdana, arial, helvetica\" size=1>" + cliente.getCli_nome() + "</font></td>\n"
                        + "  <td><font size=\"1\" face=\"verdana, arial, helvetica\">" + calendarToString(cliente.getCli_ultima_comunicacao()) + "</font></td>"
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
                enviaEmail.enviaEmailFormatoHtml(msg);
            } catch (EmailException ex) {
                Logger.getLogger(GerarTarefasAgendadas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(GerarTarefasAgendadas.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println(msg);

        }
    }

    public String calendarToString(Calendar dataHora) {
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String retorno = "";
        retorno = formatoData.format(dataHora.getTime());

        return retorno;
    }
}
