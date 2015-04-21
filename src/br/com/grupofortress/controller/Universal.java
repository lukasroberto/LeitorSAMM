package br.com.grupofortress.controller;

import java.io.IOException;
import propriedades.Propriedades;

/**
 *
 * @author informatica
 */
public class Universal {

    private static Universal instance;

    public static Universal getInstance() {
        if (instance == null) {
            instance = new Universal();
        }
        return instance;
    }
    

    public static void reiniciaAplicativo() {
        String comando = "java -jar " + Propriedades.getProp().getProperty("localaplicativo");
        try {
            Runtime.getRuntime().exec(comando);
        } catch (IOException MensagemdeErro) {
            System.out.println(MensagemdeErro);
        }
        System.exit(0);
    }

}
