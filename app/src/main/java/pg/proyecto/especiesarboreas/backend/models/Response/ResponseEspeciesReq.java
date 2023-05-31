package pg.proyecto.especiesarboreas.backend.models.Response;

import java.io.Serializable;

public class ResponseEspeciesReq implements Serializable {
    private String id_request;
    private String descripcion;
    private String position;
    private String id;
    private String title;
    private String name;
    private String nombre;
    private String id_usuario;
    private String nombre_esp;
    private String imagen;

    public String getId_request() {
        return id_request;
    }

    public void setId_request(String id_request) {
        this.id_request = id_request;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre_esp() {
        return nombre_esp;
    }

    public void setNombre_esp(String nombre_esp) {
        this.nombre_esp = nombre_esp;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "ResponseEspeciesReq{" +
                "id_request='" + id_request + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", position='" + position + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", nombre='" + nombre + '\'' +
                ", id_usuario='" + id_usuario + '\'' +
                ", nombre_esp='" + nombre_esp + '\'' +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}
