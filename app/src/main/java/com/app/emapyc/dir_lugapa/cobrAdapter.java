package com.app.emapyc.dir_lugapa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.emapyc.R;

import java.util.List;

/**
 * Created by Mendez on 13/12/2017.
 */

public class cobrAdapter extends RecyclerView.Adapter<cobrAdapter.cobrViewHolder> {
    private List<cobr> items;
    private Context context;

    public static class cobrViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public TextView visitas;
        public cobrViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imagen);
            nombre = (TextView) v.findViewById(R.id.nombre);
            visitas = (TextView) v.findViewById(R.id.visitas);

        }

    }

    public cobrAdapter(List<cobr> items,Context context) {
        this.items = items;
        this.context=context;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public cobrViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.lug_card, viewGroup, false);
        final cobrViewHolder vh = new cobrViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = vh.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Intent inici=new Intent(context,MapsActivity.class);
                    inici.putExtra("lat",items.get(position).getLat());
                    inici.putExtra("lon",items.get(position).getLon());
                    context.startActivity(inici);

                }
            }

        });

        return vh;
    }

    @Override
    public void onBindViewHolder(cobrViewHolder viewHolder, int i) {
        viewHolder.imagen.setImageResource(items.get(i).getImagen());
        viewHolder.nombre.setText(items.get(i).getNombre());
        viewHolder.visitas.setText(items.get(i).getVisitas());
    }

}

