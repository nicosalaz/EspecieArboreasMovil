package pg.proyecto.especiesarboreas.fragments.colaboradores;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import pg.proyecto.especiesarboreas.R;
import pg.proyecto.especiesarboreas.databinding.FragmentReporteMantenimientoBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReporteMantenimientoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReporteMantenimientoFragment extends Fragment {
    FragmentReporteMantenimientoBinding binding;
    private ImageButton btnImgMant;
    private ImageView imgMant;
    private EditText descMant;
    private Button btnRegMant;

    public ReporteMantenimientoFragment() {
        // Required empty public constructor
    }
    public static ReporteMantenimientoFragment newInstance(String param1, String param2) {
        ReporteMantenimientoFragment fragment = new ReporteMantenimientoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentReporteMantenimientoBinding.inflate(getLayoutInflater());
        FrameLayout frameLayout = binding.getRoot();
        btnImgMant = (ImageButton) frameLayout.findViewById(R.id.btn_img_mant);
        btnRegMant = (Button) frameLayout.findViewById(R.id.btn_reg_mant);
        imgMant = (ImageView) frameLayout.findViewById(R.id.img_mant);
        descMant = (EditText) frameLayout.findViewById(R.id.desc_mant);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reporte_mantenimiento, container, false);
    }
}