package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.sql.Timestamp;

import model.Evento;
import util.ConnectionFactory;

public class EventoDAO {

    // ------------------------------------
    // READ
    // ------------------------------------
    public List<Evento> buscarTodos() {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT id_evento, nome_evento, data_inicio_evento, data_fim_evento, local, capacidade_maxima FROM eventos";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Evento evento = new Evento(
                        rs.getLong("id_evento"),
                        rs.getString("nome_evento"),
                        rs.getTimestamp("data_inicio_evento").toLocalDateTime(),
                        rs.getTimestamp("data_fim_evento").toLocalDateTime(),
                        rs.getString("local"),
                        rs.getInt("capacidade_maxima"));
                eventos.add(evento);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar eventos: " + e.getMessage());
            e.printStackTrace();
        }
        return eventos;
    }

    // ------------------------------------
    // READ BY ID
    // ------------------------------------
    public Evento buscarPorId(Long id) {
        Evento evento = null;
        String sql = "SELECT id_evento, nome_evento, data_inicio_evento, data_fim_evento, local, capacidade_maxima FROM eventos WHERE id_evento = ?";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    evento = new Evento(
                        rs.getLong("id_evento"),
                        rs.getString("nome_evento"),
                        rs.getTimestamp("data_inicio_evento").toLocalDateTime(),
                        rs.getTimestamp("data_fim_evento").toLocalDateTime(),
                        rs.getString("local"),
                        rs.getInt("capacidade_maxima"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar evento por ID: " + id + ". Detalhes: " + e.getMessage());
            e.printStackTrace();
        }
        return evento;
    }

    // ------------------------------------
    // CREATE
    // ------------------------------------
    public void inserir(Evento evento) {
        String sql = "INSERT INTO eventos (nome_evento, data_inicio_evento, data_fim_evento, local, capacidade_maxima) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, evento.getNomeEvento());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    evento.setIdEvento(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir categoria: " + evento.getNomeEvento() + ". Detalhes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ------------------------------------
    // UPDATE
    // ------------------------------------
    public void atualizar(Evento evento) {
        String sql = "UPDATE eventos SET nome_evento= ?, data_inicio_evento= ?, data_fim_evento= ?, local= ?, capacidade_maxima= ?, WHERE id_evento = ?";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, evento.getNomeEvento());
            stmt.setTimestamp(2, Timestamp.valueOf(evento.getDataInicioEvento()));
            stmt.setTimestamp(3, Timestamp.valueOf(evento.getDataFimEvento()));
            stmt.setString(4, evento.getLocal());
            stmt.setInt(5, evento.getCapacidadeMaxima());
            stmt.setLong(6, evento.getIdEvento()); // ID no WHERE

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar categoria ID: " + evento.getIdEvento() + ". Detalhes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ------------------------------------
    // DELETE
    // ------------------------------------
    public void deletar(Long id) {
        String sql = "DELETE FROM eventos WHERE id_evento = ?";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao deletar eventos ID: " + id + ". Detalhes: " + e.getMessage());
            e.printStackTrace();
        }
    }
}