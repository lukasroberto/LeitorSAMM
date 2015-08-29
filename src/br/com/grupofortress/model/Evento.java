package br.com.grupofortress.model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LEITOR")
public class Evento implements Serializable {

    @Id
    @GeneratedValue
    private Long eve_id;
    private Calendar eve_data_hora;
    private String eve_hora;
    private String eve_data;
    private String eve_conta_grupo_receptor;
    private Long eve_codigo_cliente;
    private String eve_protocolo;
    private String eve_codigo_evento;
    private String eve_particao;
    private String eve_usuario_zona;

//    00:03  07/01  01 5087 18 E602 01 000*
//    00:03 hora central de alarme
//    07/01 data central de alarme
//    01 = Conta da vectra e grupo da vectra
//    5087 = codigo do cliente
//    18 = Protocolo de comubnicação(18=Contact ID)
//    E602 = evento da central
//    01 = partição
//    000* = Usuario / Zona
    public Long getEve_id() {
        return eve_id;
    }

    public void setEve_id(Long eve_id) {
        this.eve_id = eve_id;
    }

    public Calendar getEve_data_hora() {
        return eve_data_hora;
    }

    public void setEve_data_hora(Calendar eve_data_hora) {
        this.eve_data_hora = eve_data_hora;
    }

    public String getEve_data() {
        return eve_data;
    }

    public void setEve_data(String eve_data) {
        this.eve_data = eve_data;
    }

    public String getEve_hora() {
        return eve_hora;
    }

    public void setEve_hora(String eve_hora) {
        this.eve_hora = eve_hora;
    }

    public String getEve_conta_grupo_receptor() {
        return eve_conta_grupo_receptor;
    }

    public void setEve_conta_grupo_receptor(String eve_conta_grupo_receptor) {
        this.eve_conta_grupo_receptor = eve_conta_grupo_receptor;
    }

    public Long getEve_codigo_cliente() {
        return eve_codigo_cliente;
    }

    public void setEve_codigo_cliente(Long eve_codigo_cliente) {
        this.eve_codigo_cliente = eve_codigo_cliente;
    }

    public String getEve_protocolo() {
        return eve_protocolo;
    }

    public void setEve_Protocolo(String protocolo) {
        this.eve_protocolo = protocolo;
    }

    public String getEve_codigo_evento() {
        return eve_codigo_evento;
    }

    public void setEve_codigo_evento(String eve_codigo_evento) {
        this.eve_codigo_evento = eve_codigo_evento;
    }

    public String getEve_particao() {
        return eve_particao;
    }

    public void setEve_particao(String eve_particao) {
        this.eve_particao = eve_particao;
    }

    public String getEve_usuario_zona() {
        return eve_usuario_zona;
    }

    public void setEve_usuario_zona(String eve_usuario_zona) {
        this.eve_usuario_zona = eve_usuario_zona;
    }

}
