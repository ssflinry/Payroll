package com.example.application.utilities;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class FormatadorValores {
    private static final NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    private static final DecimalFormat formatoDecimal = new DecimalFormat("#,##0.00");

    public static String formatarMoeda(double valor) {
        return formatoMoeda.format(valor);
    }

    public static String formatarDecimal(double valor) {
        return formatoDecimal.format(valor);
    }

    public static double desformatarMoeda(String valorFormatado) {
        try {
            return formatoMoeda.parse(valorFormatado).doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public static double desformatarDecimal(String valorFormatado) {
        try {
            return formatoDecimal.parse(valorFormatado).doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public static String formatarCPF(String cpf) {
        if (cpf == null) {
            return "";
        }

        // Aplicar a máscara de CPF (###.###.###-##)
        return cpf.replaceAll("^(\\d{3})(\\d{3})(\\d{3})(\\d{2})$", "$1.$2.$3-$4");
    }

    public static String formatarPIS(String pis) {
        if (pis == null) {
            return "";
        }

        // Aplicar a máscara de PIS (###.#####.##-#)
        return pis.replaceAll("^(\\d{3})(\\d{5})(\\d{2})(\\d{1})$", "$1.$2.$3-$4");
    }

    public static String formatarRG(String rg) {
        if (rg == null) {
            return "";
        }
        return rg.replaceAll("^(\\d{2})(\\d{3})(\\d{3})(\\d{1})$", "$1.$2.$3-$4");
    }

    public static String formatarData(String data) {
        if (data == null) {
            return "";
        }

        // Suponha que o formato da data seja "YYYYMMDD"
        if (data.length() == 8) {
            return data.replaceAll("^(\\d{4})(\\d{2})(\\d{2})$", "$1/$2/$3");
        }

        return data;
    }

    public static String formatarDataHora(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        return sdf.format(date);
    }

    public static String getMesPorExtenso(int numeroMes) {
        String[] meses = new DateFormatSymbols(new Locale("pt", "BR")).getMonths();
        if (numeroMes >= 1 && numeroMes <= 12) {
            String mesPorExtenso = meses[numeroMes - 1];
            mesPorExtenso = mesPorExtenso.substring(0, 1).toUpperCase() + mesPorExtenso.substring(1).toLowerCase();
            return mesPorExtenso;
        } else {
            return "Mês inválido";
        }
    }

}
