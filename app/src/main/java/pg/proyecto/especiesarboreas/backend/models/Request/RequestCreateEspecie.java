package pg.proyecto.especiesarboreas.backend.models.Request;

import java.io.File;
import java.io.Serializable;

public class RequestCreateEspecie implements Serializable {
    private String nombre;
    private String descripcion;
    private String id_especie;
    private String longitud;
    private String latitud;
    private String id_usuario;

    private File file;
    public RequestCreateEspecie(String nombre, String descripcion, String id_especie, String longitud, String latitud, String id_usuario,File file) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id_especie = id_especie;
        this.longitud = longitud;
        this.latitud = latitud;
        this.id_usuario = id_usuario;
        this.file = file;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId_especie() {
        return id_especie;
    }

    public void setId_especie(String id_especie) {
        this.id_especie = id_especie;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
