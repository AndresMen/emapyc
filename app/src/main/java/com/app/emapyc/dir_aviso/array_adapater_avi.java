package com.app.emapyc.dir_aviso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.emapyc.R;
import com.app.emapyc.dir_aviso.aviso_cuer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mendez on 01/06/2017.
 */

public class array_adapater_avi extends ArrayAdapter<aviso_cuer> {
    private TextView chatText;
    private TextView hor;
    private TextView fech;
    private List<aviso_cuer> chatAvisoList = new ArrayList<aviso_cuer>();
    @Override
    public void add(aviso_cuer object) {
        chatAvisoList.add(object);
        super.add(object);
    }

    public array_adapater_avi(Context context, int objects) {
        super(context, 0,objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (null==convertView){
            convertView= inflater.inflate(R.layout.aviso, parent, false);
        }
        chatText = (TextView) convertView.findViewById(R.id.avis);
        hor = (TextView) convertView.findViewById(R.id.hora);
        fech = (TextView) convertView.findViewById(R.id.fe);


        aviso_cuer avi=getItem(position);
        chatText.setText(avi.getAvis());
        hor.setText(avi.getMhora());
        fech.setText(avi.getMfecha());

        return convertView;
    }



}
