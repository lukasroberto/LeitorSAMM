package br.com.grupofortress.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import propriedades.Propriedades;

/**
 * Classe para manipular a execução de tarefas agendadas automaticamentes
 *
 * @author Jean C Becker
 * @version 1.0
 */
public class AgendadeTarefas {

    private static AgendadeTarefas instance;

    public static AgendadeTarefas getInstance() {
        if (instance == null) {
            instance = new AgendadeTarefas();
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
        Date dataHoraAtual = new Date(System.currentTimeMillis());
;

        Date time = c.getTime();
        if (dataHoraAtual.getTime() < time.getTime()) {

            final Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    //EmailStatusComunicacao.getInstance().enviaEmailComunicacao();
                }
            }, time);
        }
    }

    public void parar() {
        timer.cancel();
    }
}
