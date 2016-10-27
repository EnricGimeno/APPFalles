package com.gimeno.enric.falles_designv2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



public class MainActivity extends AppCompatActivity implements DownloadCompleteListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    // IDIOMAS
    String idiomaUsuario = "";

    // FRAGMENTS
    private FragmentMap m1stFragment;
    private FragmentListView m2stFragment;
    private FragmentCalendar m3stFragment;
    private FragmentGrillView m4stFragment;
    // URL JSON
    private static String url_JSON = "http://mapas.valencia.es/lanzadera/opendata/Monumentos_falleros/JSON";

    // ICONOS TAB BAR
    private int[] tabIcons = {
            R.drawable.ic_globe_white,
            R.drawable.ic_list,
            R.drawable.ic_calendar,
            R.drawable.ic_grillview
    };

    private static final int DIALOG_ABOUT = 0;
    private static final int RESULT_SETTINGS = 1;

    // Proceso de dialogo para chequear conexion a internet
    ProgressDialog mProgressDialog;

    Cursor cursor;
    // URI para la BD
    private  static Uri uri = Uri.parse("content://es.enric.fallas/fallas/*");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Vemos si la base de datos tiene datos
        cursor = getContentResolver().query(uri, null, null, null,null);

        // Si ya hay datos no hagas nada
        if (cursor.getCount()>0){

            cursor.close();

        }else{
            cursor.close();
            // Comprueba la conexion a internet y muestra la una alerta
            if (isNetworkConnected()) {
                // Progress dialogo para la descarga
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage(getString(R.string.messageProgressDialog));
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

                // Comienza la descarga
                startDownload();


            } else {
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.titleAlert))
                        .setMessage(getString(R.string.messageAlert))
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert).show();
            }

        }

        // Carga toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Carga ViewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        // Asignacion del ViewPager al TabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        // Carga Iconos
        setupTabIcons();

        // Especificamos el idioma. La primera vez que se arranque sera en ingles
        SharedPreferences p = PreferenceManager
                .getDefaultSharedPreferences(this);
        //String idiomaUsuario = p.getString("pref_idiom", "en");
        idiomaUsuario = p.getString("pref_idiom", "en");
        setLocale(idiomaUsuario);

    }

    private void startDownload() {
        // Esto ejecutara la AsynTask con la url pasada
        new DownloadFallasTask(this,this).execute(url_JSON);
    }

    // Funcion que se ejecuta cuando la descarga ha sido finalizada
    public void downloadComplete() {
        // Eliminamos la barra de dialogo
        mProgressDialog.dismiss();

    }

    // Funcion que combrueba la conexion a internet
    private boolean isNetworkConnected() {
        // Recupera una instancia de la clase ConnectivityManager del contexto de aplicación actual.
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Recupera una instancia de la clase NetworkInfo que representa la conexión de red actual. Esto será nulo si no hay red disponible.
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // Comprueba si hay una conexión de red disponible y el dispositivo está conectado.
        return networkInfo != null && networkInfo.isConnected();
    }

    /// Iconos
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    // Definimos el numero de "tabs" indicando el fragment correspondiente y nombre del "tab"
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentMap(), "MAP");
        adapter.addFragment(new FragmentListView(), "LIST_VIEW");
        adapter.addFragment(new FragmentCalendar(), "CALENDAR");
        adapter.addFragment(new FragmentGrillView(), "GRILL_VIEW");
        viewPager.setAdapter(adapter);
    }

    // "Custom adapter class" que proporciona el fragmento requerido para cada "view pager"
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            //return mFragmentList.get(position);
            switch (position) {
                case 0:
                    return new FragmentMap();
                case 1:
                    return new FragmentListView();
                case 2:
                    return new FragmentCalendar();
                case 3:
                    return new FragmentGrillView();
                default:
                    // Esto nunca deberia de ocurrir. Siempre cuenta para cada posicion de arriba
                    return null;
            }
        }

        // Para acceder a los fragment
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment createdFragment = (Fragment) super.instantiateItem(container,position);

            switch (position){
                case 0:
                    m1stFragment = (FragmentMap) createdFragment;
                    break;
                case 1:
                    m2stFragment = (FragmentListView) createdFragment;
                    break;
                case 2:
                   m3stFragment = (FragmentCalendar) createdFragment;
                    break;
                case 3:
                    m4stFragment = (FragmentGrillView) createdFragment;
                    break;
            }

            return createdFragment;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        // Añadimos los fragments
        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            // return null si solo queremos mostrar el icono
            return null;
            //return mFragmentTitleList.get(position);
        }

    }

    // Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // "Inflate" el menu. Esto agrega elementos a la barra, si esta presente
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Menu opcion seleccionada
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Action bar items clicks. La action bar creara automaticamente
        // los handle clicks Home/Up button tan largo como se especifique en el
        // android manifest

        switch (item.getItemId()){
            // Actividad de configuracion
            case R.id.action_settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivityForResult(i, RESULT_SETTINGS);

                break;
            // Caso al pulsar el logo
            case R.id.logo:
                //Toast.makeText(this,"Pulsado boton icono", Toast.LENGTH_SHORT).show();
                android.app.FragmentManager fm = getFragmentManager();
                MyDialogFragment dialogFragment = new MyDialogFragment ();
                dialogFragment.setStyle(MyDialogFragment.STYLE_NORMAL, R.style.CustomDialog);
                dialogFragment.show(fm, "Sample Fragment");;

                break;

            default:
                return false;

        }


        return super.onOptionsItemSelected(item);
    }

    // Cambiamos el idioma si se ha producido algun cambio
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SETTINGS:

                //Aplicamos las preferencias del idioma
                PreferenceManager.setDefaultValues(this, R.xml.preference, false);
                SharedPreferences p = PreferenceManager
                        .getDefaultSharedPreferences(this);
                //String idiomaUsuario = p.getString("pref_idiom", "en");
                idiomaUsuario = p.getString("pref_idiom", "en");
                Log.i("IdiomaUsuario", idiomaUsuario);
                String idiomaPrev = p.getString("idiomaCalendario", "en");
                Log.i("IdiomaPrev", idiomaPrev);

                setLocale(idiomaUsuario);

                if (idiomaUsuario.equalsIgnoreCase(idiomaPrev) ){

                }else{
                    SharedPreferences idiomaHaCambiado = PreferenceManager.getDefaultSharedPreferences(this);
                    // Si el idioma ha cambiado deberemos de borrar el calendario y añadir los
                    // Eventos con el nuevo idioma
                    SharedPreferences.Editor editor = idiomaHaCambiado.edit();
                    editor.putBoolean("idiomaHaCambiado", true);
                    editor.commit();

                }

                Intent refresh = getIntent();
                finish();
                startActivity(refresh);

                break;
        }
    }
    // Funcion que pasado un idioma cambiara el uso de nuestro fichero string
    public void setLocale(String lang) {

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());




    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
