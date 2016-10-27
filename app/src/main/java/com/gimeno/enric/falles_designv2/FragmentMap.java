package com.gimeno.enric.falles_designv2;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

// Fragment que muestra en un mapa las fallas
public class FragmentMap extends Fragment implements  OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    SupportMapFragment mapFragment;

    // Variable view
    private static View rootView;
    // Referencia a la uru de la BD
    private  static Uri uri = Uri.parse("content://es.enric.fallas/fallas/*");

    // Definicion de la constante query
    Cursor cursor_MA;
    Cursor cursor_IN;

    public FragmentMap() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GetCursorSeccion seccion = new GetCursorSeccion(getContext());
        cursor_MA = seccion.getSeccionMA();
        cursor_IN = seccion.getSeccionIN();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflar el layout para el fragment
        try {
            rootView =  inflater.inflate(R.layout.fragment_map, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }

        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtenemos el SupportMapFragment y notificamos cuando el mapa esta listo para usarse
        mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.mGoogleMap = googleMap;
        // Posicion de Valencia
        LatLng valencia = new LatLng(39.474054, -0.376998);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(valencia).zoom(12).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        //mGoogleMap.setMyLocationEnabled(true);

        // Agregamos un marker para la mascleta
        LatLng mascleta = new LatLng(39.469773, -0.376329);
        mGoogleMap.addMarker(new MarkerOptions().position(mascleta)
                .title(getString(R.string.markerMascletaTitle))
                .snippet(getString(R.string.markerMascletaSnippet))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_rocket)));


        // Agregamos un marker para los fuegos artificiales
        LatLng fugArtificiales = new LatLng(39.464000, -0.360033);
        mGoogleMap.addMarker(new MarkerOptions().position(fugArtificiales)
                .title(getString(R.string.markerFocsTitle))
                .snippet(getString(R.string.markerFocsSnippet))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_firework)));

        // Agregamos un marker para la ofrenda
        LatLng ofrenda = new LatLng(39.4763347, -0.3754491);
        mGoogleMap.addMarker(new MarkerOptions().position(ofrenda)
                        .title(getString(R.string.markerOrenfaTitle))
                        .snippet(getString(R.string.markerOfrendaSnippet))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_bouquet)));


        // Añadimos las fallas Mayores
        if (cursor_MA != null){
            if (cursor_MA.moveToFirst()){
                while(!cursor_MA.isAfterLast()){
                    LatLng latLng = new LatLng(cursor_MA.getDouble(cursor_MA.getColumnIndex("latitud")),
                            cursor_MA.getDouble(cursor_MA.getColumnIndex("longitud")));

                    mGoogleMap.addMarker(new MarkerOptions().position(latLng)
                            .title(cursor_MA.getString(cursor_MA.getColumnIndex("nombre")))
                            .snippet(getString(R.string.markerArtist) + " " + cursor_MA.getString(cursor_MA.getColumnIndex("artista_ma")))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_falla_border_blue)));

                    cursor_MA.moveToNext();

                }

            }
            //cursor_MA.close();

        }


        // Añadimos las fallas Infantiles
        if (cursor_IN != null){
            if (cursor_IN.moveToFirst()){
                while(!cursor_IN.isAfterLast()){
                    LatLng latLng = new LatLng(cursor_IN.getDouble(cursor_IN.getColumnIndex("latitud")),
                            cursor_IN.getDouble(cursor_IN.getColumnIndex("longitud")));
                    mGoogleMap.addMarker(new MarkerOptions().position(latLng)
                            .title(cursor_IN.getString(cursor_IN.getColumnIndex("nombre")))
                            .snippet(getString(R.string.markerArtist) + " " + cursor_IN.getString(cursor_IN.getColumnIndex("artista_in")))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_falla_border_green)));


                    cursor_IN.moveToNext();

                }

            }
            //cursor_IN.close();
        }


    }


}