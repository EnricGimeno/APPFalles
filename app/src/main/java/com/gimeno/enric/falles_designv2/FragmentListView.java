package com.gimeno.enric.falles_designv2;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

// Fragment que muestra los ganadores de los ultimos años
public class FragmentListView extends ListFragment {

    // Variables globales
    ArrayList<HashMap<String, String>> mData = new ArrayList<HashMap<String, String>>();
    private SimpleAdapter mAdapter;
    private static View rootView;

    EditText inputSearch;
    TextView txtViewCargando;

    public FragmentListView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configuracion del simple adapter
        String[] from = {"fallaGanador", "añoGanador"};
        int[] to = {android.R.id.text1, android.R.id.text2};

        mAdapter = new SimpleAdapter(getActivity(), mData, android.R.layout.simple_list_item_2, from, to);
        setListAdapter(mAdapter);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para el fragment
        try {
            rootView = inflater.inflate(R.layout.fragment_listview, container, false);
        } catch (InflateException e) {

        }

        // Referencia a los id del layout
        inputSearch = (EditText) rootView.findViewById(R.id.inputSearch);
        txtViewCargando = (TextView) rootView.findViewById(android.R.id.empty);
        inputSearch.setEnabled(false);

        // Configuracion de la caja de busqueda
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                mAdapter.getFilter().filter(cs);
                txtViewCargando.setText(R.string.cajaBusqueda);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        // Leemos un fichero JSON donde tenemos los ganadores de las fallas
        JSONReader();


        return rootView;
    }


    // Leemos el fichero JSON con los ganadores y lo guardamos en un arrayList
    public void JSONReader() {
        final ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        String json = null;
        try {
            InputStream is = getResources().openRawResource(R.raw.ganadores_historico);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray m_jArry = obj.getJSONArray("ganadores");

            for (int i = 0; i < m_jArry.length(); i++) {
                final HashMap<String, String> ganadores = new HashMap<String, String>();
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                ganadores.put("fallaGanador", getString(R.string.listViewWinner) + " " + jo_inside.getString("ganador_falla"));
                ganadores.put("añoGanador", getString(R.string.listViewYear) + " " + jo_inside.getString("año"));

                result.add(ganadores);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mData.clear();
        mData.addAll(result);

        // Una vez tenemos los datos, mostramos la caja de busqueda
        inputSearch.setEnabled(true);
        mAdapter.notifyDataSetChanged();

    }
}