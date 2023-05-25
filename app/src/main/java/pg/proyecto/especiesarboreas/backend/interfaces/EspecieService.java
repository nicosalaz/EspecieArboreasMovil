package pg.proyecto.especiesarboreas.backend.interfaces;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pg.proyecto.especiesarboreas.backend.models.Request.RequestCreateEspecie;
import pg.proyecto.especiesarboreas.backend.models.Response.ResponseEspecies;
import pg.proyecto.especiesarboreas.backend.models.Response.ResponseEspeciesReq;
import pg.proyecto.especiesarboreas.backend.models.Response.ResponseEspeciesReqList;
import pg.proyecto.especiesarboreas.shared.Utils;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface EspecieService {
    String endpoint = Utils.END_ALL_ESPECIES;

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @GET(endpoint)
    Call<Object>allEspecies();

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @GET(Utils.END_ALL_TYPE_ESPECIES)
    Call<ResponseEspecies>allTypeEspecies();

    @Multipart
    @POST(Utils.END_CREATE_ESPECIES)
    Call<Object>createEspecie(@PartMap Map<String,RequestBody> bodyMap, @Part MultipartBody.Part image);

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @GET(Utils.END_ALL_ESPECIES_REQUEST)
    Call<ResponseEspeciesReqList>allEspeciesReq();

}
