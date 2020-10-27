package com.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.Adapter.AdapterRaza;
import com.DataObject.Raza;
import com.Networking.APIs;
import com.Networking.Networking;
import com.Util.ProgressView;
import com.Util.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thecats.R;

import java.util.Collections;
import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View view;
    private ListView ListViewRazas;
    private SwipeRefreshLayout swipeRefresh;
    private Vector<Raza> listaRaza;
    private Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list, container, false);

        init();
        return view;
    }

    private void init() {
        ListViewRazas = view.findViewById(R.id.ListViewRazas);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        gson = new Gson();

        swipeRefresh.setOnRefreshListener(this);


        cargarRaza();
    }

    private void cargarRaza() {
        ProgressView.Show(getContext());
        Networking.get(APIs.RAZAS, new Networking.ICallback() {
            @Override
            public void onFail(String error) {
                Utils.mostrarAlertDialog(getContext(),error);
                ProgressView.Dismiss();
                ListViewRazas.setAdapter(null);
            }

            @Override
            public void onSuccess(String response) {
                listaRaza = gson.fromJson(response.toString(), new TypeToken<Vector<Raza>>() {}.getType());
                cargarAdapter();
            }
        });
    }

    private void cargarAdapter() {
        if (listaRaza.size()>0) {
            AdapterRaza adapterRaza = new AdapterRaza(ListFragment.this.getActivity(), listaRaza);
            ListViewRazas.setAdapter(adapterRaza);
        }else{
            Utils.mostrarToast(getContext(), "Busqueda sin resultados");
            ListViewRazas.setAdapter(null);
        }
        ProgressView.Dismiss();
    }

    @Override
    public void onRefresh() {
        swipeRefresh.setRefreshing(false);
        cargarRaza();
    }


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
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