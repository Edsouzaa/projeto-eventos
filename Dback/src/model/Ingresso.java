package model;

import java.time.LocalDateTime;

public class Ingresso {

    private Long idIngresso;
    private Long participanteId;
    private Long eventoId;
    private LocalDateTime dataInscricao;

    // Construtor vazio
    public Ingresso() {
    }

    // Construtor cheio
    public Ingresso(Long idIngresso, Long participanteId, Long eventoId, LocalDateTime dataInscricao) {
        this.idIngresso = idIngresso;
        this.participanteId = participanteId;
        this.eventoId = eventoId;
        this.dataInscricao = dataInscricao;
    }

    // Construtor sem idIngresso
    public Ingresso(Long participanteId, Long eventoId, LocalDateTime dataInscricao) {
        this.participanteId = participanteId;
        this.eventoId = eventoId;
        this.dataInscricao = dataInscricao;
    }

    // Getters e Setters
    public Long getIdIngresso() {
        return idIngresso;
    }

    public void setIdIngresso(Long idIngresso) {
        this.idIngresso = idIngresso;
    }

    public Long getParticipanteId() {
        return participanteId;
    }

    public void setParticipanteId(Long participanteId) {
        this.participanteId = participanteId;
    }

    public Long getEventoId() {
        return eventoId;
    }

    public void setEventoId(Long eventoId) {
        this.eventoId = eventoId;
    }

    public LocalDateTime getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(LocalDateTime dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    @Override
    public String toString() {
        return "Ingresso{" +
                "idIngresso=" + idIngresso +
                ", participanteId=" + participanteId +
                ", eventoId=" + eventoId +
                ", dataInscricao=" + dataInscricao +
                '}';
    }
}