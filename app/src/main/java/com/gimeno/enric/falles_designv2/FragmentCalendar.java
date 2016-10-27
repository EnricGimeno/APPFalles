package com.gimeno.enric.falles_designv2;


import android.content.ContentValues;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.tyczj.extendedcalendarview.CalendarProvider;
import com.tyczj.extendedcalendarview.Day;
import com.tyczj.extendedcalendarview.Event;
import com.tyczj.extendedcalendarview.ExtendedCalendarView;
import java.util.ArrayList;
import java.util.Calendar;


// Este fragment mostrara un calendario
// Para el calendario hacemos uso de la libreria ExtendedCalendarView
// https://github.com/tyczj/ExtendedCalendarView
public class FragmentCalendar extends ListFragment {

    private static View rootView;
    ExtendedCalendarView extendedCalendar;
    ListView listView;
    SharedPreferences idiomaCalendario;

    public FragmentCalendar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflamos el layout para este fragment
        try {
            rootView =  inflater.inflate(R.layout.fragment_calendar, container, false);
        } catch (InflateException e) {

        }
        // Instancia al calendario
        extendedCalendar = (ExtendedCalendarView) rootView.findViewById(R.id.extendedCalendarView);

        // Para desplazar el calendario con un gesto izquierda o derecha
        extendedCalendar.setGesture(ExtendedCalendarView.LEFT_RIGHT_GESTURE);


        idiomaCalendario = PreferenceManager.getDefaultSharedPreferences(getActivity());


        // Cuando el idioma cambie borraremos los eventos y los volveremos a añadir
        if (idiomaCalendario.getBoolean("idiomaHaCambiado", true)){
            getActivity().getContentResolver().delete(CalendarProvider.CONTENT_URI, null,null);
            // Funcion que crea los eventos
            createEventsCalendar();
            // cambiamos las preferencias
            SharedPreferences idiomaHaCambiado = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = idiomaHaCambiado.edit();
            editor.putBoolean("idiomaHaCambiado", false);
            editor.commit();


        }

        //getActivity().getContentResolver().delete(CalendarProvider.CONTENT_URI, null,null);
        // Funcion que al pulsar en un dia muestra los eventos del dia
        initListener();

        return rootView;

    }



    private void initListener() {
        extendedCalendar.setOnDayClickListener(new ExtendedCalendarView.OnDayClickListener() {
            @Override
            public void onDayClicked(AdapterView<?> adapter, View view, int position, long id, Day day) {
                ArrayList<EventCalendario> event = new ArrayList<EventCalendario>();
                for (Event e : day.getEvents()) {
                    event.add(new EventCalendario(e.getDescription(), e.getLocation(), e.getTitle()));
                }
                // Añadimos los eventos a la lista de debajo del mapa
                CustomList adapterList = new CustomList(getActivity(),event);
                setListAdapter(adapterList);

            }
        });

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Cuando la vista ha sido creado eliminamos los separadores entre la listView
        listView = getListView();
        listView.setDivider(null);
    }

    // Funcion que contiene los eventos de las FALAS
    private void createEventsCalendar() {
        // Instancia a calendario
        Calendar cal = Calendar.getInstance();

        // CRIDA
        ContentValues valuesCrida= new ContentValues();
        valuesCrida.put(CalendarProvider.COLOR, Event.COLOR_PURPLE);
        valuesCrida.put(CalendarProvider.DESCRIPTION, "CRIDA");
        valuesCrida.put(CalendarProvider.LOCATION, "Torres Serrano");
        valuesCrida.put(CalendarProvider.EVENT, "20:00");

        cal.set(2017, 2, 27, 20, 00);
        int startjulianDayCrida = 2457811;
        valuesCrida.put(CalendarProvider.START, cal.getTimeInMillis());
        valuesCrida.put(CalendarProvider.START_DAY, startjulianDayCrida);

        cal.set(2017, 2, 27, 21, 00);
        int endDayJulianCrida = 2457811;
        valuesCrida.put(CalendarProvider.END, cal.getTimeInMillis());
        valuesCrida.put(CalendarProvider.END_DAY, endDayJulianCrida);

        Uri uriCrida = getActivity().getContentResolver().insert(CalendarProvider.CONTENT_URI, valuesCrida);

        // PLANTA FALLA INFATILES
        ContentValues valuesPlantaInfatil= new ContentValues();
        valuesPlantaInfatil.put(CalendarProvider.COLOR, Event.COLOR_PURPLE);
        valuesPlantaInfatil.put(CalendarProvider.DESCRIPTION, getString(R.string.plantaFallasInfantilesDescription));
        valuesPlantaInfatil.put(CalendarProvider.LOCATION, "");
        valuesPlantaInfatil.put(CalendarProvider.EVENT, getString(R.string.plantaFallasInfantilesEvent));

        cal.set(2017, 3, 15, 8, 00);
        int startjulianDayPlantaInfatil = 2457828;
        valuesPlantaInfatil.put(CalendarProvider.START, cal.getTimeInMillis());
        valuesPlantaInfatil.put(CalendarProvider.START_DAY, startjulianDayPlantaInfatil);

        cal.set(2017, 3, 15, 9, 00);
        int endDayJulianPlantaInfatil = 2457828;
        valuesPlantaInfatil.put(CalendarProvider.END, cal.getTimeInMillis());
        valuesPlantaInfatil.put(CalendarProvider.END_DAY, endDayJulianPlantaInfatil);

        Uri uriPlantaInfatil = getActivity().getContentResolver().insert(CalendarProvider.CONTENT_URI, valuesPlantaInfatil);

        // PLANTA FALLAS MAYORES
        ContentValues valuesPlantaMajor= new ContentValues();
        valuesPlantaMajor.put(CalendarProvider.COLOR, Event.COLOR_PURPLE);
        valuesPlantaMajor.put(CalendarProvider.DESCRIPTION, getString(R.string.plantaFallasMayoresDescription));
        valuesPlantaMajor.put(CalendarProvider.LOCATION, "");
        valuesPlantaMajor.put(CalendarProvider.EVENT, getString(R.string.plantaFallaMayoresEvent));

        cal.set(2017, 3, 16, 8, 00);
        int startjulianDayPlantaMajor = 2457829;
        valuesPlantaMajor.put(CalendarProvider.START, cal.getTimeInMillis());
        valuesPlantaMajor.put(CalendarProvider.START_DAY, startjulianDayPlantaMajor);

        cal.set(2017, 3, 16, 9, 00);
        int endDayJulianPlantaMajor = 2457829;
        valuesPlantaMajor.put(CalendarProvider.END, cal.getTimeInMillis());
        valuesPlantaMajor.put(CalendarProvider.END_DAY, endDayJulianPlantaMajor);

        Uri uriPlantaMajor = getActivity().getContentResolver().insert(CalendarProvider.CONTENT_URI, valuesPlantaMajor);

        // OFRENA
        ContentValues valuesOfrena= new ContentValues();
        valuesOfrena.put(CalendarProvider.COLOR, Event.COLOR_GREEN);
        valuesOfrena.put(CalendarProvider.DESCRIPTION, getString(R.string.ofrendaDescription));
        valuesOfrena.put(CalendarProvider.LOCATION, "Plaça de la Mare de Déu ");
        valuesOfrena.put(CalendarProvider.EVENT, "15:30 - 24:00");

        cal.set(2017, 3, 17, 15, 30);
        int startjulianDayOfrena = 2457830;
        valuesOfrena.put(CalendarProvider.START, cal.getTimeInMillis());
        valuesOfrena.put(CalendarProvider.START_DAY, startjulianDayOfrena);

        cal.set(2017, 3, 18, 23, 00);
        int endDayJulianOfrena = 2457831;
        valuesOfrena.put(CalendarProvider.END, cal.getTimeInMillis());
        valuesOfrena.put(CalendarProvider.END_DAY, endDayJulianOfrena);

        Uri uriOfrena = getActivity().getContentResolver().insert(CalendarProvider.CONTENT_URI, valuesOfrena);


        // MASCLETA
        ContentValues valuesMascleta= new ContentValues();
        valuesMascleta.put(CalendarProvider.COLOR, Event.COLOR_YELLOW);
        valuesMascleta.put(CalendarProvider.DESCRIPTION, getString(R.string.mascletaDescription));
        valuesMascleta.put(CalendarProvider.LOCATION, "Plaça Ajuntament");
        valuesMascleta.put(CalendarProvider.EVENT, "14:00");

        cal.set(2017, 3, 1, 14, 00);
        int startjulianDayMascleta = 2457814;
        valuesMascleta.put(CalendarProvider.START, cal.getTimeInMillis());
        valuesMascleta.put(CalendarProvider.START_DAY, startjulianDayMascleta);

        cal.set(2017, 3, 19, 14, 05);
        int endDayJulianMascleta = 2457832;
        valuesMascleta.put(CalendarProvider.END, cal.getTimeInMillis());
        valuesMascleta.put(CalendarProvider.END_DAY, endDayJulianMascleta);

        Uri uriMascleta = getActivity().getContentResolver().insert(CalendarProvider.CONTENT_URI, valuesMascleta);

        // CASTELL DE FOCS 15
        ContentValues valuesCastell15= new ContentValues();
        valuesCastell15.put(CalendarProvider.COLOR, Event.COLOR_BLUE);
        valuesCastell15.put(CalendarProvider.DESCRIPTION, getString(R.string.castellDescription));
        valuesCastell15.put(CalendarProvider.LOCATION, "Passeig de l'Alameda - Pont de les flors");
        valuesCastell15.put(CalendarProvider.EVENT, "00:00");

        cal.set(2017, 3, 15, 00, 00);
        int startjulianDayCastell15 = 2457828;
        valuesCastell15.put(CalendarProvider.START, cal.getTimeInMillis());
        valuesCastell15.put(CalendarProvider.START_DAY, startjulianDayCastell15);

        cal.set(2017, 3, 15, 00, 00);
        int endDayJuliaCastell15= 2457828;
        valuesCastell15.put(CalendarProvider.END, cal.getTimeInMillis());
        valuesCastell15.put(CalendarProvider.END_DAY, endDayJuliaCastell15);

        Uri uriCastell15= getActivity().getContentResolver().insert(CalendarProvider.CONTENT_URI, valuesCastell15);


        // CASTELL DE FOCS 16 y 17
        ContentValues valuesCastell= new ContentValues();
        valuesCastell.put(CalendarProvider.COLOR, Event.COLOR_BLUE);
        valuesCastell.put(CalendarProvider.DESCRIPTION, getString(R.string.castellDescription));
        valuesCastell.put(CalendarProvider.LOCATION, "Passeig de l'Alameda - Pont de les flors");
        valuesCastell.put(CalendarProvider.EVENT, "1:00");

        cal.set(2017, 3, 16, 00, 00);
        int startjulianDayCastell = 2457829;
        valuesCastell.put(CalendarProvider.START, cal.getTimeInMillis());
        valuesCastell.put(CalendarProvider.START_DAY, startjulianDayCastell);

        cal.set(2017, 3, 17, 00, 00);
        int endDayJuliaCastell= 2457830;
        valuesCastell.put(CalendarProvider.END, cal.getTimeInMillis());
        valuesCastell.put(CalendarProvider.END_DAY, endDayJuliaCastell);

        Uri uriCastell= getActivity().getContentResolver().insert(CalendarProvider.CONTENT_URI, valuesCastell);

        // NIT DEL FOC
        ContentValues valuesNitFoc= new ContentValues();
        valuesNitFoc.put(CalendarProvider.COLOR, Event.COLOR_BLUE);
        valuesNitFoc.put(CalendarProvider.DESCRIPTION, getString(R.string.nitDelFocDescription));
        valuesNitFoc.put(CalendarProvider.LOCATION, "Passeig de l'Alameda - Pont de les flors");
        valuesNitFoc.put(CalendarProvider.EVENT, "01:30");

        cal.set(2017, 3, 18, 00, 00);
        int startjulianDayNitFoc = 2457831;
        valuesNitFoc.put(CalendarProvider.START, cal.getTimeInMillis());
        valuesNitFoc.put(CalendarProvider.START_DAY, startjulianDayNitFoc);

        cal.set(2017, 3, 18, 00, 00);
        int endDayJuliaNitFoc= 2457831;
        valuesNitFoc.put(CalendarProvider.END, cal.getTimeInMillis());
        valuesNitFoc.put(CalendarProvider.END_DAY, endDayJuliaNitFoc);

        Uri urNitFoc= getActivity().getContentResolver().insert(CalendarProvider.CONTENT_URI, valuesNitFoc);


        // CREMA FALLES INFANTILS
        ContentValues valuesFallaInfantil= new ContentValues();
        valuesFallaInfantil.put(CalendarProvider.COLOR, Event.COLOR_RED);
        valuesFallaInfantil.put(CalendarProvider.DESCRIPTION, getString(R.string.cremaFallesInfantilDescription));
        valuesFallaInfantil.put(CalendarProvider.LOCATION, getString(R.string.cremaFallesInfantilLocation));
        valuesFallaInfantil.put(CalendarProvider.EVENT, "22:00");

        cal.set(2017, 3, 19, 22, 00);
        int startjulianDayFallaInfantil = 2457832;
        valuesFallaInfantil.put(CalendarProvider.START, cal.getTimeInMillis());
        valuesFallaInfantil.put(CalendarProvider.START_DAY, startjulianDayFallaInfantil);

        cal.set(2017, 3, 19, 22, 05);
        int endDayJuliaFallaInfantil= 2457832;
        valuesFallaInfantil.put(CalendarProvider.END, cal.getTimeInMillis());
        valuesFallaInfantil.put(CalendarProvider.END_DAY, endDayJuliaFallaInfantil);

        Uri uriFallaInfantil= getActivity().getContentResolver().insert(CalendarProvider.CONTENT_URI, valuesFallaInfantil);

        // CREMA FALLA ESPECIAL INFANTIL
        ContentValues valuesFallaEspInfantil= new ContentValues();
        valuesFallaEspInfantil.put(CalendarProvider.COLOR, Event.COLOR_RED);
        valuesFallaEspInfantil.put(CalendarProvider.DESCRIPTION, getString(R.string.cremaFallesInfantilEspDescription));
        valuesFallaEspInfantil.put(CalendarProvider.LOCATION, getString(R.string.cremaFallesInfantilEspLocation));
        valuesFallaEspInfantil.put(CalendarProvider.EVENT, "22:30");

        cal.set(2017, 3, 19, 22, 30);
        int startjulianDayFallaEspInfantil= 2457832;
        valuesFallaEspInfantil.put(CalendarProvider.START, cal.getTimeInMillis());
        valuesFallaEspInfantil.put(CalendarProvider.START_DAY, startjulianDayFallaEspInfantil);

        cal.set(2017, 3, 19, 22, 35);
        int endDayJuliaFallaEspInfantil = 2457832;
        valuesFallaEspInfantil.put(CalendarProvider.END, cal.getTimeInMillis());
        valuesFallaEspInfantil.put(CalendarProvider.END_DAY, endDayJuliaFallaEspInfantil);

        Uri uriFallaEspInfantil= getActivity().getContentResolver().insert(CalendarProvider.CONTENT_URI, valuesFallaEspInfantil);

        // CREMA FALLA AJUNTAMENT INFANTIL
        ContentValues valuesFallaAjunInfantil= new ContentValues();
        valuesFallaAjunInfantil.put(CalendarProvider.COLOR, Event.COLOR_RED);
        valuesFallaAjunInfantil.put(CalendarProvider.DESCRIPTION, getString(R.string.cremaFallaAjunInfantilDescription));
        valuesFallaAjunInfantil.put(CalendarProvider.LOCATION, "Plaça Ajuntament");
        valuesFallaAjunInfantil.put(CalendarProvider.EVENT, "23:00");

        cal.set(2017, 3, 19, 23, 00);
        int startjulianDayFallaAjunInfantil= 2457832;
        valuesFallaAjunInfantil.put(CalendarProvider.START, cal.getTimeInMillis());
        valuesFallaAjunInfantil.put(CalendarProvider.START_DAY, startjulianDayFallaAjunInfantil);

        cal.set(2017, 3, 19, 23, 05);
        int endDayJuliaFallaAjunInfantil = 2457832;
        valuesFallaAjunInfantil.put(CalendarProvider.END, cal.getTimeInMillis());
        valuesFallaAjunInfantil.put(CalendarProvider.END_DAY, endDayJuliaFallaAjunInfantil);

        Uri uriFallaAjunInfantil= getActivity().getContentResolver().insert(CalendarProvider.CONTENT_URI, valuesFallaAjunInfantil);

        // CREMA FALLES MAJORS
        ContentValues valuesFallaMajor= new ContentValues();
        valuesFallaMajor.put(CalendarProvider.COLOR, Event.COLOR_RED);
        valuesFallaMajor.put(CalendarProvider.DESCRIPTION, getString(R.string.cremaFallesMajorsDescription));
        valuesFallaMajor.put(CalendarProvider.LOCATION, getString(R.string.cremaFallesMajorsLocation));
        valuesFallaMajor.put(CalendarProvider.EVENT, "00:00");

        cal.set(2017, 3, 20, 00, 00);
        int startjulianDayFallaMajor= 2457832;
        valuesFallaMajor.put(CalendarProvider.START, cal.getTimeInMillis());
        valuesFallaMajor.put(CalendarProvider.START_DAY, startjulianDayFallaMajor);

        cal.set(2017, 3, 20, 00, 05);
        int endDayJuliaFallaMajor = 2457832;
        valuesFallaMajor.put(CalendarProvider.END, cal.getTimeInMillis());
        valuesFallaMajor.put(CalendarProvider.END_DAY, endDayJuliaFallaMajor);

        Uri uriFallaMajor= getActivity().getContentResolver().insert(CalendarProvider.CONTENT_URI, valuesFallaMajor);

        // CREMA FALLA ESPECIAL MAJOR
        ContentValues valuesFallaEspMajor= new ContentValues();
        valuesFallaEspMajor.put(CalendarProvider.COLOR, Event.COLOR_RED);
        valuesFallaEspMajor.put(CalendarProvider.DESCRIPTION, getString(R.string.cremaFallesMajorsEspDescription));
        valuesFallaEspMajor.put(CalendarProvider.LOCATION, getString(R.string.cremaFallesMajorsEspLocation));
        valuesFallaEspMajor.put(CalendarProvider.EVENT, "00:30");

        cal.set(2017, 3, 20, 00, 30);
        int startjulianDayFallaEspMajor= 2457833;
        valuesFallaEspMajor.put(CalendarProvider.START, cal.getTimeInMillis());
        valuesFallaEspMajor.put(CalendarProvider.START_DAY, startjulianDayFallaEspMajor);

        cal.set(2017, 3, 20, 00, 35);
        int endDayJuliaFallaEspMajor = 2457833;
        valuesFallaEspMajor.put(CalendarProvider.END, cal.getTimeInMillis());
        valuesFallaEspMajor.put(CalendarProvider.END_DAY, endDayJuliaFallaEspMajor);

        Uri uriFallaEspMajor= getActivity().getContentResolver().insert(CalendarProvider.CONTENT_URI, valuesFallaEspMajor);


        // CREMA FALLA AJUNTAMENT MAJOR
        ContentValues valuesFallaAjunMajor= new ContentValues();
        valuesFallaAjunMajor.put(CalendarProvider.COLOR, Event.COLOR_RED);
        valuesFallaAjunMajor.put(CalendarProvider.DESCRIPTION, getString(R.string.cremaFallaAjunMajorDescription));
        valuesFallaAjunMajor.put(CalendarProvider.LOCATION, "Plaça Ajuntament");
        valuesFallaAjunMajor.put(CalendarProvider.EVENT, "1:00");

        cal.set(2017, 3, 20, 1, 00);
        int startjulianDayFallaAjunMajor= 2457833;
        valuesFallaAjunMajor.put(CalendarProvider.START, cal.getTimeInMillis());
        valuesFallaAjunMajor.put(CalendarProvider.START_DAY, startjulianDayFallaAjunMajor);

        cal.set(2017, 3, 20, 1, 35);
        int endDayJuliaFallaAjunMajor = 2457833;
        valuesFallaAjunMajor.put(CalendarProvider.END, cal.getTimeInMillis());
        valuesFallaAjunMajor.put(CalendarProvider.END_DAY, endDayJuliaFallaAjunMajor);

        Uri uriFallaAjunMajor= getActivity().getContentResolver().insert(CalendarProvider.CONTENT_URI, valuesFallaAjunMajor);

        // Preferencia de idioma del calendario
        SharedPreferences.Editor editor = idiomaCalendario.edit();
        editor.putString("idiomaCalendario", idiomaCalendario.getString("pref_idiom","en"));
        editor.commit();

    }
}