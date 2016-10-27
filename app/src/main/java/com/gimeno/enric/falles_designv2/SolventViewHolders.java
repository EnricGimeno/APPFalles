package com.gimeno.enric.falles_designv2;



import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SolventViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView fallaName;
    public TextView fallaLema;
    public ImageView fallaPhoto;
    private final Context context;
    private List<FallaObject> itemList;

    public SolventViewHolders(View itemView, List<FallaObject> itemList) {
        super(itemView);
        itemView.setOnClickListener(this);

        this.itemList = itemList;
        context = itemView.getContext();
        fallaName = (TextView) itemView.findViewById(R.id.falla_name);
        fallaLema = (TextView) itemView.findViewById(R.id.falla_lema);
        fallaPhoto = (ImageView) itemView.findViewById(R.id.falla_photo);
    }
    // Cuando se pulse en uno de los bocetos pasamos la informacion correspodinte a la actividad
    // y la arrancamos
    @Override
    public void onClick(View view) {

        Intent intent_detailFalla = new Intent(context, DetailFallaView.class);
        intent_detailFalla.putExtra("nombre", itemList.get(getAdapterPosition()).getName());
        intent_detailFalla.putExtra("seccion", itemList.get(getAdapterPosition()).getSeccion());
        intent_detailFalla.putExtra("fallera", itemList.get(getAdapterPosition()).getFallera());
        intent_detailFalla.putExtra("presidente", itemList.get(getAdapterPosition()).getPresidente());
        intent_detailFalla.putExtra("artista", itemList.get(getAdapterPosition()).getArtista());
        intent_detailFalla.putExtra("lema", itemList.get(getAdapterPosition()).getLema());
        intent_detailFalla.putExtra("boceto", itemList.get(getAdapterPosition()).getPhotoURL());
        intent_detailFalla.putExtra("latitud", itemList.get(getAdapterPosition()).getLatitud());
        intent_detailFalla.putExtra("longitud", itemList.get(getAdapterPosition()).getLongitud());

        // Arrancamos la actividad detalle
        context.startActivity(intent_detailFalla);

    }
}
