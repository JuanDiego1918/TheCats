package com.Util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.DataObject.Images;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.thecats.R;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static File DirApp() {
        File SDCardRoot = Environment.getExternalStorageDirectory(); //Environment.getDataDirectory(); //
        File dirApp = new File(SDCardRoot.getPath() + "/" + Const.nameDirApp);
        //File dirApp = new File(SDCardRoot + "/data/" + "co.com.SuperRicas/" + Const.nameDirApp);
        if (!dirApp.isDirectory()) {
            dirApp.mkdirs();
        }
        return dirApp;
    }

    public static void cargarImg(final ImageView image, final String url,final Context context) {
        try {
            Glide.with(context)
                    .load(url)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            mostrarToast(context,"Error al cargar Imagen");
                            image.setImageAlpha(R.drawable.ic_error_image);
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
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(image);


        } catch (Exception e) {
            Log.e("TAG", "No existe imagen en ruta: " +url);
        }
    }

    public static String FechaActual(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static void mostrarAlertDialog(Context context, String mensaje) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_generico);
        ((TextView) dialog.findViewById(R.id.txtMensaje)).setText(mensaje);
        ((Button) dialog.findViewById(R.id.btnAceptar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void mostrarToast(Context context, String mensaje) {
        Toast toast = Toast.makeText(context, mensaje, Toast.LENGTH_SHORT);
        toast.show();
    }
}
