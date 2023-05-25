package pg.proyecto.especiesarboreas.backend.models.Response;

import java.io.Serializable;
import java.util.List;

public class ResponseEspeciesReqList implements Serializable {
    private int status;
    private List<ResponseEspeciesReq> response;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ResponseEspeciesReq> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseEspeciesReq> response) {
        this.response = response;
    }
}
