package org.izv.flora;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.izv.flora.model.entity.Flora;

import org.izv.flora.view.EditFloraActivity;

import java.util.List;

public class FloraAdapter extends RecyclerView.Adapter<FloraViewHolder> {
    private List<Flora> floraList;
    private Flora floraItem;
    private Context context;
    private AlertDialog.Builder alert;

    public FloraAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FloraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent, false);
        return new FloraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FloraViewHolder holder, int position) {

        alert = new AlertDialog.Builder(context);

        floraItem = floraList.get(position);


        Picasso.get().load("https://informatica.ieszaidinvergeles.org:10013/florayfauna/public/api/imagen/"+floraItem.getId()+"/flora").into(holder.iFlora);

        holder.tvName.setText(floraItem.getNombre());
        holder.tvFamily.setText(floraItem.getFamilia());
        holder.tvHabitat.setText(floraItem.getHabitat());
        holder.tvAlt.setText(floraItem.getAltitud());

        holder.iFlora.setOnClickListener(v -> {
            MainActivity.idFlora = floraList.get(position).getId();
            holder.flora = floraItem;
            alert.setIcon(holder.iFlora.getDrawable()).setTitle(holder.tvName.getText())
                    .setPositiveButton("EDIT", (dialog, which) -> {
                        context.startActivity(new Intent(context, EditFloraActivity.class)); })
                    .setNegativeButton("CANCEL", (dialog, which) -> {
                        //no hace nada
            }).show();
        });

    }

    @Override
    public int getItemCount() {
        if(floraList == null) {
            return 0;
        }
        return floraList.size();
    }

    public void setFloraList(List<Flora> floraList) {
        this.floraList = floraList;
        notifyDataSetChanged();
    }

}