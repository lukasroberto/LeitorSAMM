package br.com.grupofortress.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import propriedades.Propriedades;

/**
 *
 * @author informatica
 */
public class Universal {

    private static Universal instance;

//retorna a instancia desta classe
    public static Universal getInstance() {
        if (instance == null) {
            instance = new Universal();
        }
        return instance;
    }

    //retorna dada, me, dia, ano, Hora atual
    Calendar cal = Calendar.getInstance();
    String mes = "0" + (cal.get(Calendar.MONTH) + 1);
    private int dia = cal.get(Calendar.DAY_OF_MONTH);
    private int ano = cal.get(Calendar.YEAR);
    private String data = dia + "/" + mes + "/" + ano;

    //recupera Hora Atual
    GregorianCalendar teste = new GregorianCalendar();
    SimpleDateFormat teste2 = new SimpleDateFormat("HH:mm");
    String horaAtual = teste2.format(teste.getTime());

    public String getMes() {
        return mes;
    }

    public int getDia() {
        return dia;
    }

    public int getAno() {
        return ano;
    }

    public String getData() {
        return data;
    }

    public String gethoraAtual() {
        return horaAtual;
    }

    public String getDataAtualMenosUmDia() {
        return dia - 1 + "/" + mes + "/" + ano + " " + horaAtual;
    }

    //reinicia o Leitor completamente
    public static void reiniciaAplicativo() {
        String comando = "java -jar " + Propriedades.getProp().getProperty("localaplicativo");
        try {
            Runtime.getRuntime().exec(comando);
        } catch (IOException MensagemdeErro) {
            System.out.println(MensagemdeErro);
        }
        System.exit(0);
    }
// Converte Calendar para String

    public String calendarToString(Calendar dataHora) {
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String retorno = "";
        retorno = formatoData.format(dataHora.getTime());

        return retorno;
    }

    //converte data e hora para calendar
    public Calendar dateTimeToCalendar(String dateTime) {
        Calendar c = Calendar.getInstance();
        try {
            SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            c.setTime(formatoData.parse(dateTime));
        } catch (Exception e) {
            System.out.println(e);
        }
        return c;
    }
}
