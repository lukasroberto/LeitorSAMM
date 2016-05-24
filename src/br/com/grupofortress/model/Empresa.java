/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.grupofortress.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author informatica
 */
@Entity
@Table(name = "EMPRESA")
public class Empresa implements Serializable{
    
    @Id
    private Long emp_id;
    private String emp_razao_social;
    private String emp_endereco;
    private String emp_bairro;
    private String emp_cidade;
    private String emp_telefone;
    private String emp_email;
    private String emp_cnpj;
    private String emp_cep;
    private String emp_uf;
    private boolean emp_status;
    private String emp_nome_fantasia;
    private Calendar emp_data_abertura;

    public Long getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(Long emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_razao_social() {
        return emp_razao_social;
    }

    public void setEmp_razao_social(String emp_razao_social) {
        this.emp_razao_social = emp_razao_social;
    }

    public String getEmp_endereco() {
        return emp_endereco;
    }

    public void setEmp_endereco(String emp_endereco) {
        this.emp_endereco = emp_endereco;
    }

    public String getEmp_bairro() {
        return emp_bairro;
    }

    public void setEmp_bairro(String emp_bairro) {
        this.emp_bairro = emp_bairro;
    }

    public String getEmp_cidade() {
        return emp_cidade;
    }

    public void setEmp_cidade(String emp_cidade) {
        this.emp_cidade = emp_cidade;
    }

    public String getEmp_telefone() {
        return emp_telefone;
    }

    public void setEmp_telefone(String emp_telefone) {
        this.emp_telefone = emp_telefone;
    }

    public String getEmp_email() {
        return emp_email;
    }

    public void setEmp_email(String emp_email) {
        this.emp_email = emp_email;
    }

    public String getEmp_cnpj() {
        return emp_cnpj;
    }

    public void setEmp_cnpj(String emp_cnpj) {
        this.emp_cnpj = emp_cnpj;
    }

    public String getEmp_cep() {
        return emp_cep;
    }

    public void setEmp_cep(String emp_cep) {
        this.emp_cep = emp_cep;
    }

    public String getEmp_uf() {
        return emp_uf;
    }

    public void setEmp_uf(String emp_uf) {
        this.emp_uf = emp_uf;
    }

    public boolean isEmp_status() {
        return emp_status;
    }

    public void setEmp_status(boolean emp_status) {
        this.emp_status = emp_status;
    }

    public String getEmp_nome_fantasia() {
        return emp_nome_fantasia;
    }

    public void setEmp_nome_fantasia(String emp_nome_fantasia) {
        this.emp_nome_fantasia = emp_nome_fantasia;
    }

    public Calendar getEmp_data_abertura() {
        return emp_data_abertura;
    }

    public void setEmp_data_abertura(Calendar emp_data_abertura) {
        this.emp_data_abertura = emp_data_abertura;
    }
    
    
    
}
