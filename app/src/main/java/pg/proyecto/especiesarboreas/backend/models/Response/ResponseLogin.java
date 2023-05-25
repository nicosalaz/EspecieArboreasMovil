package pg.proyecto.especiesarboreas.backend.models.Response;

import pg.proyecto.especiesarboreas.backend.models.Entities.Usuario;

public class ResponseLogin {
    private String status;
    private String access_token;
    private Usuario user;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}
