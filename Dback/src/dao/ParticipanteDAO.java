package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Participante;
import util.ConnectionFactory;

public class ParticipanteDAO {

    // ------------------------------------
    // READ - Buscar todos
    // ------------------------------------
    public List<Participante> buscarTodos() {
        List<Participante> participantes = new ArrayList<>();
        String sql = "SELECT idParticipante, nome, email FROM participantes";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Participante p = new Participante(
                        rs.getLong("idParticipante"),
                        rs.getString("nome"),
                        rs.getString("email")
                );
                participantes.add(p);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar participantes: " + e.getMessage());
            e.printStackTrace();
        }

        return participantes;
    }

    // ------------------------------------
    // READ BY ID
    // ------------------------------------
    public Participante buscarPorId(Long id) {
        Participante participante = null;
        String sql = "SELECT idParticipante, nome, email FROM participantes WHERE idParticipante = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    participante = new Participante(
                            rs.getLong("idParticipante"),
                            rs.getString("nome"),
                            rs.getString("email")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar participante por ID: " + id + ". Detalhes: " + e.getMessage());
            e.printStackTrace();
        }

        return participante;
    }

    // ------------------------------------
    // CREATE
    // ------------------------------------
    public void inserir(Participante participante) {
        String sql = "INSERT INTO participantes (nome, email) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, participante.getNome());
            stmt.setString(2, participante.getEmail());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    participante.setIdParticipante(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir participante: " + participante.getNome() +
                    ". Detalhes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ------------------------------------
    // UPDATE
    // ------------------------------------
    public void atualizar(Participante participante) {
        String sql = "UPDATE participantes SET nome = ?, email = ? WHERE idParticipante = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, participante.getNome());
            stmt.setString(2, participante.getEmail());
            stmt.setLong(3, participante.getIdParticipante());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar participante ID: " +
                    participante.getIdParticipante() + ". Detalhes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ------------------------------------
    // DELETE
    // ------------------------------------
    public void deletar(Long id) {
        String sql = "DELETE FROM participantes WHERE idParticipante = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao deletar participante ID: " + id + ". Detalhes: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
