package pg.proyecto.especiesarboreas.fragments.cientifico;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import pg.proyecto.especiesarboreas.R;
import pg.proyecto.especiesarboreas.backend.ServiceDelegate;
import pg.proyecto.especiesarboreas.backend.interfaces.EspecieService;
import pg.proyecto.especiesarboreas.backend.models.Request.ReqAceptarEspecie;
import pg.proyecto.especiesarboreas.backend.models.Response.ResponseEspeciesReq;
import pg.proyecto.especiesarboreas.shared.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VerificarRegistroFragment extends Fragment {

    private View view;
    private ResponseEspeciesReq item;
    private ImageView imgEspecie;
    private TextView nomEspecies;
    private TextView especie;
    private EditText descFinal;
    private Button aceptar;
    private Button denegar;
    private ServiceDelegate serviceDelegate;
    private EspecieService service;
    private SharedPreferences preferences;
    private AlertDialog.Builder alertBuilder;
    public VerificarRegistroFragment(ResponseEspeciesReq item) {
        this.item = item;
    }
    public static VerificarRegistroFragment newInstance(ResponseEspeciesReq item){
        VerificarRegistroFragment fragment = new VerificarRegistroFragment(item);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_verificar_registro, container, false);
        imgEspecie = (ImageView) view.findViewById(R.id.img_especie);
        nomEspecies = (TextView) view.findViewById(R.id.nom_especie);
        especie = (TextView) view.findViewById(R.id.especie);
        descFinal = (EditText) view.findViewById(R.id.desc_final);
        aceptar = (Button) view.findViewById(R.id.aceptar);
        denegar = (Button) view.findViewById(R.id.denegar);
        nomEspecies.setText(item.getName());
        descFinal.setText(item.getDescripcion());
        especie.setText(item.getNombre());
        preferences = view.getContext().getSharedPreferences(Utils.KEY_PREF, Context.MODE_PRIVATE);
        Picasso.get()
                .load(item.getImagen())
                .error(R.drawable.login_image)
                .into(imgEspecie);
        serviceDelegate = new ServiceDelegate();
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aceptarReq();
            }
        });
        return view;
    }

    public void aceptarReq(){
        Utils.printProgressDialogSpinner(view.getContext(),"Aceptar Petición", "Aceptando...");
        Retrofit retrofit = serviceDelegate.getRetrofit();
        service = retrofit.create(EspecieService.class);
        ReqAceptarEspecie reqAceptarEspecie = new ReqAceptarEspecie();
        reqAceptarEspecie.setId(Integer.parseInt(item.getId_request()));
        reqAceptarEspecie.setAceptado_por(Integer.parseInt(preferences.getString(Utils.ID_USUARIO,null)));
        try {
            Call<Object> aceptarEspcie = service.aceptarRequest(reqAceptarEspecie);
            aceptarEspcie.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if (response.isSuccessful()){
                        assert response.body() != null;
                        Utils.dimissProgressDialogSpinner();
                        alertBuilder = new AlertDialog.Builder(view.getContext());
                        alertBuilder.setTitle("Respuesta exitosa");
                        alertBuilder.setMessage("El registro se acepto correctamente");
                        alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    dialogInterface.dismiss();
                                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                                    Fragment myFragment = SolicitudesReporteFragment.newInstance();
                                    activity.getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.fragmentContainerCientifico,myFragment)
                                            .addToBackStack(null)
                                            .commit();
                                }catch (Exception e){
                                    System.out.println(e.getLocalizedMessage());
                                }
                            }
                        });
                        alertBuilder.show();
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    Utils.dimissProgressDialogSpinner();
                    Utils.printToast(view.getContext(),"Fallo el servicio",Toast.LENGTH_SHORT);
                }
            });
        }catch (Exception e){
            Utils.printToast(view.getContext(),"Falló consumo de servicio", Toast.LENGTH_SHORT);
            System.out.println(e.getLocalizedMessage());
        }
    }
}