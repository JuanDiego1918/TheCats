package com.thecats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.DataObject.Images;
import com.DataObject.Raza;
import com.Networking.APIs;
import com.Networking.Networking;
import com.Util.ProgressView;
import com.Util.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Vector;

public class DetalleActivity extends AppCompatActivity {

    private Raza raza;
    private ImageView imagen;
    private TextView txtDescrip,txtNombreRaza;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        cargarBundle();
        init();
    }

    private void cargarBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("raza")) {
                raza = (Raza) bundle.getSerializable("raza");
            }
        }
    }

    private void init() {
        imagen = (ImageView) findViewById(R.id.imagen);
        txtNombreRaza = (TextView) findViewById(R.id.txtNombreRaza);
        txtDescrip = (TextView) findViewById(R.id.txtDescrip);
        gson=new Gson();

        if(raza!=null) {
            txtNombreRaza.setText(raza.getName());
            String descripcion = "Origen : " + raza.getOrigin() + "\n" +
                    "Temperamento: " + raza.getTemperament() + "\n\n" +
                    "Descripcion \n" + raza.getDescription();

            txtDescrip.setText(descripcion);
            obtenerImagen();
        }
    }

    private void obtenerImagen() {
        ProgressView.Show(this);
        Networking.get(APIs.IMAGENES+"?breed_id="+raza.getID(), new Networking.ICallback() {
            @Override
            public void onFail(String error) {
                Utils.mostrarAlertDialog(DetalleActivity.this,error);
                ProgressView.Dismiss();
            }

            @Override
            public void onSuccess(String response) {
                Vector <Images> listaResultado = gson.fromJson(response.toString(), new TypeToken<Vector<Images>>() {}.getType());

                for (Images imagenUlr:listaResultado) {
                    Utils.cargarImg(imagen,imagenUlr.getURL(),DetalleActivity.this);
                }
            }
        });
    }

    public void verWikipedia(View view) {
        Intent browserIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(raza.getWikipediaURL()));
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(browserIntent);
    }
}