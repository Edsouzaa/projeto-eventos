package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.Evento;
import model.Participante;
import model.Ingresso;
import util.ConnectionFactory;

public class IngressoDAO {

    // ------------------------------------
    // READ ALL
    // ------------------------------------
    public List<Ingresso> buscarTodos() {
        List<Ingresso> ingressos = new ArrayList<>();

        String sql = "SELECT i.idIngresso, i.dataInscricao, "
                + "p.idParticipante, p.nome AS nome_participante, p.email AS email_participante, "
                + "e.idEvento, e.nomeEvento, e.dataInicioEvento, e.dataFimEvento, e.localEvento, e.capacidadeMaxima "
                + "FROM ingressos i "
                + "JOIN participantes p ON i.participanteId = p.idParticipante "
                + "JOIN eventos e ON i.eventoId = e.idEvento";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Participante participante = new Participante(
                        rs.getLong("idParticipante"),
                        rs.getString("nome_participante"),
                        rs.getString("email_participante")
                );

                Evento evento = new Evento(
                        rs.getLong("idEvento"),
                        rs.getString("nomeEvento"),
                        rs.getTimestamp("dataInicioEvento").toLocalDateTime(),
                        rs.getTimestamp("dataFimEvento").toLocalDateTime(),
                        rs.getString("localEvento"),
                        rs.getInt("capacidadeMaxima")
                );

                Ingresso ingresso = new Ingresso(
                        rs.getLong("idIngresso"),
                        participante,
                        evento,
                        rs.getTimestamp("dataInscricao").toLocalDateTime()
                );

                ingressos.add(ingresso);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar ingressos: " + e.getMessage());
            e.printStackTrace();
        }

        return ingressos;
    }

    // ------------------------------------
    // READ BY ID
    // ------------------------------------
    public Ingresso buscarPorId(Long id) {
        Ingresso ingresso = null;

        String sql = "SELECT i.idIngresso, i.dataInscricao, "
                + "p.idParticipante, p.nome AS nome_participante, p.email AS email_participante, "
                + "e.idEvento, e.nomeEvento, e.dataInicioEvento, e.dataFimEvento, e.localEvento, e.capacidadeMaxima "
                + "FROM ingressos i "
                + "JOIN participantes p ON i.participanteId = p.idParticipante "
                + "JOIN eventos e ON i.eventoId = e.idEvento "
                + "WHERE i.idIngresso = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    Participante participante = new Participante(
                            rs.getLong("idParticipante"),
                            rs.getString("nome_participante"),
                            rs.getString("email_participante")
                    );

                    Evento evento = new Evento(
                            rs.getLong("idEvento"),
                            rs.getString("nomeEvento"),
                            rs.getTimestamp("dataInicioEvento").toLocalDateTime(),
                            rs.getTimestamp("dataFimEvento").toLocalDateTime(),
                            rs.getString("localEvento"),
                            rs.getInt("capacidadeMaxima")
                    );

                    ingresso = new Ingresso(
                            rs.getLong("idIngresso"),
                            participante,
                            evento,
                            rs.getTimestamp("dataInscricao").toLocalDateTime()
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar ingresso por ID: " + id + ". Detalhes: " + e.getMessage());
            e.printStackTrace();
        }

        return ingresso;
    }

    // ------------------------------------
    // CREATE
    // ------------------------------------
    public void inserir(Ingresso ingresso) {

        String sql = "INSERT INTO ingressos (participanteId, eventoId, dataInscricao) "
                + "VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, ingresso.getParticipanteId().getIdParticipante());
            stmt.setLong(2, ingresso.getEventoId().getIdEvento());
            stmt.setTimestamp(3, Timestamp.valueOf(ingresso.getDataInscricao()));

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    ingresso.setIdIngresso(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir ingresso. Detalhes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ------------------------------------
    // UPDATE
    // ------------------------------------
    public void atualizar(Ingresso ingresso) {

        String sql = "UPDATE ingressos SET participanteId = ?, eventoId = ?, dataInscricao = ? "
                + "WHERE idIngresso = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, ingresso.getParticipanteId().getIdParticipante());
            stmt.setLong(2, ingresso.getEventoId().getIdEvento());
            stmt.setTimestamp(3, Timestamp.valueOf(ingresso.getDataInscricao()));
            stmt.setLong(4, ingresso.getIdIngresso());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar ingresso ID: " + ingresso.getIdIngresso() 
                    + ". Detalhes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ------------------------------------
    // DELETE
    // ------------------------------------
    public void deletar(Long id) {

        String sql = "DELETE FROM ingressos WHERE idIngresso = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int linhas = stmt.executeUpdate();
            System.out.println("Ingresso ID " + id + " deletado. Linhas afetadas: " + linhas);

        } catch (SQLException e) {
            System.err.println("Erro ao deletar ingresso ID: " + id + ". Detalhes: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
