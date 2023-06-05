package pg.proyecto.especiesarboreas.backend.models.Entities;

import java.io.Serializable;

public class Reaccion implements Serializable {
    private String modificado_por;
    private String justificacion_modificacion;
    private String activo;
    private String id;
    private String tipo_reaccion;
    private String id_usuario;
    private String id_publicacion;

    public String getModificado_por() {
        return modificado_por;
    }

    public void setModificado_por(String modificado_por) {
        this.modificado_por = modificado_por;
    }

    public String getJustificacion_modificacion() {
        return justificacion_modificacion;
    }

    public void setJustificacion_modificacion(String justificacion_modificacion) {
        this.justificacion_modificacion = justificacion_modificacion;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo_reaccion() {
        return tipo_reaccion;
    }

    public void setTipo_reaccion(String tipo_reaccion) {
        this.tipo_reaccion = tipo_reaccion;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getId_publicacion() {
        return id_publicacion;
    }

    public void setId_publicacion(String id_publicacion) {
        this.id_publicacion = id_publicacion;
    }
}
