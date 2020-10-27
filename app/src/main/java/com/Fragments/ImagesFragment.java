package com.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.Adapter.AdapterImages;
import com.Adapter.AdapterRaza;
import com.DataObject.Images;
import com.DataObject.Raza;
import com.Networking.APIs;
import com.Networking.Networking;
import com.Util.ProgressView;
import com.Util.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thecats.R;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImagesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View view;
    private ListView ListViewImages;
    private SwipeRefreshLayout swipeRefresh;
    private Vector<Images> listaImages;
    private Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_images, container, false);
        init();
        return view;
    }

    private void init() {
        ListViewImages = view.findViewById(R.id.ListViewImages);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        gson = new Gson();

        swipeRefresh.setOnRefreshListener(this);


        cargarImages();
    }

    private void cargarImages() {
        ProgressView.Show(getContext());
        Networking.get(APIs.IMAGENES+"?limit=50", new Networking.ICallback() {
            @Override
            public void onFail(String error) {
                Utils.mostrarAlertDialog(getContext(),error);
                ProgressView.Dismiss();
                ListViewImages.setAdapter(null);
            }

            @Override
            public void onSuccess(String response) {
                listaImages = gson.fromJson(response.toString(), new TypeToken<Vector<Images>>() {}.getType());
                cargarAdapter();
            }
        });
    }

    private void cargarAdapter() {
        if (listaImages.size()>0) {
            AdapterImages adapterImages = new AdapterImages(ImagesFragment.this.getActivity(), R.layout.list_item_images,listaImages);
            ListViewImages.setAdapter(adapterImages);
        }else{
            Utils.mostrarToast(getContext(), "Busqueda sin resultados");
            ListViewImages.setAdapter(null);
            ProgressView.Dismiss();
        }
    }

    @Override
    public void onRefresh() {
        swipeRefresh.setRefreshing(false);
        cargarImages();
    }




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ImagesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ImagesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ImagesFragment newInstance(String param1, String param2) {
        ImagesFragment fragment = new ImagesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

}