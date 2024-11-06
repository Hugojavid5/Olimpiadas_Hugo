package model;

import java.util.Objects;

/**
 * Clase que representa una olimpiada con información sobre su identificador,
 * nombre, año, temporada y ciudad anfitriona.
 */
public class Olimpiada {
    private int id_olimpiada;
    private String nombre;
    private int anio;
    private SeasonCategory temporada; // Enum que representa la temporada
    private String ciudad;

    /**
     * Constructor con parámetros para crear una instancia de Olimpiada.
     *
     * @param id_olimpiada el identificador único de la olimpiada
     * @param nombre el nombre de la olimpiada
     * @param anio el año de la olimpiada
     * @param temporada la temporada de la olimpiada (Winter o Summer)
     * @param ciudad la ciudad anfitriona de la olimpiada
     */
    public Olimpiada(int id_olimpiada, String nombre, int anio, String temporada, String ciudad) {
        this.id_olimpiada = id_olimpiada;
        this.nombre = nombre;
        this.anio = anio;
        this.temporada = getSeasonCategory(temporada);
        this.ciudad = ciudad;
    }

    /**
     * Constructor vacío para crear una instancia de Olimpiada sin inicializar atributos.
     */
    public Olimpiada() {}

    /**
     * Enum que representa las posibles temporadas de una olimpiada: Invierno o Verano.
     */
    public enum SeasonCategory {
        WINTER, SUMMER;
    }

    /**
     * Devuelve la categoría de temporada basada en una cadena de entrada.
     *
     * @param season la temporada de la olimpiada en formato de cadena ("Winter" o "Summer")
     * @return la categoría de temporada como {@link SeasonCategory} o null si la entrada no coincide
     */
    public SeasonCategory getSeasonCategory(String season) {
        if (season.equals("Winter")) {
            return SeasonCategory.WINTER;
        } else if (season.equals("Summer")) {
            return SeasonCategory.SUMMER;
        }
        return null;
    }

    /**
     * Obtiene el identificador de la olimpiada.
     *
     * @return el id de la olimpiada
     */
    public int getId_olimpiada() {
        return id_olimpiada;
    }

    /**
     * Establece el identificador de la olimpiada.
     *
     * @param id_olimpiada el nuevo id de la olimpiada
     */
    public void setId_olimpiada(int id_olimpiada) {
        this.id_olimpiada = id_olimpiada;
    }

    /**
     * Obtiene el nombre de la olimpiada.
     *
     * @return el nombre de la olimpiada
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la olimpiada.
     *
     * @param nombre el nuevo nombre de la olimpiada
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el año en que se celebró la olimpiada.
     *
     * @return el año de la olimpiada
     */
    public int getAnio() {
        return anio;
    }

    /**
     * Establece el año en que se celebró la olimpiada.
     *
     * @param anio el nuevo año de la olimpiada
     */
    public void setAnio(int anio) {
        this.anio = anio;
    }

    /**
     * Obtiene la temporada de la olimpiada (invierno o verano).
     *
     * @return una cadena que representa la temporada de la olimpiada ("Winter" o "Summer")
     */
    public String getTemporada() {
        if (temporada.equals(SeasonCategory.WINTER)) {
            return "Winter";
        }
        return "Summer";
    }

    /**
     * Establece la temporada de la olimpiada.
     *
     * @param temporada la nueva temporada de la olimpiada
     */
    public void setTemporada(SeasonCategory temporada) {
        this.temporada = temporada;
    }

    /**
     * Obtiene la ciudad donde se celebró la olimpiada.
     *
     * @return la ciudad de la olimpiada
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Establece la ciudad donde se celebró la olimpiada.
     *
     * @param ciudad la nueva ciudad de la olimpiada
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * Compara esta olimpiada con otro objeto para verificar si son iguales
     * en base a su identificador único.
     *
     * @param o el objeto a comparar
     * @return true si los objetos son iguales, de lo contrario false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Olimpiada olimpiada = (Olimpiada) o;
        return id_olimpiada == olimpiada.id_olimpiada;
    }

    /**
     * Genera un código hash para esta olimpiada en base a su identificador único.
     *
     * @return el código hash de la olimpiada
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id_olimpiada);
    }

    /**
     * Retorna una representación en cadena de la olimpiada.
     *
     * @return el nombre de la olimpiada
     */
    @Override
    public String toString() {
        return nombre;
    }
}
