package pg.proyecto.especiesarboreas.backend.interfaces;

import pg.proyecto.especiesarboreas.backend.models.Request.RequestLogin;
import pg.proyecto.especiesarboreas.backend.models.Request.RequestRegisterUser;
import pg.proyecto.especiesarboreas.shared.Utils;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {
    @Headers({"Content-Type: application/json; charset=utf-8"})
    @POST(Utils.END_AUTH)
    Call<Object> getUsuarios(@Body RequestLogin requestLogin);

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @POST(Utils.END_REGISTER_USER)
    Call<Object> registerUsuarios(@Body RequestRegisterUser registerUser);
}
