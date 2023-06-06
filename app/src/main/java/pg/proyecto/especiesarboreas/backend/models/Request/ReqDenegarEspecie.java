package pg.proyecto.especiesarboreas.backend.models.Request;

import java.io.Serializable;

public class ReqDenegarEspecie implements Serializable {
    private int id;
    private int rechazado_por;
    private String justificacion_rechazo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRechazado_por() {
        return rechazado_por;
    }

    public void setRechazado_por(int rechazado_por) {
        this.rechazado_por = rechazado_por;
    }

    public String getJustificacion_rechazo() {
        return justificacion_rechazo;
    }

    public void setJustificacion_rechazo(String justificacion_rechazo) {
        this.justificacion_rechazo = justificacion_rechazo;
    }
}
