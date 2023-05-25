package pg.proyecto.especiesarboreas.backend;

import android.os.AsyncTask;

import pg.proyecto.especiesarboreas.shared.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceDelegate extends AsyncTask<String,Void,Object> {
    private String url = Utils.URL;

/*    public List<Object> getUsuarios(){
        usuarios = new ArrayList<>();
        try {
            retrofit = getRetrofit();
            UserService userService = retrofit.create(UserService.class);
            Call<List<Object>> call = userService.getUsuarios();
            call.enqueue(new Callback<List<Object>>() {
                @Override
                public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {
                    assert response.body() != null;
                    for (Object obj:response.body()) {
                        usuarios.add(obj);
                    }
                }

                @Override
                public void onFailure(Call<List<Object>> call, Throwable t) {
                    System.out.println(t.getCause().toString());
                }
            });
        } catch (Exception exception) {
            System.out.println(exception.getCause().toString());
        }
        return usuarios;
    }*/

    @Override
    protected Object doInBackground(String... strings) {
        return null;
    }

    public Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(Utils.URL_LOCAL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
