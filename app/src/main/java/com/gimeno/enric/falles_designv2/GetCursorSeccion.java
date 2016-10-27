package com.gimeno.enric.falles_designv2;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;

// Generacion de la query adaptada a cada seccion
public class GetCursorSeccion {

    // Contexto
    Context mContext;

    // Definicion de la constante query y cursor
    Cursor cursor_MA;
    Cursor cursor_IN;
    String query;

    // URI
    private  static Uri uri = Uri.parse("content://es.enric.fallas/fallas/*");

    public GetCursorSeccion(Context mContext) {
        this.mContext = mContext;
    }

    // Obtenemos las secciones definidas por el usuario en la interfaz
    // y mostramos los datos a partir de esta
    public Cursor getSeccionMA () {
        SharedPreferences pref_MA = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
        String caseFallas_MA = pref_MA.getString("pref_falla_MA", "N_MA");

        switch (caseFallas_MA) {
            //FALLAS MAYORES
            // Todas
            case "T_MA":
                query = null;
                break;
            // Seccion ESPECIAL
            case "MA_E":
                query = "seccion_ma = 'ESPECIAL'";
                break;
            // Seccion 1A
            case "1A":
                query = "seccion_ma = '1A'";
                break;
            case "1B":
                query = "seccion_ma = '1B'";
                break;
            case "2A":
                query = "seccion_ma = '2A'";
                break;
            case "2B":
                query = "seccion_ma = '2B'";
                break;
            case "3A":
                query = "seccion_ma = '3A'";
                break;
            case "3B":
                query = "seccion_ma = '3B'";
                break;
            case "4A":
                query = "seccion_ma = '4A'";
                break;
            case "4B":
                query = "seccion_ma = '4B'";
                break;
            case "4C":
                query = "seccion_ma = '4C'";
                break;
            case "5A":
                query = "seccion_ma = '5A'";
                break;
            case "5B":
                query = "seccion_ma = '5B'";
                break;
            case "5C":
                query = "seccion_ma = '5C'";
                break;
            case "6A":
                query = "seccion_ma = '6A'";
                break;
            case "6B":
                query = "seccion_ma = '6B'";
                break;
            case "6C":
                query = "seccion_ma = '6C'";
                break;
            case "7A":
                query = "seccion_ma = '7A'";
                break;
            case "7B":
                query = "seccion_ma = '7B'";
                break;
            case "7C":
                query = "seccion_ma = '7C'";
                break;
            // Ninguna falla
            case "N_MA":
                query = "ninguna";
                break;

        }
        // Seleccion de los 3 casos posibles
        if (query != null && query != "ninguna") {
            cursor_MA = mContext.getContentResolver().query(uri, null, query, null, null);
        }else if (query == "ninguna") {
            cursor_MA = null;
        }else if (query == null){
            cursor_MA = mContext.getContentResolver().query(uri, null, null, null, null);
        }

        return  cursor_MA;

    }


    public Cursor getSeccionIN(){
        SharedPreferences pref_IN = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
        String caseFallas_IN = pref_IN.getString("pref_falla_IN", "N_IN");

        switch (caseFallas_IN) {
            // FALLAS INFANTILES
            // Todas
            case "T_IN":
                break;
            // FALLA ESPECIAL
            case "IN_E":
                query = "seccion_in = 'ESPECIAL'";
                break;
            // Seccion 1
            case "1":
                query = "seccion_in = '1'";
                break;
            case "2":
                query = "seccion_in = '2'";
                break;
            case "3":
                query = "seccion_in = '3'";
                break;
            case "4":
                query = "seccion_in = '4'";
                break;
            case "5":
                query = "seccion_in = '5'";
                break;
            case "6":
                query = "seccion_in = '6'";
                break;
            case "7":
                query = "seccion_in = '7'";
                break;
            case "8":
                query = "seccion_in = '8'";
                break;
            case "9":
                query = "seccion_in = '9'";
                break;
            case "10":
                query = "seccion_in = '10'";
                break;
            case "11":
                query = "seccion_in = '11'";
                break;
            case "12":
                query = "seccion_in = '12'";
                break;
            case "13":
                query = "seccion_in = '13'";
                break;
            case "14":
                query = "seccion_in = '14'";
                break;
            case "15":
                query = "seccion_in = '15'";
                break;
            case "16":
                query = "seccion_in = '16'";
                break;
            case "17":
                query = "seccion_in = '17'";
                break;
            case "18":
                query = "seccion_in = '18'";
                break;
            // Ninguna falla
            case "N_IN":
                query = "ninguna";
                break;

        }
        if (query != null && query != "ninguna") {
            cursor_IN = mContext.getContentResolver().query(uri, null, query, null, null);
        }else if (query == "ninguna") {
            cursor_IN = null;
        }else if (query == null){
            cursor_IN = mContext.getContentResolver().query(uri, null, null, null, null);
        }

        return cursor_IN;
    }

}

