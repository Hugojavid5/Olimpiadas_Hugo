package controlador;

import model.Participacion;

public class ParticipacionController {
    private Participacion participacion;

    public ParticipacionController(Participacion participacion) {
        this.participacion = participacion;
    }
    public ParticipacionController() {
        this.participacion = null;
    }
}