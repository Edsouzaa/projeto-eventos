package model;

import java.time.LocalDateTime;

public class Ingresso {

    private Long idIngresso;
    private Participante participanteId;
    private Evento eventoId;
    private LocalDateTime dataInscricao;

    // Construtor vazio
    public Ingresso() {
    }

    // Construtor cheio
    public Ingresso(Long idIngresso, Participante participanteId, Evento eventoId, LocalDateTime dataInscricao) {
        this.idIngresso = idIngresso;
        this.participanteId = participanteId;
        this.eventoId = eventoId;
        this.dataInscricao = dataInscricao;
    }

    // Construtor sem idIngresso
    public Ingresso(Participante participanteId, Evento eventoId, LocalDateTime dataInscricao) {
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

    public Participante getParticipanteId() {
        return participanteId;
    }

    public void setParticipanteId(Participante participanteId) {
        this.participanteId = participanteId;
    }

    public Evento getEventoId() {
        return eventoId;
    }

    public void setEventoId(Evento eventoId) {
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