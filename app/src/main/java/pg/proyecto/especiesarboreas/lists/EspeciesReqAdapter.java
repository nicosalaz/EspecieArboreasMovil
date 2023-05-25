package pg.proyecto.especiesarboreas.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pg.proyecto.especiesarboreas.R;
import pg.proyecto.especiesarboreas.backend.models.Response.ResponseEspeciesReq;
import pg.proyecto.especiesarboreas.shared.Utils;

public class EspeciesReqAdapter extends RecyclerView.Adapter<EspeciesReqAdapter.ViewHolder> {

    List<ResponseEspeciesReq> adapterList;
    Context context;

    public EspeciesReqAdapter(List<ResponseEspeciesReq> adapterList, Context context) {
        this.adapterList = adapterList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_element,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binData(adapterList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.adapterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView txtName,txtDesc;
        Button btnRev;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_arbol);
            txtName = itemView.findViewById(R.id.txt_name);
            txtDesc = itemView.findViewById(R.id.txt_desc);
            btnRev = itemView.findViewById(R.id.btn_revisar);
            btnRev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.printToast(itemView.getContext(),String.format("Id: %s",btnRev.getId()),
                            Toast.LENGTH_SHORT);
                }
            });
        }

        void binData(ResponseEspeciesReq item){
            txtName.setText(item.getName());
            txtDesc.setText(item.getDescripcion());
            btnRev.setId(Integer.parseInt(item.getId_request()));
            Picasso.get()
                    .load(item.getImagen())
                    .error(R.drawable.login_image)
                    .into(image);
        }

    }
}
