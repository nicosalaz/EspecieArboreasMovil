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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pg.proyecto.especiesarboreas.Activities.SolicitudReporteActivity;
import pg.proyecto.especiesarboreas.R;
import pg.proyecto.especiesarboreas.backend.models.Response.ResponseEspeciesReq;
import pg.proyecto.especiesarboreas.backend.models.Response.ResponseEspeciesReqList;
import pg.proyecto.especiesarboreas.fragments.cientifico.VerificarRegistroFragment;
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
        holder.binData(adapterList.get(position),position);
        int pos = position;
        holder.btnRev. setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    clickListener.onItemClick();
                try {
//                    System.out.println(adapterList.get(pos));
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = VerificarRegistroFragment.newInstance(adapterList.get(pos));
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerCientifico,myFragment)
                            .addToBackStack(null).commit();
                }catch (Exception e){
                    e.getLocalizedMessage();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return this.adapterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView txtName,txtDesc;
        Button btnRev;
//        List<ResponseEspeciesReq> adapterList;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_arbol);
            txtName = itemView.findViewById(R.id.txt_name);
            txtDesc = itemView.findViewById(R.id.txt_desc);
            btnRev = itemView.findViewById(R.id.btn_revisar);
        }

        void binData(ResponseEspeciesReq item, int pos){
            txtName.setText(item.getName());
            txtDesc.setText(item.getDescripcion());
            btnRev.setId(pos);
            Picasso.get()
                    .load(item.getImagen())
                    .error(R.drawable.login_image)
                    .into(image);
        }

    }
}
