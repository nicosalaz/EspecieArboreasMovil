package pg.proyecto.especiesarboreas.backend.models.Response;

import pg.proyecto.especiesarboreas.backend.models.Entities.TipoIdentificacion;

import java.io.Serializable;

public class ResponseTipoIdentificacion implements Serializable {
    private TipoIdentificacion[] tipos;

    public TipoIdentificacion[] getTipos() {
        return tipos;
    }

    public void setTipos(TipoIdentificacion[] tipos) {
        this.tipos = tipos;
    }
}
