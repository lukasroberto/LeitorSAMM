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
import propriedades.Propriedades;

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
    Calendar c = Calendar.getInstance();

    /**
     * Método para iniciar a execução das tarefas.
     */
    public void iniciar() {

        final int horaEnviaEmail = Integer.parseInt(Propriedades.getProp().getProperty("horaEnviaEmail"));

        c.set(Calendar.HOUR_OF_DAY, horaEnviaEmail);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date dataHoraAtual = Universal.getInstance().getDataHoraAtual();     
        
        Date time = c.getTime();
        if (dataHoraAtual.getTime() < time.getTime()) {

            final Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    enviaEmail();
                }
            }, time);
        }
    }

    public static String formatString(String s) {
        String temp = Normalizer.normalize(s, java.text.Normalizer.Form.NFD);
        return temp.replaceAll("[^\\p{ASCII}]", "");
    }

    public void parar() {
        timer.cancel();
    }

    public void enviaEmail() {
        //Clientes Sem comunicação Fortress
        ClientesDao cli;
        cli = new ClientesDao();
        int qtdSemComunicacaoFortess = 0;
        String msgFortess = "<table width=\"100%\" cellspacing=\"1\" cellpadding=\"5\" border=\"0\" bgcolor=\"#CCCCCC\">\n"
                + "  <tr>\n"
                + "    <td bgcolor=\"#375483\"><font size=2 face=\"verdana, arial, helvetica\" color=\"#FFFFFF\"><b>Clientes Sem Comunicação Fortress</b></font></td>\n"
                + "  </tr>\n"
                + "  <tr>\n"
                + "    <td bgcolor=\"#F5F5F5\"><table width=\"95%\" cellspacing=\"1\" cellpadding=\"1\" border=\"0\" align=\"center\">\n"
                + "        <tr>\n"
                + "          <td valign=top><font face=\"verdana, arial, helvetica\" size=1><strong>Código</strong></font></td>\n"
                + "          <td><font face=\"verdana, arial, helvetica\" size=1><strong>Nome</strong></font></td>\n"
                + "          <td><font size=\"1\" face=\"verdana, arial, helvetica\"><strong>Ultimo Evento Recebido</strong></font></td>"
                + "          <td><font size=\"1\" face=\"verdana, arial, helvetica\"><strong>Observação</strong></font></td>"
                + "          <td><font size=\"1\" face=\"verdana, arial, helvetica\"><strong></strong></font></td>";

        for (Cliente cliente : cli.getClientesSemComunicacao("Fortress")) {
            qtdSemComunicacaoFortess++;
            String cli_obs = cliente.getCli_obs();
            String editar_obs = "";
            Long cli_codigo = cliente.getCli_codigo();

            if (cli_obs == null || cli_obs.trim().isEmpty()) {
                cli_obs = "<a href=\"http://192.168.0.198/fortress/view/cliente/edita_obs.php?operacao=update&clicodigo=" + cli_codigo + "\">Adicionar Obs.</a>";
            } else {
                editar_obs = "<a href=\"http://192.168.0.198/fortress/view/cliente/edita_obs.php?operacao=update&clicodigo=" + cli_codigo + "\">Editar Obs.</a>";

            }

            msgFortess = msgFortess + "<tr>"
                    + "  <td valign=top><font face=\"verdana, arial, helvetica\" size=1>" + cli_codigo + "</font></td>\n"
                    + "  <td><font face=\"verdana, arial, helvetica\" size=1>" + cliente.getCli_nome() + "</font></td>\n"
                    + "  <td><font size=\"1\" face=\"verdana, arial, helvetica\">" + Universal.getInstance().calendarToString(cliente.getCli_ultima_comunicacao()) + "</font></td>"
                    + "  <td><font size=\"1\" face=\"verdana, arial, helvetica\">" + cli_obs + "</font></td>"
                    + "  <td><font size=\"1\" face=\"verdana, arial, helvetica\">" + editar_obs + "</font></td>"
                    + "</tr>";

        }
        msgFortess = msgFortess + "      </table></td>\n"
                + "  </tr>\n"
                + "  <tr>\n"
                + "      <td bgcolor=\"#CCCCCC\"><font size=2 face=\"verdana, arial, helvetica\"><b>Total de Clientes sem comunicação Fortress: " + qtdSemComunicacaoFortess + "</b></font></td>\n"
                + "</tr>"
                + "</table> <p>";
        
        
        //Clientes Sem comunicação Logus
                int qtdSemComunicacaoLogus = 0;
        String msgLogus = "<table width=\"100%\" cellspacing=\"1\" cellpadding=\"5\" border=\"0\" bgcolor=\"#CCCCCC\">\n"
                + "  <tr>\n"
                + "    <td bgcolor=\"#A81313\"><font size=2 face=\"verdana, arial, helvetica\" color=\"#FFFFFF\"><b>Clientes Sem Comunicação Logus</b></font></td>\n"
                + "  </tr>\n"
                + "  <tr>\n"
                + "    <td bgcolor=\"#F5F5F5\"><table width=\"95%\" cellspacing=\"1\" cellpadding=\"1\" border=\"0\" align=\"center\">\n"
                + "        <tr>\n"
                + "          <td valign=top><font face=\"verdana, arial, helvetica\" size=1><strong>Código</strong></font></td>\n"
                + "          <td><font face=\"verdana, arial, helvetica\" size=1><strong>Nome</strong></font></td>\n"
                + "          <td><font size=\"1\" face=\"verdana, arial, helvetica\"><strong>Ultimo Evento Recebido</strong></font></td>"
                + "          <td><font size=\"1\" face=\"verdana, arial, helvetica\"><strong>Observação</strong></font></td>"
                + "          <td><font size=\"1\" face=\"verdana, arial, helvetica\"><strong></strong></font></td>";

        cli = new ClientesDao();
        for (Cliente cliente : cli.getClientesSemComunicacao("Logus")) {
            qtdSemComunicacaoLogus++;
            String cli_obs = cliente.getCli_obs();
            String editar_obs = "";
            Long cli_codigo = cliente.getCli_codigo();

            if (cli_obs == null || cli_obs.trim().isEmpty()) {
                cli_obs = "<a href=\"http://192.168.0.198/fortress/view/cliente/edita_obs.php?operacao=update&clicodigo=" + cli_codigo + "\">Adicionar Obs.</a>";
            } else {
                editar_obs = "<a href=\"http://192.168.0.198/fortress/view/cliente/edita_obs.php?operacao=update&clicodigo=" + cli_codigo + "\">Editar Obs.</a>";

            }

            msgLogus = msgLogus + "<tr>"
                    + "  <td valign=top><font face=\"verdana, arial, helvetica\" size=1>" + cli_codigo + "</font></td>\n"
                    + "  <td><font face=\"verdana, arial, helvetica\" size=1>" + cliente.getCli_nome() + "</font></td>\n"
                    + "  <td><font size=\"1\" face=\"verdana, arial, helvetica\">" + Universal.getInstance().calendarToString(cliente.getCli_ultima_comunicacao()) + "</font></td>"
                    + "  <td><font size=\"1\" face=\"verdana, arial, helvetica\">" + cli_obs + "</font></td>"
                    + "  <td><font size=\"1\" face=\"verdana, arial, helvetica\">" + editar_obs + "</font></td>"
                    + "</tr>";

        }
        msgLogus = msgLogus + "      </table></td>\n"
                + "  </tr>\n"
                + "  <tr>\n"
                + "      <td bgcolor=\"#CCCCCC\"><font size=2 face=\"verdana, arial, helvetica\"><b>Total de Clientes sem comunicação  Logus: " + qtdSemComunicacaoLogus + "</b></font></td>\n"
                + "</tr>"
                + "</table> ";

        CommonsMail enviaEmail = new CommonsMail();

        try {
            enviaEmail.enviaEmailFormatoHtml(formatString(msgFortess+msgLogus));
        } catch (EmailException ex) {
            Logger.getLogger(GerarTarefasAgendadas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(GerarTarefasAgendadas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
