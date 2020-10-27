package com.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.DataBase.VotesBO;
import com.DataObject.Votes;
import com.Util.ProgressView;
import com.Util.Utils;
import com.google.gson.Gson;
import com.thecats.R;

import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VotesFragment extends Fragment {

    private View view;
    private ListView listVotes;
    private Vector<Votes> listaVotos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_votes, container, false);

        init();
        return view;
    }

    private void init() {
        listVotes = view.findViewById(R.id.listVotes);
        cargarDialog();
    }

    private void cargarDialog() {
        ProgressView.Show(getContext());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(2000);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ProgressView.Dismiss();
                                if (!cargarListaVotos()) {
                                    Utils.mostrarAlertDialog(getContext(), "Debe realizar votaciones.");
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private boolean cargarListaVotos() {
        boolean estado = false;
        Vector<String> listaItems = new Vector<String>();

        listaVotos = VotesBO.listaVotos(listaItems);

        if (listaVotos.size()>0){
            estado = true;
            ArrayAdapter<String> itemsAdapter =
                    new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listaItems);
            listVotes.setAdapter(itemsAdapter);
        }else{
            listVotes.setAdapter(null);
        }

        return estado;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VotesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VotesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VotesFragment newInstance(String param1, String param2) {
        VotesFragment fragment = new VotesFragment();
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