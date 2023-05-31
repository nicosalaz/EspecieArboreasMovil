package pg.proyecto.especiesarboreas.backend.models.Request;

import java.io.Serializable;

public class ReqAceptarEspecie implements Serializable {
    private int id;
    private int aceptado_por;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAceptado_por() {
        return aceptado_por;
    }

    public void setAceptado_por(int aceptado_por) {
        this.aceptado_por = aceptado_por;
    }
}
