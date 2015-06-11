package br.com.grupofortress.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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

        }
    }
}
