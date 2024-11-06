package model;

import java.util.Objects;

/**
 * Clase que representa un equipo deportivo con información sobre su identificador,
 * nombre e iniciales.
 */
public class Equipo {
    private int id_equipo;
    private String nombre;
    private String iniciales;

    /**
     * Constructor con parámetros para crear una instancia de Equipo.
     *
     * @param id_equipo el identificador único del equipo
     * @param nombre el nombre del equipo
     * @param iniciales las iniciales del equipo
     */
    public Equipo(int id_equipo, String nombre, String iniciales) {
        this.id_equipo = id_equipo;
        this.nombre = nombre;
        this.iniciales = iniciales;
    }

    /**
     * Constructor vacío para crear una instancia de Equipo sin inicializar atributos.
     */
    public Equipo() {}

    /**
     * Obtiene el identificador único del equipo.
     *
     * @return el id del equipo
     */
    public int getId_equipo() {
        return id_equipo;
    }

    /**
     * Establece el identificador único del equipo.
     *
     * @param id_equipo el nuevo id del equipo
     */
    public void setId_equipo(int id_equipo) {
        this.id_equipo = id_equipo;
    }

    /**
     * Obtiene el nombre del equipo.
     *
     * @return el nombre del equipo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del equipo.
     *
     * @param nombre el nuevo nombre del equipo
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene las iniciales del equipo.
     *
     * @return las iniciales del equipo
     */
    public String getIniciales() {
        return iniciales;
    }

    /**
     * Establece las iniciales del equipo.
     *
     * @param iniciales las nuevas iniciales del equipo
     */
    public void setIniciales(String iniciales) {
        this.iniciales = iniciales;
    }

    /**
     * Compara esta instancia de Equipo con otro objeto para verificar si son iguales
     * en base a su identificador único.
     *
     * @param o el objeto a comparar
     * @return true si los objetos son iguales, de lo contrario false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipo equipo = (Equipo) o;
        return id_equipo == equipo.id_equipo;
    }

    /**
     * Genera un código hash para esta instancia de Equipo en base a su identificador único.
     *
     * @return el código hash del equipo
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id_equipo);
    }

    /**
     * Retorna una representación en cadena del equipo.
     *
     * @return el nombre del equipo
     */
    @Override
    public String toString() {
        return nombre;
    }
}
