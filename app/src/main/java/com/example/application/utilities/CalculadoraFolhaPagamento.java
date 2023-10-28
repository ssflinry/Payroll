package com.example.application.utilities;

public class CalculadoraFolhaPagamento {
    public static double calcularINSS(double salarioBruto) {
        double inss = 0.0;

        if (salarioBruto <= 1100.0) {
            inss = salarioBruto * 0.075;
        } else if (salarioBruto <= 2203.48) {
            inss = salarioBruto * 0.09;
        } else if (salarioBruto <= 3305.22) {
            inss = salarioBruto * 0.12;
        } else if (salarioBruto <= 6433.57) {
            inss = salarioBruto * 0.14;
        } else {
            inss = 6433.57 * 0.14;
        }
        return inss;
    }

    public static double calcularImpostoRenda(double salarioBruto) {
        double impostoRenda = 0.0;
        return impostoRenda;
    }

    public static double calcularFGTS(double salarioBruto) {
        double fgts = salarioBruto * 0.08;
        return fgts;
    }
}

