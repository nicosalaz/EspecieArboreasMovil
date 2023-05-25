package pg.proyecto.especiesarboreas.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Objects;

import pg.proyecto.especiesarboreas.MainActivity;
import pg.proyecto.especiesarboreas.R;


public class ColaboradorActivity extends AppCompatActivity {
    Intent intent;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Colaborador");
        setContentView(R.layout.activity_colaborador);
        permisos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_colaborador,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.rNuevo:
                try {
                    Navigation
                            .findNavController(this,R.id.fragmentContainerColab)
                            .navigate(R.id.nuevaEspecieFragment);
                }catch (Exception e){
                    System.out.println(e.fillInStackTrace().toString());
                }
                break;
            case R.id.rMantenimiento:
                try {
                    Navigation
                            .findNavController(this,R.id.fragmentContainerColab)
                            .navigate(R.id.reporteMantenimientoFragment);
                }catch (Exception e){
                    System.out.println(e.fillInStackTrace().toString());
                }
                break;
            case R.id.newPost:
                try {
                    Navigation
                            .findNavController(this,R.id.fragmentContainerColab)
                            .navigate(R.id.postFragment);
                }catch (Exception e){
                    System.out.println(e.fillInStackTrace().toString());
                }
                break;
            case R.id.volver:
                try {
                   intent = new Intent(this, MainActivity.class);
                   startActivity(intent);
                }catch (Exception e){
                    System.out.println(e.fillInStackTrace().toString());
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void permisos(){
        if (ContextCompat.checkSelfPermission(ColaboradorActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            System.out.println("necesita activar");
            ActivityCompat.requestPermissions(ColaboradorActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1000);
        }
        if (ContextCompat.checkSelfPermission(ColaboradorActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ColaboradorActivity.this,
                    new String[]{Manifest.permission.CAMERA},1000);
        }
        if (ContextCompat.checkSelfPermission(ColaboradorActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            System.out.println("necesita activar");
            ActivityCompat.requestPermissions(ColaboradorActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1000);
        }
    }

    /*public void  abrirCamara(View v){
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent,1);
        }
    }*/
    /*public void tomarFoto(View v){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                System.out.println(ex.fillInStackTrace().toString());
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "pg.proyecto.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "pg.proyecto.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

        }
    }*/
/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgNuevoCaso.setImageBitmap(imageBitmap);
        }
    }*/
}