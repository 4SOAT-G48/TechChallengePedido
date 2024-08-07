package br.com.fiap.soat.grupo48.pedido.domain.valueobject;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;

public class GeradorDeNumeroSequencial {
    private static final String[] MESES = {
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"
    };
    private static final String[] SEMANAS = {
        "A", "B", "C", "D", "E"
    };

    private static GeradorDeNumeroSequencial instance;
    private final DecimalFormat df;
    private int numeroSequencial = 0;
    private LocalDate ultimoReset = LocalDate.MIN;
    private String letraMes;
    private String letraSemana;
    private int numeroDia;
    private int hora;

    private GeradorDeNumeroSequencial() {
        df = new DecimalFormat("0000");
    }

    public static synchronized GeradorDeNumeroSequencial getInstance() {
        if (instance == null) {
            instance = new GeradorDeNumeroSequencial();
        }
        return instance;
    }

    public String proximoNumero() {
        LocalDate hoje = LocalDate.now();
        if (!ultimoReset.equals(hoje)) {
            numeroSequencial = 0;
            ultimoReset = hoje;
            var dataHoraAtual = LocalDateTime.now();
            var mes = dataHoraAtual.getMonthValue();
            var semana = dataHoraAtual.get(WeekFields.ISO.weekOfMonth());

            letraMes = MESES[mes - 1];
            letraSemana = SEMANAS[semana - 1];

            numeroDia = dataHoraAtual.getDayOfMonth();
        }
        return letraMes + letraSemana + numeroDia + LocalDateTime.now().getHour() + df.format(++numeroSequencial);
    }
}
