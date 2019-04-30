package com.app.emapyc.dir_lugapa;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.app.emapyc.InicioActivity;
import com.app.emapyc.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link lugarespago.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class lugarespago extends Fragment  {
    private OnFragmentInteractionListener mListener;

   /* menu_luga listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader=null;
    HashMap<String, List<String>> listDataChild = null;*/
   private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    List<cobr> items;
    public lugarespago() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
final View viw=inflater.inflate(R.layout.lug_list,container,false);

        items = new ArrayList<>();

        items.add(new cobr(R.drawable.logo_edu_cen, "E. Gran Chaco Oficina central", R.string.educedir,"-22.017565907384743","-63.67634475231171"));
        items.add(new cobr(R.drawable.logo_edu_suc, "E. Gran Chaco sucursal", R.string.edusudir,"-22.002789803122667","-63.67629647254944"));
        items.add(new cobr(R.drawable.logo_san_martin_porres, "Coop. San Martin de Porres", R.string.porresdir,"-22.014087121451638","-63.67869973182678"));
        items.add(new cobr(R.drawable.logo_banco_fortaleza, "Banco Fortaleza", R.string.fortadir,"-22.016778276948337","-63.678918834775686"));
        items.add(new cobr(R.drawable.logo_banco_fassil, "Banco Fassil", R.string.fassildir,"-22.014873","-63.679019"));
        items.add(new cobr(R.drawable.banco_sol_logo, "Banco Sol", R.string.soldir,"-22.01859286646042","-63.67797017097473"));

        // Obtener el Recycler
        recycler = (RecyclerView) viw.findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new cobrAdapter(items,getContext());
        recycler.setAdapter(adapter);

       /* expListView = (ExpandableListView) viw.findViewById(R.id.lvExp);

        // preparing list data
        //prepareListData();

        listAdapter = new menu_luga(getContext(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        FloatingActionButton fab = (FloatingActionButton) viw.findViewById(R.id.fab);
        if (Build.VERSION.SDK_INT>=18){
            fab.setVisibility(View.VISIBLE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ma=new Intent(getActivity(),MapsActivity.class);
                startActivity(ma);
            }
        });*/

        return viw;
    }

    /*private void prepareListData() {
        listDataHeader = new ArrayList<String>();

        listDataChild = new HashMap<String, List<String>>();
        // Adding child data
        listDataHeader.add("Coop. Educadores Gran Chaco Oficina central");
        listDataHeader.add("Coop. Educadores Gran Chaco Sucursal");
        listDataHeader.add("Banco Fortaleza");
        listDataHeader.add("Banco Sol");
        listDataHeader.add("Coop. San Martin de Porres");
        listDataHeader.add("Banco Fassil");

        // Adding child data
        List<String> educe = new ArrayList<String>();
        educe.add(getResources().getString(R.string.educedir));

        List<String> edusu = new ArrayList<String>();
        edusu.add(getResources().getString(R.string.edusudir));

        List<String> fort = new ArrayList<String>();
        fort.add(getResources().getString(R.string.fortadir));

        List<String> sol = new ArrayList<String>();
        sol.add(getResources().getString(R.string.soldir));

        List<String> por = new ArrayList<String>();
        por.add(getResources().getString(R.string.porresdir));

        List<String> fass = new ArrayList<String>();
        fass.add(getResources().getString(R.string.fassildir));

        listDataChild.put(listDataHeader.get(0), educe); // Header, Child data
        listDataChild.put(listDataHeader.get(1), edusu);
        listDataChild.put(listDataHeader.get(2), fort);
        listDataChild.put(listDataHeader.get(3), sol);
        listDataChild.put(listDataHeader.get(4), por);
        listDataChild.put(listDataHeader.get(5), fass);

    }*/

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
