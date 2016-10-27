package com.gimeno.enric.falles_designv2;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

public class SolventRecyclerViewAdapter extends RecyclerView.Adapter<SolventViewHolders> {

    private List<FallaObject> itemList;
    private Context context;

    public SolventRecyclerViewAdapter(Context context, List<FallaObject> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public SolventViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.solvent_list, null);
        SolventViewHolders rcv = new SolventViewHolders(layoutView,itemList);
        return rcv;
    }

    @Override
    public void onBindViewHolder(SolventViewHolders holder, int position) {
        holder.fallaName.setText(itemList.get(position).getName());
        holder.fallaLema.setText(itemList.get(position).getLema());
        Picasso.with(context).load(itemList.get(position).getPhotoURL()).into(holder.fallaPhoto);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
