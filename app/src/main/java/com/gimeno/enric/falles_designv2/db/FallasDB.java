package com.gimeno.enric.falles_designv2.db;

import android.net.Uri;
import android.provider.BaseColumns;


public class FallasDB {

    /*
	 * Espacio de nombres (se debe usar el mismo para definir el content provider
	 * en el Manifest
	 */
    public static final String AUTHORITY = "es.enric.fallas";

    /*
	 * Nombre de la base de datos
	 */
    public static final String DB_NAME = "fallas.db";

    /*
     * Version de la base de datos
     */
    public static final int DB_VERSION = 1;


    /**
     * Esta clase no debe ser instanciada
     */
    private FallasDB() {
    }

    /* Definicion de la tabla fallas */
    public static final class Fallas implements BaseColumns {

        /**
         * Esta clase no debe ser instanciada
         */
        private Fallas() {}

        /**
         *  content:// estilo URL para esta tabla
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/fallas");


        /**
         * orden por defecto
         */
        public static final String DEFAULT_SORT_ORDER = "_ID DESC";

        /**
         * Abstraccion de los nombres de campos y tabla a constantes
         * para facilitar cambios en la estructura interna de la BD
         */
        public static final String NOMBRE_TABLA = "fallas";

        //public static final String _ID = "_id";
        public static final String CAMPO_NOMBRE = "nombre";
        public static final String CAMPO_SECCION_MA = "seccion_ma";
        public static final String CAMPO_FALLERA_MA = "fallera_ma";
        public static final String CAMPO_PRESIDENTE_MA = "presidente_ma";
        public static final String CAMPO_ARTISTA_MA = "artista_ma";
        public static final String CAMPO_LEMA_MA = "lema_ma";
        public static final String CAMPO_URL_BOCETO_MA = "boceto_ma";
        public static final String CAMPO_HORA_CREMA_MA = "hora_crema_ma";
        public static final String CAMPO_LATITUD = "latitud";
        public static final String CAMPO_LONGITUD = "longitud";
        public static final String CAMPO_SECCION_IN = "seccion_in";
        public static final String CAMPO_FALLERA_IN  = "fallera_in";
        public static final String CAMPO_PRESIDENTE_IN  = "presidente_in";
        public static final String CAMPO_ARTISTA_IN  = "artista_in";
        public static final String CAMPO_LEMA_IN  = "lema_in";
        public static final String CAMPO_URL_BOCETO_IN  = "boceto_in";



    }
}
