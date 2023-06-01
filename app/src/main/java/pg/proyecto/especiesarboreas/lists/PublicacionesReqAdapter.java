package pg.proyecto.especiesarboreas.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pg.proyecto.especiesarboreas.R;
import pg.proyecto.especiesarboreas.backend.models.Entities.Publicacion;

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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomUsuario = (TextView) itemView.findViewById(R.id.nom_usuario);
            likes = (TextView) itemView.findViewById(R.id.likes);
            comments = (TextView) itemView.findViewById(R.id.comments);
            imgPub = (ImageView) itemView.findViewById(R.id.img_pub);
            imgLike = (ImageView) itemView.findViewById(R.id.img_like);
            imgCom = (ImageView) itemView.findViewById(R.id.img_com);
        }

        public void data(Publicacion publicacion) {
            nomUsuario.setText(publicacion.getNombre());
            likes.setText(publicacion.getLike().concat(" Likes"));
            comments.setText(publicacion.getNum_comen().concat(" Comentarios"));
            Picasso.get()
                    .load(publicacion.getImg())
                    .error(R.drawable.login_image)
                    .into(imgPub);
        }
    }
}
