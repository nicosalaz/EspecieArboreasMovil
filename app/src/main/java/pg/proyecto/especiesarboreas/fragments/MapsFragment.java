package pg.proyecto.especiesarboreas.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import pg.proyecto.especiesarboreas.R;
import pg.proyecto.especiesarboreas.backend.ServiceDelegate;
import pg.proyecto.especiesarboreas.backend.interfaces.EspecieService;
import pg.proyecto.especiesarboreas.backend.models.Entities.EspecieArborea;
import pg.proyecto.especiesarboreas.backend.models.Entities.Position;
import pg.proyecto.especiesarboreas.backend.models.Response.ResponseEspeciesArboreas;
import pg.proyecto.especiesarboreas.shared.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MapsFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap map;
    private View view;
    private Gson gson = new Gson();
    /*private OnMapReadyCallback callback = new OnMapReadyCallback() {

        *//**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         *//*
        @Override
        public void onMapReady(GoogleMap googleMap) {

        }
    };*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_maps, container, false);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        try {
            requestLocationPermission(googleMap);
            createMarker();
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
        //        requestLocationPermission();
    }

    private void createMarker() {
        ServiceDelegate serviceDelegate = new ServiceDelegate();
        try {
            Utils.printProgressDialogSpinner(view.getContext(),"Mapa","Cargando Mapa");
            Retrofit retrofit = serviceDelegate.getRetrofit();
            EspecieService especieService = retrofit.create(EspecieService.class);
            Call<Object> especies = especieService.allEspecies();
            especies.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if (response.isSuccessful()){
                        assert response.body() != null;
                        String obj = gson.toJson(response.body());
                        ResponseEspeciesArboreas responseEspeciesArboreas = gson.fromJson(obj,ResponseEspeciesArboreas.class);
                        Position pos = new Position();
                        if (responseEspeciesArboreas.getResponse() != null){
                            for (EspecieArborea esp:responseEspeciesArboreas.getResponse()){
                                pos = gson.fromJson(esp.getPosition(), Position.class);
                                esp.setLongitud(pos.getLng());
                                esp.setLatitud(pos.getLat());
                                map.addMarker(new MarkerOptions()
                                        .position(new LatLng(esp.getLatitud(),esp.getLongitud()))
                                        .title(esp.getNombre_especie()));
                            }
                            LatLng sydney = new LatLng(pos.getLat(), pos.getLng());
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,13f),100,null);
                        }else{
                            Utils.dimissProgressDialogSpinner();
                        }
                    }else{
                        Utils.printToast(view.getContext(),"Algo salió mal",Toast.LENGTH_LONG);
                    }
                    Utils.dimissProgressDialogSpinner();
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    System.out.println(t.getLocalizedMessage());
                    Utils.dimissProgressDialogSpinner();
                    Utils.printToast(view.getContext(),t.getLocalizedMessage(),Toast.LENGTH_LONG);
                }
            });
        }catch (Exception e){
            Utils.dimissProgressDialogSpinner();
            System.out.println(e.getLocalizedMessage());
        }
    }

    private void requestLocationPermission(GoogleMap googleMap){
        if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            map.setMyLocationEnabled(true);
        }else{
            ActivityCompat.requestPermissions((Activity) view.getContext(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
        }
    }
}