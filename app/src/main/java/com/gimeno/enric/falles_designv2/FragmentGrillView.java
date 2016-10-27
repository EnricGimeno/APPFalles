package com.gimeno.enric.falles_designv2;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

// Fragment que muestra los bocetos de las fallas en una "GridView"
public class FragmentGrillView extends Fragment {

    private static View rootView;

    // Definicion de la constante query
    Cursor cursor_MA;
    Cursor cursor_IN;

    private StaggeredGridLayoutManager gaggeredGridLayoutManager;

    public FragmentGrillView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflar el layout para este fragment
        try {
            rootView =  inflater.inflate(R.layout.fragment_grillview, container, false);
        } catch (InflateException e) {

        }

        // Llamamos a la funcion getSeccion que tenemos en otro fichero
        GetCursorSeccion seccion = new GetCursorSeccion(getContext());
        cursor_MA = seccion.getSeccionMA();
        cursor_IN = seccion.getSeccionIN();

        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        TextView txtData = (TextView)rootView.findViewById(R.id.empty_view);

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        List<FallaObject> gaggeredList = getListItemData(cursor_MA, cursor_IN);

        SolventRecyclerViewAdapter rcAdapter = new SolventRecyclerViewAdapter(getContext(), gaggeredList);
        if (rcAdapter.getItemCount() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            txtData.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            txtData.setVisibility(View.VISIBLE);
        }
        recyclerView.setAdapter(rcAdapter);

        // Cerramos los cursores
        if (cursor_MA != null && cursor_MA.getCount() > 0) {
            cursor_MA.close();

        }
        if (cursor_IN != null && cursor_IN.getCount() > 0) {
            cursor_IN.close();

        }

        return rootView;
    }




    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private List<FallaObject> getListItemData(Cursor cursor_ma, Cursor cursor_in) {
        List<FallaObject> listViewItems = new ArrayList<FallaObject>();

        // Fallas MAYOR
        if (cursor_ma != null) {
            if (cursor_ma.moveToFirst()){
                while(!cursor_ma.isAfterLast()){
                    listViewItems.add( new FallaObject(cursor_ma.getString(cursor_ma.getColumnIndex("nombre")),
                            cursor_ma.getString(cursor_ma.getColumnIndex("seccion_ma")),
                            cursor_ma.getString(cursor_ma.getColumnIndex("fallera_ma")),
                            cursor_ma.getString(cursor_ma.getColumnIndex("presidente_ma")),
                            cursor_ma.getString(cursor_ma.getColumnIndex("artista_ma")),
                            '"' + cursor_ma.getString(cursor_ma.getColumnIndex("lema_ma")) + '"',
                            cursor_ma.getString(cursor_ma.getColumnIndex("boceto_ma")),
                            cursor_ma.getDouble(cursor_ma.getColumnIndex("latitud")),
                            cursor_ma.getDouble(cursor_ma.getColumnIndex("longitud"))));
                    cursor_ma.moveToNext();
                }
            }
            cursor_ma.close();
        }

        if (cursor_in != null){
            if (cursor_in.moveToFirst()){
                while(!cursor_in.isAfterLast()){
                    listViewItems.add( new FallaObject(cursor_in.getString(cursor_in.getColumnIndex("nombre")),
                            cursor_in.getString(cursor_in.getColumnIndex("seccion_in")),
                            cursor_in.getString(cursor_in.getColumnIndex("fallera_in")),
                            cursor_in.getString(cursor_in.getColumnIndex("presidente_in")),
                            cursor_in.getString(cursor_in.getColumnIndex("artista_in")),
                            '"' + cursor_in.getString(cursor_in.getColumnIndex("lema_in")) + '"',
                            cursor_in.getString(cursor_in.getColumnIndex("boceto_in")),
                            cursor_in.getDouble(cursor_in.getColumnIndex("latitud")),
                            cursor_in.getDouble(cursor_in.getColumnIndex("longitud"))));
                    cursor_in.moveToNext();
                }
            }
            cursor_in.close();

        }

        return  listViewItems;

    }

}