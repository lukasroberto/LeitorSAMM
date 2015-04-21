package br.com.grupofortress.conection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import propriedades.Propriedades;

public class Conexao {

    private static final String servidor = Propriedades.getProp().getProperty("servidor");
    private static final String URL = "jdbc:sqlserver://"+servidor+":1433; databaseName=monitoramento;";//se não for acessar localmente mude localhost pelo nome do servidor  
    private static final String usuario = "sa";//esse usuário é um sysadmin ele tem todos os poderes, é bom que se crie um login e usuário a parte  
    private static final String password = "spotGF2008";
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";//Esse é o nome do driver, que na internet você vai encontrar de varias maneiras, mas só esse resolveu meus problemas  

    public static Connection abreConexao() throws SQLException {
        try {

            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, usuario, password);
            return con;

        } catch (ClassNotFoundException e) {
            throw new SQLException(e.getMessage());

        }
    }
}
