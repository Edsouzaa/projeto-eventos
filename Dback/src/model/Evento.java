package model;
import java.time.LocalDateTime;

public class Evento {

    private Long idEvento;
    private String nomeEvento;
    private LocalDateTime dataInicioEvento;
    private LocalDateTime dataFimEvento;
    private String local;
    private int capacidadeMaxima;

    // Construtor vazio
    public Evento() {
    }

    // Construtor cheio
    public Evento(Long idEvento, String nomeEvento, LocalDateTime dataInicioEvento, 
                  LocalDateTime dataFimEvento, String local, int capacidadeMaxima) {
        this.idEvento = idEvento;
        this.nomeEvento = nomeEvento;
        this.dataInicioEvento = dataInicioEvento;
        this.dataFimEvento = dataFimEvento;
        this.local = local;
        this.capacidadeMaxima = capacidadeMaxima;
    }

    // Construtor sem idEvento (para quando o id for gerado pelo banco de dados)
    public Evento(String nomeEvento, LocalDateTime dataInicioEvento, LocalDateTime dataFimEvento, 
                  String local, int capacidadeMaxima) {
        this.nomeEvento = nomeEvento;
        this.dataInicioEvento = dataInicioEvento;
        this.dataFimEvento = dataFimEvento;
        this.local = local;
        this.capacidadeMaxima = capacidadeMaxima;
    }

    // Getters e Setters
    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public LocalDateTime getDataInicioEvento() {
        return dataInicioEvento;
    }

    public void setDataInicioEvento(LocalDateTime dataInicioEvento) {
        this.dataInicioEvento = dataInicioEvento;
    }

    public LocalDateTime getDataFimEvento() {
        return dataFimEvento;
    }

    public void setDataFimEvento(LocalDateTime dataFimEvento) {
        this.dataFimEvento = dataFimEvento;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "idEvento=" + idEvento +
                ", nomeEvento='" + nomeEvento + '\'' +
                ", dataInicioEvento=" + dataInicioEvento +
                ", dataFimEvento=" + dataFimEvento +
                ", local='" + local + '\'' +
                ", capacidadeMaxima=" + capacidadeMaxima +
                '}';
    }
}