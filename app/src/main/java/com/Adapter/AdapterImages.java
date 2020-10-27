package com.Adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.DataBase.VotesBO;
import com.DataObject.Images;
import com.DataObject.Raza;
import com.Networking.APIs;
import com.Util.ProgressView;
import com.Util.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.thecats.R;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class AdapterImages extends ArrayAdapter<Images> {

    private static final String TAG = AdapterImages.class.getName();
    Activity context;
    Vector<Images> listaImages;
    int layout;

    public AdapterImages(@NonNull Activity context, int layout,Vector<Images> listaImages) {
        super(context, layout, listaImages);
        this.context = context;
        this.layout = layout;
        this.listaImages = listaImages;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(layout, null);
            Row holder = new Row();
            holder.imagen = (ImageView) convertView.findViewById(R.id.imagen);
            holder.btnLike = (ImageButton) convertView.findViewById(R.id.btnLike);
            convertView.setTag(holder);
        }
        Row holder = (Row) convertView.getTag();
        final Images images = listaImages.get(position);
        images.setWidth(images.getWidth());
        images.setHeight(images.getHeight());
        Utils.cargarImg(holder.imagen,images.getURL(),context);

        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean save = VotesBO.AlmacenarVoto(images.getID());
                Utils.mostrarToast(context,save ? "Voto Realizado":"Ha ocurrido un error inesperado");
            }
        });


        return (convertView);
    }

    public static class Row {
        ImageView imagen;
        ImageButton btnLike;
    }

    @Override
    public int getCount() {
        return listaImages.size();
    }

    @Override
    public Images getItem(int position) {
        return listaImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



}

