package pg.proyecto.especiesarboreas.backend.models.Entities;

import java.io.Serializable;

public class Publicacion implements Serializable {
    private String id_user;
    private String id;
    private String descripcion;
    private String nombre;
    private String like;
    private String num_comen;
    private String imagen;

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getNum_comen() {
        return num_comen;
    }

    public void setNum_comen(String num_comen) {
        this.num_comen = num_comen;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
