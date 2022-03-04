package org.izv.flora;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import org.izv.flora.model.entity.Flora;

public class FloraViewHolder extends RecyclerView.ViewHolder {

    public Flora flora;
    public TextView tvName, tvFamily, tvHabitat, tvAlt;
    public ImageView iFlora;
    public FloraViewHolder(@NonNull View itemView) {
        super(itemView);

        iFlora = itemView.findViewById(R.id.iFloraItem);
        tvName = itemView.findViewById(R.id.tvNameItem);
        tvFamily = itemView.findViewById(R.id.tvFamilyItem);
        tvHabitat = itemView.findViewById(R.id.tvHabitatItem);
        tvAlt = itemView.findViewById(R.id.tvAltitudItem);
    }

}
