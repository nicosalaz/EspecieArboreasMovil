package pg.proyecto.especiesarboreas.fragments.colaboradores;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pg.proyecto.especiesarboreas.MainActivity;
import pg.proyecto.especiesarboreas.R;
import pg.proyecto.especiesarboreas.backend.ServiceDelegate;
import pg.proyecto.especiesarboreas.backend.interfaces.EspecieService;
import pg.proyecto.especiesarboreas.backend.models.Entities.Especie;
import pg.proyecto.especiesarboreas.backend.models.Request.RequestCreateEspecie;
import pg.proyecto.especiesarboreas.backend.models.Response.ResponseEspecies;
import pg.proyecto.especiesarboreas.shared.Utils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NuevaEspecieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NuevaEspecieFragment extends Fragment{

    private EditText nombreNuevoCaso;
    private Button btnRegNuevoCaso;
    private Button btnUbicacion;
    private EditText descNueReg;
    private ImageButton btnImgRegNuevo;
    private View view;
    private ImageView imgNuevoCaso;
    private Spinner spinner;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private double latitud;
    private double longitud;
    private int idTypeEsp;
    private Bitmap bitmap = null;
    private Bitmap scaled = null;
    private File photo = null;
    private ServiceDelegate serviceDelegate;
    private SharedPreferences sharedPreferences;
    private ArrayAdapter<Especie> adapter;
    private Gson gson = new Gson();

//    private FusedLicationProviderClient fusedLicationProviderClient;
//    FragmentNuevaEspecieBinding binding;

    public static NuevaEspecieFragment newInstance() {
        NuevaEspecieFragment fragment = new NuevaEspecieFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*binding = FragmentNuevaEspecieBinding.inflate(getLayoutInflater());
        FrameLayout frameLayout = binding.getRoot();
        nombreNuevoCaso = (EditText) frameLayout.findViewById(R.id.nom_nueva_especie);
        btnRegNuevoCaso = (Button) frameLayout.findViewById(R.id.btn_reg_nuevo);
        btnImgRegNuevo = (ImageButton) frameLayout.findViewById(R.id.btn_img_reg_nuevo);
        btnImgRegNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCamara(view);
            }
        });*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_nueva_especie, container, false);
        latitud = 0.0;
        longitud = 0.0;
        idTypeEsp = 0;
        sharedPreferences = view.getContext().getSharedPreferences(Utils.KEY_PREF, Context.MODE_PRIVATE);
        nombreNuevoCaso = (EditText) view.findViewById(R.id.nom_nueva_especie);
        btnRegNuevoCaso = (Button) view.findViewById(R.id.btn_reg_nuevo);
        btnUbicacion = (Button) view.findViewById(R.id.btnUbicacion);
        descNueReg = (EditText) view.findViewById(R.id.descNueReg);
        spinner = (Spinner) view.findViewById(R.id.spinner_esp);
        imgNuevoCaso = (ImageView) view.findViewById(R.id.img_nuevo_caso);
        serviceDelegate = new ServiceDelegate();
        llenarSpinner();
        btnImgRegNuevo = (ImageButton) view.findViewById(R.id.btn_img_reg_nuevo);
        btnImgRegNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCamara();
            }
        });
        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerUbi();
            }
        });
        btnRegNuevoCaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarData();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idTypeEsp = Integer.parseInt(adapter.getItem(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    public void abrirCamara() {
        try {
            Toast.makeText(view.getContext(), "Abriendo camara...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                photo = Utils.createImageFile();
                startActivityForResult(intent, 1);
            }else{
                Toast.makeText(view.getContext(), "Debe habilitar lo permisos de la camara", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            System.out.println(e.getCause().toString());
            Toast.makeText(view.getContext(), "Error al abrir la camara", Toast.LENGTH_SHORT).show();
        }
    }
    public void obtenerUbi(){
        try {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view.getContext());
            if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener((Activity) view.getContext(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        latitud = location.getLatitude();
                        longitud = location.getLongitude();
                        Toast.makeText(view.getContext(), String.format("Posición actual, Latitud: %s , Longitud: %s",latitud,longitud), Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                ActivityCompat.requestPermissions((Activity) view.getContext(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1000);
            }
        }catch (Exception e){
            Log.println(Log.INFO,"info",e.toString());
            Toast.makeText(view.getContext(), "Error al obtener la ubicación", Toast.LENGTH_LONG).show();
        }

    }
    public void enviarData(){

        if (nombreNuevoCaso.getText().length() == 0 || descNueReg.getText().length() == 0 || (latitud == 0.0 && longitud == 0) || idTypeEsp == 0){
            Utils.printToast(view.getContext(),"¡Los campos son obligatorios!",Toast.LENGTH_LONG);
        }else {
            Utils.printProgressDialogSpinner(view.getContext(),"Registrar nueva espeie","Registrando...");
            Retrofit retrofit = serviceDelegate.getRetrofit();
            EspecieService especieServices = retrofit.create(EspecieService.class);
            RequestCreateEspecie createEspecie = new RequestCreateEspecie(nombreNuevoCaso.getText().toString(), descNueReg.getText().toString(),
                    String.valueOf(idTypeEsp), String.valueOf(longitud),
                    String.valueOf(latitud), String.valueOf(sharedPreferences.getString(Utils.ID_USUARIO,null)),photo);
            RequestBody nombre = RequestBody.create(MediaType.parse("multipart/form-data"),nombreNuevoCaso.getText().toString());
            RequestBody descripcion = RequestBody.create(MediaType.parse("multipart/form-data"),descNueReg.getText().toString());
            RequestBody id_especie = RequestBody.create(MediaType.parse("multipart/form-data"),String.valueOf(idTypeEsp));
            RequestBody longitud = RequestBody.create(MediaType.parse("multipart/form-data"),String.valueOf(this.longitud));
            RequestBody latitud = RequestBody.create(MediaType.parse("multipart/form-data"),String.valueOf(this.latitud));
            RequestBody id_usuario = RequestBody.create(MediaType.parse("multipart/form-data"),String.valueOf(sharedPreferences.getString(Utils.ID_USUARIO,null)));
            RequestBody requestImage = RequestBody.create(MediaType.parse("multipart/form-data"),photo);
            MultipartBody.Part image = null;
            image = MultipartBody.Part.createFormData("file",photo.getName(),requestImage);
            Map<String,RequestBody> map = new HashMap<>();
            map.put("nombre",nombre);
            map.put("descripcion",descripcion);
            map.put("id_especie",id_especie);
            map.put("longitud",longitud);
            map.put("latitud",latitud);
            map.put("id_usuario",id_usuario);
            try {
                Call<Object> create = especieServices.createEspecie(map,image);
                create.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (response.isSuccessful()){
                            assert response.body() != null;
                            Utils.printToast(getContext(),"Registro Exitoso!",Toast.LENGTH_LONG);
                            Utils.dimissProgressDialogSpinner();
                            Utils.printAlertDialog(view.getContext(),"¡IMPORTANTE!",
                                    "El registro será revisado por un experto y validará la veracidad del mismo");
                        }
                        limpiarCampos();

                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Utils.dimissProgressDialogSpinner();
                        System.out.println(t.getLocalizedMessage());
                        Utils.printToast(view.getContext(),"Falló la consulta",Toast.LENGTH_SHORT);
                    }
                });
            }catch (Exception e){
                System.out.println(e.getLocalizedMessage());
                Utils.dimissProgressDialogSpinner();
                Utils.printToast(view.getContext(),"Algo salió mal",Toast.LENGTH_SHORT);
            }
        }
    }

    private void llenarSpinner(){
        try {
            Retrofit retrofit = serviceDelegate.getRetrofit();
            EspecieService especieServices = retrofit.create(EspecieService.class);
            Call<ResponseEspecies> especiesCall = especieServices.allTypeEspecies();
            especiesCall.enqueue(new Callback<ResponseEspecies>() {
                @Override
                public void onResponse(Call<ResponseEspecies> call, Response<ResponseEspecies> response) {
                    if (response.isSuccessful()){
                        assert response.body() != null;
                        ResponseEspecies especies = response.body();
                        adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item,especies.getResponse());
                        spinner.setAdapter(adapter);
                    }
                }
                @Override
                public void onFailure(Call<ResponseEspecies> call, Throwable t) {
                    System.out.println(t.getLocalizedMessage());
                    Utils.printToast(view.getContext(),"Falló la consulta",Toast.LENGTH_SHORT);
                }
            });
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
            Utils.printToast(view.getContext(),"Algo salió mal",Toast.LENGTH_SHORT);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            scaled = Bitmap.createScaledBitmap(bitmap,1000,1000,true);
            try {
                Utils.construirArchivo(photo,bitmap);
                System.out.println(photo.getName());
                System.out.println(photo);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            imgNuevoCaso.setImageBitmap(scaled);
        }
    }

    private void limpiarCampos(){
        imgNuevoCaso.setImageBitmap(null);
        nombreNuevoCaso.setText("");
        descNueReg.setText("");
        latitud = 0.0;
        longitud = 0.0;
    }


}