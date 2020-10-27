package com.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.DataObject.Raza;
import com.Util.Utils;
import com.thecats.R;

import java.util.Vector;

public class AdapterRaza extends ArrayAdapter<Raza> {

    Activity context;
    Vector<Raza> listaRaza;

    public AdapterRaza(@NonNull Activity context, Vector<Raza> listaRaza) {
        super(context, R.layout.list_item,listaRaza);
        this.context = context;
        this.listaRaza = listaRaza;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_item, null);
            Row holder = new Row();
            holder.txtNombre = (TextView) convertView.findViewById(R.id.txtNombre);
            holder.editOrigen = (EditText) convertView.findViewById(R.id.editOrigen);
            holder.editTemperamento = (EditText) convertView.findViewById(R.id.editTemperamento);
            holder.btnDetalle = (Button) convertView.findViewById(R.id.btnDetalle);
            convertView.setTag(holder);
        }
        Row holder = (Row) convertView.getTag();
        final Raza raza = listaRaza.get(position);

        String nombreRaza = raza.getName();
        String origen = raza.getOrigin();
        String temperamento = raza.getTemperament();

        holder.txtNombre.setText(nombreRaza);
        holder.editOrigen.setText(origen);
        holder.editTemperamento.setText(temperamento);

        holder.btnDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+raza.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        return (convertView);
    }

    public static class Row {
        TextView txtNombre;
        EditText editOrigen;
        EditText editTemperamento;
        Button btnDetalle;
    }

    @Override
    public int getCount() {
        return listaRaza.size();
    }

    @Override
    public Raza getItem(int position) {
        return listaRaza.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}

