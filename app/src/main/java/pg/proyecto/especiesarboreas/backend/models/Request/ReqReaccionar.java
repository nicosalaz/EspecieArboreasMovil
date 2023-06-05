package pg.proyecto.especiesarboreas.backend.models.Request;

import java.io.Serializable;

public class ReqReaccionar implements Serializable {
    private String id_usuario;
    private String id_publicacion;

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
