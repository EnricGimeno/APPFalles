package com.gimeno.enric.falles_designv2.db;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

public class FallasProvider extends ContentProvider{

    public static final String PROVIDER_NAME = "es.enric.fallas";
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/fallas");

    private static final int FALLAS = 1;
    private static final int FALLA_ID = 2;

    // Creamos el uriMatcher. Esto es el listado de direcciones validas que entiende nuestro proveedor
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "fallas", FALLAS);
        uriMatcher.addURI(PROVIDER_NAME, "fallas/#", FALLA_ID);
    }
    // Declaramos la base de datos. Objeto a traves del cual accedemos a la base de datos
    private SQLiteDatabase fallasDB;


    /*Este método se encarga de inicializar la conexión con la base de datos. Como
  usamos nuestro Helper, si la base de datos no había sido creada, se creará en
  este momento (y si tiene que ser actualizada también lo hará entonces).*/
    @Override
    public boolean onCreate() {
        Context context = getContext();
        FallasSQLHelper dbHelper = new FallasSQLHelper(context);
        fallasDB = dbHelper.getWritableDatabase();
        return (fallasDB == null)? false : true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
        sqlBuilder.setTables(FallasDB.Fallas.NOMBRE_TABLA);

        if (uriMatcher.match(uri) == FALLA_ID) {
            sqlBuilder.appendWhere(FallasDB.Fallas._ID + " = " + uri.getPathSegments().get(1));
        }

        if (sortOrder == null || sortOrder == "") {
            sortOrder = FallasDB.Fallas.DEFAULT_SORT_ORDER;
        }
        /*if (uriMatcher.match(uri) == MA_E) {
            sqlBuilder.appendWhere(FallasDB.Fallas._ID + " = " + uri.getPathSegments().get(1));
            Cursor c = sqlBuilder.que
        }*/

        Cursor c = sqlBuilder.query(fallasDB, projection, selection,
                selectionArgs, null, null, sortOrder);

        // Registramos los cambios para que se enteren nuestros observers
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;

    }


    @Nullable
    @Override
    public String getType(Uri uri) {

        switch (uriMatcher.match(uri)){
            // Conjunto de fallas
            case FALLAS:
                return "vnd.android.cursor.dir/vnd.enric.fallas";
            // Para una unica falla
            case FALLA_ID:
                return "vnd.android.cursor.item/vnd.enric.fallas";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // Añade una nueva falla
        long rowID = fallasDB.insert(FallasDB.Fallas.NOMBRE_TABLA, "", values);

        // si todo ha ido ok devolvemos su Uri
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // No realizaremos nada. Ya que no borraremos datos
        // Cuando cada año se actualizen los datos, borraremos la tabla
        // Ya que los datos viejos no nos sirven
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // No actualizaremos datos. Idem que caso delete
        return 0;
    }
}
