package pg.proyecto.especiesarboreas.backend.models.Response;

import pg.proyecto.especiesarboreas.backend.models.Entities.Especie;

import java.io.Serializable;
import java.util.List;

public class ResponseEspecies implements Serializable {
    private String status;
    private List<Especie> response;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Especie> getResponse() {
        return response;
    }

    public void setResponse(List<Especie> response) {
        this.response = response;
    }
}
