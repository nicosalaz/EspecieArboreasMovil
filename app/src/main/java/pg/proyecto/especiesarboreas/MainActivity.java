package pg.proyecto.especiesarboreas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import pg.proyecto.especiesarboreas.Activities.ColaboradorActivity;
import pg.proyecto.especiesarboreas.Activities.LoginAppActivity;

import pg.proyecto.especiesarboreas.Activities.SolicitudReporteActivity;
import pg.proyecto.especiesarboreas.R;

import pg.proyecto.especiesarboreas.databinding.ActivityMainBinding;
import pg.proyecto.especiesarboreas.shared.Utils;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private Intent colabAcyivity;
    private SharedPreferences sharedPreferences;
    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        sharedPreferences = getSharedPreferences(Utils.KEY_PREF, Context.MODE_PRIVATE);
        binding.mainNav.setOnItemSelectedListener( item -> {
            switch (item.getItemId()){
                case R.id.mapa:
                    try {
                        Navigation.findNavController(this,R.id.fragmentContainerMain).navigate(R.id.action_feedFragment_to_mapsFragment);
                    }catch (Exception e){
                        System.out.println(e.fillInStackTrace().toString());
                    }
                    break;
                case R.id.publicaciones:
                    try {
//                        Navigation.findNavController(this,R.id.fragmentContainerMain).navigate(R.id.action_mapsFragment_to_feedFragment);
//                            Utils.printToast(view.getContext(),"Sección en construcción",Toast.LENGTH_LONG);
                    }catch (Exception e){
                        System.out.println(e.fillInStackTrace().toString());
                    }
                    break;
            }
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_especifco,menu);
        disabledButtondByRol(sharedPreferences.getString(Utils.ROL,null),menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.registro:
                Toast.makeText(this, "Registrar", Toast.LENGTH_LONG).show();
                colabAcyivity = new Intent(getApplicationContext(), ColaboradorActivity.class);
                startActivity(colabAcyivity);
                break;
            case R.id.reportes:
                Toast.makeText(this, "Reportes", Toast.LENGTH_LONG).show();
                colabAcyivity = new Intent(getApplicationContext(), SolicitudReporteActivity.class);
                startActivity(colabAcyivity);
                break;
            case R.id.mantenimiento:
                Toast.makeText(this, "Mantenimiento", Toast.LENGTH_LONG).show();
                break;
            case R.id.logOut:
                sharedPreferences.edit().clear();
                colabAcyivity = new Intent(getApplicationContext(), LoginAppActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(colabAcyivity);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void disabledButtondByRol(String rol,Menu menu){
        int tamanio = menu.size();
        int id_btn = 0;
        switch (rol){
            case "Colaborador":
                id_btn = R.id.registro;
                break;
            case "Administrador":
                id_btn = 1;
                break;
        }
        if (id_btn != 0){
            for (int i = 0; i < tamanio; i++) {
                if (id_btn == 1){
                    menu.getItem(i).setVisible(true);
                }else{
                    if (menu.getItem(i).getItemId() != id_btn){
                        menu.getItem(i).setVisible(false);
                        if (menu.getItem(i).getItemId() == R.id.logOut) {
                            menu.getItem(i).setVisible(true);
                        }
                    }
                }
            }
        }
    }
}