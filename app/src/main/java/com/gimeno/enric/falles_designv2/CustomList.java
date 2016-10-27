package com.gimeno.enric.falles_designv2;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

// Clase para mostrar la lista de eventos
public class CustomList extends BaseAdapter {

    private ArrayList<EventCalendario> listadoCalendario;
    private LayoutInflater lInflater;

    public CustomList(Context context, ArrayList<EventCalendario> eventos) {
        this.lInflater = LayoutInflater.from(context);
        this.listadoCalendario = eventos;
    }

    @Override
    public int getCount() { return listadoCalendario.size(); }

    @Override
    public Object getItem(int arg0) { return listadoCalendario.get(arg0); }

    @Override
    public long getItemId(int arg0) { return arg0; }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        ContenedorView contenedor = null;

        if (arg1 == null){
            arg1 = lInflater.inflate(R.layout.list_layout, null);

            contenedor = new ContenedorView();
            contenedor.nombreEvent = (TextView) arg1.findViewById(R.id.listNombre);
            contenedor.lugarEvent= (TextView) arg1.findViewById(R.id.listLugar);
            contenedor.horaEvent = (TextView) arg1.findViewById(R.id.listHora);


            arg1.setTag(contenedor);
        } else
            contenedor = (ContenedorView) arg1.getTag();

        EventCalendario versiones = (EventCalendario) getItem(arg0);
        contenedor.nombreEvent.setText(versiones.getNombre());
        contenedor.lugarEvent.setText(versiones.getLugar());
        contenedor.horaEvent.setText(versiones.getHora());

        return arg1;
    }

    class ContenedorView{
        TextView nombreEvent;
        TextView lugarEvent;
        TextView horaEvent;
    }
}