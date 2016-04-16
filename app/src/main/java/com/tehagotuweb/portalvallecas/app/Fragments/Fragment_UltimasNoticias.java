package com.tehagotuweb.portalvallecas.app.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import com.tehagotuweb.portalvallecas.app.MainMenuActivity;
import com.tehagotuweb.portalvallecas.app.R;


public class Fragment_UltimasNoticias extends Fragment{

    public static Fragment_UltimasNoticias newInstance() {
        Fragment_UltimasNoticias fragment = new Fragment_UltimasNoticias();
        return fragment;
    }

    public Fragment_UltimasNoticias() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_ultimasnoticias, container, false);

        ((MainMenuActivity) getActivity()).getSupportActionBar().setTitle("Últimas Noticias");

        return view;
    }

}
