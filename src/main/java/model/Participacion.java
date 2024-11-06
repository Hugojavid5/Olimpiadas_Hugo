package model;

/**
 * Clase que representa la participación de un deportista en un evento,
 * asociada a un equipo y con información sobre su edad y medalla obtenida.
 */
public class Participacion {
    private Deportista deportista;
    private Evento evento;
    private Equipo equipo;
    private int edad;
    private String medalla;

    /**
     * Constructor con parámetros para crear una instancia de Participación.
     *
     * @param deportista el deportista que participa
     * @param evento el evento en el que participa
     * @param equipo el equipo al que pertenece el deportista
     * @param edad la edad del deportista durante la participación
     * @param medalla la medalla obtenida en la participación (si corresponde)
     */
    public Participacion(Deportista deportista, Evento evento, Equipo equipo, int edad, String medalla) {
        this.deportista = deportista;
        this.evento = evento;
        this.equipo = equipo;
        this.edad = edad;
        this.medalla = medalla;
    }

    /**
     * Constructor vacío para crear una instancia de Participación sin inicializar atributos.
     */
    public Participacion() {}

    /**
     * Obtiene el deportista que participa en el evento.
     *
     * @return el deportista
     */
    public Deportista getDeportista() {
        return deportista;
    }

    /**
     * Establece el deportista que participa en el evento.
     *
     * @param deportista el nuevo deportista que participa
     */
    public void setDeportista(Deportista deportista) {
        this.deportista = deportista;
    }

    /**
     * Obtiene el evento de la participación.
     *
     * @return el evento
     */
    public Evento getEvento() {
        return evento;
    }

    /**
     * Establece el evento de la participación.
     *
     * @param evento el nuevo evento de la participación
     */
    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    /**
     * Obtiene el equipo al que pertenece el deportista.
     *
     * @return el equipo
     */
    public Equipo getEquipo() {
        return equipo;
    }

    /**
     * Establece el equipo al que pertenece el deportista.
     *
     * @param equipo el nuevo equipo
     */
    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    /**
     * Obtiene la edad del deportista en el momento de la participación.
     *
     * @return la edad
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Establece la edad del deportista en el momento de la participación.
     *
     * @param edad la nueva edad del deportista
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * Obtiene la medalla obtenida en la participación (si corresponde).
     *
     * @return la medalla
     */
    public String getMedalla() {
        return medalla;
    }

    /**
     * Establece la medalla obtenida en la participación.
     *
     * @param medalla la nueva medalla obtenida
     */
    public void setMedalla(String medalla) {
        this.medalla = medalla;
    }
}
