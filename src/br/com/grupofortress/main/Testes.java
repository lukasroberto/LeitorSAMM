/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.grupofortress.main;

import br.com.grupofortress.dao.LeitorDao;
import java.io.IOException;
/**
 *
 * @author informatica
 */
public class Testes {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        LeitorDao leitor = new LeitorDao();
         
        for (Object evento : leitor.getClientesNaoCadastrados()) { 
            System.out.println("Codigo: "+evento.toString());
            
        }
                
    }
}
