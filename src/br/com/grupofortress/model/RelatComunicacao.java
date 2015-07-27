package br.com.grupofortress.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author lukas
 */
@Entity
@Table(name = "RELAT_COMUNICACAO")
public class RelatComunicacao implements Serializable {

    @Id
    @GeneratedValue
    private Long com_codigo;
    private String com_data_ultima_comunicacao;
    private Long com_cli_codigo;

    public Long getCom_codigo() {
        return com_codigo;
    }

    public void setCom_codigo(Long com_codigo) {
        this.com_codigo = com_codigo;
    }

    public String getCom_data_ultima_comunicacao() {
        return com_data_ultima_comunicacao;
    }

    public void setCom_data_ultima_comunicacao(String com_data_ultima_comunicacao) {
        this.com_data_ultima_comunicacao = com_data_ultima_comunicacao;
    }

    public Long getCom_cli_codigo() {
        return com_cli_codigo;
    }

    public void setCom_cli_codigo(Long com_cli_codigo) {
        this.com_cli_codigo = com_cli_codigo;
    }

}
