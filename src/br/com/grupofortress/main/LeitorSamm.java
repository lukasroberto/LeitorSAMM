package br.com.grupofortress.main;

import br.com.grupofortress.controller.Universal;
import br.com.grupofortress.dao.ClientesDao;
import br.com.grupofortress.dao.LeitorDao;
import br.com.grupofortress.model.Evento;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import propriedades.Propriedades;

/**
 *
 * @author informatica
 */
public class LeitorSamm extends javax.swing.JFrame {

    private final String grupocontaReceptora = Propriedades.getProp().getProperty("grupocontareceptora");
    private final int nLinhasTabela = Integer.parseInt(Propriedades.getProp().getProperty("nlinhastabela"));

    String mes = Universal.getInstance().getMes();
    private int ano = Universal.getInstance().getAno();

    private final String caminho = "C://samm/" + ano + "" + mes + ".EVT/";
    private DefaultTableModel tabelaEventos;
    private static int conta = 0;

    public LeitorSamm() {
        initComponents();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpPrincipal = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbEventosRecebidos = new javax.swing.JTable();
        jmMenuPrincipal = new javax.swing.JMenuBar();
        mArquivo = new javax.swing.JMenu();
        mSair = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jpPrincipal.setBackground(new java.awt.Color(51, 51, 51));

        tbEventosRecebidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Data", "Hora", "Conta/Grupo da Receptora", "Codigo do Cliente", "Protocolo", "Evento", "Partição", "Usuário/zona"
            }
        ));
        jScrollPane1.setViewportView(tbEventosRecebidos);

        javax.swing.GroupLayout jpPrincipalLayout = new javax.swing.GroupLayout(jpPrincipal);
        jpPrincipal.setLayout(jpPrincipalLayout);
        jpPrincipalLayout.setHorizontalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPrincipalLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 773, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jpPrincipalLayout.setVerticalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        mArquivo.setText("Arquivo");

        mSair.setText("Sair");
        mSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSairActionPerformed(evt);
            }
        });
        mArquivo.add(mSair);

        jmMenuPrincipal.add(mArquivo);

        setJMenuBar(jmMenuPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSairActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mSairActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuBar jmMenuPrincipal;
    private javax.swing.JPanel jpPrincipal;
    private javax.swing.JMenu mArquivo;
    private javax.swing.JMenuItem mSair;
    private javax.swing.JTable tbEventosRecebidos;
    // End of variables declaration//GEN-END:variables

    void net() {

        tabelaEventos = (DefaultTableModel) tbEventosRecebidos.getModel();

        try {
            //le Arquivo de texto
            FileReader lerArquivoEvent = new FileReader(caminho + "event.txt");
            BufferedReader entradaEvent = new BufferedReader(lerArquivoEvent);
            String linhaEvent;

            //copia texto para outro arquivo de texto para evitar que dados sejam apagados antes de lidos
            File escreveTxtEvent = new File(caminho + "eventGravarNoBD.txt");
            FileOutputStream destinoEvent = new FileOutputStream(escreveTxtEvent, true);

            while ((linhaEvent = entradaEvent.readLine()) != null) {
                linhaEvent = "@\n" + linhaEvent;
                destinoEvent.write(linhaEvent.getBytes());
                destinoEvent.write(System.getProperty("line.separator").getBytes());
            }
            entradaEvent.close();

            //Apaga o Conteudo do event.txt para que o winsamm possa salvar novos eventos enquanto as tarefas são executadas
            FileWriter limpaEventTxt = new FileWriter(caminho + "event.txt", false);
            limpaEventTxt.close();
            
            //FileReader server para ler o arquivo de texto expecificado e jogar o resultado para "arquivo"
            FileReader arquivo = new FileReader(caminho + "eventGravarNoBD.txt");
            //copia texto para outro arquivo de texto BKP
            BufferedReader entrada = new BufferedReader(arquivo);
            //copia texto para outro arquivo de texto BKP
            //File escreveTxtBKP = new File(caminho+"eventBKP.txt");
            //FileOutputStream destinoBKP = null;
            //destinoBKP = new FileOutputStream(escreveTxtBKP, true);

            String saida;
            String arrayPartes[] = new String[3];
            String arrayCamposParte2[] = new String[6];

            LeitorDao leitorDao = null;
            ClientesDao clienteDao = null;
            if (entrada.readLine() == null) {
                System.out.println("Vazio");
            } else {
                if (tabelaEventos.getRowCount() >= nLinhasTabela) {
                    tabelaEventos.setNumRows(0);
                }
                leitorDao = new LeitorDao();
                clienteDao = new ClientesDao();
                while ((saida = entrada.readLine()) != null) {
                    //Grava a linha em um arquivo de BKP eventBKP.txt
                    //destinoBKP.write(saida.getBytes());
                    //destinoBKP.write(System.getProperty("line.separator").getBytes());

                    saida = saida.replace(grupocontaReceptora + " OKAY ", "");
                    saida = saida.replace("@", "");

                    arrayPartes = saida.split("  ");
                    if (arrayPartes.length == 3) {

                        arrayCamposParte2 = arrayPartes[2].split(" ");

                        if (arrayCamposParte2.length >= 3) {

                            String dataVectra = arrayPartes[1];
                            String horaVectra = arrayPartes[0];
                            //corrige data do Activenet que envia a data errada -- 1//22
                            if (dataVectra.contains("//")) { //Se for diferente de -1 é pq existe o caracter.
                                String quebraDataVectra[] = new String[1];
                                quebraDataVectra = dataVectra.split("//");
                                dataVectra = quebraDataVectra[0] + "/" + quebraDataVectra[1];// data = 1/22
                            }
                            dataVectra = dataVectra + "/" + ano + " " + horaVectra;

                            Evento evento = new Evento();
                            evento.setEve_data_hora(Universal.getInstance().dateTimeToCalendar(dataVectra));
                            evento.setEve_data(arrayPartes[1]);
                            evento.setEve_hora(arrayPartes[0]);

                            evento.setEve_conta_grupo_receptor(arrayCamposParte2[0]);
                            try {
                                evento.setEve_codigo_cliente(Integer.parseInt(arrayCamposParte2[1]));

                                evento.setEve_codigo_evento(arrayCamposParte2[2]);

                                if (arrayCamposParte2.length == 6) {
                                    evento.setEve_Protocolo(arrayCamposParte2[2]);
                                    evento.setEve_codigo_evento(arrayCamposParte2[3]);
                                    evento.setEve_particao(arrayCamposParte2[4]);
                                    evento.setEve_usuario_zona(arrayCamposParte2[5]);
                                }

                                tabelaEventos.addRow(new Object[]{Universal.getInstance().calendarToString(evento.getEve_data_hora()), evento.getEve_hora(), evento.getEve_conta_grupo_receptor(), evento.getEve_codigo_cliente(), evento.getEve_protocolo(), evento.getEve_codigo_evento(),
                                    evento.getEve_particao(), evento.getEve_usuario_zona()});

                                leitorDao.persist(evento);
                                clienteDao.atualizaUltimaComunicacaoCLiente(Universal.getInstance().calendarToString(evento.getEve_data_hora()), evento.getEve_codigo_cliente());

                            } catch (NumberFormatException ex) {
                                System.err.println(ex);
                            }
                        }
                    }
                }
                FileWriter limpaEventGravaNoBD = new FileWriter(caminho + "eventGravarNoBD.txt", false);
                limpaEventGravaNoBD.close();
                arquivo.close();
                entrada.close();
            }//10:08  20/2/  61 0045 18 E250 00 000
        } catch (IOException e) {
            File file = new File(caminho + "log.txt");

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write("Arquivo gravado em : " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
                writer.newLine();
                writer.write(LeitorSamm.class.getName() + "\n " + e.toString() + "\n " + e.getLocalizedMessage().toString());
                writer.flush();
                writer.close();

                Thread.sleep(10000);
                Universal.reiniciaAplicativo();

            } catch (IOException ex) {
                System.out.println(ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(LeitorSamm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
