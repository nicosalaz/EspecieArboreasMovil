package pg.proyecto.especiesarboreas.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import pg.proyecto.especiesarboreas.R;
import pg.proyecto.especiesarboreas.backend.ServiceDelegate;
import pg.proyecto.especiesarboreas.backend.interfaces.EspecieService;
import pg.proyecto.especiesarboreas.backend.interfaces.PublicacionService;
import pg.proyecto.especiesarboreas.backend.models.Response.PublicacionResponse;
import pg.proyecto.especiesarboreas.lists.PublicacionesReqAdapter;
import pg.proyecto.especiesarboreas.shared.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedFragment extends Fragment {
    private View view;
    PublicacionResponse responsePub;
    PublicacionService publicacionService;
    ServiceDelegate serviceDelegate;
    PublicacionesReqAdapter adapter;
    RecyclerView recyclerView;
    public FeedFragment() {
        // Required empty public constructor
    }
    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feed, container, false);
        serviceDelegate = new ServiceDelegate();
        recyclerView = (RecyclerView) view.findViewById(R.id.rec_pub);
        getData();
        return view;
    }

    private void getData() {
        Retrofit retrofit = serviceDelegate.getRetrofit();
        publicacionService = retrofit.create(PublicacionService.class);
        try {
            Utils.printProgressDialogSpinner(view.getContext(),"Publicaciones","Cargando publicaciones");
            Call<PublicacionResponse> allPub = publicacionService.getAllPubs();
            allPub.enqueue(new Callback<PublicacionResponse>() {
                @Override
                public void onResponse(Call<PublicacionResponse> call, Response<PublicacionResponse> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        responsePub = response.body();
                        adapter = new PublicacionesReqAdapter(responsePub.getResponse(),view.getContext());
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                        recyclerView.setAdapter(adapter);
                    }
                    Utils.dimissProgressDialogSpinner();
                }

                @Override
                public void onFailure(Call<PublicacionResponse> call, Throwable t) {
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