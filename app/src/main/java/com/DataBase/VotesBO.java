package com.DataBase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.DataObject.Votes;
import com.Util.Utils;

import java.io.File;
import java.util.Vector;

public class VotesBO {

   public static final String TAG = VotesBO.class.getName();

   public static String mensaje;

   public static boolean CrearConfigDB() {
      SQLiteDatabase db = null;
      try {
         File dbFile = new File(Utils.DirApp(), "Config.db");
         db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
         String config = "CREATE TABLE IF NOT EXISTS Votes( " +
                 "    Id       INTEGER       PRIMARY KEY AUTOINCREMENT, " +
                 "    idImagen VARCHAR (100), " +
                 "    fecha    DATE );";
         db.execSQL(config);
         return true;

      } catch (Exception e) {
         mensaje = e.getMessage();
         Log.e(TAG, " CrearConfigDB -> " + mensaje);
         return false;

      } finally {
         if (db != null) {
            db.close();
         }
      }
   }

   public static boolean AlmacenarVoto(String idImagen) {
      SQLiteDatabase db = null;
      try {
         File dbFile = new File(Utils.DirApp(), "Config.db");

         if (!dbFile.exists()) {
            CrearConfigDB();
         }
         if (dbFile.exists()) {
            db = SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);

            String fechaActual = Utils.FechaActual("yyyy-MM-dd HH:mm:ss");

            ContentValues values = new ContentValues();
            values.put("idImagen", idImagen);
            values.put("fecha", fechaActual);
            long rows = db.insertOrThrow("Votes", null, values);
            return rows > 0;
         } else {
            Log.i(TAG, "GuardarConfigUsuario: No Existe la Base de Datos Config.db o No tiene Acceso a la SD");
            return false;
         }

      } catch (Exception e) {
         mensaje = e.getMessage();
         return false;

      } finally {
         if (db != null) {
            db.close();
         }
      }
   }


   public static Vector<Votes> listaVotos(Vector<String> listaItems) {
      Vector<Votes> listaVotos = new Vector<Votes>();
      SQLiteDatabase db = null;
      Cursor cursor = null;
      mensaje = "";
      try {
         File dbFile = new File(Utils.DirApp(), "Config.db");
         if (dbFile.exists()) {
            db = SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
            String query = "SELECT Id,idImagen,fecha FROM Votes ";
            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
               do {
                  Votes votes = new Votes();
                  votes.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                  votes.setIdImagen(cursor.getString(cursor.getColumnIndex("idImagen")));
                  votes.setFecha(cursor.getString(cursor.getColumnIndex("fecha")));
                  listaVotos.addElement(votes);
                  listaItems.addElement("Id Imagen: "+votes.getIdImagen()+"\n"+"Fecha: "+votes.getFecha());
               }
               while (cursor.moveToNext());
            }
         }
      }
      catch (Exception e) {
         mensaje = e.getMessage();
      }
      finally {
         if (db != null)
            db.close();
         if (cursor != null)
            cursor.close();
      }
      return listaVotos;
   }

}
