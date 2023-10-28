package com.example.application.model.bean;

public class Holerite {
    private int funcionario_id;
    private double adiantamentoQuinzenal;
    private double salarioLiquido;
    private double totalDesconto;
    private double salarioBruto;



    public int getFuncionario_id() {return funcionario_id;}

    public void setFuncionario_id(int funcionario_id) {this.funcionario_id = funcionario_id;}

    public double getAdiantamentoQuinzenal() {
        return adiantamentoQuinzenal;
    }

    public void setAdiantamentoQuinzenal(double adiantamentoQuinzenal) {this.adiantamentoQuinzenal = adiantamentoQuinzenal;}

    public double getSalarioLiquido() {
        return salarioLiquido;
    }

    public void setSalarioLiquido(double salarioLiquido) {
        this.salarioLiquido = salarioLiquido;
    }

    public double getTotalDesconto() {
        return totalDesconto;
    }

    public void setTotalDesconto(double totalDesconto) {
        this.totalDesconto = totalDesconto;
    }

    public double getSalarioBruto() {
        return salarioBruto;
    }

    public void setSalarioBruto(double salarioBruto) {
        this.salarioBruto = salarioBruto;
    }

}