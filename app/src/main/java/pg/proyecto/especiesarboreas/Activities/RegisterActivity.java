package pg.proyecto.especiesarboreas.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import pg.proyecto.especiesarboreas.R;
import pg.proyecto.especiesarboreas.backend.interfaces.TipoIdentificacionService;
import pg.proyecto.especiesarboreas.backend.interfaces.UserService;
import pg.proyecto.especiesarboreas.backend.models.Entities.TipoIdentificacion;
import pg.proyecto.especiesarboreas.backend.models.Request.RequestRegisterUser;
import pg.proyecto.especiesarboreas.backend.models.Response.ErrorResponseCreateUser;
import pg.proyecto.especiesarboreas.shared.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    private EditText nombre;
    private EditText apellido;
    private EditText num_id;
    private EditText email;
    private EditText usuario;
    private EditText clave;
    private Spinner ciudad;
    private Spinner tipoId;
    private EditText tel;
    private String tipoIdSelected;
    private Button regUser;
    private Button volver;
    private Intent intento;
    private ArrayAdapter ciudades;
    private ArrayAdapter<TipoIdentificacion> tiposIds;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this,R.layout.activity_register,null);
        setContentView(view);
        ciudades = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Utils.ciudades);
        nombre = (EditText) findViewById(R.id.reg_nombre);
        apellido = (EditText) findViewById(R.id.reg_apd);
        num_id = (EditText) findViewById(R.id.reg_id_num);
        email =  (EditText) findViewById(R.id.reg_correo);
        usuario = (EditText) findViewById(R.id.reg_user);
        clave = (EditText) findViewById(R.id.reg_pass);
        ciudad = (Spinner) findViewById(R.id.reg_ciu);
        ciudad.setAdapter(ciudades);
        tipoId = (Spinner) findViewById(R.id.tipo_id);
        tel = (EditText) findViewById(R.id.reg_tel);
        regUser = (Button) findViewById(R.id.btn_reg_user);
        volver = (Button) findViewById(R.id.btn_v);
        llenarSpinnerTipoId();
        regUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuario(view);
            }
        });
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                volver();
            }
        });
    }

    public void volver(){
        resetForm();
        intento = new Intent(this,LoginAppActivity.class);
        startActivity(intento);
    }
    public void resetForm(){
        nombre.setText("");
        apellido.setText("");
        num_id.setText("");
        email.setText("");
        usuario.setText("");
        clave.setText("");
//        ciudad.setText("");
        tel.setText("");
    }
    public void registrarUsuario(View view){
        if (nombre.getText().toString().isEmpty() || apellido.getText().toString().isEmpty()
                || num_id.getText().toString().isEmpty() || email.getText().toString().isEmpty()
                || usuario.getText().toString().isEmpty() || clave.getText().toString().isEmpty()
                || ciudad.getSelectedItem().toString().isEmpty() || tel.getText().toString().isEmpty()
                || tipoId.getSelectedItem().toString().isEmpty()){
            Utils.printToast(view.getContext(),"Todos los campos son Obligatorios", Toast.LENGTH_LONG);
        }else{
            Utils.printProgressDialogSpinner(view.getContext(),"Resgistrar usuario","Registrando...");
            RequestRegisterUser registerUser = getRequestRegister();
            Retrofit retrofit = Utils.serviceDelegate.getRetrofit();
            UserService userService = retrofit.create(UserService.class);
            try {
                Call<Object> register = userService.registerUsuarios(registerUser);
                register.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (response.isSuccessful()){
                            assert response.body() != null;
                            String strResponse = Utils.gson.toJson(response.body());
                            ErrorResponseCreateUser responseCreateUser = null;
                            try {
                                responseCreateUser = Utils.gson.fromJson(strResponse,ErrorResponseCreateUser.class);
                            }catch (Exception e){
                                System.out.println(e.getLocalizedMessage());
                            }
                            Utils.dimissProgressDialogSpinner();
                            if (responseCreateUser != null){
                                Utils.printToast(view.getContext(),responseCreateUser.getError(),Toast.LENGTH_LONG);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Utils.printToast(view.getContext(),"Error en la petición, Intentanlo nuevamente",Toast.LENGTH_LONG);
                    }
                });
            }catch (Exception e){
                Utils.dimissProgressDialogSpinner();
                Utils.printToast(view.getContext(),"Algo salio mal, Intentanlo nuevamente",Toast.LENGTH_LONG);

            }
        }
    }

    private RequestRegisterUser getRequestRegister(){
        RequestRegisterUser request = new RequestRegisterUser();
        request.setNombre(nombre.getText().toString().trim());
        request.setApellido(apellido.getText().toString().trim());
        request.setId_identificacion(tiposIds.getItem(tipoId.getSelectedItemPosition()).getId());
        request.setNumero_identificacion(num_id.getText().toString().trim());
        request.setCorreo(email.getText().toString().trim());
        request.setUsuario(usuario.getText().toString().trim());
        request.setClave(clave.getText().toString().trim());
        request.setTelefono(tel.getText().toString().trim());
        return request;
    }
    private void llenarSpinnerTipoId(){
        Retrofit retrofit = Utils.serviceDelegate.getRetrofit();
        TipoIdentificacionService identificacionService = retrofit.create(TipoIdentificacionService.class);
        Call<List<TipoIdentificacion>> responseTipoIdentificacionCall = identificacionService.allTipos();
        try{
            responseTipoIdentificacionCall.enqueue(new Callback<List<TipoIdentificacion>>() {
                @Override
                public void onResponse(Call<List<TipoIdentificacion>> call, Response<List<TipoIdentificacion>> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        tiposIds = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item,response.body());
                        tipoId.setAdapter(tiposIds);
                    }
                }

                @Override
                public void onFailure(Call<List<TipoIdentificacion>> call, Throwable t) {
                    System.out.println(t.getLocalizedMessage());
                    Utils.printToast(view.getContext(),"Falló al traer los tipos de identificación",Toast.LENGTH_LONG);
                }
            });
        }catch (Exception e){
            Utils.printToast(view.getContext(),"Algo salió mal",Toast.LENGTH_LONG);
        }

    }
    @Override
    public void finish() {
        super.finish();
    }
}