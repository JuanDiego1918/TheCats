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
        cargarImg(holder.imagen,images);


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

    public void cargarImg(final ImageView image, Images images) {
        String url = images.getURL();
        try {
            Glide.with(context)
                    .load(url)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Utils.mostrarToast(context,"Error al cargar Imagen");
                            ProgressView.Dismiss();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            ProgressView.Dismiss();
                            return false;
                        }
                    })
                    .fitCenter()
                    .placeholder(R.drawable.ic_error_image)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(image);


        } catch (Exception e) {
            Log.e(TAG, "No existe imagen en ruta: " +url);
        }
    }

}

