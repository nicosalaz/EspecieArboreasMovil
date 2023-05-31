package pg.proyecto.especiesarboreas.shared;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import pg.proyecto.especiesarboreas.backend.ServiceDelegate;
import com.google.gson.Gson;

import java.io.Serializable;

public class Utils implements Serializable {

    public static final String URL="https://api.especiearborea.especie-arboreas.com/";
    public static final String URL_LOCAL="http://192.168.1.16:3000/";
    public static final String END_AUTH="auth";
    public static final String END_REGISTER_USER="api/usuarios/create";
    public static final String END_ALL_ESPECIES="api/especie-arborea/especieArborea";

    public static final String END_ALL_ESPECIES_REQUEST="api/especie-request/especieArborea";
    public static final String END_ACCEPT_REQUEST="api/especie-request/aceptarRequest";
    public static final String END_ALL_TYPE_ESPECIES="api/especie";
    public static final String END_CREATE_ESPECIES="api/especie-request/crearEspecieArborea";
    public static final String END_TIPOS_ID="api/identificacion";
    public static final String KEY_PREF = "usuario";
    public static final String NOMBRE_USUARIO = "nombre";
    public static final String CORREO = "correo";
    public static final String ID_USUARIO = "id";
    public static final String ROL = "rol";
    private static ProgressDialog progressDialog;
    private static AlertDialog.Builder alertDialog;
    public static Gson gson = new Gson();
    public static ServiceDelegate serviceDelegate = new ServiceDelegate();

    public static String[] ciudades ={"Cali"};

    public static void printToast(Context context,String msg,int duration){
        Toast.makeText(context, msg, duration).show();
    }
    public static void  printProgressDialogSpinner(Context context,String title,String msg){
        try {
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle(title);
            progressDialog.setMessage(msg);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
            printToast(context,"ProgressDialog fallido",Toast.LENGTH_SHORT);
        }
    }
    public static void  dimissProgressDialogSpinner(){
        try {
            progressDialog.dismiss();
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }

    public static void printAlertDialog(Context context,String title,String msg){
        alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

}
