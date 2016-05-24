package br.com.grupofortress.controller;

import br.com.grupofortress.dao.ClientesDao;
import br.com.grupofortress.dao.EmpresaDao;
import br.com.grupofortress.model.Cliente;
import br.com.grupofortress.model.Empresa;
import java.net.MalformedURLException;
import java.text.Normalizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.EmailException;
import propriedades.Propriedades;

/**
 *
 * @author informatica
 */
public class EmailStatusComunicacao {

    private static EmailStatusComunicacao instance;
    private final String emails = Propriedades.getProp().getProperty("para");

    public static EmailStatusComunicacao getInstance() {
        if (instance == null) {
            instance = new EmailStatusComunicacao();
        }
        return instance;
    }

    public static String formatString(String s) {
        String temp = Normalizer.normalize(s, java.text.Normalizer.Form.NFD);
        return temp.replaceAll("[^\\p{ASCII}]", "");
    }

    public boolean enviaEmailComunicacao() {
        //Clientes Sem comunicação Fortress
        ClientesDao cliDao;
        cliDao = new ClientesDao();

        EmpresaDao empDao;
        empDao = new EmpresaDao();

        int qtdSemComunicacao = 0;
        int totalClientesSemComunicacao = 0;
        String diaATNR = Propriedades.getProp().getProperty("dias");
        String horasATNR = Propriedades.getProp().getProperty("horas");
        String msgResumo = "";
        String msg = "";

        for (Empresa empresa : empDao.findAll()) {
            int codigoEmpresa = Integer.parseInt(empresa.getEmp_id().toString());
            String nomeEmpresa = empresa.getEmp_nome_fantasia();
            qtdSemComunicacao = 0;
            

            msg = msg + "<table width=\"100%\" cellspacing=\"1\" cellpadding=\"5\" border=\"0\" bgcolor=\"#CCCCCC\">\n"
                    + "  <tr>\n"
                    + "    <td bgcolor=\"#375483\"><font size=2 face=\"verdana, arial, helvetica\" color=\"#FFFFFF\"><b>Clientes Sem Comunicação " + nomeEmpresa + "</b></font></td>\n"
                    + "  </tr>\n"
                    + "  <tr>\n"
                    + "    <td bgcolor=\"#F5F5F5\"><table width=\"95%\" cellspacing=\"1\" cellpadding=\"1\" border=\"0\" align=\"center\">\n"
                    + "        <tr>\n"
                    + "          <td valign=top><font face=\"verdana, arial, helvetica\" size=1><strong>Código</strong></font></td>\n"
                    + "          <td><font face=\"verdana, arial, helvetica\" size=1><strong>Nome</strong></font></td>\n"
                    + "          <td><font size=\"1\" face=\"verdana, arial, helvetica\"><strong>Ultimo Evento Recebido</strong></font></td>"
                    + "          <td><font size=\"1\" face=\"verdana, arial, helvetica\"><strong>Observação</strong></font></td>"
                    + "          <td><font size=\"1\" face=\"verdana, arial, helvetica\"><strong></strong></font></td>";

            for (Cliente cliente : cliDao.getClientesSemComunicacao(codigoEmpresa)) {

                qtdSemComunicacao++;
                totalClientesSemComunicacao++;
                String cli_obs = cliente.getCli_obs();
                String editar_obs = "";
                Long cli_codigo = cliente.getCli_codigo();
                String dataUltimaComunicacao = Universal.getInstance().calendarToString(cliente.getCli_ultima_comunicacao());

                if (cli_obs == null || cli_obs.trim().isEmpty()) {
                    cli_obs = "<a href=\"http://192.168.0.198/fortress/view/cliente/edita_obs.php?operacao=update&clicodigo=" + cli_codigo + "\">Adicionar Obs.</a>";
                } else {
                    editar_obs = "<a href=\"http://192.168.0.198/fortress/view/cliente/edita_obs.php?operacao=update&clicodigo=" + cli_codigo + "\">Editar Obs.</a>";

                }

                msg = msg + "<tr>"
                        + "  <td valign=top><font face=\"verdana, arial, helvetica\" size=1>" + cli_codigo + "</font></td>\n"
                        + "  <td><font face=\"verdana, arial, helvetica\" size=1>" + cliente.getCli_nome() + "</font></td>\n"
                        + "  <td><font size=\"1\" face=\"verdana, arial, helvetica\">" + dataUltimaComunicacao + "</font></td>"
                        + "  <td><font size=\"1\" face=\"verdana, arial, helvetica\">" + cli_obs + "</font></td>"
                        + "  <td><font size=\"1\" face=\"verdana, arial, helvetica\">" + editar_obs + "</font></td>"
                        + "</tr>";
            }
            msg = msg + "      </table></td>\n"
                    + "  </tr>\n"
                    + "  <tr>\n"
                    + "      <td bgcolor=\"#CCCCCC\"><font size=2 face=\"verdana, arial, helvetica\"><b>Total de Clientes sem comunicação " + nomeEmpresa + ": " + qtdSemComunicacao + "</b></font></td>\n"
                    + "</tr>"
                    + "</table> <p>";

            msgResumo = msgResumo + "<tr>\n"
                    + "<td valign=top><font face='verdana, arial, helvetica' size=1><strong>Clientes sem Comunicação " + nomeEmpresa + ": </strong>" + qtdSemComunicacao + " </font></td>\n"
                    + "</tr>\n";
        }

        String resumo = "<table width=\"100%\" cellspacing='1' cellpadding=5 border='0' bgcolor='#F5F5F5'>\n"
                + "<tr>\n"
                + "<td colspan=\"2\" bgcolor='#3FCD54'><font size=2 face='verdana, arial, helvetica' color='#FFFFFF'><b>Resumo</b></font></td>\n"
                + "<tr>\n"
                + "<td valign=top><font face='verdana, arial, helvetica' size=1 color='#BF0005'><strong>Dados baseados em Clientes com ATNR a mais de " + diaATNR + " dias e " + horasATNR + " horas.</strong></font></td>\n"
                + "</tr>\n"
                + msgResumo
                + "<tr>\n"
                + "<td valign=top><font face='verdana, arial, helvetica' size=1><strong>Data: </strong>" + Universal.getInstance().getDataHoraAtual("dd/MM/yyyy HH:mm:ss") + " </font></td>\n"
                + "</tr>\n"
                + "<tr>\n"
                + "<td valign=top><font face='verdana, arial, helvetica' size=1><strong>Total de Clientes sem Comunicação: </strong>" + totalClientesSemComunicacao + " </font></td>\n"
                + "</tr>\n"
                + "</table> <p>";

        CommonsMail enviaEmail = new CommonsMail();

        boolean enviado = false;

        try {
            enviaEmail.enviaEmailFormatoHtml(formatString(resumo + msg), "Relatorio de Comunicacao", emails);
            enviado = true;
        } catch (EmailException ex) {
            Logger.getLogger(AgendadeTarefas.class.getName()).log(Level.SEVERE, null, ex);
            enviado = false;
        } catch (MalformedURLException ex) {
            Logger.getLogger(AgendadeTarefas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return enviado;

    }

}
