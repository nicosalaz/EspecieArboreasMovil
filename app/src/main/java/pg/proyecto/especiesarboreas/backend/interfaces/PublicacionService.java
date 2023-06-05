package pg.proyecto.especiesarboreas.backend.interfaces;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pg.proyecto.especiesarboreas.backend.models.Entities.Publicacion;
import pg.proyecto.especiesarboreas.backend.models.Request.ReqReaccionar;
import pg.proyecto.especiesarboreas.backend.models.Response.PublicacionResponse;
import pg.proyecto.especiesarboreas.backend.models.Response.ReaccionResponse;
import pg.proyecto.especiesarboreas.shared.Utils;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Field;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PublicacionService {

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @GET(Utils.END_ALL_PUB)
    Call<PublicacionResponse> getAllPubs();

    @Multipart
    @POST(Utils.END_CREATE_PUB)
    Call<Object>createPublicacion(@PartMap Map<String, RequestBody> bodyMap, @Part MultipartBody.Part image);

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @GET(Utils.END_ALL_REAC)
    Call<ReaccionResponse> getAllReacciones(@Path("id") String id);
    @Headers({"Content-Type: application/json; charset=utf-8"})
    @POST(Utils.END_REACCIONAR)
    Call<Object> reaccionar(@Body ReqReaccionar reqReaccionar);



}
