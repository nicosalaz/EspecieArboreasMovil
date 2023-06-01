package pg.proyecto.especiesarboreas.backend.models.Response;

import java.util.List;

import pg.proyecto.especiesarboreas.backend.models.Entities.Publicacion;

public class PublicacionResponse {
    private List<Publicacion> response;
    private int status;

    public List<Publicacion> getResponse() {
        return response;
    }

    public void setResponse(List<Publicacion> response) {
        this.response = response;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
