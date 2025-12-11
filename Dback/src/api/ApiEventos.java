package api;

import static spark.Spark.*;

import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import dao.ParticipanteDAO;
import dao.EventoDAO;
import dao.IngressoDAO;

import model.Participante;
import model.Evento;
import model.Ingresso;

public class ApiEventos {

    private static final ParticipanteDAO participanteDAO = new ParticipanteDAO();
    private static final EventoDAO eventoDAO = new EventoDAO();
    private static final IngressoDAO ingressoDAO = new IngressoDAO();

    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,(JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) ->
        new JsonPrimitive(src.toString())).registerTypeAdapter(LocalDateTime.class,(JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
            LocalDateTime.parse(json.getAsString())).create();

    private static final String APPLICATION_JSON = "application/json";

    public static void main(String[] args) {

        port(1234);

        after((req, res) -> res.type(APPLICATION_JSON));

        // ============================
        // PARTICIPANTES
        // ============================

        get("/participantes", (req, res) -> gson.toJson(participanteDAO.buscarTodos()));

        get("/participantes/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));
                Participante p = participanteDAO.buscarPorId(id);

                if (p != null) return gson.toJson(p);

                res.status(404);
                return "{\"mensagem\":\"Participante não encontrado\"}";
            } catch (Exception e) {
                res.status(400);
                return "{\"mensagem\":\"ID inválido\"}";
            }
        });

        post("/participantes", (req, res) -> {
            try {
                Participante p = gson.fromJson(req.body(), Participante.class);
                participanteDAO.inserir(p);

                res.status(201);
                return gson.toJson(p);
            } catch (Exception e) {
                res.status(500);
                return "{\"mensagem\":\"Erro ao criar participante\"}";
            }
        });

        put("/participantes/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));

                if (participanteDAO.buscarPorId(id) == null) {
                    res.status(404);
                    return "{\"mensagem\":\"Participante não encontrado\"}";
                }

                Participante p = gson.fromJson(req.body(), Participante.class);
                p.setIdParticipante(id);

                participanteDAO.atualizar(p);
                return gson.toJson(p);

            } catch (Exception e) {
                res.status(400);
                return "{\"mensagem\":\"Erro ao atualizar participante\"}";
            }
        });

        delete("/participantes/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));

                if (participanteDAO.buscarPorId(id) == null) {
                    res.status(404);
                    return "{\"mensagem\":\"Participante não encontrado\"}";
                }

                participanteDAO.deletar(id);
                res.status(204);
                return "";

            } catch (Exception e) {
                res.status(400);
                return "{\"mensagem\":\"Erro ao deletar participante\"}";
            }
        });


        // ============================
        // EVENTOS
        // ============================

        get("/eventos", (req, res) -> gson.toJson(eventoDAO.buscarTodos()));

        get("/eventos/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));
                Evento evento = eventoDAO.buscarPorId(id);

                if (evento != null) return gson.toJson(evento);

                res.status(404);
                return "{\"mensagem\":\"Evento não encontrado\"}";
            } catch (Exception e) {
                res.status(400);
                return "{\"mensagem\":\"ID inválido\"}";
            }
        });

        post("/eventos", (req, res) -> {
            try {
                Evento evento = gson.fromJson(req.body(), Evento.class);
                eventoDAO.inserir(evento);

                res.status(201);
                return gson.toJson(evento);
            } catch (Exception e) {
                res.status(500);
                return "{\"mensagem\":\"Erro ao criar evento\"}";
            }
        });

        put("/eventos/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));

                if (eventoDAO.buscarPorId(id) == null) {
                    res.status(404);
                    return "{\"mensagem\":\"Evento não encontrado\"}";
                }

                Evento evento = gson.fromJson(req.body(), Evento.class);
                evento.setIdEvento(id);

                eventoDAO.atualizar(evento);
                return gson.toJson(evento);

            } catch (Exception e) {
                res.status(400);
                return "{\"mensagem\":\"Erro ao atualizar evento\"}";
            }
        });

        delete("/eventos/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));

                if (eventoDAO.buscarPorId(id) == null) {
                    res.status(404);
                    return "{\"mensagem\":\"Evento não encontrado\"}";
                }

                eventoDAO.deletar(id);
                res.status(204);
                return "";

            } catch (Exception e) {
                res.status(400);
                return "{\"mensagem\":\"Erro ao deletar evento\"}";
            }
        });


        // ============================
        // INGRESSOS
        // ============================

        get("/ingressos", (req, res) -> gson.toJson(ingressoDAO.buscarTodos()));

        get("/ingressos/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));
                Ingresso ingresso = ingressoDAO.buscarPorId(id);

                if (ingresso != null) return gson.toJson(ingresso);

                res.status(404);
                return "{\"mensagem\":\"Ingresso não encontrado\"}";
            } catch (Exception e) {
                res.status(400);
                return "{\"mensagem\":\"ID inválido\"}";
            }
        });

        post("/ingressos", (req, res) -> {
            try {
                Ingresso ingresso = gson.fromJson(req.body(), Ingresso.class);
                ingressoDAO.inserir(ingresso);

                res.status(201);
                return gson.toJson(ingresso);
            } catch (Exception e) {
                res.status(500);
                return "{\"mensagem\":\"Erro ao criar ingresso\"}";
            }
        });

        put("/ingressos/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));

                if (ingressoDAO.buscarPorId(id) == null) {
                    res.status(404);
                    return "{\"mensagem\":\"Ingresso não encontrado\"}";
                }

                Ingresso ingresso = gson.fromJson(req.body(), Ingresso.class);
                ingresso.setIdIngresso(id);

                ingressoDAO.atualizar(ingresso);
                return gson.toJson(ingresso);

            } catch (Exception e) {
                res.status(400);
                return "{\"mensagem\":\"Erro ao atualizar ingresso\"}";
            }
        });

        delete("/ingressos/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));

                if (ingressoDAO.buscarPorId(id) == null) {
                    res.status(404);
                    return "{\"mensagem\":\"Ingresso não encontrado\"}";
                }

                ingressoDAO.deletar(id);
                res.status(204);
                return "";

            } catch (Exception e) {
                res.status(400);
                return "{\"mensagem\":\"Erro ao deletar ingresso\"}";
            }
        });


        System.out.println("API iniciada na porta 4567.");
        System.out.println("Rotas disponíveis:");
        System.out.println("/participantes");
        System.out.println("/eventos");
        System.out.println("/ingressos");
    }
}
