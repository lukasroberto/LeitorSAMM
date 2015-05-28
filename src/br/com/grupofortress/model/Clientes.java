/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.grupofortress.model;

import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author lukas
 */
@Entity
@Table(name = "CLIENTE")
public class Clientes {

    @Id
    private Long cli_codigo;
    private Calendar cli_ultima_comunicacao;

    public Long getID() {
        return cli_codigo;
    }

    public void setID(Long ID) {
        this.cli_codigo = ID;
    }

    public Calendar getCli_ultima_comunicacao() {
        return cli_ultima_comunicacao;
    }

    public void setCli_ultima_comunicacao(Calendar cli_ultima_comunicacao) {
        this.cli_ultima_comunicacao = cli_ultima_comunicacao;
    }

}
