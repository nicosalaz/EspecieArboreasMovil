package pg.proyecto.especiesarboreas.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import java.util.Objects;

import pg.proyecto.especiesarboreas.MainActivity;
import pg.proyecto.especiesarboreas.R;
import pg.proyecto.especiesarboreas.databinding.ActivitySolicitudReporteBinding;
import pg.proyecto.especiesarboreas.fragments.cientifico.SolicitudesReporteFragment;

public class SolicitudReporteActivity extends AppCompatActivity{
    private ActivitySolicitudReporteBinding binding;
    private ConstraintLayout constraintLayout;
    private Fragment myFragment;
    private AppCompatActivity activity;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*binding = ActivitySolicitudReporteBinding.inflate(getLayoutInflater());
        constraintLayout = binding.getRoot();*/
        Objects.requireNonNull(getSupportActionBar()).setTitle("Cientifico");
        view = View.inflate(this,R.layout.activity_solicitud_reporte,null);
        activity = (AppCompatActivity) view.getContext();
        setContentView(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_solicitudes_registro,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.volver:
                try {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }catch (Exception e){
                    System.out.println(e.fillInStackTrace().toString());
                }
                break;
            case R.id.pendientes:
                myFragment = SolicitudesReporteFragment.newInstance();
                break;
        }
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerCientifico,myFragment)
                .addToBackStack(null)
                .commit();
        return super.onOptionsItemSelected(item);
    }
}