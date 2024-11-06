package controlador;

import model.Evento;

public class EventoController {
    private Evento evento;

    public EventoController(Evento evento) {
        this.evento = evento;
    }

    public EventoController() {
        this.evento = null;
    }
}
