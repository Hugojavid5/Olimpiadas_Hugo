package model;

import java.util.Objects;

/**
 * Clase que representa un deporte con información sobre su identificador y nombre.
 */
public class Deporte {
    private int id_deporte;
    private String nombre;

    /**
     * Constructor con parámetros para crear una instancia de Deporte.
     *
     * @param id_deporte el identificador único del deporte
     * @param nombre el nombre del deporte
     */
    public Deporte(int id_deporte, String nombre) {
        this.id_deporte = id_deporte;
        this.nombre = nombre;
    }

    /**
     * Constructor vacío para crear una instancia de Deporte sin inicializar atributos.
     */
    public Deporte() {}

    /**
     * Obtiene el identificador único del deporte.
     *
     * @return el id del deporte
     */
    public int getId_deporte() {
        return id_deporte;
    }

    /**
     * Establece el identificador único del deporte.
     *
     * @param id_deporte el nuevo id del deporte
     */
    public void setId_deporte(int id_deporte) {
        this.id_deporte = id_deporte;
    }

    /**
     * Obtiene el nombre del deporte.
     *
     * @return el nombre del deporte
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del deporte.
     *
     * @param nombre el nuevo nombre del deporte
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Compara esta instancia de Deporte con otro objeto para verificar si son iguales
     * en base a su identificador único.
     *
     * @param o el objeto a comparar
     * @return true si los objetos son iguales, de lo contrario false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deporte deporte = (Deporte) o;
        return id_deporte == deporte.id_deporte;
    }

    /**
     * Genera un código hash para esta instancia de Deporte en base a su identificador único.
     *
     * @return el código hash del deporte
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id_deporte);
    }

    /**
     * Retorna una representación en cadena del deporte.
     *
     * @return el nombre del deporte
     */
    @Override
    public String toString() {
        return nombre;
    }
}
