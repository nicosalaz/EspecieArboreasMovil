package pg.proyecto.especiesarboreas.lists;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import pg.proyecto.especiesarboreas.R;
import pg.proyecto.especiesarboreas.backend.interfaces.PublicacionService;
import pg.proyecto.especiesarboreas.backend.models.Entities.Publicacion;
import pg.proyecto.especiesarboreas.backend.models.Request.ReqReaccionar;
import pg.proyecto.especiesarboreas.backend.models.Response.PublicacionResponse;
import pg.proyecto.especiesarboreas.backend.models.Response.ReaccionResponse;
import pg.proyecto.especiesarboreas.shared.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PublicacionesReqAdapter extends RecyclerView.Adapter<PublicacionesReqAdapter.ViewHolder> {
    List<Publicacion> adapter;
    Context context;

    public PublicacionesReqAdapter(List<Publicacion> adapter, Context context) {
        this.adapter = adapter;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_element_pub,null);
        return new PublicacionesReqAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.data(adapter.get(position));
    }

    @Override
    public int getItemCount() {
        return this.adapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nomUsuario,likes,comments;
        ImageView imgPub,imgLike,imgCom;
        Retrofit retrofit;
        PublicacionService publicacionService;
        SharedPreferences sharedPreferences;
        boolean accion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomUsuario = (TextView) itemView.findViewById(R.id.nom_usuario);
            likes = (TextView) itemView.findViewById(R.id.likes);
            comments = (TextView) itemView.findViewById(R.id.comments);
            imgPub = (ImageView) itemView.findViewById(R.id.img_pub);
            imgLike = (ImageView) itemView.findViewById(R.id.img_like);
            imgCom = (ImageView) itemView.findViewById(R.id.img_com);
            sharedPreferences = context.getSharedPreferences(Utils.KEY_PREF, Context.MODE_PRIVATE);
            retrofit = Utils.serviceDelegate.getRetrofit();
            publicacionService = retrofit.create(PublicacionService.class);
            accion = false;
            imgLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReqReaccionar reqReaccionar = new ReqReaccionar();
                    reqReaccionar.setId_usuario(sharedPreferences.getString(Utils.ID_USUARIO,null));
                    reqReaccionar.setId_publicacion(String.valueOf(likes.getId()));
                    System.out.println(imgLike.getColorFilter());
                    if (imgLike.getColorFilter() == null){
                        accion = true;
                    }
                    try {
                        Call<Object> reaccion = publicacionService.reaccionar(reqReaccionar);
                        reaccion.enqueue(new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                if (response.isSuccessful()){
                                    assert response.body() != null;
                                    if (accion){
                                        imgLike.setColorFilter(R.color.red);
                                        likes.setText(String.valueOf(
                                                Integer.parseInt(likes.getText().toString())+1
                                        ));
                                    }else{
                                        imgLike.setColorFilter(R.color.gris);
                                        likes.setText(String.valueOf(
                                                Integer.parseInt(likes.getText().toString())-1
                                        ));
                                    }
                                    accion = false;
                                }
                            }

                            @Override
                            public void onFailure(Call<Object> call, Throwable t) {
                                System.out.println(t.getLocalizedMessage());
                                Utils.printToast(context,"Falló la consulta", Toast.LENGTH_SHORT);

                            }
                        });
                    }catch (Exception e){
                        System.out.println(e.getLocalizedMessage());
                        Utils.printToast(context,"Algo salió mal", Toast.LENGTH_SHORT);
                    }
                }
            });

        }

        public void data(Publicacion publicacion) {
            nomUsuario.setText(publicacion.getNombre());
            likes.setText(publicacion.getLike());
            likes.setId(Integer.parseInt(publicacion.getId()));
            comments.setText(publicacion.getNum_comen());
            Picasso.get()
                    .load(publicacion.getImagen())
                    .error(R.drawable.login_image)
                    .into(imgPub);
            try{
                Call<ReaccionResponse> allReac = publicacionService.getAllReacciones(sharedPreferences.getString(Utils.ID_USUARIO,null));
                allReac.enqueue(new Callback<ReaccionResponse>() {
                    @Override
                    public void onResponse(Call<ReaccionResponse> call, Response<ReaccionResponse> response) {
                        boolean step = false;
                        int conta = 0;
                        if (response.isSuccessful()){
                            assert response.body() != null;
                            if (response.body().getResponse() != null){
                                while (conta < response.body().getResponse().size() && !step){
                                    if (Objects.equals(response.body().getResponse().get(conta).getId_publicacion(), publicacion.getId())){
                                        step = true;
                                        imgLike.setColorFilter(R.color.red);
                                    }else{
                                        conta++;
                                    }
                                }
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ReaccionResponse> call, Throwable t) {
                        System.out.println(t.getLocalizedMessage());
                        Utils.printToast(context,"Falló la consulta", Toast.LENGTH_SHORT);
                    }
                });
            }catch (Exception e){
                System.out.println(e.getLocalizedMessage());
                Utils.printToast(context,"Algo salió mal", Toast.LENGTH_SHORT);
            }
        }
    }
}
