package model;

import java.util.Objects;

/**
 * Clase que representa un evento deportivo con información sobre su identificador,
 * nombre, olimpiada y deporte asociado.
 */
public class Evento {
    private int id_evento;
    private String nombre;
    private Olimpiada olimpiada;
    private Deporte deporte;

    /**
     * Constructor con parámetros para crear una instancia de Evento.
     *
     * @param id_evento el identificador único del evento
     * @param nombre el nombre del evento
     * @param olimpiada la olimpiada asociada al evento
     * @param deporte el deporte relacionado con el evento
     */
    public Evento(int id_evento, String nombre, Olimpiada olimpiada, Deporte deporte) {
        this.id_evento = id_evento;
        this.nombre = nombre;
        this.olimpiada = olimpiada;
        this.deporte = deporte;
    }

    /**
     * Constructor vacío para crear una instancia de Evento sin inicializar atributos.
     */
    public Evento() {}

    /**
     * Obtiene el identificador único del evento.
     *
     * @return el id del evento
     */
    public int getId_evento() {
        return id_evento;
    }

    /**
     * Establece el identificador único del evento.
     *
     * @param id_evento el nuevo id del evento
     */
    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    /**
     * Obtiene el nombre del evento.
     *
     * @return el nombre del evento
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del evento.
     *
     * @param nombre el nuevo nombre del evento
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la olimpiada asociada al evento.
     *
     * @return la olimpiada del evento
     */
    public Olimpiada getOlimpiada() {
        return olimpiada;
    }

    /**
     * Establece la olimpiada asociada al evento.
     *
     * @param olimpiada la nueva olimpiada del evento
     */
    public void setOlimpiada(Olimpiada olimpiada) {
        this.olimpiada = olimpiada;
    }

    /**
     * Obtiene el deporte relacionado con el evento.
     *
     * @return el deporte del evento
     */
    public Deporte getDeporte() {
        return deporte;
    }

    /**
     * Establece el deporte relacionado con el evento.
     *
     * @param deporte el nuevo deporte del evento
     */
    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }

    /**
     * Compara esta instancia de Evento con otro objeto para verificar si son iguales
     * en base a su identificador único.
     *
     * @param o el objeto a comparar
     * @return true si los objetos son iguales, de lo contrario false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evento evento = (Evento) o;
        return id_evento == evento.id_evento;
    }

    /**
     * Genera un código hash para esta instancia de Evento en base a su identificador único.
     *
     * @return el código hash del evento
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id_evento);
    }

    /**
     * Retorna una representación en cadena del evento.
     *
     * @return el nombre del evento
     */
    @Override
    public String toString() {
        return nombre;
    }
}
