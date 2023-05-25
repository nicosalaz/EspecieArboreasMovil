package pg.proyecto.especiesarboreas.backend.models.Response;

import java.io.Serializable;

public class ErrorResponseCreateUser implements Serializable {
    private String Error;
    private String status;

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
