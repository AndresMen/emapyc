package com.app.emapyc.dir_mensaje;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.emapyc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mendez on 01/06/2017.
 */

public class array_adapater_men extends ArrayAdapter<mensaje_cuer> {
    private TextView chatText;
    private TextView hor;
    private List<mensaje_cuer> chatMessageList = new ArrayList<mensaje_cuer>();
    @Override
    public void add(mensaje_cuer object) {
        chatMessageList.add(object);
        super.add(object);
    }

    public array_adapater_men(Context context, int objects) {
        super(context, 0,objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (null==convertView){
            convertView= inflater.inflate(R.layout.mensaje, parent, false);
        }
        chatText = (TextView) convertView.findViewById(R.id.msgr);
        hor = (TextView) convertView.findViewById(R.id.dato);



        mensaje_cuer mensaj=getItem(position);
        chatText.setText(mensaj.getMmessage());
        hor.setText(mensaj.getMhora());

        return convertView;
    }



}
