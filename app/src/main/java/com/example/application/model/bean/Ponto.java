package com.example.application.model.bean;

import java.util.Date;

public class Ponto {
    private int id;
    private int pontoStatus_id;
    private Date dataHora;
    private String tipo;

    public int getPontoStatus_id() {
        return pontoStatus_id;
    }

    public void setPontoStatus_id(int pontoStatus_id) {
        this.pontoStatus_id = pontoStatus_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
