package model;

import java.sql.Blob;
import java.util.Objects;

/**
 * Clase que representa un deportista con información sobre su identificador,
 * nombre, sexo, peso, altura y foto.
 */
public class Deportista {
    private int id_deportista;
    private String nombre;
    private SexCategory sexo; // Enum
    private int peso;
    private int altura;
    private Blob foto;

    /**
     * Constructor con parámetros para crear una instancia de Deportista.
     *
     * @param id_deportista el identificador único del deportista
     * @param nombre el nombre del deportista
     * @param sexo el sexo del deportista como un caracter ('M' o 'F')
     * @param peso el peso del deportista en kilogramos
     * @param altura la altura del deportista en centímetros
     * @param foto una imagen en formato Blob del deportista
     */
    public Deportista(int id_deportista, String nombre, char sexo, int peso, int altura, Blob foto) {
        this.id_deportista = id_deportista;
        this.nombre = nombre;
        this.sexo = getSexCategory(sexo);
        this.peso = peso;
        this.altura = altura;
        this.foto = foto;
    }

    /**
     * Constructor vacío para crear una instancia de Deportista sin inicializar atributos.
     */
    public Deportista() {}

    /**
     * Enum que representa las categorías de sexo para un deportista.
     */
    public enum SexCategory {
        MALE, FEMALE;
    }

    /**
     * Metodo para obtener la categoría de sexo a partir de un carácter.
     *
     * @param sex el carácter que representa el sexo ('M' para masculino, 'F' para femenino)
     * @return la categoría de sexo correspondiente, o null si el carácter no es válido
     */
    public SexCategory getSexCategory(char sex) {
        if (sex == 'F') {
            return SexCategory.FEMALE;
        } else if (sex == 'M') {
            return SexCategory.MALE;
        }
        return null;
    }

    /**
     * Obtiene el identificador único del deportista.
     *
     * @return el id del deportista
     */
    public int getId_deportista() {
        return id_deportista;
    }

    /**
     * Establece el identificador único del deportista.
     *
     * @param id_deportista el nuevo id del deportista
     */
    public void setId_deportista(int id_deportista) {
        this.id_deportista = id_deportista;
    }

    /**
     * Obtiene el nombre del deportista.
     *
     * @return el nombre del deportista
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del deportista.
     *
     * @param nombre el nuevo nombre del deportista
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el sexo del deportista como un carácter ('M' para masculino, 'F' para femenino).
     *
     * @return el sexo del deportista
     */
    public char getSexo() {
        if (sexo.equals(SexCategory.FEMALE)) {
            return 'F';
        }
        return 'M';
    }

    /**
     * Establece el sexo del deportista.
     *
     * @param sexo la nueva categoría de sexo del deportista
     */
    public void setSexo(SexCategory sexo) {
        this.sexo = sexo;
    }

    /**
     * Obtiene el peso del deportista.
     *
     * @return el peso del deportista en kilogramos
     */
    public int getPeso() {
        return peso;
    }

    /**
     * Establece el peso del deportista.
     *
     * @param peso el nuevo peso del deportista en kilogramos
     */
    public void setPeso(int peso) {
        this.peso = peso;
    }

    /**
     * Obtiene la altura del deportista.
     *
     * @return la altura del deportista en centímetros
     */
    public int getAltura() {
        return altura;
    }

    /**
     * Establece la altura del deportista.
     *
     * @param altura la nueva altura del deportista en centímetros
     */
    public void setAltura(int altura) {
        this.altura = altura;
    }

    /**
     * Obtiene la foto del deportista en formato Blob.
     *
     * @return la foto del deportista
     */
    public Blob getFoto() {
        return foto;
    }

    /**
     * Establece la foto del deportista.
     *
     * @param foto la nueva foto del deportista en formato Blob
     */
    public void setFoto(Blob foto) {
        this.foto = foto;
    }

    /**
     * Compara esta instancia de Deportista con otro objeto para verificar si son iguales
     * en base a su identificador único.
     *
     * @param o el objeto a comparar
     * @return true si los objetos son iguales, de lo contrario false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deportista that = (Deportista) o;
        return id_deportista == that.id_deportista;
    }

    /**
     * Genera un código hash para esta instancia de Deportista en base a su identificador único.
     *
     * @return el código hash del deportista
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id_deportista);
    }

    /**
     * Retorna una representación en cadena del deportista.
     *
     * @return el nombre del deportista
     */
    @Override
    public String toString() {
        return nombre;
    }
}
