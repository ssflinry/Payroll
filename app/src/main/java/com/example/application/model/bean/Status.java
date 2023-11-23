package com.example.application.model.bean;

import java.util.Date;

public class Status {
    private int id;
    private int funcionario_id;
    private Boolean entradaStatus;
    private Boolean pausaStatus;
    private Boolean retornoStatus;
    private Boolean saidaStatus;
    private Date dataHora;

    public int getFuncionario_id() {return funcionario_id;}

    public void setFuncionario_id(int funcionario_id) {this.funcionario_id = funcionario_id;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getEntradaStatus() {
        return entradaStatus;
    }

    public void setEntradaStatus(Boolean entradaStatus) {
        this.entradaStatus = entradaStatus;
    }

    public Boolean getPausaStatus() {
        return pausaStatus;
    }

    public void setPausaStatus(Boolean pausaStatus) {
        this.pausaStatus = pausaStatus;
    }

    public Boolean getRetornoStatus() {
        return retornoStatus;
    }

    public void setRetornoStatus(Boolean retornoStatus) {
        this.retornoStatus = retornoStatus;
    }

    public Boolean getSaidaStatus() {
        return saidaStatus;
    }

    public void setSaidaStatus(Boolean saidaStatus) {
        this.saidaStatus = saidaStatus;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }
}
