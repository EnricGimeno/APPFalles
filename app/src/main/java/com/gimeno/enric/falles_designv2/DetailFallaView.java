package com.gimeno.enric.falles_designv2;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.text.DecimalFormat;
import java.text.ParseException;

// Actividad detalle que se lanzara al pulsar sobre el boceto de la falla. En la galeria
public class DetailFallaView extends AppCompatActivity {

    private ImageView fallaImagen;
    private Toolbar toolbar;
    private TextView txtName, txtLema, txtFallera, txtPresidente, txtArtista, txtAddress, txtSection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view_falla);

        // Load toolbar
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Boton flecha
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Acceso a los elementos del layout
        fallaImagen = (ImageView) findViewById(R.id.falla_imagen);
        txtName = (TextView) findViewById(R.id.txtName);
        txtLema = (TextView) findViewById(R.id.txtLema);
        txtFallera = (TextView) findViewById(R.id.txtFallera);
        txtArtista = (TextView) findViewById(R.id.txtArtista);
        txtPresidente = (TextView) findViewById(R.id.txtPresident);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtSection = (TextView) findViewById(R.id.txtSection);

        try {
            // Obtenemos el id del lugar que hay que mostrar
            // Esto se nos para al pulsar el boceto
            Bundle extras = getIntent().getExtras();
            txtName.setText(extras.getString("nombre"));
            txtLema.setText(extras.getString("lema"));
            txtFallera.setText(extras.getString("fallera"));
            txtArtista.setText(extras.getString("artista"));
            txtPresidente.setText(extras.getString("presidente"));
            txtSection.setText(extras.getString("seccion"));

            String lat = roundFourDecimals(extras.getDouble("latitud"));
            String lon = roundFourDecimals(extras.getDouble("longitud"));


            txtAddress.setText(lat + " , " + lon);


            // Mostramos la imagen (boceto). Empleamos la libreria Picasso
            Picasso.with(getApplicationContext()).load(extras.getString("boceto")).into(fallaImagen);

        }catch (Exception e) {
            e.printStackTrace();
        }


    }

    // Funcion para redondear a 4 decimales las coordenadas
    private String roundFourDecimals(double d) throws ParseException {

        DecimalFormat twoDForm = new DecimalFormat("#.####");
        String str = twoDForm.format(d);

        return str;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            // Acccion del boton flecha
            case android.R.id.home:
                this.finish();
        }
        return  true;
    }


}
