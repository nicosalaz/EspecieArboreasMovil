package pg.proyecto.especiesarboreas.backend.models.Response;

import java.util.List;

import pg.proyecto.especiesarboreas.backend.models.Entities.Reaccion;

public class ReaccionResponse {
    private List<Reaccion> response;
    private int status;

    public List<Reaccion> getResponse() {
        return response;
    }

    public void setResponse(List<Reaccion> response) {
        this.response = response;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
