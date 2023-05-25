package pg.proyecto.especiesarboreas.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Objects;

import pg.proyecto.especiesarboreas.MainActivity;
import pg.proyecto.especiesarboreas.R;
import pg.proyecto.especiesarboreas.databinding.ActivitySolicitudReporteBinding;

public class SolicitudReporteActivity extends AppCompatActivity {
    ActivitySolicitudReporteBinding binding;
    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*binding = ActivitySolicitudReporteBinding.inflate(getLayoutInflater());
        constraintLayout = binding.getRoot();*/
        Objects.requireNonNull(getSupportActionBar()).setTitle("Cientifico");
        setContentView(R.layout.activity_solicitud_reporte);
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
        }
        return super.onOptionsItemSelected(item);
    }
}