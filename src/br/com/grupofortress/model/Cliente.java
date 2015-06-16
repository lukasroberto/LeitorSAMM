package br.com.grupofortress.model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author lukas
 */
@Entity
@Table(name = "CLIENTE")
public class Cliente implements Serializable {

    @Id
    private Long cli_codigo;
    private String cli_nome;
    private String cli_rua;
    private String cli_numero;
    private String cli_bairro;
    private String cli_cidade;
    private String cli_estado;
    private String cli_telefone;
    private String cli_cad_por;
    private Calendar cli_data_cad;
    private String cli_telefone1;
    private String cli_empresa;
    private String cli_comunicacao;
    private boolean cli_monitorado;
    private Calendar cli_ultima_comunicacao;

    public Long getCli_codigo() {
        return cli_codigo;
    }

    public void setCli_codigo(Long cli_codigo) {
        this.cli_codigo = cli_codigo;
    }

    public String getCli_nome() {
        return cli_nome;
    }

    public void setCli_nome(String cli_nome) {
        this.cli_nome = cli_nome;
    }

    public String getCli_rua() {
        return cli_rua;
    }

    public void setCli_rua(String cli_rua) {
        this.cli_rua = cli_rua;
    }

    public String getCli_numero() {
        return cli_numero;
    }

    public void setCli_numero(String cli_numero) {
        this.cli_numero = cli_numero;
    }

    public String getCli_bairro() {
        return cli_bairro;
    }

    public void setCli_bairro(String cli_bairro) {
        this.cli_bairro = cli_bairro;
    }

    public String getCli_cidade() {
        return cli_cidade;
    }

    public void setCli_cidade(String cli_cidade) {
        this.cli_cidade = cli_cidade;
    }

    public String getCli_estado() {
        return cli_estado;
    }

    public void setCli_estado(String cli_estado) {
        this.cli_estado = cli_estado;
    }

    public String getCli_telefone() {
        return cli_telefone;
    }

    public void setCli_telefone(String cli_telefone) {
        this.cli_telefone = cli_telefone;
    }

    public String getCli_cad_por() {
        return cli_cad_por;
    }

    public void setCli_cad_por(String cli_cad_por) {
        this.cli_cad_por = cli_cad_por;
    }

    public Calendar getCli_data_cad() {
        return cli_data_cad;
    }

    public void setCli_data_cad(Calendar cli_data_cad) {
        this.cli_data_cad = cli_data_cad;
    }

    public String getCli_telefone1() {
        return cli_telefone1;
    }

    public void setCli_telefone1(String cli_telefone1) {
        this.cli_telefone1 = cli_telefone1;
    }

    public String getCli_empresa() {
        return cli_empresa;
    }

    public void setCli_empresa(String cli_empresa) {
        this.cli_empresa = cli_empresa;
    }

    public String getCli_comunicacao() {
        return cli_comunicacao;
    }

    public void setCli_comunicacao(String cli_comunicacao) {
        this.cli_comunicacao = cli_comunicacao;
    }

    public Calendar getCli_ultima_comunicacao() {
        return cli_ultima_comunicacao;
    }

    public void setCli_ultima_comunicacao(Calendar cli_ultima_comunicacao) {
        this.cli_ultima_comunicacao = cli_ultima_comunicacao;
    }

    public boolean getCli_monitorado() {
        return cli_monitorado;
    }

    public void setCli_monitorado(boolean cli_monitorado) {
        this.cli_monitorado = cli_monitorado;
    }

}
