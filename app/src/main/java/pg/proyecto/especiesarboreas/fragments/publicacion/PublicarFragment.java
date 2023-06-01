package pg.proyecto.especiesarboreas.fragments.publicacion;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pg.proyecto.especiesarboreas.R;
import pg.proyecto.especiesarboreas.backend.interfaces.PublicacionService;
import pg.proyecto.especiesarboreas.fragments.FeedFragment;
import pg.proyecto.especiesarboreas.shared.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PublicarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PublicarFragment extends Fragment {
    private View view;
    private EditText pie_foto;
    private ImageView img_publicar;
    private Button btn_foto,add_pub;
    private Bitmap bitmap = null;
    private File photo = null;
    private SharedPreferences sharedPreferences;
    public PublicarFragment() {

    }

    public static PublicarFragment newInstance() {
        PublicarFragment fragment = new PublicarFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_publicar, container, false);
        pie_foto = (EditText) view.findViewById(R.id.pie_foto);
        img_publicar = (ImageView) view.findViewById(R.id.img_publicar);
        btn_foto = (Button) view.findViewById(R.id.btn_foto);
        add_pub = (Button) view.findViewById(R.id.add_pub);
        sharedPreferences = view.getContext().getSharedPreferences(Utils.KEY_PREF, Context.MODE_PRIVATE);
        btn_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCamara();
            }
        });
        add_pub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarData();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            try {
                Utils.construirArchivo(photo,bitmap);
                System.out.println(photo.getName());
                System.out.println(photo);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            img_publicar.setImageBitmap(bitmap);
        }
    }

    public void enviarData(){
        try {
            if (pie_foto.getText().length() == 0 || photo == null){
                Utils.printToast(view.getContext(),"¡Todos los campos son obligatorios!",Toast.LENGTH_LONG);
            }else {
                Utils.printProgressDialogSpinner(view.getContext(),"Registrar nueva publicación","Registrando...");
                Retrofit retrofit = Utils.serviceDelegate.getRetrofit();
                PublicacionService publicacionService = retrofit.create(PublicacionService.class);
                RequestBody descripcion = RequestBody.create(MediaType.parse("multipart/form-data"),pie_foto.getText().toString());
                RequestBody id_usuario = RequestBody.create(MediaType.parse("multipart/form-data"),String.valueOf(sharedPreferences.getString(Utils.ID_USUARIO,null)));
                RequestBody requestImage = RequestBody.create(MediaType.parse("multipart/form-data"),photo);
                MultipartBody.Part image = null;
                image = MultipartBody.Part.createFormData("file",photo.getName(),requestImage);
                Map<String,RequestBody> map = new HashMap<>();
                map.put("descripcion",descripcion);
                map.put("id_usuario",id_usuario);
                Call<Object> createPub = publicacionService.createPublicacion(map,image);
                createPub.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (response.isSuccessful()){
                            assert response.body() != null;
                            Utils.dimissProgressDialogSpinner();
                            Utils.printToast(getContext(),"Registro Exitoso!",Toast.LENGTH_LONG);
                            Utils.printAlertDialog(view.getContext(),"¡EXITO!",
                                    "El registro de la publicación fue Exitoso");
                        }
                        limpiarCampos();
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        System.out.println(t.getLocalizedMessage());
                        Utils.printToast(getContext(),"Falló el consumo",Toast.LENGTH_LONG);

                    }
                });

            }
        }catch (Exception e){
            Utils.printToast(getContext(),"Algo salio mal",Toast.LENGTH_LONG);
            System.out.println(e.getLocalizedMessage());
        }
    }

    private void limpiarCampos() {
        pie_foto.setText(null);
        img_publicar.setImageBitmap(null);
        photo = null;
    }
}