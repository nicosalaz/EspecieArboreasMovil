package pg.proyecto.especiesarboreas.backend.models.Entities;

import java.io.Serializable;

public class Especie implements Serializable {
    private String modificado_por;
    private String justificacion_modificacion;
    private String activo;
    private String id;
    private String nombre;

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
