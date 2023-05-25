package pg.proyecto.especiesarboreas.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import pg.proyecto.especiesarboreas.MainActivity;
import pg.proyecto.especiesarboreas.R;
import pg.proyecto.especiesarboreas.backend.ServiceDelegate;
import pg.proyecto.especiesarboreas.backend.interfaces.UserService;
import pg.proyecto.especiesarboreas.backend.models.Request.RequestLogin;
import pg.proyecto.especiesarboreas.backend.models.Response.ResponseLogin;
import pg.proyecto.especiesarboreas.databinding.ActivityLoginAppBinding;
import pg.proyecto.especiesarboreas.shared.Utils;
import com.google.gson.Gson;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginAppActivity extends AppCompatActivity {
    ActivityLoginAppBinding binding;
    private Button singIn;
    private TextView user;
    private TextView password;
    private TextView c_cuenta;
    private LinearLayout layoutLogin;
    private Gson gson = new Gson();
    private ServiceDelegate serviceDelegate;
    private SharedPreferences sharedPreferences;
    private View container;
    private Intent iniciar;
    private String[] PERMISOS = {
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!recorrerPermisos(LoginAppActivity.this,PERMISOS)){
            ActivityCompat.requestPermissions(LoginAppActivity.this,PERMISOS,1);
        }
        binding = ActivityLoginAppBinding.inflate(getLayoutInflater());
        container = binding.getRoot();
        singIn = (Button) container.findViewById(R.id.signIn);
        user = (TextView) container.findViewById(R.id.user);
        password = (TextView) container.findViewById(R.id.password);
        c_cuenta = (TextView) container.findViewById(R.id.c_cuenta);
        layoutLogin = (LinearLayout) container.findViewById(R.id.linearLogin);
        serviceDelegate = new ServiceDelegate();
        sharedPreferences = getSharedPreferences(Utils.KEY_PREF, Context.MODE_PRIVATE);
//        Utils.printToast(container.getContext(),"Bienvenido",Toast.LENGTH_SHORT);
        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesion(view);
            }
        });
        c_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceAct();
            }
        });
        setContentView(container);
    }

    public void iniciarSesion(View v) {
        iniciar = new Intent(this, MainActivity.class);
//        Toast toast = Toast.makeText(this, "", Toast.LENGTH_LONG);
        try {
            Retrofit retrofit = serviceDelegate.getRetrofit();
            UserService userService = retrofit.create(UserService.class);
            if (user.getText().length() == 0 || password.getText().length() == 0) {
                Utils.printToast(container.getContext(),"¡Los campos son obligatorios!",Toast.LENGTH_LONG);
            } else {
                Utils.printProgressDialogSpinner(container.getContext(),"Inicio de sesión"
                        ,"Validando datos...");
                RequestLogin requestLogin = new RequestLogin();
                requestLogin.setCorreo(user.getText().toString());
                requestLogin.setClave(password.getText().toString());
                Call<Object> call = userService.getUsuarios(requestLogin);
                call.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (response.isSuccessful()){
                            try {
                                assert response.body() != null;
                                String obj = gson.toJson(response.body());
                                ResponseLogin responseLogin = gson.fromJson(obj,ResponseLogin.class);
                                if (!obj.isEmpty()){
                                    if (Objects.equals(responseLogin.getStatus(), "202.0")){
                                        if (responseLogin.getUser().getRol() == null){
                                            Utils.printToast(container.getContext(),
                                                    String.format("%s a tu cuenta le faltan permisos para utilizar la app",responseLogin.getUser().getName())
                                                    ,Toast.LENGTH_LONG);
                                        }else{
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString(Utils.NOMBRE_USUARIO,responseLogin.getUser().getName());
                                            editor.putString(Utils.CORREO,responseLogin.getUser().getEmail());
                                            editor.putString(Utils.ID_USUARIO,responseLogin.getUser().getId());
                                            editor.putString(Utils.ROL,responseLogin.getUser().getRol());
                                            editor.apply();
                                            user.setText("");
                                            password.setText("");
                                            Utils.printToast(container.getContext(),
                                                    String.format("Bienvenido %s",responseLogin.getUser().getName()),Toast.LENGTH_SHORT);
                                            Utils.dimissProgressDialogSpinner();
                                            startActivity(iniciar);
                                        }
                                    }else{
                                        Utils.dimissProgressDialogSpinner();
                                        Utils.printToast(container.getContext(),"Correo o clave incorrectos, intentalo de nuevo",Toast.LENGTH_SHORT);
                                    }
                                }
                            }catch (Exception e){
                                Utils.dimissProgressDialogSpinner();
                                Utils.printToast(container.getContext(),"Respuesta fallida",Toast.LENGTH_SHORT);
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        System.out.println(t.getLocalizedMessage());
                        Utils.dimissProgressDialogSpinner();
                        Utils.printToast(container.getContext(),"Fallo el consumo",Toast.LENGTH_SHORT);
                    }
                });
            }
        } catch (Exception exception) {
            Utils.printToast(container.getContext(),"Algo salió mal",Toast.LENGTH_SHORT);
        }
    }

    public void replaceAct(){
        iniciar = new Intent(this,RegisterActivity.class);
        startActivity(iniciar);
    }

    private boolean recorrerPermisos(Context context,String... PERMISOS){
        if (context != null && PERMISOS != null){
            for (String permiso: PERMISOS) {
                if (ActivityCompat.checkSelfPermission(context,permiso) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }
}