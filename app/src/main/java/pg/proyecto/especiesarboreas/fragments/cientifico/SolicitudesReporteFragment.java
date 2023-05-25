package pg.proyecto.especiesarboreas.fragments.cientifico;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import pg.proyecto.especiesarboreas.R;
import pg.proyecto.especiesarboreas.backend.ServiceDelegate;
import pg.proyecto.especiesarboreas.backend.interfaces.EspecieService;
import pg.proyecto.especiesarboreas.backend.models.Response.ResponseEspeciesReq;
import pg.proyecto.especiesarboreas.backend.models.Response.ResponseEspeciesReqList;
import pg.proyecto.especiesarboreas.lists.EspeciesReqAdapter;
import pg.proyecto.especiesarboreas.shared.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SolicitudesReporteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SolicitudesReporteFragment extends Fragment {

    private View view;
    private List<ResponseEspeciesReq> responseEspeciesReqs;
    private ServiceDelegate serviceDelegate;
    private EspecieService service;
    private RecyclerView recyclerView;
    private EspeciesReqAdapter adapter;

    public SolicitudesReporteFragment() {
        // Required empty public constructor
    }
    public static SolicitudesReporteFragment newInstance(String param1, String param2) {
        SolicitudesReporteFragment fragment = new SolicitudesReporteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_solicitudes_reporte, container, false);
        serviceDelegate = new ServiceDelegate();
        recyclerView = (RecyclerView) view.findViewById(R.id.list_sr);
        init();
        return view;
    }

    private void init() {
        getData();
    }
    private void getData(){
        Retrofit retrofit = serviceDelegate.getRetrofit();
        service = retrofit.create(EspecieService.class);
        try {
            Utils.printProgressDialogSpinner(view.getContext(),"Peticiones nuevas especies","Cargando peticiones");
            Call<ResponseEspeciesReqList> allEspeciesReq = service.allEspeciesReq();
            allEspeciesReq.enqueue(new Callback<ResponseEspeciesReqList>() {
                @Override
                public void onResponse(Call<ResponseEspeciesReqList> call, Response<ResponseEspeciesReqList> response) {
                    if (response.isSuccessful()){
                        assert response.body() != null;
                        Utils.dimissProgressDialogSpinner();
                        adapter = new EspeciesReqAdapter(response.body().getResponse(),view.getContext());
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                        recyclerView.setAdapter(adapter);
                    }
                    Utils.dimissProgressDialogSpinner();
                }

                @Override
                public void onFailure(Call<ResponseEspeciesReqList> call, Throwable t) {
                    System.out.println(t.getLocalizedMessage());
                    Utils.dimissProgressDialogSpinner();
                    Utils.printToast(view.getContext(),"Algo salió mal", Toast.LENGTH_SHORT);
                }
            });
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
            Utils.dimissProgressDialogSpinner();
            Utils.printToast(view.getContext(),"Algo salió mal", Toast.LENGTH_SHORT);
        }
    }
}