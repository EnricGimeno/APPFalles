package com.gimeno.enric.falles_designv2;



import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import org.cts.CRSFactory;
import org.cts.IllegalCoordinateException;
import org.cts.crs.CRSException;
import org.cts.crs.CoordinateReferenceSystem;
import org.cts.crs.GeodeticCRS;
import org.cts.op.CoordinateOperation;
import org.cts.op.CoordinateOperationFactory;
import org.cts.registry.EPSGRegistry;
import org.cts.registry.RegistryManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

// Asynctask para la descarga de la informacion del JSON
public class DownloadFallasTask extends AsyncTask<String, Void, String> {

    DownloadCompleteListener mDownloadCompleteListener;
    Context context;
    // Ruta de la URI BD
    private static Uri uri = Uri.parse("content://es.enric.fallas/fallas");

    public DownloadFallasTask(DownloadCompleteListener downloadCompleteListener, Context context) {
        this.mDownloadCompleteListener = downloadCompleteListener;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... params) {
        // Ejecutamos la funcion downloadData con la url de la tarea de ejecucion
        try {
            return downloadData(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String jsonString) {

        // Convertimos y guardamos el JSON
        if (jsonString == null) {
            return;
        }

        try {
            JSONObject json = new JSONObject(jsonString);
            JSONArray arrayFeatures = json.getJSONArray("features");

            // Create a new CRSFactory, a necessary element to create a CRS without defining one by one all its components
            CRSFactory cRSFactory = new CRSFactory();

            // En las siguientes lineas se determina las caracteristicas para pasar de coordenadas ETRS89 a geodesicas
            // Para ello usaremos la libreria CTS y que hemos importado mediante un .jar
            // https://github.com/orbisgis/cts
            // Añadimos el registro correspondiente a la CRSFactory's registry manager. EPSG registro es usado
            RegistryManager registryManager = cRSFactory.getRegistryManager();
            registryManager.addRegistry(new EPSGRegistry());


            // CTS leera el registro EPSG codigo, cuando lo encuentre, creara un sistema de coordenadas
            // de referencia usando los parametros que ha encontrado en el registro
            // ETRS89
            CoordinateReferenceSystem crs1 = cRSFactory.getCRS("EPSG:25830");
            GeodeticCRS sourceGCRS = (GeodeticCRS) crs1;
            // WGS84
            CoordinateReferenceSystem crs2 = cRSFactory.getCRS("EPSG:4326");
            GeodeticCRS targetGCRS = (GeodeticCRS) crs2;

            // Se obtiene una lista y no una unica "CoordinateTransformation". porque varios metodos pueden
            // existir para transformar una posicion desde crs1 a crs2
            List<CoordinateOperation> coordOps = CoordinateOperationFactory.createCoordinateOperations(sourceGCRS,targetGCRS);

            // Para cada una de las fallas transformamos el punto a WGS84
            // Paso esencial para poder pintar las fallas en google maps.
            // Google maps utiliza una proyeccion WGS84 en su mapa
            for (int i = 0; i < arrayFeatures.length(); i++) {

                final JSONObject jsonFeatures = arrayFeatures.getJSONObject(i);
                JSONObject jsonProperties = jsonFeatures.getJSONObject("properties");
                JSONObject jsonGeometry = jsonFeatures.getJSONObject("geometry");

                JSONArray arrayGeometry = jsonGeometry.getJSONArray("coordinates");
                Double X_ETRS89 = (Double) arrayGeometry.get(0);
                Double Y_ETRS89 = (Double) arrayGeometry.get(1);

                double[] coord = new double[2];
                coord[0] = X_ETRS89;
                coord[1] = Y_ETRS89;


                if (coordOps.size() != 0) {
                    // Testeo de cada metodo de transformacion (normalmente, un solo metodo esta disponible)
                    // Siendo nuestro caso
                    for (CoordinateOperation op : coordOps) {
                        // Transformacion del punto de crs1 a crs2
                        double[] dd  = op.transform(coord);

                        double latitud = dd[1];
                        double longitud = dd[0];

                        //Uri uri = Uri.parse("content://es.enric.fallas/fallas");
                        ContentValues values = new ContentValues();
                        values.put("nombre", jsonProperties.getString("nombre"));
                        values.put("seccion_ma", jsonProperties.getString("seccion"));
                        values.put("fallera_ma", jsonProperties.getString("fallera"));
                        values.put("presidente_ma", jsonProperties.getString("presidente"));
                        values.put("artista_ma", jsonProperties.getString("artista"));
                        values.put("lema_ma", jsonProperties.getString("lema"));
                        values.put("boceto_ma", jsonProperties.getString("boceto"));
                        values.put("hora_crema_ma", jsonProperties.getString("hora"));
                        values.put("latitud", latitud);
                        values.put("longitud", longitud);
                        values.put("seccion_in", jsonProperties.getString("seccion_i"));
                        values.put("fallera_in", jsonProperties.getString("fallera_i"));
                        values.put("presidente_in", jsonProperties.getString("presidente_i"));
                        values.put("artista_in", jsonProperties.getString("artista_i"));
                        values.put("lema_in", jsonProperties.getString("lema_i"));
                        values.put("boceto_in", jsonProperties.getString("boceto_i"));

                        // Insertamos los valores en la BD
                        context.getContentResolver().insert(uri, values);


                    }
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (CRSException e) {
            e.printStackTrace();
        } catch (IllegalCoordinateException e) {
            e.printStackTrace();
        }


        mDownloadCompleteListener.downloadComplete();

    }

    // Creamos y abrimos una conexion al recurso de la URL
    private String downloadData(String urlString) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            is = conn.getInputStream();
            return convertToString(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }

    }

    // Convertimos la informacion a String
    private String convertToString(InputStream is) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }
        return new String(total);
    }

}
