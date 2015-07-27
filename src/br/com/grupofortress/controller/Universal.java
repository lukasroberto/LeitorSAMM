package br.com.grupofortress.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    //retorna data e hora atual

    //retorna dada, mes, dia, ano, Hora atual
    private Calendar cal = Calendar.getInstance();
    private String mes = "0" + (cal.get(Calendar.MONTH) + 1);
    private int dia = cal.get(Calendar.DAY_OF_MONTH);
    private int ano = cal.get(Calendar.YEAR);
    private String data = dia + "/" + mes + "/" + ano;

    //recupera hora, minutos e segundos
    private int hora = cal.get(Calendar.HOUR_OF_DAY);
    private int minuto = cal.get(Calendar.MINUTE);
    private int segundo = cal.get(Calendar.SECOND);
    private String horaMinSegAtual = hora + ":" + minuto + ":" + segundo;

    public String getDataHoraAtual() { 
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
        Date date = new Date(); 
        return dateFormat.format(date); 
    }


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

    public String getHoraMinSegAtual() {
        return horaMinSegAtual;
    }

    public void setHoraMinSegAtual(String horaMinSegAtual) {
        this.horaMinSegAtual = horaMinSegAtual;
    }

    public String getDataAtualMenosDiaMenosHora(int dias, int horas) {

        return dia - dias + "/" + mes + "/" + ano + " " + (hora - horas) + ":" + minuto + ":" + segundo;
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
            SimpleDateFormat formatoData = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            c.setTime(formatoData.parse(dateTime));
        } catch (Exception e) {
            System.out.println(e);
        }
        return c;
    }
}
