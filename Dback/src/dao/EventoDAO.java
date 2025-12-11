package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

import model.Evento;
import util.ConnectionFactory;

public class EventoDAO {

    // ------------------------------------
    // READ
    // ------------------------------------
    public List<Evento> buscarTodos() {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT idEvento, nomeEvento, dataInicioEvento, dataFimEvento, localEvento, capacidadeMaxima FROM eventos";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Evento evento = new Evento(
                        rs.getLong("idEvento"),
                        rs.getString("nomeEvento"),
                        rs.getTimestamp("dataInicioEvento").toLocalDateTime(),
                        rs.getTimestamp("dataFimEvento").toLocalDateTime(),
                        rs.getString("localEvento"),
                        rs.getInt("capacidadeMaxima")
                );
                eventos.add(evento);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar eventos: " + e.getMessage());
        }
        return eventos;
    }

    // ------------------------------------
    // READ BY ID
    // ------------------------------------
    public Evento buscarPorId(Long id) {
        Evento evento = null;
        String sql = "SELECT idEvento, nomeEvento, dataInicioEvento, dataFimEvento, localEvento, capacidadeMaxima "
                   + "FROM eventos WHERE idEvento = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    evento = new Evento(
                            rs.getLong("idEvento"),
                            rs.getString("nomeEvento"),
                            rs.getTimestamp("dataInicioEvento").toLocalDateTime(),
                            rs.getTimestamp("dataFimEvento").toLocalDateTime(),
                            rs.getString("localEvento"),
                            rs.getInt("capacidadeMaxima")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar evento por ID: " + id + ". Detalhes: " + e.getMessage());
        }
        return evento;
    }

    // ------------------------------------
    // CREATE
    // ------------------------------------
    public void inserir(Evento evento) {
        String sql = """
            INSERT INTO eventos 
            (nomeEvento, dataInicioEvento, dataFimEvento, localEvento, capacidadeMaxima) 
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, evento.getNomeEvento());
            stmt.setTimestamp(2, Timestamp.valueOf(evento.getDataInicioEvento()));
            stmt.setTimestamp(3, Timestamp.valueOf(evento.getDataFimEvento()));
            stmt.setString(4, evento.getLocal());
            stmt.setInt(5, evento.getCapacidadeMaxima());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    evento.setIdEvento(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir evento: " + evento.getNomeEvento() + ". Detalhes: " + e.getMessage());
        }
    }

    // ------------------------------------
    // UPDATE
    // ------------------------------------
    public void atualizar(Evento evento) {
        String sql = """
            UPDATE eventos SET 
                nomeEvento = ?, 
                dataInicioEvento = ?, 
                dataFimEvento = ?, 
                localEvento = ?, 
                capacidadeMaxima = ?
            WHERE idEvento = ?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, evento.getNomeEvento());
            stmt.setTimestamp(2, Timestamp.valueOf(evento.getDataInicioEvento()));
            stmt.setTimestamp(3, Timestamp.valueOf(evento.getDataFimEvento()));
            stmt.setString(4, evento.getLocal());
            stmt.setInt(5, evento.getCapacidadeMaxima());
            stmt.setLong(6, evento.getIdEvento());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar evento ID " + evento.getIdEvento() + ": " + e.getMessage());
        }
    }

    // ------------------------------------
    // DELETE
    // ------------------------------------
    public void deletar(Long id) {
        String sql = "DELETE FROM eventos WHERE idEvento = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao deletar evento ID " + id + ": " + e.getMessage());
        }
    }
}
