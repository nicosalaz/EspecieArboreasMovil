package pg.proyecto.especiesarboreas.backend.interfaces;

import pg.proyecto.especiesarboreas.backend.models.Entities.TipoIdentificacion;
import pg.proyecto.especiesarboreas.shared.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface TipoIdentificacionService {
    @Headers({"Content-Type: application/json; charset=utf-8"})
    @GET(Utils.END_TIPOS_ID)
    Call<List<TipoIdentificacion>> allTipos();
}
