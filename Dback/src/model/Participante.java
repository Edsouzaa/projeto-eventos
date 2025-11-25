package model;

public class Participante {

    private Long idParticipante;
    private String nome;
    private String email;

    // Construtor vazio
    public Participante() {
    }

    // Construtor cheio
    public Participante(Long idParticipante, String nome, String email) {
        this.idParticipante = idParticipante;
        this.nome = nome;
        this.email = email;
    }

    // Construtor sem idParticipante
    public Participante(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    // Getters e Setters
    public Long getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(Long idParticipante) {
        this.idParticipante = idParticipante;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Participante{" +
                "idParticipante=" + idParticipante +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}