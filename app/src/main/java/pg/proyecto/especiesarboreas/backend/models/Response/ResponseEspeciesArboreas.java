package pg.proyecto.especiesarboreas.backend.models.Response;

import pg.proyecto.especiesarboreas.backend.models.Entities.EspecieArborea;

import java.util.List;

public class ResponseEspeciesArboreas {
    private String status;
    private List<EspecieArborea> response;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<EspecieArborea> getResponse() {
        return response;
    }

    public void setResponse(List<EspecieArborea> response) {
        this.response = response;
    }
}
