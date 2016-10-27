package com.gimeno.enric.falles_designv2.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FallasSQLHelper extends SQLiteOpenHelper {

    public FallasSQLHelper(Context context) {
        super(context, FallasDB.DB_NAME, null, FallasDB.DB_VERSION);
    }
    // Creacion de la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        if(db.isReadOnly()) { db=getWritableDatabase(); }

        db.execSQL("CREATE TABLE " + FallasDB.Fallas.NOMBRE_TABLA + " (" +
                FallasDB.Fallas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FallasDB.Fallas.CAMPO_NOMBRE + " TEXT," +
                FallasDB.Fallas.CAMPO_SECCION_MA + " TEXT," +
                FallasDB.Fallas.CAMPO_FALLERA_MA + " TEXT," +
                FallasDB.Fallas.CAMPO_PRESIDENTE_MA + " TEXT," +
                FallasDB.Fallas.CAMPO_ARTISTA_MA + " TEXT," +
                FallasDB.Fallas.CAMPO_LEMA_MA + " TEXT," +
                FallasDB.Fallas.CAMPO_URL_BOCETO_MA + " TEXT," +
                FallasDB.Fallas.CAMPO_HORA_CREMA_MA + " TEXT," +
                FallasDB.Fallas.CAMPO_LATITUD + " DOUBLE," +
                FallasDB.Fallas.CAMPO_LONGITUD + " DOUBLE," +
                FallasDB.Fallas.CAMPO_SECCION_IN + " TEXT," +
                FallasDB.Fallas.CAMPO_FALLERA_IN + " TEXT," +
                FallasDB.Fallas.CAMPO_PRESIDENTE_IN + " TEXT," +
                FallasDB.Fallas.CAMPO_ARTISTA_IN + " TEXT," +
                FallasDB.Fallas.CAMPO_LEMA_IN + " TEXT," +
                FallasDB.Fallas.CAMPO_URL_BOCETO_IN + " TEXT" +
                ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Cuando haya cambios en la estuctura deberemos incluir el codigo
        // SQL necesario para actualizar la base de datos
        // tendremos en cuenta la version antigua y la nueva para aplicar solo
        // los necesarios

    }
}
